package kotlin.random;

import java.util.Random;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: PlatformRandom.kt */
public final class b extends a {
    private final a d = new a();

    /* compiled from: PlatformRandom.kt */
    public static final class a extends ThreadLocal<Random> {
        a() {
        }

        /* access modifiers changed from: protected */
        @NotNull
        /* renamed from: a */
        public Random initialValue() {
            return new Random();
        }
    }

    @NotNull
    public Random getImpl() {
        Object obj = this.d.get();
        k.d(obj, "implStorage.get()");
        return (Random) obj;
    }
}
