package com.amazonaws.services.kinesisvideosignaling.model;

import java.util.HashMap;
import java.util.Map;

public enum Service {
    TURN("TURN");
    
    private static final Map<String, Service> enumMap = null;
    private String value;

    static {
        Service service;
        HashMap hashMap = new HashMap();
        enumMap = hashMap;
        hashMap.put("TURN", service);
    }

    private Service(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static Service fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, Service> map = enumMap;
        if (map.containsKey(value2)) {
            return map.get(value2);
        }
        throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
    }
}
