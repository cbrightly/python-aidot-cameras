package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SM3Digest;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithID;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECMultiplier;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.FixedPointCombMultiplier;
import org.spongycastle.util.BigIntegers;

public class SM2Signer implements DSA, ECConstants {
    private final DSAKCalculator g = new RandomDSAKCalculator();
    private byte[] h;
    private int i;
    private ECDomainParameters j;
    private ECPoint k;
    private ECKeyParameters l;

    public void a(boolean forSigning, CipherParameters param) {
        CipherParameters baseParam;
        if (param instanceof ParametersWithID) {
            baseParam = ((ParametersWithID) param).b();
            this.h = ((ParametersWithID) param).a();
        } else {
            baseParam = param;
            this.h = new byte[0];
        }
        if (forSigning) {
            if (baseParam instanceof ParametersWithRandom) {
                ParametersWithRandom rParam = (ParametersWithRandom) baseParam;
                ECKeyParameters eCKeyParameters = (ECKeyParameters) rParam.a();
                this.l = eCKeyParameters;
                ECDomainParameters b = eCKeyParameters.b();
                this.j = b;
                this.g.a(b.d(), rParam.b());
            } else {
                ECKeyParameters eCKeyParameters2 = (ECKeyParameters) baseParam;
                this.l = eCKeyParameters2;
                ECDomainParameters b2 = eCKeyParameters2.b();
                this.j = b2;
                this.g.a(b2.d(), new SecureRandom());
            }
            this.k = this.j.b().w(((ECPrivateKeyParameters) this.l).c()).y();
        } else {
            ECKeyParameters eCKeyParameters3 = (ECKeyParameters) baseParam;
            this.l = eCKeyParameters3;
            this.j = eCKeyParameters3.b();
            this.k = ((ECPublicKeyParameters) this.l).c();
        }
        this.i = (this.j.a().t() + 7) / 8;
    }

    public BigInteger[] b(byte[] message) {
        SM3Digest digest = new SM3Digest();
        byte[] z = h(digest);
        digest.update(z, 0, z.length);
        digest.update(message, 0, message.length);
        byte[] eHash = new byte[digest.e()];
        digest.c(eHash, 0);
        BigInteger n = this.j.d();
        BigInteger e = f(eHash);
        BigInteger d = ((ECPrivateKeyParameters) this.l).c();
        ECMultiplier basePointMultiplier = g();
        while (true) {
            BigInteger k2 = this.g.b();
            BigInteger r = e.add(basePointMultiplier.a(this.j.b(), k2).y().f().t()).mod(n);
            BigInteger bigInteger = ECConstants.a;
            if (!r.equals(bigInteger) && !r.add(k2).equals(n)) {
                BigInteger s = d.add(ECConstants.b).modInverse(n).multiply(k2.subtract(r.multiply(d)).mod(n)).mod(n);
                if (!s.equals(bigInteger)) {
                    return new BigInteger[]{r, s};
                }
            }
        }
    }

    public boolean c(byte[] message, BigInteger r, BigInteger s) {
        BigInteger n = this.j.d();
        BigInteger bigInteger = ECConstants.b;
        if (r.compareTo(bigInteger) < 0 || r.compareTo(n) >= 0 || s.compareTo(bigInteger) < 0 || s.compareTo(n) >= 0) {
            return false;
        }
        ECPoint q = ((ECPublicKeyParameters) this.l).c();
        SM3Digest digest = new SM3Digest();
        byte[] z = h(digest);
        digest.update(z, 0, z.length);
        digest.update(message, 0, message.length);
        byte[] eHash = new byte[digest.e()];
        digest.c(eHash, 0);
        BigInteger e = f(eHash);
        BigInteger t = r.add(s).mod(n);
        if (t.equals(ECConstants.a)) {
            return false;
        }
        return r.equals(e.add(this.j.b().w(s).a(q.w(t)).y().f().t()).mod(n));
    }

    private byte[] h(Digest digest) {
        e(digest, this.h);
        d(digest, this.j.a().n());
        d(digest, this.j.a().o());
        d(digest, this.j.b().f());
        d(digest, this.j.b().g());
        d(digest, this.k.f());
        d(digest, this.k.g());
        byte[] rv = new byte[digest.e()];
        digest.c(rv, 0);
        return rv;
    }

    private void e(Digest digest, byte[] userID) {
        int len = userID.length * 8;
        digest.d((byte) ((len >> 8) & 255));
        digest.d((byte) (len & 255));
        digest.update(userID, 0, userID.length);
    }

    private void d(Digest digest, ECFieldElement v) {
        byte[] p = BigIntegers.a(this.i, v.t());
        digest.update(p, 0, p.length);
    }

    /* access modifiers changed from: protected */
    public ECMultiplier g() {
        return new FixedPointCombMultiplier();
    }

    /* access modifiers changed from: protected */
    public BigInteger f(byte[] message) {
        return new BigInteger(1, message);
    }
}
