package org.spongycastle.crypto.params;

public class CramerShoupKeyParameters extends AsymmetricKeyParameter {
    private CramerShoupParameters d;

    protected CramerShoupKeyParameters(boolean isPrivate, CramerShoupParameters params) {
        super(isPrivate);
        this.d = params;
    }

    public CramerShoupParameters b() {
        return this.d;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CramerShoupKeyParameters)) {
            return false;
        }
        CramerShoupKeyParameters csKey = (CramerShoupKeyParameters) obj;
        CramerShoupParameters cramerShoupParameters = this.d;
        if (cramerShoupParameters != null) {
            return cramerShoupParameters.equals(csKey.b());
        }
        if (csKey.b() == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int code = a() ^ 1;
        CramerShoupParameters cramerShoupParameters = this.d;
        return cramerShoupParameters != null ? (int) (code ^ cramerShoupParameters.hashCode()) : (int) code;
    }
}
