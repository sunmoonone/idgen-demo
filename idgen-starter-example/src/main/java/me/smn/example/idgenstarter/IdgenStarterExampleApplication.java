package me.smn.example.idgenstarter;

import me.smn.idgen.CacheImpl;
import me.smn.idgen.ICache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IdgenStarterExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdgenStarterExampleApplication.class, args);
    }

    @Bean
    public ICache buildCache(){
        return new CacheImpl();
    }
}
