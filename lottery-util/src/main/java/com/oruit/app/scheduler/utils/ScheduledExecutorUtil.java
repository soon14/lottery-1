/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.scheduler.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @deprecated 任务调度
 * @author Liuk
 */
public class ScheduledExecutorUtil {


    public static void execute(Runnable runnable,Long initialDelay,Long period) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        // 从现在开始1秒钟之后，每隔1秒钟执行一次job1
        service.scheduleAtFixedRate(
                runnable, initialDelay,
                period, TimeUnit.MINUTES);
    }
}
