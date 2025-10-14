package util;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.*;

public class AppLogger {
    private static final Logger logger = Logger.getLogger("StudioLogger");
    private static final String LOG_FILE = "app.log";

    public static void sendStartupEmail() {
        Dotenv dotenv = Dotenv.load();
        String to = dotenv.get("EMAIL_TO");
        String from = dotenv.get("EMAIL_FROM");
        String host = dotenv.get("SMTP_HOST");
        String port = dotenv.get("SMTP_PORT");
        String user = dotenv.get("SMTP_USER");
        String pass = dotenv.get("SMTP_PASS");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Програму запущено");
            message.setText("Додаток успішно запущено на " + new Date());
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            SimpleFormatter formatter = new SimpleFormatter();

            FileHandler fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(formatter);
            logger.addHandler(consoleHandler);

            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.addHandler(new EmailHandler());
    }

    public static Logger getLogger() {
        return logger;
    }


    private static class EmailHandler extends Handler {
        @Override
        public void publish(LogRecord record) {
            if (record.getLevel().intValue() >= Level.SEVERE.intValue()) {
                sendEmail(record);
            }
        }

        private void sendEmail(LogRecord record) {
            Dotenv dotenv = Dotenv.load();
            String host = dotenv.get("SMTP_HOST");
            String port = dotenv.get("SMTP_PORT");
            String user = dotenv.get("SMTP_USER");
            String pass = dotenv.get("SMTP_PASS");
            String from = dotenv.get("EMAIL_FROM");
            String to = dotenv.get("EMAIL_TO");

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject("CRITICAL ERROR in Studio App");
                message.setText("Error details:\n" + record.getMessage());
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void flush() { }

        @Override
        public void close() throws SecurityException { }
    }
}
