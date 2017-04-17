package com.cydercode.inception.configuration;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.StoreConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public EnvironmentConfig environmentConfig() {
        EnvironmentConfig myEnvConfig = new EnvironmentConfig();
        myEnvConfig.setAllowCreate(true);
        return myEnvConfig;
    }

    @Bean
    public StoreConfig storeConfig() {
        StoreConfig storeConfig = new StoreConfig();
        storeConfig.setAllowCreate(true);
        return storeConfig;
    }

    @Bean
    public Environment environment(@Autowired EnvironmentConfig environmentConfig) throws DatabaseException {
        File environmentPath = new File("database");
        environmentPath.mkdirs();
        return new Environment(environmentPath, environmentConfig);

    }

    @Bean
    public EntityStore entityStore(@Autowired Environment env, @Autowired StoreConfig storeConfig) throws DatabaseException {
        return new EntityStore(env, "EntityStore", storeConfig);
    }
}
