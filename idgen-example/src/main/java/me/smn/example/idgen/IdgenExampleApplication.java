package me.smn.example.idgen;

import me.smn.idgen.CacheCounter;
import me.smn.idgen.CacheImpl;
import me.smn.idgen.ICache;
import me.smn.idgen.TypeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IdgenExampleApplication {
    @Autowired
    MyConfig config;

    public static void main(String[] args) {
        SpringApplication.run(IdgenExampleApplication.class, args);
    }

    @Bean
    public ICache buildCache(){
        return new CacheImpl();
    }

    @Bean
    public TypeIdGenerator createIdGenerator(ICache cache) {

        CacheCounter counter = new CacheCounter(config.getKeyPrefix(),cache);
        counter.initCounter(config.getObjType(),config.getDc(),0);

        return new TypeIdGenerator(counter);
    }

}
