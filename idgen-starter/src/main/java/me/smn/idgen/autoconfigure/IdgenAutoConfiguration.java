package me.smn.idgen.autoconfigure;

import me.smn.idgen.CacheCounter;
import me.smn.idgen.ICache;
import me.smn.idgen.TypeIdGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass(TypeIdGenerator.class)
@ConditionalOnMissingBean(TypeIdGenerator.class)
@ConditionalOnBean(ICache.class)
@EnableConfigurationProperties(IdgenProperties.class)
public class IdgenAutoConfiguration {

    private IdgenProperties idgenProperties;
    private boolean runOnce;

    public IdgenAutoConfiguration(IdgenProperties idgenProperties) {
        this.idgenProperties = idgenProperties;
    }

    @Bean
    public TypeIdGenerator buildTypeIdGenerator(ICache cache) throws Exception {
        if (runOnce) return null;
        runOnce = true;

        CacheCounter counter = new CacheCounter(idgenProperties.getKeyPrefix(), cache);
        counter.initCounter(idgenProperties.getObjType(), idgenProperties.getDc(), 0);

        return new TypeIdGenerator(counter, this.idgenProperties);

    }
}
