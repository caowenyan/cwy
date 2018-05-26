package cn.cindy.proxy.cglib;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.CallbackFilter;
public class MyProxyFilter implements CallbackFilter {
 @Override
 public int accept(Method method) {
        if(!"query".equalsIgnoreCase(method.getName()))
            return 0;   
        return 1;   
    }
}