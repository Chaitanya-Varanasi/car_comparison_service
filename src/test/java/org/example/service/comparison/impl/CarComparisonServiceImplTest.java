package org.example.service.comparison.impl;

import org.example.constants.CarSortAlgorithmType;
import org.example.dao.CarDao;
import org.example.exception.BadArgumentException;
import org.example.exception.CarNotFoundException;
import org.example.factory.CarSortAlgorithmFactory;
import org.example.model.Car;
import org.example.model.FieldToValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarComparisonServiceImplTest {

    private CarDao carDaoMock;
    private CarSortAlgorithmFactory carSortAlgorithmFactoryMock;
    private CarComparisonServiceImpl carComparisonService;
    private AttributeMatchBasedCarSortAlgorithm carSortAlgorithm;


    @BeforeEach
    void setUp() {
        carDaoMock = Mockito.mock(CarDao.class);
        carSortAlgorithm = Mockito.mock(AttributeMatchBasedCarSortAlgorithm.class);
        carSortAlgorithmFactoryMock = Mockito.mock(CarSortAlgorithmFactory.class);
        carComparisonService = new CarComparisonServiceImpl(carDaoMock, carSortAlgorithmFactoryMock, CarSortAlgorithmType.ATTRIBUTE_MATCH_BASED.name());
    }

    @Test
    void testGetSimilarCars_ValidCarName() {
        // Arrange
        Car selectedCar = new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg");
        List<Car> similarCars = new ArrayList<>();
        similarCars.add(selectedCar);
        when(carDaoMock.getCar("Toyota Corolla")).thenReturn(selectedCar);
        when(carSortAlgorithmFactoryMock.getCarSortAlgorithm(CarSortAlgorithmType.ATTRIBUTE_MATCH_BASED))
                .thenReturn(carSortAlgorithm);

        // Act
        List<Car> result = carComparisonService.getSimilarCars("Toyota Corolla");

        // Assert
        assertEquals(0, result.size());
        verify(carDaoMock, times(1)).getCar("Toyota Corolla");
        verify(carSortAlgorithmFactoryMock, times(1)).getCarSortAlgorithm(CarSortAlgorithmType.ATTRIBUTE_MATCH_BASED);
        verify(carSortAlgorithm, times(1)).getSimilarCars(any());
    }

    @Test
    void testGetSimilarCars_InvalidCarName() {
        // Arrange
        when(carDaoMock.getCar("Invalid Car")).thenReturn(null);

        // Act & Assert
        assertThrows(CarNotFoundException.class, () -> carComparisonService.getSimilarCars("Invalid Car"));
        verify(carDaoMock, times(1)).getCar("Invalid Car");
        verify(carSortAlgorithmFactoryMock, never()).getCarSortAlgorithm(any());
    }

    @Test
    void testCompareCars_ValidCarNames() {
        // Arrange
        Car car1 = new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg");
        Car car2 = new Car("Honda Civic", "Honda", "Civic", 2023, 180, 28, "https://example.com/civic.jpg");
        Car car3 = new Car("Hyundai Elantra", "Hyundai", "Elantra", 2023, 147, 33, "https://example.com/elantra.jpg");
        when(carDaoMock.getCar("Toyota Corolla")).thenReturn(car1);
        when(carDaoMock.getCar("Honda Civic")).thenReturn(car2);
        when(carDaoMock.getCar("Hyundai Elantra")).thenReturn(car3);

        // Act
        List<Car> result = carComparisonService.compareCars("Toyota Corolla", Arrays.asList("Honda Civic", "Hyundai Elantra"));

        // Assert
        assertEquals(3, result.size());
        assertTrue(result.contains(car1));
        assertTrue(result.contains(car2));
        assertTrue(result.contains(car3));
        verify(carDaoMock, times(1)).getCar("Toyota Corolla");
        verify(carDaoMock, times(1)).getCar("Honda Civic");
        verify(carDaoMock, times(1)).getCar("Hyundai Elantra");
    }


    @Test
    void testGetCarComparisonTable_ValidCarNames() {
        // Arrange
        Car car1 = new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg");
        Car car2 = new Car("Honda Civic", "Honda", "Civic", 2023, 180, 28, "https://example.com/civic.jpg");
        Car car3 = new Car("Hyundai Elantra", "Hyundai", "Elantra", 2023, 147, 33, "https://example.com/elantra.jpg");
        List<Car> comparisonList = Arrays.asList(car1, car2, car3);
        when(carDaoMock.getCar("Toyota Corolla")).thenReturn(car1);
        when(carDaoMock.getCar("Honda Civic")).thenReturn(car2);
        when(carDaoMock.getCar("Hyundai Elantra")).thenReturn(car3);
        // Stub the getComparisonTable method to return a non-null map

        // Act
        Map<String, List<FieldToValueMap>> result = carComparisonService.getCarComparisonTable("Toyota Corolla", Arrays.asList("Honda Civic", "Hyundai Elantra"));

        // Assert
        assertNotNull(result);
        verify(carDaoMock, times(1)).getCar("Toyota Corolla");
        verify(carDaoMock, times(1)).getCar("Honda Civic");
        verify(carDaoMock, times(1)).getCar("Hyundai Elantra");
    }

    @Test
    void testGetCarComparisonTable_InvalidCarName() {
        // Arrange
        when(carDaoMock.getCar("Toyota Corolla")).thenReturn(new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg"));
        when(carDaoMock.getCar("Honda Civic")).thenReturn(new Car("Honda Civic", "Honda", "Civic", 2023, 180, 28, "https://example.com/civic.jpg"));
        when(carDaoMock.getCar("Invalid Car")).thenReturn(null);

        // Act & Assert
        assertThrows(CarNotFoundException.class, () -> carComparisonService.getCarComparisonTable("Toyota Corolla", Arrays.asList("Honda Civic", "Invalid Car")));
        verify(carDaoMock, times(1)).getCar("Toyota Corolla");
        verify(carDaoMock, times(1)).getCar("Honda Civic");
        verify(carDaoMock, times(1)).getCar("Invalid Car");
    }

    @Test
    void testGetCarDifferenceTable_ValidCarNames() {
        // Arrange
        Car car1 = new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg");
        Car car2 = new Car("Honda Civic", "Honda", "Civic", 2023, 180, 28, "https://example.com/civic.jpg");
        Car car3 = new Car("Hyundai Elantra", "Hyundai", "Elantra", 2023, 147, 33, "https://example.com/elantra.jpg");
        List<Car> comparisonList = Arrays.asList(car1, car2, car3);
        when(carDaoMock.getCar("Toyota Corolla")).thenReturn(car1);
        when(carDaoMock.getCar("Honda Civic")).thenReturn(car2);
        when(carDaoMock.getCar("Hyundai Elantra")).thenReturn(car3);

        // Act
        Map<String, List<FieldToValueMap>> result = carComparisonService.getCarDifferenceTable("Toyota Corolla", Arrays.asList("Honda Civic", "Hyundai Elantra"));

        // Assert
        assertNotNull(result);
        verify(carDaoMock, times(1)).getCar("Toyota Corolla");
        verify(carDaoMock, times(1)).getCar("Honda Civic");
        verify(carDaoMock, times(1)).getCar("Hyundai Elantra");
    }

    @Test
    void testGetCarDifferenceTable_InvalidCarName() {
        // Arrange
        when(carDaoMock.getCar("Toyota Corolla")).thenReturn(new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg"));
        when(carDaoMock.getCar("Honda Civic")).thenReturn(new Car("Honda Civic", "Honda", "Civic", 2023, 180, 28, "https://example.com/civic.jpg"));
        when(carDaoMock.getCar("Invalid Car")).thenReturn(null);

        // Act & Assert
        assertThrows(CarNotFoundException.class, () -> carComparisonService.getCarDifferenceTable("Toyota Corolla", Arrays.asList("Honda Civic", "Invalid Car")));
        verify(carDaoMock, times(1)).getCar("Toyota Corolla");
        verify(carDaoMock, times(1)).getCar("Honda Civic");
        verify(carDaoMock, times(1)).getCar("Invalid Car");
    }

    @Test
    void testValidateComparsionInputs_ValidCarNames() {
        // Arrange
        when(carDaoMock.getCar("Toyota Corolla")).thenReturn(new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg"));
        when(carDaoMock.getCar("Honda Civic")).thenReturn(new Car("Honda Civic", "Honda", "Civic", 2023, 180, 28, "https://example.com/civic.jpg"));
        when(carDaoMock.getCar("Hyundai Elantra")).thenReturn(new Car("Hyundai Elantra", "Hyundai", "Elantra", 2023, 147, 33, "https://example.com/elantra.jpg"));

        // Act & Assert
        assertDoesNotThrow(() -> carComparisonService.getCarComparisonTable("Toyota Corolla", Arrays.asList("Honda Civic", "Hyundai Elantra")));
        verify(carDaoMock, times(1)).getCar("Toyota Corolla");
        verify(carDaoMock, times(1)).getCar("Honda Civic");
        verify(carDaoMock, times(1)).getCar("Hyundai Elantra");
    }

    @Test
    void testValidateComparsionInputs_InvalidCarName() {
        // Arrange
        when(carDaoMock.getCar("Toyota Corolla")).thenReturn(new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg"));
        when(carDaoMock.getCar("Honda Civic")).thenReturn(new Car("Honda Civic", "Honda", "Civic", 2023, 180, 28, "https://example.com/civic.jpg"));
        when(carDaoMock.getCar("Invalid Car")).thenReturn(null);

        // Act & Assert
        assertThrows(CarNotFoundException.class, () -> carComparisonService.getCarComparisonTable("Toyota Corolla", Arrays.asList("Honda Civic", "Invalid Car")));
        verify(carDaoMock, times(1)).getCar("Toyota Corolla");
        verify(carDaoMock, times(1)).getCar("Honda Civic");
        verify(carDaoMock, times(1)).getCar("Invalid Car");
    }


    @Test
    void testValidateCarNameIfPresent_InvalidCarName() {
        // Arrange
        when(carDaoMock.getCar("Invalid Car")).thenReturn(null);

        // Act & Assert
        assertThrows(CarNotFoundException.class, () -> carComparisonService.getCarComparisonTable("Invalid Car", Arrays.asList("Honda Civic", "Toyota Corolla")));
        verify(carDaoMock, times(1)).getCar("Invalid Car");
    }

    @Test
    void testValidateCarNameIfPresent_EmptyCarName() {
        // Act & Assert
        assertThrows(BadArgumentException.class, () -> carComparisonService.getCarComparisonTable("", Arrays.asList("Honda Civic", "Toyota Corolla")));
        verify(carDaoMock, never()).getCar(anyString());
    }

    @Test
    void testValidateCarNameIfPresent_NullCarName() {
        // Act & Assert
        assertThrows(BadArgumentException.class, () -> carComparisonService.getCarComparisonTable(null, Arrays.asList("Honda Civic", "Toyota Corolla")));
        verify(carDaoMock, never()).getCar(anyString());
    }
}