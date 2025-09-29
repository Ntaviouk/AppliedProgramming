package command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExitCommandTest {
    @Test
    void testExecute() {
        ExitCommand cmd = new ExitCommand();
        assertFalse(cmd.execute()); // should stop loop
    }

    @Test
    void testDescription() {
        assertEquals("Exit", new ExitCommand().getDescription());
    }
}
