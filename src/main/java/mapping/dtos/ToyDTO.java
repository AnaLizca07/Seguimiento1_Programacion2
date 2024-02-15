package mapping.dtos;

import model.Type;

public record ToyDTO(String name, Type type, Integer price, Integer amount ) {
}
