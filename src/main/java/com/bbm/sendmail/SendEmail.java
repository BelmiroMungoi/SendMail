package com.bbm.sendmail;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

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

    //metodo para envio de email com um anexo
    public void sendEmailAnexo(boolean sendHtml) {

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

            //Corpo do emai
            MimeBodyPart emailBody = new MimeBodyPart();

            if (sendHtml) {
                emailBody.setContent(content, "text/html; charset=utf-8");
            } else {
                emailBody.setText(content);
            }

            List<FileInputStream> files = new ArrayList<FileInputStream>();
            files.add(pdfSimulator());
            files.add(pdfSimulator());
            files.add(pdfSimulator());
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(emailBody);

            int index = 0;
            for (FileInputStream file : files) {
                //Anexo do email
                MimeBodyPart anexoEmail = new MimeBodyPart();
                anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(file, "application/pdf")));
                anexoEmail.setFileName("anexo"+index+".pdf");

                multipart.addBodyPart(anexoEmail);
                index++;
            }

            message.setContent(multipart);

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Esse metodo ira simular um pdf
    private FileInputStream pdfSimulator() throws Exception {
        Document document = new Document();
        File file = new File("anexo.pdf");
        file.createNewFile();
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        document.add(new Paragraph("Conteudo do pdf "));
        document.close();

        return new FileInputStream(file);
    }
}
