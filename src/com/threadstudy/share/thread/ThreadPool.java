package com.threadstudy.share.thread;

import com.threadstudy.share.base.BaseClass;

import java.util.concurrent.*;

/**
 * @author Tavis
 * 线程池的使用
 */
public class ThreadPool extends BaseClass {
    public static void main(String[] args) {
        ThreadPool main = new ThreadPool();
//        main.doCachedThreadPool();
//        main.doFixedThreadPool();
//        main.doScheduledThreadPool();
        main.doSingleThreadExecutor();

        System.exit(0);
    }

    //如果每一个线程都是相同的逻辑，可以作为私有变量
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            print("This is Thread runnable!! Thread id is : " + Thread.currentThread().getId());
        }
    };

    class MyCaller implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("calling");
            return "return_from_call";
        }

    }

    private MyCaller myCaller = new MyCaller();

    //变长线程数
    public void doCachedThreadPool() {
        int size = 10;
        //execute 方法执行 runnable 任务，submit 方法执行 callable 任务，callable 任务有返回值，而 runnable 任务是 void 的，无返回值。
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < size; i++) {
//            cachedThreadPool.execute(runnable);
            try {
                String result = cachedThreadPool.submit(myCaller).get();
                print(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        //命令task执行完关闭并等待关闭
        cachedThreadPool.shutdown();
        //等于中断
//        cachedThreadPool.shutdownNow();
        //判断是否中断
//        cachedThreadPool.isShutdown();
        try {
            //判断是否全部退出
            while (!cachedThreadPool.awaitTermination(1, TimeUnit.SECONDS)) {
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        print("cachedThreadPool tasks all finish");
    }

    //定长线程池
    public void doFixedThreadPool() {
        int size = 10;
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(size);
        for (int i = 0; i < size; i++) {
            fixedThreadPool.execute(runnable);
        }
        //命令task执行完关闭并等待关闭
        fixedThreadPool.shutdown();
        try {
            //判断是否全部退出
            while (!fixedThreadPool.awaitTermination(1, TimeUnit.SECONDS)) {
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        print("fixedThreadPool tasks all finish");
    }

    //计划任务线程池
    public void doScheduledThreadPool() {
        int size = 1;
        ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(size);
        for (int i = 0; i < size; i++) {
//            scheduledThreadPool.execute(runnable);
            ((ScheduledExecutorService) scheduledThreadPool).scheduleAtFixedRate(runnable,1,3,TimeUnit.SECONDS);
            //delay 1 seconds, and excute every 3 seconds
        }
        //命令task执行完关闭并等待关闭
//        scheduledThreadPool.shutdown();
        try {
            //判断是否全部退出
            while (!scheduledThreadPool.awaitTermination(1, TimeUnit.SECONDS)) {
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        print("scheduledThreadPool tasks all finish");
    }

    //单一线程池
    public void doSingleThreadExecutor() {
        int size = 10;
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < size; i++) {
            singleThreadExecutor.execute(runnable);
        }
        //命令task执行完关闭并等待关闭
        singleThreadExecutor.shutdown();
        try {
            //判断是否全部退出
            while (!singleThreadExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        print("singleThreadExecutor tasks all finish");
    }


}
