package org.spongycastle.math.ec.endo;

import org.spongycastle.math.ec.ECPointMap;

public interface ECEndomorphism {
    boolean a();

    ECPointMap b();
}
