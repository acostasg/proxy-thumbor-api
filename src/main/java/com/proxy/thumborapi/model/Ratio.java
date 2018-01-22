package com.proxy.thumborapi.model;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public enum Ratio {
    /**
     * Preserve width
     */
    PRESERVE_W("0x1", 0, 0),
    /**
     * Preserve height
     */
    PRESERVE_H("1x0", 0, 0),
    SQUARE("1x1", 1, 1),
    WIDE("16x9", 16, 9),
    XWIDE("21x9", 21, 9),
    WIDE_V("9x16", 9, 16),
    XWIDE_V("9x21", 21, 9);

    private static Map<String, Ratio> ratios = new HashMap<>();
    private final String key;
    private final int widthRatio;
    private final int heightRatio;

    public static Ratio fromKey(String key) {
        if (!ratios.containsKey(key)) {
            for (Ratio ratio : values()) {
                if (ratio.key.equals(key)) {
                    ratios.put(key, ratio);
                    break;
                }
            }
        }
        return ratios.get(key);
    }

    public String getKey() {
        return key;
    }

    public int getWidthRatio() {
        return widthRatio;
    }

    public int getHeightRatio() {
        return heightRatio;
    }


}
