package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JavaObjectDeserializer implements ObjectDeserializer {
    public static final JavaObjectDeserializer instance = new JavaObjectDeserializer();

    /* JADX WARNING: type inference failed for: r3v2, types: [T, java.lang.Object[]] */
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            if (componentType instanceof TypeVariable) {
                componentType = ((TypeVariable) componentType).getBounds()[0];
            }
            List<Object> list = new ArrayList<>();
            parser.parseArray(componentType, (Collection) list);
            ? r3 = (Object[]) Array.newInstance(TypeUtils.getRawClass(componentType), list.size());
            list.toArray(r3);
            return r3;
        } else if (!(type instanceof Class) || type == Object.class || type == Serializable.class || type == Cloneable.class || type == Closeable.class || type == Comparable.class) {
            return parser.parse(fieldName);
        } else {
            return parser.parseObject(type);
        }
    }

    public int getFastMatchToken() {
        return 12;
    }
}
