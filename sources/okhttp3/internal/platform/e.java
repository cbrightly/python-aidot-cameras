package okhttp3.internal.platform;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLSocket;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.a0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Jdk8WithJettyBootPlatform.kt */
public final class e extends h {
    public static final b d = new b((DefaultConstructorMarker) null);
    private final Method e;
    private final Method f;
    private final Method g;
    private final Class<?> h;
    private final Class<?> i;

    public e(@NotNull Method putMethod, @NotNull Method getMethod, @NotNull Method removeMethod, @NotNull Class<?> clientProviderClass, @NotNull Class<?> serverProviderClass) {
        k.f(putMethod, "putMethod");
        k.f(getMethod, "getMethod");
        k.f(removeMethod, "removeMethod");
        k.f(clientProviderClass, "clientProviderClass");
        k.f(serverProviderClass, "serverProviderClass");
        this.e = putMethod;
        this.f = getMethod;
        this.g = removeMethod;
        this.h = clientProviderClass;
        this.i = serverProviderClass;
    }

    public void e(@NotNull SSLSocket sslSocket, @Nullable String hostname, @NotNull List<? extends a0> protocols) {
        k.f(sslSocket, "sslSocket");
        k.f(protocols, "protocols");
        List names = h.c.b(protocols);
        try {
            Object alpnProvider = Proxy.newProxyInstance(h.class.getClassLoader(), new Class[]{this.h, this.i}, new a(names));
            this.e.invoke((Object) null, new Object[]{sslSocket, alpnProvider});
        } catch (InvocationTargetException e2) {
            throw new AssertionError("failed to set ALPN", e2);
        } catch (IllegalAccessException e3) {
            throw new AssertionError("failed to set ALPN", e3);
        }
    }

    public void b(@NotNull SSLSocket sslSocket) {
        k.f(sslSocket, "sslSocket");
        try {
            this.g.invoke((Object) null, new Object[]{sslSocket});
        } catch (IllegalAccessException e2) {
            throw new AssertionError("failed to remove ALPN", e2);
        } catch (InvocationTargetException e3) {
            throw new AssertionError("failed to remove ALPN", e3);
        }
    }

    @Nullable
    public String h(@NotNull SSLSocket sslSocket) {
        k.f(sslSocket, "sslSocket");
        try {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(this.f.invoke((Object) null, new Object[]{sslSocket}));
            if (invocationHandler != null) {
                a provider = (a) invocationHandler;
                if (!provider.b() && provider.a() == null) {
                    h.l(this, "ALPN callback dropped: HTTP/2 is disabled. Is alpn-boot on the boot class path?", 0, (Throwable) null, 6, (Object) null);
                    return null;
                } else if (provider.b()) {
                    return null;
                } else {
                    return provider.a();
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type okhttp3.internal.platform.Jdk8WithJettyBootPlatform.AlpnProvider");
            }
        } catch (InvocationTargetException e2) {
            throw new AssertionError("failed to get ALPN selected protocol", e2);
        } catch (IllegalAccessException e3) {
            throw new AssertionError("failed to get ALPN selected protocol", e3);
        }
    }

    /* compiled from: Jdk8WithJettyBootPlatform.kt */
    public static final class a implements InvocationHandler {
        private boolean c;
        @Nullable
        private String d;
        private final List<String> f;

        public a(@NotNull List<String> protocols) {
            k.f(protocols, "protocols");
            this.f = protocols;
        }

        public final boolean b() {
            return this.c;
        }

        @Nullable
        public final String a() {
            return this.d;
        }

        @Nullable
        public Object invoke(@NotNull Object proxy, @NotNull Method method, @Nullable Object[] args) {
            k.f(proxy, "proxy");
            k.f(method, FirebaseAnalytics.Param.METHOD);
            Object[] callArgs = args != null ? args : new Object[0];
            String methodName = method.getName();
            Class returnType = method.getReturnType();
            if (k.a(methodName, "supports") && k.a(Boolean.TYPE, returnType)) {
                return true;
            }
            if (!k.a(methodName, "unsupported") || !k.a(Void.TYPE, returnType)) {
                if (k.a(methodName, "protocols")) {
                    if (callArgs.length == 0) {
                        return this.f;
                    }
                }
                if ((k.a(methodName, "selectProtocol") || k.a(methodName, "select")) && k.a(String.class, returnType) && callArgs.length == 1 && (callArgs[0] instanceof List)) {
                    Object obj = callArgs[0];
                    if (obj != null) {
                        List peerProtocols = (List) obj;
                        int size = peerProtocols.size();
                        if (size >= 0) {
                            int i = 0;
                            while (true) {
                                Object obj2 = peerProtocols.get(i);
                                if (obj2 != null) {
                                    String protocol = (String) obj2;
                                    if (!this.f.contains(protocol)) {
                                        if (i == size) {
                                            break;
                                        }
                                        i++;
                                    } else {
                                        this.d = protocol;
                                        return protocol;
                                    }
                                } else {
                                    throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
                                }
                            }
                        }
                        String str = this.f.get(0);
                        this.d = str;
                        return str;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<*>");
                } else if ((!k.a(methodName, "protocolSelected") && !k.a(methodName, "selected")) || callArgs.length != 1) {
                    return method.invoke(this, Arrays.copyOf(callArgs, callArgs.length));
                } else {
                    Object obj3 = callArgs[0];
                    if (obj3 != null) {
                        this.d = (String) obj3;
                        return null;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
                }
            } else {
                this.c = true;
                return null;
            }
        }
    }

    /* compiled from: Jdk8WithJettyBootPlatform.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @Nullable
        public final h a() {
            String jvmVersion = System.getProperty("java.specification.version", "unknown");
            try {
                k.b(jvmVersion, "jvmVersion");
                if (Integer.parseInt(jvmVersion) >= 9) {
                    return null;
                }
            } catch (NumberFormatException e) {
            }
            try {
                Class alpnClass = Class.forName("org.eclipse.jetty.alpn.ALPN", true, (ClassLoader) null);
                Class providerClass = Class.forName("org.eclipse.jetty.alpn.ALPN" + "$Provider", true, (ClassLoader) null);
                Class clientProviderClass = Class.forName("org.eclipse.jetty.alpn.ALPN" + "$ClientProvider", true, (ClassLoader) null);
                Class serverProviderClass = Class.forName("org.eclipse.jetty.alpn.ALPN" + "$ServerProvider", true, (ClassLoader) null);
                Method putMethod = alpnClass.getMethod("put", new Class[]{SSLSocket.class, providerClass});
                Method getMethod = alpnClass.getMethod("get", new Class[]{SSLSocket.class});
                Method removeMethod = alpnClass.getMethod("remove", new Class[]{SSLSocket.class});
                k.b(putMethod, "putMethod");
                k.b(getMethod, "getMethod");
                k.b(removeMethod, "removeMethod");
                k.b(clientProviderClass, "clientProviderClass");
                k.b(serverProviderClass, "serverProviderClass");
                return new e(putMethod, getMethod, removeMethod, clientProviderClass, serverProviderClass);
            } catch (ClassNotFoundException | NoSuchMethodException e2) {
                return null;
            }
        }
    }
}
