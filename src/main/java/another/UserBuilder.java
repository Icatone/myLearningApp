package another;

import java.sql.*;
import java.util.ArrayList;

public class UserBuilder {
    private static final String SELECTEXISTSUSERBYLOGIN = "SELECT * FROM users where users.login = ?;";
    private static final String INSERTUSER = "INSERT INTO users (login,password_data,nickname) VALUES (?,?,?)";
    private static final String ISUSEREXIST = "SELECT 'ok' from users where login=?";
    private final DBConnection connectionHolder = new DBConnection();

    public User getUserFromDB(String login){
        try (PreparedStatement statement = connectionHolder.getConnection().prepareStatement(SELECTEXISTSUSERBYLOGIN)){
            statement.setString(1,login);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return new User(resultSet.getString("nickname"),
                        resultSet.getString("login"),
                        resultSet.getString("password_data"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public User getUserByData(String nickname,String login,String password) {
        User _user = new User(nickname,login);
        _user.setPassword_hash(password);
        return _user;
    }

    public void insertUserInDB(User user) {

        try (PreparedStatement statement = connectionHolder.getConnection().prepareStatement(INSERTUSER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword_hash());
            statement.setString(3, user.getNickname());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public String[] checkUserRegistrationData(String nickname,String login,String password) {
        ArrayList<String> response = new ArrayList<>();
        if(isUserExistsInDB(login)){
            response.add("exists");
        }
        if(password.length()<8){
            response.add("shortPassword");
        }
        if( response.isEmpty() ) { return new String[] {"ok"}; }

        else {return response.toArray(String[]::new);}
    }
    public boolean isUserExistsInDB(String login){
        try(PreparedStatement statement = connectionHolder.getConnection().prepareStatement(ISUSEREXIST)){
            statement.setString(1,login);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
