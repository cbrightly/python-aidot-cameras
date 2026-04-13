package org.glassfish.tyrus.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.ServiceFinder;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.a;
import org.osgi.framework.b;
import org.osgi.framework.c;
import org.slf4j.e;

public final class OsgiRegistry implements a {
    private static final String CoreBundleSymbolicNAME = "org.glassfish.jersey.core.jersey-common";
    /* access modifiers changed from: private */
    public static final Logger LOGGER = Logger.getLogger(OsgiRegistry.class.getName());
    private static OsgiRegistry instance;
    private final BundleContext bundleContext;
    private Map<String, Bundle> classToBundleMapping = new HashMap();
    private final Map<Long, Map<String, Callable<List<Class<?>>>>> factories = new HashMap();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public static synchronized OsgiRegistry getInstance() {
        OsgiRegistry osgiRegistry;
        BundleContext context;
        synchronized (OsgiRegistry.class) {
            if (instance == null && (ReflectionHelper.class.getClassLoader() instanceof b) && (context = c.a(OsgiRegistry.class).g()) != null) {
                instance = new OsgiRegistry(context);
            }
            osgiRegistry = instance;
        }
        return osgiRegistry;
    }

    public final class OsgiServiceFinder extends ServiceFinder.ServiceIteratorProvider {
        final ServiceFinder.ServiceIteratorProvider defaultIterator;

        private OsgiServiceFinder() {
            this.defaultIterator = new ServiceFinder.DefaultServiceIteratorProvider();
        }

        public <T> Iterator<T> createIterator(Class<T> serviceClass, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
            List<Class<?>> providerClasses = OsgiRegistry.this.locateAllProviders(serviceName);
            return !providerClasses.isEmpty() ? new Iterator<T>(providerClasses, serviceName, serviceClass) {
                Iterator<Class<?>> it;
                final /* synthetic */ List val$providerClasses;
                final /* synthetic */ Class val$serviceClass;
                final /* synthetic */ String val$serviceName;

                {
                    this.val$providerClasses = r2;
                    this.val$serviceName = r3;
                    this.val$serviceClass = r4;
                    this.it = r2.iterator();
                }

                public boolean hasNext() {
                    return this.it.hasNext();
                }

                public T next() {
                    Class<T> nextClass = this.it.next();
                    try {
                        return nextClass.newInstance();
                    } catch (Exception ex) {
                        ServiceConfigurationError sce = new ServiceConfigurationError(this.val$serviceName + ": " + LocalizationMessages.PROVIDER_COULD_NOT_BE_CREATED(nextClass.getName(), this.val$serviceClass, ex.getLocalizedMessage()));
                        sce.initCause(ex);
                        throw sce;
                    }
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }
            } : this.defaultIterator.createIterator(serviceClass, serviceName, loader, ignoreOnClassNotFound);
        }

        public <T> Iterator<Class<T>> createClassIterator(Class<T> service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
            List<Class<?>> providerClasses = OsgiRegistry.this.locateAllProviders(serviceName);
            return !providerClasses.isEmpty() ? new Iterator<Class<T>>(providerClasses) {
                Iterator<Class<?>> it;
                final /* synthetic */ List val$providerClasses;

                {
                    this.val$providerClasses = r2;
                    this.it = r2.iterator();
                }

                public boolean hasNext() {
                    return this.it.hasNext();
                }

                public Class<T> next() {
                    return this.it.next();
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }
            } : this.defaultIterator.createClassIterator(service, serviceName, loader, ignoreOnClassNotFound);
        }
    }

    public static class BundleSpiProvidersLoader implements Callable<List<Class<?>>> {
        private final Bundle bundle;
        private final String spi;
        private final URI spiRegistryUri;

        BundleSpiProvidersLoader(String spi2, URI spiRegistryUri2, Bundle bundle2) {
            this.spi = spi2;
            this.spiRegistryUri = spiRegistryUri2;
            this.bundle = bundle2;
        }

