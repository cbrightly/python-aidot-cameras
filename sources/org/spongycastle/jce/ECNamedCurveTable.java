package org.spongycastle.jce;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.ec.CustomNamedCurves;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;

public class ECNamedCurveTable {
    public static ECNamedCurveParameterSpec a(String name) {
        X9ECParameters ecP = CustomNamedCurves.h(name);
        if (ecP == null) {
            try {
                ecP = CustomNamedCurves.i(new ASN1ObjectIdentifier(name));
            } catch (IllegalArgumentException e) {
            }
            if (ecP == null && (ecP = org.spongycastle.asn1.x9.ECNamedCurveTable.b(name)) == null) {
                try {
                    ecP = org.spongycastle.asn1.x9.ECNamedCurveTable.c(new ASN1ObjectIdentifier(name));
                } catch (IllegalArgumentException e2) {
                }
            }
        }
        if (ecP == null) {
            return null;
        }
        return new ECNamedCurveParameterSpec(name, ecP.e(), ecP.f(), ecP.i(), ecP.g(), ecP.getSeed());
    }
}
