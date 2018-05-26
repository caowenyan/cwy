package cn.cindy.factory;

import cn.cindy.factory.bean.Bean;
import cn.cindy.factory.factory.AnnotationFactory;
import cn.cindy.factory.factory.Factory;

import java.util.function.Function;

/**
 * @author 曹文艳   (caowy@cloud-young.com)
 * @version V1.0
 * @description
 * @date 2018年03月30日 下午7:02
 */
public class Main {
    public static void main(String[] args) {
        AnnotationFactory factory = AnnotationFactory.getInstance();
        Bean text = new Bean();
        text.setMsgType("text");
        Bean voice = new Bean();
        voice.setMsgType("voice");
        System.out.println("switch one");
        f1(text, Factory::getFactoryMessageOne);
        f1(voice,Factory::getFactoryMessageOne);
        System.out.println("switch two");
        f1(text,Factory::getFactoryMessageTwo);
        f1(voice,Factory::getFactoryMessageTwo);
        System.out.println("annotation");
        f1(text,factory::getMessage);
        f1(voice,factory::getMessage);
    }
    public static void f1(Bean bean,Function consumer){
        Long start = System.currentTimeMillis();
        for(int i=0;i<1000_0000;i++){
            consumer.apply(bean);
        }
        System.out.println(System.currentTimeMillis()-start);
    }
}
