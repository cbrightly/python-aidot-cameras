package org.glassfish.grizzly.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

public final class ServiceFinder<T> implements Iterable<T> {
    private static final String prefix = "META-INF/services/";
    private final ClassLoader classLoader;
    private final Class<T> serviceClass;

    public static <T> ServiceFinder<T> find(Class<T> service, ClassLoader loader) {
        return new ServiceFinder<>(service, loader);
    }

    public static <T> ServiceFinder<T> find(Class<T> service) {
        return find(service, Thread.currentThread().getContextClassLoader());
    }

    private ServiceFinder(Class<T> service, ClassLoader loader) {
        this.serviceClass = service;
        this.classLoader = loader;
    }

    public Iterator<T> iterator() {
        return new LazyIterator(this.serviceClass, this.classLoader);
    }

    public T[] toArray() {
        List<T> result = new ArrayList<>();
        Iterator it = iterator();
        while (it.hasNext()) {
            result.add(it.next());
        }
        return result.toArray((Object[]) Array.newInstance(this.serviceClass, result.size()));
    }

    /* access modifiers changed from: private */
    public static void fail(Class service, String msg, Throwable cause) {
        ServiceConfigurationError sce = new ServiceConfigurationError(service.getName() + ": " + msg);
        sce.initCause(cause);
        throw sce;
    }

    /* access modifiers changed from: private */
    public static void fail(Class service, String msg) {
        throw new ServiceConfigurationError(service.getName() + ": " + msg);
    }

    private static void fail(Class service, URL u, int line, String msg) {
        fail(service, u + ":" + line + ": " + msg);
    }

