package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MemberScope.kt */
public final class d {
    /* access modifiers changed from: private */
    public static int a = 1;
    /* access modifiers changed from: private */
    public static final int b;
    /* access modifiers changed from: private */
    public static final int c;
    /* access modifiers changed from: private */
    public static final int d;
    /* access modifiers changed from: private */
    public static final int e;
    /* access modifiers changed from: private */
    public static final int f;
    /* access modifiers changed from: private */
    public static final int g;
    /* access modifiers changed from: private */
    public static final int h;
    /* access modifiers changed from: private */
    public static final int i;
    private static final int j;
    private static final int k;
    @NotNull
    public static final d l;
    @NotNull
    public static final d m;
    @NotNull
    public static final d n;
    @NotNull
    public static final d o;
    @NotNull
    public static final d p;
    @NotNull
    public static final d q;
    @NotNull
    public static final d r;
    @NotNull
    public static final d s;
    @NotNull
    public static final d t;
    @NotNull
    public static final d u;
    private static final List<a.C0412a> v;
    private static final List<a.C0412a> w;
    public static final a x;
    private final int y;
    @NotNull
    private final List<c> z;

    public d(int kindMask, @NotNull List<? extends c> excludes) {
        k.f(excludes, "excludes");
        this.z = excludes;
        int mask = kindMask;
        for (c it : excludes) {
            mask &= ~it.a();
        }
        this.y = mask;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ d(int i2, List list, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, (i3 & 2) != 0 ? q.g() : list);
    }

    @NotNull
    public final List<c> l() {
        return this.z;
    }

    public final int m() {
        return this.y;
    }

    public final boolean a(int kinds) {
        return (this.y & kinds) != 0;
    }

    @Nullable
    public final d n(int kinds) {
        int mask = this.y & kinds;
        if (mask == 0) {
            return null;
        }
        return new d(mask, this.z);
    }

