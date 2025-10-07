package util;

public abstract class MenuCommand {
    private final String name;

    public MenuCommand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void execute();
}
