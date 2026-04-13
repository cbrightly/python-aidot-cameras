package com.squareup.moshi.adapters;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import com.squareup.moshi.t;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/* compiled from: PolymorphicJsonAdapterFactory */
public final class b<T> implements f.d {
    final Class<T> a;
    final String b;
    final List<String> c;
    final List<Type> d;
    @Nullable
    final f<Object> e;

    b(Class<T> baseType, String labelKey, List<String> labels, List<Type> subtypes, @Nullable f<Object> fallbackJsonAdapter) {
        this.a = baseType;
        this.b = labelKey;
        this.c = labels;
        this.d = subtypes;
        this.e = fallbackJsonAdapter;
    }

    @CheckReturnValue
    public static <T> b<T> b(Class<T> baseType, String labelKey) {
        if (baseType == null) {
            throw new NullPointerException("baseType == null");
        } else if (labelKey != null) {
            return new b(baseType, labelKey, Collections.emptyList(), Collections.emptyList(), (f<Object>) null);
        } else {
            throw new NullPointerException("labelKey == null");
        }
    }

    public b<T> c(Class<? extends T> subtype, String label) {
        if (subtype == null) {
            throw new NullPointerException("subtype == null");
        } else if (label == null) {
            throw new NullPointerException("label == null");
        } else if (!this.c.contains(label)) {
            List<String> newLabels = new ArrayList<>(this.c);
            newLabels.add(label);
            List<Type> newSubtypes = new ArrayList<>(this.d);
            newSubtypes.add(subtype);
            return new b(this.a, this.b, newLabels, newSubtypes, this.e);
        } else {
            throw new IllegalArgumentException("Labels must be unique.");
        }
    }

    public f<?> a(Type type, Set<? extends Annotation> annotations, r moshi) {
        if (t.g(type) != this.a || !annotations.isEmpty()) {
            return null;
        }
        List<JsonAdapter<Object>> jsonAdapters = new ArrayList<>(this.d.size());
        int size = this.d.size();
        for (int i = 0; i < size; i++) {
            jsonAdapters.add(moshi.d(this.d.get(i)));
        }
        return new a(this.b, this.c, this.d, jsonAdapters, this.e).f();
    }

    /* compiled from: PolymorphicJsonAdapterFactory */
    public static final class a extends f<Object> {
        final String a;
        final List<String> b;
        final List<Type> c;
        final List<f<Object>> d;
        @Nullable
        final f<Object> e;
        final i.a f;
        final i.a g;

        a(String labelKey, List<String> labels, List<Type> subtypes, List<f<Object>> jsonAdapters, @Nullable f<Object> fallbackJsonAdapter) {
            this.a = labelKey;
            this.b = labels;
            this.c = subtypes;
            this.d = jsonAdapters;
            this.e = fallbackJsonAdapter;
            this.f = i.a.a(labelKey);
            this.g = i.a.a((String[]) labels.toArray(new String[0]));
        }

        /* JADX INFO: finally extract failed */
        public Object b(i reader) {
            i peeked = reader.z();
            peeked.T(false);
            try {
                int labelIndex = k(peeked);
                peeked.close();
                if (labelIndex == -1) {
                    return this.e.b(reader);
                }
                return this.d.get(labelIndex).b(reader);
            } catch (Throwable th) {
                peeked.close();
                throw th;
            }
        }

        private int k(i reader) {
            reader.c();
            while (reader.l()) {
                if (reader.J(this.f) == -1) {
                    reader.o0();
                    reader.u0();
                } else {
                    int labelIndex = reader.P(this.g);
                    if (labelIndex != -1 || this.e != null) {
                        return labelIndex;
                    }
                    throw new JsonDataException("Expected one of " + this.b + " for key '" + this.a + "' but found '" + reader.u() + "'. Register a subtype for this label.");
                }
            }
            throw new JsonDataException("Missing label for " + this.a);
        }

        public void i(o writer, Object value) {
            JsonAdapter<Object> adapter;
            int labelIndex = this.c.indexOf(value.getClass());
            if (labelIndex != -1) {
                adapter = (f) this.d.get(labelIndex);
            } else if (this.e != null) {
                adapter = this.e;
            } else {
                throw new IllegalArgumentException("Expected one of " + this.c + " but found " + value + ", a " + value.getClass() + ". Register this subtype.");
            }
            writer.g();
            if (adapter != this.e) {
                writer.r(this.a).W(this.b.get(labelIndex));
            }
            int flattenToken = writer.c();
            adapter.i(writer, value);
            writer.l(flattenToken);
            writer.m();
        }

        public String toString() {
            return "PolymorphicJsonAdapter(" + this.a + ")";
        }
    }
}
