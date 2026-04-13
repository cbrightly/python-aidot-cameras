package zendesk.conversationkit.android.model;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Config.kt */
public final class h {
    @NotNull
    private final d a;
    @NotNull
    private final String b;
    @NotNull
    private final x c;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof h)) {
            return false;
        }
        h hVar = (h) obj;
        return k.a(this.a, hVar.a) && k.a(this.b, hVar.b) && k.a(this.c, hVar.c);
    }

    public int hashCode() {
        return (((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
    }

    @NotNull
    public String toString() {
        return "Config(app=" + this.a + ", baseUrl=" + this.b + ", restRetryPolicy=" + this.c + ')';
    }

    public h(@NotNull d app, @NotNull String baseUrl, @NotNull x restRetryPolicy) {
        k.e(app, "app");
        k.e(baseUrl, "baseUrl");
        k.e(restRetryPolicy, "restRetryPolicy");
        this.a = app;
        this.b = baseUrl;
        this.c = restRetryPolicy;
    }

    @NotNull
    public final d a() {
        return this.a;
    }

    @NotNull
    public final String b() {
        return this.b;
    }
}
