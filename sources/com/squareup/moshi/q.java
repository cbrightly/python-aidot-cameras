package com.squareup.moshi;

import com.squareup.moshi.f;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* compiled from: MapJsonAdapter */
public final class q<K, V> extends f<Map<K, V>> {
    public static final f.d a = new a();
    private final f<K> b;
    private final f<V> c;

    /* compiled from: MapJsonAdapter */
    public class a implements f.d {
        a() {
        }

        @Nullable
        public f<?> a(Type type, Set<? extends Annotation> annotations, r moshi) {
            Class<?> rawType;
            if (!annotations.isEmpty() || (rawType = t.g(type)) != Map.class) {
                return null;
            }
            Type[] keyAndValue = t.i(type, rawType);
            return new q(moshi, keyAndValue[0], keyAndValue[1]).f();
        }
    }

    q(r moshi, Type keyType, Type valueType) {
        this.b = moshi.d(keyType);
        this.c = moshi.d(valueType);
    }

    /* renamed from: l */
    public void i(o writer, Map<K, V> map) {
        writer.g();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getKey() != null) {
                writer.v();
                this.b.i(writer, entry.getKey());
                this.c.i(writer, entry.getValue());
            } else {
                throw new JsonDataException("Map key is null at " + writer.getPath());
            }
        }
        writer.m();
    }

    /* renamed from: k */
    public Map<K, V> b(i reader) {
        LinkedHashTreeMap<K, V> result = new p<>();
        reader.c();
        while (reader.l()) {
            reader.E();
            K name = this.b.b(reader);
            V value = this.c.b(reader);
            V replaced = result.put(name, value);
            if (replaced != null) {
                throw new JsonDataException("Map key '" + name + "' has multiple values at path " + reader.getPath() + ": " + replaced + " and " + value);
            }
        }
        reader.i();
        return result;
    }

    public String toString() {
        return "JsonAdapter(" + this.b + "=" + this.c + ")";
    }
}
