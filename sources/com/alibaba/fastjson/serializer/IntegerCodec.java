package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class IntegerCodec implements ObjectSerializer, ObjectDeserializer {
    public static IntegerCodec instance = new IntegerCodec();

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        SerializeWriter out = serializer.out;
        Number value = (Number) object;
        if (value == null) {
            out.writeNull(SerializerFeature.WriteNullNumberAsZero);
            return;
        }
        if (object instanceof Long) {
            out.writeLong(value.longValue());
        } else {
            out.writeInt(value.intValue());
        }
        if (out.isEnabled(SerializerFeature.WriteClassName)) {
            Class<?> clazz = value.getClass();
            if (clazz == Byte.class) {
                out.write(66);
            } else if (clazz == Short.class) {
                out.write(83);
            }
        }
    }

    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        Integer intObj;
        JSONLexer lexer = parser.lexer;
        int token = lexer.token();
        if (token == 8) {
            lexer.nextToken(16);
            return null;
        }
        if (token == 2) {
            try {
                int val = lexer.intValue();
                lexer.nextToken(16);
                intObj = Integer.valueOf(val);
            } catch (Exception ex) {
                String message = "parseInt error";
                if (fieldName != null) {
                    message = message + ", field : " + fieldName;
                }
                throw new JSONException(message, ex);
            }
        } else if (token == 3) {
            Integer intObj2 = Integer.valueOf(TypeUtils.intValue(lexer.decimalValue()));
            lexer.nextToken(16);
            intObj = intObj2;
        } else if (token == 12) {
            JSONObject jsonObject = new JSONObject(true);
            parser.parseObject((Map) jsonObject);
            intObj = TypeUtils.castToInt(jsonObject);
        } else {
            intObj = TypeUtils.castToInt(parser.parse());
        }
        if (clazz == AtomicInteger.class) {
            return new AtomicInteger(intObj.intValue());
        }
        return intObj;
    }

    public int getFastMatchToken() {
        return 2;
    }
}
