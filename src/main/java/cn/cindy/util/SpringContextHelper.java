package cn.cindy.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
*通过将bean配置到spring的容器中，可以获取bean
*/
public class SpringContextHelper implements ApplicationContextAware {
	
	private static ApplicationContext context = null;
	
	public static Object getBean(String name) {
		return context == null ? null:context.getBean(name);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
		
	}
}
