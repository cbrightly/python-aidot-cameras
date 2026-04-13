package com.alibaba.fastjson.serializer;

import androidx.exifinterface.media.ExifInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.GZIPOutputStream;
import org.glassfish.grizzly.http.GZipContentEncoding;

public class JSONSerializer extends SerializeFilterable {
    protected final SerializeConfig config;
    protected SerialContext context;
    private DateFormat dateFormat;
    private String dateFormatPattern;
    private String fastJsonConfigDateFormatPattern;
    private String indent;
    private int indentCount;
    protected Locale locale;
    public final SerializeWriter out;
    protected IdentityHashMap<Object, SerialContext> references;
    protected TimeZone timeZone;

    public JSONSerializer() {
        this(new SerializeWriter(), SerializeConfig.getGlobalInstance());
    }

    public JSONSerializer(SerializeWriter out2) {
        this(out2, SerializeConfig.getGlobalInstance());
    }

    public JSONSerializer(SerializeConfig config2) {
        this(new SerializeWriter(), config2);
    }

    public JSONSerializer(SerializeWriter out2, SerializeConfig config2) {
        this.indentCount = 0;
        this.indent = "\t";
        this.references = null;
        this.timeZone = JSON.defaultTimeZone;
        this.locale = JSON.defaultLocale;
        this.out = out2;
        this.config = config2;
    }

    public String getDateFormatPattern() {
        DateFormat dateFormat2 = this.dateFormat;
        if (dateFormat2 instanceof SimpleDateFormat) {
            return ((SimpleDateFormat) dateFormat2).toPattern();
        }
        return this.dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        String str;
        if (this.dateFormat == null && (str = this.dateFormatPattern) != null) {
            this.dateFormat = generateDateFormat(str);
        }
        return this.dateFormat;
    }

    private DateFormat generateDateFormat(String dateFormatPattern2) {
        DateFormat dateFormat2 = new SimpleDateFormat(dateFormatPattern2, this.locale);
        dateFormat2.setTimeZone(this.timeZone);
        return dateFormat2;
    }

    public void setDateFormat(DateFormat dateFormat2) {
        this.dateFormat = dateFormat2;
        if (this.dateFormatPattern != null) {
            this.dateFormatPattern = null;
        }
    }

    public void setDateFormat(String dateFormat2) {
        this.dateFormatPattern = dateFormat2;
        if (this.dateFormat != null) {
            this.dateFormat = null;
        }
    }

    public void setFastJsonConfigDateFormatPattern(String dateFormatPattern2) {
        this.fastJsonConfigDateFormatPattern = dateFormatPattern2;
    }

    public String getFastJsonConfigDateFormatPattern() {
        return this.fastJsonConfigDateFormatPattern;
    }

    public SerialContext getContext() {
        return this.context;
    }

    public void setContext(SerialContext context2) {
        this.context = context2;
    }

    public void setContext(SerialContext parent, Object object, Object fieldName, int features) {
        setContext(parent, object, fieldName, features, 0);
    }

    public void setContext(SerialContext parent, Object object, Object fieldName, int features, int fieldFeatures) {
        if (!this.out.disableCircularReferenceDetect) {
            this.context = new SerialContext(parent, object, fieldName, features, fieldFeatures);
            if (this.references == null) {
                this.references = new IdentityHashMap<>();
            }
            this.references.put(object, this.context);
        }
    }

    public void setContext(Object object, Object fieldName) {
        setContext(this.context, object, fieldName, 0);
    }

