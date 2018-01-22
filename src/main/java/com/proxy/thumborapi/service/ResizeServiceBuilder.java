package com.proxy.thumborapi.service;

import com.proxy.thumborapi.config.ThumborServiceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResizeServiceBuilder {

    private final ResourceServiceImages resourceServiceImages;
    private final ThumborServiceProperties thumborServiceProperties;

    @Autowired
    public ResizeServiceBuilder(
            ResourceServiceImages resourceServiceImages,
            ThumborServiceProperties thumborServiceProperties
    ) {
        this.resourceServiceImages = resourceServiceImages;
        this.thumborServiceProperties = thumborServiceProperties;
    }

    public ResizeService buildImages() {
        return new ResizeService(
                this.resourceServiceImages,
                this.thumborServiceProperties
        );
    }
}
