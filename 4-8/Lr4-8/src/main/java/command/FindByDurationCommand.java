package command;

import service.AlbumService;
import model.*;
import java.util.*;

public class FindByDurationCommand implements Command {
    private AlbumService service;
    private int min, max;
    public FindByDurationCommand(AlbumService s, int min, int max) { this.service=s; this.min=min; this.max=max; }
    public boolean execute() {
        List<Track> res = service.findByDurationRange(min,max);
        if (res.isEmpty()) System.out.println("No tracks found");
        else res.forEach(System.out::println);
        return true;
    }
    public String getDescription() { return "Find tracks by duration"; }
}
