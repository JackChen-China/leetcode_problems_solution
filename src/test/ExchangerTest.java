package test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by feichen211389 on 2017/7/8.
 */
public class ExchangerTest {
    private static final Exchanger<String> exgr = new Exchanger<String>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);
    public static void main(String[] args){
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String A = "银行流水A";
                    exgr.exchange(A);
                }catch (InterruptedException e){

                }
            }
        });
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String B = "银行流水B";
                    String A = exgr.exchange("A");
                    System.out.println(A.equals(B)+" " + A + B);
                }catch (InterruptedException e){

                }
            }
        });
        threadPool.shutdown();
    }
}
