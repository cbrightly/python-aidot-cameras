package org.glassfish.tyrus.client;

import java.util.Properties;
import java.util.logging.Logger;

public class SslContextConfigurator {
    public static final SslContextConfigurator DEFAULT_CONFIG = new SslContextConfigurator();
    public static final String KEY_FACTORY_MANAGER_ALGORITHM = "ssl.KeyManagerFactory.algorithm";
    public static final String KEY_STORE_FILE = "javax.net.ssl.keyStore";
    public static final String KEY_STORE_PASSWORD = "javax.net.ssl.keyStorePassword";
    public static final String KEY_STORE_PROVIDER = "javax.net.ssl.keyStoreProvider";
    public static final String KEY_STORE_TYPE = "javax.net.ssl.keyStoreType";
    private static final Logger LOGGER = Logger.getLogger(SslContextConfigurator.class.getName());
    public static final String TRUST_FACTORY_MANAGER_ALGORITHM = "ssl.TrustManagerFactory.algorithm";
    public static final String TRUST_STORE_FILE = "javax.net.ssl.trustStore";
    public static final String TRUST_STORE_PASSWORD = "javax.net.ssl.trustStorePassword";
    public static final String TRUST_STORE_PROVIDER = "javax.net.ssl.trustStoreProvider";
    public static final String TRUST_STORE_TYPE = "javax.net.ssl.trustStoreType";
    private String keyManagerFactoryAlgorithm;
    private char[] keyPassword;
    private byte[] keyStoreBytes;
    private String keyStoreFile;
    private char[] keyStorePassword;
    private String keyStoreProvider;
    private String keyStoreType;
    private String securityProtocol;
    private String trustManagerFactoryAlgorithm;
    private byte[] trustStoreBytes;
    private String trustStoreFile;
    private char[] trustStorePassword;
    private String trustStoreProvider;
    private String trustStoreType;

    public SslContextConfigurator() {
        this(true);
    }

    public SslContextConfigurator(boolean readSystemProperties) {
        this.securityProtocol = "TLS";
        if (readSystemProperties) {
            retrieve(System.getProperties());
        }
    }

    public SslContextConfigurator setTrustStoreProvider(String trustStoreProvider2) {
        this.trustStoreProvider = trustStoreProvider2;
        return this;
    }

    public SslContextConfigurator setKeyStoreProvider(String keyStoreProvider2) {
        this.keyStoreProvider = keyStoreProvider2;
        return this;
    }

    public SslContextConfigurator setTrustStoreType(String trustStoreType2) {
        this.trustStoreType = trustStoreType2;
        return this;
    }

    public SslContextConfigurator setKeyStoreType(String keyStoreType2) {
        this.keyStoreType = keyStoreType2;
        return this;
    }

    public SslContextConfigurator setTrustStorePassword(String trustStorePassword2) {
        this.trustStorePassword = trustStorePassword2.toCharArray();
        return this;
    }

    public SslContextConfigurator setKeyStorePassword(String keyStorePassword2) {
        this.keyStorePassword = keyStorePassword2.toCharArray();
        return this;
    }

    public SslContextConfigurator setKeyStorePassword(char[] keyStorePassword2) {
        this.keyStorePassword = (char[]) keyStorePassword2.clone();
        return this;
    }

    public SslContextConfigurator setKeyPassword(String keyPassword2) {
        this.keyPassword = keyPassword2.toCharArray();
        return this;
    }

    public SslContextConfigurator setKeyPassword(char[] keyPassword2) {
        this.keyPassword = (char[]) keyPassword2.clone();
        return this;
    }

    public SslContextConfigurator setTrustStoreFile(String trustStoreFile2) {
        this.trustStoreFile = trustStoreFile2;
        this.trustStoreBytes = null;
        return this;
    }

    public SslContextConfigurator setTrustStoreBytes(byte[] trustStoreBytes2) {
        this.trustStoreBytes = (byte[]) trustStoreBytes2.clone();
        this.trustStoreFile = null;
        return this;
    }

    public SslContextConfigurator setKeyStoreFile(String keyStoreFile2) {
        this.keyStoreFile = keyStoreFile2;
        this.keyStoreBytes = null;
        return this;
    }

    public SslContextConfigurator setKeyStoreBytes(byte[] keyStoreBytes2) {
        this.keyStoreBytes = (byte[]) keyStoreBytes2.clone();
        this.keyStoreFile = null;
        return this;
    }

    public SslContextConfigurator setTrustManagerFactoryAlgorithm(String trustManagerFactoryAlgorithm2) {
        this.trustManagerFactoryAlgorithm = trustManagerFactoryAlgorithm2;
        return this;
    }

    public SslContextConfigurator setKeyManagerFactoryAlgorithm(String keyManagerFactoryAlgorithm2) {
        this.keyManagerFactoryAlgorithm = keyManagerFactoryAlgorithm2;
        return this;
    }

    public SslContextConfigurator setSecurityProtocol(String securityProtocol2) {
        this.securityProtocol = securityProtocol2;
        return this;
    }

