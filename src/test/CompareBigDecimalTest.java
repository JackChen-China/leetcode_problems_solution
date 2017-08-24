package test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * Created by JackChen on 2017/7/14.
 */
public class CompareBigDecimalTest {
    public static void main(String[] args){
        BigDecimal b1 = new BigDecimal("1.0");
        BigDecimal b2 = new BigDecimal("1.00");
        HashSet set = new HashSet();
        set.add(b1);
        set.add(b2);
        System.out.println(b1);
        System.out.println(b2);
        System.out.println(set.size());

        TreeSet tset = new TreeSet();
        tset.add(b1);
        tset.add(b2);
        System.out.println(tset.size());
        System.out.println(tset.first());

    }
}
