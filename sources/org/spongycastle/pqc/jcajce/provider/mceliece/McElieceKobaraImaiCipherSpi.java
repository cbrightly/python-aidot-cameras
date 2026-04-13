package org.spongycastle.pqc.jcajce.provider.mceliece;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
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
import org.spongycastle.pqc.crypto.mceliece.McElieceKobaraImaiCipher;
import org.spongycastle.pqc.jcajce.provider.util.AsymmetricHybridCipher;

public class McElieceKobaraImaiCipherSpi extends AsymmetricHybridCipher implements PKCSObjectIdentifiers, X509ObjectIdentifiers {
    private Digest d;
    private McElieceKobaraImaiCipher f;
    private ByteArrayOutputStream q;

    public McElieceKobaraImaiCipherSpi() {
        this.q = new ByteArrayOutputStream();
        this.q = new ByteArrayOutputStream();
    }

    protected McElieceKobaraImaiCipherSpi(Digest digest, McElieceKobaraImaiCipher cipher) {
        this.q = new ByteArrayOutputStream();
        this.d = digest;
        this.f = cipher;
        this.q = new ByteArrayOutputStream();
    }

    public byte[] l(byte[] input, int inOff, int inLen) {
        this.q.write(input, inOff, inLen);
        return new byte[0];
    }

    public byte[] b(byte[] input, int inOff, int inLen) {
        l(input, inOff, inLen);
        int i = this.c;
        if (i == 1) {
            return this.f.f(q());
        }
        if (i == 2) {
            try {
                byte[] inputOfDecr = this.q.toByteArray();
                this.q.reset();
                return r(this.f.e(inputOfDecr));
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
        this.q.reset();
        CipherParameters param = new ParametersWithRandom(McElieceCCA2KeysToParams.generatePublicKeyParameter((PublicKey) key), sr);
        this.d.reset();
        this.f.b(true, param);
    }

    /* access modifiers changed from: protected */
    public void o(Key key, AlgorithmParameterSpec params) {
        this.q.reset();
        CipherParameters param = McElieceCCA2KeysToParams.generatePrivateKeyParameter((PrivateKey) key);
        this.d.reset();
        this.f.b(false, param);
    }

    public int e(Key key) {
        if (key instanceof PublicKey) {
            return this.f.a((McElieceCCA2KeyParameters) McElieceCCA2KeysToParams.generatePublicKeyParameter((PublicKey) key));
        } else if (key instanceof PrivateKey) {
            return this.f.a((McElieceCCA2KeyParameters) McElieceCCA2KeysToParams.generatePrivateKeyParameter((PrivateKey) key));
        } else {
            throw new InvalidKeyException();
        }
    }

    private byte[] q() {
        this.q.write(1);
        byte[] result = this.q.toByteArray();
        this.q.reset();
        return result;
    }

    private byte[] r(byte[] pmBytes) {
        int index = pmBytes.length - 1;
        while (index >= 0 && pmBytes[index] == 0) {
            index--;
        }
        if (pmBytes[index] == 1) {
            byte[] mBytes = new byte[index];
            System.arraycopy(pmBytes, 0, mBytes, 0, index);
            return mBytes;
        }
        throw new BadPaddingException("invalid ciphertext");
    }

    public static class McElieceKobaraImai extends McElieceKobaraImaiCipherSpi {
        public McElieceKobaraImai() {
            super(DigestFactory.b(), new McElieceKobaraImaiCipher());
        }
    }

    public static class McElieceKobaraImai224 extends McElieceKobaraImaiCipherSpi {
        public McElieceKobaraImai224() {
            super(DigestFactory.c(), new McElieceKobaraImaiCipher());
        }
    }

    public static class McElieceKobaraImai256 extends McElieceKobaraImaiCipherSpi {
        public McElieceKobaraImai256() {
            super(DigestFactory.d(), new McElieceKobaraImaiCipher());
        }
    }

    public static class McElieceKobaraImai384 extends McElieceKobaraImaiCipherSpi {
        public McElieceKobaraImai384() {
            super(DigestFactory.e(), new McElieceKobaraImaiCipher());
        }
    }

    public static class McElieceKobaraImai512 extends McElieceKobaraImaiCipherSpi {
        public McElieceKobaraImai512() {
            super(DigestFactory.j(), new McElieceKobaraImaiCipher());
        }
    }
}
