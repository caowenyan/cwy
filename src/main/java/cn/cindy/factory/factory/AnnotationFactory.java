package cn.cindy.factory.factory;

import cn.cindy.factory.service.AbstractMessage;
import cn.cindy.factory.annotation.Message;
import cn.cindy.factory.bean.Bean;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 曹文艳   (caowy@cloud-young.com)
 * @version V1.0
 * @description
 * @date 2018年03月30日 下午6:33
 */
public class AnnotationFactory {
    private static final String CAL_PRICE_PACKAGE = "cn.cindy.factory";//这里是一个常量，表示我们扫描策略的包

    private ClassLoader classLoader = getClass().getClassLoader();

    /**
     * 这个可以通过注解实现，map的可以通过init方法实现，如PostConstruct
     */
    private List<Class<? extends AbstractMessage>> messageList;//策略列表
    private Map<Class<? extends AbstractMessage>,Object> map;//策略列表
    private Map<String,Object> mapList;//策略列表
    //根据玩家的总金额产生相应的策略
    public AbstractMessage getMessage(Object o) {
        Bean bean = (Bean)o;
        //在策略列表查找策略
        for (Class<? extends AbstractMessage> clazz : messageList) {
//            Message validRegion = handleAnnotation(clazz);//获取该策略的注解
            Message validRegion = clazz.getAnnotation(Message.class);
            //判断金额是否在注解的区间
//            if ((bean.getMsgType()+bean.getEvent()).equals(validRegion.MsgType()+validRegion.Event())) {
            if (bean.getMsgType().equals(validRegion.MsgType()) && bean.getEvent().equals(validRegion.Event())) {
                try {
                    //是的话我们返回一个当前策略的实例
                    return clazz.newInstance();
//                    return (AbstractMessage) map.get(clazz);
                } catch (Exception e) {
                    throw new RuntimeException("策略获得失败");
                }
            }
        }
        return (AbstractMessage) mapList.get(bean.getMsgType()+bean.getEvent());
    }

    //处理注解，我们传入一个策略类，返回它的注解
    private Message handleAnnotation(Class<? extends AbstractMessage> clazz) {
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        if (annotations == null || annotations.length == 0) {
            return null;
        }
        for (int i = 0; i < annotations.length; i++) {
            if (annotations[i] instanceof Message) {
                return (Message) annotations[i];
            }
        }
        return null;
    }

    //单例
    private AnnotationFactory() {
        init();
    }

    //在工厂初始化时要初始化策略列表
    private void init() {
        messageList = new ArrayList<Class<? extends AbstractMessage>>();
        map = new HashMap<Class<? extends AbstractMessage>, Object>();
        mapList = new HashMap<String, Object>();
        File[] resources = getResources();//获取到包下所有的class文件
        Class<AbstractMessage> calPriceClazz = null;
        try {
            calPriceClazz = (Class<AbstractMessage>) classLoader.loadClass(AbstractMessage.class.getName());//使用相同的加载器加载策略接口
        } catch (ClassNotFoundException e1) {
            throw new RuntimeException("未找到策略接口");
        }
        for (int i = 0; i < resources.length; i++) {
            try {
                //载入包下的类
                Class<?> clazz = classLoader.loadClass(CAL_PRICE_PACKAGE + "." + resources[i].getName().replace(".class", ""));
                //判断是否是CalPrice的实现类并且不是CalPrice它本身，满足的话加入到策略列表
                if (AbstractMessage.class.isAssignableFrom(clazz) && clazz != calPriceClazz) {
                    messageList.add((Class<? extends AbstractMessage>) clazz);
                    Object object = clazz.newInstance();
                    map.put((Class<? extends AbstractMessage>) clazz,object);
                    mapList.put(object.getClass().getAnnotation(Message.class).MsgType()+object.getClass().getAnnotation(Message.class).Event(),object);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //获取扫描的包下面所有的class文件
    private File[] getResources() {
        try {
            File file = new File(classLoader.getResource(CAL_PRICE_PACKAGE.replace(".", "/")).toURI());
            return file.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    if (pathname.getName().endsWith(".class")) {//我们只扫描class文件
                        return true;
                    }
                    return false;
                }
            });
        } catch (URISyntaxException e) {
            throw new RuntimeException("未找到策略资源");
        }
    }

    public static AnnotationFactory getInstance() {
        return AnnotationFactoryInstance.instance;
    }

    private static class AnnotationFactoryInstance {
        private static AnnotationFactory instance = new AnnotationFactory();
    }
}