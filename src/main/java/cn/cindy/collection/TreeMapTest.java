package cn.cindy.collection;

import java.util.TreeMap;

public class TreeMapTest {
	public static void main(String[] args) {
		TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
		treeMap.put("3", 3);
		treeMap.put("2", 2);
		treeMap.put("4", 4);
		treeMap.put("1", 1);
		treeMap.put("5", 5);
		treeMap.put("0", 0);
		treeMap.put("8", 8);
		treeMap.put("10", 10);
		treeMap.remove("5");
		treeMap.put("5", 5);
		treeMap.put("10", 10);
	}
}
