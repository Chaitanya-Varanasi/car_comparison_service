package org.example.constants;

public enum CarSortAlgorithmType {
    ML,
    SCORE_BASED,
    ATTRIBUTE_MATCH_BASED;

    public static CarSortAlgorithmType fromString(String carSortAlgorithmTypeString) {
        for (CarSortAlgorithmType carSortAlgorithmType : CarSortAlgorithmType.values()) {
            if (carSortAlgorithmType.name().equalsIgnoreCase(carSortAlgorithmTypeString)) {
                return carSortAlgorithmType;
            }
        }
        throw new IllegalArgumentException("Invalid CarSortAlgorithmType: " + carSortAlgorithmTypeString);
    }
}