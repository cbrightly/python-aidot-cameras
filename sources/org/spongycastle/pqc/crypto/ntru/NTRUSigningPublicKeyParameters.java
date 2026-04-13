package org.spongycastle.pqc.crypto.ntru;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.pqc.math.ntru.polynomial.IntegerPolynomial;

public class NTRUSigningPublicKeyParameters extends AsymmetricKeyParameter {
    private NTRUSigningParameters d;
    public IntegerPolynomial f;

    public NTRUSigningPublicKeyParameters(IntegerPolynomial h, NTRUSigningParameters params) {
        super(false);
        this.f = h;
        this.d = params;
    }

    public int hashCode() {
        int i = 1 * 31;
        IntegerPolynomial integerPolynomial = this.f;
        int i2 = 0;
        int result = (i + (integerPolynomial == null ? 0 : integerPolynomial.hashCode())) * 31;
        NTRUSigningParameters nTRUSigningParameters = this.d;
        if (nTRUSigningParameters != null) {
            i2 = nTRUSigningParameters.hashCode();
        }
        return result + i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NTRUSigningPublicKeyParameters other = (NTRUSigningPublicKeyParameters) obj;
        IntegerPolynomial integerPolynomial = this.f;
        if (integerPolynomial == null) {
            if (other.f != null) {
                return false;
            }
        } else if (!integerPolynomial.equals(other.f)) {
            return false;
        }
        NTRUSigningParameters nTRUSigningParameters = this.d;
        if (nTRUSigningParameters == null) {
            if (other.d != null) {
                return false;
            }
        } else if (!nTRUSigningParameters.equals(other.d)) {
            return false;
        }
        return true;
    }
}
