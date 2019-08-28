package com.threadstudy.share.thread;

import com.threadstudy.share.base.BaseClass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Tavis
 * 线程安全
 */
public class ThreadSafe extends BaseClass {
    //这样是不安全的
    private List<Integer> ticketList = new ArrayList<>(0);
    private int count=0;
    //这样才安全的
    //    private List<Integer> ticketList = Collections.synchronizedList(new ArrayList<>(0));
    //        private volatile int count=0;
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    public ThreadSafe() {
        ticketList.add(1000);
    }

    public static void main(String[] args) {
        ThreadSafe main = new ThreadSafe();
        main.text1();
    }

    //对队列操作
    private Runnable buyTicket = new Runnable() {
        @Override
        public void run() {
            //一起开始
            while (ticketList.get(0) > 0) {
                try {
                    waitThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                synchronized (ticketList) {
                    ticketList.add(0, ticketList.get(0) - 1);
//                }
                print(ticketList.get(0));
            }
        }
    };

    Thread waitThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    private void text1() {
        for (int i = 0; i < 3; i++) {
            fixedThreadPool.execute(buyTicket);
        }
        //巧妙使用线程管理思想
        waitThread.start();
    }


}
