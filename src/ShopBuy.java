import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;


public class ShopBuy extends HttpServlet  {

    public void init(){

    }
    public void buyCapacity(int price, int gain) {
        if (PlayerStatus.money >= price) {
            PlayerStatus.money -= price;
            PlayerStatus.max_inv_size += gain;
            DbAdapter.save(PlayerStatus.name, PlayerStatus.current_inventory, PlayerStatus.max_inv_size, PlayerStatus.money,PlayerStatus.permissions,PlayerStatus.won);
        }
    }

    public void buyPermissions(int price, int gain){
        if (PlayerStatus.money >= price) {
            PlayerStatus.money -= price;
            PlayerStatus.permissions += gain;
            DbAdapter.save(PlayerStatus.name, PlayerStatus.current_inventory, PlayerStatus.max_inv_size, PlayerStatus.money,PlayerStatus.permissions,PlayerStatus.won);
        }
    }

    public static boolean haspermission(int permission){
        int curr_perm=PlayerStatus.permissions;
        curr_perm=curr_perm%(permission*10);
        return curr_perm < permission;
    }

    public void printpermissions(int price, int permission,HttpServletResponse response) throws IOException {
        if (haspermission(permission)) {
            int visible_permission=1;
            if(permission==10) visible_permission=2;
            if(permission==100) visible_permission=3;
            if(permission==1000) visible_permission=4;
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(
                    "<li> cost: " + price +
                            " <form action = \"ShopBuy\" method=\"POST\">" +
                            " <input type = \"checkbox\" name = \"" + permission + "\" checked = \"checked\" />" +
                            " <input type = \"submit\" value = \"Permission: " + visible_permission + "\" /> </form>\n"
            );
        }
    }

    public void win(int price){
        if(PlayerStatus.money>=price)
        {
            PlayerStatus.money-=price;
            PlayerStatus.won=1;
            DbAdapter.save(PlayerStatus.name, PlayerStatus.current_inventory, PlayerStatus.max_inv_size, PlayerStatus.money,PlayerStatus.permissions,PlayerStatus.won);
        }
    }

    public void checkp(HttpServletRequest request){
        if(request.getParameter("1")!=null){
            buyPermissions(0,1);
        }
        if(request.getParameter("10")!=null){
            buyPermissions(5,10);
        }
        if(request.getParameter("100")!=null){
            buyPermissions(60,100);
        }
        if(request.getParameter("1000")!=null){
            buyPermissions(500,1000);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        if(request.getParameter("buy_more_space")!=null){
            buyCapacity(10,5);
        }
        if(request.getParameter("buy_more_space2")!=null){
            buyCapacity(100,50);
        }
        if(request.getParameter("win")!=null){
            win(10000);
        }
        checkp(request);
        printpermissions(0,1,response);
        printpermissions(5,10,response);
        printpermissions(60,100,response);
        printpermissions(500,1000,response);
        PrintWriter out = response.getWriter();
        out.println(
                "<li>cost: 10\n bonus capacity: 5\n"+
                "<form action = \"ShopBuy\" method=\"POST\"> <input type = \"checkbox\" name = \"buy_more_space\" checked = \"checked\" /> <input type = \"submit\" value = \"buy more space\" /> </form>\n"+
                        "<li>cost: 100\n bonus capacity: 50\n"+
                        "<form action = \"ShopBuy\" method=\"POST\"> <input type = \"checkbox\" name = \"buy_more_space2\" checked = \"checked\" /> <input type = \"submit\" value = \"buy more space\" /> </form>\n"+
                        "<li>cost:10000 win"+
                        "<form action = \"ShopBuy\" method=\"POST\"> <input type = \"checkbox\" name = \"win\" checked = \"checked\" /> <input type = \"submit\" value = \"win\" /> </form>\n"+
                "<form action = \"PlayerStatus\" method=\"POST\"> <input type=\"submit\" value=\"Player Status\"/> </form>"
        );
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
