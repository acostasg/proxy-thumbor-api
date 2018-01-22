package com.proxy.thumborapi.thumbor;

import com.proxy.thumborapi.config.ThumborServiceProperties;
import feign.Feign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ThumborHealthIndicator implements HealthIndicator {

    private final ThumborResizeService thumborResizeService;

    public ThumborHealthIndicator(ThumborServiceProperties thumborServiceProps) {
        thumborResizeService =
                Feign.builder().target(
                        ThumborResizeService.class,
                        thumborServiceProps.getServiceUrl());
    }


    @Override
    public Health health() {
        String health;
        try {
            health = thumborResizeService.health();
            if ("WORKING".equals(health)) {
                return Health.up().build();
            }
        } catch (Exception e) {
            log.error("Thumbor healthcheck failed!", e);
            health = e.getMessage();
        }

        return Health.down().withDetail("Error message", health).build();
    }

}