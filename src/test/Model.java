package test;

import java.util.Arrays;

/**
 * Created by feichen211389 on 2017/6/8.
 */
public class Model {
    byte[] b;
    public Model(){
        b = new byte[2*1024*1024];
        Arrays.fill(b,(byte)0x1);
    }
}
