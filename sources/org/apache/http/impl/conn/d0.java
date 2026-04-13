package org.apache.http.impl.conn;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.HttpException;
import org.apache.http.conn.t;
import org.apache.http.l;
import org.apache.http.o;
import org.apache.http.protocol.f;

/* compiled from: SystemDefaultRoutePlanner */
public class d0 extends p {
    private final ProxySelector b;

    public d0(t schemePortResolver, ProxySelector proxySelector) {
        super(schemePortResolver);
        this.b = proxySelector;
    }

    /* access modifiers changed from: protected */
    public l b(l target, o request, f context) {
        try {
            URI targetURI = new URI(target.toURI());
            ProxySelector proxySelectorInstance = this.b;
            if (proxySelectorInstance == null) {
                proxySelectorInstance = ProxySelector.getDefault();
            }
            if (proxySelectorInstance == null) {
                return null;
            }
            Proxy p = c(proxySelectorInstance.select(targetURI));
            if (p.type() != Proxy.Type.HTTP) {
                return null;
            }
            if (p.address() instanceof InetSocketAddress) {
                InetSocketAddress isa = (InetSocketAddress) p.address();
                return new l(d(isa), isa.getPort());
            }
            throw new HttpException("Unable to handle non-Inet proxy address: " + p.address());
        } catch (URISyntaxException ex) {
            throw new HttpException("Cannot convert host to URI: " + target, ex);
        }
    }

    private String d(InetSocketAddress isa) {
        return isa.isUnresolved() ? isa.getHostName() : isa.getAddress().getHostAddress();
    }

    private Proxy c(List<Proxy> proxies) {
        Proxy result = null;
        int i = 0;
        while (result == null && i < proxies.size()) {
            Proxy p = proxies.get(i);
            switch (a.a[p.type().ordinal()]) {
                case 1:
                case 2:
                    result = p;
                    break;
            }
            i++;
        }
        if (result == null) {
            return Proxy.NO_PROXY;
        }
        return result;
    }

    /* compiled from: SystemDefaultRoutePlanner */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[Proxy.Type.values().length];
            a = iArr;
            try {
                iArr[Proxy.Type.DIRECT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[Proxy.Type.HTTP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[Proxy.Type.SOCKS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }
}
