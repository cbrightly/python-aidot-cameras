package com.squareup.moshi;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.f;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/* compiled from: Moshi */
public final class r {
    static final List<f.d> a;
    private final List<f.d> b;
    private final int c;
    /* access modifiers changed from: private */
    public final ThreadLocal<d> d = new ThreadLocal<>();
    /* access modifiers changed from: private */
    public final Map<Object, f<?>> e = new LinkedHashMap();

    static {
        ArrayList arrayList = new ArrayList(5);
        a = arrayList;
        arrayList.add(s.a);
        arrayList.add(d.a);
        arrayList.add(q.a);
        arrayList.add(a.a);
        arrayList.add(c.a);
    }

    r(b builder) {
        int size = builder.a.size();
        List<f.d> list = a;
        List<JsonAdapter.Factory> factories = new ArrayList<>(size + list.size());
        factories.addAll(builder.a);
        factories.addAll(list);
        this.b = Collections.unmodifiableList(factories);
        this.c = builder.b;
    }

    @CheckReturnValue
    public <T> f<T> d(Type type) {
        return e(type, com.squareup.moshi.internal.b.a);
    }

    @CheckReturnValue
    public <T> f<T> c(Class<T> type) {
        return e(type, com.squareup.moshi.internal.b.a);
    }

    @CheckReturnValue
    public <T> f<T> e(Type type, Set<? extends Annotation> annotations) {
        return f(type, annotations, (String) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0020, code lost:
        r8 = r7.d.get();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0028, code lost:
        if (r8 != null) goto L_0x0035;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002a, code lost:
        r8 = new com.squareup.moshi.r.d(r7);
        r7.d.set(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0035, code lost:
        r3 = r8.d(r0, r10, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003a, code lost:
        if (r3 == null) goto L_0x0040;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003c, code lost:
        r8.c(false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003f, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0040, code lost:
        r4 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r5 = r7.b.size();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0047, code lost:
        if (r4 >= r5) goto L_0x0063;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0049, code lost:
        r6 = r7.b.get(r4).a(r0, r9, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0055, code lost:
        if (r6 != null) goto L_0x005a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0057, code lost:
        r4 = r4 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005a, code lost:
        r8.a(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005d, code lost:
        r8.c(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0062, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x007d, code lost:
        throw new java.lang.IllegalArgumentException("No JsonAdapter for " + com.squareup.moshi.internal.b.r(r0, r9));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x007e, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0080, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0085, code lost:
        throw r8.b(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0086, code lost:
        r8.c(false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0089, code lost:
        throw r4;
     */
    @javax.annotation.CheckReturnValue
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> com.squareup.moshi.f<T> f(java.lang.reflect.Type r8, java.util.Set<? extends java.lang.annotation.Annotation> r9, @javax.annotation.Nullable java.lang.String r10) {
        /*
            r7 = this;
            if (r8 == 0) goto L_0x0095
            if (r9 == 0) goto L_0x008d
            java.lang.reflect.Type r0 = com.squareup.moshi.internal.b.a(r8)
            java.lang.reflect.Type r0 = com.squareup.moshi.internal.b.m(r0)
            java.lang.Object r1 = r7.g(r0, r9)
            java.util.Map<java.lang.Object, com.squareup.moshi.f<?>> r2 = r7.e
            monitor-enter(r2)
            java.util.Map<java.lang.Object, com.squareup.moshi.f<?>> r8 = r7.e     // Catch:{ all -> 0x008a }
            java.lang.Object r8 = r8.get(r1)     // Catch:{ all -> 0x008a }
            com.squareup.moshi.f r8 = (com.squareup.moshi.f) r8     // Catch:{ all -> 0x008a }
            if (r8 == 0) goto L_0x001f
            monitor-exit(r2)     // Catch:{ all -> 0x008a }
            return r8
        L_0x001f:
            monitor-exit(r2)     // Catch:{ all -> 0x008a }
            java.lang.ThreadLocal<com.squareup.moshi.r$d> r8 = r7.d
            java.lang.Object r8 = r8.get()
            com.squareup.moshi.r$d r8 = (com.squareup.moshi.r.d) r8
            if (r8 != 0) goto L_0x0035
            com.squareup.moshi.r$d r2 = new com.squareup.moshi.r$d
            r2.<init>()
            r8 = r2
            java.lang.ThreadLocal<com.squareup.moshi.r$d> r2 = r7.d
            r2.set(r8)
        L_0x0035:
            r2 = 0
            com.squareup.moshi.f r3 = r8.d(r0, r10, r1)
            if (r3 == 0) goto L_0x0040
            r8.c(r2)
            return r3
        L_0x0040:
            r4 = 0
            java.util.List<com.squareup.moshi.f$d> r5 = r7.b     // Catch:{ IllegalArgumentException -> 0x0080 }
            int r5 = r5.size()     // Catch:{ IllegalArgumentException -> 0x0080 }
        L_0x0047:
            if (r4 >= r5) goto L_0x0063
            java.util.List<com.squareup.moshi.f$d> r6 = r7.b     // Catch:{ IllegalArgumentException -> 0x0080 }
            java.lang.Object r6 = r6.get(r4)     // Catch:{ IllegalArgumentException -> 0x0080 }
            com.squareup.moshi.f$d r6 = (com.squareup.moshi.f.d) r6     // Catch:{ IllegalArgumentException -> 0x0080 }
            com.squareup.moshi.f r6 = r6.a(r0, r9, r7)     // Catch:{ IllegalArgumentException -> 0x0080 }
            if (r6 != 0) goto L_0x005a
            int r4 = r4 + 1
            goto L_0x0047
        L_0x005a:
            r8.a(r6)     // Catch:{ IllegalArgumentException -> 0x0080 }
            r2 = 1
            r8.c(r2)
            return r6
        L_0x0063:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException     // Catch:{ IllegalArgumentException -> 0x0080 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x0080 }
            r5.<init>()     // Catch:{ IllegalArgumentException -> 0x0080 }
            java.lang.String r6 = "No JsonAdapter for "
            r5.append(r6)     // Catch:{ IllegalArgumentException -> 0x0080 }
            java.lang.String r6 = com.squareup.moshi.internal.b.r(r0, r9)     // Catch:{ IllegalArgumentException -> 0x0080 }
            r5.append(r6)     // Catch:{ IllegalArgumentException -> 0x0080 }
            java.lang.String r5 = r5.toString()     // Catch:{ IllegalArgumentException -> 0x0080 }
            r4.<init>(r5)     // Catch:{ IllegalArgumentException -> 0x0080 }
            throw r4     // Catch:{ IllegalArgumentException -> 0x0080 }
        L_0x007e:
            r4 = move-exception
            goto L_0x0086
        L_0x0080:
            r4 = move-exception
            java.lang.IllegalArgumentException r5 = r8.b(r4)     // Catch:{ all -> 0x007e }
            throw r5     // Catch:{ all -> 0x007e }
        L_0x0086:
            r8.c(r2)
            throw r4
        L_0x008a:
            r8 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x008a }
            throw r8
        L_0x008d:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "annotations == null"
            r0.<init>(r1)
            throw r0
        L_0x0095:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "type == null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.moshi.r.f(java.lang.reflect.Type, java.util.Set, java.lang.String):com.squareup.moshi.f");
    }

    private Object g(Type type, Set<? extends Annotation> annotations) {
        if (annotations.isEmpty()) {
            return type;
        }
        return Arrays.asList(new Object[]{type, annotations});
    }

    /* compiled from: Moshi */
    public static final class b {
        final List<f.d> a = new ArrayList();
        int b = 0;

        public <T> b b(Type type, f<T> jsonAdapter) {
            return a(r.h(type, jsonAdapter));
        }

        public b a(f.d factory) {
            if (factory != null) {
                List<f.d> list = this.a;
                int i = this.b;
                this.b = i + 1;
                list.add(i, factory);
                return this;
            }
            throw new IllegalArgumentException("factory == null");
        }

        @CheckReturnValue
        public r c() {
            return new r(this);
        }
    }

    static <T> f.d h(Type type, f<T> jsonAdapter) {
        if (type == null) {
            throw new IllegalArgumentException("type == null");
        } else if (jsonAdapter != null) {
            return new a(type, jsonAdapter);
        } else {
            throw new IllegalArgumentException("jsonAdapter == null");
        }
    }

    /* compiled from: Moshi */
    public class a implements f.d {
        final /* synthetic */ Type a;
        final /* synthetic */ f b;

        a(Type type, f fVar) {
            this.a = type;
            this.b = fVar;
        }

        @Nullable
        public f<?> a(Type targetType, Set<? extends Annotation> annotations, r moshi) {
            if (!annotations.isEmpty() || !com.squareup.moshi.internal.b.t(this.a, targetType)) {
                return null;
            }
            return this.b;
        }
    }

    /* compiled from: Moshi */
    public final class d {
        final List<c<?>> a = new ArrayList();
        final Deque<c<?>> b = new ArrayDeque();
        boolean c;

        d() {
        }

        /* access modifiers changed from: package-private */
        public <T> f<T> d(Type type, @Nullable String fieldName, Object cacheKey) {
            int size = this.a.size();
            for (int i = 0; i < size; i++) {
                Moshi.Lookup<T> lookup = (c) this.a.get(i);
                if (lookup.c.equals(cacheKey)) {
                    Moshi.Lookup<T> hit = lookup;
                    this.b.add(hit);
                    f<T> fVar = hit.d;
                    return fVar != null ? fVar : hit;
                }
            }
            Moshi.Lookup<Object> lookup2 = new c<>(type, fieldName, cacheKey);
            this.a.add(lookup2);
            this.b.add(lookup2);
            return null;
        }

        /* access modifiers changed from: package-private */
        public <T> void a(f<T> result) {
            ((c) this.b.getLast()).d = result;
        }

        /* access modifiers changed from: package-private */
        public void c(boolean success) {
            this.b.removeLast();
            if (this.b.isEmpty()) {
                r.this.d.remove();
                if (success) {
                    synchronized (r.this.e) {
                        int size = this.a.size();
                        for (int i = 0; i < size; i++) {
                            Moshi.Lookup<?> lookup = (c) this.a.get(i);
                            JsonAdapter<?> replaced = (f) r.this.e.put(lookup.c, lookup.d);
                            if (replaced != null) {
                                lookup.d = replaced;
                                r.this.e.put(lookup.c, replaced);
                            }
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public IllegalArgumentException b(IllegalArgumentException e) {
            if (this.c) {
                return e;
            }
            this.c = true;
            if (this.b.size() == 1 && this.b.getFirst().b == null) {
                return e;
            }
            StringBuilder errorMessageBuilder = new StringBuilder(e.getMessage());
            Iterator<c<?>> descendingIterator = this.b.descendingIterator();
            while (descendingIterator.hasNext()) {
                Moshi.Lookup<?> lookup = (c) descendingIterator.next();
                errorMessageBuilder.append("\nfor ");
                errorMessageBuilder.append(lookup.a);
                if (lookup.b != null) {
                    errorMessageBuilder.append(' ');
                    errorMessageBuilder.append(lookup.b);
                }
            }
            return new IllegalArgumentException(errorMessageBuilder.toString(), e);
        }
    }

    /* compiled from: Moshi */
    public static final class c<T> extends f<T> {
        final Type a;
        @Nullable
        final String b;
        final Object c;
        @Nullable
        f<T> d;

        c(Type type, @Nullable String fieldName, Object cacheKey) {
            this.a = type;
            this.b = fieldName;
            this.c = cacheKey;
        }

        public T b(i reader) {
            f<T> fVar = this.d;
            if (fVar != null) {
                return fVar.b(reader);
            }
            throw new IllegalStateException("JsonAdapter isn't ready");
        }

        public void i(o writer, T value) {
            f<T> fVar = this.d;
            if (fVar != null) {
                fVar.i(writer, value);
                return;
            }
            throw new IllegalStateException("JsonAdapter isn't ready");
        }

        public String toString() {
            f<T> fVar = this.d;
            return fVar != null ? fVar.toString() : super.toString();
        }
    }
}
