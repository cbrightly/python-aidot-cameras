package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import org.spongycastle.util.Arrays;

public final class SessionParameters {
    private int a;
    private short b;
    private byte[] c;
    private Certificate d;
    private byte[] e;
    private byte[] f;
    private byte[] g;

    public static final class Builder {
        private int a = -1;
        private short b = -1;
        private byte[] c = null;
        private Certificate d = null;
        private byte[] e = null;
        private byte[] f = null;
        private byte[] g = null;

        public SessionParameters a() {
            boolean z = true;
            i(this.a >= 0, "cipherSuite");
            i(this.b >= 0, "compressionAlgorithm");
            if (this.c == null) {
                z = false;
            }
            i(z, "masterSecret");
            return new SessionParameters(this.a, this.b, this.c, this.d, this.e, this.f, this.g);
        }

        public Builder b(int cipherSuite) {
            this.a = cipherSuite;
            return this;
        }

        public Builder c(short compressionAlgorithm) {
            this.b = compressionAlgorithm;
            return this;
        }

        public Builder d(byte[] masterSecret) {
            this.c = masterSecret;
            return this;
        }

        public Builder f(Certificate peerCertificate) {
            this.d = peerCertificate;
            return this;
        }

        public Builder e(byte[] pskIdentity) {
            this.e = pskIdentity;
            return this;
        }

        public Builder g(byte[] srpIdentity) {
            this.f = srpIdentity;
            return this;
        }

        public Builder h(Hashtable serverExtensions) {
            if (serverExtensions == null) {
                this.g = null;
            } else {
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                TlsProtocol.S(buf, serverExtensions);
                this.g = buf.toByteArray();
            }
            return this;
        }

        private void i(boolean condition, String parameter) {
            if (!condition) {
                throw new IllegalStateException("Required session parameter '" + parameter + "' not configured");
            }
        }
    }

    private SessionParameters(int cipherSuite, short compressionAlgorithm, byte[] masterSecret, Certificate peerCertificate, byte[] pskIdentity, byte[] srpIdentity, byte[] encodedServerExtensions) {
        this.e = null;
        this.f = null;
        this.a = cipherSuite;
        this.b = compressionAlgorithm;
        this.c = Arrays.h(masterSecret);
        this.d = peerCertificate;
        this.e = Arrays.h(pskIdentity);
        this.f = Arrays.h(srpIdentity);
        this.g = encodedServerExtensions;
    }

    public void a() {
        byte[] bArr = this.c;
        if (bArr != null) {
            Arrays.F(bArr, (byte) 0);
        }
    }

    public int b() {
        return this.a;
    }

    public short c() {
        return this.b;
    }

    public byte[] d() {
        return this.c;
    }

    public Hashtable e() {
        if (this.g == null) {
            return null;
        }
        return TlsProtocol.I(new ByteArrayInputStream(this.g));
    }
}
