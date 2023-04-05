package com.taoli.niceplace.common;

public class TakeTurnsPrint {

    Object o;
    public TakeTurnsPrint(Object o){
        this.o=o;
    }

    private static boolean flag = true;

    private static int count = 0;

    public synchronized void print1() {
        synchronized (this) {
            for (int i = 1; i <= 25; i++) {
                System.out.println("print1: " + ++count);
                try {
                    o.notifyAll();
                    o.wait();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    public void print2() {
        synchronized (this) {
            for (int i = 1; i <= 25; i++) {
                System.out.println("print2: " + ++count);
                try {
                    o.notifyAll();
                    o.wait();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}
