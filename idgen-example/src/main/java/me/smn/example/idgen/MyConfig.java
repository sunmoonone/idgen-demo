package me.smn.example.idgen;

import me.smn.idgen.IGeneratorConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="idgen")
public class MyConfig implements IGeneratorConfig {
    private String keyPrefix;

    private Long objType;

    private Long dc;

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public Long getObjType() {
        return objType;
    }

    public void setObjType(Long objType) {
        this.objType = objType;
    }

    public Long getDc() {
        return dc;
    }

    public void setDc(Long dc) {
        this.dc = dc;
    }
}
