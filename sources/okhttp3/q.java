package okhttp3;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import kotlin.collections.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Dns.kt */
public interface q {
    @NotNull
    public static final q a = new a.C0476a();
    public static final a b = new a((DefaultConstructorMarker) null);

    @NotNull
    List<InetAddress> lookup(@NotNull String str);

    /* compiled from: Dns.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* renamed from: okhttp3.q$a$a  reason: collision with other inner class name */
        /* compiled from: Dns.kt */
        public static final class C0476a implements q {
            @NotNull
            public List<InetAddress> lookup(@NotNull String hostname) {
                k.f(hostname, "hostname");
                try {
                    InetAddress[] allByName = InetAddress.getAllByName(hostname);
                    k.b(allByName, "InetAddress.getAllByName(hostname)");
                    return l.U(allByName);
                } catch (NullPointerException e) {
                    UnknownHostException $this$apply = new UnknownHostException("Broken system behaviour for dns lookup of " + hostname);
                    $this$apply.initCause(e);
                    throw $this$apply;
                }
            }
        }
    }
}
