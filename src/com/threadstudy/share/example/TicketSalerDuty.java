package com.threadstudy.share.example;

/**
 * @author Tavis
 * 使用接口，定义生产端，消费端
 */
interface TicketSalerDuty {
    //生产端
    void buyTicket();

    //消费端
    void refundTicket();

    //其他功能
    int ticketNum();
}
