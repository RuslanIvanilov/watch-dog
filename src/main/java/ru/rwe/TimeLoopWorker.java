package ru.rwe;

import org.apache.log4j.Logger;

public class TimeLoopWorker implements Runnable {
    volatile boolean cancelSignal = false;
    private Logger log = Logger.getLogger(this.getClass());

    public void cancelSignal() { cancelSignal = true; }

    @Override
    public void run() {
        Checker checker = new Checker();
        EmailSender emailSender = new EmailSender();
        try {
            while (!cancelSignal){
                String alertMessage = checker.getCheckResultMessage();
                if (checker.hasTroubles())
                    emailSender.sendEmail(WatchDogProperty.EMAIL_DEFAULT_SUBJECT, alertMessage);
                Thread.sleep(WatchDogProperty.CHECK_INTERVAL);
            }
        }catch(Exception e){
            log.info(e);
        }
    }
}
