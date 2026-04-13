package org.spongycastle.crypto.util;

import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.digests.SHA224Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.digests.SHA3Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.digests.SHA512tDigest;

public final class DigestFactory {
    public static Digest a() {
        return new MD5Digest();
    }

    public static Digest b() {
        return new SHA1Digest();
    }

    public static Digest c() {
        return new SHA224Digest();
    }

    public static Digest d() {
        return new SHA256Digest();
    }

    public static Digest e() {
        return new SHA384Digest();
    }

    public static Digest j() {
        return new SHA512Digest();
    }

    public static Digest k() {
        return new SHA512tDigest(224);
    }

    public static Digest l() {
        return new SHA512tDigest(256);
    }

    public static Digest f() {
        return new SHA3Digest(224);
    }

    public static Digest g() {
        return new SHA3Digest(256);
    }

    public static Digest h() {
        return new SHA3Digest(384);
    }

    public static Digest i() {
        return new SHA3Digest(512);
    }
}
