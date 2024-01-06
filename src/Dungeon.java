import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import jakarta.servlet.*;
import jakarta.servlet.http.*;


public class Dungeon extends HttpServlet {
    private int current_dungeon;
    public Item zbierany;

    public void init(){
        current_dungeon=0;
    }

    public void search(HttpServletResponse response) throws IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        ArrayList<Integer> AvailableItems=DbAdapter.getItemsinDungeonNotInInventory(PlayerStatus.name,current_dungeon);
        int a=AvailableItems.size();
        if(a>0) {
            Random r = new Random();
            int b = itemGenetator.losowyint(0, a - 1, r);
            Item item = DbAdapter.findItem(AvailableItems.get(b));
            zbierany = item;
            if (item.id != -1) item.printItem(response);
            if (item.weight + PlayerStatus.current_inventory <= PlayerStatus.max_inv_size) {
                out.println(
                        "<form action = \"Dungeon\" method = \"POST\"> <input type = \"checkbox\" name = \"collect\" checked = \"checked\" /> <input type = \"submit\" value = \"collect?\" /> </form>"
                );
            }
        }
    }

    public void collect(){
        PlayerStatus.current_inventory+=zbierany.weight;
        DbAdapter.collectItem(PlayerStatus.name,zbierany.id);
    }

    public void checkd(HttpServletRequest request){
        if(request.getParameter("1")!=null) {
            current_dungeon = 1;
        }
        if(request.getParameter("2")!=null) {
            current_dungeon = 2;
        }
        if(request.getParameter("3")!=null){
            current_dungeon = 3;
        }
        if(request.getParameter("4")!=null){
            current_dungeon = 4;
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        checkd(request);
        if(request.getParameter("collect")!=null)
            collect();
        int current_items=DbAdapter.getItemsinDungeonNotInInventory(PlayerStatus.name,current_dungeon).size();
        out.println("Dungeon: "+current_dungeon+"\n ");
        out.println("Items left: "+current_items+"\n");
        if(request.getParameter("search")!=null)
            search(response);
        if(current_items>0){
            out.println(
                    "<form action = \"Dungeon\" method = \"POST\"> " +
                            "<input type = \"checkbox\" name = \"search\" checked = \"checked\" /> " +
                            "<input type = \"submit\" value = \"search\" /> </form>"
            );
        }
        else
        {
            out.println("DUNGEON EMPTY");
        }
        out.println(
                        "<form action = \"PlayerStatus\" method=\"POST\"> <input type=\"submit\" value=\"Return\"/> </form>"
        );
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
