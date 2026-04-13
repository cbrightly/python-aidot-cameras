package com.alibaba.fastjson.support.jaxrs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

@Consumes({"*/*"})
@Produces({"*/*"})
@Provider
public class FastJsonProvider implements MessageBodyReader<Object>, MessageBodyWriter<Object> {
    public static final Class<?>[] DEFAULT_UNREADABLES = {InputStream.class, Reader.class};
    public static final Class<?>[] DEFAULT_UNWRITABLES = {InputStream.class, OutputStream.class, Writer.class, StreamingOutput.class, Response.class};
    @Deprecated
    protected Charset charset;
    private Class<?>[] clazzes;
    @Deprecated
    protected String dateFormat;
    private FastJsonConfig fastJsonConfig;
    @Deprecated
    protected SerializerFeature[] features;
    @Deprecated
    protected SerializeFilter[] filters;
    private boolean pretty;
    @Context
    protected Providers providers;

    public FastJsonConfig getFastJsonConfig() {
        return this.fastJsonConfig;
    }

    public void setFastJsonConfig(FastJsonConfig fastJsonConfig2) {
        this.fastJsonConfig = fastJsonConfig2;
    }

    public FastJsonProvider() {
        this.charset = Charset.forName("UTF-8");
        this.features = new SerializerFeature[0];
        this.filters = new SerializeFilter[0];
        this.fastJsonConfig = new FastJsonConfig();
        this.clazzes = null;
    }

    public FastJsonProvider(Class<?>[] clazzes2) {
        this.charset = Charset.forName("UTF-8");
        this.features = new SerializerFeature[0];
        this.filters = new SerializeFilter[0];
        this.fastJsonConfig = new FastJsonConfig();
        this.clazzes = null;
        this.clazzes = clazzes2;
    }

    public FastJsonProvider setPretty(boolean p) {
        this.pretty = p;
        return this;
    }

    @Deprecated
    public FastJsonProvider(String charset2) {
        this.charset = Charset.forName("UTF-8");
        this.features = new SerializerFeature[0];
        this.filters = new SerializeFilter[0];
        FastJsonConfig fastJsonConfig2 = new FastJsonConfig();
        this.fastJsonConfig = fastJsonConfig2;
        this.clazzes = null;
        fastJsonConfig2.setCharset(Charset.forName(charset2));
    }

    @Deprecated
    public Charset getCharset() {
        return this.fastJsonConfig.getCharset();
    }

