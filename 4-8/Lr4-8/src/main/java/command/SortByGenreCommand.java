package command;

import service.AlbumService;

public class SortByGenreCommand implements Command {
    private AlbumService service;
    public SortByGenreCommand(AlbumService s) { this.service=s; }
    public boolean execute() { service.sortByGenre(); System.out.println("Sorted by genre"); return true; }
    public String getDescription() { return "Sort album by genre"; }
}
