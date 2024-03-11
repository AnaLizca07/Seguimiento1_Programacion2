package repository;
import java.util.List;

public interface Repository <T>{
    List<T> list();
    T byId(Integer id);
    int save(T t);
    void delete(Long id);
    Boolean verifyExist(int id);
}
