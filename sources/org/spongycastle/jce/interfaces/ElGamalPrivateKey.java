package org.spongycastle.jce.interfaces;

import java.math.BigInteger;
import javax.crypto.interfaces.DHPrivateKey;
import org.spongycastle.jce.spec.ElGamalParameterSpec;

public interface ElGamalPrivateKey extends ElGamalKey, DHPrivateKey {
    /* synthetic */ ElGamalParameterSpec getParameters();

    BigInteger getX();
}
