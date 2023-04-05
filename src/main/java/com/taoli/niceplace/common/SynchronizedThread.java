package com.taoli.niceplace.common;

/**
 * @author Lenovo
 */
public class SynchronizedThread extends Thread{

    private Ticket ticket;

    public SynchronizedThread(String name, Ticket ticket) {
        //定义线程名字
        super(name);
        this.ticket = ticket;
    }

    @Override
    public void run() {
        super.run();
        ticket.sale();
    }

}
