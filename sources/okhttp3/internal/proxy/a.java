package okhttp3.internal.proxy;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.List;
import kotlin.collections.p;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NullProxySelector.kt */
public final class a extends ProxySelector {
    public static final a a = new a();

    private a() {
    }

    @NotNull
    public List<Proxy> select(@Nullable URI uri) {
        if (uri != null) {
            return p.b(Proxy.NO_PROXY);
        }
        throw new IllegalArgumentException("uri must not be null".toString());
    }

    public void connectFailed(@Nullable URI uri, @Nullable SocketAddress sa, @Nullable IOException ioe) {
    }
}
