package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.pqc.crypto.StateAwareMessageSigner;
import org.spongycastle.pqc.crypto.xmss.OTSHashAddress;
import org.spongycastle.pqc.crypto.xmss.XMSSMTSignature;
import org.spongycastle.pqc.crypto.xmss.XMSSReducedSignature;
import org.spongycastle.util.Arrays;

public class XMSSMTSigner implements StateAwareMessageSigner {
    private XMSSMTPrivateKeyParameters a;
    private XMSSMTPrivateKeyParameters b;
    private XMSSMTPublicKeyParameters c;
    private XMSSMTParameters d;
    private XMSSParameters e;
    private WOTSPlus f;
    private boolean g;
    private boolean h;

    public void a(boolean forSigning, CipherParameters param) {
        if (forSigning) {
            this.h = true;
            this.g = false;
            XMSSMTPrivateKeyParameters xMSSMTPrivateKeyParameters = (XMSSMTPrivateKeyParameters) param;
            this.a = xMSSMTPrivateKeyParameters;
            this.b = xMSSMTPrivateKeyParameters;
            XMSSMTParameters e2 = xMSSMTPrivateKeyParameters.e();
            this.d = e2;
            this.e = e2.h();
        } else {
            this.h = false;
            XMSSMTPublicKeyParameters xMSSMTPublicKeyParameters = (XMSSMTPublicKeyParameters) param;
            this.c = xMSSMTPublicKeyParameters;
            XMSSMTParameters b2 = xMSSMTPublicKeyParameters.b();
            this.d = b2;
            this.e = b2.h();
        }
        this.f = new WOTSPlus(new WOTSPlusParameters(this.d.a()));
    }

