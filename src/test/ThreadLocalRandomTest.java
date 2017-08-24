package test;

import org.apache.commons.lang.time.StopWatch;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by feichen211389 on 2017/6/7.
 */
public class ThreadLocalRandomTest {
    static final ThreadLocal<Long> longLocal = new ThreadLocal<Long>();
    static final ThreadLocal<String> stringLocal = new ThreadLocal<String>();

//    public static void main(String[] args) throws InterruptedException {
//        final test test = new test();
//        System.out.println(longLocal.get());
//        System.out.println(stringLocal.get());
//
//        Thread thread1 = new Thread(){
//            public void run() {
//                longLocal.set(1l);
//                System.out.println(longLocal.get());
//                try {
//                    Thread.sleep(10000l);
//                }catch (Exception e){
//
//                }
//            };
//        };
//        Thread thread2 = new Thread(){
//            public void run() {
//                longLocal.set(2l);
//                System.out.println(longLocal.get());
//                try {
//                    Thread.sleep(10000l);
//                }catch (Exception e){
//
//                }
//            };
//        };
//        thread2.start();
//        thread1.start();
//        thread1.join();
//        thread2.join();
//    }
//    public static void main(String[] args)throws Exception{
//
//        StopWatch watch = new StopWatch();
//        System.out.println("random start");
//        final Random random = new Random(100);
//        List<Thread> list = new ArrayList<Thread>();
//        for(int i = 0; i < 3; i++) {
//            Thread t = new Thread() {
//
//                public void run() {
//                    for (int i = 0; i < 10000000; i++)
//
//                    {
//                        random.nextDouble();
//                    }
//                }
//            };
//            list.add(t);
//        }
//        watch.start();
//        for(Thread t : list){
//            t.start();
//        }
//        for(Thread t : list){
//            t.join();
//        }
//        watch.stop();
//        System.out.println("end cost:"+watch.getTime());
//
//        System.out.println("ThreadLocalRandom start");
//        List<Thread> list1 = new ArrayList<Thread>();
//        watch.reset();
//        for(int i = 0; i < 3; i++) {
//            Thread t = new Thread() {
//
//                public void run() {
//                    for (int i = 0; i < 1000000000; i++)
//
//                    {
//                        ThreadLocalRandom.current().nextDouble();
//                    }
//                }
//            };
//            list1.add(t);
//        }
//        watch.start();
//        for(Thread t : list1){
//            t.start();
//        }
//        for(Thread t : list1){
//            t.join();
//        }
//        watch.stop();
//        System.out.println("end cost:"+watch.getTime());
//
//    }

//    public static void main(String[] args)throws Exception {
////        int THRESHOLD = 1;
////        for(int i = 0; i < THRESHOLD; i++){
////            System.out.println(ThreadLocalRandom.current().nextInt());
////        }
//        int t = 1;
//        Thread thread = Thread.currentThread();
//        if(t == 1){
//            ThreadLocal<Model> local = new ThreadLocal<Model>();
//            System.out.println(local);
//            Model m = new Model();
//            Model n = new Model();
//            WeakReference<Model> reference = new WeakReference<Model>(m);
//            local.set(n);
//            System.gc();
//            Thread.sleep(2000);
//    //        for(int i = 0; i < THRESHOLD; i++){
//    //            System.out.println(ThreadLocalRandom.current().nextInt());
//    //        }
//            System.out.println("1");
//            System.gc();
//            Thread.sleep(2000);
//    //        for(int i = 0; i < THRESHOLD; i++){
//    //            System.out.println(ThreadLocalRandom.current().nextInt());
//    //        }
//            System.out.println("2");
//            m = null;
//            n = null;
//            System.gc();
//            Thread.sleep(2000);
//    //        for(int i = 0; i < THRESHOLD; i++){
//    //            System.out.println(ThreadLocalRandom.current().nextInt());
//    //        }
//            System.out.println("3--1");
//            local = null;
//            System.gc();
//    //        System.out.println("3");
//    //        local.set(null);
//    //        System.gc();
//            Thread.sleep(2000);
//    //        for(int i = 0; i < THRESHOLD; i++){
//    //            System.out.println(ThreadLocalRandom.current().nextInt());
//    //        }
//            System.out.println("4");
//            Model x = new Model();
////            Model y = new Model();
//            System.gc();
//            Thread.sleep(1000);
//        }
//        ThreadLocal<Long> k = new ThreadLocal<Long>();
//        k.set(12312312l);
//        ThreadLocal<Long> l = new ThreadLocal<Long>();
//        l.set(5512312323412l);
//        ThreadLocal<Long> p = new ThreadLocal<Long>();
//        p.set(6612312312l);
//        System.gc();
//        Thread.sleep(1000);
//        System.out.println(123);
//    }




