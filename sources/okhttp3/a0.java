package okhttp3;

import io.netty.handler.ssl.ApplicationProtocolNames;
import java.io.IOException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Protocol.kt */
public enum a0 {
    HTTP_1_0("http/1.0"),
    HTTP_1_1(ApplicationProtocolNames.HTTP_1_1),
    SPDY_3(ApplicationProtocolNames.SPDY_3_1),
    HTTP_2(ApplicationProtocolNames.HTTP_2),
    H2_PRIOR_KNOWLEDGE("h2_prior_knowledge"),
    QUIC("quic");
    
    public static final a Companion = null;
    /* access modifiers changed from: private */
    public final String protocol;

    @NotNull
    public static final a0 get(@NotNull String str) {
        return Companion.a(str);
    }

    private a0(String protocol2) {
        this.protocol = protocol2;
    }

    static {
        Companion = new a((DefaultConstructorMarker) null);
    }

    @NotNull
    public String toString() {
        return this.protocol;
    }

    /* compiled from: Protocol.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final a0 a(@NotNull String protocol) {
            k.f(protocol, "protocol");
            a0 a0Var = a0.HTTP_1_0;
            if (!k.a(protocol, a0Var.protocol)) {
                a0Var = a0.HTTP_1_1;
                if (!k.a(protocol, a0Var.protocol)) {
                    a0Var = a0.H2_PRIOR_KNOWLEDGE;
                    if (!k.a(protocol, a0Var.protocol)) {
                        a0Var = a0.HTTP_2;
                        if (!k.a(protocol, a0Var.protocol)) {
                            a0Var = a0.SPDY_3;
                            if (!k.a(protocol, a0Var.protocol)) {
                                a0Var = a0.QUIC;
                                if (!k.a(protocol, a0Var.protocol)) {
                                    throw new IOException("Unexpected protocol: " + protocol);
                                }
                            }
                        }
                    }
                }
            }
            return a0Var;
        }
    }
}
