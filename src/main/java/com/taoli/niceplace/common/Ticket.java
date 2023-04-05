package com.taoli.niceplace.common;

public class Ticket {

    private int count = 1;
    public void sale() {
        while (true) {
            //模拟使用同一锁对象来限制多线程进入购买(相当于秒杀)
            synchronized (this) {
                //模拟先查数据库
                if (count > 200) {
                    System.out.println("票已经卖完啦");
                    break;
                } else {
                    //减库存
                    System.out.println(Thread.currentThread().getName() + "卖的第 " + count++ + " 张票");
                }
            }
        }
    }
}
