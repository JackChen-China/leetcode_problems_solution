package leetcode;

/**
 * Created by feichen211389 on 2017/8/28.
 */
public class p7 {

    public static void main(String[] args){

        int[] x = {123, Integer.MAX_VALUE, Integer.MIN_VALUE, -123};
        p7 ans = new p7();
        for(int i = 0; i < x.length; i++) {
            System.out.println(ans.reverse(x[i]));
        }
    }

    public int reverse(int x) {
        if(x == 0){
            return 0;
        }
        boolean flag = x > 0;
        int revert = 0;

        while(x != 0) {

            int suffix = x % 10;
            x /= 10;
            revert = revert * 10 + suffix;
            if(revert % 10 != suffix){
                return 0;
            }
        }
        return revert;
    }
}
