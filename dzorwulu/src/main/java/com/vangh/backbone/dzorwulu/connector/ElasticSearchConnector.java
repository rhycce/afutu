package com.vangh.backbone.dzorwulu.connector;

import com.google.gson.Gson;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthStatus;
import org.elasticsearch.action.admin.cluster.health.ClusterIndexHealth;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.indices.IndexAlreadyExistsException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by janet on 10/5/15.
 */
public class ElasticSearchConnector {
    private static final Logger LOGGER = Logger.getLogger(ElasticSearchConnector.class.getName());
    private Properties properties;
    private Client client;
    private CreateIndexRequestBuilder createIndexRequestBuilder;
    private String indexName;
    private String doctype;

    public ElasticSearchConnector(Properties properties) {
        this.properties = properties;
    }

    public void init(){
        try {
            Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", properties.getProperty("ELASTICSEARCH_CLUSTER_NAME"))
                    .put("client.transport.sniff", true)
                    .build();
            client = TransportClient.builder()
                    .settings(settings)
                    .build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(properties.getProperty("ELASTICSEARCH_HOST")), Integer.valueOf(properties.getProperty("ELASTICSEARCH_PORT"))));
                    LOGGER.info("Node stated. Connected to cluster: " + properties.getProperty("ELASTICSEARCH_CLUSTER_NAME"));
        } catch (UnknownHostException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    public void specifyIndex(String index){
        try{
            indexName = properties.getProperty("index");
            createIndexRequestBuilder = client.admin().indices().prepareCreate(indexName);
            createIndexRequestBuilder.execute().actionGet();
            String defaultMapping = properties.getProperty(indexName + "_mapping");
            doctype = properties.getProperty(indexName + "_doctype");
            if((defaultMapping != null)){
                String sourcMapping = readJsonDefinition(defaultMapping);
                if(sourcMapping != null){
                    PutMappingRequestBuilder mappingRequestBuilder = client.admin().indices()
                            .preparePutMapping(indexName)
                            .setType(doctype);
                    mappingRequestBuilder.setSource(sourcMapping);
                    PutMappingResponse response = mappingRequestBuilder.execute().actionGet();
                    if (!response.isAcknowledged()) {
                        LOGGER.warning("Could not define mapping for type [" + indexName + "]/[" + doctype + "].");
                    } else {
                        LOGGER.info("Mapping definition for [" + indexName+ "]/[" + doctype + "] succesfully created.");
                    }
                }
            }
        }catch(IndexAlreadyExistsException e) {
            LOGGER.log(Level.INFO, "Found index. " + indexName + " already exists.", e.getMessage());
        }
        printClusterStatus();
    }

    public void printClusterStatus() {
        ClusterHealthResponse result = client.admin().cluster().health(new ClusterHealthRequest(indexName)).actionGet();
        LOGGER.log(Level.INFO, "Cluster name: " + result.getClusterName());
        LOGGER.log(Level.INFO, "Active primary shards: " + result.getActivePrimaryShards());
        LOGGER.log(Level.INFO, "Active shards: " + result.getActiveShards());
        LOGGER.log(Level.INFO, "Relocating shards: " + result.getRelocatingShards());
        LOGGER.log(Level.INFO, "Unassigned shards: " + result.getUnassignedShards());
        LOGGER.log(Level.INFO, "Number of nodes: " + result.getNumberOfNodes());
        LOGGER.log(Level.INFO, "Number of data nodes " + result.getNumberOfDataNodes());
        for(Map.Entry<String, ClusterIndexHealth> entry: result.getIndices().entrySet())
            LOGGER.info( entry.getKey() + ": \t" + entry.getValue().getStatus().toString() );
    }

    private String readJsonDefinition(String mappingFile) {
        StringBuffer mapping = new StringBuffer();
        File file = new File(mappingFile);
        try {
            if (!file.exists())  {
                LOGGER.log(Level.SEVERE, "Mapping definition json file could not be located. Creating index with generic mapping");
                return null;
            }else{
                BufferedReader reader = new BufferedReader(new FileReader(file)) ;
                String line;
                while((line=reader.readLine()) != null)
                    mapping.append(line);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not read contents of mapping file. \n" + e.getMessage());

        }
        return mapping.toString();
    }

    public boolean checkNodeStatus() {
        if(client.admin().cluster().health(new ClusterHealthRequest(indexName)).actionGet().getStatus().equals(ClusterHealthStatus.RED))
            return false;
        else
            return true;
    }

    public boolean addNewDoc(Object document){
        IndexResponse response = client.prepareIndex(indexName, doctype)
                .setSource(new Gson().toJson(document))
                .execute()
                .actionGet();
        return response.isCreated();
    }
}
