package cn.cindy.completable.future;

import cn.cindy.util.SleepUtil;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @author 曹文艳   (caowy@cloud-young.com)
 * @version V1.0
 * @description
 * <p>转化，功能累加thenApply(Function)，无阻塞</p>
 * <p>扁平化转化，串联，thenCompose(Function)，无阻塞</p>
 * <p>并联，thenCombine(CompletionStage, BiFunction)</p>
 * <p>合并消费,和thenCombine类似，除了返回值，thenAcceptBoth(CompletionStage, BiConsumer)</p>
 * <p>合并消费,和thenAcceptBoth类似，只是不用两个future的返回值，runAfterBoth(CompletionStage, Runnable)</p>
 * <p>那个先执行完就返回那个，另一个执行非阻塞，applyToEither(CompletionStage, Function)</p>
 * <p>acceptEither、runAfterEither和applyToEither基本相同，处理处理参数，返回值</p>
 * <p>执行完处理，还返回原始的future，whenComplete(BiConsumer)</p>
 * <p>终端消费，thenRun(Runnable)，thenAccept(Consumer)，无阻塞</p>
 * <p>allOf,anyOf感觉是单线程的例子，join，所以这个很不舒服</p>
 * <p>异常处理：
     * <li>外抛型，由CompletableFuture.completeExceptionally(Throwable e)实现，特点是传入一个异常实例，然后在调用获取CompletableFuture实例结果的方法例如get，join等的时候，异常会直接抛出。</li>
     * <li>补偿型，由CompletableFuture.exceptionally(Function<Throwable, ? extends T> fn)实现，通过Function函数把Throwable类型转化为当前CompletableFuture的结果类型。</li>
     * <li>全方位型，由CompletableFuture.handle(BiFunction<? super T, Throwable, ? extends U> fn)实现，通过BiFunction函数实现同时获取结果、异常，并且实现转化的功能。</li></p>
 * @date 2018年05月25日 22:02
 */
public class FutureTest {
    @Test
    public void test_simple_normal() throws Exception{
        final CompletableFuture<String> future = new CompletableFuture<>();
        long start = System.currentTimeMillis();
        SleepUtil.sleep(1000);
        future.complete("耗时" + (System.currentTimeMillis()-start) + "ms");
        System.out.println(future.get());
    }

    /**
     * complete只能调用一次，后续调用将被忽略，以第一次结果为准，
     * <p>类似于completeExceptionally</p>
     * <p>这个设置一般用于future没有结果，主动设置值，为了调用者的使用</p>
     * @throws Exception
     */
    @Test
    public void test_complete_invoke_2() throws Exception{
        final CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("已经完成");
        future.complete("无效修改");
        System.out.println(future.get());
    }

    /**
     * obtrudeValue调用将要覆盖结果
     * <p>类似于obtrudeException</p>
     * @throws Exception
     */
    @Test
    public void test_obtrudeValue() throws Exception{
        final CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("已经完成");
        future.obtrudeValue("覆盖结果");
        future.complete("无效修改");
        System.out.println(future.get());
    }

    @Test
    public void test_completeExceptionally() throws Exception{
        final CompletableFuture<String> future = new CompletableFuture<>();
        future.completeExceptionally(new RuntimeException("主动抛出异常--future.completeExceptionally"));
        System.out.println(future.get());
    }

    /**
     * get若是没有结果则返回参数valueIfAbsent
     * @throws Exception
     */
    @Test
    public void test_getNow() throws Exception{
        final CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            SleepUtil.sleep(1000);//注释了就返回结果，否则返回empty
            return "结果";
        });
        SleepUtil.sleep(100);
        System.out.println(future.getNow("empty"));
    }

    /**
     * 直接返回，测试和适配器（？？？）
     * @throws Exception
     */
    @Test
    public void test_completedFuture() throws Exception{
        final CompletableFuture<String> future = CompletableFuture.completedFuture("耍我的吧？");
        System.out.println(future.getNow("empty"));
    }

    /**
     * supplyAsync和runAsync类似，一个返回结果，一个不返回结果
     * @throws Exception
     */
    @Test
    public void test_supplyAsync() throws Exception{
        final CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            SleepUtil.sleep(1000);
            System.out.println("我执行完了");
            return "我睡了1s";
        });
        System.out.println("哈喽哈");
