package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.util.Vector;
import org.spongycastle.crypto.tls.TlsProtocol;

public class TlsClientProtocol extends TlsProtocol {
    protected TlsClient D;
    TlsClientContextImpl E;
    protected byte[] F;
    protected TlsKeyExchange G;
    protected TlsAuthentication H;
    protected CertificateStatus I;
    protected CertificateRequest J;

    /* access modifiers changed from: protected */
    public void e() {
        super.e();
        this.F = null;
        this.G = null;
        this.H = null;
        this.I = null;
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
    /* JADX WARNING: Code restructure failed: missing block: B:124:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0095, code lost:
        r10.G.n();
        r10.H = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x009c, code lost:
        r10.G.j();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00a1, code lost:
        org.spongycastle.crypto.tls.TlsProtocol.c(r12);
        r10.w = 8;
        r10.f.h().l();
        r2 = r10.D.f();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00b7, code lost:
        if (r2 == null) goto L_0x00bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00b9, code lost:
        Q(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00bc, code lost:
        r10.w = 9;
        r3 = null;
        r5 = r10.J;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00c3, code lost:
        if (r5 != null) goto L_0x00cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00c5, code lost:
        r10.G.g();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00cb, code lost:
        r3 = r10.H.a(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00d1, code lost:
        if (r3 != null) goto L_0x00de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00d3, code lost:
        r10.G.g();
        N(org.spongycastle.crypto.tls.Certificate.a);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00de, code lost:
        r10.G.f(r3);
        N(r3.e());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00ea, code lost:
        r10.w = 10;
        a0();
        r10.w = 11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00fb, code lost:
        if (org.spongycastle.crypto.tls.TlsUtils.P(l()) == false) goto L_0x0106;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00fd, code lost:
        org.spongycastle.crypto.tls.TlsProtocol.j(l(), r10.G);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0106, code lost:
        r4 = r10.f.m();
        r10.q.i = org.spongycastle.crypto.tls.TlsProtocol.n(l(), r4, (byte[]) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0120, code lost:
        if (org.spongycastle.crypto.tls.TlsUtils.P(l()) != false) goto L_0x012b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0122, code lost:
        org.spongycastle.crypto.tls.TlsProtocol.j(l(), r10.G);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x012b, code lost:
        r10.f.r(p().h(), p().n());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0140, code lost:
        if (r3 == null) goto L_0x0173;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0144, code lost:
        if ((r3 instanceof org.spongycastle.crypto.tls.TlsSignerCredentials) == false) goto L_0x0173;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0146, code lost:
        r0 = (org.spongycastle.crypto.tls.TlsSignerCredentials) r3;
        r5 = org.spongycastle.crypto.tls.TlsUtils.J(l(), r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0152, code lost:
        if (r5 != null) goto L_0x015b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0154, code lost:
        r6 = r10.q.j();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x015b, code lost:
        r6 = r4.i(r5.b());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0163, code lost:
        Z(new org.spongycastle.crypto.tls.DigitallySigned(r5, r0.d(r6)));
        r10.w = 12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0173, code lost:
        O();
        P();
        r10.w = 13;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void w(short r11, java.io.ByteArrayInputStream r12) {
        /*
            r10 = this;
            boolean r0 = r10.x
            r1 = 13
            r2 = 15
            r3 = 2
            r4 = 10
            if (r0 == 0) goto L_0x0027
            r0 = 20
            if (r11 != r0) goto L_0x0021
            short r0 = r10.w
            if (r0 != r3) goto L_0x0021
            r10.B(r12)
            r10.w = r2
            r10.P()
            r10.w = r1
            r10.g()
            return
        L_0x0021:
            org.spongycastle.crypto.tls.TlsFatalAlert r0 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r0.<init>(r4)
            throw r0
        L_0x0027:
            r0 = 0
            switch(r11) {
                case 0: goto L_0x0298;
                case 2: goto L_0x0245;
                case 4: goto L_0x0224;
                case 11: goto L_0x01e6;
                case 12: goto L_0x01c3;
                case 13: goto L_0x017e;
                case 14: goto L_0x0087;
                case 20: goto L_0x0068;
                case 22: goto L_0x0045;
                case 23: goto L_0x0031;
                default: goto L_0x002b;
            }
        L_0x002b:
            org.spongycastle.crypto.tls.TlsFatalAlert r0 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r0.<init>(r4)
            throw r0
        L_0x0031:
            short r0 = r10.w
            switch(r0) {
                case 2: goto L_0x003c;
                default: goto L_0x0036;
            }
        L_0x0036:
            org.spongycastle.crypto.tls.TlsFatalAlert r0 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r0.<init>(r4)
            throw r0
        L_0x003c:
            java.util.Vector r0 = org.spongycastle.crypto.tls.TlsProtocol.J(r12)
            r10.W(r0)
            goto L_0x02a4
        L_0x0045:
            short r0 = r10.w
            switch(r0) {
                case 4: goto L_0x0050;
                default: goto L_0x004a;
            }
        L_0x004a:
            org.spongycastle.crypto.tls.TlsFatalAlert r0 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r0.<init>(r4)
            throw r0
        L_0x0050:
            boolean r0 = r10.A
            if (r0 == 0) goto L_0x0062
            org.spongycastle.crypto.tls.CertificateStatus r0 = org.spongycastle.crypto.tls.CertificateStatus.c(r12)
            r10.I = r0
            org.spongycastle.crypto.tls.TlsProtocol.c(r12)
            r0 = 5
            r10.w = r0
            goto L_0x02a4
        L_0x0062:
            org.spongycastle.crypto.tls.TlsFatalAlert r0 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r0.<init>(r4)
            throw r0
        L_0x0068:
            short r0 = r10.w
            switch(r0) {
                case 13: goto L_0x0073;
                case 14: goto L_0x0077;
                default: goto L_0x006d;
            }
        L_0x006d:
            org.spongycastle.crypto.tls.TlsFatalAlert r0 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r0.<init>(r4)
            throw r0
        L_0x0073:
            boolean r0 = r10.B
            if (r0 != 0) goto L_0x0081
        L_0x0077:
            r10.B(r12)
            r10.w = r2
            r10.g()
            goto L_0x02a4
        L_0x0081:
            org.spongycastle.crypto.tls.TlsFatalAlert r0 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r0.<init>(r4)
            throw r0
        L_0x0087:
            short r2 = r10.w
            switch(r2) {
                case 2: goto L_0x0092;
                case 3: goto L_0x0095;
                case 4: goto L_0x009c;
                case 5: goto L_0x009c;
                case 6: goto L_0x00a1;
                case 7: goto L_0x00a1;
                default: goto L_0x008c;
            }
        L_0x008c:
            org.spongycastle.crypto.tls.TlsFatalAlert r0 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r0.<init>(r4)
            throw r0
        L_0x0092:
            r10.W(r0)
        L_0x0095:
            org.spongycastle.crypto.tls.TlsKeyExchange r2 = r10.G
            r2.n()
            r10.H = r0
        L_0x009c:
            org.spongycastle.crypto.tls.TlsKeyExchange r2 = r10.G
            r2.j()
        L_0x00a1:
            org.spongycastle.crypto.tls.TlsProtocol.c(r12)
            r2 = 8
            r10.w = r2
            org.spongycastle.crypto.tls.RecordStream r2 = r10.f
            org.spongycastle.crypto.tls.TlsHandshakeHash r2 = r2.h()
            r2.l()
            org.spongycastle.crypto.tls.TlsClient r2 = r10.D
            java.util.Vector r2 = r2.f()
            if (r2 == 0) goto L_0x00bc
            r10.Q(r2)
        L_0x00bc:
            r3 = 9
            r10.w = r3
            r3 = 0
            org.spongycastle.crypto.tls.CertificateRequest r5 = r10.J
            if (r5 != 0) goto L_0x00cb
            org.spongycastle.crypto.tls.TlsKeyExchange r5 = r10.G
            r5.g()
            goto L_0x00ea
        L_0x00cb:
            org.spongycastle.crypto.tls.TlsAuthentication r6 = r10.H
            org.spongycastle.crypto.tls.TlsCredentials r3 = r6.a(r5)
            if (r3 != 0) goto L_0x00de
            org.spongycastle.crypto.tls.TlsKeyExchange r5 = r10.G
            r5.g()
            org.spongycastle.crypto.tls.Certificate r5 = org.spongycastle.crypto.tls.Certificate.a
            r10.N(r5)
            goto L_0x00ea
        L_0x00de:
            org.spongycastle.crypto.tls.TlsKeyExchange r5 = r10.G
            r5.f(r3)
            org.spongycastle.crypto.tls.Certificate r5 = r3.e()
            r10.N(r5)
        L_0x00ea:
            r10.w = r4
            r10.a0()
            r4 = 11
            r10.w = r4
            org.spongycastle.crypto.tls.TlsContext r4 = r10.l()
            boolean r4 = org.spongycastle.crypto.tls.TlsUtils.P(r4)
            if (r4 == 0) goto L_0x0106
            org.spongycastle.crypto.tls.TlsContext r4 = r10.l()
            org.spongycastle.crypto.tls.TlsKeyExchange r5 = r10.G
            org.spongycastle.crypto.tls.TlsProtocol.j(r4, r5)
        L_0x0106:
            org.spongycastle.crypto.tls.RecordStream r4 = r10.f
            org.spongycastle.crypto.tls.TlsHandshakeHash r4 = r4.m()
            org.spongycastle.crypto.tls.SecurityParameters r5 = r10.q
            org.spongycastle.crypto.tls.TlsContext r6 = r10.l()
            byte[] r0 = org.spongycastle.crypto.tls.TlsProtocol.n(r6, r4, r0)
            r5.i = r0
            org.spongycastle.crypto.tls.TlsContext r0 = r10.l()
            boolean r0 = org.spongycastle.crypto.tls.TlsUtils.P(r0)
            if (r0 != 0) goto L_0x012b
            org.spongycastle.crypto.tls.TlsContext r0 = r10.l()
            org.spongycastle.crypto.tls.TlsKeyExchange r5 = r10.G
            org.spongycastle.crypto.tls.TlsProtocol.j(r0, r5)
        L_0x012b:
            org.spongycastle.crypto.tls.RecordStream r0 = r10.f
            org.spongycastle.crypto.tls.TlsPeer r5 = r10.p()
            org.spongycastle.crypto.tls.TlsCompression r5 = r5.h()
            org.spongycastle.crypto.tls.TlsPeer r6 = r10.p()
            org.spongycastle.crypto.tls.TlsCipher r6 = r6.n()
            r0.r(r5, r6)
            if (r3 == 0) goto L_0x0173
            boolean r0 = r3 instanceof org.spongycastle.crypto.tls.TlsSignerCredentials
            if (r0 == 0) goto L_0x0173
            r0 = r3
            org.spongycastle.crypto.tls.TlsSignerCredentials r0 = (org.spongycastle.crypto.tls.TlsSignerCredentials) r0
            org.spongycastle.crypto.tls.TlsContext r5 = r10.l()
            org.spongycastle.crypto.tls.SignatureAndHashAlgorithm r5 = org.spongycastle.crypto.tls.TlsUtils.J(r5, r0)
            if (r5 != 0) goto L_0x015b
            org.spongycastle.crypto.tls.SecurityParameters r6 = r10.q
            byte[] r6 = r6.j()
            goto L_0x0163
        L_0x015b:
            short r6 = r5.b()
            byte[] r6 = r4.i(r6)
        L_0x0163:
            byte[] r7 = r0.d(r6)
            org.spongycastle.crypto.tls.DigitallySigned r8 = new org.spongycastle.crypto.tls.DigitallySigned
            r8.<init>(r5, r7)
            r10.Z(r8)
            r9 = 12
            r10.w = r9
        L_0x0173:
            r10.O()
            r10.P()
            r10.w = r1
            goto L_0x02a4
        L_0x017e:
            short r0 = r10.w
            switch(r0) {
                case 4: goto L_0x0189;
                case 5: goto L_0x0189;
                case 6: goto L_0x018e;
                default: goto L_0x0183;
            }
        L_0x0183:
            org.spongycastle.crypto.tls.TlsFatalAlert r0 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r0.<init>(r4)
            throw r0
        L_0x0189:
            org.spongycastle.crypto.tls.TlsKeyExchange r0 = r10.G
            r0.j()
        L_0x018e:
            org.spongycastle.crypto.tls.TlsAuthentication r0 = r10.H
            if (r0 == 0) goto L_0x01bb
            org.spongycastle.crypto.tls.TlsContext r0 = r10.l()
            org.spongycastle.crypto.tls.CertificateRequest r0 = org.spongycastle.crypto.tls.CertificateRequest.d(r0, r12)
            r10.J = r0
            org.spongycastle.crypto.tls.TlsProtocol.c(r12)
            org.spongycastle.crypto.tls.TlsKeyExchange r0 = r10.G
            org.spongycastle.crypto.tls.CertificateRequest r1 = r10.J
            r0.i(r1)
            org.spongycastle.crypto.tls.RecordStream r0 = r10.f
            org.spongycastle.crypto.tls.TlsHandshakeHash r0 = r0.h()
            org.spongycastle.crypto.tls.CertificateRequest r1 = r10.J
            java.util.Vector r1 = r1.c()
            org.spongycastle.crypto.tls.TlsUtils.w0(r0, r1)
            r0 = 7
            r10.w = r0
            goto L_0x02a4
        L_0x01bb:
            org.spongycastle.crypto.tls.TlsFatalAlert r0 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r1 = 40
            r0.<init>(r1)
            throw r0
        L_0x01c3:
            short r1 = r10.w
            switch(r1) {
                case 2: goto L_0x01ce;
                case 3: goto L_0x01d1;
                case 4: goto L_0x01d8;
                case 5: goto L_0x01d8;
                default: goto L_0x01c8;
            }
        L_0x01c8:
            org.spongycastle.crypto.tls.TlsFatalAlert r0 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r0.<init>(r4)
            throw r0
        L_0x01ce:
            r10.W(r0)
        L_0x01d1:
            org.spongycastle.crypto.tls.TlsKeyExchange r1 = r10.G
            r1.n()
            r10.H = r0
        L_0x01d8:
            org.spongycastle.crypto.tls.TlsKeyExchange r0 = r10.G
            r0.c(r12)
            org.spongycastle.crypto.tls.TlsProtocol.c(r12)
            r0 = 6
            r10.w = r0
            goto L_0x02a4
        L_0x01e6:
            short r1 = r10.w
            switch(r1) {
                case 2: goto L_0x01f1;
                case 3: goto L_0x01f4;
                default: goto L_0x01eb;
            }
        L_0x01eb:
            org.spongycastle.crypto.tls.TlsFatalAlert r0 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r0.<init>(r4)
            throw r0
        L_0x01f1:
            r10.W(r0)
        L_0x01f4:
            org.spongycastle.crypto.tls.Certificate r0 = org.spongycastle.crypto.tls.Certificate.d(r12)
            r10.r = r0
            org.spongycastle.crypto.tls.TlsProtocol.c(r12)
            org.spongycastle.crypto.tls.Certificate r0 = r10.r
            if (r0 == 0) goto L_0x0207
            boolean r0 = r0.c()
            if (r0 == 0) goto L_0x020a
        L_0x0207:
            r0 = 0
            r10.A = r0
        L_0x020a:
            org.spongycastle.crypto.tls.TlsKeyExchange r0 = r10.G
            org.spongycastle.crypto.tls.Certificate r1 = r10.r
            r0.m(r1)
            org.spongycastle.crypto.tls.TlsClient r0 = r10.D
            org.spongycastle.crypto.tls.TlsAuthentication r0 = r0.w()
            r10.H = r0
            org.spongycastle.crypto.tls.Certificate r1 = r10.r
            r0.b(r1)
            r0 = 4
            r10.w = r0
            goto L_0x02a4
        L_0x0224:
            short r0 = r10.w
            switch(r0) {
                case 13: goto L_0x022f;
                default: goto L_0x0229;
            }
        L_0x0229:
            org.spongycastle.crypto.tls.TlsFatalAlert r0 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r0.<init>(r4)
            throw r0
        L_0x022f:
            boolean r0 = r10.B
            if (r0 == 0) goto L_0x023f
            r10.x()
            r10.X(r12)
            r0 = 14
            r10.w = r0
            goto L_0x02a4
        L_0x023f:
            org.spongycastle.crypto.tls.TlsFatalAlert r0 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r0.<init>(r4)
            throw r0
        L_0x0245:
            short r1 = r10.w
            switch(r1) {
                case 1: goto L_0x0250;
                default: goto L_0x024a;
            }
        L_0x024a:
            org.spongycastle.crypto.tls.TlsFatalAlert r0 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r0.<init>(r4)
            throw r0
        L_0x0250:
            r10.Y(r12)
            r10.w = r3
            org.spongycastle.crypto.tls.RecordStream r1 = r10.f
            r1.l()
            r10.b()
            boolean r1 = r10.x
            if (r1 == 0) goto L_0x0288
            org.spongycastle.crypto.tls.SecurityParameters r0 = r10.q
            org.spongycastle.crypto.tls.SessionParameters r1 = r10.p
            byte[] r1 = r1.d()
            byte[] r1 = org.spongycastle.util.Arrays.h(r1)
            r0.f = r1
            org.spongycastle.crypto.tls.RecordStream r0 = r10.f
            org.spongycastle.crypto.tls.TlsPeer r1 = r10.p()
            org.spongycastle.crypto.tls.TlsCompression r1 = r1.h()
            org.spongycastle.crypto.tls.TlsPeer r2 = r10.p()
            org.spongycastle.crypto.tls.TlsCipher r2 = r2.n()
            r0.r(r1, r2)
            r10.O()
            goto L_0x02a4
        L_0x0288:
            r10.x()
            byte[] r1 = r10.F
            int r2 = r1.length
            if (r2 <= 0) goto L_0x02a4
            org.spongycastle.crypto.tls.TlsSessionImpl r2 = new org.spongycastle.crypto.tls.TlsSessionImpl
            r2.<init>(r1, r0)
            r10.o = r2
            goto L_0x02a4
        L_0x0298:
            org.spongycastle.crypto.tls.TlsProtocol.c(r12)
            short r0 = r10.w
            r1 = 16
            if (r0 != r1) goto L_0x02a4
            r10.K()
        L_0x02a4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.crypto.tls.TlsClientProtocol.w(short, java.io.ByteArrayInputStream):void");
    }

    /* access modifiers changed from: protected */
    public void W(Vector serverSupplementalData) {
        this.D.t(serverSupplementalData);
        this.w = 3;
        TlsKeyExchange a = this.D.a();
        this.G = a;
        a.a(l());
    }

    /* access modifiers changed from: protected */
    public void X(ByteArrayInputStream buf) {
        NewSessionTicket newSessionTicket = NewSessionTicket.b(buf);
        TlsProtocol.c(buf);
        this.D.D(newSessionTicket);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0056, code lost:
        r1 = r9.o;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void Y(java.io.ByteArrayInputStream r10) {
        /*
            r9 = this;
            org.spongycastle.crypto.tls.ProtocolVersion r0 = org.spongycastle.crypto.tls.TlsUtils.t0(r10)
            boolean r1 = r0.g()
            r2 = 47
            if (r1 != 0) goto L_0x01b7
            org.spongycastle.crypto.tls.RecordStream r1 = r9.f
            org.spongycastle.crypto.tls.ProtocolVersion r1 = r1.k()
            boolean r1 = r0.a(r1)
            if (r1 == 0) goto L_0x01b1
            org.spongycastle.crypto.tls.TlsContext r1 = r9.l()
            org.spongycastle.crypto.tls.ProtocolVersion r1 = r1.c()
            boolean r3 = r0.h(r1)
            if (r3 == 0) goto L_0x01ab
            org.spongycastle.crypto.tls.RecordStream r3 = r9.f
            r3.v(r0)
            org.spongycastle.crypto.tls.AbstractTlsContext r3 = r9.m()
            r3.i(r0)
            org.spongycastle.crypto.tls.TlsClient r3 = r9.D
            r3.r(r0)
            org.spongycastle.crypto.tls.SecurityParameters r0 = r9.q
            r1 = 32
            byte[] r3 = org.spongycastle.crypto.tls.TlsUtils.f0(r1, r10)
            r0.h = r3
            byte[] r0 = org.spongycastle.crypto.tls.TlsUtils.i0(r10)
            r9.F = r0
            int r3 = r0.length
            if (r3 > r1) goto L_0x01a5
            org.spongycastle.crypto.tls.TlsClient r1 = r9.D
            r1.u(r0)
            byte[] r0 = r9.F
            int r1 = r0.length
            r3 = 0
            r4 = 1
            if (r1 <= 0) goto L_0x0066
            org.spongycastle.crypto.tls.TlsSession r1 = r9.o
            if (r1 == 0) goto L_0x0066
            byte[] r1 = r1.a()
            boolean r0 = org.spongycastle.util.Arrays.b(r0, r1)
            if (r0 == 0) goto L_0x0066
            r0 = r4
            goto L_0x0067
        L_0x0066:
            r0 = r3
        L_0x0067:
            r9.x = r0
            int r0 = org.spongycastle.crypto.tls.TlsUtils.k0(r10)
            int[] r1 = r9.s
            boolean r1 = org.spongycastle.util.Arrays.v(r1, r0)
            if (r1 == 0) goto L_0x019f
            if (r0 == 0) goto L_0x019f
            boolean r1 = org.spongycastle.crypto.tls.CipherSuite.a(r0)
            if (r1 != 0) goto L_0x019f
            org.spongycastle.crypto.tls.TlsContext r1 = r9.l()
            org.spongycastle.crypto.tls.ProtocolVersion r1 = r1.b()
            boolean r1 = org.spongycastle.crypto.tls.TlsUtils.W(r0, r1)
            if (r1 == 0) goto L_0x019f
            org.spongycastle.crypto.tls.TlsClient r1 = r9.D
            r1.z(r0)
            short r1 = org.spongycastle.crypto.tls.TlsUtils.q0(r10)
            short[] r5 = r9.t
            boolean r5 = org.spongycastle.util.Arrays.w(r5, r1)
            if (r5 == 0) goto L_0x0199
            org.spongycastle.crypto.tls.TlsClient r5 = r9.D
            r5.d(r1)
            java.util.Hashtable r5 = org.spongycastle.crypto.tls.TlsProtocol.I(r10)
            r9.v = r5
            if (r5 == 0) goto L_0x00d4
            java.util.Enumeration r5 = r5.keys()
        L_0x00ad:
            boolean r6 = r5.hasMoreElements()
            if (r6 == 0) goto L_0x00d4
            java.lang.Object r6 = r5.nextElement()
            java.lang.Integer r6 = (java.lang.Integer) r6
            java.lang.Integer r7 = org.spongycastle.crypto.tls.TlsProtocol.a
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L_0x00c2
            goto L_0x00ad
        L_0x00c2:
            java.util.Hashtable r7 = r9.u
            byte[] r7 = org.spongycastle.crypto.tls.TlsUtils.C(r7, r6)
            if (r7 == 0) goto L_0x00cc
            goto L_0x00ad
        L_0x00cc:
            org.spongycastle.crypto.tls.TlsFatalAlert r2 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r3 = 110(0x6e, float:1.54E-43)
            r2.<init>(r3)
            throw r2
        L_0x00d4:
            java.util.Hashtable r5 = r9.v
            java.lang.Integer r6 = org.spongycastle.crypto.tls.TlsProtocol.a
            byte[] r5 = org.spongycastle.crypto.tls.TlsUtils.C(r5, r6)
            if (r5 == 0) goto L_0x00f5
            r9.z = r4
            byte[] r6 = org.spongycastle.crypto.tls.TlsUtils.a
            byte[] r6 = org.spongycastle.crypto.tls.TlsProtocol.h(r6)
            boolean r6 = org.spongycastle.util.Arrays.u(r5, r6)
            if (r6 == 0) goto L_0x00ed
            goto L_0x00f5
        L_0x00ed:
            org.spongycastle.crypto.tls.TlsFatalAlert r2 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r3 = 40
            r2.<init>(r3)
            throw r2
        L_0x00f5:
            org.spongycastle.crypto.tls.TlsClient r5 = r9.D
            boolean r6 = r9.z
            r5.m(r6)
            java.util.Hashtable r5 = r9.u
            java.util.Hashtable r6 = r9.v
            boolean r7 = r9.x
            if (r7 == 0) goto L_0x0122
            org.spongycastle.crypto.tls.SessionParameters r7 = r9.p
            int r7 = r7.b()
            if (r0 != r7) goto L_0x011c
            org.spongycastle.crypto.tls.SessionParameters r7 = r9.p
            short r7 = r7.c()
            if (r1 != r7) goto L_0x011c
            r5 = 0
            org.spongycastle.crypto.tls.SessionParameters r7 = r9.p
            java.util.Hashtable r6 = r7.e()
            goto L_0x0122
        L_0x011c:
            org.spongycastle.crypto.tls.TlsFatalAlert r3 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r3.<init>(r2)
            throw r3
        L_0x0122:
            org.spongycastle.crypto.tls.SecurityParameters r7 = r9.q
            r7.b = r0
            r7.c = r1
            if (r6 == 0) goto L_0x0179
            boolean r7 = org.spongycastle.crypto.tls.TlsExtensionsUtils.m(r6)
            if (r7 == 0) goto L_0x013d
            boolean r8 = org.spongycastle.crypto.tls.TlsUtils.O(r0)
            if (r8 == 0) goto L_0x0137
            goto L_0x013d
        L_0x0137:
            org.spongycastle.crypto.tls.TlsFatalAlert r3 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r3.<init>(r2)
            throw r3
        L_0x013d:
            org.spongycastle.crypto.tls.SecurityParameters r8 = r9.q
            r8.n = r7
            boolean r7 = org.spongycastle.crypto.tls.TlsExtensionsUtils.n(r6)
            r8.o = r7
            org.spongycastle.crypto.tls.SecurityParameters r7 = r9.q
            short r8 = r9.D(r5, r6, r2)
            r7.l = r8
            org.spongycastle.crypto.tls.SecurityParameters r7 = r9.q
            boolean r8 = org.spongycastle.crypto.tls.TlsExtensionsUtils.o(r6)
            r7.m = r8
            boolean r7 = r9.x
            if (r7 != 0) goto L_0x0165
            java.lang.Integer r7 = org.spongycastle.crypto.tls.TlsExtensionsUtils.g
            boolean r7 = org.spongycastle.crypto.tls.TlsUtils.L(r6, r7, r2)
            if (r7 == 0) goto L_0x0165
            r7 = r4
            goto L_0x0166
        L_0x0165:
            r7 = r3
        L_0x0166:
            r9.A = r7
            boolean r7 = r9.x
            if (r7 != 0) goto L_0x0176
            java.lang.Integer r7 = org.spongycastle.crypto.tls.TlsProtocol.b
            boolean r2 = org.spongycastle.crypto.tls.TlsUtils.L(r6, r7, r2)
            if (r2 == 0) goto L_0x0176
            r3 = r4
            goto L_0x0177
        L_0x0176:
        L_0x0177:
            r9.B = r3
        L_0x0179:
            if (r5 == 0) goto L_0x0180
            org.spongycastle.crypto.tls.TlsClient r2 = r9.D
            r2.i(r6)
        L_0x0180:
            org.spongycastle.crypto.tls.SecurityParameters r2 = r9.q
            org.spongycastle.crypto.tls.TlsContext r3 = r9.l()
            org.spongycastle.crypto.tls.SecurityParameters r4 = r9.q
            int r4 = r4.b()
            int r3 = org.spongycastle.crypto.tls.TlsProtocol.o(r3, r4)
            r2.d = r3
            org.spongycastle.crypto.tls.SecurityParameters r2 = r9.q
            r3 = 12
            r2.e = r3
            return
        L_0x0199:
            org.spongycastle.crypto.tls.TlsFatalAlert r3 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r3.<init>(r2)
            throw r3
        L_0x019f:
            org.spongycastle.crypto.tls.TlsFatalAlert r1 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r1.<init>(r2)
            throw r1
        L_0x01a5:
            org.spongycastle.crypto.tls.TlsFatalAlert r0 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r0.<init>(r2)
            throw r0
        L_0x01ab:
            org.spongycastle.crypto.tls.TlsFatalAlert r3 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r3.<init>(r2)
            throw r3
        L_0x01b1:
            org.spongycastle.crypto.tls.TlsFatalAlert r1 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r1.<init>(r2)
            throw r1
        L_0x01b7:
            org.spongycastle.crypto.tls.TlsFatalAlert r1 = new org.spongycastle.crypto.tls.TlsFatalAlert
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.crypto.tls.TlsClientProtocol.Y(java.io.ByteArrayInputStream):void");
    }

    /* access modifiers changed from: protected */
    public void Z(DigitallySigned certificateVerify) {
        TlsProtocol.HandshakeMessage message = new TlsProtocol.HandshakeMessage(this, 15);
        certificateVerify.a(message);
        message.a();
    }

    /* access modifiers changed from: protected */
    public void a0() {
        TlsProtocol.HandshakeMessage message = new TlsProtocol.HandshakeMessage(this, 16);
        this.G.h(message);
        message.a();
    }
}
