package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.logging.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AppLoggerTest {

    @Mock
    private EmailService mockEmailService; // Мокуємо залежність

    private Logger logger;

    @BeforeEach
    void setUp() {
        logger = Logger.getLogger("StudioLogger");
        for (Handler handler : logger.getHandlers()) {
            logger.removeHandler(handler);
        }
    }

    @Test
    void setupLogger_shouldConfigureAndReturnLoggerCorrectly() {
        Logger configuredLogger = AppLogger.setupLogger(mockEmailService);


        assertNotNull(configuredLogger, "Logger should not be null.");
        assertSame(logger, configuredLogger, "Should return the same logger instance.");

        assertFalse(configuredLogger.getUseParentHandlers(), "Parent handlers should be disabled.");

        assertEquals(Level.ALL, configuredLogger.getLevel(), "Log level should be set to ALL.");

        Handler[] handlers = configuredLogger.getHandlers();
        assertEquals(3, handlers.length, "There should be exactly three handlers configured.");

        boolean hasFileHandler = Arrays.stream(handlers)
                .anyMatch(h -> h instanceof FileHandler);
        boolean hasConsoleHandler = Arrays.stream(handlers)
                .anyMatch(h -> h instanceof ConsoleHandler);
        boolean hasEmailLogHandler = Arrays.stream(handlers)
                .anyMatch(h -> h instanceof EmailLogHandler);

        assertTrue(hasFileHandler, "A FileHandler should be present.");
        assertTrue(hasConsoleHandler, "A ConsoleHandler should be present.");
        assertTrue(hasEmailLogHandler, "An EmailLogHandler should be present.");
    }
}