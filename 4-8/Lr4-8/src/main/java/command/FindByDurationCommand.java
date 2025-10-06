package command;

import model.Track;
import service.AlbumService;

import java.util.List;

public class FindByDurationCommand extends AlbumCommand {
    private final int min, max;

    public FindByDurationCommand(AlbumService service, int min, int max) {
        super(service);
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean execute() {
        if (!checkService()) return true;

        List<Track> res = service.findByDurationRange(min, max);
        if (res.isEmpty()) System.out.println("No tracks found.");
        else res.forEach(System.out::println);
        return true;
    }
}
