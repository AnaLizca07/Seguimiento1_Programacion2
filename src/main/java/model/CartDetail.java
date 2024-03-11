package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CartDetail {
    private int id;
    private Toy toy;
    private ShoppingCart cart;
    private int amount;
}
