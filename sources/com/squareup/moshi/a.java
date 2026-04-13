package com.squareup.moshi;

import com.squareup.moshi.f;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

/* compiled from: ArrayJsonAdapter */
public final class a extends f<Object> {
    public static final f.d a = new C0204a();
    private final Class<?> b;
    private final f<Object> c;

    /* renamed from: com.squareup.moshi.a$a  reason: collision with other inner class name */
    /* compiled from: ArrayJsonAdapter */
    public class C0204a implements f.d {
        C0204a() {
        }

        @Nullable
        public f<?> a(Type type, Set<? extends Annotation> annotations, r moshi) {
            Type elementType = t.a(type);
            if (elementType != null && annotations.isEmpty()) {
                return new a(t.g(elementType), moshi.d(elementType)).f();
            }
            return null;
        }
    }

    a(Class<?> elementClass, f<Object> elementAdapter) {
        this.b = elementClass;
        this.c = elementAdapter;
    }

    public Object b(i reader) {
        List<Object> list = new ArrayList<>();
        reader.a();
        while (reader.l()) {
            list.add(this.c.b(reader));
        }
        reader.g();
        Object array = Array.newInstance(this.b, list.size());
        for (int i = 0; i < list.size(); i++) {
            Array.set(array, i, list.get(i));
        }
        return array;
    }

    public void i(o writer, Object value) {
        writer.a();
        int size = Array.getLength(value);
        for (int i = 0; i < size; i++) {
            this.c.i(writer, Array.get(value, i));
        }
        writer.j();
    }

    public String toString() {
        return this.c + ".array()";
    }
}
