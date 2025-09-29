package command;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class HelpCommandTest {
    @Test
    void testExecuteAndDescription() {
        Map<Integer, Command> cmds = new HashMap<>();
        cmds.put(0, new ExitCommand());
        HelpCommand help = new HelpCommand(cmds);

        assertTrue(help.execute());
        assertEquals("Help", help.getDescription());
    }
}
