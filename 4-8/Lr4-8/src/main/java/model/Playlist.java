package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Плейліст з чергою треків.
 */
public class Playlist implements Serializable {
    private final String name;
    private final Queue<Track> queue = new LinkedList<>();

    public Playlist(String name) {
        this.name = name;
    }

    public void addTrack(Track track) {
        queue.add(track);
    }

    public Track playNext() {
        return queue.poll();
    }

    @Override
    public String toString() {
        return String.format("Playlist '%s' with %d tracks", name, queue.size());
    }
}
