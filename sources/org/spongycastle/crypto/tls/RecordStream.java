package org.spongycastle.crypto.tls;

import androidx.core.view.InputDeviceCompat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.util.io.SimpleOutputStream;

public class RecordStream {
    private static int a = 16384;
    private TlsProtocol b;
    private InputStream c;
    private OutputStream d;
    private TlsCompression e;
    private TlsCompression f;
    private TlsCompression g;
    private TlsCipher h;
    private TlsCipher i;
    private TlsCipher j;
    private SequenceNumber k;
    private SequenceNumber l;
    private ByteArrayOutputStream m;
    /* access modifiers changed from: private */
    public TlsHandshakeHash n;
    private SimpleOutputStream o;
    private ProtocolVersion p;
    private ProtocolVersion q;
    private boolean r;
    private int s;
    private int t;
    private int u;

    /* renamed from: org.spongycastle.crypto.tls.RecordStream$1  reason: invalid class name */
    public class AnonymousClass1 extends SimpleOutputStream {
        final /* synthetic */ RecordStream c;

        public void write(byte[] buf, int off, int len) {
            this.c.n.update(buf, off, len);
        }
    }

    /* access modifiers changed from: package-private */
    public int j() {
        return this.s;
    }

    /* access modifiers changed from: package-private */
    public void s(int plaintextLimit) {
        this.s = plaintextLimit;
        int i2 = plaintextLimit + 1024;
        this.t = i2;
        this.u = i2 + 1024;
    }

    /* access modifiers changed from: package-private */
    public ProtocolVersion k() {
        return this.p;
    }

    /* access modifiers changed from: package-private */
    public void t(ProtocolVersion readVersion) {
        this.p = readVersion;
    }

    /* access modifiers changed from: package-private */
    public void v(ProtocolVersion writeVersion) {
        this.q = writeVersion;
    }

    /* access modifiers changed from: package-private */
    public void u(boolean enabled) {
        this.r = enabled;
    }

    /* access modifiers changed from: package-private */
    public void r(TlsCompression tlsCompression, TlsCipher tlsCipher) {
        this.e = tlsCompression;
        this.h = tlsCipher;
    }

    /* access modifiers changed from: package-private */
    public void q() {
        TlsCipher tlsCipher;
        TlsCompression tlsCompression = this.e;
        if (tlsCompression == null || (tlsCipher = this.h) == null) {
            throw new TlsFatalAlert(40);
        }
        this.g = tlsCompression;
        this.j = tlsCipher;
        this.l = new SequenceNumber((AnonymousClass1) null);
    }

    /* access modifiers changed from: package-private */
    public void o() {
        TlsCipher tlsCipher;
        TlsCompression tlsCompression = this.e;
        if (tlsCompression == null || (tlsCipher = this.h) == null) {
            throw new TlsFatalAlert(40);
        }
        this.f = tlsCompression;
        this.i = tlsCipher;
        this.k = new SequenceNumber((AnonymousClass1) null);
    }

    /* access modifiers changed from: package-private */
    public void e() {
        TlsCipher tlsCipher;
        TlsCompression tlsCompression = this.f;
        TlsCompression tlsCompression2 = this.e;
        if (tlsCompression == tlsCompression2 && this.g == tlsCompression2 && this.i == (tlsCipher = this.h) && this.j == tlsCipher) {
            this.e = null;
            this.h = null;
            return;
        }
        throw new TlsFatalAlert(40);
    }

    /* access modifiers changed from: package-private */
    public boolean n() {
        byte[] recordHeader = TlsUtils.d0(5, this.c);
        if (recordHeader == null) {
            return false;
        }
        short type = TlsUtils.r0(recordHeader, 0);
        c(type, 10);
        if (this.r) {
            ProtocolVersion version = TlsUtils.u0(recordHeader, 1);
            ProtocolVersion protocolVersion = this.p;
            if (protocolVersion == null) {
                this.p = version;
            } else if (!version.a(protocolVersion)) {
                throw new TlsFatalAlert(47);
            }
        } else if ((TlsUtils.v0(recordHeader, 1) & InputDeviceCompat.SOURCE_ANY) != 768) {
            throw new TlsFatalAlert(47);
        }
        int length = TlsUtils.l0(recordHeader, 3);
        b(length, this.u, 22);
        byte[] plaintext = d(type, this.c, length);
        this.b.E(type, plaintext, 0, plaintext.length);
        return true;
    }

