package cn.cindy.thread.timer;

import cn.cindy.util.SleepUtil;
import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 曹文艳   (caowy@cloud-young.com)
 * @version V1.0
 * @description
 * @date 2018年07月07日 22:28
 */
public class TraditionalTimerTest {
    @Test
    public void scheduleOne() {
        new Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                System.out.println("boiling");
            }
        }, 1000);
        while(true){
            SleepUtil.sleep(1000);
            System.out.println("====");
        }
    }
    @Test
    public void scheduleEvery3Second() {
        new Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                System.out.println("boiling");
            }
        }, 1000,3000);
        while(true){
            SleepUtil.sleep(1000);
            System.out.println("====");
        }
    }

    @Test
    public void schedule2_3Second() {
        new Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                System.out.println("boiling");
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("boiling");
                    }
                },3000);
            }
        }, 2000);
        while(true){
            SleepUtil.sleep(1000);
            System.out.println("====");
        }
    }

    /**
     * 这个this已经被用过，所以会报错
     */
    @Test
    public void schedule_every_2_3Second_error() {
        new Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                System.out.println("boiling");
                new Timer().schedule(this,3000);
            }
        }, 2000);
        while(true){
            SleepUtil.sleep(1000);
            System.out.println("====");
        }
    }

    @Test
    public void schedule_every_2_3Second() {
        new Timer().schedule(new MyTimerTask(), 3000);
        while(true){
            SleepUtil.sleep(1000);
            System.out.println("====");
        }
    }

    @Test
    public void schedule_diy() {
        new Timer().schedule(new MyTimerTask2(), 2000);
        while(true){
            SleepUtil.sleep(1000);
            System.out.println("====");
        }
    }

    static class MyTimerTask extends TimerTask{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"boiling");
            new Timer().schedule(new MyTimerTask(), 5000);
        }
    }

    static int count = 0;
    static class MyTimerTask2 extends TimerTask{
        @Override
        public void run() {
            count++;
            System.out.println(Thread.currentThread().getName()+"boiling");
            new Timer().schedule(new MyTimerTask2(), 2000+(count%2)*2000);
        }
    }
}
