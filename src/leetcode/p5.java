package leetcode;

/**
 * Created by feichen211389 on 2017/8/25.
 */
public class p5 {
    public static void main(String[] args){
        String[] s = {"a","aba","bb","ssdfweewfefdsoo","babadada","babad"};

        p5 ans = new p5();
        for(String str : s) {
            System.out.println(ans.longestPalindrome(str));
        }
    }

    public String longestPalindrome(String s) {

        if(s.length() == 1){
            return s;
        }

        String s0 = changeStr(s);
//        System.out.println(s0);
        int s0_length = s0.length();
        int[] count = new int[s0_length];
        int max_i = 0;
        int max_length = 0;
        for(int i = 0; i < s0_length; i++){
            int current_length = 1;
            int mirror_i = 0;
            if((max_i + max_length > i + current_length) && (( mirror_i = 2 * max_i - i) > 0)){
                current_length = min(count[mirror_i], max_i + max_length - i);
            }

            int over_limit = min(i, s0_length - i - 1 );
            for(;current_length <= over_limit; current_length++){
                if(s0.charAt(i + current_length) == s0.charAt(( i - current_length))){
                    count[i] = current_length;
                    continue;
                }else{
                    break;
                }
            }

            if(i + current_length >= max_i + max_length) {
                max_i = i;
                max_length = current_length;
            }
        }


        max_i = 0;
        max_length = 0;
        for(int i = 0; i < s0_length; i++){
//            System.out.println(i + " " + count[i]);
            if(count[i] >= max_length){
                max_length = count[i];
                max_i = i;
            }
        }
//        System.out.println(max_i);
//        System.out.println(max_length);
        return revertChangeStr(s0.substring(max_i - max_length, max_i + max_length + 1));
    }

    public String changeStr(String s){
        char element = '#';
        StringBuilder sbuilder = new StringBuilder("#");
        byte[] s_bytes = s.getBytes();
        for(int i = 0; i < s_bytes.length; i++){
            sbuilder.append((char)s_bytes[i]);
            sbuilder.append(element);
        }

        return sbuilder.toString();
    }

    public String revertChangeStr(String s){
        StringBuilder sbuilder = new StringBuilder();
        byte[] s_bytes = s.getBytes();
        for(int i = 1; i < s_bytes.length; i+=2){
            sbuilder.append((char) s_bytes[i]);
        }

        return sbuilder.toString();
    }

    public int min(int a, int b){
        if ( a < b ){
            return a;
        }
        return b;
    }
}
