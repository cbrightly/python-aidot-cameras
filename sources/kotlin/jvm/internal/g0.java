package kotlin.jvm.internal;

import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.reflect.c;
import kotlin.reflect.d;
import kotlin.reflect.n;
import kotlin.reflect.p;
import kotlin.reflect.r;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.e;

/* compiled from: TypeReference.kt */
public final class g0 implements n {
    @NotNull
    private final d c;
    @NotNull
    private final List<p> d;
    private final boolean f;

    public g0(@NotNull d classifier, @NotNull List<p> arguments, boolean isMarkedNullable) {
        k.e(classifier, "classifier");
        k.e(arguments, "arguments");
        this.c = classifier;
        this.d = arguments;
        this.f = isMarkedNullable;
    }

    @NotNull
    public d a() {
        return this.c;
    }

    @NotNull
    public List<p> e() {
        return this.d;
    }

    public boolean o() {
        return this.f;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof g0) && k.a(a(), ((g0) other).a()) && k.a(e(), ((g0) other).e()) && o() == ((g0) other).o();
    }

    public int hashCode() {
        return (((a().hashCode() * 31) + e().hashCode()) * 31) + Boolean.valueOf(o()).hashCode();
    }

    @NotNull
    public String toString() {
        return c() + " (Kotlin reflection is not available)";
    }

    private final String c() {
        String klass;
        String args;
        d a2 = a();
        Class cls = null;
        if (!(a2 instanceof c)) {
            a2 = null;
        }
        c cVar = (c) a2;
        if (cVar != null) {
            cls = kotlin.jvm.a.b(cVar);
        }
        Class javaClass = cls;
        if (javaClass == null) {
            klass = a().toString();
        } else if (javaClass.isArray()) {
            klass = n(javaClass);
        } else {
            klass = javaClass.getName();
        }
        String nullable = "";
        if (e().isEmpty()) {
            args = nullable;
        } else {
            args = y.b0(e(), ", ", "<", ">", 0, (CharSequence) null, new a(this), 24, (Object) null);
        }
        if (o()) {
            nullable = "?";
        }
        return klass + args + nullable;
    }

    /* compiled from: TypeReference.kt */
    public static final class a extends l implements l<p, CharSequence> {
        final /* synthetic */ g0 this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(g0 g0Var) {
            super(1);
            this.this$0 = g0Var;
        }

        @NotNull
        public final CharSequence invoke(@NotNull p it) {
            k.e(it, "it");
            return this.this$0.d(it);
        }
    }

    private final String n(Class<?> $this$arrayClassName) {
        if (k.a($this$arrayClassName, boolean[].class)) {
            return "kotlin.BooleanArray";
        }
        if (k.a($this$arrayClassName, char[].class)) {
            return "kotlin.CharArray";
        }
        if (k.a($this$arrayClassName, byte[].class)) {
            return "kotlin.ByteArray";
        }
        if (k.a($this$arrayClassName, short[].class)) {
            return "kotlin.ShortArray";
        }
        if (k.a($this$arrayClassName, int[].class)) {
            return "kotlin.IntArray";
        }
        if (k.a($this$arrayClassName, float[].class)) {
            return "kotlin.FloatArray";
        }
        if (k.a($this$arrayClassName, long[].class)) {
            return "kotlin.LongArray";
        }
        if (k.a($this$arrayClassName, double[].class)) {
            return "kotlin.DoubleArray";
        }
        return "kotlin.Array";
    }

    /* access modifiers changed from: private */
    public final String d(p $this$asString) {
        String typeString;
        if ($this$asString.b() == null) {
            return e.ANY_MARKER;
        }
        n a2 = $this$asString.a();
        if (!(a2 instanceof g0)) {
            a2 = null;
        }
        g0 g0Var = (g0) a2;
        if (g0Var == null || (typeString = g0Var.c()) == null) {
            typeString = String.valueOf($this$asString.a());
        }
        r b = $this$asString.b();
        if (b != null) {
            switch (f0.a[b.ordinal()]) {
                case 1:
                    return typeString;
                case 2:
                    return "in " + typeString;
                case 3:
                    return "out " + typeString;
            }
        }
        throw new NoWhenBranchMatchedException();
    }
}
