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
        throw new Exception("This toy is not the list");
    }

    @Override
    public List<ToyStoreDTO> listToys() {
        return toyList.stream().map(ToyStoreMapper::mapFrom).toList();
    }

//    @Override
//    public List<ToyStoreDTO> deleteToys(String name) throws Exception {
//        ToyStoreDTO toy = search(name);
//        if(verifyExist(name)) {
//            toyList.remove(ToyStoreMapper.mapFrom(toy));
//            FileUtils.saveToys(new File(Constants.PATH_TOYS), toyList);
//            return toyList.stream().map(ToyStoreMapper::mapFrom).toList();
//        }
//        throw new Exception("Dont Found");
//    }

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
    public Map.Entry<Type,Integer> maxToy() throws Exception {
        return sort().entrySet().stream().reduce((first,second)-> second).orElse(null);
    }

    @Override
    public Map.Entry<Type,Integer> minToy() throws Exception {
        return sort().entrySet().stream().findFirst().orElse(null);
    }

    @Override
    public List<ToyStoreDTO> increase(ToyStoreDTO toyStoreDTO, int amount) throws Exception {
        toyList.stream().filter(toy1 -> Objects.equals(toy1.getName(),toyStoreDTO.name()))
                .peek(toy -> toy.setAmount(toy.getAmount()+amount))
                .findFirst();
        FileUtils.saveToys(new File(Constants.PATH_TOYS), toyList);
        return toyList.stream().map(ToyStoreMapper::mapFrom).toList();
    }
    @Override
    public List<ToyStoreDTO> decrease(ToyStoreDTO toyStoreDTO, int amount) throws Exception {
        toyList.stream().filter(toy1 -> Objects.equals(toy1.getName(),toyStoreDTO.name()))
                .peek(toy -> {
                    if(toy.getAmount()>0){
                        toy.setAmount(toy.getAmount() - amount);
                    } else if (toy.getAmount()==0) {
                        toyList.remove(toy);
                    }
                })
                .findFirst();
        FileUtils.saveToys(new File(Constants.PATH_TOYS), toyList);
        return toyList.stream().map(ToyStoreMapper::mapFrom).toList();
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
    public Map<Type, Integer> sort() throws Exception {
        return showByType().entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
    }

    @Override
    public List<ToyStoreDTO> showToysAbove(double value) throws Exception {
        return toyList.stream()
                .filter(toy -> toy.getPrize() >= value)
                .toList().stream().map(ToyStoreMapper::mapFrom).toList();
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

//    @Override
//    public void update(ToyStoreDTO toyStoreDTO) throws Exception {
//        ToyStoreDTO oldToy = search(toyStoreDTO.name());
//        if(oldToy!=null){
//            List<ToyStoreDTO> updatedList = new ArrayList<>(toyList.stream()
//                    .map(ToyStoreMapper::mapFrom).toList());
//            updatedList.remove(oldToy);
//            updatedList.add(toyStoreDTO);
//            List<Toy> toylist = updatedList.stream().map(ToyStoreMapper::mapFrom).toList();
//            FileUtils.saveToys(new File(Constants.PATH_TOYS),toylist);
//        }
//    }

    //PREGUNTAR!!!
    public int totalAmount(){
        return toyList.size();
    }

}
