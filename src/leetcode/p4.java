package leetcode;

import java.util.Random;

/**
 * Created by feichen211389 on 2017/8/23.
 */
public class p4 {

    public static void main(String[] args){
        int[] nums1 = {};
        int[] nums2 = {};
        p4 ans = new p4();
        System.out.println(ans.findMedianSortedArrays(nums1, nums2));
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        double ans = 0.0;

        int m = nums1.length;
        int n = nums2.length;
        if(m > n){
            int t = m;
            m = n;
            n = t;

            int[] t_a = nums1;
            nums1 = nums2;
            nums2 = t_a;
        }
        Random random = new Random();
        int i = random.nextInt(m);// index of nums1
        int j = 0;//index of nums2

        boolean odd = (m + n)%2 == 1 ? true : false;
        int append = odd ? -1 : 0;
        while(true){
            j = (m + n + append) / 2 - i;
        }


    }
}
