package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory;
import com.alibaba.fastjson.parser.deserializer.ArrayListTypeFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.EnumDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.JSONPDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.MapDeserializer;
import com.alibaba.fastjson.parser.deserializer.NumberDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.SqlDateDeserializer;
import com.alibaba.fastjson.parser.deserializer.StackTraceElementDeserializer;
import com.alibaba.fastjson.parser.deserializer.TimeDeserializer;
import com.alibaba.fastjson.serializer.AtomicCodec;
import com.alibaba.fastjson.serializer.BigDecimalCodec;
import com.alibaba.fastjson.serializer.BigIntegerCodec;
import com.alibaba.fastjson.serializer.BooleanCodec;
import com.alibaba.fastjson.serializer.CalendarCodec;
import com.alibaba.fastjson.serializer.CharArrayCodec;
import com.alibaba.fastjson.serializer.CharacterCodec;
import com.alibaba.fastjson.serializer.CollectionCodec;
import com.alibaba.fastjson.serializer.DateCodec;
import com.alibaba.fastjson.serializer.FloatCodec;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.LongCodec;
import com.alibaba.fastjson.serializer.MiscCodec;
import com.alibaba.fastjson.serializer.ReferenceCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.spi.Module;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.IdentityHashMap;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import com.tencent.bugly.Bugly;
import java.io.Closeable;
import java.io.File;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.AccessControlException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import javax.xml.datatype.XMLGregorianCalendar;

public class ParserConfig {
    public static final String AUTOTYPE_ACCEPT = "fastjson.parser.autoTypeAccept";
    public static final String AUTOTYPE_SUPPORT_PROPERTY = "fastjson.parser.autoTypeSupport";
    public static final boolean AUTO_SUPPORT = "true".equals(IOUtils.getStringProperty(AUTOTYPE_SUPPORT_PROPERTY));
    private static final String[] AUTO_TYPE_ACCEPT_LIST;
    public static final String[] DENYS = splitItemsFormProperty(IOUtils.getStringProperty(DENY_PROPERTY));
    public static final String[] DENYS_INTERNAL = splitItemsFormProperty(IOUtils.getStringProperty(DENY_PROPERTY_INTERNAL));
    public static final String DENY_PROPERTY = "fastjson.parser.deny";
    public static final String DENY_PROPERTY_INTERNAL = "fastjson.parser.deny.internal";
    private static final long[] INTERNAL_WHITELIST_HASHCODES = {-9013707057526259810L, -8773806119481270567L, -8421588593326113468L, -8070393259084821111L, -7858127399773263546L, -7043543676283957292L, -6976602508726000783L, -6293031534589903644L, -6081111809668363619L, -5779433778261875721L, -5399450433995651784L, -4540135604787511831L, -4207865850564917696L, -3950343444501679205L, -3714900953609113456L, -3393714734093696063L, -3378497329992063044L, -2631228350337215662L, -2551988546877734201L, -2473987886800209058L, -2265617974881722705L, -1759511109484434299L, -1477946458560579955L, -816725787720647462L, -520183782617964618L, 59775428743665658L, 484499585846206473L, 532945107123976213L, 711449177569584898L, 829148494126372070L, 956883420092542580L, 1233162291719202522L, 1696465274354442213L, 1863557081881630420L, 2238472697200138595L, 2380202963256720577L, 2643099543618286743L, 2793877891138577121L, 3804572268889088203L, 4567982875926242015L, 4784070066737926537L, 4960004821520561233L, 5348524593377618456L, 5454920836284873808L, 5695987590363189151L, 6073645722991901167L, 6114875255374330593L, 6137737446243999215L, 6160752908990493848L, 6939315124833099497L, 7048426940343117278L, 7267793227937552092L, 8331868837379820532L, 8357451534615459155L, 8890227807433646566L, 9166532985682478006L, 9215131087512669423L};
    public static final boolean SAFE_MODE = "true".equals(IOUtils.getStringProperty(SAFE_MODE_PROPERTY));
    public static final String SAFE_MODE_PROPERTY = "fastjson.parser.safeMode";
    private static boolean awtError = false;
    public static ParserConfig global = new ParserConfig();
    private static boolean guavaError = false;
    private static boolean jdk8Error = false;
    private static boolean jodaError = false;
    private long[] acceptHashCodes;
    private boolean asmEnable;
    protected ASMDeserializerFactory asmFactory;
    private volatile List<AutoTypeCheckHandler> autoTypeCheckHandlers;
    private boolean autoTypeSupport;
    public boolean compatibleWithJavaBean;
    protected ClassLoader defaultClassLoader;
    private long[] denyHashCodes;
    private final IdentityHashMap<Type, ObjectDeserializer> deserializers;
    public final boolean fieldBased;
    private long[] internalDenyHashCodes;
    private boolean jacksonCompatible;
    private final IdentityHashMap<Type, IdentityHashMap<Type, ObjectDeserializer>> mixInDeserializers;
    private List<Module> modules;
    public PropertyNamingStrategy propertyNamingStrategy;
    private boolean safeMode;
    public final SymbolTable symbolTable;
    private final ConcurrentMap<String, Class<?>> typeMapping;

    public interface AutoTypeCheckHandler {
        Class<?> handler(String str, Class<?> cls, int i);
    }

    static {
        String[] items = splitItemsFormProperty(IOUtils.getStringProperty(AUTOTYPE_ACCEPT));
        if (items == null) {
            items = new String[0];
        }
        AUTO_TYPE_ACCEPT_LIST = items;
    }

    public static ParserConfig getGlobalInstance() {
        return global;
    }

    public ParserConfig() {
        this(false);
    }

    public ParserConfig(boolean fieldBase) {
        this((ASMDeserializerFactory) null, (ClassLoader) null, fieldBase);
    }

    public ParserConfig(ClassLoader parentClassLoader) {
        this((ASMDeserializerFactory) null, parentClassLoader, false);
    }

    public ParserConfig(ASMDeserializerFactory asmFactory2) {
        this(asmFactory2, (ClassLoader) null, false);
    }

    private ParserConfig(ASMDeserializerFactory asmFactory2, ClassLoader parentClassLoader, boolean fieldBased2) {
        this.deserializers = new IdentityHashMap<>();
        this.mixInDeserializers = new IdentityHashMap<>(16);
        this.typeMapping = new ConcurrentHashMap(16, 0.75f, 1);
        this.asmEnable = !ASMUtils.IS_ANDROID;
        this.symbolTable = new SymbolTable(4096);
        this.autoTypeSupport = AUTO_SUPPORT;
        this.jacksonCompatible = false;
        this.compatibleWithJavaBean = TypeUtils.compatibleWithJavaBean;
        this.modules = new ArrayList();
        this.safeMode = SAFE_MODE;
        this.denyHashCodes = new long[]{-9164606388214699518L, -8720046426850100497L, -8649961213709896794L, -8165637398350707645L, -8109300701639721088L, -7966123100503199569L, -7921218830998286408L, -7775351613326101303L, -7768608037458185275L, -7766605818834748097L, -6835437086156813536L, -6316154655839304624L, -6179589609550493385L, -6149093380703242441L, -6025144546313590215L, -5939269048541779808L, -5885964883385605994L, -5764804792063216819L, -5472097725414717105L, -5194641081268104286L, -5076846148177416215L, -4837536971810737970L, -4703320437989596122L, -4608341446948126581L, -4537258998789938600L, -4438775680185074100L, -4314457471973557243L, -4150995715611818742L, -4082057040235125754L, -3975378478825053783L, -3935185854875733362L, -3319207949486691020L, -3077205613010077203L, -3053747177772160511L, -2995060141064716555L, -2825378362173150292L, -2533039401923731906L, -2439930098895578154L, -2378990704010641148L, -2364987994247679115L, -2262244760619952081L, -2192804397019347313L, -2095516571388852610L, -1872417015366588117L, -1650485814983027158L, -1589194880214235129L, -965955008570215305L, -905177026366752536L, -831789045734283466L, -582813228520337988L, -254670111376247151L, -219577392946377768L, -190281065685395680L, -26639035867733124L, -9822483067882491L, 4750336058574309L, 33238344207745342L, 156405680656087946L, 218512992947536312L, 313864100207897507L, 386461436234701831L, 823641066473609950L, 1073634739308289776L, 1153291637701043748L, 1203232727967308606L, 1214780596910349029L, 1459860845934817624L, 1502845958873959152L, 1534439610567445754L, 1698504441317515818L, 1818089308493370394L, 2078113382421334967L, 2164696723069287854L, 2622551729063269307L, 2653453629929770569L, 2660670623866180977L, 2731823439467737506L, 2836431254737891113L, 2930861374593775110L, 3085473968517218653L, 3089451460101527857L, 3114862868117605599L, 3129395579983849527L, 3256258368248066264L, 3452379460455804429L, 3547627781654598988L, 3637939656440441093L, 3688179072722109200L, 3718352661124136681L, 3730752432285826863L, 3794316665763266033L, 4000049462512838776L, 4046190361520671643L, 4147696707147271408L, 4193204392725694463L, 4241163808635564644L, 4254584350247334433L, 4814658433570175913L, 4841947709850912914L, 4904007817188630457L, 5100336081510080343L, 5274044858141538265L, 5347909877633654828L, 5450448828334921485L, 5474268165959054640L, 5545425291794704408L, 5596129856135573697L, 5688200883751798389L, 5751393439502795295L, 5944107969236155580L, 6007332606592876737L, 6280357960959217660L, 6456855723474196908L, 6511035576063254270L, 6534946468240507089L, 6584624952928234050L, 6734240326434096246L, 6742705432718011780L, 6854854816081053523L, 7045245923763966215L, 7123326897294507060L, 7179336928365889465L, 7240293012336844478L, 7347653049056829645L, 7375862386996623731L, 7442624256860549330L, 7617522210483516279L, 7658177784286215602L, 8055461369741094911L, 8389032537095247355L, 8409640769019589119L, 8488266005336625107L, 8537233257283452655L, 8838294710098435315L, 9140390920032557669L, 9140416208800006522L};
        long[] hashCodes = new long[AUTO_TYPE_ACCEPT_LIST.length];
        int i = 0;
        while (true) {
            String[] strArr = AUTO_TYPE_ACCEPT_LIST;
            if (i >= strArr.length) {
                break;
            }
            hashCodes[i] = TypeUtils.fnv1a_64(strArr[i]);
            i++;
        }
        Arrays.sort(hashCodes);
        this.acceptHashCodes = hashCodes;
        this.fieldBased = fieldBased2;
        if (asmFactory2 == null && !ASMUtils.IS_ANDROID) {
            if (parentClassLoader == null) {
                try {
                    asmFactory2 = new ASMDeserializerFactory(new ASMClassLoader());
                } catch (ExceptionInInitializerError | NoClassDefFoundError | AccessControlException e) {
                }
            } else {
                asmFactory2 = new ASMDeserializerFactory(parentClassLoader);
            }
        }
        this.asmFactory = asmFactory2;
        if (asmFactory2 == null) {
            this.asmEnable = false;
        }
        initDeserializers();
        addItemsToDeny(DENYS);
        addItemsToDeny0(DENYS_INTERNAL);
        addItemsToAccept(AUTO_TYPE_ACCEPT_LIST);
    }

