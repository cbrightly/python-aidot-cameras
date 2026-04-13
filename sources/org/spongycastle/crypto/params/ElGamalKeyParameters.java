package org.spongycastle.crypto.params;

public class ElGamalKeyParameters extends AsymmetricKeyParameter {
    private ElGamalParameters d;

    protected ElGamalKeyParameters(boolean isPrivate, ElGamalParameters params) {
        super(isPrivate);
        this.d = params;
    }

    public ElGamalParameters b() {
        return this.d;
    }

    public int hashCode() {
        ElGamalParameters elGamalParameters = this.d;
        if (elGamalParameters != null) {
            return elGamalParameters.hashCode();
        }
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ElGamalKeyParameters)) {
            return false;
        }
        ElGamalKeyParameters dhKey = (ElGamalKeyParameters) obj;
        ElGamalParameters elGamalParameters = this.d;
        if (elGamalParameters != null) {
            return elGamalParameters.equals(dhKey.b());
        }
        if (dhKey.b() == null) {
            return true;
        }
        return false;
    }
}
