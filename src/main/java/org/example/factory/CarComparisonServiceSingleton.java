package org.example.factory;

import org.example.dao.CarDao;
import org.example.service.comparison.CarComparisonService;
import org.example.service.comparison.impl.CarComparisonServiceImpl;

public class CarComparisonServiceSingleton {
    private static volatile CarComparisonService carComparisonService;

    public static CarComparisonService getInstance(CarDao carDao) {
        if (carComparisonService == null) {
            synchronized (CarComparisonServiceSingleton.class) {
                if (carComparisonService == null) {
                    carComparisonService = new CarComparisonServiceImpl(carDao, new CarSortAlgorithmFactory(), new String());
                }
            }
        }
        return carComparisonService;
    }

    public CarComparisonService getCarComparisonService() {
        return carComparisonService;
    }
}