package org.example.factory;

import org.example.constants.CarSortAlgorithmType;
import org.example.service.comparison.CarSortAlgorithm;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class CarSortAlgorithmFactory {

    private static final Map<CarSortAlgorithmType, CarSortAlgorithm> CAR_SORT_ALGORITHM_MAP_MAP = new EnumMap<>(CarSortAlgorithmType.class);


    @Inject
    public void initCarSortAlgorithmFactory(final List<CarSortAlgorithm> carSortAlgorithmList) {
        for (CarSortAlgorithm carSortAlgorithm : carSortAlgorithmList) {
            CAR_SORT_ALGORITHM_MAP_MAP.put(carSortAlgorithm.getType(), carSortAlgorithm);
        }
    }


    public CarSortAlgorithm getCarSortAlgorithm(CarSortAlgorithmType carSortAlgorithmType) {
        CarSortAlgorithm carSortAlgorithm = CAR_SORT_ALGORITHM_MAP_MAP.get(carSortAlgorithmType);
        if (Objects.isNull(carSortAlgorithm)) {
            throw new NotFoundException(
                    String.format("CarSortAlgorithm not found for getCarSortAlgorithm : %s", carSortAlgorithmType));
        }
        return carSortAlgorithm;
    }

}
