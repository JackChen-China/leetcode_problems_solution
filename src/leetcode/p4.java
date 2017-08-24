package leetcode;

/**
 * Created by feichen211389 on 2017/8/23.
 */
public class p4 {

    public static void main(String[] args){
        int[] nums1 = {3};
        int[] nums2 = {1,2};
        p4 ans = new p4();
        System.out.println(ans.findMedianSortedArrays(nums1, nums2));
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

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
        int i = 0;
        int j = 0;//index of nums2
        /* （m + n)/2 is another choice， be careful that if（m+n)/2 is choosed,
         * min_right will be the middle case when m+n is odd, since middle=(m + n)/2   length(left_part) <= length(right_part)
         */
        int middle = (m + n + 1) / 2;
        boolean odd = (m + n)%2 == 1 ? true : false;
        int imin = 0;
        int imax = m;
        int max_left = 0;
        int min_right = 0;
        while(true){
            //i + j = middle
            //i point to leftest element of nums1's right part
            i = (imin + imax)/2;
            //j point to leftest element of nums2's right part
            j = middle - i;

            if(i > 0 && nums1[i -1] > nums2[j]){
                imax = i - 1;
            }else if(i < m && nums1[i] < nums2[j - 1]){
                imin = i + 1;
            }else{


                if(i == 0){
                    max_left = nums2[j - 1];
                }else if(j == 0){
                    max_left = nums1[i - 1];
                }else {
                    max_left = max(nums1[i - 1], nums2[j - 1]);
                }

                if(odd){
                    return max_left;
                }
                if(i == m){
                    min_right = nums2[j];
                }else if(j == n){
                    min_right = nums1[i];
                }else {
                    min_right = min(nums1[i], nums2[j]);
                }
                return (double)(min_right + max_left)/2;
            }
        }
    }


    public int min(int a, int b){
        if(a < b){
            return a;
        }
        return b;
    }
    public int max(int a, int b){
        if(a < b){
            return b;
        }
        return a;
    }
}
