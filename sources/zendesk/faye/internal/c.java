package zendesk.faye.internal;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.b0;
import okhttp3.h0;
import okhttp3.i0;
import okhttp3.z;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: OkHttpWebSocket.kt */
public final class c {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final z b;
    @Nullable
    private h0 c;

    public c(@NotNull z client) {
        k.e(client, "client");
        this.b = client;
    }

    public final boolean a(@NotNull String url, @NotNull i0 listener) {
        k.e(url, "url");
        k.e(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        if (this.c != null) {
            zendesk.logger.a.h("OkHttpWebSocket", "connectTo was called but socket was not null", new Object[0]);
            return false;
        }
        this.c = this.b.A(new b0.a().p(url).b(), listener);
        return true;
    }

    public final boolean b() {
        h0 h0Var = this.c;
        Boolean bool = null;
        if (h0Var != null) {
            bool = Boolean.valueOf(h0Var.f(1000, (String) null));
        }
        boolean result = false;
        if (bool == null) {
            zendesk.logger.a.h("OkHttpWebSocket", "disconnect was called but socket was null", new Object[0]);
        } else {
            result = bool.booleanValue();
        }
        if (result) {
            c();
        }
        return result;
    }

    public final boolean d(@NotNull String message) {
        k.e(message, "message");
        h0 h0Var = this.c;
        Boolean valueOf = h0Var == null ? null : Boolean.valueOf(h0Var.a(message));
        if (valueOf != null) {
            return valueOf.booleanValue();
        }
        zendesk.logger.a.h("OkHttpWebSocket", "send was called but socket was null", new Object[0]);
        return false;
    }

    public final void c() {
        this.c = null;
    }

    /* compiled from: OkHttpWebSocket.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
