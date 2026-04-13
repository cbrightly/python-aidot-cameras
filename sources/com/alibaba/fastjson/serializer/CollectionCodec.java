package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import io.netty.util.internal.StringUtil;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

public class CollectionCodec implements ObjectSerializer, ObjectDeserializer {
    public static final CollectionCodec instance = new CollectionCodec();

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        Type elementType;
        JSONSerializer jSONSerializer = serializer;
        Object obj = object;
        int i = features;
        SerializeWriter out = jSONSerializer.out;
        if (obj == null) {
            out.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        SerializerFeature serializerFeature = SerializerFeature.WriteClassName;
        if (out.isEnabled(serializerFeature) || SerializerFeature.isEnabled(i, serializerFeature)) {
            elementType = TypeUtils.getCollectionItemType(fieldType);
        } else {
            elementType = null;
        }
        Collection<?> collection = (Collection) obj;
        SerialContext context = jSONSerializer.context;
        jSONSerializer.setContext(context, obj, fieldName, 0);
        if (out.isEnabled(serializerFeature)) {
            if (HashSet.class.isAssignableFrom(collection.getClass())) {
                out.append((CharSequence) "Set");
            } else if (TreeSet.class == collection.getClass()) {
                out.append((CharSequence) "TreeSet");
            }
        }
        int i2 = 0;
        try {
            out.append('[');
            for (Object item : collection) {
                int i3 = i2 + 1;
                if (i2 != 0) {
                    out.append((char) StringUtil.COMMA);
                }
                if (item == null) {
                    out.writeNull();
                } else {
                    Class<?> clazz = item.getClass();
                    if (clazz == Integer.class) {
                        out.writeInt(((Integer) item).intValue());
                    } else if (clazz == Long.class) {
                        out.writeLong(((Long) item).longValue());
                        if (out.isEnabled(SerializerFeature.WriteClassName)) {
                            out.write(76);
                        }
                    } else {
                        ObjectSerializer itemSerializer = jSONSerializer.getObjectWriter(clazz);
                        if (!SerializerFeature.isEnabled(i, SerializerFeature.WriteClassName) || !(itemSerializer instanceof JavaBeanSerializer)) {
                            itemSerializer.write(serializer, item, Integer.valueOf(i3 - 1), elementType, features);
                        } else {
                            ObjectSerializer objectSerializer = itemSerializer;
                            ((JavaBeanSerializer) itemSerializer).writeNoneASM(serializer, item, Integer.valueOf(i3 - 1), elementType, features);
                        }
                    }
                }
                i2 = i3;
            }
            out.append(']');
        } finally {
            jSONSerializer.context = context;
        }
    }

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        if (parser.lexer.token() == 8) {
            parser.lexer.nextToken(16);
            return null;
        } else if (type == JSONArray.class) {
            JSONArray array = new JSONArray();
            parser.parseArray((Collection) array);
            return array;
        } else {
            Collection list = TypeUtils.createCollection(type);
            parser.parseArray(TypeUtils.getCollectionItemType(type), list, fieldName);
            return list;
        }
    }

    public int getFastMatchToken() {
        return 14;
    }
}
