package org.spongycastle.jce.interfaces;

import java.security.PublicKey;
import org.spongycastle.jce.spec.ECParameterSpec;
import org.spongycastle.math.ec.ECPoint;

public interface ECPublicKey extends ECKey, PublicKey {
    /* synthetic */ ECParameterSpec getParameters();

    ECPoint getQ();
}
