package com.taoli.niceplace.common;

/**
 * @author Lenovo
 */
public class JoinThread  extends Thread{

    public JoinThread(String name){
        //线程名字
        super(name);
    }

    @Override
    public void run() {
        for(int i = 0; i < 30; i++){
            System.out.println(getName() + " : " + i);
        }
    }

}
