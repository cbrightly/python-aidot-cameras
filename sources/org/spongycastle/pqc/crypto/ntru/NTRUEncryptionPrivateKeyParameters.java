package org.spongycastle.pqc.crypto.ntru;

import org.spongycastle.pqc.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.Polynomial;

public class NTRUEncryptionPrivateKeyParameters extends NTRUEncryptionKeyParameters {
    public Polynomial f;
    public IntegerPolynomial q;
    public IntegerPolynomial x;

    public NTRUEncryptionPrivateKeyParameters(IntegerPolynomial h, Polynomial t, IntegerPolynomial fp, NTRUEncryptionParameters params) {
        super(true, params);
        this.x = h;
        this.f = t;
        this.q = fp;
    }

    public int hashCode() {
        int i = 1 * 31;
        NTRUEncryptionParameters nTRUEncryptionParameters = this.d;
        int i2 = 0;
        int result = (i + (nTRUEncryptionParameters == null ? 0 : nTRUEncryptionParameters.hashCode())) * 31;
        Polynomial polynomial = this.f;
        int result2 = (result + (polynomial == null ? 0 : polynomial.hashCode())) * 31;
        IntegerPolynomial integerPolynomial = this.x;
        if (integerPolynomial != null) {
            i2 = integerPolynomial.hashCode();
        }
        return result2 + i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof NTRUEncryptionPrivateKeyParameters)) {
            return false;
        }
        NTRUEncryptionPrivateKeyParameters other = (NTRUEncryptionPrivateKeyParameters) obj;
        NTRUEncryptionParameters nTRUEncryptionParameters = this.d;
        if (nTRUEncryptionParameters == null) {
            if (other.d != null) {
                return false;
            }
        } else if (!nTRUEncryptionParameters.equals(other.d)) {
            return false;
        }
        Polynomial polynomial = this.f;
        if (polynomial == null) {
            if (other.f != null) {
                return false;
            }
        } else if (!polynomial.equals(other.f)) {
            return false;
        }
        if (!this.x.equals(other.x)) {
            return false;
        }
        return true;
    }
}
