package org.glassfish.tyrus.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.ReflectPermission;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public final class ServiceFinder<T> implements Iterable<T> {
    /* access modifiers changed from: private */
    public static final Logger LOGGER;
    private static final String PREFIX = "META-INF/services/";
    private final ClassLoader classLoader;
    private final boolean ignoreOnClassNotFound;
    private final Class<T> serviceClass;
    private final String serviceName;

    static {
        Logger logger = Logger.getLogger(ServiceFinder.class.getName());
        LOGGER = logger;
        OsgiRegistry osgiRegistry = ReflectionHelper.getOsgiRegistryInstance();
        if (osgiRegistry != null) {
            logger.log(Level.CONFIG, "Running in an OSGi environment");
            osgiRegistry.hookUp();
            return;
        }
        logger.log(Level.CONFIG, "Running in a non-OSGi environment");
    }

    /* access modifiers changed from: private */
    public static Enumeration<URL> getResources(ClassLoader loader, String name) {
        if (loader == null) {
            return getResources(name);
        }
        Enumeration<URL> resources = loader.getResources(name);
        if (resources == null || !resources.hasMoreElements()) {
            return getResources(name);
        }
        return resources;
    }

    private static Enumeration<URL> getResources(String name) {
        if (ServiceFinder.class.getClassLoader() != null) {
            return ServiceFinder.class.getClassLoader().getResources(name);
        }
        return ClassLoader.getSystemResources(name);
    }

    private static ClassLoader _getContextClassLoader() {
        return (ClassLoader) AccessController.doPrivileged(ReflectionHelper.getContextClassLoaderPA());
    }

    public static <T> ServiceFinder<T> find(Class<T> service, ClassLoader loader) {
        return find(service, loader, false);
    }

    public static <T> ServiceFinder<T> find(Class<T> service, ClassLoader loader, boolean ignoreOnClassNotFound2) {
        return new ServiceFinder<>(service, loader, ignoreOnClassNotFound2);
    }

    public static <T> ServiceFinder<T> find(Class<T> service) {
        return find(service, _getContextClassLoader(), false);
    }

    public static <T> ServiceFinder<T> find(Class<T> service, boolean ignoreOnClassNotFound2) {
        return find(service, _getContextClassLoader(), ignoreOnClassNotFound2);
    }

    public static ServiceFinder<?> find(String serviceName2) {
        return new ServiceFinder<>(Object.class, serviceName2, _getContextClassLoader(), false);
    }

    public static void setIteratorProvider(ServiceIteratorProvider sip) {
        ServiceIteratorProvider.setInstance(sip);
    }

    private ServiceFinder(Class<T> service, ClassLoader loader, boolean ignoreOnClassNotFound2) {
        this(service, service.getName(), loader, ignoreOnClassNotFound2);
    }

    private ServiceFinder(Class<T> service, String serviceName2, ClassLoader loader, boolean ignoreOnClassNotFound2) {
        this.serviceClass = service;
        this.serviceName = serviceName2;
        this.classLoader = loader;
        this.ignoreOnClassNotFound = ignoreOnClassNotFound2;
    }

    public Iterator<T> iterator() {
        return ServiceIteratorProvider.getInstance().createIterator(this.serviceClass, this.serviceName, this.classLoader, this.ignoreOnClassNotFound);
    }

    public T[] toArray() {
        List<T> result = new ArrayList<>();
        Iterator it = iterator();
        while (it.hasNext()) {
            result.add(it.next());
        }
        return result.toArray((Object[]) Array.newInstance(this.serviceClass, result.size()));
    }

    public Class<T>[] toClassArray() {
        List<Class<T>> result = new ArrayList<>();
        Iterator<Class<T>> i = ServiceIteratorProvider.getInstance().createClassIterator(this.serviceClass, this.serviceName, this.classLoader, this.ignoreOnClassNotFound);
        while (i.hasNext()) {
            result.add(i.next());
        }
        return (Class[]) result.toArray((Class[]) Array.newInstance(Class.class, result.size()));
    }

    /* access modifiers changed from: private */
    public static void fail(String serviceName2, String msg, Throwable cause) {
        ServiceConfigurationError sce = new ServiceConfigurationError(serviceName2 + ": " + msg);
        sce.initCause(cause);
        throw sce;
    }

    /* access modifiers changed from: private */
    public static void fail(String serviceName2, String msg) {
        throw new ServiceConfigurationError(serviceName2 + ": " + msg);
    }

    private static void fail(String serviceName2, URL u, int line, String msg) {
        fail(serviceName2, u + ":" + line + ": " + msg);
    }

    private static int parseLine(String serviceName2, URL u, BufferedReader r, int lc, List<String> names, Set<String> returned) {
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
                fail(serviceName2, u, lc, LocalizationMessages.ILLEGAL_CONFIG_SYNTAX());
            }
            int cp = ln2.codePointAt(0);
            if (!Character.isJavaIdentifierStart(cp)) {
                fail(serviceName2, u, lc, LocalizationMessages.ILLEGAL_PROVIDER_CLASS_NAME(ln2));
            }
            int i = Character.charCount(cp);
            while (i < n) {
                int cp2 = ln2.codePointAt(i);
                if (!Character.isJavaIdentifierPart(cp2) && cp2 != 46) {
                    fail(serviceName2, u, lc, LocalizationMessages.ILLEGAL_PROVIDER_CLASS_NAME(ln2));
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
    /* JADX WARNING: Removed duplicated region for block: B:24:0x006b A[SYNTHETIC, Splitter:B:24:0x006b] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0073 A[Catch:{ IOException -> 0x006f }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0085 A[SYNTHETIC, Splitter:B:35:0x0085] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x008d A[Catch:{ IOException -> 0x0089 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Iterator<java.lang.String> parse(java.lang.String r10, java.net.URL r11, java.util.Set<java.lang.String> r12) {
        /*
            java.lang.String r0 = ": "
            r1 = 0
            r2 = 0
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            java.net.URLConnection r3 = r11.openConnection()     // Catch:{ IOException -> 0x0054, all -> 0x0050 }
            r9 = r3
            r3 = 0
            r9.setUseCaches(r3)     // Catch:{ IOException -> 0x0054, all -> 0x0050 }
            java.io.InputStream r3 = r9.getInputStream()     // Catch:{ IOException -> 0x0054, all -> 0x0050 }
            r1 = r3
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0054, all -> 0x0050 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x0054, all -> 0x0050 }
            java.lang.String r4 = "utf-8"
            r3.<init>(r1, r4)     // Catch:{ IOException -> 0x0054, all -> 0x0050 }
            r5.<init>(r3)     // Catch:{ IOException -> 0x0054, all -> 0x0050 }
            r2 = 1
        L_0x0024:
            r3 = r10
            r4 = r11
            r6 = r2
            r8 = r12
            int r3 = parseLine(r3, r4, r5, r6, r7, r8)     // Catch:{ IOException -> 0x004e }
            r2 = r3
            if (r3 < 0) goto L_0x0030
            goto L_0x0024
        L_0x0030:
            r5.close()     // Catch:{ IOException -> 0x003a }
            if (r1 == 0) goto L_0x0039
            r1.close()     // Catch:{ IOException -> 0x003a }
        L_0x0039:
            goto L_0x007d
        L_0x003a:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
        L_0x0040:
            r3.append(r0)
            r3.append(r2)
            java.lang.String r0 = r3.toString()
            fail(r10, r0)
            goto L_0x007d
        L_0x004e:
            r2 = move-exception
            goto L_0x0057
        L_0x0050:
            r3 = move-exception
            r5 = r2
            r2 = r3
            goto L_0x0083
        L_0x0054:
            r3 = move-exception
            r5 = r2
            r2 = r3
        L_0x0057:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0082 }
            r3.<init>()     // Catch:{ all -> 0x0082 }
            r3.append(r0)     // Catch:{ all -> 0x0082 }
            r3.append(r2)     // Catch:{ all -> 0x0082 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0082 }
            fail(r10, r3)     // Catch:{ all -> 0x0082 }
            if (r5 == 0) goto L_0x0071
            r5.close()     // Catch:{ IOException -> 0x006f }
            goto L_0x0071
        L_0x006f:
            r2 = move-exception
            goto L_0x0077
        L_0x0071:
            if (r1 == 0) goto L_0x0039
            r1.close()     // Catch:{ IOException -> 0x006f }
            goto L_0x0039
        L_0x0077:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            goto L_0x0040
        L_0x007d:
            java.util.Iterator r0 = r7.iterator()
            return r0
        L_0x0082:
            r2 = move-exception
        L_0x0083:
            if (r5 == 0) goto L_0x008b
            r5.close()     // Catch:{ IOException -> 0x0089 }
            goto L_0x008b
        L_0x0089:
            r3 = move-exception
            goto L_0x0091
        L_0x008b:
            if (r1 == 0) goto L_0x00a4
            r1.close()     // Catch:{ IOException -> 0x0089 }
            goto L_0x00a4
        L_0x0091:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r0)
            r4.append(r3)
            java.lang.String r0 = r4.toString()
            fail(r10, r0)
            goto L_0x00a5
        L_0x00a4:
        L_0x00a5:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.tyrus.core.ServiceFinder.parse(java.lang.String, java.net.URL, java.util.Set):java.util.Iterator");
    }

    public static class AbstractLazyIterator<T> {
        Enumeration<URL> configs;
        final boolean ignoreOnClassNotFound;
        final ClassLoader loader;
        String nextName;
        Iterator<String> pending;
        Set<String> returned;
        final Class<T> service;
        final String serviceName;

        private AbstractLazyIterator(Class<T> service2, String serviceName2, ClassLoader loader2, boolean ignoreOnClassNotFound2) {
            this.configs = null;
            this.pending = null;
            this.returned = new TreeSet();
            this.service = service2;
            this.serviceName = serviceName2;
            this.loader = loader2;
            this.ignoreOnClassNotFound = ignoreOnClassNotFound2;
        }

        /* access modifiers changed from: protected */
        public final void setConfigs() {
            if (this.configs == null) {
                try {
                    this.configs = ServiceFinder.getResources(this.loader, ServiceFinder.PREFIX + this.serviceName);
                } catch (IOException x) {
                    String str = this.serviceName;
                    ServiceFinder.fail(str, ": " + x);
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0019, code lost:
            r0 = r9.pending.next();
            r9.nextName = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0025, code lost:
            if (r9.ignoreOnClassNotFound == false) goto L_0x0009;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            java.security.AccessController.doPrivileged(org.glassfish.tyrus.core.ReflectionHelper.classForNameWithExceptionPEA(r0, r9.loader));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0031, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0032, code lost:
            r2 = r0.getException();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0038, code lost:
            if ((r2 instanceof java.lang.ClassNotFoundException) != false) goto L_0x003a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x003a, code lost:
            handleClassNotFoundException();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0041, code lost:
            if ((r2 instanceof java.lang.NoClassDefFoundError) != false) goto L_0x0043;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0043, code lost:
            r3 = org.glassfish.tyrus.core.ServiceFinder.access$500();
            r5 = java.util.logging.Level.CONFIG;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x004d, code lost:
            if (r3.isLoggable(r5) != false) goto L_0x004f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x004f, code lost:
            org.glassfish.tyrus.core.ServiceFinder.access$500().log(r5, org.glassfish.tyrus.core.l10n.LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(r2.getLocalizedMessage(), r9.nextName, r9.service));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0062, code lost:
            r9.nextName = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0067, code lost:
            if ((r2 instanceof java.lang.ClassFormatError) != false) goto L_0x0069;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0069, code lost:
            r3 = org.glassfish.tyrus.core.ServiceFinder.access$500();
            r5 = java.util.logging.Level.CONFIG;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0073, code lost:
            if (r3.isLoggable(r5) != false) goto L_0x0075;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0075, code lost:
            org.glassfish.tyrus.core.ServiceFinder.access$500().log(r5, org.glassfish.tyrus.core.l10n.LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(r2.getLocalizedMessage(), r9.nextName, r9.service));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0088, code lost:
            r9.nextName = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x008d, code lost:
            if ((r2 instanceof java.lang.RuntimeException) != false) goto L_0x008f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0092, code lost:
            throw ((java.lang.RuntimeException) r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x0098, code lost:
            throw new java.lang.IllegalStateException(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x009a, code lost:
            handleClassNotFoundException();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean hasNext() {
            /*
                r9 = this;
                java.lang.String r0 = r9.nextName
                r1 = 1
                if (r0 == 0) goto L_0x0006
                return r1
            L_0x0006:
                r9.setConfigs()
            L_0x0009:
                java.lang.String r0 = r9.nextName
                if (r0 != 0) goto L_0x00bd
            L_0x000d:
                java.util.Iterator<java.lang.String> r0 = r9.pending
                if (r0 == 0) goto L_0x009f
                boolean r0 = r0.hasNext()
                if (r0 != 0) goto L_0x0019
                goto L_0x009f
            L_0x0019:
                java.util.Iterator<java.lang.String> r0 = r9.pending
                java.lang.Object r0 = r0.next()
                java.lang.String r0 = (java.lang.String) r0
                r9.nextName = r0
                boolean r2 = r9.ignoreOnClassNotFound
                if (r2 == 0) goto L_0x0009
                java.lang.ClassLoader r2 = r9.loader     // Catch:{ ClassNotFoundException -> 0x0099, PrivilegedActionException -> 0x0031 }
                java.security.PrivilegedExceptionAction r0 = org.glassfish.tyrus.core.ReflectionHelper.classForNameWithExceptionPEA(r0, r2)     // Catch:{ ClassNotFoundException -> 0x0099, PrivilegedActionException -> 0x0031 }
                java.security.AccessController.doPrivileged(r0)     // Catch:{ ClassNotFoundException -> 0x0099, PrivilegedActionException -> 0x0031 }
                goto L_0x009d
            L_0x0031:
                r0 = move-exception
                java.lang.Exception r2 = r0.getException()
                boolean r3 = r2 instanceof java.lang.ClassNotFoundException
                if (r3 == 0) goto L_0x003e
                r9.handleClassNotFoundException()
                goto L_0x009d
            L_0x003e:
                boolean r3 = r2 instanceof java.lang.NoClassDefFoundError
                r4 = 0
                if (r3 == 0) goto L_0x0065
                java.util.logging.Logger r3 = org.glassfish.tyrus.core.ServiceFinder.LOGGER
                java.util.logging.Level r5 = java.util.logging.Level.CONFIG
                boolean r3 = r3.isLoggable(r5)
                if (r3 == 0) goto L_0x0062
                java.util.logging.Logger r3 = org.glassfish.tyrus.core.ServiceFinder.LOGGER
                java.lang.String r6 = r2.getLocalizedMessage()
                java.lang.String r7 = r9.nextName
                java.lang.Class<T> r8 = r9.service
                java.lang.String r6 = org.glassfish.tyrus.core.l10n.LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(r6, r7, r8)
                r3.log(r5, r6)
            L_0x0062:
                r9.nextName = r4
                goto L_0x009d
            L_0x0065:
                boolean r3 = r2 instanceof java.lang.ClassFormatError
                if (r3 == 0) goto L_0x008b
                java.util.logging.Logger r3 = org.glassfish.tyrus.core.ServiceFinder.LOGGER
                java.util.logging.Level r5 = java.util.logging.Level.CONFIG
                boolean r3 = r3.isLoggable(r5)
                if (r3 == 0) goto L_0x0088
                java.util.logging.Logger r3 = org.glassfish.tyrus.core.ServiceFinder.LOGGER
                java.lang.String r6 = r2.getLocalizedMessage()
                java.lang.String r7 = r9.nextName
                java.lang.Class<T> r8 = r9.service
                java.lang.String r6 = org.glassfish.tyrus.core.l10n.LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(r6, r7, r8)
                r3.log(r5, r6)
            L_0x0088:
                r9.nextName = r4
                goto L_0x009d
            L_0x008b:
                boolean r1 = r2 instanceof java.lang.RuntimeException
                if (r1 == 0) goto L_0x0093
                r1 = r2
                java.lang.RuntimeException r1 = (java.lang.RuntimeException) r1
                throw r1
            L_0x0093:
                java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
                r1.<init>(r2)
                throw r1
            L_0x0099:
                r0 = move-exception
                r9.handleClassNotFoundException()
            L_0x009d:
                goto L_0x0009
            L_0x009f:
                java.util.Enumeration<java.net.URL> r0 = r9.configs
                boolean r0 = r0.hasMoreElements()
                if (r0 != 0) goto L_0x00a9
                r0 = 0
                return r0
            L_0x00a9:
                java.lang.String r0 = r9.serviceName
                java.util.Enumeration<java.net.URL> r2 = r9.configs
                java.lang.Object r2 = r2.nextElement()
                java.net.URL r2 = (java.net.URL) r2
                java.util.Set<java.lang.String> r3 = r9.returned
                java.util.Iterator r0 = org.glassfish.tyrus.core.ServiceFinder.parse(r0, r2, r3)
                r9.pending = r0
                goto L_0x000d
            L_0x00bd:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.glassfish.tyrus.core.ServiceFinder.AbstractLazyIterator.hasNext():boolean");
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void handleClassNotFoundException() {
            Logger access$500 = ServiceFinder.LOGGER;
            Level level = Level.CONFIG;
            if (access$500.isLoggable(level)) {
                ServiceFinder.LOGGER.log(level, LocalizationMessages.PROVIDER_NOT_FOUND(this.nextName, this.service));
            }
            this.nextName = null;
        }
    }

    public static final class LazyClassIterator<T> extends AbstractLazyIterator<T> implements Iterator<Class<T>> {
        private LazyClassIterator(Class<T> service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
            super(service, serviceName, loader, ignoreOnClassNotFound);
        }

        public Class<T> next() {
            if (hasNext()) {
                String cn = this.nextName;
                this.nextName = null;
                try {
                    Class<T> tClass = (Class) AccessController.doPrivileged(ReflectionHelper.classForNameWithExceptionPEA(cn, this.loader));
                    Logger access$500 = ServiceFinder.LOGGER;
                    Level level = Level.FINEST;
                    if (access$500.isLoggable(level)) {
                        Logger access$5002 = ServiceFinder.LOGGER;
                        access$5002.log(level, "Loading next class: " + tClass.getName());
                    }
                    return tClass;
                } catch (ClassNotFoundException e) {
                    ServiceFinder.fail(this.serviceName, LocalizationMessages.PROVIDER_NOT_FOUND(cn, this.service));
                    return null;
                } catch (PrivilegedActionException pae) {
                    Throwable thrown = pae.getCause();
                    if (thrown instanceof ClassNotFoundException) {
                        ServiceFinder.fail(this.serviceName, LocalizationMessages.PROVIDER_NOT_FOUND(cn, this.service));
                    } else if (thrown instanceof NoClassDefFoundError) {
                        ServiceFinder.fail(this.serviceName, LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(thrown.getLocalizedMessage(), cn, this.service));
                    } else if (thrown instanceof ClassFormatError) {
                        ServiceFinder.fail(this.serviceName, LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(thrown.getLocalizedMessage(), cn, this.service));
                    } else {
                        ServiceFinder.fail(this.serviceName, LocalizationMessages.PROVIDER_CLASS_COULD_NOT_BE_LOADED(cn, this.service, thrown.getLocalizedMessage()), thrown);
                    }
                    return null;
                }
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    public static final class LazyObjectIterator<T> extends AbstractLazyIterator<T> implements Iterator<T> {
        private T t;

        private LazyObjectIterator(Class<T> service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
            super(service, serviceName, loader, ignoreOnClassNotFound);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0019, code lost:
            r0 = r9.pending.next();
            r9.nextName = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
            r9.t = r9.service.cast(((java.lang.Class) java.security.AccessController.doPrivileged(org.glassfish.tyrus.core.ReflectionHelper.classForNameWithExceptionPEA(r0, r9.loader))).newInstance());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x003e, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x003f, code lost:
            r3 = r0.getCause();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0045, code lost:
            if ((r3 instanceof java.lang.ClassNotFoundException) != false) goto L_0x0047;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0047, code lost:
            handleClassNotFoundException();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x004e, code lost:
            if ((r3 instanceof java.lang.ClassFormatError) != false) goto L_0x0050;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0052, code lost:
            if (r9.ignoreOnClassNotFound != false) goto L_0x0054;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0054, code lost:
            r4 = org.glassfish.tyrus.core.ServiceFinder.access$500();
            r5 = java.util.logging.Level.CONFIG;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x005e, code lost:
            if (r4.isLoggable(r5) != false) goto L_0x0060;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0060, code lost:
            org.glassfish.tyrus.core.ServiceFinder.access$500().log(r5, org.glassfish.tyrus.core.l10n.LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(r3.getLocalizedMessage(), r9.nextName, r9.service));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0073, code lost:
            r9.nextName = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0077, code lost:
            org.glassfish.tyrus.core.ServiceFinder.access$700(r9.serviceName, org.glassfish.tyrus.core.l10n.LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(r3.getLocalizedMessage(), r9.nextName, r9.service), r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0089, code lost:
            org.glassfish.tyrus.core.ServiceFinder.access$700(r9.serviceName, org.glassfish.tyrus.core.l10n.LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(r9.nextName, r9.service, r3.getLocalizedMessage()), r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x009b, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x009e, code lost:
            if (r9.ignoreOnClassNotFound != false) goto L_0x00a0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x00a0, code lost:
            r3 = org.glassfish.tyrus.core.ServiceFinder.access$500();
            r4 = java.util.logging.Level.CONFIG;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00aa, code lost:
            if (r3.isLoggable(r4) != false) goto L_0x00ac;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x00ac, code lost:
            org.glassfish.tyrus.core.ServiceFinder.access$500().log(r4, org.glassfish.tyrus.core.l10n.LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(r0.getLocalizedMessage(), r9.nextName, r9.service));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x00bf, code lost:
            r9.nextName = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x00c2, code lost:
            org.glassfish.tyrus.core.ServiceFinder.access$700(r9.serviceName, org.glassfish.tyrus.core.l10n.LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(r0.getLocalizedMessage(), r9.nextName, r9.service), r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x00d5, code lost:
            handleClassNotFoundException();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x00d9, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x00da, code lost:
            org.glassfish.tyrus.core.ServiceFinder.access$700(r9.serviceName, org.glassfish.tyrus.core.l10n.LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(r9.nextName, r9.service, r0.getLocalizedMessage()), r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ed, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x00f0, code lost:
            if (r9.ignoreOnClassNotFound != false) goto L_0x00f2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x00f2, code lost:
            r3 = org.glassfish.tyrus.core.ServiceFinder.access$500();
            r4 = java.util.logging.Level.CONFIG;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x00fc, code lost:
            if (r3.isLoggable(r4) != false) goto L_0x00fe;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x00fe, code lost:
            org.glassfish.tyrus.core.ServiceFinder.access$500().log(r4, org.glassfish.tyrus.core.l10n.LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(r9.nextName, r9.service, r0.getLocalizedMessage()));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x0111, code lost:
            r9.nextName = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x0114, code lost:
            org.glassfish.tyrus.core.ServiceFinder.access$700(r9.serviceName, org.glassfish.tyrus.core.l10n.LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(r9.nextName, r9.service, r0.getLocalizedMessage()), r0);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean hasNext() {
            /*
                r9 = this;
                java.lang.String r0 = r9.nextName
                r1 = 1
                if (r0 == 0) goto L_0x0006
                return r1
            L_0x0006:
                r9.setConfigs()
            L_0x0009:
                java.lang.String r0 = r9.nextName
                if (r0 != 0) goto L_0x0144
            L_0x000d:
                java.util.Iterator<java.lang.String> r0 = r9.pending
                if (r0 == 0) goto L_0x0126
                boolean r0 = r0.hasNext()
                if (r0 != 0) goto L_0x0019
                goto L_0x0126
            L_0x0019:
                java.util.Iterator<java.lang.String> r0 = r9.pending
                java.lang.Object r0 = r0.next()
                java.lang.String r0 = (java.lang.String) r0
                r9.nextName = r0
                r2 = 0
                java.lang.Class<T> r3 = r9.service     // Catch:{ InstantiationException -> 0x00ed, IllegalAccessException -> 0x00d9, ClassNotFoundException -> 0x00d4, NoClassDefFoundError -> 0x009b, PrivilegedActionException -> 0x003e }
                java.lang.ClassLoader r4 = r9.loader     // Catch:{ InstantiationException -> 0x00ed, IllegalAccessException -> 0x00d9, ClassNotFoundException -> 0x00d4, NoClassDefFoundError -> 0x009b, PrivilegedActionException -> 0x003e }
                java.security.PrivilegedExceptionAction r0 = org.glassfish.tyrus.core.ReflectionHelper.classForNameWithExceptionPEA(r0, r4)     // Catch:{ InstantiationException -> 0x00ed, IllegalAccessException -> 0x00d9, ClassNotFoundException -> 0x00d4, NoClassDefFoundError -> 0x009b, PrivilegedActionException -> 0x003e }
                java.lang.Object r0 = java.security.AccessController.doPrivileged(r0)     // Catch:{ InstantiationException -> 0x00ed, IllegalAccessException -> 0x00d9, ClassNotFoundException -> 0x00d4, NoClassDefFoundError -> 0x009b, PrivilegedActionException -> 0x003e }
                java.lang.Class r0 = (java.lang.Class) r0     // Catch:{ InstantiationException -> 0x00ed, IllegalAccessException -> 0x00d9, ClassNotFoundException -> 0x00d4, NoClassDefFoundError -> 0x009b, PrivilegedActionException -> 0x003e }
                java.lang.Object r0 = r0.newInstance()     // Catch:{ InstantiationException -> 0x00ed, IllegalAccessException -> 0x00d9, ClassNotFoundException -> 0x00d4, NoClassDefFoundError -> 0x009b, PrivilegedActionException -> 0x003e }
                java.lang.Object r0 = r3.cast(r0)     // Catch:{ InstantiationException -> 0x00ed, IllegalAccessException -> 0x00d9, ClassNotFoundException -> 0x00d4, NoClassDefFoundError -> 0x009b, PrivilegedActionException -> 0x003e }
                r9.t = r0     // Catch:{ InstantiationException -> 0x00ed, IllegalAccessException -> 0x00d9, ClassNotFoundException -> 0x00d4, NoClassDefFoundError -> 0x009b, PrivilegedActionException -> 0x003e }
                goto L_0x00eb
            L_0x003e:
                r0 = move-exception
                java.lang.Throwable r3 = r0.getCause()
                boolean r4 = r3 instanceof java.lang.ClassNotFoundException
                if (r4 == 0) goto L_0x004c
                r9.handleClassNotFoundException()
                goto L_0x00eb
            L_0x004c:
                boolean r4 = r3 instanceof java.lang.ClassFormatError
                if (r4 == 0) goto L_0x0089
                boolean r4 = r9.ignoreOnClassNotFound
                if (r4 == 0) goto L_0x0077
                java.util.logging.Logger r4 = org.glassfish.tyrus.core.ServiceFinder.LOGGER
                java.util.logging.Level r5 = java.util.logging.Level.CONFIG
                boolean r4 = r4.isLoggable(r5)
                if (r4 == 0) goto L_0x0073
                java.util.logging.Logger r4 = org.glassfish.tyrus.core.ServiceFinder.LOGGER
                java.lang.String r6 = r3.getLocalizedMessage()
                java.lang.String r7 = r9.nextName
                java.lang.Class<T> r8 = r9.service
                java.lang.String r6 = org.glassfish.tyrus.core.l10n.LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(r6, r7, r8)
                r4.log(r5, r6)
            L_0x0073:
                r9.nextName = r2
                goto L_0x00eb
            L_0x0077:
                java.lang.String r2 = r9.serviceName
                java.lang.String r4 = r3.getLocalizedMessage()
                java.lang.String r5 = r9.nextName
                java.lang.Class<T> r6 = r9.service
                java.lang.String r4 = org.glassfish.tyrus.core.l10n.LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(r4, r5, r6)
                org.glassfish.tyrus.core.ServiceFinder.fail(r2, r4, r3)
                goto L_0x00eb
            L_0x0089:
                java.lang.String r2 = r9.serviceName
                java.lang.String r4 = r9.nextName
                java.lang.Class<T> r5 = r9.service
                java.lang.String r6 = r3.getLocalizedMessage()
                java.lang.String r4 = org.glassfish.tyrus.core.l10n.LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(r4, r5, r6)
                org.glassfish.tyrus.core.ServiceFinder.fail(r2, r4, r3)
                goto L_0x00eb
            L_0x009b:
                r0 = move-exception
                boolean r3 = r9.ignoreOnClassNotFound
                if (r3 == 0) goto L_0x00c2
                java.util.logging.Logger r3 = org.glassfish.tyrus.core.ServiceFinder.LOGGER
                java.util.logging.Level r4 = java.util.logging.Level.CONFIG
                boolean r3 = r3.isLoggable(r4)
                if (r3 == 0) goto L_0x00bf
                java.util.logging.Logger r3 = org.glassfish.tyrus.core.ServiceFinder.LOGGER
                java.lang.String r5 = r0.getLocalizedMessage()
                java.lang.String r6 = r9.nextName
                java.lang.Class<T> r7 = r9.service
                java.lang.String r5 = org.glassfish.tyrus.core.l10n.LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(r5, r6, r7)
                r3.log(r4, r5)
            L_0x00bf:
                r9.nextName = r2
                goto L_0x00eb
            L_0x00c2:
                java.lang.String r2 = r9.serviceName
                java.lang.String r3 = r0.getLocalizedMessage()
                java.lang.String r4 = r9.nextName
                java.lang.Class<T> r5 = r9.service
                java.lang.String r3 = org.glassfish.tyrus.core.l10n.LocalizationMessages.DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(r3, r4, r5)
                org.glassfish.tyrus.core.ServiceFinder.fail(r2, r3, r0)
                goto L_0x00eb
            L_0x00d4:
                r0 = move-exception
                r9.handleClassNotFoundException()
                goto L_0x00eb
            L_0x00d9:
                r0 = move-exception
                java.lang.String r2 = r9.serviceName
                java.lang.String r3 = r9.nextName
                java.lang.Class<T> r4 = r9.service
                java.lang.String r5 = r0.getLocalizedMessage()
                java.lang.String r3 = org.glassfish.tyrus.core.l10n.LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(r3, r4, r5)
                org.glassfish.tyrus.core.ServiceFinder.fail(r2, r3, r0)
            L_0x00eb:
                goto L_0x0009
            L_0x00ed:
                r0 = move-exception
                boolean r3 = r9.ignoreOnClassNotFound
                if (r3 == 0) goto L_0x0114
                java.util.logging.Logger r3 = org.glassfish.tyrus.core.ServiceFinder.LOGGER
                java.util.logging.Level r4 = java.util.logging.Level.CONFIG
                boolean r3 = r3.isLoggable(r4)
                if (r3 == 0) goto L_0x0111
                java.util.logging.Logger r3 = org.glassfish.tyrus.core.ServiceFinder.LOGGER
                java.lang.String r5 = r9.nextName
                java.lang.Class<T> r6 = r9.service
                java.lang.String r7 = r0.getLocalizedMessage()
                java.lang.String r5 = org.glassfish.tyrus.core.l10n.LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(r5, r6, r7)
                r3.log(r4, r5)
            L_0x0111:
                r9.nextName = r2
                goto L_0x00eb
            L_0x0114:
                java.lang.String r2 = r9.serviceName
                java.lang.String r3 = r9.nextName
                java.lang.Class<T> r4 = r9.service
                java.lang.String r5 = r0.getLocalizedMessage()
                java.lang.String r3 = org.glassfish.tyrus.core.l10n.LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(r3, r4, r5)
                org.glassfish.tyrus.core.ServiceFinder.fail(r2, r3, r0)
                goto L_0x00eb
            L_0x0126:
                java.util.Enumeration<java.net.URL> r0 = r9.configs
                boolean r0 = r0.hasMoreElements()
                if (r0 != 0) goto L_0x0130
                r0 = 0
                return r0
            L_0x0130:
                java.lang.String r0 = r9.serviceName
                java.util.Enumeration<java.net.URL> r2 = r9.configs
                java.lang.Object r2 = r2.nextElement()
                java.net.URL r2 = (java.net.URL) r2
                java.util.Set<java.lang.String> r3 = r9.returned
                java.util.Iterator r0 = org.glassfish.tyrus.core.ServiceFinder.parse(r0, r2, r3)
                r9.pending = r0
                goto L_0x000d
            L_0x0144:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.glassfish.tyrus.core.ServiceFinder.LazyObjectIterator.hasNext():boolean");
        }

        public T next() {
            if (hasNext()) {
                this.nextName = null;
                Logger access$500 = ServiceFinder.LOGGER;
                Level level = Level.FINEST;
                if (access$500.isLoggable(level)) {
                    Logger access$5002 = ServiceFinder.LOGGER;
                    access$5002.log(level, "Loading next object: " + this.t.getClass().getName());
                }
                return this.t;
            }
            throw new NoSuchElementException();
        }

        private void handleClassNotFoundException() {
            if (this.ignoreOnClassNotFound) {
                Logger access$500 = ServiceFinder.LOGGER;
                Level level = Level.CONFIG;
                if (access$500.isLoggable(level)) {
                    ServiceFinder.LOGGER.log(level, LocalizationMessages.PROVIDER_NOT_FOUND(this.nextName, this.service));
                }
                this.nextName = null;
                return;
            }
            ServiceFinder.fail(this.serviceName, LocalizationMessages.PROVIDER_NOT_FOUND(this.nextName, this.service));
        }
    }

    public static abstract class ServiceIteratorProvider {
        private static volatile ServiceIteratorProvider sip;
        private static final Object sipLock = new Object();

        public abstract <T> Iterator<Class<T>> createClassIterator(Class<T> cls, String str, ClassLoader classLoader, boolean z);

        public abstract <T> Iterator<T> createIterator(Class<T> cls, String str, ClassLoader classLoader, boolean z);

        /* access modifiers changed from: private */
        public static ServiceIteratorProvider getInstance() {
            ServiceIteratorProvider result = sip;
            if (result == null) {
                synchronized (sipLock) {
                    result = sip;
                    if (result == null) {
                        DefaultServiceIteratorProvider defaultServiceIteratorProvider = new DefaultServiceIteratorProvider();
                        result = defaultServiceIteratorProvider;
                        sip = defaultServiceIteratorProvider;
                    }
                }
            }
            return result;
        }

        /* access modifiers changed from: private */
        public static void setInstance(ServiceIteratorProvider sip2) {
            SecurityManager security = System.getSecurityManager();
            if (security != null) {
                security.checkPermission(new ReflectPermission("suppressAccessChecks"));
            }
            synchronized (sipLock) {
                sip = sip2;
            }
        }
    }

    public static final class DefaultServiceIteratorProvider extends ServiceIteratorProvider {
        public <T> Iterator<T> createIterator(Class<T> service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
            return new LazyObjectIterator(service, serviceName, loader, ignoreOnClassNotFound);
        }

        public <T> Iterator<Class<T>> createClassIterator(Class<T> service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
            return new LazyClassIterator(service, serviceName, loader, ignoreOnClassNotFound);
        }
    }
}
