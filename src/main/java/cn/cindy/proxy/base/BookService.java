package cn.cindy.proxy.base;

public class BookService implements AbstractBookService {
    public void create(){
        System.out.println("create() is running !");
    }
    public void query(){
        System.out.println("query() is running !");
    }
    public void update(){
        System.out.println("update() is running !");
    }
    public void delete(){
        System.out.println("delete() is running !");
    }
}