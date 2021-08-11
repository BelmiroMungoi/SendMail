package com.bbm.sendmail;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author bbm29
 */
public class SendEmail {

    private String userName = "belmiroteste@gmail.com";
    private String password = "";
    private String listDestination = "";
    private String subject = "";
    private String content = "";
    private String name = "";

    public SendEmail(String listDestination, String subject, String content, String name) {
        this.listDestination = listDestination;
        this.subject = subject;
        this.content = content;
        this.name = name;
    }

    public void sendEmail(boolean sendHtml) {

        try {
            Properties properties = new Properties();

            properties.put("mail.smtp.ssl.trust", "*");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.socketFactory.port", "465");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password);
                }

            });

            Address[] toUser = InternetAddress.parse(listDestination);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName, name));
            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(subject);

            if (sendHtml) {
                message.setContent(content, "text/html; charset=utf-8");
            } else {
                message.setText(content);
            }
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
