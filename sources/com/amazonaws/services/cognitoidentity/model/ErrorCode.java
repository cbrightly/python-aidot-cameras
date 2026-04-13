package com.amazonaws.services.cognitoidentity.model;

import java.util.HashMap;
import java.util.Map;

public enum ErrorCode {
    AccessDenied("AccessDenied"),
    InternalServerError("InternalServerError");
    
    private static final Map<String, ErrorCode> enumMap = null;
    private String value;

    static {
        ErrorCode errorCode;
        ErrorCode errorCode2;
        HashMap hashMap = new HashMap();
        enumMap = hashMap;
        hashMap.put("AccessDenied", errorCode);
        hashMap.put("InternalServerError", errorCode2);
    }

    private ErrorCode(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static ErrorCode fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, ErrorCode> map = enumMap;
        if (map.containsKey(value2)) {
            return map.get(value2);
        }
        throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
    }
}
