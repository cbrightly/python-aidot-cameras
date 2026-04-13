package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import io.netty.util.internal.StringUtil;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class JavaBeanDeserializer implements ObjectDeserializer {
    private final Map<String, FieldDeserializer> alterNameFieldDeserializers;
    private final ParserConfig.AutoTypeCheckHandler autoTypeCheckHandler;
    public final JavaBeanInfo beanInfo;
    protected final Class<?> clazz;
    private ConcurrentMap<String, Object> extraFieldDeserializers;
    private Map<String, FieldDeserializer> fieldDeserializerMap;
    private final FieldDeserializer[] fieldDeserializers;
    private transient long[] hashArray;
    private transient short[] hashArrayMapping;
    private transient long[] smartMatchHashArray;
    private transient short[] smartMatchHashArrayMapping;
    protected final FieldDeserializer[] sortedFieldDeserializers;

    public JavaBeanDeserializer(ParserConfig config, Class<?> clazz2) {
        this(config, clazz2, clazz2);
    }

    public JavaBeanDeserializer(ParserConfig config, Class<?> clazz2, Type type) {
        this(config, JavaBeanInfo.build(clazz2, type, config.propertyNamingStrategy, config.fieldBased, config.compatibleWithJavaBean, config.isJacksonCompatible()));
    }

    public JavaBeanDeserializer(ParserConfig config, JavaBeanInfo beanInfo2) {
        this.clazz = beanInfo2.clazz;
        this.beanInfo = beanInfo2;
        ParserConfig.AutoTypeCheckHandler autoTypeCheckHandler2 = null;
        JSONType jSONType = beanInfo2.jsonType;
        if (!(jSONType == null || jSONType.autoTypeCheckHandler() == ParserConfig.AutoTypeCheckHandler.class)) {
            try {
                autoTypeCheckHandler2 = (ParserConfig.AutoTypeCheckHandler) beanInfo2.jsonType.autoTypeCheckHandler().newInstance();
            } catch (Exception e) {
            }
        }
        this.autoTypeCheckHandler = autoTypeCheckHandler2;
        Map<String, FieldDeserializer> alterNameFieldDeserializers2 = null;
        FieldInfo[] fieldInfoArr = beanInfo2.sortedFields;
        this.sortedFieldDeserializers = new FieldDeserializer[fieldInfoArr.length];
        int size = fieldInfoArr.length;
        for (int i = 0; i < size; i++) {
            FieldInfo fieldInfo = beanInfo2.sortedFields[i];
            FieldDeserializer fieldDeserializer = config.createFieldDeserializer(config, beanInfo2, fieldInfo);
            this.sortedFieldDeserializers[i] = fieldDeserializer;
            if (size > 128) {
                if (this.fieldDeserializerMap == null) {
                    this.fieldDeserializerMap = new HashMap();
                }
                this.fieldDeserializerMap.put(fieldInfo.name, fieldDeserializer);
            }
            for (String name : fieldInfo.alternateNames) {
                if (alterNameFieldDeserializers2 == null) {
                    alterNameFieldDeserializers2 = new HashMap<>();
                }
                alterNameFieldDeserializers2.put(name, fieldDeserializer);
            }
        }
        this.alterNameFieldDeserializers = alterNameFieldDeserializers2;
        FieldInfo[] fieldInfoArr2 = beanInfo2.fields;
        this.fieldDeserializers = new FieldDeserializer[fieldInfoArr2.length];
        int size2 = fieldInfoArr2.length;
        for (int i2 = 0; i2 < size2; i2++) {
            this.fieldDeserializers[i2] = getFieldDeserializer(beanInfo2.fields[i2].name);
        }
    }

    public FieldDeserializer getFieldDeserializer(String key) {
        return getFieldDeserializer(key, (int[]) null);
    }

    public FieldDeserializer getFieldDeserializer(String key, int[] setFlags) {
        FieldDeserializer fieldDeserializer;
        if (key == null) {
            return null;
        }
        Map<String, FieldDeserializer> map = this.fieldDeserializerMap;
        if (map != null && (fieldDeserializer = map.get(key)) != null) {
            return fieldDeserializer;
        }
        int low = 0;
        int high = this.sortedFieldDeserializers.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = this.sortedFieldDeserializers[mid].fieldInfo.name.compareTo(key);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else if (isSetFlag(mid, setFlags)) {
                return null;
            } else {
                return this.sortedFieldDeserializers[mid];
            }
        }
        Map<String, FieldDeserializer> map2 = this.alterNameFieldDeserializers;
        if (map2 != null) {
            return map2.get(key);
        }
        return null;
    }

    public FieldDeserializer getFieldDeserializer(long hash) {
        if (this.hashArray == null) {
            long[] hashArray2 = new long[this.sortedFieldDeserializers.length];
            int i = 0;
            while (true) {
                FieldDeserializer[] fieldDeserializerArr = this.sortedFieldDeserializers;
                if (i >= fieldDeserializerArr.length) {
                    break;
                }
                hashArray2[i] = TypeUtils.fnv1a_64(fieldDeserializerArr[i].fieldInfo.name);
                i++;
            }
            Arrays.sort(hashArray2);
            this.hashArray = hashArray2;
        }
        int pos = Arrays.binarySearch(this.hashArray, hash);
        if (pos < 0) {
            return null;
        }
        if (this.hashArrayMapping == null) {
            short[] mapping = new short[this.hashArray.length];
            Arrays.fill(mapping, -1);
            int i2 = 0;
            while (true) {
                FieldDeserializer[] fieldDeserializerArr2 = this.sortedFieldDeserializers;
                if (i2 >= fieldDeserializerArr2.length) {
                    break;
                }
                int p = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(fieldDeserializerArr2[i2].fieldInfo.name));
                if (p >= 0) {
                    mapping[p] = (short) i2;
                }
                i2++;
            }
            this.hashArrayMapping = mapping;
        }
        short setterIndex = this.hashArrayMapping[pos];
        if (setterIndex != -1) {
            return this.sortedFieldDeserializers[setterIndex];
        }
        return null;
    }

    static boolean isSetFlag(int i, int[] setFlags) {
        if (setFlags == null) {
            return false;
        }
        int flagIndex = i / 32;
        int bitIndex = i % 32;
        if (flagIndex >= setFlags.length || (setFlags[flagIndex] & (1 << bitIndex)) == 0) {
            return false;
        }
        return true;
    }

    public Object createInstance(DefaultJSONParser parser, Type type) {
        Object object;
        DefaultJSONParser defaultJSONParser = parser;
        Type type2 = type;
        int i = 0;
        if (!(type2 instanceof Class) || !this.clazz.isInterface()) {
            JavaBeanInfo javaBeanInfo = this.beanInfo;
            Constructor<?> constructor = javaBeanInfo.defaultConstructor;
            if (constructor == null && javaBeanInfo.factoryMethod == null) {
                return null;
            }
            Method method = javaBeanInfo.factoryMethod;
            if (method != null && javaBeanInfo.defaultConstructorParameterSize > 0) {
                return null;
            }
            try {
                if (javaBeanInfo.defaultConstructorParameterSize != 0) {
                    ParseContext context = parser.getContext();
                    if (context != null) {
                        if (context.object != null) {
                            if (type2 instanceof Class) {
                                String typeName = ((Class) type2).getName();
                                String parentClassName = typeName.substring(0, typeName.lastIndexOf(36));
                                Object ctxObj = context.object;
                                String parentName = ctxObj.getClass().getName();
                                Object param = null;
                                if (!parentName.equals(parentClassName)) {
                                    ParseContext parentContext = context.parent;
                                    if (parentContext == null || parentContext.object == null || (!"java.util.ArrayList".equals(parentName) && !"java.util.List".equals(parentName) && !"java.util.Collection".equals(parentName) && !"java.util.Map".equals(parentName) && !"java.util.HashMap".equals(parentName))) {
                                        param = ctxObj;
                                    } else {
                                        String parentName2 = parentContext.object.getClass().getName();
                                        if (parentName2.equals(parentClassName)) {
                                            param = parentContext.object;
                                            String str = parentName2;
                                        } else {
                                            String str2 = parentName2;
                                        }
                                    }
                                } else {
                                    param = ctxObj;
                                }
                                if (param == null || ((param instanceof Collection) && ((Collection) param).isEmpty())) {
                                    throw new JSONException("can't create non-static inner class instance.");
                                }
                                i = 0;
                                object = constructor.newInstance(new Object[]{param});
                            } else {
                                throw new JSONException("can't create non-static inner class instance.");
                            }
                        }
                    }
                    throw new JSONException("can't create non-static inner class instance.");
                } else if (constructor != null) {
                    object = constructor.newInstance(new Object[0]);
                } else {
                    object = method.invoke((Object) null, new Object[0]);
                }
                if (defaultJSONParser != null && defaultJSONParser.lexer.isEnabled(Feature.InitStringFieldAsEmpty)) {
                    FieldInfo[] fieldInfoArr = this.beanInfo.fields;
                    int length = fieldInfoArr.length;
                    while (i < length) {
                        FieldInfo fieldInfo = fieldInfoArr[i];
                        if (fieldInfo.fieldClass == String.class) {
                            try {
                                fieldInfo.set(object, "");
                            } catch (Exception e) {
                                throw new JSONException("create instance error, class " + this.clazz.getName(), e);
                            }
                        }
                        i++;
                    }
                }
                return object;
            } catch (JSONException e2) {
                throw e2;
            } catch (Exception e3) {
                throw new JSONException("create instance error, class " + this.clazz.getName(), e3);
            }
        } else {
            return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{(Class) type2}, new JSONObject());
        }
    }

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        return deserialze(parser, type, fieldName, 0);
    }

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName, int features) {
        return deserialze(parser, type, fieldName, (Object) null, features, (int[]) null);
    }

    public <T> T deserialzeArrayMapping(DefaultJSONParser parser, Type type, Object fieldName, Object object) {
        Object value;
        DefaultJSONParser defaultJSONParser = parser;
        JSONLexer lexer = defaultJSONParser.lexer;
        if (lexer.token() == 14) {
            String scanTypeName = lexer.scanTypeName(defaultJSONParser.symbolTable);
            String typeName = scanTypeName;
            if (scanTypeName != null) {
                ObjectDeserializer deserializer = getSeeAlso(parser.getConfig(), this.beanInfo, typeName);
                if (deserializer == null) {
                    deserializer = parser.getConfig().getDeserializer((Type) parser.getConfig().checkAutoType(typeName, TypeUtils.getClass(type), lexer.getFeatures()));
                }
                if (deserializer instanceof JavaBeanDeserializer) {
                    return ((JavaBeanDeserializer) deserializer).deserialzeArrayMapping(defaultJSONParser, type, fieldName, object);
                }
                Type type2 = type;
                Object obj = fieldName;
                Object obj2 = object;
            } else {
                Type type3 = type;
                Object obj3 = fieldName;
                Object obj4 = object;
            }
            Object object2 = createInstance(parser, type);
            int i = 0;
            int size = this.sortedFieldDeserializers.length;
            while (true) {
                int i2 = 16;
                if (i >= size) {
                    break;
                }
                char seperator = i == size + -1 ? ']' : StringUtil.COMMA;
                FieldDeserializer fieldDeser = this.sortedFieldDeserializers[i];
                Class<?> fieldClass = fieldDeser.fieldInfo.fieldClass;
                if (fieldClass == Integer.TYPE) {
                    fieldDeser.setValue(object2, lexer.scanInt(seperator));
                } else if (fieldClass == String.class) {
                    fieldDeser.setValue(object2, lexer.scanString(seperator));
                } else if (fieldClass == Long.TYPE) {
                    fieldDeser.setValue(object2, lexer.scanLong(seperator));
                } else if (fieldClass.isEnum()) {
                    char ch = lexer.getCurrent();
                    if (ch == '\"' || ch == 'n') {
                        value = lexer.scanEnum(fieldClass, parser.getSymbolTable(), seperator);
                    } else if (ch < '0' || ch > '9') {
                        value = scanEnum(lexer, seperator);
                    } else {
                        value = ((EnumDeserializer) ((DefaultFieldDeserializer) fieldDeser).getFieldValueDeserilizer(parser.getConfig())).valueOf(lexer.scanInt(seperator));
                    }
                    fieldDeser.setValue(object2, value);
                } else if (fieldClass == Boolean.TYPE) {
                    fieldDeser.setValue(object2, lexer.scanBoolean(seperator));
                } else if (fieldClass == Float.TYPE) {
                    fieldDeser.setValue(object2, (Object) Float.valueOf(lexer.scanFloat(seperator)));
                } else if (fieldClass == Double.TYPE) {
                    fieldDeser.setValue(object2, (Object) Double.valueOf(lexer.scanDouble(seperator)));
                } else if (fieldClass == Date.class && lexer.getCurrent() == '1') {
                    fieldDeser.setValue(object2, (Object) new Date(lexer.scanLong(seperator)));
                } else if (fieldClass == BigDecimal.class) {
                    fieldDeser.setValue(object2, (Object) lexer.scanDecimal(seperator));
                } else {
                    lexer.nextToken(14);
                    FieldInfo fieldInfo = fieldDeser.fieldInfo;
                    fieldDeser.setValue(object2, defaultJSONParser.parseObject(fieldInfo.fieldType, (Object) fieldInfo.name));
                    if (lexer.token() == 15) {
                        break;
                    }
                    if (seperator == ']') {
                        i2 = 15;
                    }
                    check(lexer, i2);
                }
                i++;
                Type type4 = type;
                Object obj5 = fieldName;
            }
            lexer.nextToken(16);
            return object2;
        }
        Object obj6 = object;
        throw new JSONException("error");
    }

    /* access modifiers changed from: protected */
    public void check(JSONLexer lexer, int token) {
        if (lexer.token() != token) {
            throw new JSONException("syntax error");
        }
    }

    /* access modifiers changed from: protected */
    public Enum<?> scanEnum(JSONLexer lexer, char seperator) {
        throw new JSONException("illegal enum. " + lexer.info());
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x016a, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x0174, code lost:
        throw new com.alibaba.fastjson.JSONException(r0.getMessage(), r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0057, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0058, code lost:
        r34 = r48;
        r10 = r5;
        r36 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:362:?, code lost:
        r14.nextToken(16);
        r40 = r4;
        r10 = r5;
        r3 = r6;
        r1 = r24;
        r18 = r35;
        r46 = 0;
        r5 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:363:0x05f6, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:364:0x05f7, code lost:
        r10 = r5;
        r3 = r6;
        r1 = r35;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:378:?, code lost:
        r14.nextTokenWithColon(4);
        r2 = r14.token();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:379:0x0634, code lost:
        if (r2 != 4) goto L_0x071d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:380:0x0636, code lost:
        r13 = r14.stringVal();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:381:0x0640, code lost:
        if ("@".equals(r13) == false) goto L_0x064c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:382:0x0642, code lost:
        r15 = r5.object;
        r37 = r3;
        r26 = r7;
        r38 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:384:0x0652, code lost:
        if ("..".equals(r13) == false) goto L_0x0672;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:385:0x0654, code lost:
        r15 = r5.parent;
        r26 = r7;
        r7 = r15.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:386:0x065a, code lost:
        if (r7 == null) goto L_0x065f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:387:0x065c, code lost:
        r35 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:388:0x065f, code lost:
        r9.addResolveTask(new com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask(r15, r13));
        r9.resolveStatus = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:389:0x066a, code lost:
        r37 = r3;
        r38 = r12;
        r15 = r35;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:390:0x0672, code lost:
        r26 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:391:0x067a, code lost:
        if ("$".equals(r13) == false) goto L_0x069b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:392:0x067c, code lost:
        r7 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:393:0x067d, code lost:
        r15 = r7.parent;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:394:0x067f, code lost:
        if (r15 == null) goto L_0x0683;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:395:0x0681, code lost:
        r7 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:396:0x0683, code lost:
        r15 = r7.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:397:0x0685, code lost:
        if (r15 == null) goto L_0x0689;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:399:0x0689, code lost:
        r9.addResolveTask(new com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask(r7, r13));
        r9.resolveStatus = 1;
        r15 = r35;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:400:0x0696, code lost:
        r37 = r3;
        r38 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:402:0x06a1, code lost:
        if (r13.indexOf(92) <= 0) goto L_0x06d8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:403:0x06a3, code lost:
        r15 = new java.lang.StringBuilder();
        r7 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:404:0x06ac, code lost:
        r37 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:405:0x06b2, code lost:
        if (r7 >= r13.length()) goto L_0x06d0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:406:0x06b4, code lost:
        r3 = r13.charAt(r7);
        r38 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:407:0x06bc, code lost:
        if (r3 != '\\') goto L_0x06c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:408:0x06be, code lost:
        r7 = r7 + 1;
        r3 = r13.charAt(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:409:0x06c6, code lost:
        r15.append(r3);
        r7 = r7 + 1;
        r3 = r37;
        r12 = r38;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:410:0x06d0, code lost:
        r38 = r12;
        r13 = r15.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:411:0x06d8, code lost:
        r37 = r3;
        r38 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:412:0x06dc, code lost:
        r3 = r9.resolveReference(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:413:0x06e0, code lost:
        if (r3 == null) goto L_0x06e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:414:0x06e2, code lost:
        r15 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:415:0x06e5, code lost:
        r9.addResolveTask(new com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask(r5, r13));
        r9.resolveStatus = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:416:0x06f0, code lost:
        r15 = r35;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:419:?, code lost:
        r14.nextToken(13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:420:0x06fc, code lost:
        if (r14.token() != 13) goto L_0x070f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:421:0x06fe, code lost:
        r14.nextToken(16);
        r9.setContext(r5, r15, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:422:0x0707, code lost:
        if (r6 == null) goto L_0x070b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:423:0x0709, code lost:
        r6.object = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:424:0x070b, code lost:
        r9.setContext(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:425:0x070e, code lost:
        return r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:428:0x0716, code lost:
        throw new com.alibaba.fastjson.JSONException("illegal ref");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:429:0x0717, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:430:0x0718, code lost:
        r10 = r5;
        r3 = r6;
        r1 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:431:0x071d, code lost:
        r37 = r3;
        r26 = r7;
        r38 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:434:0x073d, code lost:
        throw new com.alibaba.fastjson.JSONException("illegal ref, " + com.alibaba.fastjson.parser.JSONToken.name(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:450:0x0781, code lost:
        r12 = r36;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:452:?, code lost:
        r7 = getSeeAlso(r12, r8.beanInfo, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:453:0x0787, code lost:
        r13 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:454:0x0788, code lost:
        if (r7 != null) goto L_0x07c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:456:?, code lost:
        r15 = com.alibaba.fastjson.util.TypeUtils.getClass(r44);
        r17 = r1;
        r1 = r8.autoTypeCheckHandler;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:457:0x0792, code lost:
        if (r1 == null) goto L_0x07a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:458:0x0794, code lost:
        r46 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:460:?, code lost:
        r13 = r1.handler(r3, r15, r14.getFeatures());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:461:0x07a0, code lost:
        r46 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:462:0x07a2, code lost:
        if (r13 != null) goto L_0x07ad;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:463:0x07a4, code lost:
        r13 = r12.checkAutoType(r3, r15, r14.getFeatures());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:465:0x07b5, code lost:
        r7 = r43.getConfig().getDeserializer(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:466:0x07b7, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:467:0x07b8, code lost:
        r46 = r2;
        r10 = r5;
        r3 = r6;
        r36 = r12;
        r1 = r35;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:468:0x07c2, code lost:
        r17 = r1;
        r46 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:470:?, code lost:
        r1 = r7.deserialze(r9, r13, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:471:0x07cc, code lost:
        if ((r7 instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) == false) goto L_0x07e9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:474:?, code lost:
        r2 = (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:475:0x07d1, code lost:
        if (r4 == null) goto L_0x07e9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:476:0x07d3, code lost:
        r15 = r2.getFieldDeserializer(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:477:0x07d7, code lost:
        if (r15 == null) goto L_0x07e9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:478:0x07d9, code lost:
        r15.setValue(r1, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:479:0x07dd, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:480:0x07de, code lost:
        r2 = r46;
        r10 = r5;
        r3 = r6;
        r36 = r12;
        r1 = r35;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:481:0x07e9, code lost:
        if (r6 == null) goto L_0x07f0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:482:0x07eb, code lost:
        r6.object = r35;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:483:0x07f0, code lost:
        r2 = r35;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:484:0x07f2, code lost:
        r9.setContext(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:485:0x07f5, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:486:0x07f6, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:487:0x07f7, code lost:
        r1 = r35;
        r10 = r5;
        r3 = r6;
        r36 = r12;
        r2 = r46;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:488:0x0802, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:489:0x0803, code lost:
        r1 = r35;
        r10 = r5;
        r3 = r6;
        r36 = r12;
        r2 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:597:0x0a34, code lost:
        r2 = r15;
        r3 = r17;
        r1 = r37;
        r5 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:810:0x0d4c, code lost:
        throw new com.alibaba.fastjson.JSONException("syntax error, unexpect token " + com.alibaba.fastjson.parser.JSONToken.name(r14.token()));
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:15:0x0043, B:101:0x0131] */
    /* JADX WARNING: Removed duplicated region for block: B:301:0x04e2 A[Catch:{ all -> 0x061a }] */
    /* JADX WARNING: Removed duplicated region for block: B:302:0x04e7 A[Catch:{ all -> 0x061a }] */
    /* JADX WARNING: Removed duplicated region for block: B:353:0x05d4 A[SYNTHETIC, Splitter:B:353:0x05d4] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x007e A[Catch:{ Exception -> 0x016a, all -> 0x0057 }] */
    /* JADX WARNING: Removed duplicated region for block: B:505:0x0888  */
    /* JADX WARNING: Removed duplicated region for block: B:507:0x0898 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:528:0x08e7  */
    /* JADX WARNING: Removed duplicated region for block: B:574:0x09ae A[Catch:{ all -> 0x0923, all -> 0x09c4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:593:0x0a20 A[Catch:{ all -> 0x0d56 }] */
    /* JADX WARNING: Removed duplicated region for block: B:594:0x0a28 A[Catch:{ all -> 0x0d56 }] */
    /* JADX WARNING: Removed duplicated region for block: B:750:0x0c39 A[SYNTHETIC, Splitter:B:750:0x0c39] */
    /* JADX WARNING: Removed duplicated region for block: B:820:0x0d70  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r43, java.lang.reflect.Type r44, java.lang.Object r45, java.lang.Object r46, int r47, int[] r48) {
        /*
            r42 = this;
            r8 = r42
            r9 = r43
            r10 = r44
            r11 = r45
            r1 = r46
            java.lang.Class<java.lang.Integer> r12 = java.lang.Integer.class
            java.lang.Class<java.lang.String> r13 = java.lang.String.class
            java.lang.Class<com.alibaba.fastjson.JSON> r0 = com.alibaba.fastjson.JSON.class
            if (r10 == r0) goto L_0x0d76
            java.lang.Class<com.alibaba.fastjson.JSONObject> r0 = com.alibaba.fastjson.JSONObject.class
            if (r10 != r0) goto L_0x0018
            goto L_0x0d76
        L_0x0018:
            com.alibaba.fastjson.parser.JSONLexer r0 = r9.lexer
            r14 = r0
            com.alibaba.fastjson.parser.JSONLexerBase r14 = (com.alibaba.fastjson.parser.JSONLexerBase) r14
            com.alibaba.fastjson.parser.ParserConfig r15 = r43.getConfig()
            int r2 = r14.token()
            r0 = 8
            r7 = 0
            r6 = 16
            if (r2 != r0) goto L_0x0030
            r14.nextToken(r6)
            return r7
        L_0x0030:
            com.alibaba.fastjson.parser.ParseContext r0 = r43.getContext()
            if (r1 == 0) goto L_0x003c
            if (r0 == 0) goto L_0x003c
            com.alibaba.fastjson.parser.ParseContext r0 = r0.parent
            r5 = r0
            goto L_0x003d
        L_0x003c:
            r5 = r0
        L_0x003d:
            r3 = 0
            r4 = 0
            r0 = 13
            if (r2 != r0) goto L_0x005f
            r14.nextToken(r6)     // Catch:{ all -> 0x0057 }
            if (r1 != 0) goto L_0x004d
            java.lang.Object r0 = r42.createInstance((com.alibaba.fastjson.parser.DefaultJSONParser) r43, (java.lang.reflect.Type) r44)     // Catch:{ all -> 0x0057 }
            goto L_0x004e
        L_0x004d:
            r0 = r1
        L_0x004e:
            if (r3 == 0) goto L_0x0053
            r3.object = r0
        L_0x0053:
            r9.setContext(r5)
            return r0
        L_0x0057:
            r0 = move-exception
            r34 = r48
            r10 = r5
            r36 = r15
            goto L_0x0d6e
        L_0x005f:
            r7 = 14
            if (r2 != r7) goto L_0x008a
            com.alibaba.fastjson.parser.Feature r0 = com.alibaba.fastjson.parser.Feature.SupportArrayToBean     // Catch:{ all -> 0x0057 }
            int r6 = r0.mask     // Catch:{ all -> 0x0057 }
            com.alibaba.fastjson.util.JavaBeanInfo r7 = r8.beanInfo     // Catch:{ all -> 0x0057 }
            int r7 = r7.parserFeatures     // Catch:{ all -> 0x0057 }
            r7 = r7 & r6
            if (r7 != 0) goto L_0x007b
            boolean r0 = r14.isEnabled((com.alibaba.fastjson.parser.Feature) r0)     // Catch:{ all -> 0x0057 }
            if (r0 != 0) goto L_0x007b
            r0 = r47 & r6
            if (r0 == 0) goto L_0x0079
            goto L_0x007b
        L_0x0079:
            r0 = 0
            goto L_0x007c
        L_0x007b:
            r0 = 1
        L_0x007c:
            if (r0 == 0) goto L_0x008a
            java.lang.Object r7 = r42.deserialzeArrayMapping(r43, r44, r45, r46)     // Catch:{ all -> 0x0057 }
            if (r3 == 0) goto L_0x0086
            r3.object = r1
        L_0x0086:
            r9.setContext(r5)
            return r7
        L_0x008a:
            r0 = 12
            r7 = 4
            if (r2 == r0) goto L_0x01b3
            r0 = 16
            if (r2 == r0) goto L_0x01b3
            boolean r0 = r14.isBlankInput()     // Catch:{ all -> 0x0057 }
            if (r0 == 0) goto L_0x00a3
            if (r3 == 0) goto L_0x009e
            r3.object = r1
        L_0x009e:
            r9.setContext(r5)
            r6 = 0
            return r6
        L_0x00a3:
            if (r2 != r7) goto L_0x0106
            java.lang.String r0 = r14.stringVal()     // Catch:{ all -> 0x0057 }
            r17 = r0
            int r0 = r17.length()     // Catch:{ all -> 0x0057 }
            if (r0 != 0) goto L_0x00be
            r14.nextToken()     // Catch:{ all -> 0x0057 }
            if (r3 == 0) goto L_0x00b9
            r3.object = r1
        L_0x00b9:
            r9.setContext(r5)
            r6 = 0
            return r6
        L_0x00be:
            com.alibaba.fastjson.util.JavaBeanInfo r0 = r8.beanInfo     // Catch:{ all -> 0x0057 }
            com.alibaba.fastjson.annotation.JSONType r0 = r0.jsonType     // Catch:{ all -> 0x0057 }
            if (r0 == 0) goto L_0x0101
            java.lang.Class[] r7 = r0.seeAlso()     // Catch:{ all -> 0x0057 }
            int r6 = r7.length     // Catch:{ all -> 0x0057 }
            r24 = r4
            r4 = 0
        L_0x00cc:
            if (r4 >= r6) goto L_0x00fe
            r0 = r7[r4]     // Catch:{ all -> 0x0057 }
            r19 = r0
            java.lang.Class<java.lang.Enum> r0 = java.lang.Enum.class
            r25 = r6
            r6 = r19
            boolean r0 = r0.isAssignableFrom(r6)     // Catch:{ all -> 0x0057 }
            if (r0 == 0) goto L_0x00f1
            r19 = r7
            r7 = r17
            java.lang.Enum r0 = java.lang.Enum.valueOf(r6, r7)     // Catch:{ IllegalArgumentException -> 0x00ef }
            if (r3 == 0) goto L_0x00eb
            r3.object = r1
        L_0x00eb:
            r9.setContext(r5)
            return r0
        L_0x00ef:
            r0 = move-exception
            goto L_0x00f5
        L_0x00f1:
            r19 = r7
            r7 = r17
        L_0x00f5:
            int r4 = r4 + 1
            r17 = r7
            r7 = r19
            r6 = r25
            goto L_0x00cc
        L_0x00fe:
            r7 = r17
            goto L_0x0108
        L_0x0101:
            r24 = r4
            r7 = r17
            goto L_0x0108
        L_0x0106:
            r24 = r4
        L_0x0108:
            r4 = 14
            if (r2 != r4) goto L_0x0124
            char r0 = r14.getCurrent()     // Catch:{ all -> 0x0057 }
            r4 = 93
            if (r0 != r4) goto L_0x0124
            r14.next()     // Catch:{ all -> 0x0057 }
            r14.nextToken()     // Catch:{ all -> 0x0057 }
            if (r3 == 0) goto L_0x011f
            r3.object = r1
        L_0x011f:
            r9.setContext(r5)
            r7 = 0
            return r7
        L_0x0124:
            com.alibaba.fastjson.util.JavaBeanInfo r0 = r8.beanInfo     // Catch:{ all -> 0x0057 }
            java.lang.reflect.Method r4 = r0.factoryMethod     // Catch:{ all -> 0x0057 }
            if (r4 == 0) goto L_0x0175
            com.alibaba.fastjson.util.FieldInfo[] r0 = r0.fields     // Catch:{ all -> 0x0057 }
            int r4 = r0.length     // Catch:{ all -> 0x0057 }
            r6 = 1
            if (r4 != r6) goto L_0x0175
            r4 = 0
            r0 = r0[r4]     // Catch:{ Exception -> 0x016a }
            java.lang.Class<?> r4 = r0.fieldClass     // Catch:{ Exception -> 0x016a }
            if (r4 != r12) goto L_0x0151
            r4 = 2
            if (r2 != r4) goto L_0x0169
            int r4 = r14.intValue()     // Catch:{ Exception -> 0x016a }
            r14.nextToken()     // Catch:{ Exception -> 0x016a }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x016a }
            java.lang.Object r6 = r8.createFactoryInstance(r15, r6)     // Catch:{ Exception -> 0x016a }
            if (r3 == 0) goto L_0x014d
            r3.object = r1
        L_0x014d:
            r9.setContext(r5)
            return r6
        L_0x0151:
            if (r4 != r13) goto L_0x0169
            r4 = 4
            if (r2 != r4) goto L_0x0169
            java.lang.String r4 = r14.stringVal()     // Catch:{ Exception -> 0x016a }
            r14.nextToken()     // Catch:{ Exception -> 0x016a }
            java.lang.Object r6 = r8.createFactoryInstance(r15, r4)     // Catch:{ Exception -> 0x016a }
            if (r3 == 0) goto L_0x0165
            r3.object = r1
        L_0x0165:
            r9.setContext(r5)
            return r6
        L_0x0169:
            goto L_0x0175
        L_0x016a:
            r0 = move-exception
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0057 }
            java.lang.String r6 = r0.getMessage()     // Catch:{ all -> 0x0057 }
            r4.<init>(r6, r0)     // Catch:{ all -> 0x0057 }
            throw r4     // Catch:{ all -> 0x0057 }
        L_0x0175:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0057 }
            r0.<init>()     // Catch:{ all -> 0x0057 }
            java.lang.String r4 = "syntax error, expect {, actual "
            r0.append(r4)     // Catch:{ all -> 0x0057 }
            java.lang.String r4 = r14.tokenName()     // Catch:{ all -> 0x0057 }
            r0.append(r4)     // Catch:{ all -> 0x0057 }
            java.lang.String r4 = ", pos "
            r0.append(r4)     // Catch:{ all -> 0x0057 }
            int r4 = r14.pos()     // Catch:{ all -> 0x0057 }
            r0.append(r4)     // Catch:{ all -> 0x0057 }
            boolean r4 = r11 instanceof java.lang.String     // Catch:{ all -> 0x0057 }
            if (r4 == 0) goto L_0x019f
            java.lang.String r4 = ", fieldName "
            r0.append(r4)     // Catch:{ all -> 0x0057 }
            r0.append(r11)     // Catch:{ all -> 0x0057 }
        L_0x019f:
            java.lang.String r4 = ", fastjson-version "
            r0.append(r4)     // Catch:{ all -> 0x0057 }
            java.lang.String r4 = "1.2.75"
            r0.append(r4)     // Catch:{ all -> 0x0057 }
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0057 }
            java.lang.String r6 = r0.toString()     // Catch:{ all -> 0x0057 }
            r4.<init>(r6)     // Catch:{ all -> 0x0057 }
            throw r4     // Catch:{ all -> 0x0057 }
        L_0x01b3:
            r24 = r4
            r7 = 0
            int r0 = r9.resolveStatus     // Catch:{ all -> 0x0d68 }
            r4 = 2
            if (r0 != r4) goto L_0x01bf
            r6 = 0
            r9.resolveStatus = r6     // Catch:{ all -> 0x0057 }
            goto L_0x01c0
        L_0x01bf:
            r6 = 0
        L_0x01c0:
            com.alibaba.fastjson.util.JavaBeanInfo r0 = r8.beanInfo     // Catch:{ all -> 0x0d68 }
            java.lang.String r0 = r0.typeKey     // Catch:{ all -> 0x0d68 }
            r4 = r0
            r0 = 0
            r16 = 0
            r6 = r3
            r7 = r16
            r3 = r2
            r2 = r48
        L_0x01ce:
            r21 = 0
            r23 = 0
            r25 = 0
            r26 = 0
            r27 = 0
            r28 = 0
            r46 = r3
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer[] r3 = r8.sortedFieldDeserializers     // Catch:{ all -> 0x0d5d }
            int r10 = r3.length     // Catch:{ all -> 0x0d5d }
            if (r0 >= r10) goto L_0x0215
            r10 = 16
            if (r7 >= r10) goto L_0x0215
            r3 = r3[r0]     // Catch:{ all -> 0x020a }
            com.alibaba.fastjson.util.FieldInfo r10 = r3.fieldInfo     // Catch:{ all -> 0x020a }
            r48 = r0
            java.lang.Class<?> r0 = r10.fieldClass     // Catch:{ all -> 0x020a }
            r26 = r0
            com.alibaba.fastjson.annotation.JSONField r0 = r10.getAnnotation()     // Catch:{ all -> 0x020a }
            r27 = r0
            if (r27 == 0) goto L_0x0206
            boolean r0 = r3 instanceof com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer     // Catch:{ all -> 0x020a }
            if (r0 == 0) goto L_0x0206
            r0 = r3
            com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer r0 = (com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer) r0     // Catch:{ all -> 0x020a }
            boolean r0 = r0.customDeserilizer     // Catch:{ all -> 0x020a }
            r28 = r0
            r0 = r3
            r3 = r26
            goto L_0x021d
        L_0x0206:
            r0 = r3
            r3 = r26
            goto L_0x021d
        L_0x020a:
            r0 = move-exception
            r34 = r2
            r10 = r5
            r3 = r6
            r36 = r15
            r2 = r46
            goto L_0x0d6e
        L_0x0215:
            r48 = r0
            r0 = r23
            r10 = r25
            r3 = r26
        L_0x021d:
            r23 = 0
            r25 = 0
            r26 = 0
            r29 = 0
            r31 = 0
            r32 = 0
            if (r0 == 0) goto L_0x05c8
            r34 = r2
            char[] r2 = r10.name_chars     // Catch:{ all -> 0x05bd }
            if (r28 == 0) goto L_0x024c
            boolean r35 = r14.matchField((char[]) r2)     // Catch:{ all -> 0x0243 }
            if (r35 == 0) goto L_0x024c
            r23 = 1
            r35 = r1
            r39 = r12
            r36 = r15
            r12 = r26
            goto L_0x05d2
        L_0x0243:
            r0 = move-exception
            r2 = r46
            r10 = r5
            r3 = r6
            r36 = r15
            goto L_0x0d6e
        L_0x024c:
            r35 = r1
            java.lang.Class r1 = java.lang.Integer.TYPE     // Catch:{ all -> 0x05b2 }
            r36 = r15
            r15 = -2
            if (r3 == r1) goto L_0x0578
            if (r3 != r12) goto L_0x025b
            r39 = r12
            goto L_0x057a
        L_0x025b:
            java.lang.Class r1 = java.lang.Long.TYPE     // Catch:{ all -> 0x061a }
            if (r3 == r1) goto L_0x053b
            java.lang.Class<java.lang.Long> r1 = java.lang.Long.class
            if (r3 != r1) goto L_0x0267
            r39 = r12
            goto L_0x053d
        L_0x0267:
            if (r3 != r13) goto L_0x029b
            java.lang.String r1 = r14.scanFieldString(r2)     // Catch:{ all -> 0x061a }
            r26 = r1
            int r1 = r14.matchStat     // Catch:{ all -> 0x061a }
            if (r1 <= 0) goto L_0x027d
            r23 = 1
            r25 = 1
            r39 = r12
            r12 = r26
            goto L_0x05d2
        L_0x027d:
            if (r1 != r15) goto L_0x0295
            int r7 = r7 + 1
            r15 = r46
            r40 = r4
            r10 = r5
            r39 = r12
            r1 = r35
            r0 = 16
            r2 = 13
            r3 = 1
            r4 = 0
            r5 = 0
            r26 = 4
            goto L_0x0d1a
        L_0x0295:
            r39 = r12
            r12 = r26
            goto L_0x05d2
        L_0x029b:
            java.lang.Class<java.util.Date> r1 = java.util.Date.class
            if (r3 != r1) goto L_0x02d5
            java.lang.String r1 = r10.format     // Catch:{ all -> 0x061a }
            if (r1 != 0) goto L_0x02d5
            java.util.Date r1 = r14.scanFieldDate(r2)     // Catch:{ all -> 0x061a }
            r26 = r1
            int r1 = r14.matchStat     // Catch:{ all -> 0x061a }
            if (r1 <= 0) goto L_0x02b7
            r23 = 1
            r25 = 1
            r39 = r12
            r12 = r26
            goto L_0x05d2
        L_0x02b7:
            if (r1 != r15) goto L_0x02cf
            int r7 = r7 + 1
            r15 = r46
            r40 = r4
            r10 = r5
            r39 = r12
            r1 = r35
            r0 = 16
            r2 = 13
            r3 = 1
            r4 = 0
            r5 = 0
            r26 = 4
            goto L_0x0d1a
        L_0x02cf:
            r39 = r12
            r12 = r26
            goto L_0x05d2
        L_0x02d5:
            java.lang.Class<java.math.BigDecimal> r1 = java.math.BigDecimal.class
            if (r3 != r1) goto L_0x030b
            java.math.BigDecimal r1 = r14.scanFieldDecimal(r2)     // Catch:{ all -> 0x061a }
            r26 = r1
            int r1 = r14.matchStat     // Catch:{ all -> 0x061a }
            if (r1 <= 0) goto L_0x02ed
            r23 = 1
            r25 = 1
            r39 = r12
            r12 = r26
            goto L_0x05d2
        L_0x02ed:
            if (r1 != r15) goto L_0x0305
            int r7 = r7 + 1
            r15 = r46
            r40 = r4
            r10 = r5
            r39 = r12
            r1 = r35
            r0 = 16
            r2 = 13
            r3 = 1
            r4 = 0
            r5 = 0
            r26 = 4
            goto L_0x0d1a
        L_0x0305:
            r39 = r12
            r12 = r26
            goto L_0x05d2
        L_0x030b:
            java.lang.Class<java.math.BigInteger> r1 = java.math.BigInteger.class
            if (r3 != r1) goto L_0x0341
            java.math.BigInteger r1 = r14.scanFieldBigInteger(r2)     // Catch:{ all -> 0x061a }
            r26 = r1
            int r1 = r14.matchStat     // Catch:{ all -> 0x061a }
            if (r1 <= 0) goto L_0x0323
            r23 = 1
            r25 = 1
            r39 = r12
            r12 = r26
            goto L_0x05d2
        L_0x0323:
            if (r1 != r15) goto L_0x033b
            int r7 = r7 + 1
            r15 = r46
            r40 = r4
            r10 = r5
            r39 = r12
            r1 = r35
            r0 = 16
            r2 = 13
            r3 = 1
            r4 = 0
            r5 = 0
            r26 = 4
            goto L_0x0d1a
        L_0x033b:
            r39 = r12
            r12 = r26
            goto L_0x05d2
        L_0x0341:
            java.lang.Class r1 = java.lang.Boolean.TYPE     // Catch:{ all -> 0x061a }
            if (r3 == r1) goto L_0x0502
            java.lang.Class<java.lang.Boolean> r1 = java.lang.Boolean.class
            if (r3 != r1) goto L_0x034d
            r39 = r12
            goto L_0x0504
        L_0x034d:
            java.lang.Class r1 = java.lang.Float.TYPE     // Catch:{ all -> 0x061a }
            if (r3 == r1) goto L_0x04c3
            java.lang.Class<java.lang.Float> r1 = java.lang.Float.class
            if (r3 != r1) goto L_0x0357
            goto L_0x04c3
        L_0x0357:
            java.lang.Class r1 = java.lang.Double.TYPE     // Catch:{ all -> 0x061a }
            if (r3 == r1) goto L_0x0484
            java.lang.Class<java.lang.Double> r1 = java.lang.Double.class
            if (r3 != r1) goto L_0x0361
            goto L_0x0484
        L_0x0361:
            boolean r1 = r3.isEnum()     // Catch:{ all -> 0x061a }
            if (r1 == 0) goto L_0x03bd
            com.alibaba.fastjson.parser.ParserConfig r1 = r43.getConfig()     // Catch:{ all -> 0x061a }
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r1 = r1.getDeserializer((java.lang.reflect.Type) r3)     // Catch:{ all -> 0x061a }
            boolean r1 = r1 instanceof com.alibaba.fastjson.parser.deserializer.EnumDeserializer     // Catch:{ all -> 0x061a }
            if (r1 == 0) goto L_0x03bd
            if (r27 == 0) goto L_0x037d
            java.lang.Class r1 = r27.deserializeUsing()     // Catch:{ all -> 0x061a }
            java.lang.Class<java.lang.Void> r15 = java.lang.Void.class
            if (r1 != r15) goto L_0x03bd
        L_0x037d:
            boolean r1 = r0 instanceof com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer     // Catch:{ all -> 0x061a }
            if (r1 == 0) goto L_0x03b9
            r1 = r0
            com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer r1 = (com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer) r1     // Catch:{ all -> 0x061a }
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r1 = r1.fieldValueDeserilizer     // Catch:{ all -> 0x061a }
            java.lang.Enum r15 = r8.scanEnum(r14, r2, r1)     // Catch:{ all -> 0x061a }
            r26 = r15
            int r15 = r14.matchStat     // Catch:{ all -> 0x061a }
            if (r15 <= 0) goto L_0x0398
            r15 = 1
            r23 = 1
            r25 = r23
            r23 = r15
            goto L_0x03b3
        L_0x0398:
            r37 = r1
            r1 = -2
            if (r15 != r1) goto L_0x03b3
            int r7 = r7 + 1
            r15 = r46
            r40 = r4
            r10 = r5
            r39 = r12
            r1 = r35
            r0 = 16
            r2 = 13
            r3 = 1
            r4 = 0
            r5 = 0
            r26 = 4
            goto L_0x0d1a
        L_0x03b3:
            r39 = r12
            r12 = r26
            goto L_0x05d2
        L_0x03b9:
            r39 = r12
            goto L_0x05d0
        L_0x03bd:
            java.lang.Class<int[]> r1 = int[].class
            if (r3 != r1) goto L_0x03f4
            int[] r1 = r14.scanFieldIntArray(r2)     // Catch:{ all -> 0x061a }
            r26 = r1
            int r1 = r14.matchStat     // Catch:{ all -> 0x061a }
            if (r1 <= 0) goto L_0x03d5
            r23 = 1
            r25 = 1
            r39 = r12
            r12 = r26
            goto L_0x05d2
        L_0x03d5:
            r15 = -2
            if (r1 != r15) goto L_0x03ee
            int r7 = r7 + 1
            r15 = r46
            r40 = r4
            r10 = r5
            r39 = r12
            r1 = r35
            r0 = 16
            r2 = 13
            r3 = 1
            r4 = 0
            r5 = 0
            r26 = 4
            goto L_0x0d1a
        L_0x03ee:
            r39 = r12
            r12 = r26
            goto L_0x05d2
        L_0x03f4:
            java.lang.Class<float[]> r1 = float[].class
            if (r3 != r1) goto L_0x042b
            float[] r1 = r14.scanFieldFloatArray(r2)     // Catch:{ all -> 0x061a }
            r26 = r1
            int r1 = r14.matchStat     // Catch:{ all -> 0x061a }
            if (r1 <= 0) goto L_0x040c
            r23 = 1
            r25 = 1
            r39 = r12
            r12 = r26
            goto L_0x05d2
        L_0x040c:
            r15 = -2
            if (r1 != r15) goto L_0x0425
            int r7 = r7 + 1
            r15 = r46
            r40 = r4
            r10 = r5
            r39 = r12
            r1 = r35
            r0 = 16
            r2 = 13
            r3 = 1
            r4 = 0
            r5 = 0
            r26 = 4
            goto L_0x0d1a
        L_0x0425:
            r39 = r12
            r12 = r26
            goto L_0x05d2
        L_0x042b:
            java.lang.Class<float[][]> r1 = float[][].class
            if (r3 != r1) goto L_0x0462
            float[][] r1 = r14.scanFieldFloatArray2(r2)     // Catch:{ all -> 0x061a }
            r26 = r1
            int r1 = r14.matchStat     // Catch:{ all -> 0x061a }
            if (r1 <= 0) goto L_0x0443
            r23 = 1
            r25 = 1
            r39 = r12
            r12 = r26
            goto L_0x05d2
        L_0x0443:
            r15 = -2
            if (r1 != r15) goto L_0x045c
            int r7 = r7 + 1
            r15 = r46
            r40 = r4
            r10 = r5
            r39 = r12
            r1 = r35
            r0 = 16
            r2 = 13
            r3 = 1
            r4 = 0
            r5 = 0
            r26 = 4
            goto L_0x0d1a
        L_0x045c:
            r39 = r12
            r12 = r26
            goto L_0x05d2
        L_0x0462:
            boolean r1 = r14.matchField((char[]) r2)     // Catch:{ all -> 0x061a }
            if (r1 == 0) goto L_0x0470
            r23 = 1
            r39 = r12
            r12 = r26
            goto L_0x05d2
        L_0x0470:
            r15 = r46
            r40 = r4
            r10 = r5
            r39 = r12
            r1 = r35
            r0 = 16
            r2 = 13
            r3 = 1
            r4 = 0
            r5 = 0
            r26 = 4
            goto L_0x0d1a
        L_0x0484:
            double r39 = r14.scanFieldDouble(r2)     // Catch:{ all -> 0x061a }
            int r1 = (r39 > r29 ? 1 : (r39 == r29 ? 0 : -1))
            if (r1 != 0) goto L_0x0495
            int r1 = r14.matchStat     // Catch:{ all -> 0x061a }
            r15 = 5
            if (r1 != r15) goto L_0x0495
            r1 = 0
            r26 = r1
            goto L_0x049b
        L_0x0495:
            java.lang.Double r1 = java.lang.Double.valueOf(r39)     // Catch:{ all -> 0x061a }
            r26 = r1
        L_0x049b:
            int r1 = r14.matchStat     // Catch:{ all -> 0x061a }
            if (r1 <= 0) goto L_0x04a4
            r23 = 1
            r25 = 1
            goto L_0x04bd
        L_0x04a4:
            r15 = -2
            if (r1 != r15) goto L_0x04bd
            int r7 = r7 + 1
            r15 = r46
            r40 = r4
            r10 = r5
            r39 = r12
            r1 = r35
            r0 = 16
            r2 = 13
            r3 = 1
            r4 = 0
            r5 = 0
            r26 = 4
            goto L_0x0d1a
        L_0x04bd:
            r39 = r12
            r12 = r26
            goto L_0x05d2
        L_0x04c3:
            float r1 = r14.scanFieldFloat(r2)     // Catch:{ all -> 0x061a }
            int r15 = (r1 > r31 ? 1 : (r1 == r31 ? 0 : -1))
            if (r15 != 0) goto L_0x04d6
            int r15 = r14.matchStat     // Catch:{ all -> 0x061a }
            r39 = r12
            r12 = 5
            if (r15 != r12) goto L_0x04d8
            r12 = 0
            r26 = r12
            goto L_0x04de
        L_0x04d6:
            r39 = r12
        L_0x04d8:
            java.lang.Float r12 = java.lang.Float.valueOf(r1)     // Catch:{ all -> 0x061a }
            r26 = r12
        L_0x04de:
            int r12 = r14.matchStat     // Catch:{ all -> 0x061a }
            if (r12 <= 0) goto L_0x04e7
            r23 = 1
            r25 = 1
            goto L_0x04fe
        L_0x04e7:
            r15 = -2
            if (r12 != r15) goto L_0x04fe
            int r7 = r7 + 1
            r15 = r46
            r40 = r4
            r10 = r5
            r1 = r35
            r0 = 16
            r2 = 13
            r3 = 1
            r4 = 0
            r5 = 0
            r26 = 4
            goto L_0x0d1a
        L_0x04fe:
            r12 = r26
            goto L_0x05d2
        L_0x0502:
            r39 = r12
        L_0x0504:
            boolean r1 = r14.scanFieldBoolean(r2)     // Catch:{ all -> 0x061a }
            int r12 = r14.matchStat     // Catch:{ all -> 0x061a }
            r15 = 5
            if (r12 != r15) goto L_0x0511
            r12 = 0
            r26 = r12
            goto L_0x0517
        L_0x0511:
            java.lang.Boolean r12 = java.lang.Boolean.valueOf(r1)     // Catch:{ all -> 0x061a }
            r26 = r12
        L_0x0517:
            int r12 = r14.matchStat     // Catch:{ all -> 0x061a }
            if (r12 <= 0) goto L_0x0520
            r23 = 1
            r25 = 1
            goto L_0x0537
        L_0x0520:
            r15 = -2
            if (r12 != r15) goto L_0x0537
            int r7 = r7 + 1
            r15 = r46
            r40 = r4
            r10 = r5
            r1 = r35
            r0 = 16
            r2 = 13
            r3 = 1
            r4 = 0
            r5 = 0
            r26 = 4
            goto L_0x0d1a
        L_0x0537:
            r12 = r26
            goto L_0x05d2
        L_0x053b:
            r39 = r12
        L_0x053d:
            long r40 = r14.scanFieldLong(r2)     // Catch:{ all -> 0x061a }
            int r1 = (r40 > r32 ? 1 : (r40 == r32 ? 0 : -1))
            if (r1 != 0) goto L_0x054e
            int r1 = r14.matchStat     // Catch:{ all -> 0x061a }
            r12 = 5
            if (r1 != r12) goto L_0x054e
            r1 = 0
            r26 = r1
            goto L_0x0554
        L_0x054e:
            java.lang.Long r1 = java.lang.Long.valueOf(r40)     // Catch:{ all -> 0x061a }
            r26 = r1
        L_0x0554:
            int r1 = r14.matchStat     // Catch:{ all -> 0x061a }
            if (r1 <= 0) goto L_0x055d
            r23 = 1
            r25 = 1
            goto L_0x0574
        L_0x055d:
            r12 = -2
            if (r1 != r12) goto L_0x0574
            int r7 = r7 + 1
            r15 = r46
            r40 = r4
            r10 = r5
            r1 = r35
            r0 = 16
            r2 = 13
            r3 = 1
            r4 = 0
            r5 = 0
            r26 = 4
            goto L_0x0d1a
        L_0x0574:
            r12 = r26
            goto L_0x05d2
        L_0x0578:
            r39 = r12
        L_0x057a:
            int r1 = r14.scanFieldInt(r2)     // Catch:{ all -> 0x061a }
            if (r1 != 0) goto L_0x0589
            int r12 = r14.matchStat     // Catch:{ all -> 0x061a }
            r15 = 5
            if (r12 != r15) goto L_0x0589
            r12 = 0
            r26 = r12
            goto L_0x058f
        L_0x0589:
            java.lang.Integer r12 = java.lang.Integer.valueOf(r1)     // Catch:{ all -> 0x061a }
            r26 = r12
        L_0x058f:
            int r12 = r14.matchStat     // Catch:{ all -> 0x061a }
            if (r12 <= 0) goto L_0x0598
            r23 = 1
            r25 = 1
            goto L_0x05af
        L_0x0598:
            r15 = -2
            if (r12 != r15) goto L_0x05af
            int r7 = r7 + 1
            r15 = r46
            r40 = r4
            r10 = r5
            r1 = r35
            r0 = 16
            r2 = 13
            r3 = 1
            r4 = 0
            r5 = 0
            r26 = 4
            goto L_0x0d1a
        L_0x05af:
            r12 = r26
            goto L_0x05d2
        L_0x05b2:
            r0 = move-exception
            r2 = r46
            r10 = r5
            r3 = r6
            r36 = r15
            r1 = r35
            goto L_0x0d6e
        L_0x05bd:
            r0 = move-exception
            r35 = r1
            r2 = r46
            r10 = r5
            r3 = r6
            r36 = r15
            goto L_0x0d6e
        L_0x05c8:
            r35 = r1
            r34 = r2
            r39 = r12
            r36 = r15
        L_0x05d0:
            r12 = r26
        L_0x05d2:
            if (r23 != 0) goto L_0x0888
            com.alibaba.fastjson.parser.SymbolTable r1 = r9.symbolTable     // Catch:{ all -> 0x087e }
            java.lang.String r1 = r14.scanSymbol(r1)     // Catch:{ all -> 0x087e }
            if (r1 != 0) goto L_0x0623
            int r2 = r14.token()     // Catch:{ all -> 0x061a }
            r15 = 13
            if (r2 != r15) goto L_0x05fd
            r15 = 16
            r14.nextToken(r15)     // Catch:{ all -> 0x05f6 }
            r40 = r4
            r10 = r5
            r3 = r6
            r1 = r24
            r18 = r35
            r46 = 0
            r12 = 0
            goto L_0x0a39
        L_0x05f6:
            r0 = move-exception
            r10 = r5
            r3 = r6
            r1 = r35
            goto L_0x0d6e
        L_0x05fd:
            r15 = 16
            if (r2 != r15) goto L_0x0625
            com.alibaba.fastjson.parser.Feature r15 = com.alibaba.fastjson.parser.Feature.AllowArbitraryCommas     // Catch:{ all -> 0x05f6 }
            boolean r15 = r14.isEnabled((com.alibaba.fastjson.parser.Feature) r15)     // Catch:{ all -> 0x05f6 }
            if (r15 == 0) goto L_0x0625
            r15 = r2
            r40 = r4
            r10 = r5
            r1 = r35
            r0 = 16
            r2 = 13
            r3 = 1
            r4 = 0
            r5 = 0
            r26 = 4
            goto L_0x0d1a
        L_0x061a:
            r0 = move-exception
            r2 = r46
            r10 = r5
            r3 = r6
            r1 = r35
            goto L_0x0d6e
        L_0x0623:
            r2 = r46
        L_0x0625:
            java.lang.String r15 = "$ref"
            if (r15 != r1) goto L_0x073e
            if (r5 == 0) goto L_0x073e
            r13 = 4
            r14.nextTokenWithColon(r13)     // Catch:{ all -> 0x05f6 }
            int r15 = r14.token()     // Catch:{ all -> 0x05f6 }
            r2 = r15
            if (r2 != r13) goto L_0x071d
            java.lang.String r13 = r14.stringVal()     // Catch:{ all -> 0x05f6 }
            java.lang.String r15 = "@"
            boolean r15 = r15.equals(r13)     // Catch:{ all -> 0x05f6 }
            if (r15 == 0) goto L_0x064c
            java.lang.Object r15 = r5.object     // Catch:{ all -> 0x05f6 }
            r37 = r3
            r26 = r7
            r38 = r12
            goto L_0x06f2
        L_0x064c:
            java.lang.String r15 = ".."
            boolean r15 = r15.equals(r13)     // Catch:{ all -> 0x05f6 }
            if (r15 == 0) goto L_0x0672
            com.alibaba.fastjson.parser.ParseContext r15 = r5.parent     // Catch:{ all -> 0x05f6 }
            r26 = r7
            java.lang.Object r7 = r15.object     // Catch:{ all -> 0x05f6 }
            if (r7 == 0) goto L_0x065f
            r35 = r7
            goto L_0x066a
        L_0x065f:
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r7 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x05f6 }
            r7.<init>(r15, r13)     // Catch:{ all -> 0x05f6 }
            r9.addResolveTask(r7)     // Catch:{ all -> 0x05f6 }
            r7 = 1
            r9.resolveStatus = r7     // Catch:{ all -> 0x05f6 }
        L_0x066a:
            r37 = r3
            r38 = r12
            r15 = r35
            goto L_0x06f2
        L_0x0672:
            r26 = r7
            java.lang.String r7 = "$"
            boolean r7 = r7.equals(r13)     // Catch:{ all -> 0x05f6 }
            if (r7 == 0) goto L_0x069b
            r7 = r5
        L_0x067d:
            com.alibaba.fastjson.parser.ParseContext r15 = r7.parent     // Catch:{ all -> 0x05f6 }
            if (r15 == 0) goto L_0x0683
            r7 = r15
            goto L_0x067d
        L_0x0683:
            java.lang.Object r15 = r7.object     // Catch:{ all -> 0x05f6 }
            if (r15 == 0) goto L_0x0689
            goto L_0x0696
        L_0x0689:
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r15 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x05f6 }
            r15.<init>(r7, r13)     // Catch:{ all -> 0x05f6 }
            r9.addResolveTask(r15)     // Catch:{ all -> 0x05f6 }
            r15 = 1
            r9.resolveStatus = r15     // Catch:{ all -> 0x05f6 }
            r15 = r35
        L_0x0696:
            r37 = r3
            r38 = r12
            goto L_0x06f2
        L_0x069b:
            r7 = 92
            int r15 = r13.indexOf(r7)     // Catch:{ all -> 0x05f6 }
            if (r15 <= 0) goto L_0x06d8
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ all -> 0x05f6 }
            r15.<init>()     // Catch:{ all -> 0x05f6 }
            r16 = 0
            r7 = r16
        L_0x06ac:
            r37 = r3
            int r3 = r13.length()     // Catch:{ all -> 0x05f6 }
            if (r7 >= r3) goto L_0x06d0
            char r3 = r13.charAt(r7)     // Catch:{ all -> 0x05f6 }
            r38 = r12
            r12 = 92
            if (r3 != r12) goto L_0x06c6
            int r7 = r7 + 1
            char r16 = r13.charAt(r7)     // Catch:{ all -> 0x05f6 }
            r3 = r16
        L_0x06c6:
            r15.append(r3)     // Catch:{ all -> 0x05f6 }
            r3 = 1
            int r7 = r7 + r3
            r3 = r37
            r12 = r38
            goto L_0x06ac
        L_0x06d0:
            r38 = r12
            java.lang.String r3 = r15.toString()     // Catch:{ all -> 0x05f6 }
            r13 = r3
            goto L_0x06dc
        L_0x06d8:
            r37 = r3
            r38 = r12
        L_0x06dc:
            java.lang.Object r3 = r9.resolveReference(r13)     // Catch:{ all -> 0x05f6 }
            if (r3 == 0) goto L_0x06e5
            r7 = r3
            r15 = r7
            goto L_0x06f2
        L_0x06e5:
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r7 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x05f6 }
            r7.<init>(r5, r13)     // Catch:{ all -> 0x05f6 }
            r9.addResolveTask(r7)     // Catch:{ all -> 0x05f6 }
            r7 = 1
            r9.resolveStatus = r7     // Catch:{ all -> 0x05f6 }
            r15 = r35
        L_0x06f2:
            r3 = 13
            r14.nextToken(r3)     // Catch:{ all -> 0x0717 }
            int r7 = r14.token()     // Catch:{ all -> 0x0717 }
            if (r7 != r3) goto L_0x070f
            r3 = 16
            r14.nextToken(r3)     // Catch:{ all -> 0x0717 }
            r9.setContext(r5, r15, r11)     // Catch:{ all -> 0x0717 }
            if (r6 == 0) goto L_0x070b
            r6.object = r15
        L_0x070b:
            r9.setContext(r5)
            return r15
        L_0x070f:
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0717 }
            java.lang.String r7 = "illegal ref"
            r3.<init>(r7)     // Catch:{ all -> 0x0717 }
            throw r3     // Catch:{ all -> 0x0717 }
        L_0x0717:
            r0 = move-exception
            r10 = r5
            r3 = r6
            r1 = r15
            goto L_0x0d6e
        L_0x071d:
            r37 = r3
            r26 = r7
            r38 = r12
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x05f6 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x05f6 }
            r7.<init>()     // Catch:{ all -> 0x05f6 }
            java.lang.String r12 = "illegal ref, "
            r7.append(r12)     // Catch:{ all -> 0x05f6 }
            java.lang.String r12 = com.alibaba.fastjson.parser.JSONToken.name(r2)     // Catch:{ all -> 0x05f6 }
            r7.append(r12)     // Catch:{ all -> 0x05f6 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x05f6 }
            r3.<init>(r7)     // Catch:{ all -> 0x05f6 }
            throw r3     // Catch:{ all -> 0x05f6 }
        L_0x073e:
            r37 = r3
            r26 = r7
            r38 = r12
            if (r4 == 0) goto L_0x074c
            boolean r3 = r4.equals(r1)     // Catch:{ all -> 0x05f6 }
            if (r3 != 0) goto L_0x0750
        L_0x074c:
            java.lang.String r3 = com.alibaba.fastjson.JSON.DEFAULT_TYPE_KEY     // Catch:{ all -> 0x0872 }
            if (r3 != r1) goto L_0x0863
        L_0x0750:
            r3 = 4
            r14.nextTokenWithColon(r3)     // Catch:{ all -> 0x0872 }
            int r7 = r14.token()     // Catch:{ all -> 0x0872 }
            if (r7 != r3) goto L_0x0848
            java.lang.String r3 = r14.stringVal()     // Catch:{ all -> 0x0872 }
            r7 = 16
            r14.nextToken(r7)     // Catch:{ all -> 0x0872 }
            com.alibaba.fastjson.util.JavaBeanInfo r12 = r8.beanInfo     // Catch:{ all -> 0x0872 }
            java.lang.String r12 = r12.typeName     // Catch:{ all -> 0x0872 }
            boolean r12 = r3.equals(r12)     // Catch:{ all -> 0x0872 }
            if (r12 != 0) goto L_0x0810
            com.alibaba.fastjson.parser.Feature r12 = com.alibaba.fastjson.parser.Feature.IgnoreAutoType     // Catch:{ all -> 0x0872 }
            boolean r12 = r9.isEnabled(r12)     // Catch:{ all -> 0x0872 }
            if (r12 == 0) goto L_0x077f
            r17 = r1
            r46 = r2
            r2 = r35
            r12 = r36
            goto L_0x0818
        L_0x077f:
            com.alibaba.fastjson.util.JavaBeanInfo r7 = r8.beanInfo     // Catch:{ all -> 0x0872 }
            r12 = r36
            com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer r7 = getSeeAlso(r12, r7, r3)     // Catch:{ all -> 0x0802 }
            r13 = 0
            if (r7 != 0) goto L_0x07c2
            java.lang.Class r15 = com.alibaba.fastjson.util.TypeUtils.getClass(r44)     // Catch:{ all -> 0x07b7 }
            r17 = r1
            com.alibaba.fastjson.parser.ParserConfig$AutoTypeCheckHandler r1 = r8.autoTypeCheckHandler     // Catch:{ all -> 0x07b7 }
            if (r1 == 0) goto L_0x07a0
            r46 = r2
            int r2 = r14.getFeatures()     // Catch:{ all -> 0x07dd }
            java.lang.Class r1 = r1.handler(r3, r15, r2)     // Catch:{ all -> 0x07dd }
            r13 = r1
            goto L_0x07a2
        L_0x07a0:
            r46 = r2
        L_0x07a2:
            if (r13 != 0) goto L_0x07ad
            int r1 = r14.getFeatures()     // Catch:{ all -> 0x07dd }
            java.lang.Class r1 = r12.checkAutoType(r3, r15, r1)     // Catch:{ all -> 0x07dd }
            r13 = r1
        L_0x07ad:
            com.alibaba.fastjson.parser.ParserConfig r1 = r43.getConfig()     // Catch:{ all -> 0x07dd }
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r1 = r1.getDeserializer((java.lang.reflect.Type) r13)     // Catch:{ all -> 0x07dd }
            r7 = r1
            goto L_0x07c6
        L_0x07b7:
            r0 = move-exception
            r46 = r2
            r10 = r5
            r3 = r6
            r36 = r12
            r1 = r35
            goto L_0x0d6e
        L_0x07c2:
            r17 = r1
            r46 = r2
        L_0x07c6:
            java.lang.Object r1 = r7.deserialze(r9, r13, r11)     // Catch:{ all -> 0x07f6 }
            boolean r2 = r7 instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer     // Catch:{ all -> 0x07f6 }
            if (r2 == 0) goto L_0x07e8
            r2 = r7
            com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer r2 = (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) r2     // Catch:{ all -> 0x07dd }
            if (r4 == 0) goto L_0x07e8
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r15 = r2.getFieldDeserializer((java.lang.String) r4)     // Catch:{ all -> 0x07dd }
            if (r15 == 0) goto L_0x07e8
            r15.setValue((java.lang.Object) r1, (java.lang.String) r3)     // Catch:{ all -> 0x07dd }
            goto L_0x07e8
        L_0x07dd:
            r0 = move-exception
            r2 = r46
            r10 = r5
            r3 = r6
            r36 = r12
            r1 = r35
            goto L_0x0d6e
        L_0x07e8:
            if (r6 == 0) goto L_0x07f0
            r2 = r35
            r6.object = r2
            goto L_0x07f2
        L_0x07f0:
            r2 = r35
        L_0x07f2:
            r9.setContext(r5)
            return r1
        L_0x07f6:
            r0 = move-exception
            r2 = r35
            r1 = r2
            r10 = r5
            r3 = r6
            r36 = r12
            r2 = r46
            goto L_0x0d6e
        L_0x0802:
            r0 = move-exception
            r46 = r2
            r2 = r35
            r1 = r2
            r10 = r5
            r3 = r6
            r36 = r12
            r2 = r46
            goto L_0x0d6e
        L_0x0810:
            r17 = r1
            r46 = r2
            r2 = r35
            r12 = r36
        L_0x0818:
            int r1 = r14.token()     // Catch:{ all -> 0x0859 }
            r15 = 13
            if (r1 != r15) goto L_0x0834
            r14.nextToken()     // Catch:{ all -> 0x0859 }
            r18 = r2
            r40 = r4
            r10 = r5
            r3 = r6
            r36 = r12
            r1 = r24
            r12 = 0
            r2 = r46
            r46 = 0
            goto L_0x0a39
        L_0x0834:
            r15 = r46
            r1 = r2
            r40 = r4
            r10 = r5
            r0 = r7
            r36 = r12
            r7 = r26
            r2 = 13
            r3 = 1
            r4 = 0
            r5 = 0
            r26 = 4
            goto L_0x0d1a
        L_0x0848:
            r17 = r1
            r46 = r2
            r2 = r35
            r12 = r36
            com.alibaba.fastjson.JSONException r1 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0859 }
            java.lang.String r3 = "syntax error"
            r1.<init>(r3)     // Catch:{ all -> 0x0859 }
            throw r1     // Catch:{ all -> 0x0859 }
        L_0x0859:
            r0 = move-exception
            r1 = r2
            r10 = r5
            r3 = r6
            r36 = r12
            r2 = r46
            goto L_0x0d6e
        L_0x0863:
            r17 = r1
            r46 = r2
            r2 = r35
            r12 = r36
            r7 = 16
            r15 = r46
            r21 = r17
            goto L_0x0896
        L_0x0872:
            r0 = move-exception
            r46 = r2
            r2 = r35
            r1 = r2
            r10 = r5
            r3 = r6
            r2 = r46
            goto L_0x0d6e
        L_0x087e:
            r0 = move-exception
            r2 = r35
            r1 = r2
            r10 = r5
            r3 = r6
            r2 = r46
            goto L_0x0d6e
        L_0x0888:
            r37 = r3
            r26 = r7
            r38 = r12
            r2 = r35
            r12 = r36
            r7 = 16
            r15 = r46
        L_0x0896:
            if (r2 != 0) goto L_0x08e0
            if (r24 != 0) goto L_0x08e0
            java.lang.Object r1 = r42.createInstance((com.alibaba.fastjson.parser.DefaultJSONParser) r43, (java.lang.reflect.Type) r44)     // Catch:{ all -> 0x08d7 }
            if (r1 != 0) goto L_0x08aa
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ all -> 0x08cf }
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer[] r3 = r8.fieldDeserializers     // Catch:{ all -> 0x08cf }
            int r3 = r3.length     // Catch:{ all -> 0x08cf }
            r2.<init>(r3)     // Catch:{ all -> 0x08cf }
            r24 = r2
        L_0x08aa:
            com.alibaba.fastjson.parser.ParseContext r2 = r9.setContext(r5, r1, r11)     // Catch:{ all -> 0x08cf }
            r3 = r2
            if (r34 != 0) goto L_0x08c9
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer[] r2 = r8.fieldDeserializers     // Catch:{ all -> 0x08c2 }
            int r2 = r2.length     // Catch:{ all -> 0x08c2 }
            int r2 = r2 / 32
            r6 = 1
            int r2 = r2 + r6
            int[] r2 = new int[r2]     // Catch:{ all -> 0x08c2 }
            r6 = r1
            r34 = r2
            r17 = r3
            r3 = r24
            goto L_0x08e5
        L_0x08c2:
            r0 = move-exception
            r10 = r5
            r36 = r12
            r2 = r15
            goto L_0x0d6e
        L_0x08c9:
            r6 = r1
            r17 = r3
            r3 = r24
            goto L_0x08e5
        L_0x08cf:
            r0 = move-exception
            r10 = r5
            r3 = r6
            r36 = r12
            r2 = r15
            goto L_0x0d6e
        L_0x08d7:
            r0 = move-exception
            r1 = r2
            r10 = r5
            r3 = r6
            r36 = r12
            r2 = r15
            goto L_0x0d6e
        L_0x08e0:
            r17 = r6
            r3 = r24
            r6 = r2
        L_0x08e5:
            if (r23 == 0) goto L_0x09ae
            if (r25 != 0) goto L_0x0913
            r2 = r44
            r0.parseField(r9, r6, r2, r3)     // Catch:{ all -> 0x0909 }
            r20 = r0
            r40 = r4
            r18 = r6
            r0 = r7
            r24 = r10
            r36 = r12
            r16 = r26
            r22 = r38
            r46 = 0
            r12 = 0
            r26 = 4
            r10 = r5
            r38 = r37
            r37 = r3
            goto L_0x0a1a
        L_0x0909:
            r0 = move-exception
            r10 = r5
            r1 = r6
            r36 = r12
            r2 = r15
            r3 = r17
            goto L_0x0d6e
        L_0x0913:
            r2 = r44
            if (r6 != 0) goto L_0x092d
            java.lang.String r1 = r10.name     // Catch:{ all -> 0x0923 }
            r36 = r12
            r12 = r38
            r3.put(r1, r12)     // Catch:{ all -> 0x09c4 }
            r7 = r37
            goto L_0x0970
        L_0x0923:
            r0 = move-exception
            r36 = r12
            r10 = r5
            r1 = r6
            r2 = r15
            r3 = r17
            goto L_0x0d6e
        L_0x092d:
            r36 = r12
            r12 = r38
            if (r12 != 0) goto L_0x094d
            java.lang.Class r1 = java.lang.Integer.TYPE     // Catch:{ all -> 0x09c4 }
            r7 = r37
            if (r7 == r1) goto L_0x0970
            java.lang.Class r1 = java.lang.Long.TYPE     // Catch:{ all -> 0x09c4 }
            if (r7 == r1) goto L_0x0970
            java.lang.Class r1 = java.lang.Float.TYPE     // Catch:{ all -> 0x09c4 }
            if (r7 == r1) goto L_0x0970
            java.lang.Class r1 = java.lang.Double.TYPE     // Catch:{ all -> 0x09c4 }
            if (r7 == r1) goto L_0x0970
            java.lang.Class r1 = java.lang.Boolean.TYPE     // Catch:{ all -> 0x09c4 }
            if (r7 == r1) goto L_0x0970
            r0.setValue((java.lang.Object) r6, (java.lang.Object) r12)     // Catch:{ all -> 0x09c4 }
            goto L_0x0970
        L_0x094d:
            r7 = r37
            if (r7 != r13) goto L_0x096d
            com.alibaba.fastjson.parser.Feature r1 = com.alibaba.fastjson.parser.Feature.TrimStringFieldValue     // Catch:{ all -> 0x09c4 }
            int r1 = r1.mask     // Catch:{ all -> 0x09c4 }
            r35 = r47 & r1
            if (r35 != 0) goto L_0x0965
            com.alibaba.fastjson.util.JavaBeanInfo r2 = r8.beanInfo     // Catch:{ all -> 0x09c4 }
            int r2 = r2.parserFeatures     // Catch:{ all -> 0x09c4 }
            r2 = r2 & r1
            if (r2 != 0) goto L_0x0965
            int r2 = r10.parserFeatures     // Catch:{ all -> 0x09c4 }
            r1 = r1 & r2
            if (r1 == 0) goto L_0x096d
        L_0x0965:
            r1 = r12
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x09c4 }
            java.lang.String r1 = r1.trim()     // Catch:{ all -> 0x09c4 }
            r12 = r1
        L_0x096d:
            r0.setValue((java.lang.Object) r6, (java.lang.Object) r12)     // Catch:{ all -> 0x09c4 }
        L_0x0970:
            if (r34 == 0) goto L_0x0981
            int r1 = r48 / 32
            int r2 = r48 % 32
            r35 = r34[r1]     // Catch:{ all -> 0x09c4 }
            r20 = 1
            int r37 = r20 << r2
            r35 = r35 | r37
            r34[r1] = r35     // Catch:{ all -> 0x09c4 }
            goto L_0x0983
        L_0x0981:
            r20 = 1
        L_0x0983:
            int r1 = r14.matchStat     // Catch:{ all -> 0x09c4 }
            r2 = 4
            if (r1 != r2) goto L_0x0994
            r37 = r3
            r40 = r4
            r10 = r5
            r18 = r6
            r46 = 0
            r12 = 0
            goto L_0x0a34
        L_0x0994:
            r20 = r0
            r37 = r3
            r40 = r4
            r18 = r6
            r38 = r7
            r24 = r10
            r22 = r12
            r16 = r26
            r46 = 0
            r0 = 16
            r12 = 0
            r26 = r2
            r10 = r5
            goto L_0x0a1a
        L_0x09ae:
            r36 = r12
            r7 = r37
            r12 = r38
            r2 = 4
            r20 = 1
            if (r3 != 0) goto L_0x09cc
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ all -> 0x09c4 }
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer[] r2 = r8.fieldDeserializers     // Catch:{ all -> 0x09c4 }
            int r2 = r2.length     // Catch:{ all -> 0x09c4 }
            r1.<init>(r2)     // Catch:{ all -> 0x09c4 }
            r35 = r1
            goto L_0x09ce
        L_0x09c4:
            r0 = move-exception
            r10 = r5
            r1 = r6
            r2 = r15
            r3 = r17
            goto L_0x0d6e
        L_0x09cc:
            r35 = r3
        L_0x09ce:
            r1 = r42
            r22 = 4
            r2 = r43
            r37 = r3
            r38 = r7
            r3 = r21
            r40 = r4
            r4 = r6
            r7 = r5
            r5 = r44
            r18 = r6
            r24 = r10
            r10 = r20
            r46 = 0
            r20 = r0
            r0 = 16
            r6 = r35
            r10 = r7
            r16 = r26
            r26 = r22
            r22 = r12
            r12 = 0
            r7 = r34
            boolean r1 = r1.parseField(r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0d56 }
            if (r1 != 0) goto L_0x0a12
            int r2 = r14.token()     // Catch:{ all -> 0x0d56 }
            r3 = 13
            if (r2 != r3) goto L_0x0a0a
            r14.nextToken()     // Catch:{ all -> 0x0d56 }
            goto L_0x0a34
        L_0x0a0a:
            r4 = r46
            r5 = r12
            r2 = 13
            r3 = 1
            goto L_0x0d12
        L_0x0a12:
            int r2 = r14.token()     // Catch:{ all -> 0x0d56 }
            r3 = 17
            if (r2 == r3) goto L_0x0d4d
        L_0x0a1a:
            int r1 = r14.token()     // Catch:{ all -> 0x0d56 }
            if (r1 != r0) goto L_0x0a28
            r4 = r46
            r5 = r12
            r2 = 13
            r3 = 1
            goto L_0x0d12
        L_0x0a28:
            int r1 = r14.token()     // Catch:{ all -> 0x0d56 }
            r2 = 13
            if (r1 != r2) goto L_0x0d00
            r14.nextToken(r0)     // Catch:{ all -> 0x0d56 }
        L_0x0a34:
            r2 = r15
            r3 = r17
            r1 = r37
        L_0x0a39:
            if (r18 != 0) goto L_0x0ccc
            if (r1 != 0) goto L_0x0a5c
            java.lang.Object r0 = r42.createInstance((com.alibaba.fastjson.parser.DefaultJSONParser) r43, (java.lang.reflect.Type) r44)     // Catch:{ all -> 0x0a57 }
            r4 = r0
            if (r3 != 0) goto L_0x0a4e
            com.alibaba.fastjson.parser.ParseContext r0 = r9.setContext(r10, r4, r11)     // Catch:{ all -> 0x0a4a }
            r3 = r0
            goto L_0x0a4e
        L_0x0a4a:
            r0 = move-exception
            r1 = r4
            goto L_0x0d6e
        L_0x0a4e:
            if (r3 == 0) goto L_0x0a53
            r3.object = r4
        L_0x0a53:
            r9.setContext(r10)
            return r4
        L_0x0a57:
            r0 = move-exception
            r1 = r18
            goto L_0x0d6e
        L_0x0a5c:
            com.alibaba.fastjson.util.JavaBeanInfo r0 = r8.beanInfo     // Catch:{ all -> 0x0cc5 }
            java.lang.String[] r4 = r0.creatorConstructorParameters     // Catch:{ all -> 0x0cc5 }
            java.lang.String r5 = ""
            if (r4 == 0) goto L_0x0b40
            int r0 = r4.length     // Catch:{ all -> 0x0cc5 }
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0cc5 }
            r6 = 0
        L_0x0a68:
            int r7 = r4.length     // Catch:{ all -> 0x0cc5 }
            if (r6 >= r7) goto L_0x0b39
            r7 = r4[r6]     // Catch:{ all -> 0x0cc5 }
            java.lang.Object r15 = r1.remove(r7)     // Catch:{ all -> 0x0cc5 }
            if (r15 != 0) goto L_0x0ad9
            com.alibaba.fastjson.util.JavaBeanInfo r12 = r8.beanInfo     // Catch:{ all -> 0x0cc5 }
            r48 = r2
            java.lang.reflect.Type[] r2 = r12.creatorConstructorParameterTypes     // Catch:{ all -> 0x0cbe }
            r2 = r2[r6]     // Catch:{ all -> 0x0cbe }
            com.alibaba.fastjson.util.FieldInfo[] r12 = r12.fields     // Catch:{ all -> 0x0cbe }
            r12 = r12[r6]     // Catch:{ all -> 0x0cbe }
            r16 = r7
            java.lang.Class r7 = java.lang.Byte.TYPE     // Catch:{ all -> 0x0cbe }
            if (r2 != r7) goto L_0x0a8b
            java.lang.Byte r7 = java.lang.Byte.valueOf(r46)     // Catch:{ all -> 0x0cbe }
            r15 = r7
            goto L_0x0ad6
        L_0x0a8b:
            java.lang.Class r7 = java.lang.Short.TYPE     // Catch:{ all -> 0x0cbe }
            if (r2 != r7) goto L_0x0a95
            java.lang.Short r7 = java.lang.Short.valueOf(r46)     // Catch:{ all -> 0x0cbe }
            r15 = r7
            goto L_0x0ad6
        L_0x0a95:
            java.lang.Class r7 = java.lang.Integer.TYPE     // Catch:{ all -> 0x0cbe }
            if (r2 != r7) goto L_0x0a9f
            java.lang.Integer r7 = java.lang.Integer.valueOf(r46)     // Catch:{ all -> 0x0cbe }
            r15 = r7
            goto L_0x0ad6
        L_0x0a9f:
            java.lang.Class r7 = java.lang.Long.TYPE     // Catch:{ all -> 0x0cbe }
            if (r2 != r7) goto L_0x0aa9
            java.lang.Long r7 = java.lang.Long.valueOf(r32)     // Catch:{ all -> 0x0cbe }
            r15 = r7
            goto L_0x0ad6
        L_0x0aa9:
            java.lang.Class r7 = java.lang.Float.TYPE     // Catch:{ all -> 0x0cbe }
            if (r2 != r7) goto L_0x0ab3
            java.lang.Float r7 = java.lang.Float.valueOf(r31)     // Catch:{ all -> 0x0cbe }
            r15 = r7
            goto L_0x0ad6
        L_0x0ab3:
            java.lang.Class r7 = java.lang.Double.TYPE     // Catch:{ all -> 0x0cbe }
            if (r2 != r7) goto L_0x0abd
            java.lang.Double r7 = java.lang.Double.valueOf(r29)     // Catch:{ all -> 0x0cbe }
            r15 = r7
            goto L_0x0ad6
        L_0x0abd:
            java.lang.Class r7 = java.lang.Boolean.TYPE     // Catch:{ all -> 0x0cbe }
            if (r2 != r7) goto L_0x0ac5
            java.lang.Boolean r7 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x0cbe }
            r15 = r7
            goto L_0x0ad6
        L_0x0ac5:
            if (r2 != r13) goto L_0x0ad4
            int r7 = r12.parserFeatures     // Catch:{ all -> 0x0cbe }
            r17 = r2
            com.alibaba.fastjson.parser.Feature r2 = com.alibaba.fastjson.parser.Feature.InitStringFieldAsEmpty     // Catch:{ all -> 0x0cbe }
            int r2 = r2.mask     // Catch:{ all -> 0x0cbe }
            r2 = r2 & r7
            if (r2 == 0) goto L_0x0ad6
            r15 = r5
            goto L_0x0ad6
        L_0x0ad4:
            r17 = r2
        L_0x0ad6:
            r11 = r46
            goto L_0x0b2c
        L_0x0ad9:
            r48 = r2
            r16 = r7
            com.alibaba.fastjson.util.JavaBeanInfo r2 = r8.beanInfo     // Catch:{ all -> 0x0cbe }
            java.lang.reflect.Type[] r2 = r2.creatorConstructorParameterTypes     // Catch:{ all -> 0x0cbe }
            if (r2 == 0) goto L_0x0b2a
            int r7 = r2.length     // Catch:{ all -> 0x0cbe }
            if (r6 >= r7) goto L_0x0b2a
            r2 = r2[r6]     // Catch:{ all -> 0x0cbe }
            boolean r7 = r2 instanceof java.lang.Class     // Catch:{ all -> 0x0cbe }
            if (r7 == 0) goto L_0x0b25
            r7 = r2
            java.lang.Class r7 = (java.lang.Class) r7     // Catch:{ all -> 0x0cbe }
            boolean r12 = r7.isInstance(r15)     // Catch:{ all -> 0x0cbe }
            if (r12 != 0) goto L_0x0b20
            boolean r12 = r15 instanceof java.util.List     // Catch:{ all -> 0x0cbe }
            if (r12 == 0) goto L_0x0b1b
            r12 = r15
            java.util.List r12 = (java.util.List) r12     // Catch:{ all -> 0x0cbe }
            r17 = r2
            int r2 = r12.size()     // Catch:{ all -> 0x0cbe }
            r11 = 1
            if (r2 != r11) goto L_0x0b18
            r11 = r46
            java.lang.Object r2 = r12.get(r11)     // Catch:{ all -> 0x0cbe }
            boolean r20 = r7.isInstance(r2)     // Catch:{ all -> 0x0cbe }
            if (r20 == 0) goto L_0x0b2c
            java.lang.Object r20 = r12.get(r11)     // Catch:{ all -> 0x0cbe }
            r15 = r20
            goto L_0x0b2c
        L_0x0b18:
            r11 = r46
            goto L_0x0b2c
        L_0x0b1b:
            r11 = r46
            r17 = r2
            goto L_0x0b2c
        L_0x0b20:
            r11 = r46
            r17 = r2
            goto L_0x0b2c
        L_0x0b25:
            r11 = r46
            r17 = r2
            goto L_0x0b2c
        L_0x0b2a:
            r11 = r46
        L_0x0b2c:
            r0[r6] = r15     // Catch:{ all -> 0x0cbe }
            int r6 = r6 + 1
            r2 = r48
            r46 = r11
            r12 = 0
            r11 = r45
            goto L_0x0a68
        L_0x0b39:
            r11 = r46
            r48 = r2
            r6 = r0
            goto L_0x0bd8
        L_0x0b40:
            r11 = r46
            r48 = r2
            com.alibaba.fastjson.util.FieldInfo[] r0 = r0.fields     // Catch:{ all -> 0x0cbe }
            int r2 = r0.length     // Catch:{ all -> 0x0cbe }
            java.lang.Object[] r6 = new java.lang.Object[r2]     // Catch:{ all -> 0x0cbe }
            r7 = 0
        L_0x0b4a:
            if (r7 >= r2) goto L_0x0bd4
            r12 = r0[r7]     // Catch:{ all -> 0x0cbe }
            java.lang.String r15 = r12.name     // Catch:{ all -> 0x0cbe }
            java.lang.Object r15 = r1.get(r15)     // Catch:{ all -> 0x0cbe }
            if (r15 != 0) goto L_0x0bc5
            java.lang.reflect.Type r11 = r12.fieldType     // Catch:{ all -> 0x0cbe }
            r16 = r0
            java.lang.Class r0 = java.lang.Byte.TYPE     // Catch:{ all -> 0x0cbe }
            if (r11 != r0) goto L_0x0b69
            r17 = 0
            java.lang.Byte r0 = java.lang.Byte.valueOf(r17)     // Catch:{ all -> 0x0cbe }
            r15 = r0
            r17 = r2
            goto L_0x0bc9
        L_0x0b69:
            java.lang.Class r0 = java.lang.Short.TYPE     // Catch:{ all -> 0x0cbe }
            if (r11 != r0) goto L_0x0b77
            r17 = 0
            java.lang.Short r0 = java.lang.Short.valueOf(r17)     // Catch:{ all -> 0x0cbe }
            r15 = r0
            r17 = r2
            goto L_0x0bc9
        L_0x0b77:
            java.lang.Class r0 = java.lang.Integer.TYPE     // Catch:{ all -> 0x0cbe }
            if (r11 != r0) goto L_0x0b85
            r17 = 0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r17)     // Catch:{ all -> 0x0cbe }
            r15 = r0
            r17 = r2
            goto L_0x0bc9
        L_0x0b85:
            java.lang.Class r0 = java.lang.Long.TYPE     // Catch:{ all -> 0x0cbe }
            if (r11 != r0) goto L_0x0b91
            java.lang.Long r0 = java.lang.Long.valueOf(r32)     // Catch:{ all -> 0x0cbe }
            r15 = r0
            r17 = r2
            goto L_0x0bc9
        L_0x0b91:
            java.lang.Class r0 = java.lang.Float.TYPE     // Catch:{ all -> 0x0cbe }
            if (r11 != r0) goto L_0x0b9d
            java.lang.Float r0 = java.lang.Float.valueOf(r31)     // Catch:{ all -> 0x0cbe }
            r15 = r0
            r17 = r2
            goto L_0x0bc9
        L_0x0b9d:
            java.lang.Class r0 = java.lang.Double.TYPE     // Catch:{ all -> 0x0cbe }
            if (r11 != r0) goto L_0x0ba9
            java.lang.Double r0 = java.lang.Double.valueOf(r29)     // Catch:{ all -> 0x0cbe }
            r15 = r0
            r17 = r2
            goto L_0x0bc9
        L_0x0ba9:
            java.lang.Class r0 = java.lang.Boolean.TYPE     // Catch:{ all -> 0x0cbe }
            if (r11 != r0) goto L_0x0bb3
            java.lang.Boolean r0 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x0cbe }
            r15 = r0
            r17 = r2
            goto L_0x0bc9
        L_0x0bb3:
            if (r11 != r13) goto L_0x0bc2
            int r0 = r12.parserFeatures     // Catch:{ all -> 0x0cbe }
            r17 = r2
            com.alibaba.fastjson.parser.Feature r2 = com.alibaba.fastjson.parser.Feature.InitStringFieldAsEmpty     // Catch:{ all -> 0x0cbe }
            int r2 = r2.mask     // Catch:{ all -> 0x0cbe }
            r0 = r0 & r2
            if (r0 == 0) goto L_0x0bc9
            r15 = r5
            goto L_0x0bc9
        L_0x0bc2:
            r17 = r2
            goto L_0x0bc9
        L_0x0bc5:
            r16 = r0
            r17 = r2
        L_0x0bc9:
            r6[r7] = r15     // Catch:{ all -> 0x0cbe }
            int r7 = r7 + 1
            r0 = r16
            r2 = r17
            r11 = 0
            goto L_0x0b4a
        L_0x0bd4:
            r16 = r0
            r17 = r2
        L_0x0bd8:
            com.alibaba.fastjson.util.JavaBeanInfo r0 = r8.beanInfo     // Catch:{ all -> 0x0cbe }
            java.lang.reflect.Constructor<?> r2 = r0.creatorConstructor     // Catch:{ all -> 0x0cbe }
            if (r2 == 0) goto L_0x0c8a
            r2 = 0
            boolean r0 = r0.f32kotlin     // Catch:{ all -> 0x0cbe }
            if (r0 == 0) goto L_0x0bff
            r0 = 0
        L_0x0be4:
            int r5 = r6.length     // Catch:{ all -> 0x0cbe }
            if (r0 >= r5) goto L_0x0bff
            r5 = r6[r0]     // Catch:{ all -> 0x0cbe }
            if (r5 != 0) goto L_0x0bfc
            com.alibaba.fastjson.util.JavaBeanInfo r5 = r8.beanInfo     // Catch:{ all -> 0x0cbe }
            com.alibaba.fastjson.util.FieldInfo[] r5 = r5.fields     // Catch:{ all -> 0x0cbe }
            if (r5 == 0) goto L_0x0bfc
            int r7 = r5.length     // Catch:{ all -> 0x0cbe }
            if (r0 >= r7) goto L_0x0bfc
            r5 = r5[r0]     // Catch:{ all -> 0x0cbe }
            java.lang.Class<?> r7 = r5.fieldClass     // Catch:{ all -> 0x0cbe }
            if (r7 != r13) goto L_0x0bff
            r2 = 1
            goto L_0x0bff
        L_0x0bfc:
            int r0 = r0 + 1
            goto L_0x0be4
        L_0x0bff:
            if (r2 == 0) goto L_0x0c2d
            com.alibaba.fastjson.util.JavaBeanInfo r0 = r8.beanInfo     // Catch:{ Exception -> 0x0c62 }
            java.lang.reflect.Constructor<?> r0 = r0.kotlinDefaultConstructor     // Catch:{ Exception -> 0x0c62 }
            if (r0 == 0) goto L_0x0c2d
            r5 = 0
            java.lang.Object[] r7 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x0c62 }
            java.lang.Object r0 = r0.newInstance(r7)     // Catch:{ Exception -> 0x0c62 }
            r5 = r0
            r0 = 0
        L_0x0c10:
            int r7 = r6.length     // Catch:{ Exception -> 0x0c29 }
            if (r0 >= r7) goto L_0x0c28
            r7 = r6[r0]     // Catch:{ Exception -> 0x0c29 }
            if (r7 == 0) goto L_0x0c25
            com.alibaba.fastjson.util.JavaBeanInfo r11 = r8.beanInfo     // Catch:{ Exception -> 0x0c29 }
            com.alibaba.fastjson.util.FieldInfo[] r11 = r11.fields     // Catch:{ Exception -> 0x0c29 }
            if (r11 == 0) goto L_0x0c25
            int r12 = r11.length     // Catch:{ Exception -> 0x0c29 }
            if (r0 >= r12) goto L_0x0c25
            r11 = r11[r0]     // Catch:{ Exception -> 0x0c29 }
            r11.set(r5, r7)     // Catch:{ Exception -> 0x0c29 }
        L_0x0c25:
            int r0 = r0 + 1
            goto L_0x0c10
        L_0x0c28:
            goto L_0x0c36
        L_0x0c29:
            r0 = move-exception
            r18 = r5
            goto L_0x0c63
        L_0x0c2d:
            com.alibaba.fastjson.util.JavaBeanInfo r0 = r8.beanInfo     // Catch:{ Exception -> 0x0c62 }
            java.lang.reflect.Constructor<?> r0 = r0.creatorConstructor     // Catch:{ Exception -> 0x0c62 }
            java.lang.Object r0 = r0.newInstance(r6)     // Catch:{ Exception -> 0x0c62 }
            r5 = r0
        L_0x0c36:
            if (r4 == 0) goto L_0x0c61
            java.util.Set r0 = r1.entrySet()     // Catch:{ all -> 0x0cfa }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x0cfa }
        L_0x0c41:
            boolean r7 = r0.hasNext()     // Catch:{ all -> 0x0cfa }
            if (r7 == 0) goto L_0x0c61
            java.lang.Object r7 = r0.next()     // Catch:{ all -> 0x0cfa }
            java.util.Map$Entry r7 = (java.util.Map.Entry) r7     // Catch:{ all -> 0x0cfa }
            java.lang.Object r11 = r7.getKey()     // Catch:{ all -> 0x0cfa }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ all -> 0x0cfa }
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r11 = r8.getFieldDeserializer((java.lang.String) r11)     // Catch:{ all -> 0x0cfa }
            if (r11 == 0) goto L_0x0c60
            java.lang.Object r12 = r7.getValue()     // Catch:{ all -> 0x0cfa }
            r11.setValue((java.lang.Object) r5, (java.lang.Object) r12)     // Catch:{ all -> 0x0cfa }
        L_0x0c60:
            goto L_0x0c41
        L_0x0c61:
            goto L_0x0cb9
        L_0x0c62:
            r0 = move-exception
        L_0x0c63:
            com.alibaba.fastjson.JSONException r5 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0cbe }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0cbe }
            r7.<init>()     // Catch:{ all -> 0x0cbe }
            java.lang.String r11 = "create instance error, "
            r7.append(r11)     // Catch:{ all -> 0x0cbe }
            r7.append(r4)     // Catch:{ all -> 0x0cbe }
            java.lang.String r11 = ", "
            r7.append(r11)     // Catch:{ all -> 0x0cbe }
            com.alibaba.fastjson.util.JavaBeanInfo r11 = r8.beanInfo     // Catch:{ all -> 0x0cbe }
            java.lang.reflect.Constructor<?> r11 = r11.creatorConstructor     // Catch:{ all -> 0x0cbe }
            java.lang.String r11 = r11.toGenericString()     // Catch:{ all -> 0x0cbe }
            r7.append(r11)     // Catch:{ all -> 0x0cbe }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0cbe }
            r5.<init>(r7, r0)     // Catch:{ all -> 0x0cbe }
            throw r5     // Catch:{ all -> 0x0cbe }
        L_0x0c8a:
            java.lang.reflect.Method r0 = r0.factoryMethod     // Catch:{ all -> 0x0cbe }
            if (r0 == 0) goto L_0x0cb7
            r5 = 0
            java.lang.Object r0 = r0.invoke(r5, r6)     // Catch:{ Exception -> 0x0c95 }
            r5 = r0
            goto L_0x0cb9
        L_0x0c95:
            r0 = move-exception
            r2 = r0
            r0 = r2
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0cbe }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0cbe }
            r5.<init>()     // Catch:{ all -> 0x0cbe }
            java.lang.String r7 = "create factory method error, "
            r5.append(r7)     // Catch:{ all -> 0x0cbe }
            com.alibaba.fastjson.util.JavaBeanInfo r7 = r8.beanInfo     // Catch:{ all -> 0x0cbe }
            java.lang.reflect.Method r7 = r7.factoryMethod     // Catch:{ all -> 0x0cbe }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0cbe }
            r5.append(r7)     // Catch:{ all -> 0x0cbe }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0cbe }
            r2.<init>(r5, r0)     // Catch:{ all -> 0x0cbe }
            throw r2     // Catch:{ all -> 0x0cbe }
        L_0x0cb7:
            r5 = r18
        L_0x0cb9:
            if (r3 == 0) goto L_0x0cd0
            r3.object = r5     // Catch:{ all -> 0x0cfa }
            goto L_0x0cd0
        L_0x0cbe:
            r0 = move-exception
            r2 = r48
            r1 = r18
            goto L_0x0d6e
        L_0x0cc5:
            r0 = move-exception
            r48 = r2
            r1 = r18
            goto L_0x0d6e
        L_0x0ccc:
            r48 = r2
            r5 = r18
        L_0x0cd0:
            com.alibaba.fastjson.util.JavaBeanInfo r0 = r8.beanInfo     // Catch:{ all -> 0x0cfa }
            java.lang.reflect.Method r0 = r0.buildMethod     // Catch:{ all -> 0x0cfa }
            r2 = r0
            if (r2 != 0) goto L_0x0ce0
            if (r3 == 0) goto L_0x0cdc
            r3.object = r5
        L_0x0cdc:
            r9.setContext(r10)
            return r5
        L_0x0ce0:
            r4 = 0
            java.lang.Object[] r0 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x0cf1 }
            java.lang.Object r0 = r2.invoke(r5, r0)     // Catch:{ Exception -> 0x0cf1 }
            if (r3 == 0) goto L_0x0ced
            r3.object = r5
        L_0x0ced:
            r9.setContext(r10)
            return r0
        L_0x0cf1:
            r0 = move-exception
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0cfa }
            java.lang.String r6 = "build object error"
            r4.<init>(r6, r0)     // Catch:{ all -> 0x0cfa }
            throw r4     // Catch:{ all -> 0x0cfa }
        L_0x0cfa:
            r0 = move-exception
            r2 = r48
            r1 = r5
            goto L_0x0d6e
        L_0x0d00:
            r4 = r46
            r5 = r12
            int r1 = r14.token()     // Catch:{ all -> 0x0d56 }
            r3 = 18
            if (r1 == r3) goto L_0x0d2d
            int r1 = r14.token()     // Catch:{ all -> 0x0d56 }
            r3 = 1
            if (r1 == r3) goto L_0x0d2d
        L_0x0d12:
            r7 = r16
            r6 = r17
            r1 = r18
            r24 = r37
        L_0x0d1a:
            int r11 = r48 + 1
            r5 = r10
            r0 = r11
            r3 = r15
            r2 = r34
            r15 = r36
            r12 = r39
            r4 = r40
            r10 = r44
            r11 = r45
            goto L_0x01ce
        L_0x0d2d:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0d56 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0d56 }
            r1.<init>()     // Catch:{ all -> 0x0d56 }
            java.lang.String r2 = "syntax error, unexpect token "
            r1.append(r2)     // Catch:{ all -> 0x0d56 }
            int r2 = r14.token()     // Catch:{ all -> 0x0d56 }
            java.lang.String r2 = com.alibaba.fastjson.parser.JSONToken.name(r2)     // Catch:{ all -> 0x0d56 }
            r1.append(r2)     // Catch:{ all -> 0x0d56 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0d56 }
            r0.<init>(r1)     // Catch:{ all -> 0x0d56 }
            throw r0     // Catch:{ all -> 0x0d56 }
        L_0x0d4d:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0d56 }
            java.lang.String r2 = "syntax error, unexpect token ':'"
            r0.<init>(r2)     // Catch:{ all -> 0x0d56 }
            throw r0     // Catch:{ all -> 0x0d56 }
        L_0x0d56:
            r0 = move-exception
            r2 = r15
            r3 = r17
            r1 = r18
            goto L_0x0d6e
        L_0x0d5d:
            r0 = move-exception
            r34 = r2
            r10 = r5
            r36 = r15
            r2 = r1
            r3 = r6
            r2 = r46
            goto L_0x0d6e
        L_0x0d68:
            r0 = move-exception
            r10 = r5
            r36 = r15
            r34 = r48
        L_0x0d6e:
            if (r3 == 0) goto L_0x0d72
            r3.object = r1
        L_0x0d72:
            r9.setContext(r10)
            throw r0
        L_0x0d76:
            java.lang.Object r0 = r43.parse()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object, java.lang.Object, int, int[]):java.lang.Object");
    }

    /* access modifiers changed from: protected */
    public Enum scanEnum(JSONLexerBase lexer, char[] name_chars, ObjectDeserializer fieldValueDeserilizer) {
        EnumDeserializer enumDeserializer = null;
        if (fieldValueDeserilizer instanceof EnumDeserializer) {
            enumDeserializer = (EnumDeserializer) fieldValueDeserilizer;
        }
        if (enumDeserializer == null) {
            lexer.matchStat = -1;
            return null;
        }
        long enumNameHashCode = lexer.scanEnumSymbol(name_chars);
        if (lexer.matchStat <= 0) {
            return null;
        }
        Enum e = enumDeserializer.getEnumByHashCode(enumNameHashCode);
        if (e == null) {
            if (enumNameHashCode == -3750763034362895579L) {
                return null;
            }
            if (lexer.isEnabled(Feature.ErrorOnEnumNotMatch)) {
                throw new JSONException("not match enum value, " + enumDeserializer.enumClass);
            }
        }
        return e;
    }

    public boolean parseField(DefaultJSONParser parser, String key, Object object, Type objectType, Map<String, Object> fieldValues) {
        return parseField(parser, key, object, objectType, fieldValues, (int[]) null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v8, resolved type: java.lang.reflect.Field} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v32, resolved type: com.alibaba.fastjson.parser.deserializer.FieldDeserializer} */
    /* JADX WARNING: type inference failed for: r25v0, types: [boolean, int] */
    /* JADX WARNING: type inference failed for: r25v4 */
    /* JADX WARNING: type inference failed for: r25v5 */
    /* JADX WARNING: type inference failed for: r25v7 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x0294  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0175  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parseField(com.alibaba.fastjson.parser.DefaultJSONParser r29, java.lang.String r30, java.lang.Object r31, java.lang.reflect.Type r32, java.util.Map<java.lang.String, java.lang.Object> r33, int[] r34) {
        /*
            r28 = this;
            r1 = r28
            r2 = r29
            r12 = r30
            r13 = r31
            r14 = r32
            r15 = r33
            r11 = r34
            com.alibaba.fastjson.parser.JSONLexer r10 = r2.lexer
            com.alibaba.fastjson.parser.Feature r0 = com.alibaba.fastjson.parser.Feature.DisableFieldSmartMatch
            int r9 = r0.mask
            com.alibaba.fastjson.parser.Feature r0 = com.alibaba.fastjson.parser.Feature.InitStringFieldAsEmpty
            int r8 = r0.mask
            boolean r0 = r10.isEnabled((int) r9)
            if (r0 != 0) goto L_0x003e
            com.alibaba.fastjson.util.JavaBeanInfo r0 = r1.beanInfo
            int r0 = r0.parserFeatures
            r0 = r0 & r9
            if (r0 == 0) goto L_0x0026
            goto L_0x003e
        L_0x0026:
            boolean r0 = r10.isEnabled((int) r8)
            if (r0 != 0) goto L_0x0039
            com.alibaba.fastjson.util.JavaBeanInfo r0 = r1.beanInfo
            int r0 = r0.parserFeatures
            r0 = r0 & r8
            if (r0 == 0) goto L_0x0034
            goto L_0x0039
        L_0x0034:
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r0 = r1.smartMatch(r12, r11)
            goto L_0x0042
        L_0x0039:
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r0 = r1.smartMatch(r12)
            goto L_0x0042
        L_0x003e:
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r0 = r1.getFieldDeserializer((java.lang.String) r12)
        L_0x0042:
            com.alibaba.fastjson.parser.Feature r3 = com.alibaba.fastjson.parser.Feature.SupportNonPublicField
            int r7 = r3.mask
            r16 = 0
            r6 = 1
            if (r0 != 0) goto L_0x0165
            boolean r3 = r10.isEnabled((int) r7)
            if (r3 != 0) goto L_0x0067
            com.alibaba.fastjson.util.JavaBeanInfo r3 = r1.beanInfo
            int r3 = r3.parserFeatures
            r3 = r3 & r7
            if (r3 == 0) goto L_0x0059
            goto L_0x0067
        L_0x0059:
            r18 = r0
            r25 = r6
            r19 = r7
            r20 = r8
            r26 = r9
            r27 = r10
            goto L_0x0171
        L_0x0067:
            java.util.concurrent.ConcurrentMap<java.lang.String, java.lang.Object> r3 = r1.extraFieldDeserializers
            if (r3 != 0) goto L_0x00ef
            java.util.concurrent.ConcurrentHashMap r3 = new java.util.concurrent.ConcurrentHashMap
            r4 = 1061158912(0x3f400000, float:0.75)
            r3.<init>(r6, r4, r6)
            java.lang.Class<?> r4 = r1.clazz
        L_0x0074:
            if (r4 == 0) goto L_0x00e8
            java.lang.Class<java.lang.Object> r5 = java.lang.Object.class
            if (r4 == r5) goto L_0x00e8
            java.lang.reflect.Field[] r5 = r4.getDeclaredFields()
            int r6 = r5.length
            r18 = r0
            r0 = r16
        L_0x0083:
            if (r0 >= r6) goto L_0x00dc
            r19 = r6
            r6 = r5[r0]
            r20 = r5
            java.lang.String r5 = r6.getName()
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r21 = r1.getFieldDeserializer((java.lang.String) r5)
            if (r21 == 0) goto L_0x0098
            r23 = r7
            goto L_0x00d3
        L_0x0098:
            int r21 = r6.getModifiers()
            r22 = r21 & 16
            if (r22 != 0) goto L_0x00cf
            r22 = r21 & 8
            if (r22 == 0) goto L_0x00a7
            r23 = r7
            goto L_0x00d3
        L_0x00a7:
            r22 = r5
            java.lang.Class<com.alibaba.fastjson.annotation.JSONField> r5 = com.alibaba.fastjson.annotation.JSONField.class
            java.lang.annotation.Annotation r5 = com.alibaba.fastjson.util.TypeUtils.getAnnotation((java.lang.reflect.Field) r6, r5)
            com.alibaba.fastjson.annotation.JSONField r5 = (com.alibaba.fastjson.annotation.JSONField) r5
            if (r5 == 0) goto L_0x00c5
            r23 = r7
            java.lang.String r7 = r5.name()
            r24 = r5
            java.lang.String r5 = ""
            boolean r5 = r5.equals(r7)
            if (r5 != 0) goto L_0x00c9
            r5 = r7
            goto L_0x00cb
        L_0x00c5:
            r24 = r5
            r23 = r7
        L_0x00c9:
            r5 = r22
        L_0x00cb:
            r3.put(r5, r6)
            goto L_0x00d3
        L_0x00cf:
            r22 = r5
            r23 = r7
        L_0x00d3:
            int r0 = r0 + 1
            r6 = r19
            r5 = r20
            r7 = r23
            goto L_0x0083
        L_0x00dc:
            r20 = r5
            r23 = r7
            java.lang.Class r4 = r4.getSuperclass()
            r0 = r18
            r6 = 1
            goto L_0x0074
        L_0x00e8:
            r18 = r0
            r23 = r7
            r1.extraFieldDeserializers = r3
            goto L_0x00f3
        L_0x00ef:
            r18 = r0
            r23 = r7
        L_0x00f3:
            java.util.concurrent.ConcurrentMap<java.lang.String, java.lang.Object> r0 = r1.extraFieldDeserializers
            java.lang.Object r0 = r0.get(r12)
            if (r0 == 0) goto L_0x015a
            boolean r3 = r0 instanceof com.alibaba.fastjson.parser.deserializer.FieldDeserializer
            if (r3 == 0) goto L_0x010e
            r3 = r0
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r3 = (com.alibaba.fastjson.parser.deserializer.FieldDeserializer) r3
            r20 = r8
            r26 = r9
            r27 = r10
            r19 = r23
            r25 = 1
            goto L_0x0173
        L_0x010e:
            r7 = r0
            java.lang.reflect.Field r7 = (java.lang.reflect.Field) r7
            r6 = 1
            r7.setAccessible(r6)
            com.alibaba.fastjson.util.FieldInfo r17 = new com.alibaba.fastjson.util.FieldInfo
            java.lang.Class r5 = r7.getDeclaringClass()
            java.lang.Class r19 = r7.getType()
            java.lang.reflect.Type r20 = r7.getGenericType()
            r21 = 0
            r22 = 0
            r24 = 0
            r3 = r17
            r4 = r30
            r25 = r6
            r6 = r19
            r19 = r23
            r23 = r7
            r7 = r20
            r20 = r8
            r8 = r23
            r26 = r9
            r9 = r21
            r27 = r10
            r10 = r22
            r11 = r24
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11)
            com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer r4 = new com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer
            com.alibaba.fastjson.parser.ParserConfig r5 = r29.getConfig()
            java.lang.Class<?> r6 = r1.clazz
            r4.<init>(r5, r6, r3)
            java.util.concurrent.ConcurrentMap<java.lang.String, java.lang.Object> r5 = r1.extraFieldDeserializers
            r5.put(r12, r4)
            r3 = r4
            goto L_0x0173
        L_0x015a:
            r20 = r8
            r26 = r9
            r27 = r10
            r19 = r23
            r25 = 1
            goto L_0x0171
        L_0x0165:
            r18 = r0
            r25 = r6
            r19 = r7
            r20 = r8
            r26 = r9
            r27 = r10
        L_0x0171:
            r3 = r18
        L_0x0173:
            if (r3 != 0) goto L_0x0294
            com.alibaba.fastjson.parser.Feature r4 = com.alibaba.fastjson.parser.Feature.IgnoreNotMatch
            r5 = r27
            boolean r4 = r5.isEnabled((com.alibaba.fastjson.parser.Feature) r4)
            if (r4 == 0) goto L_0x026a
            r4 = -1
            r6 = 0
        L_0x0181:
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer[] r7 = r1.sortedFieldDeserializers
            int r8 = r7.length
            if (r6 >= r8) goto L_0x024f
            r7 = r7[r6]
            com.alibaba.fastjson.util.FieldInfo r8 = r7.fieldInfo
            boolean r9 = r8.unwrapped
            if (r9 == 0) goto L_0x0245
            boolean r9 = r7 instanceof com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer
            if (r9 == 0) goto L_0x0245
            java.lang.reflect.Field r9 = r8.field
            java.lang.String r10 = "parse unwrapped field error."
            if (r9 == 0) goto L_0x021c
            r9 = r7
            com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer r9 = (com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer) r9
            com.alibaba.fastjson.parser.ParserConfig r11 = r29.getConfig()
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r11 = r9.getFieldValueDeserilizer(r11)
            boolean r0 = r11 instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer
            if (r0 == 0) goto L_0x01e9
            r18 = r3
            r3 = r11
            com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer r3 = (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) r3
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r1 = r3.getFieldDeserializer((java.lang.String) r12)
            if (r1 == 0) goto L_0x01e6
            java.lang.reflect.Field r0 = r8.field     // Catch:{ Exception -> 0x01dd }
            java.lang.Object r0 = r0.get(r13)     // Catch:{ Exception -> 0x01dd }
            if (r0 != 0) goto L_0x01cb
            r21 = r0
            r0 = r11
            com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer r0 = (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) r0     // Catch:{ Exception -> 0x01dd }
            r22 = r3
            java.lang.reflect.Type r3 = r8.fieldType     // Catch:{ Exception -> 0x01db }
            java.lang.Object r0 = r0.createInstance((com.alibaba.fastjson.parser.DefaultJSONParser) r2, (java.lang.reflect.Type) r3)     // Catch:{ Exception -> 0x01db }
            r7.setValue((java.lang.Object) r13, (java.lang.Object) r0)     // Catch:{ Exception -> 0x01db }
            goto L_0x01cf
        L_0x01cb:
            r21 = r0
            r22 = r3
        L_0x01cf:
            int r3 = r9.getFastMatchToken()     // Catch:{ Exception -> 0x01db }
            r5.nextTokenWithColon(r3)     // Catch:{ Exception -> 0x01db }
            r1.parseField(r2, r0, r14, r15)     // Catch:{ Exception -> 0x01db }
            r4 = r6
            goto L_0x021a
        L_0x01db:
            r0 = move-exception
            goto L_0x01e0
        L_0x01dd:
            r0 = move-exception
            r22 = r3
        L_0x01e0:
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException
            r3.<init>(r10, r0)
            throw r3
        L_0x01e6:
            r22 = r3
            goto L_0x021a
        L_0x01e9:
            r18 = r3
            boolean r0 = r11 instanceof com.alibaba.fastjson.parser.deserializer.MapDeserializer
            if (r0 == 0) goto L_0x021a
            r1 = r11
            com.alibaba.fastjson.parser.deserializer.MapDeserializer r1 = (com.alibaba.fastjson.parser.deserializer.MapDeserializer) r1
            java.lang.reflect.Field r0 = r8.field     // Catch:{ Exception -> 0x0213 }
            java.lang.Object r0 = r0.get(r13)     // Catch:{ Exception -> 0x0213 }
            java.util.Map r0 = (java.util.Map) r0     // Catch:{ Exception -> 0x0213 }
            if (r0 != 0) goto L_0x0206
            java.lang.reflect.Type r3 = r8.fieldType     // Catch:{ Exception -> 0x0213 }
            java.util.Map r3 = r1.createMap(r3)     // Catch:{ Exception -> 0x0213 }
            r0 = r3
            r7.setValue((java.lang.Object) r13, (java.lang.Object) r0)     // Catch:{ Exception -> 0x0213 }
        L_0x0206:
            r5.nextTokenWithColon()     // Catch:{ Exception -> 0x0213 }
            java.lang.Object r3 = r29.parse(r30)     // Catch:{ Exception -> 0x0213 }
            r0.put(r12, r3)     // Catch:{ Exception -> 0x0213 }
            r4 = r6
            goto L_0x021b
        L_0x0213:
            r0 = move-exception
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException
            r3.<init>(r10, r0)
            throw r3
        L_0x021a:
        L_0x021b:
            goto L_0x0247
        L_0x021c:
            r18 = r3
            java.lang.reflect.Method r0 = r8.method
            java.lang.Class[] r0 = r0.getParameterTypes()
            int r0 = r0.length
            r1 = 2
            if (r0 != r1) goto L_0x021b
            r5.nextTokenWithColon()
            java.lang.Object r3 = r29.parse(r30)
            java.lang.reflect.Method r0 = r8.method     // Catch:{ Exception -> 0x023e }
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x023e }
            r1[r16] = r12     // Catch:{ Exception -> 0x023e }
            r1[r25] = r3     // Catch:{ Exception -> 0x023e }
            r0.invoke(r13, r1)     // Catch:{ Exception -> 0x023e }
            r0 = r6
            r4 = r0
            goto L_0x0247
        L_0x023e:
            r0 = move-exception
            com.alibaba.fastjson.JSONException r1 = new com.alibaba.fastjson.JSONException
            r1.<init>(r10, r0)
            throw r1
        L_0x0245:
            r18 = r3
        L_0x0247:
            int r6 = r6 + 1
            r1 = r28
            r3 = r18
            goto L_0x0181
        L_0x024f:
            r18 = r3
            r0 = -1
            if (r4 == r0) goto L_0x0264
            r1 = r34
            if (r1 == 0) goto L_0x0263
            int r0 = r4 / 32
            int r3 = r4 % 32
            r6 = r1[r0]
            int r7 = r25 << r3
            r6 = r6 | r7
            r1[r0] = r6
        L_0x0263:
            return r25
        L_0x0264:
            r1 = r34
            r2.parseExtra(r13, r12)
            return r16
        L_0x026a:
            r18 = r3
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "setter not found, class "
            r3.append(r4)
            r4 = r28
            java.lang.Class<?> r6 = r4.clazz
            java.lang.String r6 = r6.getName()
            r3.append(r6)
            java.lang.String r6 = ", property "
            r3.append(r6)
            r3.append(r12)
            java.lang.String r3 = r3.toString()
            r0.<init>(r3)
            throw r0
        L_0x0294:
            r4 = r1
            r18 = r3
            r5 = r27
            r1 = r34
            r0 = -1
            r3 = 0
        L_0x029d:
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer[] r6 = r4.sortedFieldDeserializers
            int r7 = r6.length
            if (r3 >= r7) goto L_0x02af
            r6 = r6[r3]
            r7 = r18
            if (r6 != r7) goto L_0x02aa
            r0 = r3
            goto L_0x02b1
        L_0x02aa:
            int r3 = r3 + 1
            r18 = r7
            goto L_0x029d
        L_0x02af:
            r7 = r18
        L_0x02b1:
            r3 = -1
            if (r0 == r3) goto L_0x02c8
            if (r1 == 0) goto L_0x02c8
            java.lang.String r3 = "_"
            boolean r3 = r12.startsWith(r3)
            if (r3 == 0) goto L_0x02c8
            boolean r3 = isSetFlag(r0, r1)
            if (r3 == 0) goto L_0x02c8
            r2.parseExtra(r13, r12)
            return r16
        L_0x02c8:
            int r3 = r7.getFastMatchToken()
            r5.nextTokenWithColon(r3)
            r7.parseField(r2, r13, r14, r15)
            if (r1 == 0) goto L_0x02df
            int r3 = r0 / 32
            int r6 = r0 % 32
            r8 = r1[r3]
            int r9 = r25 << r6
            r8 = r8 | r9
            r1[r3] = r8
        L_0x02df:
            return r25
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.parseField(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.String, java.lang.Object, java.lang.reflect.Type, java.util.Map, int[]):boolean");
    }

    public FieldDeserializer smartMatch(String key) {
        return smartMatch(key, (int[]) null);
    }

    public FieldDeserializer smartMatch(String key, int[] setFlags) {
        if (key == null) {
            return null;
        }
        FieldDeserializer fieldDeserializer = getFieldDeserializer(key, setFlags);
        if (fieldDeserializer != null) {
            return fieldDeserializer;
        }
        if (this.smartMatchHashArray == null) {
            long[] hashArray2 = new long[this.sortedFieldDeserializers.length];
            int i = 0;
            while (true) {
                FieldDeserializer[] fieldDeserializerArr = this.sortedFieldDeserializers;
                if (i >= fieldDeserializerArr.length) {
                    break;
                }
                hashArray2[i] = fieldDeserializerArr[i].fieldInfo.nameHashCode;
                i++;
            }
            Arrays.sort(hashArray2);
            this.smartMatchHashArray = hashArray2;
        }
        int pos = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv1a_64_lower(key));
        if (pos < 0) {
            pos = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv1a_64_extract(key));
        }
        boolean is = false;
        if (pos < 0) {
            boolean startsWith = key.startsWith("is");
            is = startsWith;
            if (startsWith) {
                pos = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv1a_64_extract(key.substring(2)));
            }
        }
        if (pos >= 0) {
            if (this.smartMatchHashArrayMapping == null) {
                short[] mapping = new short[this.smartMatchHashArray.length];
                Arrays.fill(mapping, -1);
                int i2 = 0;
                while (true) {
                    FieldDeserializer[] fieldDeserializerArr2 = this.sortedFieldDeserializers;
                    if (i2 >= fieldDeserializerArr2.length) {
                        break;
                    }
                    int p = Arrays.binarySearch(this.smartMatchHashArray, fieldDeserializerArr2[i2].fieldInfo.nameHashCode);
                    if (p >= 0) {
                        mapping[p] = (short) i2;
                    }
                    i2++;
                }
                this.smartMatchHashArrayMapping = mapping;
            }
            short deserIndex = this.smartMatchHashArrayMapping[pos];
            if (deserIndex != -1 && !isSetFlag(deserIndex, setFlags)) {
                fieldDeserializer = this.sortedFieldDeserializers[deserIndex];
            }
        }
        if (fieldDeserializer == null) {
            return fieldDeserializer;
        }
        FieldInfo fieldInfo = fieldDeserializer.fieldInfo;
        if ((fieldInfo.parserFeatures & Feature.DisableFieldSmartMatch.mask) != 0) {
            return null;
        }
        Class fieldClass = fieldInfo.fieldClass;
        if (!is || fieldClass == Boolean.TYPE || fieldClass == Boolean.class) {
            return fieldDeserializer;
        }
        return null;
    }

    public int getFastMatchToken() {
        return 12;
    }

    private Object createFactoryInstance(ParserConfig config, Object value) {
        return this.beanInfo.factoryMethod.invoke((Object) null, new Object[]{value});
    }

    public Object createInstance(Map<String, Object> map, ParserConfig config) {
        Constructor<?> constructor;
        FieldInfo[] fieldInfoArr;
        Integer index;
        Iterator<Map.Entry<String, Object>> it;
        Object value;
        double doubleValue;
        float floatValue;
        ParserConfig parserConfig = config;
        JavaBeanInfo javaBeanInfo = this.beanInfo;
        Map map2 = null;
        if (javaBeanInfo.creatorConstructor == null && javaBeanInfo.factoryMethod == null) {
            Object object = createInstance((DefaultJSONParser) null, (Type) this.clazz);
            Iterator<Map.Entry<String, Object>> it2 = map.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry<String, Object> entry = it2.next();
                Object value2 = entry.getValue();
                FieldDeserializer fieldDeser = smartMatch(entry.getKey());
                if (fieldDeser != null) {
                    FieldInfo fieldInfo = fieldDeser.fieldInfo;
                    Field field = fieldDeser.fieldInfo.field;
                    Type paramType = fieldInfo.fieldType;
                    Class<?> fieldClass = fieldInfo.fieldClass;
                    JSONField fieldAnnation = fieldInfo.getAnnotation();
                    if (fieldInfo.declaringClass == null || (fieldClass.isInstance(value2) && (fieldAnnation == null || fieldAnnation.deserializeUsing() == Void.class))) {
                        if (field == null || fieldInfo.method != null) {
                            it = it2;
                        } else {
                            Class fieldType = field.getType();
                            if (fieldType == Boolean.TYPE) {
                                if (value2 == Boolean.FALSE) {
                                    field.setBoolean(object, false);
                                } else if (value2 == Boolean.TRUE) {
                                    field.setBoolean(object, true);
                                } else {
                                    it = it2;
                                }
                            } else if (fieldType == Integer.TYPE) {
                                if (value2 instanceof Number) {
                                    field.setInt(object, ((Number) value2).intValue());
                                } else {
                                    it = it2;
                                }
                            } else if (fieldType != Long.TYPE) {
                                Class fieldType2 = fieldType;
                                if (fieldType2 != Float.TYPE) {
                                    it = it2;
                                    if (fieldType2 == Double.TYPE) {
                                        if (value2 instanceof Number) {
                                            field.setDouble(object, ((Number) value2).doubleValue());
                                            it2 = it;
                                            map2 = null;
                                        } else if (value2 instanceof String) {
                                            String strVal = (String) value2;
                                            if (strVal.length() <= 10) {
                                                doubleValue = TypeUtils.parseDouble(strVal);
                                            } else {
                                                doubleValue = Double.parseDouble(strVal);
                                            }
                                            field.setDouble(object, doubleValue);
                                            it2 = it;
                                            map2 = null;
                                        }
                                    } else if (value2 != null && paramType == value2.getClass()) {
                                        field.set(object, value2);
                                        it2 = it;
                                        map2 = null;
                                    }
                                } else if (value2 instanceof Number) {
                                    field.setFloat(object, ((Number) value2).floatValue());
                                    map2 = null;
                                } else if (value2 instanceof String) {
                                    String strVal2 = (String) value2;
                                    Iterator<Map.Entry<String, Object>> it3 = it2;
                                    if (strVal2.length() <= 10) {
                                        floatValue = TypeUtils.parseFloat(strVal2);
                                    } else {
                                        floatValue = Float.parseFloat(strVal2);
                                    }
                                    field.setFloat(object, floatValue);
                                    it2 = it3;
                                    map2 = null;
                                } else {
                                    it = it2;
                                }
                            } else if (value2 instanceof Number) {
                                Class cls = fieldType;
                                field.setLong(object, ((Number) value2).longValue());
                                map2 = null;
                            } else {
                                it = it2;
                            }
                        }
                        String format = fieldInfo.format;
                        if (format != null && paramType == Date.class) {
                            value = TypeUtils.castToDate(value2, format);
                        } else if (format != null && (paramType instanceof Class) && ((Class) paramType).getName().equals("java.time.LocalDateTime")) {
                            value = Jdk8DateCodec.castToLocalDateTime(value2, format);
                        } else if (paramType instanceof ParameterizedType) {
                            value = TypeUtils.cast(value2, (ParameterizedType) paramType, parserConfig);
                        } else {
                            value = TypeUtils.cast(value2, paramType, parserConfig);
                        }
                        fieldDeser.setValue(object, value);
                        it2 = it;
                        map2 = null;
                    } else {
                        fieldDeser.parseField(new DefaultJSONParser(JSON.toJSONString(value2)), object, paramType, map2);
                    }
                }
            }
            Method method = this.beanInfo.buildMethod;
            if (method == null) {
                return object;
            }
            try {
                return method.invoke(object, new Object[0]);
            } catch (Exception e) {
                throw new JSONException("build object error", e);
            }
        } else {
            FieldInfo[] fieldInfoList = javaBeanInfo.fields;
            int size = fieldInfoList.length;
            Object[] params = new Object[size];
            Map<String, Integer> missFields = null;
            for (int i = 0; i < size; i++) {
                FieldInfo fieldInfo2 = fieldInfoList[i];
                boolean param = map.get(fieldInfo2.name);
                if (param == null) {
                    Class<?> fieldClass2 = fieldInfo2.fieldClass;
                    if (fieldClass2 == Integer.TYPE) {
                        param = 0;
                    } else if (fieldClass2 == Long.TYPE) {
                        param = 0L;
                    } else if (fieldClass2 == Short.TYPE) {
                        param = (short) 0;
                    } else if (fieldClass2 == Byte.TYPE) {
                        param = (byte) 0;
                    } else if (fieldClass2 == Float.TYPE) {
                        param = Float.valueOf(0.0f);
                    } else if (fieldClass2 == Double.TYPE) {
                        param = Double.valueOf(0.0d);
                    } else if (fieldClass2 == Character.TYPE) {
                        param = '0';
                    } else if (fieldClass2 == Boolean.TYPE) {
                        param = false;
                    }
                    if (missFields == null) {
                        missFields = new HashMap<>();
                    }
                    missFields.put(fieldInfo2.name, Integer.valueOf(i));
                }
                params[i] = param;
            }
            Map<String, Object> map3 = map;
            if (missFields != null) {
                for (Map.Entry<String, Object> entry2 : map.entrySet()) {
                    Object value3 = entry2.getValue();
                    FieldDeserializer fieldDeser2 = smartMatch(entry2.getKey());
                    if (!(fieldDeser2 == null || (index = missFields.get(fieldDeser2.fieldInfo.name)) == null)) {
                        params[index.intValue()] = value3;
                    }
                }
            }
            JavaBeanInfo javaBeanInfo2 = this.beanInfo;
            if (javaBeanInfo2.creatorConstructor != null) {
                boolean hasNull = false;
                if (javaBeanInfo2.f32kotlin) {
                    for (int i2 = 0; i2 < params.length; i2++) {
                        Object param2 = params[i2];
                        if (param2 == null) {
                            FieldInfo[] fieldInfoArr2 = this.beanInfo.fields;
                            if (fieldInfoArr2 != null && i2 < fieldInfoArr2.length && fieldInfoArr2[i2].fieldClass == String.class) {
                                hasNull = true;
                            }
                        } else {
                            Class<?> cls2 = param2.getClass();
                            FieldInfo[] fieldInfoArr3 = this.beanInfo.fields;
                            if (cls2 != fieldInfoArr3[i2].fieldClass) {
                                params[i2] = TypeUtils.cast(param2, fieldInfoArr3[i2].fieldClass, parserConfig);
                            }
                        }
                    }
                }
                if (!hasNull || (constructor = this.beanInfo.kotlinDefaultConstructor) == null) {
                    try {
                        return this.beanInfo.creatorConstructor.newInstance(params);
                    } catch (Exception e2) {
                        throw new JSONException("create instance error, " + this.beanInfo.creatorConstructor.toGenericString(), e2);
                    }
                } else {
                    try {
                        Object object2 = constructor.newInstance(new Object[0]);
                        for (int i3 = 0; i3 < params.length; i3++) {
                            Object param3 = params[i3];
                            if (!(param3 == null || (fieldInfoArr = this.beanInfo.fields) == null || i3 >= fieldInfoArr.length)) {
                                fieldInfoArr[i3].set(object2, param3);
                            }
                        }
                        return object2;
                    } catch (Exception e3) {
                        throw new JSONException("create instance error, " + this.beanInfo.creatorConstructor.toGenericString(), e3);
                    }
                }
            } else {
                Method method2 = javaBeanInfo2.factoryMethod;
                if (method2 == null) {
                    return null;
                }
                try {
                    return method2.invoke((Object) null, params);
                } catch (Exception e4) {
                    throw new JSONException("create factory method error, " + this.beanInfo.factoryMethod.toString(), e4);
                }
            }
        }
    }

    public Type getFieldType(int ordinal) {
        return this.sortedFieldDeserializers[ordinal].fieldInfo.fieldType;
    }

    /* access modifiers changed from: protected */
    public Object parseRest(DefaultJSONParser parser, Type type, Object fieldName, Object instance, int features) {
        return parseRest(parser, type, fieldName, instance, features, new int[0]);
    }

    /* access modifiers changed from: protected */
    public Object parseRest(DefaultJSONParser parser, Type type, Object fieldName, Object instance, int features, int[] setFlags) {
        return deserialze(parser, type, fieldName, instance, features, setFlags);
    }

    protected static JavaBeanDeserializer getSeeAlso(ParserConfig config, JavaBeanInfo beanInfo2, String typeName) {
        JSONType jSONType = beanInfo2.jsonType;
        if (jSONType == null) {
            return null;
        }
        for (Class<?> seeAlsoClass : jSONType.seeAlso()) {
            ObjectDeserializer seeAlsoDeser = config.getDeserializer((Type) seeAlsoClass);
            if (seeAlsoDeser instanceof JavaBeanDeserializer) {
                JavaBeanDeserializer seeAlsoJavaBeanDeser = (JavaBeanDeserializer) seeAlsoDeser;
                JavaBeanInfo subBeanInfo = seeAlsoJavaBeanDeser.beanInfo;
                if (subBeanInfo.typeName.equals(typeName)) {
                    return seeAlsoJavaBeanDeser;
                }
                JavaBeanDeserializer subSeeAlso = getSeeAlso(config, subBeanInfo, typeName);
                if (subSeeAlso != null) {
                    return subSeeAlso;
                }
            }
        }
        return null;
    }

    protected static void parseArray(Collection collection, ObjectDeserializer deser, DefaultJSONParser parser, Type type, Object fieldName) {
        JSONLexerBase lexer = (JSONLexerBase) parser.lexer;
        int token = lexer.token();
        if (token == 8) {
            lexer.nextToken(16);
            int token2 = lexer.token();
            return;
        }
        if (token != 14) {
            parser.throwException(token);
        }
        if (lexer.getCurrent() == '[') {
            lexer.next();
            lexer.setToken(14);
        } else {
            lexer.nextToken(14);
        }
        if (lexer.token() == 15) {
            lexer.nextToken();
            return;
        }
        int index = 0;
        while (true) {
            collection.add(deser.deserialze(parser, type, Integer.valueOf(index)));
            index++;
            if (lexer.token() != 16) {
                break;
            } else if (lexer.getCurrent() == '[') {
                lexer.next();
                lexer.setToken(14);
            } else {
                lexer.nextToken(14);
            }
        }
        int token3 = lexer.token();
        if (token3 != 15) {
            parser.throwException(token3);
        }
        if (lexer.getCurrent() == ',') {
            lexer.next();
            lexer.setToken(16);
            return;
        }
        lexer.nextToken(16);
    }
}
