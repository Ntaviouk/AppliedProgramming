package command;

import service.AlbumService;

public class LoadFromFileCommand extends AlbumCommand {
    private final String path;

    public LoadFromFileCommand(AlbumService service, String path) {
        super(service);
        this.path = path;
    }

    @Override
    public boolean execute() {
        if (!checkService()) return true;

        try {
            service.loadFromFile(path);
            System.out.println("Album loaded from file: " + path);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return true;
    }
}
