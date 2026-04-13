package org.apache.http.impl.auth;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.glassfish.grizzly.http.server.util.MappingData;

/* compiled from: NTLMEngineImpl */
public final class l implements k {
    /* access modifiers changed from: private */
    public static final Charset a = org.apache.http.util.e.b("UnicodeLittleUnmarked");
    /* access modifiers changed from: private */
    public static final Charset b = org.apache.http.b.b;
    private static final SecureRandom c;
    /* access modifiers changed from: private */
    public static final byte[] d;
    private static final String e = new e().f();

    l() {
    }

    static {
        SecureRandom rnd = null;
        try {
            rnd = SecureRandom.getInstance("SHA1PRNG");
        } catch (Exception e2) {
        }
        c = rnd;
        byte[] bytesWithoutNull = "NTLMSSP".getBytes(org.apache.http.b.b);
        byte[] bArr = new byte[(bytesWithoutNull.length + 1)];
        d = bArr;
        System.arraycopy(bytesWithoutNull, 0, bArr, 0, bytesWithoutNull.length);
        bArr[bytesWithoutNull.length] = 0;
    }

    static String B(String host, String domain) {
        return e;
    }

    static String C(String user, String password, String host, String domain, byte[] nonce, int type2Flags, String target, byte[] targetInformation) {
        return new g(domain, host, user, password, nonce, type2Flags, target, targetInformation).f();
    }

    private static String S(String value) {
        if (value == null) {
            return null;
        }
        int index = value.indexOf(".");
        if (index != -1) {
            return value.substring(0, index);
        }
        return value;
    }

    /* access modifiers changed from: private */
    public static String y(String host) {
        return S(host);
    }

    /* access modifiers changed from: private */
    public static String x(String domain) {
        return S(domain);
    }

    /* access modifiers changed from: private */
    public static int P(byte[] src, int index) {
        if (src.length >= index + 4) {
            return (src[index] & 255) | ((src[index + 1] & 255) << 8) | ((src[index + 2] & 255) << MappingData.PATH) | ((src[index + 3] & 255) << 24);
        }
        throw new NTLMEngineException("NTLM authentication - buffer too small for DWORD");
    }

    private static int Q(byte[] src, int index) {
        if (src.length >= index + 2) {
            return (src[index] & 255) | ((src[index + 1] & 255) << 8);
        }
        throw new NTLMEngineException("NTLM authentication - buffer too small for WORD");
    }

    /* access modifiers changed from: private */
    public static byte[] O(byte[] src, int index) {
        int length = Q(src, index);
        int offset = P(src, index + 4);
        if (src.length >= offset + length) {
            byte[] buffer = new byte[length];
            System.arraycopy(src, offset, buffer, 0, length);
            return buffer;
        }
        throw new NTLMEngineException("NTLM authentication - buffer too small for data item");
    }

    /* access modifiers changed from: private */
    public static byte[] I() {
        SecureRandom secureRandom = c;
        if (secureRandom != null) {
            byte[] rval = new byte[8];
            synchronized (secureRandom) {
                secureRandom.nextBytes(rval);
            }
            return rval;
        }
        throw new NTLMEngineException("Random generator not available");
    }

    /* access modifiers changed from: private */
    public static byte[] J() {
        SecureRandom secureRandom = c;
        if (secureRandom != null) {
            byte[] rval = new byte[16];
            synchronized (secureRandom) {
                secureRandom.nextBytes(rval);
            }
            return rval;
        }
        throw new NTLMEngineException("Random generator not available");
    }

    /* compiled from: NTLMEngineImpl */
    public static class a {
        protected final String a;
        protected final String b;
        protected final String c;
        protected final byte[] d;
        protected final String e;
        protected final byte[] f;
        protected byte[] g;
        protected byte[] h;
        protected byte[] i;
        protected byte[] j;
        protected byte[] k;
        protected byte[] l;
        protected byte[] m;
        protected byte[] n;
        protected byte[] o;
        protected byte[] p;
        protected byte[] q;
        protected byte[] r;
        protected byte[] s;
        protected byte[] t;
        protected byte[] u;
        protected byte[] v;
        protected byte[] w;
        protected byte[] x;
        protected byte[] y;
        protected byte[] z;

        public a(String domain, String user, String password, byte[] challenge, String target, byte[] targetInformation, byte[] clientChallenge, byte[] clientChallenge2, byte[] secondaryKey, byte[] timestamp) {
            this.k = null;
            this.l = null;
            this.m = null;
            this.n = null;
            this.o = null;
            this.p = null;
            this.q = null;
            this.r = null;
            this.s = null;
            this.t = null;
            this.u = null;
            this.v = null;
            this.w = null;
            this.x = null;
            this.y = null;
            this.z = null;
            this.a = domain;
            this.e = target;
            this.b = user;
            this.c = password;
            this.d = challenge;
            this.f = targetInformation;
            this.g = clientChallenge;
            this.h = clientChallenge2;
            this.i = secondaryKey;
            this.j = timestamp;
        }

