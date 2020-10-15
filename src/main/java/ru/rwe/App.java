package ru.rwe;

import org.apache.log4j.Logger;

public class App
{
    private static final Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) {
        log.info("Watch Dog run in test mode - " + WatchDogProperty.TEST_MODE);
        new TimeLoopWorker().run();
    }

}