    private void initDeserializers() {
        MiscCodec miscCodec = MiscCodec.instance;
        this.deserializers.put(SimpleDateFormat.class, miscCodec);
        this.deserializers.put(Timestamp.class, SqlDateDeserializer.instance_timestamp);
        this.deserializers.put(Date.class, SqlDateDeserializer.instance);
        this.deserializers.put(Time.class, TimeDeserializer.instance);
        this.deserializers.put(java.util.Date.class, DateCodec.instance);
        CalendarCodec calendarCodec = CalendarCodec.instance;
        this.deserializers.put(Calendar.class, calendarCodec);
        this.deserializers.put(XMLGregorianCalendar.class, calendarCodec);
        this.deserializers.put(JSONObject.class, MapDeserializer.instance);
        CollectionCodec collectionCodec = CollectionCodec.instance;
        this.deserializers.put(JSONArray.class, collectionCodec);
        this.deserializers.put(Map.class, MapDeserializer.instance);
        this.deserializers.put(HashMap.class, MapDeserializer.instance);
        this.deserializers.put(LinkedHashMap.class, MapDeserializer.instance);
        this.deserializers.put(TreeMap.class, MapDeserializer.instance);
        this.deserializers.put(ConcurrentMap.class, MapDeserializer.instance);
        this.deserializers.put(ConcurrentHashMap.class, MapDeserializer.instance);
        this.deserializers.put(Collection.class, collectionCodec);
        this.deserializers.put(List.class, collectionCodec);
        this.deserializers.put(ArrayList.class, collectionCodec);
        JavaObjectDeserializer javaObjectDeserializer = JavaObjectDeserializer.instance;
        this.deserializers.put(Object.class, javaObjectDeserializer);
        this.deserializers.put(String.class, StringCodec.instance);
        this.deserializers.put(StringBuffer.class, StringCodec.instance);
        this.deserializers.put(StringBuilder.class, StringCodec.instance);
        IdentityHashMap<Type, ObjectDeserializer> identityHashMap = this.deserializers;
        Class cls = Character.TYPE;
        CharacterCodec characterCodec = CharacterCodec.instance;
        identityHashMap.put(cls, characterCodec);
        this.deserializers.put(Character.class, characterCodec);
        IdentityHashMap<Type, ObjectDeserializer> identityHashMap2 = this.deserializers;
        Class cls2 = Byte.TYPE;
        NumberDeserializer numberDeserializer = NumberDeserializer.instance;
        identityHashMap2.put(cls2, numberDeserializer);
        this.deserializers.put(Byte.class, numberDeserializer);
        this.deserializers.put(Short.TYPE, numberDeserializer);
        this.deserializers.put(Short.class, numberDeserializer);
        this.deserializers.put(Integer.TYPE, IntegerCodec.instance);
        this.deserializers.put(Integer.class, IntegerCodec.instance);
        this.deserializers.put(Long.TYPE, LongCodec.instance);
        this.deserializers.put(Long.class, LongCodec.instance);
        this.deserializers.put(BigInteger.class, BigIntegerCodec.instance);
        this.deserializers.put(BigDecimal.class, BigDecimalCodec.instance);
        this.deserializers.put(Float.TYPE, FloatCodec.instance);
        this.deserializers.put(Float.class, FloatCodec.instance);
        this.deserializers.put(Double.TYPE, numberDeserializer);
        this.deserializers.put(Double.class, numberDeserializer);
        IdentityHashMap<Type, ObjectDeserializer> identityHashMap3 = this.deserializers;
        Class cls3 = Boolean.TYPE;
        BooleanCodec booleanCodec = BooleanCodec.instance;
        identityHashMap3.put(cls3, booleanCodec);
        this.deserializers.put(Boolean.class, booleanCodec);
        this.deserializers.put(Class.class, miscCodec);
        this.deserializers.put(char[].class, new CharArrayCodec());
        this.deserializers.put(AtomicBoolean.class, booleanCodec);
        this.deserializers.put(AtomicInteger.class, IntegerCodec.instance);
        this.deserializers.put(AtomicLong.class, LongCodec.instance);
        ReferenceCodec referenceCodec = ReferenceCodec.instance;
        this.deserializers.put(AtomicReference.class, referenceCodec);
        this.deserializers.put(WeakReference.class, referenceCodec);
        this.deserializers.put(SoftReference.class, referenceCodec);
        this.deserializers.put(UUID.class, miscCodec);
        this.deserializers.put(TimeZone.class, miscCodec);
        this.deserializers.put(Locale.class, miscCodec);
        this.deserializers.put(Currency.class, miscCodec);
        this.deserializers.put(Inet4Address.class, miscCodec);
        this.deserializers.put(Inet6Address.class, miscCodec);
        this.deserializers.put(InetSocketAddress.class, miscCodec);
        this.deserializers.put(File.class, miscCodec);
        this.deserializers.put(URI.class, miscCodec);
        this.deserializers.put(URL.class, miscCodec);
        this.deserializers.put(Pattern.class, miscCodec);
        this.deserializers.put(Charset.class, miscCodec);
        this.deserializers.put(JSONPath.class, miscCodec);
        this.deserializers.put(Number.class, numberDeserializer);
        AtomicCodec atomicCodec = AtomicCodec.instance;
        this.deserializers.put(AtomicIntegerArray.class, atomicCodec);
        this.deserializers.put(AtomicLongArray.class, atomicCodec);
        this.deserializers.put(StackTraceElement.class, StackTraceElementDeserializer.instance);
        this.deserializers.put(Serializable.class, javaObjectDeserializer);
        this.deserializers.put(Cloneable.class, javaObjectDeserializer);
        this.deserializers.put(Comparable.class, javaObjectDeserializer);
        this.deserializers.put(Closeable.class, javaObjectDeserializer);
        this.deserializers.put(JSONPObject.class, new JSONPDeserializer());
    }

    private static String[] splitItemsFormProperty(String property) {
        if (property == null || property.length() <= 0) {
            return null;
        }
        return property.split(",");
    }

    public void configFromPropety(Properties properties) {
        addItemsToDeny(splitItemsFormProperty(properties.getProperty(DENY_PROPERTY)));
        addItemsToAccept(splitItemsFormProperty(properties.getProperty(AUTOTYPE_ACCEPT)));
        String property = properties.getProperty(AUTOTYPE_SUPPORT_PROPERTY);
        if ("true".equals(property)) {
            this.autoTypeSupport = true;
        } else if (Bugly.SDK_IS_DEV.equals(property)) {
            this.autoTypeSupport = false;
        }
    }

    private void addItemsToDeny0(String[] items) {
        if (items != null) {
            for (String item : items) {
                addDenyInternal(item);
            }
        }
    }

    private void addItemsToDeny(String[] items) {
        if (items != null) {
            for (String item : items) {
                addDeny(item);
            }
        }
    }

    private void addItemsToAccept(String[] items) {
        if (items != null) {
            for (String item : items) {
                addAccept(item);
            }
        }
    }

    public boolean isSafeMode() {
        return this.safeMode;
    }

    public void setSafeMode(boolean safeMode2) {
        this.safeMode = safeMode2;
    }

    public boolean isAutoTypeSupport() {
        return this.autoTypeSupport;
    }

    public void setAutoTypeSupport(boolean autoTypeSupport2) {
        this.autoTypeSupport = autoTypeSupport2;
    }

    public boolean isAsmEnable() {
        return this.asmEnable;
    }

    public void setAsmEnable(boolean asmEnable2) {
        this.asmEnable = asmEnable2;
    }

    public IdentityHashMap<Type, ObjectDeserializer> getDerializers() {
        return this.deserializers;
    }

    public IdentityHashMap<Type, ObjectDeserializer> getDeserializers() {
        return this.deserializers;
    }

