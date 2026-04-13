package org.eclipse.paho.client.mqttv3.internal.security;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

/* compiled from: SSLSocketFactoryFactory */
public class a {
    private static final String[] a = {"com.ibm.ssl.protocol", "com.ibm.ssl.contextProvider", "com.ibm.ssl.keyStore", "com.ibm.ssl.keyStorePassword", "com.ibm.ssl.keyStoreType", "com.ibm.ssl.keyStoreProvider", "com.ibm.ssl.keyManager", "com.ibm.ssl.trustStore", "com.ibm.ssl.trustStorePassword", "com.ibm.ssl.trustStoreType", "com.ibm.ssl.trustStoreProvider", "com.ibm.ssl.trustManager", "com.ibm.ssl.enabledCipherSuites", "com.ibm.ssl.clientAuthentication"};
    private static final byte[] b = {-99, -89, -39, OTACommand.RESP_ID_VERSION, 5, -72, -119, -100};
    private Hashtable c = new Hashtable();
    private Properties d;
    private org.eclipse.paho.client.mqttv3.logging.a e = null;

    private boolean u(String key) {
        String[] strArr;
        int i = 0;
        while (true) {
            strArr = a;
            if (i < strArr.length && !strArr[i].equals(key)) {
                i++;
            }
        }
        return i < strArr.length;
    }

    private void a(Properties properties) {
        for (String k : properties.keySet()) {
            if (!u(k)) {
                throw new IllegalArgumentException(String.valueOf(k) + " is not a valid IBM SSL property key.");
            }
        }
    }

    public static char[] x(byte[] b2) {
        if (b2 == null) {
            return null;
        }
        char[] c2 = new char[(b2.length / 2)];
        int i = 0;
        int j = 0;
        while (i < b2.length) {
            int i2 = i + 1;
            c2[j] = (char) ((b2[i] & 255) + ((b2[i2] & 255) << 8));
            j++;
            i = i2 + 1;
        }
        return c2;
    }

    public static byte[] w(char[] c2) {
        if (c2 == null) {
            return null;
        }
        byte[] b2 = new byte[(c2.length * 2)];
        int i = 0;
        for (int j = 0; j < c2.length; j++) {
            int i2 = i + 1;
            b2[i] = (byte) (c2[j] & 255);
            i = i2 + 1;
            b2[i2] = (byte) ((c2[j] >> 8) & 255);
        }
        return b2;
    }

    public static String v(char[] password) {
        if (password == null) {
            return null;
        }
        byte[] bytes = w(password);
        for (int i = 0; i < bytes.length; i++) {
            byte b2 = bytes[i];
            byte[] bArr = b;
            bytes[i] = (byte) ((b2 ^ bArr[i % bArr.length]) & 255);
        }
        return "{xor}" + new String(b.b(bytes));
    }

    public static char[] d(String ePassword) {
        if (ePassword == null) {
            return null;
        }
        try {
            byte[] bytes = b.a(ePassword.substring("{xor}".length()));
            for (int i = 0; i < bytes.length; i++) {
                byte b2 = bytes[i];
                byte[] bArr = b;
                bytes[i] = (byte) ((b2 ^ bArr[i % bArr.length]) & 255);
            }
            return x(bytes);
        } catch (Exception e2) {
            return null;
        }
    }

    public static String[] y(String ciphers) {
        if (ciphers == null) {
            return null;
        }
        Vector c2 = new Vector();
        int i = ciphers.indexOf(44);
        int j = 0;
        while (i > -1) {
            c2.add(ciphers.substring(j, i));
            j = i + 1;
            i = ciphers.indexOf(44, j);
        }
        c2.add(ciphers.substring(j));
        String[] s = new String[c2.size()];
        c2.toArray(s);
        return s;
    }

    private void b(Properties p) {
        String pw = p.getProperty("com.ibm.ssl.keyStorePassword");
        if (pw != null && !pw.startsWith("{xor}")) {
            p.put("com.ibm.ssl.keyStorePassword", v(pw.toCharArray()));
        }
        String pw2 = p.getProperty("com.ibm.ssl.trustStorePassword");
        if (pw2 != null && !pw2.startsWith("{xor}")) {
            p.put("com.ibm.ssl.trustStorePassword", v(pw2.toCharArray()));
        }
    }

    public void t(Properties props, String configID) {
        a(props);
        Properties p = new Properties();
        p.putAll(props);
        b(p);
        if (configID != null) {
            this.c.put(configID, p);
        } else {
            this.d = p;
        }
    }

    private String k(String configID, String ibmKey, String sysProperty) {
        String res = l(configID, ibmKey);
        if (res == null && sysProperty != null) {
            return System.getProperty(sysProperty);
        }
        return res;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.util.Properties} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String l(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            r0 = 0
            r1 = 0
            if (r4 == 0) goto L_0x000d
            java.util.Hashtable r2 = r3.c
            java.lang.Object r2 = r2.get(r4)
            r1 = r2
            java.util.Properties r1 = (java.util.Properties) r1
        L_0x000d:
            if (r1 == 0) goto L_0x0016
            java.lang.String r0 = r1.getProperty(r5)
            if (r0 == 0) goto L_0x0016
            return r0
        L_0x0016:
            java.util.Properties r1 = r3.d
            if (r1 == 0) goto L_0x0021
            java.lang.String r0 = r1.getProperty(r5)
            if (r0 == 0) goto L_0x0021
            return r0
        L_0x0021:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.security.a.l(java.lang.String, java.lang.String):java.lang.String");
    }

    public String n(String configID) {
        return k(configID, "com.ibm.ssl.protocol", (String) null);
    }

    public String f(String configID) {
        return k(configID, "com.ibm.ssl.contextProvider", (String) null);
    }

    public char[] h(String configID) {
        String pw = k(configID, "com.ibm.ssl.keyStorePassword", "javax.net.ssl.keyStorePassword");
        if (pw == null) {
            return null;
        }
        if (pw.startsWith("{xor}")) {
            return d(pw);
        }
        return pw.toCharArray();
    }

    public String j(String configID) {
        return k(configID, "com.ibm.ssl.keyStoreType", "javax.net.ssl.keyStoreType");
    }

