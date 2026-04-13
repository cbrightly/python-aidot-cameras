package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class EnumSerializer implements ObjectSerializer {
    public static final EnumSerializer instance = new EnumSerializer();
    private final Member member;

    public EnumSerializer() {
        this.member = null;
    }

    public EnumSerializer(Member member2) {
        this.member = member2;
    }

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        Object fieldValue;
        Object fieldValue2 = this.member;
        if (fieldValue2 == null) {
            serializer.out.writeEnum((Enum) object);
            return;
        }
        try {
            if (fieldValue2 instanceof Field) {
                fieldValue = ((Field) fieldValue2).get(object);
            } else {
                fieldValue = ((Method) fieldValue2).invoke(object, new Object[0]);
            }
            serializer.write(fieldValue);
        } catch (Exception e) {
            throw new JSONException("getEnumValue error", e);
        }
    }
}
