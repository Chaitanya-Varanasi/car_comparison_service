package org.example.service.comparison.impl;

import org.example.constants.Beans;
import org.example.constants.CarSortAlgorithmType;
import org.example.dao.CarDao;
import org.example.exception.BadArgumentException;
import org.example.exception.CarNotFoundException;
import org.example.factory.CarSortAlgorithmFactory;
import org.example.model.Car;
import org.example.model.FieldToValueMap;
import org.example.service.comparison.CarComparisonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CarComparisonServiceImpl implements CarComparisonService {

    private static final Logger logger = LoggerFactory.getLogger(CarComparisonServiceImpl.class);

    private CarDao carDao;

    @Value("${car.sort.algorithm}")
    private String carSortAlgorithmType;

    private CarSortAlgorithmFactory carSortAlgorithmFactory;


    public CarComparisonServiceImpl(@Qualifier(Beans.IN_MEMORY_CAR_DAO) CarDao carDao, CarSortAlgorithmFactory carSortAlgorithmFactory,
                                    @Value("${car.sort.algorithm}") String carSortAlgorithmType) {
        this.carDao = carDao;
        this.carSortAlgorithmFactory = carSortAlgorithmFactory;
        this.carSortAlgorithmType = carSortAlgorithmType;
    }

    @Override
    public List<Car> getSimilarCars(String carName) {
        try {
            Car car = validateAndGetCarNameIfPresent(carName);
            CarSortAlgorithmType sortAlgorithmType = CarSortAlgorithmType.fromString(carSortAlgorithmType);
            return carSortAlgorithmFactory.getCarSortAlgorithm(sortAlgorithmType).getSimilarCars(car);
        } catch (Exception e) {
            logger.error(String.format("Error occurred while getting similar cars for car: %s with error : %s", carName, e.getMessage()));
            throw e;
        }
    }


    @Override
    public List<Car> compareCars(String carName, List<String> comparisonCars) {

        if(comparisonCars.size() > 3) {
            throw new BadArgumentException(String.format("Comparison cars cannot be more than 3. Input size: %s",
                    comparisonCars.size()));
        }
        List<Car> comparisonList = fetchComparisonInputs(carName, comparisonCars);
        return comparisonList;
    }

    @Override
    public Map<String, List<FieldToValueMap>> getCarComparisonTable(String carName, List<String> comparisonCars) {
        try {
            List<Car> comparisonList = compareCars(carName, comparisonCars);
            return getComparisonTable(comparisonList, Car.class);
        } catch (Exception e) {
            logger.error(String.format("Error occurred while getting comparison table for car: %s with error : %s", carName, e.getMessage()));
            throw e;
        }
    }

    @Override
    public Map<String, List<FieldToValueMap>> getCarDifferenceTable(String carName, List<String> comparisonCars) {
        try {
            List<Car> comparisonList = compareCars(carName, comparisonCars);
            return getDifferenceTable(comparisonList, Car.class);
        } catch (Exception e) {
            logger.error(String.format("Error occurred while getting difference table for car: %s with error : %s", carName, e.getMessage()));
            throw e;
        }
    }

    private List<Car> fetchComparisonInputs(String carName, List<String> comparisonCars) {
        List<Car> carList = new ArrayList<>();
        carList.add(validateAndGetCarNameIfPresent(carName));
        for (String car : comparisonCars) {
            carList.add(validateAndGetCarNameIfPresent(car));
        }
        return carList;
    }


    private Car validateAndGetCarNameIfPresent(String carName) {
        if (carName == null || carName.isEmpty()) {
            throw new BadArgumentException("Car name cannot be empty.");
        }
        Car car = carDao.getCar(carName);
        if (car == null) {
            throw new CarNotFoundException(String.format("Car with name %s not found.", carName));
        }
        return car;
    }

}