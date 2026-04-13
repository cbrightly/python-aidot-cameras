package com.alibaba.fastjson;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.FieldSerializer;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import com.google.android.libraries.places.api.model.PlaceTypes;
import com.google.maps.android.BuildConfig;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;
import org.slf4j.e;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class JSONPath implements JSONAware {
    static final long LENGTH = -1580386065683472715L;
    static final long SIZE = 5614464919154503228L;
    private static ConcurrentMap<String, JSONPath> pathCache = new ConcurrentHashMap(128, 0.75f, 1);
    private boolean hasRefSegment;
    private ParserConfig parserConfig;
    private final String path;
    private Segment[] segments;
    private SerializeConfig serializeConfig;

    public interface Filter {
        boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3);
    }

    public enum Operator {
        EQ,
        NE,
        GT,
        GE,
        LT,
        LE,
        LIKE,
        NOT_LIKE,
        RLIKE,
        NOT_RLIKE,
        IN,
        NOT_IN,
        BETWEEN,
        NOT_BETWEEN,
        And,
        Or,
        REG_MATCH
    }

    public interface Segment {
        Object eval(JSONPath jSONPath, Object obj, Object obj2);

        void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context);
    }

    public JSONPath(String path2) {
        this(path2, SerializeConfig.getGlobalInstance(), ParserConfig.getGlobalInstance());
    }

    public JSONPath(String path2, SerializeConfig serializeConfig2, ParserConfig parserConfig2) {
        if (path2 == null || path2.length() == 0) {
            throw new JSONPathException("json-path can not be null or empty");
        }
        this.path = path2;
        this.serializeConfig = serializeConfig2;
        this.parserConfig = parserConfig2;
    }

    /* access modifiers changed from: protected */
    public void init() {
        if (this.segments == null) {
            if (e.ANY_MARKER.equals(this.path)) {
                this.segments = new Segment[]{WildCardSegment.instance};
                return;
            }
            JSONPathParser parser = new JSONPathParser(this.path);
            this.segments = parser.explain();
            this.hasRefSegment = parser.hasRefSegment;
        }
    }

    public boolean isRef() {
        try {
            init();
            int i = 0;
            while (true) {
                Segment[] segmentArr = this.segments;
                if (i >= segmentArr.length) {
                    return true;
                }
                Class segmentType = segmentArr[i].getClass();
                if (segmentType != ArrayAccessSegment.class && segmentType != PropertySegment.class) {
                    return false;
                }
                i++;
            }
        } catch (JSONPathException e) {
            return false;
        }
    }

    public Object eval(Object rootObject) {
        if (rootObject == null) {
            return null;
        }
        init();
        Object currentObject = rootObject;
        int i = 0;
        while (true) {
            Segment[] segmentArr = this.segments;
            if (i >= segmentArr.length) {
                return currentObject;
            }
            currentObject = segmentArr[i].eval(this, rootObject, currentObject);
            i++;
        }
    }

    public Object extract(DefaultJSONParser parser) {
        boolean eval;
        Object obj;
        if (parser == null) {
            return null;
        }
        init();
        if (this.hasRefSegment) {
            return eval(parser.parse());
        }
        Segment[] segmentArr = this.segments;
        if (segmentArr.length == 0) {
            return parser.parse();
        }
        Segment lastSegment = segmentArr[segmentArr.length - 1];
        if ((lastSegment instanceof TypeSegment) || (lastSegment instanceof FloorSegment) || (lastSegment instanceof MultiIndexSegment)) {
            return eval(parser.parse());
        }
        Context context = null;
        int i = 0;
        while (true) {
            Segment[] segmentArr2 = this.segments;
            if (i >= segmentArr2.length) {
                return context.object;
            }
            Segment segment = segmentArr2[i];
            boolean last = i == segmentArr2.length - 1;
            if (context == null || (obj = context.object) == null) {
                if (!last) {
                    Segment nextSegment = segmentArr2[i + 1];
                    if ((segment instanceof PropertySegment) && ((PropertySegment) segment).deep && ((nextSegment instanceof ArrayAccessSegment) || (nextSegment instanceof MultiIndexSegment) || (nextSegment instanceof MultiPropertySegment) || (nextSegment instanceof SizeSegment) || (nextSegment instanceof PropertySegment) || (nextSegment instanceof FilterSegment))) {
                        eval = true;
                    } else if ((nextSegment instanceof ArrayAccessSegment) && ((ArrayAccessSegment) nextSegment).index < 0) {
                        eval = true;
                    } else if (nextSegment instanceof FilterSegment) {
                        eval = true;
                    } else if (segment instanceof WildCardSegment) {
                        eval = true;
                    } else if (segment instanceof MultiIndexSegment) {
                        eval = true;
                    } else {
                        eval = false;
                    }
                } else {
                    eval = true;
                }
                context = new Context(context, eval);
                segment.extract(this, parser, context);
            } else {
                context.object = segment.eval(this, (Object) null, obj);
            }
            i++;
        }
    }

    public static class Context {
        final boolean eval;
        Object object;
        final Context parent;

        public Context(Context parent2, boolean eval2) {
            this.parent = parent2;
            this.eval = eval2;
        }
    }

    public boolean contains(Object rootObject) {
        if (rootObject == null) {
            return false;
        }
        init();
        Object currentObject = rootObject;
        int i = 0;
        while (true) {
            Segment[] segmentArr = this.segments;
            if (i >= segmentArr.length) {
                return true;
            }
            Object parentObject = currentObject;
            currentObject = segmentArr[i].eval(this, rootObject, currentObject);
            if (currentObject == null) {
                return false;
            }
            if (currentObject == Collections.EMPTY_LIST && (parentObject instanceof List)) {
                return ((List) parentObject).contains(currentObject);
            }
            i++;
        }
    }

    public boolean containsValue(Object rootObject, Object value) {
        Object currentObject = eval(rootObject);
        if (currentObject == value) {
            return true;
        }
        if (currentObject == null) {
            return false;
        }
        if (!(currentObject instanceof Iterable)) {
            return eq(currentObject, value);
        }
        for (Object item : (Iterable) currentObject) {
            if (eq(item, value)) {
                return true;
            }
        }
        return false;
    }

    public int size(Object rootObject) {
        if (rootObject == null) {
            return -1;
        }
        init();
        Object currentObject = rootObject;
        int i = 0;
        while (true) {
            Segment[] segmentArr = this.segments;
            if (i >= segmentArr.length) {
                return evalSize(currentObject);
            }
            currentObject = segmentArr[i].eval(this, rootObject, currentObject);
            i++;
        }
    }

    public Set<?> keySet(Object rootObject) {
        if (rootObject == null) {
            return null;
        }
        init();
        Object currentObject = rootObject;
        int i = 0;
        while (true) {
            Segment[] segmentArr = this.segments;
            if (i >= segmentArr.length) {
                return evalKeySet(currentObject);
            }
            currentObject = segmentArr[i].eval(this, rootObject, currentObject);
            i++;
        }
    }

    public void patchAdd(Object rootObject, Object value, boolean replace) {
        Object newResult;
        if (rootObject != null) {
            init();
            Object currentObject = rootObject;
            Object parentObject = null;
            int i = 0;
            while (true) {
                Segment[] segmentArr = this.segments;
                if (i >= segmentArr.length) {
                    break;
                }
                parentObject = currentObject;
                Segment segment = segmentArr[i];
                currentObject = segment.eval(this, rootObject, currentObject);
                if (currentObject == null && i != this.segments.length - 1 && (segment instanceof PropertySegment)) {
                    currentObject = new JSONObject();
                    ((PropertySegment) segment).setValue(this, parentObject, currentObject);
                }
                i++;
            }
            Object result = currentObject;
            if (replace || !(result instanceof Collection)) {
                if (result == null || replace) {
                    newResult = value;
                } else {
                    Class<?> resultClass = result.getClass();
                    if (resultClass.isArray()) {
                        int length = Array.getLength(result);
                        Object descArray = Array.newInstance(resultClass.getComponentType(), length + 1);
                        System.arraycopy(result, 0, descArray, 0, length);
                        Array.set(descArray, length, value);
                        newResult = descArray;
                    } else if (Map.class.isAssignableFrom(resultClass)) {
                        newResult = value;
                    } else {
                        throw new JSONException("unsupported array put operation. " + resultClass);
                    }
                }
                Segment[] segmentArr2 = this.segments;
                Segment lastSegment = segmentArr2[segmentArr2.length - 1];
                if (lastSegment instanceof PropertySegment) {
                    ((PropertySegment) lastSegment).setValue(this, parentObject, newResult);
                } else if (lastSegment instanceof ArrayAccessSegment) {
                    ((ArrayAccessSegment) lastSegment).setValue(this, parentObject, newResult);
                } else {
                    throw new UnsupportedOperationException();
                }
            } else {
                ((Collection) result).add(value);
            }
        }
    }

    public void arrayAdd(Object rootObject, Object... values) {
        if (values != null && values.length != 0 && rootObject != null) {
            init();
            Object currentObject = rootObject;
            Object parentObject = null;
            int i = 0;
            while (true) {
                Segment[] segmentArr = this.segments;
                if (i >= segmentArr.length) {
                    break;
                }
                if (i == segmentArr.length - 1) {
                    parentObject = currentObject;
                }
                currentObject = segmentArr[i].eval(this, rootObject, currentObject);
                i++;
            }
            Object result = currentObject;
            if (result != null) {
                if (result instanceof Collection) {
                    Collection collection = (Collection) result;
                    for (Object value : values) {
                        collection.add(value);
                    }
                    return;
                }
                Class<?> resultClass = result.getClass();
                if (resultClass.isArray()) {
                    int length = Array.getLength(result);
                    Object descArray = Array.newInstance(resultClass.getComponentType(), values.length + length);
                    System.arraycopy(result, 0, descArray, 0, length);
                    for (int i2 = 0; i2 < values.length; i2++) {
                        Array.set(descArray, length + i2, values[i2]);
                    }
                    Object newResult = descArray;
                    Segment[] segmentArr2 = this.segments;
                    Segment lastSegment = segmentArr2[segmentArr2.length - 1];
                    if (lastSegment instanceof PropertySegment) {
                        ((PropertySegment) lastSegment).setValue(this, parentObject, newResult);
                    } else if (lastSegment instanceof ArrayAccessSegment) {
                        ((ArrayAccessSegment) lastSegment).setValue(this, parentObject, newResult);
                    } else {
                        throw new UnsupportedOperationException();
                    }
                } else {
                    throw new JSONException("unsupported array put operation. " + resultClass);
                }
            } else {
                throw new JSONPathException("value not found in path " + this.path);
            }
        }
    }

    public boolean remove(Object rootObject) {
        if (rootObject == null) {
            return false;
        }
        init();
        Object currentObject = rootObject;
        Object parentObject = null;
        Segment[] segmentArr = this.segments;
        Segment lastSegment = segmentArr[segmentArr.length - 1];
        int i = 0;
        while (true) {
            Segment[] segmentArr2 = this.segments;
            if (i >= segmentArr2.length) {
                break;
            } else if (i == segmentArr2.length - 1) {
                parentObject = currentObject;
                break;
            } else {
                Segment segement = segmentArr2[i];
                if (i == segmentArr2.length - 2 && (lastSegment instanceof FilterSegment) && (segement instanceof PropertySegment)) {
                    FilterSegment filterSegment = (FilterSegment) lastSegment;
                    if (currentObject instanceof List) {
                        PropertySegment propertySegment = (PropertySegment) segement;
                        Iterator it = ((List) currentObject).iterator();
                        while (it.hasNext()) {
                            Object result = propertySegment.eval(this, rootObject, it.next());
                            if (result instanceof Iterable) {
                                filterSegment.remove(this, rootObject, result);
                            } else if ((result instanceof Map) && filterSegment.filter.apply(this, rootObject, currentObject, result)) {
                                it.remove();
                            }
                        }
                        return true;
                    } else if (currentObject instanceof Map) {
                        PropertySegment propertySegment2 = (PropertySegment) segement;
                        Object result2 = propertySegment2.eval(this, rootObject, currentObject);
                        if (result2 == null) {
                            return false;
                        }
                        if ((result2 instanceof Map) && filterSegment.filter.apply(this, rootObject, currentObject, result2)) {
                            propertySegment2.remove(this, currentObject);
                            return true;
                        }
                    }
                }
                currentObject = segement.eval(this, rootObject, currentObject);
                if (currentObject == null) {
                    break;
                }
                i++;
            }
        }
        if (parentObject == null) {
            return false;
        }
        if (lastSegment instanceof PropertySegment) {
            PropertySegment propertySegment3 = (PropertySegment) lastSegment;
            if (parentObject instanceof Collection) {
                Segment[] segmentArr3 = this.segments;
                if (segmentArr3.length > 1) {
                    Segment parentSegment = segmentArr3[segmentArr3.length - 2];
                    if ((parentSegment instanceof RangeSegment) || (parentSegment instanceof MultiIndexSegment)) {
                        boolean removedOnce = false;
                        for (Object item : (Collection) parentObject) {
                            if (propertySegment3.remove(this, item)) {
                                removedOnce = true;
                            }
                        }
                        return removedOnce;
                    }
                }
            }
            return propertySegment3.remove(this, parentObject);
        } else if (lastSegment instanceof ArrayAccessSegment) {
            return ((ArrayAccessSegment) lastSegment).remove(this, parentObject);
        } else {
            if (lastSegment instanceof FilterSegment) {
                return ((FilterSegment) lastSegment).remove(this, rootObject, parentObject);
            }
            throw new UnsupportedOperationException();
        }
    }

    public boolean set(Object rootObject, Object value) {
        return set(rootObject, value, true);
    }

    public boolean set(Object rootObject, Object value, boolean p) {
        Object obj = rootObject;
        Object obj2 = value;
        if (obj == null) {
            return false;
        }
        init();
        Object currentObject = rootObject;
        Object parentObject = null;
        int i = 0;
        while (true) {
            Segment[] segmentArr = this.segments;
            if (i >= segmentArr.length) {
                break;
            }
            parentObject = currentObject;
            Segment segment = segmentArr[i];
            currentObject = segment.eval(this, obj, currentObject);
            if (currentObject == null) {
                Segment nextSegment = null;
                Segment[] segmentArr2 = this.segments;
                if (i < segmentArr2.length - 1) {
                    nextSegment = segmentArr2[i + 1];
                }
                Object newObj = null;
                if (nextSegment instanceof PropertySegment) {
                    JavaBeanDeserializer beanDeserializer = null;
                    Class<?> fieldClass = null;
                    if (segment instanceof PropertySegment) {
                        String propertyName = ((PropertySegment) segment).propertyName;
                        JavaBeanDeserializer parentBeanDeserializer = getJavaBeanDeserializer(parentObject.getClass());
                        if (parentBeanDeserializer != null) {
                            fieldClass = parentBeanDeserializer.getFieldDeserializer(propertyName).fieldInfo.fieldClass;
                            beanDeserializer = getJavaBeanDeserializer(fieldClass);
                        }
                    }
                    if (beanDeserializer == null) {
                        newObj = new JSONObject();
                    } else if (beanDeserializer.beanInfo.defaultConstructor == null) {
                        return false;
                    } else {
                        newObj = beanDeserializer.createInstance((DefaultJSONParser) null, (Type) fieldClass);
                    }
                } else if (nextSegment instanceof ArrayAccessSegment) {
                    newObj = new JSONArray();
                }
                if (newObj != null) {
                    if (!(segment instanceof PropertySegment)) {
                        if (!(segment instanceof ArrayAccessSegment)) {
                            break;
                        }
                        ((ArrayAccessSegment) segment).setValue(this, parentObject, newObj);
                        currentObject = newObj;
                    } else {
                        ((PropertySegment) segment).setValue(this, parentObject, newObj);
                        currentObject = newObj;
                    }
                } else {
                    break;
                }
            }
            i++;
        }
        if (parentObject == null) {
            return false;
        }
        Segment[] segmentArr3 = this.segments;
        Segment lastSegment = segmentArr3[segmentArr3.length - 1];
        if (lastSegment instanceof PropertySegment) {
            ((PropertySegment) lastSegment).setValue(this, parentObject, obj2);
            return true;
        } else if (lastSegment instanceof ArrayAccessSegment) {
            return ((ArrayAccessSegment) lastSegment).setValue(this, parentObject, obj2);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static Object eval(Object rootObject, String path2) {
        return compile(path2).eval(rootObject);
    }

    public static int size(Object rootObject, String path2) {
        JSONPath jsonpath = compile(path2);
        return jsonpath.evalSize(jsonpath.eval(rootObject));
    }

    public static Set<?> keySet(Object rootObject, String path2) {
        JSONPath jsonpath = compile(path2);
        return jsonpath.evalKeySet(jsonpath.eval(rootObject));
    }

    public static boolean contains(Object rootObject, String path2) {
        if (rootObject == null) {
            return false;
        }
        return compile(path2).contains(rootObject);
    }

    public static boolean containsValue(Object rootObject, String path2, Object value) {
        return compile(path2).containsValue(rootObject, value);
    }

    public static void arrayAdd(Object rootObject, String path2, Object... values) {
        compile(path2).arrayAdd(rootObject, values);
    }

    public static boolean set(Object rootObject, String path2, Object value) {
        return compile(path2).set(rootObject, value);
    }

    public static boolean remove(Object root, String path2) {
        return compile(path2).remove(root);
    }

    public static JSONPath compile(String path2) {
        if (path2 != null) {
            JSONPath jsonpath = (JSONPath) pathCache.get(path2);
            if (jsonpath != null) {
                return jsonpath;
            }
            JSONPath jsonpath2 = new JSONPath(path2);
            if (pathCache.size() >= 1024) {
                return jsonpath2;
            }
            pathCache.putIfAbsent(path2, jsonpath2);
            return (JSONPath) pathCache.get(path2);
        }
        throw new JSONPathException("jsonpath can not be null");
    }

    public static Object read(String json, String path2) {
        return compile(path2).eval(JSON.parse(json));
    }

    public static Object extract(String json, String path2, ParserConfig config, int features, Feature... optionFeatures) {
        DefaultJSONParser parser = new DefaultJSONParser(json, config, features | Feature.OrderedField.mask);
        Object result = compile(path2).extract(parser);
        parser.lexer.close();
        return result;
    }

    public static Object extract(String json, String path2) {
        return extract(json, path2, ParserConfig.global, JSON.DEFAULT_PARSER_FEATURE, new Feature[0]);
    }

    public static Map<String, Object> paths(Object javaObject) {
        return paths(javaObject, SerializeConfig.globalInstance);
    }

    public static Map<String, Object> paths(Object javaObject, SerializeConfig config) {
        Map<Object, String> values = new IdentityHashMap<>();
        Map<String, Object> paths = new HashMap<>();
        paths(values, paths, "/", javaObject, config);
        return paths;
    }

    private static void paths(Map<Object, String> values, Map<String, Object> paths, String parent, Object javaObject, SerializeConfig config) {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        StringBuilder sb4;
        if (javaObject != null) {
            if (values.put(javaObject, parent) != null) {
                Class<?> type = javaObject.getClass();
                if (!(type == String.class || type == Boolean.class || type == Character.class || type == UUID.class || type.isEnum() || (javaObject instanceof Number) || (javaObject instanceof Date))) {
                    return;
                }
            }
            paths.put(parent, javaObject);
            if (javaObject instanceof Map) {
                for (Map.Entry entry : ((Map) javaObject).entrySet()) {
                    Object key = entry.getKey();
                    if (key instanceof String) {
                        if (parent.equals("/")) {
                            sb4 = new StringBuilder();
                        } else {
                            sb4 = new StringBuilder();
                            sb4.append(parent);
                        }
                        sb4.append("/");
                        sb4.append(key);
                        paths(values, paths, sb4.toString(), entry.getValue(), config);
                    }
                }
            } else if (javaObject instanceof Collection) {
                int i = 0;
                for (Object item : (Collection) javaObject) {
                    if (parent.equals("/")) {
                        sb3 = new StringBuilder();
                    } else {
                        sb3 = new StringBuilder();
                        sb3.append(parent);
                    }
                    sb3.append("/");
                    sb3.append(i);
                    paths(values, paths, sb3.toString(), item, config);
                    i++;
                }
            } else {
                Class<?> clazz = javaObject.getClass();
                if (clazz.isArray()) {
                    int len = Array.getLength(javaObject);
                    for (int i2 = 0; i2 < len; i2++) {
                        Object item2 = Array.get(javaObject, i2);
                        if (parent.equals("/")) {
                            sb2 = new StringBuilder();
                        } else {
                            sb2 = new StringBuilder();
                            sb2.append(parent);
                        }
                        sb2.append("/");
                        sb2.append(i2);
                        paths(values, paths, sb2.toString(), item2, config);
                    }
                } else if (ParserConfig.isPrimitive2(clazz) == 0 && !clazz.isEnum()) {
                    ObjectSerializer serializer = config.getObjectWriter(clazz);
                    if (serializer instanceof JavaBeanSerializer) {
                        try {
                            for (Map.Entry<String, Object> entry2 : ((JavaBeanSerializer) serializer).getFieldValuesMap(javaObject).entrySet()) {
                                String key2 = entry2.getKey();
                                if (key2 instanceof String) {
                                    if (parent.equals("/")) {
                                        sb = new StringBuilder();
                                        sb.append("/");
                                        sb.append(key2);
                                    } else {
                                        sb = new StringBuilder();
                                        sb.append(parent);
                                        sb.append("/");
                                        sb.append(key2);
                                    }
                                    paths(values, paths, sb.toString(), entry2.getValue(), config);
                                }
                            }
                        } catch (Exception e) {
                            throw new JSONException("toJSON error", e);
                        }
                    }
                }
            }
        }
    }

    public String getPath() {
        return this.path;
    }

    public static class JSONPathParser {
        private static final Pattern strArrayPatternx = Pattern.compile(strArrayRegex);
        private static final String strArrayRegex = "'\\s*,\\s*'";
        private char ch;
        /* access modifiers changed from: private */
        public boolean hasRefSegment;
        private int level;
        private final String path;
        private int pos;

        public JSONPathParser(String path2) {
            this.path = path2;
            next();
        }

        /* access modifiers changed from: package-private */
        public void next() {
            String str = this.path;
            int i = this.pos;
            this.pos = i + 1;
            this.ch = str.charAt(i);
        }

        /* access modifiers changed from: package-private */
        public char getNextChar() {
            return this.path.charAt(this.pos);
        }

        /* access modifiers changed from: package-private */
        public boolean isEOF() {
            return this.pos >= this.path.length();
        }

        /* access modifiers changed from: package-private */
        public Segment readSegement() {
            if (this.level == 0 && this.path.length() == 1) {
                if (isDigitFirst(this.ch)) {
                    return new ArrayAccessSegment(this.ch - '0');
                }
                char c = this.ch;
                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    return new PropertySegment(Character.toString(c), false);
                }
            }
            while (!isEOF()) {
                skipWhitespace();
                char c2 = this.ch;
                if (c2 == '$') {
                    next();
                    skipWhitespace();
                    if (this.ch == '?') {
                        return new FilterSegment((Filter) parseArrayAccessFilter(false));
                    }
                } else if (c2 == '.' || c2 == '/') {
                    int c0 = this.ch;
                    boolean deep = false;
                    next();
                    if (c0 == 46 && this.ch == '.') {
                        next();
                        deep = true;
                        int length = this.path.length();
                        int i = this.pos;
                        if (length > i + 3 && this.ch == '[' && this.path.charAt(i) == '*' && this.path.charAt(this.pos + 1) == ']' && this.path.charAt(this.pos + 2) == '.') {
                            next();
                            next();
                            next();
                            next();
                        }
                    }
                    char c3 = this.ch;
                    if (c3 == '*') {
                        if (!isEOF()) {
                            next();
                        }
                        return deep ? WildCardSegment.instance_deep : WildCardSegment.instance;
                    } else if (isDigitFirst(c3)) {
                        return parseArrayAccess(false);
                    } else {
                        String propertyName = readName();
                        if (this.ch != '(') {
                            return new PropertySegment(propertyName, deep);
                        }
                        next();
                        if (this.ch == ')') {
                            if (!isEOF()) {
                                next();
                            }
                            if ("size".equals(propertyName) || "length".equals(propertyName)) {
                                return SizeSegment.instance;
                            }
                            if ("max".equals(propertyName)) {
                                return MaxSegment.instance;
                            }
                            if ("min".equals(propertyName)) {
                                return MinSegment.instance;
                            }
                            if ("keySet".equals(propertyName)) {
                                return KeySetSegment.instance;
                            }
                            if (IjkMediaMeta.IJKM_KEY_TYPE.equals(propertyName)) {
                                return TypeSegment.instance;
                            }
                            if (PlaceTypes.FLOOR.equals(propertyName)) {
                                return FloorSegment.instance;
                            }
                            throw new JSONPathException("not support jsonpath : " + this.path);
                        }
                        throw new JSONPathException("not support jsonpath : " + this.path);
                    }
                } else if (c2 == '[') {
                    return parseArrayAccess(true);
                } else {
                    if (this.level == 0) {
                        return new PropertySegment(readName(), false);
                    }
                    if (c2 == '?') {
                        return new FilterSegment((Filter) parseArrayAccessFilter(false));
                    }
                    throw new JSONPathException("not support jsonpath : " + this.path);
                }
            }
            return null;
        }

        public final void skipWhitespace() {
            while (true) {
                char c = this.ch;
                if (c > ' ') {
                    return;
                }
                if (c == ' ' || c == 13 || c == 10 || c == 9 || c == 12 || c == 8) {
                    next();
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public Segment parseArrayAccess(boolean acceptBracket) {
            Object object = parseArrayAccessFilter(acceptBracket);
            if (object instanceof Segment) {
                return (Segment) object;
            }
            return new FilterSegment((Filter) object);
        }

        /* JADX WARNING: type inference failed for: r12v32, types: [com.alibaba.fastjson.JSONPath$Filter] */
        /* JADX WARNING: type inference failed for: r8v47, types: [com.alibaba.fastjson.JSONPath$Filter] */
        /* JADX WARNING: type inference failed for: r20v8, types: [com.alibaba.fastjson.JSONPath$DoubleOpSegement] */
        /* JADX WARNING: type inference failed for: r20v9, types: [com.alibaba.fastjson.JSONPath$IntOpSegement] */
        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:154:0x0252, code lost:
            if (r10 == '|') goto L_0x0254;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:392:0x05c7, code lost:
            if (r3 == '|') goto L_0x05c9;
         */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Removed duplicated region for block: B:34:0x007a  */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x007e  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object parseArrayAccessFilter(boolean r29) {
            /*
                r28 = this;
                r0 = r28
                if (r29 == 0) goto L_0x0009
                r1 = 91
                r0.accept(r1)
            L_0x0009:
                r1 = 0
                r2 = 0
                char r3 = r0.ch
                r4 = 63
                r5 = 40
                if (r3 != r4) goto L_0x0026
                r28.next()
                r0.accept(r5)
                int r2 = r2 + 1
            L_0x001b:
                char r3 = r0.ch
                if (r3 != r5) goto L_0x0025
                r28.next()
                int r2 = r2 + 1
                goto L_0x001b
            L_0x0025:
                r1 = 1
            L_0x0026:
                r28.skipWhitespace()
                r3 = 34
                r4 = 39
                r6 = 64
                r7 = 47
                r8 = 46
                r10 = 92
                r11 = -1
                r12 = 93
                r15 = 1
                if (r1 != 0) goto L_0x0131
                char r5 = r0.ch
                boolean r5 = com.alibaba.fastjson.util.IOUtils.firstIdentifier(r5)
                if (r5 != 0) goto L_0x0131
                char r5 = r0.ch
                boolean r5 = java.lang.Character.isJavaIdentifierStart(r5)
                if (r5 != 0) goto L_0x0131
                char r5 = r0.ch
                if (r5 == r10) goto L_0x0131
                if (r5 != r6) goto L_0x0053
                goto L_0x0131
            L_0x0053:
                int r5 = r0.pos
                int r5 = r5 - r15
                char r13 = r0.ch
            L_0x0058:
                char r14 = r0.ch
                if (r14 == r12) goto L_0x0078
                if (r14 == r7) goto L_0x0078
                boolean r14 = r28.isEOF()
                if (r14 != 0) goto L_0x0078
                char r14 = r0.ch
                if (r14 != r8) goto L_0x006f
                if (r1 != 0) goto L_0x006f
                if (r1 != 0) goto L_0x006f
                if (r13 == r4) goto L_0x006f
                goto L_0x0078
            L_0x006f:
                if (r14 != r10) goto L_0x0074
                r28.next()
            L_0x0074:
                r28.next()
                goto L_0x0058
            L_0x0078:
                if (r29 == 0) goto L_0x007e
                int r7 = r0.pos
                int r7 = r7 - r15
                goto L_0x008b
            L_0x007e:
                char r14 = r0.ch
                if (r14 == r7) goto L_0x0088
                if (r14 != r8) goto L_0x0085
                goto L_0x0088
            L_0x0085:
                int r7 = r0.pos
                goto L_0x008b
            L_0x0088:
                int r7 = r0.pos
                int r7 = r7 - r15
            L_0x008b:
                java.lang.String r8 = r0.path
                java.lang.String r8 = r8.substring(r5, r7)
                int r14 = r8.indexOf(r10)
                if (r14 == 0) goto L_0x00d3
                java.lang.StringBuilder r14 = new java.lang.StringBuilder
                int r12 = r8.length()
                r14.<init>(r12)
                r12 = r14
                r14 = 0
            L_0x00a2:
                int r9 = r8.length()
                if (r14 >= r9) goto L_0x00cf
                char r9 = r8.charAt(r14)
                if (r9 != r10) goto L_0x00c8
                int r16 = r8.length()
                int r4 = r16 + -1
                if (r14 >= r4) goto L_0x00c8
                int r4 = r14 + 1
                char r4 = r8.charAt(r4)
                if (r4 == r6) goto L_0x00c2
                if (r9 == r10) goto L_0x00c2
                if (r9 != r3) goto L_0x00c8
            L_0x00c2:
                r12.append(r4)
                int r14 = r14 + 1
                goto L_0x00cb
            L_0x00c8:
                r12.append(r9)
            L_0x00cb:
                int r14 = r14 + r15
                r4 = 39
                goto L_0x00a2
            L_0x00cf:
                java.lang.String r8 = r12.toString()
            L_0x00d3:
                java.lang.String r3 = "\\."
                int r4 = r8.indexOf(r3)
                if (r4 == r11) goto L_0x011f
                r4 = 39
                if (r13 != r4) goto L_0x00fb
                int r4 = r8.length()
                r6 = 2
                if (r4 <= r6) goto L_0x00fb
                int r4 = r8.length()
                int r4 = r4 - r15
                char r4 = r8.charAt(r4)
                if (r4 != r13) goto L_0x00fb
                int r3 = r8.length()
                int r3 = r3 - r15
                java.lang.String r3 = r8.substring(r15, r3)
                goto L_0x0111
            L_0x00fb:
                java.lang.String r4 = "\\\\\\."
                java.lang.String r3 = r8.replaceAll(r4, r3)
                java.lang.String r4 = "\\-"
                int r4 = r3.indexOf(r4)
                if (r4 == r11) goto L_0x0111
                java.lang.String r4 = "\\\\-"
                java.lang.String r6 = "-"
                java.lang.String r3 = r3.replaceAll(r4, r6)
            L_0x0111:
                if (r1 == 0) goto L_0x0118
                r4 = 41
                r0.accept(r4)
            L_0x0118:
                com.alibaba.fastjson.JSONPath$PropertySegment r4 = new com.alibaba.fastjson.JSONPath$PropertySegment
                r6 = 0
                r4.<init>(r3, r6)
                return r4
            L_0x011f:
                com.alibaba.fastjson.JSONPath$Segment r3 = r0.buildArraySegement(r8)
                if (r29 == 0) goto L_0x0130
                boolean r4 = r28.isEOF()
                if (r4 != 0) goto L_0x0130
                r4 = 93
                r0.accept(r4)
            L_0x0130:
                return r3
            L_0x0131:
                r4 = 0
                char r5 = r0.ch
                if (r5 != r6) goto L_0x013d
                r28.next()
                r0.accept(r8)
                r4 = 1
            L_0x013d:
                java.lang.String r5 = r28.readName()
                r28.skipWhitespace()
                r6 = 124(0x7c, float:1.74E-43)
                r9 = 38
                r12 = 32
                if (r1 == 0) goto L_0x0173
                char r13 = r0.ch
                r14 = 41
                if (r13 != r14) goto L_0x0173
                r28.next()
                com.alibaba.fastjson.JSONPath$NotNullSegement r3 = new com.alibaba.fastjson.JSONPath$NotNullSegement
                r7 = 0
                r3.<init>(r5, r7)
            L_0x015b:
                char r7 = r0.ch
                if (r7 != r12) goto L_0x0163
                r28.next()
                goto L_0x015b
            L_0x0163:
                if (r7 == r9) goto L_0x0167
                if (r7 != r6) goto L_0x016b
            L_0x0167:
                com.alibaba.fastjson.JSONPath$Filter r3 = r0.filterRest(r3)
            L_0x016b:
                if (r29 == 0) goto L_0x0172
                r6 = 93
                r0.accept(r6)
            L_0x0172:
                return r3
            L_0x0173:
                if (r29 == 0) goto L_0x01c1
                char r13 = r0.ch
                r14 = 93
                if (r13 != r14) goto L_0x01c1
                boolean r3 = r28.isEOF()
                if (r3 == 0) goto L_0x0196
                java.lang.String r3 = "last"
                boolean r3 = r5.equals(r3)
                if (r3 == 0) goto L_0x0194
                com.alibaba.fastjson.JSONPath$MultiIndexSegment r3 = new com.alibaba.fastjson.JSONPath$MultiIndexSegment
                int[] r6 = new int[r15]
                r7 = 0
                r6[r7] = r11
                r3.<init>(r6)
                return r3
            L_0x0194:
                r7 = 0
                goto L_0x0197
            L_0x0196:
                r7 = 0
            L_0x0197:
                r28.next()
                com.alibaba.fastjson.JSONPath$NotNullSegement r3 = new com.alibaba.fastjson.JSONPath$NotNullSegement
                r3.<init>(r5, r7)
            L_0x019f:
                char r7 = r0.ch
                if (r7 != r12) goto L_0x01a7
                r28.next()
                goto L_0x019f
            L_0x01a7:
                if (r7 == r9) goto L_0x01ab
                if (r7 != r6) goto L_0x01af
            L_0x01ab:
                com.alibaba.fastjson.JSONPath$Filter r3 = r0.filterRest(r3)
            L_0x01af:
                r6 = 41
                r0.accept(r6)
                if (r1 == 0) goto L_0x01b9
                r0.accept(r6)
            L_0x01b9:
                if (r29 == 0) goto L_0x01c0
                r6 = 93
                r0.accept(r6)
            L_0x01c0:
                return r3
            L_0x01c1:
                r13 = 0
                r28.skipWhitespace()
                char r14 = r0.ch
                r11 = 40
                if (r14 != r11) goto L_0x01d7
                r28.next()
                r11 = 41
                r0.accept(r11)
                r28.skipWhitespace()
                r13 = 1
            L_0x01d7:
                com.alibaba.fastjson.JSONPath$Operator r11 = r28.readOp()
                r28.skipWhitespace()
                com.alibaba.fastjson.JSONPath$Operator r14 = com.alibaba.fastjson.JSONPath.Operator.BETWEEN
                if (r11 == r14) goto L_0x0714
                com.alibaba.fastjson.JSONPath$Operator r14 = com.alibaba.fastjson.JSONPath.Operator.NOT_BETWEEN
                if (r11 != r14) goto L_0x01ec
                r19 = r2
                r12 = r15
                r2 = 0
                goto L_0x0718
            L_0x01ec:
                com.alibaba.fastjson.JSONPath$Operator r14 = com.alibaba.fastjson.JSONPath.Operator.IN
                if (r11 == r14) goto L_0x04ec
                com.alibaba.fastjson.JSONPath$Operator r14 = com.alibaba.fastjson.JSONPath.Operator.NOT_IN
                if (r11 != r14) goto L_0x01f6
                goto L_0x04ec
            L_0x01f6:
                char r14 = r0.ch
                r10 = 39
                if (r14 == r10) goto L_0x03de
                if (r14 != r3) goto L_0x0200
                goto L_0x03de
            L_0x0200:
                boolean r3 = isDigitFirst(r14)
                if (r3 == 0) goto L_0x0269
                long r6 = r28.readLongValue()
                r16 = 0
                char r10 = r0.ch
                if (r10 != r8) goto L_0x0214
                double r16 = r0.readDoubleValue(r6)
            L_0x0214:
                r18 = 0
                int r8 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
                if (r8 != 0) goto L_0x022a
                com.alibaba.fastjson.JSONPath$IntOpSegement r8 = new com.alibaba.fastjson.JSONPath$IntOpSegement
                r20 = r8
                r21 = r5
                r22 = r13
                r23 = r6
                r25 = r11
                r20.<init>(r21, r22, r23, r25)
                goto L_0x0239
            L_0x022a:
                com.alibaba.fastjson.JSONPath$DoubleOpSegement r8 = new com.alibaba.fastjson.JSONPath$DoubleOpSegement
                r20 = r8
                r21 = r5
                r22 = r13
                r23 = r16
                r25 = r11
                r20.<init>(r21, r22, r23, r25)
            L_0x0239:
                char r10 = r0.ch
                if (r10 != r12) goto L_0x0241
                r28.next()
                goto L_0x0239
            L_0x0241:
                if (r2 <= r15) goto L_0x024c
                r12 = 41
                if (r10 != r12) goto L_0x024c
                r28.next()
                int r2 = r2 + -1
            L_0x024c:
                char r10 = r0.ch
                if (r10 == r9) goto L_0x0254
                r3 = 124(0x7c, float:1.74E-43)
                if (r10 != r3) goto L_0x0258
            L_0x0254:
                com.alibaba.fastjson.JSONPath$Filter r8 = r0.filterRest(r8)
            L_0x0258:
                if (r1 == 0) goto L_0x0261
                int r2 = r2 + -1
                r3 = 41
                r0.accept(r3)
            L_0x0261:
                if (r29 == 0) goto L_0x0268
                r3 = 93
                r0.accept(r3)
            L_0x0268:
                return r8
            L_0x0269:
                char r6 = r0.ch
                r8 = 36
                if (r6 != r8) goto L_0x0291
                com.alibaba.fastjson.JSONPath$Segment r3 = r28.readSegement()
                com.alibaba.fastjson.JSONPath$RefOpSegement r6 = new com.alibaba.fastjson.JSONPath$RefOpSegement
                r6.<init>(r5, r13, r3, r11)
                r0.hasRefSegment = r15
            L_0x027a:
                char r7 = r0.ch
                if (r7 != r12) goto L_0x0282
                r28.next()
                goto L_0x027a
            L_0x0282:
                if (r1 == 0) goto L_0x0289
                r7 = 41
                r0.accept(r7)
            L_0x0289:
                if (r29 == 0) goto L_0x0290
                r7 = 93
                r0.accept(r7)
            L_0x0290:
                return r6
            L_0x0291:
                if (r6 != r7) goto L_0x02dc
                r8 = 0
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                r10 = r3
            L_0x029a:
                r28.next()
                char r3 = r0.ch
                if (r3 != r7) goto L_0x02cb
                r28.next()
                char r3 = r0.ch
                r6 = 105(0x69, float:1.47E-43)
                if (r3 != r6) goto L_0x02af
                r28.next()
                r8 = r8 | 2
            L_0x02af:
                java.lang.String r3 = r10.toString()
                java.util.regex.Pattern r3 = java.util.regex.Pattern.compile(r3, r8)
                com.alibaba.fastjson.JSONPath$RegMatchSegement r6 = new com.alibaba.fastjson.JSONPath$RegMatchSegement
                r6.<init>(r5, r13, r3, r11)
                if (r1 == 0) goto L_0x02c3
                r7 = 41
                r0.accept(r7)
            L_0x02c3:
                if (r29 == 0) goto L_0x02ca
                r7 = 93
                r0.accept(r7)
            L_0x02ca:
                return r6
            L_0x02cb:
                r14 = 92
                if (r3 != r14) goto L_0x02d8
                r28.next()
                char r3 = r0.ch
                r10.append(r3)
                goto L_0x029a
            L_0x02d8:
                r10.append(r3)
                goto L_0x029a
            L_0x02dc:
                r7 = 110(0x6e, float:1.54E-43)
                if (r6 != r7) goto L_0x032d
                java.lang.String r6 = r28.readName()
                java.lang.String r7 = "null"
                boolean r7 = r7.equals(r6)
                if (r7 == 0) goto L_0x032b
                r7 = 0
                com.alibaba.fastjson.JSONPath$Operator r8 = com.alibaba.fastjson.JSONPath.Operator.EQ
                if (r11 != r8) goto L_0x02f8
                com.alibaba.fastjson.JSONPath$NullSegement r8 = new com.alibaba.fastjson.JSONPath$NullSegement
                r8.<init>(r5, r13)
                r7 = r8
                goto L_0x0302
            L_0x02f8:
                com.alibaba.fastjson.JSONPath$Operator r8 = com.alibaba.fastjson.JSONPath.Operator.NE
                if (r11 != r8) goto L_0x0302
                com.alibaba.fastjson.JSONPath$NotNullSegement r8 = new com.alibaba.fastjson.JSONPath$NotNullSegement
                r8.<init>(r5, r13)
                r7 = r8
            L_0x0302:
                if (r7 == 0) goto L_0x0316
            L_0x0304:
                char r8 = r0.ch
                if (r8 != r12) goto L_0x030c
                r28.next()
                goto L_0x0304
            L_0x030c:
                if (r8 == r9) goto L_0x0312
                r3 = 124(0x7c, float:1.74E-43)
                if (r8 != r3) goto L_0x0316
            L_0x0312:
                com.alibaba.fastjson.JSONPath$Filter r7 = r0.filterRest(r7)
            L_0x0316:
                if (r1 == 0) goto L_0x031d
                r3 = 41
                r0.accept(r3)
            L_0x031d:
                r3 = 93
                r0.accept(r3)
                if (r7 == 0) goto L_0x0325
                return r7
            L_0x0325:
                java.lang.UnsupportedOperationException r3 = new java.lang.UnsupportedOperationException
                r3.<init>()
                throw r3
            L_0x032b:
                goto L_0x03d8
            L_0x032d:
                r7 = 116(0x74, float:1.63E-43)
                if (r6 != r7) goto L_0x0383
                java.lang.String r6 = r28.readName()
                java.lang.String r7 = "true"
                boolean r7 = r7.equals(r6)
                if (r7 == 0) goto L_0x0382
                r7 = 0
                com.alibaba.fastjson.JSONPath$Operator r8 = com.alibaba.fastjson.JSONPath.Operator.EQ
                if (r11 != r8) goto L_0x034c
                com.alibaba.fastjson.JSONPath$ValueSegment r8 = new com.alibaba.fastjson.JSONPath$ValueSegment
                java.lang.Boolean r10 = java.lang.Boolean.TRUE
                r8.<init>(r5, r13, r10, r15)
                r7 = r8
                goto L_0x0359
            L_0x034c:
                com.alibaba.fastjson.JSONPath$Operator r8 = com.alibaba.fastjson.JSONPath.Operator.NE
                if (r11 != r8) goto L_0x0359
                com.alibaba.fastjson.JSONPath$ValueSegment r8 = new com.alibaba.fastjson.JSONPath$ValueSegment
                java.lang.Boolean r10 = java.lang.Boolean.TRUE
                r14 = 0
                r8.<init>(r5, r13, r10, r14)
                r7 = r8
            L_0x0359:
                if (r7 == 0) goto L_0x036d
            L_0x035b:
                char r8 = r0.ch
                if (r8 != r12) goto L_0x0363
                r28.next()
                goto L_0x035b
            L_0x0363:
                if (r8 == r9) goto L_0x0369
                r3 = 124(0x7c, float:1.74E-43)
                if (r8 != r3) goto L_0x036d
            L_0x0369:
                com.alibaba.fastjson.JSONPath$Filter r7 = r0.filterRest(r7)
            L_0x036d:
                if (r1 == 0) goto L_0x0374
                r3 = 41
                r0.accept(r3)
            L_0x0374:
                r3 = 93
                r0.accept(r3)
                if (r7 == 0) goto L_0x037c
                return r7
            L_0x037c:
                java.lang.UnsupportedOperationException r3 = new java.lang.UnsupportedOperationException
                r3.<init>()
                throw r3
            L_0x0382:
                goto L_0x03d7
            L_0x0383:
                r7 = 102(0x66, float:1.43E-43)
                if (r6 != r7) goto L_0x03d7
                java.lang.String r6 = r28.readName()
                java.lang.String r7 = "false"
                boolean r7 = r7.equals(r6)
                if (r7 == 0) goto L_0x03d8
                r7 = 0
                com.alibaba.fastjson.JSONPath$Operator r8 = com.alibaba.fastjson.JSONPath.Operator.EQ
                if (r11 != r8) goto L_0x03a1
                com.alibaba.fastjson.JSONPath$ValueSegment r8 = new com.alibaba.fastjson.JSONPath$ValueSegment
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                r8.<init>(r5, r13, r10, r15)
                r7 = r8
                goto L_0x03ae
            L_0x03a1:
                com.alibaba.fastjson.JSONPath$Operator r8 = com.alibaba.fastjson.JSONPath.Operator.NE
                if (r11 != r8) goto L_0x03ae
                com.alibaba.fastjson.JSONPath$ValueSegment r8 = new com.alibaba.fastjson.JSONPath$ValueSegment
                java.lang.Boolean r10 = java.lang.Boolean.FALSE
                r14 = 0
                r8.<init>(r5, r13, r10, r14)
                r7 = r8
            L_0x03ae:
                if (r7 == 0) goto L_0x03c2
            L_0x03b0:
                char r8 = r0.ch
                if (r8 != r12) goto L_0x03b8
                r28.next()
                goto L_0x03b0
            L_0x03b8:
                if (r8 == r9) goto L_0x03be
                r3 = 124(0x7c, float:1.74E-43)
                if (r8 != r3) goto L_0x03c2
            L_0x03be:
                com.alibaba.fastjson.JSONPath$Filter r7 = r0.filterRest(r7)
            L_0x03c2:
                if (r1 == 0) goto L_0x03c9
                r3 = 41
                r0.accept(r3)
            L_0x03c9:
                r3 = 93
                r0.accept(r3)
                if (r7 == 0) goto L_0x03d1
                return r7
            L_0x03d1:
                java.lang.UnsupportedOperationException r3 = new java.lang.UnsupportedOperationException
                r3.<init>()
                throw r3
            L_0x03d7:
            L_0x03d8:
                java.lang.UnsupportedOperationException r3 = new java.lang.UnsupportedOperationException
                r3.<init>()
                throw r3
            L_0x03de:
                java.lang.String r6 = r28.readString()
                r7 = 0
                com.alibaba.fastjson.JSONPath$Operator r8 = com.alibaba.fastjson.JSONPath.Operator.RLIKE
                if (r11 != r8) goto L_0x03f0
                com.alibaba.fastjson.JSONPath$RlikeSegement r8 = new com.alibaba.fastjson.JSONPath$RlikeSegement
                r10 = 0
                r8.<init>(r5, r13, r6, r10)
                r7 = r8
                goto L_0x04c7
            L_0x03f0:
                com.alibaba.fastjson.JSONPath$Operator r8 = com.alibaba.fastjson.JSONPath.Operator.NOT_RLIKE
                if (r11 != r8) goto L_0x03fc
                com.alibaba.fastjson.JSONPath$RlikeSegement r8 = new com.alibaba.fastjson.JSONPath$RlikeSegement
                r8.<init>(r5, r13, r6, r15)
                r7 = r8
                goto L_0x04c7
            L_0x03fc:
                com.alibaba.fastjson.JSONPath$Operator r8 = com.alibaba.fastjson.JSONPath.Operator.LIKE
                if (r11 == r8) goto L_0x040d
                com.alibaba.fastjson.JSONPath$Operator r8 = com.alibaba.fastjson.JSONPath.Operator.NOT_LIKE
                if (r11 != r8) goto L_0x0405
                goto L_0x040d
            L_0x0405:
                com.alibaba.fastjson.JSONPath$StringOpSegement r8 = new com.alibaba.fastjson.JSONPath$StringOpSegement
                r8.<init>(r5, r13, r6, r11)
                r7 = r8
                goto L_0x04c7
            L_0x040d:
                java.lang.String r8 = "%%"
                int r10 = r6.indexOf(r8)
                java.lang.String r14 = "%"
                r3 = -1
                if (r10 == r3) goto L_0x041d
                java.lang.String r6 = r6.replaceAll(r8, r14)
                goto L_0x040d
            L_0x041d:
                com.alibaba.fastjson.JSONPath$Operator r3 = com.alibaba.fastjson.JSONPath.Operator.NOT_LIKE
                if (r11 != r3) goto L_0x0424
                r26 = r15
                goto L_0x0426
            L_0x0424:
                r26 = 0
            L_0x0426:
                r3 = 37
                int r8 = r6.indexOf(r3)
                r10 = -1
                if (r8 != r10) goto L_0x0441
                com.alibaba.fastjson.JSONPath$Operator r3 = com.alibaba.fastjson.JSONPath.Operator.LIKE
                if (r11 != r3) goto L_0x0436
                com.alibaba.fastjson.JSONPath$Operator r3 = com.alibaba.fastjson.JSONPath.Operator.EQ
                goto L_0x0438
            L_0x0436:
                com.alibaba.fastjson.JSONPath$Operator r3 = com.alibaba.fastjson.JSONPath.Operator.NE
            L_0x0438:
                com.alibaba.fastjson.JSONPath$StringOpSegement r10 = new com.alibaba.fastjson.JSONPath$StringOpSegement
                r10.<init>(r5, r13, r6, r3)
                r7 = r10
                r11 = r3
                goto L_0x04c6
            L_0x0441:
                java.lang.String[] r10 = r6.split(r14)
                r14 = 0
                r19 = 0
                r20 = 0
                if (r8 != 0) goto L_0x0477
                int r21 = r6.length()
                int r9 = r21 + -1
                char r9 = r6.charAt(r9)
                if (r9 != r3) goto L_0x0462
                int r3 = r10.length
                int r3 = r3 - r15
                java.lang.String[] r3 = new java.lang.String[r3]
                int r9 = r3.length
                r12 = 0
                java.lang.System.arraycopy(r10, r15, r3, r12, r9)
                goto L_0x04b4
            L_0x0462:
                int r3 = r10.length
                int r3 = r3 - r15
                r19 = r10[r3]
                int r3 = r10.length
                r9 = 2
                if (r3 <= r9) goto L_0x0474
                int r3 = r10.length
                int r3 = r3 - r9
                java.lang.String[] r3 = new java.lang.String[r3]
                int r9 = r3.length
                r12 = 0
                java.lang.System.arraycopy(r10, r15, r3, r12, r9)
                goto L_0x04b4
            L_0x0474:
                r3 = r20
                goto L_0x04b4
            L_0x0477:
                int r9 = r6.length()
                int r9 = r9 - r15
                char r9 = r6.charAt(r9)
                if (r9 != r3) goto L_0x0490
                int r3 = r10.length
                if (r3 != r15) goto L_0x048b
                r3 = 0
                r14 = r10[r3]
                r3 = r20
                goto L_0x04b4
            L_0x048b:
                r20 = r10
                r3 = r20
                goto L_0x04b4
            L_0x0490:
                r3 = 0
                int r9 = r10.length
                if (r9 != r15) goto L_0x0499
                r14 = r10[r3]
                r3 = r20
                goto L_0x04b4
            L_0x0499:
                int r9 = r10.length
                r12 = 2
                if (r9 != r12) goto L_0x04a4
                r14 = r10[r3]
                r19 = r10[r15]
                r3 = r20
                goto L_0x04b4
            L_0x04a4:
                r14 = r10[r3]
                int r9 = r10.length
                int r9 = r9 - r15
                r19 = r10[r9]
                int r9 = r10.length
                r12 = 2
                int r9 = r9 - r12
                java.lang.String[] r9 = new java.lang.String[r9]
                int r12 = r9.length
                java.lang.System.arraycopy(r10, r15, r9, r3, r12)
                r3 = r9
            L_0x04b4:
                com.alibaba.fastjson.JSONPath$MatchSegement r9 = new com.alibaba.fastjson.JSONPath$MatchSegement
                r20 = r9
                r21 = r5
                r22 = r13
                r23 = r14
                r24 = r19
                r25 = r3
                r20.<init>(r21, r22, r23, r24, r25, r26)
                r7 = r9
            L_0x04c6:
            L_0x04c7:
                char r3 = r0.ch
                r8 = 32
                if (r3 != r8) goto L_0x04d1
                r28.next()
                goto L_0x04c7
            L_0x04d1:
                r8 = 38
                if (r3 == r8) goto L_0x04d9
                r8 = 124(0x7c, float:1.74E-43)
                if (r3 != r8) goto L_0x04dd
            L_0x04d9:
                com.alibaba.fastjson.JSONPath$Filter r7 = r0.filterRest(r7)
            L_0x04dd:
                if (r1 == 0) goto L_0x04e4
                r3 = 41
                r0.accept(r3)
            L_0x04e4:
                if (r29 == 0) goto L_0x04eb
                r3 = 93
                r0.accept(r3)
            L_0x04eb:
                return r7
            L_0x04ec:
                com.alibaba.fastjson.JSONPath$Operator r6 = com.alibaba.fastjson.JSONPath.Operator.NOT_IN
                if (r11 != r6) goto L_0x04f2
                r6 = r15
                goto L_0x04f3
            L_0x04f2:
                r6 = 0
            L_0x04f3:
                r7 = 40
                r0.accept(r7)
                com.alibaba.fastjson.JSONArray r7 = new com.alibaba.fastjson.JSONArray
                r7.<init>()
                java.lang.Object r8 = r28.readValue()
                r7.add(r8)
            L_0x0504:
                r28.skipWhitespace()
                char r9 = r0.ch
                r10 = 44
                if (r9 == r10) goto L_0x06f7
                r8 = 1
                r9 = 1
                r10 = 1
                java.util.Iterator r12 = r7.iterator()
            L_0x0515:
                boolean r14 = r12.hasNext()
                if (r14 == 0) goto L_0x0546
                java.lang.Object r14 = r12.next()
                if (r14 != 0) goto L_0x0525
                if (r8 == 0) goto L_0x0515
                r8 = 0
                goto L_0x0515
            L_0x0525:
                java.lang.Class r3 = r14.getClass()
                if (r8 == 0) goto L_0x053d
                java.lang.Class<java.lang.Byte> r15 = java.lang.Byte.class
                if (r3 == r15) goto L_0x053d
                java.lang.Class<java.lang.Short> r15 = java.lang.Short.class
                if (r3 == r15) goto L_0x053d
                java.lang.Class<java.lang.Integer> r15 = java.lang.Integer.class
                if (r3 == r15) goto L_0x053d
                java.lang.Class<java.lang.Long> r15 = java.lang.Long.class
                if (r3 == r15) goto L_0x053d
                r8 = 0
                r9 = 0
            L_0x053d:
                if (r10 == 0) goto L_0x0544
                java.lang.Class<java.lang.String> r15 = java.lang.String.class
                if (r3 == r15) goto L_0x0544
                r10 = 0
            L_0x0544:
                r15 = 1
                goto L_0x0515
            L_0x0546:
                int r3 = r7.size()
                r12 = 1
                if (r3 != r12) goto L_0x0589
                r3 = 0
                java.lang.Object r12 = r7.get(r3)
                if (r12 != 0) goto L_0x0589
                if (r6 == 0) goto L_0x055c
                com.alibaba.fastjson.JSONPath$NotNullSegement r3 = new com.alibaba.fastjson.JSONPath$NotNullSegement
                r3.<init>(r5, r13)
                goto L_0x0561
            L_0x055c:
                com.alibaba.fastjson.JSONPath$NullSegement r3 = new com.alibaba.fastjson.JSONPath$NullSegement
                r3.<init>(r5, r13)
            L_0x0561:
                char r12 = r0.ch
                r14 = 32
                if (r12 != r14) goto L_0x056b
                r28.next()
                goto L_0x0561
            L_0x056b:
                r14 = 38
                if (r12 == r14) goto L_0x0573
                r14 = 124(0x7c, float:1.74E-43)
                if (r12 != r14) goto L_0x0577
            L_0x0573:
                com.alibaba.fastjson.JSONPath$Filter r3 = r0.filterRest(r3)
            L_0x0577:
                r12 = 41
                r0.accept(r12)
                if (r1 == 0) goto L_0x0581
                r0.accept(r12)
            L_0x0581:
                if (r29 == 0) goto L_0x0588
                r12 = 93
                r0.accept(r12)
            L_0x0588:
                return r3
            L_0x0589:
                if (r8 == 0) goto L_0x0627
                int r12 = r7.size()
                r14 = 1
                if (r12 != r14) goto L_0x05df
                r12 = 0
                java.lang.Object r12 = r7.get(r12)
                java.lang.Number r12 = (java.lang.Number) r12
                long r14 = com.alibaba.fastjson.util.TypeUtils.longExtractValue(r12)
                if (r6 == 0) goto L_0x05a2
                com.alibaba.fastjson.JSONPath$Operator r12 = com.alibaba.fastjson.JSONPath.Operator.NE
                goto L_0x05a4
            L_0x05a2:
                com.alibaba.fastjson.JSONPath$Operator r12 = com.alibaba.fastjson.JSONPath.Operator.EQ
            L_0x05a4:
                r25 = r12
                com.alibaba.fastjson.JSONPath$IntOpSegement r12 = new com.alibaba.fastjson.JSONPath$IntOpSegement
                r20 = r12
                r21 = r5
                r22 = r13
                r23 = r14
                r20.<init>(r21, r22, r23, r25)
            L_0x05b3:
                char r3 = r0.ch
                r19 = r2
                r2 = 32
                if (r3 != r2) goto L_0x05c1
                r28.next()
                r2 = r19
                goto L_0x05b3
            L_0x05c1:
                r2 = 38
                if (r3 == r2) goto L_0x05c9
                r2 = 124(0x7c, float:1.74E-43)
                if (r3 != r2) goto L_0x05cd
            L_0x05c9:
                com.alibaba.fastjson.JSONPath$Filter r12 = r0.filterRest(r12)
            L_0x05cd:
                r2 = 41
                r0.accept(r2)
                if (r1 == 0) goto L_0x05d7
                r0.accept(r2)
            L_0x05d7:
                if (r29 == 0) goto L_0x05de
                r2 = 93
                r0.accept(r2)
            L_0x05de:
                return r12
            L_0x05df:
                r19 = r2
                int r2 = r7.size()
                long[] r2 = new long[r2]
                r12 = 0
            L_0x05e8:
                int r14 = r2.length
                if (r12 >= r14) goto L_0x05fa
                java.lang.Object r14 = r7.get(r12)
                java.lang.Number r14 = (java.lang.Number) r14
                long r14 = com.alibaba.fastjson.util.TypeUtils.longExtractValue(r14)
                r2[r12] = r14
                int r12 = r12 + 1
                goto L_0x05e8
            L_0x05fa:
                com.alibaba.fastjson.JSONPath$IntInSegement r12 = new com.alibaba.fastjson.JSONPath$IntInSegement
                r12.<init>(r5, r13, r2, r6)
            L_0x05ff:
                char r14 = r0.ch
                r15 = 32
                if (r14 != r15) goto L_0x0609
                r28.next()
                goto L_0x05ff
            L_0x0609:
                r15 = 38
                if (r14 == r15) goto L_0x0611
                r3 = 124(0x7c, float:1.74E-43)
                if (r14 != r3) goto L_0x0615
            L_0x0611:
                com.alibaba.fastjson.JSONPath$Filter r12 = r0.filterRest(r12)
            L_0x0615:
                r3 = 41
                r0.accept(r3)
                if (r1 == 0) goto L_0x061f
                r0.accept(r3)
            L_0x061f:
                if (r29 == 0) goto L_0x0626
                r3 = 93
                r0.accept(r3)
            L_0x0626:
                return r12
            L_0x0627:
                r19 = r2
                if (r10 == 0) goto L_0x06a3
                int r2 = r7.size()
                r12 = 1
                if (r2 != r12) goto L_0x066d
                r2 = 0
                java.lang.Object r2 = r7.get(r2)
                java.lang.String r2 = (java.lang.String) r2
                if (r6 == 0) goto L_0x063e
                com.alibaba.fastjson.JSONPath$Operator r12 = com.alibaba.fastjson.JSONPath.Operator.NE
                goto L_0x0640
            L_0x063e:
                com.alibaba.fastjson.JSONPath$Operator r12 = com.alibaba.fastjson.JSONPath.Operator.EQ
            L_0x0640:
                com.alibaba.fastjson.JSONPath$StringOpSegement r14 = new com.alibaba.fastjson.JSONPath$StringOpSegement
                r14.<init>(r5, r13, r2, r12)
            L_0x0645:
                char r15 = r0.ch
                r3 = 32
                if (r15 != r3) goto L_0x064f
                r28.next()
                goto L_0x0645
            L_0x064f:
                r3 = 38
                if (r15 == r3) goto L_0x0657
                r3 = 124(0x7c, float:1.74E-43)
                if (r15 != r3) goto L_0x065b
            L_0x0657:
                com.alibaba.fastjson.JSONPath$Filter r14 = r0.filterRest(r14)
            L_0x065b:
                r3 = 41
                r0.accept(r3)
                if (r1 == 0) goto L_0x0665
                r0.accept(r3)
            L_0x0665:
                if (r29 == 0) goto L_0x066c
                r3 = 93
                r0.accept(r3)
            L_0x066c:
                return r14
            L_0x066d:
                int r2 = r7.size()
                java.lang.String[] r2 = new java.lang.String[r2]
                r7.toArray(r2)
                com.alibaba.fastjson.JSONPath$StringInSegement r12 = new com.alibaba.fastjson.JSONPath$StringInSegement
                r12.<init>(r5, r13, r2, r6)
            L_0x067b:
                char r14 = r0.ch
                r15 = 32
                if (r14 != r15) goto L_0x0685
                r28.next()
                goto L_0x067b
            L_0x0685:
                r15 = 38
                if (r14 == r15) goto L_0x068d
                r3 = 124(0x7c, float:1.74E-43)
                if (r14 != r3) goto L_0x0691
            L_0x068d:
                com.alibaba.fastjson.JSONPath$Filter r12 = r0.filterRest(r12)
            L_0x0691:
                r3 = 41
                r0.accept(r3)
                if (r1 == 0) goto L_0x069b
                r0.accept(r3)
            L_0x069b:
                if (r29 == 0) goto L_0x06a2
                r3 = 93
                r0.accept(r3)
            L_0x06a2:
                return r12
            L_0x06a3:
                if (r9 == 0) goto L_0x06f1
                int r2 = r7.size()
                java.lang.Long[] r2 = new java.lang.Long[r2]
                r12 = 0
            L_0x06ac:
                int r14 = r2.length
                if (r12 >= r14) goto L_0x06c4
                java.lang.Object r14 = r7.get(r12)
                java.lang.Number r14 = (java.lang.Number) r14
                if (r14 == 0) goto L_0x06c1
                long r15 = com.alibaba.fastjson.util.TypeUtils.longExtractValue(r14)
                java.lang.Long r15 = java.lang.Long.valueOf(r15)
                r2[r12] = r15
            L_0x06c1:
                int r12 = r12 + 1
                goto L_0x06ac
            L_0x06c4:
                com.alibaba.fastjson.JSONPath$IntObjInSegement r12 = new com.alibaba.fastjson.JSONPath$IntObjInSegement
                r12.<init>(r5, r13, r2, r6)
            L_0x06c9:
                char r14 = r0.ch
                r15 = 32
                if (r14 != r15) goto L_0x06d3
                r28.next()
                goto L_0x06c9
            L_0x06d3:
                r15 = 38
                if (r14 == r15) goto L_0x06db
                r3 = 124(0x7c, float:1.74E-43)
                if (r14 != r3) goto L_0x06df
            L_0x06db:
                com.alibaba.fastjson.JSONPath$Filter r12 = r0.filterRest(r12)
            L_0x06df:
                r14 = 41
                r0.accept(r14)
                if (r1 == 0) goto L_0x06e9
                r0.accept(r14)
            L_0x06e9:
                if (r29 == 0) goto L_0x06f0
                r3 = 93
                r0.accept(r3)
            L_0x06f0:
                return r12
            L_0x06f1:
                java.lang.UnsupportedOperationException r2 = new java.lang.UnsupportedOperationException
                r2.<init>()
                throw r2
            L_0x06f7:
                r19 = r2
                r12 = r15
                r2 = 0
                r3 = 124(0x7c, float:1.74E-43)
                r9 = 38
                r10 = 93
                r14 = 41
                r15 = 32
                r28.next()
                java.lang.Object r8 = r28.readValue()
                r7.add(r8)
                r15 = r12
                r2 = r19
                goto L_0x0504
            L_0x0714:
                r19 = r2
                r12 = r15
                r2 = 0
            L_0x0718:
                com.alibaba.fastjson.JSONPath$Operator r3 = com.alibaba.fastjson.JSONPath.Operator.NOT_BETWEEN
                if (r11 != r3) goto L_0x071f
                r27 = r12
                goto L_0x0721
            L_0x071f:
                r27 = r2
            L_0x0721:
                java.lang.Object r2 = r28.readValue()
                java.lang.String r3 = r28.readName()
                java.lang.String r6 = "and"
                boolean r6 = r6.equalsIgnoreCase(r3)
                if (r6 == 0) goto L_0x0777
                java.lang.Object r6 = r28.readValue()
                if (r2 == 0) goto L_0x076f
                if (r6 == 0) goto L_0x076f
                java.lang.Class r7 = r2.getClass()
                boolean r7 = com.alibaba.fastjson.JSONPath.isInt(r7)
                if (r7 == 0) goto L_0x0767
                java.lang.Class r7 = r6.getClass()
                boolean r7 = com.alibaba.fastjson.JSONPath.isInt(r7)
                if (r7 == 0) goto L_0x0767
                com.alibaba.fastjson.JSONPath$IntBetweenSegement r7 = new com.alibaba.fastjson.JSONPath$IntBetweenSegement
                r8 = r2
                java.lang.Number r8 = (java.lang.Number) r8
                long r23 = com.alibaba.fastjson.util.TypeUtils.longExtractValue(r8)
                r8 = r6
                java.lang.Number r8 = (java.lang.Number) r8
                long r25 = com.alibaba.fastjson.util.TypeUtils.longExtractValue(r8)
                r20 = r7
                r21 = r5
                r22 = r13
                r20.<init>(r21, r22, r23, r25, r27)
                return r7
            L_0x0767:
                com.alibaba.fastjson.JSONPathException r7 = new com.alibaba.fastjson.JSONPathException
                java.lang.String r8 = r0.path
                r7.<init>(r8)
                throw r7
            L_0x076f:
                com.alibaba.fastjson.JSONPathException r7 = new com.alibaba.fastjson.JSONPathException
                java.lang.String r8 = r0.path
                r7.<init>(r8)
                throw r7
            L_0x0777:
                com.alibaba.fastjson.JSONPathException r6 = new com.alibaba.fastjson.JSONPathException
                java.lang.String r7 = r0.path
                r6.<init>(r7)
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.JSONPath.JSONPathParser.parseArrayAccessFilter(boolean):java.lang.Object");
        }

        /* access modifiers changed from: package-private */
        public Filter filterRest(Filter filter) {
            char c = this.ch;
            boolean and = c == '&';
            if ((c == '&' && getNextChar() == '&') || (this.ch == '|' && getNextChar() == '|')) {
                next();
                next();
                boolean paren = false;
                if (this.ch == '(') {
                    paren = true;
                    next();
                }
                while (this.ch == ' ') {
                    next();
                }
                filter = new FilterGroup(filter, (Filter) parseArrayAccessFilter(false), and);
                if (paren && this.ch == ')') {
                    next();
                }
            }
            return filter;
        }

        /* access modifiers changed from: protected */
        public long readLongValue() {
            int beginIndex = this.pos - 1;
            char c = this.ch;
            if (c == '+' || c == '-') {
                next();
            }
            while (true) {
                char c2 = this.ch;
                if (c2 < '0' || c2 > '9') {
                } else {
                    next();
                }
            }
            return Long.parseLong(this.path.substring(beginIndex, this.pos - 1));
        }

        /* access modifiers changed from: protected */
        public double readDoubleValue(long longValue) {
            int beginIndex = this.pos - 1;
            next();
            while (true) {
                char c = this.ch;
                if (c < '0' || c > '9') {
                } else {
                    next();
                }
            }
            return Double.parseDouble(this.path.substring(beginIndex, this.pos - 1)) + ((double) longValue);
        }

        /* access modifiers changed from: protected */
        public Object readValue() {
            skipWhitespace();
            if (isDigitFirst(this.ch)) {
                return Long.valueOf(readLongValue());
            }
            char c = this.ch;
            if (c == '\"' || c == '\'') {
                return readString();
            }
            if (c != 'n') {
                throw new UnsupportedOperationException();
            } else if (BuildConfig.TRAVIS.equals(readName())) {
                return null;
            } else {
                throw new JSONPathException(this.path);
            }
        }

        static boolean isDigitFirst(char ch2) {
            return ch2 == '-' || ch2 == '+' || (ch2 >= '0' && ch2 <= '9');
        }

        /* access modifiers changed from: protected */
        public Operator readOp() {
            Operator op = null;
            char c = this.ch;
            if (c == '=') {
                next();
                char c2 = this.ch;
                if (c2 == '~') {
                    next();
                    op = Operator.REG_MATCH;
                } else if (c2 == '=') {
                    next();
                    op = Operator.EQ;
                } else {
                    op = Operator.EQ;
                }
            } else if (c == '!') {
                next();
                accept('=');
                op = Operator.NE;
            } else if (c == '<') {
                next();
                if (this.ch == '=') {
                    next();
                    op = Operator.LE;
                } else {
                    op = Operator.LT;
                }
            } else if (c == '>') {
                next();
                if (this.ch == '=') {
                    next();
                    op = Operator.GE;
                } else {
                    op = Operator.GT;
                }
            }
            if (op != null) {
                return op;
            }
            String name = readName();
            if ("not".equalsIgnoreCase(name)) {
                skipWhitespace();
                String name2 = readName();
                if ("like".equalsIgnoreCase(name2)) {
                    return Operator.NOT_LIKE;
                }
                if ("rlike".equalsIgnoreCase(name2)) {
                    return Operator.NOT_RLIKE;
                }
                if ("in".equalsIgnoreCase(name2)) {
                    return Operator.NOT_IN;
                }
                if ("between".equalsIgnoreCase(name2)) {
                    return Operator.NOT_BETWEEN;
                }
                throw new UnsupportedOperationException();
            } else if ("nin".equalsIgnoreCase(name)) {
                return Operator.NOT_IN;
            } else {
                if ("like".equalsIgnoreCase(name)) {
                    return Operator.LIKE;
                }
                if ("rlike".equalsIgnoreCase(name)) {
                    return Operator.RLIKE;
                }
                if ("in".equalsIgnoreCase(name)) {
                    return Operator.IN;
                }
                if ("between".equalsIgnoreCase(name)) {
                    return Operator.BETWEEN;
                }
                throw new UnsupportedOperationException();
            }
        }

        /* access modifiers changed from: package-private */
        public String readName() {
            skipWhitespace();
            char c = this.ch;
            if (c == '\\' || Character.isJavaIdentifierStart(c)) {
                StringBuilder buf = new StringBuilder();
                while (!isEOF()) {
                    char c2 = this.ch;
                    if (c2 == '\\') {
                        next();
                        buf.append(this.ch);
                        if (isEOF()) {
                            return buf.toString();
                        }
                        next();
                    } else if (!Character.isJavaIdentifierPart(c2)) {
                        break;
                    } else {
                        buf.append(this.ch);
                        next();
                    }
                }
                if (isEOF() && Character.isJavaIdentifierPart(this.ch)) {
                    buf.append(this.ch);
                }
                return buf.toString();
            }
            throw new JSONPathException("illeal jsonpath syntax. " + this.path);
        }

        /* access modifiers changed from: package-private */
        public String readString() {
            char quoate = this.ch;
            next();
            int beginIndex = this.pos - 1;
            while (this.ch != quoate && !isEOF()) {
                next();
            }
            String strValue = this.path.substring(beginIndex, isEOF() ? this.pos : this.pos - 1);
            accept(quoate);
            return strValue;
        }

        /* access modifiers changed from: package-private */
        public void accept(char expect) {
            if (this.ch == ' ') {
                next();
            }
            if (this.ch != expect) {
                throw new JSONPathException("expect '" + expect + ", but '" + this.ch + "'");
            } else if (!isEOF()) {
                next();
            }
        }

        public Segment[] explain() {
            String str = this.path;
            if (str == null || str.length() == 0) {
                throw new IllegalArgumentException();
            }
            Segment[] segments = new Segment[8];
            while (true) {
                Segment segment = readSegement();
                if (segment == null) {
                    break;
                }
                if (segment instanceof PropertySegment) {
                    PropertySegment propertySegment = (PropertySegment) segment;
                    if (!propertySegment.deep && propertySegment.propertyName.equals(e.ANY_MARKER)) {
                    }
                }
                int i = this.level;
                if (i == segments.length) {
                    Segment[] t = new Segment[((i * 3) / 2)];
                    System.arraycopy(segments, 0, t, 0, i);
                    segments = t;
                }
                int i2 = this.level;
                this.level = i2 + 1;
                segments[i2] = segment;
            }
            int i3 = this.level;
            if (i3 == segments.length) {
                return segments;
            }
            Segment[] result = new Segment[i3];
            System.arraycopy(segments, 0, result, 0, i3);
            return result;
        }

        /* access modifiers changed from: package-private */
        public Segment buildArraySegement(String indexText) {
            int end;
            int step;
            int indexTextLen = indexText.length();
            char firstChar = indexText.charAt(0);
            char lastChar = indexText.charAt(indexTextLen - 1);
            int commaIndex = indexText.indexOf(44);
            if (indexText.length() > 2 && firstChar == '\'' && lastChar == '\'') {
                String propertyName = indexText.substring(1, indexTextLen - 1);
                if (commaIndex == -1 || !strArrayPatternx.matcher(indexText).find()) {
                    return new PropertySegment(propertyName, false);
                }
                return new MultiPropertySegment(propertyName.split(strArrayRegex));
            }
            int colonIndex = indexText.indexOf(58);
            if (commaIndex == -1 && colonIndex == -1) {
                if (TypeUtils.isNumber(indexText)) {
                    try {
                        return new ArrayAccessSegment(Integer.parseInt(indexText));
                    } catch (NumberFormatException e) {
                        return new PropertySegment(indexText, false);
                    }
                } else {
                    if (indexText.charAt(0) == '\"' && indexText.charAt(indexText.length() - 1) == '\"') {
                        indexText = indexText.substring(1, indexText.length() - 1);
                    }
                    return new PropertySegment(indexText, false);
                }
            } else if (commaIndex != -1) {
                String[] indexesText = indexText.split(",");
                int[] indexes = new int[indexesText.length];
                for (int i = 0; i < indexesText.length; i++) {
                    indexes[i] = Integer.parseInt(indexesText[i]);
                }
                return new MultiIndexSegment(indexes);
            } else if (colonIndex != -1) {
                String[] indexesText2 = indexText.split(":");
                int[] indexes2 = new int[indexesText2.length];
                for (int i2 = 0; i2 < indexesText2.length; i2++) {
                    String str = indexesText2[i2];
                    if (str.length() != 0) {
                        indexes2[i2] = Integer.parseInt(str);
                    } else if (i2 == 0) {
                        indexes2[i2] = 0;
                    } else {
                        throw new UnsupportedOperationException();
                    }
                }
                int start = indexes2[0];
                if (indexes2.length > 1) {
                    end = indexes2[1];
                } else {
                    end = -1;
                }
                if (indexes2.length == 3) {
                    step = indexes2[2];
                } else {
                    step = 1;
                }
                if (end >= 0 && end < start) {
                    throw new UnsupportedOperationException("end must greater than or equals start. start " + start + ",  end " + end);
                } else if (step > 0) {
                    return new RangeSegment(start, end, step);
                } else {
                    throw new UnsupportedOperationException("step must greater than zero : " + step);
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }

    public static class SizeSegment implements Segment {
        public static final SizeSegment instance = new SizeSegment();

        SizeSegment() {
        }

        public Integer eval(JSONPath path, Object rootObject, Object currentObject) {
            return Integer.valueOf(path.evalSize(currentObject));
        }

        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            context.object = Integer.valueOf(path.evalSize(parser.parse()));
        }
    }

    public static class TypeSegment implements Segment {
        public static final TypeSegment instance = new TypeSegment();

        TypeSegment() {
        }

        public String eval(JSONPath path, Object rootObject, Object currentObject) {
            if (currentObject == null) {
                return BuildConfig.TRAVIS;
            }
            if (currentObject instanceof Collection) {
                return "array";
            }
            if (currentObject instanceof Number) {
                return "number";
            }
            if (currentObject instanceof Boolean) {
                return "boolean";
            }
            if ((currentObject instanceof String) || (currentObject instanceof UUID) || (currentObject instanceof Enum)) {
                return TypedValues.Custom.S_STRING;
            }
            return "object";
        }

        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    public static class FloorSegment implements Segment {
        public static final FloorSegment instance = new FloorSegment();

        FloorSegment() {
        }

        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            if (!(currentObject instanceof JSONArray)) {
                return floor(currentObject);
            }
            JSONArray array = (JSONArray) ((JSONArray) currentObject).clone();
            for (int i = 0; i < array.size(); i++) {
                Object item = array.get(i);
                Object newItem = floor(item);
                if (newItem != item) {
                    array.set(i, newItem);
                }
            }
            return array;
        }

        private static Object floor(Object item) {
            if (item == null) {
                return null;
            }
            if (item instanceof Float) {
                return Double.valueOf(Math.floor((double) ((Float) item).floatValue()));
            }
            if (item instanceof Double) {
                return Double.valueOf(Math.floor(((Double) item).doubleValue()));
            }
            if (item instanceof BigDecimal) {
                return ((BigDecimal) item).setScale(0, RoundingMode.FLOOR);
            }
            if ((item instanceof Byte) || (item instanceof Short) || (item instanceof Integer) || (item instanceof Long) || (item instanceof BigInteger)) {
                return item;
            }
            throw new UnsupportedOperationException();
        }

        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    public static class MaxSegment implements Segment {
        public static final MaxSegment instance = new MaxSegment();

        MaxSegment() {
        }

        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            Object max = null;
            if (currentObject instanceof Collection) {
                for (Object next : (Collection) currentObject) {
                    if (next != null) {
                        if (max == null) {
                            max = next;
                        } else if (JSONPath.compare(max, next) < 0) {
                            max = next;
                        }
                    }
                }
                return max;
            }
            throw new UnsupportedOperationException();
        }

        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    public static class MinSegment implements Segment {
        public static final MinSegment instance = new MinSegment();

        MinSegment() {
        }

        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            Object min = null;
            if (currentObject instanceof Collection) {
                for (Object next : (Collection) currentObject) {
                    if (next != null) {
                        if (min == null) {
                            min = next;
                        } else if (JSONPath.compare(min, next) > 0) {
                            min = next;
                        }
                    }
                }
                return min;
            }
            throw new UnsupportedOperationException();
        }

        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    static int compare(Object a, Object b) {
        Class cls = Float.class;
        Class cls2 = Double.class;
        Class cls3 = Integer.class;
        Class cls4 = Long.class;
        if (a.getClass() == b.getClass()) {
            return ((Comparable) a).compareTo(b);
        }
        Class typeA = a.getClass();
        Class typeB = b.getClass();
        if (typeA == BigDecimal.class) {
            if (typeB == cls3) {
                b = new BigDecimal(((Integer) b).intValue());
            } else if (typeB == cls4) {
                b = new BigDecimal(((Long) b).longValue());
            } else if (typeB == cls) {
                b = new BigDecimal((double) ((Float) b).floatValue());
            } else if (typeB == cls2) {
                b = new BigDecimal(((Double) b).doubleValue());
            }
        } else if (typeA == cls4) {
            if (typeB == cls3) {
                b = new Long((long) ((Integer) b).intValue());
            } else if (typeB == BigDecimal.class) {
                a = new BigDecimal(((Long) a).longValue());
            } else if (typeB == cls) {
                a = new Float((float) ((Long) a).longValue());
            } else if (typeB == cls2) {
                a = new Double((double) ((Long) a).longValue());
            }
        } else if (typeA == cls3) {
            if (typeB == cls4) {
                a = new Long((long) ((Integer) a).intValue());
            } else if (typeB == BigDecimal.class) {
                a = new BigDecimal(((Integer) a).intValue());
            } else if (typeB == cls) {
                a = new Float((float) ((Integer) a).intValue());
            } else if (typeB == cls2) {
                a = new Double((double) ((Integer) a).intValue());
            }
        } else if (typeA == cls2) {
            if (typeB == cls3) {
                b = new Double((double) ((Integer) b).intValue());
            } else if (typeB == cls4) {
                b = new Double((double) ((Long) b).longValue());
            } else if (typeB == cls) {
                b = new Double((double) ((Float) b).floatValue());
            }
        } else if (typeA == cls) {
            if (typeB == cls3) {
                b = new Float((float) ((Integer) b).intValue());
            } else if (typeB == cls4) {
                b = new Float((float) ((Long) b).longValue());
            } else if (typeB == cls2) {
                a = new Double((double) ((Float) a).floatValue());
            }
        }
        return ((Comparable) a).compareTo(b);
    }

    public static class KeySetSegment implements Segment {
        public static final KeySetSegment instance = new KeySetSegment();

        KeySetSegment() {
        }

        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            return path.evalKeySet(currentObject);
        }

        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    public static class PropertySegment implements Segment {
        /* access modifiers changed from: private */
        public final boolean deep;
        /* access modifiers changed from: private */
        public final String propertyName;
        private final long propertyNameHash;

        public PropertySegment(String propertyName2, boolean deep2) {
            this.propertyName = propertyName2;
            this.propertyNameHash = TypeUtils.fnv1a_64(propertyName2);
            this.deep = deep2;
        }

        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            if (!this.deep) {
                return path.getPropertyValue(currentObject, this.propertyName, this.propertyNameHash);
            }
            List<Object> results = new ArrayList<>();
            path.deepScan(currentObject, this.propertyName, results);
            return results;
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x00c1  */
        /* JADX WARNING: Removed duplicated region for block: B:99:0x00b0 A[SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void extract(com.alibaba.fastjson.JSONPath r11, com.alibaba.fastjson.parser.DefaultJSONParser r12, com.alibaba.fastjson.JSONPath.Context r13) {
            /*
                r10 = this;
                com.alibaba.fastjson.parser.JSONLexer r0 = r12.lexer
                com.alibaba.fastjson.parser.JSONLexerBase r0 = (com.alibaba.fastjson.parser.JSONLexerBase) r0
                boolean r1 = r10.deep
                if (r1 == 0) goto L_0x0013
                java.lang.Object r1 = r13.object
                if (r1 != 0) goto L_0x0013
                com.alibaba.fastjson.JSONArray r1 = new com.alibaba.fastjson.JSONArray
                r1.<init>()
                r13.object = r1
            L_0x0013:
                int r1 = r0.token()
                r2 = 14
                r3 = -1
                r4 = 3
                r5 = 16
                if (r1 != r2) goto L_0x00e7
                java.lang.String r1 = r10.propertyName
                java.lang.String r2 = "*"
                boolean r1 = r2.equals(r1)
                if (r1 == 0) goto L_0x002a
                return
            L_0x002a:
                r0.nextToken()
                boolean r1 = r10.deep
                if (r1 == 0) goto L_0x0036
                java.lang.Object r1 = r13.object
                com.alibaba.fastjson.JSONArray r1 = (com.alibaba.fastjson.JSONArray) r1
                goto L_0x003b
            L_0x0036:
                com.alibaba.fastjson.JSONArray r1 = new com.alibaba.fastjson.JSONArray
                r1.<init>()
            L_0x003b:
                int r2 = r0.token()
                r6 = 0
                switch(r2) {
                    case 2: goto L_0x00a4;
                    case 3: goto L_0x00a4;
                    case 4: goto L_0x00a4;
                    case 5: goto L_0x00a4;
                    case 6: goto L_0x00a4;
                    case 7: goto L_0x00a4;
                    case 8: goto L_0x00a4;
                    case 9: goto L_0x0043;
                    case 10: goto L_0x0043;
                    case 11: goto L_0x0043;
                    case 12: goto L_0x0050;
                    case 13: goto L_0x0043;
                    case 14: goto L_0x0044;
                    default: goto L_0x0043;
                }
            L_0x0043:
                goto L_0x00a8
            L_0x0044:
                boolean r2 = r10.deep
                if (r2 == 0) goto L_0x004c
                r10.extract(r11, r12, r13)
                goto L_0x00a8
            L_0x004c:
                r0.skipObject(r6)
                goto L_0x00a8
            L_0x0050:
                boolean r2 = r10.deep
                if (r2 == 0) goto L_0x0058
                r10.extract(r11, r12, r13)
                goto L_0x00a8
            L_0x0058:
                long r7 = r10.propertyNameHash
                int r2 = r0.seekObjectToField(r7, r2)
                if (r2 != r4) goto L_0x008f
                int r7 = r0.token()
                switch(r7) {
                    case 2: goto L_0x0074;
                    case 3: goto L_0x0067;
                    case 4: goto L_0x006c;
                    default: goto L_0x0067;
                }
            L_0x0067:
                java.lang.Object r7 = r12.parse()
                goto L_0x007c
            L_0x006c:
                java.lang.String r7 = r0.stringVal()
                r0.nextToken()
                goto L_0x007c
            L_0x0074:
                java.lang.Number r7 = r0.integerValue()
                r0.nextToken()
            L_0x007c:
                r1.add(r7)
                int r8 = r0.token()
                r9 = 13
                if (r8 != r9) goto L_0x008b
                r0.nextToken()
                goto L_0x003b
            L_0x008b:
                r0.skipObject(r6)
                goto L_0x00a8
            L_0x008f:
                if (r2 != r3) goto L_0x0092
                goto L_0x003b
            L_0x0092:
                boolean r7 = r10.deep
                if (r7 != 0) goto L_0x009a
                r0.skipObject(r6)
                goto L_0x00a8
            L_0x009a:
                java.lang.UnsupportedOperationException r3 = new java.lang.UnsupportedOperationException
                java.lang.String r4 = r0.info()
                r3.<init>(r4)
                throw r3
            L_0x00a4:
                r0.nextToken()
            L_0x00a8:
                int r2 = r0.token()
                r6 = 15
                if (r2 != r6) goto L_0x00c1
                r0.nextToken()
                boolean r2 = r10.deep
                if (r2 != 0) goto L_0x00c0
                int r2 = r1.size()
                if (r2 <= 0) goto L_0x00c0
                r13.object = r1
            L_0x00c0:
                return
            L_0x00c1:
                int r2 = r0.token()
                if (r2 != r5) goto L_0x00cc
                r0.nextToken()
                goto L_0x003b
            L_0x00cc:
                com.alibaba.fastjson.JSONException r2 = new com.alibaba.fastjson.JSONException
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "illegal json : "
                r3.append(r4)
                java.lang.String r4 = r0.info()
                r3.append(r4)
                java.lang.String r3 = r3.toString()
                r2.<init>(r3)
                throw r2
            L_0x00e7:
                boolean r1 = r10.deep
                if (r1 != 0) goto L_0x0122
                long r2 = r10.propertyNameHash
                int r1 = r0.seekObjectToField(r2, r1)
                if (r1 != r4) goto L_0x0121
                boolean r2 = r13.eval
                if (r2 == 0) goto L_0x0121
                int r2 = r0.token()
                switch(r2) {
                    case 2: goto L_0x0113;
                    case 3: goto L_0x010b;
                    case 4: goto L_0x0103;
                    default: goto L_0x00fe;
                }
            L_0x00fe:
                java.lang.Object r2 = r12.parse()
                goto L_0x011b
            L_0x0103:
                java.lang.String r2 = r0.stringVal()
                r0.nextToken(r5)
                goto L_0x011b
            L_0x010b:
                java.math.BigDecimal r2 = r0.decimalValue()
                r0.nextToken(r5)
                goto L_0x011b
            L_0x0113:
                java.lang.Number r2 = r0.integerValue()
                r0.nextToken(r5)
            L_0x011b:
                boolean r3 = r13.eval
                if (r3 == 0) goto L_0x0121
                r13.object = r2
            L_0x0121:
                return
            L_0x0122:
                long r1 = r10.propertyNameHash
                boolean r6 = r10.deep
                int r1 = r0.seekObjectToField(r1, r6)
                if (r1 != r3) goto L_0x012e
                return
            L_0x012e:
                if (r1 != r4) goto L_0x0178
                boolean r2 = r13.eval
                if (r2 == 0) goto L_0x0181
                int r2 = r0.token()
                switch(r2) {
                    case 2: goto L_0x0150;
                    case 3: goto L_0x0148;
                    case 4: goto L_0x0140;
                    default: goto L_0x013b;
                }
            L_0x013b:
                java.lang.Object r2 = r12.parse()
                goto L_0x0158
            L_0x0140:
                java.lang.String r2 = r0.stringVal()
                r0.nextToken(r5)
                goto L_0x0158
            L_0x0148:
                java.math.BigDecimal r2 = r0.decimalValue()
                r0.nextToken(r5)
                goto L_0x0158
            L_0x0150:
                java.lang.Number r2 = r0.integerValue()
                r0.nextToken(r5)
            L_0x0158:
                boolean r6 = r13.eval
                if (r6 == 0) goto L_0x0177
                java.lang.Object r6 = r13.object
                boolean r7 = r6 instanceof java.util.List
                if (r7 == 0) goto L_0x0175
                java.util.List r6 = (java.util.List) r6
                int r7 = r6.size()
                if (r7 != 0) goto L_0x0171
                boolean r7 = r2 instanceof java.util.List
                if (r7 == 0) goto L_0x0171
                r13.object = r2
                goto L_0x0174
            L_0x0171:
                r6.add(r2)
            L_0x0174:
                goto L_0x0177
            L_0x0175:
                r13.object = r2
            L_0x0177:
                goto L_0x0181
            L_0x0178:
                r2 = 1
                if (r1 == r2) goto L_0x017e
                r2 = 2
                if (r1 != r2) goto L_0x0181
            L_0x017e:
                r10.extract(r11, r12, r13)
            L_0x0181:
                goto L_0x0122
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.JSONPath.PropertySegment.extract(com.alibaba.fastjson.JSONPath, com.alibaba.fastjson.parser.DefaultJSONParser, com.alibaba.fastjson.JSONPath$Context):void");
        }

        public void setValue(JSONPath path, Object parent, Object value) {
            if (this.deep) {
                path.deepSet(parent, this.propertyName, this.propertyNameHash, value);
                return;
            }
            path.setPropertyValue(parent, this.propertyName, this.propertyNameHash, value);
        }

        public boolean remove(JSONPath path, Object parent) {
            return path.removePropertyValue(parent, this.propertyName, this.deep);
        }
    }

    public static class MultiPropertySegment implements Segment {
        private final String[] propertyNames;
        private final long[] propertyNamesHash;

        public MultiPropertySegment(String[] propertyNames2) {
            this.propertyNames = propertyNames2;
            this.propertyNamesHash = new long[propertyNames2.length];
            int i = 0;
            while (true) {
                long[] jArr = this.propertyNamesHash;
                if (i < jArr.length) {
                    jArr[i] = TypeUtils.fnv1a_64(propertyNames2[i]);
                    i++;
                } else {
                    return;
                }
            }
        }

        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            List<Object> fieldValues = new ArrayList<>(this.propertyNames.length);
            int i = 0;
            while (true) {
                String[] strArr = this.propertyNames;
                if (i >= strArr.length) {
                    return fieldValues;
                }
                fieldValues.add(path.getPropertyValue(currentObject, strArr[i], this.propertyNamesHash[i]));
                i++;
            }
        }

        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            JSONArray array;
            Object value;
            JSONLexerBase lexer = (JSONLexerBase) parser.lexer;
            Object obj = context.object;
            if (obj == null) {
                JSONArray jSONArray = new JSONArray();
                array = jSONArray;
                context.object = jSONArray;
            } else {
                array = (JSONArray) obj;
            }
            for (int i = array.size(); i < this.propertyNamesHash.length; i++) {
                array.add((Object) null);
            }
            do {
                int index = lexer.seekObjectToField(this.propertyNamesHash);
                if (lexer.matchStat == 3) {
                    switch (lexer.token()) {
                        case 2:
                            value = lexer.integerValue();
                            lexer.nextToken(16);
                            break;
                        case 3:
                            value = lexer.decimalValue();
                            lexer.nextToken(16);
                            break;
                        case 4:
                            value = lexer.stringVal();
                            lexer.nextToken(16);
                            break;
                        default:
                            value = parser.parse();
                            break;
                    }
                    array.set(index, value);
                } else {
                    return;
                }
            } while (lexer.token() == 16);
        }
    }

    public static class WildCardSegment implements Segment {
        public static final WildCardSegment instance = new WildCardSegment(false);
        public static final WildCardSegment instance_deep = new WildCardSegment(true);
        private boolean deep;

        private WildCardSegment(boolean deep2) {
            this.deep = deep2;
        }

        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            if (!this.deep) {
                return path.getPropertyValues(currentObject);
            }
            List<Object> values = new ArrayList<>();
            path.deepGetPropertyValues(currentObject, values);
            return values;
        }

        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            if (context.eval) {
                Object object = parser.parse();
                if (this.deep) {
                    List<Object> values = new ArrayList<>();
                    path.deepGetPropertyValues(object, values);
                    context.object = values;
                    return;
                } else if (object instanceof JSONObject) {
                    Collection<Object> values2 = ((JSONObject) object).values();
                    JSONArray array = new JSONArray(values2.size());
                    array.addAll(values2);
                    context.object = array;
                    return;
                } else if (object instanceof JSONArray) {
                    context.object = object;
                    return;
                }
            }
            throw new JSONException("TODO");
        }
    }

    public static class ArrayAccessSegment implements Segment {
        /* access modifiers changed from: private */
        public final int index;

        public ArrayAccessSegment(int index2) {
            this.index = index2;
        }

        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            return path.getArrayItem(currentObject, this.index);
        }

        public boolean setValue(JSONPath path, Object currentObject, Object value) {
            return path.setArrayItem(path, currentObject, this.index, value);
        }

        public boolean remove(JSONPath path, Object currentObject) {
            return path.removeArrayItem(path, currentObject, this.index);
        }

        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            if (((JSONLexerBase) parser.lexer).seekArrayToItem(this.index) && context.eval) {
                context.object = parser.parse();
            }
        }
    }

    public static class MultiIndexSegment implements Segment {
        private final int[] indexes;

        public MultiIndexSegment(int[] indexes2) {
            this.indexes = indexes2;
        }

        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            List<Object> items = new JSONArray(this.indexes.length);
            int i = 0;
            while (true) {
                int[] iArr = this.indexes;
                if (i >= iArr.length) {
                    return items;
                }
                items.add(path.getArrayItem(currentObject, iArr[i]));
                i++;
            }
        }

        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            if (context.eval) {
                Object object = parser.parse();
                if (object instanceof List) {
                    int[] iArr = this.indexes;
                    int[] indexes2 = new int[iArr.length];
                    boolean z = false;
                    System.arraycopy(iArr, 0, indexes2, 0, indexes2.length);
                    if (indexes2[0] >= 0) {
                        z = true;
                    }
                    boolean noneNegative = z;
                    List list = (List) object;
                    if (noneNegative) {
                        for (int i = list.size() - 1; i >= 0; i--) {
                            if (Arrays.binarySearch(indexes2, i) < 0) {
                                list.remove(i);
                            }
                        }
                        context.object = list;
                        return;
                    }
                }
            }
            throw new UnsupportedOperationException();
        }
    }

    public static class RangeSegment implements Segment {
        private final int end;
        private final int start;
        private final int step;

        public RangeSegment(int start2, int end2, int step2) {
            this.start = start2;
            this.end = end2;
            this.step = step2;
        }

        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            int size = SizeSegment.instance.eval(path, rootObject, currentObject).intValue();
            int start2 = this.start;
            if (start2 < 0) {
                start2 += size;
            }
            int end2 = this.end;
            if (end2 < 0) {
                end2 += size;
            }
            int array_size = ((end2 - start2) / this.step) + 1;
            if (array_size == -1) {
                return null;
            }
            List<Object> items = new ArrayList<>(array_size);
            int i = start2;
            while (i <= end2 && i < size) {
                items.add(path.getArrayItem(currentObject, i));
                i += this.step;
            }
            return items;
        }

        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    public static class NotNullSegement extends PropertyFilter {
        public NotNullSegement(String propertyName, boolean function) {
            super(propertyName, function);
        }

        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            return path.getPropertyValue(item, this.propertyName, this.propertyNameHash) != null;
        }
    }

    public static class NullSegement extends PropertyFilter {
        public NullSegement(String propertyName, boolean function) {
            super(propertyName, function);
        }

        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            return get(path, rootObject, item) == null;
        }
    }

    public static class ValueSegment extends PropertyFilter {
        private boolean eq = true;
        private final Object value;

        public ValueSegment(String propertyName, boolean function, Object value2, boolean eq2) {
            super(propertyName, function);
            if (value2 != null) {
                this.value = value2;
                this.eq = eq2;
                return;
            }
            throw new IllegalArgumentException("value is null");
        }

        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            boolean result = this.value.equals(get(path, rootObject, item));
            if (!this.eq) {
                return !result;
            }
            return result;
        }
    }

    public static class IntInSegement extends PropertyFilter {
        private final boolean not;
        private final long[] values;

        public IntInSegement(String propertyName, boolean function, long[] values2, boolean not2) {
            super(propertyName, function);
            this.values = values2;
            this.not = not2;
        }

        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            if (propertyValue == null) {
                return false;
            }
            if (propertyValue instanceof Number) {
                long longPropertyValue = TypeUtils.longExtractValue((Number) propertyValue);
                for (long value : this.values) {
                    if (value == longPropertyValue) {
                        return !this.not;
                    }
                }
            }
            return this.not;
        }
    }

    public static class IntBetweenSegement extends PropertyFilter {
        private final long endValue;
        private final boolean not;
        private final long startValue;

        public IntBetweenSegement(String propertyName, boolean function, long startValue2, long endValue2, boolean not2) {
            super(propertyName, function);
            this.startValue = startValue2;
            this.endValue = endValue2;
            this.not = not2;
        }

        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            if (propertyValue == null) {
                return false;
            }
            if (propertyValue instanceof Number) {
                long longPropertyValue = TypeUtils.longExtractValue((Number) propertyValue);
                if (longPropertyValue >= this.startValue && longPropertyValue <= this.endValue) {
                    return !this.not;
                }
            }
            return this.not;
        }
    }

    public static class IntObjInSegement extends PropertyFilter {
        private final boolean not;
        private final Long[] values;

        public IntObjInSegement(String propertyName, boolean function, Long[] values2, boolean not2) {
            super(propertyName, function);
            this.values = values2;
            this.not = not2;
        }

        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            int i = 0;
            if (propertyValue == null) {
                Long[] lArr = this.values;
                int length = lArr.length;
                while (i < length) {
                    if (lArr[i] == null) {
                        return !this.not;
                    }
                    i++;
                }
                return this.not;
            }
            if (propertyValue instanceof Number) {
                long longPropertyValue = TypeUtils.longExtractValue((Number) propertyValue);
                Long[] lArr2 = this.values;
                int length2 = lArr2.length;
                while (i < length2) {
                    Long value = lArr2[i];
                    if (value != null && value.longValue() == longPropertyValue) {
                        return !this.not;
                    }
                    i++;
                }
            }
            return this.not;
        }
    }

    public static class StringInSegement extends PropertyFilter {
        private final boolean not;
        private final String[] values;

        public StringInSegement(String propertyName, boolean function, String[] values2, boolean not2) {
            super(propertyName, function);
            this.values = values2;
            this.not = not2;
        }

        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            for (String value : this.values) {
                if (value == propertyValue) {
                    return !this.not;
                }
                if (value != null && value.equals(propertyValue)) {
                    return !this.not;
                }
            }
            return this.not;
        }
    }

    public static class IntOpSegement extends PropertyFilter {
        private final Operator op;
        private final long value;
        private BigDecimal valueDecimal;
        private Double valueDouble;
        private Float valueFloat;

        public IntOpSegement(String propertyName, boolean function, long value2, Operator op2) {
            super(propertyName, function);
            this.value = value2;
            this.op = op2;
        }

        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            if (propertyValue == null || !(propertyValue instanceof Number)) {
                return false;
            }
            if (propertyValue instanceof BigDecimal) {
                if (this.valueDecimal == null) {
                    this.valueDecimal = BigDecimal.valueOf(this.value);
                }
                int result = this.valueDecimal.compareTo((BigDecimal) propertyValue);
                switch (AnonymousClass1.$SwitchMap$com$alibaba$fastjson$JSONPath$Operator[this.op.ordinal()]) {
                    case 1:
                        if (result == 0) {
                            return true;
                        }
                        return false;
                    case 2:
                        if (result != 0) {
                            return true;
                        }
                        return false;
                    case 3:
                        if (result <= 0) {
                            return true;
                        }
                        return false;
                    case 4:
                        if (result < 0) {
                            return true;
                        }
                        return false;
                    case 5:
                        if (result >= 0) {
                            return true;
                        }
                        return false;
                    case 6:
                        if (result > 0) {
                            return true;
                        }
                        return false;
                    default:
                        return false;
                }
            } else if ((propertyValue instanceof Float) != 0) {
                if (this.valueFloat == null) {
                    this.valueFloat = Float.valueOf((float) this.value);
                }
                int result2 = this.valueFloat.compareTo((Float) propertyValue);
                switch (AnonymousClass1.$SwitchMap$com$alibaba$fastjson$JSONPath$Operator[this.op.ordinal()]) {
                    case 1:
                        if (result2 == 0) {
                            return true;
                        }
                        return false;
                    case 2:
                        if (result2 != 0) {
                            return true;
                        }
                        return false;
                    case 3:
                        if (result2 <= 0) {
                            return true;
                        }
                        return false;
                    case 4:
                        if (result2 < 0) {
                            return true;
                        }
                        return false;
                    case 5:
                        if (result2 >= 0) {
                            return true;
                        }
                        return false;
                    case 6:
                        if (result2 > 0) {
                            return true;
                        }
                        return false;
                    default:
                        return false;
                }
            } else if ((propertyValue instanceof Double) != 0) {
                if (this.valueDouble == null) {
                    this.valueDouble = Double.valueOf((double) this.value);
                }
                int result3 = this.valueDouble.compareTo((Double) propertyValue);
                switch (AnonymousClass1.$SwitchMap$com$alibaba$fastjson$JSONPath$Operator[this.op.ordinal()]) {
                    case 1:
                        if (result3 == 0) {
                            return true;
                        }
                        return false;
                    case 2:
                        if (result3 != 0) {
                            return true;
                        }
                        return false;
                    case 3:
                        if (result3 <= 0) {
                            return true;
                        }
                        return false;
                    case 4:
                        if (result3 < 0) {
                            return true;
                        }
                        return false;
                    case 5:
                        if (result3 >= 0) {
                            return true;
                        }
                        return false;
                    case 6:
                        if (result3 > 0) {
                            return true;
                        }
                        return false;
                    default:
                        return false;
                }
            } else {
                long longValue = TypeUtils.longExtractValue((Number) propertyValue);
                switch (AnonymousClass1.$SwitchMap$com$alibaba$fastjson$JSONPath$Operator[this.op.ordinal()]) {
                    case 1:
                        if (longValue == this.value) {
                            return true;
                        }
                        return false;
                    case 2:
                        if (longValue != this.value) {
                            return true;
                        }
                        return false;
                    case 3:
                        if (longValue >= this.value) {
                            return true;
                        }
                        return false;
                    case 4:
                        if (longValue > this.value) {
                            return true;
                        }
                        return false;
                    case 5:
                        if (longValue <= this.value) {
                            return true;
                        }
                        return false;
                    case 6:
                        if (longValue < this.value) {
                            return true;
                        }
                        return false;
                    default:
                        return false;
                }
            }
        }
    }

    /* renamed from: com.alibaba.fastjson.JSONPath$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$alibaba$fastjson$JSONPath$Operator;

        static {
            int[] iArr = new int[Operator.values().length];
            $SwitchMap$com$alibaba$fastjson$JSONPath$Operator = iArr;
            try {
                iArr[Operator.EQ.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$JSONPath$Operator[Operator.NE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$JSONPath$Operator[Operator.GE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$JSONPath$Operator[Operator.GT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$JSONPath$Operator[Operator.LE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$alibaba$fastjson$JSONPath$Operator[Operator.LT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public static abstract class PropertyFilter implements Filter {
        static long TYPE = TypeUtils.fnv1a_64(IjkMediaMeta.IJKM_KEY_TYPE);
        protected final boolean function;
        protected Segment functionExpr;
        protected final String propertyName;
        protected final long propertyNameHash;

        protected PropertyFilter(String propertyName2, boolean function2) {
            this.propertyName = propertyName2;
            long fnv1a_64 = TypeUtils.fnv1a_64(propertyName2);
            this.propertyNameHash = fnv1a_64;
            this.function = function2;
            if (!function2) {
                return;
            }
            if (fnv1a_64 == TYPE) {
                this.functionExpr = TypeSegment.instance;
            } else if (fnv1a_64 == JSONPath.SIZE) {
                this.functionExpr = SizeSegment.instance;
            } else {
                throw new JSONPathException("unsupported funciton : " + propertyName2);
            }
        }

        /* access modifiers changed from: protected */
        public Object get(JSONPath path, Object rootObject, Object currentObject) {
            Segment segment = this.functionExpr;
            if (segment != null) {
                return segment.eval(path, rootObject, currentObject);
            }
            return path.getPropertyValue(currentObject, this.propertyName, this.propertyNameHash);
        }
    }

    public static class DoubleOpSegement extends PropertyFilter {
        private final Operator op;
        private final double value;

        public DoubleOpSegement(String propertyName, boolean function, double value2, Operator op2) {
            super(propertyName, function);
            this.value = value2;
            this.op = op2;
        }

        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            if (propertyValue == null || !(propertyValue instanceof Number)) {
                return false;
            }
            double doubleValue = ((Number) propertyValue).doubleValue();
            switch (AnonymousClass1.$SwitchMap$com$alibaba$fastjson$JSONPath$Operator[this.op.ordinal()]) {
                case 1:
                    if (doubleValue == this.value) {
                        return true;
                    }
                    return false;
                case 2:
                    if (doubleValue != this.value) {
                        return true;
                    }
                    return false;
                case 3:
                    if (doubleValue >= this.value) {
                        return true;
                    }
                    return false;
                case 4:
                    if (doubleValue > this.value) {
                        return true;
                    }
                    return false;
                case 5:
                    if (doubleValue <= this.value) {
                        return true;
                    }
                    return false;
                case 6:
                    if (doubleValue < this.value) {
                        return true;
                    }
                    return false;
                default:
                    return false;
            }
        }
    }

    public static class RefOpSegement extends PropertyFilter {
        private final Operator op;
        private final Segment refSgement;

        public RefOpSegement(String propertyName, boolean function, Segment refSgement2, Operator op2) {
            super(propertyName, function);
            this.refSgement = refSgement2;
            this.op = op2;
        }

        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            if (propertyValue == null || !(propertyValue instanceof Number)) {
                return false;
            }
            Object refValue = this.refSgement.eval(path, rootObject, rootObject);
            if ((refValue instanceof Integer) || (refValue instanceof Long) || (refValue instanceof Short) || (refValue instanceof Byte)) {
                long value = TypeUtils.longExtractValue((Number) refValue);
                if ((propertyValue instanceof Integer) || (propertyValue instanceof Long) || (propertyValue instanceof Short) || (propertyValue instanceof Byte)) {
                    long longValue = TypeUtils.longExtractValue((Number) propertyValue);
                    switch (AnonymousClass1.$SwitchMap$com$alibaba$fastjson$JSONPath$Operator[this.op.ordinal()]) {
                        case 1:
                            if (longValue == value) {
                                return true;
                            }
                            return false;
                        case 2:
                            if (longValue != value) {
                                return true;
                            }
                            return false;
                        case 3:
                            if (longValue >= value) {
                                return true;
                            }
                            return false;
                        case 4:
                            if (longValue > value) {
                                return true;
                            }
                            return false;
                        case 5:
                            if (longValue <= value) {
                                return true;
                            }
                            return false;
                        case 6:
                            if (longValue < value) {
                                return true;
                            }
                            return false;
                    }
                } else if (propertyValue instanceof BigDecimal) {
                    int result = BigDecimal.valueOf(value).compareTo((BigDecimal) propertyValue);
                    switch (AnonymousClass1.$SwitchMap$com$alibaba$fastjson$JSONPath$Operator[this.op.ordinal()]) {
                        case 1:
                            if (result == 0) {
                                return true;
                            }
                            return false;
                        case 2:
                            if (result != 0) {
                                return true;
                            }
                            return false;
                        case 3:
                            if (result <= 0) {
                                return true;
                            }
                            return false;
                        case 4:
                            if (result < 0) {
                                return true;
                            }
                            return false;
                        case 5:
                            if (result >= 0) {
                                return true;
                            }
                            return false;
                        case 6:
                            if (result > 0) {
                                return true;
                            }
                            return false;
                        default:
                            return false;
                    }
                }
            }
            throw new UnsupportedOperationException();
        }
    }

    public static class MatchSegement extends PropertyFilter {
        private final String[] containsValues;
        private final String endsWithValue;
        private final int minLength;
        private final boolean not;
        private final String startsWithValue;

        public MatchSegement(String propertyName, boolean function, String startsWithValue2, String endsWithValue2, String[] containsValues2, boolean not2) {
            super(propertyName, function);
            this.startsWithValue = startsWithValue2;
            this.endsWithValue = endsWithValue2;
            this.containsValues = containsValues2;
            this.not = not2;
            int len = startsWithValue2 != null ? 0 + startsWithValue2.length() : 0;
            len = endsWithValue2 != null ? len + endsWithValue2.length() : len;
            if (containsValues2 != null) {
                for (String item : containsValues2) {
                    len += item.length();
                }
            }
            this.minLength = len;
        }

        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            if (propertyValue == null) {
                return false;
            }
            String strPropertyValue = propertyValue.toString();
            if (strPropertyValue.length() < this.minLength) {
                return this.not;
            }
            int start = 0;
            String str = this.startsWithValue;
            if (str != null) {
                if (!strPropertyValue.startsWith(str)) {
                    return this.not;
                }
                start = 0 + this.startsWithValue.length();
            }
            String[] strArr = this.containsValues;
            if (strArr != null) {
                for (String containsValue : strArr) {
                    int index = strPropertyValue.indexOf(containsValue, start);
                    if (index == -1) {
                        return this.not;
                    }
                    start = index + containsValue.length();
                }
            }
            String str2 = this.endsWithValue;
            if (str2 == null || strPropertyValue.endsWith(str2)) {
                return !this.not;
            }
            return this.not;
        }
    }

    public static class RlikeSegement extends PropertyFilter {
        private final boolean not;
        private final Pattern pattern;

        public RlikeSegement(String propertyName, boolean function, String pattern2, boolean not2) {
            super(propertyName, function);
            this.pattern = Pattern.compile(pattern2);
            this.not = not2;
        }

        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            if (propertyValue == null) {
                return false;
            }
            boolean match = this.pattern.matcher(propertyValue.toString()).matches();
            if (this.not) {
                return !match;
            }
            return match;
        }
    }

    public static class StringOpSegement extends PropertyFilter {
        private final Operator op;
        private final String value;

        public StringOpSegement(String propertyName, boolean function, String value2, Operator op2) {
            super(propertyName, function);
            this.value = value2;
            this.op = op2;
        }

        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            Operator operator = this.op;
            if (operator == Operator.EQ) {
                return this.value.equals(propertyValue);
            }
            if (operator == Operator.NE) {
                return !this.value.equals(propertyValue);
            }
            if (propertyValue == null) {
                return false;
            }
            int compareResult = this.value.compareTo(propertyValue.toString());
            Operator operator2 = this.op;
            if (operator2 == Operator.GE) {
                if (compareResult <= 0) {
                    return true;
                }
                return false;
            } else if (operator2 == Operator.GT) {
                if (compareResult < 0) {
                    return true;
                }
                return false;
            } else if (operator2 == Operator.LE) {
                if (compareResult >= 0) {
                    return true;
                }
                return false;
            } else if (operator2 != Operator.LT) {
                return false;
            } else {
                if (compareResult > 0) {
                    return true;
                }
                return false;
            }
        }
    }

    public static class RegMatchSegement extends PropertyFilter {
        private final Operator op;
        private final Pattern pattern;

        public RegMatchSegement(String propertyName, boolean function, Pattern pattern2, Operator op2) {
            super(propertyName, function);
            this.pattern = pattern2;
            this.op = op2;
        }

        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = get(path, rootObject, item);
            if (propertyValue == null) {
                return false;
            }
            return this.pattern.matcher(propertyValue.toString()).matches();
        }
    }

    public static class FilterSegment implements Segment {
        /* access modifiers changed from: private */
        public final Filter filter;

        public FilterSegment(Filter filter2) {
            this.filter = filter2;
        }

        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            if (currentObject == null) {
                return null;
            }
            List<Object> items = new JSONArray();
            if (currentObject instanceof Iterable) {
                for (Object item : (Iterable) currentObject) {
                    if (this.filter.apply(path, rootObject, currentObject, item)) {
                        items.add(item);
                    }
                }
                return items;
            } else if (this.filter.apply(path, rootObject, currentObject, currentObject)) {
                return currentObject;
            } else {
                return null;
            }
        }

        public void extract(JSONPath path, DefaultJSONParser parser, Context context) {
            Object object = parser.parse();
            context.object = eval(path, object, object);
        }

        public boolean remove(JSONPath path, Object rootObject, Object currentObject) {
            if (currentObject == null || !(currentObject instanceof Iterable)) {
                return false;
            }
            Iterator it = ((Iterable) currentObject).iterator();
            while (it.hasNext()) {
                if (this.filter.apply(path, rootObject, currentObject, it.next())) {
                    it.remove();
                }
            }
            return true;
        }
    }

    public static class FilterGroup implements Filter {
        private boolean and;
        private List<Filter> fitlers;

        public FilterGroup(Filter left, Filter right, boolean and2) {
            ArrayList arrayList = new ArrayList(2);
            this.fitlers = arrayList;
            arrayList.add(left);
            this.fitlers.add(right);
            this.and = and2;
        }

        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            if (this.and) {
                for (Filter fitler : this.fitlers) {
                    if (!fitler.apply(path, rootObject, currentObject, item)) {
                        return false;
                    }
                }
                return true;
            }
            for (Filter fitler2 : this.fitlers) {
                if (fitler2.apply(path, rootObject, currentObject, item)) {
                    return true;
                }
            }
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public Object getArrayItem(Object currentObject, int index) {
        if (currentObject == null) {
            return null;
        }
        if (currentObject instanceof List) {
            List list = (List) currentObject;
            if (index >= 0) {
                if (index < list.size()) {
                    return list.get(index);
                }
                return null;
            } else if (Math.abs(index) <= list.size()) {
                return list.get(list.size() + index);
            } else {
                return null;
            }
        } else if (currentObject.getClass().isArray()) {
            int arrayLenth = Array.getLength(currentObject);
            if (index >= 0) {
                if (index < arrayLenth) {
                    return Array.get(currentObject, index);
                }
                return null;
            } else if (Math.abs(index) <= arrayLenth) {
                return Array.get(currentObject, arrayLenth + index);
            } else {
                return null;
            }
        } else if ((currentObject instanceof Map) != 0) {
            Map map = (Map) currentObject;
            Object value = map.get(Integer.valueOf(index));
            if (value == null) {
                return map.get(Integer.toString(index));
            }
            return value;
        } else if (currentObject instanceof Collection) {
            int i = 0;
            for (Object item : (Collection) currentObject) {
                if (i == index) {
                    return item;
                }
                i++;
            }
            return null;
        } else if (index == 0) {
            return currentObject;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public boolean setArrayItem(JSONPath path2, Object currentObject, int index, Object value) {
        if (currentObject instanceof List) {
            List list = (List) currentObject;
            if (index >= 0) {
                list.set(index, value);
            } else {
                list.set(list.size() + index, value);
            }
            return true;
        }
        Class<?> clazz = currentObject.getClass();
        if (clazz.isArray()) {
            int arrayLenth = Array.getLength(currentObject);
            if (index >= 0) {
                if (index < arrayLenth) {
                    Array.set(currentObject, index, value);
                }
            } else if (Math.abs(index) <= arrayLenth) {
                Array.set(currentObject, arrayLenth + index, value);
            }
            return true;
        }
        throw new JSONPathException("unsupported set operation." + clazz);
    }

    public boolean removeArrayItem(JSONPath path2, Object currentObject, int index) {
        if (currentObject instanceof List) {
            List list = (List) currentObject;
            if (index < 0) {
                int newIndex = list.size() + index;
                if (newIndex < 0) {
                    return false;
                }
                list.remove(newIndex);
                return true;
            } else if (index >= list.size()) {
                return false;
            } else {
                list.remove(index);
                return true;
            }
        } else {
            Class<?> clazz = currentObject.getClass();
            throw new JSONPathException("unsupported set operation." + clazz);
        }
    }

    /* access modifiers changed from: protected */
    public Collection<Object> getPropertyValues(Object currentObject) {
        if (currentObject == null) {
            return null;
        }
        JavaBeanSerializer beanSerializer = getJavaBeanSerializer(currentObject.getClass());
        if (beanSerializer != null) {
            try {
                return beanSerializer.getFieldValues(currentObject);
            } catch (Exception e) {
                throw new JSONPathException("jsonpath error, path " + this.path, e);
            }
        } else if (currentObject instanceof Map) {
            return ((Map) currentObject).values();
        } else {
            if (currentObject instanceof Collection) {
                return (Collection) currentObject;
            }
            throw new UnsupportedOperationException();
        }
    }

    /* access modifiers changed from: protected */
    public void deepGetPropertyValues(Object currentObject, List<Object> outValues) {
        Class<?> currentClass = currentObject.getClass();
        JavaBeanSerializer beanSerializer = getJavaBeanSerializer(currentClass);
        Collection collection = null;
        if (beanSerializer != null) {
            try {
                collection = beanSerializer.getFieldValues(currentObject);
            } catch (Exception e) {
                throw new JSONPathException("jsonpath error, path " + this.path, e);
            }
        } else if (currentObject instanceof Map) {
            collection = ((Map) currentObject).values();
        } else if (currentObject instanceof Collection) {
            collection = (Collection) currentObject;
        }
        if (collection != null) {
            for (Object fieldValue : collection) {
                if (fieldValue == null || ParserConfig.isPrimitive2(fieldValue.getClass())) {
                    outValues.add(fieldValue);
                } else {
                    deepGetPropertyValues(fieldValue, outValues);
                }
            }
            return;
        }
        throw new UnsupportedOperationException(currentClass.getName());
    }

    static boolean eq(Object a, Object b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a.getClass() == b.getClass()) {
            return a.equals(b);
        }
        if (!(a instanceof Number)) {
            return a.equals(b);
        }
        if (b instanceof Number) {
            return eqNotNull((Number) a, (Number) b);
        }
        return false;
    }

    static boolean eqNotNull(Number a, Number b) {
        Class clazzA = a.getClass();
        boolean isIntA = isInt(clazzA);
        Class clazzB = b.getClass();
        boolean isIntB = isInt(clazzB);
        if (a instanceof BigDecimal) {
            BigDecimal decimalA = (BigDecimal) a;
            if (isIntB) {
                return decimalA.equals(BigDecimal.valueOf(TypeUtils.longExtractValue(b)));
            }
        }
        if (isIntA) {
            if (isIntB) {
                if (a.longValue() == b.longValue()) {
                    return true;
                }
                return false;
            } else if (b instanceof BigInteger) {
                return BigInteger.valueOf(a.longValue()).equals((BigInteger) a);
            }
        }
        if (isIntB && (a instanceof BigInteger)) {
            return ((BigInteger) a).equals(BigInteger.valueOf(TypeUtils.longExtractValue(b)));
        }
        boolean isDoubleA = isDouble(clazzA);
        boolean isDoubleB = isDouble(clazzB);
        if ((!isDoubleA || !isDoubleB) && ((!isDoubleA || !isIntB) && (!isDoubleB || !isIntA))) {
            return false;
        }
        if (a.doubleValue() == b.doubleValue()) {
            return true;
        }
        return false;
    }

    protected static boolean isDouble(Class<?> clazzA) {
        return clazzA == Float.class || clazzA == Double.class;
    }

    protected static boolean isInt(Class<?> clazzA) {
        return clazzA == Byte.class || clazzA == Short.class || clazzA == Integer.class || clazzA == Long.class;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x002a  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0044  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object getPropertyValue(java.lang.Object r25, java.lang.String r26, long r27) {
        /*
            r24 = this;
            r1 = r24
            r2 = r25
            r8 = r26
            r9 = r27
            r3 = 0
            if (r2 != 0) goto L_0x000c
            return r3
        L_0x000c:
            boolean r0 = r2 instanceof java.lang.String
            if (r0 == 0) goto L_0x001b
            r0 = r2
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x001a }
            com.alibaba.fastjson.JSONObject r0 = com.alibaba.fastjson.JSON.parseObject(r0)     // Catch:{ Exception -> 0x001a }
            r11 = r0
            goto L_0x001c
        L_0x001a:
            r0 = move-exception
        L_0x001b:
            r11 = r2
        L_0x001c:
            boolean r0 = r11 instanceof java.util.Map
            r4 = -1580386065683472715(0xea11573f1af59eb5, double:-8.49505883430448E202)
            r6 = 5614464919154503228(0x4dea9618e618ae3c, double:2.239892812106928E67)
            if (r0 == 0) goto L_0x0044
            r0 = r11
            java.util.Map r0 = (java.util.Map) r0
            java.lang.Object r2 = r0.get(r8)
            if (r2 != 0) goto L_0x0043
            int r3 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r3 == 0) goto L_0x003b
            int r3 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1))
            if (r3 != 0) goto L_0x0043
        L_0x003b:
            int r3 = r0.size()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r3)
        L_0x0043:
            return r2
        L_0x0044:
            java.lang.Class r12 = r11.getClass()
            com.alibaba.fastjson.serializer.JavaBeanSerializer r13 = r1.getJavaBeanSerializer(r12)
            if (r13 == 0) goto L_0x007e
            r7 = 0
            r2 = r13
            r3 = r11
            r4 = r26
            r5 = r27
            java.lang.Object r0 = r2.getFieldValue(r3, r4, r5, r7)     // Catch:{ Exception -> 0x005a }
            return r0
        L_0x005a:
            r0 = move-exception
            r2 = r0
            r0 = r2
            com.alibaba.fastjson.JSONPathException r2 = new com.alibaba.fastjson.JSONPathException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "jsonpath error, path "
            r3.append(r4)
            java.lang.String r4 = r1.path
            r3.append(r4)
            java.lang.String r4 = ", segement "
            r3.append(r4)
            r3.append(r8)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3, r0)
            throw r2
        L_0x007e:
            boolean r0 = r11 instanceof java.util.List
            if (r0 == 0) goto L_0x00ed
            r0 = r11
            java.util.List r0 = (java.util.List) r0
            int r2 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r2 == 0) goto L_0x00e4
            int r2 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1))
            if (r2 != 0) goto L_0x008e
            goto L_0x00e4
        L_0x008e:
            r2 = 0
            r3 = 0
        L_0x0090:
            int r4 = r0.size()
            if (r3 >= r4) goto L_0x00dd
            java.lang.Object r4 = r0.get(r3)
            if (r4 != r0) goto L_0x00ac
            if (r2 != 0) goto L_0x00a8
            com.alibaba.fastjson.JSONArray r5 = new com.alibaba.fastjson.JSONArray
            int r6 = r0.size()
            r5.<init>((int) r6)
            r2 = r5
        L_0x00a8:
            r2.add(r4)
            goto L_0x00da
        L_0x00ac:
            java.lang.Object r5 = r1.getPropertyValue(r4, r8, r9)
            boolean r6 = r5 instanceof java.util.Collection
            if (r6 == 0) goto L_0x00c7
            r6 = r5
            java.util.Collection r6 = (java.util.Collection) r6
            if (r2 != 0) goto L_0x00c3
            com.alibaba.fastjson.JSONArray r7 = new com.alibaba.fastjson.JSONArray
            int r14 = r0.size()
            r7.<init>((int) r14)
            r2 = r7
        L_0x00c3:
            r2.addAll(r6)
            goto L_0x00d9
        L_0x00c7:
            if (r5 == 0) goto L_0x00d9
            if (r2 != 0) goto L_0x00d5
            com.alibaba.fastjson.JSONArray r6 = new com.alibaba.fastjson.JSONArray
            int r7 = r0.size()
            r6.<init>((int) r7)
            r2 = r6
        L_0x00d5:
            r2.add(r5)
            goto L_0x00da
        L_0x00d9:
        L_0x00da:
            int r3 = r3 + 1
            goto L_0x0090
        L_0x00dd:
            if (r2 != 0) goto L_0x00e3
            java.util.List r2 = java.util.Collections.emptyList()
        L_0x00e3:
            return r2
        L_0x00e4:
            int r2 = r0.size()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            return r2
        L_0x00ed:
            boolean r0 = r11 instanceof java.lang.Object[]
            if (r0 == 0) goto L_0x012f
            r0 = r11
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            int r2 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r2 == 0) goto L_0x0129
            int r2 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1))
            if (r2 != 0) goto L_0x00fd
            goto L_0x0129
        L_0x00fd:
            com.alibaba.fastjson.JSONArray r2 = new com.alibaba.fastjson.JSONArray
            int r3 = r0.length
            r2.<init>((int) r3)
            r3 = 0
        L_0x0104:
            int r4 = r0.length
            if (r3 >= r4) goto L_0x0128
            r4 = r0[r3]
            if (r4 != r0) goto L_0x010f
            r2.add(r4)
            goto L_0x0125
        L_0x010f:
            java.lang.Object r5 = r1.getPropertyValue(r4, r8, r9)
            boolean r6 = r5 instanceof java.util.Collection
            if (r6 == 0) goto L_0x011e
            r6 = r5
            java.util.Collection r6 = (java.util.Collection) r6
            r2.addAll(r6)
            goto L_0x0124
        L_0x011e:
            if (r5 == 0) goto L_0x0124
            r2.add(r5)
            goto L_0x0125
        L_0x0124:
        L_0x0125:
            int r3 = r3 + 1
            goto L_0x0104
        L_0x0128:
            return r2
        L_0x0129:
            int r2 = r0.length
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            return r2
        L_0x012f:
            boolean r0 = r11 instanceof java.lang.Enum
            if (r0 == 0) goto L_0x0160
            r4 = -4270347329889690746(0xc4bcadba8e631b86, double:-1.3543099103600943E23)
            r6 = -1014497654951707614(0xf1ebc7c20322fc22, double:-5.788733405278088E240)
            r0 = r11
            java.lang.Enum r0 = (java.lang.Enum) r0
            r14 = -4270347329889690746(0xc4bcadba8e631b86, double:-1.3543099103600943E23)
            int r2 = (r14 > r9 ? 1 : (r14 == r9 ? 0 : -1))
            if (r2 != 0) goto L_0x014e
            java.lang.String r2 = r0.name()
            return r2
        L_0x014e:
            r14 = -1014497654951707614(0xf1ebc7c20322fc22, double:-5.788733405278088E240)
            int r2 = (r14 > r9 ? 1 : (r14 == r9 ? 0 : -1))
            if (r2 != 0) goto L_0x0160
            int r2 = r0.ordinal()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            return r2
        L_0x0160:
            boolean r0 = r11 instanceof java.util.Calendar
            if (r0 == 0) goto L_0x01fa
            r4 = 8963398325558730460(0x7c64634977425edc, double:1.5894872030233988E291)
            r6 = -811277319855450459(0xf4bdc3936faf56a5, double:-2.1821630275621928E254)
            r14 = -3851359326990528739(0xca8d3918f4578f1d, double:-1.3667045267075351E51)
            r16 = 4647432019745535567(0x407efecc7eb5764f, double:495.924925526463)
            r18 = 6607618197526598121(0x5bb2f9bdf2fad1e9, double:5.387565597711505E133)
            r20 = -6586085717218287427(0xa49985ef4cee20bd, double:-2.2473812103034466E-132)
            r0 = r11
            java.util.Calendar r0 = (java.util.Calendar) r0
            r22 = 8963398325558730460(0x7c64634977425edc, double:1.5894872030233988E291)
            int r2 = (r22 > r9 ? 1 : (r22 == r9 ? 0 : -1))
            if (r2 != 0) goto L_0x0198
            r2 = 1
            int r2 = r0.get(r2)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            return r2
        L_0x0198:
            r22 = -811277319855450459(0xf4bdc3936faf56a5, double:-2.1821630275621928E254)
            int r2 = (r22 > r9 ? 1 : (r22 == r9 ? 0 : -1))
            if (r2 != 0) goto L_0x01ab
            r2 = 2
            int r2 = r0.get(r2)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            return r2
        L_0x01ab:
            r22 = -3851359326990528739(0xca8d3918f4578f1d, double:-1.3667045267075351E51)
            int r2 = (r22 > r9 ? 1 : (r22 == r9 ? 0 : -1))
            if (r2 != 0) goto L_0x01be
            r2 = 5
            int r2 = r0.get(r2)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            return r2
        L_0x01be:
            r22 = 4647432019745535567(0x407efecc7eb5764f, double:495.924925526463)
            int r2 = (r22 > r9 ? 1 : (r22 == r9 ? 0 : -1))
            if (r2 != 0) goto L_0x01d2
            r2 = 11
            int r2 = r0.get(r2)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            return r2
        L_0x01d2:
            r22 = 6607618197526598121(0x5bb2f9bdf2fad1e9, double:5.387565597711505E133)
            int r2 = (r22 > r9 ? 1 : (r22 == r9 ? 0 : -1))
            if (r2 != 0) goto L_0x01e6
            r2 = 12
            int r2 = r0.get(r2)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            return r2
        L_0x01e6:
            r22 = -6586085717218287427(0xa49985ef4cee20bd, double:-2.2473812103034466E-132)
            int r2 = (r22 > r9 ? 1 : (r22 == r9 ? 0 : -1))
            if (r2 != 0) goto L_0x01fa
            r2 = 13
            int r2 = r0.get(r2)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            return r2
        L_0x01fa:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.JSONPath.getPropertyValue(java.lang.Object, java.lang.String, long):java.lang.Object");
    }

    /* access modifiers changed from: protected */
    public void deepScan(Object currentObject, String propertyName, List<Object> results) {
        if (currentObject != null) {
            if (currentObject instanceof Map) {
                for (Map.Entry entry : ((Map) currentObject).entrySet()) {
                    Object val = entry.getValue();
                    if (propertyName.equals(entry.getKey())) {
                        if (val instanceof Collection) {
                            results.addAll((Collection) val);
                        } else {
                            results.add(val);
                        }
                    } else if (val != null && !ParserConfig.isPrimitive2(val.getClass())) {
                        deepScan(val, propertyName, results);
                    }
                }
            } else if (currentObject instanceof Collection) {
                for (Object next : (Collection) currentObject) {
                    if (!ParserConfig.isPrimitive2(next.getClass())) {
                        deepScan(next, propertyName, results);
                    }
                }
            } else {
                JavaBeanSerializer beanSerializer = getJavaBeanSerializer(currentObject.getClass());
                if (beanSerializer != null) {
                    try {
                        FieldSerializer fieldDeser = beanSerializer.getFieldSerializer(propertyName);
                        if (fieldDeser != null) {
                            results.add(fieldDeser.getPropertyValueDirect(currentObject));
                            return;
                        }
                        for (Object val2 : beanSerializer.getFieldValues(currentObject)) {
                            deepScan(val2, propertyName, results);
                        }
                    } catch (InvocationTargetException ex) {
                        throw new JSONException("getFieldValue error." + propertyName, ex);
                    } catch (IllegalAccessException ex2) {
                        throw new JSONException("getFieldValue error." + propertyName, ex2);
                    } catch (Exception e) {
                        throw new JSONPathException("jsonpath error, path " + this.path + ", segement " + propertyName, e);
                    }
                } else if (currentObject instanceof List) {
                    List list = (List) currentObject;
                    for (int i = 0; i < list.size(); i++) {
                        deepScan(list.get(i), propertyName, results);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void deepSet(Object currentObject, String propertyName, long propertyNameHash, Object value) {
        Object obj = currentObject;
        String str = propertyName;
        Object obj2 = value;
        if (obj != null) {
            if (obj instanceof Map) {
                Map map = (Map) obj;
                if (map.containsKey(str)) {
                    Object obj3 = map.get(str);
                    map.put(str, obj2);
                    return;
                }
                for (Object val : map.values()) {
                    deepSet(val, propertyName, propertyNameHash, value);
                }
                return;
            }
            Class<?> currentClass = currentObject.getClass();
            JavaBeanDeserializer beanDeserializer = getJavaBeanDeserializer(currentClass);
            if (beanDeserializer != null) {
                try {
                    FieldDeserializer fieldDeser = beanDeserializer.getFieldDeserializer(str);
                    if (fieldDeser != null) {
                        fieldDeser.setValue(obj, obj2);
                        return;
                    }
                    for (Object val2 : getJavaBeanSerializer(currentClass).getObjectFieldValues(obj)) {
                        deepSet(val2, propertyName, propertyNameHash, value);
                    }
                } catch (Exception e) {
                    throw new JSONPathException("jsonpath error, path " + this.path + ", segement " + str, e);
                }
            } else if (obj instanceof List) {
                List list = (List) obj;
                for (int i = 0; i < list.size(); i++) {
                    deepSet(list.get(i), propertyName, propertyNameHash, value);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean setPropertyValue(Object parent, String name, long propertyNameHash, Object value) {
        if (parent instanceof Map) {
            ((Map) parent).put(name, value);
            return true;
        } else if (parent instanceof List) {
            for (Object element : (List) parent) {
                if (element != null) {
                    setPropertyValue(element, name, propertyNameHash, value);
                }
            }
            return true;
        } else {
            ObjectDeserializer deserializer = this.parserConfig.getDeserializer((Type) parent.getClass());
            JavaBeanDeserializer beanDeserializer = null;
            if (deserializer instanceof JavaBeanDeserializer) {
                beanDeserializer = (JavaBeanDeserializer) deserializer;
            }
            if (beanDeserializer != null) {
                FieldDeserializer fieldDeserializer = beanDeserializer.getFieldDeserializer(propertyNameHash);
                if (fieldDeserializer == null) {
                    return false;
                }
                if (value != null) {
                    Class<?> cls = value.getClass();
                    FieldInfo fieldInfo = fieldDeserializer.fieldInfo;
                    if (cls != fieldInfo.fieldClass) {
                        value = TypeUtils.cast(value, fieldInfo.fieldType, this.parserConfig);
                    }
                }
                fieldDeserializer.setValue(parent, value);
                return true;
            }
            throw new UnsupportedOperationException();
        }
    }

    /* access modifiers changed from: protected */
    public boolean removePropertyValue(Object parent, String name, boolean deep) {
        boolean found = false;
        if (parent instanceof Map) {
            if (((Map) parent).remove(name) != null) {
                found = true;
            }
            if (deep) {
                for (Object item : ((Map) parent).values()) {
                    removePropertyValue(item, name, deep);
                }
            }
            return found;
        }
        ObjectDeserializer deserializer = this.parserConfig.getDeserializer((Type) parent.getClass());
        JavaBeanDeserializer beanDeserializer = null;
        if (deserializer instanceof JavaBeanDeserializer) {
            beanDeserializer = (JavaBeanDeserializer) deserializer;
        }
        if (beanDeserializer != null) {
            FieldDeserializer fieldDeserializer = beanDeserializer.getFieldDeserializer(name);
            boolean found2 = false;
            if (fieldDeserializer != null) {
                fieldDeserializer.setValue(parent, (String) null);
                found2 = true;
            }
            if (deep) {
                for (Object item2 : getPropertyValues(parent)) {
                    if (item2 != null) {
                        removePropertyValue(item2, name, deep);
                    }
                }
            }
            return found2;
        } else if (deep) {
            return false;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /* access modifiers changed from: protected */
    public JavaBeanSerializer getJavaBeanSerializer(Class<?> currentClass) {
        ObjectSerializer serializer = this.serializeConfig.getObjectWriter(currentClass);
        if (serializer instanceof JavaBeanSerializer) {
            return (JavaBeanSerializer) serializer;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public JavaBeanDeserializer getJavaBeanDeserializer(Class<?> currentClass) {
        ObjectDeserializer deserializer = this.parserConfig.getDeserializer((Type) currentClass);
        if (deserializer instanceof JavaBeanDeserializer) {
            return (JavaBeanDeserializer) deserializer;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public int evalSize(Object currentObject) {
        if (currentObject == null) {
            return -1;
        }
        if (currentObject instanceof Collection) {
            return ((Collection) currentObject).size();
        }
        if (currentObject instanceof Object[]) {
            return ((Object[]) currentObject).length;
        }
        if (currentObject.getClass().isArray()) {
            return Array.getLength(currentObject);
        }
        if (currentObject instanceof Map) {
            int count = 0;
            for (Object value : ((Map) currentObject).values()) {
                if (value != null) {
                    count++;
                }
            }
            return count;
        }
        JavaBeanSerializer beanSerializer = getJavaBeanSerializer(currentObject.getClass());
        if (beanSerializer == null) {
            return -1;
        }
        try {
            return beanSerializer.getSize(currentObject);
        } catch (Exception e) {
            throw new JSONPathException("evalSize error : " + this.path, e);
        }
    }

    /* access modifiers changed from: package-private */
    public Set<?> evalKeySet(Object currentObject) {
        JavaBeanSerializer beanSerializer;
        if (currentObject == null) {
            return null;
        }
        if (currentObject instanceof Map) {
            return ((Map) currentObject).keySet();
        }
        if ((currentObject instanceof Collection) || (currentObject instanceof Object[]) || currentObject.getClass().isArray() || (beanSerializer = getJavaBeanSerializer(currentObject.getClass())) == null) {
            return null;
        }
        try {
            return beanSerializer.getFieldNames(currentObject);
        } catch (Exception e) {
            throw new JSONPathException("evalKeySet error : " + this.path, e);
        }
    }

    public String toJSONString() {
        return JSON.toJSONString(this.path);
    }

    public static Object reserveToArray(Object object, String... paths) {
        JSONArray reserved = new JSONArray();
        if (paths == null || paths.length == 0) {
            return reserved;
        }
        for (String item : paths) {
            JSONPath path2 = compile(item);
            path2.init();
            reserved.add(path2.eval(object));
        }
        return reserved;
    }

    public static Object reserveToObject(Object object, String... paths) {
        Object value;
        if (paths == null || paths.length == 0) {
            return object;
        }
        JSONObject reserved = new JSONObject(true);
        for (String item : paths) {
            JSONPath path2 = compile(item);
            path2.init();
            Segment[] segmentArr = path2.segments;
            if ((segmentArr[segmentArr.length - 1] instanceof PropertySegment) && (value = path2.eval(object)) != null) {
                path2.set(reserved, value);
            }
        }
        return reserved;
    }
}
