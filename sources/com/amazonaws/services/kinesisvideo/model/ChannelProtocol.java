package com.amazonaws.services.kinesisvideo.model;

import java.util.HashMap;
import java.util.Map;

public enum ChannelProtocol {
    WSS("WSS"),
    HTTPS("HTTPS");
    
    private static final Map<String, ChannelProtocol> enumMap = null;
    private String value;

    static {
        ChannelProtocol channelProtocol;
        ChannelProtocol channelProtocol2;
        HashMap hashMap = new HashMap();
        enumMap = hashMap;
        hashMap.put("WSS", channelProtocol);
        hashMap.put("HTTPS", channelProtocol2);
    }

    private ChannelProtocol(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static ChannelProtocol fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, ChannelProtocol> map = enumMap;
        if (map.containsKey(value2)) {
            return map.get(value2);
        }
        throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
    }
}