    @NotNull
    public String toString() {
        T t2;
        String kindString;
        boolean z2;
        Iterator<T> it = v.iterator();
        while (true) {
            if (!it.hasNext()) {
                t2 = null;
                break;
            }
            t2 = it.next();
            if (((a.C0412a) t2).a() == this.y) {
                z2 = true;
                continue;
            } else {
                z2 = false;
                continue;
            }
            if (z2) {
                break;
            }
        }
        a.C0412a aVar = (a.C0412a) t2;
        String predefinedFilterName = aVar != null ? aVar.b() : null;
        if (predefinedFilterName != null) {
            kindString = predefinedFilterName;
        } else {
            Iterable<a.C0412a> $this$mapNotNullTo$iv$iv = w;
            Collection destination$iv$iv = new ArrayList();
            for (a.C0412a it2 : $this$mapNotNullTo$iv$iv) {
                Object it$iv$iv = a(it2.a()) ? it2.b() : null;
                if (it$iv$iv != null) {
                    destination$iv$iv.add(it$iv$iv);
                }
            }
            kindString = y.b0(destination$iv$iv, " | ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (l) null, 62, (Object) null);
        }
        return "DescriptorKindFilter(" + kindString + ", " + this.z + ')';
    }

    /* compiled from: MemberScope.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* access modifiers changed from: private */
        public final int j() {
            int f = d.a;
            int i = f;
            d.a = d.a << 1;
            return f;
        }

        public final int e() {
            return d.b;
        }

        public final int g() {
            return d.c;
        }

        public final int h() {
            return d.d;
        }

        public final int f() {
            return d.e;
        }

        public final int d() {
            return d.f;
        }

        public final int i() {
            return d.g;
        }

        public final int b() {
            return d.h;
        }

        public final int c() {
            return d.i;
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.resolve.scopes.d$a$a  reason: collision with other inner class name */
        /* compiled from: MemberScope.kt */
        public static final class C0412a {
            private final int a;
            @NotNull
            private final String b;

            public C0412a(int mask, @NotNull String name) {
                k.f(name, "name");
                this.a = mask;
                this.b = name;
            }

            public final int a() {
                return this.a;
            }

            @NotNull
            public final String b() {
                return this.b;
            }
        }
    }

    static {
        int $i$f$mapNotNull;
        Object it$iv$iv;
        int $i$f$mapNotNull2;
        Object it$iv$iv2;
        Class<d> cls = d.class;
        Object obj = null;
        a aVar = new a((DefaultConstructorMarker) null);
        x = aVar;
        int a2 = aVar.j();
        b = a2;
        int a3 = aVar.j();
        c = a3;
        int a4 = aVar.j();
        d = a4;
        int a5 = aVar.j();
        e = a5;
        int a6 = aVar.j();
        f = a6;
        int a7 = aVar.j();
        g = a7;
        int a8 = aVar.j() - 1;
        h = a8;
        int i2 = a2 | a3 | a4;
        i = i2;
        int i3 = a3 | a6 | a7;
        j = i3;
        int i4 = a6 | a7;
        k = i4;
        l = new d(a8, (List) null, 2, (DefaultConstructorMarker) null);
        m = new d(i4, (List) null, 2, (DefaultConstructorMarker) null);
        n = new d(a2, (List) null, 2, (DefaultConstructorMarker) null);
        o = new d(a3, (List) null, 2, (DefaultConstructorMarker) null);
        p = new d(a4, (List) null, 2, (DefaultConstructorMarker) null);
        q = new d(i2, (List) null, 2, (DefaultConstructorMarker) null);
        r = new d(a5, (List) null, 2, (DefaultConstructorMarker) null);
        s = new d(a6, (List) null, 2, (DefaultConstructorMarker) null);
        t = new d(a7, (List) null, 2, (DefaultConstructorMarker) null);
        u = new d(i3, (List) null, 2, (DefaultConstructorMarker) null);
        a aVar2 = x;
        Field[] fields = cls.getFields();
        k.b(fields, "T::class.java.fields");
        ArrayList arrayList = new ArrayList();
        for (Field it$iv : fields) {
            Field it$iv2 = it$iv;
            k.b(it$iv2, "it");
            if (Modifier.isStatic(it$iv2.getModifiers())) {
                arrayList.add(it$iv);
            }
        }
        Iterable<Field> $this$mapNotNull$iv = arrayList;
        int $i$f$mapNotNull3 = false;
        Collection destination$iv$iv = new ArrayList();
        for (Field field : $this$mapNotNull$iv) {
            Iterable $this$mapNotNull$iv2 = $this$mapNotNull$iv;
            Object obj2 = field.get(obj);
            if (!(obj2 instanceof d)) {
                obj2 = null;
            }
            d filter = (d) obj2;
            if (filter != null) {
                $i$f$mapNotNull2 = $i$f$mapNotNull3;
                int $i$f$mapNotNull4 = filter.y;
                k.b(field, "field");
                String name = field.getName();
                k.b(name, "field.name");
                it$iv$iv2 = new a.C0412a($i$f$mapNotNull4, name);
            } else {
                $i$f$mapNotNull2 = $i$f$mapNotNull3;
                it$iv$iv2 = null;
            }
            if (it$iv$iv2 != null) {
                destination$iv$iv.add(it$iv$iv2);
            }
            $this$mapNotNull$iv = $this$mapNotNull$iv2;
            $i$f$mapNotNull3 = $i$f$mapNotNull2;
            obj = null;
        }
        int i5 = $i$f$mapNotNull3;
        v = y.C0(destination$iv$iv);
        a aVar3 = x;
        Field[] fields2 = cls.getFields();
        k.b(fields2, "T::class.java.fields");
        ArrayList $this$filterTo$iv$iv = new ArrayList();
        for (Field field2 : fields2) {
            Field it$iv3 = field2;
            k.b(it$iv3, "it");
            if (Modifier.isStatic(it$iv3.getModifiers())) {
                $this$filterTo$iv$iv.add(field2);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Field it = (Field) element$iv$iv;
            k.b(it, "it");
            if (k.a(it.getType(), Integer.TYPE)) {
                arrayList2.add(element$iv$iv);
            }
        }
        Iterable<Field> $this$mapNotNull$iv3 = arrayList2;
        int $i$f$mapNotNull5 = false;
        Collection destination$iv$iv2 = new ArrayList();
        for (Field field3 : $this$mapNotNull$iv3) {
            Object obj3 = field3.get((Object) null);
            if (obj3 != null) {
                int mask = ((Integer) obj3).intValue();
                Iterable $this$mapNotNull$iv4 = $this$mapNotNull$iv3;
                boolean isOneBitMask = mask == ((-mask) & mask);
                if (isOneBitMask) {
                    boolean z2 = isOneBitMask;
                    k.b(field3, "field");
                    $i$f$mapNotNull = $i$f$mapNotNull5;
                    String name2 = field3.getName();
                    k.b(name2, "field.name");
                    it$iv$iv = new a.C0412a(mask, name2);
                } else {
                    $i$f$mapNotNull = $i$f$mapNotNull5;
                    it$iv$iv = null;
                }
                if (it$iv$iv != null) {
                    destination$iv$iv2.add(it$iv$iv);
                }
                $this$mapNotNull$iv3 = $this$mapNotNull$iv4;
                $i$f$mapNotNull5 = $i$f$mapNotNull;
            } else {
                int i6 = $i$f$mapNotNull5;
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
            }
        }
        w = y.C0(destination$iv$iv2);
    }
}
