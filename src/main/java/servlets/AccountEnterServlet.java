package servlets;

import another.DBConnection;
import another.User;
import another.UserBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = "/action/enter_account")
public class AccountEnterServlet extends HttpServlet {
    private UserBuilder userBuilder;

    @Override
    public void init() throws ServletException {
        userBuilder = new UserBuilder();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = userBuilder.getUserFromDB(req.getParameter("login"));
        if(user!=null&&user.isPasswordValid(req.getParameter("password"))){
            req.getSession().setAttribute("user",user);
            resp.sendRedirect("../authorized.jsp");
        }
        else {
            resp.sendRedirect("../unauthorized.jsp");
        }
    }
}