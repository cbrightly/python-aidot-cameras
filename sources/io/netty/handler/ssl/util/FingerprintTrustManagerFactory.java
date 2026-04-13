package io.netty.handler.ssl.util;

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.StringUtil;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public final class FingerprintTrustManagerFactory extends SimpleTrustManagerFactory {
    private static final Pattern FINGERPRINT_PATTERN = Pattern.compile("^[0-9a-fA-F:]+$");
    private static final Pattern FINGERPRINT_STRIP_PATTERN = Pattern.compile(":");
    private static final int SHA1_BYTE_LEN = 20;
    private static final int SHA1_HEX_LEN = 40;
    /* access modifiers changed from: private */
    public static final FastThreadLocal<MessageDigest> tlmd = new FastThreadLocal<MessageDigest>() {
        /* access modifiers changed from: protected */
        public MessageDigest initialValue() {
            try {
                return MessageDigest.getInstance("SHA1");
            } catch (NoSuchAlgorithmException e) {
                throw new Error(e);
            }
        }
    };
    /* access modifiers changed from: private */
    public final byte[][] fingerprints;
    private final TrustManager tm;

    public FingerprintTrustManagerFactory(Iterable<String> fingerprints2) {
        this(toFingerprintArray(fingerprints2));
    }

    public FingerprintTrustManagerFactory(String... fingerprints2) {
        this(toFingerprintArray(Arrays.asList(fingerprints2)));
    }

    public FingerprintTrustManagerFactory(byte[]... fingerprints2) {
        this.tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String s) {
                checkTrusted("client", chain);
            }

            public void checkServerTrusted(X509Certificate[] chain, String s) {
                checkTrusted("server", chain);
            }

            private void checkTrusted(String type, X509Certificate[] chain) {
                int i = 0;
                X509Certificate cert = chain[0];
                byte[] fingerprint = fingerprint(cert);
                boolean found = false;
                byte[][] access$000 = FingerprintTrustManagerFactory.this.fingerprints;
                int length = access$000.length;
                while (true) {
                    if (i >= length) {
                        break;
                    } else if (Arrays.equals(fingerprint, access$000[i])) {
                        found = true;
                        break;
                    } else {
                        i++;
                    }
                }
                if (!found) {
                    throw new CertificateException(type + " certificate with unknown fingerprint: " + cert.getSubjectDN());
                }
            }

            private byte[] fingerprint(X509Certificate cert) {
                MessageDigest md = (MessageDigest) FingerprintTrustManagerFactory.tlmd.get();
                md.reset();
                return md.digest(cert.getEncoded());
            }

            public X509Certificate[] getAcceptedIssuers() {
                return EmptyArrays.EMPTY_X509_CERTIFICATES;
            }
        };
        if (fingerprints2 != null) {
            List<byte[]> list = new ArrayList<>(fingerprints2.length);
            int length = fingerprints2.length;
            int i = 0;
            while (i < length) {
                byte[] f = fingerprints2[i];
                if (f == null) {
                    break;
                } else if (f.length == 20) {
                    list.add(f.clone());
                    i++;
                } else {
                    throw new IllegalArgumentException("malformed fingerprint: " + ByteBufUtil.hexDump(Unpooled.wrappedBuffer(f)) + " (expected: SHA1)");
                }
            }
            this.fingerprints = (byte[][]) list.toArray(new byte[list.size()][]);
            return;
        }
        throw new NullPointerException("fingerprints");
    }

    private static byte[][] toFingerprintArray(Iterable<String> fingerprints2) {
        String f;
        if (fingerprints2 != null) {
            List<byte[]> list = new ArrayList<>();
            Iterator<String> it = fingerprints2.iterator();
            while (it.hasNext() && (f = it.next()) != null) {
                if (FINGERPRINT_PATTERN.matcher(f).matches()) {
                    String f2 = FINGERPRINT_STRIP_PATTERN.matcher(f).replaceAll("");
                    if (f2.length() == 40) {
                        list.add(StringUtil.decodeHexDump(f2));
                    } else {
                        throw new IllegalArgumentException("malformed fingerprint: " + f2 + " (expected: SHA1)");
                    }
                } else {
                    throw new IllegalArgumentException("malformed fingerprint: " + f);
                }
            }
            return (byte[][]) list.toArray(new byte[list.size()][]);
        }
        throw new NullPointerException("fingerprints");
    }

    /* access modifiers changed from: protected */
    public void engineInit(KeyStore keyStore) {
    }

    /* access modifiers changed from: protected */
    public void engineInit(ManagerFactoryParameters managerFactoryParameters) {
    }

    /* access modifiers changed from: protected */
    public TrustManager[] engineGetTrustManagers() {
        return new TrustManager[]{this.tm};
    }
}
