package services.impl.cartDetail;

import mapping.dtos.CartDetailDTO;
import mapping.mappers.CartDetailMapper;
import model.CartDetail;
import repository.Repository;
import repository.impl.cartDetail.CartDetailRepositoryJDBCImpl;
import services.Service;

import java.util.List;

public class CartDetailServiceImpl implements Service<CartDetailDTO> {
    private final Repository<CartDetail> repository;

    public CartDetailServiceImpl() {
        this.repository = new CartDetailRepositoryJDBCImpl();
    }

    @Override
    public List<CartDetailDTO> list() {
        return repository.list().stream().map(CartDetailMapper::mapFrom).toList();
    }

    @Override
    public CartDetailDTO byId(Integer id) {
        CartDetail cartDetail = repository.byId(id);
        return CartDetailMapper.mapFrom(cartDetail);
    }

    @Override
    public int save(CartDetailDTO cartDetailDTO) {
        return repository.save(CartDetailMapper.mapFrom(cartDetailDTO));
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
