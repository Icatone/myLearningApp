package servlets;

import another.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/action/cardset")
public class CardsetHandlerServlet extends HttpServlet {
    private final CardAnswerDBConnector cardAnswerDBConnector = new CardAnswerDBConnector();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cardset_id = req.getHeader("cardset_id");
        List<Card> cardset = cardAnswerDBConnector.getCardsFromDB(cardset_id);
        final ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json");
        mapper.writeValue(resp.getOutputStream(),cardset);
        resp.flushBuffer();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

            User user = (User) session.getAttribute("user");
            BufferedReader reader = req.getReader();
            ObjectMapper objectMapper = new ObjectMapper();
            List<CardAnswer> answers = objectMapper.readValue(req.getReader(), new TypeReference<>() {
            });
            String cardset_id = req.getHeader("cardset_id");
            if (user != null) {
                cardAnswerDBConnector.sendAnswersToDB(cardset_id, user.getLogin(), answers);
            }
            else{
                session.setAttribute("isAnswersInDB",false);
                session.setAttribute("answersCardset_id", cardset_id);
            }
            session.setAttribute("answers", answers.toArray(CardAnswer[]::new));

    }


}
