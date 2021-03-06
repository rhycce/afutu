package com.vangh.backbone.dzorwulu.datatype;

import com.vangh.backbone.dzorwulu.utils.Utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by janet on 10/5/15.
 */
public class Order {
   private int id;
    private String source;
    private String destination;
    private int client;
    private int recipient;
    private String comments;
    private Utils.OrderStatus status;
    private Timestamp orderdate;
    private int assignedto;
    private Timestamp delivereddate;

    public Order(int id, String source, String destination, int client, int recipient, String comments, Utils.OrderStatus status, Timestamp orderdate, int assignedto) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.client = client;
        this.recipient = recipient;
        this.comments = comments;
        this.status = status;
        this.delivereddate = null;
        this.orderdate =  orderdate;
        this.assignedto = assignedto;
    }

    public Order(String source, String destination, int client, int recipient) {
        this.id = 0;
        this.source = source;
        this.destination = destination;
        this.client = client;
        this.recipient = recipient;
        this.comments = "";
        this.status = Utils.OrderStatus.PLACED;
        this.delivereddate = null;
        this.orderdate =  Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        this.assignedto = 0;
    }

    public void setAssignedto(int assignedto) {
        this.assignedto = assignedto;
    }

    public void setDelivereddate(Timestamp delivereddate) {
        this.delivereddate = delivereddate;
    }

    public void setStatus(Utils.OrderStatus status) {
        this.status = status;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getClient() {
        return client;
    }

    public int getRecipient() {
        return recipient;
    }

    public String getComments() {
        return comments;
    }

    public Utils.OrderStatus getStatus() {
        return status;
    }

    public Timestamp getOrderdate() {
        return orderdate;
    }

    public Timestamp getDelivereddate() {
        return delivereddate;
    }

    public int getAssignedto() {
        return assignedto;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", client=" + client +
                ", recipient=" + recipient +
                ", comments='" + comments + '\'' +
                ", status=" + status +
                ", orderdate=" + orderdate +
                ", delivereddate=" + delivereddate +
                ", assignedto=" + assignedto +
                '}';
    }

}
