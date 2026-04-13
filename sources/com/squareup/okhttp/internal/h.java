package com.squareup.okhttp.internal;

import com.squareup.okhttp.internal.tls.e;
import com.squareup.okhttp.internal.tls.f;
import com.squareup.okhttp.u;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/* compiled from: Platform */
public class h {
    private static final h a = e();

    public static h f() {
        return a;
    }

    public String g() {
        return "OkHttp";
    }

    public void i(String warning) {
        System.out.println(warning);
    }

    public X509TrustManager k(SSLSocketFactory sslSocketFactory) {
        return null;
    }

    public f l(X509TrustManager trustManager) {
        return new e(trustManager.getAcceptedIssuers());
    }

    public void c(SSLSocket sslSocket, String hostname, List<u> list) {
    }

    public void a(SSLSocket sslSocket) {
    }

    public String h(SSLSocket socket) {
        return null;
    }

    public void d(Socket socket, InetSocketAddress address, int connectTimeout) {
        socket.connect(address, connectTimeout);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x007e, code lost:
        r0 = r4;
        r12 = r5;
        r13 = r9;
        r14 = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x007b A[ExcHandler: NoSuchMethodException (e java.lang.NoSuchMethodException), PHI: r4 r5 r9 
      PHI: (r4v13 'trafficStatsTagSocket' java.lang.reflect.Method) = (r4v10 'trafficStatsTagSocket' java.lang.reflect.Method), (r4v14 'trafficStatsTagSocket' java.lang.reflect.Method), (r4v14 'trafficStatsTagSocket' java.lang.reflect.Method) binds: [B:9:0x0037, B:12:0x0059, B:13:?] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r5v7 'trafficStatsUntagSocket' java.lang.reflect.Method) = (r5v4 'trafficStatsUntagSocket' java.lang.reflect.Method), (r5v8 'trafficStatsUntagSocket' java.lang.reflect.Method), (r5v8 'trafficStatsUntagSocket' java.lang.reflect.Method) binds: [B:9:0x0037, B:12:0x0059, B:13:?] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r9v5 'getAlpnSelectedProtocol' com.squareup.okhttp.internal.OptionalMethod<java.net.Socket>) = (r9v2 'getAlpnSelectedProtocol' com.squareup.okhttp.internal.OptionalMethod<java.net.Socket>), (r9v2 'getAlpnSelectedProtocol' com.squareup.okhttp.internal.OptionalMethod<java.net.Socket>), (r9v8 'getAlpnSelectedProtocol' com.squareup.okhttp.internal.OptionalMethod<java.net.Socket>) binds: [B:9:0x0037, B:12:0x0059, B:13:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:9:0x0037] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.squareup.okhttp.internal.h e() {
        /*
            java.lang.Class<byte[]> r0 = byte[].class
            r1 = 1
            r2 = 0
            java.lang.String r3 = "com.android.org.conscrypt.SSLParametersImpl"
            java.lang.Class r3 = java.lang.Class.forName(r3)     // Catch:{ ClassNotFoundException -> 0x000b }
            goto L_0x0014
        L_0x000b:
            r3 = move-exception
            java.lang.String r4 = "org.apache.harmony.xnet.provider.jsse.SSLParametersImpl"
            java.lang.Class r4 = java.lang.Class.forName(r4)     // Catch:{ ClassNotFoundException -> 0x008e }
            r3 = r4
        L_0x0014:
            com.squareup.okhttp.internal.g r6 = new com.squareup.okhttp.internal.g     // Catch:{ ClassNotFoundException -> 0x008e }
            java.lang.String r4 = "setUseSessionTickets"
            java.lang.Class[] r5 = new java.lang.Class[r1]     // Catch:{ ClassNotFoundException -> 0x008e }
            java.lang.Class r7 = java.lang.Boolean.TYPE     // Catch:{ ClassNotFoundException -> 0x008e }
            r5[r2] = r7     // Catch:{ ClassNotFoundException -> 0x008e }
            r8 = 0
            r6.<init>(r8, r4, r5)     // Catch:{ ClassNotFoundException -> 0x008e }
            com.squareup.okhttp.internal.g r7 = new com.squareup.okhttp.internal.g     // Catch:{ ClassNotFoundException -> 0x008e }
            java.lang.String r4 = "setHostname"
            java.lang.Class[] r5 = new java.lang.Class[r1]     // Catch:{ ClassNotFoundException -> 0x008e }
            java.lang.Class<java.lang.String> r9 = java.lang.String.class
            r5[r2] = r9     // Catch:{ ClassNotFoundException -> 0x008e }
            r7.<init>(r8, r4, r5)     // Catch:{ ClassNotFoundException -> 0x008e }
            r4 = 0
            r5 = 0
            r9 = 0
            r10 = 0
            java.lang.String r11 = "android.net.TrafficStats"
            java.lang.Class r11 = java.lang.Class.forName(r11)     // Catch:{ ClassNotFoundException -> 0x007d, NoSuchMethodException -> 0x007b }
            java.lang.String r12 = "tagSocket"
            java.lang.Class[] r13 = new java.lang.Class[r1]     // Catch:{ ClassNotFoundException -> 0x007d, NoSuchMethodException -> 0x007b }
            java.lang.Class<java.net.Socket> r14 = java.net.Socket.class
            r13[r2] = r14     // Catch:{ ClassNotFoundException -> 0x007d, NoSuchMethodException -> 0x007b }
            java.lang.reflect.Method r12 = r11.getMethod(r12, r13)     // Catch:{ ClassNotFoundException -> 0x007d, NoSuchMethodException -> 0x007b }
            r4 = r12
            java.lang.String r12 = "untagSocket"
            java.lang.Class[] r13 = new java.lang.Class[r1]     // Catch:{ ClassNotFoundException -> 0x007d, NoSuchMethodException -> 0x007b }
            java.lang.Class<java.net.Socket> r14 = java.net.Socket.class
            r13[r2] = r14     // Catch:{ ClassNotFoundException -> 0x007d, NoSuchMethodException -> 0x007b }
            java.lang.reflect.Method r12 = r11.getMethod(r12, r13)     // Catch:{ ClassNotFoundException -> 0x007d, NoSuchMethodException -> 0x007b }
            r5 = r12
            java.lang.String r12 = "android.net.Network"
            java.lang.Class.forName(r12)     // Catch:{ ClassNotFoundException -> 0x0075, NoSuchMethodException -> 0x007b }
            com.squareup.okhttp.internal.g r12 = new com.squareup.okhttp.internal.g     // Catch:{ ClassNotFoundException -> 0x0075, NoSuchMethodException -> 0x007b }
            java.lang.String r13 = "getAlpnSelectedProtocol"
            java.lang.Class[] r14 = new java.lang.Class[r2]     // Catch:{ ClassNotFoundException -> 0x0075, NoSuchMethodException -> 0x007b }
            r12.<init>(r0, r13, r14)     // Catch:{ ClassNotFoundException -> 0x0075, NoSuchMethodException -> 0x007b }
            r9 = r12
            com.squareup.okhttp.internal.g r12 = new com.squareup.okhttp.internal.g     // Catch:{ ClassNotFoundException -> 0x0075, NoSuchMethodException -> 0x007b }
            java.lang.String r13 = "setAlpnProtocols"
            java.lang.Class[] r14 = new java.lang.Class[r1]     // Catch:{ ClassNotFoundException -> 0x0075, NoSuchMethodException -> 0x007b }
            r14[r2] = r0     // Catch:{ ClassNotFoundException -> 0x0075, NoSuchMethodException -> 0x007b }
            r12.<init>(r8, r13, r14)     // Catch:{ ClassNotFoundException -> 0x0075, NoSuchMethodException -> 0x007b }
            r0 = r12
            r10 = r0
            goto L_0x0076
        L_0x0075:
            r0 = move-exception
        L_0x0076:
            r0 = r4
            r12 = r5
            r13 = r9
            r14 = r10
            goto L_0x0082
        L_0x007b:
            r0 = move-exception
            goto L_0x007e
        L_0x007d:
            r0 = move-exception
        L_0x007e:
            r0 = r4
            r12 = r5
            r13 = r9
            r14 = r10
        L_0x0082:
            com.squareup.okhttp.internal.h$a r15 = new com.squareup.okhttp.internal.h$a     // Catch:{ ClassNotFoundException -> 0x008e }
            r4 = r15
            r5 = r3
            r8 = r0
            r9 = r12
            r10 = r13
            r11 = r14
            r4.<init>(r5, r6, r7, r8, r9, r10, r11)     // Catch:{ ClassNotFoundException -> 0x008e }
            return r15
        L_0x008e:
            r0 = move-exception
            java.lang.String r0 = "sun.security.ssl.SSLContextImpl"
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x0119 }
            java.lang.String r3 = "org.eclipse.jetty.alpn.ALPN"
            r10 = r3
            java.lang.Class r3 = java.lang.Class.forName(r10)     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            r11 = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            r3.<init>()     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            r3.append(r10)     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.String r4 = "$Provider"
            r3.append(r4)     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.String r3 = r3.toString()     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.Class r3 = java.lang.Class.forName(r3)     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            r12 = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            r3.<init>()     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            r3.append(r10)     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.String r4 = "$ClientProvider"
            r3.append(r4)     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.String r3 = r3.toString()     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.Class r8 = java.lang.Class.forName(r3)     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            r3.<init>()     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            r3.append(r10)     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.String r4 = "$ServerProvider"
            r3.append(r4)     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.String r3 = r3.toString()     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.Class r9 = java.lang.Class.forName(r3)     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.String r3 = "put"
            r4 = 2
            java.lang.Class[] r4 = new java.lang.Class[r4]     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.Class<javax.net.ssl.SSLSocket> r5 = javax.net.ssl.SSLSocket.class
            r4[r2] = r5     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            r4[r1] = r12     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.reflect.Method r5 = r11.getMethod(r3, r4)     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.String r3 = "get"
            java.lang.Class[] r4 = new java.lang.Class[r1]     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.Class<javax.net.ssl.SSLSocket> r6 = javax.net.ssl.SSLSocket.class
            r4[r2] = r6     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.reflect.Method r6 = r11.getMethod(r3, r4)     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.String r3 = "remove"
            java.lang.Class[] r1 = new java.lang.Class[r1]     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.Class<javax.net.ssl.SSLSocket> r4 = javax.net.ssl.SSLSocket.class
            r1[r2] = r4     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            java.lang.reflect.Method r7 = r11.getMethod(r3, r1)     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            com.squareup.okhttp.internal.h$c r1 = new com.squareup.okhttp.internal.h$c     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            r3 = r1
            r4 = r0
            r3.<init>(r4, r5, r6, r7, r8, r9)     // Catch:{ ClassNotFoundException -> 0x0112, NoSuchMethodException -> 0x0110 }
            return r1
        L_0x0110:
            r1 = move-exception
            goto L_0x0113
        L_0x0112:
            r1 = move-exception
        L_0x0113:
            com.squareup.okhttp.internal.h$b r1 = new com.squareup.okhttp.internal.h$b     // Catch:{ ClassNotFoundException -> 0x0119 }
            r1.<init>(r0)     // Catch:{ ClassNotFoundException -> 0x0119 }
            return r1
        L_0x0119:
            r0 = move-exception
            com.squareup.okhttp.internal.h r0 = new com.squareup.okhttp.internal.h
            r0.<init>()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.h.e():com.squareup.okhttp.internal.h");
    }

    /* compiled from: Platform */
    public static class a extends h {
        private final Class<?> b;
        private final g<Socket> c;
        private final g<Socket> d;
        private final Method e;
        private final Method f;
        private final g<Socket> g;
        private final g<Socket> h;

        public a(Class<?> sslParametersClass, g<Socket> setUseSessionTickets, g<Socket> setHostname, Method trafficStatsTagSocket, Method trafficStatsUntagSocket, g<Socket> getAlpnSelectedProtocol, g<Socket> setAlpnProtocols) {
            this.b = sslParametersClass;
            this.c = setUseSessionTickets;
            this.d = setHostname;
            this.e = trafficStatsTagSocket;
            this.f = trafficStatsUntagSocket;
            this.g = getAlpnSelectedProtocol;
            this.h = setAlpnProtocols;
        }

        public void d(Socket socket, InetSocketAddress address, int connectTimeout) {
            try {
                socket.connect(address, connectTimeout);
            } catch (AssertionError e2) {
                if (j.o(e2)) {
                    throw new IOException(e2);
                }
                throw e2;
            } catch (SecurityException e3) {
                IOException ioException = new IOException("Exception in connect");
                ioException.initCause(e3);
                throw ioException;
            }
        }

        public X509TrustManager k(SSLSocketFactory sslSocketFactory) {
            Object context = h.j(sslSocketFactory, this.b, "sslParameters");
            if (context == null) {
                try {
                    context = h.j(sslSocketFactory, Class.forName("com.google.android.gms.org.conscrypt.SSLParametersImpl", false, sslSocketFactory.getClass().getClassLoader()), "sslParameters");
                } catch (ClassNotFoundException e2) {
                    return null;
                }
            }
            X509TrustManager x509TrustManager = (X509TrustManager) h.j(context, X509TrustManager.class, "x509TrustManager");
            if (x509TrustManager != null) {
                return x509TrustManager;
            }
            return (X509TrustManager) h.j(context, X509TrustManager.class, "trustManager");
        }

        public f l(X509TrustManager trustManager) {
            f result = com.squareup.okhttp.internal.tls.a.b(trustManager);
            if (result != null) {
                return result;
            }
            return h.super.l(trustManager);
        }

        public void c(SSLSocket sslSocket, String hostname, List<u> protocols) {
            if (hostname != null) {
                this.c.e(sslSocket, true);
                this.d.e(sslSocket, hostname);
            }
            g<Socket> gVar = this.h;
            if (gVar != null && gVar.g(sslSocket)) {
                this.h.f(sslSocket, h.b(protocols));
            }
        }

        public String h(SSLSocket socket) {
            byte[] alpnResult;
            g<Socket> gVar = this.g;
            if (gVar == null || !gVar.g(socket) || (alpnResult = (byte[]) this.g.f(socket, new Object[0])) == null) {
                return null;
            }
            return new String(alpnResult, j.c);
        }
    }

    /* compiled from: Platform */
    public static class b extends h {
        private final Class<?> b;

        public b(Class<?> sslContextClass) {
            this.b = sslContextClass;
        }

        public X509TrustManager k(SSLSocketFactory sslSocketFactory) {
            Object context = h.j(sslSocketFactory, this.b, "context");
            if (context == null) {
                return null;
            }
            return (X509TrustManager) h.j(context, X509TrustManager.class, "trustManager");
        }
    }

    /* compiled from: Platform */
    public static class c extends b {
        private final Method c;
        private final Method d;
        private final Method e;
        private final Class<?> f;
        private final Class<?> g;

        public c(Class<?> sslContextClass, Method putMethod, Method getMethod, Method removeMethod, Class<?> clientProviderClass, Class<?> serverProviderClass) {
            super(sslContextClass);
            this.c = putMethod;
            this.d = getMethod;
            this.e = removeMethod;
            this.f = clientProviderClass;
            this.g = serverProviderClass;
        }

        public void c(SSLSocket sslSocket, String hostname, List<u> protocols) {
            List<String> names = new ArrayList<>(protocols.size());
            int size = protocols.size();
            for (int i = 0; i < size; i++) {
                u protocol = protocols.get(i);
                if (protocol != u.HTTP_1_0) {
                    names.add(protocol.toString());
                }
            }
            try {
                Object provider = Proxy.newProxyInstance(h.class.getClassLoader(), new Class[]{this.f, this.g}, new d(names));
                this.c.invoke((Object) null, new Object[]{sslSocket, provider});
            } catch (IllegalAccessException | InvocationTargetException e2) {
                throw new AssertionError(e2);
            }
        }

        public void a(SSLSocket sslSocket) {
            try {
                this.e.invoke((Object) null, new Object[]{sslSocket});
            } catch (IllegalAccessException | InvocationTargetException e2) {
                throw new AssertionError();
            }
        }

        public String h(SSLSocket socket) {
            try {
                d provider = (d) Proxy.getInvocationHandler(this.d.invoke((Object) null, new Object[]{socket}));
                if (!provider.d && provider.f == null) {
                    d.a.log(Level.INFO, "ALPN callback dropped: SPDY and HTTP/2 are disabled. Is alpn-boot on the boot class path?");
                    return null;
                } else if (provider.d) {
                    return null;
                } else {
                    return provider.f;
                }
            } catch (IllegalAccessException | InvocationTargetException e2) {
                throw new AssertionError();
            }
        }
    }

    /* compiled from: Platform */
    public static class d implements InvocationHandler {
        private final List<String> c;
        /* access modifiers changed from: private */
        public boolean d;
        /* access modifiers changed from: private */
        public String f;

        public d(List<String> protocols) {
            this.c = protocols;
        }

        public Object invoke(Object proxy, Method method, Object[] args) {
            String methodName = method.getName();
            Class<?> returnType = method.getReturnType();
            if (args == null) {
                args = j.b;
            }
            if (methodName.equals("supports") && Boolean.TYPE == returnType) {
                return true;
            }
            if (methodName.equals("unsupported") && Void.TYPE == returnType) {
                this.d = true;
                return null;
            } else if (methodName.equals("protocols") && args.length == 0) {
                return this.c;
            } else {
                if ((methodName.equals("selectProtocol") || methodName.equals("select")) && String.class == returnType && args.length == 1 && (args[0] instanceof List)) {
                    List<String> peerProtocols = (List) args[0];
                    int size = peerProtocols.size();
                    for (int i = 0; i < size; i++) {
                        if (this.c.contains(peerProtocols.get(i))) {
                            String str = peerProtocols.get(i);
                            this.f = str;
                            return str;
                        }
                    }
                    String str2 = this.c.get(0);
                    this.f = str2;
                    return str2;
                } else if ((!methodName.equals("protocolSelected") && !methodName.equals("selected")) || args.length != 1) {
                    return method.invoke(this, args);
                } else {
                    this.f = (String) args[0];
                    return null;
                }
            }
        }
    }

    static byte[] b(List<u> protocols) {
        okio.c result = new okio.c();
        int size = protocols.size();
        for (int i = 0; i < size; i++) {
            u protocol = protocols.get(i);
            if (protocol != u.HTTP_1_0) {
                result.writeByte(protocol.toString().length());
                result.writeUtf8(protocol.toString());
            }
        }
        return result.q0();
    }

    static <T> T j(Object instance, Class<T> fieldType, String fieldName) {
        Object delegate;
        Class<Object> cls = Object.class;
        Class cls2 = instance.getClass();
        while (cls2 != cls) {
            try {
                Field field = cls2.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(instance);
                if (value != null) {
                    if (fieldType.isInstance(value)) {
                        return fieldType.cast(value);
                    }
                }
                return null;
            } catch (NoSuchFieldException e) {
                cls2 = cls2.getSuperclass();
            } catch (IllegalAccessException e2) {
                throw new AssertionError();
            }
        }
        if (fieldName.equals("delegate") || (delegate = j(instance, cls, "delegate")) == null) {
            return null;
        }
        return j(delegate, fieldType, fieldName);
    }
}
