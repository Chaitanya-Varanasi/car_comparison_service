package org.example.model;

public class CarComparisonRequest {
    private Car car;
    private Car targetCar;

    public CarComparisonRequest(Car car, Car targetCar) {
        this.car = car;
        this.targetCar = targetCar;
    }

    public Car getCar() {
        return car;
    }

    public Car getTargetCar() {
        return targetCar;
    }
}
