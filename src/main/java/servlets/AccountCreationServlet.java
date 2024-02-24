package servlets;

import another.User;
import another.UserBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/action/create_account")
public class AccountCreationServlet extends HttpServlet {
    private UserBuilder userBuilder;

    @Override
    public void init() throws ServletException {
        userBuilder = new UserBuilder();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] uBResp = userBuilder.checkUserRegistrationData(
                req.getParameter("nickname"),
                req.getParameter("login"),
                req.getParameter("password")
        );
        if(uBResp[0].equals("ok")){
            User user = userBuilder.getUserByData(
                    req.getParameter("nickname"),
                    req.getParameter("login"),
                    req.getParameter("password")
            );
            userBuilder.insertUserInDB(user);
            req.getSession().setAttribute("user",user);
            resp.sendRedirect("../authorized.jsp");
        }
        else {
            resp.sendRedirect("../registration.html?" +
                    Arrays.stream(uBResp)
                            .map((x)->"error="+x)
                            .collect(Collectors.joining("&"))
            );
        }
    }

}
