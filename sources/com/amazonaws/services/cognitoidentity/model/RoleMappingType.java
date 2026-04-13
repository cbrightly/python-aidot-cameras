package com.amazonaws.services.cognitoidentity.model;

import java.util.HashMap;
import java.util.Map;

public enum RoleMappingType {
    Token("Token"),
    Rules("Rules");
    
    private static final Map<String, RoleMappingType> enumMap = null;
    private String value;

    static {
        RoleMappingType roleMappingType;
        RoleMappingType roleMappingType2;
        HashMap hashMap = new HashMap();
        enumMap = hashMap;
        hashMap.put("Token", roleMappingType);
        hashMap.put("Rules", roleMappingType2);
    }

    private RoleMappingType(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static RoleMappingType fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, RoleMappingType> map = enumMap;
        if (map.containsKey(value2)) {
            return map.get(value2);
        }
        throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
    }
}
