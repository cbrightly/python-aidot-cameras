package com.alibaba.fastjson.serializer;

import com.google.maps.android.BuildConfig;
import io.netty.util.internal.StringUtil;
import java.lang.reflect.Type;

public class ArraySerializer implements ObjectSerializer {
    private final ObjectSerializer compObjectSerializer;
    private final Class<?> componentType;

    public ArraySerializer(Class<?> componentType2, ObjectSerializer compObjectSerializer2) {
        this.componentType = componentType2;
        this.compObjectSerializer = compObjectSerializer2;
    }

    public final void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        JSONSerializer jSONSerializer = serializer;
        Object obj = object;
        SerializeWriter out = jSONSerializer.out;
        if (obj == null) {
            out.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        Object[] array = (Object[]) obj;
        int size = array.length;
        SerialContext context = jSONSerializer.context;
        jSONSerializer.setContext(context, obj, fieldName, 0);
        try {
            out.append('[');
            for (int i = 0; i < size; i++) {
                if (i != 0) {
                    out.append((char) StringUtil.COMMA);
                }
                Object item = array[i];
                if (item == null) {
                    if (!out.isEnabled(SerializerFeature.WriteNullStringAsEmpty) || !(obj instanceof String[])) {
                        out.append((CharSequence) BuildConfig.TRAVIS);
                    } else {
                        out.writeString("");
                    }
                } else if (item.getClass() == this.componentType) {
                    this.compObjectSerializer.write(serializer, item, Integer.valueOf(i), (Type) null, 0);
                } else {
                    jSONSerializer.getObjectWriter(item.getClass()).write(serializer, item, Integer.valueOf(i), (Type) null, 0);
                }
            }
            out.append(']');
        } finally {
            jSONSerializer.context = context;
        }
    }
}
