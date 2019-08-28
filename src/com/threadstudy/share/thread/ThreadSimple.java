package com.threadstudy.share.thread;

import com.threadstudy.share.base.BaseClass;

/**
 * @author Tavis
 * 简单的线程基础回顾
 */
public class ThreadSimple extends BaseClass {
    public static void main(String[] args) {
        ThreadSimple main = new ThreadSimple();
        //如何使用线程
        //main.threadDome1();
        //节省runable创建开销
        //main.threadDome2();
        main.threadApi();
//        main.threadWaitAndNotify();
//        main.threadJoin();

        System.exit(0);
    }

    public void threadDome1() {
        //初始化线程
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                print("Hello World");
            }
        });
        //线程开始
        thread.start();
    }

    //如果每一个线程都是相同的逻辑，可以作为私有变量
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            print("This is Thread Dome 2!! Thread id is : " + Thread.currentThread().getId());
        }
    };

    //另外的写法
    public void threadDome2() {
        int cycle = 10;
        //创建10个线程
        for (int i = 0; i < cycle; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }

    //thread简单API，中断使用
    public void threadApi() {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < Integer.MIN_VALUE; i++) {

                }
                print("Thread_doing stop!!");
            }
        });
        thread1.start();
        print("Has Thread_doing Interrupted: " + thread1.isInterrupted());
        thread1.interrupt();
        print("Has Thread_doing Interrupted: " + thread1.isInterrupted());

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                print("Thread start...");
                print("Thread will sleep...");
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                print("sleep finished");
            }
        });
        thread2.start();

        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        print("Has Thread_time_wait Interrupted: " + thread2.isInterrupted());
        thread2.interrupt();
        print("Has Thread_time_wait Interrupted: " + thread2.isInterrupted());

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                print("thread3 stop ");
            }
        });

        print(thread3.getName());
        print(String.valueOf(thread3.getId()));
        print(String.valueOf(thread3.getPriority()));
        print(String.valueOf(thread3.getThreadGroup()));
        print(String.valueOf(thread3.getState()));
        print(String.valueOf(thread3.isAlive()));
        print(String.valueOf(thread3.isDaemon()));

    }

    //Wait && Notify
    public void threadWaitAndNotify() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    print("thread1 begin to wait...");
                    wait();
                    print("thread1 stop to wait...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                print("thread2 call thread1 stop to wait...");
//                thread1.notify();
                notifyAll();
            }
        });
        thread1.start();
    }

    //Join的使用
    public void threadJoin() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    print("Thread1 will sleep 5sec");
                    Thread.sleep(5*1000);
                    print("Thread1 stop sleep");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                print("thread2 will wait thread5 to stop ");
                try {
                    thread1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                print("thread2 continue ");
                print("thread2 stop ");
            }
        });
        thread2.start();

        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        print("MainThread continue ");
    }

}
