package util;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;


public class EmailLogHandler extends Handler {
    private final EmailService emailService;

    public EmailLogHandler(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void publish(LogRecord record) {
        if (record.getLevel().intValue() >= Level.SEVERE.intValue()) {
            emailService.sendErrorEmail(record.getMessage());
        }
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }
}