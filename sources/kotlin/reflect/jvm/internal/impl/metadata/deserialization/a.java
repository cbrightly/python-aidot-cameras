package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.l;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import meshsdk.ConfigUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BinaryVersion.kt */
public abstract class a {
    public static final C0385a a = new C0385a((DefaultConstructorMarker) null);
    private final int b;
    private final int c;
    private final int d;
    @NotNull
    private final List<Integer> e;
    private final int[] f;

    public a(@NotNull int... numbers) {
        k.f(numbers, "numbers");
        this.f = numbers;
        Integer z = l.z(numbers, 0);
        int i = -1;
        this.b = z != null ? z.intValue() : -1;
        Integer z2 = l.z(numbers, 1);
        this.c = z2 != null ? z2.intValue() : -1;
        Integer z3 = l.z(numbers, 2);
        this.d = z3 != null ? z3.intValue() : i;
        this.e = numbers.length > 3 ? y.C0(kotlin.collections.k.b(numbers).subList(3, numbers.length)) : q.g();
    }

    public final int a() {
        return this.b;
    }

    public final int b() {
        return this.c;
    }

    @NotNull
    public final int[] f() {
        return this.f;
    }

    /* access modifiers changed from: protected */
    public final boolean e(@NotNull a ourVersion) {
        k.f(ourVersion, "ourVersion");
        int i = this.b;
        if (i == 0) {
            return ourVersion.b == 0 && this.c == ourVersion.c;
        }
        if (i != ourVersion.b || this.c > ourVersion.c) {
            return false;
        }
        return true;
    }

    public final boolean d(@NotNull a version) {
        k.f(version, ConfigUtil.VERSION_FILE);
        return c(version.b, version.c, version.d);
    }

    public final boolean c(int major, int minor, int patch) {
        int i = this.b;
        if (i > major) {
            return true;
        }
        if (i < major) {
            return false;
        }
        int i2 = this.c;
        if (i2 > minor) {
            return true;
        }
        if (i2 < minor) {
            return false;
        }
        if (this.d >= patch) {
            return true;
        }
        return false;
    }

    @NotNull
    public String toString() {
        int[] $this$takeWhile$iv = f();
        ArrayList list$iv = new ArrayList();
        int length = $this$takeWhile$iv.length;
        for (int i = 0; i < length; i++) {
            int item$iv = $this$takeWhile$iv[i];
            if (!(item$iv != -1)) {
                break;
            }
            list$iv.add(Integer.valueOf(item$iv));
        }
        return list$iv.isEmpty() ? "unknown" : y.b0(list$iv, ".", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (kotlin.jvm.functions.l) null, 62, (Object) null);
    }

    public boolean equals(@Nullable Object other) {
        return other != null && k.a(getClass(), other.getClass()) && this.b == ((a) other).b && this.c == ((a) other).c && this.d == ((a) other).d && k.a(this.e, ((a) other).e);
    }

    public int hashCode() {
        int result = this.b;
        int result2 = result + (result * 31) + this.c;
        int result3 = result2 + (result2 * 31) + this.d;
        return result3 + (result3 * 31) + this.e.hashCode();
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.deserialization.a$a  reason: collision with other inner class name */
    /* compiled from: BinaryVersion.kt */
    public static final class C0385a {
        private C0385a() {
        }

        public /* synthetic */ C0385a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
