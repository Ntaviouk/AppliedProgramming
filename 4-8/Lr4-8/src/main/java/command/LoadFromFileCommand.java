package command;

import service.AlbumService;

public class LoadFromFileCommand implements Command {
    private AlbumService service;
    private String path;
    public LoadFromFileCommand(AlbumService s, String path) { this.service=s; this.path=path; }
    public boolean execute() {
        try {
            service.loadFromFile(path);
            System.out.println("Loaded from file");
        } catch(Exception e) { System.out.println("Error: "+e.getMessage()); }
        return true;
    }
    public String getDescription() { return "Load album from file"; }
}
