package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECMultiplier;
import org.spongycastle.math.ec.FixedPointCombMultiplier;
import org.spongycastle.math.ec.WNafUtil;

public class ECKeyPairGenerator implements AsymmetricCipherKeyPairGenerator, ECConstants {
    ECDomainParameters g;
    SecureRandom h;

    public void c(KeyGenerationParameters param) {
        ECKeyGenerationParameters ecP = (ECKeyGenerationParameters) param;
        this.h = ecP.a();
        this.g = ecP.c();
        if (this.h == null) {
            this.h = new SecureRandom();
        }
    }

    public AsymmetricCipherKeyPair a() {
        BigInteger n = this.g.d();
        int nBitLength = n.bitLength();
        int minWeight = nBitLength >>> 2;
        while (true) {
            BigInteger d = new BigInteger(nBitLength, this.h);
            if (d.compareTo(ECConstants.c) >= 0 && d.compareTo(n) < 0 && WNafUtil.e(d) >= minWeight) {
                return new AsymmetricCipherKeyPair(new ECPublicKeyParameters(b().a(this.g.b(), d), this.g), new ECPrivateKeyParameters(d, this.g));
            }
        }
    }

    /* access modifiers changed from: protected */
    public ECMultiplier b() {
        return new FixedPointCombMultiplier();
    }
}
