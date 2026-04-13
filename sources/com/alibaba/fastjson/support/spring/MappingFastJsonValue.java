package com.alibaba.fastjson.support.spring;

import com.alibaba.fastjson.serializer.JSONSerializable;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.lang.reflect.Type;

@Deprecated
public class MappingFastJsonValue implements JSONSerializable {
    private static final int BrowserSecureMask = SerializerFeature.BrowserSecure.mask;
    private static final String SECURITY_PREFIX = "/**/";
    private String jsonpFunction;
    private Object value;

    public MappingFastJsonValue(Object value2) {
        this.value = value2;
    }

    public void setValue(Object value2) {
        this.value = value2;
    }

    public Object getValue() {
        return this.value;
    }

    public void setJsonpFunction(String functionName) {
        this.jsonpFunction = functionName;
    }

    public String getJsonpFunction() {
        return this.jsonpFunction;
    }

    public void write(JSONSerializer serializer, Object fieldName, Type fieldType, int features) {
        SerializeWriter writer = serializer.out;
        if (this.jsonpFunction == null) {
            serializer.write(this.value);
            return;
        }
        int i = BrowserSecureMask;
        if ((features & i) != 0 || writer.isEnabled(i)) {
            writer.write(SECURITY_PREFIX);
        }
        writer.write(this.jsonpFunction);
        writer.write(40);
        serializer.write(this.value);
        writer.write(41);
    }
}
