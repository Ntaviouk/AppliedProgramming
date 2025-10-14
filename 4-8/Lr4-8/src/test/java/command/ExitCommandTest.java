package command;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class ExitCommandTest {
    @Test
    void execute_shouldReturnFalseAndPrintMessage() {
        ExitCommand command = new ExitCommand();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        boolean result = command.execute();

        assertFalse(result, "ExitCommand should return false to stop the main loop.");
        assertTrue(outContent.toString().contains("Exiting program..."));

        System.setOut(System.out);
    }
}