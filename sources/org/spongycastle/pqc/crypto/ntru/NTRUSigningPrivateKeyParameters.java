package org.spongycastle.pqc.crypto.ntru;

import java.util.ArrayList;
import java.util.List;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.pqc.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.Polynomial;

public class NTRUSigningPrivateKeyParameters extends AsymmetricKeyParameter {
    private List<Basis> d;
    private NTRUSigningPublicKeyParameters f;

    public NTRUSigningPrivateKeyParameters(List<Basis> bases, NTRUSigningPublicKeyParameters publicKey) {
        super(true);
        this.d = new ArrayList(bases);
        this.f = publicKey;
    }

    public int hashCode() {
        int result = 1 * 31;
        List<Basis> list = this.d;
        if (list == null) {
            return result;
        }
        int result2 = result + list.hashCode();
        for (Basis basis : this.d) {
            result2 += basis.hashCode();
        }
        return result2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NTRUSigningPrivateKeyParameters other = (NTRUSigningPrivateKeyParameters) obj;
        List<Basis> list = this.d;
        if ((list == null) != (other.d == null)) {
            return false;
        }
        if (list == null) {
            return true;
        }
        if (list.size() != other.d.size()) {
            return false;
        }
        for (int i = 0; i < this.d.size(); i++) {
            Basis basis1 = this.d.get(i);
            Basis basis2 = other.d.get(i);
            if (!basis1.a.equals(basis2.a) || !basis1.b.equals(basis2.b)) {
                return false;
            }
            if ((i != 0 && !basis1.c.equals(basis2.c)) || !basis1.d.equals(basis2.d)) {
                return false;
            }
        }
        return true;
    }

    public static class Basis {
        public Polynomial a;
        public Polynomial b;
        public IntegerPolynomial c;
        NTRUSigningKeyGenerationParameters d;

        protected Basis(Polynomial f, Polynomial fPrime, IntegerPolynomial h, NTRUSigningKeyGenerationParameters params) {
            this.a = f;
            this.b = fPrime;
            this.c = h;
            this.d = params;
        }

        public int hashCode() {
            int i = 1 * 31;
            Polynomial polynomial = this.a;
            int i2 = 0;
            int result = (i + (polynomial == null ? 0 : polynomial.hashCode())) * 31;
            Polynomial polynomial2 = this.b;
            int result2 = (result + (polynomial2 == null ? 0 : polynomial2.hashCode())) * 31;
            IntegerPolynomial integerPolynomial = this.c;
            int result3 = (result2 + (integerPolynomial == null ? 0 : integerPolynomial.hashCode())) * 31;
            NTRUSigningKeyGenerationParameters nTRUSigningKeyGenerationParameters = this.d;
            if (nTRUSigningKeyGenerationParameters != null) {
                i2 = nTRUSigningKeyGenerationParameters.hashCode();
            }
            return result3 + i2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof Basis)) {
                return false;
            }
            Basis other = (Basis) obj;
            Polynomial polynomial = this.a;
            if (polynomial == null) {
                if (other.a != null) {
                    return false;
                }
            } else if (!polynomial.equals(other.a)) {
                return false;
            }
            Polynomial polynomial2 = this.b;
            if (polynomial2 == null) {
                if (other.b != null) {
                    return false;
                }
            } else if (!polynomial2.equals(other.b)) {
                return false;
            }
            IntegerPolynomial integerPolynomial = this.c;
            if (integerPolynomial == null) {
                if (other.c != null) {
                    return false;
                }
            } else if (!integerPolynomial.equals(other.c)) {
                return false;
            }
            NTRUSigningKeyGenerationParameters nTRUSigningKeyGenerationParameters = this.d;
            if (nTRUSigningKeyGenerationParameters == null) {
                if (other.d != null) {
                    return false;
                }
            } else if (!nTRUSigningKeyGenerationParameters.equals(other.d)) {
                return false;
            }
            return true;
        }
    }
}
