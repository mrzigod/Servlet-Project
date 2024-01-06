import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import jakarta.servlet.*;
import jakarta.servlet.http.*;


public class Shop extends HttpServlet  {

    public void init(){

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(
                "<form action = \"ShopSell\" method=\"POST\">" +
                "<input type = \"submit\" value = \"sell\" /> </form>"+
                        "<form action = \"ShopBuy\" method=\"POST\">" +
                        "<input type = \"submit\" value = \"buy\" /> </form>"+
                "<form action = \"PlayerStatus\" method=\"POST\"> <input type=\"submit\" value=\"Player Status\"/> </form>"
        );
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
