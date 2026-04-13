package com.amazonaws.services.kinesisvideo.model;

import java.util.HashMap;
import java.util.Map;

public enum Status {
    CREATING("CREATING"),
    ACTIVE("ACTIVE"),
    UPDATING("UPDATING"),
    DELETING("DELETING");
    
    private static final Map<String, Status> enumMap = null;
    private String value;

    static {
        Status status;
        Status status2;
        Status status3;
        Status status4;
        HashMap hashMap = new HashMap();
        enumMap = hashMap;
        hashMap.put("CREATING", status);
        hashMap.put("ACTIVE", status2);
        hashMap.put("UPDATING", status3);
        hashMap.put("DELETING", status4);
    }

    private Status(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static Status fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, Status> map = enumMap;
        if (map.containsKey(value2)) {
            return map.get(value2);
        }
        throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
    }
}
