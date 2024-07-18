package org.example.api;

import com.google.common.collect.Lists;
import org.example.model.Car;
import org.example.model.FieldToValueMap;
import org.example.service.comparison.CarComparisonService;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CarComparisonApiTest {

    @BeforeMethod
    private void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private CarComparisonService carComparisonService;

    @InjectMocks
    private CarComparisonApi carComparisonApi;

    @Test
    public void testGetSimilarCars() {
        String carName = "Honda Civic";
        List<Car> similarCars = new ArrayList<>();
        similarCars.add(new Car("Honda Civic", "Honda", "Civic", 2023, 180, 28, "https://example.com/civic.jpg"));
        similarCars.add(new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg"));
        similarCars.add(new Car("Hyundai Elantra", "Hyundai", "Elantra", 2023, 147, 33, "https://example.com/elantra.jpg"));

        when(carComparisonService.getSimilarCars(carName)).thenReturn(similarCars);

        ResponseEntity<List<Car>> response = carComparisonApi.getSimilarCars(carName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(similarCars, response.getBody());
    }

    @Test
    public void testCompareCars() {
        String carName = "Honda Civic";
        List<String> comparisonCars = Arrays.asList("Toyota Corolla", "Hyundai Elantra");
        List<Car> comparisonList = new ArrayList<>();

        comparisonList.add(new Car("Honda Civic", "Honda", "Civic", 2023, 180, 28, "https://example.com/civic.jpg"));
        comparisonList.add(new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg"));
        comparisonList.add(new Car("Hyundai Elantra", "Hyundai", "Elantra", 2023, 147, 33, "https://example.com/elantra.jpg"));

        when(carComparisonService.compareCars(carName, comparisonCars)).thenReturn(comparisonList);

        ResponseEntity<List<Car>> response = carComparisonApi.compareCars(carName, comparisonCars);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comparisonList, response.getBody());
    }

    @Test
    public void testGetComparisonTable() {
        String carName = "Honda Civic";
        List<String> comparisonCars = Arrays.asList("Toyota Corolla", "Hyundai Elantra");
        Map<String, List<FieldToValueMap>> comparisonTable = new HashMap<>();
        comparisonTable.put("Honda Civic", Arrays.asList(new FieldToValueMap("make", "Honda"), new FieldToValueMap("model", "Civic")));
        comparisonTable.put("Toyota Corolla", Arrays.asList(new FieldToValueMap("make", "Toyota"), new FieldToValueMap("model", "Corolla")));
        comparisonTable.put("Hyundai Elantra", Arrays.asList(new FieldToValueMap("make", "Hyundai"), new FieldToValueMap("model", "Elantra")));

        when(carComparisonService.getCarComparisonTable(carName, comparisonCars)).thenReturn(comparisonTable);

        ResponseEntity<Map<String, List<FieldToValueMap>>> response = carComparisonApi.getComparisonTable(carName, comparisonCars);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comparisonTable, response.getBody());
    }

    @Test
    public void testGetDifferenceTable() {
        String carName = "Honda Civic";
        List<String> comparisonCars = Arrays.asList("Toyota Corolla", "Hyundai Elantra");
        Map<String, List<FieldToValueMap>> differenceTable = new HashMap<>();
        differenceTable.put("Honda Civic", Arrays.asList(new FieldToValueMap("make", "Honda"), new FieldToValueMap("model", "Civic")));
        differenceTable.put("Toyota Corolla", Arrays.asList(new FieldToValueMap("make", "Toyota"), new FieldToValueMap("model", "Corolla")));
        differenceTable.put("Hyundai Elantra", Lists.asList(new FieldToValueMap("make", "Hyundai"), new FieldToValueMap[]{new FieldToValueMap("model", "Elantra")}));

        when(carComparisonService.getCarDifferenceTable(carName, comparisonCars)).thenReturn(differenceTable);

        ResponseEntity<Map<String, List<FieldToValueMap>>> response = carComparisonApi.getDifferenceTable(carName, comparisonCars);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(differenceTable, response.getBody());
    }
}
