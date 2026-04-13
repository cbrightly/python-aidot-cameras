package org.spongycastle.crypto.params;

public class DHKeyParameters extends AsymmetricKeyParameter {
    private DHParameters d;

    protected DHKeyParameters(boolean isPrivate, DHParameters params) {
        super(isPrivate);
        this.d = params;
    }

    public DHParameters b() {
        return this.d;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DHKeyParameters)) {
            return false;
        }
        DHKeyParameters dhKey = (DHKeyParameters) obj;
        DHParameters dHParameters = this.d;
        if (dHParameters != null) {
            return dHParameters.equals(dhKey.b());
        }
        if (dhKey.b() == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int code = a() ^ 1;
        DHParameters dHParameters = this.d;
        return dHParameters != null ? (int) (code ^ dHParameters.hashCode()) : (int) code;
    }
}
