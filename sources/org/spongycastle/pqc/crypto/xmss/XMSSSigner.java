package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.pqc.crypto.StateAwareMessageSigner;
import org.spongycastle.pqc.crypto.xmss.OTSHashAddress;
import org.spongycastle.pqc.crypto.xmss.XMSSSignature;
import org.spongycastle.util.Arrays;

public class XMSSSigner implements StateAwareMessageSigner {
    private XMSSPrivateKeyParameters a;
    private XMSSPrivateKeyParameters b;
    private XMSSPublicKeyParameters c;
    private XMSSParameters d;
    private KeyedHashFunctions e;
    private boolean f;
    private boolean g;

    public void a(boolean forSigning, CipherParameters param) {
        if (forSigning) {
            this.f = true;
            this.g = false;
            XMSSPrivateKeyParameters xMSSPrivateKeyParameters = (XMSSPrivateKeyParameters) param;
            this.a = xMSSPrivateKeyParameters;
            this.b = xMSSPrivateKeyParameters;
            XMSSParameters e2 = xMSSPrivateKeyParameters.e();
            this.d = e2;
            this.e = e2.f().d();
            return;
        }
        this.f = false;
        XMSSPublicKeyParameters xMSSPublicKeyParameters = (XMSSPublicKeyParameters) param;
        this.c = xMSSPublicKeyParameters;
        XMSSParameters b2 = xMSSPublicKeyParameters.b();
        this.d = b2;
        this.e = b2.f().d();
    }

    public byte[] b(byte[] message) {
        if (message == null) {
            throw new NullPointerException("message == null");
        } else if (this.f) {
            XMSSPrivateKeyParameters xMSSPrivateKeyParameters = this.a;
            if (xMSSPrivateKeyParameters == null) {
                throw new IllegalStateException("signing key no longer usable");
            } else if (!xMSSPrivateKeyParameters.b().getAuthenticationPath().isEmpty()) {
                int index = this.a.c();
                if (XMSSUtil.l(this.d.d(), (long) index)) {
                    byte[] random = this.e.d(this.a.h(), XMSSUtil.q((long) index, 32));
                    XMSSSignature signature = (XMSSSignature) new XMSSSignature.Builder(this.d).l(index).m(random).h(d(this.e.c(Arrays.s(random, this.a.g(), XMSSUtil.q((long) index, this.d.c())), message), (OTSHashAddress) new OTSHashAddress.Builder().p(index).l())).f(this.a.b().getAuthenticationPath()).e();
                    this.g = true;
                    XMSSPrivateKeyParameters xMSSPrivateKeyParameters2 = this.b;
                    if (xMSSPrivateKeyParameters2 != null) {
                        XMSSPrivateKeyParameters d2 = xMSSPrivateKeyParameters2.d();
                        this.a = d2;
                        this.b = d2;
                    } else {
                        this.a = null;
                    }
                    return signature.d();
                }
                throw new IllegalStateException("index out of bounds");
            } else {
                throw new IllegalStateException("not initialized");
            }
        } else {
            throw new IllegalStateException("signer not initialized for signature generation");
        }
    }

    public boolean c(byte[] message, byte[] signature) {
        XMSSSignature sig = new XMSSSignature.Builder(this.d).n(signature).e();
        int index = sig.e();
        this.d.f().j(new byte[this.d.c()], this.c.c());
        byte[] messageDigest = this.e.c(Arrays.s(sig.f(), this.c.d(), XMSSUtil.q((long) index, this.d.c())), message);
        int xmssHeight = this.d.d();
        return Arrays.u(XMSSVerifierUtil.a(this.d.f(), xmssHeight, messageDigest, sig, (OTSHashAddress) new OTSHashAddress.Builder().p(index).l(), XMSSUtil.i((long) index, xmssHeight)).getValue(), this.c.d());
    }

    private WOTSPlusSignature d(byte[] messageDigest, OTSHashAddress otsHashAddress) {
        if (messageDigest.length != this.d.c()) {
            throw new IllegalArgumentException("size of messageDigest needs to be equal to size of digest");
        } else if (otsHashAddress != null) {
            this.d.f().j(this.d.f().i(this.a.i(), otsHashAddress), this.a.f());
            return this.d.f().k(messageDigest, otsHashAddress);
        } else {
            throw new NullPointerException("otsHashAddress == null");
        }
    }
}
