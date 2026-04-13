package com.amazonaws.services.kinesisvideo.model;

import java.util.HashMap;
import java.util.Map;

public enum ChannelType {
    SINGLE_MASTER("SINGLE_MASTER");
    
    private static final Map<String, ChannelType> enumMap = null;
    private String value;

    static {
        ChannelType channelType;
        HashMap hashMap = new HashMap();
        enumMap = hashMap;
        hashMap.put("SINGLE_MASTER", channelType);
    }

    private ChannelType(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static ChannelType fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, ChannelType> map = enumMap;
        if (map.containsKey(value2)) {
            return map.get(value2);
        }
        throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
    }
}
