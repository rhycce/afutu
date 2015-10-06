package com.vangh.backbone.dzorwulu.connector;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by janet on 10/5/15.
 */
public class ElasticSearchConnector {
    private static final Logger LOGGER = Logger.getLogger(ElasticSearchConnector.class.getName());
    private Properties properties;
    private Client client;

    public ElasticSearchConnector(Properties properties) {
        this.properties = properties;
    }

    public void init(){
        try {
            Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", properties.getProperty("ELASTICSEARC_CLUSTER_NAME"))
                    .put("client.transport.sniff", true)
                    .build();
            client = TransportClient.builder()
                    .settings(settings)
                    .build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(properties.getProperty("ELASTICSEARCH_HOST")), Integer.valueOf(properties.getProperty("ELASTICSEARCH_PORT"))));
        } catch (UnknownHostException e) {
            LOGGER.severe(e.getMessage());
        }
    }
}
