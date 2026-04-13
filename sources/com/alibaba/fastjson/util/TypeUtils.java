package com.alibaba.fastjson.util;

import androidx.exifinterface.media.ExifInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.EnumDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.CalendarCodec;
import com.alibaba.fastjson.serializer.SerializeBeanInfo;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.maps.android.BuildConfig;
import com.meituan.robust.Constants;
import com.sensorsdata.analytics.android.sdk.util.TimeUtils;
import com.tencent.bugly.Bugly;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.AccessControlException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeUtils {
    private static final Pattern NUMBER_WITH_TRAILING_ZEROS_PATTERN = Pattern.compile("\\.0*$");
    private static volatile boolean classXmlAccessorType_error = false;
    private static volatile Class class_Clob = null;
    private static volatile boolean class_Clob_error = false;
    private static Class<? extends Annotation> class_JacksonCreator = null;
    private static boolean class_JacksonCreator_error = false;
    private static Class<? extends Annotation> class_ManyToMany = null;
    private static boolean class_ManyToMany_error = false;
    private static Class<? extends Annotation> class_OneToMany = null;
    private static boolean class_OneToMany_error = false;
    private static volatile Class class_XmlAccessType = null;
    private static volatile Class class_XmlAccessorType = null;
    private static Class class_deque;
    public static boolean compatibleWithFieldName;
    public static boolean compatibleWithJavaBean;
    private static volatile Field field_XmlAccessType_FIELD = null;
    private static volatile Object field_XmlAccessType_FIELD_VALUE = null;
    private static volatile Map<Class, String[]> kotlinIgnores;
    private static volatile boolean kotlinIgnores_error;
    private static volatile boolean kotlin_class_klass_error;
    private static volatile boolean kotlin_error;
    private static volatile Constructor kotlin_kclass_constructor;
    private static volatile Method kotlin_kclass_getConstructors;
    private static volatile Method kotlin_kfunction_getParameters;
    private static volatile Method kotlin_kparameter_getName;
    private static volatile Class kotlin_metadata;
    private static volatile boolean kotlin_metadata_error;
    private static ConcurrentMap<String, Class<?>> mappings = new ConcurrentHashMap(256, 0.75f, 1);
    private static Method method_HibernateIsInitialized = null;
    private static boolean method_HibernateIsInitialized_error = false;
    private static volatile Method method_XmlAccessorType_value = null;
    private static Class<?> optionalClass;
    private static boolean optionalClassInited = false;
    private static Method oracleDateMethod;
    private static boolean oracleDateMethodInited = false;
    private static Method oracleTimestampMethod;
    private static boolean oracleTimestampMethodInited = false;
    private static Class<?> pathClass;
    private static boolean pathClass_error = false;
    private static boolean setAccessibleEnable = true;
    private static Class<? extends Annotation> transientClass;
    private static boolean transientClassInited = false;

    static {
        compatibleWithJavaBean = false;
        compatibleWithFieldName = false;
        class_deque = null;
        try {
            compatibleWithJavaBean = "true".equals(IOUtils.getStringProperty(IOUtils.FASTJSON_COMPATIBLEWITHJAVABEAN));
            compatibleWithFieldName = "true".equals(IOUtils.getStringProperty(IOUtils.FASTJSON_COMPATIBLEWITHFIELDNAME));
        } catch (Throwable th) {
        }
        try {
            class_deque = Class.forName("java.util.Deque");
        } catch (Throwable th2) {
        }
        addBaseClassMappings();
    }

    public static boolean isXmlField(Class clazz) {
        Annotation annotation;
        if (class_XmlAccessorType == null && !classXmlAccessorType_error) {
            try {
                class_XmlAccessorType = Class.forName("javax.xml.bind.annotation.XmlAccessorType");
            } catch (Throwable th) {
                classXmlAccessorType_error = true;
            }
        }
        if (class_XmlAccessorType == null || (annotation = getAnnotation((Class<?>) clazz, class_XmlAccessorType)) == null) {
            return false;
        }
        if (method_XmlAccessorType_value == null && !classXmlAccessorType_error) {
            try {
                method_XmlAccessorType_value = class_XmlAccessorType.getMethod("value", new Class[0]);
            } catch (Throwable th2) {
                classXmlAccessorType_error = true;
            }
        }
        if (method_XmlAccessorType_value == null) {
            return false;
        }
        Object value = null;
        if (!classXmlAccessorType_error) {
            try {
                value = method_XmlAccessorType_value.invoke(annotation, new Object[0]);
            } catch (Throwable th3) {
                classXmlAccessorType_error = true;
            }
        }
        if (value == null) {
            return false;
        }
        if (class_XmlAccessType == null && !classXmlAccessorType_error) {
            try {
                class_XmlAccessType = Class.forName("javax.xml.bind.annotation.XmlAccessType");
                field_XmlAccessType_FIELD = class_XmlAccessType.getField("FIELD");
                field_XmlAccessType_FIELD_VALUE = field_XmlAccessType_FIELD.get((Object) null);
            } catch (Throwable th4) {
                classXmlAccessorType_error = true;
            }
        }
        if (value == field_XmlAccessType_FIELD_VALUE) {
            return true;
        }
        return false;
    }

    public static Annotation getXmlAccessorType(Class clazz) {
        if (class_XmlAccessorType == null && !classXmlAccessorType_error) {
            try {
                class_XmlAccessorType = Class.forName("javax.xml.bind.annotation.XmlAccessorType");
            } catch (Throwable th) {
                classXmlAccessorType_error = true;
            }
        }
        if (class_XmlAccessorType == null) {
            return null;
        }
        return getAnnotation((Class<?>) clazz, class_XmlAccessorType);
    }

    public static boolean isClob(Class clazz) {
        if (class_Clob == null && !class_Clob_error) {
            try {
                class_Clob = Class.forName("java.sql.Clob");
            } catch (Throwable th) {
                class_Clob_error = true;
            }
        }
        if (class_Clob == null) {
            return false;
        }
        return class_Clob.isAssignableFrom(clazz);
    }

    public static String castToString(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public static Byte castToByte(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return Byte.valueOf(byteValue((BigDecimal) value));
        }
        if (value instanceof Number) {
            return Byte.valueOf(((Number) value).byteValue());
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || BuildConfig.TRAVIS.equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            return Byte.valueOf(Byte.parseByte(strVal));
        } else if (value instanceof Boolean) {
            return Byte.valueOf(((Boolean) value).booleanValue() ? (byte) 1 : 0);
        } else {
            throw new JSONException("can not cast to byte, value : " + value);
        }
    }

    public static Character castToChar(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Character) {
            return (Character) value;
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return null;
            }
            if (strVal.length() == 1) {
                return Character.valueOf(strVal.charAt(0));
            }
            throw new JSONException("can not cast to char, value : " + value);
        }
        throw new JSONException("can not cast to char, value : " + value);
    }

    public static Short castToShort(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return Short.valueOf(shortValue((BigDecimal) value));
        }
        if (value instanceof Number) {
            return Short.valueOf(((Number) value).shortValue());
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || BuildConfig.TRAVIS.equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            return Short.valueOf(Short.parseShort(strVal));
        } else if (value instanceof Boolean) {
            return Short.valueOf(((Boolean) value).booleanValue() ? (short) 1 : 0);
        } else {
            throw new JSONException("can not cast to short, value : " + value);
        }
    }

    public static BigDecimal castToBigDecimal(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Float) {
            if (Float.isNaN(((Float) value).floatValue()) || Float.isInfinite(((Float) value).floatValue())) {
                return null;
            }
        } else if (value instanceof Double) {
            if (Double.isNaN(((Double) value).doubleValue()) || Double.isInfinite(((Double) value).doubleValue())) {
                return null;
            }
        } else if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else {
            if (value instanceof BigInteger) {
                return new BigDecimal((BigInteger) value);
            }
            if ((value instanceof Map) && ((Map) value).size() == 0) {
                return null;
            }
        }
        String strVal = value.toString();
        if (strVal.length() == 0 || strVal.equalsIgnoreCase(BuildConfig.TRAVIS)) {
            return null;
        }
        if (strVal.length() <= 65535) {
            return new BigDecimal(strVal);
        }
        throw new JSONException("decimal overflow");
    }

    public static BigInteger castToBigInteger(Object value) {
        int scale;
        if (value == null) {
            return null;
        }
        if (value instanceof Float) {
            Float floatValue = (Float) value;
            if (Float.isNaN(floatValue.floatValue()) || Float.isInfinite(floatValue.floatValue())) {
                return null;
            }
            return BigInteger.valueOf(floatValue.longValue());
        } else if (value instanceof Double) {
            Double doubleValue = (Double) value;
            if (Double.isNaN(doubleValue.doubleValue()) || Double.isInfinite(doubleValue.doubleValue())) {
                return null;
            }
            return BigInteger.valueOf(doubleValue.longValue());
        } else if (value instanceof BigInteger) {
            return (BigInteger) value;
        } else {
            if ((value instanceof BigDecimal) && (scale = ((BigDecimal) value).scale()) > -1000 && scale < 1000) {
                return ((BigDecimal) value).toBigInteger();
            }
            String strVal = value.toString();
            if (strVal.length() == 0 || strVal.equalsIgnoreCase(BuildConfig.TRAVIS)) {
                return null;
            }
            if (strVal.length() <= 65535) {
                return new BigInteger(strVal);
            }
            throw new JSONException("decimal overflow");
        }
    }

    public static Float castToFloat(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return Float.valueOf(((Number) value).floatValue());
        }
        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0 || BuildConfig.TRAVIS.equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            if (strVal.indexOf(44) != -1) {
                strVal = strVal.replaceAll(",", "");
            }
            return Float.valueOf(Float.parseFloat(strVal));
        } else if (value instanceof Boolean) {
            return Float.valueOf(((Boolean) value).booleanValue() ? 1.0f : 0.0f);
        } else {
            throw new JSONException("can not cast to float, value : " + value);
        }
    }

    public static Double castToDouble(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return Double.valueOf(((Number) value).doubleValue());
        }
        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0 || BuildConfig.TRAVIS.equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            if (strVal.indexOf(44) != -1) {
                strVal = strVal.replaceAll(",", "");
            }
            return Double.valueOf(Double.parseDouble(strVal));
        } else if (value instanceof Boolean) {
            return Double.valueOf(((Boolean) value).booleanValue() ? 1.0d : 0.0d);
        } else {
            throw new JSONException("can not cast to double, value : " + value);
        }
    }

    public static Date castToDate(Object value) {
        return castToDate(value, (String) null);
    }

    public static Date castToDate(Object value, String format) {
        if (value == null) {
            return null;
        }
        if (value instanceof Date) {
            return (Date) value;
        }
        if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        }
        long longValue = -1;
        if (value instanceof BigDecimal) {
            return new Date(longValue((BigDecimal) value));
        }
        if (value instanceof Number) {
            long longValue2 = ((Number) value).longValue();
            if ("unixtime".equals(format)) {
                longValue2 *= 1000;
            }
            return new Date(longValue2);
        }
        if (value instanceof String) {
            String strVal = (String) value;
            JSONScanner dateLexer = new JSONScanner(strVal);
            try {
                if (dateLexer.scanISO8601DateIfMatch(false)) {
                    return dateLexer.getCalendar().getTime();
                }
                dateLexer.close();
                if (strVal.startsWith("/Date(") && strVal.endsWith(")/")) {
                    strVal = strVal.substring(6, strVal.length() - 2);
                }
                if (strVal.indexOf(45) > 0 || strVal.indexOf(43) > 0 || format != null) {
                    if (format == null) {
                        int len = strVal.length();
                        if (len == JSON.DEFFAULT_DATE_FORMAT.length() || (len == 22 && JSON.DEFFAULT_DATE_FORMAT.equals("yyyyMMddHHmmssSSSZ"))) {
                            format = JSON.DEFFAULT_DATE_FORMAT;
                        } else if (len == 10) {
                            format = TimeUtils.YYYY_MM_DD;
                        } else if (len == "yyyy-MM-dd HH:mm:ss".length()) {
                            format = "yyyy-MM-dd HH:mm:ss";
                        } else if (len == 29 && strVal.charAt(26) == ':' && strVal.charAt(28) == '0') {
                            format = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
                        } else if (len == 23 && strVal.charAt(19) == ',') {
                            format = "yyyy-MM-dd HH:mm:ss,SSS";
                        } else {
                            format = "yyyy-MM-dd HH:mm:ss.SSS";
                        }
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat(format, JSON.defaultLocale);
                    dateFormat.setTimeZone(JSON.defaultTimeZone);
                    try {
                        return dateFormat.parse(strVal);
                    } catch (ParseException e) {
                        throw new JSONException("can not cast to Date, value : " + strVal);
                    }
                } else if (strVal.length() == 0) {
                    return null;
                } else {
                    longValue = Long.parseLong(strVal);
                }
            } finally {
                dateLexer.close();
            }
        }
        if (longValue != -1) {
            return new Date(longValue);
        }
        Class<?> clazz = value.getClass();
        if ("oracle.sql.TIMESTAMP".equals(clazz.getName())) {
            if (oracleTimestampMethod == null && !oracleTimestampMethodInited) {
                try {
                    oracleTimestampMethod = clazz.getMethod("toJdbc", new Class[0]);
                } catch (NoSuchMethodException e2) {
                } catch (Throwable th) {
                    oracleTimestampMethodInited = true;
                    throw th;
                }
                oracleTimestampMethodInited = true;
            }
            try {
                return (Date) oracleTimestampMethod.invoke(value, new Object[0]);
            } catch (Exception e3) {
                throw new JSONException("can not cast oracle.sql.TIMESTAMP to Date", e3);
            }
        } else if ("oracle.sql.DATE".equals(clazz.getName())) {
            if (oracleDateMethod == null && !oracleDateMethodInited) {
                try {
                    oracleDateMethod = clazz.getMethod("toJdbc", new Class[0]);
                } catch (NoSuchMethodException e4) {
                } catch (Throwable th2) {
                    oracleDateMethodInited = true;
                    throw th2;
                }
                oracleDateMethodInited = true;
            }
            try {
                return (Date) oracleDateMethod.invoke(value, new Object[0]);
            } catch (Exception e5) {
                throw new JSONException("can not cast oracle.sql.DATE to Date", e5);
            }
        } else {
            throw new JSONException("can not cast to Date, value : " + value);
        }
    }

    public static java.sql.Date castToSqlDate(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof java.sql.Date) {
            return (java.sql.Date) value;
        }
        if (value instanceof Date) {
            return new java.sql.Date(((Date) value).getTime());
        }
        if (value instanceof Calendar) {
            return new java.sql.Date(((Calendar) value).getTimeInMillis());
        }
        long longValue = 0;
        if (value instanceof BigDecimal) {
            longValue = longValue((BigDecimal) value);
        } else if (value instanceof Number) {
            longValue = ((Number) value).longValue();
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || BuildConfig.TRAVIS.equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            if (isNumber(strVal)) {
                longValue = Long.parseLong(strVal);
            } else {
                JSONScanner scanner = new JSONScanner(strVal);
                if (scanner.scanISO8601DateIfMatch(false)) {
                    longValue = scanner.getCalendar().getTime().getTime();
                } else {
                    throw new JSONException("can not cast to Timestamp, value : " + strVal);
                }
            }
        }
        if (longValue > 0) {
            return new java.sql.Date(longValue);
        }
        throw new JSONException("can not cast to Date, value : " + value);
    }

    public static long longExtractValue(Number number) {
        if (number instanceof BigDecimal) {
            return ((BigDecimal) number).longValueExact();
        }
        return number.longValue();
    }

    public static Time castToSqlTime(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Time) {
            return (Time) value;
        }
        if (value instanceof Date) {
            return new Time(((Date) value).getTime());
        }
        if (value instanceof Calendar) {
            return new Time(((Calendar) value).getTimeInMillis());
        }
        long longValue = 0;
        if (value instanceof BigDecimal) {
            longValue = longValue((BigDecimal) value);
        } else if (value instanceof Number) {
            longValue = ((Number) value).longValue();
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || BuildConfig.TRAVIS.equalsIgnoreCase(strVal)) {
                return null;
            }
            if (isNumber(strVal)) {
                longValue = Long.parseLong(strVal);
            } else {
                JSONScanner scanner = new JSONScanner(strVal);
                if (scanner.scanISO8601DateIfMatch(false)) {
                    longValue = scanner.getCalendar().getTime().getTime();
                } else {
                    throw new JSONException("can not cast to Timestamp, value : " + strVal);
                }
            }
        }
        if (longValue > 0) {
            return new Time(longValue);
        }
        throw new JSONException("can not cast to Date, value : " + value);
    }

    public static Timestamp castToTimestamp(Object value) {
        Object obj = value;
        if (obj == null) {
            return null;
        }
        if (obj instanceof Calendar) {
            return new Timestamp(((Calendar) obj).getTimeInMillis());
        }
        if (obj instanceof Timestamp) {
            return (Timestamp) obj;
        }
        if (obj instanceof Date) {
            return new Timestamp(((Date) obj).getTime());
        }
        long longValue = 0;
        if (obj instanceof BigDecimal) {
            longValue = longValue((BigDecimal) obj);
        } else if (obj instanceof Number) {
            longValue = ((Number) obj).longValue();
        }
        if (obj instanceof String) {
            String strVal = (String) obj;
            if (strVal.length() == 0 || BuildConfig.TRAVIS.equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            if (strVal.endsWith(".000000000")) {
                strVal = strVal.substring(0, strVal.length() - 10);
            } else if (strVal.endsWith(".000000")) {
                strVal = strVal.substring(0, strVal.length() - 7);
            }
            if (strVal.length() == 29 && strVal.charAt(4) == '-' && strVal.charAt(7) == '-' && strVal.charAt(10) == ' ' && strVal.charAt(13) == ':' && strVal.charAt(16) == ':' && strVal.charAt(19) == '.') {
                int year = num(strVal.charAt(0), strVal.charAt(1), strVal.charAt(2), strVal.charAt(3));
                int month = num(strVal.charAt(5), strVal.charAt(6));
                return new Timestamp(year - 1900, month - 1, num(strVal.charAt(8), strVal.charAt(9)), num(strVal.charAt(11), strVal.charAt(12)), num(strVal.charAt(14), strVal.charAt(15)), num(strVal.charAt(17), strVal.charAt(18)), num(strVal.charAt(20), strVal.charAt(21), strVal.charAt(22), strVal.charAt(23), strVal.charAt(24), strVal.charAt(25), strVal.charAt(26), strVal.charAt(27), strVal.charAt(28)));
            } else if (isNumber(strVal) != 0) {
                longValue = Long.parseLong(strVal);
            } else {
                JSONScanner scanner = new JSONScanner(strVal);
                if (scanner.scanISO8601DateIfMatch(false)) {
                    longValue = scanner.getCalendar().getTime().getTime();
                } else {
                    throw new JSONException("can not cast to Timestamp, value : " + strVal);
                }
            }
        }
        if (longValue >= 0) {
            return new Timestamp(longValue);
        }
        throw new JSONException("can not cast to Timestamp, value : " + obj);
    }

    static int num(char c0, char c1) {
        if (c0 < '0' || c0 > '9' || c1 < '0' || c1 > '9') {
            return -1;
        }
        return ((c0 - '0') * 10) + (c1 - '0');
    }

    static int num(char c0, char c1, char c2, char c3) {
        if (c0 < '0' || c0 > '9' || c1 < '0' || c1 > '9' || c2 < '0' || c2 > '9' || c3 < '0' || c3 > '9') {
            return -1;
        }
        return ((c0 - '0') * 1000) + ((c1 - '0') * 100) + ((c2 - '0') * 10) + (c3 - '0');
    }

    static int num(char c0, char c1, char c2, char c3, char c4, char c5, char c6, char c7, char c8) {
        if (c0 < '0' || c0 > '9' || c1 < '0' || c1 > '9' || c2 < '0' || c2 > '9' || c3 < '0' || c3 > '9' || c4 < '0' || c4 > '9' || c5 < '0' || c5 > '9' || c6 < '0' || c6 > '9' || c7 < '0' || c7 > '9' || c8 < '0' || c8 > '9') {
            return -1;
        }
        return ((c0 - '0') * 100000000) + ((c1 - '0') * 10000000) + ((c2 - '0') * 1000000) + ((c3 - '0') * 100000) + ((c4 - '0') * 10000) + ((c5 - '0') * 1000) + ((c6 - '0') * 100) + ((c7 - '0') * 10) + (c8 - '0');
    }

    public static boolean isNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '+' || ch == '-') {
                if (i != 0) {
                    return false;
                }
            } else if (ch < '0' || ch > '9') {
                return false;
            }
        }
        return true;
    }

    public static Long castToLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return Long.valueOf(longValue((BigDecimal) value));
        }
        if (value instanceof Number) {
            return Long.valueOf(((Number) value).longValue());
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || BuildConfig.TRAVIS.equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            if (strVal.indexOf(44) != -1) {
                strVal = strVal.replaceAll(",", "");
            }
            try {
                return Long.valueOf(Long.parseLong(strVal));
            } catch (NumberFormatException e) {
                JSONScanner dateParser = new JSONScanner(strVal);
                Calendar calendar = null;
                if (dateParser.scanISO8601DateIfMatch(false)) {
                    calendar = dateParser.getCalendar();
                }
                dateParser.close();
                if (calendar != null) {
                    return Long.valueOf(calendar.getTimeInMillis());
                }
            }
        }
        if (value instanceof Map) {
            Map map = (Map) value;
            if (map.size() == 2 && map.containsKey("andIncrement") && map.containsKey("andDecrement")) {
                Iterator iter = map.values().iterator();
                iter.next();
                return castToLong(iter.next());
            }
        }
        if (value instanceof Boolean) {
            return Long.valueOf(((Boolean) value).booleanValue() ? 1 : 0);
        }
        throw new JSONException("can not cast to long, value : " + value);
    }

    public static byte byteValue(BigDecimal decimal) {
        if (decimal == null) {
            return 0;
        }
        int scale = decimal.scale();
        if (scale < -100 || scale > 100) {
            return decimal.byteValueExact();
        }
        return decimal.byteValue();
    }

    public static short shortValue(BigDecimal decimal) {
        if (decimal == null) {
            return 0;
        }
        int scale = decimal.scale();
        if (scale < -100 || scale > 100) {
            return decimal.shortValueExact();
        }
        return decimal.shortValue();
    }

    public static int intValue(BigDecimal decimal) {
        if (decimal == null) {
            return 0;
        }
        int scale = decimal.scale();
        if (scale < -100 || scale > 100) {
            return decimal.intValueExact();
        }
        return decimal.intValue();
    }

    public static long longValue(BigDecimal decimal) {
        if (decimal == null) {
            return 0;
        }
        int scale = decimal.scale();
        if (scale < -100 || scale > 100) {
            return decimal.longValueExact();
        }
        return decimal.longValue();
    }

    public static Integer castToInt(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof BigDecimal) {
            return Integer.valueOf(intValue((BigDecimal) value));
        }
        if (value instanceof Number) {
            return Integer.valueOf(((Number) value).intValue());
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || BuildConfig.TRAVIS.equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            if (strVal.indexOf(44) != -1) {
                strVal = strVal.replaceAll(",", "");
            }
            Matcher matcher = NUMBER_WITH_TRAILING_ZEROS_PATTERN.matcher(strVal);
            if (matcher.find()) {
                strVal = matcher.replaceAll("");
            }
            return Integer.valueOf(Integer.parseInt(strVal));
        } else if (value instanceof Boolean) {
            return Integer.valueOf(((Boolean) value).booleanValue() ? 1 : 0);
        } else {
            if (value instanceof Map) {
                Map map = (Map) value;
                if (map.size() == 2 && map.containsKey("andIncrement") && map.containsKey("andDecrement")) {
                    Iterator iter = map.values().iterator();
                    iter.next();
                    return castToInt(iter.next());
                }
            }
            throw new JSONException("can not cast to int, value : " + value);
        }
    }

    public static byte[] castToBytes(Object value) {
        if (value instanceof byte[]) {
            return (byte[]) value;
        }
        if (value instanceof String) {
            return IOUtils.decodeBase64((String) value);
        }
        throw new JSONException("can not cast to byte[], value : " + value);
    }

    public static Boolean castToBoolean(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        boolean z = false;
        if (value instanceof BigDecimal) {
            if (intValue((BigDecimal) value) == 1) {
                z = true;
            }
            return Boolean.valueOf(z);
        } else if (value instanceof Number) {
            if (((Number) value).intValue() == 1) {
                z = true;
            }
            return Boolean.valueOf(z);
        } else {
            if (value instanceof String) {
                String strVal = (String) value;
                if (strVal.length() == 0 || BuildConfig.TRAVIS.equals(strVal) || "NULL".equals(strVal)) {
                    return null;
                }
                if ("true".equalsIgnoreCase(strVal) || "1".equals(strVal)) {
                    return Boolean.TRUE;
                }
                if (Bugly.SDK_IS_DEV.equalsIgnoreCase(strVal) || "0".equals(strVal)) {
                    return Boolean.FALSE;
                }
                if ("Y".equalsIgnoreCase(strVal) || ExifInterface.GPS_DIRECTION_TRUE.equals(strVal)) {
                    return Boolean.TRUE;
                }
                if ("F".equalsIgnoreCase(strVal) || "N".equals(strVal)) {
                    return Boolean.FALSE;
                }
            }
            throw new JSONException("can not cast to boolean, value : " + value);
        }
    }

    public static <T> T castToJavaBean(Object obj, Class<T> clazz) {
        return cast(obj, clazz, ParserConfig.getGlobalInstance());
    }

    public static <T> T cast(Object obj, Class<T> clazz, ParserConfig config) {
        Calendar calendar;
        if (obj == null) {
            if (clazz == Integer.TYPE) {
                return 0;
            }
            if (clazz == Long.TYPE) {
                return 0L;
            }
            if (clazz == Short.TYPE) {
                return (short) 0;
            }
            if (clazz == Byte.TYPE) {
                return (byte) 0;
            }
            if (clazz == Float.TYPE) {
                return Float.valueOf(0.0f);
            }
            if (clazz == Double.TYPE) {
                return Double.valueOf(0.0d);
            }
            if (clazz == Boolean.TYPE) {
                return Boolean.FALSE;
            }
            return null;
        } else if (clazz == null) {
            throw new IllegalArgumentException("clazz is null");
        } else if (clazz == obj.getClass()) {
            return obj;
        } else {
            if (!(obj instanceof Map)) {
                if (clazz.isArray()) {
                    if (obj instanceof Collection) {
                        Collection<Object> collection = (Collection) obj;
                        int index = 0;
                        Object array = Array.newInstance(clazz.getComponentType(), collection.size());
                        for (Object item : collection) {
                            Array.set(array, index, cast(item, clazz.getComponentType(), config));
                            index++;
                        }
                        return array;
                    } else if (clazz == byte[].class) {
                        return castToBytes(obj);
                    }
                }
                if (clazz.isAssignableFrom(obj.getClass())) {
                    return obj;
                }
                if (clazz == Boolean.TYPE || clazz == Boolean.class) {
                    return castToBoolean(obj);
                }
                if (clazz == Byte.TYPE || clazz == Byte.class) {
                    return castToByte(obj);
                }
                if (clazz == Character.TYPE || clazz == Character.class) {
                    return castToChar(obj);
                }
                if (clazz == Short.TYPE || clazz == Short.class) {
                    return castToShort(obj);
                }
                if (clazz == Integer.TYPE || clazz == Integer.class) {
                    return castToInt(obj);
                }
                if (clazz == Long.TYPE || clazz == Long.class) {
                    return castToLong(obj);
                }
                if (clazz == Float.TYPE || clazz == Float.class) {
                    return castToFloat(obj);
                }
                if (clazz == Double.TYPE || clazz == Double.class) {
                    return castToDouble(obj);
                }
                if (clazz == String.class) {
                    return castToString(obj);
                }
                if (clazz == BigDecimal.class) {
                    return castToBigDecimal(obj);
                }
                if (clazz == BigInteger.class) {
                    return castToBigInteger(obj);
                }
                if (clazz == Date.class) {
                    return castToDate(obj);
                }
                if (clazz == java.sql.Date.class) {
                    return castToSqlDate(obj);
                }
                if (clazz == Time.class) {
                    return castToSqlTime(obj);
                }
                if (clazz == Timestamp.class) {
                    return castToTimestamp(obj);
                }
                if (clazz.isEnum()) {
                    return castToEnum(obj, clazz, config);
                }
                if (Calendar.class.isAssignableFrom(clazz)) {
                    Date date = castToDate(obj);
                    if (clazz == Calendar.class) {
                        calendar = Calendar.getInstance(JSON.defaultTimeZone, JSON.defaultLocale);
                    } else {
                        try {
                            calendar = clazz.newInstance();
                        } catch (Exception e) {
                            throw new JSONException("can not cast to : " + clazz.getName(), e);
                        }
                    }
                    calendar.setTime(date);
                    return calendar;
                }
                String className = clazz.getName();
                if (className.equals("javax.xml.datatype.XMLGregorianCalendar")) {
                    Date date2 = castToDate(obj);
                    Calendar calendar2 = Calendar.getInstance(JSON.defaultTimeZone, JSON.defaultLocale);
                    calendar2.setTime(date2);
                    return CalendarCodec.instance.createXMLGregorianCalendar(calendar2);
                }
                if (obj instanceof String) {
                    String strVal = (String) obj;
                    if (strVal.length() == 0 || BuildConfig.TRAVIS.equals(strVal) || "NULL".equals(strVal)) {
                        return null;
                    }
                    if (clazz == Currency.class) {
                        return Currency.getInstance(strVal);
                    }
                    if (clazz == Locale.class) {
                        return toLocale(strVal);
                    }
                    if (className.startsWith("java.time.")) {
                        return JSON.parseObject(JSON.toJSONString(strVal), clazz);
                    }
                }
                if (config.get(clazz) != null) {
                    return JSON.parseObject(JSON.toJSONString(obj), clazz);
                }
                throw new JSONException("can not cast to : " + clazz.getName());
            } else if (clazz == Map.class) {
                return obj;
            } else {
                Map map = (Map) obj;
                if (clazz != Object.class || map.containsKey(JSON.DEFAULT_TYPE_KEY)) {
                    return castToJavaBean((Map) obj, clazz, config);
                }
                return obj;
            }
        }
    }

    public static Locale toLocale(String strVal) {
        String[] items = strVal.split("_");
        if (items.length == 1) {
            return new Locale(items[0]);
        }
        if (items.length == 2) {
            return new Locale(items[0], items[1]);
        }
        return new Locale(items[0], items[1], items[2]);
    }

    public static <T> T castToEnum(Object obj, Class<T> clazz, ParserConfig mapping) {
        try {
            if (obj instanceof String) {
                String name = (String) obj;
                if (name.length() == 0) {
                    return null;
                }
                if (mapping == null) {
                    mapping = ParserConfig.getGlobalInstance();
                }
                ObjectDeserializer deserializer = mapping.getDeserializer((Type) clazz);
                if (deserializer instanceof EnumDeserializer) {
                    return ((EnumDeserializer) deserializer).getEnumByHashCode(fnv1a_64(name));
                }
                return Enum.valueOf(clazz, name);
            }
            if (obj instanceof BigDecimal) {
                int ordinal = intValue((BigDecimal) obj);
                Object[] values = clazz.getEnumConstants();
                if (ordinal < values.length) {
                    return values[ordinal];
                }
            }
            if ((obj instanceof Number) != 0) {
                int ordinal2 = ((Number) obj).intValue();
                Object[] values2 = clazz.getEnumConstants();
                if (ordinal2 < values2.length) {
                    return values2[ordinal2];
                }
            }
            throw new JSONException("can not cast to : " + clazz.getName());
        } catch (Exception ex) {
            throw new JSONException("can not cast to : " + clazz.getName(), ex);
        }
    }

    public static <T> T cast(Object obj, Type type, ParserConfig mapping) {
        if (obj == null) {
            return null;
        }
        if (type instanceof Class) {
            return cast(obj, (Class) type, mapping);
        }
        if (type instanceof ParameterizedType) {
            return cast(obj, (ParameterizedType) type, mapping);
        }
        if (obj instanceof String) {
            String strVal = (String) obj;
            if (strVal.length() == 0 || BuildConfig.TRAVIS.equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
        }
        if (type instanceof TypeVariable) {
            return obj;
        }
        throw new JSONException("can not cast to : " + type);
    }

    public static <T> T cast(Object obj, ParameterizedType type, ParserConfig mapping) {
        Collection collection;
        Object itemValue;
        Object itemValue2;
        Type rawTye = type.getRawType();
        if (rawTye == List.class || rawTye == ArrayList.class) {
            Type itemType = type.getActualTypeArguments()[0];
            if (obj instanceof List) {
                List listObj = (List) obj;
                List arrayList = new ArrayList(listObj.size());
                for (int i = 0; i < listObj.size(); i++) {
                    Object item = listObj.get(i);
                    if (!(itemType instanceof Class)) {
                        itemValue2 = cast(item, itemType, mapping);
                    } else if (item == null || item.getClass() != JSONObject.class) {
                        itemValue2 = cast(item, (Class) itemType, mapping);
                    } else {
                        itemValue2 = ((JSONObject) item).toJavaObject((Class) itemType, mapping, 0);
                    }
                    arrayList.add(itemValue2);
                }
                return arrayList;
            }
        }
        if (rawTye == Set.class || rawTye == HashSet.class || rawTye == TreeSet.class || rawTye == Collection.class || rawTye == List.class || rawTye == ArrayList.class) {
            Type itemType2 = type.getActualTypeArguments()[0];
            if (obj instanceof Iterable) {
                if (rawTye == Set.class || rawTye == HashSet.class) {
                    collection = new HashSet();
                } else if (rawTye == TreeSet.class) {
                    collection = new TreeSet();
                } else {
                    collection = new ArrayList();
                }
                for (Object item2 : (Iterable) obj) {
                    if (!(itemType2 instanceof Class)) {
                        itemValue = cast(item2, itemType2, mapping);
                    } else if (item2 == null || item2.getClass() != JSONObject.class) {
                        itemValue = cast(item2, (Class) itemType2, mapping);
                    } else {
                        itemValue = ((JSONObject) item2).toJavaObject((Class) itemType2, mapping, 0);
                    }
                    collection.add(itemValue);
                }
                return collection;
            }
        }
        if (rawTye == Map.class || rawTye == HashMap.class) {
            Type keyType = type.getActualTypeArguments()[0];
            Type valueType = type.getActualTypeArguments()[1];
            if (obj instanceof Map) {
                Map map = new HashMap();
                for (Map.Entry entry : ((Map) obj).entrySet()) {
                    map.put(cast(entry.getKey(), keyType, mapping), cast(entry.getValue(), valueType, mapping));
                }
                return map;
            }
        }
        if ((obj instanceof String) && ((String) obj).length() == 0) {
            return null;
        }
        Type[] actualTypeArguments = type.getActualTypeArguments();
        if (actualTypeArguments.length == 1 && (type.getActualTypeArguments()[0] instanceof WildcardType)) {
            return cast(obj, rawTye, mapping);
        }
        if (rawTye == Map.Entry.class && (obj instanceof Map) && ((Map) obj).size() == 1) {
            Map.Entry entry2 = (Map.Entry) ((Map) obj).entrySet().iterator().next();
            Object entryValue = entry2.getValue();
            if (actualTypeArguments.length == 2 && (entryValue instanceof Map)) {
                entry2.setValue(cast(entryValue, actualTypeArguments[1], mapping));
            }
            return entry2;
        }
        if (rawTye instanceof Class) {
            if (mapping == null) {
                mapping = ParserConfig.global;
            }
            ObjectDeserializer deserializer = mapping.getDeserializer(rawTye);
            if (deserializer != null) {
                return deserializer.deserialze(new DefaultJSONParser(JSON.toJSONString(obj), mapping), type, (Object) null);
            }
        }
        throw new JSONException("can not cast to : " + type);
    }

    /* JADX WARNING: type inference failed for: r1v1, types: [com.alibaba.fastjson.parser.deserializer.ObjectDeserializer] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> T castToJavaBean(java.util.Map<java.lang.String, java.lang.Object> r6, java.lang.Class<T> r7, com.alibaba.fastjson.parser.ParserConfig r8) {
        /*
            java.lang.Class<java.lang.StackTraceElement> r0 = java.lang.StackTraceElement.class
            if (r7 != r0) goto L_0x003e
            java.lang.String r0 = "className"
            java.lang.Object r0 = r6.get(r0)     // Catch:{ Exception -> 0x0152 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x0152 }
            java.lang.String r1 = "methodName"
            java.lang.Object r1 = r6.get(r1)     // Catch:{ Exception -> 0x0152 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Exception -> 0x0152 }
            java.lang.String r2 = "fileName"
            java.lang.Object r2 = r6.get(r2)     // Catch:{ Exception -> 0x0152 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x0152 }
            java.lang.String r3 = "lineNumber"
            java.lang.Object r3 = r6.get(r3)     // Catch:{ Exception -> 0x0152 }
            java.lang.Number r3 = (java.lang.Number) r3     // Catch:{ Exception -> 0x0152 }
            if (r3 != 0) goto L_0x0028
            r4 = 0
            goto L_0x0038
        L_0x0028:
            boolean r4 = r3 instanceof java.math.BigDecimal     // Catch:{ Exception -> 0x0152 }
            if (r4 == 0) goto L_0x0034
            r4 = r3
            java.math.BigDecimal r4 = (java.math.BigDecimal) r4     // Catch:{ Exception -> 0x0152 }
            int r4 = r4.intValueExact()     // Catch:{ Exception -> 0x0152 }
            goto L_0x0038
        L_0x0034:
            int r4 = r3.intValue()     // Catch:{ Exception -> 0x0152 }
        L_0x0038:
            java.lang.StackTraceElement r3 = new java.lang.StackTraceElement     // Catch:{ Exception -> 0x0152 }
            r3.<init>(r0, r1, r2, r4)     // Catch:{ Exception -> 0x0152 }
            return r3
        L_0x003e:
            java.lang.String r0 = com.alibaba.fastjson.JSON.DEFAULT_TYPE_KEY     // Catch:{ Exception -> 0x0152 }
            java.lang.Object r0 = r6.get(r0)     // Catch:{ Exception -> 0x0152 }
            boolean r1 = r0 instanceof java.lang.String     // Catch:{ Exception -> 0x0152 }
            if (r1 == 0) goto L_0x0079
            r1 = r0
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Exception -> 0x0152 }
            if (r8 != 0) goto L_0x0050
            com.alibaba.fastjson.parser.ParserConfig r2 = com.alibaba.fastjson.parser.ParserConfig.global     // Catch:{ Exception -> 0x0152 }
            r8 = r2
        L_0x0050:
            r2 = 0
            java.lang.Class r2 = r8.checkAutoType(r1, r2)     // Catch:{ Exception -> 0x0152 }
            if (r2 == 0) goto L_0x0062
            boolean r3 = r2.equals(r7)     // Catch:{ Exception -> 0x0152 }
            if (r3 != 0) goto L_0x0079
            java.lang.Object r3 = castToJavaBean(r6, r2, r8)     // Catch:{ Exception -> 0x0152 }
            return r3
        L_0x0062:
            java.lang.ClassNotFoundException r3 = new java.lang.ClassNotFoundException     // Catch:{ Exception -> 0x0152 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0152 }
            r4.<init>()     // Catch:{ Exception -> 0x0152 }
            r4.append(r1)     // Catch:{ Exception -> 0x0152 }
            java.lang.String r5 = " not found"
            r4.append(r5)     // Catch:{ Exception -> 0x0152 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0152 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0152 }
            throw r3     // Catch:{ Exception -> 0x0152 }
        L_0x0079:
            boolean r0 = r7.isInterface()     // Catch:{ Exception -> 0x0152 }
            if (r0 == 0) goto L_0x00b5
            boolean r0 = r6 instanceof com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x0152 }
            if (r0 == 0) goto L_0x0087
            r0 = r6
            com.alibaba.fastjson.JSONObject r0 = (com.alibaba.fastjson.JSONObject) r0     // Catch:{ Exception -> 0x0152 }
            goto L_0x008c
        L_0x0087:
            com.alibaba.fastjson.JSONObject r0 = new com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x0152 }
            r0.<init>((java.util.Map<java.lang.String, java.lang.Object>) r6)     // Catch:{ Exception -> 0x0152 }
        L_0x008c:
            if (r8 != 0) goto L_0x0093
            com.alibaba.fastjson.parser.ParserConfig r1 = com.alibaba.fastjson.parser.ParserConfig.getGlobalInstance()     // Catch:{ Exception -> 0x0152 }
            r8 = r1
        L_0x0093:
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r1 = r8.get(r7)     // Catch:{ Exception -> 0x0152 }
            if (r1 == 0) goto L_0x00a2
            java.lang.String r2 = com.alibaba.fastjson.JSON.toJSONString(r0)     // Catch:{ Exception -> 0x0152 }
            java.lang.Object r3 = com.alibaba.fastjson.JSON.parseObject((java.lang.String) r2, r7)     // Catch:{ Exception -> 0x0152 }
            return r3
        L_0x00a2:
            java.lang.Thread r2 = java.lang.Thread.currentThread()     // Catch:{ Exception -> 0x0152 }
            java.lang.ClassLoader r2 = r2.getContextClassLoader()     // Catch:{ Exception -> 0x0152 }
            r3 = 1
            java.lang.Class[] r3 = new java.lang.Class[r3]     // Catch:{ Exception -> 0x0152 }
            r4 = 0
            r3[r4] = r7     // Catch:{ Exception -> 0x0152 }
            java.lang.Object r2 = java.lang.reflect.Proxy.newProxyInstance(r2, r3, r0)     // Catch:{ Exception -> 0x0152 }
            return r2
        L_0x00b5:
            java.lang.Class<java.util.Locale> r0 = java.util.Locale.class
            if (r7 != r0) goto L_0x00e1
            java.lang.String r0 = "language"
            java.lang.Object r0 = r6.get(r0)     // Catch:{ Exception -> 0x0152 }
            java.lang.String r1 = "country"
            java.lang.Object r1 = r6.get(r1)     // Catch:{ Exception -> 0x0152 }
            boolean r2 = r0 instanceof java.lang.String     // Catch:{ Exception -> 0x0152 }
            if (r2 == 0) goto L_0x00e1
            r2 = r0
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x0152 }
            boolean r3 = r1 instanceof java.lang.String     // Catch:{ Exception -> 0x0152 }
            if (r3 == 0) goto L_0x00d9
            r3 = r1
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ Exception -> 0x0152 }
            java.util.Locale r4 = new java.util.Locale     // Catch:{ Exception -> 0x0152 }
            r4.<init>(r2, r3)     // Catch:{ Exception -> 0x0152 }
            return r4
        L_0x00d9:
            if (r1 != 0) goto L_0x00e1
            java.util.Locale r3 = new java.util.Locale     // Catch:{ Exception -> 0x0152 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x0152 }
            return r3
        L_0x00e1:
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            if (r7 != r0) goto L_0x00ee
            boolean r0 = r6 instanceof com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x0152 }
            if (r0 == 0) goto L_0x00ee
            java.lang.String r0 = r6.toString()     // Catch:{ Exception -> 0x0152 }
            return r0
        L_0x00ee:
            java.lang.Class<com.alibaba.fastjson.JSON> r0 = com.alibaba.fastjson.JSON.class
            if (r7 != r0) goto L_0x00f7
            boolean r0 = r6 instanceof com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x0152 }
            if (r0 == 0) goto L_0x00f7
            return r6
        L_0x00f7:
            java.lang.Class<java.util.LinkedHashMap> r0 = java.util.LinkedHashMap.class
            if (r7 != r0) goto L_0x010b
            boolean r0 = r6 instanceof com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x0152 }
            if (r0 == 0) goto L_0x010b
            r0 = r6
            com.alibaba.fastjson.JSONObject r0 = (com.alibaba.fastjson.JSONObject) r0     // Catch:{ Exception -> 0x0152 }
            java.util.Map r1 = r0.getInnerMap()     // Catch:{ Exception -> 0x0152 }
            boolean r2 = r1 instanceof java.util.LinkedHashMap     // Catch:{ Exception -> 0x0152 }
            if (r2 == 0) goto L_0x010b
            return r1
        L_0x010b:
            boolean r0 = r7.isInstance(r6)     // Catch:{ Exception -> 0x0152 }
            if (r0 == 0) goto L_0x0112
            return r6
        L_0x0112:
            java.lang.Class<com.alibaba.fastjson.JSONObject> r0 = com.alibaba.fastjson.JSONObject.class
            if (r7 != r0) goto L_0x011c
            com.alibaba.fastjson.JSONObject r0 = new com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x0152 }
            r0.<init>((java.util.Map<java.lang.String, java.lang.Object>) r6)     // Catch:{ Exception -> 0x0152 }
            return r0
        L_0x011c:
            if (r8 != 0) goto L_0x0123
            com.alibaba.fastjson.parser.ParserConfig r0 = com.alibaba.fastjson.parser.ParserConfig.getGlobalInstance()     // Catch:{ Exception -> 0x0152 }
            r8 = r0
        L_0x0123:
            r0 = 0
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r1 = r8.getDeserializer((java.lang.reflect.Type) r7)     // Catch:{ Exception -> 0x0152 }
            boolean r2 = r1 instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer     // Catch:{ Exception -> 0x0152 }
            if (r2 == 0) goto L_0x0130
            r2 = r1
            com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer r2 = (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) r2     // Catch:{ Exception -> 0x0152 }
            r0 = r2
        L_0x0130:
            if (r0 == 0) goto L_0x0137
            java.lang.Object r2 = r0.createInstance((java.util.Map<java.lang.String, java.lang.Object>) r6, (com.alibaba.fastjson.parser.ParserConfig) r8)     // Catch:{ Exception -> 0x0152 }
            return r2
        L_0x0137:
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ Exception -> 0x0152 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0152 }
            r3.<init>()     // Catch:{ Exception -> 0x0152 }
            java.lang.String r4 = "can not get javaBeanDeserializer. "
            r3.append(r4)     // Catch:{ Exception -> 0x0152 }
            java.lang.String r4 = r7.getName()     // Catch:{ Exception -> 0x0152 }
            r3.append(r4)     // Catch:{ Exception -> 0x0152 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0152 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0152 }
            throw r2     // Catch:{ Exception -> 0x0152 }
        L_0x0152:
            r0 = move-exception
            com.alibaba.fastjson.JSONException r1 = new com.alibaba.fastjson.JSONException
            java.lang.String r2 = r0.getMessage()
            r1.<init>(r2, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.TypeUtils.castToJavaBean(java.util.Map, java.lang.Class, com.alibaba.fastjson.parser.ParserConfig):java.lang.Object");
    }

    private static void addBaseClassMappings() {
        Class<char[]> cls = char[].class;
        Class<boolean[]> cls2 = boolean[].class;
        Class<double[]> cls3 = double[].class;
        Class<float[]> cls4 = float[].class;
        Class<long[]> cls5 = long[].class;
        Class<int[]> cls6 = int[].class;
        Class<short[]> cls7 = short[].class;
        Class<byte[]> cls8 = byte[].class;
        mappings.put(Constants.BYTE, Byte.TYPE);
        mappings.put(Constants.SHORT, Short.TYPE);
        mappings.put(Constants.INT, Integer.TYPE);
        mappings.put(Constants.LONG, Long.TYPE);
        mappings.put("float", Float.TYPE);
        mappings.put(Constants.DOUBLE, Double.TYPE);
        mappings.put("boolean", Boolean.TYPE);
        mappings.put(Constants.CHAR, Character.TYPE);
        mappings.put("[byte", cls8);
        mappings.put("[short", cls7);
        mappings.put("[int", cls6);
        mappings.put("[long", cls5);
        mappings.put("[float", cls4);
        mappings.put("[double", cls3);
        mappings.put("[boolean", cls2);
        mappings.put("[char", cls);
        mappings.put("[B", cls8);
        mappings.put("[S", cls7);
        mappings.put("[I", cls6);
        mappings.put("[J", cls5);
        mappings.put("[F", cls4);
        mappings.put("[D", cls3);
        mappings.put("[C", cls);
        mappings.put("[Z", cls2);
        for (Class clazz : new Class[]{Object.class, Cloneable.class, loadClass("java.lang.AutoCloseable"), Exception.class, RuntimeException.class, IllegalAccessError.class, IllegalAccessException.class, IllegalArgumentException.class, IllegalMonitorStateException.class, IllegalStateException.class, IllegalThreadStateException.class, IndexOutOfBoundsException.class, InstantiationError.class, InstantiationException.class, InternalError.class, InterruptedException.class, LinkageError.class, NegativeArraySizeException.class, NoClassDefFoundError.class, NoSuchFieldError.class, NoSuchFieldException.class, NoSuchMethodError.class, NoSuchMethodException.class, NullPointerException.class, NumberFormatException.class, OutOfMemoryError.class, SecurityException.class, StackOverflowError.class, StringIndexOutOfBoundsException.class, TypeNotPresentException.class, VerifyError.class, StackTraceElement.class, HashMap.class, Hashtable.class, TreeMap.class, IdentityHashMap.class, WeakHashMap.class, LinkedHashMap.class, HashSet.class, LinkedHashSet.class, TreeSet.class, ArrayList.class, TimeUnit.class, ConcurrentHashMap.class, AtomicInteger.class, AtomicLong.class, Collections.EMPTY_MAP.getClass(), Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Number.class, String.class, BigDecimal.class, BigInteger.class, BitSet.class, Calendar.class, Date.class, Locale.class, UUID.class, Time.class, java.sql.Date.class, Timestamp.class, SimpleDateFormat.class, JSONObject.class, JSONPObject.class, JSONArray.class}) {
            if (clazz != null) {
                mappings.put(clazz.getName(), clazz);
            }
        }
    }

    public static void clearClassMapping() {
        mappings.clear();
        addBaseClassMappings();
    }

    public static void addMapping(String className, Class<?> clazz) {
        mappings.put(className, clazz);
    }

    public static Class<?> loadClass(String className) {
        return loadClass(className, (ClassLoader) null);
    }

    public static boolean isPath(Class<?> clazz) {
        if (pathClass == null && !pathClass_error) {
            try {
                pathClass = Class.forName("java.nio.file.Path");
            } catch (Throwable th) {
                pathClass_error = true;
            }
        }
        Class<?> cls = pathClass;
        if (cls != null) {
            return cls.isAssignableFrom(clazz);
        }
        return false;
    }

    public static Class<?> getClassFromMapping(String className) {
        return (Class) mappings.get(className);
    }

    public static Class<?> loadClass(String className, ClassLoader classLoader) {
        return loadClass(className, classLoader, false);
    }

    public static Class<?> loadClass(String className, ClassLoader classLoader, boolean cache) {
        if (className == null || className.length() == 0) {
            return null;
        }
        if (className.length() <= 198) {
            Class<?> clazz = (Class) mappings.get(className);
            if (clazz != null) {
                return clazz;
            }
            if (className.charAt(0) == '[') {
                return Array.newInstance(loadClass(className.substring(1), classLoader), 0).getClass();
            }
            if (className.startsWith("L") && className.endsWith(Constants.PACKNAME_END)) {
                return loadClass(className.substring(1, className.length() - 1), classLoader);
            }
            if (classLoader != null) {
                try {
                    Class<?> clazz2 = classLoader.loadClass(className);
                    if (cache) {
                        mappings.put(className, clazz2);
                    }
                    return clazz2;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            try {
                ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                if (!(contextClassLoader == null || contextClassLoader == classLoader)) {
                    Class<?> clazz3 = contextClassLoader.loadClass(className);
                    if (cache) {
                        mappings.put(className, clazz3);
                    }
                    return clazz3;
                }
            } catch (Throwable th) {
            }
            try {
                Class<?> clazz4 = Class.forName(className);
                if (cache) {
                    mappings.put(className, clazz4);
                }
                return clazz4;
            } catch (Throwable th2) {
                return clazz;
            }
        } else {
            throw new JSONException("illegal className : " + className);
        }
    }

    public static SerializeBeanInfo buildBeanInfo(Class<?> beanType, Map<String, String> aliasMap, PropertyNamingStrategy propertyNamingStrategy) {
        return buildBeanInfo(beanType, aliasMap, propertyNamingStrategy, false);
    }

    /* JADX WARNING: type inference failed for: r21v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.alibaba.fastjson.serializer.SerializeBeanInfo buildBeanInfo(java.lang.Class<?> r21, java.util.Map<java.lang.String, java.lang.String> r22, com.alibaba.fastjson.PropertyNamingStrategy r23, boolean r24) {
        /*
            r8 = r21
            r9 = r22
            java.lang.Class<com.alibaba.fastjson.annotation.JSONType> r0 = com.alibaba.fastjson.annotation.JSONType.class
            java.lang.annotation.Annotation r0 = getAnnotation((java.lang.Class<?>) r8, r0)
            r10 = r0
            com.alibaba.fastjson.annotation.JSONType r10 = (com.alibaba.fastjson.annotation.JSONType) r10
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            if (r10 == 0) goto L_0x008c
            java.lang.String[] r0 = r10.orders()
            java.lang.String r1 = r10.typeName()
            int r4 = r1.length()
            if (r4 != 0) goto L_0x0022
            r1 = 0
        L_0x0022:
            com.alibaba.fastjson.PropertyNamingStrategy r4 = r10.naming()
            com.alibaba.fastjson.PropertyNamingStrategy r5 = com.alibaba.fastjson.PropertyNamingStrategy.CamelCase
            if (r4 == r5) goto L_0x002c
            r5 = r4
            goto L_0x002e
        L_0x002c:
            r5 = r23
        L_0x002e:
            com.alibaba.fastjson.serializer.SerializerFeature[] r6 = r10.serialzeFeatures()
            int r6 = com.alibaba.fastjson.serializer.SerializerFeature.of(r6)
            java.lang.Class r7 = r21.getSuperclass()
        L_0x003a:
            if (r7 == 0) goto L_0x005b
            java.lang.Class<java.lang.Object> r11 = java.lang.Object.class
            if (r7 == r11) goto L_0x005b
            java.lang.Class<com.alibaba.fastjson.annotation.JSONType> r11 = com.alibaba.fastjson.annotation.JSONType.class
            java.lang.annotation.Annotation r11 = getAnnotation((java.lang.Class<?>) r7, r11)
            com.alibaba.fastjson.annotation.JSONType r11 = (com.alibaba.fastjson.annotation.JSONType) r11
            if (r11 != 0) goto L_0x004b
            goto L_0x005b
        L_0x004b:
            java.lang.String r2 = r11.typeKey()
            int r12 = r2.length()
            if (r12 == 0) goto L_0x0056
            goto L_0x005b
        L_0x0056:
            java.lang.Class r7 = r7.getSuperclass()
            goto L_0x003a
        L_0x005b:
            java.lang.Class[] r7 = r21.getInterfaces()
            int r11 = r7.length
            r12 = r3
        L_0x0061:
            if (r12 >= r11) goto L_0x007d
            r13 = r7[r12]
            java.lang.Class<com.alibaba.fastjson.annotation.JSONType> r14 = com.alibaba.fastjson.annotation.JSONType.class
            java.lang.annotation.Annotation r14 = getAnnotation((java.lang.Class<?>) r13, r14)
            com.alibaba.fastjson.annotation.JSONType r14 = (com.alibaba.fastjson.annotation.JSONType) r14
            if (r14 == 0) goto L_0x007a
            java.lang.String r2 = r14.typeKey()
            int r15 = r2.length()
            if (r15 == 0) goto L_0x007a
            goto L_0x007d
        L_0x007a:
            int r12 = r12 + 1
            goto L_0x0061
        L_0x007d:
            if (r2 == 0) goto L_0x0086
            int r7 = r2.length()
            if (r7 != 0) goto L_0x0086
            r2 = 0
        L_0x0086:
            r12 = r0
            r13 = r1
            r14 = r2
            r11 = r5
            r15 = r6
            goto L_0x0093
        L_0x008c:
            r6 = 0
            r11 = r23
            r12 = r0
            r13 = r1
            r14 = r2
            r15 = r6
        L_0x0093:
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r7 = r0
            com.alibaba.fastjson.parser.ParserConfig.parserAllFieldToCache(r8, r7)
            if (r24 == 0) goto L_0x00a3
            java.util.List r0 = computeGettersWithFieldBase(r8, r9, r3, r11)
            goto L_0x00af
        L_0x00a3:
            r4 = 0
            r0 = r21
            r1 = r10
            r2 = r22
            r3 = r7
            r5 = r11
            java.util.List r0 = computeGetters(r0, r1, r2, r3, r4, r5)
        L_0x00af:
            r6 = r0
            int r0 = r6.size()
            com.alibaba.fastjson.util.FieldInfo[] r5 = new com.alibaba.fastjson.util.FieldInfo[r0]
            r6.toArray(r5)
            if (r12 == 0) goto L_0x00d6
            int r0 = r12.length
            if (r0 == 0) goto L_0x00d6
            if (r24 == 0) goto L_0x00c7
            r0 = 1
            java.util.List r0 = computeGettersWithFieldBase(r8, r9, r0, r11)
            r8 = r5
            goto L_0x00d4
        L_0x00c7:
            r4 = 1
            r0 = r21
            r1 = r10
            r2 = r22
            r3 = r7
            r8 = r5
            r5 = r11
            java.util.List r0 = computeGetters(r0, r1, r2, r3, r4, r5)
        L_0x00d4:
            r5 = r0
            goto L_0x00e0
        L_0x00d6:
            r8 = r5
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>(r6)
            java.util.Collections.sort(r0)
            r5 = r0
        L_0x00e0:
            int r0 = r5.size()
            com.alibaba.fastjson.util.FieldInfo[] r0 = new com.alibaba.fastjson.util.FieldInfo[r0]
            r5.toArray(r0)
            boolean r1 = java.util.Arrays.equals(r0, r8)
            if (r1 == 0) goto L_0x00f3
            r0 = r8
            r16 = r0
            goto L_0x00f5
        L_0x00f3:
            r16 = r0
        L_0x00f5:
            com.alibaba.fastjson.serializer.SerializeBeanInfo r17 = new com.alibaba.fastjson.serializer.SerializeBeanInfo
            r0 = r17
            r1 = r21
            r2 = r10
            r3 = r13
            r4 = r14
            r18 = r5
            r5 = r15
            r19 = r6
            r6 = r8
            r20 = r7
            r7 = r16
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            return r17
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.TypeUtils.buildBeanInfo(java.lang.Class, java.util.Map, com.alibaba.fastjson.PropertyNamingStrategy, boolean):com.alibaba.fastjson.serializer.SerializeBeanInfo");
    }

    public static List<FieldInfo> computeGettersWithFieldBase(Class<?> clazz, Map<String, String> aliasMap, boolean sorted, PropertyNamingStrategy propertyNamingStrategy) {
        Map<String, FieldInfo> fieldInfoMap = new LinkedHashMap<>();
        for (Class<?> currentClass = clazz; currentClass != null; currentClass = currentClass.getSuperclass()) {
            computeFields(currentClass, aliasMap, propertyNamingStrategy, fieldInfoMap, currentClass.getDeclaredFields());
        }
        return getFieldInfos(clazz, sorted, fieldInfoMap);
    }

    public static List<FieldInfo> computeGetters(Class<?> clazz, Map<String, String> aliasMap) {
        return computeGetters(clazz, aliasMap, true);
    }

    public static List<FieldInfo> computeGetters(Class<?> clazz, Map<String, String> aliasMap, boolean sorted) {
        Map<String, Field> fieldCacheMap = new HashMap<>();
        ParserConfig.parserAllFieldToCache(clazz, fieldCacheMap);
        return computeGetters(clazz, (JSONType) getAnnotation(clazz, JSONType.class), aliasMap, fieldCacheMap, sorted, PropertyNamingStrategy.CamelCase);
    }

    /* JADX WARNING: type inference failed for: r2v8, types: [java.lang.annotation.Annotation] */
    /* JADX WARNING: type inference failed for: r2v29, types: [java.lang.annotation.Annotation] */
    /* JADX WARNING: type inference failed for: r2v47, types: [java.lang.annotation.Annotation] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:164:0x0388  */
    /* JADX WARNING: Removed duplicated region for block: B:184:0x040f  */
    /* JADX WARNING: Removed duplicated region for block: B:188:0x0428  */
    /* JADX WARNING: Removed duplicated region for block: B:189:0x042b A[PHI: r3 
      PHI: (r3v15 'propertyName' java.lang.String) = (r3v14 'propertyName' java.lang.String), (r3v19 'propertyName' java.lang.String) binds: [B:185:0x041d, B:187:0x0426] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.List<com.alibaba.fastjson.util.FieldInfo> computeGetters(java.lang.Class<?> r41, com.alibaba.fastjson.annotation.JSONType r42, java.util.Map<java.lang.String, java.lang.String> r43, java.util.Map<java.lang.String, java.lang.reflect.Field> r44, boolean r45, com.alibaba.fastjson.PropertyNamingStrategy r46) {
        /*
            r12 = r41
            r13 = r43
            r14 = r44
            r15 = r46
            java.util.LinkedHashMap r0 = new java.util.LinkedHashMap
            r0.<init>()
            r11 = r0
            boolean r16 = isKotlin(r41)
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            java.lang.reflect.Method[] r10 = r41.getMethods()
            int r9 = r10.length
            r7 = 0
        L_0x001c:
            if (r7 >= r9) goto L_0x062f
            r6 = r10[r7]
            java.lang.String r5 = r6.getName()
            r4 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            int r20 = r6.getModifiers()
            boolean r20 = java.lang.reflect.Modifier.isStatic(r20)
            if (r20 == 0) goto L_0x0044
            r22 = r1
            r28 = r3
            r34 = r7
            r35 = r9
            r20 = r10
            r1 = r11
            r29 = 0
            goto L_0x061c
        L_0x0044:
            r20 = r10
            java.lang.Class r10 = r6.getReturnType()
            java.lang.Class r8 = java.lang.Void.TYPE
            boolean r8 = r10.equals(r8)
            if (r8 == 0) goto L_0x005f
            r22 = r1
            r28 = r3
            r34 = r7
            r35 = r9
            r1 = r11
            r29 = 0
            goto L_0x061c
        L_0x005f:
            java.lang.Class[] r8 = r6.getParameterTypes()
            int r8 = r8.length
            if (r8 == 0) goto L_0x0073
            r22 = r1
            r28 = r3
            r34 = r7
            r35 = r9
            r1 = r11
            r29 = 0
            goto L_0x061c
        L_0x0073:
            java.lang.Class<java.lang.ClassLoader> r8 = java.lang.ClassLoader.class
            if (r10 == r8) goto L_0x060b
            java.lang.Class<java.io.InputStream> r8 = java.io.InputStream.class
            if (r10 == r8) goto L_0x060b
            java.lang.Class<java.io.Reader> r8 = java.io.Reader.class
            if (r10 != r8) goto L_0x008c
            r22 = r1
            r28 = r3
            r34 = r7
            r35 = r9
            r1 = r11
            r29 = 0
            goto L_0x061c
        L_0x008c:
            java.lang.String r8 = "getMetaClass"
            boolean r8 = r5.equals(r8)
            if (r8 == 0) goto L_0x00ad
            java.lang.String r8 = r10.getName()
            r22 = r1
            java.lang.String r1 = "groovy.lang.MetaClass"
            boolean r1 = r8.equals(r1)
            if (r1 == 0) goto L_0x00af
            r28 = r3
            r34 = r7
            r35 = r9
            r1 = r11
            r29 = 0
            goto L_0x061c
        L_0x00ad:
            r22 = r1
        L_0x00af:
            java.lang.String r1 = "getSuppressed"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x00ca
            java.lang.Class r1 = r6.getDeclaringClass()
            java.lang.Class<java.lang.Throwable> r8 = java.lang.Throwable.class
            if (r1 != r8) goto L_0x00ca
            r28 = r3
            r34 = r7
            r35 = r9
            r1 = r11
            r29 = 0
            goto L_0x061c
        L_0x00ca:
            if (r16 == 0) goto L_0x00dd
            boolean r1 = isKotlinIgnore(r12, r5)
            if (r1 == 0) goto L_0x00dd
            r28 = r3
            r34 = r7
            r35 = r9
            r1 = r11
            r29 = 0
            goto L_0x061c
        L_0x00dd:
            r1 = 0
            java.lang.Boolean r23 = java.lang.Boolean.valueOf(r1)
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r1 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r1 = getAnnotation((java.lang.reflect.Method) r6, r1)
            com.alibaba.fastjson.annotation.JSONField r1 = (com.alibaba.fastjson.annotation.JSONField) r1
            if (r1 != 0) goto L_0x00f0
            com.alibaba.fastjson.annotation.JSONField r1 = getSuperMethodAnnotation(r12, r6)
        L_0x00f0:
            java.lang.String r8 = "get"
            r24 = r11
            if (r1 != 0) goto L_0x01e9
            if (r16 == 0) goto L_0x01e9
            if (r0 != 0) goto L_0x0150
            java.lang.reflect.Constructor[] r0 = r41.getDeclaredConstructors()
            java.lang.reflect.Constructor r25 = getKotlinConstructor(r0)
            if (r25 == 0) goto L_0x0147
            java.lang.annotation.Annotation[][] r22 = getParameterAnnotations((java.lang.reflect.Constructor) r25)
            java.lang.String[] r2 = getKoltinConstructorParameters(r41)
            if (r2 == 0) goto L_0x013e
            int r11 = r2.length
            java.lang.String[] r11 = new java.lang.String[r11]
            r27 = r0
            int r0 = r2.length
            r28 = r3
            r3 = 0
            java.lang.System.arraycopy(r2, r3, r11, r3, r0)
            java.util.Arrays.sort(r11)
            int r0 = r2.length
            short[] r0 = new short[r0]
            r21 = 0
            r3 = r21
        L_0x0124:
            r21 = r1
            int r1 = r2.length
            if (r3 >= r1) goto L_0x0137
            r1 = r2[r3]
            int r1 = java.util.Arrays.binarySearch(r11, r1)
            r0[r1] = r3
            int r1 = r3 + 1
            short r3 = (short) r1
            r1 = r21
            goto L_0x0124
        L_0x0137:
            r2 = r11
            r3 = r0
            r1 = r22
            r0 = r27
            goto L_0x0156
        L_0x013e:
            r27 = r0
            r21 = r1
            r28 = r3
            r1 = r22
            goto L_0x0156
        L_0x0147:
            r27 = r0
            r21 = r1
            r28 = r3
            r1 = r22
            goto L_0x0156
        L_0x0150:
            r21 = r1
            r28 = r3
            r1 = r22
        L_0x0156:
            if (r2 == 0) goto L_0x01de
            if (r3 == 0) goto L_0x01de
            boolean r11 = r5.startsWith(r8)
            if (r11 == 0) goto L_0x01de
            r11 = 3
            java.lang.String r22 = r5.substring(r11)
            java.lang.String r11 = decapitalize(r22)
            int r22 = java.util.Arrays.binarySearch(r2, r11)
            if (r22 >= 0) goto L_0x018a
            r25 = 0
            r27 = r0
            r0 = r25
        L_0x0175:
            r25 = r4
            int r4 = r2.length
            if (r0 >= r4) goto L_0x018e
            r4 = r2[r0]
            boolean r4 = r11.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x0185
            r22 = r0
            goto L_0x018e
        L_0x0185:
            int r0 = r0 + 1
            r4 = r25
            goto L_0x0175
        L_0x018a:
            r27 = r0
            r25 = r4
        L_0x018e:
            if (r22 < 0) goto L_0x01d9
            short r0 = r3[r22]
            r4 = r1[r0]
            if (r4 == 0) goto L_0x01b6
            r28 = r0
            int r0 = r4.length
            r30 = r1
            r1 = 0
        L_0x019c:
            if (r1 >= r0) goto L_0x01b3
            r31 = r0
            r0 = r4[r1]
            r32 = r2
            boolean r2 = r0 instanceof com.alibaba.fastjson.annotation.JSONField
            if (r2 == 0) goto L_0x01ac
            r1 = r0
            com.alibaba.fastjson.annotation.JSONField r1 = (com.alibaba.fastjson.annotation.JSONField) r1
            goto L_0x01be
        L_0x01ac:
            int r1 = r1 + 1
            r0 = r31
            r2 = r32
            goto L_0x019c
        L_0x01b3:
            r32 = r2
            goto L_0x01bc
        L_0x01b6:
            r28 = r0
            r30 = r1
            r32 = r2
        L_0x01bc:
            r1 = r21
        L_0x01be:
            if (r1 != 0) goto L_0x01d4
            java.lang.reflect.Field r0 = com.alibaba.fastjson.parser.ParserConfig.getFieldFromCache(r11, r14)
            if (r0 == 0) goto L_0x01d4
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r2 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r2 = getAnnotation((java.lang.reflect.Field) r0, r2)
            r1 = r2
            com.alibaba.fastjson.annotation.JSONField r1 = (com.alibaba.fastjson.annotation.JSONField) r1
            r21 = r1
            r28 = r3
            goto L_0x01f5
        L_0x01d4:
            r21 = r1
            r28 = r3
            goto L_0x01f5
        L_0x01d9:
            r30 = r1
            r32 = r2
            goto L_0x01e6
        L_0x01de:
            r27 = r0
            r30 = r1
            r32 = r2
            r25 = r4
        L_0x01e6:
            r28 = r3
            goto L_0x01f5
        L_0x01e9:
            r21 = r1
            r28 = r3
            r25 = r4
            r27 = r0
            r32 = r2
            r30 = r22
        L_0x01f5:
            if (r21 == 0) goto L_0x0294
            boolean r0 = r21.serialize()
            if (r0 != 0) goto L_0x0207
            r34 = r7
            r35 = r9
            r1 = r24
            r29 = 0
            goto L_0x0604
        L_0x0207:
            int r22 = r21.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r0 = r21.serialzeFeatures()
            int r17 = com.alibaba.fastjson.serializer.SerializerFeature.of(r0)
            com.alibaba.fastjson.parser.Feature[] r0 = r21.parseFeatures()
            int r18 = com.alibaba.fastjson.parser.Feature.of(r0)
            java.lang.String r0 = r21.name()
            int r0 = r0.length()
            if (r0 == 0) goto L_0x0272
            java.lang.String r0 = r21.name()
            if (r13 == 0) goto L_0x0240
            java.lang.Object r1 = r13.get(r0)
            r0 = r1
            java.lang.String r0 = (java.lang.String) r0
            if (r0 != 0) goto L_0x023e
            r34 = r7
            r35 = r9
            r1 = r24
            r29 = 0
            goto L_0x0604
        L_0x023e:
            r11 = r0
            goto L_0x0241
        L_0x0240:
            r11 = r0
        L_0x0241:
            com.alibaba.fastjson.util.FieldInfo r25 = new com.alibaba.fastjson.util.FieldInfo
            r3 = 0
            r8 = 0
            r26 = 0
            r0 = r25
            r1 = r11
            r2 = r6
            r29 = 0
            r4 = r41
            r33 = r5
            r5 = r8
            r31 = r6
            r6 = r22
            r34 = r7
            r7 = r17
            r8 = r18
            r35 = r9
            r9 = r21
            r36 = r10
            r10 = r26
            r13 = r11
            r15 = r24
            r11 = r19
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            r15.put(r13, r0)
            r1 = r15
            goto L_0x0604
        L_0x0272:
            r33 = r5
            r31 = r6
            r34 = r7
            r35 = r9
            r36 = r10
            r15 = r24
            r29 = 0
            java.lang.String r0 = r21.label()
            int r0 = r0.length()
            if (r0 == 0) goto L_0x0291
            java.lang.String r19 = r21.label()
            r4 = r22
            goto L_0x02a4
        L_0x0291:
            r4 = r22
            goto L_0x02a4
        L_0x0294:
            r33 = r5
            r31 = r6
            r34 = r7
            r35 = r9
            r36 = r10
            r15 = r24
            r29 = 0
            r4 = r25
        L_0x02a4:
            r13 = r33
            boolean r0 = r13.startsWith(r8)
            r11 = 102(0x66, float:1.43E-43)
            r10 = 95
            if (r0 == 0) goto L_0x0472
            int r0 = r13.length()
            r1 = 4
            if (r0 >= r1) goto L_0x02ba
            r1 = r15
            goto L_0x0604
        L_0x02ba:
            java.lang.String r0 = "getClass"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x02c5
            r1 = r15
            goto L_0x0604
        L_0x02c5:
            java.lang.String r0 = "getDeclaringClass"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x02d6
            boolean r0 = r41.isEnum()
            if (r0 == 0) goto L_0x02d6
            r1 = r15
            goto L_0x0604
        L_0x02d6:
            r0 = 3
            char r9 = r13.charAt(r0)
            r0 = 0
            boolean r2 = java.lang.Character.isUpperCase(r9)
            if (r2 != 0) goto L_0x0337
            r2 = 512(0x200, float:7.175E-43)
            if (r9 <= r2) goto L_0x02e7
            goto L_0x0337
        L_0x02e7:
            if (r9 != r10) goto L_0x0307
            r2 = 3
            java.lang.String r3 = r13.substring(r2)
            java.lang.Object r2 = r14.get(r3)
            r0 = r2
            java.lang.reflect.Field r0 = (java.lang.reflect.Field) r0
            if (r0 != 0) goto L_0x034e
            r2 = r3
            java.lang.String r1 = r13.substring(r1)
            java.lang.reflect.Field r0 = com.alibaba.fastjson.parser.ParserConfig.getFieldFromCache(r1, r14)
            if (r0 != 0) goto L_0x0305
            r1 = r2
            r3 = r1
            goto L_0x0306
        L_0x0305:
            r3 = r1
        L_0x0306:
            goto L_0x034e
        L_0x0307:
            if (r9 != r11) goto L_0x030f
            r2 = 3
            java.lang.String r3 = r13.substring(r2)
            goto L_0x034e
        L_0x030f:
            r2 = 3
            int r3 = r13.length()
            r5 = 5
            if (r3 < r5) goto L_0x032a
            char r1 = r13.charAt(r1)
            boolean r1 = java.lang.Character.isUpperCase(r1)
            if (r1 == 0) goto L_0x032a
            java.lang.String r1 = r13.substring(r2)
            java.lang.String r3 = decapitalize(r1)
            goto L_0x034e
        L_0x032a:
            java.lang.String r3 = r13.substring(r2)
            java.lang.reflect.Field r0 = com.alibaba.fastjson.parser.ParserConfig.getFieldFromCache(r3, r14)
            if (r0 != 0) goto L_0x034e
            r1 = r15
            goto L_0x0604
        L_0x0337:
            boolean r1 = compatibleWithJavaBean
            if (r1 == 0) goto L_0x0345
            r1 = 3
            java.lang.String r2 = r13.substring(r1)
            java.lang.String r2 = decapitalize(r2)
            goto L_0x034a
        L_0x0345:
            r1 = 3
            java.lang.String r2 = getPropertyNameByMethodName(r13)
        L_0x034a:
            java.lang.String r3 = getPropertyNameByCompatibleFieldName(r14, r13, r2, r1)
        L_0x034e:
            boolean r22 = isJSONTypeIgnore(r12, r3)
            if (r22 == 0) goto L_0x0357
            r1 = r15
            goto L_0x0604
        L_0x0357:
            if (r0 != 0) goto L_0x035d
            java.lang.reflect.Field r0 = com.alibaba.fastjson.parser.ParserConfig.getFieldFromCache(r3, r14)
        L_0x035d:
            r1 = 1
            if (r0 != 0) goto L_0x0383
            int r2 = r3.length()
            if (r2 <= r1) goto L_0x0383
            char r2 = r3.charAt(r1)
            r5 = 65
            if (r2 < r5) goto L_0x0381
            r5 = 90
            if (r2 > r5) goto L_0x0381
            r8 = 3
            java.lang.String r5 = r13.substring(r8)
            java.lang.String r5 = decapitalize(r5)
            java.lang.reflect.Field r0 = com.alibaba.fastjson.parser.ParserConfig.getFieldFromCache(r5, r14)
            r7 = r0
            goto L_0x0385
        L_0x0381:
            r8 = 3
            goto L_0x0384
        L_0x0383:
            r8 = 3
        L_0x0384:
            r7 = r0
        L_0x0385:
            r0 = 0
            if (r7 == 0) goto L_0x040f
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r2 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r2 = getAnnotation((java.lang.reflect.Field) r7, r2)
            r0 = r2
            com.alibaba.fastjson.annotation.JSONField r0 = (com.alibaba.fastjson.annotation.JSONField) r0
            if (r0 == 0) goto L_0x0400
            boolean r2 = r0.serialize()
            if (r2 != 0) goto L_0x039c
            r1 = r15
            goto L_0x0604
        L_0x039c:
            int r2 = r0.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r4 = r0.serialzeFeatures()
            int r4 = com.alibaba.fastjson.serializer.SerializerFeature.of(r4)
            com.alibaba.fastjson.parser.Feature[] r5 = r0.parseFeatures()
            int r5 = com.alibaba.fastjson.parser.Feature.of(r5)
            java.lang.String r6 = r0.name()
            int r6 = r6.length()
            if (r6 == 0) goto L_0x03d6
            java.lang.Boolean r23 = java.lang.Boolean.valueOf(r1)
            java.lang.String r1 = r0.name()
            r6 = r43
            if (r6 == 0) goto L_0x03d4
            java.lang.Object r3 = r6.get(r1)
            r1 = r3
            java.lang.String r1 = (java.lang.String) r1
            if (r1 != 0) goto L_0x03d2
            r1 = r15
            goto L_0x0604
        L_0x03d2:
            r3 = r1
            goto L_0x03d8
        L_0x03d4:
            r3 = r1
            goto L_0x03d8
        L_0x03d6:
            r6 = r43
        L_0x03d8:
            java.lang.String r1 = r0.label()
            int r1 = r1.length()
            if (r1 == 0) goto L_0x03f3
            java.lang.String r1 = r0.label()
            r17 = r0
            r24 = r1
            r18 = r2
            r19 = r4
            r25 = r23
            r23 = r5
            goto L_0x041d
        L_0x03f3:
            r17 = r0
            r18 = r2
            r24 = r19
            r25 = r23
            r19 = r4
            r23 = r5
            goto L_0x041d
        L_0x0400:
            r6 = r43
            r24 = r19
            r25 = r23
            r19 = r17
            r23 = r18
            r17 = r0
            r18 = r4
            goto L_0x041d
        L_0x040f:
            r6 = r43
            r24 = r19
            r25 = r23
            r19 = r17
            r23 = r18
            r17 = r0
            r18 = r4
        L_0x041d:
            if (r6 == 0) goto L_0x042b
            java.lang.Object r0 = r6.get(r3)
            r3 = r0
            java.lang.String r3 = (java.lang.String) r3
            if (r3 != 0) goto L_0x042b
            r1 = r15
            goto L_0x0604
        L_0x042b:
            r5 = r15
            r15 = r46
            if (r15 == 0) goto L_0x043c
            boolean r0 = r25.booleanValue()
            if (r0 != 0) goto L_0x043c
            java.lang.String r3 = r15.translate(r3)
            r4 = r3
            goto L_0x043d
        L_0x043c:
            r4 = r3
        L_0x043d:
            com.alibaba.fastjson.util.FieldInfo r26 = new com.alibaba.fastjson.util.FieldInfo
            r33 = 0
            r0 = r26
            r1 = r4
            r2 = r31
            r3 = r7
            r15 = r4
            r4 = r41
            r12 = r5
            r5 = r33
            r6 = r18
            r33 = r7
            r7 = r19
            r37 = r8
            r8 = r23
            r38 = r9
            r9 = r21
            r10 = r17
            r14 = r37
            r11 = r24
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            r12.put(r15, r0)
            r4 = r18
            r17 = r19
            r18 = r23
            r19 = r24
            r23 = r25
            goto L_0x0474
        L_0x0472:
            r12 = r15
            r14 = 3
        L_0x0474:
            java.lang.String r0 = "is"
            boolean r0 = r13.startsWith(r0)
            if (r0 == 0) goto L_0x05fd
            int r0 = r13.length()
            if (r0 >= r14) goto L_0x0489
            r14 = r44
            r1 = r12
            r12 = r41
            goto L_0x0604
        L_0x0489:
            java.lang.Class r0 = java.lang.Boolean.TYPE
            r15 = r36
            if (r15 == r0) goto L_0x049a
            java.lang.Class<java.lang.Boolean> r0 = java.lang.Boolean.class
            if (r15 == r0) goto L_0x049a
            r14 = r44
            r1 = r12
            r12 = r41
            goto L_0x0604
        L_0x049a:
            r0 = 2
            char r11 = r13.charAt(r0)
            r1 = 0
            boolean r2 = java.lang.Character.isUpperCase(r11)
            if (r2 == 0) goto L_0x04d5
            boolean r2 = compatibleWithJavaBean
            if (r2 == 0) goto L_0x04b3
            java.lang.String r2 = r13.substring(r0)
            java.lang.String r2 = decapitalize(r2)
            goto L_0x04ce
        L_0x04b3:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            char r3 = r13.charAt(r0)
            char r3 = java.lang.Character.toLowerCase(r3)
            r2.append(r3)
            java.lang.String r3 = r13.substring(r14)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
        L_0x04ce:
            r14 = r44
            java.lang.String r0 = getPropertyNameByCompatibleFieldName(r14, r13, r2, r0)
            goto L_0x0510
        L_0x04d5:
            r2 = r14
            r14 = r44
            r3 = 95
            if (r11 != r3) goto L_0x04f8
            java.lang.String r2 = r13.substring(r2)
            java.lang.Object r3 = r14.get(r2)
            r1 = r3
            java.lang.reflect.Field r1 = (java.lang.reflect.Field) r1
            if (r1 != 0) goto L_0x04f6
            r3 = r2
            java.lang.String r0 = r13.substring(r0)
            java.lang.reflect.Field r1 = com.alibaba.fastjson.parser.ParserConfig.getFieldFromCache(r0, r14)
            if (r1 != 0) goto L_0x04f5
            r0 = r3
        L_0x04f5:
            goto L_0x0510
        L_0x04f6:
            r0 = r2
            goto L_0x0510
        L_0x04f8:
            r2 = 102(0x66, float:1.43E-43)
            if (r11 != r2) goto L_0x0501
            java.lang.String r0 = r13.substring(r0)
            goto L_0x0510
        L_0x0501:
            java.lang.String r0 = r13.substring(r0)
            java.lang.reflect.Field r1 = com.alibaba.fastjson.parser.ParserConfig.getFieldFromCache(r0, r14)
            if (r1 != 0) goto L_0x0510
            r1 = r12
            r12 = r41
            goto L_0x0604
        L_0x0510:
            r10 = r12
            r12 = r41
            boolean r22 = isJSONTypeIgnore(r12, r0)
            if (r22 == 0) goto L_0x051c
            r1 = r10
            goto L_0x0604
        L_0x051c:
            if (r1 != 0) goto L_0x0522
            java.lang.reflect.Field r1 = com.alibaba.fastjson.parser.ParserConfig.getFieldFromCache(r0, r14)
        L_0x0522:
            if (r1 != 0) goto L_0x052a
            java.lang.reflect.Field r1 = com.alibaba.fastjson.parser.ParserConfig.getFieldFromCache(r13, r14)
            r9 = r1
            goto L_0x052b
        L_0x052a:
            r9 = r1
        L_0x052b:
            r1 = 0
            if (r9 == 0) goto L_0x05a7
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r2 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r2 = getAnnotation((java.lang.reflect.Field) r9, r2)
            r1 = r2
            com.alibaba.fastjson.annotation.JSONField r1 = (com.alibaba.fastjson.annotation.JSONField) r1
            if (r1 == 0) goto L_0x059a
            boolean r2 = r1.serialize()
            if (r2 != 0) goto L_0x0542
            r1 = r10
            goto L_0x0604
        L_0x0542:
            int r4 = r1.ordinal()
            com.alibaba.fastjson.serializer.SerializerFeature[] r2 = r1.serialzeFeatures()
            int r17 = com.alibaba.fastjson.serializer.SerializerFeature.of(r2)
            com.alibaba.fastjson.parser.Feature[] r2 = r1.parseFeatures()
            int r18 = com.alibaba.fastjson.parser.Feature.of(r2)
            java.lang.String r2 = r1.name()
            int r2 = r2.length()
            if (r2 == 0) goto L_0x0574
            java.lang.String r0 = r1.name()
            r8 = r43
            if (r8 == 0) goto L_0x0576
            java.lang.Object r2 = r8.get(r0)
            r0 = r2
            java.lang.String r0 = (java.lang.String) r0
            if (r0 != 0) goto L_0x0576
            r1 = r10
            goto L_0x0604
        L_0x0574:
            r8 = r43
        L_0x0576:
            java.lang.String r2 = r1.label()
            int r2 = r2.length()
            if (r2 == 0) goto L_0x058f
            java.lang.String r19 = r1.label()
            r24 = r18
            r25 = r19
            r18 = r4
            r19 = r17
            r17 = r1
            goto L_0x05b3
        L_0x058f:
            r24 = r18
            r25 = r19
            r18 = r4
            r19 = r17
            r17 = r1
            goto L_0x05b3
        L_0x059a:
            r8 = r43
            r24 = r18
            r25 = r19
            r18 = r4
            r19 = r17
            r17 = r1
            goto L_0x05b3
        L_0x05a7:
            r8 = r43
            r24 = r18
            r25 = r19
            r18 = r4
            r19 = r17
            r17 = r1
        L_0x05b3:
            if (r8 == 0) goto L_0x05c0
            java.lang.Object r1 = r8.get(r0)
            r0 = r1
            java.lang.String r0 = (java.lang.String) r0
            if (r0 != 0) goto L_0x05c0
            r1 = r10
            goto L_0x0604
        L_0x05c0:
            r7 = r46
            if (r7 == 0) goto L_0x05ca
            java.lang.String r0 = r7.translate(r0)
            r6 = r0
            goto L_0x05cb
        L_0x05ca:
            r6 = r0
        L_0x05cb:
            boolean r0 = r10.containsKey(r6)
            if (r0 == 0) goto L_0x05d3
            r1 = r10
            goto L_0x0604
        L_0x05d3:
            com.alibaba.fastjson.util.FieldInfo r26 = new com.alibaba.fastjson.util.FieldInfo
            r5 = 0
            r0 = r26
            r1 = r6
            r2 = r31
            r3 = r9
            r4 = r41
            r39 = r6
            r6 = r18
            r7 = r19
            r8 = r24
            r33 = r9
            r9 = r21
            r40 = r10
            r10 = r17
            r36 = r11
            r11 = r25
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            r2 = r39
            r1 = r40
            r1.put(r2, r0)
            goto L_0x0604
        L_0x05fd:
            r14 = r44
            r1 = r12
            r15 = r36
            r12 = r41
        L_0x0604:
            r0 = r27
            r3 = r28
            r2 = r32
            goto L_0x0620
        L_0x060b:
            r22 = r1
            r28 = r3
            r25 = r4
            r13 = r5
            r31 = r6
            r34 = r7
            r35 = r9
            r15 = r10
            r1 = r11
            r29 = 0
        L_0x061c:
            r30 = r22
            r3 = r28
        L_0x0620:
            int r7 = r34 + 1
            r13 = r43
            r15 = r46
            r11 = r1
            r10 = r20
            r1 = r30
            r9 = r35
            goto L_0x001c
        L_0x062f:
            r22 = r1
            r28 = r3
            r1 = r11
            java.lang.reflect.Field[] r3 = r41.getFields()
            r4 = r43
            r5 = r46
            computeFields(r12, r4, r5, r1, r3)
            r6 = r45
            java.util.List r7 = getFieldInfos(r12, r6, r1)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.TypeUtils.computeGetters(java.lang.Class, com.alibaba.fastjson.annotation.JSONType, java.util.Map, java.util.Map, boolean, com.alibaba.fastjson.PropertyNamingStrategy):java.util.List");
    }

    private static List<FieldInfo> getFieldInfos(Class<?> clazz, boolean sorted, Map<String, FieldInfo> fieldInfoMap) {
        List<FieldInfo> fieldInfoList = new ArrayList<>();
        String[] orders = null;
        JSONType annotation = (JSONType) getAnnotation(clazz, JSONType.class);
        if (annotation != null) {
            orders = annotation.orders();
        }
        if (orders == null || orders.length <= 0) {
            fieldInfoList.addAll(fieldInfoMap.values());
            if (sorted) {
                Collections.sort(fieldInfoList);
            }
        } else {
            LinkedHashMap<String, FieldInfo> map = new LinkedHashMap<>(fieldInfoList.size());
            for (FieldInfo field : fieldInfoMap.values()) {
                map.put(field.name, field);
            }
            for (String item : orders) {
                FieldInfo field2 = map.get(item);
                if (field2 != null) {
                    fieldInfoList.add(field2);
                    map.remove(item);
                }
            }
            fieldInfoList.addAll(map.values());
        }
        return fieldInfoList;
    }

    private static void computeFields(Class<?> clazz, Map<String, String> aliasMap, PropertyNamingStrategy propertyNamingStrategy, Map<String, FieldInfo> fieldInfoMap, Field[] fields) {
        String label;
        int parserFeatures;
        int serialzeFeatures;
        int ordinal;
        String propertyName;
        Map<String, String> map = aliasMap;
        PropertyNamingStrategy propertyNamingStrategy2 = propertyNamingStrategy;
        Map<String, FieldInfo> map2 = fieldInfoMap;
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                JSONField fieldAnnotation = (JSONField) getAnnotation(field, JSONField.class);
                String propertyName2 = field.getName();
                if (fieldAnnotation == null) {
                    ordinal = 0;
                    serialzeFeatures = 0;
                    parserFeatures = 0;
                    label = null;
                } else if (fieldAnnotation.serialize()) {
                    int ordinal2 = fieldAnnotation.ordinal();
                    int serialzeFeatures2 = SerializerFeature.of(fieldAnnotation.serialzeFeatures());
                    int parserFeatures2 = Feature.of(fieldAnnotation.parseFeatures());
                    if (fieldAnnotation.name().length() != 0) {
                        propertyName2 = fieldAnnotation.name();
                    }
                    if (fieldAnnotation.label().length() != 0) {
                        ordinal = ordinal2;
                        serialzeFeatures = serialzeFeatures2;
                        parserFeatures = parserFeatures2;
                        label = fieldAnnotation.label();
                    } else {
                        ordinal = ordinal2;
                        serialzeFeatures = serialzeFeatures2;
                        parserFeatures = parserFeatures2;
                        label = null;
                    }
                }
                if (map == null || (propertyName2 = map.get(propertyName2)) != null) {
                    if (propertyNamingStrategy2 != null) {
                        propertyName = propertyNamingStrategy2.translate(propertyName2);
                    } else {
                        propertyName = propertyName2;
                    }
                    if (!map2.containsKey(propertyName)) {
                        Field field2 = field;
                        map2.put(propertyName, new FieldInfo(propertyName, (Method) null, field, clazz, (Type) null, ordinal, serialzeFeatures, parserFeatures, (JSONField) null, fieldAnnotation, label));
                    } else {
                        Field field3 = field;
                    }
                }
            }
        }
    }

    private static String getPropertyNameByCompatibleFieldName(Map<String, Field> fieldCacheMap, String methodName, String propertyName, int fromIdx) {
        if (!compatibleWithFieldName || fieldCacheMap.containsKey(propertyName)) {
            return propertyName;
        }
        String tempPropertyName = methodName.substring(fromIdx);
        return fieldCacheMap.containsKey(tempPropertyName) ? tempPropertyName : propertyName;
    }

    public static JSONField getSuperMethodAnnotation(Class<?> clazz, Method method) {
        JSONField annotation;
        JSONField annotation2;
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            Class<?>[] types = method.getParameterTypes();
            for (Class<?> interfaceClass : interfaces) {
                for (Method interfaceMethod : interfaceClass.getMethods()) {
                    Class<?>[] interfaceTypes = interfaceMethod.getParameterTypes();
                    if (interfaceTypes.length == types.length && interfaceMethod.getName().equals(method.getName())) {
                        boolean match = true;
                        int i = 0;
                        while (true) {
                            if (i >= types.length) {
                                break;
                            } else if (!interfaceTypes[i].equals(types[i])) {
                                match = false;
                                break;
                            } else {
                                i++;
                            }
                        }
                        if (match && (annotation2 = (JSONField) getAnnotation(interfaceMethod, JSONField.class)) != null) {
                            return annotation2;
                        }
                    }
                }
            }
        }
        Class<? super Object> superclass = clazz.getSuperclass();
        if (superclass != null && Modifier.isAbstract(superclass.getModifiers())) {
            Class<?>[] types2 = method.getParameterTypes();
            for (Method interfaceMethod2 : superclass.getMethods()) {
                Class<?>[] interfaceTypes2 = interfaceMethod2.getParameterTypes();
                if (interfaceTypes2.length == types2.length && interfaceMethod2.getName().equals(method.getName())) {
                    boolean match2 = true;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= types2.length) {
                            break;
                        } else if (!interfaceTypes2[i2].equals(types2[i2])) {
                            match2 = false;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (match2 && (annotation = (JSONField) getAnnotation(interfaceMethod2, JSONField.class)) != null) {
                        return annotation;
                    }
                }
            }
        }
        return null;
    }

    private static boolean isJSONTypeIgnore(Class<?> clazz, String propertyName) {
        JSONType jsonType = (JSONType) getAnnotation(clazz, JSONType.class);
        if (jsonType != null) {
            String[] fields = jsonType.includes();
            if (fields.length > 0) {
                for (String equals : fields) {
                    if (propertyName.equals(equals)) {
                        return false;
                    }
                }
                return true;
            }
            String[] fields2 = jsonType.ignores();
            for (String equals2 : fields2) {
                if (propertyName.equals(equals2)) {
                    return true;
                }
            }
        }
        if (clazz.getSuperclass() == Object.class || clazz.getSuperclass() == null) {
            return false;
        }
        return isJSONTypeIgnore(clazz.getSuperclass(), propertyName);
    }

    public static boolean isGenericParamType(Type type) {
        if (type instanceof ParameterizedType) {
            return true;
        }
        if (!(type instanceof Class)) {
            return false;
        }
        Type superType = ((Class) type).getGenericSuperclass();
        if (superType == Object.class || !isGenericParamType(superType)) {
            return false;
        }
        return true;
    }

    public static Type getGenericParamType(Type type) {
        if (!(type instanceof ParameterizedType) && (type instanceof Class)) {
            return getGenericParamType(((Class) type).getGenericSuperclass());
        }
        return type;
    }

    public static Type unwrapOptional(Type type) {
        if (!optionalClassInited) {
            try {
                optionalClass = Class.forName("java.util.Optional");
            } catch (Exception e) {
            } catch (Throwable th) {
                optionalClassInited = true;
                throw th;
            }
            optionalClassInited = true;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            if (parameterizedType.getRawType() == optionalClass) {
                return parameterizedType.getActualTypeArguments()[0];
            }
        }
        return type;
    }

    public static Class<?> getClass(Type type) {
        if (type.getClass() == Class.class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        }
        if (type instanceof TypeVariable) {
            Type boundType = ((TypeVariable) type).getBounds()[0];
            if (boundType instanceof Class) {
                return (Class) boundType;
            }
            return getClass(boundType);
        } else if (!(type instanceof WildcardType)) {
            return Object.class;
        } else {
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            if (upperBounds.length == 1) {
                return getClass(upperBounds[0]);
            }
            return Object.class;
        }
    }

    public static Field getField(Class<?> clazz, String fieldName, Field[] declaredFields) {
        for (Field field : declaredFields) {
            String itemName = field.getName();
            if (fieldName.equals(itemName)) {
                return field;
            }
            if (fieldName.length() > 2) {
                char charAt = fieldName.charAt(0);
                char c0 = charAt;
                if (charAt >= 'a' && c0 <= 'z') {
                    char charAt2 = fieldName.charAt(1);
                    char c1 = charAt2;
                    if (charAt2 >= 'A' && c1 <= 'Z' && fieldName.equalsIgnoreCase(itemName)) {
                        return field;
                    }
                }
            }
        }
        Class<? super Object> superclass = clazz.getSuperclass();
        if (superclass == null || superclass == Object.class) {
            return null;
        }
        return getField(superclass, fieldName, superclass.getDeclaredFields());
    }

    public static int getSerializeFeatures(Class<?> clazz) {
        JSONType annotation = (JSONType) getAnnotation(clazz, JSONType.class);
        if (annotation == null) {
            return 0;
        }
        return SerializerFeature.of(annotation.serialzeFeatures());
    }

    public static int getParserFeatures(Class<?> clazz) {
        JSONType annotation = (JSONType) getAnnotation(clazz, JSONType.class);
        if (annotation == null) {
            return 0;
        }
        return Feature.of(annotation.parseFeatures());
    }

    public static String decapitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        if (name.length() > 1 && Character.isUpperCase(name.charAt(1)) && Character.isUpperCase(name.charAt(0))) {
            return name;
        }
        char[] chars = name.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    public static String getPropertyNameByMethodName(String methodName) {
        return Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
    }

    static void setAccessible(AccessibleObject obj) {
        if (setAccessibleEnable && !obj.isAccessible()) {
            try {
                obj.setAccessible(true);
            } catch (AccessControlException e) {
                setAccessibleEnable = false;
            }
        }
    }

    public static Type getCollectionItemType(Type fieldType) {
        if (fieldType instanceof ParameterizedType) {
            return getCollectionItemType((ParameterizedType) fieldType);
        }
        if (fieldType instanceof Class) {
            return getCollectionItemType((Class<?>) (Class) fieldType);
        }
        return Object.class;
    }

    private static Type getCollectionItemType(Class<?> clazz) {
        if (clazz.getName().startsWith("java.")) {
            return Object.class;
        }
        return getCollectionItemType(getCollectionSuperType(clazz));
    }

    private static Type getCollectionItemType(ParameterizedType parameterizedType) {
        Type rawType = parameterizedType.getRawType();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (rawType == Collection.class) {
            return getWildcardTypeUpperBounds(actualTypeArguments[0]);
        }
        Class<?> rawClass = (Class) rawType;
        Map<TypeVariable, Type> actualTypeMap = createActualTypeMap(rawClass.getTypeParameters(), actualTypeArguments);
        Type superType = getCollectionSuperType(rawClass);
        if (!(superType instanceof ParameterizedType)) {
            return getCollectionItemType((Class<?>) (Class) superType);
        }
        Class<?> superClass = getRawClass(superType);
        Type[] superClassTypeParameters = ((ParameterizedType) superType).getActualTypeArguments();
        if (superClassTypeParameters.length > 0) {
            return getCollectionItemType(makeParameterizedType(superClass, superClassTypeParameters, actualTypeMap));
        }
        return getCollectionItemType(superClass);
    }

    private static Type getCollectionSuperType(Class<?> clazz) {
        Type assignable = null;
        for (Type type : clazz.getGenericInterfaces()) {
            Class<?> rawClass = getRawClass(type);
            if (rawClass == Collection.class) {
                return type;
            }
            if (Collection.class.isAssignableFrom(rawClass)) {
                assignable = type;
            }
        }
        return assignable == null ? clazz.getGenericSuperclass() : assignable;
    }

    private static Map<TypeVariable, Type> createActualTypeMap(TypeVariable[] typeParameters, Type[] actualTypeArguments) {
        int length = typeParameters.length;
        Map<TypeVariable, Type> actualTypeMap = new HashMap<>(length);
        for (int i = 0; i < length; i++) {
            actualTypeMap.put(typeParameters[i], actualTypeArguments[i]);
        }
        return actualTypeMap;
    }

    private static ParameterizedType makeParameterizedType(Class<?> rawClass, Type[] typeParameters, Map<TypeVariable, Type> actualTypeMap) {
        int length = typeParameters.length;
        Type[] actualTypeArguments = new Type[length];
        for (int i = 0; i < length; i++) {
            actualTypeArguments[i] = getActualType(typeParameters[i], actualTypeMap);
        }
        return new ParameterizedTypeImpl(actualTypeArguments, (Type) null, rawClass);
    }

    private static Type getActualType(Type typeParameter, Map<TypeVariable, Type> actualTypeMap) {
        if (typeParameter instanceof TypeVariable) {
            return actualTypeMap.get(typeParameter);
        }
        if (typeParameter instanceof ParameterizedType) {
            return makeParameterizedType(getRawClass(typeParameter), ((ParameterizedType) typeParameter).getActualTypeArguments(), actualTypeMap);
        }
        if (typeParameter instanceof GenericArrayType) {
            return new GenericArrayTypeImpl(getActualType(((GenericArrayType) typeParameter).getGenericComponentType(), actualTypeMap));
        }
        return typeParameter;
    }

    private static Type getWildcardTypeUpperBounds(Type type) {
        if (!(type instanceof WildcardType)) {
            return type;
        }
        Type[] upperBounds = ((WildcardType) type).getUpperBounds();
        return upperBounds.length > 0 ? upperBounds[0] : Object.class;
    }

    public static Class<?> getCollectionItemClass(Type fieldType) {
        if (!(fieldType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type actualTypeArgument = ((ParameterizedType) fieldType).getActualTypeArguments()[0];
        if (actualTypeArgument instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) actualTypeArgument).getUpperBounds();
            if (upperBounds.length == 1) {
                actualTypeArgument = upperBounds[0];
            }
        }
        if (actualTypeArgument instanceof Class) {
            Class<?> itemClass = (Class) actualTypeArgument;
            if (Modifier.isPublic(itemClass.getModifiers())) {
                return itemClass;
            }
            throw new JSONException("can not create ASMParser");
        }
        throw new JSONException("can not create ASMParser");
    }

    public static Type checkPrimitiveArray(GenericArrayType genericArrayType) {
        Type clz = genericArrayType;
        Type genericComponentType = genericArrayType.getGenericComponentType();
        String prefix = Constants.ARRAY_TYPE;
        while (genericComponentType instanceof GenericArrayType) {
            genericComponentType = ((GenericArrayType) genericComponentType).getGenericComponentType();
            prefix = prefix + prefix;
        }
        if (!(genericComponentType instanceof Class)) {
            return clz;
        }
        Class<?> ck = (Class) genericComponentType;
        if (!ck.isPrimitive()) {
            return clz;
        }
        try {
            if (ck == Boolean.TYPE) {
                return Class.forName(prefix + "Z");
            } else if (ck == Character.TYPE) {
                return Class.forName(prefix + "C");
            } else if (ck == Byte.TYPE) {
                return Class.forName(prefix + "B");
            } else if (ck == Short.TYPE) {
                return Class.forName(prefix + ExifInterface.LATITUDE_SOUTH);
            } else if (ck == Integer.TYPE) {
                return Class.forName(prefix + "I");
            } else if (ck == Long.TYPE) {
                return Class.forName(prefix + "J");
            } else if (ck == Float.TYPE) {
                return Class.forName(prefix + "F");
            } else if (ck != Double.TYPE) {
                return clz;
            } else {
                return Class.forName(prefix + "D");
            }
        } catch (ClassNotFoundException e) {
            return clz;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v27, resolved type: java.lang.reflect.Type[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Collection createCollection(java.lang.reflect.Type r5) {
        /*
            java.lang.Class r0 = getRawClass(r5)
            java.lang.Class<java.util.AbstractCollection> r1 = java.util.AbstractCollection.class
            if (r0 == r1) goto L_0x00a6
            java.lang.Class<java.util.Collection> r1 = java.util.Collection.class
            if (r0 != r1) goto L_0x000e
            goto L_0x00a6
        L_0x000e:
            java.lang.Class<java.util.HashSet> r1 = java.util.HashSet.class
            boolean r1 = r0.isAssignableFrom(r1)
            if (r1 == 0) goto L_0x001d
            java.util.HashSet r1 = new java.util.HashSet
            r1.<init>()
            goto L_0x00ab
        L_0x001d:
            java.lang.Class<java.util.LinkedHashSet> r1 = java.util.LinkedHashSet.class
            boolean r1 = r0.isAssignableFrom(r1)
            if (r1 == 0) goto L_0x002c
            java.util.LinkedHashSet r1 = new java.util.LinkedHashSet
            r1.<init>()
            goto L_0x00ab
        L_0x002c:
            java.lang.Class<java.util.TreeSet> r1 = java.util.TreeSet.class
            boolean r1 = r0.isAssignableFrom(r1)
            if (r1 == 0) goto L_0x003b
            java.util.TreeSet r1 = new java.util.TreeSet
            r1.<init>()
            goto L_0x00ab
        L_0x003b:
            java.lang.Class<java.util.ArrayList> r1 = java.util.ArrayList.class
            boolean r1 = r0.isAssignableFrom(r1)
            if (r1 == 0) goto L_0x0049
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            goto L_0x00ab
        L_0x0049:
            java.lang.Class<java.util.EnumSet> r1 = java.util.EnumSet.class
            boolean r1 = r0.isAssignableFrom(r1)
            if (r1 == 0) goto L_0x006a
            boolean r1 = r5 instanceof java.lang.reflect.ParameterizedType
            if (r1 == 0) goto L_0x0060
            r1 = r5
            java.lang.reflect.ParameterizedType r1 = (java.lang.reflect.ParameterizedType) r1
            java.lang.reflect.Type[] r1 = r1.getActualTypeArguments()
            r2 = 0
            r1 = r1[r2]
            goto L_0x0062
        L_0x0060:
            java.lang.Class<java.lang.Object> r1 = java.lang.Object.class
        L_0x0062:
            r2 = r1
            java.lang.Class r2 = (java.lang.Class) r2
            java.util.EnumSet r1 = java.util.EnumSet.noneOf(r2)
            goto L_0x00ab
        L_0x006a:
            java.lang.Class<java.util.Queue> r1 = java.util.Queue.class
            boolean r1 = r0.isAssignableFrom(r1)
            if (r1 != 0) goto L_0x00a0
            java.lang.Class r1 = class_deque
            if (r1 == 0) goto L_0x007d
            boolean r1 = r0.isAssignableFrom(r1)
            if (r1 == 0) goto L_0x007d
            goto L_0x00a0
        L_0x007d:
            java.lang.Object r1 = r0.newInstance()     // Catch:{ Exception -> 0x0084 }
            java.util.Collection r1 = (java.util.Collection) r1     // Catch:{ Exception -> 0x0084 }
            goto L_0x00ab
        L_0x0084:
            r1 = move-exception
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "create instance error, class "
            r3.append(r4)
            java.lang.String r4 = r0.getName()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x00a0:
            java.util.LinkedList r1 = new java.util.LinkedList
            r1.<init>()
            goto L_0x00ab
        L_0x00a6:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
        L_0x00ab:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.TypeUtils.createCollection(java.lang.reflect.Type):java.util.Collection");
    }

    public static Class<?> getRawClass(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return getRawClass(((ParameterizedType) type).getRawType());
        }
        if (type instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            if (upperBounds.length == 1) {
                return getRawClass(upperBounds[0]);
            }
            throw new JSONException("TODO");
        }
        throw new JSONException("TODO");
    }

    public static boolean isProxy(Class<?> clazz) {
        for (Class<?> item : clazz.getInterfaces()) {
            String interfaceName = item.getName();
            if (interfaceName.equals("net.sf.cglib.proxy.Factory") || interfaceName.equals("org.springframework.cglib.proxy.Factory") || interfaceName.equals("javassist.util.proxy.ProxyObject") || interfaceName.equals("org.apache.ibatis.javassist.util.proxy.ProxyObject") || interfaceName.equals("org.hibernate.proxy.HibernateProxy") || interfaceName.equals("org.springframework.context.annotation.ConfigurationClassEnhancer$EnhancedConfiguration")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTransient(Method method) {
        if (method == null) {
            return false;
        }
        if (!transientClassInited) {
            try {
                transientClass = Class.forName("java.beans.Transient");
            } catch (Exception e) {
            } catch (Throwable th) {
                transientClassInited = true;
                throw th;
            }
            transientClassInited = true;
        }
        Class<? extends Annotation> cls = transientClass;
        if (cls == null || getAnnotation(method, cls) == null) {
            return false;
        }
        return true;
    }

    public static boolean isAnnotationPresentOneToMany(Method method) {
        if (method == null) {
            return false;
        }
        if (class_OneToMany == null && !class_OneToMany_error) {
            try {
                class_OneToMany = Class.forName("javax.persistence.OneToMany");
            } catch (Throwable th) {
                class_OneToMany_error = true;
            }
        }
        Class<? extends Annotation> cls = class_OneToMany;
        if (cls == null || !method.isAnnotationPresent(cls)) {
            return false;
        }
        return true;
    }

    public static boolean isAnnotationPresentManyToMany(Method method) {
        if (method == null) {
            return false;
        }
        if (class_ManyToMany == null && !class_ManyToMany_error) {
            try {
                class_ManyToMany = Class.forName("javax.persistence.ManyToMany");
            } catch (Throwable th) {
                class_ManyToMany_error = true;
            }
        }
        if (class_ManyToMany == null) {
            return false;
        }
        if (method.isAnnotationPresent(class_OneToMany) || method.isAnnotationPresent(class_ManyToMany)) {
            return true;
        }
        return false;
    }

    public static boolean isHibernateInitialized(Object object) {
        if (object == null) {
            return false;
        }
        if (method_HibernateIsInitialized == null && !method_HibernateIsInitialized_error) {
            try {
                method_HibernateIsInitialized = Class.forName("org.hibernate.Hibernate").getMethod("isInitialized", new Class[]{Object.class});
            } catch (Throwable th) {
                method_HibernateIsInitialized_error = true;
            }
        }
        Method method = method_HibernateIsInitialized;
        if (method != null) {
            try {
                return ((Boolean) method.invoke((Object) null, new Object[]{object})).booleanValue();
            } catch (Throwable th2) {
            }
        }
        return true;
    }

    public static double parseDouble(String str) {
        int len = str.length();
        if (len > 10) {
            return Double.parseDouble(str);
        }
        boolean negative = false;
        long longValue = 0;
        int scale = 0;
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch == '-' && i == 0) {
                negative = true;
            } else if (ch == '.') {
                if (scale != 0) {
                    return Double.parseDouble(str);
                }
                scale = (len - i) - 1;
            } else if (ch < '0' || ch > '9') {
                return Double.parseDouble(str);
            } else {
                longValue = (10 * longValue) + ((long) (ch - '0'));
            }
        }
        if (negative) {
            longValue = -longValue;
        }
        switch (scale) {
            case 0:
                return (double) longValue;
            case 1:
                return ((double) longValue) / 10.0d;
            case 2:
                return ((double) longValue) / 100.0d;
            case 3:
                return ((double) longValue) / 1000.0d;
            case 4:
                return ((double) longValue) / 10000.0d;
            case 5:
                return ((double) longValue) / 100000.0d;
            case 6:
                return ((double) longValue) / 1000000.0d;
            case 7:
                return ((double) longValue) / 1.0E7d;
            case 8:
                return ((double) longValue) / 1.0E8d;
            case 9:
                return ((double) longValue) / 1.0E9d;
            default:
                return Double.parseDouble(str);
        }
    }

    public static float parseFloat(String str) {
        int len = str.length();
        if (len >= 10) {
            return Float.parseFloat(str);
        }
        boolean negative = false;
        long longValue = 0;
        int scale = 0;
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch == '-' && i == 0) {
                negative = true;
            } else if (ch == '.') {
                if (scale != 0) {
                    return Float.parseFloat(str);
                }
                scale = (len - i) - 1;
            } else if (ch < '0' || ch > '9') {
                return Float.parseFloat(str);
            } else {
                longValue = (10 * longValue) + ((long) (ch - '0'));
            }
        }
        if (negative) {
            longValue = -longValue;
        }
        switch (scale) {
            case 0:
                return (float) longValue;
            case 1:
                return ((float) longValue) / 10.0f;
            case 2:
                return ((float) longValue) / 100.0f;
            case 3:
                return ((float) longValue) / 1000.0f;
            case 4:
                return ((float) longValue) / 10000.0f;
            case 5:
                return ((float) longValue) / 100000.0f;
            case 6:
                return ((float) longValue) / 1000000.0f;
            case 7:
                return ((float) longValue) / 1.0E7f;
            case 8:
                return ((float) longValue) / 1.0E8f;
            case 9:
                return ((float) longValue) / 1.0E9f;
            default:
                return Float.parseFloat(str);
        }
    }

    public static long fnv1a_64_extract(String key) {
        long hashCode = -3750763034362895579L;
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            if (!(ch == '_' || ch == '-')) {
                if (ch >= 'A' && ch <= 'Z') {
                    ch = (char) (ch + ' ');
                }
                hashCode = (hashCode ^ ((long) ch)) * 1099511628211L;
            }
        }
        return hashCode;
    }

    public static long fnv1a_64_lower(String key) {
        long hashCode = -3750763034362895579L;
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                ch = (char) (ch + ' ');
            }
            hashCode = (hashCode ^ ((long) ch)) * 1099511628211L;
        }
        return hashCode;
    }

    public static long fnv1a_64(String key) {
        long hashCode = -3750763034362895579L;
        for (int i = 0; i < key.length(); i++) {
            hashCode = (hashCode ^ ((long) key.charAt(i))) * 1099511628211L;
        }
        return hashCode;
    }

    public static boolean isKotlin(Class clazz) {
        if (kotlin_metadata == null && !kotlin_metadata_error) {
            try {
                kotlin_metadata = Class.forName("kotlin.l");
            } catch (Throwable th) {
                kotlin_metadata_error = true;
            }
        }
        if (kotlin_metadata == null || !clazz.isAnnotationPresent(kotlin_metadata)) {
            return false;
        }
        return true;
    }

    public static Constructor getKotlinConstructor(Constructor[] constructors) {
        return getKotlinConstructor(constructors, (String[]) null);
    }

    public static Constructor getKotlinConstructor(Constructor[] constructors, String[] paramNames) {
        Constructor creatorConstructor = null;
        for (Constructor constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if ((paramNames == null || parameterTypes.length == paramNames.length) && ((parameterTypes.length <= 0 || !parameterTypes[parameterTypes.length - 1].getName().equals("kotlin.jvm.internal.DefaultConstructorMarker")) && (creatorConstructor == null || creatorConstructor.getParameterTypes().length < parameterTypes.length))) {
                creatorConstructor = constructor;
            }
        }
        return creatorConstructor;
    }

    public static String[] getKoltinConstructorParameters(Class clazz) {
        if (kotlin_kclass_constructor == null && !kotlin_class_klass_error) {
            try {
                kotlin_kclass_constructor = Class.forName("kotlin.reflect.jvm.internal.g").getConstructor(new Class[]{Class.class});
            } catch (Throwable th) {
                kotlin_class_klass_error = true;
            }
        }
        if (kotlin_kclass_constructor == null) {
            return null;
        }
        if (kotlin_kclass_getConstructors == null && !kotlin_class_klass_error) {
            try {
                kotlin_kclass_getConstructors = Class.forName("kotlin.reflect.jvm.internal.g").getMethod("getConstructors", new Class[0]);
            } catch (Throwable th2) {
                kotlin_class_klass_error = true;
            }
        }
        if (kotlin_kfunction_getParameters == null && !kotlin_class_klass_error) {
            try {
                kotlin_kfunction_getParameters = Class.forName("kotlin.reflect.f").getMethod("getParameters", new Class[0]);
            } catch (Throwable th3) {
                kotlin_class_klass_error = true;
            }
        }
        if (kotlin_kparameter_getName == null && !kotlin_class_klass_error) {
            try {
                kotlin_kparameter_getName = Class.forName("kotlin.reflect.j").getMethod("getName", new Class[0]);
            } catch (Throwable th4) {
                kotlin_class_klass_error = true;
            }
        }
        if (kotlin_error) {
            return null;
        }
        Object constructor = null;
        try {
            Iterator iterator = ((Iterable) kotlin_kclass_getConstructors.invoke(kotlin_kclass_constructor.newInstance(new Object[]{clazz}), new Object[0])).iterator();
            while (iterator.hasNext()) {
                Object item = iterator.next();
                List parameters = (List) kotlin_kfunction_getParameters.invoke(item, new Object[0]);
                if (constructor == null || parameters.size() != 0) {
                    constructor = item;
                }
                iterator.hasNext();
            }
            if (constructor == null) {
                return null;
            }
            List parameters2 = (List) kotlin_kfunction_getParameters.invoke(constructor, new Object[0]);
            String[] names = new String[parameters2.size()];
            for (int i = 0; i < parameters2.size(); i++) {
                names[i] = (String) kotlin_kparameter_getName.invoke(parameters2.get(i), new Object[0]);
            }
            return names;
        } catch (Throwable e) {
            e.printStackTrace();
            kotlin_error = true;
            return null;
        }
    }

    private static boolean isKotlinIgnore(Class clazz, String methodName) {
        if (kotlinIgnores == null && !kotlinIgnores_error) {
            try {
                Map<Class, String[]> map = new HashMap<>();
                map.put(Class.forName("kotlin.ranges.c"), new String[]{"getEndInclusive", "isEmpty"});
                map.put(Class.forName("kotlin.ranges.i"), new String[]{"getEndInclusive", "isEmpty"});
                map.put(Class.forName("kotlin.ranges.l"), new String[]{"getEndInclusive", "isEmpty"});
                map.put(Class.forName("kotlin.ranges.e"), new String[]{"getEndInclusive", "isEmpty"});
                map.put(Class.forName("kotlin.ranges.d"), new String[]{"getEndInclusive", "isEmpty"});
                kotlinIgnores = map;
            } catch (Throwable th) {
                kotlinIgnores_error = true;
            }
        }
        if (kotlinIgnores == null) {
            return false;
        }
        String[] ignores = kotlinIgnores.get(clazz);
        if (ignores == null || Arrays.binarySearch(ignores, methodName) < 0) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [java.lang.reflect.Type] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <A extends java.lang.annotation.Annotation> A getAnnotation(java.lang.Class<?> r10, java.lang.Class<A> r11) {
        /*
            java.lang.annotation.Annotation r0 = r10.getAnnotation(r11)
            r1 = 0
            java.lang.reflect.Type r2 = com.alibaba.fastjson.JSON.getMixInAnnotations(r10)
            boolean r3 = r2 instanceof java.lang.Class
            if (r3 == 0) goto L_0x0010
            r1 = r2
            java.lang.Class r1 = (java.lang.Class) r1
        L_0x0010:
            r3 = 0
            if (r1 == 0) goto L_0x0037
            java.lang.annotation.Annotation r4 = r1.getAnnotation(r11)
            java.lang.annotation.Annotation[] r5 = r1.getAnnotations()
            if (r4 != 0) goto L_0x0034
            int r6 = r5.length
            if (r6 <= 0) goto L_0x0034
            int r6 = r5.length
            r7 = r3
        L_0x0022:
            if (r7 >= r6) goto L_0x0034
            r8 = r5[r7]
            java.lang.Class r9 = r8.annotationType()
            java.lang.annotation.Annotation r4 = r9.getAnnotation(r11)
            if (r4 == 0) goto L_0x0031
            goto L_0x0034
        L_0x0031:
            int r7 = r7 + 1
            goto L_0x0022
        L_0x0034:
            if (r4 == 0) goto L_0x0037
            return r4
        L_0x0037:
            java.lang.annotation.Annotation[] r4 = r10.getAnnotations()
            if (r0 != 0) goto L_0x0053
            int r5 = r4.length
            if (r5 <= 0) goto L_0x0053
            int r5 = r4.length
        L_0x0041:
            if (r3 >= r5) goto L_0x0053
            r6 = r4[r3]
            java.lang.Class r7 = r6.annotationType()
            java.lang.annotation.Annotation r0 = r7.getAnnotation(r11)
            if (r0 == 0) goto L_0x0050
            goto L_0x0053
        L_0x0050:
            int r3 = r3 + 1
            goto L_0x0041
        L_0x0053:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.TypeUtils.getAnnotation(java.lang.Class, java.lang.Class):java.lang.annotation.Annotation");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v0, resolved type: java.lang.Class<java.lang.Object>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v1, resolved type: java.lang.Class<? super java.lang.Object>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: java.lang.Class<? super java.lang.Object>} */
    /* JADX WARNING: type inference failed for: r3v0, types: [java.lang.reflect.Type] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <A extends java.lang.annotation.Annotation> A getAnnotation(java.lang.reflect.Field r8, java.lang.Class<A> r9) {
        /*
            java.lang.annotation.Annotation r0 = r8.getAnnotation(r9)
            java.lang.Class r1 = r8.getDeclaringClass()
            r2 = 0
            java.lang.reflect.Type r3 = com.alibaba.fastjson.JSON.getMixInAnnotations(r1)
            boolean r4 = r3 instanceof java.lang.Class
            if (r4 == 0) goto L_0x0014
            r2 = r3
            java.lang.Class r2 = (java.lang.Class) r2
        L_0x0014:
            if (r2 == 0) goto L_0x0038
            r4 = 0
            java.lang.String r5 = r8.getName()
            r6 = r2
        L_0x001c:
            if (r6 == 0) goto L_0x002e
            java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
            if (r6 == r7) goto L_0x002e
            java.lang.reflect.Field r7 = r6.getDeclaredField(r5)     // Catch:{ NoSuchFieldException -> 0x0028 }
            r4 = r7
            goto L_0x002e
        L_0x0028:
            r7 = move-exception
            java.lang.Class r6 = r6.getSuperclass()
            goto L_0x001c
        L_0x002e:
            if (r4 != 0) goto L_0x0031
            return r0
        L_0x0031:
            java.lang.annotation.Annotation r6 = r4.getAnnotation(r9)
            if (r6 == 0) goto L_0x0038
            return r6
        L_0x0038:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.TypeUtils.getAnnotation(java.lang.reflect.Field, java.lang.Class):java.lang.annotation.Annotation");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v0, resolved type: java.lang.Class<java.lang.Object>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v1, resolved type: java.lang.Class<? super java.lang.Object>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v3, resolved type: java.lang.Class<? super java.lang.Object>} */
    /* JADX WARNING: type inference failed for: r3v0, types: [java.lang.reflect.Type] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <A extends java.lang.annotation.Annotation> A getAnnotation(java.lang.reflect.Method r9, java.lang.Class<A> r10) {
        /*
            java.lang.annotation.Annotation r0 = r9.getAnnotation(r10)
            java.lang.Class r1 = r9.getDeclaringClass()
            r2 = 0
            java.lang.reflect.Type r3 = com.alibaba.fastjson.JSON.getMixInAnnotations(r1)
            boolean r4 = r3 instanceof java.lang.Class
            if (r4 == 0) goto L_0x0014
            r2 = r3
            java.lang.Class r2 = (java.lang.Class) r2
        L_0x0014:
            if (r2 == 0) goto L_0x003c
            r4 = 0
            java.lang.String r5 = r9.getName()
            java.lang.Class[] r6 = r9.getParameterTypes()
            r7 = r2
        L_0x0020:
            if (r7 == 0) goto L_0x0032
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            if (r7 == r8) goto L_0x0032
            java.lang.reflect.Method r8 = r7.getDeclaredMethod(r5, r6)     // Catch:{ NoSuchMethodException -> 0x002c }
            r4 = r8
            goto L_0x0032
        L_0x002c:
            r8 = move-exception
            java.lang.Class r7 = r7.getSuperclass()
            goto L_0x0020
        L_0x0032:
            if (r4 != 0) goto L_0x0035
            return r0
        L_0x0035:
            java.lang.annotation.Annotation r7 = r4.getAnnotation(r10)
            if (r7 == 0) goto L_0x003c
            return r7
        L_0x003c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.TypeUtils.getAnnotation(java.lang.reflect.Method, java.lang.Class):java.lang.annotation.Annotation");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v0, resolved type: java.lang.Class<java.lang.Object>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v1, resolved type: java.lang.Class<? super java.lang.Object>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v3, resolved type: java.lang.Class<? super java.lang.Object>} */
    /* JADX WARNING: type inference failed for: r3v0, types: [java.lang.reflect.Type] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.annotation.Annotation[][] getParameterAnnotations(java.lang.reflect.Method r9) {
        /*
            java.lang.annotation.Annotation[][] r0 = r9.getParameterAnnotations()
            java.lang.Class r1 = r9.getDeclaringClass()
            r2 = 0
            java.lang.reflect.Type r3 = com.alibaba.fastjson.JSON.getMixInAnnotations(r1)
            boolean r4 = r3 instanceof java.lang.Class
            if (r4 == 0) goto L_0x0014
            r2 = r3
            java.lang.Class r2 = (java.lang.Class) r2
        L_0x0014:
            if (r2 == 0) goto L_0x003d
            r4 = 0
            java.lang.String r5 = r9.getName()
            java.lang.Class[] r6 = r9.getParameterTypes()
            r7 = r2
        L_0x0020:
            if (r7 == 0) goto L_0x0033
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            if (r7 == r8) goto L_0x0033
            java.lang.reflect.Method r8 = r7.getDeclaredMethod(r5, r6)     // Catch:{ NoSuchMethodException -> 0x002c }
            r4 = r8
            goto L_0x0033
        L_0x002c:
            r8 = move-exception
            java.lang.Class r7 = r7.getSuperclass()
            goto L_0x0020
        L_0x0033:
            if (r4 != 0) goto L_0x0036
            return r0
        L_0x0036:
            java.lang.annotation.Annotation[][] r7 = r4.getParameterAnnotations()
            if (r7 == 0) goto L_0x003d
            return r7
        L_0x003d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.TypeUtils.getParameterAnnotations(java.lang.reflect.Method):java.lang.annotation.Annotation[][]");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v0, resolved type: java.lang.Class<java.lang.Object>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v1, resolved type: java.lang.Class<java.lang.Object>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v3, resolved type: java.lang.Class<? super java.lang.Object>} */
    /* JADX WARNING: type inference failed for: r3v0, types: [java.lang.reflect.Type] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.annotation.Annotation[][] getParameterAnnotations(java.lang.reflect.Constructor r13) {
        /*
            java.lang.annotation.Annotation[][] r0 = r13.getParameterAnnotations()
            java.lang.Class r1 = r13.getDeclaringClass()
            r2 = 0
            java.lang.reflect.Type r3 = com.alibaba.fastjson.JSON.getMixInAnnotations(r1)
            boolean r4 = r3 instanceof java.lang.Class
            if (r4 == 0) goto L_0x0014
            r2 = r3
            java.lang.Class r2 = (java.lang.Class) r2
        L_0x0014:
            if (r2 == 0) goto L_0x0076
            r4 = 0
            java.lang.Class[] r5 = r13.getParameterTypes()
            java.util.ArrayList r6 = new java.util.ArrayList
            r7 = 2
            r6.<init>(r7)
            java.lang.Class r7 = r2.getEnclosingClass()
        L_0x0025:
            if (r7 == 0) goto L_0x002f
            r6.add(r7)
            java.lang.Class r7 = r7.getEnclosingClass()
            goto L_0x0025
        L_0x002f:
            int r7 = r6.size()
            r8 = r2
        L_0x0034:
            if (r8 == 0) goto L_0x006c
            java.lang.Class<java.lang.Object> r9 = java.lang.Object.class
            if (r8 == r9) goto L_0x006c
            if (r7 == 0) goto L_0x005d
            int r9 = r5.length     // Catch:{ NoSuchMethodException -> 0x0063 }
            int r9 = r9 + r7
            java.lang.Class[] r9 = new java.lang.Class[r9]     // Catch:{ NoSuchMethodException -> 0x0063 }
            r10 = 0
            int r11 = r5.length     // Catch:{ NoSuchMethodException -> 0x0063 }
            java.lang.System.arraycopy(r5, r10, r9, r7, r11)     // Catch:{ NoSuchMethodException -> 0x0063 }
            r10 = r7
        L_0x0046:
            if (r10 <= 0) goto L_0x0057
            int r11 = r10 + -1
            int r12 = r10 + -1
            java.lang.Object r12 = r6.get(r12)     // Catch:{ NoSuchMethodException -> 0x0063 }
            java.lang.Class r12 = (java.lang.Class) r12     // Catch:{ NoSuchMethodException -> 0x0063 }
            r9[r11] = r12     // Catch:{ NoSuchMethodException -> 0x0063 }
            int r10 = r10 + -1
            goto L_0x0046
        L_0x0057:
            java.lang.reflect.Constructor r10 = r2.getDeclaredConstructor(r9)     // Catch:{ NoSuchMethodException -> 0x0063 }
            r4 = r10
            goto L_0x0062
        L_0x005d:
            java.lang.reflect.Constructor r9 = r2.getDeclaredConstructor(r5)     // Catch:{ NoSuchMethodException -> 0x0063 }
            r4 = r9
        L_0x0062:
            goto L_0x006c
        L_0x0063:
            r9 = move-exception
            int r7 = r7 + -1
            java.lang.Class r8 = r8.getSuperclass()
            goto L_0x0034
        L_0x006c:
            if (r4 != 0) goto L_0x006f
            return r0
        L_0x006f:
            java.lang.annotation.Annotation[][] r8 = r4.getParameterAnnotations()
            if (r8 == 0) goto L_0x0076
            return r8
        L_0x0076:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.TypeUtils.getParameterAnnotations(java.lang.reflect.Constructor):java.lang.annotation.Annotation[][]");
    }

    public static boolean isJacksonCreator(Method method) {
        if (method == null) {
            return false;
        }
        if (class_JacksonCreator == null && !class_JacksonCreator_error) {
            try {
                class_JacksonCreator = Class.forName("com.fasterxml.jackson.annotation.JsonCreator");
            } catch (Throwable th) {
                class_JacksonCreator_error = true;
            }
        }
        Class<? extends Annotation> cls = class_JacksonCreator;
        if (cls == null || !method.isAnnotationPresent(cls)) {
            return false;
        }
        return true;
    }
}
