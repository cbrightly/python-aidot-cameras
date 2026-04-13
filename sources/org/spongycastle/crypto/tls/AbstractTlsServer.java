package org.spongycastle.crypto.tls;

import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.util.Arrays;

public abstract class AbstractTlsServer extends AbstractTlsPeer implements TlsServer {
    protected TlsCipherFactory a;
    protected TlsServerContext b;
    protected ProtocolVersion c;
    protected int[] d;
    protected short[] e;
    protected Hashtable f;
    protected boolean g;
    protected short h;
    protected boolean i;
    protected Vector j;
    protected boolean k;
    protected int[] l;
    protected short[] m;
    protected short[] n;
    protected ProtocolVersion o;
    protected int p;
    protected short q;
    protected Hashtable r;

    /* access modifiers changed from: protected */
    public abstract int[] I();

    public AbstractTlsServer() {
        this(new DefaultTlsCipherFactory());
    }

    public AbstractTlsServer(TlsCipherFactory cipherFactory) {
        this.a = cipherFactory;
    }

    /* access modifiers changed from: protected */
    public boolean F() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean G() {
        return false;
    }

    /* access modifiers changed from: protected */
    public Hashtable H() {
        Hashtable j2 = TlsExtensionsUtils.j(this.r);
        this.r = j2;
        return j2;
    }

    /* access modifiers changed from: protected */
    public short[] J() {
        return new short[]{0};
    }

    /* access modifiers changed from: protected */
    public ProtocolVersion K() {
        return ProtocolVersion.c;
    }

    /* access modifiers changed from: protected */
    public ProtocolVersion L() {
        return ProtocolVersion.b;
    }

    /* access modifiers changed from: protected */
    public boolean M(int[] namedCurves, short[] ecPointFormats) {
        if (namedCurves == null) {
            return TlsECCUtils.p();
        }
        for (int namedCurve : namedCurves) {
            if (NamedCurve.a(namedCurve) && (!NamedCurve.b(namedCurve) || TlsECCUtils.s(namedCurve))) {
                return true;
            }
        }
        return false;
    }

    public void A(ProtocolVersion clientVersion) {
        this.c = clientVersion;
    }

    public void c(boolean isFallback) {
        if (isFallback && K().i(this.c)) {
            throw new TlsFatalAlert(86);
        }
    }

    public void o(int[] offeredCipherSuites) {
        this.d = offeredCipherSuites;
        this.k = TlsECCUtils.d(offeredCipherSuites);
    }

    public void C(short[] offeredCompressionMethods) {
        this.e = offeredCompressionMethods;
    }

    public void l(Hashtable clientExtensions) {
        this.f = clientExtensions;
        if (clientExtensions != null) {
            this.g = TlsExtensionsUtils.m(clientExtensions);
            short k2 = TlsExtensionsUtils.k(clientExtensions);
            this.h = k2;
            if (k2 < 0 || MaxFragmentLength.a(k2)) {
                this.i = TlsExtensionsUtils.o(clientExtensions);
                Vector I = TlsUtils.I(clientExtensions);
                this.j = I;
                if (I == null || TlsUtils.Q(this.c)) {
                    this.l = TlsECCUtils.n(clientExtensions);
                    this.m = TlsECCUtils.o(clientExtensions);
                    return;
                }
                throw new TlsFatalAlert(47);
            }
            throw new TlsFatalAlert(47);
        }
    }

    public ProtocolVersion b() {
        if (L().h(this.c)) {
            ProtocolVersion maximumVersion = K();
            if (this.c.h(maximumVersion)) {
                ProtocolVersion protocolVersion = this.c;
                this.o = protocolVersion;
                return protocolVersion;
            } else if (this.c.i(maximumVersion)) {
                this.o = maximumVersion;
                return maximumVersion;
            }
        }
        throw new TlsFatalAlert(70);
    }

    public int B() {
        Vector sigAlgs = TlsUtils.K(this.j);
        boolean eccCipherSuitesEnabled = M(this.l, this.m);
        int[] cipherSuites = I();
        int i2 = 0;
        while (i2 < cipherSuites.length) {
            int cipherSuite = cipherSuites[i2];
            if (!Arrays.v(this.d, cipherSuite) || ((!eccCipherSuitesEnabled && TlsECCUtils.r(cipherSuite)) || !TlsUtils.W(cipherSuite, this.o) || !TlsUtils.V(cipherSuite, sigAlgs))) {
                i2++;
            } else {
                this.p = cipherSuite;
                return cipherSuite;
            }
        }
        throw new TlsFatalAlert(40);
    }

    public short g() {
        short[] compressionMethods = J();
        for (int i2 = 0; i2 < compressionMethods.length; i2++) {
            if (Arrays.w(this.e, compressionMethods[i2])) {
                short s = compressionMethods[i2];
                this.q = s;
                return s;
            }
        }
        throw new TlsFatalAlert(40);
    }

    public Hashtable e() {
        if (this.g && F() && TlsUtils.O(this.p)) {
            TlsExtensionsUtils.a(H());
        }
        short s = this.h;
        if (s >= 0 && MaxFragmentLength.a(s)) {
            TlsExtensionsUtils.c(H(), this.h);
        }
        if (this.i && G()) {
            TlsExtensionsUtils.d(H());
        }
        if (this.m != null && TlsECCUtils.r(this.p)) {
            this.n = new short[]{0, 1, 2};
            TlsECCUtils.a(H(), this.n);
        }
        return this.r;
    }

    public Vector k() {
        return null;
    }

    public CertificateStatus y() {
        return null;
    }

    public CertificateRequest x() {
        return null;
    }

    public void q(Vector clientSupplementalData) {
        if (clientSupplementalData != null) {
            throw new TlsFatalAlert(10);
        }
    }

    public void E(Certificate clientCertificate) {
        throw new TlsFatalAlert(80);
    }

    public TlsCompression h() {
        switch (this.q) {
            case 0:
                return new TlsNullCompression();
            default:
                throw new TlsFatalAlert(80);
        }
    }

    public TlsCipher n() {
        return this.a.a(this.b, TlsUtils.B(this.p), TlsUtils.F(this.p));
    }

    public NewSessionTicket j() {
        return new NewSessionTicket(0, TlsUtils.a);
    }
}
