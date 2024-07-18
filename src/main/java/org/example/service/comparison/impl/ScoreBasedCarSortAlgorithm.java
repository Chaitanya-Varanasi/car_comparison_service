package org.example.service.comparison.impl;

import org.example.constants.Beans;
import org.example.constants.CarSortAlgorithmType;
import org.example.dao.CarDao;
import org.example.model.Car;
import org.example.model.CarComparisonRequest;
import org.example.service.comparison.CarSortAlgorithm;
import org.example.util.async.AsyncTaskExecutor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class ScoreBasedCarSortAlgorithm extends AsyncTaskExecutor<CarComparisonRequest, Double> implements CarSortAlgorithm {

    private CarDao carDao;
    private ExecutorService executorService; // Adjust thread pool size as needed


    public ScoreBasedCarSortAlgorithm(@Qualifier(Beans.IN_MEMORY_CAR_DAO) CarDao carDao) {
        this.carDao = carDao;
        executorService = Executors.newFixedThreadPool(10);
    }

//    @Override
//    public List<Car> getSimilarCars(Car selectedCar) {
//        List<Car> suggestions = new ArrayList<>();
//
//        // Create a map to store the similarity scores for each car
//        Map<Car, Double> similarityScores = new HashMap<>();
//
//        // Calculate similarity scores based on various features
//        for (Car car : carDao.getAllCars()) {
//
//            if(car.getName().equals(selectedCar.getName())) {
//                continue;
//            }
//
//            double score = 0;
//
//            // Make and Model
//            if (car.getMake().equals(selectedCar.getMake()) && car.getModel().equals(selectedCar.getModel())) {
//                score += 2; // Higher weight for exact match
//            } else if (car.getMake().equals(selectedCar.getMake())) {
//                score += 1; // Some weight for matching make
//            }
//
//            // Year
//            if (car.getYear() == selectedCar.getYear()) {
//                score += 1;
//            }
//
//            // Horsepower (consider a range)
//            int hpDifference = Math.abs(car.getHorsepower() - selectedCar.getHorsepower());
//            score+= (double) 2 /(hpDifference%10);
//
//            // Fuel Efficiency (consider a range)
//            // ... (similar logic for fuel efficiency)
//            int fuelDifference = Math.abs(car.getFuelEfficiency() - selectedCar.getFuelEfficiency());
//            score+= (double) 2 /fuelDifference;
//
//            similarityScores.put(car, score);
//        }
//
//        // Sort cars by similarity score in descending order
//        List<Map.Entry<Car, Double>> sortedScores = similarityScores.entrySet().stream()
//                .sorted(Map.Entry.<Car, Double>comparingByValue().reversed())
//                .collect(Collectors.toList());
//
//        // Add the top 10 similar cars to the suggestions list
//        for (int i = 0; i < 10 && i < sortedScores.size(); i++) {
//            suggestions.add(sortedScores.get(i).getKey());
//        }
//
//        return suggestions;
//    }

    @Override
    public List<Car> getSimilarCars(Car selectedCar) {
        List<Car> suggestions = new ArrayList<>();

        // Create a map to store the similarity scores for each car
        Map<Car, Double> similarityScores = new HashMap<>();

        List<CarComparisonRequest> carComparisonInput = carDao.getAllCars().stream()
                        .filter(car -> !car.getName().equals(selectedCar.getName()))
                .map(car -> {
                    return new CarComparisonRequest(car, selectedCar);
                    })
                                .collect(Collectors.toList());


        Map<CarComparisonRequest, Double>  output = compute(carComparisonInput,
                this::calculateSimilarityScore, executorService);

        output.forEach((request, score) -> similarityScores.put(request.getCar(), score));

        // Sort cars by similarity score in descending order
        List<Map.Entry<Car, Double>> sortedScores = similarityScores.entrySet().stream()
                .sorted(Map.Entry.<Car, Double>comparingByValue().reversed())
                .collect(Collectors.toList());

        // Add the top 10 similar cars to the suggestions list
        for (int i = 0; i < 10 && i < sortedScores.size(); i++) {
            suggestions.add(sortedScores.get(i).getKey());
        }

        return suggestions;
    }

    private Double calculateSimilarityScore(CarComparisonRequest carComparisonRequest) {
        double score = 0;
        Car car = carComparisonRequest.getCar();
        Car selectedCar = carComparisonRequest.getTargetCar();
        // Make and Model
        if (car.getMake().equals(selectedCar.getMake()) && car.getModel().equals(selectedCar.getModel())) {
            score += 2; // Higher weight for exact match
        } else if (car.getMake().equals(selectedCar.getMake())) {
            score += 1; // Some weight for matching make
        }

        // Year
        if (car.getYear() == selectedCar.getYear()) {
            score += 1;
        }

        // Horsepower (consider a range)
        int hpDifference = Math.abs(car.getHorsepower() - selectedCar.getHorsepower());
        score += 2 / ((double) hpDifference / 10);

        // Fuel Efficiency (consider a range)
        int fuelDifference = Math.abs(car.getFuelEfficiency() - selectedCar.getFuelEfficiency());
        score += (double) 2 / fuelDifference;

        return score;
    }

    @Override
    public CarSortAlgorithmType getType() {
        return CarSortAlgorithmType.SCORE_BASED;
    }

}