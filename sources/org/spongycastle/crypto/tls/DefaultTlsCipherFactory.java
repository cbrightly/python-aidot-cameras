package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.engines.AESEngine;
import org.spongycastle.crypto.engines.CamelliaEngine;
import org.spongycastle.crypto.engines.DESedeEngine;
import org.spongycastle.crypto.engines.RC4Engine;
import org.spongycastle.crypto.engines.SEEDEngine;
import org.spongycastle.crypto.modes.AEADBlockCipher;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.modes.CCMBlockCipher;
import org.spongycastle.crypto.modes.GCMBlockCipher;
import org.spongycastle.crypto.modes.OCBBlockCipher;

public class DefaultTlsCipherFactory extends AbstractTlsCipherFactory {
    public TlsCipher a(TlsContext context, int encryptionAlgorithm, int macAlgorithm) {
        switch (encryptionAlgorithm) {
            case 0:
                return t(context, macAlgorithm);
            case 2:
                return u(context, 16, macAlgorithm);
            case 7:
                return r(context, macAlgorithm);
            case 8:
                return g(context, 16, macAlgorithm);
            case 9:
                return g(context, 32, macAlgorithm);
            case 10:
                return n(context, 16, 16);
            case 11:
                return n(context, 32, 16);
            case 12:
                return j(context, 16, macAlgorithm);
            case 13:
                return j(context, 32, macAlgorithm);
            case 14:
                return x(context, macAlgorithm);
            case 15:
                return m(context, 16, 16);
            case 16:
                return m(context, 16, 8);
            case 17:
                return m(context, 32, 16);
            case 18:
                return m(context, 32, 8);
            case 19:
                return p(context, 16, 16);
            case 20:
                return p(context, 32, 16);
            case 21:
                return l(context);
            case 103:
                return o(context, 16, 12);
            case 104:
                return o(context, 32, 12);
            default:
                throw new TlsFatalAlert(80);
        }
    }

    /* access modifiers changed from: protected */
    public TlsBlockCipher g(TlsContext context, int cipherKeySize, int macAlgorithm) {
        return new TlsBlockCipher(context, f(), f(), s(macAlgorithm), s(macAlgorithm), cipherKeySize);
    }

    /* access modifiers changed from: protected */
    public TlsBlockCipher j(TlsContext context, int cipherKeySize, int macAlgorithm) {
        return new TlsBlockCipher(context, i(), i(), s(macAlgorithm), s(macAlgorithm), cipherKeySize);
    }

    /* access modifiers changed from: protected */
    public TlsCipher l(TlsContext context) {
        return new Chacha20Poly1305(context);
    }

    /* access modifiers changed from: protected */
    public TlsAEADCipher m(TlsContext context, int cipherKeySize, int macSize) {
        return new TlsAEADCipher(context, b(), b(), cipherKeySize, macSize);
    }

    /* access modifiers changed from: protected */
    public TlsAEADCipher n(TlsContext context, int cipherKeySize, int macSize) {
        return new TlsAEADCipher(context, c(), c(), cipherKeySize, macSize);
    }

    /* access modifiers changed from: protected */
    public TlsAEADCipher o(TlsContext context, int cipherKeySize, int macSize) {
        return new TlsAEADCipher(context, d(), d(), cipherKeySize, macSize, 2);
    }

    /* access modifiers changed from: protected */
    public TlsAEADCipher p(TlsContext context, int cipherKeySize, int macSize) {
        return new TlsAEADCipher(context, e(), e(), cipherKeySize, macSize);
    }

    /* access modifiers changed from: protected */
    public TlsBlockCipher r(TlsContext context, int macAlgorithm) {
        return new TlsBlockCipher(context, q(), q(), s(macAlgorithm), s(macAlgorithm), 24);
    }

    /* access modifiers changed from: protected */
    public TlsNullCipher t(TlsContext context, int macAlgorithm) {
        return new TlsNullCipher(context, s(macAlgorithm), s(macAlgorithm));
    }

    /* access modifiers changed from: protected */
    public TlsStreamCipher u(TlsContext context, int cipherKeySize, int macAlgorithm) {
        return new TlsStreamCipher(context, v(), v(), s(macAlgorithm), s(macAlgorithm), cipherKeySize, false);
    }

    /* access modifiers changed from: protected */
    public TlsBlockCipher x(TlsContext context, int macAlgorithm) {
        return new TlsBlockCipher(context, w(), w(), s(macAlgorithm), s(macAlgorithm), 16);
    }

    /* access modifiers changed from: protected */
    public BlockCipher h() {
        return new AESEngine();
    }

    /* access modifiers changed from: protected */
    public BlockCipher k() {
        return new CamelliaEngine();
    }

    /* access modifiers changed from: protected */
    public BlockCipher f() {
        return new CBCBlockCipher(h());
    }

    /* access modifiers changed from: protected */
    public AEADBlockCipher b() {
        return new CCMBlockCipher(h());
    }

    /* access modifiers changed from: protected */
    public AEADBlockCipher c() {
        return new GCMBlockCipher(h());
    }

    /* access modifiers changed from: protected */
    public AEADBlockCipher d() {
        return new OCBBlockCipher(h(), h());
    }

    /* access modifiers changed from: protected */
    public AEADBlockCipher e() {
        return new GCMBlockCipher(k());
    }

    /* access modifiers changed from: protected */
    public BlockCipher i() {
        return new CBCBlockCipher(k());
    }

    /* access modifiers changed from: protected */
    public BlockCipher q() {
        return new CBCBlockCipher(new DESedeEngine());
    }

    /* access modifiers changed from: protected */
    public StreamCipher v() {
        return new RC4Engine();
    }

    /* access modifiers changed from: protected */
    public BlockCipher w() {
        return new CBCBlockCipher(new SEEDEngine());
    }

    /* access modifiers changed from: protected */
    public Digest s(int macAlgorithm) {
        switch (macAlgorithm) {
            case 0:
                return null;
            case 1:
                return TlsUtils.o(1);
            case 2:
                return TlsUtils.o(2);
            case 3:
                return TlsUtils.o(4);
            case 4:
                return TlsUtils.o(5);
            case 5:
                return TlsUtils.o(6);
            default:
                throw new TlsFatalAlert(80);
        }
    }
}
