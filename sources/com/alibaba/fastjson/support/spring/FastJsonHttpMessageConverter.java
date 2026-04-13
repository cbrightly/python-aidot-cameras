package com.alibaba.fastjson.support.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.nio.charset.Charset;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;

public class FastJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> implements GenericHttpMessageConverter<Object> {
    public static final MediaType APPLICATION_JAVASCRIPT = new MediaType("application", "javascript");
    @Deprecated
    protected String dateFormat;
    private FastJsonConfig fastJsonConfig = new FastJsonConfig();
    @Deprecated
    protected SerializerFeature[] features = new SerializerFeature[0];
    @Deprecated
    protected SerializeFilter[] filters = new SerializeFilter[0];

    public FastJsonConfig getFastJsonConfig() {
        return this.fastJsonConfig;
    }

    public void setFastJsonConfig(FastJsonConfig fastJsonConfig2) {
        this.fastJsonConfig = fastJsonConfig2;
    }

    public FastJsonHttpMessageConverter() {
        super(MediaType.ALL);
    }

    @Deprecated
    public Charset getCharset() {
        return this.fastJsonConfig.getCharset();
    }

    @Deprecated
    public void setCharset(Charset charset) {
        this.fastJsonConfig.setCharset(charset);
    }

    @Deprecated
    public String getDateFormat() {
        return this.fastJsonConfig.getDateFormat();
    }

    @Deprecated
    public void setDateFormat(String dateFormat2) {
        this.fastJsonConfig.setDateFormat(dateFormat2);
    }

    @Deprecated
    public SerializerFeature[] getFeatures() {
        return this.fastJsonConfig.getSerializerFeatures();
    }

    @Deprecated
    public void setFeatures(SerializerFeature... features2) {
        this.fastJsonConfig.setSerializerFeatures(features2);
    }

    @Deprecated
    public SerializeFilter[] getFilters() {
        return this.fastJsonConfig.getSerializeFilters();
    }

    @Deprecated
    public void setFilters(SerializeFilter... filters2) {
        this.fastJsonConfig.setSerializeFilters(filters2);
    }

    @Deprecated
    public void addSerializeFilter(SerializeFilter filter) {
        if (filter != null) {
            int length = this.fastJsonConfig.getSerializeFilters().length;
            SerializeFilter[] filters2 = new SerializeFilter[(length + 1)];
            System.arraycopy(this.fastJsonConfig.getSerializeFilters(), 0, filters2, 0, length);
            filters2[filters2.length - 1] = filter;
            this.fastJsonConfig.setSerializeFilters(filters2);
        }
    }

