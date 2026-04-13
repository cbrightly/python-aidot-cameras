package org.spongycastle.pqc.jcajce.provider.mceliece;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.pqc.crypto.mceliece.McElieceCCA2KeyParameters;
import org.spongycastle.pqc.crypto.mceliece.McElieceFujisakiCipher;
import org.spongycastle.pqc.jcajce.provider.util.AsymmetricHybridCipher;

public class McElieceFujisakiCipherSpi extends AsymmetricHybridCipher implements PKCSObjectIdentifiers, X509ObjectIdentifiers {
    private Digest d;
    private McElieceFujisakiCipher f;
    private ByteArrayOutputStream q = new ByteArrayOutputStream();

    protected McElieceFujisakiCipherSpi(Digest digest, McElieceFujisakiCipher cipher) {
        this.d = digest;
        this.f = cipher;
    }

    public byte[] l(byte[] input, int inOff, int inLen) {
        this.q.write(input, inOff, inLen);
        return new byte[0];
    }

    public byte[] b(byte[] input, int inOff, int inLen) {
        l(input, inOff, inLen);
        byte[] data = this.q.toByteArray();
        this.q.reset();
        int i = this.c;
        if (i == 1) {
            return this.f.f(data);
        }
        if (i == 2) {
            try {
                return this.f.e(data);
            } catch (InvalidCipherTextException e) {
                throw new BadPaddingException(e.getMessage());
            }
        } else {
            throw new IllegalStateException("unknown mode in doFinal");
        }
    }

    /* access modifiers changed from: protected */
    public int n(int inLen) {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int m(int inLen) {
        return 0;
    }

    /* access modifiers changed from: protected */
    public void p(Key key, AlgorithmParameterSpec params, SecureRandom sr) {
        CipherParameters param = new ParametersWithRandom(McElieceCCA2KeysToParams.generatePublicKeyParameter((PublicKey) key), sr);
        this.d.reset();
        this.f.b(true, param);
    }

    /* access modifiers changed from: protected */
    public void o(Key key, AlgorithmParameterSpec params) {
        CipherParameters param = McElieceCCA2KeysToParams.generatePrivateKeyParameter((PrivateKey) key);
        this.d.reset();
        this.f.b(false, param);
    }

    public int e(Key key) {
        McElieceCCA2KeyParameters mcElieceCCA2KeyParameters;
        if (key instanceof PublicKey) {
            mcElieceCCA2KeyParameters = (McElieceCCA2KeyParameters) McElieceCCA2KeysToParams.generatePublicKeyParameter((PublicKey) key);
        } else {
            mcElieceCCA2KeyParameters = (McElieceCCA2KeyParameters) McElieceCCA2KeysToParams.generatePrivateKeyParameter((PrivateKey) key);
        }
        return this.f.a(mcElieceCCA2KeyParameters);
    }

    public static class McElieceFujisaki extends McElieceFujisakiCipherSpi {
        public McElieceFujisaki() {
            super(DigestFactory.b(), new McElieceFujisakiCipher());
        }
    }
}
