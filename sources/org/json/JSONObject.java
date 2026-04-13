package org.json;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.constant.SpInputType;
import com.google.maps.android.BuildConfig;
import com.meituan.robust.Constants;
import com.tencent.bugly.Bugly;
import java.io.Closeable;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;
import meshsdk.model.json.MeshStorage;

public class JSONObject {
    public static final Object NULL = new Null();
    static final Pattern NUMBER_PATTERN = Pattern.compile("-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?");
    private final Map<String, Object> map;

    public static final class Null {
        private Null() {
        }

        /* access modifiers changed from: protected */
        public final Object clone() {
            return this;
        }

        public boolean equals(Object object) {
            return object == null || object == this;
        }

        public int hashCode() {
            return 0;
        }

        public String toString() {
            return BuildConfig.TRAVIS;
        }
    }

    public JSONObject() {
        this.map = new HashMap();
    }

    public JSONObject(JSONObject jo, String... names) {
        this(names.length);
        for (int i = 0; i < names.length; i++) {
            try {
                putOnce(names[i], jo.opt(names[i]));
            } catch (Exception e) {
            }
        }
    }

    public JSONObject(JSONTokener x) {
        this();
        if (x.nextClean() == '{') {
            while (true) {
                switch (x.nextClean()) {
                    case 0:
                        throw x.syntaxError("A JSONObject text must end with '}'");
                    case '}':
                        return;
                    default:
                        x.back();
                        String key = x.nextValue().toString();
                        if (x.nextClean() == ':') {
                            if (key != null) {
                                if (opt(key) == null) {
                                    Object value = x.nextValue();
                                    if (value != null) {
                                        put(key, value);
                                    }
                                } else {
                                    throw x.syntaxError("Duplicate key \"" + key + "\"");
                                }
                            }
                            switch (x.nextClean()) {
                                case ',':
                                case ';':
                                    if (x.nextClean() != '}') {
                                        x.back();
                                    } else {
                                        return;
                                    }
                                case '}':
                                    return;
                                default:
                                    throw x.syntaxError("Expected a ',' or '}'");
                            }
                        } else {
                            throw x.syntaxError("Expected a ':' after a key");
                        }
                }
            }
        } else {
            throw x.syntaxError("A JSONObject text must begin with '{'");
        }
    }

    public JSONObject(Map<?, ?> m) {
        if (m == null) {
            this.map = new HashMap();
            return;
        }
        this.map = new HashMap(m.size());
        for (Map.Entry<?, ?> e : m.entrySet()) {
            if (e.getKey() != null) {
                Object value = e.getValue();
                if (value != null) {
                    this.map.put(String.valueOf(e.getKey()), wrap(value));
                }
            } else {
                throw new NullPointerException("Null key.");
            }
        }
    }

    public JSONObject(Object bean) {
        this();
        populateMap(bean);
    }

    public JSONObject(Object object, String... names) {
        this(names.length);
        Class<?> c = object.getClass();
        for (String name : names) {
            try {
                putOpt(name, c.getField(name).get(object));
            } catch (Exception e) {
            }
        }
    }

    public JSONObject(String source) {
        this(new JSONTokener(source));
    }

