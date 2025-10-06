package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;


@Data
@AllArgsConstructor
public class Artist implements Serializable {
    private String name;
    private String country;

    @Override
    public String toString() {
        return name + " (" + country + ")";
    }
}
