package ru.rwe;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.*;

import org.junit.Test;

public class AppTest
{
    @Test
    public void shouldAnswerHostName(){
        InetAddress ip;
        String hostname;
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            System.out.println("Your current IP address : " + ip);
            System.out.println("Your current Hostname : " + hostname);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        assertTrue( true );
    }

    @Test
    public void shouldPingIpPort() throws IOException {
        InetAddress address = InetAddress.getByName("192.168.31.30");
        boolean reachable = address.isReachable(5000);
        System.out.println("Is host reachable? " + reachable);
        assertTrue( reachable );
    }


    @Test
    public void shouldAnswerTimeDelta() throws InterruptedException {
        LocalDateTime startDateTime = LocalDateTime.now();
        new Thread().sleep(30000);
        LocalDateTime endDateTime = LocalDateTime.now();
        Duration duration = Duration.between(startDateTime, endDateTime);
        System.out.println( duration.getSeconds() );
        assertTrue( true );
    }


}