    /* access modifiers changed from: protected */
    public boolean supports(Class<?> cls) {
        return true;
    }

    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        return FastJsonHttpMessageConverter.super.canRead(contextClass, mediaType);
    }

    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return FastJsonHttpMessageConverter.super.canWrite(clazz, mediaType);
    }

    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) {
        return readType(getType(type, contextClass), inputMessage);
    }

    public void write(Object o, Type type, MediaType contentType, HttpOutputMessage outputMessage) {
        FastJsonHttpMessageConverter.super.write(o, contentType, outputMessage);
    }

    /* access modifiers changed from: protected */
    public Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) {
        return readType(getType(clazz, (Class<?>) null), inputMessage);
    }

    private Object readType(Type type, HttpInputMessage inputMessage) {
        try {
            return JSON.parseObject(inputMessage.getBody(), this.fastJsonConfig.getCharset(), type, this.fastJsonConfig.getParserConfig(), this.fastJsonConfig.getParseProcess(), JSON.DEFAULT_PARSER_FEATURE, this.fastJsonConfig.getFeatures());
        } catch (JSONException ex) {
            throw new HttpMessageNotReadableException("JSON parse error: " + ex.getMessage(), ex);
        } catch (IOException ex2) {
            throw new HttpMessageNotReadableException("I/O error while reading input message", ex2);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0087 A[Catch:{ JSONException -> 0x00a6, all -> 0x00a4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0094 A[Catch:{ JSONException -> 0x00a6, all -> 0x00a4 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeInternal(java.lang.Object r16, org.springframework.http.HttpOutputMessage r17) {
        /*
            r15 = this;
            r1 = r15
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>()
            r10 = r0
            org.springframework.http.HttpHeaders r0 = r17.getHeaders()     // Catch:{ JSONException -> 0x00a6 }
            com.alibaba.fastjson.support.config.FastJsonConfig r2 = r1.fastJsonConfig     // Catch:{ JSONException -> 0x00a6 }
            com.alibaba.fastjson.serializer.SerializeFilter[] r2 = r2.getSerializeFilters()     // Catch:{ JSONException -> 0x00a6 }
            r11 = r2
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ JSONException -> 0x00a6 }
            java.util.List r3 = java.util.Arrays.asList(r11)     // Catch:{ JSONException -> 0x00a6 }
            r2.<init>(r3)     // Catch:{ JSONException -> 0x00a6 }
            r12 = r2
            r2 = 0
            java.lang.Object r3 = r15.strangeCodeForJackson(r16)     // Catch:{ JSONException -> 0x00a6 }
            boolean r4 = r3 instanceof com.alibaba.fastjson.support.spring.FastJsonContainer     // Catch:{ JSONException -> 0x00a6 }
            if (r4 == 0) goto L_0x003a
            r4 = r3
            com.alibaba.fastjson.support.spring.FastJsonContainer r4 = (com.alibaba.fastjson.support.spring.FastJsonContainer) r4     // Catch:{ JSONException -> 0x00a6 }
            com.alibaba.fastjson.support.spring.PropertyPreFilters r5 = r4.getFilters()     // Catch:{ JSONException -> 0x00a6 }
            java.util.List r6 = r5.getFilters()     // Catch:{ JSONException -> 0x00a6 }
            r12.addAll(r6)     // Catch:{ JSONException -> 0x00a6 }
            java.lang.Object r6 = r4.getValue()     // Catch:{ JSONException -> 0x00a6 }
            r3 = r6
            r13 = r3
            goto L_0x003b
        L_0x003a:
            r13 = r3
        L_0x003b:
            boolean r3 = r13 instanceof com.alibaba.fastjson.support.spring.MappingFastJsonValue     // Catch:{ JSONException -> 0x00a6 }
            if (r3 == 0) goto L_0x0050
            r3 = r13
            com.alibaba.fastjson.support.spring.MappingFastJsonValue r3 = (com.alibaba.fastjson.support.spring.MappingFastJsonValue) r3     // Catch:{ JSONException -> 0x00a6 }
            java.lang.String r3 = r3.getJsonpFunction()     // Catch:{ JSONException -> 0x00a6 }
            boolean r3 = org.springframework.util.StringUtils.isEmpty(r3)     // Catch:{ JSONException -> 0x00a6 }
            if (r3 != 0) goto L_0x0057
            r2 = 1
            r14 = r2
            goto L_0x0058
        L_0x0050:
            boolean r3 = r13 instanceof com.alibaba.fastjson.JSONPObject     // Catch:{ JSONException -> 0x00a6 }
            if (r3 == 0) goto L_0x0057
            r2 = 1
            r14 = r2
            goto L_0x0058
        L_0x0057:
            r14 = r2
        L_0x0058:
            com.alibaba.fastjson.support.config.FastJsonConfig r2 = r1.fastJsonConfig     // Catch:{ JSONException -> 0x00a6 }
            java.nio.charset.Charset r3 = r2.getCharset()     // Catch:{ JSONException -> 0x00a6 }
            com.alibaba.fastjson.support.config.FastJsonConfig r2 = r1.fastJsonConfig     // Catch:{ JSONException -> 0x00a6 }
            com.alibaba.fastjson.serializer.SerializeConfig r5 = r2.getSerializeConfig()     // Catch:{ JSONException -> 0x00a6 }
            int r2 = r12.size()     // Catch:{ JSONException -> 0x00a6 }
            com.alibaba.fastjson.serializer.SerializeFilter[] r2 = new com.alibaba.fastjson.serializer.SerializeFilter[r2]     // Catch:{ JSONException -> 0x00a6 }
            java.lang.Object[] r2 = r12.toArray(r2)     // Catch:{ JSONException -> 0x00a6 }
            r6 = r2
            com.alibaba.fastjson.serializer.SerializeFilter[] r6 = (com.alibaba.fastjson.serializer.SerializeFilter[]) r6     // Catch:{ JSONException -> 0x00a6 }
            com.alibaba.fastjson.support.config.FastJsonConfig r2 = r1.fastJsonConfig     // Catch:{ JSONException -> 0x00a6 }
            java.lang.String r7 = r2.getDateFormat()     // Catch:{ JSONException -> 0x00a6 }
            int r8 = com.alibaba.fastjson.JSON.DEFAULT_GENERATE_FEATURE     // Catch:{ JSONException -> 0x00a6 }
            com.alibaba.fastjson.support.config.FastJsonConfig r2 = r1.fastJsonConfig     // Catch:{ JSONException -> 0x00a6 }
            com.alibaba.fastjson.serializer.SerializerFeature[] r9 = r2.getSerializerFeatures()     // Catch:{ JSONException -> 0x00a6 }
            r2 = r10
            r4 = r13
            int r2 = com.alibaba.fastjson.JSON.writeJSONStringWithFastJsonConfig(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ JSONException -> 0x00a6 }
            if (r14 == 0) goto L_0x008c
            org.springframework.http.MediaType r3 = APPLICATION_JAVASCRIPT     // Catch:{ JSONException -> 0x00a6 }
            r0.setContentType(r3)     // Catch:{ JSONException -> 0x00a6 }
        L_0x008c:
            com.alibaba.fastjson.support.config.FastJsonConfig r3 = r1.fastJsonConfig     // Catch:{ JSONException -> 0x00a6 }
            boolean r3 = r3.isWriteContentLength()     // Catch:{ JSONException -> 0x00a6 }
            if (r3 == 0) goto L_0x0098
            long r3 = (long) r2     // Catch:{ JSONException -> 0x00a6 }
            r0.setContentLength(r3)     // Catch:{ JSONException -> 0x00a6 }
        L_0x0098:
            java.io.OutputStream r3 = r17.getBody()     // Catch:{ JSONException -> 0x00a6 }
            r10.writeTo(r3)     // Catch:{ JSONException -> 0x00a6 }
            r10.close()
            return
        L_0x00a4:
            r0 = move-exception
            goto L_0x00c2
        L_0x00a6:
            r0 = move-exception
            org.springframework.http.converter.HttpMessageNotWritableException r2 = new org.springframework.http.converter.HttpMessageNotWritableException     // Catch:{ all -> 0x00a4 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a4 }
            r3.<init>()     // Catch:{ all -> 0x00a4 }
            java.lang.String r4 = "Could not write JSON: "
            r3.append(r4)     // Catch:{ all -> 0x00a4 }
            java.lang.String r4 = r0.getMessage()     // Catch:{ all -> 0x00a4 }
            r3.append(r4)     // Catch:{ all -> 0x00a4 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00a4 }
            r2.<init>(r3, r0)     // Catch:{ all -> 0x00a4 }
            throw r2     // Catch:{ all -> 0x00a4 }
        L_0x00c2:
            r10.close()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter.writeInternal(java.lang.Object, org.springframework.http.HttpOutputMessage):void");
    }

    private Object strangeCodeForJackson(Object obj) {
        if (obj == null || !"com.fasterxml.jackson.databind.node.ObjectNode".equals(obj.getClass().getName())) {
            return obj;
        }
        return obj.toString();
    }

    /* access modifiers changed from: protected */
    public Type getType(Type type, Class<?> contextClass) {
        if (Spring4TypeResolvableHelper.isSupport()) {
            return Spring4TypeResolvableHelper.getType(type, contextClass);
        }
        return type;
    }

    public static class Spring4TypeResolvableHelper {
        private static boolean hasClazzResolvableType;

        private Spring4TypeResolvableHelper() {
        }

        static {
            try {
                Class.forName("org.springframework.core.ResolvableType");
                hasClazzResolvableType = true;
            } catch (ClassNotFoundException e) {
                hasClazzResolvableType = false;
            }
        }

        /* access modifiers changed from: private */
        public static boolean isSupport() {
            return hasClazzResolvableType;
        }

        /* access modifiers changed from: private */
        public static Type getType(Type type, Class<?> contextClass) {
            if (contextClass != null) {
                ResolvableType resolvedType = ResolvableType.forType(type);
                if (type instanceof TypeVariable) {
                    ResolvableType resolvedTypeVariable = resolveVariable((TypeVariable) type, ResolvableType.forClass(contextClass));
                    if (resolvedTypeVariable != ResolvableType.NONE) {
                        return resolvedTypeVariable.resolve();
                    }
                } else if ((type instanceof ParameterizedType) && resolvedType.hasUnresolvableGenerics()) {
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    Class<?>[] generics = new Class[parameterizedType.getActualTypeArguments().length];
                    Type[] typeArguments = parameterizedType.getActualTypeArguments();
                    for (int i = 0; i < typeArguments.length; i++) {
                        Type typeArgument = typeArguments[i];
                        if (typeArgument instanceof TypeVariable) {
                            ResolvableType resolvedTypeArgument = resolveVariable((TypeVariable) typeArgument, ResolvableType.forClass(contextClass));
                            if (resolvedTypeArgument != ResolvableType.NONE) {
                                generics[i] = resolvedTypeArgument.resolve();
                            } else {
                                generics[i] = ResolvableType.forType(typeArgument).resolve();
                            }
                        } else {
                            generics[i] = ResolvableType.forType(typeArgument).resolve();
                        }
                    }
                    return ResolvableType.forClassWithGenerics(resolvedType.getRawClass(), generics).getType();
                }
            }
            return type;
        }

        private static ResolvableType resolveVariable(TypeVariable<?> typeVariable, ResolvableType contextType) {
            if (contextType.hasGenerics()) {
                ResolvableType resolvedType = ResolvableType.forType(typeVariable, contextType);
                if (resolvedType.resolve() != null) {
                    return resolvedType;
                }
            }
            ResolvableType resolvedType2 = contextType.getSuperType();
            if (resolvedType2 != ResolvableType.NONE) {
                ResolvableType resolvedType3 = resolveVariable(typeVariable, resolvedType2);
                if (resolvedType3.resolve() != null) {
                    return resolvedType3;
                }
            }
            for (ResolvableType ifc : contextType.getInterfaces()) {
                ResolvableType resolvedType4 = resolveVariable(typeVariable, ifc);
                if (resolvedType4.resolve() != null) {
                    return resolvedType4;
                }
            }
            return ResolvableType.NONE;
        }
    }
}