    private static int parseLine(Class service, URL u, BufferedReader r, int lc, List<String> names, Set<String> returned) {
        String ln = r.readLine();
        if (ln == null) {
            return -1;
        }
        int ci = ln.indexOf(35);
        if (ci >= 0) {
            ln = ln.substring(0, ci);
        }
        String ln2 = ln.trim();
        int n = ln2.length();
        if (n != 0) {
            if (ln2.indexOf(32) >= 0 || ln2.indexOf(9) >= 0) {
                fail(service, u, lc, "Illegal configuration-file syntax");
            }
            int cp = ln2.codePointAt(0);
            if (!Character.isJavaIdentifierStart(cp)) {
                fail(service, u, lc, "Illegal provider-class name: " + ln2);
            }
            int i = Character.charCount(cp);
            while (i < n) {
                int cp2 = ln2.codePointAt(i);
                if (!Character.isJavaIdentifierPart(cp2) && cp2 != 46) {
                    fail(service, u, lc, "Illegal provider-class name: " + ln2);
                }
                i += Character.charCount(cp2);
            }
            if (returned.contains(ln2) == 0) {
                names.add(ln2);
                returned.add(ln2);
            }
        }
        return lc + 1;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0062 A[SYNTHETIC, Splitter:B:24:0x0062] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x006a A[Catch:{ IOException -> 0x0066 }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x007c A[SYNTHETIC, Splitter:B:35:0x007c] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0084 A[Catch:{ IOException -> 0x0080 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Iterator<java.lang.String> parse(java.lang.Class r9, java.net.URL r10, java.util.Set<java.lang.String> r11) {
        /*
            java.lang.String r0 = ": "
            r1 = 0
            r2 = 0
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            java.io.InputStream r3 = r10.openStream()     // Catch:{ IOException -> 0x004b, all -> 0x0047 }
            r1 = r3
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch:{ IOException -> 0x004b, all -> 0x0047 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x004b, all -> 0x0047 }
            java.lang.String r4 = "utf-8"
            r3.<init>(r1, r4)     // Catch:{ IOException -> 0x004b, all -> 0x0047 }
            r5.<init>(r3)     // Catch:{ IOException -> 0x004b, all -> 0x0047 }
            r2 = 1
        L_0x001b:
            r3 = r9
            r4 = r10
            r6 = r2
            r8 = r11
            int r3 = parseLine(r3, r4, r5, r6, r7, r8)     // Catch:{ IOException -> 0x0045 }
            r2 = r3
            if (r3 < 0) goto L_0x0027
            goto L_0x001b
        L_0x0027:
            r5.close()     // Catch:{ IOException -> 0x0031 }
            if (r1 == 0) goto L_0x0030
            r1.close()     // Catch:{ IOException -> 0x0031 }
        L_0x0030:
            goto L_0x0074
        L_0x0031:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
        L_0x0037:
            r3.append(r0)
            r3.append(r2)
            java.lang.String r0 = r3.toString()
            fail(r9, r0)
            goto L_0x0074
        L_0x0045:
            r2 = move-exception
            goto L_0x004e
        L_0x0047:
            r3 = move-exception
            r5 = r2
            r2 = r3
            goto L_0x007a
        L_0x004b:
            r3 = move-exception
            r5 = r2
            r2 = r3
        L_0x004e:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0079 }
            r3.<init>()     // Catch:{ all -> 0x0079 }
            r3.append(r0)     // Catch:{ all -> 0x0079 }
            r3.append(r2)     // Catch:{ all -> 0x0079 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0079 }
            fail(r9, r3)     // Catch:{ all -> 0x0079 }
            if (r5 == 0) goto L_0x0068
            r5.close()     // Catch:{ IOException -> 0x0066 }
            goto L_0x0068
        L_0x0066:
            r2 = move-exception
            goto L_0x006e
        L_0x0068:
            if (r1 == 0) goto L_0x0030
            r1.close()     // Catch:{ IOException -> 0x0066 }
            goto L_0x0030
        L_0x006e:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            goto L_0x0037
        L_0x0074:
            java.util.Iterator r0 = r7.iterator()
            return r0
        L_0x0079:
            r2 = move-exception
        L_0x007a:
            if (r5 == 0) goto L_0x0082
            r5.close()     // Catch:{ IOException -> 0x0080 }
            goto L_0x0082
        L_0x0080:
            r3 = move-exception
            goto L_0x0088
        L_0x0082:
            if (r1 == 0) goto L_0x009b
            r1.close()     // Catch:{ IOException -> 0x0080 }
            goto L_0x009b
        L_0x0088:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r0)
            r4.append(r3)
            java.lang.String r0 = r4.toString()
            fail(r9, r0)
            goto L_0x009c
        L_0x009b:
        L_0x009c:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.utils.ServiceFinder.parse(java.lang.Class, java.net.URL, java.util.Set):java.util.Iterator");
    }

    public static class LazyIterator<T> implements Iterator<T> {
        Enumeration<URL> configs;
        URL currentConfig;
        final ClassLoader loader;
        String nextName;
        Iterator<String> pending;
        final Set<String> returned;
        final Class<T> service;

        private LazyIterator(Class<T> service2, ClassLoader loader2) {
            this.configs = null;
            this.pending = null;
            this.returned = new TreeSet();
            this.nextName = null;
            this.currentConfig = null;
            this.service = service2;
            this.loader = loader2;
        }

        public boolean hasNext() {
            if (this.nextName != null) {
                return true;
            }
            if (this.configs == null) {
                try {
                    String fullName = ServiceFinder.prefix + this.service.getName();
                    ClassLoader classLoader = this.loader;
                    if (classLoader == null) {
                        this.configs = ClassLoader.getSystemResources(fullName);
                    } else {
                        this.configs = classLoader.getResources(fullName);
                    }
                } catch (IOException x) {
                    ServiceFinder.fail(this.service, ": " + x);
                }
            }
            while (true) {
                Iterator<String> it = this.pending;
                if (it != null && it.hasNext()) {
                    this.nextName = this.pending.next();
                    return true;
                } else if (!this.configs.hasMoreElements()) {
                    return false;
                } else {
                    URL nextElement = this.configs.nextElement();
                    this.currentConfig = nextElement;
                    this.pending = ServiceFinder.parse(this.service, nextElement, this.returned);
                }
            }
        }

        public T next() {
            if (hasNext()) {
                String cn = this.nextName;
                this.nextName = null;
                try {
                    return this.service.cast(Class.forName(cn, true, this.loader).newInstance());
                } catch (ClassNotFoundException e) {
                    Class<T> cls = this.service;
                    ServiceFinder.fail(cls, "Provider " + cn + " is specified in " + this.currentConfig + " but not found");
                    return null;
                } catch (Exception x) {
                    Class<T> cls2 = this.service;
                    ServiceFinder.fail(cls2, "Provider " + cn + " is specified in " + this.currentConfig + "but could not be instantiated: " + x, x);
                    return null;
                }
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
