package org.spongycastle.jce.interfaces;

import java.math.BigInteger;
import java.security.PublicKey;

public interface GOST3410PublicKey extends GOST3410Key, PublicKey {
    /* synthetic */ GOST3410Params getParameters();

    BigInteger getY();
}
