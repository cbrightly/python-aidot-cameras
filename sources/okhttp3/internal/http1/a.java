package okhttp3.internal.http1;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.u;
import okio.e;
import org.jetbrains.annotations.NotNull;

/* compiled from: HeadersReader.kt */
public final class a {
    public static final C0462a a = new C0462a((DefaultConstructorMarker) null);
    private long b = ((long) 262144);
    @NotNull
    private final e c;

    public a(@NotNull e source) {
        k.f(source, "source");
        this.c = source;
    }

    @NotNull
    public final String b() {
        String line = this.c.Q(this.b);
        this.b -= (long) line.length();
        return line;
    }

    @NotNull
    public final u a() {
        u.a result = new u.a();
        while (true) {
            String line = b();
            if (line.length() == 0) {
                return result.f();
            }
            result.c(line);
        }
    }

    /* renamed from: okhttp3.internal.http1.a$a  reason: collision with other inner class name */
    /* compiled from: HeadersReader.kt */
    public static final class C0462a {
        private C0462a() {
        }

        public /* synthetic */ C0462a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
