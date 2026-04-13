package org.spongycastle.pqc.jcajce.provider.newhope;

import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import org.spongycastle.pqc.crypto.ExchangePair;
import org.spongycastle.pqc.crypto.newhope.NHAgreement;
import org.spongycastle.pqc.crypto.newhope.NHExchangePairGenerator;
import org.spongycastle.pqc.crypto.newhope.NHPublicKeyParameters;
import org.spongycastle.util.Arrays;

public class KeyAgreementSpi extends BaseAgreementSpi {
    private NHAgreement i;
    private BCNHPublicKey j;
    private NHExchangePairGenerator k;
    private byte[] l;

    public KeyAgreementSpi() {
        super("NH", (DerivationFunction) null);
    }

    /* access modifiers changed from: protected */
    public void engineInit(Key key, SecureRandom secureRandom) {
        if (key != null) {
            NHAgreement nHAgreement = new NHAgreement();
            this.i = nHAgreement;
            nHAgreement.b(((BCNHPrivateKey) key).getKeyParams());
            return;
        }
        this.k = new NHExchangePairGenerator(secureRandom);
    }

    /* access modifiers changed from: protected */
    public void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        throw new InvalidAlgorithmParameterException("NewHope does not require parameters");
    }

    /* access modifiers changed from: protected */
    public Key engineDoPhase(Key key, boolean lastPhase) {
        if (lastPhase) {
            BCNHPublicKey bCNHPublicKey = (BCNHPublicKey) key;
            this.j = bCNHPublicKey;
            NHExchangePairGenerator nHExchangePairGenerator = this.k;
            if (nHExchangePairGenerator != null) {
                ExchangePair exchPair = nHExchangePairGenerator.a((AsymmetricKeyParameter) bCNHPublicKey.getKeyParams());
                this.l = exchPair.b();
                return new BCNHPublicKey((NHPublicKeyParameters) exchPair.a());
            }
            this.l = this.i.a(bCNHPublicKey.getKeyParams());
            return null;
        }
        throw new IllegalStateException("NewHope can only be between two parties.");
    }

    /* access modifiers changed from: protected */
    public byte[] engineGenerateSecret() {
        byte[] rv = Arrays.h(this.l);
        Arrays.F(this.l, (byte) 0);
        return rv;
    }

    /* access modifiers changed from: protected */
    public int engineGenerateSecret(byte[] bytes, int offset) {
        byte[] bArr = this.l;
        System.arraycopy(bArr, 0, bytes, offset, bArr.length);
        Arrays.F(this.l, (byte) 0);
        return this.l.length;
    }

    /* access modifiers changed from: protected */
    public byte[] a() {
        return engineGenerateSecret();
    }
}
