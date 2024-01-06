import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;


public class DungeonPicker extends HttpServlet {

    public void printDungeon(int level,HttpServletResponse response) throws IOException{
        if(!ShopBuy.haspermission(level)){
            PrintWriter out = response.getWriter();
            int visible_level=1;
            if(level==10) visible_level=2;
            if(level==100) visible_level=3;
            if(level==1000) visible_level=4;

            out.println(
                "<form action = \"Dungeon\" method=\"POST\">  " +
                        "<input type = \"checkbox\" name = \""+visible_level+"\" checked = \"checked\" />" +
                        "<input type=\"submit\" value=\""+visible_level+"\"/> </form>"
            );
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        printDungeon(1,response);
        printDungeon(10,response);
        printDungeon(100,response);
        printDungeon(1000,response);
        out.println(
                "<form action = \"PlayerStatus\" method=\"POST\"> <input type=\"submit\" value=\"Return\"/> </form>"
        );
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}