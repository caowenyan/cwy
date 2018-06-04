package cn.cindy.spring.aop;

import org.springframework.stereotype.Component;

@Component
public class Chinese implements Person {  
  
    @Override  
    public String sayHello(String name) {  
        System.out.println("sayHello方法被调用...");  
        return name+" Hello,Spring AOP";  
    }  
  
    @Override  
    public void eat(String food) {  
        System.out.println("我正在吃:"+food);  
    }  
  
      
}