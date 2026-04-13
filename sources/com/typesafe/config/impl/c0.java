package com.typesafe.config.impl;

import com.meituan.robust.Constants;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigMemorySize;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.b;
import com.typesafe.config.c;
import com.typesafe.config.d;
import com.typesafe.config.e;
import com.typesafe.config.f;
import com.typesafe.config.h;
import com.typesafe.config.impl.SimpleConfig;
import com.typesafe.config.j;
import com.typesafe.config.k;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* compiled from: SimpleConfig */
public final class c0 implements com.typesafe.config.a, q, Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = 1;
    private final a object;

    static {
        Class<c0> cls = c0.class;
    }

    c0(a object2) {
        this.object = object2;
    }

    public a root() {
        return this.object;
    }

    public f origin() {
        return this.object.origin();
    }

    public c0 resolve() {
        return resolve(h.a());
    }

    public c0 resolve(h options) {
        return resolveWith((com.typesafe.config.a) this, options);
    }

    public c0 resolveWith(com.typesafe.config.a source) {
        return resolveWith(source, h.a());
    }

    public c0 resolveWith(com.typesafe.config.a source, h options) {
        AbstractConfigValue resolved = w.k(this.object, ((c0) source).object, options);
        if (resolved == this.object) {
            return this;
        }
        return new c0((a) resolved);
    }

    private j y(String pathExpression) {
        s path = s.g(pathExpression);
        try {
            return this.object.peekPath(path);
        } catch (ConfigException.NotResolved e) {
            throw f.c(path, e);
        }
    }

    public boolean hasPath(String pathExpression) {
        j peeked = y(pathExpression);
        return (peeked == null || peeked.valueType() == k.NULL) ? false : true;
    }

    public boolean hasPathOrNull(String path) {
        return y(path) != null;
    }

    public boolean isEmpty() {
        return this.object.isEmpty();
    }

    private static void q(Set<Map.Entry<String, j>> entries, s parent, a obj) {
        for (Map.Entry<String, ConfigValue> entry : obj.entrySet()) {
            j v = (j) entry.getValue();
            s path = s.f(entry.getKey());
            if (parent != null) {
                path = path.i(parent);
            }
            if (v instanceof a) {
                q(entries, path, (a) v);
            } else if (!(v instanceof j)) {
                entries.add(new AbstractMap.SimpleImmutableEntry(path.k(), v));
            }
        }
    }

    public Set<Map.Entry<String, j>> entrySet() {
        Set<Map.Entry<String, ConfigValue>> entries = new HashSet<>();
        q(entries, (s) null, this.object);
        return entries;
    }

    private static AbstractConfigValue C(AbstractConfigValue v, k expected, s originalPath) {
        if (v.valueType() != k.NULL) {
            return v;
        }
        throw new ConfigException.Null(v.origin(), originalPath.k(), expected != null ? expected.name() : null);
    }

    private static AbstractConfigValue l(a self, String key, k expected, s originalPath) {
        return C(m(self, key, expected, originalPath), expected, originalPath);
    }

    private static AbstractConfigValue m(a self, String key, k expected, s originalPath) {
        AbstractConfigValue v = self.peekAssumingResolved(key, originalPath);
        if (v != null) {
            if (expected != null) {
                v = o.a(v, expected);
            }
            if (expected == null || v.valueType() == expected || v.valueType() == k.NULL) {
                return v;
            }
            throw new ConfigException.WrongType(v.origin(), originalPath.k(), expected.name(), v.valueType().name());
        }
        throw new ConfigException.Missing(originalPath.k());
    }

    private static AbstractConfigValue n(a self, s path, k expected, s originalPath) {
        try {
            String key = path.b();
            s next = path.j();
            if (next == null) {
                return m(self, key, expected, originalPath);
            }
            a o = (a) l(self, key, k.OBJECT, originalPath.m(0, originalPath.e() - next.e()));
            if (o != null) {
                return n(o, next, expected, originalPath);
            }
            throw new AssertionError();
        } catch (ConfigException.NotResolved e) {
            throw f.c(path, e);
        }
    }

    /* access modifiers changed from: package-private */
    public AbstractConfigValue find(s pathExpression, k expected, s originalPath) {
        return C(n(this.object, pathExpression, expected, originalPath), expected, originalPath);
    }

    /* access modifiers changed from: package-private */
    public AbstractConfigValue find(String pathExpression, k expected) {
        s path = s.g(pathExpression);
        return find(path, expected, path);
    }

    private AbstractConfigValue o(s pathExpression, k expected, s originalPath) {
        return n(this.object, pathExpression, expected, originalPath);
    }

    private AbstractConfigValue p(String pathExpression, k expected) {
        s path = s.g(pathExpression);
        return o(path, expected, path);
    }

    public AbstractConfigValue getValue(String path) {
        return find(path, (k) null);
    }

    public boolean getIsNull(String path) {
        return p(path, (k) null).valueType() == k.NULL;
    }

    public boolean getBoolean(String path) {
        return ((Boolean) find(path, k.BOOLEAN).unwrapped()).booleanValue();
    }

    private k r(String path) {
        return (k) find(path, k.NUMBER);
    }

    public Number getNumber(String path) {
        return r(path).unwrapped();
    }

    public int getInt(String path) {
        return r(path).intValueRangeChecked(path);
    }

    public long getLong(String path) {
        return getNumber(path).longValue();
    }

    public double getDouble(String path) {
        return getNumber(path).doubleValue();
    }

    public String getString(String path) {
        return (String) find(path, k.STRING).unwrapped();
    }

    public <T extends Enum<T>> T getEnum(Class<T> enumClass, String path) {
        return u(path, enumClass, find(path, k.STRING));
    }

    public b getList(String path) {
        return (b) find(path, k.LIST);
    }

    public a getObject(String path) {
        return (a) find(path, k.OBJECT);
    }

    public c0 getConfig(String path) {
        return getObject(path).toConfig();
    }

    public Object getAnyRef(String path) {
        return find(path, (k) null).unwrapped();
    }

    public Long getBytes(String path) {
        try {
            return Long.valueOf(getLong(path));
        } catch (ConfigException.WrongType e) {
            j v = find(path, k.STRING);
            return Long.valueOf(parseBytes((String) v.unwrapped(), v.origin(), path));
        }
    }

    public c getMemorySize(String path) {
        return c.a(getBytes(path).longValue());
    }

    @Deprecated
    public Long getMilliseconds(String path) {
        return Long.valueOf(getDuration(path, TimeUnit.MILLISECONDS));
    }

    @Deprecated
    public Long getNanoseconds(String path) {
        return Long.valueOf(getDuration(path, TimeUnit.NANOSECONDS));
    }

    public long getDuration(String path, TimeUnit unit) {
        j v = find(path, k.STRING);
        return unit.convert(parseDuration((String) v.unwrapped(), v.origin(), path), TimeUnit.NANOSECONDS);
    }

    public Duration getDuration(String path) {
        j v = find(path, k.STRING);
        return Duration.ofNanos(parseDuration((String) v.unwrapped(), v.origin(), path));
    }

    private <T> List<T> v(String path, k expected) {
        List<T> l = new ArrayList<>();
        Iterator<? extends ConfigValue> it = getList(path).iterator();
        while (it.hasNext()) {
            AbstractConfigValue v = (AbstractConfigValue) ((j) it.next());
            if (expected != null) {
                v = o.a(v, expected);
            }
            if (v.valueType() == expected) {
                l.add(v.unwrapped());
            } else {
                throw new ConfigException.WrongType(v.origin(), path, "list of " + expected.name(), "list of " + v.valueType().name());
            }
        }
        return l;
    }

    public List<Boolean> getBooleanList(String path) {
        return v(path, k.BOOLEAN);
    }

    public List<Number> getNumberList(String path) {
        return v(path, k.NUMBER);
    }

    public List<Integer> getIntList(String path) {
        List<Integer> l = new ArrayList<>();
        Iterator<AbstractConfigValue> it = w(path, k.NUMBER).iterator();
        while (it.hasNext()) {
            l.add(Integer.valueOf(((k) it.next()).intValueRangeChecked(path)));
        }
        return l;
    }

    public List<Long> getLongList(String path) {
        List<Long> l = new ArrayList<>();
        for (Number n : getNumberList(path)) {
            l.add(Long.valueOf(n.longValue()));
        }
        return l;
    }

    public List<Double> getDoubleList(String path) {
        List<Double> l = new ArrayList<>();
        for (Number n : getNumberList(path)) {
            l.add(Double.valueOf(n.doubleValue()));
        }
        return l;
    }

    public List<String> getStringList(String path) {
        return v(path, k.STRING);
    }

    public <T extends Enum<T>> List<T> getEnumList(Class<T> enumClass, String path) {
        List<ConfigString> enumNames = w(path, k.STRING);
        List<T> enumList = new ArrayList<>();
        Iterator<ConfigString> it = enumNames.iterator();
        while (it.hasNext()) {
            enumList.add(u(path, enumClass, (m) it.next()));
        }
        return enumList;
    }

    private <T extends Enum<T>> T u(String path, Class<T> enumClass, j enumConfigValue) {
        String enumName = (String) enumConfigValue.unwrapped();
        try {
            return Enum.valueOf(enumClass, enumName);
        } catch (IllegalArgumentException e) {
            List<String> enumNames = new ArrayList<>();
            Enum[] enumConstants = (Enum[]) enumClass.getEnumConstants();
            if (enumConstants != null) {
                for (Enum enumConstant : enumConstants) {
                    enumNames.add(enumConstant.name());
                }
            }
            throw new ConfigException.BadValue(enumConfigValue.origin(), path, String.format("The enum class %s has no constant of the name '%s' (should be one of %s.)", new Object[]{enumClass.getSimpleName(), enumName, enumNames}));
        }
    }

    private <T extends j> List<T> w(String path, k expected) {
        List<T> l = new ArrayList<>();
        Iterator<? extends ConfigValue> it = getList(path).iterator();
        while (it.hasNext()) {
            AbstractConfigValue v = (AbstractConfigValue) ((j) it.next());
            if (expected != null) {
                v = o.a(v, expected);
            }
            if (v.valueType() == expected) {
                l.add(v);
            } else {
                throw new ConfigException.WrongType(v.origin(), path, "list of " + expected.name(), "list of " + v.valueType().name());
            }
        }
        return l;
    }

    public List<e> getObjectList(String path) {
        return w(path, k.OBJECT);
    }

    public List<? extends com.typesafe.config.a> getConfigList(String path) {
        List<e> objectList = getObjectList(path);
        List<Config> l = new ArrayList<>();
        for (e o : objectList) {
            l.add(o.toConfig());
        }
        return l;
    }

    public List<? extends Object> getAnyRefList(String path) {
        List<Object> l = new ArrayList<>();
        Iterator<? extends ConfigValue> it = getList(path).iterator();
        while (it.hasNext()) {
            l.add(((j) it.next()).unwrapped());
        }
        return l;
    }

    public List<Long> getBytesList(String path) {
        List<Long> l = new ArrayList<>();
        Iterator<? extends ConfigValue> it = getList(path).iterator();
        while (it.hasNext()) {
            j v = (j) it.next();
            if (v.valueType() == k.NUMBER) {
                l.add(Long.valueOf(((Number) v.unwrapped()).longValue()));
            } else if (v.valueType() == k.STRING) {
                l.add(Long.valueOf(parseBytes((String) v.unwrapped(), v.origin(), path)));
            } else {
                throw new ConfigException.WrongType(v.origin(), path, "memory size string or number of bytes", v.valueType().name());
            }
        }
        return l;
    }

    public List<c> getMemorySizeList(String path) {
        List<Long> list = getBytesList(path);
        List<ConfigMemorySize> builder = new ArrayList<>();
        for (Long v : list) {
            builder.add(c.a(v.longValue()));
        }
        return builder;
    }

    public List<Long> getDurationList(String path, TimeUnit unit) {
        List<Long> l = new ArrayList<>();
        Iterator<? extends ConfigValue> it = getList(path).iterator();
        while (it.hasNext()) {
            j v = (j) it.next();
            if (v.valueType() == k.NUMBER) {
                l.add(Long.valueOf(unit.convert(((Number) v.unwrapped()).longValue(), TimeUnit.MILLISECONDS)));
            } else if (v.valueType() == k.STRING) {
                l.add(Long.valueOf(unit.convert(parseDuration((String) v.unwrapped(), v.origin(), path), TimeUnit.NANOSECONDS)));
            } else {
                throw new ConfigException.WrongType(v.origin(), path, "duration string or number of milliseconds", v.valueType().name());
            }
        }
        return l;
    }

    public List<Duration> getDurationList(String path) {
        List<Long> l = getDurationList(path, TimeUnit.NANOSECONDS);
        List<Duration> builder = new ArrayList<>(l.size());
        for (Long value : l) {
            builder.add(Duration.ofNanos(value.longValue()));
        }
        return builder;
    }

    @Deprecated
    public List<Long> getMillisecondsList(String path) {
        return getDurationList(path, TimeUnit.MILLISECONDS);
    }

    @Deprecated
    public List<Long> getNanosecondsList(String path) {
        return getDurationList(path, TimeUnit.NANOSECONDS);
    }

    public a toFallbackValue() {
        return this.object;
    }

    public c0 withFallback(d other) {
        return this.object.withFallback(other).toConfig();
    }

    public final boolean equals(Object other) {
        if (other instanceof c0) {
            return this.object.equals(((c0) other).object);
        }
        return false;
    }

    public final int hashCode() {
        return this.object.hashCode() * 41;
    }

    public String toString() {
        return "Config(" + this.object.toString() + ")";
    }

    private static String x(String s) {
        int i = s.length() - 1;
        while (i >= 0 && Character.isLetter(s.charAt(i))) {
            i--;
        }
        return s.substring(i + 1);
    }

    public static long parseDuration(String input, f originForException, String pathForException) {
        TimeUnit units;
        String s = g.i(input);
        String originalUnitString = x(s);
        String unitString = originalUnitString;
        String numberString = g.i(s.substring(0, s.length() - unitString.length()));
        if (numberString.length() != 0) {
            if (unitString.length() > 2 && !unitString.endsWith("s")) {
                unitString = unitString + "s";
            }
            if (unitString.equals("") || unitString.equals("ms") || unitString.equals("millis") || unitString.equals("milliseconds")) {
                units = TimeUnit.MILLISECONDS;
            } else if (unitString.equals("us") || unitString.equals("micros") || unitString.equals("microseconds")) {
                units = TimeUnit.MICROSECONDS;
            } else if (unitString.equals("ns") || unitString.equals("nanos") || unitString.equals("nanoseconds")) {
                units = TimeUnit.NANOSECONDS;
            } else if (unitString.equals("d") || unitString.equals("days")) {
                units = TimeUnit.DAYS;
            } else if (unitString.equals("h") || unitString.equals("hours")) {
                units = TimeUnit.HOURS;
            } else if (unitString.equals("s") || unitString.equals("seconds")) {
                units = TimeUnit.SECONDS;
            } else if (unitString.equals("m") || unitString.equals("minutes")) {
                units = TimeUnit.MINUTES;
            } else {
                throw new ConfigException.BadValue(originForException, pathForException, "Could not parse time unit '" + originalUnitString + "' (try ns, us, ms, s, m, h, d)");
            }
            try {
                if (numberString.matches("[+-]?[0-9]+")) {
                    return units.toNanos(Long.parseLong(numberString));
                }
                return (long) (Double.parseDouble(numberString) * ((double) units.toNanos(1)));
            } catch (NumberFormatException e) {
                throw new ConfigException.BadValue(originForException, pathForException, "Could not parse duration number '" + numberString + "'");
            }
        } else {
            throw new ConfigException.BadValue(originForException, pathForException, "No number in duration value '" + input + "'");
        }
    }

    /* compiled from: SimpleConfig */
    public enum a {
        BYTES("", 1024, 0),
        KILOBYTES("kilo", 1000, 1),
        MEGABYTES("mega", 1000, 2),
        GIGABYTES("giga", 1000, 3),
        TERABYTES("tera", 1000, 4),
        PETABYTES("peta", 1000, 5),
        EXABYTES("exa", 1000, 6),
        ZETTABYTES("zetta", 1000, 7),
        YOTTABYTES("yotta", 1000, 8),
        KIBIBYTES("kibi", 1024, 1),
        MEBIBYTES("mebi", 1024, 2),
        GIBIBYTES("gibi", 1024, 3),
        TEBIBYTES("tebi", 1024, 4),
        PEBIBYTES("pebi", 1024, 5),
        EXBIBYTES("exbi", 1024, 6),
        ZEBIBYTES("zebi", 1024, 7),
        YOBIBYTES("yobi", 1024, 8);
        
        private static Map<String, a> c;
        final BigInteger bytes;
        final int power;
        final int powerOf;
        final String prefix;

        static {
            c = a();
        }

        private a(String prefix2, int powerOf2, int power2) {
            this.prefix = prefix2;
            this.powerOf = powerOf2;
            this.power = power2;
            this.bytes = BigInteger.valueOf((long) powerOf2).pow(power2);
        }

        private static Map<String, a> a() {
            Map<String, SimpleConfig.MemoryUnit> map = new HashMap<>();
            for (a unit : values()) {
                map.put(unit.prefix + Constants.BYTE, unit);
                map.put(unit.prefix + "bytes", unit);
                if (unit.prefix.length() == 0) {
                    map.put("b", unit);
                    map.put("B", unit);
                    map.put("", unit);
                } else {
                    String first = unit.prefix.substring(0, 1);
                    String firstUpper = first.toUpperCase();
                    int i = unit.powerOf;
                    if (i == 1024) {
                        map.put(first, unit);
                        map.put(firstUpper, unit);
                        map.put(firstUpper + "i", unit);
                        map.put(firstUpper + "iB", unit);
                    } else if (i != 1000) {
                        throw new RuntimeException("broken MemoryUnit enum");
                    } else if (unit.power == 1) {
                        map.put(first + "B", unit);
                    } else {
                        map.put(firstUpper + "B", unit);
                    }
                }
            }
            return map;
        }

        static a parseUnit(String unit) {
            return c.get(unit);
        }
    }

    public static long parseBytes(String input, f originForException, String pathForException) {
        BigInteger result;
        String s = g.i(input);
        String unitString = x(s);
        String numberString = g.i(s.substring(0, s.length() - unitString.length()));
        if (numberString.length() != 0) {
            a units = a.parseUnit(unitString);
            if (units != null) {
                try {
                    if (numberString.matches("[0-9]+")) {
                        result = units.bytes.multiply(new BigInteger(numberString));
                    } else {
                        result = new BigDecimal(units.bytes).multiply(new BigDecimal(numberString)).toBigInteger();
                    }
                    if (result.bitLength() < 64) {
                        return result.longValue();
                    }
                    throw new ConfigException.BadValue(originForException, pathForException, "size-in-bytes value is out of range for a 64-bit long: '" + input + "'");
                } catch (NumberFormatException e) {
                    throw new ConfigException.BadValue(originForException, pathForException, "Could not parse size-in-bytes number '" + numberString + "'");
                }
            } else {
                throw new ConfigException.BadValue(originForException, pathForException, "Could not parse size-in-bytes unit '" + unitString + "' (try k, K, kB, KiB, kilobytes, kibibytes)");
            }
        } else {
            throw new ConfigException.BadValue(originForException, pathForException, "No number in size-in-bytes value '" + input + "'");
        }
    }

    private AbstractConfigValue B(s path) {
        return root().peekPath(path);
    }

    private static void d(List<ConfigException.a> accumulator, s path, f origin, String problem) {
        accumulator.add(new ConfigException.a(path.k(), origin, problem));
    }

    private static String t(k type) {
        return type.name().toLowerCase();
    }

    private static String s(j refValue) {
        if (!(refValue instanceof a)) {
            return t(refValue.valueType());
        }
        a obj = (a) refValue;
        if (obj.isEmpty()) {
            return t(refValue.valueType());
        }
        return "object with keys " + obj.keySet();
    }

    private static void c(List<ConfigException.a> accumulator, String refDesc, s path, f origin) {
        d(accumulator, path, origin, "No setting at '" + path.k() + "', expecting: " + refDesc);
    }

    private static void a(List<ConfigException.a> accumulator, j refValue, s path, f origin) {
        c(accumulator, s(refValue), path, origin);
    }

    static void addMissing(List<ConfigException.a> accumulator, k refType, s path, f origin) {
        c(accumulator, t(refType), path, origin);
    }

    private static void g(List<ConfigException.a> accumulator, String refDesc, AbstractConfigValue actual, s path) {
        f0 origin = actual.origin();
        d(accumulator, path, origin, "Wrong value type at '" + path.k() + "', expecting: " + refDesc + " but got: " + s(actual));
    }

    private static void e(List<ConfigException.a> accumulator, j refValue, AbstractConfigValue actual, s path) {
        g(accumulator, s(refValue), actual, path);
    }

    private static void f(List<ConfigException.a> accumulator, k refType, AbstractConfigValue actual, s path) {
        g(accumulator, t(refType), actual, path);
    }

    private static boolean k(AbstractConfigValue v) {
        k kVar = k.NULL;
        return o.a(v, kVar).valueType() == kVar;
    }

    private static boolean z(j reference, AbstractConfigValue value) {
        if (k((AbstractConfigValue) reference)) {
            return true;
        }
        return A(reference.valueType(), value);
    }

    private static boolean A(k referenceType, AbstractConfigValue value) {
        if (referenceType == k.NULL || k(value)) {
            return true;
        }
        if (referenceType == k.OBJECT) {
            if (value instanceof a) {
                return true;
            }
            return false;
        } else if (referenceType == k.LIST) {
            if ((value instanceof d0) || (value instanceof e0)) {
                return true;
            }
            return false;
        } else if (referenceType == k.STRING || (value instanceof m) || referenceType == value.valueType()) {
            return true;
        } else {
            return false;
        }
    }

    private static void j(s path, a reference, a value, List<ConfigException.a> accumulator) {
        s childPath;
        for (Map.Entry<String, ConfigValue> entry : reference.entrySet()) {
            String key = entry.getKey();
            if (path != null) {
                childPath = s.f(key).i(path);
            } else {
                childPath = s.f(key);
            }
            AbstractConfigValue v = value.get((Object) key);
            if (v == null) {
                a(accumulator, (j) entry.getValue(), childPath, value.origin());
            } else {
                i(childPath, (j) entry.getValue(), v, accumulator);
            }
        }
    }

    private static void h(s path, d0 listRef, d0 listValue, List<ConfigException.a> accumulator) {
        if (!listRef.isEmpty() && !listValue.isEmpty()) {
            AbstractConfigValue refElement = listRef.get(0);
            Iterator<j> it = listValue.iterator();
            while (it.hasNext()) {
                AbstractConfigValue e = (AbstractConfigValue) it.next();
                if (!z(refElement, e)) {
                    f0 origin = e.origin();
                    d(accumulator, path, origin, "List at '" + path.k() + "' contains wrong value type, expecting list of " + s(refElement) + " but got element of type " + s(e));
                    return;
                }
            }
        }
    }

    static void checkValid(s path, k referenceType, AbstractConfigValue value, List<ConfigException.a> accumulator) {
        if (A(referenceType, value)) {
            k kVar = k.LIST;
            if (referenceType == kVar && (value instanceof e0) && !(o.a(value, kVar) instanceof d0)) {
                f(accumulator, referenceType, value, path);
                return;
            }
            return;
        }
        f(accumulator, referenceType, value, path);
    }

    private static void i(s path, j reference, AbstractConfigValue value, List<ConfigException.a> accumulator) {
        if (!z(reference, value)) {
            e(accumulator, reference, value, path);
        } else if ((reference instanceof a) && (value instanceof a)) {
            j(path, (a) reference, (a) value, accumulator);
        } else if ((reference instanceof d0) && (value instanceof d0)) {
            h(path, (d0) reference, (d0) value, accumulator);
        } else if ((reference instanceof d0) && (value instanceof e0)) {
            d0 listRef = (d0) reference;
            AbstractConfigValue listValue = o.a(value, k.LIST);
            if (listValue instanceof d0) {
                h(path, listRef, (d0) listValue, accumulator);
            } else {
                e(accumulator, reference, value, path);
            }
        }
    }

    public boolean isResolved() {
        return root().resolveStatus() == a0.RESOLVED;
    }

    public void checkValid(com.typesafe.config.a reference, String... restrictToPaths) {
        c0 ref = (c0) reference;
        a0 resolveStatus = ref.root().resolveStatus();
        a0 a0Var = a0.RESOLVED;
        if (resolveStatus != a0Var) {
            throw new ConfigException.BugOrBroken("do not call checkValid() with an unresolved reference config, call Config#resolve(), see Config#resolve() API docs");
        } else if (root().resolveStatus() == a0Var) {
            List<ConfigException.ValidationProblem> problems = new ArrayList<>();
            if (restrictToPaths.length == 0) {
                j((s) null, ref.root(), root(), problems);
            } else {
                for (String p : restrictToPaths) {
                    s path = s.g(p);
                    AbstractConfigValue refValue = ref.B(path);
                    if (refValue != null) {
                        AbstractConfigValue child = B(path);
                        if (child != null) {
                            i(path, refValue, child, problems);
                        } else {
                            a(problems, refValue, path, origin());
                        }
                    }
                }
            }
            if (!problems.isEmpty()) {
                throw new ConfigException.ValidationFailed(problems);
            }
        } else {
            throw new ConfigException.NotResolved("need to Config#resolve() each config before using it, see the API docs for Config#resolve()");
        }
    }

    public c0 withOnlyPath(String pathExpression) {
        return new c0(root().withOnlyPath(s.g(pathExpression)));
    }

    public c0 withoutPath(String pathExpression) {
        return new c0(root().withoutPath(s.g(pathExpression)));
    }

    public c0 withValue(String pathExpression, j v) {
        return new c0(root().withValue(s.g(pathExpression), v));
    }

    /* access modifiers changed from: package-private */
    public c0 atKey(f origin, String key) {
        return root().atKey(origin, key);
    }

    public c0 atKey(String key) {
        return root().atKey(key);
    }

    public com.typesafe.config.a atPath(String path) {
        return root().atPath(path);
    }

    private Object writeReplace() {
        return new b0((com.typesafe.config.a) this);
    }
}
