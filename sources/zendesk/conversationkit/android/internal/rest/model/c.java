package zendesk.conversationkit.android.internal.rest.model;

import com.google.chip.chiptool.setuppayloadscanner.a;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: UploadFileDto.kt */
public final class c {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    private final long c;
    @NotNull
    private final String d;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return k.a(this.a, cVar.a) && k.a(this.b, cVar.b) && this.c == cVar.c && k.a(this.d, cVar.d);
    }

    public int hashCode() {
        return (((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + a.a(this.c)) * 31) + this.d.hashCode();
    }

    @NotNull
    public String toString() {
        return "Upload(uri=" + this.a + ", name=" + this.b + ", size=" + this.c + ", mimeType=" + this.d + ')';
    }

    public c(@NotNull String uri, @NotNull String name, long size, @NotNull String mimeType) {
        k.e(uri, "uri");
        k.e(name, "name");
        k.e(mimeType, "mimeType");
        this.a = uri;
        this.b = name;
        this.c = size;
        this.d = mimeType;
    }

    @NotNull
    public final String c() {
        return this.a;
    }

    @NotNull
    public final String b() {
        return this.b;
    }

    @NotNull
    public final String a() {
        return this.d;
    }
}