    /* access modifiers changed from: package-private */
    public byte[] d(short type, InputStream input, int len) {
        byte[] buf = TlsUtils.f0(len, input);
        byte[] decoded = this.i.a(this.k.a(10), type, buf, 0, buf.length);
        b(decoded.length, this.t, 22);
        OutputStream cOut = this.f.a(this.m);
        if (cOut != this.m) {
            cOut.write(decoded, 0, decoded.length);
            cOut.flush();
            decoded = g();
        }
        b(decoded.length, this.s, 30);
        if (decoded.length >= 1 || type == 23) {
            return decoded;
        }
        throw new TlsFatalAlert(47);
    }

    /* access modifiers changed from: package-private */
    public void w(short type, byte[] plaintext, int plaintextOffset, int plaintextLength) {
        byte[] ciphertext;
        short s2 = type;
        int i2 = plaintextLength;
        if (this.q != null) {
            c(s2, 80);
            b(i2, this.s, 80);
            if (i2 >= 1 || s2 == 23) {
                OutputStream cOut = this.g.b(this.m);
                long seqNo = this.l.a(80);
                if (cOut == this.m) {
                    ciphertext = this.j.b(seqNo, type, plaintext, plaintextOffset, plaintextLength);
                    byte[] bArr = plaintext;
                } else {
                    cOut.write(plaintext, plaintextOffset, i2);
                    cOut.flush();
                    byte[] compressed = g();
                    b(compressed.length, i2 + 1024, 80);
                    byte[] bArr2 = compressed;
                    ciphertext = this.j.b(seqNo, type, compressed, 0, compressed.length);
                }
                b(ciphertext.length, this.u, 80);
                byte[] record = new byte[(ciphertext.length + 5)];
                TlsUtils.M0(s2, record, 0);
                TlsUtils.S0(this.q, record, 1);
                TlsUtils.E0(ciphertext.length, record, 3);
                System.arraycopy(ciphertext, 0, record, 5, ciphertext.length);
                this.d.write(record);
                this.d.flush();
                return;
            }
            throw new TlsFatalAlert(80);
        }
    }

    /* access modifiers changed from: package-private */
    public void l() {
        this.n = this.n.g();
    }

    /* access modifiers changed from: package-private */
    public TlsHandshakeHash h() {
        return this.n;
    }

    /* access modifiers changed from: package-private */
    public OutputStream i() {
        return this.o;
    }

    /* access modifiers changed from: package-private */
    public TlsHandshakeHash m() {
        TlsHandshakeHash result = this.n;
        this.n = this.n.a();
        return result;
    }

    /* access modifiers changed from: package-private */
    public void p() {
        try {
            this.c.close();
        } catch (IOException e2) {
        }
        try {
            this.d.close();
        } catch (IOException e3) {
        }
    }

    /* access modifiers changed from: package-private */
    public void f() {
        this.d.flush();
    }

    private byte[] g() {
        byte[] contents = this.m.toByteArray();
        this.m.reset();
        return contents;
    }

    private static void c(short type, short alertDescription) {
        switch (type) {
            case 20:
            case 21:
            case 22:
            case 23:
                return;
            default:
                throw new TlsFatalAlert(alertDescription);
        }
    }

    private static void b(int length, int limit, short alertDescription) {
        if (length > limit) {
            throw new TlsFatalAlert(alertDescription);
        }
    }

    public static class SequenceNumber {
        private long a;
        private boolean b;

        private SequenceNumber() {
            this.a = 0;
            this.b = false;
        }

        /* synthetic */ SequenceNumber(AnonymousClass1 x0) {
            this();
        }

        /* access modifiers changed from: package-private */
        public synchronized long a(short alertDescription) {
            long result;
            if (!this.b) {
                long j = this.a;
                result = j;
                long j2 = j + 1;
                this.a = j2;
                if (j2 == 0) {
                    this.b = true;
                }
            } else {
                throw new TlsFatalAlert(alertDescription);
            }
            return result;
        }
    }
}
