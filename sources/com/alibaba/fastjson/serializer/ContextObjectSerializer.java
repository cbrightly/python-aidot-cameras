package com.alibaba.fastjson.serializer;

public interface ContextObjectSerializer extends ObjectSerializer {
    void write(JSONSerializer jSONSerializer, Object obj, BeanContext beanContext);
}
