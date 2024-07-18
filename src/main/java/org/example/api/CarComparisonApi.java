package org.example.api;

import org.example.model.Car;
import org.example.model.FieldToValueMap;
import org.example.service.comparison.CarComparisonService;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;
import java.util.Map;


@RestController
public class CarComparisonApi {

    @Autowired
    private CarComparisonService carComparisonService;

    @GetMapping("/api/v1/cars/{carName}/similar")
    public ResponseEntity<List<Car>> getSimilarCars(@PathVariable @NotBlank String carName) {
        List<Car> similarCars = carComparisonService.getSimilarCars(carName);
        return new ResponseEntity<>(similarCars, HttpStatus.OK);
    }

    @PostMapping("/api/v1/cars/{carName}/compare")
    public ResponseEntity<List<Car>> compareCars(@PathVariable @NotBlank String carName, @RequestBody @Valid @NotEmpty List<String> comparisonCars) {
        List<Car> comparisonList = carComparisonService.compareCars(carName, comparisonCars);
        return new ResponseEntity<>(comparisonList, HttpStatus.OK);
    }

    @PostMapping("/api/v1/cars/{carName}/comparison-table")
    public ResponseEntity<Map<String, List<FieldToValueMap>>>  getComparisonTable(@PathVariable @NotBlank String carName, @RequestBody @Valid @NotEmpty List<String> comparisonCars) {
        Map<String, List<FieldToValueMap>> comparisonTable = carComparisonService.getCarComparisonTable(carName, comparisonCars);
        return new ResponseEntity<>(comparisonTable, HttpStatus.OK);
    }

    @PostMapping("/api/v1/cars/{carName}/difference-table")
    public ResponseEntity<Map<String, List<FieldToValueMap>>> getDifferenceTable(@PathVariable @NotBlank String carName, @RequestBody @Valid @NotEmpty List<String> comparisonCars) {
        Map<String, List<FieldToValueMap>> differenceTable = carComparisonService.getCarDifferenceTable(carName, comparisonCars);
        return new ResponseEntity<>(differenceTable, HttpStatus.OK);
    }
}