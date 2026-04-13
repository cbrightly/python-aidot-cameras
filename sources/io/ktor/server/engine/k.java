package io.ktor.server.engine;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.collections.g0;
import kotlin.collections.l;
import kotlin.collections.o0;
import kotlin.collections.p0;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.v;
import kotlin.collections.y;
import kotlin.ranges.i;
import kotlin.text.w;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: ClassLoaders.kt */
public final class k {
    @NotNull
    public static final Set<URL> a(@NotNull ClassLoader $this$allURLs) {
        Set parentUrls;
        kotlin.jvm.internal.k.f($this$allURLs, "$this$allURLs");
        ClassLoader parent = $this$allURLs.getParent();
        if (parent == null || (parentUrls = a(parent)) == null) {
            parentUrls = o0.d();
        }
        if ($this$allURLs instanceof URLClassLoader) {
            URL[] uRLs = ((URLClassLoader) $this$allURLs).getURLs();
            kotlin.jvm.internal.k.b(uRLs, "urLs");
            return p0.i(y.H0(l.s(uRLs)), parentUrls);
        }
        List ucp = c($this$allURLs);
        if (ucp != null) {
            return p0.i(parentUrls, ucp);
        }
        return parentUrls;
    }

    private static final List<URL> c(@NotNull ClassLoader $this$urlClassPath) {
        Method getURLsMethod;
        try {
            Field ucpField = b($this$urlClassPath.getClass());
            if (ucpField == null) {
                return null;
            }
            ucpField.setAccessible(true);
            Object ucpInstance = ucpField.get($this$urlClassPath);
            if (ucpInstance == null || (getURLsMethod = ucpInstance.getClass().getMethod("getURLs", new Class[0])) == null) {
                return null;
            }
            getURLsMethod.setAccessible(true);
            URL[] urls = (URL[]) getURLsMethod.invoke(ucpInstance, new Object[0]);
            if (urls != null) {
                return l.U(urls);
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    private static final List<URL> d(@NotNull ClassLoader $this$urlClassPathByPackagesList) {
        Iterable list$iv$iv;
        ClassLoader classLoader = $this$urlClassPathByPackagesList;
        Iterable<String> $this$mapTo$iv$iv = new j(classLoader).a();
        int i = 10;
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (String it : $this$mapTo$iv$iv) {
            arrayList.add(w.G(it, '.', '/', false, 4, (Object) null));
        }
        Collection destination$iv = new HashSet();
        Iterable<String> $this$flatMapTo$iv = arrayList;
        for (String packageName : $this$flatMapTo$iv) {
            List segments = x.E0(packageName, new char[]{'/'}, false, 0, 6, (Object) null);
            Iterable $this$mapTo$iv$iv2 = new i(1, segments.size());
            Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv2, i));
            Iterator it2 = $this$mapTo$iv$iv2.iterator();
            while (it2.hasNext()) {
                destination$iv$iv.add(y.b0(segments.subList(0, ((g0) it2).nextInt()), "/", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (kotlin.jvm.functions.l) null, 62, (Object) null));
                $this$flatMapTo$iv = $this$flatMapTo$iv;
            }
            v.x(destination$iv, y.o0(destination$iv$iv, packageName));
            $this$flatMapTo$iv = $this$flatMapTo$iv;
            i = 10;
        }
        ArrayList arrayList2 = new ArrayList();
        for (String path : y.o0(y.u0(destination$iv, new a()), "")) {
            Enumeration resources = classLoader.getResources(path);
            if (resources != null) {
                list$iv$iv = Collections.list(resources);
                kotlin.jvm.internal.k.b(list$iv$iv, "java.util.Collections.list(this)");
                if (list$iv$iv != null) {
                    v.x(arrayList2, list$iv$iv);
                }
            }
            list$iv$iv = q.g();
            v.x(arrayList2, list$iv$iv);
        }
        Iterable $this$distinctBy$iv = arrayList2;
        HashSet set$iv = new HashSet();
        ArrayList list$iv = new ArrayList();
        for (Object e$iv : $this$distinctBy$iv) {
            URL it3 = (URL) e$iv;
            kotlin.jvm.internal.k.b(it3, "it");
            String path2 = it3.getPath();
            kotlin.jvm.internal.k.b(path2, "it.path");
            if (set$iv.add(x.Y0(path2, '!', (String) null, 2, (Object) null)) != 0) {
                list$iv.add(e$iv);
            }
        }
        return list$iv;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0041 A[LOOP:0: B:1:0x000d->B:10:0x0041, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0045 A[EDGE_INSN: B:21:0x0045->B:12:0x0045 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final java.lang.reflect.Field b(@org.jetbrains.annotations.NotNull java.lang.Class<?> r11) {
        /*
            java.lang.reflect.Field[] r0 = r11.getDeclaredFields()
            java.lang.String r1 = "declaredFields"
            kotlin.jvm.internal.k.b(r0, r1)
            r1 = 0
            int r2 = r0.length
            r3 = 0
            r4 = r3
        L_0x000d:
            r5 = 0
            if (r4 >= r2) goto L_0x0044
            r6 = r0[r4]
            r7 = r6
            r8 = 0
            java.lang.String r9 = "it"
            kotlin.jvm.internal.k.b(r7, r9)
            java.lang.String r9 = r7.getName()
            java.lang.String r10 = "ucp"
            boolean r9 = kotlin.jvm.internal.k.a(r9, r10)
            if (r9 == 0) goto L_0x003d
            java.lang.Class r9 = r7.getType()
            java.lang.String r10 = "it.type"
            kotlin.jvm.internal.k.b(r9, r10)
            java.lang.String r9 = r9.getSimpleName()
            java.lang.String r10 = "URLClassPath"
            boolean r9 = kotlin.jvm.internal.k.a(r9, r10)
            if (r9 == 0) goto L_0x003d
            r9 = 1
            goto L_0x003e
        L_0x003d:
            r9 = r3
        L_0x003e:
            if (r9 == 0) goto L_0x0041
            goto L_0x0045
        L_0x0041:
            int r4 = r4 + 1
            goto L_0x000d
        L_0x0044:
            r6 = r5
        L_0x0045:
            if (r6 == 0) goto L_0x004a
            r0 = r6
            r1 = 0
            return r0
        L_0x004a:
            java.lang.Class r0 = r11.getSuperclass()
            if (r0 == 0) goto L_0x0058
            java.lang.reflect.Field r0 = b(r0)
            if (r0 == 0) goto L_0x0058
            return r0
        L_0x0058:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.engine.k.b(java.lang.Class):java.lang.reflect.Field");
    }

    /* compiled from: Comparisons.kt */
    public static final class a<T> implements Comparator<T> {
        public final int compare(T a, T b) {
            CharSequence $this$count$iv = (String) a;
            int count$iv = 0;
            int i = 0;
            while (true) {
                boolean z = true;
                if (i >= $this$count$iv.length()) {
                    break;
                }
                if ($this$count$iv.charAt(i) != '/') {
                    z = false;
                }
                if (z) {
                    count$iv++;
                }
                i++;
            }
            Integer valueOf = Integer.valueOf(count$iv);
            CharSequence $this$count$iv2 = (String) b;
            int count$iv2 = 0;
            for (int i2 = 0; i2 < $this$count$iv2.length(); i2++) {
                if (($this$count$iv2.charAt(i2) == '/' ? (char) 1 : 0) != 0) {
                    count$iv2++;
                }
            }
            return kotlin.comparisons.a.c(valueOf, Integer.valueOf(count$iv2));
        }
    }
}
