package com.amazonaws.services.kinesisvideo.model;

import java.util.HashMap;
import java.util.Map;

public enum ComparisonOperator {
    BEGINS_WITH("BEGINS_WITH");
    
    private static final Map<String, ComparisonOperator> enumMap = null;
    private String value;

    static {
        ComparisonOperator comparisonOperator;
        HashMap hashMap = new HashMap();
        enumMap = hashMap;
        hashMap.put("BEGINS_WITH", comparisonOperator);
    }

    private ComparisonOperator(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static ComparisonOperator fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, ComparisonOperator> map = enumMap;
        if (map.containsKey(value2)) {
            return map.get(value2);
        }
        throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
    }
}
