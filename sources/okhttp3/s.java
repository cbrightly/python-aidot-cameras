package okhttp3;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.v;
import okio.c;
import okio.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FormBody.kt */
public final class s extends c0 {
    private static final x a = x.c.a("application/x-www-form-urlencoded");
    public static final b b = new b((DefaultConstructorMarker) null);
    private final List<String> c;
    private final List<String> d;

    public s(@NotNull List<String> encodedNames, @NotNull List<String> encodedValues) {
        k.f(encodedNames, "encodedNames");
        k.f(encodedValues, "encodedValues");
        this.c = okhttp3.internal.b.R(encodedNames);
        this.d = okhttp3.internal.b.R(encodedValues);
    }

    @NotNull
    public x contentType() {
        return a;
    }

    public long contentLength() {
        return a((d) null, true);
    }

    public void writeTo(@NotNull d sink) {
        k.f(sink, "sink");
        a(sink, false);
    }

    private final long a(d sink, boolean countBytes) {
        c buffer;
        if (countBytes) {
            buffer = new c();
        } else {
            if (sink == null) {
                k.n();
            }
            buffer = sink.getBuffer();
        }
        int size = this.c.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                buffer.writeByte(38);
            }
            buffer.writeUtf8(this.c.get(i));
            buffer.writeByte(61);
            buffer.writeUtf8(this.d.get(i));
        }
        if (!countBytes) {
            return 0;
        }
        long byteCount = buffer.e1();
        buffer.clear();
        return byteCount;
    }

    /* compiled from: FormBody.kt */
    public static final class a {
        private final List<String> a;
        private final List<String> b;
        private final Charset c;

        public a() {
            this((Charset) null, 1, (DefaultConstructorMarker) null);
        }

        public a(@Nullable Charset charset) {
            this.c = charset;
            this.a = new ArrayList();
            this.b = new ArrayList();
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ a(Charset charset, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : charset);
        }

        @NotNull
        public final a a(@NotNull String name, @NotNull String value) {
            k.f(name, "name");
            k.f(value, "value");
            List<String> list = this.a;
            v.b bVar = v.b;
            list.add(v.b.b(bVar, name, 0, 0, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, false, this.c, 91, (Object) null));
            this.b.add(v.b.b(bVar, value, 0, 0, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, false, this.c, 91, (Object) null));
            return this;
        }

        @NotNull
        public final a b(@NotNull String name, @NotNull String value) {
            k.f(name, "name");
            k.f(value, "value");
            List<String> list = this.a;
            v.b bVar = v.b;
            list.add(v.b.b(bVar, name, 0, 0, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, false, this.c, 83, (Object) null));
            this.b.add(v.b.b(bVar, value, 0, 0, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, false, this.c, 83, (Object) null));
            return this;
        }

        @NotNull
        public final s c() {
            return new s(this.a, this.b);
        }
    }

    /* compiled from: FormBody.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
