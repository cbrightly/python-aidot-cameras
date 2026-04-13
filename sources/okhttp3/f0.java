package okhttp3;

import com.google.android.libraries.places.api.model.PlaceTypes;
import java.net.InetSocketAddress;
import java.net.Proxy;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Route.kt */
public final class f0 {
    @NotNull
    private final a a;
    @NotNull
    private final Proxy b;
    @NotNull
    private final InetSocketAddress c;

    public f0(@NotNull a address, @NotNull Proxy proxy, @NotNull InetSocketAddress socketAddress) {
        k.f(address, PlaceTypes.ADDRESS);
        k.f(proxy, "proxy");
        k.f(socketAddress, "socketAddress");
        this.a = address;
        this.b = proxy;
        this.c = socketAddress;
    }

    @NotNull
    public final a a() {
        return this.a;
    }

    @NotNull
    public final Proxy b() {
        return this.b;
    }

    @NotNull
    public final InetSocketAddress d() {
        return this.c;
    }

    public final boolean c() {
        return this.a.k() != null && this.b.type() == Proxy.Type.HTTP;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof f0) && k.a(((f0) other).a, this.a) && k.a(((f0) other).b, this.b) && k.a(((f0) other).c, this.c);
    }

    public int hashCode() {
        return (((((17 * 31) + this.a.hashCode()) * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
    }

    @NotNull
    public String toString() {
        return "Route{" + this.c + '}';
    }
}