    public byte[] b(byte[] message) {
        int totalHeight;
        int xmssHeight;
        long globalIndex;
        byte[] bArr = message;
        if (bArr == null) {
            throw new NullPointerException("message == null");
        } else if (this.h) {
            XMSSMTPrivateKeyParameters xMSSMTPrivateKeyParameters = this.a;
            if (xMSSMTPrivateKeyParameters == null) {
                throw new IllegalStateException("signing key no longer usable");
            } else if (!xMSSMTPrivateKeyParameters.b().isEmpty()) {
                BDSStateMap bdsState = this.a.b();
                long globalIndex2 = this.a.c();
                int totalHeight2 = this.d.c();
                int xmssHeight2 = this.e.d();
                if (XMSSUtil.l(totalHeight2, globalIndex2)) {
                    byte[] random = this.f.d().d(this.a.h(), XMSSUtil.q(globalIndex2, 32));
                    byte[] messageDigest = this.f.d().c(Arrays.s(random, this.a.g(), XMSSUtil.q(globalIndex2, this.d.b())), bArr);
                    XMSSMTSignature signature = new XMSSMTSignature.Builder(this.d).g(globalIndex2).h(random).f();
                    long indexTree = XMSSUtil.j(globalIndex2, xmssHeight2);
                    int indexLeaf = XMSSUtil.i(globalIndex2, xmssHeight2);
                    this.f.j(new byte[this.d.b()], this.a.f());
                    OTSHashAddress otsHashAddress = (OTSHashAddress) ((OTSHashAddress.Builder) new OTSHashAddress.Builder().h(indexTree)).p(indexLeaf).l();
                    if (bdsState.get(0) == null || indexLeaf == 0) {
                        int i = totalHeight2;
                        byte[] bArr2 = random;
                        totalHeight = 0;
                        bdsState.put(0, new BDS(this.e, this.a.f(), this.a.i(), otsHashAddress));
                    } else {
                        int i2 = totalHeight2;
                        byte[] bArr3 = random;
                        totalHeight = 0;
                    }
                    XMSSReducedSignature reducedSignature = new XMSSReducedSignature.Builder(this.e).h(d(messageDigest, otsHashAddress)).f(bdsState.get(totalHeight).getAuthenticationPath()).e();
                    signature.c().add(reducedSignature);
                    int layer = 1;
                    while (layer < this.d.d()) {
                        XMSSNode root = bdsState.get(layer - 1).getRoot();
                        int indexLeaf2 = XMSSUtil.i(indexTree, xmssHeight2);
                        indexTree = XMSSUtil.j(indexTree, xmssHeight2);
                        OTSHashAddress oTSHashAddress = otsHashAddress;
                        otsHashAddress = (OTSHashAddress) ((OTSHashAddress.Builder) ((OTSHashAddress.Builder) new OTSHashAddress.Builder().g(layer)).h(indexTree)).p(indexLeaf2).l();
                        XMSSReducedSignature xMSSReducedSignature = reducedSignature;
                        WOTSPlusSignature wotsPlusSignature = d(root.getValue(), otsHashAddress);
                        if (bdsState.get(layer) == null || XMSSUtil.n(globalIndex2, xmssHeight2, layer)) {
                            globalIndex = globalIndex2;
                            xmssHeight = xmssHeight2;
                            bdsState.put(layer, new BDS(this.e, this.a.f(), this.a.i(), otsHashAddress));
                        } else {
                            globalIndex = globalIndex2;
                            xmssHeight = xmssHeight2;
                        }
                        reducedSignature = new XMSSReducedSignature.Builder(this.e).h(wotsPlusSignature).f(bdsState.get(layer).getAuthenticationPath()).e();
                        signature.c().add(reducedSignature);
                        layer++;
                        globalIndex2 = globalIndex;
                        xmssHeight2 = xmssHeight;
                    }
                    OTSHashAddress oTSHashAddress2 = otsHashAddress;
                    long j = globalIndex2;
                    XMSSReducedSignature xMSSReducedSignature2 = reducedSignature;
                    int i3 = xmssHeight2;
                    this.g = true;
                    XMSSMTPrivateKeyParameters xMSSMTPrivateKeyParameters2 = this.b;
                    if (xMSSMTPrivateKeyParameters2 != null) {
                        XMSSMTPrivateKeyParameters d2 = xMSSMTPrivateKeyParameters2.d();
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
        byte[] bArr = message;
        byte[] bArr2 = signature;
        if (bArr == null) {
            throw new NullPointerException("message == null");
        } else if (bArr2 == null) {
            throw new NullPointerException("signature == null");
        } else if (this.c != null) {
            XMSSMTSignature sig = new XMSSMTSignature.Builder(this.d).i(bArr2).f();
            byte[] messageDigest = this.f.d().c(Arrays.s(sig.b(), this.c.d(), XMSSUtil.q(sig.a(), this.d.b())), bArr);
            long globalIndex = sig.a();
            int xmssHeight = this.e.d();
            long indexTree = XMSSUtil.j(globalIndex, xmssHeight);
            int indexLeaf = XMSSUtil.i(globalIndex, xmssHeight);
            this.f.j(new byte[this.d.b()], this.c.c());
            XMSSNode rootNode = XMSSVerifierUtil.a(this.f, xmssHeight, messageDigest, sig.c().get(0), (OTSHashAddress) ((OTSHashAddress.Builder) new OTSHashAddress.Builder().h(indexTree)).p(indexLeaf).l(), indexLeaf);
            int layer = 1;
            long indexTree2 = indexTree;
            while (layer < this.d.d()) {
                int indexLeaf2 = XMSSUtil.i(indexTree2, xmssHeight);
                indexTree2 = XMSSUtil.j(indexTree2, xmssHeight);
                OTSHashAddress otsHashAddress = (OTSHashAddress) ((OTSHashAddress.Builder) ((OTSHashAddress.Builder) new OTSHashAddress.Builder().g(layer)).h(indexTree2)).p(indexLeaf2).l();
                int i = xmssHeight;
                rootNode = XMSSVerifierUtil.a(this.f, xmssHeight, rootNode.getValue(), sig.c().get(layer), otsHashAddress, indexLeaf2);
                layer++;
                int i2 = indexLeaf2;
                OTSHashAddress oTSHashAddress = otsHashAddress;
            }
            return Arrays.u(rootNode.getValue(), this.c.d());
        } else {
            throw new NullPointerException("publicKey == null");
        }
    }

    private WOTSPlusSignature d(byte[] messageDigest, OTSHashAddress otsHashAddress) {
        if (messageDigest.length != this.d.b()) {
            throw new IllegalArgumentException("size of messageDigest needs to be equal to size of digest");
        } else if (otsHashAddress != null) {
            WOTSPlus wOTSPlus = this.f;
            wOTSPlus.j(wOTSPlus.i(this.a.i(), otsHashAddress), this.a.f());
            return this.f.k(messageDigest, otsHashAddress);
        } else {
            throw new NullPointerException("otsHashAddress == null");
        }
    }
}
