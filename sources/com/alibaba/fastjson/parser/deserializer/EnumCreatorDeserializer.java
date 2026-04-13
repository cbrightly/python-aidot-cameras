package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class EnumCreatorDeserializer implements ObjectDeserializer {
    private final Method creator;
    private final Class paramType;

    public EnumCreatorDeserializer(Method creator2) {
        this.creator = creator2;
        this.paramType = creator2.getParameterTypes()[0];
    }

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Object arg = parser.parseObject(this.paramType);
        try {
            return this.creator.invoke((Object) null, new Object[]{arg});
        } catch (IllegalAccessException e) {
            throw new JSONException("parse enum error", e);
        } catch (InvocationTargetException e2) {
            throw new JSONException("parse enum error", e2);
        }
    }

    public int getFastMatchToken() {
        return 0;
    }
}
