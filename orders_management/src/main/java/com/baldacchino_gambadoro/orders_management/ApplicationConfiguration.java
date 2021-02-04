package com.baldacchino_gambadoro.orders_management;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication(
        exclude = {
                MongoAutoConfiguration.class,
                MongoDataAutoConfiguration.class,
                MongoReactiveDataAutoConfiguration.class,
                EmbeddedMongoAutoConfiguration.class
        }
)
@AutoConfigureAfter({EmbeddedMongoAutoConfiguration.class, MongoDataAutoConfiguration.class, MongoAutoConfiguration.class})
public class ApplicationConfiguration extends AbstractMongoClientConfiguration {

    private final Environment env;

    private final String dbname;

    public ApplicationConfiguration(Environment env) {
        this.env = env;
        this.dbname = env.getRequiredProperty("MONGO_INITDB_DATABASE");
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        final String host = env.getRequiredProperty("MONGODB_HOSTNAME");
        final String port = env.getProperty("MONGODB_PORT", "27017");
        final String user = env.getRequiredProperty("MONGO_INITDB_ROOT_USERNAME");
        final String pass = env.getRequiredProperty("MONGO_INITDB_ROOT_PASSWORD");

        String s = String.format("mongodb://%s:%s@%s:%s/%s", user, pass, host, port, dbname);
        return MongoClients.create(s);
    }

    @Override
    protected String getDatabaseName() {
        return dbname;
    }
}