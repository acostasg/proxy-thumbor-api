package com.proxy.thumborapi.service;

import java.util.HashMap;

public class SourcePropertiesMother {
    public SourcePropertiesMother() {
    }

    HashMap<String, HashMap<String, Object>> getProperties() {
        HashMap<String, HashMap<String, Object>> properties = new HashMap<>();
        HashMap<String, Object> images = new HashMap<String, Object>();
        HashMap<String, String> sizes = new HashMap<String, String>();

        sizes.put("test", "150x150");

        images.put("url-service", "http://test");
        images.put("sizes", sizes);

        properties.put("es", images);
        return properties;
    }
}