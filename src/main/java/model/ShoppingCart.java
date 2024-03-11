package model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ShoppingCart {
    private int id;
    private Customer customer;
    private Employee employee;
    private LocalDate date;
}