    @Deprecated
    public void setCharset(Charset charset2) {
        this.fastJsonConfig.setCharset(charset2);
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

    /* access modifiers changed from: protected */
    public boolean isAssignableFrom(Class<?> type, Class<?>[] classes) {
        if (type == null) {
            return false;
        }
        for (Class<?> cls : classes) {
            if (cls.isAssignableFrom(type)) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isValidType(Class<?> type, Annotation[] classAnnotations) {
        if (type == null) {
            return false;
        }
        Class<?>[] clsArr = this.clazzes;
        if (clsArr == null) {
            return true;
        }
        for (Class<?> cls : clsArr) {
            if (cls == type) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean hasMatchingMediaType(MediaType mediaType) {
        if (mediaType == null) {
            return true;
        }
        String subtype = mediaType.getSubtype();
        if ("json".equalsIgnoreCase(subtype) || subtype.endsWith("+json") || "javascript".equals(subtype) || "x-javascript".equals(subtype) || "x-json".equals(subtype) || "x-www-form-urlencoded".equalsIgnoreCase(subtype) || subtype.endsWith("x-www-form-urlencoded")) {
            return true;
        }
        return false;
    }

    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        if (hasMatchingMediaType(mediaType) && isAssignableFrom(type, DEFAULT_UNWRITABLES)) {
            return isValidType(type, annotations);
        }
        return false;
    }

    public long getSize(Object t, Class<?> cls, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    /* JADX WARNING: type inference failed for: r6v4, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeTo(java.lang.Object r15, java.lang.Class<?> r16, java.lang.reflect.Type r17, java.lang.annotation.Annotation[] r18, javax.ws.rs.core.MediaType r19, javax.ws.rs.core.MultivaluedMap<java.lang.String, java.lang.Object> r20, java.io.OutputStream r21) {
        /*
            r14 = this;
            r1 = r14
            r2 = r16
            r3 = r19
            com.alibaba.fastjson.support.config.FastJsonConfig r4 = r14.locateConfigProvider(r2, r3)
            com.alibaba.fastjson.serializer.SerializerFeature[] r0 = r4.getSerializerFeatures()
            boolean r5 = r1.pretty
            if (r5 == 0) goto L_0x0037
            if (r0 != 0) goto L_0x001d
            r5 = 1
            com.alibaba.fastjson.serializer.SerializerFeature[] r5 = new com.alibaba.fastjson.serializer.SerializerFeature[r5]
            r6 = 0
            com.alibaba.fastjson.serializer.SerializerFeature r7 = com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat
            r5[r6] = r7
            r0 = r5
            goto L_0x0032
        L_0x001d:
            java.util.ArrayList r5 = new java.util.ArrayList
            java.util.List r6 = java.util.Arrays.asList(r0)
            r5.<init>(r6)
            com.alibaba.fastjson.serializer.SerializerFeature r6 = com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat
            r5.add(r6)
            java.lang.Object[] r6 = r5.toArray(r0)
            r0 = r6
            com.alibaba.fastjson.serializer.SerializerFeature[] r0 = (com.alibaba.fastjson.serializer.SerializerFeature[]) r0
        L_0x0032:
            r4.setSerializerFeatures(r0)
            r5 = r0
            goto L_0x0038
        L_0x0037:
            r5 = r0
        L_0x0038:
            java.nio.charset.Charset r7 = r4.getCharset()     // Catch:{ JSONException -> 0x005a }
            com.alibaba.fastjson.serializer.SerializeConfig r9 = r4.getSerializeConfig()     // Catch:{ JSONException -> 0x005a }
            com.alibaba.fastjson.serializer.SerializeFilter[] r10 = r4.getSerializeFilters()     // Catch:{ JSONException -> 0x005a }
            java.lang.String r11 = r4.getDateFormat()     // Catch:{ JSONException -> 0x005a }
            int r12 = com.alibaba.fastjson.JSON.DEFAULT_GENERATE_FEATURE     // Catch:{ JSONException -> 0x005a }
            com.alibaba.fastjson.serializer.SerializerFeature[] r13 = r4.getSerializerFeatures()     // Catch:{ JSONException -> 0x005a }
            r6 = r21
            r8 = r15
            com.alibaba.fastjson.JSON.writeJSONStringWithFastJsonConfig(r6, r7, r8, r9, r10, r11, r12, r13)     // Catch:{ JSONException -> 0x005a }
            r21.flush()     // Catch:{ JSONException -> 0x005a }
            return
        L_0x005a:
            r0 = move-exception
            javax.ws.rs.WebApplicationException r6 = new javax.ws.rs.WebApplicationException
            r6.<init>(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.support.jaxrs.FastJsonProvider.writeTo(java.lang.Object, java.lang.Class, java.lang.reflect.Type, java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap, java.io.OutputStream):void");
    }

    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        if (hasMatchingMediaType(mediaType) && isAssignableFrom(type, DEFAULT_UNREADABLES)) {
            return isValidType(type, annotations);
        }
        return false;
    }

    public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap, InputStream entityStream) {
        try {
            FastJsonConfig fastJsonConfig2 = locateConfigProvider(type, mediaType);
            return JSON.parseObject(entityStream, fastJsonConfig2.getCharset(), genericType, fastJsonConfig2.getParserConfig(), fastJsonConfig2.getParseProcess(), JSON.DEFAULT_PARSER_FEATURE, fastJsonConfig2.getFeatures());
        } catch (JSONException ex) {
            throw new WebApplicationException(ex);
        }
    }

    /* access modifiers changed from: protected */
    public FastJsonConfig locateConfigProvider(Class<?> type, MediaType mediaType) {
        Class<FastJsonConfig> cls = FastJsonConfig.class;
        Providers providers2 = this.providers;
        if (providers2 != null) {
            ContextResolver<FastJsonConfig> resolver = providers2.getContextResolver(cls, mediaType);
            if (resolver == null) {
                resolver = this.providers.getContextResolver(cls, (MediaType) null);
            }
            if (resolver != null) {
                return (FastJsonConfig) resolver.getContext(type);
            }
        }
        return this.fastJsonConfig;
    }
}
