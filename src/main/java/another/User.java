package another;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Clock;
import java.util.Arrays;

public class User {
    private String nickname;
    private String login;
    private String password_hash;

    public User(String nickname,String login,String password_hash){
        this.nickname = nickname;
        this.login = login;
        this.password_hash = password_hash;
    }
    User(String nickname,String login){
        this(nickname,login,null);
    }

    public boolean isPasswordValid(String password) {
        try {
            return generatePasswordHash(password).equals(password_hash);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String generatePasswordHash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] _password_hash = md.digest(password.getBytes());

        _password_hash = md.digest((byteArray2String(_password_hash)+login).getBytes());

        return byteArray2String(_password_hash);
    }

    public void setPassword_hash(String password) {
        try {
            this.password_hash = generatePasswordHash(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String byteArray2String(byte[] array){
        StringBuilder stringBuilder = new StringBuilder();
        for(byte b: array){
            stringBuilder.append(String.format("%02x",b));
        }
        return stringBuilder.toString();
    }

    public String getLogin() {
        return login;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword_hash() {
        return password_hash;
    }


}
