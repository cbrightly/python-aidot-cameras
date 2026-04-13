package org.spongycastle.jcajce.provider.asymmetric.util;

import java.util.HashSet;
import java.util.Set;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;

public class DESUtil {
    private static final Set<String> a;

    static {
        HashSet hashSet = new HashSet();
        a = hashSet;
        hashSet.add("DES");
        hashSet.add("DESEDE");
        hashSet.add(OIWObjectIdentifiers.e.s());
        ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.m0;
        hashSet.add(aSN1ObjectIdentifier.s());
        hashSet.add(aSN1ObjectIdentifier.s());
        hashSet.add(PKCSObjectIdentifiers.y2.s());
    }
}
