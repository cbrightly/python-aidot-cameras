package org.spongycastle.x509;

import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXParameters;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.spongycastle.util.Selector;

public class ExtendedPKIXBuilderParameters extends ExtendedPKIXParameters {
    private int a2 = 5;
    private Set p2 = Collections.EMPTY_SET;

    public Set l() {
        return Collections.unmodifiableSet(this.p2);
    }

    public ExtendedPKIXBuilderParameters(Set trustAnchors, Selector targetConstraints) {
        super(trustAnchors);
        k(targetConstraints);
    }

    public int m() {
        return this.a2;
    }

    /* access modifiers changed from: protected */
    public void j(PKIXParameters params) {
        super.j(params);
        if (params instanceof ExtendedPKIXBuilderParameters) {
            ExtendedPKIXBuilderParameters _params = (ExtendedPKIXBuilderParameters) params;
            this.a2 = _params.a2;
            this.p2 = new HashSet(_params.p2);
        }
        if (params instanceof PKIXBuilderParameters) {
            this.a2 = ((PKIXBuilderParameters) params).getMaxPathLength();
        }
    }

    public Object clone() {
        try {
            ExtendedPKIXBuilderParameters params = new ExtendedPKIXBuilderParameters(getTrustAnchors(), f());
            params.j(this);
            return params;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
