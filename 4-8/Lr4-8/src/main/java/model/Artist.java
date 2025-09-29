package model;

import java.io.Serializable;

public class Artist implements Serializable {
    private String name;
    private String country;

    public Artist(String name, String country) {
        this.name = name;
        this.country = country;
    }


    @Override
    public String toString() {
        return name + " (" + country + ")";
    }
}
