package com.youdian.soundeffects.util;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author DHB
 */
public class ThreadUtil {


    /**
     * 创建线程池
     *
     * @param corePoolSize 线程池大小
     * @return 线程池
     */
    public static ScheduledThreadPoolExecutor newExecutorService(int corePoolSize, String name) {
        return new ScheduledThreadPoolExecutor(corePoolSize,
                new BasicThreadFactory.Builder().namingPattern(name + "-%d").daemon(true).build());
    }


}
