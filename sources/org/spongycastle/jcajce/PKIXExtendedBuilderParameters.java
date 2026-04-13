package org.spongycastle.jcajce;

import java.security.InvalidParameterException;
import java.security.cert.CertPathParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXParameters;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.spongycastle.jcajce.PKIXExtendedParameters;

public class PKIXExtendedBuilderParameters implements CertPathParameters {
    private final PKIXExtendedParameters c;
    private final Set<X509Certificate> d;
    private final int f;

    public static class Builder {
        /* access modifiers changed from: private */
        public final PKIXExtendedParameters a;
        /* access modifiers changed from: private */
        public int b = 5;
        /* access modifiers changed from: private */
        public Set<X509Certificate> c = new HashSet();

        public Builder(PKIXBuilderParameters baseParameters) {
            this.a = new PKIXExtendedParameters.Builder((PKIXParameters) baseParameters).n();
            this.b = baseParameters.getMaxPathLength();
        }

        public Builder(PKIXExtendedParameters baseParameters) {
            this.a = baseParameters;
        }

        public Builder d(Set<X509Certificate> excludedCerts) {
            this.c.addAll(excludedCerts);
            return this;
        }

        public Builder f(int maxPathLength) {
            if (maxPathLength >= -1) {
                this.b = maxPathLength;
                return this;
            }
            throw new InvalidParameterException("The maximum path length parameter can not be less than -1.");
        }

        public PKIXExtendedBuilderParameters e() {
            return new PKIXExtendedBuilderParameters(this);
        }
    }

    private PKIXExtendedBuilderParameters(Builder builder) {
        this.c = builder.a;
        this.d = Collections.unmodifiableSet(builder.c);
        this.f = builder.b;
    }

    public PKIXExtendedParameters a() {
        return this.c;
    }

    public Set b() {
        return this.d;
    }

    public int c() {
        return this.f;
    }

    public Object clone() {
        return this;
    }
}
