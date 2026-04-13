package org.spongycastle.crypto.tls;

import java.util.Hashtable;
import java.util.Vector;

public abstract class AbstractTlsClient extends AbstractTlsPeer implements TlsClient {
    protected TlsCipherFactory a;
    protected TlsClientContext b;
    protected Vector c;
    protected int[] d;
    protected short[] e;
    protected short[] f;
    protected int g;
    protected short h;

    public AbstractTlsClient() {
        this(new DefaultTlsCipherFactory());
    }

    public AbstractTlsClient(TlsCipherFactory cipherFactory) {
        this.a = cipherFactory;
    }

    /* access modifiers changed from: protected */
    public boolean F(Integer extensionType, byte[] extensionData) {
        switch (extensionType.intValue()) {
            case 10:
                TlsECCUtils.x(extensionData);
                return true;
            default:
                return false;
        }
    }

    /* access modifiers changed from: protected */
    public void G(Hashtable serverExtensions, Integer extensionType) {
        byte[] extensionData = TlsUtils.C(serverExtensions, extensionType);
        if (extensionData != null && !F(extensionType, extensionData)) {
            throw new TlsFatalAlert(47);
        }
    }

    public ProtocolVersion H() {
        return ProtocolVersion.b;
    }

    public void r(ProtocolVersion serverVersion) {
        if (!H().h(serverVersion)) {
            throw new TlsFatalAlert(70);
        }
    }

    public void u(byte[] sessionID) {
    }

    public void z(int selectedCipherSuite) {
        this.g = selectedCipherSuite;
    }

    public void d(short selectedCompressionMethod) {
        this.h = selectedCompressionMethod;
    }

    public void i(Hashtable serverExtensions) {
        if (serverExtensions != null) {
            G(serverExtensions, TlsUtils.e);
            G(serverExtensions, TlsECCUtils.a);
            if (TlsECCUtils.r(this.g)) {
                this.f = TlsECCUtils.o(serverExtensions);
            } else {
                G(serverExtensions, TlsECCUtils.b);
            }
            G(serverExtensions, TlsExtensionsUtils.e);
        }
    }

    public void t(Vector serverSupplementalData) {
        if (serverSupplementalData != null) {
            throw new TlsFatalAlert(10);
        }
    }

    public Vector f() {
        return null;
    }

    public TlsCompression h() {
        switch (this.h) {
            case 0:
                return new TlsNullCompression();
            default:
                throw new TlsFatalAlert(80);
        }
    }

    public TlsCipher n() {
        return this.a.a(this.b, TlsUtils.B(this.g), TlsUtils.F(this.g));
    }

    public void D(NewSessionTicket newSessionTicket) {
    }
}