    public JSONObject(String baseName, Locale locale) {
        this();
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, Thread.currentThread().getContextClassLoader());
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            if (key != null) {
                String[] path = ((String) key).split("\\.");
                int last = path.length - 1;
                JSONObject target = this;
                for (int i = 0; i < last; i++) {
                    String segment = path[i];
                    JSONObject nextTarget = target.optJSONObject(segment);
                    if (nextTarget == null) {
                        nextTarget = new JSONObject();
                        target.put(segment, (Object) nextTarget);
                    }
                    target = nextTarget;
                }
                target.put(path[last], (Object) bundle.getString((String) key));
            }
        }
    }

    protected JSONObject(int initialCapacity) {
        this.map = new HashMap(initialCapacity);
    }

    public JSONObject accumulate(String key, Object value) {
        testValidity(value);
        Object object = opt(key);
        if (object == null) {
            put(key, value instanceof JSONArray ? new JSONArray().put(value) : value);
        } else if (object instanceof JSONArray) {
            ((JSONArray) object).put(value);
        } else {
            put(key, (Object) new JSONArray().put(object).put(value));
        }
        return this;
    }

    public JSONObject append(String key, Object value) {
        testValidity(value);
        Object object = opt(key);
        if (object == null) {
            put(key, (Object) new JSONArray().put(value));
        } else if (object instanceof JSONArray) {
            put(key, (Object) ((JSONArray) object).put(value));
        } else {
            throw wrongValueFormatException(key, "JSONArray", (Object) null, (Throwable) null);
        }
        return this;
    }

    public static String doubleToString(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            return BuildConfig.TRAVIS;
        }
        String string = Double.toString(d);
        if (string.indexOf(46) <= 0 || string.indexOf(101) >= 0 || string.indexOf(69) >= 0) {
            return string;
        }
        while (string.endsWith("0")) {
            string = string.substring(0, string.length() - 1);
        }
        if (string.endsWith(".")) {
            return string.substring(0, string.length() - 1);
        }
        return string;
    }

    public Object get(String key) {
        if (key != null) {
            Object object = opt(key);
            if (object != null) {
                return object;
            }
            throw new JSONException("JSONObject[" + quote(key) + "] not found.");
        }
        throw new JSONException("Null key.");
    }

    public <E extends Enum<E>> E getEnum(Class<E> clazz, String key) {
        E val = optEnum(clazz, key);
        if (val != null) {
            return val;
        }
        throw wrongValueFormatException(key, "enum of type " + quote(clazz.getSimpleName()), (Throwable) null);
    }

    public boolean getBoolean(String key) {
        Object object = get(key);
        if (object.equals(Boolean.FALSE)) {
            return false;
        }
        if ((object instanceof String) && ((String) object).equalsIgnoreCase(Bugly.SDK_IS_DEV)) {
            return false;
        }
        if (object.equals(Boolean.TRUE)) {
            return true;
        }
        if ((object instanceof String) && ((String) object).equalsIgnoreCase("true")) {
            return true;
        }
        throw wrongValueFormatException(key, SpInputType.BOOLEAN, (Throwable) null);
    }

    public BigInteger getBigInteger(String key) {
        Object object = get(key);
        BigInteger ret = objectToBigInteger(object, (BigInteger) null);
        if (ret != null) {
            return ret;
        }
        throw wrongValueFormatException(key, "BigInteger", object, (Throwable) null);
    }

    public BigDecimal getBigDecimal(String key) {
        Object object = get(key);
        BigDecimal ret = objectToBigDecimal(object, (BigDecimal) null);
        if (ret != null) {
            return ret;
        }
        throw wrongValueFormatException(key, "BigDecimal", object, (Throwable) null);
    }

    public double getDouble(String key) {
        Object object = get(key);
        if (object instanceof Number) {
            return ((Number) object).doubleValue();
        }
        try {
            return Double.parseDouble(object.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(key, Constants.DOUBLE, e);
        }
    }

    public float getFloat(String key) {
        Object object = get(key);
        if (object instanceof Number) {
            return ((Number) object).floatValue();
        }
        try {
            return Float.parseFloat(object.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(key, "float", e);
        }
    }

    public Number getNumber(String key) {
        Object object = get(key);
        try {
            if (object instanceof Number) {
                return (Number) object;
            }
            return stringToNumber(object.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(key, "number", e);
        }
    }

    public int getInt(String key) {
        Object object = get(key);
        if (object instanceof Number) {
            return ((Number) object).intValue();
        }
        try {
            return Integer.parseInt(object.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(key, Constants.INT, e);
        }
    }

    public JSONArray getJSONArray(String key) {
        Object object = get(key);
        if (object instanceof JSONArray) {
            return (JSONArray) object;
        }
        throw wrongValueFormatException(key, "JSONArray", (Throwable) null);
    }

    public JSONObject getJSONObject(String key) {
        Object object = get(key);
        if (object instanceof JSONObject) {
            return (JSONObject) object;
        }
        throw wrongValueFormatException(key, "JSONObject", (Throwable) null);
    }

    public long getLong(String key) {
        Object object = get(key);
        if (object instanceof Number) {
            return ((Number) object).longValue();
        }
        try {
            return Long.parseLong(object.toString());
        } catch (Exception e) {
            throw wrongValueFormatException(key, Constants.LONG, e);
        }
    }

    public static String[] getNames(JSONObject jo) {
        if (jo.isEmpty()) {
            return null;
        }
        return (String[]) jo.keySet().toArray(new String[jo.length()]);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0004, code lost:
        r2 = r6.getClass().getFields();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String[] getNames(java.lang.Object r6) {
        /*
            r0 = 0
            if (r6 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.Class r1 = r6.getClass()
            java.lang.reflect.Field[] r2 = r1.getFields()
            int r3 = r2.length
            if (r3 != 0) goto L_0x0010
            return r0
        L_0x0010:
            java.lang.String[] r0 = new java.lang.String[r3]
            r4 = 0
        L_0x0013:
            if (r4 >= r3) goto L_0x0020
            r5 = r2[r4]
            java.lang.String r5 = r5.getName()
            r0[r4] = r5
            int r4 = r4 + 1
            goto L_0x0013
        L_0x0020:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.JSONObject.getNames(java.lang.Object):java.lang.String[]");
    }

    public String getString(String key) {
        Object object = get(key);
        if (object instanceof String) {
            return (String) object;
        }
        throw wrongValueFormatException(key, TypedValues.Custom.S_STRING, (Throwable) null);
    }

    public boolean has(String key) {
        return this.map.containsKey(key);
    }

    public JSONObject increment(String key) {
        Object value = opt(key);
        if (value == null) {
            put(key, 1);
        } else if (value instanceof Integer) {
            put(key, ((Integer) value).intValue() + 1);
        } else if (value instanceof Long) {
            put(key, ((Long) value).longValue() + 1);
        } else if (value instanceof BigInteger) {
            put(key, (Object) ((BigInteger) value).add(BigInteger.ONE));
        } else if (value instanceof Float) {
            put(key, ((Float) value).floatValue() + 1.0f);
        } else if (value instanceof Double) {
            put(key, ((Double) value).doubleValue() + 1.0d);
        } else if (value instanceof BigDecimal) {
            put(key, (Object) ((BigDecimal) value).add(BigDecimal.ONE));
        } else {
            throw new JSONException("Unable to increment [" + quote(key) + "].");
        }
        return this;
    }

    public boolean isNull(String key) {
        return NULL.equals(opt(key));
    }

    public Iterator<String> keys() {
        return keySet().iterator();
    }

    public Set<String> keySet() {
        return this.map.keySet();
    }

    /* access modifiers changed from: protected */
    public Set<Map.Entry<String, Object>> entrySet() {
        return this.map.entrySet();
    }

    public int length() {
        return this.map.size();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public JSONArray names() {
        if (this.map.isEmpty()) {
            return null;
        }
        return new JSONArray((Collection<?>) this.map.keySet());
    }

    public static String numberToString(Number number) {
        if (number != null) {
            testValidity(number);
            String string = number.toString();
            if (string.indexOf(46) <= 0 || string.indexOf(101) >= 0 || string.indexOf(69) >= 0) {
                return string;
            }
            while (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith(".")) {
                return string.substring(0, string.length() - 1);
            }
            return string;
        }
        throw new JSONException("Null pointer");
    }

    public Object opt(String key) {
        if (key == null) {
            return null;
        }
        return this.map.get(key);
    }

    public <E extends Enum<E>> E optEnum(Class<E> clazz, String key) {
        return optEnum(clazz, key, (Enum) null);
    }

    public <E extends Enum<E>> E optEnum(Class<E> clazz, String key, E defaultValue) {
        try {
            E opt = opt(key);
            if (NULL.equals(opt)) {
                return defaultValue;
            }
            if (clazz.isAssignableFrom(opt.getClass())) {
                return (Enum) opt;
            }
            return Enum.valueOf(clazz, opt.toString());
        } catch (IllegalArgumentException e) {
            return defaultValue;
        } catch (NullPointerException e2) {
            return defaultValue;
        }
    }

    public boolean optBoolean(String key) {
        return optBoolean(key, false);
    }

    public boolean optBoolean(String key, boolean defaultValue) {
        Object val = opt(key);
        if (NULL.equals(val)) {
            return defaultValue;
        }
        if (val instanceof Boolean) {
            return ((Boolean) val).booleanValue();
        }
        try {
            return getBoolean(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public BigDecimal optBigDecimal(String key, BigDecimal defaultValue) {
        return objectToBigDecimal(opt(key), defaultValue);
    }

    static BigDecimal objectToBigDecimal(Object val, BigDecimal defaultValue) {
        if (NULL.equals(val)) {
            return defaultValue;
        }
        if (val instanceof BigDecimal) {
            return (BigDecimal) val;
        }
        if (val instanceof BigInteger) {
            return new BigDecimal((BigInteger) val);
        }
        if ((val instanceof Double) || (val instanceof Float)) {
            if (Double.isNaN(((Number) val).doubleValue())) {
                return defaultValue;
            }
            return new BigDecimal(((Number) val).doubleValue());
        } else if ((val instanceof Long) || (val instanceof Integer) || (val instanceof Short) || (val instanceof Byte)) {
            return new BigDecimal(((Number) val).longValue());
        } else {
            try {
                return new BigDecimal(val.toString());
            } catch (Exception e) {
                return defaultValue;
            }
        }
    }

    public BigInteger optBigInteger(String key, BigInteger defaultValue) {
        return objectToBigInteger(opt(key), defaultValue);
    }

    static BigInteger objectToBigInteger(Object val, BigInteger defaultValue) {
        if (NULL.equals(val)) {
            return defaultValue;
        }
        if (val instanceof BigInteger) {
            return (BigInteger) val;
        }
        if (val instanceof BigDecimal) {
            return ((BigDecimal) val).toBigInteger();
        }
        if ((val instanceof Double) || (val instanceof Float)) {
            double d = ((Number) val).doubleValue();
            if (Double.isNaN(d)) {
                return defaultValue;
            }
            return new BigDecimal(d).toBigInteger();
        } else if ((val instanceof Long) || (val instanceof Integer) || (val instanceof Short) || (val instanceof Byte)) {
            return BigInteger.valueOf(((Number) val).longValue());
        } else {
            try {
                String valStr = val.toString();
                if (isDecimalNotation(valStr)) {
                    return new BigDecimal(valStr).toBigInteger();
                }
                return new BigInteger(valStr);
            } catch (Exception e) {
                return defaultValue;
            }
        }
    }

    public double optDouble(String key) {
        return optDouble(key, Double.NaN);
    }

    public double optDouble(String key, double defaultValue) {
        Number val = optNumber(key);
        if (val == null) {
            return defaultValue;
        }
        return val.doubleValue();
    }

    public float optFloat(String key) {
        return optFloat(key, Float.NaN);
    }

    public float optFloat(String key, float defaultValue) {
        Number val = optNumber(key);
        if (val == null) {
            return defaultValue;
        }
        return val.floatValue();
    }

    public int optInt(String key) {
        return optInt(key, 0);
    }

    public int optInt(String key, int defaultValue) {
        Number val = optNumber(key, (Number) null);
        if (val == null) {
            return defaultValue;
        }
        return val.intValue();
    }

    public JSONArray optJSONArray(String key) {
        Object o = opt(key);
        if (o instanceof JSONArray) {
            return (JSONArray) o;
        }
        return null;
    }

    public JSONObject optJSONObject(String key) {
        Object object = opt(key);
        if (object instanceof JSONObject) {
            return (JSONObject) object;
        }
        return null;
    }

    public long optLong(String key) {
        return optLong(key, 0);
    }

    public long optLong(String key, long defaultValue) {
        Number val = optNumber(key, (Number) null);
        if (val == null) {
            return defaultValue;
        }
        return val.longValue();
    }

    public Number optNumber(String key) {
        return optNumber(key, (Number) null);
    }

    public Number optNumber(String key, Number defaultValue) {
        Object val = opt(key);
        if (NULL.equals(val)) {
            return defaultValue;
        }
        if (val instanceof Number) {
            return (Number) val;
        }
        try {
            return stringToNumber(val.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public String optString(String key) {
        return optString(key, "");
    }

    public String optString(String key, String defaultValue) {
        Object object = opt(key);
        return NULL.equals(object) ? defaultValue : object.toString();
    }

    private void populateMap(Object bean) {
        String key;
        Class<?> klass = bean.getClass();
        for (Method method : klass.getClassLoader() != null ? klass.getMethods() : klass.getDeclaredMethods()) {
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers) && method.getParameterTypes().length == 0 && !method.isBridge() && method.getReturnType() != Void.TYPE && isValidMethodName(method.getName()) && (key = getKeyNameFromMethod(method)) != null && !key.isEmpty()) {
                try {
                    Object result = method.invoke(bean, new Object[0]);
                    if (result != null) {
                        this.map.put(key, wrap(result));
                        if (result instanceof Closeable) {
                            try {
                                ((Closeable) result).close();
                            } catch (IOException e) {
                            }
                        }
                    }
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
                }
            }
        }
    }

    private static boolean isValidMethodName(String name) {
        return !"getClass".equals(name) && !"getDeclaringClass".equals(name);
    }

    private static String getKeyNameFromMethod(Method method) {
        String key;
        int forcedNameDepth;
        int ignoreDepth = getAnnotationDepth(method, JSONPropertyIgnore.class);
        if (ignoreDepth > 0 && ((forcedNameDepth = getAnnotationDepth(method, JSONPropertyName.class)) < 0 || ignoreDepth <= forcedNameDepth)) {
            return null;
        }
        JSONPropertyName annotation = (JSONPropertyName) getAnnotation(method, JSONPropertyName.class);
        if (annotation != null && annotation.value() != null && !annotation.value().isEmpty()) {
            return annotation.value();
        }
        String name = method.getName();
        if (name.startsWith("get") && name.length() > 3) {
            key = name.substring(3);
        } else if (!name.startsWith("is") || name.length() <= 2) {
            return null;
        } else {
            key = name.substring(2);
        }
        if (Character.isLowerCase(key.charAt(0))) {
            return null;
        }
        if (key.length() == 1) {
            return key.toLowerCase(Locale.ROOT);
        }
        if (Character.isUpperCase(key.charAt(1))) {
            return key;
        }
        return key.substring(0, 1).toLowerCase(Locale.ROOT) + key.substring(1);
    }

    private static <A extends Annotation> A getAnnotation(Method m, Class<A> annotationClass) {
        if (m == null || annotationClass == null) {
            return null;
        }
        if (m.isAnnotationPresent(annotationClass)) {
            return m.getAnnotation(annotationClass);
        }
        Class<?> c = m.getDeclaringClass();
        if (c.getSuperclass() == null) {
            return null;
        }
        Class<?>[] interfaces = c.getInterfaces();
        int length = interfaces.length;
        int i = 0;
        while (i < length) {
            try {
                return getAnnotation(interfaces[i].getMethod(m.getName(), m.getParameterTypes()), annotationClass);
            } catch (NoSuchMethodException | SecurityException e) {
                i++;
            }
        }
        try {
            return getAnnotation(c.getSuperclass().getMethod(m.getName(), m.getParameterTypes()), annotationClass);
        } catch (SecurityException e2) {
            return null;
        } catch (NoSuchMethodException e3) {
            return null;
        }
    }

    private static int getAnnotationDepth(Method m, Class<? extends Annotation> annotationClass) {
        if (m == null || annotationClass == null) {
            return -1;
        }
        if (m.isAnnotationPresent(annotationClass)) {
            return 1;
        }
        Class<?> c = m.getDeclaringClass();
        if (c.getSuperclass() == null) {
            return -1;
        }
        Class<?>[] interfaces = c.getInterfaces();
        int length = interfaces.length;
        int i = 0;
        while (i < length) {
            try {
                int d = getAnnotationDepth(interfaces[i].getMethod(m.getName(), m.getParameterTypes()), annotationClass);
                if (d > 0) {
                    return d + 1;
                }
                i++;
            } catch (NoSuchMethodException | SecurityException e) {
            }
        }
        try {
            int d2 = getAnnotationDepth(c.getSuperclass().getMethod(m.getName(), m.getParameterTypes()), annotationClass);
            if (d2 > 0) {
                return d2 + 1;
            }
            return -1;
        } catch (SecurityException e2) {
            return -1;
        } catch (NoSuchMethodException e3) {
            return -1;
        }
    }

    public JSONObject put(String key, boolean value) {
        return put(key, (Object) value ? Boolean.TRUE : Boolean.FALSE);
    }

    public JSONObject put(String key, Collection<?> value) {
        return put(key, (Object) new JSONArray(value));
    }

    public JSONObject put(String key, double value) {
        return put(key, (Object) Double.valueOf(value));
    }

    public JSONObject put(String key, float value) {
        return put(key, (Object) Float.valueOf(value));
    }

    public JSONObject put(String key, int value) {
        return put(key, (Object) Integer.valueOf(value));
    }

    public JSONObject put(String key, long value) {
        return put(key, (Object) Long.valueOf(value));
    }

    public JSONObject put(String key, Map<?, ?> value) {
        return put(key, (Object) new JSONObject(value));
    }

    public JSONObject put(String key, Object value) {
        if (key != null) {
            if (value != null) {
                testValidity(value);
                this.map.put(key, value);
            } else {
                remove(key);
            }
            return this;
        }
        throw new NullPointerException("Null key.");
    }

    public JSONObject putOnce(String key, Object value) {
        if (key == null || value == null) {
            return this;
        }
        if (opt(key) == null) {
            return put(key, value);
        }
        throw new JSONException("Duplicate key \"" + key + "\"");
    }

    public JSONObject putOpt(String key, Object value) {
        if (key == null || value == null) {
            return this;
        }
        return put(key, value);
    }

    public Object query(String jsonPointer) {
        return query(new JSONPointer(jsonPointer));
    }

    public Object query(JSONPointer jsonPointer) {
        return jsonPointer.queryFrom(this);
    }

    public Object optQuery(String jsonPointer) {
        return optQuery(new JSONPointer(jsonPointer));
    }

    public Object optQuery(JSONPointer jsonPointer) {
        try {
            return jsonPointer.queryFrom(this);
        } catch (JSONPointerException e) {
            return null;
        }
    }

    public static String quote(String string) {
        String obj;
        StringWriter sw = new StringWriter();
        synchronized (sw.getBuffer()) {
            try {
                obj = quote(string, sw).toString();
            } catch (IOException e) {
                return "";
            } catch (Throwable th) {
                throw th;
            }
        }
        return obj;
    }

    public static Writer quote(String string, Writer w) {
        if (string == null || string.isEmpty()) {
            w.write("\"\"");
            return w;
        }
        char c = 0;
        int len = string.length();
        w.write(34);
        for (int i = 0; i < len; i++) {
            char b = c;
            c = string.charAt(i);
            switch (c) {
                case 8:
                    w.write("\\b");
                    break;
                case 9:
                    w.write("\\t");
                    break;
                case 10:
                    w.write("\\n");
                    break;
                case 12:
                    w.write("\\f");
                    break;
                case 13:
                    w.write("\\r");
                    break;
                case '\"':
                case '\\':
                    w.write(92);
                    w.write(c);
                    break;
                case '/':
                    if (b == '<') {
                        w.write(92);
                    }
                    w.write(c);
                    break;
                default:
                    if (c >= ' ' && ((c < 128 || c >= 160) && (c < 8192 || c >= 8448))) {
                        w.write(c);
                        break;
                    } else {
                        w.write("\\u");
                        String hhhh = Integer.toHexString(c);
                        w.write(MeshStorage.Defaults.ADDRESS_INVALID, 0, 4 - hhhh.length());
                        w.write(hhhh);
                        break;
                    }
                    break;
            }
        }
        w.write(34);
        return w;
    }

    public Object remove(String key) {
        return this.map.remove(key);
    }

    public boolean similar(Object other) {
        try {
            if (!(other instanceof JSONObject) || !keySet().equals(((JSONObject) other).keySet())) {
                return false;
            }
            for (Map.Entry<String, ?> entry : entrySet()) {
                Object valueThis = entry.getValue();
                Object valueOther = ((JSONObject) other).get(entry.getKey());
                if (valueThis != valueOther) {
                    if (valueThis == null) {
                        return false;
                    }
                    if (valueThis instanceof JSONObject) {
                        if (!((JSONObject) valueThis).similar(valueOther)) {
                            return false;
                        }
                    } else if (valueThis instanceof JSONArray) {
                        if (!((JSONArray) valueThis).similar(valueOther)) {
                            return false;
                        }
                    } else if (!valueThis.equals(valueOther)) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    protected static boolean isDecimalNotation(String val) {
        return val.indexOf(46) > -1 || val.indexOf(101) > -1 || val.indexOf(69) > -1 || "-0".equals(val);
    }

    protected static Number stringToNumber(String val) {
        char initial = val.charAt(0);
        if ((initial < '0' || initial > '9') && initial != '-') {
            throw new NumberFormatException("val [" + val + "] is not a valid number.");
        } else if (!isDecimalNotation(val)) {
            BigInteger bi = new BigInteger(val);
            if (bi.bitLength() <= 31) {
                return Integer.valueOf(bi.intValue());
            }
            if (bi.bitLength() <= 63) {
                return Long.valueOf(bi.longValue());
            }
            return bi;
        } else if (val.length() > 14) {
            return new BigDecimal(val);
        } else {
            Double d = Double.valueOf(val);
            if (d.isInfinite() || d.isNaN()) {
                return new BigDecimal(val);
            }
            return d;
        }
    }

    public static Object stringToValue(String string) {
        if ("".equals(string)) {
            return string;
        }
        if ("true".equalsIgnoreCase(string)) {
            return Boolean.TRUE;
        }
        if (Bugly.SDK_IS_DEV.equalsIgnoreCase(string)) {
            return Boolean.FALSE;
        }
        if (BuildConfig.TRAVIS.equalsIgnoreCase(string)) {
            return NULL;
        }
        char initial = string.charAt(0);
        if ((initial >= '0' && initial <= '9') || initial == '-') {
            try {
                if (isDecimalNotation(string)) {
                    Double d = Double.valueOf(string);
                    if (!d.isInfinite() && !d.isNaN()) {
                        return d;
                    }
                } else {
                    Long myLong = Long.valueOf(string);
                    if (string.equals(myLong.toString())) {
                        if (myLong.longValue() == ((long) myLong.intValue())) {
                            return Integer.valueOf(myLong.intValue());
                        }
                        return myLong;
                    }
                }
            } catch (Exception e) {
            }
        }
        return string;
    }

    public static void testValidity(Object o) {
        if (o == null) {
            return;
        }
        if (o instanceof Double) {
            if (((Double) o).isInfinite() || ((Double) o).isNaN()) {
                throw new JSONException("JSON does not allow non-finite numbers.");
            }
        } else if (!(o instanceof Float)) {
        } else {
            if (((Float) o).isInfinite() || ((Float) o).isNaN()) {
                throw new JSONException("JSON does not allow non-finite numbers.");
            }
        }
    }

    public JSONArray toJSONArray(JSONArray names) {
        if (names == null || names.isEmpty()) {
            return null;
        }
        JSONArray ja = new JSONArray();
        for (int i = 0; i < names.length(); i++) {
            ja.put(opt(names.getString(i)));
        }
        return ja;
    }

    public String toString() {
        try {
            return toString(0);
        } catch (Exception e) {
            return null;
        }
    }

    public String toString(int indentFactor) {
        String obj;
        StringWriter w = new StringWriter();
        synchronized (w.getBuffer()) {
            obj = write(w, indentFactor, 0).toString();
        }
        return obj;
    }

    public static String valueToString(Object value) {
        return JSONWriter.valueToString(value);
    }

    public static Object wrap(Object object) {
        if (object == null) {
            try {
                return NULL;
            } catch (Exception e) {
                return null;
            }
        } else {
            if (!(object instanceof JSONObject) && !(object instanceof JSONArray) && !NULL.equals(object) && !(object instanceof JSONString) && !(object instanceof Byte) && !(object instanceof Character) && !(object instanceof Short) && !(object instanceof Integer) && !(object instanceof Long) && !(object instanceof Boolean) && !(object instanceof Float) && !(object instanceof Double) && !(object instanceof String) && !(object instanceof BigInteger) && !(object instanceof BigDecimal)) {
                if (!(object instanceof Enum)) {
                    if (object instanceof Collection) {
                        return new JSONArray((Collection) object);
                    }
                    if (object.getClass().isArray()) {
                        return new JSONArray(object);
                    }
                    if (object instanceof Map) {
                        return new JSONObject((Map) object);
                    }
                    Package objectPackage = object.getClass().getPackage();
                    String objectPackageName = objectPackage != null ? objectPackage.getName() : "";
                    if (!objectPackageName.startsWith("java.") && !objectPackageName.startsWith("javax.")) {
                        if (object.getClass().getClassLoader() != null) {
                            return new JSONObject(object);
                        }
                    }
                    return object.toString();
                }
            }
            return object;
        }
    }

    public Writer write(Writer writer) {
        return write(writer, 0, 0);
    }

    static final Writer writeValue(Writer writer, Object value, int indentFactor, int indent) {
        if (value == null || value.equals((Object) null)) {
            writer.write(BuildConfig.TRAVIS);
        } else if (value instanceof JSONString) {
            try {
                Object o = ((JSONString) value).toJSONString();
                writer.write(o != null ? o.toString() : quote(value.toString()));
            } catch (Exception e) {
                throw new JSONException((Throwable) e);
            }
        } else if (value instanceof Number) {
            String numberAsString = numberToString((Number) value);
            if (NUMBER_PATTERN.matcher(numberAsString).matches()) {
                writer.write(numberAsString);
            } else {
                quote(numberAsString, writer);
            }
        } else if (value instanceof Boolean) {
            writer.write(value.toString());
        } else if (value instanceof Enum) {
            writer.write(quote(((Enum) value).name()));
        } else if (value instanceof JSONObject) {
            ((JSONObject) value).write(writer, indentFactor, indent);
        } else if (value instanceof JSONArray) {
            ((JSONArray) value).write(writer, indentFactor, indent);
        } else if (value instanceof Map) {
            new JSONObject((Map) value).write(writer, indentFactor, indent);
        } else if (value instanceof Collection) {
            new JSONArray((Collection) value).write(writer, indentFactor, indent);
        } else if (value.getClass().isArray()) {
            new JSONArray(value).write(writer, indentFactor, indent);
        } else {
            quote(value.toString(), writer);
        }
        return writer;
    }

    static final void indent(Writer writer, int indent) {
        for (int i = 0; i < indent; i++) {
            writer.write(32);
        }
    }

    public Writer write(Writer writer, int indentFactor, int indent) {
        String key;
        String key2;
        boolean needsComma = false;
        try {
            int length = length();
            writer.write(123);
            if (length == 1) {
                Map.Entry<String, ?> entry = entrySet().iterator().next();
                key2 = entry.getKey();
                writer.write(quote(key2));
                writer.write(58);
                if (indentFactor > 0) {
                    writer.write(32);
                }
                writeValue(writer, entry.getValue(), indentFactor, indent);
            } else if (length != 0) {
                int newIndent = indent + indentFactor;
                for (Map.Entry<String, ?> entry2 : entrySet()) {
                    if (needsComma) {
                        writer.write(44);
                    }
                    if (indentFactor > 0) {
                        writer.write(10);
                    }
                    indent(writer, newIndent);
                    key = entry2.getKey();
                    writer.write(quote(key));
                    writer.write(58);
                    if (indentFactor > 0) {
                        writer.write(32);
                    }
                    writeValue(writer, entry2.getValue(), indentFactor, newIndent);
                    needsComma = true;
                }
                if (indentFactor > 0) {
                    writer.write(10);
                }
                indent(writer, indent);
            }
            writer.write(125);
            return writer;
        } catch (Exception e) {
            throw new JSONException("Unable to write JSONObject value for key: " + key, e);
        } catch (Exception e2) {
            throw new JSONException("Unable to write JSONObject value for key: " + key2, e2);
        } catch (IOException exception) {
            throw new JSONException((Throwable) exception);
        }
    }

    public Map<String, Object> toMap() {
        Object value;
        Map<String, Object> results = new HashMap<>();
        for (Map.Entry<String, Object> entry : entrySet()) {
            if (entry.getValue() == null || NULL.equals(entry.getValue())) {
                value = null;
            } else if (entry.getValue() instanceof JSONObject) {
                value = ((JSONObject) entry.getValue()).toMap();
            } else if (entry.getValue() instanceof JSONArray) {
                value = ((JSONArray) entry.getValue()).toList();
            } else {
                value = entry.getValue();
            }
            results.put(entry.getKey(), value);
        }
        return results;
    }

    private static JSONException wrongValueFormatException(String key, String valueType, Throwable cause) {
        return new JSONException("JSONObject[" + quote(key) + "] is not a " + valueType + ".", cause);
    }

    private static JSONException wrongValueFormatException(String key, String valueType, Object value, Throwable cause) {
        return new JSONException("JSONObject[" + quote(key) + "] is not a " + valueType + " (" + value + ").", cause);
    }
}
