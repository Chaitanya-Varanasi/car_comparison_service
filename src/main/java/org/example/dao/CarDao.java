package org.example.dao;

import org.example.model.Car;

import java.util.List;

public interface CarDao {
    void addCar(Car car);
    Car getCar(String carName);
    List<Car> getAllCars();
}