package coil.fetch;

import coil.decode.b;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okio.e;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FetchResult.kt */
public final class m extends f {
    @NotNull
    private final e a;
    @Nullable
    private final String b;
    @NotNull
    private final b c;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof m)) {
            return false;
        }
        m mVar = (m) obj;
        return k.a(this.a, mVar.a) && k.a(this.b, mVar.b) && this.c == mVar.c;
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        String str = this.b;
        return ((hashCode + (str == null ? 0 : str.hashCode())) * 31) + this.c.hashCode();
    }

    @NotNull
    public String toString() {
        return "SourceResult(source=" + this.a + ", mimeType=" + this.b + ", dataSource=" + this.c + ')';
    }

    @NotNull
    public final e c() {
        return this.a;
    }

    @Nullable
    public final String b() {
        return this.b;
    }

    @NotNull
    public final b a() {
        return this.c;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public m(@NotNull e source, @Nullable String mimeType, @NotNull b dataSource) {
        super((DefaultConstructorMarker) null);
        k.e(source, "source");
        k.e(dataSource, "dataSource");
        this.a = source;
        this.b = mimeType;
        this.c = dataSource;
    }
}
