package zendesk.android.internal.network;

import android.content.Context;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.android.internal.extension.a;
import zendesk.android.internal.g;

/* compiled from: NetworkData.kt */
public final class b {
    @NotNull
    private final Context a;
    @NotNull
    private final g b;

    public b(@NotNull Context context, @NotNull g config) {
        k.e(context, "context");
        k.e(config, "config");
        this.a = context;
        this.b = config;
    }

    @NotNull
    public final String b() {
        return "Zendesk-SDK/" + this.b.d() + " Android/" + this.b.c() + " Variant/Zendesk";
    }

    @NotNull
    public final String a() {
        return a.a(this.a);
    }
}
