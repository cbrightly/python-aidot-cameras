package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EnumDeserializer implements ObjectDeserializer {
    protected final Class<?> enumClass;
    protected long[] enumNameHashCodes;
    protected final Enum[] enums;
    protected final Enum[] ordinalEnums;

    public EnumDeserializer(Class<?> enumClass2) {
        String[] strArr;
        String jsonFieldName;
        Class<?> cls = enumClass2;
        this.enumClass = cls;
        this.ordinalEnums = (Enum[]) enumClass2.getEnumConstants();
        Map<Long, Enum> enumMap = new HashMap<>();
        int i = 0;
        while (true) {
            Enum[] enumArr = this.ordinalEnums;
            if (i >= enumArr.length) {
                break;
            }
            Enum e = enumArr[i];
            String name = e.name();
            JSONField jsonField = null;
            try {
                jsonField = (JSONField) TypeUtils.getAnnotation(cls.getField(name), JSONField.class);
                if (!(jsonField == null || (jsonFieldName = jsonField.name()) == null || jsonFieldName.length() <= 0)) {
                    name = jsonFieldName;
                }
            } catch (Exception e2) {
            }
            long hash = -3750763034362895579L;
            long hash_lower = -3750763034362895579L;
            for (int j = 0; j < name.length(); j++) {
                char ch = name.charAt(j);
                hash = (hash ^ ((long) ch)) * 1099511628211L;
                hash_lower = (hash_lower ^ ((long) ((ch < 'A' || ch > 'Z') ? ch : ch + ' '))) * 1099511628211L;
            }
            enumMap.put(Long.valueOf(hash), e);
            if (hash != hash_lower) {
                enumMap.put(Long.valueOf(hash_lower), e);
            }
            if (jsonField != null) {
                String[] alternateNames = jsonField.alternateNames();
                int length = alternateNames.length;
                int i2 = 0;
                while (i2 < length) {
                    String alterName = alternateNames[i2];
                    long alterNameHash = -3750763034362895579L;
                    int j2 = 0;
                    while (true) {
                        strArr = alternateNames;
                        if (j2 >= alterName.length()) {
                            break;
                        }
                        alterNameHash = (alterNameHash ^ ((long) alterName.charAt(j2))) * 1099511628211L;
                        j2++;
                        alternateNames = strArr;
                        name = name;
                        jsonField = jsonField;
                    }
                    String name2 = name;
                    JSONField jsonField2 = jsonField;
                    if (!(alterNameHash == hash || alterNameHash == hash_lower)) {
                        enumMap.put(Long.valueOf(alterNameHash), e);
                    }
                    i2++;
                    alternateNames = strArr;
                    name = name2;
                    jsonField = jsonField2;
                }
            }
            JSONField jSONField = jsonField;
            i++;
        }
        this.enumNameHashCodes = new long[enumMap.size()];
        int i3 = 0;
        for (Long h : enumMap.keySet()) {
            this.enumNameHashCodes[i3] = h.longValue();
            i3++;
        }
        Arrays.sort(this.enumNameHashCodes);
        this.enums = new Enum[this.enumNameHashCodes.length];
        int i4 = 0;
        while (true) {
            long[] jArr = this.enumNameHashCodes;
            if (i4 < jArr.length) {
                this.enums[i4] = enumMap.get(Long.valueOf(jArr[i4]));
                i4++;
            } else {
                return;
            }
        }
    }

    public Enum getEnumByHashCode(long hashCode) {
        int enumIndex;
        if (this.enums != null && (enumIndex = Arrays.binarySearch(this.enumNameHashCodes, hashCode)) >= 0) {
            return this.enums[enumIndex];
        }
        return null;
    }

    public Enum<?> valueOf(int ordinal) {
        return this.ordinalEnums[ordinal];
    }

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        try {
            JSONLexer lexer = parser.lexer;
            int token = lexer.token();
            if (token == 2) {
                int intValue = lexer.intValue();
                lexer.nextToken(16);
                if (intValue >= 0) {
                    T[] tArr = this.ordinalEnums;
                    if (intValue < tArr.length) {
                        return tArr[intValue];
                    }
                }
                throw new JSONException("parse enum " + this.enumClass.getName() + " error, value : " + intValue);
            } else if (token == 4) {
                String name = lexer.stringVal();
                lexer.nextToken(16);
                if (name.length() == 0) {
                    return null;
                }
                long hash = -3750763034362895579L;
                long hash_lower = -3750763034362895579L;
                for (int j = 0; j < name.length(); j++) {
                    char ch = name.charAt(j);
                    hash = (hash ^ ((long) ch)) * 1099511628211L;
                    hash_lower = (hash_lower ^ ((long) ((ch < 'A' || ch > 'Z') ? ch : ch + ' '))) * 1099511628211L;
                }
                Enum e = getEnumByHashCode(hash);
                if (e == null && hash_lower != hash) {
                    e = getEnumByHashCode(hash_lower);
                }
                if (e == null) {
                    if (lexer.isEnabled(Feature.ErrorOnEnumNotMatch)) {
                        throw new JSONException("not match enum value, " + this.enumClass.getName() + " : " + name);
                    }
                }
                return e;
            } else if (token == 8) {
                lexer.nextToken(16);
                return null;
            } else {
                Object value = parser.parse();
                throw new JSONException("parse enum " + this.enumClass.getName() + " error, value : " + value);
            }
        } catch (JSONException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new JSONException(e3.getMessage(), e3);
        }
    }

    public int getFastMatchToken() {
        return 2;
    }
}
