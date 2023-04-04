package com.bizu.querycenter.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Component;

@Component
public class MongoDBClient {

    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public MongoDBClient() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb+srv://alimuratkuslu:alis2001@movieapi.urlccoc.mongodb.net/test"))
                .retryWrites(true)
                .build();

        this.mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase("QueryCenter");
    }

    public MongoDatabase getDatabase() {
        return this.database;
    }

    public MongoClient getClient() {
        return this.mongoClient;
    }
}
