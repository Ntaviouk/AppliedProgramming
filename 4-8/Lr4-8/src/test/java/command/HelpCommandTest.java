package command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HelpCommandTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void execute_shouldPrintDescriptionOfAllCommands() {
        Map<Integer, Command> testCommands = new LinkedHashMap<>();
        testCommands.put(1, new ExitCommand()); // getDescription() поверне "Exit"
        testCommands.put(2, new ShowAlbumCommand(null)); // getDescription() поверне "ShowAlbum"

        HelpCommand helpCommand = new HelpCommand(testCommands);

        boolean result = helpCommand.execute();

        assertTrue(result, "HelpCommand should always return true.");

        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("1: Exit"), "Output should contain the first command description.");
        assertTrue(output.contains("2: ShowAlbum"), "Output should contain the second command description.");
    }

    @Test
    void execute_whenCommandMapIsEmpty_shouldPrintNothing() {
        Map<Integer, Command> emptyCommands = new LinkedHashMap<>();
        HelpCommand helpCommand = new HelpCommand(emptyCommands);

        boolean result = helpCommand.execute();

        assertTrue(result);
        assertEquals("", outputStreamCaptor.toString().trim(), "Output should be empty for an empty command map.");
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    private static class ExitCommand implements Command {
        @Override
        public boolean execute() { return false; }
    }
}