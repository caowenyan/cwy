package cn.cindy.proxy.cglib.base;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor{
 private Logger log=Logger.getLogger(CglibProxy.class);
 private Object targectObject;

 public Object getProxyInstance(Object object) {
  this.targectObject = object;
  Enhancer enhancer = new Enhancer();
  enhancer.setSuperclass(object.getClass());
  enhancer.setCallback(this);
  return enhancer.create();
 }
 
 @Override
 public  Object intercept(Object object, Method method, Object[] args,
   MethodProxy methodProxy) throws Throwable {
  log.info("调用的方法是：" + method.getName());
 if(!"query".equals(method.getName())){
  log.info("需要执行安全操作");
 }
  //注意一个是代理类与代理方法，一个是目标类与目标方法
  Object result = methodProxy.invokeSuper(object, args);
//  Object result = method.invoke(targectObject, args);

  return result;
 }
}