//        SleepUtil.sleep(1000);
//        System.out.println(future.get());
    }

    /**
     * test_runAsync_thenApply与test_supplyAsync_thenApply，只是第一个future有无返回值，没有可以当做参数传入，就是null
     * <p>thenApply：转化，将future.get的值转化为他的入参，处理后得到新的future</p>
     * <p>thenApply()起到转换结果的作用，总结来说就是叠加多个CompletableFuture的功能，把多个CompletableFuture组合在一起，跨线程池进行异步调用，调用的过程就是结果转换的过程</p>
     * @throws Exception
     */
    @Test
    public void test_runAsync_thenApply() throws Exception{
        long start = System.currentTimeMillis();
        CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
            SleepUtil.sleep(1000);
        });
        CompletableFuture<String>future1 = future.thenApply((s)->{
            SleepUtil.sleep(500);
            return "test_runAsync_thenApply: "+s;
        });
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"ms");
        String result = future1.get();
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"ms, result: "+result);
    }

    /**
     * 测试顺序，thenApply在第一个future执行完后执行，换成thenApplyAsync暂时一样，以待测试
     * @throws Exception
     */
    @Test
    public void test_supplyAsync_thenApply() throws Exception{
        long start = System.currentTimeMillis();
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            System.out.println("第一个开始");
            SleepUtil.sleep(1000);
            System.out.println("第一个结束");
            return "abcdEFG";
        });
