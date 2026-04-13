package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.tls.TlsProtocol;
import org.spongycastle.crypto.util.PublicKeyFactory;
import org.spongycastle.util.Arrays;

public class TlsServerProtocol extends TlsProtocol {
    protected TlsServer D;
    TlsServerContextImpl E;
    protected TlsKeyExchange F;
    protected TlsCredentials G;
    protected CertificateRequest H;
    protected short I;
    protected TlsHandshakeHash J;

    /* access modifiers changed from: protected */
    public void e() {
        super.e();
        this.F = null;
        this.G = null;
        this.H = null;
        this.J = null;
    }

    /* access modifiers changed from: protected */
    public TlsContext l() {
        return this.E;
    }

    /* access modifiers changed from: package-private */
    public AbstractTlsContext m() {
        return this.E;
    }

    /* access modifiers changed from: protected */
    public TlsPeer p() {
        return this.D;
    }

    /* access modifiers changed from: protected */
    public void w(short type, ByteArrayInputStream buf) {
        CertificateStatus certificateStatus;
        switch (type) {
            case 1:
                switch (this.w) {
                    case 0:
                        a0(buf);
                        boolean z = true;
                        this.w = 1;
                        g0();
                        this.w = 2;
                        this.f.l();
                        Vector serverSupplementalData = this.D.k();
                        if (serverSupplementalData != null) {
                            Q(serverSupplementalData);
                        }
                        this.w = 3;
                        TlsKeyExchange a = this.D.a();
                        this.F = a;
                        a.a(l());
                        TlsCredentials credentials = this.D.getCredentials();
                        this.G = credentials;
                        Certificate serverCertificate = null;
                        if (credentials == null) {
                            this.F.n();
                        } else {
                            this.F.k(credentials);
                            serverCertificate = this.G.e();
                            N(serverCertificate);
                        }
                        this.w = 4;
                        if (serverCertificate == null || serverCertificate.c()) {
                            this.A = false;
                        }
                        if (this.A && (certificateStatus = this.D.y()) != null) {
                            d0(certificateStatus);
                        }
                        this.w = 5;
                        byte[] serverKeyExchange = this.F.b();
                        if (serverKeyExchange != null) {
                            h0(serverKeyExchange);
                        }
                        this.w = 6;
                        if (this.G != null) {
                            CertificateRequest x = this.D.x();
                            this.H = x;
                            if (x != null) {
                                boolean U = TlsUtils.U(l());
                                if (this.H.c() == null) {
                                    z = false;
                                }
                                if (U == z) {
                                    this.F.i(this.H);
                                    c0(this.H);
                                    TlsUtils.w0(this.f.h(), this.H.c());
                                } else {
                                    throw new TlsFatalAlert(80);
                                }
                            }
                        }
                        this.w = 7;
                        f0();
                        this.w = 8;
                        this.f.h().l();
                        return;
                    case 16:
                        K();
                        return;
                    default:
                        throw new TlsFatalAlert(10);
                }
            case 11:
                switch (this.w) {
                    case 8:
                        this.D.q((Vector) null);
                        break;
                    case 9:
                        break;
                    default:
                        throw new TlsFatalAlert(10);
                }
                if (this.H != null) {
                    Y(buf);
                    this.w = 10;
                    return;
                }
                throw new TlsFatalAlert(10);
            case 15:
                switch (this.w) {
                    case 11:
                        if (W()) {
                            Z(buf);
                            this.w = 12;
                            return;
                        }
                        throw new TlsFatalAlert(10);
                    default:
                        throw new TlsFatalAlert(10);
                }
            case 16:
                switch (this.w) {
                    case 8:
                        this.D.q((Vector) null);
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    default:
                        throw new TlsFatalAlert(10);
                }
                if (this.H == null) {
                    this.F.g();
                } else if (TlsUtils.U(l())) {
                    throw new TlsFatalAlert(10);
                } else if (!TlsUtils.P(l())) {
                    X(Certificate.a);
                } else if (this.r == null) {
                    throw new TlsFatalAlert(10);
                }
                b0(buf);
                this.w = 11;
                return;
            case 20:
                switch (this.w) {
                    case 11:
                        if (W()) {
                            throw new TlsFatalAlert(10);
                        }
                        break;
                    case 12:
                        break;
                    default:
                        throw new TlsFatalAlert(10);
                }
                B(buf);
                this.w = 13;
                if (this.B) {
                    e0(this.D.j());
                    O();
                }
                this.w = 14;
                P();
                this.w = 15;
                g();
                return;
            case 23:
                switch (this.w) {
                    case 8:
                        this.D.q(TlsProtocol.J(buf));
                        this.w = 9;
                        return;
                    default:
                        throw new TlsFatalAlert(10);
                }
            default:
                throw new TlsFatalAlert(10);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0023, code lost:
        X(org.spongycastle.crypto.tls.Certificate.a);
        r3.w = 10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void r(short r4) {
        /*
            r3 = this;
            super.r(r4)
            switch(r4) {
                case 41: goto L_0x0007;
                default: goto L_0x0006;
            }
        L_0x0006:
            return
        L_0x0007:
            org.spongycastle.crypto.tls.TlsContext r0 = r3.l()
            boolean r0 = org.spongycastle.crypto.tls.TlsUtils.P(r0)
            r1 = 10
            if (r0 == 0) goto L_0x002b
            org.spongycastle.crypto.tls.CertificateRequest r0 = r3.H
            if (r0 == 0) goto L_0x002b
            short r0 = r3.w
            switch(r0) {
                case 8: goto L_0x001d;
                case 9: goto L_0x0023;
                default: goto L_0x001c;
            }
        L_0x001c:
            goto L_0x002b
        L_0x001d:
            org.spongycastle.crypto.tls.TlsServer r0 = r3.D
            r2 = 0
            r0.q(r2)
        L_0x0023:
            org.spongycastle.crypto.tls.Certificate r0 = org.spongycastle.crypto.tls.Certificate.a
            r3.X(r0)
            r3.w = r1
            return
        L_0x002b:
            org.spongycastle.crypto.tls.TlsFatalAlert r0 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.crypto.tls.TlsServerProtocol.r(short):void");
    }

    /* access modifiers changed from: protected */
    public void X(Certificate clientCertificate) {
        if (this.H == null) {
            throw new IllegalStateException();
        } else if (this.r == null) {
            this.r = clientCertificate;
            if (clientCertificate.c()) {
                this.F.g();
            } else {
                this.I = TlsUtils.x(clientCertificate, this.G.e());
                this.F.d(clientCertificate);
            }
            this.D.E(clientCertificate);
        } else {
            throw new TlsFatalAlert(10);
        }
    }

    /* access modifiers changed from: protected */
    public void Y(ByteArrayInputStream buf) {
        Certificate clientCertificate = Certificate.d(buf);
        TlsProtocol.c(buf);
        X(clientCertificate);
    }

    /* access modifiers changed from: protected */
    public void Z(ByteArrayInputStream buf) {
        byte[] hash;
        if (this.H != null) {
            DigitallySigned clientCertificateVerify = DigitallySigned.d(l(), buf);
            TlsProtocol.c(buf);
            try {
                SignatureAndHashAlgorithm signatureAlgorithm = clientCertificateVerify.b();
                if (TlsUtils.U(l())) {
                    TlsUtils.z0(this.H.c(), signatureAlgorithm);
                    hash = this.J.i(signatureAlgorithm.b());
                } else {
                    hash = this.q.j();
                }
                AsymmetricKeyParameter publicKey = PublicKeyFactory.a(this.r.b(0).q());
                TlsSigner tlsSigner = TlsUtils.q(this.I);
                tlsSigner.a(l());
                if (!tlsSigner.b(signatureAlgorithm, clientCertificateVerify.c(), publicKey, hash)) {
                    throw new TlsFatalAlert(51);
                }
            } catch (TlsFatalAlert e) {
                throw e;
            } catch (Exception e2) {
                throw new TlsFatalAlert(51, e2);
            }
        } else {
            throw new IllegalStateException();
        }
    }

    /* access modifiers changed from: protected */
    public void a0(ByteArrayInputStream buf) {
        ProtocolVersion client_version = TlsUtils.t0(buf);
        this.f.v(client_version);
        if (!client_version.g()) {
            byte[] client_random = TlsUtils.f0(32, buf);
            if (TlsUtils.i0(buf).length <= 32) {
                int cipher_suites_length = TlsUtils.k0(buf);
                if (cipher_suites_length < 2 || (cipher_suites_length & 1) != 0) {
                    throw new TlsFatalAlert(50);
                }
                this.s = TlsUtils.m0(cipher_suites_length / 2, buf);
                int compression_methods_length = TlsUtils.q0(buf);
                if (compression_methods_length >= 1) {
                    this.t = TlsUtils.s0(compression_methods_length, buf);
                    Hashtable I2 = TlsProtocol.I(buf);
                    this.u = I2;
                    this.q.o = TlsExtensionsUtils.n(I2);
                    m().a(client_version);
                    this.D.A(client_version);
                    this.D.c(Arrays.v(this.s, 22016));
                    this.q.g = client_random;
                    this.D.o(this.s);
                    this.D.C(this.t);
                    if (Arrays.v(this.s, 255)) {
                        this.z = true;
                    }
                    byte[] renegExtData = TlsUtils.C(this.u, TlsProtocol.a);
                    if (renegExtData != null) {
                        this.z = true;
                        if (!Arrays.u(renegExtData, TlsProtocol.h(TlsUtils.a))) {
                            throw new TlsFatalAlert(40);
                        }
                    }
                    this.D.m(this.z);
                    Hashtable hashtable = this.u;
                    if (hashtable != null) {
                        TlsExtensionsUtils.l(hashtable);
                        this.D.l(this.u);
                        return;
                    }
                    return;
                }
                throw new TlsFatalAlert(47);
            }
            throw new TlsFatalAlert(47);
        }
        throw new TlsFatalAlert(47);
    }

    /* access modifiers changed from: protected */
    public void b0(ByteArrayInputStream buf) {
        this.F.e(buf);
        TlsProtocol.c(buf);
        if (TlsUtils.P(l())) {
            TlsProtocol.j(l(), this.F);
        }
        this.J = this.f.m();
        this.q.i = TlsProtocol.n(l(), this.J, (byte[]) null);
        if (!TlsUtils.P(l())) {
            TlsProtocol.j(l(), this.F);
        }
        this.f.r(p().h(), p().n());
        if (!this.B) {
            O();
        }
    }

    /* access modifiers changed from: protected */
    public void c0(CertificateRequest certificateRequest) {
        TlsProtocol.HandshakeMessage message = new TlsProtocol.HandshakeMessage(this, 13);
        certificateRequest.a(message);
        message.a();
    }

    /* access modifiers changed from: protected */
    public void d0(CertificateStatus certificateStatus) {
        TlsProtocol.HandshakeMessage message = new TlsProtocol.HandshakeMessage(this, 22);
        certificateStatus.a(message);
        message.a();
    }

    /* access modifiers changed from: protected */
    public void e0(NewSessionTicket newSessionTicket) {
        if (newSessionTicket != null) {
            TlsProtocol.HandshakeMessage message = new TlsProtocol.HandshakeMessage(this, 4);
            newSessionTicket.a(message);
            message.a();
            return;
        }
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    public void g0() {
        TlsProtocol.HandshakeMessage message = new TlsProtocol.HandshakeMessage(this, 2);
        ProtocolVersion server_version = this.D.b();
        if (server_version.h(l().c())) {
            this.f.t(server_version);
            this.f.v(server_version);
            boolean z = true;
            this.f.u(true);
            m().i(server_version);
            TlsUtils.R0(server_version, message);
            message.write(this.q.h);
            byte[] bArr = TlsUtils.a;
            TlsUtils.C0(bArr, message);
            int selectedCipherSuite = this.D.B();
            if (!Arrays.v(this.s, selectedCipherSuite) || selectedCipherSuite == 0 || CipherSuite.a(selectedCipherSuite) || !TlsUtils.W(selectedCipherSuite, l().b())) {
                throw new TlsFatalAlert(80);
            }
            this.q.b = selectedCipherSuite;
            short selectedCompressionMethod = this.D.g();
            if (Arrays.w(this.t, selectedCompressionMethod)) {
                this.q.c = selectedCompressionMethod;
                TlsUtils.D0(selectedCipherSuite, message);
                TlsUtils.L0(selectedCompressionMethod, message);
                Hashtable e = this.D.e();
                this.v = e;
                if (this.z) {
                    Integer num = TlsProtocol.a;
                    if (TlsUtils.C(e, num) == null) {
                        Hashtable j = TlsExtensionsUtils.j(this.v);
                        this.v = j;
                        j.put(num, TlsProtocol.h(bArr));
                    }
                }
                if (this.q.o) {
                    Hashtable j2 = TlsExtensionsUtils.j(this.v);
                    this.v = j2;
                    TlsExtensionsUtils.b(j2);
                }
                Hashtable hashtable = this.v;
                if (hashtable != null) {
                    this.q.n = TlsExtensionsUtils.m(hashtable);
                    this.q.l = D(this.u, this.v, 80);
                    this.q.m = TlsExtensionsUtils.o(this.v);
                    this.A = !this.x && TlsUtils.L(this.v, TlsExtensionsUtils.g, 80);
                    if (this.x || !TlsUtils.L(this.v, TlsProtocol.b, 80)) {
                        z = false;
                    }
                    this.B = z;
                    TlsProtocol.S(message, this.v);
                }
                this.q.d = TlsProtocol.o(l(), this.q.b());
                this.q.e = 12;
                b();
                message.a();
                return;
            }
            throw new TlsFatalAlert(80);
        }
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    public void f0() {
        byte[] message = new byte[4];
        TlsUtils.M0(14, message, 0);
        TlsUtils.G0(0, message, 1);
        T(message, 0, message.length);
    }

    /* access modifiers changed from: protected */
    public void h0(byte[] serverKeyExchange) {
        TlsProtocol.HandshakeMessage message = new TlsProtocol.HandshakeMessage(12, serverKeyExchange.length);
        message.write(serverKeyExchange);
        message.a();
    }

    /* access modifiers changed from: protected */
    public boolean W() {
        short s = this.I;
        return s >= 0 && TlsUtils.M(s);
    }
}
