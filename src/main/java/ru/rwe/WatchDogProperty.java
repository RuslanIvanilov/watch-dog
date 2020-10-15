package ru.rwe;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class WatchDogProperty {

    private WatchDogProperty(){}

    public static final boolean TEST_MODE;
    public static final String EMAIL_TO;
    public static final String EMAIL_FROM;
    public static final String EMAIL_HOST;
    public static final int CHECK_INTERVAL;
    public static final String EMAIL_DEFAULT_SUBJECT;
    public static final String HEAP_SIZE_IN;
    public static final int HEAP_FREE_SIZE_PERCENT_REMAINS;
    public static final String ROUND_PRECISION_TEMPLATE;
    public static final double BYTES_CALC_COEFF;
    public static final int  HEAP_REMAINS_PERCENT_DELTA;
    public static final String[] REQUEST_HOSTS;

    static{
        Logger log = Logger.getLogger(WatchDogProperty.class);
        Configuration config = null;

        try {
            config = new PropertiesConfiguration("watch-dog.properties");

            TEST_MODE = config.getBoolean("app-test-mode");
            EMAIL_TO = TEST_MODE?config.getString("test-email-to"):config.getString("prod-email-to") ;
            EMAIL_FROM = TEST_MODE?config.getString("test-email-from"):config.getString("prod-email-from") ;
            EMAIL_HOST = TEST_MODE?config.getString("test-email-host"):config.getString("prod-email-host") ;
            EMAIL_DEFAULT_SUBJECT = config.getString("email-default-subject");
            CHECK_INTERVAL = config.getInt("check-interval");
            HEAP_SIZE_IN = config.getString("heap-size-in").toUpperCase();
            HEAP_FREE_SIZE_PERCENT_REMAINS = config.getInt("heap-free-size-percent-remains");
            ROUND_PRECISION_TEMPLATE = config.getString("round-precision-template");
            BYTES_CALC_COEFF = config.getDouble("bytes-calc-coeff");
            HEAP_REMAINS_PERCENT_DELTA = config.getInt("heap-remains-percent-delta");
            REQUEST_HOSTS = TEST_MODE?config.getStringArray ("test-request-host"):config.getStringArray("prod-request-host") ;

        } catch(Exception e){
            log.info(e);
            throw new RuntimeException(e);
        }

    }

}
