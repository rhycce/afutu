package com.vangh.backbone.dzorwulu.datatype;

import java.sql.Timestamp;

/**
 * Created by Janet on 10/5/2015.
 */
public class StatusLog{
    private String comment;
    private Timestamp timestamp;
    private int facilityId;
    private int loggedBy;
    private int packageId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public int getLoggedBy() {
        return loggedBy;
    }

    public void setLoggedBy(int loggedBy) {
        this.loggedBy = loggedBy;
    }

    public StatusLog(String comment, Timestamp timestamp, int facilityId, int loggedBy, int packageId) {
        this.comment = comment;
        this.timestamp = timestamp;
        this.facilityId = facilityId;
        this.loggedBy = loggedBy;
        this.packageId = packageId;
    }


}
