package com.alibaba.fastjson.support.retrofit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.c0;
import okhttp3.e0;
import okhttp3.x;
import retrofit2.h;
import retrofit2.t;

public class Retrofit2ConverterFactory extends h.a {
    @Deprecated
    private static final Feature[] EMPTY_SERIALIZER_FEATURES = new Feature[0];
    /* access modifiers changed from: private */
    public static final x MEDIA_TYPE = x.h("application/json; charset=UTF-8");
    /* access modifiers changed from: private */
    public FastJsonConfig fastJsonConfig;
    @Deprecated
    private int featureValues;
    @Deprecated
    private Feature[] features;
    @Deprecated
    private ParserConfig parserConfig;
    @Deprecated
    private SerializeConfig serializeConfig;
    @Deprecated
    private SerializerFeature[] serializerFeatures;

    public Retrofit2ConverterFactory() {
        this.parserConfig = ParserConfig.getGlobalInstance();
        this.featureValues = JSON.DEFAULT_PARSER_FEATURE;
        this.fastJsonConfig = new FastJsonConfig();
    }

    public Retrofit2ConverterFactory(FastJsonConfig fastJsonConfig2) {
        this.parserConfig = ParserConfig.getGlobalInstance();
        this.featureValues = JSON.DEFAULT_PARSER_FEATURE;
        this.fastJsonConfig = fastJsonConfig2;
    }

    public static Retrofit2ConverterFactory create() {
        return create(new FastJsonConfig());
    }

    public static Retrofit2ConverterFactory create(FastJsonConfig fastJsonConfig2) {
        if (fastJsonConfig2 != null) {
            return new Retrofit2ConverterFactory(fastJsonConfig2);
        }
        throw new NullPointerException("fastJsonConfig == null");
    }

    public h<e0, Object> responseBodyConverter(Type type, Annotation[] annotations, t retrofit) {
        return new ResponseBodyConverter(type);
    }

    public h<Object, c0> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, t retrofit) {
        return new RequestBodyConverter();
    }

    public FastJsonConfig getFastJsonConfig() {
        return this.fastJsonConfig;
    }

    public Retrofit2ConverterFactory setFastJsonConfig(FastJsonConfig fastJsonConfig2) {
        this.fastJsonConfig = fastJsonConfig2;
        return this;
    }

    @Deprecated
    public ParserConfig getParserConfig() {
        return this.fastJsonConfig.getParserConfig();
    }

    @Deprecated
    public Retrofit2ConverterFactory setParserConfig(ParserConfig config) {
        this.fastJsonConfig.setParserConfig(config);
        return this;
    }

    @Deprecated
    public int getParserFeatureValues() {
        return JSON.DEFAULT_PARSER_FEATURE;
    }

    @Deprecated
    public Retrofit2ConverterFactory setParserFeatureValues(int featureValues2) {
        return this;
    }

    @Deprecated
    public Feature[] getParserFeatures() {
        return this.fastJsonConfig.getFeatures();
    }

    @Deprecated
    public Retrofit2ConverterFactory setParserFeatures(Feature[] features2) {
        this.fastJsonConfig.setFeatures(features2);
        return this;
    }

    @Deprecated
    public SerializeConfig getSerializeConfig() {
        return this.fastJsonConfig.getSerializeConfig();
    }

    @Deprecated
    public Retrofit2ConverterFactory setSerializeConfig(SerializeConfig serializeConfig2) {
        this.fastJsonConfig.setSerializeConfig(serializeConfig2);
        return this;
    }

    @Deprecated
    public SerializerFeature[] getSerializerFeatures() {
        return this.fastJsonConfig.getSerializerFeatures();
    }

    @Deprecated
    public Retrofit2ConverterFactory setSerializerFeatures(SerializerFeature[] features2) {
        this.fastJsonConfig.setSerializerFeatures(features2);
        return this;
    }

    public final class ResponseBodyConverter<T> implements h<e0, T> {
        private Type type;

        ResponseBodyConverter(Type type2) {
            this.type = type2;
        }

        public T convert(e0 value) {
            try {
                T parseObject = JSON.parseObject(value.bytes(), Retrofit2ConverterFactory.this.fastJsonConfig.getCharset(), this.type, Retrofit2ConverterFactory.this.fastJsonConfig.getParserConfig(), Retrofit2ConverterFactory.this.fastJsonConfig.getParseProcess(), JSON.DEFAULT_PARSER_FEATURE, Retrofit2ConverterFactory.this.fastJsonConfig.getFeatures());
                value.close();
                return parseObject;
            } catch (Exception e) {
                throw new IOException("JSON parse error: " + e.getMessage(), e);
            } catch (Throwable th) {
                value.close();
                throw th;
            }
        }
    }

    public final class RequestBodyConverter<T> implements h<T, c0> {
        RequestBodyConverter() {
        }

        public c0 convert(T value) {
            try {
                return c0.create(Retrofit2ConverterFactory.MEDIA_TYPE, JSON.toJSONBytesWithFastJsonConfig(Retrofit2ConverterFactory.this.fastJsonConfig.getCharset(), value, Retrofit2ConverterFactory.this.fastJsonConfig.getSerializeConfig(), Retrofit2ConverterFactory.this.fastJsonConfig.getSerializeFilters(), Retrofit2ConverterFactory.this.fastJsonConfig.getDateFormat(), JSON.DEFAULT_GENERATE_FEATURE, Retrofit2ConverterFactory.this.fastJsonConfig.getSerializerFeatures()));
            } catch (Exception e) {
                throw new IOException("Could not write JSON: " + e.getMessage(), e);
            }
        }
    }
}
