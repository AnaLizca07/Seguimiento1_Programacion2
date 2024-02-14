package mapping.dtos;

import model.Type;

public record ToyStoreDTO(String name, Type type, Integer price, Integer amount ) {
}
