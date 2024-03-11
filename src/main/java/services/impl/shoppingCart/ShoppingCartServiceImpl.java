package services.impl.shoppingCart;

import mapping.dtos.ShoppingCartDTO;
import mapping.mappers.ShoppingCartMapper;
import model.ShoppingCart;
import repository.Repository;
import repository.impl.shoppingCart.ShoppingCartRepositoryJDBCImpl;
import services.Service;

import java.util.List;

public class ShoppingCartServiceImpl implements Service<ShoppingCartDTO> {
    private final Repository<ShoppingCart> repository;

    public ShoppingCartServiceImpl() {
        this.repository = new ShoppingCartRepositoryJDBCImpl();
    }

    @Override
    public List<ShoppingCartDTO> list() {
       return repository.list().stream().map(ShoppingCartMapper::mapFrom).toList();
    }

    @Override
    public ShoppingCartDTO byId(Integer id) {
        ShoppingCart shoppingCart = repository.byId(id);
        return ShoppingCartMapper.mapFrom(shoppingCart);
    }

    @Override
    public int save(ShoppingCartDTO shoppingCartDTO) {
        return repository.save(ShoppingCartMapper.mapFrom(shoppingCartDTO));
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public Boolean verifyExist(int id) {
        return repository.verifyExist(id);
    }
}
