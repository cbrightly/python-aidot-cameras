package com.amazonaws.services.kinesisvideo.model;

import java.util.HashMap;
import java.util.Map;

public enum APIName {
    PUT_MEDIA("PUT_MEDIA"),
    GET_MEDIA("GET_MEDIA"),
    LIST_FRAGMENTS("LIST_FRAGMENTS"),
    GET_MEDIA_FOR_FRAGMENT_LIST("GET_MEDIA_FOR_FRAGMENT_LIST"),
    GET_HLS_STREAMING_SESSION_URL("GET_HLS_STREAMING_SESSION_URL"),
    GET_DASH_STREAMING_SESSION_URL("GET_DASH_STREAMING_SESSION_URL");
    
    private static final Map<String, APIName> enumMap = null;
    private String value;

    static {
        APIName aPIName;
        APIName aPIName2;
        APIName aPIName3;
        APIName aPIName4;
        APIName aPIName5;
        APIName aPIName6;
        HashMap hashMap = new HashMap();
        enumMap = hashMap;
        hashMap.put("PUT_MEDIA", aPIName);
        hashMap.put("GET_MEDIA", aPIName2);
        hashMap.put("LIST_FRAGMENTS", aPIName3);
        hashMap.put("GET_MEDIA_FOR_FRAGMENT_LIST", aPIName4);
        hashMap.put("GET_HLS_STREAMING_SESSION_URL", aPIName5);
        hashMap.put("GET_DASH_STREAMING_SESSION_URL", aPIName6);
    }

    private APIName(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static APIName fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, APIName> map = enumMap;
        if (map.containsKey(value2)) {
            return map.get(value2);
        }
        throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
    }
}
