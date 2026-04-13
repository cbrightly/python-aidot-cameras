package io.ktor.http.content;

import io.ktor.util.date.b;
import io.ktor.util.date.c;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Versions.kt */
public final class e {
    private final c a;
    @NotNull
    private final c b;

    public boolean equals(@Nullable Object obj) {
        if (this != obj) {
            return (obj instanceof e) && k.a(this.b, ((e) obj).b);
        }
        return true;
    }

    public int hashCode() {
        c cVar = this.b;
        if (cVar != null) {
            return cVar.hashCode();
        }
        return 0;
    }

    @NotNull
    public String toString() {
        return "LastModifiedVersion(lastModified=" + this.b + ")";
    }

    public e(@NotNull c lastModified) {
        k.f(lastModified, "lastModified");
        this.b = lastModified;
        this.a = b.a(lastModified);
    }
}
