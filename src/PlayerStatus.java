import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class PlayerStatus extends HttpServlet {

    public static String name;
    public static int current_inventory;
    public static int max_inv_size;
    public static int money;
    public static int permissions;
    public static int won;

    public void init() throws ServletException {
        // Do required initialization
        name = "Player";
        current_inventory=0;
        max_inv_size=10;
        money=10;
        permissions=1;
        won=0;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type
        response.setContentType("text/html");
        if (request.getParameter("name") != null){
            name = request.getParameter("name");
            if(DbAdapter.hasPlayer(name)){
                DbAdapter.load(name);
                current_inventory=DbAdapter.getCurrentInventorySize(name);
            }
            else{
                DbAdapter.registerPlayer(name);
                current_inventory=0;
                max_inv_size=10;
                money=10;
                permissions=1;
                won=0;
            }
        }
        if(request.getParameter("save")!=null){
            DbAdapter.save(name,current_inventory,max_inv_size,money,permissions,won);
        }
        PrintWriter out = response.getWriter();
        String title = "Player Status";
        String docType =
                "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor = \"#f0f0f0\">\n" +
                "<h1 align = \"center\">" + title + "</h1>\n" +
                "<ul>\n" +
                "  <li><b>Name</b>: "
                + name + "\n" +
                "  <li><b>Money</b>: "
                + money + "\n" +
                "  <li><b>Inventory</b>: "
                + current_inventory+ "/"+ max_inv_size+"\n" +
                "</ul>\n" +
                "</body>" +
                "<form action = \"Shop\" method=\"POST\"> <input type=\"submit\" value=\"Shop\"/> </form>"+
                "<form action = \"DungeonPicker\" method=\"POST\"> <input type=\"submit\" value=\"Dungeon\"/> </form>"+
                //"<form action = \"PlayerStatus\" method = \"POST\"> <input type = \"checkbox\" name = \"save\" checked = \"checked\" /> <input type = \"submit\" value = \"save\" /> </form>"+
                "<form action = \"SignIn\" method = \"POST\"> <input type = \"submit\" value = \"Sign Out\" /> </form>"+
                "</html>"
        );
        if(won==1){
            out.println(
                    "<form action = \"VictoryScreen\" method=\"POST\"> <input type=\"submit\" value=\"Win\"/> </form>"
            );
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

            doGet(request, response);
        }

}