package test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

/**
 * Created by JackChen on 2017/6/28.
 */
public abstract class BaseHystrixCommand<T> extends HystrixCommand<T> {

    public BaseHystrixCommand(){
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorld")));
    }

    public abstract T run();
}
