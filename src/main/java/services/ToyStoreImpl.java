package services;

import mapping.dtos.ToyStoreDTO;
import model.Type;

import java.util.List;
import java.util.Map;

public interface ToyStoreImpl {
    //add, list
    List<ToyStoreDTO> addToy(ToyStoreDTO toyStoreDTO) throws Exception;
    List<ToyStoreDTO> listToys();
   List<ToyStoreDTO> deleteToys(String name) throws Exception;
    List<ToyStoreDTO> sort(List<ToyStoreDTO> toyStoreDTO) throws Exception;
    ToyStoreDTO search(String name) throws Exception;
    Object maxToy() throws Exception;
    Object minToy() throws Exception;
    Type increase() throws Exception;
    Type decrease() throws Exception;
    Map<Type,Integer> showByType() throws Exception;
    List<ToyStoreDTO> showToysAbove(double value) throws Exception;
    Boolean verifyExist(String name);
    Integer totalToys() throws Exception;
}