        public List<Class<?>> call() {
            try {
                Logger access$100 = OsgiRegistry.LOGGER;
                Level level = Level.FINEST;
                if (access$100.isLoggable(level)) {
                    OsgiRegistry.LOGGER.log(level, "Loading providers for SPI: {0}", this.spi);
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(this.spiRegistryUri.toURL().openStream(), "UTF-8"));
                List<Class<?>> providerClasses = new ArrayList<>();
                while (true) {
                    String readLine = br.readLine();
                    String providerClassName = readLine;
                    if (readLine == null) {
                        br.close();
                        return providerClasses;
                    } else if (providerClassName.trim().length() != 0) {
                        Logger access$1002 = OsgiRegistry.LOGGER;
                        Level level2 = Level.FINEST;
                        if (access$1002.isLoggable(level2)) {
                            OsgiRegistry.LOGGER.log(level2, "SPI provider: {0}", providerClassName);
                        }
                        providerClasses.add(this.bundle.x(providerClassName));
                    }
                }
            } catch (Exception e) {
                OsgiRegistry.LOGGER.log(Level.WARNING, LocalizationMessages.EXCEPTION_CAUGHT_WHILE_LOADING_SPI_PROVIDERS(), e);
                throw e;
            } catch (Error e2) {
                OsgiRegistry.LOGGER.log(Level.WARNING, LocalizationMessages.ERROR_CAUGHT_WHILE_LOADING_SPI_PROVIDERS(), e2);
                throw e2;
            }
        }

        public String toString() {
            return this.spiRegistryUri.toString();
        }

