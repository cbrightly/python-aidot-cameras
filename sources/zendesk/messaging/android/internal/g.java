package zendesk.messaging.android.internal;

import android.content.Context;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.jvm.functions.a;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.messaging.android.internal.conversationscreen.n;
import zendesk.messaging.android.internal.conversationscreen.o;
import zendesk.messaging.android.internal.conversationscreen.q;
import zendesk.messaging.android.internal.conversationscreen.r;

/* compiled from: Environment.kt */
public final class g implements e {
    @NotNull
    private final Context b;

    public g(@NotNull Context context) {
        k.e(context, "context");
        this.b = context;
    }

    @NotNull
    public q c() {
        return new q(this.b);
    }

    @NotNull
    public r d() {
        return new r(this.b, (Locale) null, (TimeZone) null, false, 14, (DefaultConstructorMarker) null);
    }

    @NotNull
    public n b() {
        return new n(c(), d(), (a) null, 4, (DefaultConstructorMarker) null);
    }

    @NotNull
    public o a() {
        return new o(b(), c(), d(), (a) null, (a) null, 24, (DefaultConstructorMarker) null);
    }
}
