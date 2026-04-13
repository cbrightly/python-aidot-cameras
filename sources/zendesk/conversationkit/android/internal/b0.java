package zendesk.conversationkit.android.internal;

import android.content.Context;
import com.squareup.moshi.r;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.internal.user.c;
import zendesk.storage.android.b;
import zendesk.storage.android.d;
import zendesk.storage.android.e;

/* compiled from: StorageFactory.kt */
public final class b0 {
    @NotNull
    private final Context a;
    @NotNull
    private final b b;

    public b0(@NotNull Context context, @NotNull b serializer) {
        k.e(context, "context");
        k.e(serializer, "serializer");
        this.a = context;
        this.b = serializer;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ b0(Context context, b bVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? new n((r) null, 1, (DefaultConstructorMarker) null) : bVar);
    }

    @NotNull
    public final k b() {
        return new k(d.a.a("zendesk.conversationkit", this.a, e.a.a));
    }

    @NotNull
    public final zendesk.conversationkit.android.internal.app.b a(@NotNull String appId) {
        k.e(appId, "appId");
        return new zendesk.conversationkit.android.internal.app.b(d.a.a(k.l("zendesk.conversationkit.app.", appId), this.a, new e.b(this.b)));
    }

    @NotNull
    public final c c(@NotNull String userId) {
        k.e(userId, "userId");
        return new c(d.a.a(k.l("zendesk.conversationkit.user.", userId), this.a, new e.b(this.b)));
    }
}
