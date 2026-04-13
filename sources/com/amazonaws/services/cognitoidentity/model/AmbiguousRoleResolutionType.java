package com.amazonaws.services.cognitoidentity.model;

import java.util.HashMap;
import java.util.Map;

public enum AmbiguousRoleResolutionType {
    AuthenticatedRole("AuthenticatedRole"),
    Deny("Deny");
    
    private static final Map<String, AmbiguousRoleResolutionType> enumMap = null;
    private String value;

    static {
        AmbiguousRoleResolutionType ambiguousRoleResolutionType;
        AmbiguousRoleResolutionType ambiguousRoleResolutionType2;
        HashMap hashMap = new HashMap();
        enumMap = hashMap;
        hashMap.put("AuthenticatedRole", ambiguousRoleResolutionType);
        hashMap.put("Deny", ambiguousRoleResolutionType2);
    }

    private AmbiguousRoleResolutionType(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static AmbiguousRoleResolutionType fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, AmbiguousRoleResolutionType> map = enumMap;
        if (map.containsKey(value2)) {
            return map.get(value2);
        }
        throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
    }
}
