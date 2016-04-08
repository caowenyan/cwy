package cn.cindy;

public class Employee {

    public String name=null;  

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
    public static void main(String[] args) {  
        Employee worker=new Employee("张三");  
        Employee manager=new Employee("李四");  
        swap(worker,manager);  
        System.out.println(worker.name+" "+manager.name); //打印结果仍然是： 张三 李四  
    }  
}  