package ru.rwe;

import org.apache.log4j.Logger;
import ru.rwe.util.BytesConvertor;
import java.io.IOException;
import java.net.InetAddress;

public class Checker {
    private Logger log = Logger.getLogger(this.getClass());
    private StringBuilder checkResultMessage = new StringBuilder();
    private int lastRemainsPercent = 0;
    private boolean lastIsReachable = true;

    public String getCheckResultMessage(){
        String result = checkResultMessage.toString();
        log.info("checkResultMessage : " + result );
        checkResultMessage.setLength(0);
        return result;
    }

    public boolean hasTroubles(){
        return allChecks();
    }

    public boolean allChecks(){
        String HeapSizeMessage = checkHeapSize();
        log.info("HeapSizeMessage = "+HeapSizeMessage);
        if(HeapSizeMessage.trim().length() > 0) checkResultMessage.append( HeapSizeMessage );
        String RequestHostMessage = checkRequestHost();
        if(RequestHostMessage.trim().length() > 0) checkResultMessage.append( RequestHostMessage );
        log.info("checkResultMessage.length = " + checkResultMessage.length() + " ["+ checkResultMessage.toString() +"]");
        return checkResultMessage.length() > 0;
    }

    private String checkHeapSize(){
        String result = "";
        HeapChecker heapChecker = new HeapChecker(lastRemainsPercent);
        int newLastRemainsPercent = heapChecker.getNewLastRemainsPercent();
        log.info("lastRemainsPercent = "+lastRemainsPercent);
        log.info("newLastRemainsPercent = "+newLastRemainsPercent);
        if( lastRemainsPercent != newLastRemainsPercent){
            lastRemainsPercent = newLastRemainsPercent;
            result = "Java Heap free memory remains = "+BytesConvertor.convert( heapChecker.getFreeSize() )+" - "+ heapChecker.getCurrentRemainsPercent() + " %" ;
            log.info(result);
        }
        return result;
    }

    private String checkRequestHost(){
        String result = "";
        for(String host : WatchDogProperty.REQUEST_HOSTS){
            try {
                boolean isReachable = InetAddress.getByName(host).isReachable(1000);
                log.info("Connection to "+host+" is reachable = "+isReachable);
                if(lastIsReachable != isReachable){
                    lastIsReachable = isReachable;
                    result = "\tConnection to host: "+host+" is "+lastIsReachable;
                    log.info(result);
                }
            }catch(IOException e){
                log.info(e);
            }
        }
        return result;
    }

}
