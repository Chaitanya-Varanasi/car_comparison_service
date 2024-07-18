package org.example.dao.impl;

import org.example.constants.Beans;
import org.example.dao.CarDao;
import org.example.model.Car;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component(Beans.IN_MEMORY_CAR_DAO)
public class InMemoryCarDao implements CarDao {

    private Map<String, Car> carMap;

    public InMemoryCarDao() {
        carMap = new ConcurrentHashMap<>();
        this.addCar(new Car("Honda Civic", "Honda", "Civic", 2023, 180, 28, "https://example.com/civic.jpg"));
        this.addCar(new Car("Honda Civic V2", "Honda", "Civic", 2024, 220, 30, "https://example.com/civic2.jpg"));
        this.addCar(new Car("Honda Civic V3", "Honda", "Civic", 2025, 280, 32, "https://example.com/civic3.jpg"));

        this.addCar(new Car("Toyota Corolla", "Toyota", "Corolla", 2023, 169, 30, "https://example.com/corolla.jpg"));
        this.addCar(new Car("Hyundai Elantra", "Hyundai", "Elantra", 2023, 147, 33, "https://example.com/elantra.jpg"));
        this.addCar(new Car("Ford Focus", "Ford", "Focus", 2023, 181, 29, "https://example.com/focus.jpg"));
        this.addCar(new Car("Mazda3", "Mazda", "3", 2023, 186, 32, "https://example.com/mazda3.jpg"));
        this.addCar(new Car("Kia Forte", "Kia", "Forte", 2023, 147, 31, "https://example.com/forte.jpg"));
        this.addCar(new Car("Chevrolet Cruze", "Chevrolet", "Cruze", 2023, 153, 30, "https://example.com/cruze.jpg"));
        this.addCar(new Car("Nissan Sentra", "Nissan", "Sentra", 2023, 141, 30, "https://example.com/sentra.jpg"));
        this.addCar(new Car("Volkswagen Jetta", "Volkswagen", "Jetta", 2023, 148, 30, "https://example.com/jetta.jpg"));
        this.addCar(new Car("Subaru Impreza", "Subaru", "Impreza", 2023, 152, 32, "https://example.com/impreza.jpg"));
    }

    @Override
    public void addCar(Car car) {
        carMap.put(car.getName(), car);
    }

    @Override
    public Car getCar(String carName) {
        return carMap.get(carName);
    }

    @Override
    public List<Car> getAllCars() {
        return new ArrayList<>(carMap.values());
    }
}
