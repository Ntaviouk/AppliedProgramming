package command;

import java.util.Map;

public class HelpCommand implements Command {
    private final Map<Integer, Command> commands;

    public HelpCommand(Map<Integer, Command> commands) {
        this.commands = commands;
    }

    @Override
    public boolean execute() {
        commands.forEach((k, v) -> System.out.println(k + ": " + v.getDescription()));
        return true;
    }
}