        public a(String domain, String user, String password, byte[] challenge, String target, byte[] targetInformation) {
            this(domain, user, password, challenge, target, targetInformation, (byte[]) null, (byte[]) null, (byte[]) null, (byte[]) null);
        }

        public byte[] a() {
            if (this.g == null) {
                this.g = l.I();
            }
            return this.g;
        }

        public byte[] b() {
            if (this.h == null) {
                this.h = l.I();
            }
            return this.h;
        }

        public byte[] s() {
            if (this.i == null) {
                this.i = l.J();
            }
            return this.i;
        }

        public byte[] d() {
            if (this.k == null) {
                this.k = l.E(this.c);
            }
            return this.k;
        }

        public byte[] e() {
            if (this.l == null) {
                this.l = l.F(d(), this.d);
            }
            return this.l;
        }

        public byte[] l() {
            if (this.m == null) {
                this.m = l.L(this.c);
            }
            return this.m;
        }

        public byte[] m() {
            if (this.n == null) {
                this.n = l.F(l(), this.d);
            }
            return this.n;
        }

        public byte[] g() {
            if (this.p == null) {
                this.p = l.G(this.a, this.b, l());
            }
            return this.p;
        }

        public byte[] p() {
            if (this.o == null) {
                this.o = l.M(this.a, this.b, l());
            }
            return this.o;
        }

        public byte[] t() {
            if (this.j == null) {
                long time = (System.currentTimeMillis() + 11644473600000L) * 10000;
                this.j = new byte[8];
                for (int i2 = 0; i2 < 8; i2++) {
                    this.j[i2] = (byte) ((int) time);
                    time >>>= 8;
                }
            }
            return this.j;
        }

        public byte[] o() {
            if (this.r == null) {
                this.r = l.z(b(), this.f, t());
            }
            return this.r;
        }

        public byte[] q() {
            if (this.s == null) {
                this.s = l.H(p(), this.d, o());
            }
            return this.s;
        }

        public byte[] h() {
            if (this.q == null) {
                this.q = l.H(g(), this.d, a());
            }
            return this.q;
        }

        public byte[] j() {
            if (this.t == null) {
                this.t = l.K(l(), this.d, a());
            }
            return this.t;
        }

        public byte[] c() {
            if (this.u == null) {
                byte[] clntChallenge = a();
                byte[] bArr = new byte[24];
                this.u = bArr;
                System.arraycopy(clntChallenge, 0, bArr, 0, clntChallenge.length);
                byte[] bArr2 = this.u;
                Arrays.fill(bArr2, clntChallenge.length, bArr2.length, (byte) 0);
            }
            return this.u;
        }

        public byte[] f() {
            if (this.v == null) {
                this.v = new byte[16];
                System.arraycopy(d(), 0, this.v, 0, 8);
                Arrays.fill(this.v, 8, 16, (byte) 0);
            }
            return this.v;
        }

        public byte[] n() {
            if (this.w == null) {
                c md4 = new c();
                md4.f(l());
                this.w = md4.a();
            }
            return this.w;
        }

        public byte[] r() {
            if (this.x == null) {
                byte[] ntlmv2hash = p();
                byte[] truncatedResponse = new byte[16];
                System.arraycopy(q(), 0, truncatedResponse, 0, 16);
                this.x = l.D(truncatedResponse, ntlmv2hash);
            }
            return this.x;
        }

        public byte[] k() {
            if (this.y == null) {
                byte[] ntlm2SessionResponseNonce = c();
                byte[] bArr = this.d;
                byte[] sessionNonce = new byte[(bArr.length + ntlm2SessionResponseNonce.length)];
                System.arraycopy(bArr, 0, sessionNonce, 0, bArr.length);
                System.arraycopy(ntlm2SessionResponseNonce, 0, sessionNonce, this.d.length, ntlm2SessionResponseNonce.length);
                this.y = l.D(sessionNonce, n());
            }
            return this.y;
        }

        public byte[] i() {
            if (this.z == null) {
                try {
                    byte[] keyBytes = new byte[14];
                    System.arraycopy(d(), 0, keyBytes, 0, 8);
                    Arrays.fill(keyBytes, 8, keyBytes.length, (byte) -67);
                    Key lowKey = l.A(keyBytes, 0);
                    Key highKey = l.A(keyBytes, 7);
                    byte[] truncatedResponse = new byte[8];
                    System.arraycopy(e(), 0, truncatedResponse, 0, truncatedResponse.length);
                    Cipher des = Cipher.getInstance("DES/ECB/NoPadding");
                    des.init(1, lowKey);
                    byte[] lowPart = des.doFinal(truncatedResponse);
                    Cipher des2 = Cipher.getInstance("DES/ECB/NoPadding");
                    des2.init(1, highKey);
                    byte[] highPart = des2.doFinal(truncatedResponse);
                    byte[] bArr = new byte[16];
                    this.z = bArr;
                    System.arraycopy(lowPart, 0, bArr, 0, lowPart.length);
                    System.arraycopy(highPart, 0, this.z, lowPart.length, highPart.length);
                } catch (Exception e2) {
                    throw new NTLMEngineException(e2.getMessage(), e2);
                }
            }
            return this.z;
        }
    }

