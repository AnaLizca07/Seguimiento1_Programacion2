package services;

import model.ShoppingCart;

import java.util.List;

public interface Service<T> {
    List<T> list();
    T byId(Integer id);
    int save(T t);
    void delete(Long id);
    Boolean verifyExist(int id);
}
