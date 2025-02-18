package com.bondarchuk.SpringBootBetAPI.businessLogic;

public enum Car {
    FERRARI("Ferrari"),
    BMW("BMW"),
    AUDI("Audi"),
    HONDA("Honda");

    private final String name;

    Car(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

