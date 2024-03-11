package org.service.impl;

import mapping.dtos.ToyDTO;
import model.Toy;
import model.ToyCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ToyService;
import services.impl.toy.ToyServiceImpl;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
public class ToyServiceImplTest {

    private ToyService toyService;

    @BeforeEach
    public void setup(){
        toyService = new ToyServiceImpl();
    }
    @Test
    void list(){
        List<ToyDTO> result = toyService.list();
        assertNotNull(result);
    }

    @Test
    void byId(){
        ToyDTO toyDTO = toyService.byId(81);
        assertNotNull(toyDTO);
    }

    @Test
    void save(){
        Toy toy = Toy.builder()
                .id(82)
                .name("awa")
                .price(123.12)
                .amount(4)
                .category(ToyCategory.builder()
                        .id(1)
                        .name("fem")
                        .build())
                .build();
        toyService.save(toy);
    }

    @Test
    void delete(){
        toyService.delete(81L);
    }
    @Test
    void maxToy() throws Exception {
        ToyCategory toyCategory = toyService.maxToy();
        assertNotNull(toyCategory);
    }
    @Test
    void minToy() throws Exception {
        ToyCategory toyCategory = toyService.minToy();
        assertNotNull(toyCategory);
    }

    @Test
    void categories() throws Exception {
        Map<ToyCategory, Integer> result = toyService.categories();
        assertNotNull(result);
    }
    @Test
    void sortByCategory() throws Exception {
        Map<ToyCategory, Integer> result = toyService.sortByCategory();
        assertNotNull(result);
    }
    @Test
    void increase() throws Exception {
        toyService.increase(82,1);
    }
    @Test
    void decrease() throws Exception {
        toyService.decrease(82,1);
    }
    @Test
    void showToysAboveOf() throws Exception {
        List<ToyDTO> result = toyService.showToysAboveOf(123.12);
        assertNotNull(result);
    }
    @Test
    void verifyExist() throws Exception {
        Boolean result = toyService.verifyExist(81);
        assertNotNull(result);
    }
    @Test
    void totalToys() throws Exception {
        Integer result = toyService.totalToys();
        assertNotNull(result);
    }
    @Test
    void totalPriceAllToys() throws Exception {
        Double result = toyService.totalPriceAllToys();
        assertNotNull(result);
    }



}
