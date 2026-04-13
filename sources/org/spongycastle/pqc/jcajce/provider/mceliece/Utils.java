package org.spongycastle.pqc.jcajce.provider.mceliece;

import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.util.DigestFactory;

public class Utils {
    Utils() {
    }

    static AlgorithmIdentifier a(String digestName) {
        if (digestName.equals("SHA-1")) {
            return new AlgorithmIdentifier(OIWObjectIdentifiers.i, DERNull.c);
        }
        if (digestName.equals("SHA-224")) {
            return new AlgorithmIdentifier(NISTObjectIdentifiers.f, DERNull.c);
        }
        if (digestName.equals("SHA-256")) {
            return new AlgorithmIdentifier(NISTObjectIdentifiers.c, DERNull.c);
        }
        if (digestName.equals("SHA-384")) {
            return new AlgorithmIdentifier(NISTObjectIdentifiers.d, DERNull.c);
        }
        if (digestName.equals("SHA-512")) {
            return new AlgorithmIdentifier(NISTObjectIdentifiers.e, DERNull.c);
        }
        throw new IllegalArgumentException("unrecognised digest algorithm: " + digestName);
    }

    static Digest b(AlgorithmIdentifier digest) {
        if (digest.e().equals(OIWObjectIdentifiers.i)) {
            return DigestFactory.b();
        }
        if (digest.e().equals(NISTObjectIdentifiers.f)) {
            return DigestFactory.c();
        }
        if (digest.e().equals(NISTObjectIdentifiers.c)) {
            return DigestFactory.d();
        }
        if (digest.e().equals(NISTObjectIdentifiers.d)) {
            return DigestFactory.e();
        }
        if (digest.e().equals(NISTObjectIdentifiers.e)) {
            return DigestFactory.j();
        }
        throw new IllegalArgumentException("unrecognised OID in digest algorithm identifier: " + digest.e());
    }
}
