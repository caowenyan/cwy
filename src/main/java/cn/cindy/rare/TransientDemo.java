package cn.cindy.rare;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cn.cindy.rare.base.User;

/**
 * 一个对象只要实现了Serilizable接口，这个对象就可以被序列化
 * 实现了Serilizable接口，这个类的所有属性和方法都会自动序列化。
 * 实现了Serilizable接口 , 将不需要序列化的属性前添加关键字transient，序列化对象的时候，这个属性就不会序列化到指定的目的地中。
 *
 * 一旦变量被transient修饰，变量将不再是对象持久化的一部分，该变量内容在序列化后无法获得访问。
 * transient关键字只能修饰变量，而不能修饰方法和类。注意，本地变量是不能被transient关键字修饰的。
 * 		变量如果是用户自定义类变量，则该类需要实现Serializable接口。
 * 被transient关键字修饰的变量不再能被序列化，一个静态变量不管是否被transient修饰，均不能被序列化。
 */
public class TransientDemo {
	static String path = "src/main/resources/userTransient";
	
	public static void init(User user){
        user.setUsername("caowenyan");
        user.setPasswd("123");
	}
	
	public static void printUser(User user){
		 System.out.println("username: " + user.getUsername());
	     System.err.println("password: " + user.getPasswd());
	}
	
	public static void main(String[] args) {
		User user = new User();
		init(user);
        System.out.println("存入的数据: ");
        printUser(user);
        
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(new FileOutputStream(path));
            os.writeObject(user); // 将User对象写进文件
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	if(os!=null){
        		try {
        			os.close();
        		}catch(Exception e){
        			e.printStackTrace();
        		}
        	}
        }
        try {
            // 在反序列化之前改变username的值
//            User.username = "caoxin";//由于全局变量修改导致数据被修改
            
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(path));
            user = (User) is.readObject(); // 从流中读取User的数据
            is.close();
            
            System.out.println("读取序列化后的内容: ");
            System.out.println("username: " + user.getUsername());
            System.err.println("password: " + user.getPasswd());
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
}
