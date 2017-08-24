package Helper;

/**
 * Created by sJackChen on 2017/8/22.
 */
public class PrintUtil {

    public static boolean printable = true;
    public static void print(String s){
        if(printable) {
            System.out.println(s);
        }
    }

    public static void print(Integer s){
        if(printable) {
            System.out.println(s);
        }
    }
}
