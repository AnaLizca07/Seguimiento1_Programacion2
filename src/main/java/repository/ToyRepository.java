package repository;

import mapping.dtos.ToyDTO;
import model.Toy;
import model.ToyCategory;
import model.ToyType;

import java.util.List;
import java.util.Map;

public interface ToyRepository{
    List<Toy> list();
    Toy byId(Integer id);
    int save(Toy toy);
    void delete(Long id);
    ToyCategory maxToy() throws Exception;
    ToyCategory minToy() throws Exception;
    Map<ToyCategory, Integer> categories() throws Exception;
    Map<ToyCategory, Integer> sortByCategory() throws Exception;
    void increase(int idToy, int amount) throws Exception;
    void decrease(int idToy, int amount) throws Exception;
    List<Toy> showToysAboveOf(double value) throws Exception;
    Boolean verifyExist(int id);
    Integer totalToys() throws Exception;
    Double totalPriceAllToys() throws Exception;
}
