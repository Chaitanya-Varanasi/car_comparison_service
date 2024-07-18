package org.example.dao.impl;

import org.example.model.Car;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

public class InMemoryCarDaoTest {

    private InMemoryCarDao inMemoryCarDao = new InMemoryCarDao();

    @Test
    void addCar() {
        Car car = new Car("Tesla Model 3", "Tesla", "Model 3", 2023, 272, 33, "https://example.com/model3.jpg");
        inMemoryCarDao.addCar(car);
        assertEquals(car, inMemoryCarDao.getCar("Tesla Model 3"));
    }

    @Test
    void getCar() {
        Car hondaCivic = inMemoryCarDao.getCar("Honda Civic");
        assertNotNull(hondaCivic);
        assertEquals("Honda Civic", hondaCivic.getName());
        assertEquals("Honda", hondaCivic.getMake());
        assertEquals("Civic", hondaCivic.getModel());
        assertEquals(2023, hondaCivic.getYear());
        assertEquals(180, hondaCivic.getHorsepower());
        assertEquals(28, hondaCivic.getFuelEfficiency());
        assertEquals("https://example.com/civic.jpg", hondaCivic.getImageUrl());
    }

    @Test
    void getCar_NotFound() {
        Car nonExistentCar = inMemoryCarDao.getCar("Non-Existent Car");
        assertNull(nonExistentCar);
    }

    @Test
    void getAllCars() {
        List<Car> allCars = inMemoryCarDao.getAllCars();
        assertEquals(12, allCars.size());
        assertTrue(allCars.contains(inMemoryCarDao.getCar("Honda Civic")));
        assertTrue(allCars.contains(inMemoryCarDao.getCar("Toyota Corolla")));
        assertTrue(allCars.contains(inMemoryCarDao.getCar("Hyundai Elantra")));
        assertTrue(allCars.contains(inMemoryCarDao.getCar("Ford Focus")));
        assertTrue(allCars.contains(inMemoryCarDao.getCar("Mazda3")));
        assertTrue(allCars.contains(inMemoryCarDao.getCar("Kia Forte")));
        assertTrue(allCars.contains(inMemoryCarDao.getCar("Chevrolet Cruze")));
        assertTrue(allCars.contains(inMemoryCarDao.getCar("Nissan Sentra")));
        assertTrue(allCars.contains(inMemoryCarDao.getCar("Volkswagen Jetta")));
        assertTrue(allCars.contains(inMemoryCarDao.getCar("Subaru Impreza")));
    }
}
