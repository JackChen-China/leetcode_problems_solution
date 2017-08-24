package test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 */
public class ConcurrentHashMapIteratorTest {
//    public static void main(String[] args) {
//
//        Map<String,String> map =
//                new ConcurrentHashMap<String, String>();
//
//        map.put("Java", "Java");
//        map.put("Honk", "Honk");
//        map.put("Test", "Test");
//
//        System.out.println("Pring value Set:\n");
//        Set<String> keySet = map.keySet();
//
//        System.out.println(keySet);
//        System.out.println("\nPring value using iterator:\n");
//        Iterator<String> iterator = map.keySet().iterator();
//        while(iterator.hasNext()) {
//            String key = (String)iterator.next();
//            String val = (String)map.get(key);
//            map.put("New value","New value");
//            System.out.println("Key:" + key
//                    + "  Value: " + val);
//        }
//
//    }

    public static void main(String[] args)throws Exception{
//        Map<String,Integer> map =
//                new ConcurrentHashMap<String, Integer>();

        for(int i = 0; i < 100; i++){
            test();
        }

    }
    public static void test() throws Exception{
        final Set<Pointer> pointers = Collections.newSetFromMap(new ConcurrentHashMap());
        final Map storePointers = new HashMap();

        long start = System.currentTimeMillis();
        for(int i = 0; i < 800000; i++){
            Pointer p = new Pointer("1_"+i);
            pointers.add(p);
            storePointers.put(p,1);
        }
//        pointers.add(new Pointer("Java"));
//        pointers.add(new Pointer("Honk"));
//        pointers.add(new Pointer("Test"));

        int threadSize = 3;
        final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        final Iterator iterator = pointers.iterator();
        List<Thread> addThreads = new ArrayList<Thread>();
        int startThreadIndex = 2;
        for(int t = startThreadIndex; t < startThreadIndex + threadSize -1; t++) {
            final int threadIndex = t;
            Thread addThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    countDownLatch.countDown();
                    for (int i = 0; i < 200000; i++) {
                        pointers.add(new Pointer(threadIndex + "_" + i));
                    }
//                System.out.println("addThread end");
                }
            });
            addThreads.add(addThread);
        }


        Thread freeAndClearThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //free
                while(iterator.hasNext()) {
                    Pointer p = (Pointer)iterator.next();
                    countDownLatch.countDown();
                    p.setFree(true);
                }
                //clear
//                pointers.clear();
//                System.out.println("freeAndClearThread end");
            }
        });

        for(Thread thread : addThreads){
            thread.start();
        }
        freeAndClearThread.start();

        for(Thread thread : addThreads){
            thread.join();
        }
        freeAndClearThread.join();
//        Thread.sleep(1000l);
        System.out.println("main iterator start, cost:" + (System.currentTimeMillis() - start));
        Set set = storePointers.keySet();
        Iterator itr = set.iterator();
        int x = 0;
        while(itr.hasNext()){
            Pointer poi = (Pointer)itr.next();
            if(!poi.isFree()){
                String key = poi.getKey();
                System.out.println(poi.getKey());
                if(key.startsWith("1_")){
//                    System.out.println(poi.getKey());
                    x++;
                }
            }
        }
        System.out.println("error count: " + x);
//        System.out.println("total count: " + pointers.size());
        System.out.println();
    }
}


class Pointer{
    String key;
    public final AtomicBoolean free = new AtomicBoolean(false);

    Pointer(String key){
        this.key = key;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    public void setFree(boolean flag){
        free.set(flag);
    }

    public boolean isFree() {
        return this.free.get();
    }

}