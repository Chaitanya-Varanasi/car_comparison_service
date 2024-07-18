package org.example.service.comparison.impl;

import org.example.constants.Beans;
import org.example.constants.CarSortAlgorithmType;
import org.example.dao.CarDao;
import org.example.model.Car;
import org.example.service.comparison.CarSortAlgorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MLBasedCarSortAlgorithm implements CarSortAlgorithm {
    private final CarDao carDao;

    public MLBasedCarSortAlgorithm(@Qualifier(Beans.IN_MEMORY_CAR_DAO) CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public List<Car> getSimilarCars(Car car) {
        List<Car> allCars = carDao.getAllCars();
        List<Car> similarCars = new ArrayList<>();
        //Car selecteCar = carDao.getCar(car);

        return similarCars;
    }

    @Override
    public CarSortAlgorithmType getType() {
        return CarSortAlgorithmType.ML;
    }


}