package org.spongycastle.pqc.crypto.gmss;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.pqc.crypto.StateAwareMessageSigner;
import org.spongycastle.util.Memoable;

public class GMSSStateAwareSigner implements StateAwareMessageSigner {
    private final GMSSSigner a;
    private GMSSPrivateKeyParameters b;

    /* renamed from: org.spongycastle.pqc.crypto.gmss.GMSSStateAwareSigner$1  reason: invalid class name */
    public class AnonymousClass1 implements GMSSDigestProvider {
        final /* synthetic */ Memoable a;

        public Digest get() {
            return (Digest) this.a.copy();
        }
    }

    public void a(boolean forSigning, CipherParameters param) {
        if (forSigning) {
            if (param instanceof ParametersWithRandom) {
                this.b = (GMSSPrivateKeyParameters) ((ParametersWithRandom) param).a();
            } else {
                this.b = (GMSSPrivateKeyParameters) param;
            }
        }
        this.a.a(forSigning, param);
    }

    public byte[] b(byte[] message) {
        if (this.b != null) {
            byte[] sig = this.a.b(message);
            this.b = this.b.n();
            return sig;
        }
        throw new IllegalStateException("signing key no longer usable");
    }

    public boolean c(byte[] message, byte[] signature) {
        return this.a.c(message, signature);
    }
}
