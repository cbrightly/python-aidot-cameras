package kotlinx.coroutines.internal;

import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import kotlin.collections.r;
import kotlin.collections.v;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;

@kotlin.l(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J!\u0010\u0005\u001a\u0004\u0018\u00010\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u0006\u0010\t\u001a\u00020\u0004H\bJ1\u0010\n\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b2\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\bH\u0002¢\u0006\u0002\u0010\u0010J*\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0012\"\u0004\b\u0000\u0010\u000b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\b2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0013\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00060\u0012H\u0000¢\u0006\u0002\b\u0014J/\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0012\"\u0004\b\u0000\u0010\u000b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\b2\u0006\u0010\r\u001a\u00020\u000eH\u0000¢\u0006\u0002\b\u0016J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u00122\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00040\u00122\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J,\u0010\u001d\u001a\u0002H\u001e\"\u0004\b\u0000\u0010\u001e*\u00020\u001f2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u0002H\u001e0!H\b¢\u0006\u0002\u0010\"R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lkotlinx/coroutines/internal/FastServiceLoader;", "", "()V", "PREFIX", "", "createInstanceOf", "Lkotlinx/coroutines/internal/MainDispatcherFactory;", "baseClass", "Ljava/lang/Class;", "serviceClass", "getProviderInstance", "S", "name", "loader", "Ljava/lang/ClassLoader;", "service", "(Ljava/lang/String;Ljava/lang/ClassLoader;Ljava/lang/Class;)Ljava/lang/Object;", "load", "", "loadMainDispatcherFactory", "loadMainDispatcherFactory$kotlinx_coroutines_core", "loadProviders", "loadProviders$kotlinx_coroutines_core", "parse", "url", "Ljava/net/URL;", "parseFile", "r", "Ljava/io/BufferedReader;", "use", "R", "Ljava/util/jar/JarFile;", "block", "Lkotlin/Function1;", "(Ljava/util/jar/JarFile;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: FastServiceLoader.kt */
public final class l {
    @NotNull
    public static final l a = new l();

    private l() {
    }

    @NotNull
    public final List<v> c() {
        v $this$loadMainDispatcherFactory_u24lambda_u2d0;
        Class clz = v.class;
        if (!m.a()) {
            return b(clz, clz.getClassLoader());
        }
        try {
            ArrayList result = new ArrayList(2);
            v $this$loadMainDispatcherFactory_u24lambda_u2d1 = null;
            try {
                $this$loadMainDispatcherFactory_u24lambda_u2d0 = clz.cast(Class.forName("kotlinx.coroutines.android.AndroidDispatcherFactory", true, clz.getClassLoader()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
            } catch (ClassNotFoundException e) {
                $this$loadMainDispatcherFactory_u24lambda_u2d0 = null;
            }
            if ($this$loadMainDispatcherFactory_u24lambda_u2d0 != null) {
                result.add($this$loadMainDispatcherFactory_u24lambda_u2d0);
            }
            try {
                $this$loadMainDispatcherFactory_u24lambda_u2d1 = clz.cast(Class.forName("kotlinx.coroutines.test.internal.TestMainDispatcherFactory", true, clz.getClassLoader()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
            } catch (ClassNotFoundException e2) {
            }
            if ($this$loadMainDispatcherFactory_u24lambda_u2d1 == null) {
                return result;
            }
            result.add($this$loadMainDispatcherFactory_u24lambda_u2d1);
            return result;
        } catch (Throwable th) {
            return b(clz, clz.getClassLoader());
        }
    }

    private final <S> List<S> b(Class<S> service, ClassLoader loader) {
        try {
            return d(service, loader);
        } catch (Throwable th) {
            return y.C0(ServiceLoader.load(service, loader));
        }
    }

    @NotNull
    public final <S> List<S> d(@NotNull Class<S> service, @NotNull ClassLoader loader) {
        Iterable<URL> $this$flatMapTo$iv$iv = Collections.list(loader.getResources(k.l("META-INF/services/", service.getName())));
        k.d($this$flatMapTo$iv$iv, "list(this)");
        Collection destination$iv$iv = new ArrayList();
        for (URL it : $this$flatMapTo$iv$iv) {
            v.x(destination$iv$iv, a.e(it));
        }
        Set providers = y.H0(destination$iv$iv);
        if (!providers.isEmpty()) {
            Iterable<String> $this$mapTo$iv$iv = providers;
            ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (String it2 : $this$mapTo$iv$iv) {
                arrayList.add(a.a(it2, loader, service));
            }
            return arrayList;
        }
        throw new IllegalArgumentException("No providers were loaded with FastServiceLoader".toString());
    }

    private final <S> S a(String name, ClassLoader loader, Class<S> service) {
        Class clazz = Class.forName(name, false, loader);
        if (service.isAssignableFrom(clazz)) {
            return service.cast(clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
        }
        throw new IllegalArgumentException(("Expected service of class " + service + ", but found " + clazz).toString());
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0056, code lost:
        r11 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        kotlin.io.b.a(r10, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x005a, code lost:
        throw r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0087, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0088, code lost:
        kotlin.io.b.a(r1, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x008b, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.util.List<java.lang.String> e(java.net.URL r15) {
        /*
            r14 = this;
            java.lang.String r0 = r15.toString()
            java.lang.String r1 = "jar"
            r2 = 0
            r3 = 2
            r4 = 0
            boolean r1 = kotlin.text.w.N(r0, r1, r2, r3, r4)
            if (r1 == 0) goto L_0x006b
            java.lang.String r1 = "jar:file:"
            java.lang.String r1 = kotlin.text.x.R0(r0, r1, r4, r3, r4)
            r5 = 33
            java.lang.String r1 = kotlin.text.x.Y0(r1, r5, r4, r3, r4)
            java.lang.String r5 = "!/"
            java.lang.String r3 = kotlin.text.x.R0(r0, r5, r4, r3, r4)
            java.util.jar.JarFile r5 = new java.util.jar.JarFile
            r5.<init>(r1, r2)
            r2 = r5
            r5 = r14
            r6 = 0
            r7 = 0
            r8 = r2
            r9 = 0
            java.io.BufferedReader r10 = new java.io.BufferedReader     // Catch:{ all -> 0x005b }
            java.io.InputStreamReader r11 = new java.io.InputStreamReader     // Catch:{ all -> 0x005b }
            java.util.zip.ZipEntry r12 = new java.util.zip.ZipEntry     // Catch:{ all -> 0x005b }
            r12.<init>(r3)     // Catch:{ all -> 0x005b }
            java.io.InputStream r12 = r8.getInputStream(r12)     // Catch:{ all -> 0x005b }
            java.lang.String r13 = "UTF-8"
            r11.<init>(r12, r13)     // Catch:{ all -> 0x005b }
            r10.<init>(r11)     // Catch:{ all -> 0x005b }
            r11 = r10
            r12 = 0
            kotlinx.coroutines.internal.l r13 = a     // Catch:{ all -> 0x0054 }
            java.util.List r13 = r13.f(r11)     // Catch:{ all -> 0x0054 }
            kotlin.io.b.a(r10, r4)     // Catch:{ all -> 0x005b }
            r2.close()     // Catch:{ all -> 0x0052 }
            return r13
        L_0x0052:
            r2 = move-exception
            throw r2
        L_0x0054:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x0056 }
        L_0x0056:
            r11 = move-exception
            kotlin.io.b.a(r10, r4)     // Catch:{ all -> 0x005b }
            throw r11     // Catch:{ all -> 0x005b }
        L_0x005b:
            r4 = move-exception
            r7 = r4
            throw r4     // Catch:{ all -> 0x005f }
        L_0x005f:
            r4 = move-exception
            r2.close()     // Catch:{ all -> 0x0065 }
            throw r4
        L_0x0065:
            r4 = move-exception
            kotlin.b.a(r7, r4)
            throw r7
        L_0x006b:
            java.io.BufferedReader r1 = new java.io.BufferedReader
            java.io.InputStreamReader r2 = new java.io.InputStreamReader
            java.io.InputStream r3 = r15.openStream()
            r2.<init>(r3)
            r1.<init>(r2)
            r2 = r1
            r3 = 0
            kotlinx.coroutines.internal.l r5 = a     // Catch:{ all -> 0x0085 }
            java.util.List r5 = r5.f(r2)     // Catch:{ all -> 0x0085 }
            kotlin.io.b.a(r1, r4)
            return r5
        L_0x0085:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0087 }
        L_0x0087:
            r3 = move-exception
            kotlin.io.b.a(r1, r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.l.e(java.net.URL):java.util.List");
    }

    private final List<String> f(BufferedReader r) {
        CharSequence $this$all$iv;
        char it;
        Set names = new LinkedHashSet();
        while (true) {
            String line = r.readLine();
            if (line == null) {
                return y.C0(names);
            }
            CharSequence serviceName = x.e1(x.Z0(line, "#", (String) null, 2, (Object) null)).toString();
            CharSequence $this$all$iv2 = serviceName;
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= $this$all$iv2.length()) {
                    $this$all$iv = 1;
                    break;
                }
                char element$iv = $this$all$iv2.charAt(i);
                i++;
                char it2 = element$iv;
                if (it2 == '.' || Character.isJavaIdentifierPart(it2)) {
                    it = 1;
                    continue;
                } else {
                    it = 0;
                    continue;
                }
                if (it == 0) {
                    $this$all$iv = null;
                    break;
                }
            }
            if ($this$all$iv != null) {
                if (serviceName.length() > 0) {
                    z = true;
                }
                if (z) {
                    names.add(serviceName);
                }
            } else {
                throw new IllegalArgumentException(k.l("Illegal service provider class name: ", serviceName).toString());
            }
        }
    }
}
