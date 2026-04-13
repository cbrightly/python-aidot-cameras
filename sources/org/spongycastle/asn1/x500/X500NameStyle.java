package org.spongycastle.asn1.x500;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public interface X500NameStyle {
    String a(X500Name x500Name);

    RDN[] b(String str);

    ASN1ObjectIdentifier c(String str);

    boolean d(X500Name x500Name, X500Name x500Name2);

    ASN1Encodable e(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str);

    int f(X500Name x500Name);
}
