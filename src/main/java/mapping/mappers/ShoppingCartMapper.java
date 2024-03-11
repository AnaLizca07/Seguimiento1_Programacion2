package mapping.mappers;

import mapping.dtos.ShoppingCartDTO;
import model.ShoppingCart;

public class ShoppingCartMapper {
    public static ShoppingCart mapFrom(ShoppingCartDTO shoppingCartDTO){
        return ShoppingCart.builder()
                .id(shoppingCartDTO.id())
                .customer(shoppingCartDTO.idCustomer())
                .employee(shoppingCartDTO.idEmployee())
                .date(shoppingCartDTO.date())
                .build();
//        return new model.ShoppingCart(shoppingCartDTO.id(), shoppingCartDTO.idCustomer(), shoppingCartDTO.idEmployee(), shoppingCartDTO.date());
    }

    public static ShoppingCartDTO mapFrom(ShoppingCart shoppingCart){
        return ShoppingCartDTO.builder()
               .id(shoppingCart.getId())
                .idCustomer(shoppingCart.getCustomer())
                .idEmployee(shoppingCart.getEmployee())
               .date(shoppingCart.getDate())
               .build();
//        return new ShoppingCartDTO(shoppingCart.getId(), shoppingCart.getIdCustomer(), shoppingCart.getIdEmployee(), shoppingCart.getDate());
    }
}
