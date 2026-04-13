package org.spongycastle.crypto.tls;

import com.alibaba.fastjson.asm.Opcodes;
import org.spongycastle.crypto.agreement.DHStandardGroups;
import org.spongycastle.crypto.params.DHParameters;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class PSKTlsServer extends AbstractTlsServer {
    protected TlsPSKIdentityManager s;

    /* access modifiers changed from: protected */
    public TlsEncryptionCredentials P() {
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    public DHParameters O() {
        return DHStandardGroups.q;
    }

    /* access modifiers changed from: protected */
    public int[] I() {
        return new int[]{49207, 49205, Opcodes.GETSTATIC, IjkMediaMeta.FF_PROFILE_H264_HIGH_444};
    }

    public TlsCredentials getCredentials() {
        switch (TlsUtils.E(this.p)) {
            case 13:
            case 14:
            case 24:
                return null;
            case 15:
                return P();
            default:
                throw new TlsFatalAlert(80);
        }
    }

    public TlsKeyExchange a() {
        int keyExchangeAlgorithm = TlsUtils.E(this.p);
        switch (keyExchangeAlgorithm) {
            case 13:
            case 14:
            case 15:
            case 24:
                return N(keyExchangeAlgorithm);
            default:
                throw new TlsFatalAlert(80);
        }
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange N(int keyExchange) {
        return new TlsPSKKeyExchange(keyExchange, this.j, (TlsPSKIdentity) null, this.s, O(), this.l, this.m, this.n);
    }
}
