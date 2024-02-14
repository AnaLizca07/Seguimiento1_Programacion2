package services;

import mapping.dtos.ToyStoreDTO;
import model.Type;

import java.util.List;
import java.util.Map;

public interface ToyStoreInt {
    //add, list
    List<ToyStoreDTO> addToy(ToyStoreDTO toyStoreDTO) throws Exception;
    List<ToyStoreDTO> listToys();
//   List<ToyStoreDTO> deleteToys(String name) throws Exception;

    ToyStoreDTO search(String name) throws Exception;
    Map.Entry<Type,Integer> maxToy() throws Exception;
    Map.Entry<Type,Integer> minToy() throws Exception;
    List<ToyStoreDTO> increase(ToyStoreDTO toyStoreDTO, int amount) throws Exception;
    List<ToyStoreDTO> decrease(ToyStoreDTO toyStoreDTO, int amount) throws Exception;
    Map<Type,Integer> showByType() throws Exception;
    Map<Type,Integer> sort() throws Exception;
    List<ToyStoreDTO> showToysAbove(double value) throws Exception;
    Boolean verifyExist(String name);
    Integer totalToys() throws Exception;
//    void  update(ToyStoreDTO toyStoreDTO) throws Exception;
}
