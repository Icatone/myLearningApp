package servlets;

import another.Card;
import another.Cardset;
import another.DBConnection;
import another.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

@WebServlet(urlPatterns = "/seeCardsets")
public class seeCardsetsServlet extends HttpServlet {
    private final DBConnection connectionHolder = new DBConnection();
    private final String SELECTCARDSETS = "SELECT cardset_id,cardset_name,cardset_description,cardset_author,nickname from cardsets join users on cardset_author = login";
    private final String DELETECARDSET = "DELETE FROM cardsets where cardset_id=?::UUID";
    private final String[] strings= new String[] {
            "Мои наборы",
            "Начать тренировку",
            "Редактировать",
            "Удалить",
            "Автор:",
            "Все наборы",
            "На главную"
    };
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User)req.getSession().getAttribute("user");
        Cardset[] cardsets = getCardsetsFromDB();
        Cardset[] user_cardsets = {};
        if(user != null){
            req.setAttribute("isLogged",true);
            user_cardsets = Arrays.stream(cardsets).filter(cardset -> Objects.equals(cardset.author_login, user.getLogin())).toArray(Cardset[]::new);
        }
        else {
            req.setAttribute("isLogged",false);

        }
        req.setAttribute("cardsets",cardsets);
        req.setAttribute("user_cardsets",user_cardsets);
        req.setAttribute("strings",strings);
        getServletContext().getRequestDispatcher("/seeCardsets.jsp").forward(req,resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cardset_id = req.getHeader("cardset_id");
        deleteCardsetFromDB(cardset_id);
    }

    private Cardset[] getCardsetsFromDB(){
        LinkedList<Cardset> cardsets = new LinkedList<>();

        try (PreparedStatement statement = connectionHolder.getConnection().prepareStatement(SELECTCARDSETS)) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                cardsets.add(new Cardset(
                        resultSet.getString("cardset_id"),
                        resultSet.getString("cardset_name"),
                        resultSet.getString("cardset_description"),
                        resultSet.getString("cardset_author"),
                        resultSet.getString("nickname")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cardsets.toArray(Cardset[]::new);
    }
    private void deleteCardsetFromDB(String cardset_id){
        try (PreparedStatement statement = connectionHolder.getConnection().prepareStatement(DELETECARDSET)) {
            statement.setString(1,cardset_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
