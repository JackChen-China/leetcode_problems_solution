package leetcode;

/**
 * Created by feichen211389 on 2017/8/28.
 */
public class p9 {

    public boolean isPalindrome(int x) {
        if(x < 0 || x%10 == 0)
            return false;
        int reverse = 0;
        while(x > reverse){
            reverse = reverse * 10 + x%10;
            x/=10;
        }
        return x == reverse || x==reverse/10;
    }
}
