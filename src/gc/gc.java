package gc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JackChen on 2017/6/2.
 */
public class gc {
    public static int COUNT = 1024*8;
    public static Map map = new HashMap();
//    public static void main(String[] args)throws Exception{
////        List list1 = new ArrayList();
////        for(int i = 0 ; i < (COUNT/4); i++){
////            list1.add(""+i);
////        }
////        System.gc();
////        list1=null;
//        Thread.sleep(5000);
//        System.out.println("1");
//        List list = new ArrayList<String>();
//        for(int i = 0 ; i < COUNT; i++){
//            list.add(""+i);
//        }
//        map.put(1,1);
//        list = null;
//        System.out.println("2");
//        System.gc();
//
//        Thread.sleep(5000);
//        System.out.println(map.get(1) instanceof String);
//        System.out.println("finish");
//    }

    private static   sun.misc.Unsafe UNSAFE;

   static {
       UNSAFE = sun.misc.Unsafe.getUnsafe();
   }
    public static void main(String[] args){
        System.out.println(UNSAFE);
    }
}
