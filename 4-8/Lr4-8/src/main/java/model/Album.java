package model;

import lombok.Data;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


@Data
public class Album implements Serializable {
    private String name;
    private final List<Track> tracks = new ArrayList<>();

    public Album(String name) {
        this.name = name;
    }

    public void addTrack(Track track) {
        tracks.add(track);
    }

    public int totalDuration() {
        return tracks.stream()
                .mapToInt(Track::getDurationSeconds)
                .sum();
    }

    public void sortByGenre() {
        tracks.sort(Comparator.comparing(t -> t.getGenre().name()));
    }

    public List<Track> findByDurationRange(int min, int max) {
        return tracks.stream()
                .filter(t -> t.getDurationSeconds() >= min && t.getDurationSeconds() <= max)
                .collect(Collectors.toList());
    }
}
