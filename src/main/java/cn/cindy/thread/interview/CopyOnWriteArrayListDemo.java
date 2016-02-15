package cn.cindy.thread.interview;
 
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
public class CopyOnWriteArrayListDemo {
    /**
     * 读线程
     */
    private static class ReadTask implements Runnable {
        List<String> list;
 
        public ReadTask(List<String> list) {
            this.list = list;
        }
 
        public void run() {
            for (String str : list) {
                System.out.print(str + "\t");
            }
            System.out.println();
        }
    }
    /**
     * 写线程
     */
    private static class WriteTask implements Runnable {
        List<String> list;
        int index;
 
        public WriteTask(List<String> list, int index) {
            this.list = list;
            this.index = index;
        }
 
        public void run() {
            System.err.println("remove and writer");
            list.remove(index);
            list.add(index, "write_" + index);
        }
    }
 
    public void run() {
        final int NUM = 10;
        //List<String> list = new ArrayList<String>();
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();
        for (int i = 0; i < NUM; i++) {
            list.add("main_" + i);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(NUM);
        for (int i = 0; i < NUM; i++) {
            executorService.execute(new ReadTask(list));
            executorService.execute(new WriteTask(list, i));
        }
        executorService.shutdown();
    }
 
    public static void main(String[] args) {
        new CopyOnWriteArrayListDemo().run();
    }
}