package com.amazonaws.services.cognitoidentity.model;

import java.util.HashMap;
import java.util.Map;

public enum MappingRuleMatchType {
    Equals("Equals"),
    Contains("Contains"),
    StartsWith("StartsWith"),
    NotEqual("NotEqual");
    
    private static final Map<String, MappingRuleMatchType> enumMap = null;
    private String value;

    static {
        MappingRuleMatchType mappingRuleMatchType;
        MappingRuleMatchType mappingRuleMatchType2;
        MappingRuleMatchType mappingRuleMatchType3;
        MappingRuleMatchType mappingRuleMatchType4;
        HashMap hashMap = new HashMap();
        enumMap = hashMap;
        hashMap.put("Equals", mappingRuleMatchType);
        hashMap.put("Contains", mappingRuleMatchType2);
        hashMap.put("StartsWith", mappingRuleMatchType3);
        hashMap.put("NotEqual", mappingRuleMatchType4);
    }

    private MappingRuleMatchType(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static MappingRuleMatchType fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, MappingRuleMatchType> map = enumMap;
        if (map.containsKey(value2)) {
            return map.get(value2);
        }
        throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
    }
}
