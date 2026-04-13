package okhttp3.internal.authenticator;

import java.net.Authenticator;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import okhttp3.a;
import okhttp3.b0;
import okhttp3.d0;
import okhttp3.f0;
import okhttp3.h;
import okhttp3.o;
import okhttp3.q;
import okhttp3.v;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JavaNetAuthenticator.kt */
public final class b implements okhttp3.b {
    private final q d;

    public b(@NotNull q defaultDns) {
        k.f(defaultDns, "defaultDns");
        this.d = defaultDns;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ b(q qVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? q.a : qVar);
    }

    @Nullable
    public b0 a(@Nullable f0 route, @NotNull d0 response) {
        Proxy proxy;
        q dns;
        PasswordAuthentication auth;
        a a;
        k.f(response, "response");
        List challenges = response.i();
        b0 request = response.J();
        v url = request.l();
        boolean proxyAuthorization = response.j() == 407;
        if (route == null || (proxy = route.b()) == null) {
            proxy = Proxy.NO_PROXY;
        }
        for (h challenge : challenges) {
            if (w.y("Basic", challenge.c(), true)) {
                if (route == null || (a = route.a()) == null || (dns = a.c()) == null) {
                    dns = this.d;
                }
                if (proxyAuthorization) {
                    SocketAddress address = proxy.address();
                    if (address != null) {
                        InetSocketAddress proxyAddress = (InetSocketAddress) address;
                        String hostName = proxyAddress.getHostName();
                        k.b(proxy, "proxy");
                        auth = Authenticator.requestPasswordAuthentication(hostName, b(proxy, url, dns), proxyAddress.getPort(), url.t(), challenge.b(), challenge.c(), url.v(), Authenticator.RequestorType.PROXY);
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type java.net.InetSocketAddress");
                    }
                } else {
                    String j = url.j();
                    k.b(proxy, "proxy");
                    auth = Authenticator.requestPasswordAuthentication(j, b(proxy, url, dns), url.p(), url.t(), challenge.b(), challenge.c(), url.v(), Authenticator.RequestorType.SERVER);
                }
                if (auth != null) {
                    String credentialHeader = proxyAuthorization ? "Proxy-Authorization" : "Authorization";
                    String userName = auth.getUserName();
                    k.b(userName, "auth.userName");
                    char[] password = auth.getPassword();
                    k.b(password, "auth.password");
                    return request.i().g(credentialHeader, o.b(userName, new String(password), challenge.a())).b();
                }
            }
        }
        return null;
    }

    private final InetAddress b(@NotNull Proxy $this$connectToInetAddress, v url, q dns) {
        Proxy.Type type = $this$connectToInetAddress.type();
        if (type != null) {
            switch (a.a[type.ordinal()]) {
                case 1:
                    return (InetAddress) y.S(dns.lookup(url.j()));
            }
        }
        SocketAddress address = $this$connectToInetAddress.address();
        if (address != null) {
            InetAddress address2 = ((InetSocketAddress) address).getAddress();
            k.b(address2, "(address() as InetSocketAddress).address");
            return address2;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.net.InetSocketAddress");
    }
}
