package hdu.p2044;

import java.util.Scanner;

/**
 * Created by JackChen on 2017/6/29.
 */
public class Main {
    public static long dynamicProgramBee(long x){

        long a = 0;
        long b = 0;
        long result = 0;

        for(long i = 0; i <= x; i++){
            if(i == 0 || i == 1){
                a = 1;
                b = 1;
                result = 1;
            }else{
                a = b;
                b = result;
                result = a + b;
            }
        }
        return result;
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String first = scanner.nextLine();
        long num = Long.valueOf(first.trim());
        for(long i = 0; i < num; i++){
            String line = scanner.nextLine();
            String[] c = line.split(" ");
            long a = Long.valueOf(c[0]);
            long b = Long.valueOf(c[1]);
            long x = b - a;
            System.out.println(dynamicProgramBee(x));
        }
    }
}
