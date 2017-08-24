package test;

/**
 * Created by feichen211389 on 2017/7/19.
 */
public class ExtendTest {
    public static void main(String[] args){
        A a = new A();
        System.out.println("a:" + a.x);
        B b1 = new B();
        B b2 = new B();
        System.out.println("b1:" + b1.x);
        System.out.println("b2:" + b2.x);
        System.out.println("a:" + a.x);
        b2.set();
        System.out.println("b1:" + b1.x);
        System.out.println("b2:" + b2.x);
        System.out.println("a:" + a.x);
    }
}
