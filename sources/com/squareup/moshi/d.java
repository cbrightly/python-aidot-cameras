package com.squareup.moshi;

import com.squareup.moshi.f;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

/* compiled from: CollectionJsonAdapter */
public abstract class d<C extends Collection<T>, T> extends f<C> {
    public static final f.d a = new a();
    private final f<T> b;

    /* access modifiers changed from: package-private */
    public abstract C m();

    /* synthetic */ d(f x0, a x1) {
        this(x0);
    }

    /* compiled from: CollectionJsonAdapter */
    public class a implements f.d {
        a() {
        }

        @Nullable
        public f<?> a(Type type, Set<? extends Annotation> annotations, r moshi) {
            Class<?> rawType = t.g(type);
            if (!annotations.isEmpty()) {
                return null;
            }
            if (rawType == List.class || rawType == Collection.class) {
                return d.l(type, moshi).f();
            }
            if (rawType == Set.class) {
                return d.n(type, moshi).f();
            }
            return null;
        }
    }

    private d(f<T> elementAdapter) {
        this.b = elementAdapter;
    }

    static <T> f<Collection<T>> l(Type type, r moshi) {
        return new b(moshi.d(t.c(type, Collection.class)));
    }

    /* compiled from: CollectionJsonAdapter */
    public class b extends d<Collection<T>, T> {
        b(f elementAdapter) {
            super(elementAdapter, (a) null);
        }

        public /* bridge */ /* synthetic */ Object b(i iVar) {
            return d.super.k(iVar);
        }

        public /* bridge */ /* synthetic */ void i(o oVar, Object obj) {
            d.super.o(oVar, (Collection) obj);
        }

        /* access modifiers changed from: package-private */
        public Collection<T> m() {
            return new ArrayList();
        }
    }

    static <T> f<Set<T>> n(Type type, r moshi) {
        return new c(moshi.d(t.c(type, Collection.class)));
    }

    /* compiled from: CollectionJsonAdapter */
    public class c extends d<Set<T>, T> {
        c(f elementAdapter) {
            super(elementAdapter, (a) null);
        }

        public /* bridge */ /* synthetic */ Object b(i iVar) {
            return d.super.k(iVar);
        }

        public /* bridge */ /* synthetic */ void i(o oVar, Object obj) {
            d.super.o(oVar, (Collection) obj);
        }

        /* access modifiers changed from: package-private */
        /* renamed from: p */
        public Set<T> m() {
            return new LinkedHashSet();
        }
    }

    public C k(i reader) {
        C result = m();
        reader.a();
        while (reader.l()) {
            result.add(this.b.b(reader));
        }
        reader.g();
        return result;
    }

    public void o(o writer, C value) {
        writer.a();
        Iterator it = value.iterator();
        while (it.hasNext()) {
            this.b.i(writer, it.next());
        }
        writer.j();
    }

    public String toString() {
        return this.b + ".collection()";
    }
}
