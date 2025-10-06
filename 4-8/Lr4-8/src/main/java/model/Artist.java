package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Artist implements Serializable {
    private String name;
    private String country;

}
