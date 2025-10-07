package model;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Data
public class Playlist implements Serializable {
    private String name;
    private final Queue<Track> queue = new LinkedList<>();

    public Playlist(String name) { this.name = name; }

    public void addTrack(Track t) { queue.add(t); }

    public Track playNext() { return queue.poll(); }

    public List<Track> getTracks() { return List.copyOf(queue); }

    @Override
    public String toString() {
        return String.format("Playlist '%s' (%d tracks)", name, queue.size());
    }
}
