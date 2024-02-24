package another;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CardAnswerDBConnector {
    private final DBConnection connectionHolder = new DBConnection();
    private final String SELECTCARDS = "SELECT first_word,second_word FROM cards where cardset_id = ?::UUID";
    private final String INSERTANSWERS = "INSERT INTO card_answers (cardset_id,question,user_answer,right_answer,answer_user) values (?::UUID,?,?,?,?)";
    public LinkedList<Card> getCardsFromDB(String cardset_id) {
        LinkedList<Card> cards = new LinkedList<>();
        try (PreparedStatement statement = connectionHolder.getConnection().prepareStatement(SELECTCARDS)){
            statement.setString(1,cardset_id);
            ResultSet resultSet = statement.executeQuery();
            int cnt = 0;
            while(resultSet.next()) {
                cards.add(
                        new Card(
                                cnt,
                                resultSet.getString("first_word")
                                ,resultSet.getString("second_word"))
                );
                ++cnt;
            }
            return cards;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendAnswersToDB(String cardset_id, String user_login, List<CardAnswer> answers){
        try (PreparedStatement statement = connectionHolder.getConnection().prepareStatement(INSERTANSWERS)){
            statement.setString(1,cardset_id);
            statement.setString(5,user_login);
            for(CardAnswer answer : answers){
                statement.setString(2,answer.getCard().getQuestion());
                statement.setString(4,answer.getCard().getAnswer());
                statement.setString(3,answer.getAnswer());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
