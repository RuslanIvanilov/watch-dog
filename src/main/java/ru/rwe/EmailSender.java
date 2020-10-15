package ru.rwe;

import org.apache.log4j.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class EmailSender {
    private Logger log = Logger.getLogger(this.getClass());

    public void sendEmail(String subject, String emailText) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", WatchDogProperty.EMAIL_HOST);
        Session session = Session.getDefaultInstance(properties);
        String[] emailsList = WatchDogProperty.EMAIL_TO.split(";");

        for (String email : emailsList) {
            if(email.length() <1 ) continue;
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(WatchDogProperty.EMAIL_FROM));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                message.setSubject(subject + " " + getHostName());
                message.setText(emailText);
                Transport.send(message);
                log.info(getMessageHeadPartForLog(message));
                log.info("Subject: "+message.getSubject());
                log.info("message text: " +  message.getContent());
            } catch (
                    MessagingException | IOException mex) {
                log.info(mex);
            }
        }

    }

    private String getMessageHeadPartForLog(MimeMessage message) throws MessagingException {
        String result = "";
        Enumeration<String> headlines = message.getAllHeaderLines();
        while(headlines.hasMoreElements()) {
            result = headlines.nextElement();
            if(result.contains("To:")) break;
        }
        return result;
    }

    private String getHostName() throws UnknownHostException{
        return InetAddress.getLocalHost().getHostName();

    }
}
