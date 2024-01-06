import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class Redirect extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // New location to be redirected
        String site = "http://localhost:8080/";
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", site);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
} 