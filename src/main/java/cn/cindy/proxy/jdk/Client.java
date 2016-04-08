package cn.cindy.proxy.jdk;

import cn.cindy.proxy.BookServiceBean;
import cn.cindy.proxy.cglib.*;
import cn.cindy.proxy.cglib.BookServiceFactory;

/**
 * Created by Caowenyan on 2016/3/7.
 */
public class Client {
    public static void main(String[] args) {
        BookServiceBean service = cn.cindy.proxy.cglib.BookServiceFactory.getProxyInstance(new MyCglibProxy("boss"));
        service.create();
        BookServiceBean service2 = cn.cindy.proxy.cglib.BookServiceFactory.getProxyInstance(new MyCglibProxy("john"));
        service2.create();

        BookServiceBean service3 = cn.cindy.proxy.cglib.BookServiceFactory.getProxyInstanceByFilter(new MyCglibProxy("jhon"));
        service3.create();
        BookServiceBean service4 = BookServiceFactory.getProxyInstanceByFilter(new MyCglibProxy("jhon"));
        service4.query();
    }
}
