package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import io.netty.util.internal.StringUtil;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class ByteBufferCodec implements ObjectSerializer, ObjectDeserializer {
    public static final ByteBufferCodec instance = new ByteBufferCodec();

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        return ((ByteBufferBean) parser.parseObject(ByteBufferBean.class)).byteBuffer();
    }

    public int getFastMatchToken() {
        return 14;
    }

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        ByteBuffer byteBuf = (ByteBuffer) object;
        byte[] array = byteBuf.array();
        SerializeWriter out = serializer.out;
        out.write(123);
        out.writeFieldName("array");
        out.writeByteArray(array);
        out.writeFieldValue((char) StringUtil.COMMA, "limit", byteBuf.limit());
        out.writeFieldValue((char) StringUtil.COMMA, "position", byteBuf.position());
        out.write(125);
    }

    public static class ByteBufferBean {
        public byte[] array;
        public int limit;
        public int position;

        public ByteBuffer byteBuffer() {
            ByteBuffer buf = ByteBuffer.wrap(this.array);
            buf.limit(this.limit);
            buf.position(this.position);
            return buf;
        }
    }
}
