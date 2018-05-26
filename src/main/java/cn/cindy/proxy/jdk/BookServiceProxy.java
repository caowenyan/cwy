package cn.cindy.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class BookServiceProxy implements InvocationHandler{

 private Object target;

 public BookServiceProxy(Object target){
  this.target = target;
 }

 @Override
 public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
  Object result=null;
  System.out.println("事物开始");
  //执行方法
  result=method.invoke(target, args);
  System.out.println("事物结束");
  return result;
 }
}