//        SleepUtil.sleep(100);
//        future.complete("hala");//若是加上表示第一个已经执行完，第二个直接执行不等待
        future = future.thenApply((s)->{
            System.out.println("第二个开始");
            SleepUtil.sleep(500);
            System.out.println("第二个结束");
            return "test_runAsync_thenApply: "+s.toUpperCase();
        });
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"ms");
        String result = future.get();
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"ms, result: "+result);
    }

    @Test
    public void test_supplyAsync_thenAccept() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("第一个开始");
            SleepUtil.sleep(1000);
            System.out.println("第一个结束");
            return "abcdEFG";
        }).thenAccept(System.out::println);
        System.out.println("开始了");
        System.out.println("结束了："+future.get());
    }

    /**
     * 外抛异常
     * @throws Exception
     */
    @Test
    public void test_completeExceptionally_1()throws Exception{
        CompletableFuture<String> future = new CompletableFuture<>();
        try {
            System.out.println("我知道你已经完事了");
            throw new RuntimeException("test exception");
        }catch (Exception e){
            System.out.println("抛了个异常");
            future.completeExceptionally(e);
            future.complete("test success");
        }
        SleepUtil.sleep(1000);
        System.out.println("完事儿");
        System.out.println(future.get());
    }

    /**
     * 补偿性异常
     * @throws Exception
     */
    @Test
    public void test_exceptionally()throws Exception{
        long start = System.currentTimeMillis();
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return "hello world";
        }).join();
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"ms, result: "+result);
    }
    /**
     * 全方位异常
     * @throws Exception
     */
    @Test
    public void test_handle()throws Exception{
        long start = System.currentTimeMillis();
        //注意这里OK为String类型
        CompletableFuture<Integer> safe = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "32";
        }).handle((ok, ex) -> {
            if (ok != null) {
                return Integer.parseInt(ok);
            } else {
                System.out.println();
                return -1;
            }
        });
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"ms, result: "+safe.join());
    }

    private CompletableFuture<Integer> calculateRelevance(String doc){
        return CompletableFuture.supplyAsync(()->{
            SleepUtil.sleep(1000);
            return doc.indexOf("c");
        });
    }
    /**
     * 主要对比thenApply和太狠Compose，两者是map和floatMap的差别，扁平化处理
     * thenCompose()方法允许你对两个异步操作进行流水线，第一个操作完成时，将其结果作为参数传递给第二个操作
     * @throws Exception
     */
    @Test
    public void test_thenCompose() throws Exception{
        long start = System.currentTimeMillis();
        CompletableFuture<String> docFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("start");
            SleepUtil.sleep(1000);
            System.out.println("end");
            return "abcdEFG";
        });
        CompletableFuture<CompletableFuture<Integer>> f =  docFuture.thenApply(this::calculateRelevance);
        CompletableFuture<Integer> relevanceFuture = docFuture.thenCompose(this::calculateRelevance);
        //第一个打印的时间很好玩，因为是print阻塞了，所以打印的是阻塞前的时间
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"ms, result: "+f.get().get());
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"ms, result: "+relevanceFuture.get());
    }

    /**
     * thenCombine吧两个结果组装成一个新的结果，A、B并联，C把A、B的结果组合起来
     * @throws Exception
     */
    @Test
    public void test_thenCombine() throws Exception {
        long start = System.currentTimeMillis();
        CompletableFuture<String> firstFuture = CompletableFuture.completedFuture("不想写");
        CompletableFuture<Integer> secondFuture = CompletableFuture.completedFuture(100);
        CompletableFuture<String> routeFuture = firstFuture.thenCombine(secondFuture, (first, second) -> {
            SleepUtil.sleep(1000 );
            return first + second;
        });
        String result = routeFuture.get();
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"ms, result: "+result);
    }

    @Test
    public void test_thenAcceptBoth() throws Exception{
        long start = System.currentTimeMillis();
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            SleepUtil.sleep(2000);
            return "hello";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            SleepUtil.sleep(3000);
            return "world";
        }), (s1, s2) -> System.out.println(s1 + " " + s2));
        future.get();
        System.out.println("耗时："+(System.currentTimeMillis()-start)+"ms");
    }

    /**
     * 两个future互不依赖，合并操作也不需要各自的返回值
     * @throws Exception
     */
    @Test
    public void test_runAfterBoth() throws Exception{
        long start = System.currentTimeMillis();
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            SleepUtil.sleep(2000);
            return "hello";
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> {
            SleepUtil.sleep(3000);
            return "world";
        }), () -> System.out.println("完事了"));
        System.out.println("start耗时："+(System.currentTimeMillis()-start)+"ms");
        future.get();
        System.out.println("end耗时："+(System.currentTimeMillis()-start)+"ms");
    }

    /**
     * applyToEither，哪一个时间段就返回哪一个
     * @throws Exception
     */
    @Test
    public void test_applyToEither() throws Exception{
        long start = System.currentTimeMillis();
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            SleepUtil.sleep(2000);
            System.out.println("s1");
            return "s1";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            SleepUtil.sleep(3000);
            System.out.println("s2");
            return "s2";
        }), s->s);
        System.out.println("start耗时："+(System.currentTimeMillis()-start)+"ms");
        String result = future.get();
        System.out.println("end耗时："+(System.currentTimeMillis()-start)+"ms, result: "+result);
        SleepUtil.sleep(1000);
    }

    @Test
    public void test_acceptEither() throws Exception{
        long start = System.currentTimeMillis();
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            SleepUtil.sleep(2000);
            System.out.println("s1");
            return "s1";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            SleepUtil.sleep(3000);
            System.out.println("s2");
            return "s2";
        }), System.out::println);
        System.out.println("start耗时："+(System.currentTimeMillis()-start)+"ms");
        future.get();
        System.out.println("end耗时："+(System.currentTimeMillis()-start)+"ms");
        SleepUtil.sleep(1000);
    }

    /**
     * whenComplete:执行完后还会返回原始的Future对象
     */
    @Test
    public void test_whenComplete(){
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).whenComplete((s, t) -> {
            System.out.println(s);
            System.out.println(t.getMessage());
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return "hello world";
        }).join();
        System.out.println(result);
    }

    @Test
    public void test_allOf() throws Exception {
        long start = System.currentTimeMillis();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            SleepUtil.sleep(1000);
            System.out.println("s1");
        });
        CompletableFuture<String> future1 = future.thenApply((s) -> {
            SleepUtil.sleep(2000);
            System.out.println("s2");
            return "test_runAsync_thenApply: " + s;
        });
        System.out.println("start耗时："+(System.currentTimeMillis()-start)+"ms");
//        CompletableFuture.allOf(future,future1).join();
        CompletableFuture.anyOf(future,future1).join();
        System.out.println("end耗时："+(System.currentTimeMillis()-start)+"ms");
        SleepUtil.sleep(2000);
        System.out.println("end2耗时："+(System.currentTimeMillis()-start)+"ms");
    }
}

