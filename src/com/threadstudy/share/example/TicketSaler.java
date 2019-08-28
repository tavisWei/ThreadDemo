package com.threadstudy.share.example;

/**
 * @author Tavis
 * 单一变量线程安全使用单例
 */
public class TicketSaler implements TicketSalerDuty {
    private static volatile TicketSaler mTicketSaler;
    private volatile int ticket = 10000;

    public static TicketSaler getInstance() {
        if (mTicketSaler == null) {
            synchronized (TicketSaler.class) {
                if (mTicketSaler == null) {
                    mTicketSaler = new TicketSaler();
                }
            }
        }
        return mTicketSaler;
    }

    @Override
    public void buyTicket() {
        ticket--;
    }

    @Override
    public void refundTicket() {
        ticket++;
    }

    @Override
    public int ticketNum() {
        return ticket;
    }
}
