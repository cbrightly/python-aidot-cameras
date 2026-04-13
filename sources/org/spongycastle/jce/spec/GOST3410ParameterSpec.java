package org.spongycastle.jce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.GOST3410NamedParameters;
import org.spongycastle.asn1.cryptopro.GOST3410ParamSetParameters;
import org.spongycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.spongycastle.jce.interfaces.GOST3410Params;

public class GOST3410ParameterSpec implements AlgorithmParameterSpec, GOST3410Params {
    private GOST3410PublicKeyParameterSetSpec a;
    private String b;
    private String c;
    private String d;

    public GOST3410ParameterSpec(String keyParamSetID, String digestParamSetOID, String encryptionParamSetOID) {
        GOST3410ParamSetParameters ecP = null;
        try {
            ecP = GOST3410NamedParameters.a(new ASN1ObjectIdentifier(keyParamSetID));
        } catch (IllegalArgumentException e) {
            ASN1ObjectIdentifier oid = GOST3410NamedParameters.b(keyParamSetID);
            if (oid != null) {
                keyParamSetID = oid.s();
                ecP = GOST3410NamedParameters.a(oid);
            }
        }
        if (ecP != null) {
            this.a = new GOST3410PublicKeyParameterSetSpec(ecP.f(), ecP.g(), ecP.e());
            this.b = keyParamSetID;
            this.c = digestParamSetOID;
            this.d = encryptionParamSetOID;
            return;
        }
        throw new IllegalArgumentException("no key parameter set for passed in name/OID.");
    }

    public GOST3410ParameterSpec(String keyParamSetID, String digestParamSetOID) {
        this(keyParamSetID, digestParamSetOID, (String) null);
    }

    public GOST3410ParameterSpec(String keyParamSetID) {
        this(keyParamSetID, CryptoProObjectIdentifiers.p.s(), (String) null);
    }

    public GOST3410ParameterSpec(GOST3410PublicKeyParameterSetSpec spec) {
        this.a = spec;
        this.c = CryptoProObjectIdentifiers.p.s();
        this.d = null;
    }

    public String getPublicKeyParamSetOID() {
        return this.b;
    }

    public GOST3410PublicKeyParameterSetSpec getPublicKeyParameters() {
        return this.a;
    }

    public String getDigestParamSetOID() {
        return this.c;
    }

    public String getEncryptionParamSetOID() {
        return this.d;
    }

    public boolean equals(Object o) {
        if (!(o instanceof GOST3410ParameterSpec)) {
            return false;
        }
        GOST3410ParameterSpec other = (GOST3410ParameterSpec) o;
        if (!this.a.equals(other.a) || !this.c.equals(other.c)) {
            return false;
        }
        String str = this.d;
        String str2 = other.d;
        if (str == str2 || (str != null && str.equals(str2))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() ^ this.c.hashCode();
        String str = this.d;
        return hashCode ^ (str != null ? str.hashCode() : 0);
    }

    public static GOST3410ParameterSpec a(GOST3410PublicKeyAlgParameters params) {
        if (params.f() != null) {
            return new GOST3410ParameterSpec(params.h().s(), params.e().s(), params.f().s());
        }
        return new GOST3410ParameterSpec(params.h().s(), params.e().s());
    }
}
