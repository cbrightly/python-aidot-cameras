package com.amazonaws.services.kinesisvideo.model;

import java.util.HashMap;
import java.util.Map;

public enum UpdateDataRetentionOperation {
    INCREASE_DATA_RETENTION("INCREASE_DATA_RETENTION"),
    DECREASE_DATA_RETENTION("DECREASE_DATA_RETENTION");
    
    private static final Map<String, UpdateDataRetentionOperation> enumMap = null;
    private String value;

    static {
        UpdateDataRetentionOperation updateDataRetentionOperation;
        UpdateDataRetentionOperation updateDataRetentionOperation2;
        HashMap hashMap = new HashMap();
        enumMap = hashMap;
        hashMap.put("INCREASE_DATA_RETENTION", updateDataRetentionOperation);
        hashMap.put("DECREASE_DATA_RETENTION", updateDataRetentionOperation2);
    }

    private UpdateDataRetentionOperation(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static UpdateDataRetentionOperation fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, UpdateDataRetentionOperation> map = enumMap;
        if (map.containsKey(value2)) {
            return map.get(value2);
        }
        throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
    }
}
