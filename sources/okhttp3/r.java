package okhttp3;

import androidx.core.app.NotificationCompat;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: EventListener.kt */
public abstract class r {
    @NotNull
    public static final r a = new a();
    public static final b b = new b((DefaultConstructorMarker) null);

    /* compiled from: EventListener.kt */
    public interface c {
        @NotNull
        r a(@NotNull e eVar);
    }

    public void f(@NotNull e call) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
    }

    public void p(@NotNull e call, @NotNull v url) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(url, "url");
    }

    public void o(@NotNull e call, @NotNull v url, @NotNull List<Proxy> proxies) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(url, "url");
        k.f(proxies, "proxies");
    }

    public void n(@NotNull e call, @NotNull String domainName) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(domainName, "domainName");
    }

    public void m(@NotNull e call, @NotNull String domainName, @NotNull List<InetAddress> inetAddressList) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(domainName, "domainName");
        k.f(inetAddressList, "inetAddressList");
    }

    public void j(@NotNull e call, @NotNull InetSocketAddress inetSocketAddress, @NotNull Proxy proxy) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(inetSocketAddress, "inetSocketAddress");
        k.f(proxy, "proxy");
    }

    public void C(@NotNull e call) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
    }

    public void B(@NotNull e call, @Nullable t handshake) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
    }

    public void h(@NotNull e call, @NotNull InetSocketAddress inetSocketAddress, @NotNull Proxy proxy, @Nullable a0 protocol) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(inetSocketAddress, "inetSocketAddress");
        k.f(proxy, "proxy");
    }

    public void i(@NotNull e call, @NotNull InetSocketAddress inetSocketAddress, @NotNull Proxy proxy, @Nullable a0 protocol, @NotNull IOException ioe) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(inetSocketAddress, "inetSocketAddress");
        k.f(proxy, "proxy");
        k.f(ioe, "ioe");
    }

    public void k(@NotNull e call, @NotNull j connection) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(connection, "connection");
    }

    public void l(@NotNull e call, @NotNull j connection) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(connection, "connection");
    }

    public void u(@NotNull e call) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
    }

    public void t(@NotNull e call, @NotNull b0 request) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(request, Progress.REQUEST);
    }

    public void r(@NotNull e call) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
    }

    public void q(@NotNull e call, long byteCount) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
    }

    public void s(@NotNull e call, @NotNull IOException ioe) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(ioe, "ioe");
    }

    public void z(@NotNull e call) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
    }

    public void y(@NotNull e call, @NotNull d0 response) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(response, "response");
    }

    public void w(@NotNull e call) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
    }

    public void v(@NotNull e call, long byteCount) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
    }

    public void x(@NotNull e call, @NotNull IOException ioe) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(ioe, "ioe");
    }

    public void d(@NotNull e call) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
    }

    public void e(@NotNull e call, @NotNull IOException ioe) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(ioe, "ioe");
    }

    public void g(@NotNull e call) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
    }

    public void A(@NotNull e call, @NotNull d0 response) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(response, "response");
    }

    public void b(@NotNull e call, @NotNull d0 response) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(response, "response");
    }

    public void c(@NotNull e call) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
    }

    public void a(@NotNull e call, @NotNull d0 cachedResponse) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(cachedResponse, "cachedResponse");
    }

    /* compiled from: EventListener.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    /* compiled from: EventListener.kt */
    public static final class a extends r {
        a() {
        }
    }
}
