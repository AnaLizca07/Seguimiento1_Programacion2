package mapping.mappers;

import mapping.dtos.CartDetailDTO;
import model.CartDetail;

public class CartDetailMapper {
    public static CartDetail mapFrom(CartDetailDTO cartDetailDTO){
        return CartDetail.builder()
                .id(cartDetailDTO.id())
                .cart(cartDetailDTO.idCart())
                .toy(cartDetailDTO.idToy())
                .amount(cartDetailDTO.amount())
                .build();
//        return new model.CartDetail(cartDetailDTO.id(), cartDetailDTO.idCart(), cartDetailDTO.toy(), cartDetailDTO.amount());
    }

    public static CartDetailDTO mapFrom(CartDetail cartDetail){
        return CartDetailDTO.builder()
               .id(cartDetail.getId())
                .idCart(cartDetail.getCart())
                .idToy(cartDetail.getToy())
               .amount(cartDetail.getAmount())
               .build();
//        return new CartDetailDTO(cartDetail.getId(), cartDetail.getIdCart(), cartDetail.getIdToy(), cartDetail.getAmount());
    }
}
