package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.deserializer.Jdk8DateCodec;
import com.alibaba.fastjson.parser.deserializer.OptionalCodec;
import com.alibaba.fastjson.spi.Module;
import com.alibaba.fastjson.support.moneta.MonetaCodec;
import com.alibaba.fastjson.support.springfox.SwaggerJsonSerializer;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.IdentityHashMap;
import com.alibaba.fastjson.util.ServiceLoader;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.File;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import javax.xml.datatype.XMLGregorianCalendar;
import org.w3c.dom.Node;

public class SerializeConfig {
    private static boolean awtError = false;
    public static final SerializeConfig globalInstance = new SerializeConfig();
    private static boolean guavaError = false;
    private static boolean jdk8Error = false;
    private static boolean jodaError = false;
    private static boolean oracleJdbcError = false;
    private static boolean springfoxError = false;
    private boolean asm;
    private ASMSerializerFactory asmFactory;
    private long[] denyClasses;
    private final boolean fieldBased;
    private final IdentityHashMap<Type, IdentityHashMap<Type, ObjectSerializer>> mixInSerializers;
    private List<Module> modules;
    public PropertyNamingStrategy propertyNamingStrategy;
    private final IdentityHashMap<Type, ObjectSerializer> serializers;
    protected String typeKey;

    public String getTypeKey() {
        return this.typeKey;
    }

    public void setTypeKey(String typeKey2) {
        this.typeKey = typeKey2;
    }

    private final JavaBeanSerializer createASMSerializer(SerializeBeanInfo beanInfo) {
        JavaBeanSerializer serializer = this.asmFactory.createJavaBeanSerializer(beanInfo);
        int i = 0;
        while (true) {
            FieldSerializer[] fieldSerializerArr = serializer.sortedGetters;
            if (i >= fieldSerializerArr.length) {
                return serializer;
            }
            Class<?> fieldClass = fieldSerializerArr[i].fieldInfo.fieldClass;
            if (fieldClass.isEnum() && !(getObjectWriter(fieldClass) instanceof EnumSerializer)) {
                serializer.writeDirect = false;
            }
            i++;
        }
    }

    public final ObjectSerializer createJavaBeanSerializer(Class<?> clazz) {
        String className = clazz.getName();
        if (Arrays.binarySearch(this.denyClasses, TypeUtils.fnv1a_64(className)) < 0) {
            SerializeBeanInfo beanInfo = TypeUtils.buildBeanInfo(clazz, (Map<String, String>) null, this.propertyNamingStrategy, this.fieldBased);
            if (beanInfo.fields.length != 0 || !Iterable.class.isAssignableFrom(clazz)) {
                return createJavaBeanSerializer(beanInfo);
            }
            return MiscCodec.instance;
        }
        throw new JSONException("not support class : " + className);
    }

