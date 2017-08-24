package leetcode;

import Helper.PrintUtil;

/**
 */
public class p3 {

    public static void main(String[] args){
        PrintUtil.printable = true;
        System.out.println(lengthOfLongestSubstring("qwnfenpglqdq"));
    }

    public static int lengthOfLongestSubstring(String s) {
        int[] map = new int[256];//char index of string
        for(int i = 0; i < 256; i++){
            map[i] = -1;
        }
        byte[] b = s.getBytes();

        int cMaxSubStrStart = 0;
        int cMaxSubStrEnd = 0;//nonIncluded
        int cSubStrStart = 0;
        for(int i = 0; i < b.length; i++){
            if(map[b[i]] == -1 || map[b[i]] < cSubStrStart){
                map[b[i]] = i;
            }else{
                if ((cMaxSubStrEnd - cMaxSubStrStart) < (i - cSubStrStart)) {
                    cMaxSubStrEnd = i;
                    cMaxSubStrStart = cSubStrStart;
                }
//                int b_cSubStrStart = cSubStrStart;
                cSubStrStart = map[b[i]] + 1;
//                for (int j = b_cSubStrStart; j < cSubStrStart; j++) {
//                    map[b[j]] = -1;
//                }
                map[b[i]] = i;
            }
        }
        PrintUtil.print(cMaxSubStrEnd);
        PrintUtil.print(cMaxSubStrStart);
        PrintUtil.print(cSubStrStart);
        if((cMaxSubStrEnd - cMaxSubStrStart) < (b.length - cSubStrStart)){
            cMaxSubStrEnd = b.length;
            cMaxSubStrStart = cSubStrStart;
        }
        return cMaxSubStrEnd - cMaxSubStrStart;
    }
}
