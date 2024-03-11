package mapping.dtos;

import lombok.Builder;
import model.ShoppingCart;
import model.Toy;

@Builder

public record CartDetailDTO(int id, Toy idToy, ShoppingCart idCart, int amount) {
}