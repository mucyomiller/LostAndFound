package com.regismutangana.lostandfound.Model;

/**
 * Created by mucyomiller on 6/19/17.
 */

public class Card {
    private String OwnerName;
    private String IdNumber;
    private String Location;

    //needed by firebase
    public Card() {
    }

    public Card(String ownerName, String idNumber, String location) {
        OwnerName = ownerName;
        IdNumber = idNumber;
        Location = location;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getIdNumber() {
        return IdNumber;
    }

    public void setIdNumber(String idNumber) {
        IdNumber = idNumber;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
