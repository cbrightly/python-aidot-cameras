package io.netty.handler.ssl.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

public final class SelfSignedCertificate {
    private static final Date DEFAULT_NOT_AFTER = new Date(SystemPropertyUtil.getLong("io.netty.selfSignedCertificate.defaultNotAfter", 253402300799000L));
    private static final Date DEFAULT_NOT_BEFORE = new Date(SystemPropertyUtil.getLong("io.netty.selfSignedCertificate.defaultNotBefore", System.currentTimeMillis() - 31536000000L));
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) SelfSignedCertificate.class);
    private final X509Certificate cert;
    private final File certificate;
    private final PrivateKey key;
    private final File privateKey;

    public SelfSignedCertificate() {
        this(DEFAULT_NOT_BEFORE, DEFAULT_NOT_AFTER);
    }

    public SelfSignedCertificate(Date notBefore, Date notAfter) {
        this("example.com", notBefore, notAfter);
    }

    public SelfSignedCertificate(String fqdn) {
        this(fqdn, DEFAULT_NOT_BEFORE, DEFAULT_NOT_AFTER);
    }

    public SelfSignedCertificate(String fqdn, Date notBefore, Date notAfter) {
        this(fqdn, ThreadLocalInsecureRandom.current(), 1024, notBefore, notAfter);
    }

    public SelfSignedCertificate(String fqdn, SecureRandom random, int bits) {
        this(fqdn, random, bits, DEFAULT_NOT_BEFORE, DEFAULT_NOT_AFTER);
    }

    public SelfSignedCertificate(String fqdn, SecureRandom random, int bits, Date notBefore, Date notAfter) {
        String[] paths;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(bits, random);
            KeyPair keypair = keyGen.generateKeyPair();
            try {
                paths = OpenJdkSelfSignedCertGenerator.generate(fqdn, keypair, random, notBefore, notAfter);
            } catch (Throwable t2) {
                logger.debug("Failed to generate a self-signed X.509 certificate using Bouncy Castle:", t2);
                throw new CertificateException("No provider succeeded to generate a self-signed certificate. See debug log for the root cause.", t2);
            }
            File file = new File(paths[0]);
            this.certificate = file;
            this.privateKey = new File(paths[1]);
            this.key = keypair.getPrivate();
            FileInputStream certificateInput = null;
            try {
                certificateInput = new FileInputStream(file);
                this.cert = (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(certificateInput);
                try {
                    certificateInput.close();
                } catch (IOException e) {
                    InternalLogger internalLogger = logger;
                    internalLogger.warn("Failed to close a file: " + this.certificate, (Throwable) e);
                }
            } catch (Exception e2) {
                throw new CertificateEncodingException(e2);
            } catch (Throwable th) {
                if (certificateInput != null) {
                    try {
                        certificateInput.close();
                    } catch (IOException e3) {
                        InternalLogger internalLogger2 = logger;
                        internalLogger2.warn("Failed to close a file: " + this.certificate, (Throwable) e3);
                    }
                }
                throw th;
            }
        } catch (NoSuchAlgorithmException e4) {
            throw new Error(e4);
        }
    }

    public File certificate() {
        return this.certificate;
    }

    public File privateKey() {
        return this.privateKey;
    }

    public X509Certificate cert() {
        return this.cert;
    }

    public PrivateKey key() {
        return this.key;
    }

    public void delete() {
        safeDelete(this.certificate);
        safeDelete(this.privateKey);
    }

    static String[] newSelfSignedCertificate(String fqdn, PrivateKey key2, X509Certificate cert2) {
        ByteBuf encodedBuf = Unpooled.wrappedBuffer(key2.getEncoded());
        try {
            encodedBuf = Base64.encode(encodedBuf, true);
            StringBuilder sb = new StringBuilder();
            sb.append("-----BEGIN PRIVATE KEY-----\n");
            Charset charset = CharsetUtil.US_ASCII;
            sb.append(encodedBuf.toString(charset));
            sb.append("\n-----END PRIVATE KEY-----\n");
            String keyText = sb.toString();
            encodedBuf.release();
            File keyFile = File.createTempFile("keyutil_" + fqdn + '_', ".key");
            keyFile.deleteOnExit();
            OutputStream keyOut = new FileOutputStream(keyFile);
            try {
                keyOut.write(keyText.getBytes(charset));
                keyOut.close();
                keyOut = null;
                if (keyOut != null) {
                }
                ByteBuf encodedBuf2 = Unpooled.wrappedBuffer(cert2.getEncoded());
                try {
                    encodedBuf2 = Base64.encode(encodedBuf2, true);
                    String certText = "-----BEGIN CERTIFICATE-----\n" + encodedBuf2.toString(charset) + "\n-----END CERTIFICATE-----\n";
                    encodedBuf2.release();
                    File certFile = File.createTempFile("keyutil_" + fqdn + '_', ".crt");
                    certFile.deleteOnExit();
                    OutputStream certOut = new FileOutputStream(certFile);
                    try {
                        certOut.write(certText.getBytes(charset));
                        certOut.close();
                        certOut = null;
                        if (certOut != null) {
                        }
                        return new String[]{certFile.getPath(), keyFile.getPath()};
                    } finally {
                        safeClose(certFile, certOut);
                        safeDelete(certFile);
                        safeDelete(keyFile);
                    }
                } catch (Throwable th) {
                    throw th;
                } finally {
                    encodedBuf2.release();
                }
            } finally {
                safeClose(keyFile, keyOut);
                safeDelete(keyFile);
            }
        } catch (Throwable th2) {
            throw th2;
        } finally {
            encodedBuf.release();
        }
    }

    private static void safeDelete(File certFile) {
        if (!certFile.delete()) {
            InternalLogger internalLogger = logger;
            internalLogger.warn("Failed to delete a file: " + certFile);
        }
    }

    private static void safeClose(File keyFile, OutputStream keyOut) {
        try {
            keyOut.close();
        } catch (IOException e) {
            InternalLogger internalLogger = logger;
            internalLogger.warn("Failed to close a file: " + keyFile, (Throwable) e);
        }
    }
}
