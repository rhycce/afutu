package com.vangh.backbone.dzorwulu.datatype;

/**
 * Created by Janet on 10/6/2015.
 */
public class Messenger {
    private int id;
    private String firstname;
    private String lastname;
    private int branchId;
    private int trackerID;
    private String phone;
    private String email;

    public Messenger(int id, String firstname, String lastname, int branchId, int trackerID, String phone, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.branchId = branchId;
        this.trackerID = trackerID;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public void setTrackerID(int trackerID) {
        this.trackerID = trackerID;
    }

    public int getTrackerId(){
        return trackerID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
