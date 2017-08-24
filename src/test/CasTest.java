package test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class CasTest {

    static int a = 0;
    static AtomicInteger b = new AtomicInteger(0);

    public static void main(String[] args) throws Exception{
        final long start = System.nanoTime();
        int x = 5;
        final CyclicBarrier ba = new CyclicBarrier(x);

        final CountDownLatch latch = new CountDownLatch(x);
        for (int i = 0; i < x; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        ba.await();
                        for (int i = 0; i < 1000000; i++) {
                            blockingAdd(1);
//                            nonBlockingAdd(1);
                        }
                        latch.countDown();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        latch.await();

//        if (b.get() == 100000000) {
            System.out.println((System.nanoTime() - start)/1000000);
//        }
//
//        if (a == 100000000) {
            System.out.println((System.nanoTime() - start)/1000000);
//        }

    }

    public synchronized static void blockingAdd(int i) {
        a = a + i;
    }

    public static int nonBlockingAdd(int i) {
        return b.getAndAdd(i);
    }

}
