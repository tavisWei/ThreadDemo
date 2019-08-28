package com.threadstudy.share.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * @author Tavis
 * 复杂变量线程安全使用单例
 */
public class TicketSalerForList implements TicketSalerDuty {
    private static volatile TicketSalerForList mTicketSaler;
    private final static int TICKET_SIZE = 10000;
    private List<Integer> ticketList = Collections.synchronizedList(new ArrayList<>(0));

    public static TicketSalerForList getInstance() {
        if (mTicketSaler == null) {
            synchronized (TicketSalerForList.class) {
                if (mTicketSaler == null) {
                    mTicketSaler = new TicketSalerForList();
                }
            }
        }
        return mTicketSaler;
    }

    public TicketSalerForList() {
        for (int i = 0; i < TICKET_SIZE; i++) {
            ticketList.add(1);
        }
    }

    @Override
    public synchronized void buyTicket() {
//        synchronized (ticketList) {
            int delIndex = ticketList.size() - 1;
            ticketList.remove(delIndex);
//        }
    }

    @Override
    public void refundTicket() {
        ticketList.add(1);
    }

    @Override
    public int ticketNum() {
        return ticketList.size();
    }
}
