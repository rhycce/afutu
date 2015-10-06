package com.vangh.backbone.dzorwulu.datatype;

import com.google.gson.annotations.SerializedName;
import com.vangh.backbone.dzorwulu.utils.ElasticsearchDocument;

import java.sql.Timestamp;

/**
 * Created by Janet on 10/5/2015.
 */
public class StatusLog implements ElasticsearchDocument{
    @SerializedName("id")private String documentId;
    private String comment;
    private Timestamp timestamp;
    private int facilityId;
    private int loggedBy;
    private int packageiD;

    public String getDocuemntID() {
        return this.documentId;
    }


}
