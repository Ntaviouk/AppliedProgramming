package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class Playlist implements Serializable {
    private String name;
    private Queue<Track> queue = new LinkedList<>();


    public Playlist(String name) { this.name = name; }


    public void addTrack(Track t) { queue.add(t); }
    public Track playNext() { return queue.poll(); }


    @Override
    public String toString() {
        return "Playlist '"+name+"' with "+queue.size()+" tracks";
    }
}
