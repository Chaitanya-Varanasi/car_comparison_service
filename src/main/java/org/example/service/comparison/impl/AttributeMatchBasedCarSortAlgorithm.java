package org.example.service.comparison.impl;

import org.example.constants.Beans;
import org.example.constants.CarSortAlgorithmType;
import org.example.dao.CarDao;
import org.example.model.Car;
import org.example.service.comparison.CarSortAlgorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AttributeMatchBasedCarSortAlgorithm implements CarSortAlgorithm {

    private CarDao carDao;

    public AttributeMatchBasedCarSortAlgorithm(@Qualifier(Beans.IN_MEMORY_CAR_DAO) CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public List<Car> getSimilarCars(Car selectedCar) {
        List<Car> suggestions = new ArrayList<>();
        List<Car> cars = carDao.getAllCars();

        // Find similar cars based on make, model, and year
        for (Car car : cars) {
            if (car.getMake().equals(selectedCar.getMake()) &&
                    car.getModel().equals(selectedCar.getModel()) &&
                    car.getYear()==(selectedCar.getYear()) &&
                    !car.getName().equals(selectedCar.getName())) {
                suggestions.add(car);
                if (suggestions.size() >= 10) {
                    break;
                }
            }
        }

        // If not enough similar cars found based on make, model, and year,
        // expand the search to include cars with the same make and model
        if (suggestions.size() < 10) {
            for (Car car : cars) {
                if (car.getMake().equals(selectedCar.getMake()) &&
                        car.getModel().equals(selectedCar.getModel()) &&
                        !car.getName().equals(selectedCar.getName()) && !suggestions.contains(car)) {
                    suggestions.add(car);
                    if (suggestions.size() >= 10) {
                        break;
                    }
                }
            }
        }

        // If still not enough similar cars found,
        // expand the search to include cars with the same make
        if (suggestions.size() < 10) {
            for (Car car : cars) {
                if (car.getMake().equals(selectedCar.getMake()) &&
                        !car.getName().equals(selectedCar.getName()) && !suggestions.contains(car)) {
                    suggestions.add(car);
                    if (suggestions.size() >= 10) {
                        break;
                    }
                }
            }
        }

        return suggestions;
    }

    @Override
    public CarSortAlgorithmType getType() {
        return CarSortAlgorithmType.ATTRIBUTE_MATCH_BASED;
    }

}
