package org.service.impl;
import mapping.dtos.ToyStoreDTO;
import model.Type;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import services.implementacion.ToyServiceImpl;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ToyServiceImplTest {

    private ToyServiceImpl service;

    @Before
    public void setup(){
        service = new ToyServiceImpl();
    }

    @Test
    public void addToy_Successful() throws Exception {
        String name = "Cat";
        Type type = Type.M;
        Integer price = 200000;
        Integer amount = 3;
        ToyStoreDTO toyToAdd = new ToyStoreDTO(name,type,price,amount);
        List<ToyStoreDTO> expected = Collections.singletonList(toyToAdd);

        List<ToyStoreDTO> result = service.addToy(toyToAdd);
        assertEquals(expected,result);
    }
    @Test
    public void addToy_Exception() throws Exception {
        String name = "Cat";
        Type type = Type.M;
        Integer price = 200000;
        Integer amount = 3;
        ToyStoreDTO toyToAdd = new ToyStoreDTO(name,type,price,amount);
        List<ToyStoreDTO> expected = Collections.singletonList(toyToAdd);

        List<ToyStoreDTO> result = service.addToy(toyToAdd);
        assertEquals(expected,result);

        assertThrows(Exception.class,()->service.addToy(toyToAdd));
    }

    @Test
    public void list_Successful(){
        List<ToyStoreDTO> toyStoreDTOList = service.listToys();
        assertNotNull(toyStoreDTOList);
        assertFalse(toyStoreDTOList.isEmpty());
    }//como hago el escenario malo.

    @Test
    public void search_Successful() throws Exception {
        String name = "Cat";
        Type type = Type.M;
        Integer price = 200000;
        Integer amount = 3;
        ToyStoreDTO toyExpected = new ToyStoreDTO(name,type,price,amount);

        ToyStoreDTO result = service.search(name);
        assertEquals(toyExpected,result);

        assertThrows(Exception.class,()->service.search(name));
    }

    @Test
    public void search_Exception() throws Exception {
        String name = "Ball";
        Type type = Type.M;
        Integer price = 200000;
        Integer amount = 3;
        ToyStoreDTO toyExpected = new ToyStoreDTO(name,type,price,amount);

        ToyStoreDTO result = service.search(name);
        assertThrows(Exception.class,()->service.search(name));
    }



    @Test
    public void test_MaxToy() throws Exception {
        Map<Type, Integer> testMap = Map.of(Type.M,30,Type.F,10,Type.U,50);
        Map.Entry<Type,Integer> expected = testMap.entrySet().stream().max(Map.Entry.comparingByValue()).orElse(null);
        Map.Entry<Type,Integer> result = service.maxToy();

        assertEquals(expected,result);
    }

    @Test
    public void testMinToy()throws Exception{
        Map<Type, Integer> testMap = Map.of(Type.M,30,Type.F,10,Type.U,50);
        Map.Entry<Type,Integer> expected = testMap.entrySet().stream().min(Map.Entry.comparingByValue()).orElse(null);
        Map.Entry<Type,Integer> result = service.minToy();

        assertEquals(expected,result);
    }

    @Test
    public void testIncrease(){

    }

    @Test
    public void testDecrease(){

    }

    @Test
    public void testShowByType(){

    }

    @Test
    public void testSort(){

    }

    @Test
    public void testShowToysAbove(){

    }

    @Test
    public void testVerifyExist(){

    }

    @Test
    public void testTotalToys(){

    }

}
