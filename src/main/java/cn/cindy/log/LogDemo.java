package cn.cindy.log;

import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 记录此篇博客的原因:在生产中出现了处理失败,但是却找不到原因
 * logger.error(exception)和logger.error("",exception) 
 * 看很多人都是后者的写法，为什么就不能直接用logger.error(exception)呢？
 * 前者只打印一行报错信息，后者却可以打印出堆栈信息
 * @author Caowenyan
 */
public class LogDemo {
	private static Logger log = LoggerFactory.getLogger(LogDemo.class);
	public static void main(String[] args) {
		FileInputStream input = null;
		try{
			input = new FileInputStream("student.xls");
			System.out.println(input.read());
		}catch(Exception e){
			/**null
			 * public void error(String msg) {
				    logger.log(FQCN, Level.ERROR, msg, null);
				  }
			 */
			log.error("异常"+e);//调用的相当于是e的toString方法:Returns a short description of this throwable.
			System.out.println();
			/**参数t
			 * public void error(String msg, Throwable t) {
				    logger.log(FQCN, Level.ERROR, msg, t);
				  }
			 */
			log.error("异常",e);
		}finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
