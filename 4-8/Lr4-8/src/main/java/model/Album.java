package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Album implements Serializable {
    private String name;
    private List<Track> tracks = new ArrayList<>();

    public Album(String name) { this.name = name; }
    public String getName() { return name; }
    public List<Track> getTracks() { return tracks; }

    public void addTrack(Track t) { tracks.add(t); }

    public int totalDuration() {
        return tracks.stream().mapToInt(Track::getDurationSeconds).sum();
    }

    public void sortByGenre() {
        tracks.sort(Comparator.comparing(t -> t.getGenre().name()));
    }

    public List<Track> findByDurationRange(int min, int max) {
        List<Track> res = new ArrayList<>();
        for (Track t : tracks) {
            if (t.getDurationSeconds() >= min && t.getDurationSeconds() <= max) {
                res.add(t);
            }
        }
        return res;
    }
}
