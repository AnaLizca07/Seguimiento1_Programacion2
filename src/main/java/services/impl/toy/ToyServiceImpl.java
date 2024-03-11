package services.impl.toy;

import mapping.dtos.ToyDTO;
import mapping.mappers.ToyMapper;
import model.Toy;
import model.ToyCategory;
import repository.ToyRepository;
import repository.impl.toy.ToyRepositoryJDBCImpl;
import services.ToyService;
import services.threads.Threads;

import java.util.*;

public class ToyServiceImpl implements ToyService {
    private final ToyRepository toyRepository;
    public ToyServiceImpl(){
        this.toyRepository = new ToyRepositoryJDBCImpl();
    }
    @Override
    public void sleepThread(int millis) {
        try{
            Thread.sleep(millis);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ToyDTO> list() {
        return toyRepository.list()
                        .stream()
                        .map(ToyMapper::mapFrom)
                        .toList();
    }

    @Override
    public ToyDTO byId(Integer id) {
        Toy toy = toyRepository.byId(id);
        return ToyMapper.mapFrom(toy);
    }

    @Override
    public void save(Toy toy) {
        toyRepository.save(toy);
    }

    @Override
    public void delete(Long id) {
        toyRepository.delete(id);
    }

    @Override
    public ToyCategory maxToy() throws Exception {
        return toyRepository.maxToy();
    }

    @Override
    public ToyCategory minToy() throws Exception {
        return toyRepository.minToy();
    }

    @Override
    public Map<ToyCategory, Integer> categories() throws Exception {
        return toyRepository.categories();
    }

    @Override
    public Map<ToyCategory, Integer> sortByCategory() throws Exception {
        return toyRepository.sortByCategory();
    }

    @Override
    public void increase(int idToy, int amount) throws Exception {
        toyRepository.increase(idToy,amount);
    }

    @Override
    public void decrease(int idToy, int amount) throws Exception {
        toyRepository.decrease(idToy,amount);
    }

    @Override
    public List<ToyDTO> showToysAboveOf(double value) throws Exception {
        return toyRepository.showToysAboveOf(value)
                .stream()
                .map(ToyMapper::mapFrom).toList();
    }

    @Override
    public Boolean verifyExist(int id) {
        return toyRepository.verifyExist(id);
    }

    @Override
    public Integer totalToys() throws Exception {
        return toyRepository.totalToys();
    }

    @Override
    public Double totalPriceAllToys() throws Exception {
        return toyRepository.totalPriceAllToys();
    }
}

