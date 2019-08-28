package com.threadstudy.share.example;

import com.threadstudy.share.base.BaseClass;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Tavis
 * 线程安全使用例子
 * 定义8个消费端，2个生产端，对唯一的数据源在线程安全的情况下进行并发操作，提高效率
 */
public class TicketShop extends BaseClass {
    //    private TicketSalerDuty mTicketSaler = TicketSalerForList.getInstance();
    private TicketSalerDuty mTicketSaler = TicketSaler.getInstance();

    private final static int CONSUMER_QUEQUE_SIZE = 8;

    private final static int REFUND_QUEQUE_SIZE = 2;

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(CONSUMER_QUEQUE_SIZE + REFUND_QUEQUE_SIZE);

    private boolean shopWorking = true;

    private Runnable buyRunnable = new Runnable() {
        @Override
        public void run() {
            while (shopWorking) {
                synchronized (mTicketSaler) {
                    mTicketSaler.buyTicket();
                    print("Now have Ticket : " + mTicketSaler.ticketNum());
                }
                try {
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Runnable refundRunnable = new Runnable() {
        @Override
        public void run() {
            while (shopWorking) {
                synchronized (mTicketSaler) {
                    mTicketSaler.refundTicket();
                    print("Now have Ticket : " + mTicketSaler.ticketNum());
                }
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void startToSaleTicket() {
        print("Shop is openning");
        for (int i = 0; i < CONSUMER_QUEQUE_SIZE; i++) {
            fixedThreadPool.execute(buyRunnable);
        }
        for (int i = 0; i < REFUND_QUEQUE_SIZE; i++) {
            fixedThreadPool.execute(refundRunnable);
        }
    }

    public void offWork() throws InterruptedException {
        fixedThreadPool.shutdown();
        shopWorking = false;
        while (!fixedThreadPool.awaitTermination(1, TimeUnit.SECONDS)) {
        }
        print("Shop is closing");
    }

    public static void main(String[] args) {
        try {
            TicketShop mTicketShop = new TicketShop();
            //开始营业
            mTicketShop.startToSaleTicket();
            //营业1分钟
            Thread.sleep(60 * 1000);
            //营业结束
            mTicketShop.offWork();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
