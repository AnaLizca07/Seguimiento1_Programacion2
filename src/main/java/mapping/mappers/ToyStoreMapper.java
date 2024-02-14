package mapping.mappers;

import mapping.dtos.ToyStoreDTO;
import model.Toy;

public class ToyStoreMapper {
    public static Toy mapFrom(ToyStoreDTO toyStoreDTO){
        return new Toy(toyStoreDTO.name(),toyStoreDTO.type(),toyStoreDTO.price(),toyStoreDTO.amount());
    }

    public static ToyStoreDTO mapFrom(Toy toy){return  new ToyStoreDTO(toy.getName(),toy.getType(),toy.getPrice(),toy.getAmount()  );}}
