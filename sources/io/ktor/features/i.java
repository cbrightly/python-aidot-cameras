package io.ktor.features;

import io.ktor.http.c;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ContentNegotiation.kt */
public final class i {
    @NotNull
    private final c a;
    private final double b;

    @NotNull
    public final c a() {
        return this.a;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof i)) {
            return false;
        }
        i iVar = (i) obj;
        return k.a(this.a, iVar.a) && Double.compare(this.b, iVar.b) == 0;
    }

    public int hashCode() {
        c cVar = this.a;
        int hashCode = cVar != null ? cVar.hashCode() : 0;
        long doubleToLongBits = Double.doubleToLongBits(this.b);
        return (hashCode * 31) + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)));
    }

    @NotNull
    public String toString() {
        return "ContentTypeWithQuality(contentType=" + this.a + ", quality=" + this.b + ")";
    }

    public i(@NotNull c contentType, double quality) {
        k.f(contentType, "contentType");
        this.a = contentType;
        this.b = quality;
        if (!(quality >= 0.0d && quality <= 1.0d)) {
            throw new IllegalArgumentException(("Quality should be in range [0, 1]: " + quality).toString());
        }
    }

    @NotNull
    public final c b() {
        return this.a;
    }

    public final double c() {
        return this.b;
    }
}
