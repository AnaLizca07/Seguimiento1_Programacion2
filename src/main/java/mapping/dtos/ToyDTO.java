package mapping.dtos;

import lombok.Builder;
import model.ToyCategory;

@Builder

public record ToyDTO(int id, String name, ToyCategory category, Double price, Integer amount ) {
}