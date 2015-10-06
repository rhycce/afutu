package com.vangh.backbone.dzorwulu.dao;

import com.vangh.backbone.dzorwulu.connector.MysqlConnector;

import java.util.Properties;

/**
 * Created by janet on 10/5/15.
 */
public class PackageDao {
    private final Properties properties;
    private MysqlConnector connector;

    public PackageDao(Properties properties){
        this.properties = properties;
        connector = new MysqlConnector(this.properties);
    }

}
