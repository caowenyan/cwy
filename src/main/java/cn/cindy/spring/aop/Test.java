package cn.cindy.spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
        Person p=(Person) ctx.getBean("chinese");  
        System.out.println(p.sayHello("张三"));  
        p.eat("西瓜");  
    }
}