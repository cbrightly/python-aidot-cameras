package org.spongycastle.pqc.crypto.ntru;

import org.spongycastle.pqc.math.ntru.polynomial.IntegerPolynomial;

public class NTRUEncryptionPublicKeyParameters extends NTRUEncryptionKeyParameters {
    public IntegerPolynomial f;

    public NTRUEncryptionPublicKeyParameters(IntegerPolynomial h, NTRUEncryptionParameters params) {
        super(false, params);
        this.f = h;
    }

    public int hashCode() {
        int i = 1 * 31;
        IntegerPolynomial integerPolynomial = this.f;
        int i2 = 0;
        int result = (i + (integerPolynomial == null ? 0 : integerPolynomial.hashCode())) * 31;
        NTRUEncryptionParameters nTRUEncryptionParameters = this.d;
        if (nTRUEncryptionParameters != null) {
            i2 = nTRUEncryptionParameters.hashCode();
        }
        return result + i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof NTRUEncryptionPublicKeyParameters)) {
            return false;
        }
        NTRUEncryptionPublicKeyParameters other = (NTRUEncryptionPublicKeyParameters) obj;
        IntegerPolynomial integerPolynomial = this.f;
        if (integerPolynomial == null) {
            if (other.f != null) {
                return false;
            }
        } else if (!integerPolynomial.equals(other.f)) {
            return false;
        }
        NTRUEncryptionParameters nTRUEncryptionParameters = this.d;
        if (nTRUEncryptionParameters == null) {
            if (other.d != null) {
                return false;
            }
        } else if (!nTRUEncryptionParameters.equals(other.d)) {
            return false;
        }
        return true;
    }
}
