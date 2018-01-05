/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.weixin.example;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Administrator
 */
public class Test {

   public static void main(String[] args) throws InterruptedException {  
        TimerTask task = new TimerTask() {  
            @Override  
            public void run() {  
                // task to run goes here  
                System.out.println("Hello !!!");  
            }  
        };  
        Timer timer = new Timer();  
        // schedules the task to be run in an interval  
        int times = 1000*20;
        timer.schedule(task, times);
        Thread.sleep(times+5000);
        timer.cancel();
        task.cancel();
        System.out.println("----run success !----");
        System.gc();
//        timer.scheduleAtFixedRate(task, delay, intevalPeriod);  
    } // end of mai
}
