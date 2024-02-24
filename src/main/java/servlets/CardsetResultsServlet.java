package servlets;

import another.CardAnswer;
import another.CardAnswerDBConnector;
import another.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/result")
public class CardsetResultsServlet extends HttpServlet {
    private final CardAnswerDBConnector cardAnswerDBConnector = new CardAnswerDBConnector();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session =  req.getSession();
        req.setAttribute("answers",session.getAttribute("answers"));
        req.setAttribute("strings", new String[]{"Вопрос", "Ответ", "Правильный ответ","Ваш результат не сохранён. Чтобы его сохранить, войдите в свой профиль","На главную"});
        req.setAttribute("isLogged",req.getSession().getAttribute("user")!=null);

        if(session.getAttribute("user")!=null&&
                session.getAttribute("isAnswersInDB")!=null&&
                !(boolean) session.getAttribute("isAnswersInDB")) {
            cardAnswerDBConnector.sendAnswersToDB(
                    (String) session.getAttribute("answersCardset_id"),
                    ((User)session.getAttribute("user")).getLogin(),
                    Arrays.stream(((CardAnswer[])session.getAttribute("answers"))).collect(Collectors.toList())
                    );

        }
        getServletContext().getRequestDispatcher("/results.jsp").forward(req,resp);
    }
}
