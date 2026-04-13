package org.glassfish.grizzly.ssl;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import org.glassfish.grizzly.Grizzly;

public class SSLContextConfigurator {
    public static final SSLContextConfigurator DEFAULT_CONFIG = new SSLContextConfigurator();
    public static final String KEY_FACTORY_MANAGER_ALGORITHM = "ssl.KeyManagerFactory.algorithm";
    public static final String KEY_STORE_FILE = "javax.net.ssl.keyStore";
    public static final String KEY_STORE_PASSWORD = "javax.net.ssl.keyStorePassword";
    public static final String KEY_STORE_PROVIDER = "javax.net.ssl.keyStoreProvider";
    public static final String KEY_STORE_TYPE = "javax.net.ssl.keyStoreType";
    private static final Logger LOGGER = Grizzly.logger(SSLContextConfigurator.class);
    public static final String TRUST_FACTORY_MANAGER_ALGORITHM = "ssl.TrustManagerFactory.algorithm";
    public static final String TRUST_STORE_FILE = "javax.net.ssl.trustStore";
    public static final String TRUST_STORE_PASSWORD = "javax.net.ssl.trustStorePassword";
    public static final String TRUST_STORE_PROVIDER = "javax.net.ssl.trustStoreProvider";
    public static final String TRUST_STORE_TYPE = "javax.net.ssl.trustStoreType";
    private String keyManagerFactoryAlgorithm;
    private char[] keyPass;
    private byte[] keyStoreBytes;
    private String keyStoreFile;
    private char[] keyStorePass;
    private String keyStoreProvider;
    private String keyStoreType;
    private String securityProtocol;
    private String trustManagerFactoryAlgorithm;
    private byte[] trustStoreBytes;
    private String trustStoreFile;
    private char[] trustStorePass;
    private String trustStoreProvider;
    private String trustStoreType;

    public SSLContextConfigurator() {
        this(true);
    }

    public SSLContextConfigurator(boolean readSystemProperties) {
        this.securityProtocol = "TLS";
        if (readSystemProperties) {
            retrieve(System.getProperties());
        }
    }

    public void setTrustStoreProvider(String trustStoreProvider2) {
        this.trustStoreProvider = trustStoreProvider2;
    }

    public void setKeyStoreProvider(String keyStoreProvider2) {
        this.keyStoreProvider = keyStoreProvider2;
    }

    public void setTrustStoreType(String trustStoreType2) {
        this.trustStoreType = trustStoreType2;
    }

    public void setKeyStoreType(String keyStoreType2) {
        this.keyStoreType = keyStoreType2;
    }

    public void setTrustStorePass(String trustStorePass2) {
        this.trustStorePass = trustStorePass2.toCharArray();
    }

    public void setKeyStorePass(String keyStorePass2) {
        this.keyStorePass = keyStorePass2.toCharArray();
    }

    public void setKeyStorePass(char[] keyStorePass2) {
        this.keyStorePass = keyStorePass2;
    }

    public void setKeyPass(String keyPass2) {
        this.keyPass = keyPass2.toCharArray();
    }

    public void setKeyPass(char[] keyPass2) {
        this.keyPass = keyPass2;
    }

    public void setTrustStoreFile(String trustStoreFile2) {
        this.trustStoreFile = trustStoreFile2;
        this.trustStoreBytes = null;
    }

    public void setTrustStoreBytes(byte[] trustStoreBytes2) {
        this.trustStoreBytes = trustStoreBytes2;
        this.trustStoreFile = null;
    }

    public void setKeyStoreFile(String keyStoreFile2) {
        this.keyStoreFile = keyStoreFile2;
        this.keyStoreBytes = null;
    }

    public void setKeyStoreBytes(byte[] keyStoreBytes2) {
        this.keyStoreBytes = keyStoreBytes2;
        this.keyStoreFile = null;
    }

    public void setTrustManagerFactoryAlgorithm(String trustManagerFactoryAlgorithm2) {
        this.trustManagerFactoryAlgorithm = trustManagerFactoryAlgorithm2;
    }

