package test;

import org.apache.directmemory.DirectMemory;
import org.apache.directmemory.cache.CacheService;
import org.apache.directmemory.measures.Ram;
import org.apache.log4j.PropertyConfigurator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by feichen211389 on 2017/8/10.
 */
public class DirectMemoryTest {

    static CacheService<String, Object> directCache ;

    public static void main(String[] args) throws Exception{

        PropertyConfigurator.configure("d://log4j.properties");
        directCache = new DirectMemory<String, Object>().setNumberOfBuffers(10)
                .setSize(Ram.Mb(128)).setInitialCapacity(300000).setDisposalTime(1000 * 60 * 60 * 8)
                .setConcurrencyLevel(200).newCacheService("127.0.0.1", 17951);
        for(int i = 0; i < 1000; i++){
            test();
            System.out.println();
//            directCache
        }

//        directCache.close();
    }

    static String s = "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
    public static void test() throws Exception{
        final int countPerThread = 40000;
        for(int i = 0; i < countPerThread; i++){
            directCache.put("1_"+i, s+i);
        }

        List<Thread> addThreads = new ArrayList<Thread>();
        int prefixStart = 2;
        int addThreadCount  = 2;
        final CountDownLatch countDownLatch = new CountDownLatch(addThreadCount + 1);
        System.out.println("init finish");
        for(int threadIndex = prefixStart; threadIndex < prefixStart + addThreadCount; threadIndex++){
            final int F_threadIndex = threadIndex;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    countDownLatch.countDown();
                    for(int i = 0; i < countPerThread; i++){
                        directCache.put(F_threadIndex + "_"+i, s+i);
                    }
                }
            });
            addThreads.add(thread);
        }

        Thread clearThread = new Thread(new Runnable() {
            @Override
            public void run() {
                countDownLatch.countDown();
                directCache.clear();
            }
        });

        for(Thread thread : addThreads){
            thread.start();
        }
        clearThread.start();
        for(Thread thread : addThreads){
            thread.join();
        }
        clearThread.join();

        System.out.println("put clead finish, start retrieve");
        String key = "";
        for(int i = 1; i <= addThreadCount + 1; i++){
            try {
                for(int j = 0; j < countPerThread; j ++) {
                        key = addThreadCount + "_" + j;
                        directCache.retrieve(key);
                }
            }catch(Exception e){
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+key);
                directCache.retrieve(key);
                e.printStackTrace();
                break;
            }
        }

        System.out.println("read again");
        for(int i = 1; i <= addThreadCount + 1; i++){
            try {
                for(int j = 0; j < countPerThread; j ++) {
                    key = addThreadCount + "_" + j;
                    directCache.retrieve(key);
                }
            }catch(Exception e){
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+key);
                directCache.retrieve(key);
                e.printStackTrace();
                break;
            }
        }
    }
}
