package com.proxy.thumborapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;

@Data
@ConfigurationProperties
public class SourcesServiceProperties {

    private static final String URL_SERVICE = "url-service";
    private static final String SIZES = "sizes";

    private HashMap<String, HashMap<String, Object>> sources;

    public Boolean containsKey(String source) {
        return this.sources.containsKey(source);
    }

    public String getUrl(String source) {
        return (String) this.sources.get(source).get(URL_SERVICE);
    }

    @SuppressWarnings("unchecked")
    public Boolean hasSize(String source, String alias) {
        HashMap<String, String> sizes = (HashMap<String, String>) this.sources.get(source).get(SIZES);
        return sizes.containsKey(alias);
    }

    @SuppressWarnings("unchecked")
    public String getSize(String source, String alias) {
        HashMap<String, String> sizes = (HashMap<String, String>) this.sources.get(source).get(SIZES);
        return sizes.get(alias);
    }

    public void setSources(HashMap<String, HashMap<String, Object>> sources) {
        this.sources = sources;
    }
}
