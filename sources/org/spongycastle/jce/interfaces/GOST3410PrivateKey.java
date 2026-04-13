package org.spongycastle.jce.interfaces;

import java.math.BigInteger;
import java.security.PrivateKey;

public interface GOST3410PrivateKey extends GOST3410Key, PrivateKey {
    /* synthetic */ GOST3410Params getParameters();

    BigInteger getX();
}
