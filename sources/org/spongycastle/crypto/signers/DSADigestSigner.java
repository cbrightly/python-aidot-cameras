package org.spongycastle.crypto.signers;

import java.io.IOException;
import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;

public class DSADigestSigner implements Signer {
    private final Digest a;
    private final DSA b;
    private boolean c;

    public DSADigestSigner(DSA signer, Digest digest) {
        this.a = digest;
        this.b = signer;
    }

    public void a(boolean forSigning, CipherParameters parameters) {
        AsymmetricKeyParameter k;
        this.c = forSigning;
        if (parameters instanceof ParametersWithRandom) {
            k = (AsymmetricKeyParameter) ((ParametersWithRandom) parameters).a();
        } else {
            k = (AsymmetricKeyParameter) parameters;
        }
        if (forSigning && !k.a()) {
            throw new IllegalArgumentException("Signing Requires Private Key.");
        } else if (forSigning || !k.a()) {
            g();
            this.b.a(forSigning, parameters);
        } else {
            throw new IllegalArgumentException("Verification Requires Public Key.");
        }
    }

    public void d(byte input) {
        this.a.d(input);
    }

    public void update(byte[] input, int inOff, int length) {
        this.a.update(input, inOff, length);
    }

    public byte[] c() {
        if (this.c) {
            byte[] hash = new byte[this.a.e()];
            this.a.c(hash, 0);
            BigInteger[] sig = this.b.b(hash);
            try {
                return f(sig[0], sig[1]);
            } catch (IOException e) {
                throw new IllegalStateException("unable to encode signature");
            }
        } else {
            throw new IllegalStateException("DSADigestSigner not initialised for signature generation.");
        }
    }

    public boolean b(byte[] signature) {
        if (!this.c) {
            byte[] hash = new byte[this.a.e()];
            this.a.c(hash, 0);
            try {
                BigInteger[] sig = e(signature);
                return this.b.c(hash, sig[0], sig[1]);
            } catch (IOException e) {
                return false;
            }
        } else {
            throw new IllegalStateException("DSADigestSigner not initialised for verification");
        }
    }

    public void g() {
        this.a.reset();
    }

    private byte[] f(BigInteger r, BigInteger s) {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer(r));
        v.a(new ASN1Integer(s));
        return new DERSequence(v).getEncoded("DER");
    }

    private BigInteger[] e(byte[] encoding) {
        ASN1Sequence s = (ASN1Sequence) ASN1Primitive.h(encoding);
        return new BigInteger[]{((ASN1Integer) s.r(0)).r(), ((ASN1Integer) s.r(1)).r()};
    }
}
