package com.proxy.thumborapi.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        ThumborServiceProperties.class,
        SourcesServiceProperties.class
})
public class ThumborApiConfig {
}
