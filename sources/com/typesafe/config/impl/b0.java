package com.typesafe.config.impl;

import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.e;
import com.typesafe.config.f;
import com.typesafe.config.impl.SerializedConfigValue;
import com.typesafe.config.impl.m;
import com.typesafe.config.j;
import com.typesafe.config.k;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: SerializedConfigValue */
public class b0 extends AbstractConfigValue implements Externalizable {
    private static final long serialVersionUID = 1;
    private j value;
    private boolean wasConfig;

    /* compiled from: SerializedConfigValue */
    public enum c {
        UNKNOWN,
        END_MARKER,
        ROOT_VALUE,
        ROOT_WAS_CONFIG,
        VALUE_DATA,
        VALUE_ORIGIN,
        ORIGIN_DESCRIPTION,
        ORIGIN_LINE_NUMBER,
        ORIGIN_END_LINE_NUMBER,
        ORIGIN_TYPE,
        ORIGIN_URL,
        ORIGIN_COMMENTS,
        ORIGIN_NULL_URL,
        ORIGIN_NULL_COMMENTS,
        ORIGIN_RESOURCE,
        ORIGIN_NULL_RESOURCE;

        static c forInt(int b) {
            if (b < values().length) {
                return values()[b];
            }
            return UNKNOWN;
        }
    }

    /* compiled from: SerializedConfigValue */
    public enum d {
        NULL(k.NULL),
        BOOLEAN(k.BOOLEAN),
        INT(r4),
        LONG(r4),
        DOUBLE(r4),
        STRING(k.STRING),
        LIST(k.LIST),
        OBJECT(k.OBJECT);
        
        k configType;

        private d(k configType2) {
            this.configType = configType2;
        }

        static d forInt(int b) {
            if (b < values().length) {
                return values()[b];
            }
            return null;
        }

        static d forValue(j value) {
            k t = value.valueType();
            if (t != k.NUMBER) {
                for (d st : values()) {
                    if (st.configType == t) {
                        return st;
                    }
                }
            } else if (value instanceof h) {
                return INT;
            } else {
                if (value instanceof i) {
                    return LONG;
                }
                if (value instanceof e) {
                    return DOUBLE;
                }
            }
            throw new ConfigException.BugOrBroken("don't know how to serialize " + value);
        }
    }

    public b0() {
        super((f) null);
    }

    b0(j value2) {
        this();
        this.value = value2;
        this.wasConfig = false;
    }

    b0(com.typesafe.config.a conf) {
        this((j) conf.root());
        this.wasConfig = true;
    }

    private Object readResolve() {
        if (this.wasConfig) {
            return ((e) this.value).toConfig();
        }
        return this.value;
    }

    /* compiled from: SerializedConfigValue */
    public static class b {
        final c a;
        final ByteArrayOutputStream b;
        final DataOutput c;

        b(c code) {
            this.a = code;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            this.b = byteArrayOutputStream;
            this.c = new DataOutputStream(byteArrayOutputStream);
        }
    }

    private static void p(DataOutput out, c code, Object v) {
        switch (a.a[code.ordinal()]) {
            case 1:
                out.writeUTF((String) v);
                return;
            case 2:
                out.writeInt(((Integer) v).intValue());
                return;
            case 3:
                out.writeInt(((Integer) v).intValue());
                return;
            case 4:
                out.writeByte(((Integer) v).intValue());
                return;
            case 5:
                out.writeUTF((String) v);
                return;
            case 6:
                out.writeUTF((String) v);
                return;
            case 7:
                List<String> list = (List) v;
                out.writeInt(list.size());
                for (String s : list) {
                    out.writeUTF(s);
                }
                return;
            case 8:
            case 9:
            case 10:
                return;
            default:
                throw new IOException("Unhandled field from origin: " + code);
        }
    }

    static void writeOrigin(DataOutput out, f0 origin, f0 baseOrigin) {
        Map<c, Object> map;
        if (origin != null) {
            map = origin.o(baseOrigin);
        } else {
            map = Collections.emptyMap();
        }
        for (Map.Entry<SerializedConfigValue.SerializedField, Object> e : map.entrySet()) {
            b field = new b((c) e.getKey());
            p(field.c, field.a, e.getValue());
            o(out, field);
        }
        n(out);
    }

