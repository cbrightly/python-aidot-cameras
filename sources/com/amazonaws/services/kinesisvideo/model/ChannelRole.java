package com.amazonaws.services.kinesisvideo.model;

import java.util.HashMap;
import java.util.Map;

public enum ChannelRole {
    MASTER("MASTER"),
    VIEWER("VIEWER");
    
    private static final Map<String, ChannelRole> enumMap = null;
    private String value;

    static {
        ChannelRole channelRole;
        ChannelRole channelRole2;
        HashMap hashMap = new HashMap();
        enumMap = hashMap;
        hashMap.put("MASTER", channelRole);
        hashMap.put("VIEWER", channelRole2);
    }

    private ChannelRole(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static ChannelRole fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, ChannelRole> map = enumMap;
        if (map.containsKey(value2)) {
            return map.get(value2);
        }
        throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
    }
}
