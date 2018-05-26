package cn.cindy.completable.future;

import cn.cindy.util.SleepUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 曹文艳   (caowy@cloud-young.com)
 * @version V1.0
 * @description
 * <p><i>加同步和不加同步的差别，异步可以添加新的线程池</i></p>
 * @date 2018年05月26日 17:25
 */
public class ComposeTest {

    public static void main(String[] args) {
        System.out.println("main thread: " + Thread.currentThread());

//        new Thread(ComposeTest::test1) {{setName("my-new-thread");}}.start();
//        test2();
//        test3();
        new Thread(ComposeTest::test3) {{setName("my-new-thread");}}.start();
        SleepUtil.sleep(5000);
    }

    /**
     * 可以看出没有一个使用原先线程设置的名字，内部都是用线程数的，不会使用原先的，原先的只在最外层
     * 从测试中看出thenRun总是和主future的线程是一样的，不一样的话应该是线程窃取导致
     * thenRunAsync就应该是随机分配了，分到哪个线程那个线程执行
     * 奇怪的是为什么我的机器可以分到8个线程，当我执行2个thenRunAsync和3个thenRun时，thenRun没有被窃取
     */
    private static void test1() {

        CompletionStage<Void> futurePrice = CompletableFuture.runAsync(() -> {
                SleepUtil.sleep(1000);
                System.out.println("test1:1 - runAsync(runnable), job thread: " + Thread.currentThread());
            }
        );

        System.out.println("test1:flag1");

        futurePrice.thenRun(() -> {
            SleepUtil.sleep(1000);
            System.out.println("test1:2 - thenRun(runnable)), action thread: " + Thread.currentThread());
        });

        System.out.println("test1:flag2");

        futurePrice.thenRunAsync(() -> {
            System.out.println("test1:3 - thenRunAsync(runnable), action thread: " + Thread.currentThread());
        });

        futurePrice.thenRun(() -> {
            SleepUtil.sleep(500);
            System.out.println("test1:4 - thenRun(runnable)), action thread: " + Thread.currentThread());
        });

        futurePrice.thenRunAsync(() -> {
            SleepUtil.sleep(1000);
            System.out.println("test1:5 - thenRunAsync(runnable), action thread: " + Thread.currentThread());
        });

//        futurePrice.thenRunAsync(() -> {
//            System.out.println("test1:6 - thenRunAsync(runnable), action thread: " + Thread.currentThread());
//        });

        futurePrice.thenRun(() -> {
            SleepUtil.sleep(100);
            System.out.println("test1:7 - thenRun(runnable)), action thread: " + Thread.currentThread());
        });

//        futurePrice.thenRunAsync(() -> {
//            System.out.println("test1:8 - thenRunAsync(runnable), action thread: " + Thread.currentThread());
//        });
//
//        futurePrice.thenRunAsync(() -> {
//            System.out.println("test1:9 - thenRunAsync(runnable), action thread: " + Thread.currentThread());
//        });
    }


    /**
     * 从3的线程名字看出，thenRun和future用的一个线程池，thenRunAsync配啥用啥
     * 奇怪的是不加5，线程2的时间总不是线程1+线程2的，加了线程5，线程2的时间就对了？？？？？
     */
    private static void test2() {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionStage<Void> futurePrice = CompletableFuture.runAsync(() -> {
            SleepUtil.sleep(1000);
            System.out.println("test2:1 - runAsync(runnable, executor), job thread: " + Thread.currentThread()+", "+(System.currentTimeMillis()-start)+"ms");
        }, executorService);

        System.out.println("test2:flag1");

        futurePrice.thenRunAsync(() -> {
            SleepUtil.sleep(1000);
            System.out.println("test2:2 - thenRunAsync(runnable), action thread: " + Thread.currentThread()+", "+(System.currentTimeMillis()-start)+"ms");
        });

        System.out.println("test2:flag2");

        futurePrice.thenRun(() -> {
            SleepUtil.sleep(2000);
            System.out.println("test2:3 - thenRun(runnable), action thread: " + Thread.currentThread()+", "+(System.currentTimeMillis()-start)+"ms");
        });

        futurePrice.thenRunAsync(() -> {
            SleepUtil.sleep(3000);
            System.out.println("test2:4 - thenRunAsync(runnable, executor), action thread: " + Thread.currentThread()+", "+(System.currentTimeMillis()-start)+"ms");
        }, executorService);


        futurePrice.thenRunAsync(() -> {
            System.out.println("test2:5 - thenRunAsync(runnable), action thread: " + Thread.currentThread()+", "+(System.currentTimeMillis()-start)+"ms");
        });

//        executorService.shutdown();
    }

    /**
     * thenRun还能跑到调用者线程上去
     * 所以谁来执行回调方法并不确定，完全由 JVM 来定，或者是 Lambda 在其中作祟，把 Lambda 换成匿名类还是一样的效果。
     * thenRun() 大概是能根据调用者线程是否空闲来使用当前线程还是用执行任务的线程池。带 async 的方法会把任务再次提交到线程池中去。
     */
    private static void test3() {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionStage<Void> futurePrice = CompletableFuture.runAsync(() -> {
            System.out.println("test3:1 - runAsync(runnable, executor), job thread: " + Thread.currentThread()+", "+(System.currentTimeMillis()-start)+"ms");
        }, executorService);

        System.out.println("test3:flag1");

        futurePrice.thenRunAsync(() -> {
            System.out.println("test3:2 - thenRunAsync(runnable), action thread: " + Thread.currentThread()+", "+(System.currentTimeMillis()-start)+"ms");
        });

        System.out.println("test3:flag2");

        futurePrice.thenRun(() -> {
            System.out.println("test2:3 - thenRun(runnable), action thread: " + Thread.currentThread()+", "+(System.currentTimeMillis()-start)+"ms");
        });

        futurePrice.thenRunAsync(() -> {
            System.out.println("test3:4 - thenRunAsync(runnable, executor), action thread: " + Thread.currentThread()+", "+(System.currentTimeMillis()-start)+"ms");
        }, executorService);


        futurePrice.thenRunAsync(() -> {
            System.out.println("test3:5 - thenRunAsync(runnable), action thread: " + Thread.currentThread()+", "+(System.currentTimeMillis()-start)+"ms");
        });

//        executorService.shutdown();
    }

}