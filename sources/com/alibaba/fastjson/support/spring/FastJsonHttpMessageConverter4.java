package com.alibaba.fastjson.support.spring;

import java.lang.reflect.Type;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

@Deprecated
public class FastJsonHttpMessageConverter4 extends FastJsonHttpMessageConverter {
    /* access modifiers changed from: protected */
    public boolean supports(Class<?> clazz) {
        return super.supports(clazz);
    }

    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        return super.canRead(type, contextClass, mediaType);
    }

    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return super.canWrite(type, clazz, mediaType);
    }

    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) {
        return super.read(type, contextClass, inputMessage);
    }

    public void write(Object o, Type type, MediaType contentType, HttpOutputMessage outputMessage) {
        super.write(o, type, contentType, outputMessage);
    }

    /* access modifiers changed from: protected */
    public Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) {
        return super.readInternal(clazz, inputMessage);
    }

    /* access modifiers changed from: protected */
    public void writeInternal(Object object, HttpOutputMessage outputMessage) {
        super.writeInternal(object, outputMessage);
    }
}
