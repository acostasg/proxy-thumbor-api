package com.proxy.thumborapi.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ImageSize {

    orig(0),
    xs(100),
    s(400),
    m(800),
    l(1200),
    xl(2000);

    private final int size;

    public int getSize() {
        return size;
    }

    public String size() {
        return String.valueOf(size);
    }
}
