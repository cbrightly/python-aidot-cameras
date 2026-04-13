package org.spongycastle.crypto.signers;

import java.io.IOException;
import java.util.Hashtable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSABlindedEngine;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.Arrays;

public class RSADigestSigner implements Signer {
    private static final Hashtable a;
    private final AsymmetricBlockCipher b = new PKCS1Encoding(new RSABlindedEngine());
    private final AlgorithmIdentifier c;
    private final Digest d;
    private boolean e;

    static {
        Hashtable hashtable = new Hashtable();
        a = hashtable;
        hashtable.put("RIPEMD128", TeleTrusTObjectIdentifiers.c);
        hashtable.put("RIPEMD160", TeleTrusTObjectIdentifiers.b);
        hashtable.put("RIPEMD256", TeleTrusTObjectIdentifiers.d);
        hashtable.put("SHA-1", X509ObjectIdentifiers.L2);
        hashtable.put("SHA-224", NISTObjectIdentifiers.f);
        hashtable.put("SHA-256", NISTObjectIdentifiers.c);
        hashtable.put("SHA-384", NISTObjectIdentifiers.d);
        hashtable.put("SHA-512", NISTObjectIdentifiers.e);
        hashtable.put("SHA-512/224", NISTObjectIdentifiers.g);
        hashtable.put("SHA-512/256", NISTObjectIdentifiers.h);
        hashtable.put("SHA3-224", NISTObjectIdentifiers.i);
        hashtable.put("SHA3-256", NISTObjectIdentifiers.j);
        hashtable.put("SHA3-384", NISTObjectIdentifiers.k);
        hashtable.put("SHA3-512", NISTObjectIdentifiers.l);
        hashtable.put("MD2", PKCSObjectIdentifiers.r0);
        hashtable.put("MD4", PKCSObjectIdentifiers.s0);
        hashtable.put("MD5", PKCSObjectIdentifiers.t0);
    }

    public RSADigestSigner(Digest digest, ASN1ObjectIdentifier digestOid) {
        this.d = digest;
        this.c = new AlgorithmIdentifier(digestOid, DERNull.c);
    }

    public void a(boolean forSigning, CipherParameters parameters) {
        AsymmetricKeyParameter k;
        this.e = forSigning;
        if (parameters instanceof ParametersWithRandom) {
            k = (AsymmetricKeyParameter) ((ParametersWithRandom) parameters).a();
        } else {
            k = (AsymmetricKeyParameter) parameters;
        }
        if (forSigning && !k.a()) {
            throw new IllegalArgumentException("signing requires private key");
        } else if (forSigning || !k.a()) {
            f();
            this.b.a(forSigning, parameters);
        } else {
            throw new IllegalArgumentException("verification requires public key");
        }
    }

    public void d(byte input) {
        this.d.d(input);
    }

    public void update(byte[] input, int inOff, int length) {
        this.d.update(input, inOff, length);
    }

    public byte[] c() {
        if (this.e) {
            byte[] hash = new byte[this.d.e()];
            this.d.c(hash, 0);
            try {
                byte[] data = e(hash);
                return this.b.d(data, 0, data.length);
            } catch (IOException e2) {
                throw new CryptoException("unable to encode signature: " + e2.getMessage(), e2);
            }
        } else {
            throw new IllegalStateException("RSADigestSigner not initialised for signature generation.");
        }
    }

    public boolean b(byte[] signature) {
        if (!this.e) {
            byte[] hash = new byte[this.d.e()];
            this.d.c(hash, 0);
            try {
                byte[] sig = this.b.d(signature, 0, signature.length);
                byte[] expected = e(hash);
                if (sig.length == expected.length) {
                    return Arrays.u(sig, expected);
                }
                if (sig.length == expected.length - 2) {
                    int sigOffset = (sig.length - hash.length) - 2;
                    int expectedOffset = (expected.length - hash.length) - 2;
                    expected[1] = (byte) (expected[1] - 2);
                    expected[3] = (byte) (expected[3] - 2);
                    int nonEqual = 0;
                    for (int i = 0; i < hash.length; i++) {
                        nonEqual |= sig[sigOffset + i] ^ expected[expectedOffset + i];
                    }
                    for (int i2 = 0; i2 < sigOffset; i2++) {
                        nonEqual |= sig[i2] ^ expected[i2];
                    }
                    if (nonEqual == 0) {
                        return true;
                    }
                    return false;
                }
                Arrays.u(expected, expected);
                return false;
            } catch (Exception e2) {
                return false;
            }
        } else {
            throw new IllegalStateException("RSADigestSigner not initialised for verification");
        }
    }

    public void f() {
        this.d.reset();
    }

    private byte[] e(byte[] hash) {
        return new DigestInfo(this.c, hash).getEncoded("DER");
    }
}
