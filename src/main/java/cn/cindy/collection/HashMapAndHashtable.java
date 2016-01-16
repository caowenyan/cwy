package cn.cindy.collection;

import java.util.HashMap;

/**
 * 1、HashMap是非线程安全的，HashTable是线程安全的。
 * 2、HashMap的键和值都允许有null值存在，而HashTable则不行。
 * 3、因为线程安全的问题，HashMap效率比HashTable的要高。
 *
 * 在HashMap中，null可以作为键，这样的键只有一个；可以有一个或多个键所对应的值为null。
 * 当get()方法返回null值时，即可以表示 HashMap中没有该键，也可以表示该键所对应的值为null。
 * 因此，在HashMap中不能由get()方法来判断HashMap中是否存在某个键，而应该用containsKey()方法来判断。
 */
public class HashMapAndHashtable {

	public static void main(String[] args) {
		HashMap map = new HashMap();
		map.put(null, 1);
		System.out.println(map.get(null));
		System.out.println(map.containsKey(null));
		System.out.println(map.containsValue(1));
		
		map = new HashMap();
		map.put(1, null);
		System.out.println(map.get(null));
		System.out.println(map.containsKey(null));
		System.out.println(map.containsValue(null));
	}

}