    public boolean validateConfiguration() {
        return validateConfiguration(false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:103:0x018a, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:?, code lost:
        LOGGER.log(java.util.logging.Level.FINEST, "Could not close key store file", r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x0196, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x0197, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Error initializing trust store (no such provider)", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x01a2, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x01a3, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Error initializing trust manager factory (no such algorithm)", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x01e8, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x01e9, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Trust store certificate exception.", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x01f4, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x01f5, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Error initializing trust store", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:?, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:?, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:?, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:?, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0086, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0087, code lost:
        if (r4 != null) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x008d, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        LOGGER.log(java.util.logging.Level.FINEST, "Could not close key store file", r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0098, code lost:
        throw r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0099, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x009a, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Error initializing key store (no such provider)", r1);
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00a5, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00a6, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Error initializing key manager factory (no such algorithm)", r1);
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00b1, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00b2, code lost:
        r4 = LOGGER;
        r5 = java.util.logging.Level.FINE;
        r4.log(r5, "Error loading key store from file: " + r9.keyStoreFile, r1);
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00ce, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00cf, code lost:
        r4 = LOGGER;
        r5 = java.util.logging.Level.FINE;
        r4.log(r5, "Can't find key store file: " + r9.keyStoreFile, r1);
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00eb, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00ec, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Key store unrecoverable exception.", r1);
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00f7, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00f8, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Key store certificate exception.", r1);
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0103, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0104, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Error initializing key store", r1);
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0161, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:?, code lost:
        LOGGER.log(java.util.logging.Level.FINEST, "Could not close key store file", r2);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x0196 A[ExcHandler: NoSuchProviderException (r1v7 'e' java.security.NoSuchProviderException A[CUSTOM_DECLARE]), Splitter:B:67:0x0117] */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x01a2 A[ExcHandler: NoSuchAlgorithmException (r1v6 'e' java.security.NoSuchAlgorithmException A[CUSTOM_DECLARE]), Splitter:B:67:0x0117] */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x01e8 A[ExcHandler: CertificateException (r1v3 'e' java.security.cert.CertificateException A[CUSTOM_DECLARE]), Splitter:B:67:0x0117] */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x01f4 A[ExcHandler: KeyStoreException (r1v2 'e' java.security.KeyStoreException A[CUSTOM_DECLARE]), Splitter:B:67:0x0117] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0099 A[ExcHandler: NoSuchProviderException (r1v25 'e' java.security.NoSuchProviderException A[CUSTOM_DECLARE]), Splitter:B:5:0x0013] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00a5 A[ExcHandler: NoSuchAlgorithmException (r1v24 'e' java.security.NoSuchAlgorithmException A[CUSTOM_DECLARE]), Splitter:B:5:0x0013] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00eb A[ExcHandler: UnrecoverableKeyException (r1v21 'e' java.security.UnrecoverableKeyException A[CUSTOM_DECLARE]), Splitter:B:5:0x0013] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00f7 A[ExcHandler: CertificateException (r1v20 'e' java.security.cert.CertificateException A[CUSTOM_DECLARE]), Splitter:B:5:0x0013] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0103 A[ExcHandler: KeyStoreException (r1v19 'e' java.security.KeyStoreException A[CUSTOM_DECLARE]), Splitter:B:5:0x0013] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean validateConfiguration(boolean r10) {
        /*
            r9 = this;
            r0 = 1
            byte[] r1 = r9.keyStoreBytes
            java.lang.String r2 = "NONE"
            java.lang.String r3 = "Could not close key store file"
            if (r1 != 0) goto L_0x0013
            java.lang.String r1 = r9.keyStoreFile
            if (r1 == 0) goto L_0x000e
            goto L_0x0013
        L_0x000e:
            r1 = r10 ^ 1
            r0 = r0 & r1
            goto L_0x010f
        L_0x0013:
            java.lang.String r1 = r9.keyStoreProvider     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            if (r1 == 0) goto L_0x0027
            java.lang.String r1 = r9.keyStoreType     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            if (r1 == 0) goto L_0x001c
            goto L_0x0020
        L_0x001c:
            java.lang.String r1 = java.security.KeyStore.getDefaultType()     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
        L_0x0020:
            java.lang.String r4 = r9.keyStoreProvider     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            java.security.KeyStore r1 = java.security.KeyStore.getInstance(r1, r4)     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            goto L_0x0034
        L_0x0027:
            java.lang.String r1 = r9.keyStoreType     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            if (r1 == 0) goto L_0x002c
            goto L_0x0030
        L_0x002c:
            java.lang.String r1 = java.security.KeyStore.getDefaultType()     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
        L_0x0030:
            java.security.KeyStore r1 = java.security.KeyStore.getInstance(r1)     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
        L_0x0034:
            r4 = 0
            byte[] r5 = r9.keyStoreBytes     // Catch:{ all -> 0x0086 }
            if (r5 == 0) goto L_0x0042
            java.io.ByteArrayInputStream r5 = new java.io.ByteArrayInputStream     // Catch:{ all -> 0x0086 }
            byte[] r6 = r9.keyStoreBytes     // Catch:{ all -> 0x0086 }
            r5.<init>(r6)     // Catch:{ all -> 0x0086 }
            r4 = r5
            goto L_0x0052
        L_0x0042:
            java.lang.String r5 = r9.keyStoreFile     // Catch:{ all -> 0x0086 }
            boolean r5 = r5.equals(r2)     // Catch:{ all -> 0x0086 }
            if (r5 != 0) goto L_0x0052
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ all -> 0x0086 }
            java.lang.String r6 = r9.keyStoreFile     // Catch:{ all -> 0x0086 }
            r5.<init>(r6)     // Catch:{ all -> 0x0086 }
            r4 = r5
        L_0x0052:
            char[] r5 = r9.keyStorePassword     // Catch:{ all -> 0x0086 }
            r1.load(r4, r5)     // Catch:{ all -> 0x0086 }
            if (r4 == 0) goto L_0x0066
            r4.close()     // Catch:{ IOException -> 0x005d, KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            goto L_0x0066
        L_0x005d:
            r5 = move-exception
            java.util.logging.Logger r6 = LOGGER     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            java.util.logging.Level r7 = java.util.logging.Level.FINEST     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            r6.log(r7, r3, r5)     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            goto L_0x0067
        L_0x0066:
        L_0x0067:
            java.lang.String r5 = r9.keyManagerFactoryAlgorithm     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            if (r5 != 0) goto L_0x0076
            java.lang.String r6 = "ssl.KeyManagerFactory.algorithm"
            java.lang.String r7 = javax.net.ssl.KeyManagerFactory.getDefaultAlgorithm()     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            java.lang.String r6 = java.lang.System.getProperty(r6, r7)     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            r5 = r6
        L_0x0076:
            javax.net.ssl.KeyManagerFactory r6 = javax.net.ssl.KeyManagerFactory.getInstance(r5)     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            char[] r7 = r9.keyPassword     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            if (r7 == 0) goto L_0x007f
            goto L_0x0081
        L_0x007f:
            char[] r7 = r9.keyStorePassword     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
        L_0x0081:
            r6.init(r1, r7)     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            goto L_0x010e
        L_0x0086:
            r5 = move-exception
            if (r4 == 0) goto L_0x0096
            r4.close()     // Catch:{ IOException -> 0x008d, KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            goto L_0x0096
        L_0x008d:
            r6 = move-exception
            java.util.logging.Logger r7 = LOGGER     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            java.util.logging.Level r8 = java.util.logging.Level.FINEST     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            r7.log(r8, r3, r6)     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
            goto L_0x0097
        L_0x0096:
        L_0x0097:
            throw r5     // Catch:{ KeyStoreException -> 0x0103, CertificateException -> 0x00f7, UnrecoverableKeyException -> 0x00eb, FileNotFoundException -> 0x00ce, IOException -> 0x00b1, NoSuchAlgorithmException -> 0x00a5, NoSuchProviderException -> 0x0099 }
        L_0x0099:
            r1 = move-exception
            java.util.logging.Logger r4 = LOGGER
            java.util.logging.Level r5 = java.util.logging.Level.FINE
            java.lang.String r6 = "Error initializing key store (no such provider)"
            r4.log(r5, r6, r1)
            r0 = 0
            goto L_0x010e
        L_0x00a5:
            r1 = move-exception
            java.util.logging.Logger r4 = LOGGER
            java.util.logging.Level r5 = java.util.logging.Level.FINE
            java.lang.String r6 = "Error initializing key manager factory (no such algorithm)"
            r4.log(r5, r6, r1)
            r0 = 0
            goto L_0x010e
        L_0x00b1:
            r1 = move-exception
            java.util.logging.Logger r4 = LOGGER
            java.util.logging.Level r5 = java.util.logging.Level.FINE
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Error loading key store from file: "
            r6.append(r7)
            java.lang.String r7 = r9.keyStoreFile
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r4.log(r5, r6, r1)
            r0 = 0
            goto L_0x010e
        L_0x00ce:
            r1 = move-exception
            java.util.logging.Logger r4 = LOGGER
            java.util.logging.Level r5 = java.util.logging.Level.FINE
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Can't find key store file: "
            r6.append(r7)
            java.lang.String r7 = r9.keyStoreFile
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r4.log(r5, r6, r1)
            r0 = 0
            goto L_0x010e
        L_0x00eb:
            r1 = move-exception
            java.util.logging.Logger r4 = LOGGER
            java.util.logging.Level r5 = java.util.logging.Level.FINE
            java.lang.String r6 = "Key store unrecoverable exception."
            r4.log(r5, r6, r1)
            r0 = 0
            goto L_0x010e
        L_0x00f7:
            r1 = move-exception
            java.util.logging.Logger r4 = LOGGER
            java.util.logging.Level r5 = java.util.logging.Level.FINE
            java.lang.String r6 = "Key store certificate exception."
            r4.log(r5, r6, r1)
            r0 = 0
            goto L_0x010e
        L_0x0103:
            r1 = move-exception
            java.util.logging.Logger r4 = LOGGER
            java.util.logging.Level r5 = java.util.logging.Level.FINE
            java.lang.String r6 = "Error initializing key store"
            r4.log(r5, r6, r1)
            r0 = 0
        L_0x010e:
        L_0x010f:
            byte[] r1 = r9.trustStoreBytes
            if (r1 != 0) goto L_0x0117
            java.lang.String r1 = r9.trustStoreFile
            if (r1 == 0) goto L_0x0200
        L_0x0117:
            java.lang.String r1 = r9.trustStoreProvider     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
            if (r1 == 0) goto L_0x012b
            java.lang.String r1 = r9.trustStoreType     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
            if (r1 == 0) goto L_0x0120
            goto L_0x0124
        L_0x0120:
            java.lang.String r1 = java.security.KeyStore.getDefaultType()     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
        L_0x0124:
            java.lang.String r4 = r9.trustStoreProvider     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
            java.security.KeyStore r1 = java.security.KeyStore.getInstance(r1, r4)     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
            goto L_0x0138
        L_0x012b:
            java.lang.String r1 = r9.trustStoreType     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
            if (r1 == 0) goto L_0x0130
            goto L_0x0134
        L_0x0130:
            java.lang.String r1 = java.security.KeyStore.getDefaultType()     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
        L_0x0134:
            java.security.KeyStore r1 = java.security.KeyStore.getInstance(r1)     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
        L_0x0138:
            r4 = 0
            byte[] r5 = r9.trustStoreBytes     // Catch:{ all -> 0x0183 }
            if (r5 == 0) goto L_0x0146
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch:{ all -> 0x0183 }
            byte[] r5 = r9.trustStoreBytes     // Catch:{ all -> 0x0183 }
            r2.<init>(r5)     // Catch:{ all -> 0x0183 }
            r4 = r2
            goto L_0x0156
        L_0x0146:
            java.lang.String r5 = r9.trustStoreFile     // Catch:{ all -> 0x0183 }
            boolean r2 = r5.equals(r2)     // Catch:{ all -> 0x0183 }
            if (r2 != 0) goto L_0x0156
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ all -> 0x0183 }
            java.lang.String r5 = r9.trustStoreFile     // Catch:{ all -> 0x0183 }
            r2.<init>(r5)     // Catch:{ all -> 0x0183 }
            r4 = r2
        L_0x0156:
            char[] r2 = r9.trustStorePassword     // Catch:{ all -> 0x0183 }
            r1.load(r4, r2)     // Catch:{ all -> 0x0183 }
            if (r4 == 0) goto L_0x016a
            r4.close()     // Catch:{ IOException -> 0x0161, KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
            goto L_0x016a
        L_0x0161:
            r2 = move-exception
            java.util.logging.Logger r5 = LOGGER     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
            java.util.logging.Level r6 = java.util.logging.Level.FINEST     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
            r5.log(r6, r3, r2)     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
            goto L_0x016b
        L_0x016a:
        L_0x016b:
            java.lang.String r2 = r9.trustManagerFactoryAlgorithm     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
            if (r2 != 0) goto L_0x017a
            java.lang.String r3 = "ssl.TrustManagerFactory.algorithm"
            java.lang.String r5 = javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm()     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
            java.lang.String r3 = java.lang.System.getProperty(r3, r5)     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
            r2 = r3
        L_0x017a:
            javax.net.ssl.TrustManagerFactory r3 = javax.net.ssl.TrustManagerFactory.getInstance(r2)     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
            r3.init(r1)     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
            goto L_0x01ff
        L_0x0183:
            r2 = move-exception
            if (r4 == 0) goto L_0x0193
            r4.close()     // Catch:{ IOException -> 0x018a, KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
            goto L_0x0193
        L_0x018a:
            r5 = move-exception
            java.util.logging.Logger r6 = LOGGER     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
            java.util.logging.Level r7 = java.util.logging.Level.FINEST     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
            r6.log(r7, r3, r5)     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
            goto L_0x0194
        L_0x0193:
        L_0x0194:
            throw r2     // Catch:{ KeyStoreException -> 0x01f4, CertificateException -> 0x01e8, FileNotFoundException -> 0x01cb, IOException -> 0x01ae, NoSuchAlgorithmException -> 0x01a2, NoSuchProviderException -> 0x0196 }
        L_0x0196:
            r1 = move-exception
            java.util.logging.Logger r2 = LOGGER
            java.util.logging.Level r3 = java.util.logging.Level.FINE
            java.lang.String r4 = "Error initializing trust store (no such provider)"
            r2.log(r3, r4, r1)
            r0 = 0
            goto L_0x0200
        L_0x01a2:
            r1 = move-exception
            java.util.logging.Logger r2 = LOGGER
            java.util.logging.Level r3 = java.util.logging.Level.FINE
            java.lang.String r4 = "Error initializing trust manager factory (no such algorithm)"
            r2.log(r3, r4, r1)
            r0 = 0
            goto L_0x01ff
        L_0x01ae:
            r1 = move-exception
            java.util.logging.Logger r2 = LOGGER
            java.util.logging.Level r3 = java.util.logging.Level.FINE
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Error loading trust store from file: "
            r4.append(r5)
            java.lang.String r5 = r9.trustStoreFile
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r2.log(r3, r4, r1)
            r0 = 0
            goto L_0x01ff
        L_0x01cb:
            r1 = move-exception
            java.util.logging.Logger r2 = LOGGER
            java.util.logging.Level r3 = java.util.logging.Level.FINE
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Can't find trust store file: "
            r4.append(r5)
            java.lang.String r5 = r9.trustStoreFile
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r2.log(r3, r4, r1)
            r0 = 0
            goto L_0x01ff
        L_0x01e8:
            r1 = move-exception
            java.util.logging.Logger r2 = LOGGER
            java.util.logging.Level r3 = java.util.logging.Level.FINE
            java.lang.String r4 = "Trust store certificate exception."
            r2.log(r3, r4, r1)
            r0 = 0
            goto L_0x01ff
        L_0x01f4:
            r1 = move-exception
            java.util.logging.Logger r2 = LOGGER
            java.util.logging.Level r3 = java.util.logging.Level.FINE
            java.lang.String r4 = "Error initializing trust store"
            r2.log(r3, r4, r1)
            r0 = 0
        L_0x01ff:
        L_0x0200:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.tyrus.client.SslContextConfigurator.validateConfiguration(boolean):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:103:0x017c, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x017d, code lost:
        if (r5 != null) goto L_0x017f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x0183, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:?, code lost:
        LOGGER.log(java.util.logging.Level.FINEST, "Could not close trust store file", r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x018e, code lost:
        throw r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x018f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:?, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Error initializing trust store (no such provider)", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x019a, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x019b, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Error initializing trust manager factory (no such algorithm)", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x01a5, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x01a6, code lost:
        r1 = LOGGER;
        r5 = java.util.logging.Level.FINE;
        r1.log(r5, "Error loading trust store from file: " + r12.trustStoreFile, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x01c1, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x01c2, code lost:
        r1 = LOGGER;
        r5 = java.util.logging.Level.FINE;
        r1.log(r5, "Can't find trust store file: " + r12.trustStoreFile, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x01dd, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x01de, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Trust store certificate exception.", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x01e8, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x01e9, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Error initializing trust store", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x021f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x0220, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Key management error.", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x005b, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        LOGGER.log(java.util.logging.Level.FINEST, "Could not close key store file", r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0085, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0086, code lost:
        if (r7 != null) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        r7.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x008c, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
        LOGGER.log(java.util.logging.Level.FINEST, "Could not close key store file", r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0097, code lost:
        throw r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0098, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Error initializing key store (no such provider)", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00a3, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00a4, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Error initializing key manager factory (no such algorithm)", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00ae, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00af, code lost:
        r5 = LOGGER;
        r7 = java.util.logging.Level.FINE;
        r5.log(r7, "Error loading key store from file: " + r12.keyStoreFile, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00ca, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00cb, code lost:
        r5 = LOGGER;
        r7 = java.util.logging.Level.FINE;
        r5.log(r7, "Can't find key store file: " + r12.keyStoreFile, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00e6, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00e7, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Key store unrecoverable exception.", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00f1, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00f2, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Key store certificate exception.", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00fc, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00fd, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Error initializing key store", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x000f, code lost:
        if (r12.keyStoreFile != null) goto L_0x0011;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0159, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:?, code lost:
        LOGGER.log(java.util.logging.Level.FINEST, "Could not close trust store file", r6);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x018f A[ExcHandler: NoSuchProviderException (r0v11 'e' java.security.NoSuchProviderException A[CUSTOM_DECLARE]), Splitter:B:71:0x010f] */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x019a A[Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }, ExcHandler: NoSuchAlgorithmException (r0v10 'e' java.security.NoSuchAlgorithmException A[CUSTOM_DECLARE, Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }]), Splitter:B:71:0x010f] */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x01dd A[Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }, ExcHandler: CertificateException (r0v7 'e' java.security.cert.CertificateException A[CUSTOM_DECLARE, Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }]), Splitter:B:71:0x010f] */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x01e8 A[Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }, ExcHandler: KeyStoreException (r0v6 'e' java.security.KeyStoreException A[CUSTOM_DECLARE, Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }]), Splitter:B:71:0x010f] */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x021f A[ExcHandler: KeyManagementException (r0v1 'e' java.security.KeyManagementException A[CUSTOM_DECLARE]), Splitter:B:1:0x0007] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0098 A[ExcHandler: NoSuchProviderException (r1v30 'e' java.security.NoSuchProviderException A[CUSTOM_DECLARE]), Splitter:B:8:0x0011] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00a3 A[Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }, ExcHandler: NoSuchAlgorithmException (r1v29 'e' java.security.NoSuchAlgorithmException A[CUSTOM_DECLARE, Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }]), Splitter:B:8:0x0011] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00e6 A[Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }, ExcHandler: UnrecoverableKeyException (r1v26 'e' java.security.UnrecoverableKeyException A[CUSTOM_DECLARE, Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }]), Splitter:B:8:0x0011] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00f1 A[Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }, ExcHandler: CertificateException (r1v25 'e' java.security.cert.CertificateException A[CUSTOM_DECLARE, Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }]), Splitter:B:8:0x0011] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00fc A[Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }, ExcHandler: KeyStoreException (r1v24 'e' java.security.KeyStoreException A[CUSTOM_DECLARE, Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }]), Splitter:B:8:0x0011] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public javax.net.ssl.SSLContext createSSLContext() {
        /*
            r12 = this;
            java.lang.String r0 = "Could not close trust store file"
            java.lang.String r1 = "Could not close key store file"
            r2 = 0
            r3 = 0
            r4 = 0
            byte[] r5 = r12.keyStoreBytes     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r6 = "NONE"
            if (r5 != 0) goto L_0x0011
            java.lang.String r5 = r12.keyStoreFile     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            if (r5 == 0) goto L_0x0107
        L_0x0011:
            java.lang.String r5 = r12.keyStoreProvider     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            if (r5 == 0) goto L_0x0025
            java.lang.String r5 = r12.keyStoreType     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            if (r5 == 0) goto L_0x001a
            goto L_0x001e
        L_0x001a:
            java.lang.String r5 = java.security.KeyStore.getDefaultType()     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
        L_0x001e:
            java.lang.String r7 = r12.keyStoreProvider     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            java.security.KeyStore r5 = java.security.KeyStore.getInstance(r5, r7)     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            goto L_0x0032
        L_0x0025:
            java.lang.String r5 = r12.keyStoreType     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            if (r5 == 0) goto L_0x002a
            goto L_0x002e
        L_0x002a:
            java.lang.String r5 = java.security.KeyStore.getDefaultType()     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
        L_0x002e:
            java.security.KeyStore r5 = java.security.KeyStore.getInstance(r5)     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
        L_0x0032:
            r7 = 0
            byte[] r8 = r12.keyStoreBytes     // Catch:{ all -> 0x0085 }
            if (r8 == 0) goto L_0x0040
            java.io.ByteArrayInputStream r8 = new java.io.ByteArrayInputStream     // Catch:{ all -> 0x0085 }
            byte[] r9 = r12.keyStoreBytes     // Catch:{ all -> 0x0085 }
            r8.<init>(r9)     // Catch:{ all -> 0x0085 }
            r7 = r8
            goto L_0x0050
        L_0x0040:
            java.lang.String r8 = r12.keyStoreFile     // Catch:{ all -> 0x0085 }
            boolean r8 = r8.equals(r6)     // Catch:{ all -> 0x0085 }
            if (r8 != 0) goto L_0x0050
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch:{ all -> 0x0085 }
            java.lang.String r9 = r12.keyStoreFile     // Catch:{ all -> 0x0085 }
            r8.<init>(r9)     // Catch:{ all -> 0x0085 }
            r7 = r8
        L_0x0050:
            char[] r8 = r12.keyStorePassword     // Catch:{ all -> 0x0085 }
            r5.load(r7, r8)     // Catch:{ all -> 0x0085 }
            if (r7 == 0) goto L_0x0064
            r7.close()     // Catch:{ IOException -> 0x005b, KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            goto L_0x0064
        L_0x005b:
            r8 = move-exception
            java.util.logging.Logger r9 = LOGGER     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            java.util.logging.Level r10 = java.util.logging.Level.FINEST     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            r9.log(r10, r1, r8)     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            goto L_0x0065
        L_0x0064:
        L_0x0065:
            java.lang.String r1 = r12.keyManagerFactoryAlgorithm     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            if (r1 != 0) goto L_0x0074
            java.lang.String r8 = "ssl.KeyManagerFactory.algorithm"
            java.lang.String r9 = javax.net.ssl.KeyManagerFactory.getDefaultAlgorithm()     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            java.lang.String r8 = java.lang.System.getProperty(r8, r9)     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            r1 = r8
        L_0x0074:
            javax.net.ssl.KeyManagerFactory r8 = javax.net.ssl.KeyManagerFactory.getInstance(r1)     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            r4 = r8
            char[] r8 = r12.keyPassword     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            if (r8 == 0) goto L_0x007e
            goto L_0x0080
        L_0x007e:
            char[] r8 = r12.keyStorePassword     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
        L_0x0080:
            r4.init(r5, r8)     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            goto L_0x0106
        L_0x0085:
            r8 = move-exception
            if (r7 == 0) goto L_0x0095
            r7.close()     // Catch:{ IOException -> 0x008c, KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            goto L_0x0095
        L_0x008c:
            r9 = move-exception
            java.util.logging.Logger r10 = LOGGER     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            java.util.logging.Level r11 = java.util.logging.Level.FINEST     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            r10.log(r11, r1, r9)     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
            goto L_0x0096
        L_0x0095:
        L_0x0096:
            throw r8     // Catch:{ KeyStoreException -> 0x00fc, CertificateException -> 0x00f1, UnrecoverableKeyException -> 0x00e6, FileNotFoundException -> 0x00ca, IOException -> 0x00ae, NoSuchAlgorithmException -> 0x00a3, NoSuchProviderException -> 0x0098, KeyManagementException -> 0x021f }
        L_0x0098:
            r1 = move-exception
            java.util.logging.Logger r5 = LOGGER     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.util.logging.Level r7 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r8 = "Error initializing key store (no such provider)"
            r5.log(r7, r8, r1)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            goto L_0x0107
        L_0x00a3:
            r1 = move-exception
            java.util.logging.Logger r5 = LOGGER     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.util.logging.Level r7 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r8 = "Error initializing key manager factory (no such algorithm)"
            r5.log(r7, r8, r1)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            goto L_0x0106
        L_0x00ae:
            r1 = move-exception
            java.util.logging.Logger r5 = LOGGER     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.util.logging.Level r7 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            r8.<init>()     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r9 = "Error loading key store from file: "
            r8.append(r9)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r9 = r12.keyStoreFile     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            r8.append(r9)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r8 = r8.toString()     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            r5.log(r7, r8, r1)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            goto L_0x0106
        L_0x00ca:
            r1 = move-exception
            java.util.logging.Logger r5 = LOGGER     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.util.logging.Level r7 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            r8.<init>()     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r9 = "Can't find key store file: "
            r8.append(r9)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r9 = r12.keyStoreFile     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            r8.append(r9)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r8 = r8.toString()     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            r5.log(r7, r8, r1)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            goto L_0x0106
        L_0x00e6:
            r1 = move-exception
            java.util.logging.Logger r5 = LOGGER     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.util.logging.Level r7 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r8 = "Key store unrecoverable exception."
            r5.log(r7, r8, r1)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            goto L_0x0106
        L_0x00f1:
            r1 = move-exception
            java.util.logging.Logger r5 = LOGGER     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.util.logging.Level r7 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r8 = "Key store certificate exception."
            r5.log(r7, r8, r1)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            goto L_0x0106
        L_0x00fc:
            r1 = move-exception
            java.util.logging.Logger r5 = LOGGER     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.util.logging.Level r7 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r8 = "Error initializing key store"
            r5.log(r7, r8, r1)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
        L_0x0106:
        L_0x0107:
            byte[] r1 = r12.trustStoreBytes     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            if (r1 != 0) goto L_0x010f
            java.lang.String r1 = r12.trustStoreFile     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            if (r1 == 0) goto L_0x01f3
        L_0x010f:
            java.lang.String r1 = r12.trustStoreProvider     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
            if (r1 == 0) goto L_0x0123
            java.lang.String r1 = r12.trustStoreType     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
            if (r1 == 0) goto L_0x0118
            goto L_0x011c
        L_0x0118:
            java.lang.String r1 = java.security.KeyStore.getDefaultType()     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
        L_0x011c:
            java.lang.String r5 = r12.trustStoreProvider     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
            java.security.KeyStore r1 = java.security.KeyStore.getInstance(r1, r5)     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
            goto L_0x0130
        L_0x0123:
            java.lang.String r1 = r12.trustStoreType     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
            if (r1 == 0) goto L_0x0128
            goto L_0x012c
        L_0x0128:
            java.lang.String r1 = java.security.KeyStore.getDefaultType()     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
        L_0x012c:
            java.security.KeyStore r1 = java.security.KeyStore.getInstance(r1)     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
        L_0x0130:
            r5 = 0
            byte[] r7 = r12.trustStoreBytes     // Catch:{ all -> 0x017c }
            if (r7 == 0) goto L_0x013e
            java.io.ByteArrayInputStream r6 = new java.io.ByteArrayInputStream     // Catch:{ all -> 0x017c }
            byte[] r7 = r12.trustStoreBytes     // Catch:{ all -> 0x017c }
            r6.<init>(r7)     // Catch:{ all -> 0x017c }
            r5 = r6
            goto L_0x014e
        L_0x013e:
            java.lang.String r7 = r12.trustStoreFile     // Catch:{ all -> 0x017c }
            boolean r6 = r7.equals(r6)     // Catch:{ all -> 0x017c }
            if (r6 != 0) goto L_0x014e
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ all -> 0x017c }
            java.lang.String r7 = r12.trustStoreFile     // Catch:{ all -> 0x017c }
            r6.<init>(r7)     // Catch:{ all -> 0x017c }
            r5 = r6
        L_0x014e:
            char[] r6 = r12.trustStorePassword     // Catch:{ all -> 0x017c }
            r1.load(r5, r6)     // Catch:{ all -> 0x017c }
            if (r5 == 0) goto L_0x0162
            r5.close()     // Catch:{ IOException -> 0x0159, KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
            goto L_0x0162
        L_0x0159:
            r6 = move-exception
            java.util.logging.Logger r7 = LOGGER     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
            java.util.logging.Level r8 = java.util.logging.Level.FINEST     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
            r7.log(r8, r0, r6)     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
            goto L_0x0163
        L_0x0162:
        L_0x0163:
            java.lang.String r0 = r12.trustManagerFactoryAlgorithm     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
            if (r0 != 0) goto L_0x0172
            java.lang.String r6 = "ssl.TrustManagerFactory.algorithm"
            java.lang.String r7 = javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm()     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
            java.lang.String r6 = java.lang.System.getProperty(r6, r7)     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
            r0 = r6
        L_0x0172:
            javax.net.ssl.TrustManagerFactory r6 = javax.net.ssl.TrustManagerFactory.getInstance(r0)     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
            r3 = r6
            r3.init(r1)     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
            goto L_0x01f2
        L_0x017c:
            r6 = move-exception
            if (r5 == 0) goto L_0x018c
            r5.close()     // Catch:{ IOException -> 0x0183, KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
            goto L_0x018c
        L_0x0183:
            r7 = move-exception
            java.util.logging.Logger r8 = LOGGER     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
            java.util.logging.Level r9 = java.util.logging.Level.FINEST     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
            r8.log(r9, r0, r7)     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
            goto L_0x018d
        L_0x018c:
        L_0x018d:
            throw r6     // Catch:{ KeyStoreException -> 0x01e8, CertificateException -> 0x01dd, FileNotFoundException -> 0x01c1, IOException -> 0x01a5, NoSuchAlgorithmException -> 0x019a, NoSuchProviderException -> 0x018f, KeyManagementException -> 0x021f }
        L_0x018f:
            r0 = move-exception
            java.util.logging.Logger r1 = LOGGER     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r6 = "Error initializing trust store (no such provider)"
            r1.log(r5, r6, r0)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            goto L_0x01f3
        L_0x019a:
            r0 = move-exception
            java.util.logging.Logger r1 = LOGGER     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r6 = "Error initializing trust manager factory (no such algorithm)"
            r1.log(r5, r6, r0)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            goto L_0x01f2
        L_0x01a5:
            r0 = move-exception
            java.util.logging.Logger r1 = LOGGER     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            r6.<init>()     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r7 = "Error loading trust store from file: "
            r6.append(r7)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r7 = r12.trustStoreFile     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            r6.append(r7)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r6 = r6.toString()     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            r1.log(r5, r6, r0)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            goto L_0x01f2
        L_0x01c1:
            r0 = move-exception
            java.util.logging.Logger r1 = LOGGER     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            r6.<init>()     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r7 = "Can't find trust store file: "
            r6.append(r7)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r7 = r12.trustStoreFile     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            r6.append(r7)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r6 = r6.toString()     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            r1.log(r5, r6, r0)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            goto L_0x01f2
        L_0x01dd:
            r0 = move-exception
            java.util.logging.Logger r1 = LOGGER     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r6 = "Trust store certificate exception."
            r1.log(r5, r6, r0)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            goto L_0x01f2
        L_0x01e8:
            r0 = move-exception
            java.util.logging.Logger r1 = LOGGER     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            java.lang.String r6 = "Error initializing trust store"
            r1.log(r5, r6, r0)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
        L_0x01f2:
        L_0x01f3:
            java.lang.String r0 = "TLS"
            java.lang.String r1 = r12.securityProtocol     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            if (r1 == 0) goto L_0x01fa
            r0 = r1
        L_0x01fa:
            javax.net.ssl.SSLContext r1 = javax.net.ssl.SSLContext.getInstance(r0)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            r2 = r1
            r1 = 0
            if (r4 == 0) goto L_0x0207
            javax.net.ssl.KeyManager[] r5 = r4.getKeyManagers()     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            goto L_0x0208
        L_0x0207:
            r5 = r1
        L_0x0208:
            if (r3 == 0) goto L_0x020f
            javax.net.ssl.TrustManager[] r6 = r3.getTrustManagers()     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            goto L_0x0210
        L_0x020f:
            r6 = r1
        L_0x0210:
            r2.init(r5, r6, r1)     // Catch:{ KeyManagementException -> 0x021f, NoSuchAlgorithmException -> 0x0214 }
            goto L_0x0229
        L_0x0214:
            r0 = move-exception
            java.util.logging.Logger r1 = LOGGER
            java.util.logging.Level r3 = java.util.logging.Level.FINE
            java.lang.String r4 = "Error initializing algorithm."
            r1.log(r3, r4, r0)
            goto L_0x022a
        L_0x021f:
            r0 = move-exception
            java.util.logging.Logger r1 = LOGGER
            java.util.logging.Level r3 = java.util.logging.Level.FINE
            java.lang.String r4 = "Key management error."
            r1.log(r3, r4, r0)
        L_0x0229:
        L_0x022a:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.tyrus.client.SslContextConfigurator.createSSLContext():javax.net.ssl.SSLContext");
    }

    public SslContextConfigurator retrieve(Properties props) {
        this.trustStoreProvider = props.getProperty("javax.net.ssl.trustStoreProvider");
        this.keyStoreProvider = props.getProperty("javax.net.ssl.keyStoreProvider");
        this.trustStoreType = props.getProperty("javax.net.ssl.trustStoreType");
        this.keyStoreType = props.getProperty("javax.net.ssl.keyStoreType");
        if (props.getProperty("javax.net.ssl.trustStorePassword") != null) {
            this.trustStorePassword = props.getProperty("javax.net.ssl.trustStorePassword").toCharArray();
        } else {
            this.trustStorePassword = null;
        }
        if (props.getProperty("javax.net.ssl.keyStorePassword") != null) {
            this.keyStorePassword = props.getProperty("javax.net.ssl.keyStorePassword").toCharArray();
        } else {
            this.keyStorePassword = null;
        }
        this.trustStoreFile = props.getProperty("javax.net.ssl.trustStore");
        this.keyStoreFile = props.getProperty("javax.net.ssl.keyStore");
        this.trustStoreBytes = null;
        this.keyStoreBytes = null;
        this.securityProtocol = "TLS";
        return this;
    }
}