    static f0 readOrigin(DataInput in, f0 baseOrigin) {
        Map<SerializedConfigValue.SerializedField, Object> m = new EnumMap<>(c.class);
        while (true) {
            Object v = null;
            c field = f(in);
            switch (a.a[field.ordinal()]) {
                case 1:
                    in.readInt();
                    v = in.readUTF();
                    break;
                case 2:
                    in.readInt();
                    v = Integer.valueOf(in.readInt());
                    break;
                case 3:
                    in.readInt();
                    v = Integer.valueOf(in.readInt());
                    break;
                case 4:
                    in.readInt();
                    v = Integer.valueOf(in.readUnsignedByte());
                    break;
                case 5:
                    in.readInt();
                    v = in.readUTF();
                    break;
                case 6:
                    in.readInt();
                    v = in.readUTF();
                    break;
                case 7:
                    in.readInt();
                    int size = in.readInt();
                    List<String> list = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        list.add(in.readUTF());
                    }
                    v = list;
                    break;
                case 8:
                case 9:
                case 10:
                    in.readInt();
                    v = "";
                    break;
                case 11:
                    return f0.f(baseOrigin, m);
                case 12:
                case 13:
                case 14:
                case 15:
                    throw new IOException("Not expecting this field here: " + field);
                case 16:
                    k(in);
                    break;
            }
            if (v != null) {
                m.put(field, v);
            }
        }
    }

    private static void r(DataOutput out, j value2) {
        d st = d.forValue(value2);
        out.writeByte(st.ordinal());
        switch (a.b[st.ordinal()]) {
            case 1:
                out.writeBoolean(((b) value2).unwrapped().booleanValue());
                return;
            case 3:
                out.writeInt(((h) value2).unwrapped().intValue());
                out.writeUTF(((k) value2).transformToString());
                return;
            case 4:
                out.writeLong(((i) value2).unwrapped().longValue());
                out.writeUTF(((k) value2).transformToString());
                return;
            case 5:
                out.writeDouble(((e) value2).unwrapped().doubleValue());
                out.writeUTF(((k) value2).transformToString());
                return;
            case 6:
                out.writeUTF(((m) value2).unwrapped());
                return;
            case 7:
                com.typesafe.config.b<j> list = (com.typesafe.config.b) value2;
                out.writeInt(list.size());
                for (j v : list) {
                    q(out, v, (f0) list.origin());
                }
                return;
            case 8:
                e obj = (e) value2;
                out.writeInt(obj.size());
                for (Map.Entry<String, ConfigValue> e : obj.entrySet()) {
                    out.writeUTF(e.getKey());
                    q(out, (j) e.getValue(), (f0) obj.origin());
                }
                return;
            default:
                return;
        }
    }

    /* compiled from: SerializedConfigValue */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[d.values().length];
            b = iArr;
            try {
                iArr[d.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                b[d.NULL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                b[d.INT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                b[d.LONG.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                b[d.DOUBLE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                b[d.STRING.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                b[d.LIST.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                b[d.OBJECT.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            int[] iArr2 = new int[c.values().length];
            a = iArr2;
            try {
                iArr2[c.ORIGIN_DESCRIPTION.ordinal()] = 1;
            } catch (NoSuchFieldError e9) {
            }
            try {
                a[c.ORIGIN_LINE_NUMBER.ordinal()] = 2;
            } catch (NoSuchFieldError e10) {
            }
            try {
                a[c.ORIGIN_END_LINE_NUMBER.ordinal()] = 3;
            } catch (NoSuchFieldError e11) {
            }
            try {
                a[c.ORIGIN_TYPE.ordinal()] = 4;
            } catch (NoSuchFieldError e12) {
            }
            try {
                a[c.ORIGIN_URL.ordinal()] = 5;
            } catch (NoSuchFieldError e13) {
            }
            try {
                a[c.ORIGIN_RESOURCE.ordinal()] = 6;
            } catch (NoSuchFieldError e14) {
            }
            try {
                a[c.ORIGIN_COMMENTS.ordinal()] = 7;
            } catch (NoSuchFieldError e15) {
            }
            try {
                a[c.ORIGIN_NULL_URL.ordinal()] = 8;
            } catch (NoSuchFieldError e16) {
            }
            try {
                a[c.ORIGIN_NULL_RESOURCE.ordinal()] = 9;
            } catch (NoSuchFieldError e17) {
            }
            try {
                a[c.ORIGIN_NULL_COMMENTS.ordinal()] = 10;
            } catch (NoSuchFieldError e18) {
            }
            try {
                a[c.END_MARKER.ordinal()] = 11;
            } catch (NoSuchFieldError e19) {
            }
            try {
                a[c.ROOT_VALUE.ordinal()] = 12;
            } catch (NoSuchFieldError e20) {
            }
            try {
                a[c.ROOT_WAS_CONFIG.ordinal()] = 13;
            } catch (NoSuchFieldError e21) {
            }
            try {
                a[c.VALUE_DATA.ordinal()] = 14;
            } catch (NoSuchFieldError e22) {
            }
            try {
                a[c.VALUE_ORIGIN.ordinal()] = 15;
            } catch (NoSuchFieldError e23) {
            }
            try {
                a[c.UNKNOWN.ordinal()] = 16;
            } catch (NoSuchFieldError e24) {
            }
        }
    }

    private static AbstractConfigValue h(DataInput in, f0 origin) {
        int stb = in.readUnsignedByte();
        d st = d.forInt(stb);
        if (st != null) {
            switch (a.b[st.ordinal()]) {
                case 1:
                    return new b(origin, in.readBoolean());
                case 2:
                    return new j(origin);
                case 3:
                    return new h(origin, in.readInt(), in.readUTF());
                case 4:
                    return new i(origin, in.readLong(), in.readUTF());
                case 5:
                    return new e(origin, in.readDouble(), in.readUTF());
                case 6:
                    return new m.a(origin, in.readUTF());
                case 7:
                    int mapSize = in.readInt();
                    List<AbstractConfigValue> list = new ArrayList<>(mapSize);
                    for (int i = 0; i < mapSize; i++) {
                        list.add(g(in, origin));
                    }
                    return new d0(origin, list);
                case 8:
                    int mapSize2 = in.readInt();
                    Map<String, AbstractConfigValue> map = new HashMap<>(mapSize2);
                    for (int i2 = 0; i2 < mapSize2; i2++) {
                        map.put(in.readUTF(), g(in, origin));
                    }
                    return new e0(origin, map);
                default:
                    throw new IOException("Unhandled serialized value type: " + st);
            }
        } else {
            throw new IOException("Unknown serialized value type: " + stb);
        }
    }

    private static void q(DataOutput out, j value2, f0 baseOrigin) {
        b origin = new b(c.VALUE_ORIGIN);
        writeOrigin(origin.c, (f0) value2.origin(), baseOrigin);
        o(out, origin);
        b data = new b(c.VALUE_DATA);
        r(data.c, value2);
        o(out, data);
        n(out);
    }

    private static AbstractConfigValue g(DataInput in, f0 baseOrigin) {
        AbstractConfigValue value2 = null;
        f0 origin = null;
        while (true) {
            c code = f(in);
            if (code == c.END_MARKER) {
                if (value2 != null) {
                    return value2;
                }
                throw new IOException("No value data found in serialization of value");
            } else if (code == c.VALUE_DATA) {
                if (origin != null) {
                    in.readInt();
                    value2 = h(in, origin);
                } else {
                    throw new IOException("Origin must be stored before value data");
                }
            } else if (code == c.VALUE_ORIGIN) {
                in.readInt();
                origin = readOrigin(in, baseOrigin);
            } else {
                k(in);
            }
        }
    }

    private static void o(DataOutput out, b field) {
        byte[] bytes = field.b.toByteArray();
        out.writeByte(field.a.ordinal());
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    private static void n(DataOutput out) {
        out.writeByte(c.END_MARKER.ordinal());
    }

    private static c f(DataInput in) {
        int c2 = in.readUnsignedByte();
        if (c2 != c.UNKNOWN.ordinal()) {
            return c.forInt(c2);
        }
        throw new IOException("field code " + c2 + " is not supposed to be on the wire");
    }

    private static void k(DataInput in) {
        int len = in.readInt();
        int skipped = in.skipBytes(len);
        if (skipped < len) {
            in.readFully(new byte[(len - skipped)]);
        }
    }

    public void writeExternal(ObjectOutput out) {
        if (((AbstractConfigValue) this.value).resolveStatus() == a0.RESOLVED) {
            b field = new b(c.ROOT_VALUE);
            q(field.c, this.value, (f0) null);
            o(out, field);
            b field2 = new b(c.ROOT_WAS_CONFIG);
            field2.c.writeBoolean(this.wasConfig);
            o(out, field2);
            n(out);
            return;
        }
        throw new NotSerializableException("tried to serialize a value with unresolved substitutions, need to Config#resolve() first, see API docs");
    }

    public void readExternal(ObjectInput in) {
        while (true) {
            c code = f(in);
            if (code != c.END_MARKER) {
                if (code == c.ROOT_VALUE) {
                    in.readInt();
                    this.value = g(in, (f0) null);
                } else if (code == c.ROOT_WAS_CONFIG) {
                    in.readInt();
                    this.wasConfig = in.readBoolean();
                } else {
                    k(in);
                }
            } else {
                return;
            }
        }
    }

    private static ConfigException i() {
        return new ConfigException.BugOrBroken(b0.class.getName() + " should not exist outside of serialization");
    }

    public k valueType() {
        throw i();
    }

    public Object unwrapped() {
        throw i();
    }

    /* access modifiers changed from: protected */
    public b0 newCopy(f origin) {
        throw i();
    }

    public final String toString() {
        return getClass().getSimpleName() + "(value=" + this.value + ",wasConfig=" + this.wasConfig + ")";
    }

    public boolean equals(Object other) {
        if (!(other instanceof b0) || !canEqual(other) || this.wasConfig != ((b0) other).wasConfig || !this.value.equals(((b0) other).value)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return ((this.wasConfig ? 1 : 0) + ((this.value.hashCode() + 41) * 41)) * 41;
    }
}
