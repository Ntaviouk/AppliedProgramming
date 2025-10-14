package util;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class EmailService {
    private final String to;
    private final String from;
    private final String user;
    private final String pass;
    private final Properties props;
    private final Session session;

    public EmailService() {
        Dotenv dotenv = Dotenv.load();
        this.to = dotenv.get("EMAIL_TO");
        this.from = dotenv.get("EMAIL_FROM");
        this.user = dotenv.get("SMTP_USER");
        this.pass = dotenv.get("SMTP_PASS");
        String host = dotenv.get("SMTP_HOST");
        String port = dotenv.get("SMTP_PORT");

        this.props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        this.session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });
    }


    private void send(String subject, String text) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            // У реальному додатку тут варто логувати помилку
            e.printStackTrace();
        }
    }

    public void sendStartupEmail() {
        send("Програму запущено", "Додаток успішно запущено на " + new Date());
    }


    public void sendErrorEmail(String errorMessage) {
        String subject = "CRITICAL ERROR in Studio App";
        String body = "Error details:\n" + errorMessage;
        send(subject, body);
    }
}