package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MapDeserializer extends ContextObjectDeserializer implements ObjectDeserializer {
    public static MapDeserializer instance = new MapDeserializer();

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName, String format, int features) {
        Map<Object, Object> map;
        if (type == JSONObject.class && parser.getFieldTypeResolver() == null) {
            return parser.parseObject();
        }
        JSONLexer lexer = parser.lexer;
        if (lexer.token() == 8) {
            lexer.nextToken(16);
            return null;
        }
        boolean unmodifiableMap = (type instanceof Class) && "java.util.Collections$UnmodifiableMap".equals(((Class) type).getName());
        if ((lexer.getFeatures() & Feature.OrderedField.mask) != 0) {
            map = createMap(type, lexer.getFeatures());
        } else {
            map = createMap(type);
        }
        ParseContext context = parser.getContext();
        try {
            parser.setContext(context, map, fieldName);
            T t = deserialze(parser, type, fieldName, (Map) map, features);
            if (unmodifiableMap) {
                t = Collections.unmodifiableMap((Map) t);
            }
            return t;
        } finally {
            parser.setContext(context);
        }
    }

    /* access modifiers changed from: protected */
    public Object deserialze(DefaultJSONParser parser, Type type, Object fieldName, Map map) {
        return deserialze(parser, type, fieldName, map, 0);
    }

    /* access modifiers changed from: protected */
    public Object deserialze(DefaultJSONParser parser, Type type, Object fieldName, Map map, int features) {
        Type valueType;
        if (!(type instanceof ParameterizedType)) {
            return parser.parseObject(map, fieldName);
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type keyType = parameterizedType.getActualTypeArguments()[0];
        if (map.getClass().getName().equals("org.springframework.util.LinkedMultiValueMap")) {
            valueType = List.class;
        } else {
            valueType = parameterizedType.getActualTypeArguments()[1];
        }
        if (String.class == keyType) {
            return parseMap(parser, (Map<String, Object>) map, valueType, fieldName, features);
        }
        return parseMap(parser, (Map<Object, Object>) map, keyType, valueType, fieldName);
    }

    public static Map parseMap(DefaultJSONParser parser, Map<String, Object> map, Type valueType, Object fieldName) {
        return parseMap(parser, map, valueType, fieldName, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:75:?, code lost:
        r9 = r10.getDeserializer((java.lang.reflect.Type) r14);
        r4.nextToken(16);
        r1.setResolveStatus(2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x01c6, code lost:
        if (r6 == null) goto L_0x01cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x01ca, code lost:
        if ((r3 instanceof java.lang.Integer) != false) goto L_0x01cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x01cc, code lost:
        r16.popContext();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01cf, code lost:
        r11 = (java.util.Map) r9.deserialze(r1, r14, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01d5, code lost:
        r1.setContext(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x01d8, code lost:
        return r11;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Map parseMap(com.alibaba.fastjson.parser.DefaultJSONParser r16, java.util.Map<java.lang.String, java.lang.Object> r17, java.lang.reflect.Type r18, java.lang.Object r19, int r20) {
        /*
            r1 = r16
            r2 = r17
            r3 = r19
            com.alibaba.fastjson.parser.JSONLexer r4 = r1.lexer
            int r5 = r4.token()
            r0 = 12
            if (r5 == r0) goto L_0x0093
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r6 = "syntax error, expect {, actual "
            r0.append(r6)
            java.lang.String r6 = r4.tokenName()
            r0.append(r6)
            java.lang.String r0 = r0.toString()
            boolean r6 = r3 instanceof java.lang.String
            if (r6 == 0) goto L_0x004a
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r0)
            java.lang.String r7 = ", fieldName "
            r6.append(r7)
            java.lang.String r0 = r6.toString()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r0)
            r6.append(r3)
            java.lang.String r0 = r6.toString()
        L_0x004a:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r0)
            java.lang.String r7 = ", "
            r6.append(r7)
            java.lang.String r0 = r6.toString()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r0)
            java.lang.String r7 = r4.info()
            r6.append(r7)
            java.lang.String r0 = r6.toString()
            r6 = 4
            if (r5 == r6) goto L_0x008d
            com.alibaba.fastjson.JSONArray r6 = new com.alibaba.fastjson.JSONArray
            r6.<init>()
            r1.parseArray((java.util.Collection) r6, (java.lang.Object) r3)
            int r7 = r6.size()
            r8 = 1
            if (r7 != r8) goto L_0x008d
            r7 = 0
            java.lang.Object r7 = r6.get(r7)
            boolean r8 = r7 instanceof com.alibaba.fastjson.JSONObject
            if (r8 == 0) goto L_0x008d
            r8 = r7
            com.alibaba.fastjson.JSONObject r8 = (com.alibaba.fastjson.JSONObject) r8
            return r8
        L_0x008d:
            com.alibaba.fastjson.JSONException r6 = new com.alibaba.fastjson.JSONException
            r6.<init>(r0)
            throw r6
        L_0x0093:
            com.alibaba.fastjson.parser.ParseContext r6 = r16.getContext()
            r0 = 0
        L_0x0098:
            r4.skipWhitespace()     // Catch:{ all -> 0x0260 }
            char r7 = r4.getCurrent()     // Catch:{ all -> 0x0260 }
            com.alibaba.fastjson.parser.Feature r8 = com.alibaba.fastjson.parser.Feature.AllowArbitraryCommas     // Catch:{ all -> 0x0260 }
            boolean r8 = r4.isEnabled((com.alibaba.fastjson.parser.Feature) r8)     // Catch:{ all -> 0x0260 }
            if (r8 == 0) goto L_0x00b7
        L_0x00a7:
            r8 = 44
            if (r7 != r8) goto L_0x00b7
            r4.next()     // Catch:{ all -> 0x0260 }
            r4.skipWhitespace()     // Catch:{ all -> 0x0260 }
            char r8 = r4.getCurrent()     // Catch:{ all -> 0x0260 }
            r7 = r8
            goto L_0x00a7
        L_0x00b7:
            java.lang.String r8 = "expect ':' at "
            r9 = 58
            r10 = 34
            r11 = 16
            if (r7 != r10) goto L_0x00ee
            com.alibaba.fastjson.parser.SymbolTable r12 = r16.getSymbolTable()     // Catch:{ all -> 0x0260 }
            java.lang.String r12 = r4.scanSymbol(r12, r10)     // Catch:{ all -> 0x0260 }
            r4.skipWhitespace()     // Catch:{ all -> 0x0260 }
            char r13 = r4.getCurrent()     // Catch:{ all -> 0x0260 }
            r7 = r13
            if (r7 != r9) goto L_0x00d5
            goto L_0x015b
        L_0x00d5:
            com.alibaba.fastjson.JSONException r9 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0260 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0260 }
            r10.<init>()     // Catch:{ all -> 0x0260 }
            r10.append(r8)     // Catch:{ all -> 0x0260 }
            int r8 = r4.pos()     // Catch:{ all -> 0x0260 }
            r10.append(r8)     // Catch:{ all -> 0x0260 }
            java.lang.String r8 = r10.toString()     // Catch:{ all -> 0x0260 }
            r9.<init>(r8)     // Catch:{ all -> 0x0260 }
            throw r9     // Catch:{ all -> 0x0260 }
        L_0x00ee:
            r12 = 125(0x7d, float:1.75E-43)
            if (r7 != r12) goto L_0x0100
            r4.next()     // Catch:{ all -> 0x0260 }
            r4.resetStringPosition()     // Catch:{ all -> 0x0260 }
            r4.nextToken(r11)     // Catch:{ all -> 0x0260 }
            r1.setContext(r6)
            return r2
        L_0x0100:
            java.lang.String r12 = "syntax error"
            r13 = 39
            if (r7 != r13) goto L_0x0141
            com.alibaba.fastjson.parser.Feature r14 = com.alibaba.fastjson.parser.Feature.AllowSingleQuotes     // Catch:{ all -> 0x0260 }
            boolean r14 = r4.isEnabled((com.alibaba.fastjson.parser.Feature) r14)     // Catch:{ all -> 0x0260 }
            if (r14 == 0) goto L_0x013b
            com.alibaba.fastjson.parser.SymbolTable r12 = r16.getSymbolTable()     // Catch:{ all -> 0x0260 }
            java.lang.String r12 = r4.scanSymbol(r12, r13)     // Catch:{ all -> 0x0260 }
            r4.skipWhitespace()     // Catch:{ all -> 0x0260 }
            char r13 = r4.getCurrent()     // Catch:{ all -> 0x0260 }
            r7 = r13
            if (r7 != r9) goto L_0x0122
            goto L_0x015b
        L_0x0122:
            com.alibaba.fastjson.JSONException r9 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0260 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0260 }
            r10.<init>()     // Catch:{ all -> 0x0260 }
            r10.append(r8)     // Catch:{ all -> 0x0260 }
            int r8 = r4.pos()     // Catch:{ all -> 0x0260 }
            r10.append(r8)     // Catch:{ all -> 0x0260 }
            java.lang.String r8 = r10.toString()     // Catch:{ all -> 0x0260 }
            r9.<init>(r8)     // Catch:{ all -> 0x0260 }
            throw r9     // Catch:{ all -> 0x0260 }
        L_0x013b:
            com.alibaba.fastjson.JSONException r8 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x0260 }
            r8.<init>(r12)     // Catch:{ all -> 0x0260 }
            throw r8     // Catch:{ all -> 0x0260 }
        L_0x0141:
            com.alibaba.fastjson.parser.Feature r13 = com.alibaba.fastjson.parser.Feature.AllowUnQuotedFieldNames     // Catch:{ all -> 0x0260 }
            boolean r13 = r4.isEnabled((com.alibaba.fastjson.parser.Feature) r13)     // Catch:{ all -> 0x0260 }
            if (r13 == 0) goto L_0x0254
            com.alibaba.fastjson.parser.SymbolTable r12 = r16.getSymbolTable()     // Catch:{ all -> 0x0260 }
            java.lang.String r12 = r4.scanSymbolUnQuoted(r12)     // Catch:{ all -> 0x0260 }
            r4.skipWhitespace()     // Catch:{ all -> 0x0260 }
            char r13 = r4.getCurrent()     // Catch:{ all -> 0x0260 }
            r7 = r13
            if (r7 != r9) goto L_0x022f
        L_0x015b:
            r4.next()     // Catch:{ all -> 0x0260 }
            r4.skipWhitespace()     // Catch:{ all -> 0x0260 }
            char r8 = r4.getCurrent()     // Catch:{ all -> 0x0260 }
            r7 = r8
            r4.resetStringPosition()     // Catch:{ all -> 0x0260 }
            java.lang.String r8 = com.alibaba.fastjson.JSON.DEFAULT_TYPE_KEY     // Catch:{ all -> 0x0260 }
            r9 = 13
            if (r12 != r8) goto L_0x01e0
            com.alibaba.fastjson.parser.Feature r8 = com.alibaba.fastjson.parser.Feature.DisableSpecialKeyDetect     // Catch:{ all -> 0x01dc }
            boolean r13 = r4.isEnabled((com.alibaba.fastjson.parser.Feature) r8)     // Catch:{ all -> 0x01dc }
            if (r13 != 0) goto L_0x01d9
            r13 = r20
            boolean r8 = com.alibaba.fastjson.parser.Feature.isEnabled(r13, r8)     // Catch:{ all -> 0x022b }
            if (r8 != 0) goto L_0x01e2
            com.alibaba.fastjson.parser.SymbolTable r8 = r16.getSymbolTable()     // Catch:{ all -> 0x022b }
            java.lang.String r8 = r4.scanSymbol(r8, r10)     // Catch:{ all -> 0x022b }
            com.alibaba.fastjson.parser.ParserConfig r10 = r16.getConfig()     // Catch:{ all -> 0x022b }
            java.lang.String r14 = "java.util.HashMap"
            boolean r14 = r8.equals(r14)     // Catch:{ all -> 0x022b }
            if (r14 == 0) goto L_0x0196
            java.lang.Class<java.util.HashMap> r14 = java.util.HashMap.class
            goto L_0x019f
        L_0x0196:
            r14 = 0
            int r15 = r4.getFeatures()     // Catch:{ all -> 0x022b }
            java.lang.Class r14 = r10.checkAutoType(r8, r14, r15)     // Catch:{ all -> 0x022b }
        L_0x019f:
            java.lang.Class<java.util.Map> r15 = java.util.Map.class
            boolean r15 = r15.isAssignableFrom(r14)     // Catch:{ all -> 0x022b }
            if (r15 == 0) goto L_0x01bb
            r4.nextToken(r11)     // Catch:{ all -> 0x022b }
            int r15 = r4.token()     // Catch:{ all -> 0x022b }
            if (r15 != r9) goto L_0x01b8
            r4.nextToken(r11)     // Catch:{ all -> 0x022b }
            r1.setContext(r6)
            return r2
        L_0x01b8:
            r10 = r18
            goto L_0x0222
        L_0x01bb:
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r9 = r10.getDeserializer((java.lang.reflect.Type) r14)     // Catch:{ all -> 0x022b }
            r4.nextToken(r11)     // Catch:{ all -> 0x022b }
            r11 = 2
            r1.setResolveStatus(r11)     // Catch:{ all -> 0x022b }
            if (r6 == 0) goto L_0x01cf
            boolean r11 = r3 instanceof java.lang.Integer     // Catch:{ all -> 0x022b }
            if (r11 != 0) goto L_0x01cf
            r16.popContext()     // Catch:{ all -> 0x022b }
        L_0x01cf:
            java.lang.Object r11 = r9.deserialze(r1, r14, r3)     // Catch:{ all -> 0x022b }
            java.util.Map r11 = (java.util.Map) r11     // Catch:{ all -> 0x022b }
            r1.setContext(r6)
            return r11
        L_0x01d9:
            r13 = r20
            goto L_0x01e2
        L_0x01dc:
            r0 = move-exception
            r13 = r20
            goto L_0x022c
        L_0x01e0:
            r13 = r20
        L_0x01e2:
            r4.nextToken()     // Catch:{ all -> 0x022b }
            if (r0 == 0) goto L_0x01ea
            r1.setContext(r6)     // Catch:{ all -> 0x022b }
        L_0x01ea:
            int r8 = r4.token()     // Catch:{ all -> 0x022b }
            r10 = 8
            if (r8 != r10) goto L_0x01f9
            r8 = 0
            r4.nextToken()     // Catch:{ all -> 0x022b }
            r10 = r18
            goto L_0x01ff
        L_0x01f9:
            r10 = r18
            java.lang.Object r8 = r1.parseObject((java.lang.reflect.Type) r10, (java.lang.Object) r12)     // Catch:{ all -> 0x025e }
        L_0x01ff:
            r2.put(r12, r8)     // Catch:{ all -> 0x025e }
            r1.checkMapResolve(r2, r12)     // Catch:{ all -> 0x025e }
            r1.setContext(r6, r8, r12)     // Catch:{ all -> 0x025e }
            r1.setContext(r6)     // Catch:{ all -> 0x025e }
            int r11 = r4.token()     // Catch:{ all -> 0x025e }
            r14 = 20
            if (r11 == r14) goto L_0x0226
            r14 = 15
            if (r11 != r14) goto L_0x0218
            goto L_0x0226
        L_0x0218:
            if (r11 != r9) goto L_0x0222
            r4.nextToken()     // Catch:{ all -> 0x025e }
            r1.setContext(r6)
            return r2
        L_0x0222:
            int r0 = r0 + 1
            goto L_0x0098
        L_0x0226:
            r1.setContext(r6)
            return r2
        L_0x022b:
            r0 = move-exception
        L_0x022c:
            r10 = r18
            goto L_0x0265
        L_0x022f:
            r10 = r18
            r13 = r20
            com.alibaba.fastjson.JSONException r9 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x025e }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x025e }
            r11.<init>()     // Catch:{ all -> 0x025e }
            r11.append(r8)     // Catch:{ all -> 0x025e }
            int r8 = r4.pos()     // Catch:{ all -> 0x025e }
            r11.append(r8)     // Catch:{ all -> 0x025e }
            java.lang.String r8 = ", actual "
            r11.append(r8)     // Catch:{ all -> 0x025e }
            r11.append(r7)     // Catch:{ all -> 0x025e }
            java.lang.String r8 = r11.toString()     // Catch:{ all -> 0x025e }
            r9.<init>(r8)     // Catch:{ all -> 0x025e }
            throw r9     // Catch:{ all -> 0x025e }
        L_0x0254:
            r10 = r18
            r13 = r20
            com.alibaba.fastjson.JSONException r8 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x025e }
            r8.<init>(r12)     // Catch:{ all -> 0x025e }
            throw r8     // Catch:{ all -> 0x025e }
        L_0x025e:
            r0 = move-exception
            goto L_0x0265
        L_0x0260:
            r0 = move-exception
            r10 = r18
            r13 = r20
        L_0x0265:
            r1.setContext(r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.MapDeserializer.parseMap(com.alibaba.fastjson.parser.DefaultJSONParser, java.util.Map, java.lang.reflect.Type, java.lang.Object, int):java.util.Map");
    }

    public static Object parseMap(DefaultJSONParser parser, Map<Object, Object> map, Type keyType, Type valueType, Object fieldName) {
        Object key;
        JSONLexer lexer = parser.lexer;
        if (lexer.token() == 12 || lexer.token() == 16) {
            ObjectDeserializer keyDeserializer = parser.getConfig().getDeserializer(keyType);
            ObjectDeserializer valueDeserializer = parser.getConfig().getDeserializer(valueType);
            lexer.nextToken(keyDeserializer.getFastMatchToken());
            ParseContext context = parser.getContext();
            while (lexer.token() != 13) {
                try {
                    if (lexer.token() != 4 || !lexer.isRef() || lexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                        if (map.size() == 0 && lexer.token() == 4 && JSON.DEFAULT_TYPE_KEY.equals(lexer.stringVal()) && !lexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                            lexer.nextTokenWithColon(4);
                            lexer.nextToken(16);
                            if (lexer.token() == 13) {
                                lexer.nextToken();
                                return map;
                            }
                            lexer.nextToken(keyDeserializer.getFastMatchToken());
                        }
                        if (lexer.token() != 4 || !(keyDeserializer instanceof JavaBeanDeserializer)) {
                            key = keyDeserializer.deserialze(parser, keyType, (Object) null);
                        } else {
                            String keyStrValue = lexer.stringVal();
                            lexer.nextToken();
                            DefaultJSONParser keyParser = new DefaultJSONParser(keyStrValue, parser.getConfig(), parser.getLexer().getFeatures());
                            keyParser.setDateFormat(parser.getDateFomartPattern());
                            key = keyDeserializer.deserialze(keyParser, keyType, (Object) null);
                        }
                        if (lexer.token() == 17) {
                            lexer.nextToken(valueDeserializer.getFastMatchToken());
                            Object value = valueDeserializer.deserialze(parser, valueType, key);
                            parser.checkMapResolve(map, key);
                            map.put(key, value);
                            if (lexer.token() == 16) {
                                lexer.nextToken(keyDeserializer.getFastMatchToken());
                            }
                        } else {
                            throw new JSONException("syntax error, expect :, actual " + lexer.token());
                        }
                    } else {
                        Object object = null;
                        lexer.nextTokenWithColon(4);
                        if (lexer.token() == 4) {
                            String ref = lexer.stringVal();
                            if ("..".equals(ref)) {
                                object = context.parent.object;
                            } else if ("$".equals(ref)) {
                                ParseContext rootContext = context;
                                while (true) {
                                    ParseContext parseContext = rootContext.parent;
                                    if (parseContext == null) {
                                        break;
                                    }
                                    rootContext = parseContext;
                                }
                                object = rootContext.object;
                            } else {
                                parser.addResolveTask(new DefaultJSONParser.ResolveTask(context, ref));
                                parser.setResolveStatus(1);
                            }
                            lexer.nextToken(13);
                            if (lexer.token() == 13) {
                                lexer.nextToken(16);
                                parser.setContext(context);
                                return object;
                            }
                            throw new JSONException("illegal ref");
                        }
                        throw new JSONException("illegal ref, " + JSONToken.name(lexer.token()));
                    }
                } finally {
                    parser.setContext(context);
                }
            }
            lexer.nextToken(16);
            parser.setContext(context);
            return map;
        }
        throw new JSONException("syntax error, expect {, actual " + lexer.tokenName());
    }

    public Map<Object, Object> createMap(Type type) {
        return createMap(type, JSON.DEFAULT_GENERATE_FEATURE);
    }

    public Map<Object, Object> createMap(Type type, int featrues) {
        if (type == Properties.class) {
            return new Properties();
        }
        if (type == Hashtable.class) {
            return new Hashtable();
        }
        if (type == IdentityHashMap.class) {
            return new IdentityHashMap();
        }
        if (type == SortedMap.class || type == TreeMap.class) {
            return new TreeMap();
        }
        if (type == ConcurrentMap.class || type == ConcurrentHashMap.class) {
            return new ConcurrentHashMap();
        }
        if (type == Map.class) {
            return (Feature.OrderedField.mask & featrues) != 0 ? new LinkedHashMap() : new HashMap();
        }
        if (type == HashMap.class) {
            return new HashMap();
        }
        if (type == LinkedHashMap.class) {
            return new LinkedHashMap();
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            if (EnumMap.class.equals(rawType)) {
                return new EnumMap((Class) parameterizedType.getActualTypeArguments()[0]);
            }
            return createMap(rawType, featrues);
        }
        Class<?> clazz = (Class) type;
        if (clazz.isInterface()) {
            throw new JSONException("unsupport type " + type);
        } else if ("java.util.Collections$UnmodifiableMap".equals(clazz.getName())) {
            return new HashMap();
        } else {
            try {
                return (Map) clazz.newInstance();
            } catch (Exception e) {
                throw new JSONException("unsupport type " + type, e);
            }
        }
    }

    public int getFastMatchToken() {
        return 12;
    }
}
