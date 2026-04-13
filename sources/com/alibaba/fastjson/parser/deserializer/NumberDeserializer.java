package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.math.BigDecimal;

public class NumberDeserializer implements ObjectDeserializer {
    public static final NumberDeserializer instance = new NumberDeserializer();

    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        Class<Byte> cls = Byte.class;
        Class<Short> cls2 = Short.class;
        Class<Double> cls3 = Double.class;
        JSONLexer lexer = parser.lexer;
        if (lexer.token() == 2) {
            if (clazz == Double.TYPE || clazz == cls3) {
                String val = lexer.numberString();
                lexer.nextToken(16);
                return Double.valueOf(Double.parseDouble(val));
            }
            long val2 = lexer.longValue();
            lexer.nextToken(16);
            if (clazz == Short.TYPE || clazz == cls2) {
                if (val2 <= 32767 && val2 >= -32768) {
                    return Short.valueOf((short) ((int) val2));
                }
                throw new JSONException("short overflow : " + val2);
            } else if (clazz == Byte.TYPE || clazz == cls) {
                if (val2 <= 127 && val2 >= -128) {
                    return Byte.valueOf((byte) ((int) val2));
                }
                throw new JSONException("short overflow : " + val2);
            } else if (val2 < -2147483648L || val2 > 2147483647L) {
                return Long.valueOf(val2);
            } else {
                return Integer.valueOf((int) val2);
            }
        } else if (lexer.token() == 3) {
            if (clazz == Double.TYPE || clazz == cls3) {
                String val3 = lexer.numberString();
                lexer.nextToken(16);
                return Double.valueOf(Double.parseDouble(val3));
            } else if (clazz == Short.TYPE || clazz == cls2) {
                BigDecimal val4 = lexer.decimalValue();
                lexer.nextToken(16);
                return Short.valueOf(TypeUtils.shortValue(val4));
            } else if (clazz == Byte.TYPE || clazz == cls) {
                BigDecimal val5 = lexer.decimalValue();
                lexer.nextToken(16);
                return Byte.valueOf(TypeUtils.byteValue(val5));
            } else {
                BigDecimal val6 = lexer.decimalValue();
                lexer.nextToken(16);
                if (lexer.isEnabled(Feature.UseBigDecimal)) {
                    return val6;
                }
                return Double.valueOf(val6.doubleValue());
            }
        } else if (lexer.token() != 18 || !"NaN".equals(lexer.stringVal())) {
            Object value = parser.parse();
            if (value == null) {
                return null;
            }
            if (clazz == Double.TYPE || clazz == cls3) {
                try {
                    return TypeUtils.castToDouble(value);
                } catch (Exception ex) {
                    throw new JSONException("parseDouble error, field : " + fieldName, ex);
                }
            } else if (clazz == Short.TYPE || clazz == cls2) {
                try {
                    return TypeUtils.castToShort(value);
                } catch (Exception ex2) {
                    throw new JSONException("parseShort error, field : " + fieldName, ex2);
                }
            } else if (clazz != Byte.TYPE && clazz != cls) {
                return TypeUtils.castToBigDecimal(value);
            } else {
                try {
                    return TypeUtils.castToByte(value);
                } catch (Exception ex3) {
                    throw new JSONException("parseByte error, field : " + fieldName, ex3);
                }
            }
        } else {
            lexer.nextToken();
            if (clazz == cls3) {
                return Double.valueOf(Double.NaN);
            }
            if (clazz == Float.class) {
                return Float.valueOf(Float.NaN);
            }
            return null;
        }
    }

    public int getFastMatchToken() {
        return 2;
    }
}
