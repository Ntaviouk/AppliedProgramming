package command;

import service.AlbumService;

public abstract class AlbumCommand implements Command {
    protected final AlbumService service;

    protected AlbumCommand(AlbumService service) {
        this.service = service;
    }

    protected boolean checkService() {
        if (service == null) {
            System.out.println("No album created.");
            return false;
        }
        return true;
    }
}
