package okhttp3.internal.http2;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okio.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Header.kt */
public final class b {
    @NotNull
    public static final f a;
    @NotNull
    public static final f b;
    @NotNull
    public static final f c;
    @NotNull
    public static final f d;
    @NotNull
    public static final f e;
    @NotNull
    public static final f f;
    public static final a g = new a((DefaultConstructorMarker) null);
    public final int h;
    @NotNull
    public final f i;
    @NotNull
    public final f j;

    @NotNull
    public final f a() {
        return this.i;
    }

    @NotNull
    public final f b() {
        return this.j;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return k.a(this.i, bVar.i) && k.a(this.j, bVar.j);
    }

    public int hashCode() {
        f fVar = this.i;
        int i2 = 0;
        int hashCode = (fVar != null ? fVar.hashCode() : 0) * 31;
        f fVar2 = this.j;
        if (fVar2 != null) {
            i2 = fVar2.hashCode();
        }
        return hashCode + i2;
    }

    public b(@NotNull f name, @NotNull f value) {
        k.f(name, "name");
        k.f(value, "value");
        this.i = name;
        this.j = value;
        this.h = name.size() + 32 + value.size();
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public b(@org.jetbrains.annotations.NotNull java.lang.String r3, @org.jetbrains.annotations.NotNull java.lang.String r4) {
        /*
            r2 = this;
            java.lang.String r0 = "name"
            kotlin.jvm.internal.k.f(r3, r0)
            java.lang.String r0 = "value"
            kotlin.jvm.internal.k.f(r4, r0)
            okio.f$a r0 = okio.f.Companion
            okio.f r1 = r0.d(r3)
            okio.f r0 = r0.d(r4)
            r2.<init>((okio.f) r1, (okio.f) r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.b.<init>(java.lang.String, java.lang.String):void");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public b(@NotNull f name, @NotNull String value) {
        this(name, f.Companion.d(value));
        k.f(name, "name");
        k.f(value, "value");
    }

    @NotNull
    public String toString() {
        return this.i.utf8() + ": " + this.j.utf8();
    }

    /* compiled from: Header.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    static {
        f.a aVar = f.Companion;
        a = aVar.d(":");
        b = aVar.d(":status");
        c = aVar.d(":method");
        d = aVar.d(":path");
        e = aVar.d(":scheme");
        f = aVar.d(":authority");
    }
}
