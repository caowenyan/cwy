package cn.cindy.quote;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

public class Employee {
    private String name=null;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Employee(){}
    public Employee(String n){  
        this.name=n;  
    }  
    //将两个Employee对象交换  
    public static void swap(Employee e1,Employee e2){  
        Employee temp=e1;  
        e1=e2;  
        e2=temp;  
        System.out.println(e1.name+" "+e2.name); //打印结果：李四 张三
    }  
    //主函数  
    public static void main(String[] args) throws Exception{
        System.out.println(StringUtils.leftPad("1", 5, '0'));
        Employee worker=new Employee("张三");  
        Employee manager=new Employee("李四");  
        swap(worker,manager);  
        System.out.println(worker.name+" "+manager.name); //打印结果仍然是： 张三 李四
    }
}  
