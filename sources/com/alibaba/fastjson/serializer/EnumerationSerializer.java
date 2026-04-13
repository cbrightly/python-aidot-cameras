package com.alibaba.fastjson.serializer;

import io.netty.util.internal.StringUtil;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Enumeration;

public class EnumerationSerializer implements ObjectSerializer {
    public static EnumerationSerializer instance = new EnumerationSerializer();

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        Type elementType;
        JSONSerializer jSONSerializer = serializer;
        Object obj = object;
        Type type = fieldType;
        SerializeWriter out = jSONSerializer.out;
        if (obj == null) {
            out.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        if (!out.isEnabled(SerializerFeature.WriteClassName) || !(type instanceof ParameterizedType)) {
            elementType = null;
        } else {
            elementType = ((ParameterizedType) type).getActualTypeArguments()[0];
        }
        Enumeration<?> e = (Enumeration) obj;
        SerialContext context = jSONSerializer.context;
        jSONSerializer.setContext(context, obj, fieldName, 0);
        int i = 0;
        try {
            out.append('[');
            while (e.hasMoreElements()) {
                Object item = e.nextElement();
                int i2 = i + 1;
                if (i != 0) {
                    out.append((char) StringUtil.COMMA);
                }
                if (item == null) {
                    out.writeNull();
                } else {
                    jSONSerializer.getObjectWriter(item.getClass()).write(serializer, item, Integer.valueOf(i2 - 1), elementType, 0);
                }
                i = i2;
            }
            out.append(']');
        } finally {
            jSONSerializer.context = context;
        }
    }
}
