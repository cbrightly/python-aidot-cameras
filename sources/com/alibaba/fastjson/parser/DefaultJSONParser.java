package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.JSONPathException;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessable;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldTypeResolver;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.ResolveFieldDeserializer;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class DefaultJSONParser implements Closeable {
    public static final int NONE = 0;
    public static final int NeedToResolve = 1;
    public static final int TypeNameRedirect = 2;
    private static final Set<Class<?>> primitiveClasses;
    private String[] autoTypeAccept;
    private boolean autoTypeEnable;
    protected ParserConfig config;
    protected ParseContext context;
    private ParseContext[] contextArray;
    private int contextArrayIndex;
    private DateFormat dateFormat;
    private String dateFormatPattern;
    private List<ExtraProcessor> extraProcessors;
    private List<ExtraTypeProvider> extraTypeProviders;
    protected FieldTypeResolver fieldTypeResolver;
    public final Object input;
    protected transient BeanContext lastBeanContext;
    public final JSONLexer lexer;
    private int objectKeyLevel;
    public int resolveStatus;
    private List<ResolveTask> resolveTaskList;
    public final SymbolTable symbolTable;

    static {
        HashSet hashSet = new HashSet();
        primitiveClasses = hashSet;
        hashSet.addAll(Arrays.asList(new Class[]{Boolean.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, BigInteger.class, BigDecimal.class, String.class}));
    }

    public String getDateFomartPattern() {
        return this.dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        if (this.dateFormat == null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.dateFormatPattern, this.lexer.getLocale());
            this.dateFormat = simpleDateFormat;
            simpleDateFormat.setTimeZone(this.lexer.getTimeZone());
        }
        return this.dateFormat;
    }

    public void setDateFormat(String dateFormat2) {
        this.dateFormatPattern = dateFormat2;
        this.dateFormat = null;
    }

    public void setDateFomrat(DateFormat dateFormat2) {
        setDateFormat(dateFormat2);
    }

    public void setDateFormat(DateFormat dateFormat2) {
        this.dateFormat = dateFormat2;
    }

    public DefaultJSONParser(String input2) {
        this(input2, ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
    }

    public DefaultJSONParser(String input2, ParserConfig config2) {
        this((Object) input2, (JSONLexer) new JSONScanner(input2, JSON.DEFAULT_PARSER_FEATURE), config2);
    }

    public DefaultJSONParser(String input2, ParserConfig config2, int features) {
        this((Object) input2, (JSONLexer) new JSONScanner(input2, features), config2);
    }

    public DefaultJSONParser(char[] input2, int length, ParserConfig config2, int features) {
        this((Object) input2, (JSONLexer) new JSONScanner(input2, length, features), config2);
    }

    public DefaultJSONParser(JSONLexer lexer2) {
        this(lexer2, ParserConfig.getGlobalInstance());
    }

    public DefaultJSONParser(JSONLexer lexer2, ParserConfig config2) {
        this((Object) null, lexer2, config2);
    }

    public DefaultJSONParser(Object input2, JSONLexer lexer2, ParserConfig config2) {
        this.dateFormatPattern = JSON.DEFFAULT_DATE_FORMAT;
        this.contextArrayIndex = 0;
        this.resolveStatus = 0;
        this.extraTypeProviders = null;
        this.extraProcessors = null;
        this.fieldTypeResolver = null;
        this.objectKeyLevel = 0;
        this.autoTypeAccept = null;
        this.lexer = lexer2;
        this.input = input2;
        this.config = config2;
        this.symbolTable = config2.symbolTable;
        int ch = lexer2.getCurrent();
        if (ch == 123) {
            lexer2.next();
            ((JSONLexerBase) lexer2).token = 12;
        } else if (ch == 91) {
            lexer2.next();
            ((JSONLexerBase) lexer2).token = 14;
        } else {
            lexer2.nextToken();
        }
    }

    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public String getInput() {
        Object obj = this.input;
        if (obj instanceof char[]) {
            return new String((char[]) this.input);
        }
        return obj.toString();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v1, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v3, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v7, resolved type: java.lang.Number} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v10, resolved type: java.lang.Number} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v11, resolved type: java.lang.Number} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v12, resolved type: java.lang.Number} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v38, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v38, resolved type: java.util.Date} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v39, resolved type: java.util.Date} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v41, resolved type: java.util.Date} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v48, resolved type: com.alibaba.fastjson.JSONObject} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v49, resolved type: com.alibaba.fastjson.JSONObject} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v50, resolved type: com.alibaba.fastjson.JSONObject} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v77, resolved type: java.util.HashMap} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v79, resolved type: java.util.HashMap} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v80, resolved type: java.util.HashMap} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v81, resolved type: java.lang.Cloneable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v82, resolved type: java.util.Map} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v83, resolved type: java.util.Map} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v84, resolved type: java.util.HashMap} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v9, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v12, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v13, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v15, resolved type: java.lang.String} */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x0290, code lost:
        r14 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x02b0, code lost:
        r5.nextToken(16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x02bb, code lost:
        if (r5.token() != 13) goto L_0x0313;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x02bd, code lost:
        r5.nextToken(16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x02c0, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x02c9, code lost:
        if ((r1.config.getDeserializer((java.lang.reflect.Type) r8) instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) == false) goto L_0x02d2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x02cb, code lost:
        r0 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r2, r8, r1.config);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x02d2, code lost:
        if (r0 != null) goto L_0x0305;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x02d6, code lost:
        if (r8 != java.lang.Cloneable.class) goto L_0x02df;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x02d8, code lost:
        r0 = new java.util.HashMap();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x02e5, code lost:
        if ("java.util.Collections$EmptyMap".equals(r6) == false) goto L_0x02ed;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x02e7, code lost:
        r0 = java.util.Collections.emptyMap();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x02f3, code lost:
        if ("java.util.Collections$UnmodifiableMap".equals(r6) == false) goto L_0x0300;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x02f5, code lost:
        r0 = java.util.Collections.unmodifiableMap(new java.util.HashMap());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x0304, code lost:
        r0 = r8.newInstance();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x0305, code lost:
        setContext(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x0309, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x0313, code lost:
        setResolveStatus(2);
        r0 = r1.context;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x0319, code lost:
        if (r0 == null) goto L_0x032a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x031b, code lost:
        if (r3 == null) goto L_0x032a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x031f, code lost:
        if ((r3 instanceof java.lang.Integer) != false) goto L_0x032a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x0325, code lost:
        if ((r0.fieldName instanceof java.lang.Integer) != false) goto L_0x032a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:0x0327, code lost:
        popContext();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x032e, code lost:
        if (r24.size() <= 0) goto L_0x0342;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:187:0x0330, code lost:
        r0 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r2, r8, r1.config);
        setResolveStatus(0);
        parseObject(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:0x033d, code lost:
        setContext(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:189:0x0341, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:191:?, code lost:
        r0 = r1.config.getDeserializer((java.lang.reflect.Type) r8);
        r4 = r0.getClass();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:192:0x0352, code lost:
        if (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.class.isAssignableFrom(r4) == false) goto L_0x0361;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:194:0x0356, code lost:
        if (r4 == com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.class) goto L_0x0361;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:196:0x035a, code lost:
        if (r4 == com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer.class) goto L_0x0361;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:197:0x035c, code lost:
        setResolveStatus(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:199:0x0363, code lost:
        if ((r0 instanceof com.alibaba.fastjson.parser.deserializer.MapDeserializer) == false) goto L_0x0369;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:200:0x0365, code lost:
        setResolveStatus(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:201:0x0369, code lost:
        r14 = r0.deserialze(r1, r8, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:202:0x036d, code lost:
        setContext(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:203:0x0371, code lost:
        return r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:223:0x03b9, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:224:0x03c0, code lost:
        if ("@".equals(r6) == false) goto L_0x03dc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:225:0x03c2, code lost:
        r4 = r1.context;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:226:0x03c4, code lost:
        if (r4 == null) goto L_0x043a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:227:0x03c6, code lost:
        r8 = r4.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:228:0x03cb, code lost:
        if ((r8 instanceof java.lang.Object[]) != false) goto L_0x03da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:230:0x03cf, code lost:
        if ((r8 instanceof java.util.Collection) == false) goto L_0x03d2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:232:0x03d2, code lost:
        r14 = r4.parent;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:233:0x03d4, code lost:
        if (r14 == null) goto L_0x03db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:234:0x03d6, code lost:
        r0 = r14.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:235:0x03da, code lost:
        r0 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:238:0x03e2, code lost:
        if ("..".equals(r6) == false) goto L_0x03f7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:239:0x03e4, code lost:
        r4 = r13.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:240:0x03e6, code lost:
        if (r4 == null) goto L_0x03ea;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:241:0x03e8, code lost:
        r0 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:242:0x03ea, code lost:
        addResolveTask(new com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask(r13, r6));
        setResolveStatus(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:244:0x03fd, code lost:
        if ("$".equals(r6) == false) goto L_0x0419;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:245:0x03ff, code lost:
        r4 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:246:0x0400, code lost:
        r8 = r4.parent;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:247:0x0402, code lost:
        if (r8 == null) goto L_0x0406;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:248:0x0404, code lost:
        r4 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:249:0x0406, code lost:
        r8 = r4.object;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:250:0x0408, code lost:
        if (r8 == null) goto L_0x040c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:251:0x040a, code lost:
        r0 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:252:0x040c, code lost:
        addResolveTask(new com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask(r4, r6));
        setResolveStatus(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:255:0x0421, code lost:
        if (com.alibaba.fastjson.JSONPath.compile(r6).isRef() == false) goto L_0x0430;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:256:0x0423, code lost:
        addResolveTask(new com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask(r13, r6));
        setResolveStatus(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:257:0x0430, code lost:
        r0 = new com.alibaba.fastjson.JSONObject().fluentPut("$ref", r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:259:0x0440, code lost:
        if (r5.token() != 13) goto L_0x044c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:260:0x0442, code lost:
        r5.nextToken(16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:261:0x0447, code lost:
        setContext(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:262:0x044b, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:265:0x0467, code lost:
        throw new com.alibaba.fastjson.JSONException("syntax error, " + r5.info());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:310:0x0524, code lost:
        if (r0 != '}') goto L_0x0537;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:311:0x0526, code lost:
        r5.next();
        r5.resetStringPosition();
        r5.nextToken();
        setContext(r8, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:312:0x0532, code lost:
        setContext(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:313:0x0536, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:316:0x0558, code lost:
        throw new com.alibaba.fastjson.JSONException("syntax error, position at " + r5.pos() + ", name " + r10);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:431:0x019a A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0198 A[Catch:{ Exception -> 0x030a, NumberFormatException -> 0x01b3, all -> 0x070f }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object parseObject(java.util.Map r24, java.lang.Object r25) {
        /*
            r23 = this;
            r1 = r23
            r2 = r24
            r3 = r25
            java.lang.String r4 = "parse number key error"
            com.alibaba.fastjson.parser.JSONLexer r5 = r1.lexer
            int r0 = r5.token()
            r6 = 0
            r7 = 8
            if (r0 != r7) goto L_0x0017
            r5.nextToken()
            return r6
        L_0x0017:
            int r0 = r5.token()
            r7 = 13
            if (r0 != r7) goto L_0x0023
            r5.nextToken()
            return r2
        L_0x0023:
            int r0 = r5.token()
            r8 = 4
            if (r0 != r8) goto L_0x0038
            java.lang.String r0 = r5.stringVal()
            int r0 = r0.length()
            if (r0 != 0) goto L_0x0038
            r5.nextToken()
            return r2
        L_0x0038:
            int r0 = r5.token()
            r9 = 12
            r10 = 16
            if (r0 == r9) goto L_0x0071
            int r0 = r5.token()
            if (r0 != r10) goto L_0x0049
            goto L_0x0071
        L_0x0049:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "syntax error, expect {, actual "
            r4.append(r6)
            java.lang.String r6 = r5.tokenName()
            r4.append(r6)
            java.lang.String r6 = ", "
            r4.append(r6)
            java.lang.String r6 = r5.info()
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            r0.<init>(r4)
            throw r0
        L_0x0071:
            com.alibaba.fastjson.parser.ParseContext r9 = r1.context
            boolean r0 = r2 instanceof com.alibaba.fastjson.JSONObject     // Catch:{ all -> 0x0712 }
            r11 = r0
            if (r11 == 0) goto L_0x0080
            r0 = r2
            com.alibaba.fastjson.JSONObject r0 = (com.alibaba.fastjson.JSONObject) r0     // Catch:{ all -> 0x0712 }
            java.util.Map r0 = r0.getInnerMap()     // Catch:{ all -> 0x0712 }
            goto L_0x0081
        L_0x0080:
            r0 = r2
        L_0x0081:
            r12 = r0
            r0 = 0
            r13 = r9
            r9 = r0
        L_0x0085:
            r5.skipWhitespace()     // Catch:{ all -> 0x070f }
            char r0 = r5.getCurrent()     // Catch:{ all -> 0x070f }
            com.alibaba.fastjson.parser.Feature r14 = com.alibaba.fastjson.parser.Feature.AllowArbitraryCommas     // Catch:{ all -> 0x070f }
            boolean r14 = r5.isEnabled((com.alibaba.fastjson.parser.Feature) r14)     // Catch:{ all -> 0x070f }
            r15 = 44
            if (r14 == 0) goto L_0x00a6
        L_0x0096:
            if (r0 != r15) goto L_0x00a4
            r5.next()     // Catch:{ all -> 0x070f }
            r5.skipWhitespace()     // Catch:{ all -> 0x070f }
            char r14 = r5.getCurrent()     // Catch:{ all -> 0x070f }
            r0 = r14
            goto L_0x0096
        L_0x00a4:
            r14 = r0
            goto L_0x00a7
        L_0x00a6:
            r14 = r0
        L_0x00a7:
            r16 = 0
            java.lang.String r6 = ", name "
            java.lang.String r8 = "expect ':' at "
            r0 = 58
            r7 = 34
            java.lang.String r15 = "syntax error"
            if (r14 != r7) goto L_0x00e8
            com.alibaba.fastjson.parser.SymbolTable r10 = r1.symbolTable     // Catch:{ all -> 0x070f }
            java.lang.String r10 = r5.scanSymbol(r10, r7)     // Catch:{ all -> 0x070f }
            r5.skipWhitespace()     // Catch:{ all -> 0x070f }
            char r22 = r5.getCurrent()     // Catch:{ all -> 0x070f }
            r14 = r22
            if (r14 != r0) goto L_0x00c9
            goto L_0x022b
        L_0x00c9:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x070f }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x070f }
            r4.<init>()     // Catch:{ all -> 0x070f }
            r4.append(r8)     // Catch:{ all -> 0x070f }
            int r7 = r5.pos()     // Catch:{ all -> 0x070f }
            r4.append(r7)     // Catch:{ all -> 0x070f }
            r4.append(r6)     // Catch:{ all -> 0x070f }
            r4.append(r10)     // Catch:{ all -> 0x070f }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x070f }
            r0.<init>(r4)     // Catch:{ all -> 0x070f }
            throw r0     // Catch:{ all -> 0x070f }
        L_0x00e8:
            r10 = 125(0x7d, float:1.75E-43)
            if (r14 != r10) goto L_0x0113
            r5.next()     // Catch:{ all -> 0x070f }
            r5.resetStringPosition()     // Catch:{ all -> 0x070f }
            r5.nextToken()     // Catch:{ all -> 0x070f }
            if (r9 != 0) goto L_0x010e
            com.alibaba.fastjson.parser.ParseContext r0 = r1.context     // Catch:{ all -> 0x070f }
            if (r0 == 0) goto L_0x0105
            java.lang.Object r4 = r0.fieldName     // Catch:{ all -> 0x070f }
            if (r3 != r4) goto L_0x0105
            java.lang.Object r4 = r0.object     // Catch:{ all -> 0x070f }
            if (r2 != r4) goto L_0x0105
            r13 = r0
            goto L_0x010e
        L_0x0105:
            com.alibaba.fastjson.parser.ParseContext r0 = r23.setContext(r24, r25)     // Catch:{ all -> 0x070f }
            if (r13 != 0) goto L_0x010d
            r4 = r0
            r13 = r4
        L_0x010d:
            r9 = 1
        L_0x010e:
            r1.setContext(r13)
            return r2
        L_0x0113:
            r10 = 39
            if (r14 != r10) goto L_0x0151
            com.alibaba.fastjson.parser.Feature r7 = com.alibaba.fastjson.parser.Feature.AllowSingleQuotes     // Catch:{ all -> 0x070f }
            boolean r7 = r5.isEnabled((com.alibaba.fastjson.parser.Feature) r7)     // Catch:{ all -> 0x070f }
            if (r7 == 0) goto L_0x014b
            com.alibaba.fastjson.parser.SymbolTable r7 = r1.symbolTable     // Catch:{ all -> 0x070f }
            java.lang.String r7 = r5.scanSymbol(r7, r10)     // Catch:{ all -> 0x070f }
            r10 = r7
            r5.skipWhitespace()     // Catch:{ all -> 0x070f }
            char r7 = r5.getCurrent()     // Catch:{ all -> 0x070f }
            r14 = r7
            if (r14 != r0) goto L_0x0132
            goto L_0x022b
        L_0x0132:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x070f }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x070f }
            r4.<init>()     // Catch:{ all -> 0x070f }
            r4.append(r8)     // Catch:{ all -> 0x070f }
            int r6 = r5.pos()     // Catch:{ all -> 0x070f }
            r4.append(r6)     // Catch:{ all -> 0x070f }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x070f }
            r0.<init>(r4)     // Catch:{ all -> 0x070f }
            throw r0     // Catch:{ all -> 0x070f }
        L_0x014b:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x070f }
            r0.<init>(r15)     // Catch:{ all -> 0x070f }
            throw r0     // Catch:{ all -> 0x070f }
        L_0x0151:
            r7 = 26
            if (r14 == r7) goto L_0x0709
            r7 = 44
            if (r14 == r7) goto L_0x0703
            r7 = 48
            if (r14 < r7) goto L_0x0161
            r7 = 57
            if (r14 <= r7) goto L_0x0165
        L_0x0161:
            r7 = 45
            if (r14 != r7) goto L_0x01cd
        L_0x0165:
            r5.resetStringPosition()     // Catch:{ all -> 0x070f }
            r5.scanNumber()     // Catch:{ all -> 0x070f }
            int r7 = r5.token()     // Catch:{ NumberFormatException -> 0x01b3 }
            r8 = 2
            if (r7 != r8) goto L_0x0177
            java.lang.Number r7 = r5.integerValue()     // Catch:{ NumberFormatException -> 0x01b3 }
            goto L_0x017d
        L_0x0177:
            r7 = 1
            java.lang.Number r8 = r5.decimalValue(r7)     // Catch:{ NumberFormatException -> 0x01b3 }
            r7 = r8
        L_0x017d:
            com.alibaba.fastjson.parser.Feature r8 = com.alibaba.fastjson.parser.Feature.NonStringKeyAsString     // Catch:{ NumberFormatException -> 0x01b3 }
            boolean r8 = r5.isEnabled((com.alibaba.fastjson.parser.Feature) r8)     // Catch:{ NumberFormatException -> 0x01b3 }
            if (r8 != 0) goto L_0x018a
            if (r11 == 0) goto L_0x0188
            goto L_0x018a
        L_0x0188:
            r10 = r7
            goto L_0x0190
        L_0x018a:
            java.lang.String r8 = r7.toString()     // Catch:{ NumberFormatException -> 0x01b3 }
            r7 = r8
            r10 = r7
        L_0x0190:
            char r7 = r5.getCurrent()     // Catch:{ all -> 0x070f }
            r14 = r7
            if (r14 != r0) goto L_0x019a
            goto L_0x022b
        L_0x019a:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x070f }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x070f }
            r6.<init>()     // Catch:{ all -> 0x070f }
            r6.append(r4)     // Catch:{ all -> 0x070f }
            java.lang.String r4 = r5.info()     // Catch:{ all -> 0x070f }
            r6.append(r4)     // Catch:{ all -> 0x070f }
            java.lang.String r4 = r6.toString()     // Catch:{ all -> 0x070f }
            r0.<init>(r4)     // Catch:{ all -> 0x070f }
            throw r0     // Catch:{ all -> 0x070f }
        L_0x01b3:
            r0 = move-exception
            com.alibaba.fastjson.JSONException r6 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x070f }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x070f }
            r7.<init>()     // Catch:{ all -> 0x070f }
            r7.append(r4)     // Catch:{ all -> 0x070f }
            java.lang.String r4 = r5.info()     // Catch:{ all -> 0x070f }
            r7.append(r4)     // Catch:{ all -> 0x070f }
            java.lang.String r4 = r7.toString()     // Catch:{ all -> 0x070f }
            r6.<init>(r4)     // Catch:{ all -> 0x070f }
            throw r6     // Catch:{ all -> 0x070f }
        L_0x01cd:
            r7 = 123(0x7b, float:1.72E-43)
            if (r14 == r7) goto L_0x0217
            r7 = 91
            if (r14 != r7) goto L_0x01d6
            goto L_0x0217
        L_0x01d6:
            com.alibaba.fastjson.parser.Feature r7 = com.alibaba.fastjson.parser.Feature.AllowUnQuotedFieldNames     // Catch:{ all -> 0x070f }
            boolean r7 = r5.isEnabled((com.alibaba.fastjson.parser.Feature) r7)     // Catch:{ all -> 0x070f }
            if (r7 == 0) goto L_0x0211
            com.alibaba.fastjson.parser.SymbolTable r7 = r1.symbolTable     // Catch:{ all -> 0x070f }
            java.lang.String r7 = r5.scanSymbolUnQuoted(r7)     // Catch:{ all -> 0x070f }
            r10 = r7
            r5.skipWhitespace()     // Catch:{ all -> 0x070f }
            char r7 = r5.getCurrent()     // Catch:{ all -> 0x070f }
            r14 = r7
            if (r14 != r0) goto L_0x01f0
            goto L_0x022b
        L_0x01f0:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x070f }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x070f }
            r4.<init>()     // Catch:{ all -> 0x070f }
            r4.append(r8)     // Catch:{ all -> 0x070f }
            int r6 = r5.pos()     // Catch:{ all -> 0x070f }
            r4.append(r6)     // Catch:{ all -> 0x070f }
            java.lang.String r6 = ", actual "
            r4.append(r6)     // Catch:{ all -> 0x070f }
            r4.append(r14)     // Catch:{ all -> 0x070f }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x070f }
            r0.<init>(r4)     // Catch:{ all -> 0x070f }
            throw r0     // Catch:{ all -> 0x070f }
        L_0x0211:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x070f }
            r0.<init>(r15)     // Catch:{ all -> 0x070f }
            throw r0     // Catch:{ all -> 0x070f }
        L_0x0217:
            int r0 = r1.objectKeyLevel     // Catch:{ all -> 0x070f }
            int r7 = r0 + 1
            r1.objectKeyLevel = r7     // Catch:{ all -> 0x070f }
            r7 = 512(0x200, float:7.175E-43)
            if (r0 > r7) goto L_0x06fb
            r5.nextToken()     // Catch:{ all -> 0x070f }
            java.lang.Object r0 = r23.parse()     // Catch:{ all -> 0x070f }
            r10 = r0
            r16 = 1
        L_0x022b:
            if (r16 != 0) goto L_0x0233
            r5.next()     // Catch:{ all -> 0x070f }
            r5.skipWhitespace()     // Catch:{ all -> 0x070f }
        L_0x0233:
            char r0 = r5.getCurrent()     // Catch:{ all -> 0x070f }
            r7 = r0
            r5.resetStringPosition()     // Catch:{ all -> 0x070f }
            java.lang.String r0 = com.alibaba.fastjson.JSON.DEFAULT_TYPE_KEY     // Catch:{ all -> 0x070f }
            if (r10 != r0) goto L_0x0375
            com.alibaba.fastjson.parser.Feature r0 = com.alibaba.fastjson.parser.Feature.DisableSpecialKeyDetect     // Catch:{ all -> 0x070f }
            boolean r0 = r5.isEnabled((com.alibaba.fastjson.parser.Feature) r0)     // Catch:{ all -> 0x070f }
            if (r0 != 0) goto L_0x0372
            com.alibaba.fastjson.parser.SymbolTable r0 = r1.symbolTable     // Catch:{ all -> 0x070f }
            r6 = 34
            java.lang.String r0 = r5.scanSymbol(r0, r6)     // Catch:{ all -> 0x070f }
            r6 = r0
            com.alibaba.fastjson.parser.Feature r0 = com.alibaba.fastjson.parser.Feature.IgnoreAutoType     // Catch:{ all -> 0x070f }
            boolean r0 = r5.isEnabled((com.alibaba.fastjson.parser.Feature) r0)     // Catch:{ all -> 0x070f }
            if (r0 == 0) goto L_0x025b
            r0 = 4
            goto L_0x03b1
        L_0x025b:
            r0 = 0
            if (r2 == 0) goto L_0x0274
            java.lang.Class r14 = r24.getClass()     // Catch:{ all -> 0x070f }
            java.lang.String r14 = r14.getName()     // Catch:{ all -> 0x070f }
            boolean r14 = r14.equals(r6)     // Catch:{ all -> 0x070f }
            if (r14 == 0) goto L_0x0274
            java.lang.Class r14 = r24.getClass()     // Catch:{ all -> 0x070f }
            r0 = r14
            r8 = r0
            r15 = 0
            goto L_0x02a6
        L_0x0274:
            r14 = 1
            r15 = 0
        L_0x0276:
            int r8 = r6.length()     // Catch:{ all -> 0x070f }
            if (r15 >= r8) goto L_0x0292
            char r8 = r6.charAt(r15)     // Catch:{ all -> 0x070f }
            r18 = r0
            r0 = 48
            if (r8 < r0) goto L_0x0290
            r0 = 57
            if (r8 <= r0) goto L_0x028b
            goto L_0x0290
        L_0x028b:
            int r15 = r15 + 1
            r0 = r18
            goto L_0x0276
        L_0x0290:
            r14 = 0
            goto L_0x0294
        L_0x0292:
            r18 = r0
        L_0x0294:
            if (r14 != 0) goto L_0x02a3
            com.alibaba.fastjson.parser.ParserConfig r0 = r1.config     // Catch:{ all -> 0x070f }
            int r8 = r5.getFeatures()     // Catch:{ all -> 0x070f }
            r15 = 0
            java.lang.Class r0 = r0.checkAutoType(r6, r15, r8)     // Catch:{ all -> 0x070f }
            r8 = r0
            goto L_0x02a6
        L_0x02a3:
            r15 = 0
            r8 = r18
        L_0x02a6:
            if (r8 != 0) goto L_0x02b0
            java.lang.String r0 = com.alibaba.fastjson.JSON.DEFAULT_TYPE_KEY     // Catch:{ all -> 0x070f }
            r12.put(r0, r6)     // Catch:{ all -> 0x070f }
            r0 = 4
            goto L_0x03b1
        L_0x02b0:
            r0 = 16
            r5.nextToken(r0)     // Catch:{ all -> 0x070f }
            int r4 = r5.token()     // Catch:{ all -> 0x070f }
            r14 = 13
            if (r4 != r14) goto L_0x0313
            r5.nextToken(r0)     // Catch:{ all -> 0x070f }
            r0 = 0
            com.alibaba.fastjson.parser.ParserConfig r4 = r1.config     // Catch:{ Exception -> 0x030a }
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r4 = r4.getDeserializer((java.lang.reflect.Type) r8)     // Catch:{ Exception -> 0x030a }
            boolean r14 = r4 instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer     // Catch:{ Exception -> 0x030a }
            if (r14 == 0) goto L_0x02d2
            com.alibaba.fastjson.parser.ParserConfig r14 = r1.config     // Catch:{ Exception -> 0x030a }
            java.lang.Object r14 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r2, r8, (com.alibaba.fastjson.parser.ParserConfig) r14)     // Catch:{ Exception -> 0x030a }
            r0 = r14
        L_0x02d2:
            if (r0 != 0) goto L_0x0305
            java.lang.Class<java.lang.Cloneable> r14 = java.lang.Cloneable.class
            if (r8 != r14) goto L_0x02df
            java.util.HashMap r14 = new java.util.HashMap     // Catch:{ Exception -> 0x030a }
            r14.<init>()     // Catch:{ Exception -> 0x030a }
            r0 = r14
            goto L_0x0305
        L_0x02df:
            java.lang.String r14 = "java.util.Collections$EmptyMap"
            boolean r14 = r14.equals(r6)     // Catch:{ Exception -> 0x030a }
            if (r14 == 0) goto L_0x02ed
            java.util.Map r14 = java.util.Collections.emptyMap()     // Catch:{ Exception -> 0x030a }
            r0 = r14
            goto L_0x0305
        L_0x02ed:
            java.lang.String r14 = "java.util.Collections$UnmodifiableMap"
            boolean r14 = r14.equals(r6)     // Catch:{ Exception -> 0x030a }
            if (r14 == 0) goto L_0x0300
            java.util.HashMap r14 = new java.util.HashMap     // Catch:{ Exception -> 0x030a }
            r14.<init>()     // Catch:{ Exception -> 0x030a }
            java.util.Map r14 = java.util.Collections.unmodifiableMap(r14)     // Catch:{ Exception -> 0x030a }
            r0 = r14
            goto L_0x0305
        L_0x0300:
            java.lang.Object r14 = r8.newInstance()     // Catch:{ Exception -> 0x030a }
            r0 = r14
        L_0x0305:
            r1.setContext(r13)
            return r0
        L_0x030a:
            r0 = move-exception
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x070f }
            java.lang.String r14 = "create instance error"
            r4.<init>(r14, r0)     // Catch:{ all -> 0x070f }
            throw r4     // Catch:{ all -> 0x070f }
        L_0x0313:
            r0 = 2
            r1.setResolveStatus(r0)     // Catch:{ all -> 0x070f }
            com.alibaba.fastjson.parser.ParseContext r0 = r1.context     // Catch:{ all -> 0x070f }
            if (r0 == 0) goto L_0x032a
            if (r3 == 0) goto L_0x032a
            boolean r4 = r3 instanceof java.lang.Integer     // Catch:{ all -> 0x070f }
            if (r4 != 0) goto L_0x032a
            java.lang.Object r0 = r0.fieldName     // Catch:{ all -> 0x070f }
            boolean r0 = r0 instanceof java.lang.Integer     // Catch:{ all -> 0x070f }
            if (r0 != 0) goto L_0x032a
            r23.popContext()     // Catch:{ all -> 0x070f }
        L_0x032a:
            int r0 = r24.size()     // Catch:{ all -> 0x070f }
            if (r0 <= 0) goto L_0x0342
            com.alibaba.fastjson.parser.ParserConfig r0 = r1.config     // Catch:{ all -> 0x070f }
            java.lang.Object r0 = com.alibaba.fastjson.util.TypeUtils.cast((java.lang.Object) r2, r8, (com.alibaba.fastjson.parser.ParserConfig) r0)     // Catch:{ all -> 0x070f }
            r4 = 0
            r1.setResolveStatus(r4)     // Catch:{ all -> 0x070f }
            r1.parseObject((java.lang.Object) r0)     // Catch:{ all -> 0x070f }
            r1.setContext(r13)
            return r0
        L_0x0342:
            com.alibaba.fastjson.parser.ParserConfig r0 = r1.config     // Catch:{ all -> 0x070f }
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r0 = r0.getDeserializer((java.lang.reflect.Type) r8)     // Catch:{ all -> 0x070f }
            java.lang.Class r4 = r0.getClass()     // Catch:{ all -> 0x070f }
            java.lang.Class<com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer> r14 = com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.class
            boolean r14 = r14.isAssignableFrom(r4)     // Catch:{ all -> 0x070f }
            if (r14 == 0) goto L_0x0361
            java.lang.Class<com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer> r14 = com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.class
            if (r4 == r14) goto L_0x0361
            java.lang.Class<com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer> r14 = com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer.class
            if (r4 == r14) goto L_0x0361
            r14 = 0
            r1.setResolveStatus(r14)     // Catch:{ all -> 0x070f }
            goto L_0x0369
        L_0x0361:
            boolean r14 = r0 instanceof com.alibaba.fastjson.parser.deserializer.MapDeserializer     // Catch:{ all -> 0x070f }
            if (r14 == 0) goto L_0x0369
            r14 = 0
            r1.setResolveStatus(r14)     // Catch:{ all -> 0x070f }
        L_0x0369:
            java.lang.Object r14 = r0.deserialze(r1, r8, r3)     // Catch:{ all -> 0x070f }
            r1.setContext(r13)
            return r14
        L_0x0372:
            r0 = 0
            r14 = 0
            goto L_0x0377
        L_0x0375:
            r0 = 0
            r14 = 0
        L_0x0377:
            java.lang.String r8 = "$ref"
            if (r10 != r8) goto L_0x048a
            if (r13 == 0) goto L_0x048a
            if (r2 == 0) goto L_0x038a
            int r17 = r24.size()     // Catch:{ all -> 0x070f }
            if (r17 != 0) goto L_0x0386
            goto L_0x038a
        L_0x0386:
            r0 = 4
            r8 = 1
            goto L_0x048c
        L_0x038a:
            com.alibaba.fastjson.parser.Feature r0 = com.alibaba.fastjson.parser.Feature.DisableSpecialKeyDetect     // Catch:{ all -> 0x070f }
            boolean r0 = r5.isEnabled((com.alibaba.fastjson.parser.Feature) r0)     // Catch:{ all -> 0x070f }
            if (r0 != 0) goto L_0x0487
            r0 = 4
            r5.nextToken(r0)     // Catch:{ all -> 0x070f }
            int r6 = r5.token()     // Catch:{ all -> 0x070f }
            if (r6 != r0) goto L_0x0468
            java.lang.String r6 = r5.stringVal()     // Catch:{ all -> 0x070f }
            r14 = 13
            r5.nextToken(r14)     // Catch:{ all -> 0x070f }
            int r14 = r5.token()     // Catch:{ all -> 0x070f }
            r15 = 16
            if (r14 != r15) goto L_0x03b9
            r12.put(r10, r6)     // Catch:{ all -> 0x070f }
        L_0x03b1:
            r8 = r0
            r6 = 0
            r7 = 13
            r10 = 16
            goto L_0x0085
        L_0x03b9:
            r0 = 0
            java.lang.String r4 = "@"
            boolean r4 = r4.equals(r6)     // Catch:{ all -> 0x070f }
            if (r4 == 0) goto L_0x03dc
            com.alibaba.fastjson.parser.ParseContext r4 = r1.context     // Catch:{ all -> 0x070f }
            if (r4 == 0) goto L_0x043a
            java.lang.Object r8 = r4.object     // Catch:{ all -> 0x070f }
            boolean r14 = r8 instanceof java.lang.Object[]     // Catch:{ all -> 0x070f }
            if (r14 != 0) goto L_0x03da
            boolean r14 = r8 instanceof java.util.Collection     // Catch:{ all -> 0x070f }
            if (r14 == 0) goto L_0x03d2
            goto L_0x03da
        L_0x03d2:
            com.alibaba.fastjson.parser.ParseContext r14 = r4.parent     // Catch:{ all -> 0x070f }
            if (r14 == 0) goto L_0x03db
            java.lang.Object r14 = r14.object     // Catch:{ all -> 0x070f }
            r0 = r14
            goto L_0x03db
        L_0x03da:
            r0 = r8
        L_0x03db:
            goto L_0x043a
        L_0x03dc:
            java.lang.String r4 = ".."
            boolean r4 = r4.equals(r6)     // Catch:{ all -> 0x070f }
            if (r4 == 0) goto L_0x03f7
            java.lang.Object r4 = r13.object     // Catch:{ all -> 0x070f }
            if (r4 == 0) goto L_0x03ea
            r0 = r4
            goto L_0x043a
        L_0x03ea:
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r4 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x070f }
            r4.<init>(r13, r6)     // Catch:{ all -> 0x070f }
            r1.addResolveTask(r4)     // Catch:{ all -> 0x070f }
            r4 = 1
            r1.setResolveStatus(r4)     // Catch:{ all -> 0x070f }
            goto L_0x043a
        L_0x03f7:
            java.lang.String r4 = "$"
            boolean r4 = r4.equals(r6)     // Catch:{ all -> 0x070f }
            if (r4 == 0) goto L_0x0419
            r4 = r13
        L_0x0400:
            com.alibaba.fastjson.parser.ParseContext r8 = r4.parent     // Catch:{ all -> 0x070f }
            if (r8 == 0) goto L_0x0406
            r4 = r8
            goto L_0x0400
        L_0x0406:
            java.lang.Object r8 = r4.object     // Catch:{ all -> 0x070f }
            if (r8 == 0) goto L_0x040c
            r0 = r8
            goto L_0x0418
        L_0x040c:
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r8 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x070f }
            r8.<init>(r4, r6)     // Catch:{ all -> 0x070f }
            r1.addResolveTask(r8)     // Catch:{ all -> 0x070f }
            r8 = 1
            r1.setResolveStatus(r8)     // Catch:{ all -> 0x070f }
        L_0x0418:
            goto L_0x043a
        L_0x0419:
            com.alibaba.fastjson.JSONPath r4 = com.alibaba.fastjson.JSONPath.compile(r6)     // Catch:{ all -> 0x070f }
            boolean r14 = r4.isRef()     // Catch:{ all -> 0x070f }
            if (r14 == 0) goto L_0x0430
            com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask r8 = new com.alibaba.fastjson.parser.DefaultJSONParser$ResolveTask     // Catch:{ all -> 0x070f }
            r8.<init>(r13, r6)     // Catch:{ all -> 0x070f }
            r1.addResolveTask(r8)     // Catch:{ all -> 0x070f }
            r8 = 1
            r1.setResolveStatus(r8)     // Catch:{ all -> 0x070f }
            goto L_0x043a
        L_0x0430:
            com.alibaba.fastjson.JSONObject r14 = new com.alibaba.fastjson.JSONObject     // Catch:{ all -> 0x070f }
            r14.<init>()     // Catch:{ all -> 0x070f }
            com.alibaba.fastjson.JSONObject r8 = r14.fluentPut(r8, r6)     // Catch:{ all -> 0x070f }
            r0 = r8
        L_0x043a:
            int r4 = r5.token()     // Catch:{ all -> 0x070f }
            r8 = 13
            if (r4 != r8) goto L_0x044c
            r4 = 16
            r5.nextToken(r4)     // Catch:{ all -> 0x070f }
            r1.setContext(r13)
            return r0
        L_0x044c:
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x070f }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x070f }
            r8.<init>()     // Catch:{ all -> 0x070f }
            java.lang.String r14 = "syntax error, "
            r8.append(r14)     // Catch:{ all -> 0x070f }
            java.lang.String r14 = r5.info()     // Catch:{ all -> 0x070f }
            r8.append(r14)     // Catch:{ all -> 0x070f }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x070f }
            r4.<init>(r8)     // Catch:{ all -> 0x070f }
            throw r4     // Catch:{ all -> 0x070f }
        L_0x0468:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x070f }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x070f }
            r4.<init>()     // Catch:{ all -> 0x070f }
            java.lang.String r6 = "illegal ref, "
            r4.append(r6)     // Catch:{ all -> 0x070f }
            int r6 = r5.token()     // Catch:{ all -> 0x070f }
            java.lang.String r6 = com.alibaba.fastjson.parser.JSONToken.name(r6)     // Catch:{ all -> 0x070f }
            r4.append(r6)     // Catch:{ all -> 0x070f }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x070f }
            r0.<init>(r4)     // Catch:{ all -> 0x070f }
            throw r0     // Catch:{ all -> 0x070f }
        L_0x0487:
            r0 = 4
            r8 = 1
            goto L_0x048c
        L_0x048a:
            r0 = 4
            r8 = 1
        L_0x048c:
            if (r9 != 0) goto L_0x04a5
            com.alibaba.fastjson.parser.ParseContext r0 = r1.context     // Catch:{ all -> 0x070f }
            if (r0 == 0) goto L_0x049c
            java.lang.Object r8 = r0.fieldName     // Catch:{ all -> 0x070f }
            if (r3 != r8) goto L_0x049c
            java.lang.Object r8 = r0.object     // Catch:{ all -> 0x070f }
            if (r2 != r8) goto L_0x049c
            r13 = r0
            goto L_0x04a5
        L_0x049c:
            com.alibaba.fastjson.parser.ParseContext r0 = r23.setContext(r24, r25)     // Catch:{ all -> 0x070f }
            if (r13 != 0) goto L_0x04a3
            r13 = r0
        L_0x04a3:
            r8 = 1
            r9 = r8
        L_0x04a5:
            java.lang.Class r0 = r24.getClass()     // Catch:{ all -> 0x070f }
            java.lang.Class<com.alibaba.fastjson.JSONObject> r8 = com.alibaba.fastjson.JSONObject.class
            if (r0 != r8) goto L_0x04b2
            if (r10 != 0) goto L_0x04b2
            java.lang.String r0 = "null"
            r10 = r0
        L_0x04b2:
            r0 = 34
            if (r7 != r0) goto L_0x04e2
            r5.scanString()     // Catch:{ all -> 0x070f }
            java.lang.String r0 = r5.stringVal()     // Catch:{ all -> 0x070f }
            r8 = r0
            com.alibaba.fastjson.parser.Feature r14 = com.alibaba.fastjson.parser.Feature.AllowISO8601DateFormat     // Catch:{ all -> 0x070f }
            boolean r14 = r5.isEnabled((com.alibaba.fastjson.parser.Feature) r14)     // Catch:{ all -> 0x070f }
            if (r14 == 0) goto L_0x04dd
            com.alibaba.fastjson.parser.JSONScanner r14 = new com.alibaba.fastjson.parser.JSONScanner     // Catch:{ all -> 0x070f }
            r14.<init>(r0)     // Catch:{ all -> 0x070f }
            boolean r15 = r14.scanISO8601DateIfMatch()     // Catch:{ all -> 0x070f }
            if (r15 == 0) goto L_0x04da
            java.util.Calendar r15 = r14.getCalendar()     // Catch:{ all -> 0x070f }
            java.util.Date r15 = r15.getTime()     // Catch:{ all -> 0x070f }
            r8 = r15
        L_0x04da:
            r14.close()     // Catch:{ all -> 0x070f }
        L_0x04dd:
            r12.put(r10, r8)     // Catch:{ all -> 0x070f }
            goto L_0x050c
        L_0x04e2:
            r0 = 48
            if (r7 < r0) goto L_0x04ea
            r0 = 57
            if (r7 <= r0) goto L_0x04ee
        L_0x04ea:
            r0 = 45
            if (r7 != r0) goto L_0x0559
        L_0x04ee:
            r5.scanNumber()     // Catch:{ all -> 0x070f }
            int r0 = r5.token()     // Catch:{ all -> 0x070f }
            r8 = 2
            if (r0 != r8) goto L_0x04fe
            java.lang.Number r0 = r5.integerValue()     // Catch:{ all -> 0x070f }
            r8 = r0
            goto L_0x0509
        L_0x04fe:
            com.alibaba.fastjson.parser.Feature r0 = com.alibaba.fastjson.parser.Feature.UseBigDecimal     // Catch:{ all -> 0x070f }
            boolean r0 = r5.isEnabled((com.alibaba.fastjson.parser.Feature) r0)     // Catch:{ all -> 0x070f }
            java.lang.Number r0 = r5.decimalValue(r0)     // Catch:{ all -> 0x070f }
            r8 = r0
        L_0x0509:
            r12.put(r10, r8)     // Catch:{ all -> 0x070f }
        L_0x050c:
            r5.skipWhitespace()     // Catch:{ all -> 0x070f }
            char r0 = r5.getCurrent()     // Catch:{ all -> 0x070f }
            r7 = 44
            if (r0 != r7) goto L_0x0522
            r5.next()     // Catch:{ all -> 0x070f }
            r18 = r4
            r4 = 13
            r8 = 16
            goto L_0x06cf
        L_0x0522:
            r4 = 125(0x7d, float:1.75E-43)
            if (r0 != r4) goto L_0x0537
            r5.next()     // Catch:{ all -> 0x070f }
            r5.resetStringPosition()     // Catch:{ all -> 0x070f }
            r5.nextToken()     // Catch:{ all -> 0x070f }
            r1.setContext(r8, r10)     // Catch:{ all -> 0x070f }
            r1.setContext(r13)
            return r2
        L_0x0537:
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x070f }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x070f }
            r7.<init>()     // Catch:{ all -> 0x070f }
            java.lang.String r14 = "syntax error, position at "
            r7.append(r14)     // Catch:{ all -> 0x070f }
            int r14 = r5.pos()     // Catch:{ all -> 0x070f }
            r7.append(r14)     // Catch:{ all -> 0x070f }
            r7.append(r6)     // Catch:{ all -> 0x070f }
            r7.append(r10)     // Catch:{ all -> 0x070f }
            java.lang.String r6 = r7.toString()     // Catch:{ all -> 0x070f }
            r4.<init>(r6)     // Catch:{ all -> 0x070f }
            throw r4     // Catch:{ all -> 0x070f }
        L_0x0559:
            r0 = 91
            if (r7 != r0) goto L_0x05b7
            r5.nextToken()     // Catch:{ all -> 0x070f }
            com.alibaba.fastjson.JSONArray r0 = new com.alibaba.fastjson.JSONArray     // Catch:{ all -> 0x070f }
            r0.<init>()     // Catch:{ all -> 0x070f }
            if (r3 == 0) goto L_0x0572
            java.lang.Class r6 = r25.getClass()     // Catch:{ all -> 0x070f }
            java.lang.Class<java.lang.Integer> r8 = java.lang.Integer.class
            if (r6 != r8) goto L_0x0572
            r21 = 1
            goto L_0x0574
        L_0x0572:
            r21 = r14
        L_0x0574:
            r6 = r21
            if (r3 != 0) goto L_0x057b
            r1.setContext(r13)     // Catch:{ all -> 0x070f }
        L_0x057b:
            r1.parseArray((java.util.Collection) r0, (java.lang.Object) r10)     // Catch:{ all -> 0x070f }
            com.alibaba.fastjson.parser.Feature r8 = com.alibaba.fastjson.parser.Feature.UseObjectArray     // Catch:{ all -> 0x070f }
            boolean r8 = r5.isEnabled((com.alibaba.fastjson.parser.Feature) r8)     // Catch:{ all -> 0x070f }
            if (r8 == 0) goto L_0x058b
            java.lang.Object[] r8 = r0.toArray()     // Catch:{ all -> 0x070f }
            goto L_0x058c
        L_0x058b:
            r8 = r0
        L_0x058c:
            r12.put(r10, r8)     // Catch:{ all -> 0x070f }
            int r14 = r5.token()     // Catch:{ all -> 0x070f }
            r18 = r0
            r0 = 13
            if (r14 != r0) goto L_0x05a1
            r5.nextToken()     // Catch:{ all -> 0x070f }
            r1.setContext(r13)
            return r2
        L_0x05a1:
            int r0 = r5.token()     // Catch:{ all -> 0x070f }
            r14 = 16
            if (r0 != r14) goto L_0x05b1
            r18 = r4
            r4 = 13
            r8 = 16
            goto L_0x06cf
        L_0x05b1:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x070f }
            r0.<init>(r15)     // Catch:{ all -> 0x070f }
            throw r0     // Catch:{ all -> 0x070f }
        L_0x05b7:
            r0 = 123(0x7b, float:1.72E-43)
            if (r7 != r0) goto L_0x06aa
            r5.nextToken()     // Catch:{ all -> 0x070f }
            if (r3 == 0) goto L_0x05cb
            java.lang.Class r0 = r25.getClass()     // Catch:{ all -> 0x070f }
            java.lang.Class<java.lang.Integer> r6 = java.lang.Integer.class
            if (r0 != r6) goto L_0x05cb
            r21 = 1
            goto L_0x05cd
        L_0x05cb:
            r21 = r14
        L_0x05cd:
            r0 = r21
            com.alibaba.fastjson.parser.Feature r6 = com.alibaba.fastjson.parser.Feature.CustomMapDeserializer     // Catch:{ all -> 0x070f }
            boolean r6 = r5.isEnabled((com.alibaba.fastjson.parser.Feature) r6)     // Catch:{ all -> 0x070f }
            if (r6 == 0) goto L_0x05ff
            com.alibaba.fastjson.parser.ParserConfig r6 = r1.config     // Catch:{ all -> 0x070f }
            java.lang.Class<java.util.Map> r8 = java.util.Map.class
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r6 = r6.getDeserializer((java.lang.reflect.Type) r8)     // Catch:{ all -> 0x070f }
            com.alibaba.fastjson.parser.deserializer.MapDeserializer r6 = (com.alibaba.fastjson.parser.deserializer.MapDeserializer) r6     // Catch:{ all -> 0x070f }
            int r8 = r5.getFeatures()     // Catch:{ all -> 0x070f }
            com.alibaba.fastjson.parser.Feature r14 = com.alibaba.fastjson.parser.Feature.OrderedField     // Catch:{ all -> 0x070f }
            int r14 = r14.mask     // Catch:{ all -> 0x070f }
            r8 = r8 & r14
            if (r8 == 0) goto L_0x05f7
            java.lang.Class<java.util.Map> r8 = java.util.Map.class
            int r14 = r5.getFeatures()     // Catch:{ all -> 0x070f }
            java.util.Map r8 = r6.createMap(r8, r14)     // Catch:{ all -> 0x070f }
            goto L_0x05fd
        L_0x05f7:
            java.lang.Class<java.util.Map> r8 = java.util.Map.class
            java.util.Map r8 = r6.createMap(r8)     // Catch:{ all -> 0x070f }
        L_0x05fd:
            r6 = r8
            goto L_0x060a
        L_0x05ff:
            com.alibaba.fastjson.JSONObject r6 = new com.alibaba.fastjson.JSONObject     // Catch:{ all -> 0x070f }
            com.alibaba.fastjson.parser.Feature r8 = com.alibaba.fastjson.parser.Feature.OrderedField     // Catch:{ all -> 0x070f }
            boolean r8 = r5.isEnabled((com.alibaba.fastjson.parser.Feature) r8)     // Catch:{ all -> 0x070f }
            r6.<init>((boolean) r8)     // Catch:{ all -> 0x070f }
        L_0x060a:
            r8 = 0
            if (r0 != 0) goto L_0x0614
            com.alibaba.fastjson.parser.ParseContext r14 = r1.context     // Catch:{ all -> 0x070f }
            com.alibaba.fastjson.parser.ParseContext r14 = r1.setContext(r14, r6, r10)     // Catch:{ all -> 0x070f }
            r8 = r14
        L_0x0614:
            r14 = 0
            r15 = 0
            com.alibaba.fastjson.parser.deserializer.FieldTypeResolver r3 = r1.fieldTypeResolver     // Catch:{ all -> 0x070f }
            if (r3 == 0) goto L_0x063f
            if (r10 == 0) goto L_0x0621
            java.lang.String r3 = r10.toString()     // Catch:{ all -> 0x070f }
            goto L_0x0622
        L_0x0621:
            r3 = 0
        L_0x0622:
            r18 = r4
            com.alibaba.fastjson.parser.deserializer.FieldTypeResolver r4 = r1.fieldTypeResolver     // Catch:{ all -> 0x070f }
            java.lang.reflect.Type r4 = r4.resolve(r2, r3)     // Catch:{ all -> 0x070f }
            if (r4 == 0) goto L_0x063c
            r19 = r3
            com.alibaba.fastjson.parser.ParserConfig r3 = r1.config     // Catch:{ all -> 0x070f }
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r3 = r3.getDeserializer((java.lang.reflect.Type) r4)     // Catch:{ all -> 0x070f }
            java.lang.Object r20 = r3.deserialze(r1, r4, r10)     // Catch:{ all -> 0x070f }
            r14 = r20
            r15 = 1
            goto L_0x0641
        L_0x063c:
            r19 = r3
            goto L_0x0641
        L_0x063f:
            r18 = r4
        L_0x0641:
            if (r15 != 0) goto L_0x0648
            java.lang.Object r3 = r1.parseObject((java.util.Map) r6, (java.lang.Object) r10)     // Catch:{ all -> 0x070f }
            r14 = r3
        L_0x0648:
            if (r8 == 0) goto L_0x064e
            if (r6 == r14) goto L_0x064e
            r8.object = r2     // Catch:{ all -> 0x070f }
        L_0x064e:
            if (r10 == 0) goto L_0x0657
            java.lang.String r3 = r10.toString()     // Catch:{ all -> 0x070f }
            r1.checkMapResolve(r2, r3)     // Catch:{ all -> 0x070f }
        L_0x0657:
            r12.put(r10, r14)     // Catch:{ all -> 0x070f }
            if (r0 == 0) goto L_0x065f
            r1.setContext(r14, r10)     // Catch:{ all -> 0x070f }
        L_0x065f:
            int r3 = r5.token()     // Catch:{ all -> 0x070f }
            r4 = 13
            if (r3 != r4) goto L_0x0672
            r5.nextToken()     // Catch:{ all -> 0x070f }
            r1.setContext(r13)     // Catch:{ all -> 0x070f }
            r1.setContext(r13)
            return r2
        L_0x0672:
            int r3 = r5.token()     // Catch:{ all -> 0x070f }
            r4 = 16
            if (r3 != r4) goto L_0x068c
            if (r0 == 0) goto L_0x0684
            r23.popContext()     // Catch:{ all -> 0x070f }
            r4 = 13
            r8 = 16
            goto L_0x06cf
        L_0x0684:
            r1.setContext(r13)     // Catch:{ all -> 0x070f }
            r4 = 13
            r8 = 16
            goto L_0x06cf
        L_0x068c:
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x070f }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x070f }
            r4.<init>()     // Catch:{ all -> 0x070f }
            r19 = r0
            java.lang.String r0 = "syntax error, "
            r4.append(r0)     // Catch:{ all -> 0x070f }
            java.lang.String r0 = r5.tokenName()     // Catch:{ all -> 0x070f }
            r4.append(r0)     // Catch:{ all -> 0x070f }
            java.lang.String r0 = r4.toString()     // Catch:{ all -> 0x070f }
            r3.<init>(r0)     // Catch:{ all -> 0x070f }
            throw r3     // Catch:{ all -> 0x070f }
        L_0x06aa:
            r18 = r4
            r5.nextToken()     // Catch:{ all -> 0x070f }
            java.lang.Object r0 = r23.parse()     // Catch:{ all -> 0x070f }
            r12.put(r10, r0)     // Catch:{ all -> 0x070f }
            int r3 = r5.token()     // Catch:{ all -> 0x070f }
            r4 = 13
            if (r3 != r4) goto L_0x06c6
            r5.nextToken()     // Catch:{ all -> 0x070f }
            r1.setContext(r13)
            return r2
        L_0x06c6:
            int r3 = r5.token()     // Catch:{ all -> 0x070f }
            r8 = 16
            if (r3 != r8) goto L_0x06d9
        L_0x06cf:
            r3 = r25
            r7 = r4
            r10 = r8
            r4 = r18
            r6 = 0
            r8 = 4
            goto L_0x0085
        L_0x06d9:
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x070f }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x070f }
            r4.<init>()     // Catch:{ all -> 0x070f }
            java.lang.String r8 = "syntax error, position at "
            r4.append(r8)     // Catch:{ all -> 0x070f }
            int r8 = r5.pos()     // Catch:{ all -> 0x070f }
            r4.append(r8)     // Catch:{ all -> 0x070f }
            r4.append(r6)     // Catch:{ all -> 0x070f }
            r4.append(r10)     // Catch:{ all -> 0x070f }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x070f }
            r3.<init>(r4)     // Catch:{ all -> 0x070f }
            throw r3     // Catch:{ all -> 0x070f }
        L_0x06fb:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x070f }
            java.lang.String r3 = "object key level > 512"
            r0.<init>(r3)     // Catch:{ all -> 0x070f }
            throw r0     // Catch:{ all -> 0x070f }
        L_0x0703:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x070f }
            r0.<init>(r15)     // Catch:{ all -> 0x070f }
            throw r0     // Catch:{ all -> 0x070f }
        L_0x0709:
            com.alibaba.fastjson.JSONException r0 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x070f }
            r0.<init>(r15)     // Catch:{ all -> 0x070f }
            throw r0     // Catch:{ all -> 0x070f }
        L_0x070f:
            r0 = move-exception
            r9 = r13
            goto L_0x0713
        L_0x0712:
            r0 = move-exception
        L_0x0713:
            r1.setContext(r9)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parseObject(java.util.Map, java.lang.Object):java.lang.Object");
    }

    public ParserConfig getConfig() {
        return this.config;
    }

    public void setConfig(ParserConfig config2) {
        this.config = config2;
    }

    public <T> T parseObject(Class<T> clazz) {
        return parseObject((Type) clazz, (Object) null);
    }

    public <T> T parseObject(Type type) {
        return parseObject(type, (Object) null);
    }

    public <T> T parseObject(Type type, Object fieldName) {
        int token = this.lexer.token();
        if (token == 8) {
            this.lexer.nextToken();
            return null;
        }
        if (token == 4) {
            if (type == byte[].class) {
                Object bytesValue = this.lexer.bytesValue();
                this.lexer.nextToken();
                return bytesValue;
            } else if (type == char[].class) {
                String strVal = this.lexer.stringVal();
                this.lexer.nextToken();
                return strVal.toCharArray();
            }
        }
        ObjectDeserializer deserializer = this.config.getDeserializer(type);
        try {
            if (deserializer.getClass() != JavaBeanDeserializer.class) {
                return deserializer.deserialze(this, type, fieldName);
            }
            if (this.lexer.token() != 12) {
                if (this.lexer.token() != 14) {
                    throw new JSONException("syntax error,except start with { or [,but actually start with " + this.lexer.tokenName());
                }
            }
            return ((JavaBeanDeserializer) deserializer).deserialze(this, type, fieldName, 0);
        } catch (JSONException e) {
            throw e;
        } catch (Throwable e2) {
            throw new JSONException(e2.getMessage(), e2);
        }
    }

    public <T> List<T> parseArray(Class<T> clazz) {
        List<T> array = new ArrayList<>();
        parseArray((Class<?>) clazz, (Collection) array);
        return array;
    }

    public void parseArray(Class<?> clazz, Collection array) {
        parseArray((Type) clazz, array);
    }

    public void parseArray(Type type, Collection array) {
        parseArray(type, array, (Object) null);
    }

    /* JADX INFO: finally extract failed */
    public void parseArray(Type type, Collection array, Object fieldName) {
        ObjectDeserializer deserializer;
        Object val;
        Object obj;
        Class<String> cls = String.class;
        int token = this.lexer.token();
        if (token == 21 || token == 22) {
            this.lexer.nextToken();
            token = this.lexer.token();
        }
        if (token == 14) {
            if (Integer.TYPE == type) {
                deserializer = IntegerCodec.instance;
                this.lexer.nextToken(2);
            } else if (cls == type) {
                deserializer = StringCodec.instance;
                this.lexer.nextToken(4);
            } else {
                deserializer = this.config.getDeserializer(type);
                this.lexer.nextToken(deserializer.getFastMatchToken());
            }
            ParseContext context2 = this.context;
            setContext(array, fieldName);
            int i = 0;
            while (true) {
                try {
                    if (this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                        while (this.lexer.token() == 16) {
                            this.lexer.nextToken();
                        }
                    }
                    if (this.lexer.token() == 15) {
                        setContext(context2);
                        this.lexer.nextToken(16);
                        return;
                    }
                    if (Integer.TYPE == type) {
                        array.add(IntegerCodec.instance.deserialze(this, (Type) null, (Object) null));
                    } else if (cls == type) {
                        if (this.lexer.token() == 4) {
                            obj = this.lexer.stringVal();
                            this.lexer.nextToken(16);
                        } else {
                            Object obj2 = parse();
                            if (obj2 == null) {
                                obj = null;
                            } else {
                                obj = obj2.toString();
                            }
                        }
                        array.add(obj);
                    } else {
                        if (this.lexer.token() == 8) {
                            this.lexer.nextToken();
                            val = null;
                        } else {
                            val = deserializer.deserialze(this, type, Integer.valueOf(i));
                        }
                        array.add(val);
                        checkListResolve(array);
                    }
                    if (this.lexer.token() == 16) {
                        this.lexer.nextToken(deserializer.getFastMatchToken());
                    }
                    i++;
                } catch (Throwable th) {
                    setContext(context2);
                    throw th;
                }
            }
        } else {
            throw new JSONException("expect '[', but " + JSONToken.name(token) + ", " + this.lexer.info());
        }
    }

    public Object[] parseArray(Type[] types) {
        String value;
        char c;
        Class<?> clazz;
        Type[] typeArr = types;
        Object obj = null;
        int i = 8;
        if (this.lexer.token() == 8) {
            this.lexer.nextToken(16);
            return null;
        }
        int i2 = 14;
        if (this.lexer.token() == 14) {
            Object[] list = new Object[typeArr.length];
            if (typeArr.length == 0) {
                this.lexer.nextToken(15);
                if (this.lexer.token() == 15) {
                    this.lexer.nextToken(16);
                    return new Object[0];
                }
                throw new JSONException("syntax error");
            }
            this.lexer.nextToken(2);
            int i3 = 0;
            while (i3 < typeArr.length) {
                if (this.lexer.token() == i) {
                    value = null;
                    this.lexer.nextToken(16);
                } else {
                    Type type = typeArr[i3];
                    if (type == Integer.TYPE || type == Integer.class) {
                        if (this.lexer.token() == 2) {
                            Integer valueOf = Integer.valueOf(this.lexer.intValue());
                            this.lexer.nextToken(16);
                            value = valueOf;
                        } else {
                            value = TypeUtils.cast(parse(), type, this.config);
                        }
                    } else if (type != String.class) {
                        boolean isArray = false;
                        Class<?> componentType = null;
                        if (i3 == typeArr.length - 1 && (type instanceof Class) && !(((clazz = (Class) type) == byte[].class || clazz == char[].class) && this.lexer.token() == 4)) {
                            isArray = clazz.isArray();
                            componentType = clazz.getComponentType();
                        }
                        if (!isArray || this.lexer.token() == i2) {
                            value = this.config.getDeserializer(type).deserialze(this, type, Integer.valueOf(i3));
                        } else {
                            List<Object> varList = new ArrayList<>();
                            ObjectDeserializer deserializer = this.config.getDeserializer((Type) componentType);
                            int fastMatch = deserializer.getFastMatchToken();
                            if (this.lexer.token() != 15) {
                                while (true) {
                                    varList.add(deserializer.deserialze(this, type, obj));
                                    if (this.lexer.token() != 16) {
                                        break;
                                    }
                                    this.lexer.nextToken(fastMatch);
                                    obj = null;
                                }
                                if (this.lexer.token() != 15) {
                                    throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
                                }
                            }
                            value = TypeUtils.cast((Object) varList, type, this.config);
                        }
                    } else if (this.lexer.token() == 4) {
                        String stringVal = this.lexer.stringVal();
                        this.lexer.nextToken(16);
                        value = stringVal;
                    } else {
                        value = TypeUtils.cast(parse(), type, this.config);
                    }
                }
                list[i3] = value;
                if (this.lexer.token() == 15) {
                    break;
                } else if (this.lexer.token() == 16) {
                    if (i3 == typeArr.length - 1) {
                        this.lexer.nextToken(15);
                        c = 2;
                    } else {
                        c = 2;
                        this.lexer.nextToken(2);
                    }
                    i3++;
                    char c2 = c;
                    obj = null;
                    i = 8;
                    i2 = 14;
                } else {
                    throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
                }
            }
            if (this.lexer.token() == 15) {
                this.lexer.nextToken(16);
                return list;
            }
            throw new JSONException("syntax error");
        }
        throw new JSONException("syntax error : " + this.lexer.tokenName());
    }

    /* JADX WARNING: type inference failed for: r2v1, types: [com.alibaba.fastjson.parser.deserializer.ObjectDeserializer] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void parseObject(java.lang.Object r14) {
        /*
            r13 = this;
            java.lang.Class r0 = r14.getClass()
            r1 = 0
            com.alibaba.fastjson.parser.ParserConfig r2 = r13.config
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r2 = r2.getDeserializer((java.lang.reflect.Type) r0)
            boolean r3 = r2 instanceof com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer
            if (r3 == 0) goto L_0x0012
            r1 = r2
            com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer r1 = (com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer) r1
        L_0x0012:
            com.alibaba.fastjson.parser.JSONLexer r3 = r13.lexer
            int r3 = r3.token()
            r4 = 12
            r5 = 16
            if (r3 == r4) goto L_0x0045
            com.alibaba.fastjson.parser.JSONLexer r3 = r13.lexer
            int r3 = r3.token()
            if (r3 != r5) goto L_0x0027
            goto L_0x0045
        L_0x0027:
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "syntax error, expect {, actual "
            r4.append(r5)
            com.alibaba.fastjson.parser.JSONLexer r5 = r13.lexer
            java.lang.String r5 = r5.tokenName()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        L_0x0045:
            com.alibaba.fastjson.parser.JSONLexer r3 = r13.lexer
            com.alibaba.fastjson.parser.SymbolTable r4 = r13.symbolTable
            java.lang.String r3 = r3.scanSymbol(r4)
            r4 = 13
            if (r3 != 0) goto L_0x0073
            com.alibaba.fastjson.parser.JSONLexer r6 = r13.lexer
            int r6 = r6.token()
            if (r6 != r4) goto L_0x0060
            com.alibaba.fastjson.parser.JSONLexer r4 = r13.lexer
            r4.nextToken(r5)
            return
        L_0x0060:
            com.alibaba.fastjson.parser.JSONLexer r6 = r13.lexer
            int r6 = r6.token()
            if (r6 != r5) goto L_0x0073
            com.alibaba.fastjson.parser.JSONLexer r6 = r13.lexer
            com.alibaba.fastjson.parser.Feature r7 = com.alibaba.fastjson.parser.Feature.AllowArbitraryCommas
            boolean r6 = r6.isEnabled((com.alibaba.fastjson.parser.Feature) r7)
            if (r6 == 0) goto L_0x0073
            goto L_0x0045
        L_0x0073:
            r6 = 0
            if (r1 == 0) goto L_0x007a
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r6 = r1.getFieldDeserializer((java.lang.String) r3)
        L_0x007a:
            if (r6 != 0) goto L_0x00c0
            com.alibaba.fastjson.parser.JSONLexer r7 = r13.lexer
            com.alibaba.fastjson.parser.Feature r8 = com.alibaba.fastjson.parser.Feature.IgnoreNotMatch
            boolean r7 = r7.isEnabled((com.alibaba.fastjson.parser.Feature) r8)
            if (r7 == 0) goto L_0x009c
            com.alibaba.fastjson.parser.JSONLexer r7 = r13.lexer
            r7.nextTokenWithColon()
            r13.parse()
            com.alibaba.fastjson.parser.JSONLexer r7 = r13.lexer
            int r7 = r7.token()
            if (r7 != r4) goto L_0x0045
            com.alibaba.fastjson.parser.JSONLexer r4 = r13.lexer
            r4.nextToken()
            return
        L_0x009c:
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "setter not found, class "
            r5.append(r7)
            java.lang.String r7 = r0.getName()
            r5.append(r7)
            java.lang.String r7 = ", property "
            r5.append(r7)
            r5.append(r3)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x00c0:
            com.alibaba.fastjson.util.FieldInfo r7 = r6.fieldInfo
            java.lang.Class<?> r8 = r7.fieldClass
            java.lang.reflect.Type r7 = r7.fieldType
            java.lang.Class r9 = java.lang.Integer.TYPE
            r10 = 2
            r11 = 0
            if (r8 != r9) goto L_0x00d8
            com.alibaba.fastjson.parser.JSONLexer r9 = r13.lexer
            r9.nextTokenWithColon(r10)
            com.alibaba.fastjson.serializer.IntegerCodec r9 = com.alibaba.fastjson.serializer.IntegerCodec.instance
            java.lang.Object r9 = r9.deserialze(r13, r7, r11)
            goto L_0x010b
        L_0x00d8:
            java.lang.Class<java.lang.String> r9 = java.lang.String.class
            if (r8 != r9) goto L_0x00e7
            com.alibaba.fastjson.parser.JSONLexer r9 = r13.lexer
            r10 = 4
            r9.nextTokenWithColon(r10)
            java.lang.Object r9 = com.alibaba.fastjson.serializer.StringCodec.deserialze(r13)
            goto L_0x010b
        L_0x00e7:
            java.lang.Class r9 = java.lang.Long.TYPE
            if (r8 != r9) goto L_0x00f7
            com.alibaba.fastjson.parser.JSONLexer r9 = r13.lexer
            r9.nextTokenWithColon(r10)
            com.alibaba.fastjson.serializer.LongCodec r9 = com.alibaba.fastjson.serializer.LongCodec.instance
            java.lang.Object r9 = r9.deserialze(r13, r7, r11)
            goto L_0x010b
        L_0x00f7:
            com.alibaba.fastjson.parser.ParserConfig r9 = r13.config
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r9 = r9.getDeserializer(r8, r7)
            com.alibaba.fastjson.parser.JSONLexer r10 = r13.lexer
            int r12 = r9.getFastMatchToken()
            r10.nextTokenWithColon(r12)
            java.lang.Object r10 = r9.deserialze(r13, r7, r11)
            r9 = r10
        L_0x010b:
            r6.setValue((java.lang.Object) r14, (java.lang.Object) r9)
            com.alibaba.fastjson.parser.JSONLexer r7 = r13.lexer
            int r7 = r7.token()
            if (r7 != r5) goto L_0x0118
            goto L_0x0045
        L_0x0118:
            com.alibaba.fastjson.parser.JSONLexer r7 = r13.lexer
            int r7 = r7.token()
            if (r7 != r4) goto L_0x0126
            com.alibaba.fastjson.parser.JSONLexer r4 = r13.lexer
            r4.nextToken(r5)
            return
        L_0x0126:
            goto L_0x0045
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parseObject(java.lang.Object):void");
    }

    public Object parseArrayWithType(Type collectionType) {
        if (this.lexer.token() == 8) {
            this.lexer.nextToken();
            return null;
        }
        Type[] actualTypes = ((ParameterizedType) collectionType).getActualTypeArguments();
        if (actualTypes.length == 1) {
            Type actualTypeArgument = actualTypes[0];
            if (actualTypeArgument instanceof Class) {
                List<Object> array = new ArrayList<>();
                parseArray((Class<?>) (Class) actualTypeArgument, (Collection) array);
                return array;
            } else if (actualTypeArgument instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) actualTypeArgument;
                Type upperBoundType = wildcardType.getUpperBounds()[0];
                if (!Object.class.equals(upperBoundType)) {
                    List<Object> array2 = new ArrayList<>();
                    parseArray((Class<?>) (Class) upperBoundType, (Collection) array2);
                    return array2;
                } else if (wildcardType.getLowerBounds().length == 0) {
                    return parse();
                } else {
                    throw new JSONException("not support type : " + collectionType);
                }
            } else {
                if (actualTypeArgument instanceof TypeVariable) {
                    TypeVariable<?> typeVariable = (TypeVariable) actualTypeArgument;
                    Type[] bounds = typeVariable.getBounds();
                    if (bounds.length == 1) {
                        Type boundType = bounds[0];
                        if (boundType instanceof Class) {
                            List<Object> array3 = new ArrayList<>();
                            parseArray((Class<?>) (Class) boundType, (Collection) array3);
                            return array3;
                        }
                    } else {
                        throw new JSONException("not support : " + typeVariable);
                    }
                }
                if (actualTypeArgument instanceof ParameterizedType) {
                    List<Object> array4 = new ArrayList<>();
                    parseArray((Type) (ParameterizedType) actualTypeArgument, (Collection) array4);
                    return array4;
                }
                throw new JSONException("TODO : " + collectionType);
            }
        } else {
            throw new JSONException("not support type " + collectionType);
        }
    }

    public void acceptType(String typeName) {
        JSONLexer lexer2 = this.lexer;
        lexer2.nextTokenWithColon();
        if (lexer2.token() != 4) {
            throw new JSONException("type not match error");
        } else if (typeName.equals(lexer2.stringVal())) {
            lexer2.nextToken();
            if (lexer2.token() == 16) {
                lexer2.nextToken();
            }
        } else {
            throw new JSONException("type not match error");
        }
    }

    public int getResolveStatus() {
        return this.resolveStatus;
    }

    public void setResolveStatus(int resolveStatus2) {
        this.resolveStatus = resolveStatus2;
    }

    public Object getObject(String path) {
        for (int i = 0; i < this.contextArrayIndex; i++) {
            if (path.equals(this.contextArray[i].toString())) {
                return this.contextArray[i].object;
            }
        }
        return null;
    }

    public void checkListResolve(Collection array) {
        if (this.resolveStatus != 1) {
            return;
        }
        if (array instanceof List) {
            ResolveTask task = getLastResolveTask();
            task.fieldDeserializer = new ResolveFieldDeserializer(this, (List) array, array.size() - 1);
            task.ownerContext = this.context;
            setResolveStatus(0);
            return;
        }
        ResolveTask task2 = getLastResolveTask();
        task2.fieldDeserializer = new ResolveFieldDeserializer(array);
        task2.ownerContext = this.context;
        setResolveStatus(0);
    }

    public void checkMapResolve(Map object, Object fieldName) {
        if (this.resolveStatus == 1) {
            ResolveFieldDeserializer fieldResolver = new ResolveFieldDeserializer(object, fieldName);
            ResolveTask task = getLastResolveTask();
            task.fieldDeserializer = fieldResolver;
            task.ownerContext = this.context;
            setResolveStatus(0);
        }
    }

    public Object parseObject(Map object) {
        return parseObject(object, (Object) null);
    }

    public JSONObject parseObject() {
        Object parsedObject = parseObject((Map) new JSONObject(this.lexer.isEnabled(Feature.OrderedField)));
        if (parsedObject instanceof JSONObject) {
            return (JSONObject) parsedObject;
        }
        if (parsedObject == null) {
            return null;
        }
        return new JSONObject((Map<String, Object>) (Map) parsedObject);
    }

    public final void parseArray(Collection array) {
        parseArray(array, (Object) null);
    }

    public final void parseArray(Collection array, Object fieldName) {
        Object value;
        Object value2;
        Object value3;
        JSONLexer lexer2 = this.lexer;
        if (lexer2.token() == 21 || lexer2.token() == 22) {
            lexer2.nextToken();
        }
        if (lexer2.token() == 14) {
            lexer2.nextToken(4);
            ParseContext parseContext = this.context;
            if (parseContext == null || parseContext.level <= 512) {
                ParseContext context2 = this.context;
                setContext(array, fieldName);
                int i = 0;
                while (true) {
                    try {
                        if (lexer2.isEnabled(Feature.AllowArbitraryCommas)) {
                            while (lexer2.token() == 16) {
                                lexer2.nextToken();
                            }
                        }
                        switch (lexer2.token()) {
                            case 2:
                                Object integerValue = lexer2.integerValue();
                                lexer2.nextToken(16);
                                value = integerValue;
                                break;
                            case 3:
                                if (lexer2.isEnabled(Feature.UseBigDecimal)) {
                                    value2 = lexer2.decimalValue(true);
                                } else {
                                    value2 = lexer2.decimalValue(false);
                                }
                                lexer2.nextToken(16);
                                value = value2;
                                break;
                            case 4:
                                String stringLiteral = lexer2.stringVal();
                                lexer2.nextToken(16);
                                if (!lexer2.isEnabled(Feature.AllowISO8601DateFormat)) {
                                    String str = stringLiteral;
                                    value = stringLiteral;
                                    break;
                                } else {
                                    JSONScanner iso8601Lexer = new JSONScanner(stringLiteral);
                                    if (iso8601Lexer.scanISO8601DateIfMatch()) {
                                        value3 = iso8601Lexer.getCalendar().getTime();
                                    } else {
                                        value3 = stringLiteral;
                                    }
                                    iso8601Lexer.close();
                                    value = value3;
                                    break;
                                }
                            case 6:
                                Object obj = Boolean.TRUE;
                                lexer2.nextToken(16);
                                value = obj;
                                break;
                            case 7:
                                Object obj2 = Boolean.FALSE;
                                lexer2.nextToken(16);
                                value = obj2;
                                break;
                            case 8:
                                value = null;
                                lexer2.nextToken(4);
                                break;
                            case 12:
                                value = parseObject((Map) new JSONObject(lexer2.isEnabled(Feature.OrderedField)), (Object) Integer.valueOf(i));
                                break;
                            case 14:
                                JSONArray jSONArray = new JSONArray();
                                parseArray((Collection) jSONArray, (Object) Integer.valueOf(i));
                                if (!lexer2.isEnabled(Feature.UseObjectArray)) {
                                    Object value4 = jSONArray;
                                    value = jSONArray;
                                    break;
                                } else {
                                    value = jSONArray.toArray();
                                    break;
                                }
                            case 15:
                                lexer2.nextToken(16);
                                return;
                            case 20:
                                throw new JSONException("unclosed jsonArray");
                            case 23:
                                value = null;
                                lexer2.nextToken(4);
                                break;
                            default:
                                value = parse();
                                break;
                        }
                        array.add(value);
                        checkListResolve(array);
                        if (lexer2.token() == 16) {
                            lexer2.nextToken(4);
                        }
                        i++;
                    } finally {
                        setContext(context2);
                    }
                }
            } else {
                throw new JSONException("array level > 512");
            }
        } else {
            throw new JSONException("syntax error, expect [, actual " + JSONToken.name(lexer2.token()) + ", pos " + lexer2.pos() + ", fieldName " + fieldName);
        }
    }

    public ParseContext getContext() {
        return this.context;
    }

    public ParseContext getOwnerContext() {
        return this.context.parent;
    }

    public List<ResolveTask> getResolveTaskList() {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        return this.resolveTaskList;
    }

    public void addResolveTask(ResolveTask task) {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        this.resolveTaskList.add(task);
    }

    public ResolveTask getLastResolveTask() {
        List<ResolveTask> list = this.resolveTaskList;
        return list.get(list.size() - 1);
    }

    public List<ExtraProcessor> getExtraProcessors() {
        if (this.extraProcessors == null) {
            this.extraProcessors = new ArrayList(2);
        }
        return this.extraProcessors;
    }

    public List<ExtraTypeProvider> getExtraTypeProviders() {
        if (this.extraTypeProviders == null) {
            this.extraTypeProviders = new ArrayList(2);
        }
        return this.extraTypeProviders;
    }

    public FieldTypeResolver getFieldTypeResolver() {
        return this.fieldTypeResolver;
    }

    public void setFieldTypeResolver(FieldTypeResolver fieldTypeResolver2) {
        this.fieldTypeResolver = fieldTypeResolver2;
    }

    public void setContext(ParseContext context2) {
        if (!this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            this.context = context2;
        }
    }

    public void popContext() {
        if (!this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            this.context = this.context.parent;
            int i = this.contextArrayIndex;
            if (i > 0) {
                int i2 = i - 1;
                this.contextArrayIndex = i2;
                this.contextArray[i2] = null;
            }
        }
    }

    public ParseContext setContext(Object object, Object fieldName) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        return setContext(this.context, object, fieldName);
    }

    public ParseContext setContext(ParseContext parent, Object object, Object fieldName) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        ParseContext parseContext = new ParseContext(parent, object, fieldName);
        this.context = parseContext;
        addContext(parseContext);
        return this.context;
    }

    private void addContext(ParseContext context2) {
        int i = this.contextArrayIndex;
        this.contextArrayIndex = i + 1;
        ParseContext[] parseContextArr = this.contextArray;
        if (parseContextArr == null) {
            this.contextArray = new ParseContext[8];
        } else if (i >= parseContextArr.length) {
            ParseContext[] newArray = new ParseContext[((parseContextArr.length * 3) / 2)];
            System.arraycopy(parseContextArr, 0, newArray, 0, parseContextArr.length);
            this.contextArray = newArray;
        }
        this.contextArray[i] = context2;
    }

    public Object parse() {
        return parse((Object) null);
    }

    public Object parseKey() {
        if (this.lexer.token() != 18) {
            return parse((Object) null);
        }
        String value = this.lexer.stringVal();
        this.lexer.nextToken(16);
        return value;
    }

    public Object parse(Object fieldName) {
        JSONLexer lexer2 = this.lexer;
        switch (lexer2.token()) {
            case 2:
                Number intValue = lexer2.integerValue();
                lexer2.nextToken();
                return intValue;
            case 3:
                Object value = lexer2.decimalValue(lexer2.isEnabled(Feature.UseBigDecimal));
                lexer2.nextToken();
                return value;
            case 4:
                String stringLiteral = lexer2.stringVal();
                lexer2.nextToken(16);
                if (lexer2.isEnabled(Feature.AllowISO8601DateFormat)) {
                    JSONScanner iso8601Lexer = new JSONScanner(stringLiteral);
                    try {
                        if (iso8601Lexer.scanISO8601DateIfMatch()) {
                            return iso8601Lexer.getCalendar().getTime();
                        }
                        iso8601Lexer.close();
                    } finally {
                        iso8601Lexer.close();
                    }
                }
                return stringLiteral;
            case 6:
                lexer2.nextToken();
                return Boolean.TRUE;
            case 7:
                lexer2.nextToken();
                return Boolean.FALSE;
            case 8:
                lexer2.nextToken();
                return null;
            case 9:
                lexer2.nextToken(18);
                if (lexer2.token() == 18) {
                    lexer2.nextToken(10);
                    accept(10);
                    long time = lexer2.integerValue().longValue();
                    accept(2);
                    accept(11);
                    return new Date(time);
                }
                throw new JSONException("syntax error");
            case 12:
                return parseObject((Map) new JSONObject(lexer2.isEnabled(Feature.OrderedField)), fieldName);
            case 14:
                JSONArray array = new JSONArray();
                parseArray((Collection) array, fieldName);
                if (lexer2.isEnabled(Feature.UseObjectArray)) {
                    return array.toArray();
                }
                return array;
            case 18:
                if ("NaN".equals(lexer2.stringVal())) {
                    lexer2.nextToken();
                    return null;
                }
                throw new JSONException("syntax error, " + lexer2.info());
            case 20:
                if (lexer2.isBlankInput()) {
                    return null;
                }
                throw new JSONException("unterminated json string, " + lexer2.info());
            case 21:
                lexer2.nextToken();
                HashSet<Object> set = new HashSet<>();
                parseArray((Collection) set, fieldName);
                return set;
            case 22:
                lexer2.nextToken();
                TreeSet<Object> treeSet = new TreeSet<>();
                parseArray((Collection) treeSet, fieldName);
                return treeSet;
            case 23:
                lexer2.nextToken();
                return null;
            case 26:
                byte[] bytes = lexer2.bytesValue();
                lexer2.nextToken();
                return bytes;
            default:
                throw new JSONException("syntax error, " + lexer2.info());
        }
    }

    public void config(Feature feature, boolean state) {
        this.lexer.config(feature, state);
    }

    public boolean isEnabled(Feature feature) {
        return this.lexer.isEnabled(feature);
    }

    public JSONLexer getLexer() {
        return this.lexer;
    }

    public final void accept(int token) {
        JSONLexer lexer2 = this.lexer;
        if (lexer2.token() == token) {
            lexer2.nextToken();
            return;
        }
        throw new JSONException("syntax error, expect " + JSONToken.name(token) + ", actual " + JSONToken.name(lexer2.token()));
    }

    public final void accept(int token, int nextExpectToken) {
        JSONLexer lexer2 = this.lexer;
        if (lexer2.token() == token) {
            lexer2.nextToken(nextExpectToken);
        } else {
            throwException(token);
        }
    }

    public void throwException(int token) {
        throw new JSONException("syntax error, expect " + JSONToken.name(token) + ", actual " + JSONToken.name(this.lexer.token()));
    }

    public void close() {
        JSONLexer lexer2 = this.lexer;
        try {
            if (lexer2.isEnabled(Feature.AutoCloseSource)) {
                if (lexer2.token() != 20) {
                    throw new JSONException("not close json text, token : " + JSONToken.name(lexer2.token()));
                }
            }
        } finally {
            lexer2.close();
        }
    }

    public Object resolveReference(String ref) {
        if (this.contextArray == null) {
            return null;
        }
        int i = 0;
        while (true) {
            ParseContext[] parseContextArr = this.contextArray;
            if (i >= parseContextArr.length || i >= this.contextArrayIndex) {
                return null;
            }
            ParseContext context2 = parseContextArr[i];
            if (context2.toString().equals(ref)) {
                return context2.object;
            }
            i++;
        }
        return null;
    }

    public void handleResovleTask(Object value) {
        Object refValue;
        FieldInfo fieldInfo;
        List<ResolveTask> list = this.resolveTaskList;
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ResolveTask task = this.resolveTaskList.get(i);
                String ref = task.referenceValue;
                Object object = null;
                ParseContext parseContext = task.ownerContext;
                if (parseContext != null) {
                    object = parseContext.object;
                }
                if (ref.startsWith("$")) {
                    refValue = getObject(ref);
                    if (refValue == null) {
                        try {
                            JSONPath jsonpath = JSONPath.compile(ref);
                            if (jsonpath.isRef()) {
                                refValue = jsonpath.eval(value);
                            }
                        } catch (JSONPathException e) {
                        }
                    }
                } else {
                    refValue = task.context.object;
                }
                FieldDeserializer fieldDeser = task.fieldDeserializer;
                if (fieldDeser != null) {
                    if (refValue != null && refValue.getClass() == JSONObject.class && (fieldInfo = fieldDeser.fieldInfo) != null && !Map.class.isAssignableFrom(fieldInfo.fieldClass)) {
                        Object root = this.contextArray[0].object;
                        JSONPath jsonpath2 = JSONPath.compile(ref);
                        if (jsonpath2.isRef()) {
                            refValue = jsonpath2.eval(root);
                        }
                    }
                    if (fieldDeser.getOwnerClass() != null && !fieldDeser.getOwnerClass().isInstance(object) && task.ownerContext.parent != null && fieldDeser.getOwnerClass().isInstance(task.ownerContext.parent.object)) {
                        object = task.ownerContext.parent.object;
                    }
                    fieldDeser.setValue(object, refValue);
                }
            }
        }
    }

    public static class ResolveTask {
        public final ParseContext context;
        public FieldDeserializer fieldDeserializer;
        public ParseContext ownerContext;
        public final String referenceValue;

        public ResolveTask(ParseContext context2, String referenceValue2) {
            this.context = context2;
            this.referenceValue = referenceValue2;
        }
    }

    public void parseExtra(Object object, String key) {
        Object value;
        this.lexer.nextTokenWithColon();
        Type type = null;
        List<ExtraTypeProvider> list = this.extraTypeProviders;
        if (list != null) {
            for (ExtraTypeProvider extraProvider : list) {
                type = extraProvider.getExtraType(object, key);
            }
        }
        if (type == null) {
            value = parse();
        } else {
            value = parseObject(type);
        }
        if (object instanceof ExtraProcessable) {
            ((ExtraProcessable) object).processExtra(key, value);
            return;
        }
        List<ExtraProcessor> list2 = this.extraProcessors;
        if (list2 != null) {
            for (ExtraProcessor process : list2) {
                process.processExtra(object, key, value);
            }
        }
        if (this.resolveStatus == 1) {
            this.resolveStatus = 0;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:62:?, code lost:
        r4 = r11.config.getDeserializer((java.lang.reflect.Type) r5);
        r11.lexer.nextToken(16);
        setResolveStatus(2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x01e3, code lost:
        if (r0 == null) goto L_0x01ec;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x01e7, code lost:
        if ((r13 instanceof java.lang.Integer) != false) goto L_0x01ec;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x01e9, code lost:
        popContext();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x01ec, code lost:
        r6 = (java.util.Map) r4.deserialze(r11, r5, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x01f2, code lost:
        setContext(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x01f5, code lost:
        return r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object parse(com.alibaba.fastjson.parser.deserializer.PropertyProcessable r12, java.lang.Object r13) {
        /*
            r11 = this;
            com.alibaba.fastjson.parser.JSONLexer r0 = r11.lexer
            int r0 = r0.token()
            r1 = 12
            if (r0 == r1) goto L_0x008e
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "syntax error, expect {, actual "
            r0.append(r1)
            com.alibaba.fastjson.parser.JSONLexer r1 = r11.lexer
            java.lang.String r1 = r1.tokenName()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            boolean r1 = r13 instanceof java.lang.String
            if (r1 == 0) goto L_0x0046
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r2 = ", fieldName "
            r1.append(r2)
            java.lang.String r0 = r1.toString()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            r1.append(r13)
            java.lang.String r0 = r1.toString()
        L_0x0046:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r2 = ", "
            r1.append(r2)
            java.lang.String r0 = r1.toString()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            com.alibaba.fastjson.parser.JSONLexer r2 = r11.lexer
            java.lang.String r2 = r2.info()
            r1.append(r2)
            java.lang.String r0 = r1.toString()
            com.alibaba.fastjson.JSONArray r1 = new com.alibaba.fastjson.JSONArray
            r1.<init>()
            r11.parseArray((java.util.Collection) r1, (java.lang.Object) r13)
            int r2 = r1.size()
            r3 = 1
            if (r2 != r3) goto L_0x0088
            r2 = 0
            java.lang.Object r2 = r1.get(r2)
            boolean r3 = r2 instanceof com.alibaba.fastjson.JSONObject
            if (r3 == 0) goto L_0x0088
            r3 = r2
            com.alibaba.fastjson.JSONObject r3 = (com.alibaba.fastjson.JSONObject) r3
            return r3
        L_0x0088:
            com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException
            r2.<init>(r0)
            throw r2
        L_0x008e:
            com.alibaba.fastjson.parser.ParseContext r0 = r11.context
            r1 = 0
        L_0x0091:
            com.alibaba.fastjson.parser.JSONLexer r2 = r11.lexer     // Catch:{ all -> 0x026f }
            r2.skipWhitespace()     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r2 = r11.lexer     // Catch:{ all -> 0x026f }
            char r2 = r2.getCurrent()     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r3 = r11.lexer     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.Feature r4 = com.alibaba.fastjson.parser.Feature.AllowArbitraryCommas     // Catch:{ all -> 0x026f }
            boolean r3 = r3.isEnabled((com.alibaba.fastjson.parser.Feature) r4)     // Catch:{ all -> 0x026f }
            if (r3 == 0) goto L_0x00bc
        L_0x00a6:
            r3 = 44
            if (r2 != r3) goto L_0x00bc
            com.alibaba.fastjson.parser.JSONLexer r3 = r11.lexer     // Catch:{ all -> 0x026f }
            r3.next()     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r3 = r11.lexer     // Catch:{ all -> 0x026f }
            r3.skipWhitespace()     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r3 = r11.lexer     // Catch:{ all -> 0x026f }
            char r3 = r3.getCurrent()     // Catch:{ all -> 0x026f }
            r2 = r3
            goto L_0x00a6
        L_0x00bc:
            java.lang.String r3 = "expect ':' at "
            r4 = 58
            r5 = 34
            r6 = 16
            if (r2 != r5) goto L_0x00f9
            com.alibaba.fastjson.parser.JSONLexer r7 = r11.lexer     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.SymbolTable r8 = r11.symbolTable     // Catch:{ all -> 0x026f }
            java.lang.String r7 = r7.scanSymbol(r8, r5)     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r8 = r11.lexer     // Catch:{ all -> 0x026f }
            r8.skipWhitespace()     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r8 = r11.lexer     // Catch:{ all -> 0x026f }
            char r8 = r8.getCurrent()     // Catch:{ all -> 0x026f }
            r2 = r8
            if (r2 != r4) goto L_0x00de
            goto L_0x017a
        L_0x00de:
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x026f }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x026f }
            r5.<init>()     // Catch:{ all -> 0x026f }
            r5.append(r3)     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r3 = r11.lexer     // Catch:{ all -> 0x026f }
            int r3 = r3.pos()     // Catch:{ all -> 0x026f }
            r5.append(r3)     // Catch:{ all -> 0x026f }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x026f }
            r4.<init>(r3)     // Catch:{ all -> 0x026f }
            throw r4     // Catch:{ all -> 0x026f }
        L_0x00f9:
            r7 = 125(0x7d, float:1.75E-43)
            if (r2 != r7) goto L_0x0111
            com.alibaba.fastjson.parser.JSONLexer r3 = r11.lexer     // Catch:{ all -> 0x026f }
            r3.next()     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r3 = r11.lexer     // Catch:{ all -> 0x026f }
            r3.resetStringPosition()     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r3 = r11.lexer     // Catch:{ all -> 0x026f }
            r3.nextToken(r6)     // Catch:{ all -> 0x026f }
            r11.setContext(r0)
            return r12
        L_0x0111:
            java.lang.String r7 = "syntax error"
            r8 = 39
            if (r2 != r8) goto L_0x015a
            com.alibaba.fastjson.parser.JSONLexer r9 = r11.lexer     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.Feature r10 = com.alibaba.fastjson.parser.Feature.AllowSingleQuotes     // Catch:{ all -> 0x026f }
            boolean r9 = r9.isEnabled((com.alibaba.fastjson.parser.Feature) r10)     // Catch:{ all -> 0x026f }
            if (r9 == 0) goto L_0x0154
            com.alibaba.fastjson.parser.JSONLexer r7 = r11.lexer     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.SymbolTable r9 = r11.symbolTable     // Catch:{ all -> 0x026f }
            java.lang.String r7 = r7.scanSymbol(r9, r8)     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r8 = r11.lexer     // Catch:{ all -> 0x026f }
            r8.skipWhitespace()     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r8 = r11.lexer     // Catch:{ all -> 0x026f }
            char r8 = r8.getCurrent()     // Catch:{ all -> 0x026f }
            r2 = r8
            if (r2 != r4) goto L_0x0139
            goto L_0x017a
        L_0x0139:
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x026f }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x026f }
            r5.<init>()     // Catch:{ all -> 0x026f }
            r5.append(r3)     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r3 = r11.lexer     // Catch:{ all -> 0x026f }
            int r3 = r3.pos()     // Catch:{ all -> 0x026f }
            r5.append(r3)     // Catch:{ all -> 0x026f }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x026f }
            r4.<init>(r3)     // Catch:{ all -> 0x026f }
            throw r4     // Catch:{ all -> 0x026f }
        L_0x0154:
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x026f }
            r3.<init>(r7)     // Catch:{ all -> 0x026f }
            throw r3     // Catch:{ all -> 0x026f }
        L_0x015a:
            com.alibaba.fastjson.parser.JSONLexer r8 = r11.lexer     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.Feature r9 = com.alibaba.fastjson.parser.Feature.AllowUnQuotedFieldNames     // Catch:{ all -> 0x026f }
            boolean r8 = r8.isEnabled((com.alibaba.fastjson.parser.Feature) r9)     // Catch:{ all -> 0x026f }
            if (r8 == 0) goto L_0x0269
            com.alibaba.fastjson.parser.JSONLexer r7 = r11.lexer     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.SymbolTable r8 = r11.symbolTable     // Catch:{ all -> 0x026f }
            java.lang.String r7 = r7.scanSymbolUnQuoted(r8)     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r8 = r11.lexer     // Catch:{ all -> 0x026f }
            r8.skipWhitespace()     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r8 = r11.lexer     // Catch:{ all -> 0x026f }
            char r8 = r8.getCurrent()     // Catch:{ all -> 0x026f }
            r2 = r8
            if (r2 != r4) goto L_0x0246
        L_0x017a:
            com.alibaba.fastjson.parser.JSONLexer r3 = r11.lexer     // Catch:{ all -> 0x026f }
            r3.next()     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r3 = r11.lexer     // Catch:{ all -> 0x026f }
            r3.skipWhitespace()     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r3 = r11.lexer     // Catch:{ all -> 0x026f }
            char r3 = r3.getCurrent()     // Catch:{ all -> 0x026f }
            r2 = r3
            com.alibaba.fastjson.parser.JSONLexer r3 = r11.lexer     // Catch:{ all -> 0x026f }
            r3.resetStringPosition()     // Catch:{ all -> 0x026f }
            java.lang.String r3 = com.alibaba.fastjson.JSON.DEFAULT_TYPE_KEY     // Catch:{ all -> 0x026f }
            r4 = 13
            if (r7 != r3) goto L_0x01f6
            com.alibaba.fastjson.parser.JSONLexer r3 = r11.lexer     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.Feature r8 = com.alibaba.fastjson.parser.Feature.DisableSpecialKeyDetect     // Catch:{ all -> 0x026f }
            boolean r3 = r3.isEnabled((com.alibaba.fastjson.parser.Feature) r8)     // Catch:{ all -> 0x026f }
            if (r3 != 0) goto L_0x01f6
            com.alibaba.fastjson.parser.JSONLexer r3 = r11.lexer     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.SymbolTable r8 = r11.symbolTable     // Catch:{ all -> 0x026f }
            java.lang.String r3 = r3.scanSymbol(r8, r5)     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.ParserConfig r5 = r11.config     // Catch:{ all -> 0x026f }
            r8 = 0
            com.alibaba.fastjson.parser.JSONLexer r9 = r11.lexer     // Catch:{ all -> 0x026f }
            int r9 = r9.getFeatures()     // Catch:{ all -> 0x026f }
            java.lang.Class r5 = r5.checkAutoType(r3, r8, r9)     // Catch:{ all -> 0x026f }
            java.lang.Class<java.util.Map> r8 = java.util.Map.class
            boolean r8 = r8.isAssignableFrom(r5)     // Catch:{ all -> 0x026f }
            if (r8 == 0) goto L_0x01d4
            com.alibaba.fastjson.parser.JSONLexer r8 = r11.lexer     // Catch:{ all -> 0x026f }
            r8.nextToken(r6)     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r8 = r11.lexer     // Catch:{ all -> 0x026f }
            int r8 = r8.token()     // Catch:{ all -> 0x026f }
            if (r8 != r4) goto L_0x023d
            com.alibaba.fastjson.parser.JSONLexer r4 = r11.lexer     // Catch:{ all -> 0x026f }
            r4.nextToken(r6)     // Catch:{ all -> 0x026f }
            r11.setContext(r0)
            return r12
        L_0x01d4:
            com.alibaba.fastjson.parser.ParserConfig r4 = r11.config     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.deserializer.ObjectDeserializer r4 = r4.getDeserializer((java.lang.reflect.Type) r5)     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r8 = r11.lexer     // Catch:{ all -> 0x026f }
            r8.nextToken(r6)     // Catch:{ all -> 0x026f }
            r6 = 2
            r11.setResolveStatus(r6)     // Catch:{ all -> 0x026f }
            if (r0 == 0) goto L_0x01ec
            boolean r6 = r13 instanceof java.lang.Integer     // Catch:{ all -> 0x026f }
            if (r6 != 0) goto L_0x01ec
            r11.popContext()     // Catch:{ all -> 0x026f }
        L_0x01ec:
            java.lang.Object r6 = r4.deserialze(r11, r5, r13)     // Catch:{ all -> 0x026f }
            java.util.Map r6 = (java.util.Map) r6     // Catch:{ all -> 0x026f }
            r11.setContext(r0)
            return r6
        L_0x01f6:
            com.alibaba.fastjson.parser.JSONLexer r3 = r11.lexer     // Catch:{ all -> 0x026f }
            r3.nextToken()     // Catch:{ all -> 0x026f }
            if (r1 == 0) goto L_0x0200
            r11.setContext(r0)     // Catch:{ all -> 0x026f }
        L_0x0200:
            java.lang.reflect.Type r3 = r12.getType(r7)     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r5 = r11.lexer     // Catch:{ all -> 0x026f }
            int r5 = r5.token()     // Catch:{ all -> 0x026f }
            r6 = 8
            if (r5 != r6) goto L_0x0215
            r5 = 0
            com.alibaba.fastjson.parser.JSONLexer r6 = r11.lexer     // Catch:{ all -> 0x026f }
            r6.nextToken()     // Catch:{ all -> 0x026f }
            goto L_0x0219
        L_0x0215:
            java.lang.Object r5 = r11.parseObject((java.lang.reflect.Type) r3, (java.lang.Object) r7)     // Catch:{ all -> 0x026f }
        L_0x0219:
            r12.apply(r7, r5)     // Catch:{ all -> 0x026f }
            r11.setContext(r0, r5, r7)     // Catch:{ all -> 0x026f }
            r11.setContext(r0)     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r6 = r11.lexer     // Catch:{ all -> 0x026f }
            int r6 = r6.token()     // Catch:{ all -> 0x026f }
            r8 = 20
            if (r6 == r8) goto L_0x0241
            r8 = 15
            if (r6 != r8) goto L_0x0231
            goto L_0x0241
        L_0x0231:
            if (r6 != r4) goto L_0x023d
            com.alibaba.fastjson.parser.JSONLexer r4 = r11.lexer     // Catch:{ all -> 0x026f }
            r4.nextToken()     // Catch:{ all -> 0x026f }
            r11.setContext(r0)
            return r12
        L_0x023d:
            int r1 = r1 + 1
            goto L_0x0091
        L_0x0241:
            r11.setContext(r0)
            return r12
        L_0x0246:
            com.alibaba.fastjson.JSONException r4 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x026f }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x026f }
            r5.<init>()     // Catch:{ all -> 0x026f }
            r5.append(r3)     // Catch:{ all -> 0x026f }
            com.alibaba.fastjson.parser.JSONLexer r3 = r11.lexer     // Catch:{ all -> 0x026f }
            int r3 = r3.pos()     // Catch:{ all -> 0x026f }
            r5.append(r3)     // Catch:{ all -> 0x026f }
            java.lang.String r3 = ", actual "
            r5.append(r3)     // Catch:{ all -> 0x026f }
            r5.append(r2)     // Catch:{ all -> 0x026f }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x026f }
            r4.<init>(r3)     // Catch:{ all -> 0x026f }
            throw r4     // Catch:{ all -> 0x026f }
        L_0x0269:
            com.alibaba.fastjson.JSONException r3 = new com.alibaba.fastjson.JSONException     // Catch:{ all -> 0x026f }
            r3.<init>(r7)     // Catch:{ all -> 0x026f }
            throw r3     // Catch:{ all -> 0x026f }
        L_0x026f:
            r1 = move-exception
            r11.setContext(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.DefaultJSONParser.parse(com.alibaba.fastjson.parser.deserializer.PropertyProcessable, java.lang.Object):java.lang.Object");
    }
}
