package com.alibaba.fastjson.serializer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public class JSONObjectCodec implements ObjectSerializer {
    public static final JSONObjectCodec instance = new JSONObjectCodec();

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        SerializeWriter out = serializer.out;
        MapSerializer mapSerializer = MapSerializer.instance;
        try {
            Field mapField = object.getClass().getDeclaredField("map");
            if (Modifier.isPrivate(mapField.getModifiers())) {
                mapField.setAccessible(true);
            }
            mapSerializer.write(serializer, mapField.get(object), fieldName, fieldType, features);
        } catch (Exception e) {
            out.writeNull();
        }
    }
}
