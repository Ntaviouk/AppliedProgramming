package util;

import java.io.IOException;
import java.util.logging.*;

public class AppLogger {
    private static final String LOG_FILE = "app.log";


    public static Logger setupLogger(EmailService emailService) {
        Logger logger = Logger.getLogger("StudioLogger");
        logger.setUseParentHandlers(false);
        for (Handler handler : logger.getHandlers()) {
            logger.removeHandler(handler);
        }

        try {
            SimpleFormatter formatter = new SimpleFormatter();

            FileHandler fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(formatter);
            logger.addHandler(consoleHandler);

            logger.addHandler(new EmailLogHandler(emailService));

            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logger;
    }
}