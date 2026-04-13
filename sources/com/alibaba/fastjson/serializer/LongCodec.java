package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class LongCodec implements ObjectSerializer, ObjectDeserializer {
    public static LongCodec instance = new LongCodec();

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull(SerializerFeature.WriteNullNumberAsZero);
            return;
        }
        long value = ((Long) object).longValue();
        out.writeLong(value);
        if (out.isEnabled(SerializerFeature.WriteClassName) && value <= 2147483647L && value >= -2147483648L && fieldType != Long.class && fieldType != Long.TYPE) {
            out.write(76);
        }
    }

    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        Long longObject;
        JSONLexer lexer = parser.lexer;
        try {
            int token = lexer.token();
            if (token == 2) {
                long longValue = lexer.longValue();
                lexer.nextToken(16);
                longObject = Long.valueOf(longValue);
            } else if (token == 3) {
                Long longObject2 = Long.valueOf(TypeUtils.longValue(lexer.decimalValue()));
                lexer.nextToken(16);
                longObject = longObject2;
            } else {
                if (token == 12) {
                    JSONObject jsonObject = new JSONObject(true);
                    parser.parseObject((Map) jsonObject);
                    longObject = TypeUtils.castToLong(jsonObject);
                } else {
                    longObject = TypeUtils.castToLong(parser.parse());
                }
                if (longObject == null) {
                    return null;
                }
            }
            return clazz == AtomicLong.class ? new AtomicLong(longObject.longValue()) : longObject;
        } catch (Exception ex) {
            throw new JSONException("parseLong error, field : " + fieldName, ex);
        }
    }

    public int getFastMatchToken() {
        return 2;
    }
}
