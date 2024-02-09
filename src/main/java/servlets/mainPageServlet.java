package aboba;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.logging.Logger;


@WebServlet(urlPatterns = "/hello")
public class mainPageServlet extends HttpServlet {
    final Logger logger = Logger.getLogger(this.getClass().getName());
    @Override
    public void init() throws ServletException {
        super.init();
        logger.info("abdula");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        res.setStatus(200);
        PrintWriter writer = res.getWriter();

        try(FileReader reader = new FileReader("/src/main/resources/unauthorized.html")) {
            int smth = reader.read();
            while(smth!=-1) {
                writer.print(smth);
                smth = reader.read();
            }
        }
        writer.flush();
    }
}
