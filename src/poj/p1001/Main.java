package poj.p1001;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by JackChen on 2017/4/11.
 */
public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            BigDecimal r = scanner.nextBigDecimal();
            int n = scanner.nextInt();
//            r = r.pow(n);
            int r_scale = r.scale();
//            String r_s = r.toString();
            String r_s = r.toPlainString();
            System.out.println(r_s);



            String str = r.stripTrailingZeros().toPlainString();
            if (str.startsWith("0.")){
                str = str.substring(1);
            }
            System.out.println(str);
        }
    }
}
