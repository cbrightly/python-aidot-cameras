package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import kotlin.reflect.jvm.internal.impl.metadata.c;
import kotlin.reflect.jvm.internal.impl.metadata.j;
import kotlin.reflect.jvm.internal.impl.metadata.k;
import kotlin.reflect.jvm.internal.impl.metadata.x;
import kotlin.reflect.jvm.internal.impl.protobuf.i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Flags */
public class b {
    public static final C0386b A;
    public static final C0386b B;
    public static final C0386b C;
    public static final C0386b D;
    public static final C0386b E;
    public static final C0386b F;
    public static final C0386b G;
    public static final C0386b H;
    public static final C0386b I;
    public static final C0386b J;
    public static final C0386b K = d.c();
    public static final C0386b a = d.c();
    public static final C0386b b;
    public static final d<x> c;
    public static final d<k> d;
    public static final d<c.C0384c> e;
    public static final C0386b f;
    public static final C0386b g;
    public static final C0386b h;
    public static final C0386b i;
    public static final C0386b j;
    public static final C0386b k;
    public static final d<j> l;
    public static final C0386b m;
    public static final C0386b n;
    public static final C0386b o;
    public static final C0386b p;
    public static final C0386b q;
    public static final C0386b r;
    public static final C0386b s;
    public static final C0386b t;
    public static final C0386b u;
    public static final C0386b v;
    public static final C0386b w;
    public static final C0386b x;
    public static final C0386b y;
    public static final C0386b z;

    private static /* synthetic */ void a(int i2) {
        Object[] objArr = new Object[3];
        switch (i2) {
            case 1:
            case 5:
            case 8:
            case 11:
                objArr[0] = "modality";
                break;
            case 2:
                objArr[0] = "kind";
                break;
            case 6:
            case 9:
                objArr[0] = "memberKind";
                break;
            default:
                objArr[0] = "visibility";
                break;
        }
        objArr[1] = "kotlin/reflect/jvm/internal/impl/metadata/deserialization/Flags";
        switch (i2) {
            case 3:
                objArr[2] = "getConstructorFlags";
                break;
            case 4:
            case 5:
            case 6:
                objArr[2] = "getFunctionFlags";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "getPropertyFlags";
                break;
            case 10:
            case 11:
                objArr[2] = "getAccessorFlags";
                break;
            default:
                objArr[2] = "getClassFlags";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    static {
        C0386b c2 = d.c();
        b = c2;
        d<x> a2 = d.a(c2, x.values());
        c = a2;
        d<k> a3 = d.a(a2, k.values());
        d = a3;
        d<c.C0384c> a4 = d.a(a3, c.C0384c.values());
        e = a4;
        C0386b b2 = d.b(a4);
        f = b2;
        C0386b b3 = d.b(b2);
        g = b3;
        C0386b b4 = d.b(b3);
        h = b4;
        C0386b b5 = d.b(b4);
        i = b5;
        j = d.b(b5);
        k = d.b(a2);
        d<j> a5 = d.a(a3, j.values());
        l = a5;
        C0386b b6 = d.b(a5);
        m = b6;
        C0386b b7 = d.b(b6);
        n = b7;
        C0386b b8 = d.b(b7);
        o = b8;
        C0386b b9 = d.b(b8);
        p = b9;
        C0386b b10 = d.b(b9);
        q = b10;
        C0386b b11 = d.b(b10);
        r = b11;
        s = d.b(b11);
        C0386b b12 = d.b(a5);
        t = b12;
        C0386b b13 = d.b(b12);
        u = b13;
        C0386b b14 = d.b(b13);
        v = b14;
        C0386b b15 = d.b(b14);
        w = b15;
        C0386b b16 = d.b(b15);
        x = b16;
        C0386b b17 = d.b(b16);
        y = b17;
        C0386b b18 = d.b(b17);
        z = b18;
        C0386b b19 = d.b(b18);
        A = b19;
        B = d.b(b19);
        C0386b b20 = d.b(c2);
        C = b20;
        C0386b b21 = d.b(b20);
        D = b21;
        E = d.b(b21);
        C0386b b22 = d.b(a3);
        F = b22;
        C0386b b23 = d.b(b22);
        G = b23;
        H = d.b(b23);
        C0386b c3 = d.c();
        I = c3;
        J = d.b(c3);
    }

    public static int b(boolean hasAnnotations, @NotNull x visibility, @NotNull k modality, boolean isNotDefault, boolean isExternal, boolean isInlineAccessor) {
        if (visibility == null) {
            a(10);
        }
        if (modality == null) {
            a(11);
        }
        return b.e(Boolean.valueOf(hasAnnotations)) | d.e(modality) | c.e(visibility) | F.e(Boolean.valueOf(isNotDefault)) | G.e(Boolean.valueOf(isExternal)) | H.e(Boolean.valueOf(isInlineAccessor));
    }

    /* compiled from: Flags */
    public static abstract class d<E> {
        public final int a;
        public final int b;

        public abstract E d(int i);

        public abstract int e(E e);

        public static <E extends i.a> d<E> a(d<?> previousField, E[] values) {
            return new c(previousField.a + previousField.b, values);
        }

        public static C0386b c() {
            return new C0386b(0);
        }

        public static C0386b b(d<?> previousField) {
            return new C0386b(previousField.a + previousField.b);
        }

        private d(int offset, int bitWidth) {
            this.a = offset;
            this.b = bitWidth;
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.deserialization.b$b  reason: collision with other inner class name */
    /* compiled from: Flags */
    public static class C0386b extends d<Boolean> {
        private static /* synthetic */ void f(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", new Object[]{"kotlin/reflect/jvm/internal/impl/metadata/deserialization/Flags$BooleanFlagField", "get"}));
        }

        public C0386b(int offset) {
            super(offset, 1);
        }

        @NotNull
        /* renamed from: g */
        public Boolean d(int flags) {
            boolean z = true;
            if (((1 << this.a) & flags) == 0) {
                z = false;
            }
            Boolean valueOf = Boolean.valueOf(z);
            if (valueOf == null) {
                f(0);
            }
            return valueOf;
        }

        /* renamed from: h */
        public int e(Boolean value) {
            if (value.booleanValue()) {
                return 1 << this.a;
            }
            return 0;
        }
    }

    /* compiled from: Flags */
    public static class c<E extends i.a> extends d<E> {
        private final E[] c;

        private static /* synthetic */ void f(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[]{"enumEntries", "kotlin/reflect/jvm/internal/impl/metadata/deserialization/Flags$EnumLiteFlagField", "bitWidth"}));
        }

        public c(int offset, E[] values) {
            super(offset, g(values));
            this.c = values;
        }

        private static <E> int g(@NotNull E[] enumEntries) {
            if (enumEntries == null) {
                f(0);
            }
            int length = enumEntries.length - 1;
            if (length == 0) {
                return 1;
            }
            for (int i = 31; i >= 0; i--) {
                if (((1 << i) & length) != 0) {
                    return i + 1;
                }
            }
            throw new IllegalStateException("Empty enum: " + enumEntries.getClass());
        }

        @Nullable
        /* renamed from: h */
        public E d(int flags) {
            int i = this.a;
            int value = (flags & (((1 << this.b) - 1) << i)) >> i;
            for (E e : this.c) {
                if (e.getNumber() == value) {
                    return e;
                }
            }
            return null;
        }

        /* renamed from: i */
        public int e(E value) {
            return value.getNumber() << this.a;
        }
    }
}
