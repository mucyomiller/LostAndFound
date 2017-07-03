package com.regismutangana.lostandfound.Model;

/**
 * Created by miller on 6/19/17.
 */

public class Automobile {
    private String Name;
    private String ModelName;
    private String PlateNumber;
    private String FoundLocation;
    private String LostLocation;
    private String FounderUid;
    private String OwnerUid;

    public Automobile() {
    }


    public Automobile(String name, String modelName, String plateNumber, String foundLocation, String lostLocation, String founderUid, String ownerUid) {
        Name = name;
        ModelName = modelName;
        PlateNumber = plateNumber;
        FoundLocation = foundLocation;
        LostLocation = lostLocation;
        FounderUid = founderUid;
        OwnerUid = ownerUid;
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

    public String getFoundLocation() {
        return FoundLocation;
    }

    public void setFoundLocation(String foundLocation) {
        FoundLocation = foundLocation;
    }

    public String getLostLocation() {
        return LostLocation;
    }

    public void setLostLocation(String lostLocation) {
        LostLocation = lostLocation;
    }

    public String getFounderUid() {
        return FounderUid;
    }

    public void setFounderUid(String founderUid) {
        FounderUid = founderUid;
    }

    public String getOwnerUid() {
        return OwnerUid;
    }

    public void setOwnerUid(String ownerUid) {
        OwnerUid = ownerUid;
    }
}
