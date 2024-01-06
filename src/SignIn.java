import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class SignIn extends HttpServlet {

    public void init() throws ServletException {
        try {
            DbAdapter.connect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type
        response.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println(
                "<form action = \"PlayerStatus\" method = \"POST\"> Name: <input type = \"text\" name = \"name\"> <input type = \"submit\" value = \"Sign In\" /> </form>"
        );
    }
}