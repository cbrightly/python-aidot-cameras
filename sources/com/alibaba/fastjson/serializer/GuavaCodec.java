package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class GuavaCodec implements ObjectSerializer, ObjectDeserializer {
    public static GuavaCodec instance = new GuavaCodec();

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        SerializeWriter serializeWriter = serializer.out;
        if (object instanceof Multimap) {
            serializer.write((Object) ((Multimap) object).asMap());
        }
    }

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Type rawType = type;
        if (type instanceof ParameterizedType) {
            rawType = ((ParameterizedType) type).getRawType();
        }
        if (rawType != ArrayListMultimap.class) {
            return null;
        }
        ArrayListMultimap multimap = ArrayListMultimap.create();
        for (Map.Entry entry : parser.parseObject().entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Collection) {
                multimap.putAll(entry.getKey(), (List) value);
            } else {
                multimap.put(entry.getKey(), value);
            }
        }
        return multimap;
    }

    public int getFastMatchToken() {
        return 0;
    }
}
