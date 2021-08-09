package com.bbm.sendmail;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import org.junit.Test;

/**
 *
 * @author bbm29
 */
public class AppTest {

    // Colocar o email que ira enviar os emails
    private String userName = "";
    // Password do email fonte
    private String password = "";

    @Test
    public void testEmail() {

        try {
            Properties properties = new Properties();
            //Para autorizacao
            properties.put("mail.smtp.auth", "true");
            //Para seguranca
            properties.put("mail.smtp.starttls", "true");
            //Para o servidor
            properties.put("mail.smtp.host", "smtp.gmail.com");
            //Para a porta do servidor
            properties.put("mail.smtp.port", "465");
            //Para a porta a ser conectada pelo socket
            properties.put("mail.smtp.socketFactory.port", "465");
            //Para a classe a ser usada
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
