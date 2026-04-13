package org.spongycastle.x509;

import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.PKIXParameters;
import java.security.cert.X509CertSelector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.spongycastle.util.Selector;

public class ExtendedPKIXParameters extends PKIXParameters {
    private int a1 = 0;
    private List c = new ArrayList();
    private Selector d;
    private boolean f;
    private Set p0 = new HashSet();
    private boolean p1 = false;
    private List q = new ArrayList();
    private Set x = new HashSet();
    private Set y = new HashSet();
    private Set z = new HashSet();

    public ExtendedPKIXParameters(Set trustAnchors) {
        super(trustAnchors);
    }

    /* access modifiers changed from: protected */
    public void j(PKIXParameters params) {
        Selector selector;
        setDate(params.getDate());
        setCertPathCheckers(params.getCertPathCheckers());
        setCertStores(params.getCertStores());
        setAnyPolicyInhibited(params.isAnyPolicyInhibited());
        setExplicitPolicyRequired(params.isExplicitPolicyRequired());
        setPolicyMappingInhibited(params.isPolicyMappingInhibited());
        setRevocationEnabled(params.isRevocationEnabled());
        setInitialPolicies(params.getInitialPolicies());
        setPolicyQualifiersRejected(params.getPolicyQualifiersRejected());
        setSigProvider(params.getSigProvider());
        setTargetCertConstraints(params.getTargetCertConstraints());
        try {
            setTrustAnchors(params.getTrustAnchors());
            if (params instanceof ExtendedPKIXParameters) {
                ExtendedPKIXParameters _params = (ExtendedPKIXParameters) params;
                this.a1 = _params.a1;
                this.p1 = _params.p1;
                this.f = _params.f;
                Selector selector2 = _params.d;
                if (selector2 == null) {
                    selector = null;
                } else {
                    selector = (Selector) selector2.clone();
                }
                this.d = selector;
                this.c = new ArrayList(_params.c);
                this.q = new ArrayList(_params.q);
                this.x = new HashSet(_params.x);
                this.z = new HashSet(_params.z);
                this.y = new HashSet(_params.y);
                this.p0 = new HashSet(_params.p0);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean i() {
        return this.p1;
    }

    public int h() {
        return this.a1;
    }

    public void setCertStores(List stores) {
        if (stores != null) {
            Iterator it = stores.iterator();
            while (it.hasNext()) {
                addCertStore((CertStore) it.next());
            }
        }
    }

    public List a() {
        return Collections.unmodifiableList(this.q);
    }

    public List e() {
        return Collections.unmodifiableList(new ArrayList(this.c));
    }

    public Object clone() {
        try {
            ExtendedPKIXParameters params = new ExtendedPKIXParameters(getTrustAnchors());
            params.j(this);
            return params;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Selector f() {
        Selector selector = this.d;
        if (selector != null) {
            return (Selector) selector.clone();
        }
        return null;
    }

    public void k(Selector selector) {
        if (selector != null) {
            this.d = (Selector) selector.clone();
        } else {
            this.d = null;
        }
    }

    public void setTargetCertConstraints(CertSelector selector) {
        super.setTargetCertConstraints(selector);
        if (selector != null) {
            this.d = X509CertStoreSelector.a((X509CertSelector) selector);
        } else {
            this.d = null;
        }
    }

    public Set c() {
        return Collections.unmodifiableSet(this.y);
    }

    public Set d() {
        return Collections.unmodifiableSet(this.z);
    }

    public Set b() {
        return Collections.unmodifiableSet(this.p0);
    }
}