    public void setKeyManagerFactoryAlgorithm(String keyManagerFactoryAlgorithm2) {
        this.keyManagerFactoryAlgorithm = keyManagerFactoryAlgorithm2;
    }

    public void setSecurityProtocol(String securityProtocol2) {
        this.securityProtocol = securityProtocol2;
    }

    @Deprecated
    public boolean validateConfiguration() {
        return validateConfiguration(false);
    }

    @Deprecated
    public boolean validateConfiguration(boolean needsKeyStore) {
        KeyStore trustStore;
        KeyStore keyStore;
        boolean valid = true;
        if (this.keyStoreBytes == null && this.keyStoreFile == null) {
            valid = !needsKeyStore;
        } else {
            try {
                if (this.keyStoreProvider != null) {
                    String str = this.keyStoreType;
                    if (str == null) {
                        str = KeyStore.getDefaultType();
                    }
                    keyStore = KeyStore.getInstance(str, this.keyStoreProvider);
                } else {
                    String str2 = this.keyStoreType;
                    if (str2 == null) {
                        str2 = KeyStore.getDefaultType();
                    }
                    keyStore = KeyStore.getInstance(str2);
                }
                loadBytes(this.keyStoreBytes, this.keyStoreFile, this.keyStorePass, keyStore);
                String kmfAlgorithm = this.keyManagerFactoryAlgorithm;
                if (kmfAlgorithm == null) {
                    kmfAlgorithm = System.getProperty("ssl.KeyManagerFactory.algorithm", KeyManagerFactory.getDefaultAlgorithm());
                }
                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(kmfAlgorithm);
                char[] cArr = this.keyPass;
                if (cArr == null) {
                    cArr = this.keyStorePass;
                }
                keyManagerFactory.init(keyStore, cArr);
            } catch (KeyStoreException e) {
                LOGGER.log(Level.FINE, "Error initializing key store", e);
                valid = false;
            } catch (CertificateException e2) {
                LOGGER.log(Level.FINE, "Key store certificate exception.", e2);
                valid = false;
            } catch (UnrecoverableKeyException e3) {
                LOGGER.log(Level.FINE, "Key store unrecoverable exception.", e3);
                valid = false;
            } catch (FileNotFoundException e4) {
                LOGGER.log(Level.FINE, "Can't find key store file: " + this.keyStoreFile, e4);
                valid = false;
            } catch (IOException e5) {
                LOGGER.log(Level.FINE, "Error loading key store from file: " + this.keyStoreFile, e5);
                valid = false;
            } catch (NoSuchAlgorithmException e6) {
                LOGGER.log(Level.FINE, "Error initializing key manager factory (no such algorithm)", e6);
                valid = false;
            } catch (NoSuchProviderException e7) {
                LOGGER.log(Level.FINE, "Error initializing key store (no such provider)", e7);
                valid = false;
            }
        }
        if (this.trustStoreBytes == null && this.trustStoreFile == null) {
            return valid;
        }
        try {
            if (this.trustStoreProvider != null) {
                String str3 = this.trustStoreType;
                if (str3 == null) {
                    str3 = KeyStore.getDefaultType();
                }
                trustStore = KeyStore.getInstance(str3, this.trustStoreProvider);
            } else {
                String str4 = this.trustStoreType;
                if (str4 == null) {
                    str4 = KeyStore.getDefaultType();
                }
                trustStore = KeyStore.getInstance(str4);
            }
            loadBytes(this.trustStoreBytes, this.trustStoreFile, this.trustStorePass, trustStore);
            String tmfAlgorithm = this.trustManagerFactoryAlgorithm;
            if (tmfAlgorithm == null) {
                tmfAlgorithm = System.getProperty("ssl.TrustManagerFactory.algorithm", TrustManagerFactory.getDefaultAlgorithm());
            }
            TrustManagerFactory.getInstance(tmfAlgorithm).init(trustStore);
            return valid;
        } catch (KeyStoreException e8) {
            LOGGER.log(Level.FINE, "Error initializing trust store", e8);
            return false;
        } catch (CertificateException e9) {
            LOGGER.log(Level.FINE, "Trust store certificate exception.", e9);
            return false;
        } catch (FileNotFoundException e10) {
            LOGGER.log(Level.FINE, "Can't find trust store file: " + this.trustStoreFile, e10);
            return false;
        } catch (IOException e11) {
            LOGGER.log(Level.FINE, "Error loading trust store from file: " + this.trustStoreFile, e11);
            return false;
        } catch (NoSuchAlgorithmException e12) {
            LOGGER.log(Level.FINE, "Error initializing trust manager factory (no such algorithm)", e12);
            return false;
        } catch (NoSuchProviderException e13) {
            LOGGER.log(Level.FINE, "Error initializing trust store (no such provider)", e13);
            return false;
        }
    }

