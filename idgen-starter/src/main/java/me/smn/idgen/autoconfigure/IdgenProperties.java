package me.smn.idgen.autoconfigure;

import me.smn.idgen.IGeneratorConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("idgen")
public class IdgenProperties implements IGeneratorConfig {
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
