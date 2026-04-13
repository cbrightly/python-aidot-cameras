package org.spongycastle.pqc.crypto.newhope;

import java.security.SecureRandom;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.pqc.crypto.ExchangePair;
import org.spongycastle.pqc.crypto.ExchangePairGenerator;

public class NHExchangePairGenerator implements ExchangePairGenerator {
    private final SecureRandom a;

    public NHExchangePairGenerator(SecureRandom random) {
        this.a = random;
    }

    public ExchangePair a(AsymmetricKeyParameter senderPublicKey) {
        byte[] sharedValue = new byte[32];
        byte[] publicKeyValue = new byte[2048];
        NewHope.i(this.a, sharedValue, publicKeyValue, ((NHPublicKeyParameters) senderPublicKey).d);
        return new ExchangePair(new NHPublicKeyParameters(publicKeyValue), sharedValue);
    }
}
