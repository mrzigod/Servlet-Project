import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ResetDB extends HttpServlet {

    public void init(){
        DbAdapter.dropTables();
        DbAdapter.createTables();
        itemGenetator.filldb();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html");
    }
}
