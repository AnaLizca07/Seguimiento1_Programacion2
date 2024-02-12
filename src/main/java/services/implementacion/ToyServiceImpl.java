package services.implementacion;

import mapping.dtos.ToyStoreDTO;
import mapping.mappers.ToyStoreMapper;
import model.Toy;
import model.Type;
import services.ToyStoreImpl;
import utilis.Constants;
import utilis.FileUtils;
import view.ToyStore;

import java.io.File;
import java.util.*;
import java.util.Collections;
import java.util.stream.Collectors;

public class ToyServiceImpl implements ToyStoreImpl {
    private List<Toy> toyList;
    public ToyServiceImpl(){
        toyList = FileUtils.getToys(new File(Constants.PATH_TOYS));
    }
    @Override
    public List<ToyStoreDTO> addToy(ToyStoreDTO toyStoreDTO) throws Exception {
        if(!verifyExist(toyStoreDTO.name())){
            toyList.add(ToyStoreMapper.mapFrom(toyStoreDTO));
            FileUtils.saveToys(new File(Constants.PATH_TOYS), toyList);
            return toyList.stream().map(ToyStoreMapper::mapFrom).toList();
        }
        throw new Exception("This toy is in the list");
    }

    @Override
    public List<ToyStoreDTO> listToys() {
        return toyList.stream().map(ToyStoreMapper::mapFrom).toList();
    }

    @Override
    public List<ToyStoreDTO> deleteToys(String name) throws Exception {
        ToyStoreDTO toy = search(name);
        if(verifyExist(name)) {
            toyList.remove(ToyStoreMapper.mapFrom(toy));
            FileUtils.saveToys(new File(Constants.PATH_TOYS), toyList);
            return toyList.stream().map(ToyStoreMapper::mapFrom).toList();
        }
        throw new Exception("Dont Found");
    }



    @Override
    public List<ToyStoreDTO> sort(List<ToyStoreDTO> toyStoreDTO) throws Exception {
        return toyStoreDTO.stream().sorted(Comparator.comparing(ToyStoreDTO::name)).toList();
    }

    @Override
    public ToyStoreDTO search(String name) throws Exception {
        if(verifyExist(name)){
            List<ToyStoreDTO> list = toyList.stream().filter(toyList-> Objects.equals(toyList.getName(), name))
                    .findFirst().stream().map(ToyStoreMapper::mapFrom).toList();
            return list.getFirst();
        }
        throw new Exception("Dont found");

    }

    @Override
    public Object maxToy() throws Exception {
        Type firstKey = ((TreeMap<Type, Integer>) showByType()).firstKey();
        Integer firstValue = showByType().get(firstKey);
        return new AbstractMap.SimpleEntry<>(firstKey,firstValue);
    }

    @Override
    public Object minToy() throws Exception {
        Type lastKey = ((TreeMap<Type, Integer>) showByType()).lastKey();
        Integer lastValue = showByType().get(lastKey);
        return new AbstractMap.SimpleEntry<>(lastKey,lastValue);
    }

    @Override
    public Type increase() throws Exception {
        return null;
    }

    @Override
    public Type decrease() throws Exception {
        return null;
    }

    @Override
    public Map<Type, Integer> showByType() throws Exception {
        Map<Type,Integer> showByType = new TreeMap<>();
        for(Toy toy : toyList){
            Type type = toy.getType();
            showByType.put(type,showByType.getOrDefault(type,0)+1);
        }
        return showByType;
    }

    @Override
    public List<ToyStoreDTO> showToysAbove(double value) throws Exception {
        List<ToyStoreDTO> toyAbove = new ArrayList<>();
        toyAbove.stream().filter(e->e.amount()>value);
        for ( Toy toy : toyList){
            if (toy.getAmount()>value){
            }
        }
        return toyAbove;
    }

    @Override
    public Boolean verifyExist(String name) {
        return toyList.stream().anyMatch(e -> e.getName().equalsIgnoreCase(name));
    }

    @Override
    public Integer totalToys() throws Exception {
        Integer totalCount = 0;
        for (Toy toy : toyList){
            totalCount += toy.getAmount();
        }
        return totalCount;
    }


    //PREGUNTAR!!!
    public int totalAmount(){
        return toyList.size();
    }
}
