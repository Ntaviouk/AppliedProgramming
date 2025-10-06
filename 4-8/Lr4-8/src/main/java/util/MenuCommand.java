package util;


public class MenuCommand {
    private final String name;
    private final Runnable action;

    public MenuCommand(String name, Runnable action) {
        this.name = name;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public Runnable getAction() {
        return action;
    }
}
