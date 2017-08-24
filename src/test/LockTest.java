package test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by feichen211389 on 2017/7/7.
 */
public class LockTest {
    private static Object mutex = new Object();

    public static void main(String[] args)throws Exception{
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread1 start");
                synchronized (mutex){
                    System.out.println("thread1 enter mutex");
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    System.out.println("thread2 start");
                    synchronized (mutex){
                        System.out.println("thread2 enter mutex");
                        Thread.sleep(2000);
                        int x = 0/0;
                        System.out.println("thread2 end");
                    }
                }catch (Exception e){
                    System.out.println("catch");
                }finally {
                    System.out.println("f enter");
                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){}
                    System.out.println("finally");
                }
            }
        });
        thread2.start();
        Thread.sleep(100);
        thread1.start();
        System.out.println("main");
        countDownLatch.await();
        Thread.sleep(5000);
    }
}