    static byte[] D(byte[] value, byte[] key) {
        b hmacMD5 = new b(key);
        hmacMD5.b(value);
        return hmacMD5.a();
    }

    static byte[] f(byte[] value, byte[] key) {
        try {
            Cipher rc4 = Cipher.getInstance("RC4");
            rc4.init(1, new SecretKeySpec(key, "RC4"));
            return rc4.doFinal(value);
        } catch (Exception e2) {
            throw new NTLMEngineException(e2.getMessage(), e2);
        }
    }

    static byte[] K(byte[] ntlmHash, byte[] challenge, byte[] clientChallenge) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(challenge);
            md5.update(clientChallenge);
            byte[] sessionHash = new byte[8];
            System.arraycopy(md5.digest(), 0, sessionHash, 0, 8);
            return F(ntlmHash, sessionHash);
        } catch (Exception e2) {
            if (e2 instanceof NTLMEngineException) {
                throw ((NTLMEngineException) e2);
            }
            throw new NTLMEngineException(e2.getMessage(), e2);
        }
    }

    /* access modifiers changed from: private */
    public static byte[] E(String password) {
        try {
            String upperCase = password.toUpperCase(Locale.ROOT);
            Charset charset = org.apache.http.b.b;
            byte[] oemPassword = upperCase.getBytes(charset);
            byte[] keyBytes = new byte[14];
            System.arraycopy(oemPassword, 0, keyBytes, 0, Math.min(oemPassword.length, 14));
            Key lowKey = A(keyBytes, 0);
            Key highKey = A(keyBytes, 7);
            byte[] magicConstant = "KGS!@#$%".getBytes(charset);
            Cipher des = Cipher.getInstance("DES/ECB/NoPadding");
            des.init(1, lowKey);
            byte[] lowHash = des.doFinal(magicConstant);
            des.init(1, highKey);
            byte[] highHash = des.doFinal(magicConstant);
            byte[] lmHash = new byte[16];
            System.arraycopy(lowHash, 0, lmHash, 0, 8);
            System.arraycopy(highHash, 0, lmHash, 8, 8);
            return lmHash;
        } catch (Exception e2) {
            throw new NTLMEngineException(e2.getMessage(), e2);
        }
    }

    /* access modifiers changed from: private */
    public static byte[] L(String password) {
        Charset charset = a;
        if (charset != null) {
            byte[] unicodePassword = password.getBytes(charset);
            c md4 = new c();
            md4.f(unicodePassword);
            return md4.a();
        }
        throw new NTLMEngineException("Unicode not supported");
    }

    /* access modifiers changed from: private */
    public static byte[] G(String domain, String user, byte[] ntlmHash) {
        Charset charset = a;
        if (charset != null) {
            b hmacMD5 = new b(ntlmHash);
            Locale locale = Locale.ROOT;
            hmacMD5.b(user.toUpperCase(locale).getBytes(charset));
            if (domain != null) {
                hmacMD5.b(domain.toUpperCase(locale).getBytes(charset));
            }
            return hmacMD5.a();
        }
        throw new NTLMEngineException("Unicode not supported");
    }

    /* access modifiers changed from: private */
    public static byte[] M(String domain, String user, byte[] ntlmHash) {
        Charset charset = a;
        if (charset != null) {
            b hmacMD5 = new b(ntlmHash);
            hmacMD5.b(user.toUpperCase(Locale.ROOT).getBytes(charset));
            if (domain != null) {
                hmacMD5.b(domain.getBytes(charset));
            }
            return hmacMD5.a();
        }
        throw new NTLMEngineException("Unicode not supported");
    }

    /* access modifiers changed from: private */
    public static byte[] F(byte[] hash, byte[] challenge) {
        try {
            byte[] keyBytes = new byte[21];
            System.arraycopy(hash, 0, keyBytes, 0, 16);
            Key lowKey = A(keyBytes, 0);
            Key middleKey = A(keyBytes, 7);
            Key highKey = A(keyBytes, 14);
            Cipher des = Cipher.getInstance("DES/ECB/NoPadding");
            des.init(1, lowKey);
            byte[] lowResponse = des.doFinal(challenge);
            des.init(1, middleKey);
            byte[] middleResponse = des.doFinal(challenge);
            des.init(1, highKey);
            byte[] highResponse = des.doFinal(challenge);
            byte[] lmResponse = new byte[24];
            System.arraycopy(lowResponse, 0, lmResponse, 0, 8);
            System.arraycopy(middleResponse, 0, lmResponse, 8, 8);
            System.arraycopy(highResponse, 0, lmResponse, 16, 8);
            return lmResponse;
        } catch (Exception e2) {
            throw new NTLMEngineException(e2.getMessage(), e2);
        }
    }

    /* access modifiers changed from: private */
    public static byte[] H(byte[] hash, byte[] challenge, byte[] clientData) {
        b hmacMD5 = new b(hash);
        hmacMD5.b(challenge);
        hmacMD5.b(clientData);
        byte[] mac = hmacMD5.a();
        byte[] lmv2Response = new byte[(mac.length + clientData.length)];
        System.arraycopy(mac, 0, lmv2Response, 0, mac.length);
        System.arraycopy(clientData, 0, lmv2Response, mac.length, clientData.length);
        return lmv2Response;
    }

    /* access modifiers changed from: private */
    public static byte[] z(byte[] clientChallenge, byte[] targetInformation, byte[] timestamp) {
        byte[] blobSignature = {1, 1, 0, 0};
        byte[] reserved = {0, 0, 0, 0};
        byte[] unknown1 = {0, 0, 0, 0};
        byte[] unknown2 = {0, 0, 0, 0};
        byte[] blob = new byte[(blobSignature.length + reserved.length + timestamp.length + 8 + unknown1.length + targetInformation.length + unknown2.length)];
        System.arraycopy(blobSignature, 0, blob, 0, blobSignature.length);
        int offset = 0 + blobSignature.length;
        System.arraycopy(reserved, 0, blob, offset, reserved.length);
        int offset2 = offset + reserved.length;
        System.arraycopy(timestamp, 0, blob, offset2, timestamp.length);
        int offset3 = offset2 + timestamp.length;
        System.arraycopy(clientChallenge, 0, blob, offset3, 8);
        int offset4 = offset3 + 8;
        System.arraycopy(unknown1, 0, blob, offset4, unknown1.length);
        int offset5 = offset4 + unknown1.length;
        System.arraycopy(targetInformation, 0, blob, offset5, targetInformation.length);
        int offset6 = offset5 + targetInformation.length;
        System.arraycopy(unknown2, 0, blob, offset6, unknown2.length);
        int offset7 = offset6 + unknown2.length;
        return blob;
    }

    /* access modifiers changed from: private */
    public static Key A(byte[] bytes, int offset) {
        byte[] keyBytes = new byte[7];
        System.arraycopy(bytes, offset, keyBytes, 0, 7);
        byte[] material = {keyBytes[0], (byte) ((keyBytes[0] << 7) | ((keyBytes[1] & 255) >>> 1)), (byte) ((keyBytes[1] << 6) | ((keyBytes[2] & 255) >>> 2)), (byte) ((keyBytes[2] << 5) | ((keyBytes[3] & 255) >>> 3)), (byte) ((keyBytes[3] << 4) | ((keyBytes[4] & 255) >>> 4)), (byte) ((keyBytes[4] << 3) | ((keyBytes[5] & 255) >>> 5)), (byte) ((keyBytes[5] << 2) | ((keyBytes[6] & 255) >>> 6)), (byte) (keyBytes[6] << 1)};
        N(material);
        return new SecretKeySpec(material, "DES");
    }

    private static void N(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            byte b2 = bytes[i];
            if (((((((((b2 >>> 7) ^ (b2 >>> 6)) ^ (b2 >>> 5)) ^ (b2 >>> 4)) ^ (b2 >>> 3)) ^ (b2 >>> 2)) ^ (b2 >>> 1)) & 1) == 0) {
                bytes[i] = (byte) (1 | bytes[i]);
            } else {
                bytes[i] = (byte) (bytes[i] & -2);
            }
        }
    }

    /* compiled from: NTLMEngineImpl */
    public static class d {
        private byte[] a = null;
        private int b = 0;

        d() {
        }

        d(String messageBody, int expectedType) {
            byte[] m = org.apache.commons.codec.binary.a.m(messageBody.getBytes(l.b));
            this.a = m;
            if (m.length >= l.d.length) {
                int i = 0;
                while (i < l.d.length) {
                    if (this.a[i] == l.d[i]) {
                        i++;
                    } else {
                        throw new NTLMEngineException("NTLM message expected - instead got unrecognized bytes");
                    }
                }
                int type = j(l.d.length);
                if (type == expectedType) {
                    this.b = this.a.length;
                    return;
                }
                throw new NTLMEngineException("NTLM type " + Integer.toString(expectedType) + " message expected - instead got type " + Integer.toString(type));
            }
            throw new NTLMEngineException("NTLM message decoding error - packet too short");
        }

        /* access modifiers changed from: protected */
        public int e() {
            return this.b;
        }

        /* access modifiers changed from: protected */
        public void h(byte[] buffer, int position) {
            byte[] bArr = this.a;
            if (bArr.length >= buffer.length + position) {
                System.arraycopy(bArr, position, buffer, 0, buffer.length);
                return;
            }
            throw new NTLMEngineException("NTLM: Message too short");
        }

        /* access modifiers changed from: protected */
        public int j(int position) {
            return l.P(this.a, position);
        }

        /* access modifiers changed from: protected */
        public byte[] i(int position) {
            return l.O(this.a, position);
        }

        /* access modifiers changed from: protected */
        public void g(int maxlength, int messageType) {
            this.a = new byte[maxlength];
            this.b = 0;
            b(l.d);
            c(messageType);
        }

        /* access modifiers changed from: protected */
        public void a(byte b2) {
            byte[] bArr = this.a;
            int i = this.b;
            bArr[i] = b2;
            this.b = i + 1;
        }

        /* access modifiers changed from: protected */
        public void b(byte[] bytes) {
            if (bytes != null) {
                for (byte b2 : bytes) {
                    byte[] bArr = this.a;
                    int i = this.b;
                    bArr[i] = b2;
                    this.b = i + 1;
                }
            }
        }

        /* access modifiers changed from: protected */
        public void d(int value) {
            a((byte) (value & 255));
            a((byte) ((value >> 8) & 255));
        }

        /* access modifiers changed from: protected */
        public void c(int value) {
            a((byte) (value & 255));
            a((byte) ((value >> 8) & 255));
            a((byte) ((value >> 16) & 255));
            a((byte) ((value >> 24) & 255));
        }

        /* access modifiers changed from: package-private */
        public String f() {
            byte[] resp;
            byte[] bArr = this.a;
            int length = bArr.length;
            int i = this.b;
            if (length > i) {
                byte[] tmp = new byte[i];
                System.arraycopy(bArr, 0, tmp, 0, i);
                resp = tmp;
            } else {
                resp = this.a;
            }
            return org.apache.http.util.f.b(org.apache.commons.codec.binary.a.n(resp));
        }
    }

    /* compiled from: NTLMEngineImpl */
    public static class e extends d {
        private final byte[] c = null;
        private final byte[] d = null;

        e() {
        }

        /* access modifiers changed from: package-private */
        public String f() {
            g(40, 1);
            c(-1576500735);
            d(0);
            d(0);
            c(40);
            d(0);
            d(0);
            c(40);
            d(261);
            c(2600);
            d(3840);
            byte[] bArr = this.c;
            if (bArr != null) {
                b(bArr);
            }
            byte[] bArr2 = this.d;
            if (bArr2 != null) {
                b(bArr2);
            }
            return super.f();
        }
    }

    /* compiled from: NTLMEngineImpl */
    public static class f extends d {
        protected byte[] c;
        protected String d;
        protected byte[] e;
        protected int f;

        f(String message) {
            super(message, 2);
            byte[] bArr = new byte[8];
            this.c = bArr;
            h(bArr, 24);
            int j = j(20);
            this.f = j;
            if ((j & 1) != 0) {
                this.d = null;
                if (e() >= 20) {
                    byte[] bytes = i(12);
                    if (bytes.length != 0) {
                        try {
                            this.d = new String(bytes, "UnicodeLittleUnmarked");
                        } catch (UnsupportedEncodingException e2) {
                            throw new NTLMEngineException(e2.getMessage(), e2);
                        }
                    }
                }
                this.e = null;
                if (e() >= 48) {
                    byte[] bytes2 = i(40);
                    if (bytes2.length != 0) {
                        this.e = bytes2;
                        return;
                    }
                    return;
                }
                return;
            }
            throw new NTLMEngineException("NTLM type 2 message indicates no support for Unicode. Flags are: " + Integer.toString(this.f));
        }

        /* access modifiers changed from: package-private */
        public byte[] k() {
            return this.c;
        }

        /* access modifiers changed from: package-private */
        public String m() {
            return this.d;
        }

        /* access modifiers changed from: package-private */
        public byte[] n() {
            return this.e;
        }

        /* access modifiers changed from: package-private */
        public int l() {
            return this.f;
        }
    }

    /* compiled from: NTLMEngineImpl */
    public static class g extends d {
        protected int c;
        protected byte[] d;
        protected byte[] e;
        protected byte[] f;
        protected byte[] g;
        protected byte[] h;
        protected byte[] i;

        g(String domain, String host, String user, String password, byte[] nonce, int type2Flags, String target, byte[] targetInformation) {
            byte[] userSessionKey;
            int i2 = type2Flags;
            this.c = i2;
            String unqualifiedHost = l.y(host);
            String unqualifiedDomain = l.x(domain);
            a gen = new a(unqualifiedDomain, user, password, nonce, target, targetInformation);
            byte[] bArr = null;
            if ((8388608 & i2) != 0 && targetInformation != null && target != null) {
                try {
                    this.h = gen.q();
                    this.g = gen.h();
                    if ((i2 & 128) != 0) {
                        userSessionKey = gen.i();
                    } else {
                        userSessionKey = gen.r();
                    }
                } catch (NTLMEngineException e2) {
                    this.h = new byte[0];
                    this.g = gen.e();
                    if ((i2 & 128) != 0) {
                        userSessionKey = gen.i();
                    } else {
                        userSessionKey = gen.f();
                    }
                }
            } else if ((524288 & i2) != 0) {
                this.h = gen.j();
                this.g = gen.c();
                if ((i2 & 128) != 0) {
                    userSessionKey = gen.i();
                } else {
                    userSessionKey = gen.k();
                }
            } else {
                this.h = gen.m();
                this.g = gen.e();
                if ((i2 & 128) != 0) {
                    userSessionKey = gen.i();
                } else {
                    userSessionKey = gen.n();
                }
            }
            if ((i2 & 16) == 0) {
                this.i = null;
            } else if ((1073741824 & i2) != 0) {
                this.i = l.f(gen.s(), userSessionKey);
            } else {
                this.i = userSessionKey;
            }
            if (l.a != null) {
                this.e = unqualifiedHost != null ? unqualifiedHost.getBytes(l.a) : null;
                this.d = unqualifiedDomain != null ? unqualifiedDomain.toUpperCase(Locale.ROOT).getBytes(l.a) : bArr;
                String str = user;
                this.f = user.getBytes(l.a);
                return;
            }
            String str2 = user;
            throw new NTLMEngineException("Unicode not supported");
        }

        /* access modifiers changed from: package-private */
        public String f() {
            int sessionKeyLen;
            int ntRespLen = this.h.length;
            int lmRespLen = this.g.length;
            byte[] bArr = this.d;
            int hostLen = 0;
            int domainLen = bArr != null ? bArr.length : 0;
            byte[] bArr2 = this.e;
            if (bArr2 != null) {
                hostLen = bArr2.length;
            }
            int userLen = this.f.length;
            byte[] bArr3 = this.i;
            if (bArr3 != null) {
                sessionKeyLen = bArr3.length;
            } else {
                sessionKeyLen = 0;
            }
            int ntRespOffset = lmRespLen + 72;
            int domainOffset = ntRespOffset + ntRespLen;
            int userOffset = domainOffset + domainLen;
            int hostOffset = userOffset + userLen;
            int sessionKeyOffset = hostOffset + hostLen;
            g(sessionKeyOffset + sessionKeyLen, 3);
            d(lmRespLen);
            d(lmRespLen);
            c(72);
            d(ntRespLen);
            d(ntRespLen);
            c(ntRespOffset);
            d(domainLen);
            d(domainLen);
            c(domainOffset);
            d(userLen);
            d(userLen);
            c(userOffset);
            d(hostLen);
            d(hostLen);
            c(hostOffset);
            d(sessionKeyLen);
            d(sessionKeyLen);
            c(sessionKeyOffset);
            int i2 = this.c;
            int i3 = ntRespLen;
            c((i2 & 512) | (i2 & 128) | (524288 & i2) | 33554432 | (32768 & i2) | (i2 & 32) | (i2 & 16) | (536870912 & i2) | (Integer.MIN_VALUE & i2) | (1073741824 & i2) | (8388608 & i2) | (i2 & 1) | (i2 & 4));
            d(261);
            c(2600);
            d(3840);
            b(this.g);
            b(this.h);
            b(this.d);
            b(this.f);
            b(this.e);
            byte[] bArr4 = this.i;
            if (bArr4 != null) {
                b(bArr4);
            }
            return super.f();
        }
    }

    static void T(byte[] buffer, int value, int offset) {
        buffer[offset] = (byte) (value & 255);
        buffer[offset + 1] = (byte) ((value >> 8) & 255);
        buffer[offset + 2] = (byte) ((value >> 16) & 255);
        buffer[offset + 3] = (byte) ((value >> 24) & 255);
    }

    static int c(int x, int y, int z) {
        return (x & y) | ((~x) & z);
    }

    static int d(int x, int y, int z) {
        return (x & y) | (x & z) | (y & z);
    }

    static int e(int x, int y, int z) {
        return (x ^ y) ^ z;
    }

    static int R(int val, int numbits) {
        return (val << numbits) | (val >>> (32 - numbits));
    }

    /* compiled from: NTLMEngineImpl */
    public static class c {
        protected int a = 1732584193;
        protected int b = -271733879;
        protected int c = -1732584194;
        protected int d = 271733878;
        protected long e = 0;
        protected byte[] f = new byte[64];

        c() {
        }

        /* access modifiers changed from: package-private */
        public void f(byte[] input) {
            byte[] bArr;
            int curBufferPos = (int) (this.e & 63);
            int inputIndex = 0;
            while (true) {
                int length = (input.length - inputIndex) + curBufferPos;
                bArr = this.f;
                if (length < bArr.length) {
                    break;
                }
                int transferAmt = bArr.length - curBufferPos;
                System.arraycopy(input, inputIndex, bArr, curBufferPos, transferAmt);
                this.e += (long) transferAmt;
                curBufferPos = 0;
                inputIndex += transferAmt;
                b();
            }
            if (inputIndex < input.length) {
                int transferAmt2 = input.length - inputIndex;
                System.arraycopy(input, inputIndex, bArr, curBufferPos, transferAmt2);
                this.e += (long) transferAmt2;
                int curBufferPos2 = curBufferPos + transferAmt2;
            }
        }

        /* access modifiers changed from: package-private */
        public byte[] a() {
            int bufferIndex = (int) (this.e & 63);
            int padLen = bufferIndex < 56 ? 56 - bufferIndex : 120 - bufferIndex;
            byte[] postBytes = new byte[(padLen + 8)];
            postBytes[0] = OTACommand.RESP_ID_VERSION;
            for (int i = 0; i < 8; i++) {
                postBytes[padLen + i] = (byte) ((int) ((this.e * 8) >>> (i * 8)));
            }
            f(postBytes);
            byte[] result = new byte[16];
            l.T(result, this.a, 0);
            l.T(result, this.b, 4);
            l.T(result, this.c, 8);
            l.T(result, this.d, 12);
            return result;
        }

        /* access modifiers changed from: protected */
        public void b() {
            int[] d2 = new int[16];
            for (int i = 0; i < 16; i++) {
                byte[] bArr = this.f;
                d2[i] = (bArr[i * 4] & 255) + ((bArr[(i * 4) + 1] & 255) << 8) + ((bArr[(i * 4) + 2] & 255) << MappingData.PATH) + ((bArr[(i * 4) + 3] & 255) << 24);
            }
            int AA = this.a;
            int BB = this.b;
            int CC = this.c;
            int DD = this.d;
            c(d2);
            d(d2);
            e(d2);
            this.a += AA;
            this.b += BB;
            this.c += CC;
            this.d += DD;
        }

        /* access modifiers changed from: protected */
        public void c(int[] d2) {
            int R = l.R(this.a + l.c(this.b, this.c, this.d) + d2[0], 3);
            this.a = R;
            int R2 = l.R(this.d + l.c(R, this.b, this.c) + d2[1], 7);
            this.d = R2;
            int R3 = l.R(this.c + l.c(R2, this.a, this.b) + d2[2], 11);
            this.c = R3;
            int R4 = l.R(this.b + l.c(R3, this.d, this.a) + d2[3], 19);
            this.b = R4;
            int R5 = l.R(this.a + l.c(R4, this.c, this.d) + d2[4], 3);
            this.a = R5;
            int R6 = l.R(this.d + l.c(R5, this.b, this.c) + d2[5], 7);
            this.d = R6;
            int R7 = l.R(this.c + l.c(R6, this.a, this.b) + d2[6], 11);
            this.c = R7;
            int R8 = l.R(this.b + l.c(R7, this.d, this.a) + d2[7], 19);
            this.b = R8;
            int R9 = l.R(this.a + l.c(R8, this.c, this.d) + d2[8], 3);
            this.a = R9;
            int R10 = l.R(this.d + l.c(R9, this.b, this.c) + d2[9], 7);
            this.d = R10;
            int R11 = l.R(this.c + l.c(R10, this.a, this.b) + d2[10], 11);
            this.c = R11;
            int R12 = l.R(this.b + l.c(R11, this.d, this.a) + d2[11], 19);
            this.b = R12;
            int R13 = l.R(this.a + l.c(R12, this.c, this.d) + d2[12], 3);
            this.a = R13;
            int R14 = l.R(this.d + l.c(R13, this.b, this.c) + d2[13], 7);
            this.d = R14;
            int R15 = l.R(this.c + l.c(R14, this.a, this.b) + d2[14], 11);
            this.c = R15;
            this.b = l.R(this.b + l.c(R15, this.d, this.a) + d2[15], 19);
        }

        /* access modifiers changed from: protected */
        public void d(int[] d2) {
            int R = l.R(this.a + l.d(this.b, this.c, this.d) + d2[0] + 1518500249, 3);
            this.a = R;
            int R2 = l.R(this.d + l.d(R, this.b, this.c) + d2[4] + 1518500249, 5);
            this.d = R2;
            int R3 = l.R(this.c + l.d(R2, this.a, this.b) + d2[8] + 1518500249, 9);
            this.c = R3;
            int R4 = l.R(this.b + l.d(R3, this.d, this.a) + d2[12] + 1518500249, 13);
            this.b = R4;
            int R5 = l.R(this.a + l.d(R4, this.c, this.d) + d2[1] + 1518500249, 3);
            this.a = R5;
            int R6 = l.R(this.d + l.d(R5, this.b, this.c) + d2[5] + 1518500249, 5);
            this.d = R6;
            int R7 = l.R(this.c + l.d(R6, this.a, this.b) + d2[9] + 1518500249, 9);
            this.c = R7;
            int R8 = l.R(this.b + l.d(R7, this.d, this.a) + d2[13] + 1518500249, 13);
            this.b = R8;
            int R9 = l.R(this.a + l.d(R8, this.c, this.d) + d2[2] + 1518500249, 3);
            this.a = R9;
            int R10 = l.R(this.d + l.d(R9, this.b, this.c) + d2[6] + 1518500249, 5);
            this.d = R10;
            int R11 = l.R(this.c + l.d(R10, this.a, this.b) + d2[10] + 1518500249, 9);
            this.c = R11;
            int R12 = l.R(this.b + l.d(R11, this.d, this.a) + d2[14] + 1518500249, 13);
            this.b = R12;
            int R13 = l.R(this.a + l.d(R12, this.c, this.d) + d2[3] + 1518500249, 3);
            this.a = R13;
            int R14 = l.R(this.d + l.d(R13, this.b, this.c) + d2[7] + 1518500249, 5);
            this.d = R14;
            int R15 = l.R(this.c + l.d(R14, this.a, this.b) + d2[11] + 1518500249, 9);
            this.c = R15;
            this.b = l.R(this.b + l.d(R15, this.d, this.a) + d2[15] + 1518500249, 13);
        }

        /* access modifiers changed from: protected */
        public void e(int[] d2) {
            int R = l.R(this.a + l.e(this.b, this.c, this.d) + d2[0] + 1859775393, 3);
            this.a = R;
            int R2 = l.R(this.d + l.e(R, this.b, this.c) + d2[8] + 1859775393, 9);
            this.d = R2;
            int R3 = l.R(this.c + l.e(R2, this.a, this.b) + d2[4] + 1859775393, 11);
            this.c = R3;
            int R4 = l.R(this.b + l.e(R3, this.d, this.a) + d2[12] + 1859775393, 15);
            this.b = R4;
            int R5 = l.R(this.a + l.e(R4, this.c, this.d) + d2[2] + 1859775393, 3);
            this.a = R5;
            int R6 = l.R(this.d + l.e(R5, this.b, this.c) + d2[10] + 1859775393, 9);
            this.d = R6;
            int R7 = l.R(this.c + l.e(R6, this.a, this.b) + d2[6] + 1859775393, 11);
            this.c = R7;
            int R8 = l.R(this.b + l.e(R7, this.d, this.a) + d2[14] + 1859775393, 15);
            this.b = R8;
            int R9 = l.R(this.a + l.e(R8, this.c, this.d) + d2[1] + 1859775393, 3);
            this.a = R9;
            int R10 = l.R(this.d + l.e(R9, this.b, this.c) + d2[9] + 1859775393, 9);
            this.d = R10;
            int R11 = l.R(this.c + l.e(R10, this.a, this.b) + d2[5] + 1859775393, 11);
            this.c = R11;
            int R12 = l.R(this.b + l.e(R11, this.d, this.a) + d2[13] + 1859775393, 15);
            this.b = R12;
            int R13 = l.R(this.a + l.e(R12, this.c, this.d) + d2[3] + 1859775393, 3);
            this.a = R13;
            int R14 = l.R(this.d + l.e(R13, this.b, this.c) + d2[11] + 1859775393, 9);
            this.d = R14;
            int R15 = l.R(this.c + l.e(R14, this.a, this.b) + d2[7] + 1859775393, 11);
            this.c = R15;
            this.b = l.R(this.b + l.e(R15, this.d, this.a) + d2[15] + 1859775393, 15);
        }
    }

    /* compiled from: NTLMEngineImpl */
    public static class b {
        protected byte[] a;
        protected byte[] b;
        protected MessageDigest c;

        b(byte[] input) {
            byte[] key = input;
            try {
                MessageDigest instance = MessageDigest.getInstance("MD5");
                this.c = instance;
                this.a = new byte[64];
                this.b = new byte[64];
                int keyLength = key.length;
                if (keyLength > 64) {
                    instance.update(key);
                    key = this.c.digest();
                    keyLength = key.length;
                }
                int i = 0;
                while (i < keyLength) {
                    this.a[i] = (byte) (54 ^ key[i]);
                    this.b[i] = (byte) (92 ^ key[i]);
                    i++;
                }
                while (i < 64) {
                    this.a[i] = 54;
                    this.b[i] = 92;
                    i++;
                }
                this.c.reset();
                this.c.update(this.a);
            } catch (Exception ex) {
                throw new NTLMEngineException("Error getting md5 message digest implementation: " + ex.getMessage(), ex);
            }
        }

        /* access modifiers changed from: package-private */
        public byte[] a() {
            byte[] digest = this.c.digest();
            this.c.update(this.b);
            return this.c.digest(digest);
        }

        /* access modifiers changed from: package-private */
        public void b(byte[] input) {
            this.c.update(input);
        }
    }

    public String b(String domain, String workstation) {
        return B(workstation, domain);
    }

    public String a(String username, String password, String domain, String workstation, String challenge) {
        f t2m = new f(challenge);
        return C(username, password, workstation, domain, t2m.k(), t2m.l(), t2m.m(), t2m.n());
    }
}
