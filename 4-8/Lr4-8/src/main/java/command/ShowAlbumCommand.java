package command;

import service.AlbumService;

public class ShowAlbumCommand implements Command {
    private AlbumService service;
    public ShowAlbumCommand(AlbumService s) { this.service = s; }
    public boolean execute() { service.printAlbum(); return true; }
    public String getDescription() { return "Show album"; }
}