    public ObjectSerializer createJavaBeanSerializer(SerializeBeanInfo beanInfo) {
        FieldInfo[] fieldInfoArr;
        SerializeConfig serializeConfig = this;
        SerializeBeanInfo serializeBeanInfo = beanInfo;
        JSONType jsonType = serializeBeanInfo.jsonType;
        boolean asm2 = serializeConfig.asm && !serializeConfig.fieldBased;
        if (jsonType != null) {
            Class<?> serializerClass = jsonType.serializer();
            if (serializerClass != Void.class) {
                try {
                    Object newInstance = serializerClass.newInstance();
                    if (newInstance instanceof ObjectSerializer) {
                        return (ObjectSerializer) newInstance;
                    }
                } catch (Throwable th) {
                }
            }
            if (!jsonType.asm()) {
                asm2 = false;
            }
            if (asm2) {
                SerializerFeature[] serialzeFeatures = jsonType.serialzeFeatures();
                int length = serialzeFeatures.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    SerializerFeature feature = serialzeFeatures[i];
                    if (SerializerFeature.WriteNonStringValueAsString == feature || SerializerFeature.WriteEnumUsingToString == feature || SerializerFeature.NotWriteDefaultValue == feature || SerializerFeature.BrowserCompatible == feature) {
                        asm2 = false;
                    } else {
                        i++;
                    }
                }
                asm2 = false;
            }
            if (asm2 && jsonType.serialzeFilters().length != 0) {
                asm2 = false;
            }
        }
        Class<?> serializerClass2 = serializeBeanInfo.beanType;
        if (!Modifier.isPublic(serializeBeanInfo.beanType.getModifiers())) {
            return new JavaBeanSerializer(serializeBeanInfo);
        }
        if ((asm2 && serializeConfig.asmFactory.classLoader.isExternalClass(serializerClass2)) || serializerClass2 == Serializable.class || serializerClass2 == Object.class) {
            asm2 = false;
        }
        if (asm2 && !ASMUtils.checkName(serializerClass2.getSimpleName())) {
            asm2 = false;
        }
        if (asm2 && serializeBeanInfo.beanType.isInterface()) {
            asm2 = false;
        }
        if (asm2) {
            FieldInfo[] fieldInfoArr2 = serializeBeanInfo.fields;
            int length2 = fieldInfoArr2.length;
            int i2 = 0;
            while (true) {
                if (i2 < length2) {
                    FieldInfo fieldInfo = fieldInfoArr2[i2];
                    Field field = fieldInfo.field;
                    if (field != null && !field.getType().equals(fieldInfo.fieldClass)) {
                        asm2 = false;
                        break;
                    }
                    Method method = fieldInfo.method;
                    if (method == null || method.getReturnType().equals(fieldInfo.fieldClass)) {
                        if (fieldInfo.fieldClass.isEnum() && serializeConfig.get(fieldInfo.fieldClass) != EnumSerializer.instance) {
                            asm2 = false;
                            break;
                        }
                        JSONField annotation = fieldInfo.getAnnotation();
                        if (annotation != null) {
                            String format = annotation.format();
                            if (format.length() == 0 || (fieldInfo.fieldClass == String.class && "trim".equals(format))) {
                                if (ASMUtils.checkName(annotation.name()) && !annotation.jsonDirect() && annotation.serializeUsing() == Void.class && !annotation.unwrapped()) {
                                    SerializerFeature[] serialzeFeatures2 = annotation.serialzeFeatures();
                                    int length3 = serialzeFeatures2.length;
                                    int i3 = 0;
                                    while (true) {
                                        if (i3 >= length3) {
                                            fieldInfoArr = fieldInfoArr2;
                                            break;
                                        }
                                        fieldInfoArr = fieldInfoArr2;
                                        SerializerFeature feature2 = serialzeFeatures2[i3];
                                        if (SerializerFeature.WriteNonStringValueAsString == feature2 || SerializerFeature.WriteEnumUsingToString == feature2 || SerializerFeature.NotWriteDefaultValue == feature2 || SerializerFeature.BrowserCompatible == feature2 || SerializerFeature.WriteClassName == feature2) {
                                            asm2 = false;
                                        } else {
                                            i3++;
                                            fieldInfoArr2 = fieldInfoArr;
                                        }
                                    }
                                    asm2 = false;
                                    if (!TypeUtils.isAnnotationPresentOneToMany(method) && !TypeUtils.isAnnotationPresentManyToMany(method)) {
                                        if (annotation.defaultValue() != null && !"".equals(annotation.defaultValue())) {
                                            asm2 = false;
                                            break;
                                        }
                                    } else {
                                        asm2 = false;
                                    }
                                } else {
                                    asm2 = false;
                                }
                            }
                        } else {
                            fieldInfoArr = fieldInfoArr2;
                        }
                        i2++;
                        serializeConfig = this;
                        fieldInfoArr2 = fieldInfoArr;
                    } else {
                        asm2 = false;
                        break;
                    }
                } else {
                    break;
                }
            }
            asm2 = false;
        }
        if (asm2) {
            try {
                ObjectSerializer asmSerializer = createASMSerializer(beanInfo);
                if (asmSerializer != null) {
                    return asmSerializer;
                }
            } catch (ClassCastException | ClassFormatError | ClassNotFoundException e) {
            } catch (OutOfMemoryError e2) {
                OutOfMemoryError e3 = e2;
                if (e3.getMessage().indexOf("Metaspace") != -1) {
                    throw e3;
                }
            } catch (Throwable th2) {
                throw new JSONException("create asm serializer error, verson 1.2.75, class " + serializerClass2, th2);
            }
        }
        return new JavaBeanSerializer(serializeBeanInfo);
    }

    public boolean isAsmEnable() {
        return this.asm;
    }

    public void setAsmEnable(boolean asmEnable) {
        if (!ASMUtils.IS_ANDROID) {
            this.asm = asmEnable;
        }
    }

    public static SerializeConfig getGlobalInstance() {
        return globalInstance;
    }

    public SerializeConfig() {
        this(8192);
    }

    public SerializeConfig(boolean fieldBase) {
        this(8192, fieldBase);
    }

    public SerializeConfig(int tableSize) {
        this(tableSize, false);
    }

    public SerializeConfig(int tableSize, boolean fieldBase) {
        this.asm = !ASMUtils.IS_ANDROID;
        this.typeKey = JSON.DEFAULT_TYPE_KEY;
        this.denyClasses = new long[]{4165360493669296979L, 4446674157046724083L};
        this.modules = new ArrayList();
        this.fieldBased = fieldBase;
        this.serializers = new IdentityHashMap<>(tableSize);
        this.mixInSerializers = new IdentityHashMap<>(16);
        try {
            if (this.asm) {
                this.asmFactory = new ASMSerializerFactory();
            }
        } catch (Throwable th) {
            this.asm = false;
        }
        initSerializers();
    }

    private void initSerializers() {
        put((Type) Boolean.class, (ObjectSerializer) BooleanCodec.instance);
        put((Type) Character.class, (ObjectSerializer) CharacterCodec.instance);
        put((Type) Byte.class, (ObjectSerializer) IntegerCodec.instance);
        put((Type) Short.class, (ObjectSerializer) IntegerCodec.instance);
        put((Type) Integer.class, (ObjectSerializer) IntegerCodec.instance);
        put((Type) Long.class, (ObjectSerializer) LongCodec.instance);
        put((Type) Float.class, (ObjectSerializer) FloatCodec.instance);
        put((Type) Double.class, (ObjectSerializer) DoubleSerializer.instance);
        put((Type) BigDecimal.class, (ObjectSerializer) BigDecimalCodec.instance);
        put((Type) BigInteger.class, (ObjectSerializer) BigIntegerCodec.instance);
        put((Type) String.class, (ObjectSerializer) StringCodec.instance);
        put((Type) byte[].class, (ObjectSerializer) PrimitiveArraySerializer.instance);
        put((Type) short[].class, (ObjectSerializer) PrimitiveArraySerializer.instance);
        put((Type) int[].class, (ObjectSerializer) PrimitiveArraySerializer.instance);
        put((Type) long[].class, (ObjectSerializer) PrimitiveArraySerializer.instance);
        put((Type) float[].class, (ObjectSerializer) PrimitiveArraySerializer.instance);
        put((Type) double[].class, (ObjectSerializer) PrimitiveArraySerializer.instance);
        put((Type) boolean[].class, (ObjectSerializer) PrimitiveArraySerializer.instance);
        put((Type) char[].class, (ObjectSerializer) PrimitiveArraySerializer.instance);
        put((Type) Object[].class, (ObjectSerializer) ObjectArrayCodec.instance);
        MiscCodec miscCodec = MiscCodec.instance;
        put((Type) Class.class, (ObjectSerializer) miscCodec);
        put((Type) SimpleDateFormat.class, (ObjectSerializer) miscCodec);
        put((Type) Currency.class, (ObjectSerializer) new MiscCodec());
        put((Type) TimeZone.class, (ObjectSerializer) miscCodec);
        put((Type) InetAddress.class, (ObjectSerializer) miscCodec);
        put((Type) Inet4Address.class, (ObjectSerializer) miscCodec);
        put((Type) Inet6Address.class, (ObjectSerializer) miscCodec);
        put((Type) InetSocketAddress.class, (ObjectSerializer) miscCodec);
        put((Type) File.class, (ObjectSerializer) miscCodec);
        AppendableSerializer appendableSerializer = AppendableSerializer.instance;
        put((Type) Appendable.class, (ObjectSerializer) appendableSerializer);
        put((Type) StringBuffer.class, (ObjectSerializer) appendableSerializer);
        put((Type) StringBuilder.class, (ObjectSerializer) appendableSerializer);
        ToStringSerializer toStringSerializer = ToStringSerializer.instance;
        put((Type) Charset.class, (ObjectSerializer) toStringSerializer);
        put((Type) Pattern.class, (ObjectSerializer) toStringSerializer);
        put((Type) Locale.class, (ObjectSerializer) toStringSerializer);
        put((Type) URI.class, (ObjectSerializer) toStringSerializer);
        put((Type) URL.class, (ObjectSerializer) toStringSerializer);
        put((Type) UUID.class, (ObjectSerializer) toStringSerializer);
        AtomicCodec atomicCodec = AtomicCodec.instance;
        put((Type) AtomicBoolean.class, (ObjectSerializer) atomicCodec);
        put((Type) AtomicInteger.class, (ObjectSerializer) atomicCodec);
        put((Type) AtomicLong.class, (ObjectSerializer) atomicCodec);
        ReferenceCodec referenceCodec = ReferenceCodec.instance;
        put((Type) AtomicReference.class, (ObjectSerializer) referenceCodec);
        put((Type) AtomicIntegerArray.class, (ObjectSerializer) atomicCodec);
        put((Type) AtomicLongArray.class, (ObjectSerializer) atomicCodec);
        put((Type) WeakReference.class, (ObjectSerializer) referenceCodec);
        put((Type) SoftReference.class, (ObjectSerializer) referenceCodec);
        put((Type) LinkedList.class, (ObjectSerializer) CollectionCodec.instance);
    }

    public void addFilter(Class<?> clazz, SerializeFilter filter) {
        ObjectSerializer serializer = getObjectWriter(clazz);
        if (serializer instanceof SerializeFilterable) {
            SerializeFilterable filterable = (SerializeFilterable) serializer;
            if (this == globalInstance || filterable != MapSerializer.instance) {
                filterable.addFilter(filter);
                return;
            }
            MapSerializer newMapSer = new MapSerializer();
            put((Type) clazz, (ObjectSerializer) newMapSer);
            newMapSer.addFilter(filter);
        }
    }

    public void config(Class<?> clazz, SerializerFeature feature, boolean value) {
        ObjectSerializer serializer = getObjectWriter(clazz, false);
        if (serializer == null) {
            SerializeBeanInfo beanInfo = TypeUtils.buildBeanInfo(clazz, (Map<String, String>) null, this.propertyNamingStrategy);
            if (value) {
                beanInfo.features |= feature.mask;
            } else {
                beanInfo.features &= ~feature.mask;
            }
            put((Type) clazz, createJavaBeanSerializer(beanInfo));
        } else if (serializer instanceof JavaBeanSerializer) {
            SerializeBeanInfo beanInfo2 = ((JavaBeanSerializer) serializer).beanInfo;
            int originalFeaturs = beanInfo2.features;
            if (value) {
                beanInfo2.features |= feature.mask;
            } else {
                beanInfo2.features &= ~feature.mask;
            }
            if (originalFeaturs != beanInfo2.features && serializer.getClass() != JavaBeanSerializer.class) {
                put((Type) clazz, createJavaBeanSerializer(beanInfo2));
            }
        }
    }

    public ObjectSerializer getObjectWriter(Class<?> clazz) {
        return getObjectWriter(clazz, true);
    }

    public ObjectSerializer getObjectWriter(Class<?> clazz, boolean create) {
        JSONType jsonType;
        ClassLoader classLoader;
        Class<?> cls = clazz;
        Class<AutowiredObjectSerializer> cls2 = AutowiredObjectSerializer.class;
        ObjectSerializer writer = get(clazz);
        if (writer != null) {
            return writer;
        }
        try {
            for (AutowiredObjectSerializer next : ServiceLoader.load(cls2, Thread.currentThread().getContextClassLoader())) {
                if (next instanceof AutowiredObjectSerializer) {
                    AutowiredObjectSerializer autowired = next;
                    for (Type forType : autowired.getAutowiredFor()) {
                        put(forType, (ObjectSerializer) autowired);
                    }
                }
            }
        } catch (ClassCastException e) {
        }
        ObjectSerializer writer2 = get(clazz);
        if (writer2 == null && (classLoader = JSON.class.getClassLoader()) != Thread.currentThread().getContextClassLoader()) {
            try {
                for (AutowiredObjectSerializer next2 : ServiceLoader.load(cls2, classLoader)) {
                    if (next2 instanceof AutowiredObjectSerializer) {
                        AutowiredObjectSerializer autowired2 = next2;
                        for (Type forType2 : autowired2.getAutowiredFor()) {
                            put(forType2, (ObjectSerializer) autowired2);
                        }
                    }
                }
            } catch (ClassCastException e2) {
            }
            writer2 = get(clazz);
        }
        for (Module module : this.modules) {
            writer2 = module.createSerializer(this, cls);
            if (writer2 != null) {
                put((Type) cls, writer2);
                return writer2;
            }
        }
        if (writer2 != null) {
            return writer2;
        }
        String className = clazz.getName();
        if (Map.class.isAssignableFrom(cls)) {
            ObjectSerializer writer3 = MapSerializer.instance;
            put((Type) cls, writer3);
            writer2 = writer3;
        } else if (List.class.isAssignableFrom(cls)) {
            ObjectSerializer writer4 = ListSerializer.instance;
            put((Type) cls, writer4);
            writer2 = writer4;
        } else if (Collection.class.isAssignableFrom(cls)) {
            ObjectSerializer writer5 = CollectionCodec.instance;
            put((Type) cls, writer5);
            writer2 = writer5;
        } else if (Date.class.isAssignableFrom(cls)) {
            ObjectSerializer writer6 = DateCodec.instance;
            put((Type) cls, writer6);
            writer2 = writer6;
        } else if (JSONAware.class.isAssignableFrom(cls)) {
            ObjectSerializer writer7 = JSONAwareSerializer.instance;
            put((Type) cls, writer7);
            writer2 = writer7;
        } else if (JSONSerializable.class.isAssignableFrom(cls)) {
            ObjectSerializer writer8 = JSONSerializableSerializer.instance;
            put((Type) cls, writer8);
            writer2 = writer8;
        } else if (JSONStreamAware.class.isAssignableFrom(cls)) {
            ObjectSerializer writer9 = MiscCodec.instance;
            put((Type) cls, writer9);
            writer2 = writer9;
        } else if (clazz.isEnum()) {
            Class mixedInType = (Class) JSON.getMixInAnnotations(clazz);
            if (mixedInType != null) {
                jsonType = (JSONType) TypeUtils.getAnnotation((Class<?>) mixedInType, JSONType.class);
            } else {
                jsonType = (JSONType) TypeUtils.getAnnotation(cls, JSONType.class);
            }
            if (jsonType == null || !jsonType.serializeEnumAsJavaBean()) {
                Member member = null;
                if (mixedInType != null) {
                    Member mixedInMember = getEnumValueField(mixedInType);
                    if (mixedInMember != null) {
                        try {
                            if (mixedInMember instanceof Method) {
                                Method mixedInMethod = (Method) mixedInMember;
                                member = cls.getMethod(mixedInMethod.getName(), mixedInMethod.getParameterTypes());
                            }
                        } catch (Exception e3) {
                        }
                    }
                } else {
                    member = getEnumValueField(clazz);
                }
                if (member != null) {
                    ObjectSerializer enumSerializer = new EnumSerializer(member);
                    writer2 = enumSerializer;
                    put((Type) cls, enumSerializer);
                } else {
                    ObjectSerializer enumSerializer2 = getEnumSerializer();
                    writer2 = enumSerializer2;
                    put((Type) cls, enumSerializer2);
                }
            } else {
                ObjectSerializer createJavaBeanSerializer = createJavaBeanSerializer(clazz);
                writer2 = createJavaBeanSerializer;
                put((Type) cls, createJavaBeanSerializer);
            }
        } else {
            Class<? super Object> superclass = clazz.getSuperclass();
            Class<? super Object> cls3 = superclass;
            if (superclass != null && cls3.isEnum()) {
                JSONType jsonType2 = (JSONType) TypeUtils.getAnnotation((Class<?>) cls3, JSONType.class);
                if (jsonType2 == null || !jsonType2.serializeEnumAsJavaBean()) {
                    ObjectSerializer enumSerializer3 = getEnumSerializer();
                    writer2 = enumSerializer3;
                    put((Type) cls, enumSerializer3);
                } else {
                    ObjectSerializer createJavaBeanSerializer2 = createJavaBeanSerializer(clazz);
                    writer2 = createJavaBeanSerializer2;
                    put((Type) cls, createJavaBeanSerializer2);
                }
            } else if (clazz.isArray()) {
                Class<?> componentType = clazz.getComponentType();
                ObjectSerializer arraySerializer = new ArraySerializer(componentType, getObjectWriter(componentType));
                writer2 = arraySerializer;
                put((Type) cls, arraySerializer);
            } else if (Throwable.class.isAssignableFrom(cls)) {
                SerializeBeanInfo beanInfo = TypeUtils.buildBeanInfo(cls, (Map<String, String>) null, this.propertyNamingStrategy);
                beanInfo.features |= SerializerFeature.WriteClassName.mask;
                ObjectSerializer javaBeanSerializer = new JavaBeanSerializer(beanInfo);
                writer2 = javaBeanSerializer;
                put((Type) cls, javaBeanSerializer);
            } else if (TimeZone.class.isAssignableFrom(cls) || Map.Entry.class.isAssignableFrom(cls)) {
                ObjectSerializer writer10 = MiscCodec.instance;
                put((Type) cls, writer10);
                writer2 = writer10;
            } else if (Appendable.class.isAssignableFrom(cls)) {
                ObjectSerializer writer11 = AppendableSerializer.instance;
                put((Type) cls, writer11);
                writer2 = writer11;
            } else if (Charset.class.isAssignableFrom(cls)) {
                ObjectSerializer writer12 = ToStringSerializer.instance;
                put((Type) cls, writer12);
                writer2 = writer12;
            } else if (Enumeration.class.isAssignableFrom(cls)) {
                ObjectSerializer writer13 = EnumerationSerializer.instance;
                put((Type) cls, writer13);
                writer2 = writer13;
            } else if (Calendar.class.isAssignableFrom(cls) || XMLGregorianCalendar.class.isAssignableFrom(cls)) {
                ObjectSerializer writer14 = CalendarCodec.instance;
                put((Type) cls, writer14);
                writer2 = writer14;
            } else if (TypeUtils.isClob(clazz)) {
                ObjectSerializer writer15 = ClobSeriliazer.instance;
                put((Type) cls, writer15);
                writer2 = writer15;
            } else if (TypeUtils.isPath(clazz)) {
                ObjectSerializer writer16 = ToStringSerializer.instance;
                put((Type) cls, writer16);
                writer2 = writer16;
            } else if (Iterator.class.isAssignableFrom(cls)) {
                ObjectSerializer writer17 = MiscCodec.instance;
                put((Type) cls, writer17);
                writer2 = writer17;
            } else if (Node.class.isAssignableFrom(cls)) {
                ObjectSerializer writer18 = MiscCodec.instance;
                put((Type) cls, writer18);
                writer2 = writer18;
            } else {
                int i = 0;
                if (className.startsWith("java.awt.") && AwtCodec.support(clazz) && !awtError) {
                    try {
                        for (String name : new String[]{"java.awt.Color", "java.awt.Font", "java.awt.Point", "java.awt.Rectangle"}) {
                            if (name.equals(className)) {
                                Class<?> cls4 = Class.forName(name);
                                ObjectSerializer objectSerializer = AwtCodec.instance;
                                ObjectSerializer writer19 = objectSerializer;
                                put((Type) cls4, objectSerializer);
                                return writer19;
                            }
                        }
                    } catch (Throwable th) {
                        awtError = true;
                    }
                }
                if (!jdk8Error && (className.startsWith("java.time.") || className.startsWith("java.util.Optional") || className.equals("java.util.concurrent.atomic.LongAdder") || className.equals("java.util.concurrent.atomic.DoubleAdder"))) {
                    try {
                        for (String name2 : new String[]{"java.time.LocalDateTime", "java.time.LocalDate", "java.time.LocalTime", "java.time.ZonedDateTime", "java.time.OffsetDateTime", "java.time.OffsetTime", "java.time.ZoneOffset", "java.time.ZoneRegion", "java.time.Period", "java.time.Duration", "java.time.Instant"}) {
                            if (name2.equals(className)) {
                                Class<?> cls5 = Class.forName(name2);
                                ObjectSerializer objectSerializer2 = Jdk8DateCodec.instance;
                                ObjectSerializer writer20 = objectSerializer2;
                                put((Type) cls5, objectSerializer2);
                                return writer20;
                            }
                        }
                        for (String name3 : new String[]{"java.util.Optional", "java.util.OptionalDouble", "java.util.OptionalInt", "java.util.OptionalLong"}) {
                            if (name3.equals(className)) {
                                Class<?> cls6 = Class.forName(name3);
                                ObjectSerializer objectSerializer3 = OptionalCodec.instance;
                                ObjectSerializer writer21 = objectSerializer3;
                                put((Type) cls6, objectSerializer3);
                                return writer21;
                            }
                        }
                        for (String name4 : new String[]{"java.util.concurrent.atomic.LongAdder", "java.util.concurrent.atomic.DoubleAdder"}) {
                            if (name4.equals(className)) {
                                Class<?> cls7 = Class.forName(name4);
                                ObjectSerializer objectSerializer4 = AdderSerializer.instance;
                                ObjectSerializer writer22 = objectSerializer4;
                                put((Type) cls7, objectSerializer4);
                                return writer22;
                            }
                        }
                    } catch (Throwable th2) {
                        jdk8Error = true;
                    }
                }
                if (!oracleJdbcError && className.startsWith("oracle.sql.")) {
                    try {
                        for (String name5 : new String[]{"oracle.sql.DATE", "oracle.sql.TIMESTAMP"}) {
                            if (name5.equals(className)) {
                                Class<?> cls8 = Class.forName(name5);
                                ObjectSerializer objectSerializer5 = DateCodec.instance;
                                ObjectSerializer writer23 = objectSerializer5;
                                put((Type) cls8, objectSerializer5);
                                return writer23;
                            }
                        }
                    } catch (Throwable th3) {
                        oracleJdbcError = true;
                    }
                }
                if (!springfoxError && className.equals("springfox.documentation.spring.web.json.Json")) {
                    try {
                        Class<?> cls9 = Class.forName("springfox.documentation.spring.web.json.Json");
                        ObjectSerializer objectSerializer6 = SwaggerJsonSerializer.instance;
                        writer2 = objectSerializer6;
                        put((Type) cls9, objectSerializer6);
                        return writer2;
                    } catch (ClassNotFoundException e4) {
                        springfoxError = true;
                    }
                }
                if (!guavaError && className.startsWith("com.google.common.collect.")) {
                    try {
                        for (String name6 : new String[]{"com.google.common.collect.HashMultimap", "com.google.common.collect.LinkedListMultimap", "com.google.common.collect.LinkedHashMultimap", "com.google.common.collect.ArrayListMultimap", "com.google.common.collect.TreeMultimap"}) {
                            if (name6.equals(className)) {
                                Class<?> cls10 = Class.forName(name6);
                                ObjectSerializer objectSerializer7 = GuavaCodec.instance;
                                ObjectSerializer writer24 = objectSerializer7;
                                put((Type) cls10, objectSerializer7);
                                return writer24;
                            }
                        }
                    } catch (ClassNotFoundException e5) {
                        guavaError = true;
                    }
                }
                if (className.equals("net.sf.json.JSONNull")) {
                    ObjectSerializer objectSerializer8 = MiscCodec.instance;
                    ObjectSerializer writer25 = objectSerializer8;
                    put((Type) cls, objectSerializer8);
                    return writer25;
                } else if (className.equals("org.json.JSONObject")) {
                    ObjectSerializer objectSerializer9 = JSONObjectCodec.instance;
                    ObjectSerializer writer26 = objectSerializer9;
                    put((Type) cls, objectSerializer9);
                    return writer26;
                } else {
                    if (!jodaError && className.startsWith("org.joda.")) {
                        try {
                            for (String name7 : new String[]{"org.joda.time.LocalDate", "org.joda.time.LocalDateTime", "org.joda.time.LocalTime", "org.joda.time.Instant", "org.joda.time.DateTime", "org.joda.time.Period", "org.joda.time.Duration", "org.joda.time.DateTimeZone", "org.joda.time.UTCDateTimeZone", "org.joda.time.tz.CachedDateTimeZone", "org.joda.time.tz.FixedDateTimeZone"}) {
                                if (name7.equals(className)) {
                                    Class<?> cls11 = Class.forName(name7);
                                    ObjectSerializer objectSerializer10 = JodaCodec.instance;
                                    ObjectSerializer writer27 = objectSerializer10;
                                    put((Type) cls11, objectSerializer10);
                                    return writer27;
                                }
                            }
                        } catch (ClassNotFoundException e6) {
                            jodaError = true;
                        }
                    }
                    if ("java.nio.HeapByteBuffer".equals(className)) {
                        ObjectSerializer objectSerializer11 = ByteBufferCodec.instance;
                        ObjectSerializer writer28 = objectSerializer11;
                        put((Type) cls, objectSerializer11);
                        return writer28;
                    } else if ("org.javamoney.moneta.Money".equals(className)) {
                        ObjectSerializer objectSerializer12 = MonetaCodec.instance;
                        ObjectSerializer writer29 = objectSerializer12;
                        put((Type) cls, objectSerializer12);
                        return writer29;
                    } else if ("com.google.protobuf.Descriptors$FieldDescriptor".equals(className)) {
                        ObjectSerializer objectSerializer13 = ToStringSerializer.instance;
                        ObjectSerializer writer30 = objectSerializer13;
                        put((Type) cls, objectSerializer13);
                        return writer30;
                    } else {
                        Class[] interfaces = clazz.getInterfaces();
                        if (interfaces.length == 1 && interfaces[0].isAnnotation()) {
                            put((Type) cls, (ObjectSerializer) AnnotationSerializer.instance);
                            return AnnotationSerializer.instance;
                        } else if (TypeUtils.isProxy(clazz)) {
                            ObjectSerializer superWriter = getObjectWriter(clazz.getSuperclass());
                            put((Type) cls, superWriter);
                            return superWriter;
                        } else {
                            if (Proxy.isProxyClass(clazz)) {
                                Class handlerClass = null;
                                if (interfaces.length != 2) {
                                    int length = interfaces.length;
                                    while (true) {
                                        if (i >= length) {
                                            break;
                                        }
                                        Class proxiedInterface = interfaces[i];
                                        if (!proxiedInterface.getName().startsWith("org.springframework.aop.")) {
                                            if (handlerClass != null) {
                                                handlerClass = null;
                                                break;
                                            }
                                            handlerClass = proxiedInterface;
                                        }
                                        i++;
                                    }
                                } else {
                                    handlerClass = interfaces[1];
                                }
                                if (handlerClass != null) {
                                    ObjectSerializer superWriter2 = getObjectWriter(handlerClass);
                                    put((Type) cls, superWriter2);
                                    return superWriter2;
                                }
                            }
                            if (create) {
                                ObjectSerializer writer31 = createJavaBeanSerializer(clazz);
                                put((Type) cls, writer31);
                                writer2 = writer31;
                            }
                        }
                    }
                }
            }
        }
        if (writer2 == null) {
            return get(clazz);
        }
        return writer2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: java.lang.reflect.Method} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.lang.reflect.Method} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: java.lang.reflect.Field[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: java.lang.reflect.Method} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v0, resolved type: java.lang.reflect.Field} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: java.lang.reflect.Method} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: java.lang.reflect.Method} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: java.lang.reflect.Method} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: java.lang.reflect.Method} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.reflect.Member getEnumValueField(java.lang.Class r9) {
        /*
            r0 = 0
            java.lang.reflect.Method[] r1 = r9.getMethods()
            int r2 = r1.length
            r3 = 0
            r4 = r3
        L_0x0008:
            r5 = 0
            if (r4 >= r2) goto L_0x0027
            r6 = r1[r4]
            java.lang.Class r7 = r6.getReturnType()
            java.lang.Class<java.lang.Void> r8 = java.lang.Void.class
            if (r7 != r8) goto L_0x0016
            goto L_0x0024
        L_0x0016:
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r7 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r7 = r6.getAnnotation(r7)
            com.alibaba.fastjson.annotation.JSONField r7 = (com.alibaba.fastjson.annotation.JSONField) r7
            if (r7 == 0) goto L_0x0024
            if (r0 == 0) goto L_0x0023
            return r5
        L_0x0023:
            r0 = r6
        L_0x0024:
            int r4 = r4 + 1
            goto L_0x0008
        L_0x0027:
            java.lang.reflect.Field[] r2 = r9.getFields()
            int r4 = r2.length
        L_0x002c:
            if (r3 >= r4) goto L_0x0041
            r6 = r2[r3]
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r7 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r7 = r6.getAnnotation(r7)
            com.alibaba.fastjson.annotation.JSONField r7 = (com.alibaba.fastjson.annotation.JSONField) r7
            if (r7 == 0) goto L_0x003e
            if (r0 == 0) goto L_0x003d
            return r5
        L_0x003d:
            r0 = r6
        L_0x003e:
            int r3 = r3 + 1
            goto L_0x002c
        L_0x0041:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.SerializeConfig.getEnumValueField(java.lang.Class):java.lang.reflect.Member");
    }

    /* access modifiers changed from: protected */
    public ObjectSerializer getEnumSerializer() {
        return EnumSerializer.instance;
    }

    public final ObjectSerializer get(Type type) {
        Type mixin = JSON.getMixInAnnotations(type);
        if (mixin == null) {
            return this.serializers.get(type);
        }
        IdentityHashMap<Type, ObjectSerializer> mixInClasses = this.mixInSerializers.get(type);
        if (mixInClasses == null) {
            return null;
        }
        return mixInClasses.get(mixin);
    }

    public boolean put(Object type, Object value) {
        return put((Type) type, (ObjectSerializer) value);
    }

    public boolean put(Type type, ObjectSerializer value) {
        Type mixin = JSON.getMixInAnnotations(type);
        if (mixin == null) {
            return this.serializers.put(type, value);
        }
        IdentityHashMap<Type, ObjectSerializer> mixInClasses = this.mixInSerializers.get(type);
        if (mixInClasses == null) {
            mixInClasses = new IdentityHashMap<>(4);
            this.mixInSerializers.put(type, mixInClasses);
        }
        return mixInClasses.put(mixin, value);
    }

    public void configEnumAsJavaBean(Class<? extends Enum>... enumClasses) {
        for (Class<? extends Enum> enumClass : enumClasses) {
            put((Type) enumClass, createJavaBeanSerializer((Class<?>) enumClass));
        }
    }

    public void setPropertyNamingStrategy(PropertyNamingStrategy propertyNamingStrategy2) {
        this.propertyNamingStrategy = propertyNamingStrategy2;
    }

    public void clearSerializers() {
        this.serializers.clear();
        initSerializers();
    }

    public void register(Module module) {
        this.modules.add(module);
    }
}
