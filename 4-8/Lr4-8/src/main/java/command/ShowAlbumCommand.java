package command;

import service.AlbumService;

public class ShowAlbumCommand extends AlbumCommand {
    public ShowAlbumCommand(AlbumService service) {
        super(service);
    }

    @Override
    public boolean execute() {
        if (!checkService()) return true;
        service.printAlbum();
        return true;
    }
}
