package cn.cindy.proxy.jdk;

import cn.cindy.proxy.base.AbstractBookService;

import cn.cindy.proxy.base.BookService;

import java.lang.reflect.Proxy;

/**
 * Created by Caowenyan on 2016/3/7.
 */
public class Client {
    public static void main(String[] args) {
        AbstractBookService bookService = new BookService();
        BookServiceProxy bookServiceProxy = new BookServiceProxy(bookService);
        AbstractBookService serviceBean = (AbstractBookService)//<b>一定是抽象类,否则会报转化异常</b>
                Proxy.newProxyInstance(
                        BookService.class.getClassLoader(),//或是AbstractBookService.class.getClassLoader()
                        BookService.class.getInterfaces(),//必须是AbstractBookService,也可以这么写new Class[]{AbstractBookService.class}
                        bookServiceProxy
                );
        serviceBean.query();
    }
}