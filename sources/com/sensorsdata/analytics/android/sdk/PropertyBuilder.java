package com.sensorsdata.analytics.android.sdk;

import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;

public final class PropertyBuilder {
    private static final String TAG = "SA.PropertyBuilder";
    private final LinkedHashMap<String, Object> innerPropertyMap = new LinkedHashMap<>();

    private PropertyBuilder() {
    }

    public static PropertyBuilder newInstance() {
        return new PropertyBuilder();
    }

    public PropertyBuilder append(String key, Object value) {
        this.innerPropertyMap.put(key, value);
        return this;
    }

    public PropertyBuilder append(Map<String, Object> propertyMap) {
        if (propertyMap != null && !propertyMap.isEmpty()) {
            this.innerPropertyMap.putAll(propertyMap);
        }
        return this;
    }

    public PropertyBuilder append(Object... keyValuePairs) {
        if (keyValuePairs == null || keyValuePairs.length <= 1) {
            SALog.i(TAG, "The key value pair is incorrect.");
            return this;
        }
        int index = 0;
        while (index < keyValuePairs.length) {
            String str = keyValuePairs[index];
            int index2 = index + 1;
            if (index2 >= keyValuePairs.length) {
                SALog.i(TAG, "this element key[index= " + index2 + "] will be ignored, because no element can pair with it. ");
                return this;
            }
            Object valueObj = keyValuePairs[index2];
            if (str instanceof String) {
                this.innerPropertyMap.put(str, valueObj);
            } else {
                SALog.i(TAG, "this element key[index= " + index2 + "] is not a String, the method will ignore the element and the next element. ");
            }
            index = index2 + 1;
        }
        return this;
    }

    public JSONObject toJSONObject() {
        this.innerPropertyMap.remove((Object) null);
        if (this.innerPropertyMap.isEmpty()) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        for (String key : this.innerPropertyMap.keySet()) {
            try {
                jsonObject.put(key, this.innerPropertyMap.get(key));
            } catch (Exception ex) {
                SALog.printStackTrace(ex);
            }
        }
        return jsonObject;
    }

    public int size() {
        return this.innerPropertyMap.size();
    }

    public Object remove(String key) {
        return this.innerPropertyMap.remove(key);
    }

    public void clear() {
        this.innerPropertyMap.clear();
    }
}
