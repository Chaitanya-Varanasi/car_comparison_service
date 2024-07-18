package org.example.service.comparison;

import org.example.model.Car;
import org.example.model.FieldToValueMap;

import java.util.List;
import java.util.Map;

public interface CarComparisonService extends ComparisonService {
    List<Car> getSimilarCars(String carName);

    List<Car> compareCars(String carName, List<String> comparisonCars);

    Map<String, List<FieldToValueMap>> getCarComparisonTable(String carName, List<String> comparisonCars);

    Map<String, List<FieldToValueMap>> getCarDifferenceTable(String carName, List<String> comparisonCars);
}
