package cn.cindy.thread.interview;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 　Copy-On-Write简称COW,延时懒惰策略
 * CopyOnWriteArrayList适合使用在读操作远远大于写操作的场景里，比如缓存
 *
 * CopyOnWrite容器即写时复制的容器。通俗的理解是当我们往一个容器添加元素的时候，
 * 不直接往当前容器添加，而是先将当前容器进行Copy，复制出一个新的容器，
 * 然后新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器。
 * 这样做的好处是我们可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。
 * 所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。
 *
 * 那这样是不是就会出现并发时开辟了几个内存存储对象,导致每次指向新的内存地址,没有更新???那不就错了?
 *
 * 以上问题不会出现,因为在添加数据时执行了lock.lock();加锁,所以不会出现一次复制多份副本.
 */
//不能改动此Test类
public class Test3 extends Thread{

	private TestDo testDo;
	private String key;
	private String value;
	
	public Test3(String key,String key2,String value){
		this.testDo = TestDo.getInstance();
		/*常量"1"和"1"是同一个对象，下面这行代码就是要用"1"+""的方式产生新的对象，
		以实现内容没有改变，仍然相等（都还为"1"），但对象却不再是同一个的效果*/
		this.key = key+key2;
		this.value = value;
	}


	public static void main(String[] args) throws InterruptedException{
		Test3 a = new Test3("1","","1");
		Test3 b = new Test3("1","","2");
		Test3 c = new Test3("3","","3");
		Test3 d = new Test3("4","","4");
		System.out.println("begin:"+(System.currentTimeMillis()/1000));
		a.start();
		b.start();
		c.start();
		d.start();

	}
	
	public void run(){
		testDo.doSome(key, value);
	}

	static class TestDo {

		private TestDo() {}
		private static TestDo _instance = new TestDo();
		public static TestDo getInstance() {
			return _instance;
		}

		//private ArrayList keys = new ArrayList();
		private CopyOnWriteArrayList keys = new CopyOnWriteArrayList();
		public void doSome(Object key, String value) {
			Object o = key;
			if(!keys.contains(o)){
				keys.add(o);
			}else{

				for(Iterator iter=keys.iterator();iter.hasNext();){
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Object oo = iter.next();
					if(oo.equals(o)){
						o = oo;
						break;
					}
				}
			}
			synchronized(o)
			// 以大括号内的是需要局部同步的代码，不能改动!
			{
				try {
					Thread.sleep(1000);
					System.out.println(key+":"+value + ":"
							+ (System.currentTimeMillis() / 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
