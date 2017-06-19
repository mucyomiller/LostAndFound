package com.regismutangana.lostandfound.Model;

/**
 * Created by miller on 6/19/17.
 */

public class Automobile {
    private String Name;
    private String ModelName;
    private String PlateNumber;
    private String Location;

    public Automobile() {
    }

    public Automobile(String name, String modelName, String plateNumber, String location) {
        Name = name;
        ModelName = modelName;
        PlateNumber = plateNumber;
        Location = location;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getModelName() {
        return ModelName;
    }

    public void setModelName(String modelName) {
        ModelName = modelName;
    }

    public String getPlateNumber() {
        return PlateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        PlateNumber = plateNumber;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
