package com.vangh.backbone.dzorwulu.datatype;

/**
 * Created by Janet on 10/6/2015.
 */
public class Messenger {
    private int id;
    private String firstname;
    private String lastname;
    private int branchId;

    public Messenger(int id, String firstname, String lastname, int branchId) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.branchId = branchId;
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

}
