package onlypolish.email;

import java.util.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import static onlypolish.email.EmailProperties.*;

public class Email {

    private String recipientEmail;

    private String subject;

    private String content;

    private String attachment;

    public Email(String recipientEmail, String subject, String content){
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.content = content;
    }

    private Properties getProperties(){
        Properties properties = new Properties();
        properties.put(p_host, d_host);
        properties.put(p_email, EmailVariables.OUR_EMAIL);
        properties.put(p_port, d_port);
        properties.put(p_starttls_enable, d_true);
        properties.put(p_auth, d_true);
        properties.put(p_trusted, d_host);
        return properties;
    }

    private Session getSession(){
        Properties properties = getProperties();
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EmailVariables.OUR_EMAIL, Password.password);
                    }
                });
        return session;
    }

    public void setAttachment(String attachment){
        this.attachment = attachment;
    }

    public void sendEmail() throws MessagingException {
        Session session = getSession();
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EmailVariables.OUR_EMAIL));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        message.setSubject(subject);
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(content);
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        if(attachment != null){
            messageBodyPart = new MimeBodyPart();
            String filename = attachment;
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
        }
        message.setContent(multipart);
        message.saveChanges();
        Transport.send(message);
    }
}
