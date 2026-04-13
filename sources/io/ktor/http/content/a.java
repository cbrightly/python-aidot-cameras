package io.ktor.http.content;

import io.ktor.http.c;
import io.ktor.http.content.j;
import io.ktor.http.v;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ByteArrayContent.kt */
public final class a extends j.a {
    private final byte[] b;
    @Nullable
    private final c c;
    @Nullable
    private final v d;

    public a(@NotNull byte[] bytes, @Nullable c contentType, @Nullable v status) {
        k.f(bytes, "bytes");
        this.b = bytes;
        this.c = contentType;
        this.d = status;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ a(byte[] bArr, c cVar, v vVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(bArr, (i & 2) != 0 ? null : cVar, (i & 4) != 0 ? null : vVar);
    }

    @Nullable
    public c b() {
        return this.c;
    }

    @Nullable
    public v e() {
        return this.d;
    }

    @NotNull
    public Long a() {
        return Long.valueOf((long) this.b.length);
    }

    @NotNull
    public byte[] g() {
        return this.b;
    }
}
