package com.regismutangana.lostandfound.Model;

/**
 * Created by miller on 6/19/17.
 */

public class Gadget {
    private String Name;
    private String Type;
    private String ModelName;
    private String SerialNumber;
    private String FoundLocation;
    private String LostLocation;
    private String FounderUid;
    private String OwnerUid;

    public Gadget() {
    }

    public Gadget(String name, String type, String modelName, String serialNumber, String foundLocation, String lostLocation, String founderUid, String ownerUid) {
        Name = name;
        Type = type;
        ModelName = modelName;
        SerialNumber = serialNumber;
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
