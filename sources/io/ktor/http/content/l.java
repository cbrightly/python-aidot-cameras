package io.ktor.http.content;

import io.ktor.http.c;
import io.ktor.http.content.j;
import io.ktor.http.d;
import io.ktor.http.v;
import io.netty.util.internal.StringUtil;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.z;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TextContent.kt */
public final class l extends j.a {
    private final g b;
    @NotNull
    private final String c;
    @NotNull
    private final c d;
    @Nullable
    private final v e;

    private final byte[] h() {
        return (byte[]) this.b.getValue();
    }

    public l(@NotNull String text, @NotNull c contentType, @Nullable v status) {
        k.f(text, "text");
        k.f(contentType, "contentType");
        this.c = text;
        this.d = contentType;
        this.e = status;
        this.b = i.a(kotlin.k.NONE, new a(this));
    }

    @NotNull
    public final String i() {
        return this.c;
    }

    @NotNull
    public c b() {
        return this.d;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ l(String str, c cVar, v vVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, cVar, (i & 4) != 0 ? null : vVar);
    }

    @Nullable
    public v e() {
        return this.e;
    }

    /* compiled from: TextContent.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<byte[]> {
        final /* synthetic */ l this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(l lVar) {
            super(0);
            this.this$0 = lVar;
        }

        @NotNull
        public final byte[] invoke() {
            String $this$toByteArray$iv = this.this$0.i();
            Charset charset$iv = d.a(this.this$0.b());
            if (charset$iv == null) {
                charset$iv = kotlin.text.c.a;
            }
            CharsetEncoder newEncoder = charset$iv.newEncoder();
            k.b(newEncoder, "charset.newEncoder()");
            return io.ktor.utils.io.charsets.a.f(newEncoder, $this$toByteArray$iv, 0, $this$toByteArray$iv.length());
        }
    }

    @NotNull
    public Long a() {
        return Long.valueOf((long) h().length);
    }

    @NotNull
    public byte[] g() {
        return h();
    }

    @NotNull
    public String toString() {
        return "TextContent[" + b() + "] \"" + z.j1(this.c, 30) + StringUtil.DOUBLE_QUOTE;
    }
}
