package com.taoli.niceplace.common;

public class TurnsPrint {
    private static boolean flag = true;

    private static int count = 0;
    Object o;
    public TurnsPrint(Object o){
        this.o=o;
    }

    public  void print1() {
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
