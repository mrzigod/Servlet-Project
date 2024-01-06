import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DbAdapter {
    public static String user = "postgres";

    public static String password = "postgres";

    public static String jdbcUrl = "jdbc:postgresql://localhost:5433/";

    private static Connection connection = null;

    public static synchronized void connect() throws SQLException {
        connection = DriverManager.getConnection(jdbcUrl, user, password);
    }

    public static void dropTables() {
        try {
            Statement statement = connection.createStatement();
            String sql = "Drop table if exists Players cascade; " +
                    "Drop table if exists Items cascade; " +
                    "Drop table if exists Inventories;"+
                    "Drop sequence if exists item_ids";
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTables() {
        try {
            Statement statement = connection.createStatement();
            Scanner scanner = new Scanner(DbAdapter.class.getResourceAsStream("Database.sql"));
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNext()) {
                builder.append('\n').append(scanner.nextLine());
            }
            statement.executeUpdate(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean hasPlayer(String playerName) {
        try {
            Statement statement = connection.createStatement();
            String query = "Select money from Players where name=\'" + playerName + "\'";
            ResultSet result = statement.executeQuery(query);
            return result.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
        return false;
    }

    public static void registerPlayer(String username) {
        try {
            Statement statement = connection.createStatement();
            String query = "Insert into Players(name, current_inventory, max_inv_size,money,permissions) VALUES(?, ?, ?,?,?)";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, username);
            pst.setInt(2, 0);
            pst.setInt(3, 10);
            pst.setInt(4, 10);
            pst.setInt(5,1);
            pst.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save(String username, int current_inventory, int max_inv_size, int money,int permissions,int won) {
        try {
            String query = "Update Players \n" +
                    "set current_inventory = ?, max_inv_size = ?,money = ?,permissions = ?, won=? \n" +
                    "where name = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(6, username);
            pst.setInt(1, current_inventory);
            pst.setInt(2, max_inv_size);
            pst.setInt(3, money);
            pst.setInt(4, permissions);
            pst.setInt(5,won);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load(String username) {
        try {
            Statement statement = connection.createStatement();
            String query = "Select * from Players where name = \'" + username + "\'";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                PlayerStatus.max_inv_size = result.getInt("max_inv_size");
                PlayerStatus.money = result.getInt("money");
                PlayerStatus.permissions=result.getInt("permissions");
                PlayerStatus.won=result.getInt("won");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void collectItem(String username, int id) {
        try {
            Statement statement = connection.createStatement();
            String query = "Insert into Inventories values(?,?)";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, username);
            pst.setInt(2, id);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sellItem(String username, int id) {
        try {
            Statement statement = connection.createStatement();
            String query = "delete from Inventories " +
                    "where player_name = ? and item_id = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, username);
            pst.setInt(2, id);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Item findItem(int id) {
        try {
            Statement statement = connection.createStatement();
            String query = "Select * from Items where item_id=\'" + id + "\'";
            ResultSet result = statement.executeQuery(query);
            Item A = new Item();
            if (result.next()) {
                A.id = id;
                A.name = result.getString("item_name");
                A.weight = result.getInt("item_size");
                A.value = result.getInt("cost");
                return A;
            }
            A.id = -1;
            return A;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Item();
    }

    public static int getCurrentInventorySize(String username) {
        try {
            Statement statement = connection.createStatement();
            String query = "Select sum(item_size) from Inventories join Items on Items.item_id=Inventories.item_id where player_name=\'" + username + "\'";
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                return result.getInt("sum");
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static ArrayList<Item> getCurrentInventory(String username) {
        ArrayList<Item> lista = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "Select * from Inventories join Items on Items.item_id=Inventories.item_id where player_name=\'" + username + "\'";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                Item item= new Item();
                item.weight=result.getInt("item_size");
                item.value=result.getInt("cost");
                item.name=result.getString("item_name");
                item.id=result.getInt("item_id");
                lista.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static void registerItem(String name, int size, int cost, int dungeon) {
        try {
            Statement statement = connection.createStatement();
            String query = "Insert into Items(item_name, item_size, cost,dungeon) VALUES(?, ?, ?,?)";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, name);
            pst.setInt(2, size);
            pst.setInt(3, cost);
            pst.setInt(4, dungeon);
            pst.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ArrayList<Integer> getItemsInDungeon(int dungeon) {
        ArrayList<Integer> lista = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "Select item_id from Items where dungeon=\'" + dungeon + "\'";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                lista.add(result.getInt("item_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static ArrayList<Integer> getItemsInInventory(String username) {
        ArrayList<Integer> lista = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "Select item_id from Inventories where player_name=\'" + username + "\'";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                lista.add(result.getInt("item_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public static ArrayList<Integer> getItemsinDungeonNotInInventory(String username, int dungeon){
        ArrayList<Integer> DungeonItems=getItemsInDungeon(dungeon);
        ArrayList<Integer> InventoryItems=getItemsInInventory(username);
        for (int i=0;i<InventoryItems.size();i++) {
            DungeonItems.remove(InventoryItems.get(i));
        }
        return DungeonItems;
    }
}
