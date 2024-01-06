import java.util.Random;

public class itemGenetator {

    public static int losowyint(int minval, int maxval,Random r){
        return minval+(int)(r.nextFloat()*(maxval-minval+1));
    }
    public static void generateDungeon(int minsize, int maxsize, int minval, int maxval,int dungeon, int amount) {
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            int p = 97;
            int q = 122;
            int length = 8;
            StringBuilder buffer = new StringBuilder(length);
            for (int j = 0; j < length; j++) {
                buffer.append((char) losowyint(p, q, random));
            }
            String name = buffer.toString();
            int size = losowyint(minsize, maxsize, random);
            int cost = losowyint(minval, maxval, random);
            DbAdapter.registerItem(name,size,cost,dungeon);
        }
    }
    public static void filldb(){
        Random r=new Random();
        generateDungeon(1,5,2,7,2,losowyint(5,10,r));
        generateDungeon(5,15,30,60,3,losowyint(5,10,r));
        generateDungeon(20,100,300, 1000,4,losowyint(5,10,r));
    }
}
