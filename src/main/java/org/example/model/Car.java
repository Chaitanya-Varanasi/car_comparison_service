package org.example.model;

import java.util.UUID;

public class Car extends ObjectIdentifier {

    private String id;
    private String make;
    private String model;
    private int year;
    private int horsepower;
    private int fuelEfficiency;
    private String imageUrl;

    public Car(String name, String make, String model, int year, int horsepower, int fuelEfficiency, String imageUrl) {
        super(name);
        this.id = UUID.randomUUID().toString();
        this.make = make;
        this.model = model;
        this.year = year;
        this.horsepower = horsepower;
        this.fuelEfficiency = fuelEfficiency;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return getObjectIdentifier();
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public int getFuelEfficiency() {
        return fuelEfficiency;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId() {
        return id;
    }
}