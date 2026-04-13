package org.spongycastle.pqc.jcajce.provider.xmss;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Xof;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.digests.SHAKEDigest;

public class DigestUtil {
    DigestUtil() {
    }

    static Digest a(ASN1ObjectIdentifier oid) {
        if (oid.equals(NISTObjectIdentifiers.c)) {
            return new SHA256Digest();
        }
        if (oid.equals(NISTObjectIdentifiers.e)) {
            return new SHA512Digest();
        }
        if (oid.equals(NISTObjectIdentifiers.m)) {
            return new SHAKEDigest(128);
        }
        if (oid.equals(NISTObjectIdentifiers.n)) {
            return new SHAKEDigest(256);
        }
        throw new IllegalArgumentException("unrecognized digest OID: " + oid);
    }

    public static byte[] b(Digest digest) {
        byte[] hash = new byte[c(digest)];
        if (digest instanceof Xof) {
            ((Xof) digest).j(hash, 0, hash.length);
        } else {
            digest.c(hash, 0);
        }
        return hash;
    }

    public static int c(Digest digest) {
        if (digest instanceof Xof) {
            return digest.e() * 2;
        }
        return digest.e();
    }

    public static String d(ASN1ObjectIdentifier treeDigest) {
        if (treeDigest.equals(NISTObjectIdentifiers.c)) {
            return "SHA256";
        }
        if (treeDigest.equals(NISTObjectIdentifiers.e)) {
            return "SHA512";
        }
        if (treeDigest.equals(NISTObjectIdentifiers.m)) {
            return "SHAKE128";
        }
        if (treeDigest.equals(NISTObjectIdentifiers.n)) {
            return "SHAKE256";
        }
        throw new IllegalArgumentException("unrecognized digest OID: " + treeDigest);
    }
}
