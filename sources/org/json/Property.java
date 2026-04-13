package org.json;

import java.util.Enumeration;
import java.util.Properties;

public class Property {
    public static JSONObject toJSONObject(Properties properties) {
        JSONObject jo = new JSONObject();
        if (properties != null && !properties.isEmpty()) {
            Enumeration<?> enumProperties = properties.propertyNames();
            while (enumProperties.hasMoreElements()) {
                String name = (String) enumProperties.nextElement();
                jo.put(name, (Object) properties.getProperty(name));
            }
        }
        return jo;
    }

    public static Properties toProperties(JSONObject jo) {
        Properties properties = new Properties();
        if (jo != null) {
            for (String key : jo.keySet()) {
                Object value = jo.opt(key);
                if (!JSONObject.NULL.equals(value)) {
                    properties.put(key, value.toString());
                }
            }
        }
        return properties;
    }
}
