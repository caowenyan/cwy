package cn.cindy.thread.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 重入:允许 reader 和 writer 按照 ReentrantLock 的样式重新获取读取锁或写入锁。
 * 		在写入线程保持的所有写入锁都已经释放后，才允许重入 reader 使用它们。
 */
public class CacheTest {

	Map<String,Object> cache = new HashMap<String,Object>();
	
	public static void main(String[] args) {
		final CacheTest cacheTest = new CacheTest();
		for(int i=0;i<10;i++){
			new Thread(){
				public void run() {
					System.out.println("获得数据( k1 , "+cacheTest.get("k1")+")");
					System.out.println("获得数据( k2 , "+cacheTest.get("k2")+")");
					System.out.println("获得数据( k3 , "+cacheTest.get("k3")+")");
					System.out.println("获得数据( k4 , "+cacheTest.get("k4")+")");
				};
			}.start();
		}
	}
	
	ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public Object get(String key){
		lock.readLock().lock();
		Object value = null;
		try {
			value = cache.get(key);
			if(value==null){
				lock.readLock().unlock();
				lock.writeLock().lock();
				if(cache.get(key)==null){//不能判断value,因为value肯定是空的,所以需要从缓存中读取
					Thread.sleep(1);
					value = new Random().nextInt(1000);//从数据库或是其他地方获取数据
					System.out.println("从数据库中取出( "+key+" , "+value+" )");
					cache.put(key, value);
				}else{//缓存中有数据,所以直接获取就可
					value = cache.get(key);
				}
				//锁降级：从写入锁降级为读取锁，其实现方式是：先获取写入锁，然后获取读取锁，最后释放写入锁。
				//但是，从读取锁升级到写入锁是不可能的。
				lock.readLock().lock();
				lock.writeLock().unlock();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.readLock().unlock();
		}
		return value;
	}
}
