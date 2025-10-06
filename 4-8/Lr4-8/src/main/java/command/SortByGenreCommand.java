package command;

import service.AlbumService;

public class SortByGenreCommand extends AlbumCommand {
    public SortByGenreCommand(AlbumService service) {
        super(service);
    }

    @Override
    public boolean execute() {
        if (!checkService()) return true;
        service.sortByGenre();
        System.out.println("Album sorted by genre.");
        return true;
    }
}