        public int hashCode() {
            return this.spiRegistryUri.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj instanceof BundleSpiProvidersLoader) {
                return this.spiRegistryUri.equals(((BundleSpiProvidersLoader) obj).spiRegistryUri);
            }
            return false;
        }
    }

    public void bundleChanged(BundleEvent event) {
        if (event.getType() == 32) {
            register(event.getBundle());
        } else if (event.getType() == 64 || event.getType() == 16) {
            Bundle unregisteredBundle = event.getBundle();
            this.lock.writeLock().lock();
            try {
                this.factories.remove(Long.valueOf(unregisteredBundle.z()));
                if (unregisteredBundle.t().equals(CoreBundleSymbolicNAME)) {
                    this.bundleContext.a(this);
                    this.factories.clear();
                }
            } finally {
                this.lock.writeLock().unlock();
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v0, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v10, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v13, resolved type: boolean} */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x010d, code lost:
        r0 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x010d A[ExcHandler: all (th java.lang.Throwable), PHI: r6 
      PHI: (r6v8 'jarInputStream' java.util.jar.JarInputStream) = (r6v5 'jarInputStream' java.util.jar.JarInputStream), (r6v9 'jarInputStream' java.util.jar.JarInputStream), (r6v9 'jarInputStream' java.util.jar.JarInputStream) binds: [B:19:0x00bf, B:29:0x00ea, B:30:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:19:0x00bf] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0117 A[SYNTHETIC, Splitter:B:43:0x0117] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0126 A[SYNTHETIC, Splitter:B:51:0x0126] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x012c A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Enumeration<java.net.URL> getPackageResources(java.lang.String r20, java.lang.ClassLoader r21) {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            java.util.LinkedList r0 = new java.util.LinkedList
            r0.<init>()
            r3 = r0
            java.util.Map<java.lang.String, org.osgi.framework.Bundle> r0 = r1.classToBundleMapping
            r0.clear()
            org.osgi.framework.BundleContext r0 = r1.bundleContext
            org.osgi.framework.Bundle[] r4 = r0.b()
            int r5 = r4.length
            r6 = 0
            r7 = r6
        L_0x0018:
            if (r7 >= r5) goto L_0x0142
            r8 = r4[r7]
            r0 = 2
            java.lang.String[] r9 = new java.lang.String[r0]
            r9[r6] = r2
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "WEB-INF/classes/"
            r10.append(r11)
            r10.append(r2)
            java.lang.String r10 = r10.toString()
            r11 = 1
            r9[r11] = r10
            r10 = r6
        L_0x0036:
            java.lang.String r12 = ""
            java.lang.String r14 = ".class"
            if (r10 >= r0) goto L_0x009f
            r0 = r9[r10]
            java.lang.String r11 = "*"
            java.util.Enumeration r11 = r8.v(r0, r11, r6)
            if (r11 == 0) goto L_0x0093
        L_0x0047:
            boolean r16 = r11.hasMoreElements()
            if (r16 == 0) goto L_0x008e
            java.lang.Object r16 = r11.nextElement()
            r6 = r16
            java.net.URL r6 = (java.net.URL) r6
            java.lang.String r13 = r6.getPath()
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r2)
            r18 = r0
            r17 = r4
            r4 = 47
            int r0 = r13.lastIndexOf(r4)
            java.lang.String r0 = r13.substring(r0)
            r15.append(r0)
            java.lang.String r0 = r15.toString()
            r15 = 46
            java.lang.String r0 = r0.replace(r4, r15)
            java.lang.String r0 = r0.replace(r14, r12)
            java.util.Map<java.lang.String, org.osgi.framework.Bundle> r4 = r1.classToBundleMapping
            r4.put(r0, r8)
            r3.add(r6)
            r4 = r17
            r0 = r18
            r6 = 0
            goto L_0x0047
        L_0x008e:
            r18 = r0
            r17 = r4
            goto L_0x0097
        L_0x0093:
            r18 = r0
            r17 = r4
        L_0x0097:
            int r10 = r10 + 1
            r4 = r17
            r0 = 2
            r6 = 0
            r11 = 1
            goto L_0x0036
        L_0x009f:
            r17 = r4
            java.lang.String r0 = "/"
            java.lang.String r4 = "*.jar"
            r6 = 1
            java.util.Enumeration r4 = r8.v(r0, r4, r6)
            if (r4 == 0) goto L_0x0135
        L_0x00ac:
            boolean r0 = r4.hasMoreElements()
            if (r0 == 0) goto L_0x0132
            r6 = 0
            java.lang.Object r0 = r4.nextElement()     // Catch:{ Exception -> 0x011d, all -> 0x0111 }
            java.net.URL r0 = (java.net.URL) r0     // Catch:{ Exception -> 0x011d, all -> 0x0111 }
            java.lang.String r0 = r0.getPath()     // Catch:{ Exception -> 0x011d, all -> 0x0111 }
            r9 = r21
            java.io.InputStream r0 = r9.getResourceAsStream(r0)     // Catch:{ Exception -> 0x010f, all -> 0x010d }
            java.util.jar.JarInputStream r10 = new java.util.jar.JarInputStream     // Catch:{ Exception -> 0x010f, all -> 0x010d }
            r10.<init>(r0)     // Catch:{ Exception -> 0x010f, all -> 0x010d }
            r6 = r10
        L_0x00c9:
            java.util.jar.JarEntry r10 = r6.getNextJarEntry()     // Catch:{ Exception -> 0x010f, all -> 0x010d }
            r11 = r10
            if (r10 == 0) goto L_0x0104
            java.lang.String r10 = r11.getName()     // Catch:{ Exception -> 0x010f, all -> 0x010d }
            boolean r13 = r10.endsWith(r14)     // Catch:{ Exception -> 0x010f, all -> 0x010d }
            if (r13 == 0) goto L_0x00fb
            boolean r13 = r10.contains(r2)     // Catch:{ Exception -> 0x010f, all -> 0x010d }
            if (r13 == 0) goto L_0x00fb
            java.util.Map<java.lang.String, org.osgi.framework.Bundle> r13 = r1.classToBundleMapping     // Catch:{ Exception -> 0x010f, all -> 0x010d }
            java.lang.String r15 = r10.replace(r14, r12)     // Catch:{ Exception -> 0x010f, all -> 0x010d }
            r1 = 46
            r2 = 47
            java.lang.String r15 = r15.replace(r2, r1)     // Catch:{ Exception -> 0x00f9, all -> 0x010d }
            r13.put(r15, r8)     // Catch:{ Exception -> 0x00f9, all -> 0x010d }
            java.net.URL r13 = r8.i(r10)     // Catch:{ Exception -> 0x00f9, all -> 0x010d }
            r3.add(r13)     // Catch:{ Exception -> 0x00f9, all -> 0x010d }
            goto L_0x00ff
        L_0x00f9:
            r0 = move-exception
            goto L_0x0124
        L_0x00fb:
            r1 = 46
            r2 = 47
        L_0x00ff:
            r1 = r19
            r2 = r20
            goto L_0x00c9
        L_0x0104:
            r1 = 46
            r2 = 47
            r6.close()     // Catch:{ IOException -> 0x012a }
            goto L_0x0129
        L_0x010d:
            r0 = move-exception
            goto L_0x0114
        L_0x010f:
            r0 = move-exception
            goto L_0x0120
        L_0x0111:
            r0 = move-exception
            r9 = r21
        L_0x0114:
            r1 = r0
            if (r6 == 0) goto L_0x011c
            r6.close()     // Catch:{ IOException -> 0x011b }
            goto L_0x011c
        L_0x011b:
            r0 = move-exception
        L_0x011c:
            throw r1
        L_0x011d:
            r0 = move-exception
            r9 = r21
        L_0x0120:
            r1 = 46
            r2 = 47
        L_0x0124:
            if (r6 == 0) goto L_0x012c
            r6.close()     // Catch:{ IOException -> 0x012a }
        L_0x0129:
            goto L_0x012c
        L_0x012a:
            r0 = move-exception
            goto L_0x0129
        L_0x012c:
            r1 = r19
            r2 = r20
            goto L_0x00ac
        L_0x0132:
            r9 = r21
            goto L_0x0137
        L_0x0135:
            r9 = r21
        L_0x0137:
            int r7 = r7 + 1
            r6 = 0
            r1 = r19
            r2 = r20
            r4 = r17
            goto L_0x0018
        L_0x0142:
            r9 = r21
            java.util.Enumeration r0 = java.util.Collections.enumeration(r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.tyrus.core.OsgiRegistry.getPackageResources(java.lang.String, java.lang.ClassLoader):java.util.Enumeration");
    }

    public Class<?> classForNameWithException(String className) {
        Bundle bundle = this.classToBundleMapping.get(className);
        if (bundle != null) {
            return bundle.x(className);
        }
        throw new ClassNotFoundException(className);
    }

    public ResourceBundle getResourceBundle(String bundleName) {
        int lastDotIndex = bundleName.lastIndexOf(46);
        String path = bundleName.substring(0, lastDotIndex).replace('.', '/');
        String propertiesName = bundleName.substring(lastDotIndex + 1, bundleName.length()) + ".properties";
        Bundle[] b = this.bundleContext.b();
        int length = b.length;
        int i = 0;
        while (i < length) {
            Enumeration entries = b[i].v(path, propertiesName, false);
            if (entries == null || !entries.hasMoreElements()) {
                i++;
            } else {
                try {
                    return new PropertyResourceBundle(entries.nextElement().openStream());
                } catch (IOException e) {
                    Logger logger = LOGGER;
                    if (logger.isLoggable(Level.FINE)) {
                        logger.fine("Exception caught when tried to load resource bundle in OSGi");
                    }
                    return null;
                }
            }
        }
        return null;
    }

    private OsgiRegistry(BundleContext bundleContext2) {
        this.bundleContext = bundleContext2;
    }

    public void hookUp() {
        setOSGiServiceFinderIteratorProvider();
        this.bundleContext.c(this);
        registerExistingBundles();
    }

    private void registerExistingBundles() {
        for (Bundle bundle : this.bundleContext.b()) {
            if (bundle.getState() == 4 || bundle.getState() == 8 || bundle.getState() == 32 || bundle.getState() == 16) {
                register(bundle);
            }
        }
    }

    private void setOSGiServiceFinderIteratorProvider() {
        ServiceFinder.setIteratorProvider(new OsgiServiceFinder());
    }

    /* JADX INFO: finally extract failed */
    private void register(Bundle bundle) {
        Logger logger = LOGGER;
        Level level = Level.FINEST;
        if (logger.isLoggable(level)) {
            logger.log(level, "checking bundle {0}", Long.valueOf(bundle.z()));
        }
        this.lock.writeLock().lock();
        try {
            Map<String, Callable<List<Class<?>>>> map = this.factories.get(Long.valueOf(bundle.z()));
            if (map == null) {
                map = new ConcurrentHashMap<>();
                this.factories.put(Long.valueOf(bundle.z()), map);
            }
            this.lock.writeLock().unlock();
            Enumeration e = bundle.v("META-INF/services/", e.ANY_MARKER, false);
            if (e != null) {
                while (e.hasMoreElements()) {
                    URL u = e.nextElement();
                    String url = u.toString();
                    if (!url.endsWith("/")) {
                        String factoryId = url.substring(url.lastIndexOf("/") + 1);
                        try {
                            map.put(factoryId, new BundleSpiProvidersLoader(factoryId, u.toURI(), bundle));
                        } catch (URISyntaxException e2) {
                        }
                    }
                }
            }
        } catch (Throwable th) {
            this.lock.writeLock().unlock();
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public List<Class<?>> locateAllProviders(String serviceName) {
        this.lock.readLock().lock();
        try {
            List<Class<?>> result = new LinkedList<>();
            for (Map<String, Callable<List<Class<?>>>> value : this.factories.values()) {
                if (value.containsKey(serviceName)) {
                    try {
                        result.addAll((Collection) value.get(serviceName).call());
                    } catch (Exception e) {
                    }
                }
            }
            return result;
        } finally {
            this.lock.readLock().unlock();
        }
    }
}
