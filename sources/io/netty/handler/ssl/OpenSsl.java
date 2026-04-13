package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.internal.tcnative.Buffer;
import io.netty.internal.tcnative.Library;
import io.netty.internal.tcnative.SSL;
import io.netty.internal.tcnative.SSLContext;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.NativeLibraryLoader;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class OpenSsl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final Set<String> AVAILABLE_CIPHER_SUITES;
    private static final Set<String> AVAILABLE_JAVA_CIPHER_SUITES;
    private static final Set<String> AVAILABLE_OPENSSL_CIPHER_SUITES;
    static final List<String> DEFAULT_CIPHERS;
    static final Set<String> SUPPORTED_PROTOCOLS_SET;
    private static final boolean SUPPORTS_HOSTNAME_VALIDATION;
    private static final boolean SUPPORTS_KEYMANAGER_FACTORY;
    private static final boolean SUPPORTS_OCSP;
    private static final Throwable UNAVAILABILITY_CAUSE;
    private static final boolean USE_KEYMANAGER_FACTORY;
    private static final InternalLogger logger;

    /* JADX WARNING: Removed duplicated region for block: B:101:0x0209  */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x0216  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x022f  */
    /* JADX WARNING: Removed duplicated region for block: B:117:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0141 A[Catch:{ all -> 0x0160 }] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0146 A[Catch:{ all -> 0x0160 }] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0188 A[LOOP:1: B:87:0x0182->B:89:0x0188, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x01e5  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x01f0  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x01fc  */
    static {
        /*
            java.lang.Class<io.netty.handler.ssl.OpenSsl> r0 = io.netty.handler.ssl.OpenSsl.class
            io.netty.util.internal.logging.InternalLogger r1 = io.netty.util.internal.logging.InternalLoggerFactory.getInstance((java.lang.Class<?>) r0)
            logger = r1
            r1 = 0
            r2 = 0
            java.lang.String r3 = "io.netty.internal.tcnative.SSL"
            java.lang.ClassLoader r0 = r0.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x0014 }
            java.lang.Class.forName(r3, r2, r0)     // Catch:{ ClassNotFoundException -> 0x0014 }
            goto L_0x0037
        L_0x0014:
            r0 = move-exception
            r1 = r0
            io.netty.util.internal.logging.InternalLogger r3 = logger
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "netty-tcnative not in the classpath; "
            r4.append(r5)
            java.lang.Class<io.netty.handler.ssl.OpenSslEngine> r5 = io.netty.handler.ssl.OpenSslEngine.class
            java.lang.String r5 = r5.getSimpleName()
            r4.append(r5)
            java.lang.String r5 = " will be unavailable."
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.debug(r4)
        L_0x0037:
            if (r1 != 0) goto L_0x008e
            loadTcNative()     // Catch:{ all -> 0x003d }
            goto L_0x0062
        L_0x003d:
            r0 = move-exception
            r3 = r0
            r0 = r3
            r1 = r0
            io.netty.util.internal.logging.InternalLogger r3 = logger
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Failed to load netty-tcnative; "
            r4.append(r5)
            java.lang.Class<io.netty.handler.ssl.OpenSslEngine> r5 = io.netty.handler.ssl.OpenSslEngine.class
            java.lang.String r5 = r5.getSimpleName()
            r4.append(r5)
            java.lang.String r5 = " will be unavailable, unless the application has already loaded the symbols by some other means. See http://netty.io/wiki/forked-tomcat-native.html for more information."
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.debug((java.lang.String) r4, (java.lang.Throwable) r0)
        L_0x0062:
            initializeTcNative()     // Catch:{ all -> 0x0067 }
            r1 = 0
            goto L_0x008e
        L_0x0067:
            r0 = move-exception
            r3 = r0
            r0 = r3
            if (r1 != 0) goto L_0x006d
            r1 = r0
        L_0x006d:
            io.netty.util.internal.logging.InternalLogger r3 = logger
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Failed to initialize netty-tcnative; "
            r4.append(r5)
            java.lang.Class<io.netty.handler.ssl.OpenSslEngine> r5 = io.netty.handler.ssl.OpenSslEngine.class
            java.lang.String r5 = r5.getSimpleName()
            r4.append(r5)
            java.lang.String r5 = " will be unavailable. See http://netty.io/wiki/forked-tomcat-native.html for more information."
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.debug((java.lang.String) r4, (java.lang.Throwable) r0)
        L_0x008e:
            UNAVAILABILITY_CAUSE = r1
            if (r1 != 0) goto L_0x0244
            io.netty.util.internal.logging.InternalLogger r0 = logger
            java.lang.String r3 = io.netty.internal.tcnative.SSL.versionString()
            java.lang.String r4 = "netty-tcnative using native library: {}"
            r0.debug((java.lang.String) r4, (java.lang.Object) r3)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r3 = r0
            java.util.LinkedHashSet r0 = new java.util.LinkedHashSet
            r4 = 128(0x80, float:1.794E-43)
            r0.<init>(r4)
            r4 = r0
            r5 = 0
            r6 = 0
            r7 = 0
            r0 = 31
            r8 = 1
            long r9 = io.netty.internal.tcnative.SSLContext.make(r0, r8)     // Catch:{ Exception -> 0x0165 }
            r11 = 0
            r13 = 0
            java.lang.String r0 = "ALL"
            io.netty.internal.tcnative.SSLContext.setCipherSuite(r9, r0)     // Catch:{ all -> 0x0160 }
            long r14 = io.netty.internal.tcnative.SSL.newSSL(r9, r8)     // Catch:{ all -> 0x0160 }
            r16 = 0
            java.lang.String[] r0 = io.netty.internal.tcnative.SSL.getCiphers(r14)     // Catch:{ all -> 0x014e }
            int r8 = r0.length     // Catch:{ all -> 0x014e }
        L_0x00c8:
            if (r2 >= r8) goto L_0x00e9
            r19 = r0[r2]     // Catch:{ all -> 0x014e }
            r20 = r19
            r19 = r0
            r0 = r20
            if (r0 == 0) goto L_0x00e4
            boolean r20 = r0.isEmpty()     // Catch:{ all -> 0x014e }
            if (r20 != 0) goto L_0x00e4
            boolean r20 = r4.contains(r0)     // Catch:{ all -> 0x014e }
            if (r20 == 0) goto L_0x00e1
            goto L_0x00e4
        L_0x00e1:
            r4.add(r0)     // Catch:{ all -> 0x014e }
        L_0x00e4:
            int r2 = r2 + 1
            r0 = r19
            goto L_0x00c8
        L_0x00e9:
            java.lang.String r0 = "netty.io"
            r2 = 0
            io.netty.internal.tcnative.SSL.setHostNameValidation(r14, r2, r0)     // Catch:{ all -> 0x00f2 }
            r0 = 1
            r7 = r0
            goto L_0x00fa
        L_0x00f2:
            r0 = move-exception
            io.netty.util.internal.logging.InternalLogger r2 = logger     // Catch:{ all -> 0x014e }
            java.lang.String r8 = "Hostname Verification not supported."
            r2.debug(r8)     // Catch:{ all -> 0x014e }
        L_0x00fa:
            io.netty.handler.ssl.util.SelfSignedCertificate r0 = new io.netty.handler.ssl.util.SelfSignedCertificate     // Catch:{ all -> 0x0132 }
            r0.<init>()     // Catch:{ all -> 0x0132 }
            r2 = r0
            r8 = 1
            java.security.cert.X509Certificate[] r0 = new java.security.cert.X509Certificate[r8]     // Catch:{ all -> 0x012f }
            java.security.cert.X509Certificate r8 = r2.cert()     // Catch:{ all -> 0x012f }
            r13 = 0
            r0[r13] = r8     // Catch:{ all -> 0x012f }
            long r18 = io.netty.handler.ssl.ReferenceCountedOpenSslContext.toBIO((java.security.cert.X509Certificate[]) r0)     // Catch:{ all -> 0x012f }
            r11 = r18
            io.netty.internal.tcnative.SSL.setCertificateChainBio(r14, r11, r13)     // Catch:{ all -> 0x012f }
            r5 = 1
            io.netty.handler.ssl.OpenSsl$1 r0 = new io.netty.handler.ssl.OpenSsl$1     // Catch:{ all -> 0x0125 }
            r0.<init>()     // Catch:{ all -> 0x0125 }
            java.lang.Object r0 = java.security.AccessController.doPrivileged(r0)     // Catch:{ all -> 0x0125 }
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x0125 }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x0125 }
            r6 = r0
            goto L_0x012d
        L_0x0125:
            r0 = move-exception
            io.netty.util.internal.logging.InternalLogger r8 = logger     // Catch:{ all -> 0x012f }
            java.lang.String r13 = "Failed to get useKeyManagerFactory system property."
            r8.debug(r13)     // Catch:{ all -> 0x012f }
        L_0x012d:
            r13 = r2
            goto L_0x013a
        L_0x012f:
            r0 = move-exception
            r13 = r2
            goto L_0x0133
        L_0x0132:
            r0 = move-exception
        L_0x0133:
            io.netty.util.internal.logging.InternalLogger r2 = logger     // Catch:{ all -> 0x014e }
            java.lang.String r8 = "KeyManagerFactory not supported."
            r2.debug(r8)     // Catch:{ all -> 0x014e }
        L_0x013a:
            io.netty.internal.tcnative.SSL.freeSSL(r14)     // Catch:{ all -> 0x0160 }
            int r0 = (r11 > r16 ? 1 : (r11 == r16 ? 0 : -1))
            if (r0 == 0) goto L_0x0144
            io.netty.internal.tcnative.SSL.freeBIO(r11)     // Catch:{ all -> 0x0160 }
        L_0x0144:
            if (r13 == 0) goto L_0x0149
            r13.delete()     // Catch:{ all -> 0x0160 }
        L_0x0149:
            io.netty.internal.tcnative.SSLContext.free(r9)     // Catch:{ Exception -> 0x0165 }
            goto L_0x016d
        L_0x014e:
            r0 = move-exception
            io.netty.internal.tcnative.SSL.freeSSL(r14)     // Catch:{ all -> 0x0160 }
            int r2 = (r11 > r16 ? 1 : (r11 == r16 ? 0 : -1))
            if (r2 == 0) goto L_0x0159
            io.netty.internal.tcnative.SSL.freeBIO(r11)     // Catch:{ all -> 0x0160 }
        L_0x0159:
            if (r13 == 0) goto L_0x015e
            r13.delete()     // Catch:{ all -> 0x0160 }
        L_0x015e:
            throw r0     // Catch:{ all -> 0x0160 }
        L_0x0160:
            r0 = move-exception
            io.netty.internal.tcnative.SSLContext.free(r9)     // Catch:{ Exception -> 0x0165 }
            throw r0     // Catch:{ Exception -> 0x0165 }
        L_0x0165:
            r0 = move-exception
            io.netty.util.internal.logging.InternalLogger r2 = logger
            java.lang.String r8 = "Failed to get the list of available OpenSSL cipher suites."
            r2.warn((java.lang.String) r8, (java.lang.Throwable) r0)
        L_0x016d:
            java.util.Set r0 = java.util.Collections.unmodifiableSet(r4)
            AVAILABLE_OPENSSL_CIPHER_SUITES = r0
            java.util.LinkedHashSet r2 = new java.util.LinkedHashSet
            int r8 = r0.size()
            r9 = 2
            int r8 = r8 * r9
            r2.<init>(r8)
            java.util.Iterator r0 = r0.iterator()
        L_0x0182:
            boolean r8 = r0.hasNext()
            if (r8 == 0) goto L_0x01a1
            java.lang.Object r8 = r0.next()
            java.lang.String r8 = (java.lang.String) r8
            java.lang.String r10 = "TLS"
            java.lang.String r10 = io.netty.handler.ssl.CipherSuiteConverter.toJava(r8, r10)
            r2.add(r10)
            java.lang.String r10 = "SSL"
            java.lang.String r10 = io.netty.handler.ssl.CipherSuiteConverter.toJava(r8, r10)
            r2.add(r10)
            goto L_0x0182
        L_0x01a1:
            java.lang.String[] r0 = io.netty.handler.ssl.SslUtils.DEFAULT_CIPHER_SUITES
            io.netty.handler.ssl.SslUtils.addIfSupported(r2, r3, r0)
            io.netty.handler.ssl.SslUtils.useFallbackCiphersIfDefaultIsEmpty((java.util.List<java.lang.String>) r3, (java.lang.Iterable<java.lang.String>) r2)
            java.util.List r0 = java.util.Collections.unmodifiableList(r3)
            DEFAULT_CIPHERS = r0
            java.util.Set r8 = java.util.Collections.unmodifiableSet(r2)
            AVAILABLE_JAVA_CIPHER_SUITES = r8
            java.util.LinkedHashSet r10 = new java.util.LinkedHashSet
            java.util.Set<java.lang.String> r11 = AVAILABLE_OPENSSL_CIPHER_SUITES
            int r12 = r11.size()
            int r13 = r8.size()
            int r12 = r12 + r13
            r10.<init>(r12)
            r10.addAll(r11)
            r10.addAll(r8)
            AVAILABLE_CIPHER_SUITES = r10
            SUPPORTS_KEYMANAGER_FACTORY = r5
            SUPPORTS_HOSTNAME_VALIDATION = r7
            USE_KEYMANAGER_FACTORY = r6
            java.util.LinkedHashSet r8 = new java.util.LinkedHashSet
            r11 = 6
            r8.<init>(r11)
            java.lang.String r11 = "SSLv2Hello"
            r8.add(r11)
            r11 = 1
            boolean r12 = doesSupportProtocol(r11)
            if (r12 == 0) goto L_0x01ea
            java.lang.String r11 = "SSLv2"
            r8.add(r11)
        L_0x01ea:
            boolean r9 = doesSupportProtocol(r9)
            if (r9 == 0) goto L_0x01f5
            java.lang.String r9 = "SSLv3"
            r8.add(r9)
        L_0x01f5:
            r9 = 4
            boolean r9 = doesSupportProtocol(r9)
            if (r9 == 0) goto L_0x0201
            java.lang.String r9 = "TLSv1"
            r8.add(r9)
        L_0x0201:
            r9 = 8
            boolean r9 = doesSupportProtocol(r9)
            if (r9 == 0) goto L_0x020e
            java.lang.String r9 = "TLSv1.1"
            r8.add(r9)
        L_0x020e:
            r9 = 16
            boolean r9 = doesSupportProtocol(r9)
            if (r9 == 0) goto L_0x021b
            java.lang.String r9 = "TLSv1.2"
            r8.add(r9)
        L_0x021b:
            java.util.Set r9 = java.util.Collections.unmodifiableSet(r8)
            SUPPORTED_PROTOCOLS_SET = r9
            boolean r11 = doesSupportOcsp()
            SUPPORTS_OCSP = r11
            io.netty.util.internal.logging.InternalLogger r11 = logger
            boolean r12 = r11.isDebugEnabled()
            if (r12 == 0) goto L_0x0243
            r12 = 1
            java.util.Set[] r12 = new java.util.Set[r12]
            r13 = 0
            r12[r13] = r9
            java.util.List r9 = java.util.Arrays.asList(r12)
            java.lang.String r12 = "Supported protocols (OpenSSL): {} "
            r11.debug((java.lang.String) r12, (java.lang.Object) r9)
            java.lang.String r9 = "Default cipher suites (OpenSSL): {}"
            r11.debug((java.lang.String) r9, (java.lang.Object) r0)
        L_0x0243:
            goto L_0x026b
        L_0x0244:
            java.util.List r0 = java.util.Collections.emptyList()
            DEFAULT_CIPHERS = r0
            java.util.Set r0 = java.util.Collections.emptySet()
            AVAILABLE_OPENSSL_CIPHER_SUITES = r0
            java.util.Set r0 = java.util.Collections.emptySet()
            AVAILABLE_JAVA_CIPHER_SUITES = r0
            java.util.Set r0 = java.util.Collections.emptySet()
            AVAILABLE_CIPHER_SUITES = r0
            r2 = 0
            SUPPORTS_KEYMANAGER_FACTORY = r2
            SUPPORTS_HOSTNAME_VALIDATION = r2
            USE_KEYMANAGER_FACTORY = r2
            java.util.Set r0 = java.util.Collections.emptySet()
            SUPPORTED_PROTOCOLS_SET = r0
            SUPPORTS_OCSP = r2
        L_0x026b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.OpenSsl.<clinit>():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0032, code lost:
        if (r1 == -1) goto L_0x0035;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0020, code lost:
        if (r1 != -1) goto L_0x0022;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0022, code lost:
        io.netty.internal.tcnative.SSLContext.free(r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean doesSupportOcsp() {
        /*
            r0 = 0
            int r1 = version()
            long r1 = (long) r1
            r3 = 268443648(0x10002000, double:1.326287843E-315)
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 < 0) goto L_0x0035
            r1 = -1
            r3 = 16
            r4 = 1
            r5 = -1
            long r3 = io.netty.internal.tcnative.SSLContext.make(r3, r4)     // Catch:{ Exception -> 0x002f, all -> 0x0026 }
            r1 = r3
            r3 = 0
            io.netty.internal.tcnative.SSLContext.enableOcsp(r1, r3)     // Catch:{ Exception -> 0x002f, all -> 0x0026 }
            r0 = 1
            int r3 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r3 == 0) goto L_0x0035
        L_0x0022:
            io.netty.internal.tcnative.SSLContext.free(r1)
            goto L_0x0035
        L_0x0026:
            r3 = move-exception
            int r4 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r4 == 0) goto L_0x002e
            io.netty.internal.tcnative.SSLContext.free(r1)
        L_0x002e:
            throw r3
        L_0x002f:
            r3 = move-exception
            int r3 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r3 == 0) goto L_0x0035
            goto L_0x0022
        L_0x0035:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.OpenSsl.doesSupportOcsp():boolean");
    }

    private static boolean doesSupportProtocol(int protocol) {
        try {
            long sslCtx = SSLContext.make(protocol, 2);
            if (sslCtx != -1) {
                SSLContext.free(sslCtx);
            }
            return true;
        } catch (Exception e) {
            if (-1 != -1) {
                SSLContext.free(-1);
            }
            return false;
        } catch (Throwable th) {
            if (-1 != -1) {
                SSLContext.free(-1);
            }
            throw th;
        }
    }

    public static boolean isAvailable() {
        return UNAVAILABILITY_CAUSE == null;
    }

    public static boolean isAlpnSupported() {
        return ((long) version()) >= 268443648;
    }

    public static boolean isOcspSupported() {
        return SUPPORTS_OCSP;
    }

    public static int version() {
        if (isAvailable()) {
            return SSL.version();
        }
        return -1;
    }

    public static String versionString() {
        if (isAvailable()) {
            return SSL.versionString();
        }
        return null;
    }

    public static void ensureAvailability() {
        Throwable th = UNAVAILABILITY_CAUSE;
        if (th != null) {
            throw ((Error) new UnsatisfiedLinkError("failed to load the required native library").initCause(th));
        }
    }

    public static Throwable unavailabilityCause() {
        return UNAVAILABILITY_CAUSE;
    }

    @Deprecated
    public static Set<String> availableCipherSuites() {
        return availableOpenSslCipherSuites();
    }

    public static Set<String> availableOpenSslCipherSuites() {
        return AVAILABLE_OPENSSL_CIPHER_SUITES;
    }

    public static Set<String> availableJavaCipherSuites() {
        return AVAILABLE_JAVA_CIPHER_SUITES;
    }

    public static boolean isCipherSuiteAvailable(String cipherSuite) {
        String converted = CipherSuiteConverter.toOpenSsl(cipherSuite);
        if (converted != null) {
            cipherSuite = converted;
        }
        return AVAILABLE_OPENSSL_CIPHER_SUITES.contains(cipherSuite);
    }

    public static boolean supportsKeyManagerFactory() {
        return SUPPORTS_KEYMANAGER_FACTORY;
    }

    public static boolean supportsHostnameValidation() {
        return SUPPORTS_HOSTNAME_VALIDATION;
    }

    static boolean useKeyManagerFactory() {
        return USE_KEYMANAGER_FACTORY;
    }

    static long memoryAddress(ByteBuf buf) {
        if (buf.isDirect()) {
            return buf.hasMemoryAddress() ? buf.memoryAddress() : Buffer.address(buf.nioBuffer());
        }
        throw new AssertionError();
    }

    private OpenSsl() {
    }

    private static void loadTcNative() {
        String os = PlatformDependent.normalizedOs();
        String arch = PlatformDependent.normalizedArch();
        Set<String> libNames = new LinkedHashSet<>(4);
        libNames.add("netty_tcnative" + "_" + os + '_' + arch);
        if ("linux".equalsIgnoreCase(os)) {
            libNames.add("netty_tcnative" + "_" + os + '_' + arch + "_fedora");
        }
        libNames.add("netty_tcnative" + "_" + arch);
        libNames.add("netty_tcnative");
        NativeLibraryLoader.loadFirstAvailable(SSL.class.getClassLoader(), (String[]) libNames.toArray(new String[libNames.size()]));
    }

    private static boolean initializeTcNative() {
        return Library.initialize();
    }

    static void releaseIfNeeded(ReferenceCounted counted) {
        if (counted.refCnt() > 0) {
            ReferenceCountUtil.safeRelease(counted);
        }
    }
}
