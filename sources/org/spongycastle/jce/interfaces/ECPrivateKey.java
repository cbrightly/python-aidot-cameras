package org.spongycastle.jce.interfaces;

import java.math.BigInteger;
import java.security.PrivateKey;
import org.spongycastle.jce.spec.ECParameterSpec;

public interface ECPrivateKey extends ECKey, PrivateKey {
    BigInteger getD();

    /* synthetic */ ECParameterSpec getParameters();
}
