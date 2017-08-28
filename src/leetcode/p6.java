package leetcode;

/**
 * Created by feichen211389 on 2017/8/28.
 */
public class p6 {

    public static void main(String[] args){
        String[] strs = {"PAYPALISHIRING"};
        int[] lines = {3};

        p6 ans = new p6();
        for(int i = 0; i< strs.length; i++){
            System.out.println(ans.convert(strs[i], lines[i]));
        }
    }

    public String convert(String s, int numRows) {
        StringBuilder[] stringBuilder = new StringBuilder[numRows];
        for(int i = 0; i < numRows; i++){
            stringBuilder[i] = new StringBuilder();
        }

        int index = 0;
        int length = s.length();

        char[] chars = s.toCharArray();

        while(index < length){
            for(int i = 0; i < numRows && index < length; i++){
                stringBuilder[i].append(chars[index++]);
            }
            for(int j = numRows - 2; j > 0 && index < length; j--){
                stringBuilder[j].append(chars[index++]);
            }
        }

        for(int i = 1; i < numRows; i++){
            stringBuilder[0].append(stringBuilder[i]);
        }

        return stringBuilder[0].toString();
    }
}