package servlets;

import another.Card;
import another.DBConnection;
import another.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/cardsetCreation")
public class CardsetCreationServlet extends HttpServlet {
    private final DBConnection connection = new DBConnection();
    private final String[] strings = new String[]{
            "Для того чтобы создавать наборы карточек, необходимо быть авторизованым. Войдите или создайте аккаунт",
            "Название набора:",
            "Название",
            "Описание:",
            "Описание",
            "Вопрос",
            "Ответ",
            "Добавить строку",
            "Удалить строку",
            "Сохранить",
            "название не может быть пустым",
            "измените название",
            "пожалуйста заполните все строки или удалите ненужные",
            "набор успешно сохнанён",
            "Что-то пошло не так",
            "На главную"};
    private final String INSERTCARDSET = "INSERT INTO cardsets (cardset_name,cardset_description,cardset_author) values (?,?,?) RETURNING cardset_id";
    private final String INSERTCARDS = "INSERT INTO cards (cardset_id,first_word,second_word,card_id) values (?::UUID,?,?,?)";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("strings",strings);
        req.setAttribute("isLogged",req.getSession().getAttribute("user")!=null);
        getServletContext().getRequestDispatcher("/cardsetCreation.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> cardset = objectMapper.readValue(req.getReader(), new TypeReference<>(){});
        try (PreparedStatement statement = connection.getConnection().prepareStatement(INSERTCARDSET);
             PreparedStatement cardsStatement = connection.getConnection().prepareStatement(INSERTCARDS)) {
            statement.setString(1,(String) cardset.get("name"));
            statement.setString(2,(String) cardset.get("description"));
            statement.setString(3,((User)req.getSession().getAttribute("user")).getLogin());

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            cardsStatement.setString(1,resultSet.getString("cardset_id"));

            LinkedHashMap<String,String>[] cards = ((ArrayList<LinkedHashMap<String,String>>)cardset.get("cards")).toArray(LinkedHashMap[]::new);
            int i = 0;
            for(LinkedHashMap<String,String> card : cards){
                cardsStatement.setString(2,card.get("question"));
                cardsStatement.setString(3,card.get("answer"));
                cardsStatement.setInt(4,i);

                cardsStatement.addBatch();
                ++i;
            }
            cardsStatement.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
