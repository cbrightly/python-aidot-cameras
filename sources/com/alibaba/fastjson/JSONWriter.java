package com.alibaba.fastjson;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.Closeable;
import java.io.Flushable;
import java.io.Writer;

public class JSONWriter implements Closeable, Flushable {
    private JSONStreamContext context;
    private JSONSerializer serializer;
    private SerializeWriter writer;

    public JSONWriter(Writer out) {
        SerializeWriter serializeWriter = new SerializeWriter(out);
        this.writer = serializeWriter;
        this.serializer = new JSONSerializer(serializeWriter);
    }

    public void config(SerializerFeature feature, boolean state) {
        this.writer.config(feature, state);
    }

    public void startObject() {
        if (this.context != null) {
            beginStructure();
        }
        this.context = new JSONStreamContext(this.context, 1001);
        this.writer.write(123);
    }

    public void endObject() {
        this.writer.write(125);
        endStructure();
    }

    public void writeKey(String key) {
        writeObject(key);
    }

    public void writeValue(Object object) {
        writeObject(object);
    }

    public void writeObject(String object) {
        beforeWrite();
        this.serializer.write(object);
        afterWrite();
    }

    public void writeObject(Object object) {
        beforeWrite();
        this.serializer.write(object);
        afterWrite();
    }

    public void startArray() {
        if (this.context != null) {
            beginStructure();
        }
        this.context = new JSONStreamContext(this.context, 1004);
        this.writer.write(91);
    }

    private void beginStructure() {
        JSONStreamContext jSONStreamContext = this.context;
        int state = jSONStreamContext.state;
        switch (jSONStreamContext.state) {
            case 1001:
            case 1004:
                return;
            case 1002:
                this.writer.write(58);
                return;
            case 1005:
                this.writer.write(44);
                return;
            default:
                throw new JSONException("illegal state : " + state);
        }
    }

    public void endArray() {
        this.writer.write(93);
        endStructure();
    }

    private void endStructure() {
        JSONStreamContext jSONStreamContext = this.context.parent;
        this.context = jSONStreamContext;
        if (jSONStreamContext != null) {
            int newState = -1;
            switch (jSONStreamContext.state) {
                case 1001:
                    newState = 1002;
                    break;
                case 1002:
                    newState = 1003;
                    break;
                case 1004:
                    newState = 1005;
                    break;
            }
            if (newState != -1) {
                jSONStreamContext.state = newState;
            }
        }
    }

    private void beforeWrite() {
        JSONStreamContext jSONStreamContext = this.context;
        if (jSONStreamContext != null) {
            switch (jSONStreamContext.state) {
                case 1002:
                    this.writer.write(58);
                    return;
                case 1003:
                    this.writer.write(44);
                    return;
                case 1005:
                    this.writer.write(44);
                    return;
                default:
                    return;
            }
        }
    }

    private void afterWrite() {
        JSONStreamContext jSONStreamContext = this.context;
        if (jSONStreamContext != null) {
            int newState = -1;
            switch (jSONStreamContext.state) {
                case 1001:
                case 1003:
                    newState = 1002;
                    break;
                case 1002:
                    newState = 1003;
                    break;
                case 1004:
                    newState = 1005;
                    break;
            }
            if (newState != -1) {
                jSONStreamContext.state = newState;
            }
        }
    }

    public void flush() {
        this.writer.flush();
    }

    public void close() {
        this.writer.close();
    }

    @Deprecated
    public void writeStartObject() {
        startObject();
    }

    @Deprecated
    public void writeEndObject() {
        endObject();
    }

    @Deprecated
    public void writeStartArray() {
        startArray();
    }

    @Deprecated
    public void writeEndArray() {
        endArray();
    }
}
