package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import io.netty.util.internal.StringUtil;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FieldSerializer implements Comparable<FieldSerializer> {
    protected boolean browserCompatible;
    protected boolean disableCircularReferenceDetect = false;
    private final String double_quoted_fieldPrefix;
    protected int features;
    protected BeanContext fieldContext;
    public final FieldInfo fieldInfo;
    private String format;
    protected boolean persistenceXToMany = false;
    private RuntimeSerializerInfo runtimeInfo;
    protected boolean serializeUsing = false;
    private String single_quoted_fieldPrefix;
    private String un_quoted_fieldPrefix;
    protected boolean writeEnumUsingName = false;
    protected boolean writeEnumUsingToString = false;
    protected final boolean writeNull;

    public FieldSerializer(Class<?> beanType, FieldInfo fieldInfo2) {
        JSONType jsonType;
        boolean z = false;
        this.fieldInfo = fieldInfo2;
        this.fieldContext = new BeanContext(beanType, fieldInfo2);
        if (!(beanType == null || (jsonType = (JSONType) TypeUtils.getAnnotation(beanType, JSONType.class)) == null)) {
            for (SerializerFeature feature : jsonType.serialzeFeatures()) {
                if (feature == SerializerFeature.WriteEnumUsingToString) {
                    this.writeEnumUsingToString = true;
                } else if (feature == SerializerFeature.WriteEnumUsingName) {
                    this.writeEnumUsingName = true;
                } else if (feature == SerializerFeature.DisableCircularReferenceDetect) {
                    this.disableCircularReferenceDetect = true;
                } else {
                    SerializerFeature serializerFeature = SerializerFeature.BrowserCompatible;
                    if (feature == serializerFeature) {
                        this.features = serializerFeature.mask | this.features;
                        this.browserCompatible = true;
                    } else {
                        SerializerFeature serializerFeature2 = SerializerFeature.WriteMapNullValue;
                        if (feature == serializerFeature2) {
                            this.features = serializerFeature2.mask | this.features;
                        }
                    }
                }
            }
        }
        fieldInfo2.setAccessible();
        this.double_quoted_fieldPrefix = StringUtil.DOUBLE_QUOTE + fieldInfo2.name + "\":";
        boolean writeNull2 = false;
        JSONField annotation = fieldInfo2.getAnnotation();
        if (annotation != null) {
            SerializerFeature[] serialzeFeatures = annotation.serialzeFeatures();
            int length = serialzeFeatures.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                } else if ((serialzeFeatures[i].getMask() & SerializerFeature.WRITE_MAP_NULL_FEATURES) != 0) {
                    writeNull2 = true;
                    break;
                } else {
                    i++;
                }
            }
            String format2 = annotation.format();
            this.format = format2;
            if (format2.trim().length() == 0) {
                this.format = null;
            }
            for (SerializerFeature feature2 : annotation.serialzeFeatures()) {
                if (feature2 == SerializerFeature.WriteEnumUsingToString) {
                    this.writeEnumUsingToString = true;
                } else if (feature2 == SerializerFeature.WriteEnumUsingName) {
                    this.writeEnumUsingName = true;
                } else if (feature2 == SerializerFeature.DisableCircularReferenceDetect) {
                    this.disableCircularReferenceDetect = true;
                } else if (feature2 == SerializerFeature.BrowserCompatible) {
                    this.browserCompatible = true;
                }
            }
            this.features |= SerializerFeature.of(annotation.serialzeFeatures());
        }
        this.writeNull = writeNull2;
        this.persistenceXToMany = (TypeUtils.isAnnotationPresentOneToMany(fieldInfo2.method) || TypeUtils.isAnnotationPresentManyToMany(fieldInfo2.method)) ? true : z;
    }

    public void writePrefix(JSONSerializer serializer) {
        SerializeWriter out = serializer.out;
        if (!out.quoteFieldNames) {
            if (this.un_quoted_fieldPrefix == null) {
                this.un_quoted_fieldPrefix = this.fieldInfo.name + ":";
            }
            out.write(this.un_quoted_fieldPrefix);
        } else if (SerializerFeature.isEnabled(out.features, this.fieldInfo.serialzeFeatures, SerializerFeature.UseSingleQuotes)) {
            if (this.single_quoted_fieldPrefix == null) {
                this.single_quoted_fieldPrefix = '\'' + this.fieldInfo.name + "':";
            }
            out.write(this.single_quoted_fieldPrefix);
        } else {
            out.write(this.double_quoted_fieldPrefix);
        }
    }

    public Object getPropertyValueDirect(Object object) {
        Object fieldValue = this.fieldInfo.get(object);
        if (!this.persistenceXToMany || TypeUtils.isHibernateInitialized(fieldValue)) {
            return fieldValue;
        }
        return null;
    }

    public Object getPropertyValue(Object object) {
        Class<?> cls;
        Object propertyValue = this.fieldInfo.get(object);
        if (this.format == null || propertyValue == null || ((cls = this.fieldInfo.fieldClass) != Date.class && cls != java.sql.Date.class)) {
            return propertyValue;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(this.format, JSON.defaultLocale);
        dateFormat.setTimeZone(JSON.defaultTimeZone);
        return dateFormat.format(propertyValue);
    }

    public int compareTo(FieldSerializer o) {
        return this.fieldInfo.compareTo(o.fieldInfo);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v53, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: com.alibaba.fastjson.serializer.ObjectSerializer} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeValue(com.alibaba.fastjson.serializer.JSONSerializer r13, java.lang.Object r14) {
        /*
            r12 = this;
            com.alibaba.fastjson.serializer.FieldSerializer$RuntimeSerializerInfo r0 = r12.runtimeInfo
            if (r0 != 0) goto L_0x0092
            if (r14 != 0) goto L_0x003b
            com.alibaba.fastjson.util.FieldInfo r0 = r12.fieldInfo
            java.lang.Class<?> r0 = r0.fieldClass
            java.lang.Class r1 = java.lang.Byte.TYPE
            if (r0 != r1) goto L_0x0011
            java.lang.Class<java.lang.Byte> r0 = java.lang.Byte.class
            goto L_0x003f
        L_0x0011:
            java.lang.Class r1 = java.lang.Short.TYPE
            if (r0 != r1) goto L_0x0018
            java.lang.Class<java.lang.Short> r0 = java.lang.Short.class
            goto L_0x003f
        L_0x0018:
            java.lang.Class r1 = java.lang.Integer.TYPE
            if (r0 != r1) goto L_0x001f
            java.lang.Class<java.lang.Integer> r0 = java.lang.Integer.class
            goto L_0x003f
        L_0x001f:
            java.lang.Class r1 = java.lang.Long.TYPE
            if (r0 != r1) goto L_0x0026
            java.lang.Class<java.lang.Long> r0 = java.lang.Long.class
            goto L_0x003f
        L_0x0026:
            java.lang.Class r1 = java.lang.Float.TYPE
            if (r0 != r1) goto L_0x002d
            java.lang.Class<java.lang.Float> r0 = java.lang.Float.class
            goto L_0x003f
        L_0x002d:
            java.lang.Class r1 = java.lang.Double.TYPE
            if (r0 != r1) goto L_0x0034
            java.lang.Class<java.lang.Double> r0 = java.lang.Double.class
            goto L_0x003f
        L_0x0034:
            java.lang.Class r1 = java.lang.Boolean.TYPE
            if (r0 != r1) goto L_0x003f
            java.lang.Class<java.lang.Boolean> r0 = java.lang.Boolean.class
            goto L_0x003f
        L_0x003b:
            java.lang.Class r0 = r14.getClass()
        L_0x003f:
            r1 = 0
            com.alibaba.fastjson.util.FieldInfo r2 = r12.fieldInfo
            com.alibaba.fastjson.annotation.JSONField r2 = r2.getAnnotation()
            if (r2 == 0) goto L_0x005f
            java.lang.Class r3 = r2.serializeUsing()
            java.lang.Class<java.lang.Void> r4 = java.lang.Void.class
            if (r3 == r4) goto L_0x005f
            java.lang.Class r3 = r2.serializeUsing()
            java.lang.Object r3 = r3.newInstance()
            r1 = r3
            com.alibaba.fastjson.serializer.ObjectSerializer r1 = (com.alibaba.fastjson.serializer.ObjectSerializer) r1
            r3 = 1
            r12.serializeUsing = r3
            goto L_0x008b
        L_0x005f:
            java.lang.String r3 = r12.format
            if (r3 == 0) goto L_0x0085
            java.lang.Class r3 = java.lang.Double.TYPE
            if (r0 == r3) goto L_0x007d
            java.lang.Class<java.lang.Double> r3 = java.lang.Double.class
            if (r0 != r3) goto L_0x006c
            goto L_0x007d
        L_0x006c:
            java.lang.Class r3 = java.lang.Float.TYPE
            if (r0 == r3) goto L_0x0074
            java.lang.Class<java.lang.Float> r3 = java.lang.Float.class
            if (r0 != r3) goto L_0x0085
        L_0x0074:
            com.alibaba.fastjson.serializer.FloatCodec r3 = new com.alibaba.fastjson.serializer.FloatCodec
            java.lang.String r4 = r12.format
            r3.<init>((java.lang.String) r4)
            r1 = r3
            goto L_0x0085
        L_0x007d:
            com.alibaba.fastjson.serializer.DoubleSerializer r3 = new com.alibaba.fastjson.serializer.DoubleSerializer
            java.lang.String r4 = r12.format
            r3.<init>((java.lang.String) r4)
            r1 = r3
        L_0x0085:
            if (r1 != 0) goto L_0x008b
            com.alibaba.fastjson.serializer.ObjectSerializer r1 = r13.getObjectWriter(r0)
        L_0x008b:
            com.alibaba.fastjson.serializer.FieldSerializer$RuntimeSerializerInfo r3 = new com.alibaba.fastjson.serializer.FieldSerializer$RuntimeSerializerInfo
            r3.<init>(r1, r0)
            r12.runtimeInfo = r3
        L_0x0092:
            com.alibaba.fastjson.serializer.FieldSerializer$RuntimeSerializerInfo r0 = r12.runtimeInfo
            boolean r1 = r12.disableCircularReferenceDetect
            if (r1 == 0) goto L_0x00a2
            com.alibaba.fastjson.util.FieldInfo r1 = r12.fieldInfo
            int r1 = r1.serialzeFeatures
            com.alibaba.fastjson.serializer.SerializerFeature r2 = com.alibaba.fastjson.serializer.SerializerFeature.DisableCircularReferenceDetect
            int r2 = r2.mask
            r1 = r1 | r2
            goto L_0x00a6
        L_0x00a2:
            com.alibaba.fastjson.util.FieldInfo r1 = r12.fieldInfo
            int r1 = r1.serialzeFeatures
        L_0x00a6:
            int r2 = r12.features
            r1 = r1 | r2
            if (r14 != 0) goto L_0x012a
            com.alibaba.fastjson.serializer.SerializeWriter r2 = r13.out
            com.alibaba.fastjson.util.FieldInfo r3 = r12.fieldInfo
            java.lang.Class<?> r3 = r3.fieldClass
            java.lang.Class<java.lang.Object> r4 = java.lang.Object.class
            if (r3 != r4) goto L_0x00c1
            int r3 = com.alibaba.fastjson.serializer.SerializerFeature.WRITE_MAP_NULL_FEATURES
            boolean r3 = r2.isEnabled((int) r3)
            if (r3 == 0) goto L_0x00c1
            r2.writeNull()
            return
        L_0x00c1:
            java.lang.Class<?> r9 = r0.runtimeFieldClass
            java.lang.Class<java.lang.Number> r3 = java.lang.Number.class
            boolean r3 = r3.isAssignableFrom(r9)
            if (r3 == 0) goto L_0x00d5
            int r3 = r12.features
            com.alibaba.fastjson.serializer.SerializerFeature r4 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullNumberAsZero
            int r4 = r4.mask
            r2.writeNull(r3, r4)
            return
        L_0x00d5:
            java.lang.Class<java.lang.String> r3 = java.lang.String.class
            if (r3 != r9) goto L_0x00e3
            int r3 = r12.features
            com.alibaba.fastjson.serializer.SerializerFeature r4 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullStringAsEmpty
            int r4 = r4.mask
            r2.writeNull(r3, r4)
            return
        L_0x00e3:
            java.lang.Class<java.lang.Boolean> r3 = java.lang.Boolean.class
            if (r3 != r9) goto L_0x00f1
            int r3 = r12.features
            com.alibaba.fastjson.serializer.SerializerFeature r4 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullBooleanAsFalse
            int r4 = r4.mask
            r2.writeNull(r3, r4)
            return
        L_0x00f1:
            java.lang.Class<java.util.Collection> r3 = java.util.Collection.class
            boolean r3 = r3.isAssignableFrom(r9)
            if (r3 != 0) goto L_0x0120
            boolean r3 = r9.isArray()
            if (r3 == 0) goto L_0x0100
            goto L_0x0120
        L_0x0100:
            com.alibaba.fastjson.serializer.ObjectSerializer r10 = r0.fieldSerializer
            int r3 = com.alibaba.fastjson.serializer.SerializerFeature.WRITE_MAP_NULL_FEATURES
            boolean r3 = r2.isEnabled((int) r3)
            if (r3 == 0) goto L_0x0112
            boolean r3 = r10 instanceof com.alibaba.fastjson.serializer.JavaBeanSerializer
            if (r3 == 0) goto L_0x0112
            r2.writeNull()
            return
        L_0x0112:
            r5 = 0
            com.alibaba.fastjson.util.FieldInfo r3 = r12.fieldInfo
            java.lang.String r6 = r3.name
            java.lang.reflect.Type r7 = r3.fieldType
            r3 = r10
            r4 = r13
            r8 = r1
            r3.write(r4, r5, r6, r7, r8)
            return
        L_0x0120:
            int r3 = r12.features
            com.alibaba.fastjson.serializer.SerializerFeature r4 = com.alibaba.fastjson.serializer.SerializerFeature.WriteNullListAsEmpty
            int r4 = r4.mask
            r2.writeNull(r3, r4)
            return
        L_0x012a:
            com.alibaba.fastjson.util.FieldInfo r2 = r12.fieldInfo
            boolean r2 = r2.isEnum
            if (r2 == 0) goto L_0x0152
            boolean r2 = r12.writeEnumUsingName
            if (r2 == 0) goto L_0x0141
            com.alibaba.fastjson.serializer.SerializeWriter r2 = r13.out
            r3 = r14
            java.lang.Enum r3 = (java.lang.Enum) r3
            java.lang.String r3 = r3.name()
            r2.writeString((java.lang.String) r3)
            return
        L_0x0141:
            boolean r2 = r12.writeEnumUsingToString
            if (r2 == 0) goto L_0x0152
            com.alibaba.fastjson.serializer.SerializeWriter r2 = r13.out
            r3 = r14
            java.lang.Enum r3 = (java.lang.Enum) r3
            java.lang.String r3 = r3.toString()
            r2.writeString((java.lang.String) r3)
            return
        L_0x0152:
            java.lang.Class r2 = r14.getClass()
            java.lang.Class<?> r3 = r0.runtimeFieldClass
            if (r2 == r3) goto L_0x0165
            boolean r3 = r12.serializeUsing
            if (r3 == 0) goto L_0x015f
            goto L_0x0165
        L_0x015f:
            com.alibaba.fastjson.serializer.ObjectSerializer r3 = r13.getObjectWriter(r2)
            r10 = r3
            goto L_0x0168
        L_0x0165:
            com.alibaba.fastjson.serializer.ObjectSerializer r3 = r0.fieldSerializer
            r10 = r3
        L_0x0168:
            java.lang.String r3 = r12.format
            if (r3 == 0) goto L_0x0185
            boolean r4 = r10 instanceof com.alibaba.fastjson.serializer.DoubleSerializer
            if (r4 != 0) goto L_0x0185
            boolean r4 = r10 instanceof com.alibaba.fastjson.serializer.FloatCodec
            if (r4 != 0) goto L_0x0185
            boolean r4 = r10 instanceof com.alibaba.fastjson.serializer.ContextObjectSerializer
            if (r4 == 0) goto L_0x0181
            r3 = r10
            com.alibaba.fastjson.serializer.ContextObjectSerializer r3 = (com.alibaba.fastjson.serializer.ContextObjectSerializer) r3
            com.alibaba.fastjson.serializer.BeanContext r4 = r12.fieldContext
            r3.write(r13, r14, r4)
            goto L_0x0184
        L_0x0181:
            r13.writeWithFormat(r14, r3)
        L_0x0184:
            return
        L_0x0185:
            com.alibaba.fastjson.util.FieldInfo r3 = r12.fieldInfo
            boolean r4 = r3.unwrapped
            if (r4 == 0) goto L_0x01b3
            boolean r4 = r10 instanceof com.alibaba.fastjson.serializer.JavaBeanSerializer
            if (r4 == 0) goto L_0x019f
            r11 = r10
            com.alibaba.fastjson.serializer.JavaBeanSerializer r11 = (com.alibaba.fastjson.serializer.JavaBeanSerializer) r11
            java.lang.String r6 = r3.name
            java.lang.reflect.Type r7 = r3.fieldType
            r9 = 1
            r3 = r11
            r4 = r13
            r5 = r14
            r8 = r1
            r3.write(r4, r5, r6, r7, r8, r9)
            return
        L_0x019f:
            boolean r4 = r10 instanceof com.alibaba.fastjson.serializer.MapSerializer
            if (r4 == 0) goto L_0x01b3
            r11 = r10
            com.alibaba.fastjson.serializer.MapSerializer r11 = (com.alibaba.fastjson.serializer.MapSerializer) r11
            java.lang.String r6 = r3.name
            java.lang.reflect.Type r7 = r3.fieldType
            r9 = 1
            r3 = r11
            r4 = r13
            r5 = r14
            r8 = r1
            r3.write(r4, r5, r6, r7, r8, r9)
            return
        L_0x01b3:
            int r4 = r12.features
            com.alibaba.fastjson.serializer.SerializerFeature r5 = com.alibaba.fastjson.serializer.SerializerFeature.WriteClassName
            int r5 = r5.mask
            r4 = r4 & r5
            if (r4 == 0) goto L_0x01d4
            java.lang.Class<?> r4 = r3.fieldClass
            if (r2 == r4) goto L_0x01d4
            boolean r4 = r10 instanceof com.alibaba.fastjson.serializer.JavaBeanSerializer
            if (r4 == 0) goto L_0x01d4
            r4 = r10
            com.alibaba.fastjson.serializer.JavaBeanSerializer r4 = (com.alibaba.fastjson.serializer.JavaBeanSerializer) r4
            java.lang.String r6 = r3.name
            java.lang.reflect.Type r7 = r3.fieldType
            r9 = 0
            r3 = r4
            r4 = r13
            r5 = r14
            r8 = r1
            r3.write(r4, r5, r6, r7, r8, r9)
            return
        L_0x01d4:
            boolean r4 = r12.browserCompatible
            if (r4 == 0) goto L_0x0207
            java.lang.Class<?> r3 = r3.fieldClass
            java.lang.Class r4 = java.lang.Long.TYPE
            if (r3 == r4) goto L_0x01e2
            java.lang.Class<java.lang.Long> r4 = java.lang.Long.class
            if (r3 != r4) goto L_0x0207
        L_0x01e2:
            r3 = r14
            java.lang.Long r3 = (java.lang.Long) r3
            long r3 = r3.longValue()
            r5 = 9007199254740991(0x1fffffffffffff, double:4.4501477170144023E-308)
            int r5 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r5 > 0) goto L_0x01fb
            r5 = -9007199254740991(0xffe0000000000001, double:-8.988465674311582E307)
            int r5 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r5 >= 0) goto L_0x0207
        L_0x01fb:
            com.alibaba.fastjson.serializer.SerializeWriter r5 = r13.getWriter()
            java.lang.String r6 = java.lang.Long.toString(r3)
            r5.writeString((java.lang.String) r6)
            return
        L_0x0207:
            com.alibaba.fastjson.util.FieldInfo r3 = r12.fieldInfo
            java.lang.String r6 = r3.name
            java.lang.reflect.Type r7 = r3.fieldType
            r3 = r10
            r4 = r13
            r5 = r14
            r8 = r1
            r3.write(r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.FieldSerializer.writeValue(com.alibaba.fastjson.serializer.JSONSerializer, java.lang.Object):void");
    }

    public static class RuntimeSerializerInfo {
        final ObjectSerializer fieldSerializer;
        final Class<?> runtimeFieldClass;

        public RuntimeSerializerInfo(ObjectSerializer fieldSerializer2, Class<?> runtimeFieldClass2) {
            this.fieldSerializer = fieldSerializer2;
            this.runtimeFieldClass = runtimeFieldClass2;
        }
    }
}
