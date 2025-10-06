package command;

public interface Command {
    boolean execute();
    default String getDescription() {
        return this.getClass().getSimpleName().replace("Command", "");
    }
}
