package cn.cindy.proxy.base;

public class BookService implements AbstractBookService {
    //加final只对cglib有用，因为jdk动态代理针对接口编程，是不可能有final方法的
    public final void create(){
        System.out.println("create() is running !");
    }
    public void query(){
        System.out.println("query() is running !");
    }
    public void query(int id){
        System.out.println("query(id) is running !");
    }
    public void update(){
        System.out.println("update() is running !");
    }
    public void delete(){
        System.out.println("delete() is running !");
    }
}