    private static final long COUNT = 10000000;
    private static final int THREADS = 2;

    private static final long sleep = 5000;

    public static void main(String[] args) throws Exception {
        System.out.println( "Shared Random" );
        testRandom(THREADS, COUNT);
        Thread.sleep(sleep);
        System.out.println("ThreadLocal<Random>");
        testTL_Random(THREADS, COUNT);
        Thread.sleep(sleep);
        System.out.println("ThreadLocalRandom");
        testTLRandom(THREADS, COUNT);
        Thread.sleep(sleep);
        System.out.println("Shared Random[] with no padding");
        testRandomArray(THREADS, COUNT, 1);
        Thread.sleep(sleep);
        System.out.println("Shared Random[] with padding");
        testRandomArray(THREADS, COUNT, 2);
    }

    //runner for all tests
    private static class RandomTask implements Runnable
    {
        private final Random rnd;
        protected final int id;
        private final long cnt;
        private final CountDownLatch latch;

        private RandomTask(Random rnd, int id, long cnt, CountDownLatch latch) {
            this.rnd = rnd;
            this.id = id;
            this.cnt = cnt;
            this.latch = latch;
        }

        protected Random getRandom()
        {
            return rnd;
        }

        @Override
        public void run() {
            try {
                final Random r = getRandom();
                latch.countDown();
                latch.await();
                final long start = System.currentTimeMillis();
                int sum = 0;
                for ( long j = 0; j < cnt; ++j )
                {
                    sum += r.nextInt();
                }
                final long time = System.currentTimeMillis() - start;
                System.out.println( "Thread #" + id + " Time = " + time / 1000.0 + " sec, sum = " + sum );
            } catch (InterruptedException e) {
            }
        }
    }

    private static void testRandom( final int threads, final long cnt )
    {
        final CountDownLatch latch = new CountDownLatch( threads );
        final Random r = new Random( 100 );
        for ( int i = 0; i < threads; ++i )
        {
            final Thread thread = new Thread( new RandomTask( r, i, cnt, latch ) );
            thread.start();
        }
    }

    private static void testRandomArray( final int threads, final long cnt, final int padding )
    {
        final CountDownLatch latch = new CountDownLatch( threads );
        final Random[] rnd = new Random[threads * padding];
        for ( int i = 0; i < threads * padding; ++i ) //allocate together
            rnd[ i ] = new Random( 100 );
        for ( int i = 0; i < threads; ++i )
        {
            final Thread thread = new Thread( new RandomTask( rnd[ i * padding ], i, cnt, latch ) );
            thread.start();
        }
    }

    private static void testTLRandom( final int threads, final long cnt )
    {
        final CountDownLatch latch = new CountDownLatch( threads );
        for ( int i = 0; i < threads; ++i )
        {
            final Thread thread = new Thread( new RandomTask( null, i, cnt, latch ) {
                @Override
                protected Random getRandom() {
                    return ThreadLocalRandom.current();
                }
            } );
            thread.start();
        }
    }

    private static void testTL_Random( final int threads, final long cnt )
    {
        final CountDownLatch latch = new CountDownLatch( threads );
        final ThreadLocal<Random> rnd = new ThreadLocal<Random>() {
            @Override
            protected Random initialValue() {
                return new Random( 100 );
            }
        };
        for ( int i = 0; i < threads; ++i )
        {
            final Thread thread = new Thread( new RandomTask( null, i, cnt, latch ) {
                @Override
                protected Random getRandom() {
                    return rnd.get();
                }
            } );
            thread.start();
        }
    }


}
