package model;

import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class Toy implements Serializable {
    private int id;
    private String name;
    private ToyCategory category;
    private Double price;
    private Integer amount;
}