    public ObjectDeserializer getDeserializer(Type type) {
        ObjectDeserializer deserializer = get(type);
        if (deserializer != null) {
            return deserializer;
        }
        if (type instanceof Class) {
            return getDeserializer((Class) type, type);
        }
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType instanceof Class) {
                return getDeserializer((Class) rawType, type);
            }
            return getDeserializer(rawType);
        }
        if (type instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            if (upperBounds.length == 1) {
                return getDeserializer(upperBounds[0]);
            }
        }
        return JavaObjectDeserializer.instance;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: com.alibaba.fastjson.util.ParameterizedTypeImpl} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: java.lang.Class<?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v12, resolved type: com.alibaba.fastjson.serializer.MiscCodec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v13, resolved type: com.alibaba.fastjson.serializer.MiscCodec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v14, resolved type: com.alibaba.fastjson.serializer.ByteBufferCodec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v1, resolved type: com.alibaba.fastjson.util.ParameterizedTypeImpl} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: java.lang.Class<?>} */
    /* JADX WARNING: type inference failed for: r0v45, types: [java.lang.reflect.Type] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alibaba.fastjson.parser.deserializer.ObjectDeserializer getDeserializer(java.lang.Class<?> r22, java.lang.reflect.Type r23) {
        /*
            r21 = this;
            r1 = r21
            r2 = r22
            r0 = r23
            java.lang.String r3 = "java.util.Optional"
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r4 = r1.get(r0)
            if (r4 != 0) goto L_0x001d
            boolean r5 = r0 instanceof com.alibaba.fastjson.util.ParameterizedTypeImpl
            if (r5 == 0) goto L_0x001d
            r5 = r0
            com.alibaba.fastjson.util.ParameterizedTypeImpl r5 = (com.alibaba.fastjson.util.ParameterizedTypeImpl) r5
            java.lang.reflect.Type r5 = com.alibaba.fastjson.TypeReference.intern(r5)
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r4 = r1.get(r5)
        L_0x001d:
            if (r4 == 0) goto L_0x0020
            return r4
        L_0x0020:
            if (r0 != 0) goto L_0x0026
            r0 = r22
            r5 = r0
            goto L_0x0027
        L_0x0026:
            r5 = r0
        L_0x0027:
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r0 = r1.get(r5)
            if (r0 == 0) goto L_0x002e
            return r0
        L_0x002e:
            java.lang.Class<com.alibaba.fastjson.annotation.JSONType> r4 = com.alibaba.fastjson.annotation.JSONType.class
            java.lang.annotation.Annotation r4 = com.alibaba.fastjson.util.TypeUtils.getAnnotation((java.lang.Class<?>) r2, r4)
            com.alibaba.fastjson.annotation.JSONType r4 = (com.alibaba.fastjson.annotation.JSONType) r4
            if (r4 == 0) goto L_0x0045
            java.lang.Class r6 = r4.mappingTo()
            java.lang.Class<java.lang.Void> r7 = java.lang.Void.class
            if (r6 == r7) goto L_0x0045
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r3 = r1.getDeserializer(r6, r6)
            return r3
        L_0x0045:
            boolean r4 = r5 instanceof java.lang.reflect.WildcardType
            if (r4 != 0) goto L_0x0051
            boolean r4 = r5 instanceof java.lang.reflect.TypeVariable
            if (r4 != 0) goto L_0x0051
            boolean r4 = r5 instanceof java.lang.reflect.ParameterizedType
            if (r4 == 0) goto L_0x0055
        L_0x0051:
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r0 = r21.get(r22)
        L_0x0055:
            if (r0 == 0) goto L_0x0058
            return r0
        L_0x0058:
            java.util.List<com.alibaba.fastjson.spi.Module> r4 = r1.modules
            java.util.Iterator r4 = r4.iterator()
            r6 = r0
        L_0x005f:
            boolean r0 = r4.hasNext()
            if (r0 == 0) goto L_0x0076
            java.lang.Object r0 = r4.next()
            com.alibaba.fastjson.spi.Module r0 = (com.alibaba.fastjson.spi.Module) r0
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r6 = r0.createDeserializer(r1, r2)
            if (r6 == 0) goto L_0x0075
            r1.putDeserializer(r5, r6)
            return r6
        L_0x0075:
            goto L_0x005f
        L_0x0076:
            java.lang.String r0 = r22.getName()
            r4 = 36
            r7 = 46
            java.lang.String r4 = r0.replace(r4, r7)
            java.lang.String r0 = "java.awt."
            boolean r0 = r4.startsWith(r0)
            r7 = 0
            r8 = 1
            if (r0 == 0) goto L_0x00c3
            boolean r0 = com.alibaba.fastjson.serializer.AwtCodec.support(r22)
            if (r0 == 0) goto L_0x00c3
            boolean r0 = awtError
            if (r0 != 0) goto L_0x00c3
            java.lang.String r0 = "java.awt.Point"
            java.lang.String r9 = "java.awt.Font"
            java.lang.String r10 = "java.awt.Rectangle"
            java.lang.String r11 = "java.awt.Color"
            java.lang.String[] r0 = new java.lang.String[]{r0, r9, r10, r11}
            r9 = r0
            int r0 = r9.length     // Catch:{ all -> 0x00be }
            r10 = r7
        L_0x00a5:
            if (r10 >= r0) goto L_0x00bd
            r11 = r9[r10]     // Catch:{ all -> 0x00be }
            boolean r12 = r11.equals(r4)     // Catch:{ all -> 0x00be }
            if (r12 == 0) goto L_0x00ba
            java.lang.Class r0 = java.lang.Class.forName(r11)     // Catch:{ all -> 0x00be }
            com.alibaba.fastjson.serializer.AwtCodec r10 = com.alibaba.fastjson.serializer.AwtCodec.instance     // Catch:{ all -> 0x00be }
            r6 = r10
            r1.putDeserializer(r0, r10)     // Catch:{ all -> 0x00be }
            return r6
        L_0x00ba:
            int r10 = r10 + 1
            goto L_0x00a5
        L_0x00bd:
            goto L_0x00c1
        L_0x00be:
            r0 = move-exception
            awtError = r8
        L_0x00c1:
            com.alibaba.fastjson.serializer.AwtCodec r6 = com.alibaba.fastjson.serializer.AwtCodec.instance
        L_0x00c3:
            boolean r0 = jdk8Error
            if (r0 != 0) goto L_0x0135
            java.lang.String r0 = "java.time."
            boolean r0 = r4.startsWith(r0)     // Catch:{ all -> 0x0132 }
            if (r0 == 0) goto L_0x0106
            java.lang.String r9 = "java.time.LocalDateTime"
            java.lang.String r10 = "java.time.LocalDate"
            java.lang.String r11 = "java.time.LocalTime"
            java.lang.String r12 = "java.time.ZonedDateTime"
            java.lang.String r13 = "java.time.OffsetDateTime"
            java.lang.String r14 = "java.time.OffsetTime"
            java.lang.String r15 = "java.time.ZoneOffset"
            java.lang.String r16 = "java.time.ZoneRegion"
            java.lang.String r17 = "java.time.ZoneId"
            java.lang.String r18 = "java.time.Period"
            java.lang.String r19 = "java.time.Duration"
            java.lang.String r20 = "java.time.Instant"
            java.lang.String[] r0 = new java.lang.String[]{r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20}     // Catch:{ all -> 0x0132 }
            int r3 = r0.length     // Catch:{ all -> 0x0132 }
            r9 = r7
        L_0x00ed:
            if (r9 >= r3) goto L_0x0105
            r10 = r0[r9]     // Catch:{ all -> 0x0132 }
            boolean r11 = r10.equals(r4)     // Catch:{ all -> 0x0132 }
            if (r11 == 0) goto L_0x0102
            java.lang.Class r3 = java.lang.Class.forName(r10)     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.deserializer.Jdk8DateCodec r9 = com.alibaba.fastjson.parser.deserializer.Jdk8DateCodec.instance     // Catch:{ all -> 0x0132 }
            r6 = r9
            r1.putDeserializer(r3, r9)     // Catch:{ all -> 0x0132 }
            return r6
        L_0x0102:
            int r9 = r9 + 1
            goto L_0x00ed
        L_0x0105:
            goto L_0x0130
        L_0x0106:
            boolean r0 = r4.startsWith(r3)     // Catch:{ all -> 0x0132 }
            if (r0 == 0) goto L_0x0130
            java.lang.String r0 = "java.util.OptionalDouble"
            java.lang.String r9 = "java.util.OptionalInt"
            java.lang.String r10 = "java.util.OptionalLong"
            java.lang.String[] r0 = new java.lang.String[]{r3, r0, r9, r10}     // Catch:{ all -> 0x0132 }
            int r3 = r0.length     // Catch:{ all -> 0x0132 }
            r9 = r7
        L_0x0118:
            if (r9 >= r3) goto L_0x0131
            r10 = r0[r9]     // Catch:{ all -> 0x0132 }
            boolean r11 = r10.equals(r4)     // Catch:{ all -> 0x0132 }
            if (r11 == 0) goto L_0x012d
            java.lang.Class r3 = java.lang.Class.forName(r10)     // Catch:{ all -> 0x0132 }
            com.alibaba.fastjson.parser.deserializer.OptionalCodec r9 = com.alibaba.fastjson.parser.deserializer.OptionalCodec.instance     // Catch:{ all -> 0x0132 }
            r6 = r9
            r1.putDeserializer(r3, r9)     // Catch:{ all -> 0x0132 }
            return r6
        L_0x012d:
            int r9 = r9 + 1
            goto L_0x0118
        L_0x0130:
        L_0x0131:
            goto L_0x0135
        L_0x0132:
            r0 = move-exception
            jdk8Error = r8
        L_0x0135:
            boolean r0 = jodaError
            if (r0 != 0) goto L_0x0175
            java.lang.String r0 = "org.joda.time."
            boolean r0 = r4.startsWith(r0)     // Catch:{ all -> 0x0172 }
            if (r0 == 0) goto L_0x0171
            java.lang.String r9 = "org.joda.time.DateTime"
            java.lang.String r10 = "org.joda.time.LocalDate"
            java.lang.String r11 = "org.joda.time.LocalDateTime"
            java.lang.String r12 = "org.joda.time.LocalTime"
            java.lang.String r13 = "org.joda.time.Instant"
            java.lang.String r14 = "org.joda.time.Period"
            java.lang.String r15 = "org.joda.time.Duration"
            java.lang.String r16 = "org.joda.time.DateTimeZone"
            java.lang.String r17 = "org.joda.time.format.DateTimeFormatter"
            java.lang.String[] r0 = new java.lang.String[]{r9, r10, r11, r12, r13, r14, r15, r16, r17}     // Catch:{ all -> 0x0172 }
            int r3 = r0.length     // Catch:{ all -> 0x0172 }
            r9 = r7
        L_0x0159:
            if (r9 >= r3) goto L_0x0171
            r10 = r0[r9]     // Catch:{ all -> 0x0172 }
            boolean r11 = r10.equals(r4)     // Catch:{ all -> 0x0172 }
            if (r11 == 0) goto L_0x016e
            java.lang.Class r3 = java.lang.Class.forName(r10)     // Catch:{ all -> 0x0172 }
            com.alibaba.fastjson.serializer.JodaCodec r9 = com.alibaba.fastjson.serializer.JodaCodec.instance     // Catch:{ all -> 0x0172 }
            r6 = r9
            r1.putDeserializer(r3, r9)     // Catch:{ all -> 0x0172 }
            return r6
        L_0x016e:
            int r9 = r9 + 1
            goto L_0x0159
        L_0x0171:
            goto L_0x0175
        L_0x0172:
            r0 = move-exception
            jodaError = r8
        L_0x0175:
            boolean r0 = guavaError
            if (r0 != 0) goto L_0x01ad
            java.lang.String r0 = "com.google.common.collect."
            boolean r0 = r4.startsWith(r0)
            if (r0 == 0) goto L_0x01ad
            java.lang.String r0 = "com.google.common.collect.HashMultimap"
            java.lang.String r3 = "com.google.common.collect.LinkedListMultimap"
            java.lang.String r9 = "com.google.common.collect.LinkedHashMultimap"
            java.lang.String r10 = "com.google.common.collect.ArrayListMultimap"
            java.lang.String r11 = "com.google.common.collect.TreeMultimap"
            java.lang.String[] r0 = new java.lang.String[]{r0, r3, r9, r10, r11}     // Catch:{ ClassNotFoundException -> 0x01aa }
            int r3 = r0.length     // Catch:{ ClassNotFoundException -> 0x01aa }
            r9 = r7
        L_0x0191:
            if (r9 >= r3) goto L_0x01a9
            r10 = r0[r9]     // Catch:{ ClassNotFoundException -> 0x01aa }
            boolean r11 = r10.equals(r4)     // Catch:{ ClassNotFoundException -> 0x01aa }
            if (r11 == 0) goto L_0x01a6
            java.lang.Class r3 = java.lang.Class.forName(r10)     // Catch:{ ClassNotFoundException -> 0x01aa }
            com.alibaba.fastjson.serializer.GuavaCodec r9 = com.alibaba.fastjson.serializer.GuavaCodec.instance     // Catch:{ ClassNotFoundException -> 0x01aa }
            r6 = r9
            r1.putDeserializer(r3, r9)     // Catch:{ ClassNotFoundException -> 0x01aa }
            return r6
        L_0x01a6:
            int r9 = r9 + 1
            goto L_0x0191
        L_0x01a9:
            goto L_0x01ad
        L_0x01aa:
            r0 = move-exception
            guavaError = r8
        L_0x01ad:
            java.lang.String r0 = "java.nio.ByteBuffer"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x01bb
            com.alibaba.fastjson.serializer.ByteBufferCodec r0 = com.alibaba.fastjson.serializer.ByteBufferCodec.instance
            r6 = r0
            r1.putDeserializer(r2, r0)
        L_0x01bb:
            java.lang.String r0 = "java.nio.file.Path"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x01c9
            com.alibaba.fastjson.serializer.MiscCodec r0 = com.alibaba.fastjson.serializer.MiscCodec.instance
            r6 = r0
            r1.putDeserializer(r2, r0)
        L_0x01c9:
            java.lang.Class<java.util.Map$Entry> r0 = java.util.Map.Entry.class
            if (r2 != r0) goto L_0x01d3
            com.alibaba.fastjson.serializer.MiscCodec r0 = com.alibaba.fastjson.serializer.MiscCodec.instance
            r6 = r0
            r1.putDeserializer(r2, r0)
        L_0x01d3:
            java.lang.String r0 = "org.javamoney.moneta.Money"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x01e1
            com.alibaba.fastjson.support.moneta.MonetaCodec r0 = com.alibaba.fastjson.support.moneta.MonetaCodec.instance
            r6 = r0
            r1.putDeserializer(r2, r0)
        L_0x01e1:
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            java.lang.ClassLoader r3 = r0.getContextClassLoader()
            java.lang.Class<com.alibaba.fastjson.parser.deserializer.AutowiredObjectDeserializer> r0 = com.alibaba.fastjson.parser.deserializer.AutowiredObjectDeserializer.class
            java.util.Set r0 = com.alibaba.fastjson.util.ServiceLoader.load(r0, (java.lang.ClassLoader) r3)     // Catch:{ Exception -> 0x0219 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ Exception -> 0x0219 }
        L_0x01f3:
            boolean r8 = r0.hasNext()     // Catch:{ Exception -> 0x0219 }
            if (r8 == 0) goto L_0x0218
            java.lang.Object r8 = r0.next()     // Catch:{ Exception -> 0x0219 }
            com.alibaba.fastjson.parser.deserializer.AutowiredObjectDeserializer r8 = (com.alibaba.fastjson.parser.deserializer.AutowiredObjectDeserializer) r8     // Catch:{ Exception -> 0x0219 }
            java.util.Set r9 = r8.getAutowiredFor()     // Catch:{ Exception -> 0x0219 }
            java.util.Iterator r9 = r9.iterator()     // Catch:{ Exception -> 0x0219 }
        L_0x0207:
            boolean r10 = r9.hasNext()     // Catch:{ Exception -> 0x0219 }
            if (r10 == 0) goto L_0x0217
            java.lang.Object r10 = r9.next()     // Catch:{ Exception -> 0x0219 }
            java.lang.reflect.Type r10 = (java.lang.reflect.Type) r10     // Catch:{ Exception -> 0x0219 }
            r1.putDeserializer(r10, r8)     // Catch:{ Exception -> 0x0219 }
            goto L_0x0207
        L_0x0217:
            goto L_0x01f3
        L_0x0218:
            goto L_0x021a
        L_0x0219:
            r0 = move-exception
        L_0x021a:
            if (r6 != 0) goto L_0x0220
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r6 = r1.get(r5)
        L_0x0220:
            if (r6 == 0) goto L_0x0223
            return r6
        L_0x0223:
            boolean r0 = r22.isEnum()
            if (r0 == 0) goto L_0x029d
            boolean r0 = r1.jacksonCompatible
            if (r0 == 0) goto L_0x0247
            java.lang.reflect.Method[] r0 = r22.getMethods()
            int r8 = r0.length
        L_0x0232:
            if (r7 >= r8) goto L_0x0247
            r9 = r0[r7]
            boolean r10 = com.alibaba.fastjson.util.TypeUtils.isJacksonCreator(r9)
            if (r10 == 0) goto L_0x0244
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r6 = r1.createJavaBeanDeserializer(r2, r5)
            r1.putDeserializer(r5, r6)
            return r6
        L_0x0244:
            int r7 = r7 + 1
            goto L_0x0232
        L_0x0247:
            java.lang.reflect.Type r0 = com.alibaba.fastjson.JSON.getMixInAnnotations(r22)
            r7 = r0
            java.lang.Class r7 = (java.lang.Class) r7
            r0 = 0
            if (r7 == 0) goto L_0x0253
            r8 = r7
            goto L_0x0254
        L_0x0253:
            r8 = r2
        L_0x0254:
            java.lang.Class<com.alibaba.fastjson.annotation.JSONType> r9 = com.alibaba.fastjson.annotation.JSONType.class
            java.lang.annotation.Annotation r8 = com.alibaba.fastjson.util.TypeUtils.getAnnotation((java.lang.Class<?>) r8, r9)
            com.alibaba.fastjson.annotation.JSONType r8 = (com.alibaba.fastjson.annotation.JSONType) r8
            if (r8 == 0) goto L_0x026f
            java.lang.Class r9 = r8.deserializer()
            java.lang.Object r0 = r9.newInstance()     // Catch:{ all -> 0x026d }
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r0 = (com.alibaba.fastjson.parser.deserializer.ObjectDeserializer) r0     // Catch:{ all -> 0x026d }
            r6 = r0
            r1.putDeserializer(r2, r6)     // Catch:{ all -> 0x026d }
            return r6
        L_0x026d:
            r0 = move-exception
            goto L_0x0270
        L_0x026f:
            r9 = r0
        L_0x0270:
            r10 = 0
            if (r7 == 0) goto L_0x0289
            java.lang.reflect.Method r11 = getEnumCreator(r7, r2)
            if (r11 == 0) goto L_0x0288
            java.lang.String r0 = r11.getName()     // Catch:{ Exception -> 0x0287 }
            java.lang.Class[] r12 = r11.getParameterTypes()     // Catch:{ Exception -> 0x0287 }
            java.lang.reflect.Method r0 = r2.getMethod(r0, r12)     // Catch:{ Exception -> 0x0287 }
            r10 = r0
            goto L_0x0288
        L_0x0287:
            r0 = move-exception
        L_0x0288:
            goto L_0x028d
        L_0x0289:
            java.lang.reflect.Method r10 = getEnumCreator(r2, r2)
        L_0x028d:
            if (r10 == 0) goto L_0x0298
            com.alibaba.fastjson.parser.deserializer.EnumCreatorDeserializer r0 = new com.alibaba.fastjson.parser.deserializer.EnumCreatorDeserializer
            r0.<init>(r10)
            r1.putDeserializer(r2, r0)
            return r0
        L_0x0298:
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r0 = r21.getEnumDeserializer(r22)
            goto L_0x02fb
        L_0x029d:
            boolean r0 = r22.isArray()
            if (r0 == 0) goto L_0x02a6
            com.alibaba.fastjson.serializer.ObjectArrayCodec r0 = com.alibaba.fastjson.serializer.ObjectArrayCodec.instance
            goto L_0x02fb
        L_0x02a6:
            java.lang.Class<java.util.Set> r0 = java.util.Set.class
            if (r2 == r0) goto L_0x02f9
            java.lang.Class<java.util.HashSet> r0 = java.util.HashSet.class
            if (r2 == r0) goto L_0x02f9
            java.lang.Class<java.util.Collection> r0 = java.util.Collection.class
            if (r2 == r0) goto L_0x02f9
            java.lang.Class<java.util.List> r0 = java.util.List.class
            if (r2 == r0) goto L_0x02f9
            java.lang.Class<java.util.ArrayList> r0 = java.util.ArrayList.class
            if (r2 != r0) goto L_0x02bb
            goto L_0x02f9
        L_0x02bb:
            java.lang.Class<java.util.Collection> r0 = java.util.Collection.class
            boolean r0 = r0.isAssignableFrom(r2)
            if (r0 == 0) goto L_0x02c6
            com.alibaba.fastjson.serializer.CollectionCodec r0 = com.alibaba.fastjson.serializer.CollectionCodec.instance
            goto L_0x02fb
        L_0x02c6:
            java.lang.Class<java.util.Map> r0 = java.util.Map.class
            boolean r0 = r0.isAssignableFrom(r2)
            if (r0 == 0) goto L_0x02d1
            com.alibaba.fastjson.parser.deserializer.MapDeserializer r0 = com.alibaba.fastjson.parser.deserializer.MapDeserializer.instance
            goto L_0x02fb
        L_0x02d1:
            java.lang.Class<java.lang.Throwable> r0 = java.lang.Throwable.class
            boolean r0 = r0.isAssignableFrom(r2)
            if (r0 == 0) goto L_0x02df
            com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer r0 = new com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer
            r0.<init>(r1, r2)
            goto L_0x02fb
        L_0x02df:
            java.lang.Class<com.alibaba.fastjson.parser.deserializer.PropertyProcessable> r0 = com.alibaba.fastjson.parser.deserializer.PropertyProcessable.class
            boolean r0 = r0.isAssignableFrom(r2)
            if (r0 == 0) goto L_0x02ed
            com.alibaba.fastjson.parser.deserializer.PropertyProcessableDeserializer r0 = new com.alibaba.fastjson.parser.deserializer.PropertyProcessableDeserializer
            r0.<init>(r2)
            goto L_0x02fb
        L_0x02ed:
            java.lang.Class<java.net.InetAddress> r0 = java.net.InetAddress.class
            if (r2 != r0) goto L_0x02f4
            com.alibaba.fastjson.serializer.MiscCodec r0 = com.alibaba.fastjson.serializer.MiscCodec.instance
            goto L_0x02fb
        L_0x02f4:
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r0 = r1.createJavaBeanDeserializer(r2, r5)
            goto L_0x02fb
        L_0x02f9:
            com.alibaba.fastjson.serializer.CollectionCodec r0 = com.alibaba.fastjson.serializer.CollectionCodec.instance
        L_0x02fb:
            r1.putDeserializer(r5, r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.ParserConfig.getDeserializer(java.lang.Class, java.lang.reflect.Type):com.alibaba.fastjson.parser.deserializer.ObjectDeserializer");
    }

    private static Method getEnumCreator(Class clazz, Class enumClass) {
        for (Method method : clazz.getMethods()) {
            if (Modifier.isStatic(method.getModifiers()) && method.getReturnType() == enumClass && method.getParameterTypes().length == 1 && ((JSONCreator) method.getAnnotation(JSONCreator.class)) != null) {
                return method;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public ObjectDeserializer getEnumDeserializer(Class<?> clazz) {
        return new EnumDeserializer(clazz);
    }

    public void initJavaBeanDeserializers(Class<?>... classes) {
        if (classes != null) {
            for (Class<?> type : classes) {
                if (type != null) {
                    putDeserializer(type, createJavaBeanDeserializer(type, type));
                }
            }
        }
    }

    public ObjectDeserializer createJavaBeanDeserializer(Class<?> clazz, Type type) {
        Method method;
        ASMDeserializerFactory aSMDeserializerFactory;
        boolean asmEnable2 = this.asmEnable & (!this.fieldBased);
        int i = 0;
        if (asmEnable2) {
            JSONType jsonType = (JSONType) TypeUtils.getAnnotation(clazz, JSONType.class);
            if (jsonType != null) {
                Class<?> deserializerClass = jsonType.deserializer();
                if (deserializerClass != Void.class) {
                    try {
                        Object newInstance = deserializerClass.newInstance();
                        if (newInstance instanceof ObjectDeserializer) {
                            return (ObjectDeserializer) newInstance;
                        }
                    } catch (Throwable th) {
                    }
                }
                asmEnable2 = jsonType.asm() && jsonType.parseFeatures().length == 0;
            }
            if (asmEnable2) {
                Class<?> superClass = JavaBeanInfo.getBuilderClass(clazz, jsonType);
                if (superClass == null) {
                    superClass = clazz;
                }
                while (true) {
                    if (Modifier.isPublic(superClass.getModifiers())) {
                        superClass = superClass.getSuperclass();
                        if (superClass != Object.class) {
                            if (superClass == null) {
                                break;
                            }
                        } else {
                            break;
                        }
                    } else {
                        asmEnable2 = false;
                        break;
                    }
                }
            }
        }
        if (clazz.getTypeParameters().length != 0) {
            asmEnable2 = false;
        }
        if (asmEnable2 && (aSMDeserializerFactory = this.asmFactory) != null && aSMDeserializerFactory.classLoader.isExternalClass(clazz)) {
            asmEnable2 = false;
        }
        if (asmEnable2) {
            asmEnable2 = ASMUtils.checkName(clazz.getSimpleName());
        }
        if (asmEnable2) {
            if (clazz.isInterface()) {
                asmEnable2 = false;
            }
            JavaBeanInfo beanInfo = JavaBeanInfo.build(clazz, type, this.propertyNamingStrategy, false, TypeUtils.compatibleWithJavaBean, this.jacksonCompatible);
            if (asmEnable2 && beanInfo.fields.length > 200) {
                asmEnable2 = false;
            }
            Constructor<?> defaultConstructor = beanInfo.defaultConstructor;
            if (asmEnable2 && defaultConstructor == null && !clazz.isInterface()) {
                asmEnable2 = false;
            }
            FieldInfo[] fieldInfoArr = beanInfo.fields;
            int length = fieldInfoArr.length;
            while (true) {
                if (i >= length) {
                    break;
                }
                FieldInfo fieldInfo = fieldInfoArr[i];
                if (!fieldInfo.getOnly) {
                    Class<?> fieldClass = fieldInfo.fieldClass;
                    if (Modifier.isPublic(fieldClass.getModifiers())) {
                        if (!fieldClass.isMemberClass() || Modifier.isStatic(fieldClass.getModifiers())) {
                            if (fieldInfo.getMember() != null && !ASMUtils.checkName(fieldInfo.getMember().getName())) {
                                asmEnable2 = false;
                                break;
                            }
                            JSONField annotation = fieldInfo.getAnnotation();
                            if ((annotation == null || (ASMUtils.checkName(annotation.name()) && annotation.format().length() == 0 && annotation.deserializeUsing() == Void.class && annotation.parseFeatures().length == 0 && !annotation.unwrapped())) && ((method = fieldInfo.method) == null || method.getParameterTypes().length <= 1)) {
                                if (fieldClass.isEnum() && !(getDeserializer((Type) fieldClass) instanceof EnumDeserializer)) {
                                    asmEnable2 = false;
                                    break;
                                }
                                i++;
                            }
                        } else {
                            asmEnable2 = false;
                            break;
                        }
                    } else {
                        asmEnable2 = false;
                        break;
                    }
                } else {
                    asmEnable2 = false;
                    break;
                }
            }
            asmEnable2 = false;
        }
        if (asmEnable2 && clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers())) {
            asmEnable2 = false;
        }
        if (asmEnable2 && TypeUtils.isXmlField(clazz)) {
            asmEnable2 = false;
        }
        if (!asmEnable2) {
            return new JavaBeanDeserializer(this, clazz, type);
        }
        JavaBeanInfo beanInfo2 = JavaBeanInfo.build(clazz, type, this.propertyNamingStrategy);
        try {
            return this.asmFactory.createJavaBeanDeserializer(this, beanInfo2);
        } catch (NoSuchMethodException e) {
            return new JavaBeanDeserializer(this, clazz, type);
        } catch (JSONException e2) {
            return new JavaBeanDeserializer(this, beanInfo2);
        } catch (Exception e3) {
            throw new JSONException("create asm deserializer error, " + clazz.getName(), e3);
        }
    }

    public FieldDeserializer createFieldDeserializer(ParserConfig mapping, JavaBeanInfo beanInfo, FieldInfo fieldInfo) {
        Class<?> clazz = beanInfo.clazz;
        Class<?> fieldClass = fieldInfo.fieldClass;
        Class<?> deserializeUsing = null;
        JSONField annotation = fieldInfo.getAnnotation();
        if (annotation != null && (deserializeUsing = annotation.deserializeUsing()) == Void.class) {
            deserializeUsing = null;
        }
        if (deserializeUsing == null && (fieldClass == List.class || fieldClass == ArrayList.class)) {
            return new ArrayListTypeFieldDeserializer(mapping, clazz, fieldInfo);
        }
        return new DefaultFieldDeserializer(mapping, clazz, fieldInfo);
    }

    public void putDeserializer(Type type, ObjectDeserializer deserializer) {
        Type mixin = JSON.getMixInAnnotations(type);
        if (mixin != null) {
            IdentityHashMap<Type, ObjectDeserializer> mixInClasses = this.mixInDeserializers.get(type);
            if (mixInClasses == null) {
                mixInClasses = new IdentityHashMap<>(4);
                this.mixInDeserializers.put(type, mixInClasses);
            }
            mixInClasses.put(mixin, deserializer);
            return;
        }
        this.deserializers.put(type, deserializer);
    }

    public ObjectDeserializer get(Type type) {
        Type mixin = JSON.getMixInAnnotations(type);
        if (mixin == null) {
            return this.deserializers.get(type);
        }
        IdentityHashMap<Type, ObjectDeserializer> mixInClasses = this.mixInDeserializers.get(type);
        if (mixInClasses == null) {
            return null;
        }
        return mixInClasses.get(mixin);
    }

    public ObjectDeserializer getDeserializer(FieldInfo fieldInfo) {
        return getDeserializer(fieldInfo.fieldClass, fieldInfo.fieldType);
    }

    public boolean isPrimitive(Class<?> clazz) {
        return isPrimitive2(clazz);
    }

    public static boolean isPrimitive2(Class<?> clazz) {
        return clazz.isPrimitive() || clazz == Boolean.class || clazz == Character.class || clazz == Byte.class || clazz == Short.class || clazz == Integer.class || clazz == Long.class || clazz == Float.class || clazz == Double.class || clazz == BigInteger.class || clazz == BigDecimal.class || clazz == String.class || clazz == java.util.Date.class || clazz == Date.class || clazz == Time.class || clazz == Timestamp.class || clazz.isEnum();
    }

    public static void parserAllFieldToCache(Class<?> clazz, Map<String, Field> fieldCacheMap) {
        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            if (!fieldCacheMap.containsKey(fieldName)) {
                fieldCacheMap.put(fieldName, field);
            }
        }
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            parserAllFieldToCache(clazz.getSuperclass(), fieldCacheMap);
        }
    }

    public static Field getFieldFromCache(String fieldName, Map<String, Field> fieldCacheMap) {
        Field field = fieldCacheMap.get(fieldName);
        if (field == null) {
            field = fieldCacheMap.get("_" + fieldName);
        }
        if (field == null) {
            field = fieldCacheMap.get("m_" + fieldName);
        }
        if (field != null) {
            return field;
        }
        char c0 = fieldName.charAt(0);
        if (c0 >= 'a' && c0 <= 'z') {
            char[] chars = fieldName.toCharArray();
            chars[0] = (char) (chars[0] - ' ');
            field = fieldCacheMap.get(new String(chars));
        }
        if (fieldName.length() <= 2) {
            return field;
        }
        char c1 = fieldName.charAt(1);
        if (fieldName.length() <= 2 || c0 < 'a' || c0 > 'z' || c1 < 'A' || c1 > 'Z') {
            return field;
        }
        for (Map.Entry<String, Field> entry : fieldCacheMap.entrySet()) {
            if (fieldName.equalsIgnoreCase(entry.getKey())) {
                return entry.getValue();
            }
        }
        return field;
    }

    public ClassLoader getDefaultClassLoader() {
        return this.defaultClassLoader;
    }

    public void setDefaultClassLoader(ClassLoader defaultClassLoader2) {
        this.defaultClassLoader = defaultClassLoader2;
    }

    public void addDenyInternal(String name) {
        if (name != null && name.length() != 0) {
            long hash = TypeUtils.fnv1a_64(name);
            long[] jArr = this.internalDenyHashCodes;
            if (jArr == null) {
                this.internalDenyHashCodes = new long[]{hash};
            } else if (Arrays.binarySearch(jArr, hash) < 0) {
                long[] jArr2 = this.internalDenyHashCodes;
                long[] hashCodes = new long[(jArr2.length + 1)];
                hashCodes[hashCodes.length - 1] = hash;
                System.arraycopy(jArr2, 0, hashCodes, 0, jArr2.length);
                Arrays.sort(hashCodes);
                this.internalDenyHashCodes = hashCodes;
            }
        }
    }

    public void addDeny(String name) {
        if (name != null && name.length() != 0) {
            long hash = TypeUtils.fnv1a_64(name);
            if (Arrays.binarySearch(this.denyHashCodes, hash) < 0) {
                long[] jArr = this.denyHashCodes;
                long[] hashCodes = new long[(jArr.length + 1)];
                hashCodes[hashCodes.length - 1] = hash;
                System.arraycopy(jArr, 0, hashCodes, 0, jArr.length);
                Arrays.sort(hashCodes);
                this.denyHashCodes = hashCodes;
            }
        }
    }

    public void addAccept(String name) {
        if (name != null && name.length() != 0) {
            long hash = TypeUtils.fnv1a_64(name);
            if (Arrays.binarySearch(this.acceptHashCodes, hash) < 0) {
                long[] jArr = this.acceptHashCodes;
                long[] hashCodes = new long[(jArr.length + 1)];
                hashCodes[hashCodes.length - 1] = hash;
                System.arraycopy(jArr, 0, hashCodes, 0, jArr.length);
                Arrays.sort(hashCodes);
                this.acceptHashCodes = hashCodes;
            }
        }
    }

    public Class<?> checkAutoType(Class type) {
        if (get(type) != null) {
            return type;
        }
        return checkAutoType(type.getName(), (Class<?>) null, JSON.DEFAULT_PARSER_FEATURE);
    }

    public Class<?> checkAutoType(String typeName, Class<?> expectClass) {
        return checkAutoType(typeName, expectClass, JSON.DEFAULT_PARSER_FEATURE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v49, resolved type: java.lang.Class<?>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Class<?> checkAutoType(java.lang.String r36, java.lang.Class<?> r37, int r38) {
        /*
            r35 = this;
            r1 = r35
            r2 = r36
            r3 = r37
            r4 = r38
            if (r2 != 0) goto L_0x000c
            r0 = 0
            return r0
        L_0x000c:
            java.util.List<com.alibaba.fastjson.parser.ParserConfig$AutoTypeCheckHandler> r0 = r1.autoTypeCheckHandlers
            if (r0 == 0) goto L_0x002a
            java.util.List<com.alibaba.fastjson.parser.ParserConfig$AutoTypeCheckHandler> r0 = r1.autoTypeCheckHandlers
            java.util.Iterator r0 = r0.iterator()
        L_0x0016:
            boolean r5 = r0.hasNext()
            if (r5 == 0) goto L_0x002a
            java.lang.Object r5 = r0.next()
            com.alibaba.fastjson.parser.ParserConfig$AutoTypeCheckHandler r5 = (com.alibaba.fastjson.parser.ParserConfig.AutoTypeCheckHandler) r5
            java.lang.Class r6 = r5.handler(r2, r3, r4)
            if (r6 == 0) goto L_0x0029
            return r6
        L_0x0029:
            goto L_0x0016
        L_0x002a:
            com.alibaba.fastjson.parser.Feature r0 = com.alibaba.fastjson.parser.Feature.SafeMode
            int r5 = r0.mask
            boolean r0 = r1.safeMode
            r6 = 0
            if (r0 != 0) goto L_0x003f
            r0 = r4 & r5
            if (r0 != 0) goto L_0x003f
            int r0 = com.alibaba.fastjson.JSON.DEFAULT_PARSER_FEATURE
            r0 = r0 & r5
            if (r0 == 0) goto L_0x003d
            goto L_0x003f
        L_0x003d:
            r0 = r6
            goto L_0x0040
        L_0x003f:
            r0 = 1
        L_0x0040:
            r8 = r0
            if (r8 != 0) goto L_0x044b
            int r0 = r36.length()
            r9 = 192(0xc0, float:2.69E-43)
            java.lang.String r10 = "autoType is not support. "
            if (r0 >= r9) goto L_0x0432
            int r0 = r36.length()
            r9 = 3
            if (r0 < r9) goto L_0x0432
            if (r3 != 0) goto L_0x0059
            r0 = 0
            r9 = r0
            goto L_0x00c1
        L_0x0059:
            java.lang.String r0 = r37.getName()
            long r11 = com.alibaba.fastjson.util.TypeUtils.fnv1a_64(r0)
            r13 = -8024746738719829346(0x90a25f5baa21529e, double:-1.514751450580626E-228)
            int r0 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r0 == 0) goto L_0x00bf
            r13 = 3247277300971823414(0x2d10a5801b9d6136, double:1.2768618085266423E-91)
            int r0 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r0 == 0) goto L_0x00bf
            r13 = -5811778396720452501(0xaf586a571e302c6b, double:-1.2869594668238042E-80)
            int r0 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r0 == 0) goto L_0x00bf
            r13 = -1368967840069965882(0xed007300a7b227c6, double:-1.1341028219519378E217)
            int r0 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r0 == 0) goto L_0x00bf
            r13 = 2980334044947851925(0x295c4605fd1eaa95, double:1.8810554767322845E-109)
            int r0 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r0 == 0) goto L_0x00bf
            r13 = 5183404141909004468(0x47ef269aadc650b4, double:3.312520992710671E38)
            int r0 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r0 == 0) goto L_0x00bf
            r13 = 7222019943667248779(0x6439c4dff712ae8b, double:6.373467611436065E174)
            int r0 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r0 == 0) goto L_0x00bf
            r13 = -2027296626235911549(0xe3dd9875a2dc5283, double:-1.1437309411088266E173)
            int r0 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r0 == 0) goto L_0x00bf
            r13 = -2114196234051346931(0xe2a8ddba03e69e0d, double:-1.8328867399748285E167)
            int r0 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r0 == 0) goto L_0x00bf
            r13 = -2939497380989775398(0xd734ceb4c3e9d1da, double:-1.2509996135591577E112)
            int r0 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r0 != 0) goto L_0x00bc
            goto L_0x00bf
        L_0x00bc:
            r0 = 1
            r9 = r0
            goto L_0x00c1
        L_0x00bf:
            r0 = 0
            r9 = r0
        L_0x00c1:
            r0 = 36
            r11 = 46
            java.lang.String r12 = r2.replace(r0, r11)
            r13 = -3750763034362895579(0xcbf29ce484222325, double:-7.302176725335867E57)
            r15 = 1099511628211(0x100000001b3, double:5.43230922702E-312)
            char r0 = r12.charAt(r6)
            long r6 = (long) r0
            r19 = -3750763034362895579(0xcbf29ce484222325, double:-7.302176725335867E57)
            long r6 = r6 ^ r19
            r21 = 1099511628211(0x100000001b3, double:5.43230922702E-312)
            long r6 = r6 * r21
            r23 = -5808493101479473382(0xaf64164c86024f1a, double:-2.1176223865607047E-80)
            int r0 = (r6 > r23 ? 1 : (r6 == r23 ? 0 : -1))
            if (r0 == 0) goto L_0x041b
            int r0 = r12.length()
            r18 = 1
            int r0 = r0 + -1
            char r0 = r12.charAt(r0)
            r24 = r12
            long r11 = (long) r0
            long r11 = r11 ^ r6
            long r11 = r11 * r21
            r25 = 655701488918567152(0x9198507b5af98f0, double:7.914409534561175E-265)
            int r0 = (r11 > r25 ? 1 : (r11 == r25 ? 0 : -1))
            if (r0 == 0) goto L_0x0404
            r11 = r24
            r12 = 0
            char r0 = r11.charAt(r12)
            r12 = r5
            r24 = r6
            long r5 = (long) r0
            long r5 = r5 ^ r19
            long r5 = r5 * r21
            r7 = 1
            char r0 = r11.charAt(r7)
            r19 = r8
            long r7 = (long) r0
            long r5 = r5 ^ r7
            long r5 = r5 * r21
            r0 = 2
            char r0 = r11.charAt(r0)
            long r7 = (long) r0
            long r5 = r5 ^ r7
            long r5 = r5 * r21
            long r7 = com.alibaba.fastjson.util.TypeUtils.fnv1a_64(r11)
            long[] r0 = INTERNAL_WHITELIST_HASHCODES
            int r0 = java.util.Arrays.binarySearch(r0, r7)
            if (r0 < 0) goto L_0x013b
            r0 = 1
            goto L_0x013c
        L_0x013b:
            r0 = 0
        L_0x013c:
            r20 = r0
            long[] r0 = r1.internalDenyHashCodes
            if (r0 == 0) goto L_0x0185
            r26 = r5
            r0 = 3
        L_0x0145:
            r28 = r12
            int r12 = r11.length()
            if (r0 >= r12) goto L_0x0180
            char r12 = r11.charAt(r0)
            r29 = r13
            long r12 = (long) r12
            long r12 = r26 ^ r12
            long r12 = r12 * r21
            long[] r14 = r1.internalDenyHashCodes
            int r14 = java.util.Arrays.binarySearch(r14, r12)
            if (r14 >= 0) goto L_0x0169
            int r0 = r0 + 1
            r26 = r12
            r12 = r28
            r13 = r29
            goto L_0x0145
        L_0x0169:
            com.alibaba.fastjson.JSONException r14 = new com.alibaba.fastjson.JSONException
            r31 = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r10)
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            r14.<init>(r0)
            throw r14
        L_0x0180:
            r31 = r0
            r29 = r13
            goto L_0x0189
        L_0x0185:
            r28 = r12
            r29 = r13
        L_0x0189:
            if (r20 != 0) goto L_0x01ed
            boolean r0 = r1.autoTypeSupport
            if (r0 != 0) goto L_0x0195
            if (r9 == 0) goto L_0x0192
            goto L_0x0195
        L_0x0192:
            r26 = r15
            goto L_0x01ef
        L_0x0195:
            r12 = r5
            r0 = 3
        L_0x0197:
            int r14 = r11.length()
            if (r0 >= r14) goto L_0x01ea
            char r14 = r11.charAt(r0)
            r26 = r15
            long r14 = (long) r14
            long r12 = r12 ^ r14
            long r12 = r12 * r21
            long[] r14 = r1.acceptHashCodes
            int r14 = java.util.Arrays.binarySearch(r14, r12)
            if (r14 < 0) goto L_0x01b9
            java.lang.ClassLoader r14 = r1.defaultClassLoader
            r15 = 1
            java.lang.Class r14 = com.alibaba.fastjson.util.TypeUtils.loadClass(r2, r14, r15)
            if (r14 == 0) goto L_0x01b9
            return r14
        L_0x01b9:
            long[] r14 = r1.denyHashCodes
            int r14 = java.util.Arrays.binarySearch(r14, r12)
            if (r14 < 0) goto L_0x01e5
            java.lang.Class r14 = com.alibaba.fastjson.util.TypeUtils.getClassFromMapping(r36)
            if (r14 != 0) goto L_0x01e5
            long[] r14 = r1.acceptHashCodes
            int r14 = java.util.Arrays.binarySearch(r14, r7)
            if (r14 < 0) goto L_0x01d0
            goto L_0x01e5
        L_0x01d0:
            com.alibaba.fastjson.JSONException r14 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r10)
            r15.append(r2)
            java.lang.String r10 = r15.toString()
            r14.<init>(r10)
            throw r14
        L_0x01e5:
            int r0 = r0 + 1
            r15 = r26
            goto L_0x0197
        L_0x01ea:
            r26 = r15
            goto L_0x01ef
        L_0x01ed:
            r26 = r15
        L_0x01ef:
            java.lang.Class r0 = com.alibaba.fastjson.util.TypeUtils.getClassFromMapping(r36)
            if (r0 != 0) goto L_0x01fb
            com.alibaba.fastjson.util.IdentityHashMap<java.lang.reflect.Type, com.alibaba.fastjson.parser.deserializer.ObjectDeserializer> r12 = r1.deserializers
            java.lang.Class r0 = r12.findClass(r2)
        L_0x01fb:
            if (r0 != 0) goto L_0x0206
            java.util.concurrent.ConcurrentMap<java.lang.String, java.lang.Class<?>> r12 = r1.typeMapping
            java.lang.Object r12 = r12.get(r2)
            r0 = r12
            java.lang.Class r0 = (java.lang.Class) r0
        L_0x0206:
            if (r20 == 0) goto L_0x0211
            java.lang.ClassLoader r12 = r1.defaultClassLoader
            r13 = 1
            java.lang.Class r0 = com.alibaba.fastjson.util.TypeUtils.loadClass(r2, r12, r13)
            r12 = r0
            goto L_0x0212
        L_0x0211:
            r12 = r0
        L_0x0212:
            java.lang.String r13 = " -> "
            java.lang.String r14 = "type not match. "
            if (r12 == 0) goto L_0x0246
            if (r3 == 0) goto L_0x0245
            java.lang.Class<java.util.HashMap> r0 = java.util.HashMap.class
            if (r12 == r0) goto L_0x0245
            boolean r0 = r3.isAssignableFrom(r12)
            if (r0 == 0) goto L_0x0226
            goto L_0x0245
        L_0x0226:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r14)
            r10.append(r2)
            r10.append(r13)
            java.lang.String r13 = r37.getName()
            r10.append(r13)
            java.lang.String r10 = r10.toString()
            r0.<init>(r10)
            throw r0
        L_0x0245:
            return r12
        L_0x0246:
            boolean r0 = r1.autoTypeSupport
            if (r0 != 0) goto L_0x02c1
            r15 = r5
            r0 = 3
        L_0x024c:
            r31 = r5
            int r5 = r11.length()
            if (r0 >= r5) goto L_0x02be
            char r5 = r11.charAt(r0)
            r33 = r7
            long r6 = (long) r5
            long r6 = r6 ^ r15
            long r6 = r6 * r21
            long[] r8 = r1.denyHashCodes
            int r8 = java.util.Arrays.binarySearch(r8, r6)
            if (r8 >= 0) goto L_0x02a9
            long[] r8 = r1.acceptHashCodes
            int r8 = java.util.Arrays.binarySearch(r8, r6)
            if (r8 < 0) goto L_0x02a1
            java.lang.ClassLoader r8 = r1.defaultClassLoader
            r10 = 1
            java.lang.Class r8 = com.alibaba.fastjson.util.TypeUtils.loadClass(r2, r8, r10)
            if (r8 != 0) goto L_0x0278
            return r3
        L_0x0278:
            if (r3 == 0) goto L_0x02a0
            boolean r10 = r3.isAssignableFrom(r8)
            if (r10 != 0) goto L_0x0281
            goto L_0x02a0
        L_0x0281:
            com.alibaba.fastjson.JSONException r10 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r14)
            r12.append(r2)
            r12.append(r13)
            java.lang.String r13 = r37.getName()
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            r10.<init>(r12)
            throw r10
        L_0x02a0:
            return r8
        L_0x02a1:
            int r0 = r0 + 1
            r15 = r6
            r5 = r31
            r7 = r33
            goto L_0x024c
        L_0x02a9:
            com.alibaba.fastjson.JSONException r8 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r10)
            r13.append(r2)
            java.lang.String r10 = r13.toString()
            r8.<init>(r10)
            throw r8
        L_0x02be:
            r33 = r7
            goto L_0x02c5
        L_0x02c1:
            r31 = r5
            r33 = r7
        L_0x02c5:
            r5 = 0
            r6 = 0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0330, all -> 0x0329 }
            r0.<init>()     // Catch:{ Exception -> 0x0330, all -> 0x0329 }
            r7 = 47
            r8 = 46
            java.lang.String r7 = r2.replace(r8, r7)     // Catch:{ Exception -> 0x0330, all -> 0x0329 }
            r0.append(r7)     // Catch:{ Exception -> 0x0330, all -> 0x0329 }
            java.lang.String r7 = ".class"
            r0.append(r7)     // Catch:{ Exception -> 0x0330, all -> 0x0329 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0330, all -> 0x0329 }
            java.lang.ClassLoader r7 = r1.defaultClassLoader     // Catch:{ Exception -> 0x0330, all -> 0x0329 }
            if (r7 == 0) goto L_0x02f3
            java.io.InputStream r7 = r7.getResourceAsStream(r0)     // Catch:{ Exception -> 0x02ee, all -> 0x02ea }
            r6 = r7
            goto L_0x02fe
        L_0x02ea:
            r0 = move-exception
            r16 = r5
            goto L_0x032c
        L_0x02ee:
            r0 = move-exception
            r16 = r5
            r5 = 0
            goto L_0x0334
        L_0x02f3:
            java.lang.Class<com.alibaba.fastjson.parser.ParserConfig> r7 = com.alibaba.fastjson.parser.ParserConfig.class
            java.lang.ClassLoader r7 = r7.getClassLoader()     // Catch:{ Exception -> 0x0330, all -> 0x0329 }
            java.io.InputStream r7 = r7.getResourceAsStream(r0)     // Catch:{ Exception -> 0x0330, all -> 0x0329 }
            r6 = r7
        L_0x02fe:
            if (r6 == 0) goto L_0x0323
            com.alibaba.fastjson.asm.ClassReader r7 = new com.alibaba.fastjson.asm.ClassReader     // Catch:{ Exception -> 0x0330, all -> 0x0329 }
            r8 = 1
            r7.<init>(r6, r8)     // Catch:{ Exception -> 0x0330, all -> 0x0329 }
            com.alibaba.fastjson.asm.TypeCollector r15 = new com.alibaba.fastjson.asm.TypeCollector     // Catch:{ Exception -> 0x0330, all -> 0x0329 }
            java.lang.String r8 = "<clinit>"
            r17 = r0
            r16 = r5
            r5 = 0
            java.lang.Class[] r0 = new java.lang.Class[r5]     // Catch:{ Exception -> 0x0321, all -> 0x031f }
            r15.<init>(r8, r0)     // Catch:{ Exception -> 0x0321, all -> 0x031f }
            r0 = r15
            r7.accept(r0)     // Catch:{ Exception -> 0x0321, all -> 0x031f }
            boolean r8 = r0.hasJsonType()     // Catch:{ Exception -> 0x0321, all -> 0x031f }
            r16 = r8
            goto L_0x0334
        L_0x031f:
            r0 = move-exception
            goto L_0x032c
        L_0x0321:
            r0 = move-exception
            goto L_0x0334
        L_0x0323:
            r17 = r0
            r16 = r5
            r5 = 0
            goto L_0x0334
        L_0x0329:
            r0 = move-exception
            r16 = r5
        L_0x032c:
            com.alibaba.fastjson.util.IOUtils.close(r6)
            throw r0
        L_0x0330:
            r0 = move-exception
            r16 = r5
            r5 = 0
        L_0x0334:
            com.alibaba.fastjson.util.IOUtils.close(r6)
            com.alibaba.fastjson.parser.Feature r0 = com.alibaba.fastjson.parser.Feature.SupportAutoType
            int r0 = r0.mask
            boolean r7 = r1.autoTypeSupport
            if (r7 != 0) goto L_0x034c
            r7 = r4 & r0
            if (r7 != 0) goto L_0x034c
            int r7 = com.alibaba.fastjson.JSON.DEFAULT_PARSER_FEATURE
            r7 = r7 & r0
            if (r7 == 0) goto L_0x034a
            goto L_0x034c
        L_0x034a:
            r7 = r5
            goto L_0x034d
        L_0x034c:
            r7 = 1
        L_0x034d:
            if (r7 != 0) goto L_0x0353
            if (r16 != 0) goto L_0x0353
            if (r9 == 0) goto L_0x0365
        L_0x0353:
            if (r7 != 0) goto L_0x035b
            if (r16 == 0) goto L_0x0358
            goto L_0x035b
        L_0x0358:
            r18 = r5
            goto L_0x035d
        L_0x035b:
            r18 = 1
        L_0x035d:
            r5 = r18
            java.lang.ClassLoader r8 = r1.defaultClassLoader
            java.lang.Class r12 = com.alibaba.fastjson.util.TypeUtils.loadClass(r2, r8, r5)
        L_0x0365:
            if (r12 == 0) goto L_0x03e7
            if (r16 == 0) goto L_0x036d
            com.alibaba.fastjson.util.TypeUtils.addMapping(r2, r12)
            return r12
        L_0x036d:
            java.lang.Class<java.lang.ClassLoader> r5 = java.lang.ClassLoader.class
            boolean r5 = r5.isAssignableFrom(r12)
            if (r5 != 0) goto L_0x03d2
            java.lang.Class<javax.sql.DataSource> r5 = javax.sql.DataSource.class
            boolean r5 = r5.isAssignableFrom(r12)
            if (r5 != 0) goto L_0x03d2
            java.lang.Class<javax.sql.RowSet> r5 = javax.sql.RowSet.class
            boolean r5 = r5.isAssignableFrom(r12)
            if (r5 != 0) goto L_0x03d2
            if (r3 == 0) goto L_0x03b0
            boolean r5 = r3.isAssignableFrom(r12)
            if (r5 == 0) goto L_0x0391
            com.alibaba.fastjson.util.TypeUtils.addMapping(r2, r12)
            return r12
        L_0x0391:
            com.alibaba.fastjson.JSONException r5 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r14)
            r8.append(r2)
            r8.append(r13)
            java.lang.String r10 = r37.getName()
            r8.append(r10)
            java.lang.String r8 = r8.toString()
            r5.<init>(r8)
            throw r5
        L_0x03b0:
            com.alibaba.fastjson.PropertyNamingStrategy r5 = r1.propertyNamingStrategy
            com.alibaba.fastjson.util.JavaBeanInfo r5 = com.alibaba.fastjson.util.JavaBeanInfo.build(r12, r12, r5)
            java.lang.reflect.Constructor<?> r8 = r5.creatorConstructor
            if (r8 == 0) goto L_0x03e7
            if (r7 != 0) goto L_0x03bd
            goto L_0x03e7
        L_0x03bd:
            com.alibaba.fastjson.JSONException r8 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r10)
            r13.append(r2)
            java.lang.String r10 = r13.toString()
            r8.<init>(r10)
            throw r8
        L_0x03d2:
            com.alibaba.fastjson.JSONException r5 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r10)
            r8.append(r2)
            java.lang.String r8 = r8.toString()
            r5.<init>(r8)
            throw r5
        L_0x03e7:
            if (r7 == 0) goto L_0x03ef
            if (r12 == 0) goto L_0x03ee
            com.alibaba.fastjson.util.TypeUtils.addMapping(r2, r12)
        L_0x03ee:
            return r12
        L_0x03ef:
            com.alibaba.fastjson.JSONException r5 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r10)
            r8.append(r2)
            java.lang.String r8 = r8.toString()
            r5.<init>(r8)
            throw r5
        L_0x0404:
            r28 = r5
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r10)
            r5.append(r2)
            java.lang.String r5 = r5.toString()
            r0.<init>(r5)
            throw r0
        L_0x041b:
            r28 = r5
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r10)
            r5.append(r2)
            java.lang.String r5 = r5.toString()
            r0.<init>(r5)
            throw r0
        L_0x0432:
            r28 = r5
            r19 = r8
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r10)
            r5.append(r2)
            java.lang.String r5 = r5.toString()
            r0.<init>(r5)
            throw r0
        L_0x044b:
            r28 = r5
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "safeMode not support autoType : "
            r5.append(r6)
            r5.append(r2)
            java.lang.String r5 = r5.toString()
            r0.<init>(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.ParserConfig.checkAutoType(java.lang.String, java.lang.Class, int):java.lang.Class");
    }

    public void clearDeserializers() {
        this.deserializers.clear();
        initDeserializers();
    }

    public boolean isJacksonCompatible() {
        return this.jacksonCompatible;
    }

    public void setJacksonCompatible(boolean jacksonCompatible2) {
        this.jacksonCompatible = jacksonCompatible2;
    }

    public void register(String typeName, Class type) {
        this.typeMapping.putIfAbsent(typeName, type);
    }

    public void register(Module module) {
        this.modules.add(module);
    }

    public void addAutoTypeCheckHandler(AutoTypeCheckHandler h) {
        List<AutoTypeCheckHandler> autoTypeCheckHandlers2 = this.autoTypeCheckHandlers;
        if (autoTypeCheckHandlers2 == null) {
            CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
            autoTypeCheckHandlers2 = copyOnWriteArrayList;
            this.autoTypeCheckHandlers = copyOnWriteArrayList;
        }
        autoTypeCheckHandlers2.add(h);
    }
}