    public void popContext() {
        SerialContext serialContext = this.context;
        if (serialContext != null) {
            this.context = serialContext.parent;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0016, code lost:
        r0 = r2.context;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean isWriteClassName(java.lang.reflect.Type r3, java.lang.Object r4) {
        /*
            r2 = this;
            com.alibaba.fastjson.serializer.SerializeWriter r0 = r2.out
            com.alibaba.fastjson.serializer.SerializerFeature r1 = com.alibaba.fastjson.serializer.SerializerFeature.WriteClassName
            boolean r0 = r0.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r1)
            if (r0 == 0) goto L_0x0020
            if (r3 != 0) goto L_0x001e
            com.alibaba.fastjson.serializer.SerializeWriter r0 = r2.out
            com.alibaba.fastjson.serializer.SerializerFeature r1 = com.alibaba.fastjson.serializer.SerializerFeature.NotWriteRootClassName
            boolean r0 = r0.isEnabled((com.alibaba.fastjson.serializer.SerializerFeature) r1)
            if (r0 == 0) goto L_0x001e
            com.alibaba.fastjson.serializer.SerialContext r0 = r2.context
            if (r0 == 0) goto L_0x0020
            com.alibaba.fastjson.serializer.SerialContext r0 = r0.parent
            if (r0 == 0) goto L_0x0020
        L_0x001e:
            r0 = 1
            goto L_0x0021
        L_0x0020:
            r0 = 0
        L_0x0021:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.JSONSerializer.isWriteClassName(java.lang.reflect.Type, java.lang.Object):boolean");
    }

    public boolean containsReference(Object value) {
        SerialContext refContext;
        IdentityHashMap<Object, SerialContext> identityHashMap = this.references;
        if (identityHashMap == null || (refContext = identityHashMap.get(value)) == null || value == Collections.emptyMap()) {
            return false;
        }
        Object fieldName = refContext.fieldName;
        if (fieldName == null || (fieldName instanceof Integer) || (fieldName instanceof String)) {
            return true;
        }
        return false;
    }

    public void writeReference(Object object) {
        SerialContext context2 = this.context;
        if (object == context2.object) {
            this.out.write("{\"$ref\":\"@\"}");
            return;
        }
        SerialContext parentContext = context2.parent;
        if (parentContext == null || object != parentContext.object) {
            SerialContext rootContext = context2;
            while (rootContext.parent != null) {
                rootContext = rootContext.parent;
            }
            if (object == rootContext.object) {
                this.out.write("{\"$ref\":\"$\"}");
                return;
            }
            this.out.write("{\"$ref\":\"");
            this.out.write(this.references.get(object).toString());
            this.out.write("\"}");
            return;
        }
        this.out.write("{\"$ref\":\"..\"}");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001e, code lost:
        r0 = r2.contextValueFilters;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        r0 = r1.contextValueFilters;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0014, code lost:
        r0 = r2.valueFilters;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean checkValue(com.alibaba.fastjson.serializer.SerializeFilterable r2) {
        /*
            r1 = this;
            java.util.List<com.alibaba.fastjson.serializer.ValueFilter> r0 = r1.valueFilters
            if (r0 == 0) goto L_0x000a
            int r0 = r0.size()
            if (r0 > 0) goto L_0x002e
        L_0x000a:
            java.util.List<com.alibaba.fastjson.serializer.ContextValueFilter> r0 = r1.contextValueFilters
            if (r0 == 0) goto L_0x0014
            int r0 = r0.size()
            if (r0 > 0) goto L_0x002e
        L_0x0014:
            java.util.List<com.alibaba.fastjson.serializer.ValueFilter> r0 = r2.valueFilters
            if (r0 == 0) goto L_0x001e
            int r0 = r0.size()
            if (r0 > 0) goto L_0x002e
        L_0x001e:
            java.util.List<com.alibaba.fastjson.serializer.ContextValueFilter> r0 = r2.contextValueFilters
            if (r0 == 0) goto L_0x0028
            int r0 = r0.size()
            if (r0 > 0) goto L_0x002e
        L_0x0028:
            com.alibaba.fastjson.serializer.SerializeWriter r0 = r1.out
            boolean r0 = r0.writeNonStringValueAsString
            if (r0 == 0) goto L_0x0030
        L_0x002e:
            r0 = 1
            goto L_0x0031
        L_0x0030:
            r0 = 0
        L_0x0031:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.JSONSerializer.checkValue(com.alibaba.fastjson.serializer.SerializeFilterable):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        r0 = r2.nameFilters;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean hasNameFilters(com.alibaba.fastjson.serializer.SerializeFilterable r2) {
        /*
            r1 = this;
            java.util.List<com.alibaba.fastjson.serializer.NameFilter> r0 = r1.nameFilters
            if (r0 == 0) goto L_0x000a
            int r0 = r0.size()
            if (r0 > 0) goto L_0x0014
        L_0x000a:
            java.util.List<com.alibaba.fastjson.serializer.NameFilter> r0 = r2.nameFilters
            if (r0 == 0) goto L_0x0016
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x0016
        L_0x0014:
            r0 = 1
            goto L_0x0017
        L_0x0016:
            r0 = 0
        L_0x0017:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.JSONSerializer.hasNameFilters(com.alibaba.fastjson.serializer.SerializeFilterable):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        r0 = r2.propertyFilters;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean hasPropertyFilters(com.alibaba.fastjson.serializer.SerializeFilterable r2) {
        /*
            r1 = this;
            java.util.List<com.alibaba.fastjson.serializer.PropertyFilter> r0 = r1.propertyFilters
            if (r0 == 0) goto L_0x000a
            int r0 = r0.size()
            if (r0 > 0) goto L_0x0014
        L_0x000a:
            java.util.List<com.alibaba.fastjson.serializer.PropertyFilter> r0 = r2.propertyFilters
            if (r0 == 0) goto L_0x0016
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x0016
        L_0x0014:
            r0 = 1
            goto L_0x0017
        L_0x0016:
            r0 = 0
        L_0x0017:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.JSONSerializer.hasPropertyFilters(com.alibaba.fastjson.serializer.SerializeFilterable):boolean");
    }

    public int getIndentCount() {
        return this.indentCount;
    }

    public void incrementIndent() {
        this.indentCount++;
    }

    public void decrementIdent() {
        this.indentCount--;
    }

    public void println() {
        this.out.write(10);
        for (int i = 0; i < this.indentCount; i++) {
            this.out.write(this.indent);
        }
    }

    public SerializeWriter getWriter() {
        return this.out;
    }

    public String toString() {
        return this.out.toString();
    }

    public void config(SerializerFeature feature, boolean state) {
        this.out.config(feature, state);
    }

    public boolean isEnabled(SerializerFeature feature) {
        return this.out.isEnabled(feature);
    }

    public void writeNull() {
        this.out.writeNull();
    }

    public SerializeConfig getMapping() {
        return this.config;
    }

    public static void write(Writer out2, Object object) {
        SerializeWriter writer = new SerializeWriter();
        try {
            new JSONSerializer(writer).write(object);
            writer.writeTo(out2);
            writer.close();
        } catch (IOException ex) {
            throw new JSONException(ex.getMessage(), ex);
        } catch (Throwable th) {
            writer.close();
            throw th;
        }
    }

    public static void write(SerializeWriter out2, Object object) {
        new JSONSerializer(out2).write(object);
    }

    public final void write(Object object) {
        if (object == null) {
            this.out.writeNull();
            return;
        }
        try {
            getObjectWriter(object.getClass()).write(this, object, (Object) null, (Type) null, 0);
        } catch (IOException e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    public final void writeAs(Object object, Class type) {
        if (object == null) {
            this.out.writeNull();
            return;
        }
        try {
            getObjectWriter(type).write(this, object, (Object) null, (Type) null, 0);
        } catch (IOException e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    public final void writeWithFieldName(Object object, Object fieldName) {
        writeWithFieldName(object, fieldName, (Type) null, 0);
    }

    /* access modifiers changed from: protected */
    public final void writeKeyValue(char seperator, String key, Object value) {
        if (seperator != 0) {
            this.out.write((int) seperator);
        }
        this.out.writeFieldName(key);
        write(value);
    }

    public final void writeWithFieldName(Object object, Object fieldName, Type fieldType, int fieldFeatures) {
        if (object == null) {
            try {
                this.out.writeNull();
            } catch (IOException e) {
                throw new JSONException(e.getMessage(), e);
            }
        } else {
            getObjectWriter(object.getClass()).write(this, object, fieldName, fieldType, fieldFeatures);
        }
    }

    public final void writeWithFormat(Object object, String format) {
        GZIPOutputStream gzipOut;
        if (object instanceof Date) {
            if ("unixtime".equals(format)) {
                this.out.writeInt((int) (((Date) object).getTime() / 1000));
            } else if ("millis".equals(format)) {
                this.out.writeLong(((Date) object).getTime());
            } else {
                DateFormat dateFormat2 = getDateFormat();
                if (dateFormat2 == null) {
                    if (format != null) {
                        try {
                            dateFormat2 = generateDateFormat(format);
                        } catch (IllegalArgumentException e) {
                            dateFormat2 = generateDateFormat(format.replaceAll(ExifInterface.GPS_DIRECTION_TRUE, "'T'"));
                        }
                    } else {
                        String str = this.fastJsonConfigDateFormatPattern;
                        if (str != null) {
                            dateFormat2 = generateDateFormat(str);
                        } else {
                            dateFormat2 = generateDateFormat(JSON.DEFFAULT_DATE_FORMAT);
                        }
                    }
                }
                this.out.writeString(dateFormat2.format((Date) object));
            }
        } else if (object instanceof byte[]) {
            byte[] bytes = (byte[]) object;
            if (GZipContentEncoding.NAME.equals(format) || "gzip,base64".equals(format)) {
                try {
                    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                    if (bytes.length < 512) {
                        gzipOut = new GZIPOutputStream(byteOut, bytes.length);
                    } else {
                        gzipOut = new GZIPOutputStream(byteOut);
                    }
                    gzipOut.write(bytes);
                    gzipOut.finish();
                    this.out.writeByteArray(byteOut.toByteArray());
                    IOUtils.close(gzipOut);
                } catch (IOException ex) {
                    throw new JSONException("write gzipBytes error", ex);
                } catch (Throwable th) {
                    IOUtils.close((Closeable) null);
                    throw th;
                }
            } else if ("hex".equals(format)) {
                this.out.writeHex(bytes);
            } else {
                this.out.writeByteArray(bytes);
            }
        } else if (object instanceof Collection) {
            Collection collection = (Collection) object;
            Iterator iterator = collection.iterator();
            this.out.write(91);
            for (int i = 0; i < collection.size(); i++) {
                Object item = iterator.next();
                if (i != 0) {
                    this.out.write(44);
                }
                writeWithFormat(item, format);
            }
            this.out.write(93);
        } else {
            write(object);
        }
    }

    public final void write(String text) {
        StringCodec.instance.write(this, text);
    }

    public ObjectSerializer getObjectWriter(Class<?> clazz) {
        return this.config.getObjectWriter(clazz);
    }

    public void close() {
        this.out.close();
    }
}
