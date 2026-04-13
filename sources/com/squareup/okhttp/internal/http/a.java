package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.b;
import com.squareup.okhttp.g;
import com.squareup.okhttp.l;
import com.squareup.okhttp.q;
import com.squareup.okhttp.v;
import com.squareup.okhttp.x;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.List;

/* compiled from: AuthenticatorAdapter */
public final class a implements b {
    public static final b a = new a();

    public v b(Proxy proxy, x response) {
        List<g> n = response.n();
        v request = response.x();
        q url = request.k();
        int size = n.size();
        for (int i = 0; i < size; i++) {
            g challenge = n.get(i);
            if (!"Basic".equalsIgnoreCase(challenge.b())) {
                Proxy proxy2 = proxy;
            } else {
                PasswordAuthentication auth = Authenticator.requestPasswordAuthentication(url.q(), c(proxy, url), url.A(), url.E(), challenge.a(), challenge.b(), url.G(), Authenticator.RequestorType.SERVER);
                if (auth != null) {
                    return request.n().i("Authorization", l.a(auth.getUserName(), new String(auth.getPassword()))).g();
                }
            }
        }
        Proxy proxy3 = proxy;
        return null;
    }

    public v a(Proxy proxy, x response) {
        List<g> n = response.n();
        v request = response.x();
        q url = request.k();
        int size = n.size();
        for (int i = 0; i < size; i++) {
            g challenge = n.get(i);
            if ("Basic".equalsIgnoreCase(challenge.b())) {
                InetSocketAddress proxyAddress = (InetSocketAddress) proxy.address();
                PasswordAuthentication auth = Authenticator.requestPasswordAuthentication(proxyAddress.getHostName(), c(proxy, url), proxyAddress.getPort(), url.E(), challenge.a(), challenge.b(), url.G(), Authenticator.RequestorType.PROXY);
                if (auth != null) {
                    return request.n().i("Proxy-Authorization", l.a(auth.getUserName(), new String(auth.getPassword()))).g();
                }
            }
        }
        return null;
    }

    private InetAddress c(Proxy proxy, q url) {
        if (proxy == null || proxy.type() == Proxy.Type.DIRECT) {
            return InetAddress.getByName(url.q());
        }
        return ((InetSocketAddress) proxy.address()).getAddress();
    }
}
