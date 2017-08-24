package forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by JackChen on 2017/8/17.
 */
public class CountTask extends RecursiveTask {

    private static final int THRESHOLD = 2;

    private int start;

    private int end;

    public CountTask(int start, int end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int re = 0;

        boolean canCompute = (end - start) <= THRESHOLD;
        if(canCompute){
            re = doWork();
            try {
                Thread.sleep(10000);
            }catch (Exception e){

            }
        }else{
            int middle = (start + end) / 2;
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle + 1, end);
            leftTask.fork();
            rightTask.fork();

            int leftResult = (Integer)leftTask.join();
            int rightResult = (Integer)rightTask.join();
            re = leftResult + rightResult;
        }
        return re;
    }

    private int doWork(){
        int sum = 0;
        for(int i = start; i <=end; i++){
            sum += i;
        }
        return sum;
    }

    public static void main(String[] args){
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(1,10);
        Future<Integer> result = forkJoinPool.submit(task);
        try{
            System.out.println(result.get());
        }catch (InterruptedException e){

        }catch (ExecutionException e){

        }
    }
}
