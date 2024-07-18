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

class ScoreBasedCarSortAlgorithmTest {

    private CarDao carDaoMock;
    private ScoreBasedCarSortAlgorithm algorithm;

    @BeforeEach
    void setUp() {
        carDaoMock = Mockito.mock(CarDao.class);
        algorithm = new ScoreBasedCarSortAlgorithm(carDaoMock);
    }

    @Test
    void testGetSimilarCars_ExactMatch() {
        // Arrange
        Car selectedCar = new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg");
        List<Car> allCars = new ArrayList<>();
        allCars.add(selectedCar);
        allCars.add(new Car("Honda Civic", "Honda", "Civic", 2022, 180, 28, "https://example.com/civic.jpg"));
        allCars.add(new Car("Toyota Corolla .5", "Toyota", "Corolla", 2023, 170, 32, "https://example.com/corolla.jpg")); // Duplicate
        when(carDaoMock.getCar("Toyota Corolla")).thenReturn(selectedCar);
        when(carDaoMock.getAllCars()).thenReturn(allCars);

        // Act
        List<Car> similarCars = algorithm.getSimilarCars(selectedCar);

        // Assert
        assertEquals(2, similarCars.size());
        assertTrue(allCars.containsAll(similarCars));
        verify(carDaoMock, times(1)).getAllCars();
    }

    @Test
    void testGetSimilarCars_MakeAndModelMatch() {
        // Arrange
        Car selectedCar = new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg");
        List<Car> allCars = new ArrayList<>();
        allCars.add(selectedCar);
        allCars.add(new Car("Toyota Corolla .5", "Toyota", "Corolla", 2022, 170, 32, "https://example.com/corolla.jpg"));
        allCars.add(new Car("Honda Civic", "Honda", "Civic", 2022, 180, 28, "https://example.com/civic.jpg"));
        when(carDaoMock.getCar("Toyota Corolla")).thenReturn(selectedCar);
        when(carDaoMock.getAllCars()).thenReturn(allCars);

        // Act
        List<Car> similarCars = algorithm.getSimilarCars(selectedCar);

        // Assert
        assertEquals(2, similarCars.size());
        assertTrue(allCars.containsAll(similarCars));
        verify(carDaoMock, times(1)).getAllCars();
    }

    @Test
    void testGetSimilarCars_MakeMatch() {
        // Arrange
        Car selectedCar = new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg");
        List<Car> allCars = new ArrayList<>();
        allCars.add(selectedCar);
        allCars.add(new Car("Toyota Camry", "Toyota", "Camry", 2022, 180, 28, "https://example.com/camry.jpg"));
        allCars.add(new Car("Honda Civic", "Honda", "Civic", 2022, 180, 28, "https://example.com/civic.jpg"));
        when(carDaoMock.getCar("Toyota Corolla")).thenReturn(selectedCar);
        when(carDaoMock.getAllCars()).thenReturn(allCars);

        // Act
        List<Car> similarCars = algorithm.getSimilarCars(selectedCar);

        // Assert
        assertEquals(2, similarCars.size());
        assertTrue(allCars.containsAll(similarCars));
        verify(carDaoMock, times(1)).getAllCars();
    }

    @Test
    void testGetSimilarCars_YearMatch() {
        // Arrange
        Car selectedCar = new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg");
        List<Car> allCars = new ArrayList<>();
        allCars.add(selectedCar);
        allCars.add(new Car("Honda Civic", "Honda", "Civic", 2023, 180, 28, "https://example.com/civic.jpg"));
        allCars.add(new Car("Ford Mustang", "Ford", "Mustang", 2022, 180, 28, "https://example.com/mustang.jpg"));
        when(carDaoMock.getAllCars()).thenReturn(allCars);

        // Act
        List<Car> similarCars = algorithm.getSimilarCars(selectedCar);

        // Assert
        assertEquals(2, similarCars.size());
        assertTrue(allCars.containsAll(similarCars));
         verify(carDaoMock, times(1)).getAllCars();
    }

    @Test
    void testGetSimilarCars_HorsepowerMatch() {
        // Arrange
        Car selectedCar = new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg");
        List<Car> allCars = new ArrayList<>();
        allCars.add(selectedCar);
        allCars.add(new Car("Honda Civic", "Honda", "Civic", 2022, 170, 28, "https://example.com/civic.jpg"));
        allCars.add(new Car("Ford Mustang", "Ford", "Mustang", 2022, 180, 28, "https://example.com/mustang.jpg"));
        when(carDaoMock.getAllCars()).thenReturn(allCars);

        // Act
        List<Car> similarCars = algorithm.getSimilarCars(selectedCar);

        // Assert
        assertEquals(2, similarCars.size());
        assertTrue(allCars.containsAll(similarCars));
        verify(carDaoMock, times(0)).getCar("Toyota Corolla");
        verify(carDaoMock, times(1)).getAllCars();
    }

    @Test
    void testGetSimilarCars_FuelEfficiencyMatch() {
        // Arrange
        Car selectedCar = new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg");
        List<Car> allCars = new ArrayList<>();
        allCars.add(selectedCar);
        allCars.add(new Car("Honda Civic", "Honda", "Civic", 2022, 180, 30, "https://example.com/civic.jpg"));
        allCars.add(new Car("Ford Mustang", "Ford", "Mustang", 2022, 180, 28, "https://example.com/mustang.jpg"));
        when(carDaoMock.getAllCars()).thenReturn(allCars);

        // Act
        List<Car> similarCars = algorithm.getSimilarCars(selectedCar);

        // Assert
        assertEquals(2, similarCars.size());
        assertTrue(allCars.containsAll(similarCars));
        verify(carDaoMock, times(1)).getAllCars();
    }

    @Test
    void testGetSimilarCars_NoMatch() {
        // Arrange
        Car selectedCar = new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg");
        List<Car> allCars = new ArrayList<>();
        allCars.add(new Car("Honda Civic", "Honda", "Civic", 2022, 180, 28, "https://example.com/civic.jpg"));
        allCars.add(new Car("Ford Mustang", "Ford", "Mustang", 2022, 180, 28, "https://example.com/mustang.jpg"));
        when(carDaoMock.getCar("Toyota Corolla")).thenReturn(selectedCar);
        when(carDaoMock.getAllCars()).thenReturn(allCars);

        // Act
        List<Car> similarCars = algorithm.getSimilarCars(selectedCar);

        // Assert
        assertEquals(2, similarCars.size());
        assertTrue(allCars.containsAll(similarCars));
        verify(carDaoMock, times(0)).getCar("Toyota Corolla");
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
        when(carDaoMock.getCar("Toyota Corolla")).thenReturn(selectedCar);
        when(carDaoMock.getAllCars()).thenReturn(allCars);

        // Act
        List<Car> similarCars = algorithm.getSimilarCars(selectedCar);

        // Assert
        assertEquals(10, similarCars.size());
        verify(carDaoMock, times(0)).getCar("Toyota Corolla");
        verify(carDaoMock, times(1)).getAllCars();
    }

    @Test
    void testGetType() {
        // Act
        CarSortAlgorithmType type = algorithm.getType();

        // Assert
        assertEquals(CarSortAlgorithmType.SCORE_BASED, type);
    }
}