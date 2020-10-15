package ru.rwe;

import org.apache.log4j.Logger;
import ru.rwe.util.BytesConvertor;

public class HeapChecker {
    private Logger log = Logger.getLogger(this.getClass());
    private double onePercent;
    private long freeSize;
    private int currentRemainsPercent;
    private int lastRemainsPercent;

    public HeapChecker(int lastRemainsPercent) {
        this.lastRemainsPercent = lastRemainsPercent;
        long usedSize = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long maxSize = Runtime.getRuntime().maxMemory();
        freeSize = maxSize - usedSize;
        onePercent = maxSize /100.00;
        currentRemainsPercent = calcCurrentRemainsPercent();
        log.info("totalSizeByte = " + maxSize);
        log.info("freeSizeByte = " + freeSize);
        log.info("totalSize = " + BytesConvertor.convert(maxSize));
        log.info("onePercent = " + onePercent);
        log.info("remainsSize = " + BytesConvertor.convert(freeSize));
        log.info("limitPercent = " + WatchDogProperty.HEAP_FREE_SIZE_PERCENT_REMAINS+" %");
    }

    public long getFreeSize(){
        return freeSize;
    }

    public int getCurrentRemainsPercent(){
        return currentRemainsPercent;
    }

    public int getNewLastRemainsPercent() {
        int newLastRemainsPercent = lastRemainsPercent;
        if( isHeapSizeRemainsCritic() && isNeedCreateAlert()) {
            log.info("ALARM: FREE MEMORY remains = "+ currentRemainsPercent +" %, but need min = " + WatchDogProperty.HEAP_FREE_SIZE_PERCENT_REMAINS+" %" );
            log.info("SET lastRemainsPercent old = " + lastRemainsPercent + " to new = "+currentRemainsPercent);
            newLastRemainsPercent = currentRemainsPercent;
        }

        return newLastRemainsPercent;
    }

    private int calcCurrentRemainsPercent(){
        double remainsPercent = freeSize/onePercent;
        log.info("double remainsPercent = " + remainsPercent );
        currentRemainsPercent = Math.toIntExact(Math.round(remainsPercent));
        log.info("currentRemainsPercent = " + currentRemainsPercent + " %");
        return currentRemainsPercent;
    }

    private boolean isHeapSizeRemainsCritic(){
        return currentRemainsPercent <= WatchDogProperty.HEAP_FREE_SIZE_PERCENT_REMAINS;
    }

    private boolean isNeedCreateAlert(){
        return ( Math.abs(lastRemainsPercent - currentRemainsPercent) ) > WatchDogProperty.HEAP_REMAINS_PERCENT_DELTA;
    }



}
