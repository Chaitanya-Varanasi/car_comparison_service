package org.example.service.comparison.impl;

import org.example.constants.CarSortAlgorithmType;
import org.example.dao.CarDao;
import org.example.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttributeMatchBasedCarSortAlgorithmTest {

    private CarDao carDaoMock;
    private AttributeMatchBasedCarSortAlgorithm algorithm;

    @BeforeEach
    void setUp() {
        carDaoMock = Mockito.mock(CarDao.class);
        algorithm = new AttributeMatchBasedCarSortAlgorithm(carDaoMock);
    }


    @Test
    void testGetSimilarCars_MakeAndModelMatch() {
        // Arrange
        Car selectedCar = new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg");
        List<Car> allCars = new ArrayList<>();
        allCars.add(selectedCar);
        allCars.add(new Car("Toyota Corolla 2", "Toyota", "Corolla", 2023, 170, 38, "https://example.com/corolla.jpg"));
        allCars.add(new Car("Honda Civic", "Honda", "Civic", 2023, 180, 28, "https://example.com/civic.jpg"));

        when(carDaoMock.getAllCars()).thenReturn(allCars);

        // Act
        List<Car> similarCars = algorithm.getSimilarCars(selectedCar);

        // Assert
        assertEquals(1, similarCars.size());
        assertTrue(similarCars.contains(allCars.get(1)));
        verify(carDaoMock, times(1)).getAllCars();
    }

    @Test
    void testGetSimilarCars_MakeMatch() {
        // Arrange
        Car selectedCar = new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg");
        List<Car> allCars = new ArrayList<>();
        allCars.add(selectedCar);
        allCars.add(new Car("Toyota Camry", "Toyota", "Camry", 2023, 180, 28, "https://example.com/camry.jpg"));
        allCars.add(new Car("Honda Civic", "Honda", "Civic", 2023, 180, 28, "https://example.com/civic.jpg"));
        when(carDaoMock.getAllCars()).thenReturn(allCars);

        // Act
        List<Car> similarCars = algorithm.getSimilarCars(selectedCar);

        // Assert
        assertEquals(1, similarCars.size());
        assertFalse(similarCars.contains(selectedCar));
        assertTrue(similarCars.contains(allCars.get(1)));
        verify(carDaoMock, times(1)).getAllCars();
    }

    @Test
    void testGetSimilarCars_NoMatch() {
        // Arrange
        Car selectedCar = new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg");
        List<Car> allCars = new ArrayList<>();
        allCars.add(new Car("Honda Civic", "Honda", "Civic", 2023, 180, 28, "https://example.com/civic.jpg"));
        allCars.add(new Car("Ford Mustang", "Ford", "Mustang", 2023, 180, 28, "https://example.com/mustang.jpg"));
        when(carDaoMock.getAllCars()).thenReturn(allCars);

        // Act
        List<Car> similarCars = algorithm.getSimilarCars(selectedCar);

        // Assert
        assertEquals(0, similarCars.size());
        verify(carDaoMock, times(1)).getAllCars();
    }

    @Test
    void testGetSimilarCars_Limit10() {
        // Arrange
        Car selectedCar = new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg");
        List<Car> allCars = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            allCars.add(new Car("Toyota Corolla " + i, "Toyota", "Corolla", 2023, 169 + i, 30 + i, "https://example.com/corolla.jpg"));
        }
        when(carDaoMock.getAllCars()).thenReturn(allCars);

        // Act
        List<Car> similarCars = algorithm.getSimilarCars(selectedCar);

        // Assert
        assertEquals(10, similarCars.size());
        verify(carDaoMock, times(1)).getAllCars();
    }

    @Test
    void testGetType() {
        CarSortAlgorithmType type = algorithm.getType();
        assertEquals(CarSortAlgorithmType.ATTRIBUTE_MATCH_BASED, type);
    }
}