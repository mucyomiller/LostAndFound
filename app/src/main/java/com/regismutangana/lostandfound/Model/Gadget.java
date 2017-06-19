package com.regismutangana.lostandfound.Model;

/**
 * Created by miller on 6/19/17.
 */

public class Gadget {
    private String Name;
    private String Type;
    private String ModelName;
    private String SerialNumber;
    private String Location;

    public Gadget() {
    }

    public Gadget(String name, String type, String modelName, String serialNumber, String location) {
        Name = name;
        Type = type;
        ModelName = modelName;
        SerialNumber = serialNumber;
        Location = location;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getModelName() {
        return ModelName;
    }

    public void setModelName(String modelName) {
        ModelName = modelName;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
