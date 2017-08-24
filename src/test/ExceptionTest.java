package test;

/**
 * Created by feichen211389 on 2017/6/29.
 */
public class ExceptionTest {
    public static void main(String[] args){
        try{
            int y = 1/2;
        }catch(Exception e){
            System.out.println("catch");
            e.printStackTrace();
            System.out.println("catch end");
        }finally {
            System.out.println("finally");
            int x = 0/0;
            System.out.println("finally end");
        }
    }
}
