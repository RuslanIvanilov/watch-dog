package ru.rwe.util;

import org.apache.log4j.Logger;

public class TimeMeasure {
    public static void check(Runnable task){
        Logger log = Logger.getLogger(TimeMeasure.class);
        long startTime = System.currentTimeMillis();
        task.run();

        long elapsed = System.currentTimeMillis() - startTime;
        log.info("used time: " + elapsed + " ms");
    }
}
