package zendesk.faye;

import kotlin.jvm.internal.k;
import okhttp3.z;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.faye.internal.b;
import zendesk.faye.internal.c;

/* compiled from: FayeClientBuilder.kt */
public final class f {
    @NotNull
    private final String a;
    @Nullable
    private z b;
    @Nullable
    private h c;

    public f(@NotNull String serverUrl) {
        k.e(serverUrl, "serverUrl");
        this.a = serverUrl;
    }

    @NotNull
    public final e a() {
        z zVar = this.b;
        if (zVar == null) {
            zVar = new z();
        }
        b bVar = new b(this.a, new c(zVar));
        b $this$build_u24lambda_u2d2 = bVar;
        h p0 = this.c;
        if (p0 != null) {
            $this$build_u24lambda_u2d2.b(p0);
        }
        return bVar;
    }
}
