package cn.cindy.collection;

/**
 * 1）  Vector的方法都是同步的(Synchronized),是线程安全的(thread-safe)，
 * 			而ArrayList的方法不是，由于线程的同步必然要影响性能，因此,ArrayList的性能比Vector好。 
 * 2） 当Vector或ArrayList中的元素超过它的初始大小时,
 * 			Vector会将它的容量翻倍,而ArrayList只增加50%的大小，这样,ArrayList就有利于节约内存空间。
 */
public class ArrayListAndVector {

}
