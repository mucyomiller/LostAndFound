package com.regismutangana.lostandfound.Model;

/**
 * Created by mucyomiller on 6/19/17.
 */

public class Card {
    private String OwnerName;
    private String IdNumber;
    private String FoundLocation;
    private String LostLocation;
    private String FounderUid;
    private String OwnerUid;

    //needed by firebase
    public Card() {
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

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getIdNumber() { return IdNumber;}

    public void setIdNumber(String idNumber) { IdNumber = idNumber;}
}
