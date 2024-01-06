import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Item {
    public String name;
    public int id;
    public int weight;
    public int value;
    Item(){

    }
    void printItem(HttpServletResponse response){
        try {
            PrintWriter out = response.getWriter();
            out.println(
                    "<li>"+name + "\n weight:" + weight + "\n value" + value+"\n"
            );
            out.println("\n");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void printSellableItem(HttpServletResponse response){
        try {
            PrintWriter out = response.getWriter();
            out.println(
                    "<li>"+name + "\n weight:" + weight + "\n value" + value+
                            "<form action = \"ShopSell\" method = \"POST\"> <input type = \"checkbox\" name ="+id+ " checked = \"checked\" \n/>"
            );
            out.println("\n");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
