package test;

/**
 * Created by feichen211389 on 2017/7/6.
 */
public class TestJavaObject {
    String name = "";
    TestJavaObject(String s){
        this.name = s;
    }
    public void func1(TestJavaObject t){
        t.name = "func1";
    }
    public void func2(TestJavaObject t){
        t = new TestJavaObject("func2");
    }

    public static void main(String[] args){
        TestJavaObject o = new TestJavaObject("start");

        System.out.println(o.name);
        o.func1(o);
        System.out.println(o.name);
        o.func2(o);
        System.out.println(o.name);
    }
}
