
package com.vangh.backbone.dzorwulu.datatype;

/**
 * Created by Janet on 10/6/2015.
 */
public class Client {
    private int id;
    private String firstname;
    private String lastname;
    private String phone;
    private String comments;
    private String email;

    public Client(String firstname, String lastname, String phone, String comments, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.comments = comments;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
