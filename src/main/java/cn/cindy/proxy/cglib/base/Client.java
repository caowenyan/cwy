package cn.cindy.proxy.cglib.base;

import cn.cindy.proxy.base.BookService;

/**
 * Created by Caowenyan on 2016/3/7.
 */
public class Client {
    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        BookService service =new BookService();
        BookService service1 = (BookService)cglibProxy.getProxyInstance(service);
        service1.create();
        BookService service2 = (BookService)cglibProxy.getProxyInstance(service);
        service2.query();
    }
}
