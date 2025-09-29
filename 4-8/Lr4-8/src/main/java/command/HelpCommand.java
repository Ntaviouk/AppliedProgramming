package command;

import java.util.Map;

public class HelpCommand implements Command {
    private Map<Integer, Command> cmds;
    public HelpCommand(Map<Integer, Command> cmds) { this.cmds = cmds; }
    public boolean execute() {
        cmds.forEach((k,v) -> System.out.println(k+": "+v.getDescription()));
        return true;
    }
    public String getDescription() { return "Help"; }
}
