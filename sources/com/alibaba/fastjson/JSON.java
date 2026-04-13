package com.alibaba.fastjson;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldTypeResolver;
import com.alibaba.fastjson.parser.deserializer.ParseProcess;
import com.alibaba.fastjson.serializer.JSONSerializable;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import com.tencent.bugly.Bugly;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public abstract class JSON implements JSONStreamAware, JSONAware {
    public static int DEFAULT_GENERATE_FEATURE = ((((0 | SerializerFeature.QuoteFieldNames.getMask()) | SerializerFeature.SkipTransientField.getMask()) | SerializerFeature.WriteEnumUsingName.getMask()) | SerializerFeature.SortField.getMask());
    public static int DEFAULT_PARSER_FEATURE = ((((((((0 | Feature.AutoCloseSource.getMask()) | Feature.InternFieldNames.getMask()) | Feature.UseBigDecimal.getMask()) | Feature.AllowUnQuotedFieldNames.getMask()) | Feature.AllowSingleQuotes.getMask()) | Feature.AllowArbitraryCommas.getMask()) | Feature.SortFeidFastMatch.getMask()) | Feature.IgnoreNotMatch.getMask());
    public static String DEFAULT_TYPE_KEY = "@type";
    public static String DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String VERSION = "1.2.75";
    private static final ThreadLocal<byte[]> bytesLocal = new ThreadLocal<>();
    private static final ThreadLocal<char[]> charsLocal = new ThreadLocal<>();
    public static Locale defaultLocale = Locale.getDefault();
    public static TimeZone defaultTimeZone = TimeZone.getDefault();
    static final SerializeFilter[] emptyFilters = new SerializeFilter[0];
    private static final ConcurrentHashMap<Type, Type> mixInsMapper = new ConcurrentHashMap<>(16);

    static {
        config(IOUtils.DEFAULT_PROPERTIES);
    }

    private static void config(Properties properties) {
        String featuresProperty = properties.getProperty("fastjson.serializerFeatures.MapSortField");
        int mask = SerializerFeature.MapSortField.getMask();
        if ("true".equals(featuresProperty)) {
            DEFAULT_GENERATE_FEATURE |= mask;
        } else if (Bugly.SDK_IS_DEV.equals(featuresProperty)) {
            DEFAULT_GENERATE_FEATURE &= ~mask;
        }
        if ("true".equals(properties.getProperty("parser.features.NonStringKeyAsString"))) {
            DEFAULT_PARSER_FEATURE |= Feature.NonStringKeyAsString.getMask();
        }
        if ("true".equals(properties.getProperty("parser.features.ErrorOnEnumNotMatch")) || "true".equals(properties.getProperty("fastjson.parser.features.ErrorOnEnumNotMatch"))) {
            DEFAULT_PARSER_FEATURE |= Feature.ErrorOnEnumNotMatch.getMask();
        }
        if (Bugly.SDK_IS_DEV.equals(properties.getProperty("fastjson.asmEnable"))) {
            ParserConfig.global.setAsmEnable(false);
            SerializeConfig.globalInstance.setAsmEnable(false);
        }
    }

    public static void setDefaultTypeKey(String typeKey) {
        DEFAULT_TYPE_KEY = typeKey;
        ParserConfig.global.symbolTable.addSymbol(typeKey, 0, typeKey.length(), typeKey.hashCode(), true);
    }

    public static Object parse(String text) {
        return parse(text, DEFAULT_PARSER_FEATURE);
    }

    public static Object parse(String text, ParserConfig config) {
        return parse(text, config, DEFAULT_PARSER_FEATURE);
    }

    public static Object parse(String text, ParserConfig config, Feature... features) {
        int featureValues = DEFAULT_PARSER_FEATURE;
        for (Feature feature : features) {
            featureValues = Feature.config(featureValues, feature, true);
        }
        return parse(text, config, featureValues);
    }

    public static Object parse(String text, ParserConfig config, int features) {
        if (text == null) {
            return null;
        }
        DefaultJSONParser parser = new DefaultJSONParser(text, config, features);
        Object value = parser.parse();
        parser.handleResovleTask(value);
        parser.close();
        return value;
    }

    public static Object parse(String text, int features) {
        return parse(text, ParserConfig.getGlobalInstance(), features);
    }

    public static Object parse(byte[] input, Feature... features) {
        char[] chars = allocateChars(input.length);
        int len = IOUtils.decodeUTF8(input, 0, input.length, chars);
        if (len < 0) {
            return null;
        }
        return parse(new String(chars, 0, len), features);
    }

    public static Object parse(byte[] input, int off, int len, CharsetDecoder charsetDecoder, Feature... features) {
        if (input == null || input.length == 0) {
            return null;
        }
        int featureValues = DEFAULT_PARSER_FEATURE;
        for (Feature feature : features) {
            featureValues = Feature.config(featureValues, feature, true);
        }
        return parse(input, off, len, charsetDecoder, featureValues);
    }

    public static Object parse(byte[] input, int off, int len, CharsetDecoder charsetDecoder, int features) {
        charsetDecoder.reset();
        char[] chars = allocateChars((int) (((double) len) * ((double) charsetDecoder.maxCharsPerByte())));
        ByteBuffer byteBuf = ByteBuffer.wrap(input, off, len);
        CharBuffer charBuf = CharBuffer.wrap(chars);
        IOUtils.decode(charsetDecoder, byteBuf, charBuf);
        DefaultJSONParser parser = new DefaultJSONParser(chars, charBuf.position(), ParserConfig.getGlobalInstance(), features);
        Object value = parser.parse();
        parser.handleResovleTask(value);
        parser.close();
        return value;
    }

    public static Object parse(String text, Feature... features) {
        int featureValues = DEFAULT_PARSER_FEATURE;
        for (Feature feature : features) {
            featureValues = Feature.config(featureValues, feature, true);
        }
        return parse(text, featureValues);
    }

    public static JSONObject parseObject(String text, Feature... features) {
        return (JSONObject) parse(text, features);
    }

    public static JSONObject parseObject(String text) {
        Object obj = parse(text);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        try {
            return (JSONObject) toJSON(obj);
        } catch (RuntimeException e) {
            throw new JSONException("can not cast to JSONObject.", e);
        }
    }

    public static <T> T parseObject(String text, TypeReference<T> type, Feature... features) {
        return parseObject(text, type.type, ParserConfig.global, DEFAULT_PARSER_FEATURE, features);
    }

    public static <T> T parseObject(String json, Class<T> clazz, Feature... features) {
        return parseObject(json, (Type) clazz, ParserConfig.global, (ParseProcess) null, DEFAULT_PARSER_FEATURE, features);
    }

    public static <T> T parseObject(String text, Class<T> clazz, ParseProcess processor, Feature... features) {
        return parseObject(text, (Type) clazz, ParserConfig.global, processor, DEFAULT_PARSER_FEATURE, features);
    }

    public static <T> T parseObject(String json, Type type, Feature... features) {
        return parseObject(json, type, ParserConfig.global, DEFAULT_PARSER_FEATURE, features);
    }

    public static <T> T parseObject(String input, Type clazz, ParseProcess processor, Feature... features) {
        return parseObject(input, clazz, ParserConfig.global, processor, DEFAULT_PARSER_FEATURE, features);
    }

    public static <T> T parseObject(String input, Type clazz, int featureValues, Feature... features) {
        if (input == null) {
            return null;
        }
        for (Feature feature : features) {
            featureValues = Feature.config(featureValues, feature, true);
        }
        DefaultJSONParser parser = new DefaultJSONParser(input, ParserConfig.getGlobalInstance(), featureValues);
        T value = parser.parseObject(clazz);
        parser.handleResovleTask(value);
        parser.close();
        return value;
    }

    public static <T> T parseObject(String input, Type clazz, ParserConfig config, Feature... features) {
        return parseObject(input, clazz, config, (ParseProcess) null, DEFAULT_PARSER_FEATURE, features);
    }

    public static <T> T parseObject(String input, Type clazz, ParserConfig config, int featureValues, Feature... features) {
        return parseObject(input, clazz, config, (ParseProcess) null, featureValues, features);
    }

    public static <T> T parseObject(String input, Type clazz, ParserConfig config, ParseProcess processor, int featureValues, Feature... features) {
        if (input == null || input.length() == 0) {
            return null;
        }
        if (features != null) {
            for (Feature feature : features) {
                featureValues |= feature.mask;
            }
        }
        DefaultJSONParser parser = new DefaultJSONParser(input, config, featureValues);
        if (processor != null) {
            if (processor instanceof ExtraTypeProvider) {
                parser.getExtraTypeProviders().add((ExtraTypeProvider) processor);
            }
            if (processor instanceof ExtraProcessor) {
                parser.getExtraProcessors().add((ExtraProcessor) processor);
            }
            if (processor instanceof FieldTypeResolver) {
                parser.setFieldTypeResolver((FieldTypeResolver) processor);
            }
        }
        T value = parser.parseObject(clazz, (Object) null);
        parser.handleResovleTask(value);
        parser.close();
        return value;
    }

    public static <T> T parseObject(byte[] bytes, Type clazz, Feature... features) {
        return parseObject(bytes, 0, bytes.length, IOUtils.UTF8, clazz, features);
    }

    public static <T> T parseObject(byte[] bytes, int offset, int len, Charset charset, Type clazz, Feature... features) {
        return parseObject(bytes, offset, len, charset, clazz, ParserConfig.global, (ParseProcess) null, DEFAULT_PARSER_FEATURE, features);
    }

    public static <T> T parseObject(byte[] bytes, Charset charset, Type clazz, ParserConfig config, ParseProcess processor, int featureValues, Feature... features) {
        return parseObject(bytes, 0, bytes.length, charset, clazz, config, processor, featureValues, features);
    }

    public static <T> T parseObject(byte[] bytes, int offset, int len, Charset charset, Type clazz, ParserConfig config, ParseProcess processor, int featureValues, Feature... features) {
        String strVal;
        if (charset == null) {
            charset = IOUtils.UTF8;
        }
        if (charset == IOUtils.UTF8) {
            char[] chars = allocateChars(bytes.length);
            int chars_len = IOUtils.decodeUTF8(bytes, offset, len, chars);
            if (chars_len < 0) {
                return null;
            }
            strVal = new String(chars, 0, chars_len);
        } else if (len < 0) {
            return null;
        } else {
            strVal = new String(bytes, offset, len, charset);
        }
        return parseObject(strVal, clazz, config, processor, featureValues, features);
    }

    public static <T> T parseObject(byte[] input, int off, int len, CharsetDecoder charsetDecoder, Type clazz, Feature... features) {
        charsetDecoder.reset();
        char[] chars = allocateChars((int) (((double) len) * ((double) charsetDecoder.maxCharsPerByte())));
        ByteBuffer byteBuf = ByteBuffer.wrap(input, off, len);
        CharBuffer charByte = CharBuffer.wrap(chars);
        IOUtils.decode(charsetDecoder, byteBuf, charByte);
        return parseObject(chars, charByte.position(), clazz, features);
    }

    public static <T> T parseObject(char[] input, int length, Type clazz, Feature... features) {
        if (input == null || input.length == 0) {
            return null;
        }
        int featureValues = DEFAULT_PARSER_FEATURE;
        for (Feature feature : features) {
            featureValues = Feature.config(featureValues, feature, true);
        }
        DefaultJSONParser parser = new DefaultJSONParser(input, length, ParserConfig.getGlobalInstance(), featureValues);
        T value = parser.parseObject(clazz);
        parser.handleResovleTask(value);
        parser.close();
        return value;
    }

    public static <T> T parseObject(InputStream is, Type type, Feature... features) {
        return parseObject(is, IOUtils.UTF8, type, features);
    }

    public static <T> T parseObject(InputStream is, Charset charset, Type type, Feature... features) {
        return parseObject(is, charset, type, ParserConfig.global, features);
    }

    public static <T> T parseObject(InputStream is, Charset charset, Type type, ParserConfig config, Feature... features) {
        return parseObject(is, charset, type, config, (ParseProcess) null, DEFAULT_PARSER_FEATURE, features);
    }

    public static <T> T parseObject(InputStream is, Charset charset, Type type, ParserConfig config, ParseProcess processor, int featureValues, Feature... features) {
        Charset charset2;
        if (charset == null) {
            charset2 = IOUtils.UTF8;
        } else {
            charset2 = charset;
        }
        byte[] bytes = allocateBytes(65536);
        int offset = 0;
        while (true) {
            InputStream inputStream = is;
            int readCount = is.read(bytes, offset, bytes.length - offset);
            if (readCount == -1) {
                return parseObject(bytes, 0, offset, charset2, type, config, processor, featureValues, features);
            }
            offset += readCount;
            if (offset == bytes.length) {
                byte[] newBytes = new byte[((bytes.length * 3) / 2)];
                System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
                bytes = newBytes;
            }
        }
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        return parseObject(text, clazz, new Feature[0]);
    }

    public static JSONArray parseArray(String text) {
        return parseArray(text, ParserConfig.global);
    }

    public static JSONArray parseArray(String text, ParserConfig parserConfig) {
        JSONArray array;
        if (text == null) {
            return null;
        }
        DefaultJSONParser parser = new DefaultJSONParser(text, parserConfig);
        JSONLexer lexer = parser.lexer;
        if (lexer.token() == 8) {
            lexer.nextToken();
            array = null;
        } else if (lexer.token() == 20) {
            array = null;
        } else {
            array = new JSONArray();
            parser.parseArray((Collection) array);
            parser.handleResovleTask(array);
        }
        parser.close();
        return array;
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        return parseArray(text, clazz, ParserConfig.global);
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz, ParserConfig config) {
        List<T> list;
        if (text == null) {
            return null;
        }
        DefaultJSONParser parser = new DefaultJSONParser(text, config);
        JSONLexer lexer = parser.lexer;
        int token = lexer.token();
        if (token == 8) {
            lexer.nextToken();
            list = null;
        } else if (token != 20 || !lexer.isBlankInput()) {
            list = new ArrayList<>();
            parser.parseArray((Class<?>) clazz, (Collection) list);
            parser.handleResovleTask(list);
        } else {
            list = null;
        }
        parser.close();
        return list;
    }

    public static List<Object> parseArray(String text, Type[] types) {
        return parseArray(text, types, ParserConfig.global);
    }

    public static List<Object> parseArray(String text, Type[] types, ParserConfig config) {
        List<Object> list;
        if (text == null) {
            return null;
        }
        DefaultJSONParser parser = new DefaultJSONParser(text, config);
        Object[] objectArray = parser.parseArray(types);
        if (objectArray == null) {
            list = null;
        } else {
            list = Arrays.asList(objectArray);
        }
        parser.handleResovleTask(list);
        parser.close();
        return list;
    }

    public static String toJSONString(Object object) {
        return toJSONString(object, emptyFilters, new SerializerFeature[0]);
    }

    public static String toJSONString(Object object, SerializerFeature... features) {
        return toJSONString(object, DEFAULT_GENERATE_FEATURE, features);
    }

    public static String toJSONString(Object object, int defaultFeatures, SerializerFeature... features) {
        SerializeWriter out = new SerializeWriter((Writer) null, defaultFeatures, features);
        try {
            new JSONSerializer(out).write(object);
            return out.toString();
        } finally {
            out.close();
        }
    }

    public static String toJSONStringWithDateFormat(Object object, String dateFormat, SerializerFeature... features) {
        return toJSONString(object, SerializeConfig.globalInstance, (SerializeFilter[]) null, dateFormat, DEFAULT_GENERATE_FEATURE, features);
    }

    public static String toJSONString(Object object, SerializeFilter filter, SerializerFeature... features) {
        return toJSONString(object, SerializeConfig.globalInstance, new SerializeFilter[]{filter}, (String) null, DEFAULT_GENERATE_FEATURE, features);
    }

    public static String toJSONString(Object object, SerializeFilter[] filters, SerializerFeature... features) {
        return toJSONString(object, SerializeConfig.globalInstance, filters, (String) null, DEFAULT_GENERATE_FEATURE, features);
    }

    public static byte[] toJSONBytes(Object object, SerializerFeature... features) {
        return toJSONBytes(object, DEFAULT_GENERATE_FEATURE, features);
    }

    public static byte[] toJSONBytes(Object object, SerializeFilter filter, SerializerFeature... features) {
        return toJSONBytes(object, SerializeConfig.globalInstance, new SerializeFilter[]{filter}, DEFAULT_GENERATE_FEATURE, features);
    }

    public static byte[] toJSONBytes(Object object, int defaultFeatures, SerializerFeature... features) {
        return toJSONBytes(object, SerializeConfig.globalInstance, defaultFeatures, features);
    }

    public static String toJSONString(Object object, SerializeConfig config, SerializerFeature... features) {
        return toJSONString(object, config, (SerializeFilter) null, features);
    }

    public static String toJSONString(Object object, SerializeConfig config, SerializeFilter filter, SerializerFeature... features) {
        return toJSONString(object, config, new SerializeFilter[]{filter}, (String) null, DEFAULT_GENERATE_FEATURE, features);
    }

    public static String toJSONString(Object object, SerializeConfig config, SerializeFilter[] filters, SerializerFeature... features) {
        return toJSONString(object, config, filters, (String) null, DEFAULT_GENERATE_FEATURE, features);
    }

    public static String toJSONString(Object object, SerializeConfig config, SerializeFilter[] filters, String dateFormat, int defaultFeatures, SerializerFeature... features) {
        SerializeWriter out = new SerializeWriter((Writer) null, defaultFeatures, features);
        try {
            JSONSerializer serializer = new JSONSerializer(out, config);
            if (!(dateFormat == null || dateFormat.length() == 0)) {
                serializer.setDateFormat(dateFormat);
                serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
            }
            if (filters != null) {
                for (SerializeFilter filter : filters) {
                    serializer.addFilter(filter);
                }
            }
            serializer.write(object);
            return out.toString();
        } finally {
            out.close();
        }
    }

    public static String toJSONStringZ(Object object, SerializeConfig mapping, SerializerFeature... features) {
        return toJSONString(object, mapping, emptyFilters, (String) null, 0, features);
    }

    public static byte[] toJSONBytes(Object object, SerializeConfig config, SerializerFeature... features) {
        return toJSONBytes(object, config, emptyFilters, DEFAULT_GENERATE_FEATURE, features);
    }

    public static byte[] toJSONBytes(Object object, SerializeConfig config, int defaultFeatures, SerializerFeature... features) {
        return toJSONBytes(object, config, emptyFilters, defaultFeatures, features);
    }

    public static byte[] toJSONBytes(Object object, SerializeFilter[] filters, SerializerFeature... features) {
        return toJSONBytes(object, SerializeConfig.globalInstance, filters, DEFAULT_GENERATE_FEATURE, features);
    }

    public static byte[] toJSONBytes(Object object, SerializeConfig config, SerializeFilter filter, SerializerFeature... features) {
        return toJSONBytes(object, config, new SerializeFilter[]{filter}, DEFAULT_GENERATE_FEATURE, features);
    }

    public static byte[] toJSONBytes(Object object, SerializeConfig config, SerializeFilter[] filters, int defaultFeatures, SerializerFeature... features) {
        return toJSONBytes(object, config, filters, (String) null, defaultFeatures, features);
    }

    public static byte[] toJSONBytes(Object object, SerializeConfig config, SerializeFilter[] filters, String dateFormat, int defaultFeatures, SerializerFeature... features) {
        return toJSONBytes(IOUtils.UTF8, object, config, filters, dateFormat, defaultFeatures, features);
    }

    public static byte[] toJSONBytes(Charset charset, Object object, SerializeConfig config, SerializeFilter[] filters, String dateFormat, int defaultFeatures, SerializerFeature... features) {
        SerializeWriter out = new SerializeWriter((Writer) null, defaultFeatures, features);
        try {
            JSONSerializer serializer = new JSONSerializer(out, config);
            if (!(dateFormat == null || dateFormat.length() == 0)) {
                serializer.setDateFormat(dateFormat);
                serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
            }
            if (filters != null) {
                for (SerializeFilter filter : filters) {
                    serializer.addFilter(filter);
                }
            }
            serializer.write(object);
            return out.toBytes(charset);
        } finally {
            out.close();
        }
    }

    public static byte[] toJSONBytesWithFastJsonConfig(Charset charset, Object object, SerializeConfig config, SerializeFilter[] filters, String dateFormat, int defaultFeatures, SerializerFeature... features) {
        SerializeWriter out = new SerializeWriter((Writer) null, defaultFeatures, features);
        try {
            JSONSerializer serializer = new JSONSerializer(out, config);
            if (!(dateFormat == null || dateFormat.length() == 0)) {
                serializer.setFastJsonConfigDateFormatPattern(dateFormat);
                serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
            }
            if (filters != null) {
                for (SerializeFilter filter : filters) {
                    serializer.addFilter(filter);
                }
            }
            serializer.write(object);
            return out.toBytes(charset);
        } finally {
            out.close();
        }
    }

    public static String toJSONString(Object object, boolean prettyFormat) {
        if (!prettyFormat) {
            return toJSONString(object);
        }
        return toJSONString(object, SerializerFeature.PrettyFormat);
    }

    public static void writeJSONStringTo(Object object, Writer writer, SerializerFeature... features) {
        writeJSONString(writer, object, features);
    }

    public static void writeJSONString(Writer writer, Object object, SerializerFeature... features) {
        writeJSONString(writer, object, DEFAULT_GENERATE_FEATURE, features);
    }

    public static void writeJSONString(Writer writer, Object object, int defaultFeatures, SerializerFeature... features) {
        SerializeWriter out = new SerializeWriter(writer, defaultFeatures, features);
        try {
            new JSONSerializer(out).write(object);
        } finally {
            out.close();
        }
    }

    public static final int writeJSONString(OutputStream os, Object object, SerializerFeature... features) {
        return writeJSONString(os, object, DEFAULT_GENERATE_FEATURE, features);
    }

    public static final int writeJSONString(OutputStream os, Object object, int defaultFeatures, SerializerFeature... features) {
        return writeJSONString(os, IOUtils.UTF8, object, SerializeConfig.globalInstance, (SerializeFilter[]) null, (String) null, defaultFeatures, features);
    }

    public static final int writeJSONString(OutputStream os, Charset charset, Object object, SerializerFeature... features) {
        return writeJSONString(os, charset, object, SerializeConfig.globalInstance, (SerializeFilter[]) null, (String) null, DEFAULT_GENERATE_FEATURE, features);
    }

    public static final int writeJSONString(OutputStream os, Charset charset, Object object, SerializeConfig config, SerializeFilter[] filters, String dateFormat, int defaultFeatures, SerializerFeature... features) {
        SerializeWriter writer = new SerializeWriter((Writer) null, defaultFeatures, features);
        try {
            JSONSerializer serializer = new JSONSerializer(writer, config);
            if (!(dateFormat == null || dateFormat.length() == 0)) {
                serializer.setDateFormat(dateFormat);
                serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
            }
            if (filters != null) {
                for (SerializeFilter filter : filters) {
                    serializer.addFilter(filter);
                }
            }
            serializer.write(object);
            return writer.writeToEx(os, charset);
        } finally {
            writer.close();
        }
    }

    public static final int writeJSONStringWithFastJsonConfig(OutputStream os, Charset charset, Object object, SerializeConfig config, SerializeFilter[] filters, String dateFormat, int defaultFeatures, SerializerFeature... features) {
        SerializeWriter writer = new SerializeWriter((Writer) null, defaultFeatures, features);
        try {
            JSONSerializer serializer = new JSONSerializer(writer, config);
            if (!(dateFormat == null || dateFormat.length() == 0)) {
                serializer.setFastJsonConfigDateFormatPattern(dateFormat);
                serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
            }
            if (filters != null) {
                for (SerializeFilter filter : filters) {
                    serializer.addFilter(filter);
                }
            }
            serializer.write(object);
            return writer.writeToEx(os, charset);
        } finally {
            writer.close();
        }
    }

    public String toString() {
        return toJSONString();
    }

    public String toJSONString() {
        SerializeWriter out = new SerializeWriter();
        try {
            new JSONSerializer(out).write((Object) this);
            return out.toString();
        } finally {
            out.close();
        }
    }

    public String toString(SerializerFeature... features) {
        SerializeWriter out = new SerializeWriter((Writer) null, DEFAULT_GENERATE_FEATURE, features);
        try {
            new JSONSerializer(out).write((Object) this);
            return out.toString();
        } finally {
            out.close();
        }
    }

    public void writeJSONString(Appendable appendable) {
        SerializeWriter out = new SerializeWriter();
        try {
            new JSONSerializer(out).write((Object) this);
            appendable.append(out.toString());
            out.close();
        } catch (IOException e) {
            throw new JSONException(e.getMessage(), e);
        } catch (Throwable th) {
            out.close();
            throw th;
        }
    }

    public static Object toJSON(Object javaObject) {
        return toJSON(javaObject, SerializeConfig.globalInstance);
    }

    public static Object toJSON(Object javaObject, ParserConfig parserConfig) {
        return toJSON(javaObject, SerializeConfig.globalInstance);
    }

    public static Object toJSON(Object javaObject, SerializeConfig config) {
        Map innerMap;
        if (javaObject == null) {
            return null;
        }
        if (javaObject instanceof JSON) {
            return javaObject;
        }
        if (javaObject instanceof Map) {
            Map<Object, Object> map = (Map) javaObject;
            int size = map.size();
            if (map instanceof LinkedHashMap) {
                innerMap = new LinkedHashMap(size);
            } else if (map instanceof TreeMap) {
                innerMap = new TreeMap();
            } else {
                innerMap = new HashMap(size);
            }
            JSONObject json = new JSONObject((Map<String, Object>) innerMap);
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                json.put(TypeUtils.castToString(entry.getKey()), toJSON(entry.getValue(), config));
            }
            return json;
        } else if (javaObject instanceof Collection) {
            Collection<Object> collection = (Collection) javaObject;
            JSONArray array = new JSONArray(collection.size());
            for (Object item : collection) {
                array.add(toJSON(item, config));
            }
            return array;
        } else if (javaObject instanceof JSONSerializable) {
            return parse(toJSONString(javaObject));
        } else {
            Class<?> clazz = javaObject.getClass();
            if (clazz.isEnum()) {
                return ((Enum) javaObject).name();
            }
            if (clazz.isArray()) {
                int len = Array.getLength(javaObject);
                JSONArray array2 = new JSONArray(len);
                for (int i = 0; i < len; i++) {
                    array2.add(toJSON(Array.get(javaObject, i)));
                }
                return array2;
            } else if (ParserConfig.isPrimitive2(clazz) != 0) {
                return javaObject;
            } else {
                ObjectSerializer serializer = config.getObjectWriter(clazz);
                if (!(serializer instanceof JavaBeanSerializer)) {
                    return parse(toJSONString(javaObject, config, new SerializerFeature[0]));
                }
                JavaBeanSerializer javaBeanSerializer = (JavaBeanSerializer) serializer;
                JSONObject json2 = new JSONObject();
                try {
                    for (Map.Entry<String, Object> entry2 : javaBeanSerializer.getFieldValuesMap(javaObject).entrySet()) {
                        json2.put(entry2.getKey(), toJSON(entry2.getValue(), config));
                    }
                    return json2;
                } catch (Exception e) {
                    throw new JSONException("toJSON error", e);
                }
            }
        }
    }

    public static <T> T toJavaObject(JSON json, Class<T> clazz) {
        return TypeUtils.cast((Object) json, clazz, ParserConfig.getGlobalInstance());
    }

    public <T> T toJavaObject(Class<T> clazz) {
        if (clazz == JSONArray.class || clazz == JSON.class || clazz == Collection.class || clazz == List.class) {
            return this;
        }
        return TypeUtils.cast((Object) this, clazz, ParserConfig.getGlobalInstance());
    }

    public <T> T toJavaObject(Type type) {
        return TypeUtils.cast((Object) this, type, ParserConfig.getGlobalInstance());
    }

    public <T> T toJavaObject(TypeReference typeReference) {
        return TypeUtils.cast((Object) this, typeReference != null ? typeReference.getType() : null, ParserConfig.getGlobalInstance());
    }

    private static byte[] allocateBytes(int length) {
        ThreadLocal<byte[]> threadLocal = bytesLocal;
        byte[] chars = threadLocal.get();
        if (chars == null) {
            if (length > 65536) {
                return new byte[length];
            }
            byte[] chars2 = new byte[65536];
            threadLocal.set(chars2);
            return chars2;
        } else if (chars.length < length) {
            return new byte[length];
        } else {
            return chars;
        }
    }

    private static char[] allocateChars(int length) {
        ThreadLocal<char[]> threadLocal = charsLocal;
        char[] chars = threadLocal.get();
        if (chars == null) {
            if (length > 65536) {
                return new char[length];
            }
            char[] chars2 = new char[65536];
            threadLocal.set(chars2);
            return chars2;
        } else if (chars.length < length) {
            return new char[length];
        } else {
            return chars;
        }
    }

    public static boolean isValid(String str) {
        boolean z = false;
        if (str == null || str.length() == 0) {
            return false;
        }
        JSONScanner lexer = new JSONScanner(str);
        try {
            lexer.nextToken();
            switch (lexer.token()) {
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    lexer.nextToken();
                    break;
                case 12:
                    if (lexer.getCurrent() != 26) {
                        lexer.skipObject(true);
                        break;
                    } else {
                        lexer.close();
                        return false;
                    }
                case 14:
                    lexer.skipArray(true);
                    break;
                default:
                    return false;
            }
            if (lexer.token() == 20) {
                z = true;
            }
            lexer.close();
            return z;
        } catch (Exception e) {
            return false;
        } finally {
            lexer.close();
        }
    }

    public static boolean isValidObject(String str) {
        boolean z = false;
        if (str == null || str.length() == 0) {
            return false;
        }
        JSONScanner lexer = new JSONScanner(str);
        try {
            lexer.nextToken();
            if (lexer.token() != 12) {
                lexer.close();
                return false;
            } else if (lexer.getCurrent() == 26) {
                return false;
            } else {
                lexer.skipObject(true);
                if (lexer.token() == 20) {
                    z = true;
                }
                lexer.close();
                return z;
            }
        } catch (Exception e) {
            return false;
        } finally {
            lexer.close();
        }
    }

    public static boolean isValidArray(String str) {
        boolean z = false;
        if (str == null || str.length() == 0) {
            return false;
        }
        JSONScanner lexer = new JSONScanner(str);
        try {
            lexer.nextToken();
            if (lexer.token() == 14) {
                lexer.skipArray(true);
                if (lexer.token() == 20) {
                    z = true;
                }
                return z;
            }
            lexer.close();
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            lexer.close();
        }
    }

    public static <T> void handleResovleTask(DefaultJSONParser parser, T value) {
        parser.handleResovleTask(value);
    }

    public static void addMixInAnnotations(Type target, Type mixinSource) {
        if (target != null && mixinSource != null) {
            mixInsMapper.put(target, mixinSource);
        }
    }

    public static void removeMixInAnnotations(Type target) {
        if (target != null) {
            mixInsMapper.remove(target);
        }
    }

    public static void clearMixInAnnotations() {
        mixInsMapper.clear();
    }

    public static Type getMixInAnnotations(Type target) {
        if (target != null) {
            return mixInsMapper.get(target);
        }
        return null;
    }
}
