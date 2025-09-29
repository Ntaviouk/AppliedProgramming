package command;

public class ExitCommand implements Command {
    public boolean execute() { System.out.println("Exit"); return false; }
    public String getDescription() { return "Exit"; }
}
