package com.example;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public final class MongoProvider implements AutoCloseable {

    private final MongoClient client;
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public MongoProvider() {
        String uri = envOrDefault(
                "MONGO_URI",
                "mongodb://admin:admin123@localhost:27017/?authSource=admin");
        String dbName = envOrDefault("MONGO_DB", "empresas_a24mda");
        String dbCol = envOrDefault("MONGO_DB", "empleado");

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .build();

        this.client = MongoClients.create(settings);
        this.database = client.getDatabase(dbName);
        this.collection = database.getCollection(dbCol);
    }

    private String envOrDefault(String key, String def) {
        String variable = System.getenv(key);
        if (variable == null || variable.isBlank()) {
            variable = def;
        }
        return variable;
    }

    @Override
    public void close() throws Exception {
        client.close();
    }

    public MongoCollection<Document> empleado() {
        return collection;
    }

}
