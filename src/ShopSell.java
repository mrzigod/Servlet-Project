import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;


public class ShopSell extends HttpServlet  {

    public void init(){

    }
    public void sell(Item a){
        PlayerStatus.money+=a.value;
        PlayerStatus.current_inventory-=a.weight;
        DbAdapter.sellItem(PlayerStatus.name,a.id);
        DbAdapter.save(PlayerStatus.name, PlayerStatus.current_inventory, PlayerStatus.max_inv_size, PlayerStatus.money,PlayerStatus.permissions,PlayerStatus.won);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        Enumeration paramNames= request.getParameterNames();
        while(paramNames.hasMoreElements()){
            String paramName=(String)paramNames.nextElement();
            int itemId=Integer.valueOf(paramName);
            sell(DbAdapter.findItem(itemId));
        }
        PrintWriter out = response.getWriter();
        ArrayList<Item> inventory=DbAdapter.getCurrentInventory(PlayerStatus.name);
        inventory.forEach((a) -> a.printSellableItem(response));
        if(inventory.size()>0) {
            out.println(
                    "<li><input type = \"submit\" value = \"sell\" /> </form>"
            );
        }
        else{
            out.println(
                    "<li>No Items"
            );
        }

        out.println(
                "<form action = \"PlayerStatus\" method=\"POST\"> <input type=\"submit\" value=\"Player Status\"/> </form>"
        );
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
