package com.amir.inventory.configuration;

import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

@Configuration
@Profile("!test")
public class ReactiveMongoConfiguration {

    @Value("${spring.data.mongodb.host:localhost}")
    String host;
    @Value("${spring.data.mongodb.port:0}")
    int port;
    @Value("${spring.data.mongodb.database:inventory}")
    String database;

    @Value("${spring.data.mongodb.uri:}")
    private String mongoUri;

    @Bean
    public ReactiveMongoDatabaseFactory inventoryMongoDatabaseFactory() {
        if (!mongoUri.isEmpty()) {
            return new SimpleReactiveMongoDatabaseFactory(MongoClients.create(mongoUri), database);
        }
        return new SimpleReactiveMongoDatabaseFactory(MongoClients.create("mongodb://" + host + ":" + port), database);
    }
}