    @Deprecated
    public SSLContext createSSLContext() {
        return createSSLContext(false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:138:0x020a, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x020b, code lost:
        LOGGER.log(java.util.logging.Level.FINE, "Key management error.", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x0214, code lost:
        if (r9 != false) goto L_0x0218;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x021d, code lost:
        throw new org.glassfish.grizzly.ssl.SSLContextConfigurator.GenericStoreException(r1);
     */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x020a A[ExcHandler: KeyManagementException (r1v1 'e' java.security.KeyManagementException A[CUSTOM_DECLARE]), Splitter:B:1:0x0003] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public javax.net.ssl.SSLContext createSSLContext(boolean r9) {
        /*
            r8 = this;
            r0 = 0
            r1 = 0
            r2 = 0
            byte[] r3 = r8.keyStoreBytes     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            if (r3 != 0) goto L_0x000b
            java.lang.String r3 = r8.keyStoreFile     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            if (r3 == 0) goto L_0x00f0
        L_0x000b:
            java.lang.String r3 = r8.keyStoreProvider     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
            if (r3 == 0) goto L_0x001f
            java.lang.String r3 = r8.keyStoreType     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
            if (r3 == 0) goto L_0x0014
            goto L_0x0018
        L_0x0014:
            java.lang.String r3 = java.security.KeyStore.getDefaultType()     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
        L_0x0018:
            java.lang.String r4 = r8.keyStoreProvider     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
            java.security.KeyStore r3 = java.security.KeyStore.getInstance(r3, r4)     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
            goto L_0x002c
        L_0x001f:
            java.lang.String r3 = r8.keyStoreType     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
            if (r3 == 0) goto L_0x0024
            goto L_0x0028
        L_0x0024:
            java.lang.String r3 = java.security.KeyStore.getDefaultType()     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
        L_0x0028:
            java.security.KeyStore r3 = java.security.KeyStore.getInstance(r3)     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
        L_0x002c:
            byte[] r4 = r8.keyStoreBytes     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
            java.lang.String r5 = r8.keyStoreFile     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
            char[] r6 = r8.keyStorePass     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
            loadBytes(r4, r5, r6, r3)     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
            java.lang.String r4 = r8.keyManagerFactoryAlgorithm     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
            if (r4 != 0) goto L_0x0044
            java.lang.String r5 = "ssl.KeyManagerFactory.algorithm"
            java.lang.String r6 = javax.net.ssl.KeyManagerFactory.getDefaultAlgorithm()     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
            java.lang.String r5 = java.lang.System.getProperty(r5, r6)     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
            r4 = r5
        L_0x0044:
            javax.net.ssl.KeyManagerFactory r5 = javax.net.ssl.KeyManagerFactory.getInstance(r4)     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
            r2 = r5
            char[] r5 = r8.keyPass     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
            if (r5 == 0) goto L_0x004e
            goto L_0x0050
        L_0x004e:
            char[] r5 = r8.keyStorePass     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
        L_0x0050:
            r2.init(r3, r5)     // Catch:{ KeyStoreException -> 0x00e3, CertificateException -> 0x00d0, UnrecoverableKeyException -> 0x00bd, FileNotFoundException -> 0x0099, IOException -> 0x0075, NoSuchAlgorithmException -> 0x0061, NoSuchProviderException -> 0x0055, KeyManagementException -> 0x020a }
            goto L_0x00ef
        L_0x0055:
            r3 = move-exception
            java.util.logging.Logger r4 = LOGGER     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r6 = "Error initializing key store (no such provider)"
            r4.log(r5, r6, r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            goto L_0x00f0
        L_0x0061:
            r3 = move-exception
            java.util.logging.Logger r4 = LOGGER     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r6 = "Error initializing key manager factory (no such algorithm)"
            r4.log(r5, r6, r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            if (r9 != 0) goto L_0x006f
            goto L_0x00ef
        L_0x006f:
            org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException r4 = new org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r4.<init>(r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            throw r4     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
        L_0x0075:
            r3 = move-exception
            java.util.logging.Logger r4 = LOGGER     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r6.<init>()     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r7 = "Error loading key store from file: "
            r6.append(r7)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r7 = r8.keyStoreFile     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r6.append(r7)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r6 = r6.toString()     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r4.log(r5, r6, r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            if (r9 != 0) goto L_0x0093
            goto L_0x00ef
        L_0x0093:
            org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException r4 = new org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r4.<init>(r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            throw r4     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
        L_0x0099:
            r3 = move-exception
            java.util.logging.Logger r4 = LOGGER     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r6.<init>()     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r7 = "Can't find key store file: "
            r6.append(r7)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r7 = r8.keyStoreFile     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r6.append(r7)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r6 = r6.toString()     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r4.log(r5, r6, r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            if (r9 != 0) goto L_0x00b7
            goto L_0x00ef
        L_0x00b7:
            org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException r4 = new org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r4.<init>(r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            throw r4     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
        L_0x00bd:
            r3 = move-exception
            java.util.logging.Logger r4 = LOGGER     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r6 = "Key store unrecoverable exception."
            r4.log(r5, r6, r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            if (r9 != 0) goto L_0x00ca
            goto L_0x00ef
        L_0x00ca:
            org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException r4 = new org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r4.<init>(r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            throw r4     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
        L_0x00d0:
            r3 = move-exception
            java.util.logging.Logger r4 = LOGGER     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r6 = "Key store certificate exception."
            r4.log(r5, r6, r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            if (r9 != 0) goto L_0x00dd
            goto L_0x00ef
        L_0x00dd:
            org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException r4 = new org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r4.<init>(r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            throw r4     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
        L_0x00e3:
            r3 = move-exception
            java.util.logging.Logger r4 = LOGGER     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r6 = "Error initializing key store"
            r4.log(r5, r6, r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            if (r9 != 0) goto L_0x01f1
        L_0x00ef:
        L_0x00f0:
            byte[] r3 = r8.trustStoreBytes     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            if (r3 != 0) goto L_0x00f8
            java.lang.String r3 = r8.trustStoreFile     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            if (r3 == 0) goto L_0x01ca
        L_0x00f8:
            java.lang.String r3 = r8.trustStoreProvider     // Catch:{ KeyStoreException -> 0x01bd, CertificateException -> 0x01aa, FileNotFoundException -> 0x0186, IOException -> 0x0162, NoSuchAlgorithmException -> 0x014f, NoSuchProviderException -> 0x013b, KeyManagementException -> 0x020a }
            if (r3 == 0) goto L_0x010c
            java.lang.String r3 = r8.trustStoreType     // Catch:{ KeyStoreException -> 0x01bd, CertificateException -> 0x01aa, FileNotFoundException -> 0x0186, IOException -> 0x0162, NoSuchAlgorithmException -> 0x014f, NoSuchProviderException -> 0x013b, KeyManagementException -> 0x020a }
            if (r3 == 0) goto L_0x0101
            goto L_0x0105
        L_0x0101:
            java.lang.String r3 = java.security.KeyStore.getDefaultType()     // Catch:{ KeyStoreException -> 0x01bd, CertificateException -> 0x01aa, FileNotFoundException -> 0x0186, IOException -> 0x0162, NoSuchAlgorithmException -> 0x014f, NoSuchProviderException -> 0x013b, KeyManagementException -> 0x020a }
        L_0x0105:
            java.lang.String r4 = r8.trustStoreProvider     // Catch:{ KeyStoreException -> 0x01bd, CertificateException -> 0x01aa, FileNotFoundException -> 0x0186, IOException -> 0x0162, NoSuchAlgorithmException -> 0x014f, NoSuchProviderException -> 0x013b, KeyManagementException -> 0x020a }
            java.security.KeyStore r3 = java.security.KeyStore.getInstance(r3, r4)     // Catch:{ KeyStoreException -> 0x01bd, CertificateException -> 0x01aa, FileNotFoundException -> 0x0186, IOException -> 0x0162, NoSuchAlgorithmException -> 0x014f, NoSuchProviderException -> 0x013b, KeyManagementException -> 0x020a }
            goto L_0x0119
        L_0x010c:
            java.lang.String r3 = r8.trustStoreType     // Catch:{ KeyStoreException -> 0x01bd, CertificateException -> 0x01aa, FileNotFoundException -> 0x0186, IOException -> 0x0162, NoSuchAlgorithmException -> 0x014f, NoSuchProviderException -> 0x013b, KeyManagementException -> 0x020a }
            if (r3 == 0) goto L_0x0111
            goto L_0x0115
        L_0x0111:
            java.lang.String r3 = java.security.KeyStore.getDefaultType()     // Catch:{ KeyStoreException -> 0x01bd, CertificateException -> 0x01aa, FileNotFoundException -> 0x0186, IOException -> 0x0162, NoSuchAlgorithmException -> 0x014f, NoSuchProviderException -> 0x013b, KeyManagementException -> 0x020a }
        L_0x0115:
            java.security.KeyStore r3 = java.security.KeyStore.getInstance(r3)     // Catch:{ KeyStoreException -> 0x01bd, CertificateException -> 0x01aa, FileNotFoundException -> 0x0186, IOException -> 0x0162, NoSuchAlgorithmException -> 0x014f, NoSuchProviderException -> 0x013b, KeyManagementException -> 0x020a }
        L_0x0119:
            byte[] r4 = r8.trustStoreBytes     // Catch:{ KeyStoreException -> 0x01bd, CertificateException -> 0x01aa, FileNotFoundException -> 0x0186, IOException -> 0x0162, NoSuchAlgorithmException -> 0x014f, NoSuchProviderException -> 0x013b, KeyManagementException -> 0x020a }
            java.lang.String r5 = r8.trustStoreFile     // Catch:{ KeyStoreException -> 0x01bd, CertificateException -> 0x01aa, FileNotFoundException -> 0x0186, IOException -> 0x0162, NoSuchAlgorithmException -> 0x014f, NoSuchProviderException -> 0x013b, KeyManagementException -> 0x020a }
            char[] r6 = r8.trustStorePass     // Catch:{ KeyStoreException -> 0x01bd, CertificateException -> 0x01aa, FileNotFoundException -> 0x0186, IOException -> 0x0162, NoSuchAlgorithmException -> 0x014f, NoSuchProviderException -> 0x013b, KeyManagementException -> 0x020a }
            loadBytes(r4, r5, r6, r3)     // Catch:{ KeyStoreException -> 0x01bd, CertificateException -> 0x01aa, FileNotFoundException -> 0x0186, IOException -> 0x0162, NoSuchAlgorithmException -> 0x014f, NoSuchProviderException -> 0x013b, KeyManagementException -> 0x020a }
            java.lang.String r4 = r8.trustManagerFactoryAlgorithm     // Catch:{ KeyStoreException -> 0x01bd, CertificateException -> 0x01aa, FileNotFoundException -> 0x0186, IOException -> 0x0162, NoSuchAlgorithmException -> 0x014f, NoSuchProviderException -> 0x013b, KeyManagementException -> 0x020a }
            if (r4 != 0) goto L_0x0131
            java.lang.String r5 = "ssl.TrustManagerFactory.algorithm"
            java.lang.String r6 = javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm()     // Catch:{ KeyStoreException -> 0x01bd, CertificateException -> 0x01aa, FileNotFoundException -> 0x0186, IOException -> 0x0162, NoSuchAlgorithmException -> 0x014f, NoSuchProviderException -> 0x013b, KeyManagementException -> 0x020a }
            java.lang.String r5 = java.lang.System.getProperty(r5, r6)     // Catch:{ KeyStoreException -> 0x01bd, CertificateException -> 0x01aa, FileNotFoundException -> 0x0186, IOException -> 0x0162, NoSuchAlgorithmException -> 0x014f, NoSuchProviderException -> 0x013b, KeyManagementException -> 0x020a }
            r4 = r5
        L_0x0131:
            javax.net.ssl.TrustManagerFactory r5 = javax.net.ssl.TrustManagerFactory.getInstance(r4)     // Catch:{ KeyStoreException -> 0x01bd, CertificateException -> 0x01aa, FileNotFoundException -> 0x0186, IOException -> 0x0162, NoSuchAlgorithmException -> 0x014f, NoSuchProviderException -> 0x013b, KeyManagementException -> 0x020a }
            r1 = r5
            r1.init(r3)     // Catch:{ KeyStoreException -> 0x01bd, CertificateException -> 0x01aa, FileNotFoundException -> 0x0186, IOException -> 0x0162, NoSuchAlgorithmException -> 0x014f, NoSuchProviderException -> 0x013b, KeyManagementException -> 0x020a }
            goto L_0x01c9
        L_0x013b:
            r3 = move-exception
            java.util.logging.Logger r4 = LOGGER     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r6 = "Error initializing trust store (no such provider)"
            r4.log(r5, r6, r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            if (r9 != 0) goto L_0x0149
            goto L_0x01ca
        L_0x0149:
            org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException r4 = new org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r4.<init>(r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            throw r4     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
        L_0x014f:
            r3 = move-exception
            java.util.logging.Logger r4 = LOGGER     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r6 = "Error initializing trust manager factory (no such algorithm)"
            r4.log(r5, r6, r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            if (r9 != 0) goto L_0x015c
            goto L_0x01c9
        L_0x015c:
            org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException r4 = new org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r4.<init>(r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            throw r4     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
        L_0x0162:
            r3 = move-exception
            java.util.logging.Logger r4 = LOGGER     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r6.<init>()     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r7 = "Error loading trust store from file: "
            r6.append(r7)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r7 = r8.trustStoreFile     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r6.append(r7)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r6 = r6.toString()     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r4.log(r5, r6, r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            if (r9 != 0) goto L_0x0180
            goto L_0x01c9
        L_0x0180:
            org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException r4 = new org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r4.<init>(r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            throw r4     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
        L_0x0186:
            r3 = move-exception
            java.util.logging.Logger r4 = LOGGER     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r6.<init>()     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r7 = "Can't find trust store file: "
            r6.append(r7)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r7 = r8.trustStoreFile     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r6.append(r7)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r6 = r6.toString()     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r4.log(r5, r6, r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            if (r9 != 0) goto L_0x01a4
            goto L_0x01c9
        L_0x01a4:
            org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException r4 = new org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r4.<init>(r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            throw r4     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
        L_0x01aa:
            r3 = move-exception
            java.util.logging.Logger r4 = LOGGER     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r6 = "Trust store certificate exception."
            r4.log(r5, r6, r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            if (r9 != 0) goto L_0x01b7
            goto L_0x01c9
        L_0x01b7:
            org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException r4 = new org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r4.<init>(r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            throw r4     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
        L_0x01bd:
            r3 = move-exception
            java.util.logging.Logger r4 = LOGGER     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            java.lang.String r6 = "Error initializing trust store"
            r4.log(r5, r6, r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            if (r9 != 0) goto L_0x01eb
        L_0x01c9:
        L_0x01ca:
            java.lang.String r3 = "TLS"
            java.lang.String r4 = r8.securityProtocol     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            if (r4 == 0) goto L_0x01d1
            r3 = r4
        L_0x01d1:
            javax.net.ssl.SSLContext r4 = javax.net.ssl.SSLContext.getInstance(r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r0 = r4
            r4 = 0
            if (r2 == 0) goto L_0x01de
            javax.net.ssl.KeyManager[] r5 = r2.getKeyManagers()     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            goto L_0x01df
        L_0x01de:
            r5 = r4
        L_0x01df:
            if (r1 == 0) goto L_0x01e6
            javax.net.ssl.TrustManager[] r6 = r1.getTrustManagers()     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            goto L_0x01e7
        L_0x01e6:
            r6 = r4
        L_0x01e7:
            r0.init(r5, r6, r4)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            goto L_0x0216
        L_0x01eb:
            org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException r4 = new org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r4.<init>(r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            throw r4     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
        L_0x01f1:
            org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException r4 = new org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            r4.<init>(r3)     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
            throw r4     // Catch:{ KeyManagementException -> 0x020a, NoSuchAlgorithmException -> 0x01f7 }
        L_0x01f7:
            r1 = move-exception
            java.util.logging.Logger r2 = LOGGER
            java.util.logging.Level r3 = java.util.logging.Level.FINE
            java.lang.String r4 = "Error initializing algorithm."
            r2.log(r3, r4, r1)
            if (r9 != 0) goto L_0x0204
            goto L_0x0217
        L_0x0204:
            org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException r2 = new org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException
            r2.<init>(r1)
            throw r2
        L_0x020a:
            r1 = move-exception
            java.util.logging.Logger r2 = LOGGER
            java.util.logging.Level r3 = java.util.logging.Level.FINE
            java.lang.String r4 = "Key management error."
            r2.log(r3, r4, r1)
            if (r9 != 0) goto L_0x0218
        L_0x0216:
        L_0x0217:
            return r0
        L_0x0218:
            org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException r2 = new org.glassfish.grizzly.ssl.SSLContextConfigurator$GenericStoreException
            r2.<init>(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.ssl.SSLContextConfigurator.createSSLContext(boolean):javax.net.ssl.SSLContext");
    }

    public void retrieve(Properties props) {
        this.trustStoreProvider = props.getProperty("javax.net.ssl.trustStoreProvider");
        this.keyStoreProvider = props.getProperty("javax.net.ssl.keyStoreProvider");
        this.trustStoreType = props.getProperty("javax.net.ssl.trustStoreType");
        this.keyStoreType = props.getProperty("javax.net.ssl.keyStoreType");
        if (props.getProperty("javax.net.ssl.trustStorePassword") != null) {
            this.trustStorePass = props.getProperty("javax.net.ssl.trustStorePassword").toCharArray();
        } else {
            this.trustStorePass = null;
        }
        if (props.getProperty("javax.net.ssl.keyStorePassword") != null) {
            this.keyStorePass = props.getProperty("javax.net.ssl.keyStorePassword").toCharArray();
        } else {
            this.keyStorePass = null;
        }
        this.trustStoreFile = props.getProperty("javax.net.ssl.trustStore");
        this.keyStoreFile = props.getProperty("javax.net.ssl.keyStore");
        this.trustStoreBytes = null;
        this.keyStoreBytes = null;
        this.securityProtocol = "TLS";
    }

    private static void loadBytes(byte[] bytes, String storeFile, char[] password, KeyStore store) {
        InputStream inputStream = null;
        if (bytes != null) {
            try {
                inputStream = new ByteArrayInputStream(bytes);
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                    }
                }
                throw th;
            }
        } else if (!"NONE".equals(storeFile)) {
            inputStream = new FileInputStream(storeFile);
        }
        store.load(inputStream, password);
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e2) {
            }
        }
    }

    public static final class GenericStoreException extends RuntimeException {
        public GenericStoreException(Throwable cause) {
            super(cause);
        }
    }
}
