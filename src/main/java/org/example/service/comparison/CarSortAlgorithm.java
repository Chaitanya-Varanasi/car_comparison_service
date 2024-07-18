package org.example.service.comparison;

import org.example.constants.CarSortAlgorithmType;
import org.example.model.Car;

import java.util.List;

public interface CarSortAlgorithm {
    List<Car> getSimilarCars(Car car);
    CarSortAlgorithmType getType();
}
