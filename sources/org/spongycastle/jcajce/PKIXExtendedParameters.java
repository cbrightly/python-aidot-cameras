package org.spongycastle.jcajce;

import java.security.cert.CertPathParameters;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.jcajce.PKIXCertStoreSelector;

public class PKIXExtendedParameters implements CertPathParameters {
    /* access modifiers changed from: private */
    public final boolean a1;
    private final Set<TrustAnchor> a2;
    /* access modifiers changed from: private */
    public final PKIXParameters c;
    /* access modifiers changed from: private */
    public final PKIXCertStoreSelector d;
    /* access modifiers changed from: private */
    public final Date f;
    private final boolean p0;
    /* access modifiers changed from: private */
    public final int p1;
    /* access modifiers changed from: private */
    public final List<PKIXCertStore> q;
    /* access modifiers changed from: private */
    public final Map<GeneralName, PKIXCertStore> x;
    /* access modifiers changed from: private */
    public final List<PKIXCRLStore> y;
    /* access modifiers changed from: private */
    public final Map<GeneralName, PKIXCRLStore> z;

    public static class Builder {
        /* access modifiers changed from: private */
        public final PKIXParameters a;
        /* access modifiers changed from: private */
        public final Date b;
        /* access modifiers changed from: private */
        public PKIXCertStoreSelector c;
        /* access modifiers changed from: private */
        public List<PKIXCertStore> d = new ArrayList();
        /* access modifiers changed from: private */
        public Map<GeneralName, PKIXCertStore> e = new HashMap();
        /* access modifiers changed from: private */
        public List<PKIXCRLStore> f = new ArrayList();
        /* access modifiers changed from: private */
        public Map<GeneralName, PKIXCRLStore> g = new HashMap();
        /* access modifiers changed from: private */
        public boolean h;
        /* access modifiers changed from: private */
        public int i = 0;
        /* access modifiers changed from: private */
        public boolean j = false;
        /* access modifiers changed from: private */
        public Set<TrustAnchor> k;

        public Builder(PKIXParameters baseParameters) {
            this.a = (PKIXParameters) baseParameters.clone();
            CertSelector constraints = baseParameters.getTargetCertConstraints();
            if (constraints != null) {
                this.c = new PKIXCertStoreSelector.Builder(constraints).a();
            }
            Date checkDate = baseParameters.getDate();
            this.b = checkDate == null ? new Date() : checkDate;
            this.h = baseParameters.isRevocationEnabled();
            this.k = baseParameters.getTrustAnchors();
        }

        public Builder(PKIXExtendedParameters baseParameters) {
            this.a = baseParameters.c;
            this.b = baseParameters.f;
            this.c = baseParameters.d;
            this.d = new ArrayList(baseParameters.q);
            this.e = new HashMap(baseParameters.x);
            this.f = new ArrayList(baseParameters.y);
            this.g = new HashMap(baseParameters.z);
            this.j = baseParameters.a1;
            this.i = baseParameters.p1;
            this.h = baseParameters.A();
            this.k = baseParameters.u();
        }

        public Builder m(PKIXCertStore store) {
            this.d.add(store);
            return this;
        }

        public Builder l(PKIXCRLStore store) {
            this.f.add(store);
            return this;
        }

        public Builder p(PKIXCertStoreSelector selector) {
            this.c = selector;
            return this;
        }

        public Builder r(boolean useDeltas) {
            this.j = useDeltas;
            return this;
        }

        public Builder s(int validityModel) {
            this.i = validityModel;
            return this;
        }

        public Builder q(TrustAnchor trustAnchor) {
            this.k = Collections.singleton(trustAnchor);
            return this;
        }

        public void o(boolean revocationEnabled) {
            this.h = revocationEnabled;
        }

        public PKIXExtendedParameters n() {
            return new PKIXExtendedParameters(this);
        }
    }

    private PKIXExtendedParameters(Builder builder) {
        this.c = builder.a;
        this.f = builder.b;
        this.q = Collections.unmodifiableList(builder.d);
        this.x = Collections.unmodifiableMap(new HashMap(builder.e));
        this.y = Collections.unmodifiableList(builder.f);
        this.z = Collections.unmodifiableMap(new HashMap(builder.g));
        this.d = builder.c;
        this.p0 = builder.h;
        this.a1 = builder.j;
        this.p1 = builder.i;
        this.a2 = Collections.unmodifiableSet(builder.k);
    }

    public List<PKIXCertStore> n() {
        return this.q;
    }

    public Map<GeneralName, PKIXCertStore> r() {
        return this.x;
    }

    public List<PKIXCRLStore> k() {
        return this.y;
    }

    public Map<GeneralName, PKIXCRLStore> q() {
        return this.z;
    }

    public Date o() {
        return new Date(this.f.getTime());
    }

    public boolean B() {
        return this.a1;
    }

    public int v() {
        return this.p1;
    }

    public Object clone() {
        return this;
    }

    public PKIXCertStoreSelector t() {
        return this.d;
    }

    public Set u() {
        return this.a2;
    }

    public Set p() {
        return this.c.getInitialPolicies();
    }

    public String s() {
        return this.c.getSigProvider();
    }

    public boolean y() {
        return this.c.isExplicitPolicyRequired();
    }

    public boolean w() {
        return this.c.isAnyPolicyInhibited();
    }

    public boolean z() {
        return this.c.isPolicyMappingInhibited();
    }

    public List l() {
        return this.c.getCertPathCheckers();
    }

    public List<CertStore> m() {
        return this.c.getCertStores();
    }

    public boolean A() {
        return this.p0;
    }
}
