package mapping.mappers;

import mapping.dtos.ToyDTO;
import model.Toy;

public class ToyMapper {
    public static Toy mapFrom(ToyDTO toyStoreDTO) {
        return Toy.builder()
                .id(toyStoreDTO.id())
                .name(toyStoreDTO.name())
                .category(toyStoreDTO.category())
                .price(toyStoreDTO.price())
                .amount(toyStoreDTO.amount())
                .build();
//        return new model.Toy(toyStoreDTO.id(),toyStoreDTO.name(), toyStoreDTO.categoryId(),toyStoreDTO.price(),toyStoreDTO.amount());
    }

    public static ToyDTO mapFrom(Toy toy) {
        return ToyDTO.builder()
                .id(toy.getId())
                .name(toy.getName())
                .category(toy.getCategory())
                .price(toy.getPrice())
                .amount(toy.getAmount())
                .build();
    }
}
//        return  new ToyDTO(toy.getId(), toy.getName(),toy.getCategoryId(),toy.getPrice(),toy.getAmount()  );}}
