package another;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/mama"})
public class AuthorizationFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        User user = (User)((HttpServletRequest)req).getSession().getAttribute("user");
        if(user!=null){
            ((HttpServletResponse)res).sendRedirect("authorized.jsp");
        }
        else {
            ((HttpServletResponse)res).sendRedirect("unauthorized.jsp");
        }

    }
}
