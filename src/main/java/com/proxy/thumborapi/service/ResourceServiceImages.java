package com.proxy.thumborapi.service;

import com.proxy.thumborapi.config.SourcesServiceProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class ResourceServiceImages implements ResourceService {

    private final SourcesServiceProperties sourcesServiceProperties;
    private String source;

    public ResourceServiceImages(SourcesServiceProperties sourcesServiceProperties) {
        this.sourcesServiceProperties = sourcesServiceProperties;
    }

    @Override
    public List<String> listObjects(String element, String elementName) {
        return null;
    }

    @Override
    public byte[] readImage(String imageId) throws IOException {
        return null;
    }

    @Override
    public URL generateTemporaryUrlForImage(String imageId) {
        try {
            return new URL(this.sourcesServiceProperties.getUrl(this.source).concat(imageId));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private void setSource(String source) {
        this.source = source;
    }

    @Override
    public String sizeBySourceAndAlias(String source, String alias) {

        if (!this.sourcesServiceProperties.containsKey(source)) {
            return null;
        }

        this.setSource(source);

        if (!this.sourcesServiceProperties.hasSize(source, alias)) {
            return null;
        }

        return this.sourcesServiceProperties.getSize(source, alias);

    }
}
