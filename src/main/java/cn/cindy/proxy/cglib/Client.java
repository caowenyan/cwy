package cn.cindy.proxy.cglib;

import cn.cindy.proxy.BookServiceBean;

/**
 * Created by Caowenyan on 2016/3/7.
 */
public class Client {
    public static void main(String[] args) {
        BookServiceBean service = BookServiceFactory.getProxyInstance(new MyCglibProxy("boss"));
        service.create();
        BookServiceBean service2 = BookServiceFactory.getProxyInstance(new MyCglibProxy("john"));
        service2.create();

        BookServiceBean service3 = BookServiceFactory.getProxyInstanceByFilter(new MyCglibProxy("jhon"));
        service3.create();
        BookServiceBean service4 = BookServiceFactory.getProxyInstanceByFilter(new MyCglibProxy("jhon"));
        service4.query();
    }
}