    public String i(String configID) {
        return k(configID, "com.ibm.ssl.keyStoreProvider", (String) null);
    }

    public String g(String configID) {
        return k(configID, "com.ibm.ssl.keyManager", "ssl.KeyManagerFactory.algorithm");
    }

    public String p(String configID) {
        String encodedPath = k(configID, "com.ibm.ssl.trustStore", "javax.net.ssl.trustStore");
        try {
            return URLDecoder.decode(encodedPath, StandardCharsets.UTF_8.name());
        } catch (Exception e2) {
            return encodedPath;
        }
    }

    public char[] q(String configID) {
        String pw = k(configID, "com.ibm.ssl.trustStorePassword", "javax.net.ssl.trustStorePassword");
        if (pw == null) {
            return null;
        }
        if (pw.startsWith("{xor}")) {
            return d(pw);
        }
        return pw.toCharArray();
    }

    public String s(String configID) {
        return k(configID, "com.ibm.ssl.trustStoreType", (String) null);
    }

    public String r(String configID) {
        return k(configID, "com.ibm.ssl.trustStoreProvider", (String) null);
    }

    public String o(String configID) {
        return k(configID, "com.ibm.ssl.trustManager", "ssl.TrustManagerFactory.algorithm");
    }

    public String[] e(String configID) {
        return y(k(configID, "com.ibm.ssl.enabledCipherSuites", (String) null));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:194:0x0300, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:195:0x0301, code lost:
        r22 = r6;
        r25 = r7;
        r3 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:196:0x0308, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:197:0x0309, code lost:
        r22 = r6;
        r25 = r7;
        r3 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:198:0x0310, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:199:0x0311, code lost:
        r22 = r6;
        r25 = r7;
        r3 = r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private javax.net.ssl.SSLContext m(java.lang.String r33) {
        /*
            r32 = this;
            r1 = r32
            r2 = r33
            java.lang.String r3 = "com.ibm.ssl.keyStore"
            java.lang.String r4 = "getSSLContext"
            r5 = 0
            java.lang.String r6 = r32.n(r33)
            if (r6 != 0) goto L_0x0011
            java.lang.String r6 = "TLS"
        L_0x0011:
            org.eclipse.paho.client.mqttv3.logging.a r7 = r1.e
            r8 = 1
            java.lang.String r9 = "null (broker defaults)"
            r10 = 0
            r11 = 2
            java.lang.String r12 = "getSSLContext"
            java.lang.String r13 = "org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory"
            if (r7 == 0) goto L_0x002e
            java.lang.Object[] r14 = new java.lang.Object[r11]
            if (r2 == 0) goto L_0x0024
            r15 = r2
            goto L_0x0025
        L_0x0024:
            r15 = r9
        L_0x0025:
            r14[r10] = r15
            r14[r8] = r6
            java.lang.String r15 = "12000"
            r7.fine(r13, r12, r15, r14)
        L_0x002e:
            java.lang.String r7 = r32.f(r33)
            if (r7 != 0) goto L_0x0058
            javax.net.ssl.SSLContext r14 = javax.net.ssl.SSLContext.getInstance(r6)     // Catch:{ NoSuchAlgorithmException -> 0x004e, NoSuchProviderException -> 0x0044, KeyManagementException -> 0x003a }
            r5 = r14
            goto L_0x005d
        L_0x003a:
            r0 = move-exception
            r3 = r0
            r21 = r4
            r22 = r6
            r25 = r7
            goto L_0x0579
        L_0x0044:
            r0 = move-exception
            r3 = r0
            r21 = r4
            r22 = r6
            r25 = r7
            goto L_0x0587
        L_0x004e:
            r0 = move-exception
            r3 = r0
            r21 = r4
            r22 = r6
            r25 = r7
            goto L_0x0595
        L_0x0058:
            javax.net.ssl.SSLContext r14 = javax.net.ssl.SSLContext.getInstance(r6, r7)     // Catch:{ NoSuchAlgorithmException -> 0x058d, NoSuchProviderException -> 0x057f, KeyManagementException -> 0x0571 }
            r5 = r14
        L_0x005d:
            org.eclipse.paho.client.mqttv3.logging.a r14 = r1.e     // Catch:{ NoSuchAlgorithmException -> 0x058d, NoSuchProviderException -> 0x057f, KeyManagementException -> 0x0571 }
            if (r14 == 0) goto L_0x007d
            java.lang.String r15 = "12001"
            java.lang.Object[] r8 = new java.lang.Object[r11]     // Catch:{ NoSuchAlgorithmException -> 0x004e, NoSuchProviderException -> 0x0044, KeyManagementException -> 0x003a }
            if (r2 == 0) goto L_0x006a
            r17 = r2
            goto L_0x006c
        L_0x006a:
            r17 = r9
        L_0x006c:
            r8[r10] = r17     // Catch:{ NoSuchAlgorithmException -> 0x004e, NoSuchProviderException -> 0x0044, KeyManagementException -> 0x003a }
            java.security.Provider r17 = r5.getProvider()     // Catch:{ NoSuchAlgorithmException -> 0x004e, NoSuchProviderException -> 0x0044, KeyManagementException -> 0x003a }
            java.lang.String r17 = r17.getName()     // Catch:{ NoSuchAlgorithmException -> 0x004e, NoSuchProviderException -> 0x0044, KeyManagementException -> 0x003a }
            r16 = 1
            r8[r16] = r17     // Catch:{ NoSuchAlgorithmException -> 0x004e, NoSuchProviderException -> 0x0044, KeyManagementException -> 0x003a }
            r14.fine(r13, r12, r15, r8)     // Catch:{ NoSuchAlgorithmException -> 0x004e, NoSuchProviderException -> 0x0044, KeyManagementException -> 0x003a }
        L_0x007d:
            r8 = 0
            java.lang.String r14 = r1.k(r2, r3, r8)     // Catch:{ NoSuchAlgorithmException -> 0x058d, NoSuchProviderException -> 0x057f, KeyManagementException -> 0x0571 }
            r15 = 0
            r17 = 0
            r18 = 0
            java.lang.String r19 = "null"
            if (r15 != 0) goto L_0x0318
            if (r14 != 0) goto L_0x0094
            java.lang.String r8 = "javax.net.ssl.keyStore"
            java.lang.String r3 = r1.k(r2, r3, r8)     // Catch:{ NoSuchAlgorithmException -> 0x004e, NoSuchProviderException -> 0x0044, KeyManagementException -> 0x003a }
            r14 = r3
        L_0x0094:
            org.eclipse.paho.client.mqttv3.logging.a r3 = r1.e     // Catch:{ NoSuchAlgorithmException -> 0x058d, NoSuchProviderException -> 0x057f, KeyManagementException -> 0x0571 }
            if (r3 == 0) goto L_0x00b5
            java.lang.String r8 = "12004"
            java.lang.Object[] r10 = new java.lang.Object[r11]     // Catch:{ NoSuchAlgorithmException -> 0x004e, NoSuchProviderException -> 0x0044, KeyManagementException -> 0x003a }
            if (r2 == 0) goto L_0x00a1
            r21 = r2
            goto L_0x00a3
        L_0x00a1:
            r21 = r9
        L_0x00a3:
            r20 = 0
            r10[r20] = r21     // Catch:{ NoSuchAlgorithmException -> 0x004e, NoSuchProviderException -> 0x0044, KeyManagementException -> 0x003a }
            if (r14 == 0) goto L_0x00ac
            r21 = r14
            goto L_0x00ae
        L_0x00ac:
            r21 = r19
        L_0x00ae:
            r16 = 1
            r10[r16] = r21     // Catch:{ NoSuchAlgorithmException -> 0x004e, NoSuchProviderException -> 0x0044, KeyManagementException -> 0x003a }
            r3.fine(r13, r12, r8, r10)     // Catch:{ NoSuchAlgorithmException -> 0x004e, NoSuchProviderException -> 0x0044, KeyManagementException -> 0x003a }
        L_0x00b5:
            char[] r3 = r32.h(r33)     // Catch:{ NoSuchAlgorithmException -> 0x058d, NoSuchProviderException -> 0x057f, KeyManagementException -> 0x0571 }
            org.eclipse.paho.client.mqttv3.logging.a r8 = r1.e     // Catch:{ NoSuchAlgorithmException -> 0x058d, NoSuchProviderException -> 0x057f, KeyManagementException -> 0x0571 }
            if (r8 == 0) goto L_0x00fd
            java.lang.String r10 = "12005"
            r21 = r4
            java.lang.Object[] r4 = new java.lang.Object[r11]     // Catch:{ NoSuchAlgorithmException -> 0x011b, NoSuchProviderException -> 0x0113, KeyManagementException -> 0x010b }
            if (r2 == 0) goto L_0x00c8
            r22 = r2
            goto L_0x00ca
        L_0x00c8:
            r22 = r9
        L_0x00ca:
            r20 = 0
            r4[r20] = r22     // Catch:{ NoSuchAlgorithmException -> 0x011b, NoSuchProviderException -> 0x0113, KeyManagementException -> 0x010b }
            if (r3 == 0) goto L_0x00d5
            java.lang.String r22 = v(r3)     // Catch:{ NoSuchAlgorithmException -> 0x011b, NoSuchProviderException -> 0x0113, KeyManagementException -> 0x010b }
            goto L_0x00d7
        L_0x00d5:
            r22 = r19
        L_0x00d7:
            r16 = 1
            r4[r16] = r22     // Catch:{ NoSuchAlgorithmException -> 0x011b, NoSuchProviderException -> 0x0113, KeyManagementException -> 0x010b }
            r8.fine(r13, r12, r10, r4)     // Catch:{ NoSuchAlgorithmException -> 0x011b, NoSuchProviderException -> 0x0113, KeyManagementException -> 0x010b }
            goto L_0x00ff
        L_0x00df:
            r0 = move-exception
            r21 = r4
            r3 = r0
            r22 = r6
            r25 = r7
            goto L_0x0579
        L_0x00e9:
            r0 = move-exception
            r21 = r4
            r3 = r0
            r22 = r6
            r25 = r7
            goto L_0x0587
        L_0x00f3:
            r0 = move-exception
            r21 = r4
            r3 = r0
            r22 = r6
            r25 = r7
            goto L_0x0595
        L_0x00fd:
            r21 = r4
        L_0x00ff:
            java.lang.String r4 = r32.j(r33)     // Catch:{ NoSuchAlgorithmException -> 0x0310, NoSuchProviderException -> 0x0308, KeyManagementException -> 0x0300 }
            if (r4 != 0) goto L_0x0123
            java.lang.String r8 = java.security.KeyStore.getDefaultType()     // Catch:{ NoSuchAlgorithmException -> 0x011b, NoSuchProviderException -> 0x0113, KeyManagementException -> 0x010b }
            r4 = r8
            goto L_0x0123
        L_0x010b:
            r0 = move-exception
            r3 = r0
            r22 = r6
            r25 = r7
            goto L_0x0579
        L_0x0113:
            r0 = move-exception
            r3 = r0
            r22 = r6
            r25 = r7
            goto L_0x0587
        L_0x011b:
            r0 = move-exception
            r3 = r0
            r22 = r6
            r25 = r7
            goto L_0x0595
        L_0x0123:
            org.eclipse.paho.client.mqttv3.logging.a r8 = r1.e     // Catch:{ NoSuchAlgorithmException -> 0x0310, NoSuchProviderException -> 0x0308, KeyManagementException -> 0x0300 }
            if (r8 == 0) goto L_0x0171
            java.lang.String r10 = "12006"
            r22 = r6
            java.lang.Object[] r6 = new java.lang.Object[r11]     // Catch:{ NoSuchAlgorithmException -> 0x0153, NoSuchProviderException -> 0x014d, KeyManagementException -> 0x0147 }
            if (r2 == 0) goto L_0x0132
            r23 = r2
            goto L_0x0134
        L_0x0132:
            r23 = r9
        L_0x0134:
            r20 = 0
            r6[r20] = r23     // Catch:{ NoSuchAlgorithmException -> 0x0153, NoSuchProviderException -> 0x014d, KeyManagementException -> 0x0147 }
            if (r4 == 0) goto L_0x013d
            r23 = r4
            goto L_0x013f
        L_0x013d:
            r23 = r19
        L_0x013f:
            r16 = 1
            r6[r16] = r23     // Catch:{ NoSuchAlgorithmException -> 0x0153, NoSuchProviderException -> 0x014d, KeyManagementException -> 0x0147 }
            r8.fine(r13, r12, r10, r6)     // Catch:{ NoSuchAlgorithmException -> 0x0153, NoSuchProviderException -> 0x014d, KeyManagementException -> 0x0147 }
            goto L_0x0173
        L_0x0147:
            r0 = move-exception
            r3 = r0
            r25 = r7
            goto L_0x0579
        L_0x014d:
            r0 = move-exception
            r3 = r0
            r25 = r7
            goto L_0x0587
        L_0x0153:
            r0 = move-exception
            r3 = r0
            r25 = r7
            goto L_0x0595
        L_0x0159:
            r0 = move-exception
            r22 = r6
            r3 = r0
            r25 = r7
            goto L_0x0579
        L_0x0161:
            r0 = move-exception
            r22 = r6
            r3 = r0
            r25 = r7
            goto L_0x0587
        L_0x0169:
            r0 = move-exception
            r22 = r6
            r3 = r0
            r25 = r7
            goto L_0x0595
        L_0x0171:
            r22 = r6
        L_0x0173:
            java.lang.String r6 = javax.net.ssl.KeyManagerFactory.getDefaultAlgorithm()     // Catch:{ NoSuchAlgorithmException -> 0x02fa, NoSuchProviderException -> 0x02f4, KeyManagementException -> 0x02ee }
            java.lang.String r8 = r32.i(r33)     // Catch:{ NoSuchAlgorithmException -> 0x02fa, NoSuchProviderException -> 0x02f4, KeyManagementException -> 0x02ee }
            java.lang.String r10 = r32.g(r33)     // Catch:{ NoSuchAlgorithmException -> 0x02fa, NoSuchProviderException -> 0x02f4, KeyManagementException -> 0x02ee }
            if (r10 == 0) goto L_0x0182
            r6 = r10
        L_0x0182:
            if (r14 == 0) goto L_0x02e3
            if (r4 == 0) goto L_0x02e3
            if (r6 == 0) goto L_0x02e3
            java.security.KeyStore r23 = java.security.KeyStore.getInstance(r4)     // Catch:{ KeyStoreException -> 0x02d5, CertificateException -> 0x02c7, FileNotFoundException -> 0x02b9, IOException -> 0x02ab, UnrecoverableKeyException -> 0x029d }
            r15 = r23
            java.io.FileInputStream r11 = new java.io.FileInputStream     // Catch:{ KeyStoreException -> 0x02d5, CertificateException -> 0x02c7, FileNotFoundException -> 0x02b9, IOException -> 0x02ab, UnrecoverableKeyException -> 0x029d }
            r11.<init>(r14)     // Catch:{ KeyStoreException -> 0x02d5, CertificateException -> 0x02c7, FileNotFoundException -> 0x02b9, IOException -> 0x02ab, UnrecoverableKeyException -> 0x029d }
            r15.load(r11, r3)     // Catch:{ KeyStoreException -> 0x02d5, CertificateException -> 0x02c7, FileNotFoundException -> 0x02b9, IOException -> 0x02ab, UnrecoverableKeyException -> 0x029d }
            if (r8 == 0) goto L_0x01cf
            javax.net.ssl.KeyManagerFactory r11 = javax.net.ssl.KeyManagerFactory.getInstance(r6, r8)     // Catch:{ KeyStoreException -> 0x01c5, CertificateException -> 0x01bb, FileNotFoundException -> 0x01b1, IOException -> 0x01a7, UnrecoverableKeyException -> 0x019d }
            goto L_0x01d3
        L_0x019d:
            r0 = move-exception
            r24 = r4
            r25 = r7
            r26 = r8
            r4 = r0
            goto L_0x02a5
        L_0x01a7:
            r0 = move-exception
            r24 = r4
            r25 = r7
            r26 = r8
            r4 = r0
            goto L_0x02b3
        L_0x01b1:
            r0 = move-exception
            r24 = r4
            r25 = r7
            r26 = r8
            r4 = r0
            goto L_0x02c1
        L_0x01bb:
            r0 = move-exception
            r24 = r4
            r25 = r7
            r26 = r8
            r4 = r0
            goto L_0x02cf
        L_0x01c5:
            r0 = move-exception
            r24 = r4
            r25 = r7
            r26 = r8
            r4 = r0
            goto L_0x02dd
        L_0x01cf:
            javax.net.ssl.KeyManagerFactory r11 = javax.net.ssl.KeyManagerFactory.getInstance(r6)     // Catch:{ KeyStoreException -> 0x02d5, CertificateException -> 0x02c7, FileNotFoundException -> 0x02b9, IOException -> 0x02ab, UnrecoverableKeyException -> 0x029d }
        L_0x01d3:
            r24 = r4
            org.eclipse.paho.client.mqttv3.logging.a r4 = r1.e     // Catch:{ KeyStoreException -> 0x0294, CertificateException -> 0x028b, FileNotFoundException -> 0x0282, IOException -> 0x0279, UnrecoverableKeyException -> 0x0270 }
            if (r4 == 0) goto L_0x023d
            r25 = r7
            java.lang.String r7 = "12010"
            r26 = r8
            r27 = r9
            r8 = 2
            java.lang.Object[] r9 = new java.lang.Object[r8]     // Catch:{ KeyStoreException -> 0x026a, CertificateException -> 0x0264, FileNotFoundException -> 0x025e, IOException -> 0x0258, UnrecoverableKeyException -> 0x0252 }
            if (r2 == 0) goto L_0x01e8
            r8 = r2
            goto L_0x01ea
        L_0x01e8:
            r8 = r27
        L_0x01ea:
            r17 = 0
            r9[r17] = r8     // Catch:{ KeyStoreException -> 0x026a, CertificateException -> 0x0264, FileNotFoundException -> 0x025e, IOException -> 0x0258, UnrecoverableKeyException -> 0x0252 }
            r8 = 1
            r9[r8] = r6     // Catch:{ KeyStoreException -> 0x026a, CertificateException -> 0x0264, FileNotFoundException -> 0x025e, IOException -> 0x0258, UnrecoverableKeyException -> 0x0252 }
            r4.fine(r13, r12, r7, r9)     // Catch:{ KeyStoreException -> 0x026a, CertificateException -> 0x0264, FileNotFoundException -> 0x025e, IOException -> 0x0258, UnrecoverableKeyException -> 0x0252 }
            org.eclipse.paho.client.mqttv3.logging.a r4 = r1.e     // Catch:{ KeyStoreException -> 0x026a, CertificateException -> 0x0264, FileNotFoundException -> 0x025e, IOException -> 0x0258, UnrecoverableKeyException -> 0x0252 }
            java.lang.String r7 = "12009"
            r8 = 2
            java.lang.Object[] r9 = new java.lang.Object[r8]     // Catch:{ KeyStoreException -> 0x026a, CertificateException -> 0x0264, FileNotFoundException -> 0x025e, IOException -> 0x0258, UnrecoverableKeyException -> 0x0252 }
            if (r2 == 0) goto L_0x01ff
            r8 = r2
            goto L_0x0201
        L_0x01ff:
            r8 = r27
        L_0x0201:
            r17 = 0
            r9[r17] = r8     // Catch:{ KeyStoreException -> 0x026a, CertificateException -> 0x0264, FileNotFoundException -> 0x025e, IOException -> 0x0258, UnrecoverableKeyException -> 0x0252 }
            java.security.Provider r8 = r11.getProvider()     // Catch:{ KeyStoreException -> 0x026a, CertificateException -> 0x0264, FileNotFoundException -> 0x025e, IOException -> 0x0258, UnrecoverableKeyException -> 0x0252 }
            java.lang.String r8 = r8.getName()     // Catch:{ KeyStoreException -> 0x026a, CertificateException -> 0x0264, FileNotFoundException -> 0x025e, IOException -> 0x0258, UnrecoverableKeyException -> 0x0252 }
            r16 = 1
            r9[r16] = r8     // Catch:{ KeyStoreException -> 0x026a, CertificateException -> 0x0264, FileNotFoundException -> 0x025e, IOException -> 0x0258, UnrecoverableKeyException -> 0x0252 }
            r4.fine(r13, r12, r7, r9)     // Catch:{ KeyStoreException -> 0x026a, CertificateException -> 0x0264, FileNotFoundException -> 0x025e, IOException -> 0x0258, UnrecoverableKeyException -> 0x0252 }
            goto L_0x0243
        L_0x0215:
            r0 = move-exception
            r26 = r8
            r4 = r0
            r17 = r11
            goto L_0x02a5
        L_0x021d:
            r0 = move-exception
            r26 = r8
            r4 = r0
            r17 = r11
            goto L_0x02b3
        L_0x0225:
            r0 = move-exception
            r26 = r8
            r4 = r0
            r17 = r11
            goto L_0x02c1
        L_0x022d:
            r0 = move-exception
            r26 = r8
            r4 = r0
            r17 = r11
            goto L_0x02cf
        L_0x0235:
            r0 = move-exception
            r26 = r8
            r4 = r0
            r17 = r11
            goto L_0x02dd
        L_0x023d:
            r25 = r7
            r26 = r8
            r27 = r9
        L_0x0243:
            r11.init(r15, r3)     // Catch:{ KeyStoreException -> 0x026a, CertificateException -> 0x0264, FileNotFoundException -> 0x025e, IOException -> 0x0258, UnrecoverableKeyException -> 0x0252 }
            javax.net.ssl.KeyManager[] r4 = r11.getKeyManagers()     // Catch:{ KeyStoreException -> 0x026a, CertificateException -> 0x0264, FileNotFoundException -> 0x025e, IOException -> 0x0258, UnrecoverableKeyException -> 0x0252 }
            r18 = r4
            r17 = r11
            r3 = r18
            goto L_0x0322
        L_0x0252:
            r0 = move-exception
            r4 = r0
            r17 = r11
            goto L_0x02a5
        L_0x0258:
            r0 = move-exception
            r4 = r0
            r17 = r11
            goto L_0x02b3
        L_0x025e:
            r0 = move-exception
            r4 = r0
            r17 = r11
            goto L_0x02c1
        L_0x0264:
            r0 = move-exception
            r4 = r0
            r17 = r11
            goto L_0x02cf
        L_0x026a:
            r0 = move-exception
            r4 = r0
            r17 = r11
            goto L_0x02dd
        L_0x0270:
            r0 = move-exception
            r25 = r7
            r26 = r8
            r4 = r0
            r17 = r11
            goto L_0x02a5
        L_0x0279:
            r0 = move-exception
            r25 = r7
            r26 = r8
            r4 = r0
            r17 = r11
            goto L_0x02b3
        L_0x0282:
            r0 = move-exception
            r25 = r7
            r26 = r8
            r4 = r0
            r17 = r11
            goto L_0x02c1
        L_0x028b:
            r0 = move-exception
            r25 = r7
            r26 = r8
            r4 = r0
            r17 = r11
            goto L_0x02cf
        L_0x0294:
            r0 = move-exception
            r25 = r7
            r26 = r8
            r4 = r0
            r17 = r11
            goto L_0x02dd
        L_0x029d:
            r0 = move-exception
            r24 = r4
            r25 = r7
            r26 = r8
            r4 = r0
        L_0x02a5:
            org.eclipse.paho.client.mqttv3.MqttSecurityException r7 = new org.eclipse.paho.client.mqttv3.MqttSecurityException     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            r7.<init>((java.lang.Throwable) r4)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            throw r7     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
        L_0x02ab:
            r0 = move-exception
            r24 = r4
            r25 = r7
            r26 = r8
            r4 = r0
        L_0x02b3:
            org.eclipse.paho.client.mqttv3.MqttSecurityException r7 = new org.eclipse.paho.client.mqttv3.MqttSecurityException     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            r7.<init>((java.lang.Throwable) r4)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            throw r7     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
        L_0x02b9:
            r0 = move-exception
            r24 = r4
            r25 = r7
            r26 = r8
            r4 = r0
        L_0x02c1:
            org.eclipse.paho.client.mqttv3.MqttSecurityException r7 = new org.eclipse.paho.client.mqttv3.MqttSecurityException     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            r7.<init>((java.lang.Throwable) r4)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            throw r7     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
        L_0x02c7:
            r0 = move-exception
            r24 = r4
            r25 = r7
            r26 = r8
            r4 = r0
        L_0x02cf:
            org.eclipse.paho.client.mqttv3.MqttSecurityException r7 = new org.eclipse.paho.client.mqttv3.MqttSecurityException     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            r7.<init>((java.lang.Throwable) r4)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            throw r7     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
        L_0x02d5:
            r0 = move-exception
            r24 = r4
            r25 = r7
            r26 = r8
            r4 = r0
        L_0x02dd:
            org.eclipse.paho.client.mqttv3.MqttSecurityException r7 = new org.eclipse.paho.client.mqttv3.MqttSecurityException     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            r7.<init>((java.lang.Throwable) r4)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            throw r7     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
        L_0x02e3:
            r24 = r4
            r25 = r7
            r26 = r8
            r27 = r9
            r3 = r18
            goto L_0x0322
        L_0x02ee:
            r0 = move-exception
            r25 = r7
            r3 = r0
            goto L_0x0579
        L_0x02f4:
            r0 = move-exception
            r25 = r7
            r3 = r0
            goto L_0x0587
        L_0x02fa:
            r0 = move-exception
            r25 = r7
            r3 = r0
            goto L_0x0595
        L_0x0300:
            r0 = move-exception
            r22 = r6
            r25 = r7
            r3 = r0
            goto L_0x0579
        L_0x0308:
            r0 = move-exception
            r22 = r6
            r25 = r7
            r3 = r0
            goto L_0x0587
        L_0x0310:
            r0 = move-exception
            r22 = r6
            r25 = r7
            r3 = r0
            goto L_0x0595
        L_0x0318:
            r21 = r4
            r22 = r6
            r25 = r7
            r27 = r9
            r3 = r18
        L_0x0322:
            java.lang.String r4 = r32.p(r33)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            org.eclipse.paho.client.mqttv3.logging.a r6 = r1.e     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            if (r6 == 0) goto L_0x0344
            java.lang.String r7 = "12011"
            r8 = 2
            java.lang.Object[] r9 = new java.lang.Object[r8]     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            if (r2 == 0) goto L_0x0333
            r8 = r2
            goto L_0x0335
        L_0x0333:
            r8 = r27
        L_0x0335:
            r10 = 0
            r9[r10] = r8     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            if (r4 == 0) goto L_0x033c
            r8 = r4
            goto L_0x033e
        L_0x033c:
            r8 = r19
        L_0x033e:
            r10 = 1
            r9[r10] = r8     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            r6.fine(r13, r12, r7, r9)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
        L_0x0344:
            r6 = 0
            r7 = 0
            r8 = 0
            char[] r9 = r32.q(r33)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            org.eclipse.paho.client.mqttv3.logging.a r10 = r1.e     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            if (r10 == 0) goto L_0x0373
            java.lang.String r11 = "12012"
            r18 = r6
            r24 = r7
            r6 = 2
            java.lang.Object[] r7 = new java.lang.Object[r6]     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            if (r2 == 0) goto L_0x035c
            r6 = r2
            goto L_0x035e
        L_0x035c:
            r6 = r27
        L_0x035e:
            r20 = 0
            r7[r20] = r6     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            if (r9 == 0) goto L_0x0369
            java.lang.String r6 = v(r9)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            goto L_0x036b
        L_0x0369:
            r6 = r19
        L_0x036b:
            r16 = 1
            r7[r16] = r6     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            r10.fine(r13, r12, r11, r7)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            goto L_0x0377
        L_0x0373:
            r18 = r6
            r24 = r7
        L_0x0377:
            java.lang.String r6 = r32.s(r33)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            if (r6 != 0) goto L_0x0382
            java.lang.String r7 = java.security.KeyStore.getDefaultType()     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            r6 = r7
        L_0x0382:
            org.eclipse.paho.client.mqttv3.logging.a r7 = r1.e     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            if (r7 == 0) goto L_0x03a2
            java.lang.String r10 = "12013"
            r26 = r8
            r11 = 2
            java.lang.Object[] r8 = new java.lang.Object[r11]     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            if (r2 == 0) goto L_0x0391
            r11 = r2
            goto L_0x0393
        L_0x0391:
            r11 = r27
        L_0x0393:
            r20 = 0
            r8[r20] = r11     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            if (r6 == 0) goto L_0x039b
            r19 = r6
        L_0x039b:
            r11 = 1
            r8[r11] = r19     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            r7.fine(r13, r12, r10, r8)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            goto L_0x03a4
        L_0x03a2:
            r26 = r8
        L_0x03a4:
            java.lang.String r7 = javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm()     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            java.lang.String r8 = r32.r(r33)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            java.lang.String r10 = r32.o(r33)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            if (r10 == 0) goto L_0x03b3
            r7 = r10
        L_0x03b3:
            if (r4 == 0) goto L_0x0554
            if (r6 == 0) goto L_0x0554
            if (r7 == 0) goto L_0x0554
            java.security.KeyStore r11 = java.security.KeyStore.getInstance(r6)     // Catch:{ KeyStoreException -> 0x0540, CertificateException -> 0x052c, FileNotFoundException -> 0x0518, IOException -> 0x0504 }
            r19 = r6
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ KeyStoreException -> 0x04f8, CertificateException -> 0x04ec, FileNotFoundException -> 0x04e0, IOException -> 0x04d4 }
            r6.<init>(r4)     // Catch:{ KeyStoreException -> 0x04f8, CertificateException -> 0x04ec, FileNotFoundException -> 0x04e0, IOException -> 0x04d4 }
            r11.load(r6, r9)     // Catch:{ KeyStoreException -> 0x04f8, CertificateException -> 0x04ec, FileNotFoundException -> 0x04e0, IOException -> 0x04d4 }
            if (r8 == 0) goto L_0x0402
            javax.net.ssl.TrustManagerFactory r6 = javax.net.ssl.TrustManagerFactory.getInstance(r7, r8)     // Catch:{ KeyStoreException -> 0x03f5, CertificateException -> 0x03e8, FileNotFoundException -> 0x03db, IOException -> 0x03ce }
            goto L_0x0406
        L_0x03ce:
            r0 = move-exception
            r28 = r4
            r29 = r8
            r30 = r9
            r31 = r10
            r6 = r11
            r4 = r0
            goto L_0x0512
        L_0x03db:
            r0 = move-exception
            r28 = r4
            r29 = r8
            r30 = r9
            r31 = r10
            r6 = r11
            r4 = r0
            goto L_0x0526
        L_0x03e8:
            r0 = move-exception
            r28 = r4
            r29 = r8
            r30 = r9
            r31 = r10
            r6 = r11
            r4 = r0
            goto L_0x053a
        L_0x03f5:
            r0 = move-exception
            r28 = r4
            r29 = r8
            r30 = r9
            r31 = r10
            r6 = r11
            r4 = r0
            goto L_0x054e
        L_0x0402:
            javax.net.ssl.TrustManagerFactory r6 = javax.net.ssl.TrustManagerFactory.getInstance(r7)     // Catch:{ KeyStoreException -> 0x04f8, CertificateException -> 0x04ec, FileNotFoundException -> 0x04e0, IOException -> 0x04d4 }
        L_0x0406:
            r28 = r4
            org.eclipse.paho.client.mqttv3.logging.a r4 = r1.e     // Catch:{ KeyStoreException -> 0x04c7, CertificateException -> 0x04ba, FileNotFoundException -> 0x04ad, IOException -> 0x04a0 }
            if (r4 == 0) goto L_0x0471
            r29 = r8
            java.lang.String r8 = "12017"
            r30 = r9
            r31 = r10
            r9 = 2
            java.lang.Object[] r10 = new java.lang.Object[r9]     // Catch:{ KeyStoreException -> 0x0499, CertificateException -> 0x0492, FileNotFoundException -> 0x048b, IOException -> 0x0484 }
            if (r2 == 0) goto L_0x041b
            r9 = r2
            goto L_0x041d
        L_0x041b:
            r9 = r27
        L_0x041d:
            r18 = 0
            r10[r18] = r9     // Catch:{ KeyStoreException -> 0x0499, CertificateException -> 0x0492, FileNotFoundException -> 0x048b, IOException -> 0x0484 }
            r9 = 1
            r10[r9] = r7     // Catch:{ KeyStoreException -> 0x0499, CertificateException -> 0x0492, FileNotFoundException -> 0x048b, IOException -> 0x0484 }
            r4.fine(r13, r12, r8, r10)     // Catch:{ KeyStoreException -> 0x0499, CertificateException -> 0x0492, FileNotFoundException -> 0x048b, IOException -> 0x0484 }
            org.eclipse.paho.client.mqttv3.logging.a r4 = r1.e     // Catch:{ KeyStoreException -> 0x0499, CertificateException -> 0x0492, FileNotFoundException -> 0x048b, IOException -> 0x0484 }
            java.lang.String r8 = "12016"
            r9 = 2
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ KeyStoreException -> 0x0499, CertificateException -> 0x0492, FileNotFoundException -> 0x048b, IOException -> 0x0484 }
            if (r2 == 0) goto L_0x0432
            r27 = r2
        L_0x0432:
            r10 = 0
            r9[r10] = r27     // Catch:{ KeyStoreException -> 0x0499, CertificateException -> 0x0492, FileNotFoundException -> 0x048b, IOException -> 0x0484 }
            java.security.Provider r10 = r6.getProvider()     // Catch:{ KeyStoreException -> 0x0499, CertificateException -> 0x0492, FileNotFoundException -> 0x048b, IOException -> 0x0484 }
            java.lang.String r10 = r10.getName()     // Catch:{ KeyStoreException -> 0x0499, CertificateException -> 0x0492, FileNotFoundException -> 0x048b, IOException -> 0x0484 }
            r16 = 1
            r9[r16] = r10     // Catch:{ KeyStoreException -> 0x0499, CertificateException -> 0x0492, FileNotFoundException -> 0x048b, IOException -> 0x0484 }
            r4.fine(r13, r12, r8, r9)     // Catch:{ KeyStoreException -> 0x0499, CertificateException -> 0x0492, FileNotFoundException -> 0x048b, IOException -> 0x0484 }
            goto L_0x0477
        L_0x0445:
            r0 = move-exception
            r30 = r9
            r31 = r10
            r4 = r0
            r24 = r6
            r6 = r11
            goto L_0x0512
        L_0x0450:
            r0 = move-exception
            r30 = r9
            r31 = r10
            r4 = r0
            r24 = r6
            r6 = r11
            goto L_0x0526
        L_0x045b:
            r0 = move-exception
            r30 = r9
            r31 = r10
            r4 = r0
            r24 = r6
            r6 = r11
            goto L_0x053a
        L_0x0466:
            r0 = move-exception
            r30 = r9
            r31 = r10
            r4 = r0
            r24 = r6
            r6 = r11
            goto L_0x054e
        L_0x0471:
            r29 = r8
            r30 = r9
            r31 = r10
        L_0x0477:
            r6.init(r11)     // Catch:{ KeyStoreException -> 0x0499, CertificateException -> 0x0492, FileNotFoundException -> 0x048b, IOException -> 0x0484 }
            javax.net.ssl.TrustManager[] r4 = r6.getTrustManagers()     // Catch:{ KeyStoreException -> 0x0499, CertificateException -> 0x0492, FileNotFoundException -> 0x048b, IOException -> 0x0484 }
            r8 = r4
            r24 = r6
            r6 = r11
            goto L_0x0562
        L_0x0484:
            r0 = move-exception
            r4 = r0
            r24 = r6
            r6 = r11
            goto L_0x0512
        L_0x048b:
            r0 = move-exception
            r4 = r0
            r24 = r6
            r6 = r11
            goto L_0x0526
        L_0x0492:
            r0 = move-exception
            r4 = r0
            r24 = r6
            r6 = r11
            goto L_0x053a
        L_0x0499:
            r0 = move-exception
            r4 = r0
            r24 = r6
            r6 = r11
            goto L_0x054e
        L_0x04a0:
            r0 = move-exception
            r29 = r8
            r30 = r9
            r31 = r10
            r4 = r0
            r24 = r6
            r6 = r11
            goto L_0x0512
        L_0x04ad:
            r0 = move-exception
            r29 = r8
            r30 = r9
            r31 = r10
            r4 = r0
            r24 = r6
            r6 = r11
            goto L_0x0526
        L_0x04ba:
            r0 = move-exception
            r29 = r8
            r30 = r9
            r31 = r10
            r4 = r0
            r24 = r6
            r6 = r11
            goto L_0x053a
        L_0x04c7:
            r0 = move-exception
            r29 = r8
            r30 = r9
            r31 = r10
            r4 = r0
            r24 = r6
            r6 = r11
            goto L_0x054e
        L_0x04d4:
            r0 = move-exception
            r28 = r4
            r29 = r8
            r30 = r9
            r31 = r10
            r4 = r0
            r6 = r11
            goto L_0x0512
        L_0x04e0:
            r0 = move-exception
            r28 = r4
            r29 = r8
            r30 = r9
            r31 = r10
            r4 = r0
            r6 = r11
            goto L_0x0526
        L_0x04ec:
            r0 = move-exception
            r28 = r4
            r29 = r8
            r30 = r9
            r31 = r10
            r4 = r0
            r6 = r11
            goto L_0x053a
        L_0x04f8:
            r0 = move-exception
            r28 = r4
            r29 = r8
            r30 = r9
            r31 = r10
            r4 = r0
            r6 = r11
            goto L_0x054e
        L_0x0504:
            r0 = move-exception
            r28 = r4
            r19 = r6
            r29 = r8
            r30 = r9
            r31 = r10
            r4 = r0
            r6 = r18
        L_0x0512:
            org.eclipse.paho.client.mqttv3.MqttSecurityException r8 = new org.eclipse.paho.client.mqttv3.MqttSecurityException     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            r8.<init>((java.lang.Throwable) r4)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            throw r8     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
        L_0x0518:
            r0 = move-exception
            r28 = r4
            r19 = r6
            r29 = r8
            r30 = r9
            r31 = r10
            r4 = r0
            r6 = r18
        L_0x0526:
            org.eclipse.paho.client.mqttv3.MqttSecurityException r8 = new org.eclipse.paho.client.mqttv3.MqttSecurityException     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            r8.<init>((java.lang.Throwable) r4)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            throw r8     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
        L_0x052c:
            r0 = move-exception
            r28 = r4
            r19 = r6
            r29 = r8
            r30 = r9
            r31 = r10
            r4 = r0
            r6 = r18
        L_0x053a:
            org.eclipse.paho.client.mqttv3.MqttSecurityException r8 = new org.eclipse.paho.client.mqttv3.MqttSecurityException     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            r8.<init>((java.lang.Throwable) r4)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            throw r8     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
        L_0x0540:
            r0 = move-exception
            r28 = r4
            r19 = r6
            r29 = r8
            r30 = r9
            r31 = r10
            r4 = r0
            r6 = r18
        L_0x054e:
            org.eclipse.paho.client.mqttv3.MqttSecurityException r8 = new org.eclipse.paho.client.mqttv3.MqttSecurityException     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            r8.<init>((java.lang.Throwable) r4)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            throw r8     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
        L_0x0554:
            r28 = r4
            r19 = r6
            r29 = r8
            r30 = r9
            r31 = r10
            r6 = r18
            r8 = r26
        L_0x0562:
            r4 = 0
            r5.init(r3, r8, r4)     // Catch:{ NoSuchAlgorithmException -> 0x056e, NoSuchProviderException -> 0x056b, KeyManagementException -> 0x0568 }
            return r5
        L_0x0568:
            r0 = move-exception
            r3 = r0
            goto L_0x0579
        L_0x056b:
            r0 = move-exception
            r3 = r0
            goto L_0x0587
        L_0x056e:
            r0 = move-exception
            r3 = r0
            goto L_0x0595
        L_0x0571:
            r0 = move-exception
            r21 = r4
            r22 = r6
            r25 = r7
            r3 = r0
        L_0x0579:
            org.eclipse.paho.client.mqttv3.MqttSecurityException r4 = new org.eclipse.paho.client.mqttv3.MqttSecurityException
            r4.<init>((java.lang.Throwable) r3)
            throw r4
        L_0x057f:
            r0 = move-exception
            r21 = r4
            r22 = r6
            r25 = r7
            r3 = r0
        L_0x0587:
            org.eclipse.paho.client.mqttv3.MqttSecurityException r4 = new org.eclipse.paho.client.mqttv3.MqttSecurityException
            r4.<init>((java.lang.Throwable) r3)
            throw r4
        L_0x058d:
            r0 = move-exception
            r21 = r4
            r22 = r6
            r25 = r7
            r3 = r0
        L_0x0595:
            org.eclipse.paho.client.mqttv3.MqttSecurityException r4 = new org.eclipse.paho.client.mqttv3.MqttSecurityException
            r4.<init>((java.lang.Throwable) r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.security.a.m(java.lang.String):javax.net.ssl.SSLContext");
    }

    public SSLSocketFactory c(String configID) {
        SSLContext ctx = m(configID);
        org.eclipse.paho.client.mqttv3.logging.a aVar = this.e;
        if (aVar != null) {
            Object[] objArr = new Object[2];
            objArr[0] = configID != null ? configID : "null (broker defaults)";
            objArr[1] = e(configID) != null ? k(configID, "com.ibm.ssl.enabledCipherSuites", (String) null) : "null (using platform-enabled cipher suites)";
            aVar.fine("org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory", "createSocketFactory", "12020", objArr);
        }
        return ctx.getSocketFactory();
    }
}
