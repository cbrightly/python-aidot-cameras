package org.spongycastle.jce.interfaces;

import java.math.BigInteger;
import javax.crypto.interfaces.DHPublicKey;
import org.spongycastle.jce.spec.ElGamalParameterSpec;

public interface ElGamalPublicKey extends ElGamalKey, DHPublicKey {
    /* synthetic */ ElGamalParameterSpec getParameters();

    BigInteger getY();
}
