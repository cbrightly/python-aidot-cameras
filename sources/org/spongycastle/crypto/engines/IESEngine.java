package org.spongycastle.crypto.engines;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.spongycastle.crypto.BasicAgreement;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.EphemeralKeyPair;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.KeyParser;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.generators.EphemeralKeyPairGenerator;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.IESParameters;
import org.spongycastle.crypto.params.IESWithCipherParameters;
import org.spongycastle.crypto.params.KDFParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.Pack;

public class IESEngine {
    BasicAgreement a;
    DerivationFunction b;
    Mac c;
    BufferedBlockCipher d;
    byte[] e;
    boolean f;
    CipherParameters g;
    CipherParameters h;
    IESParameters i;
    byte[] j;
    private EphemeralKeyPairGenerator k;
    private KeyParser l;
    private byte[] m;

    public IESEngine(BasicAgreement agree, DerivationFunction kdf, Mac mac) {
        this.a = agree;
        this.b = kdf;
        this.c = mac;
        this.e = new byte[mac.e()];
        this.d = null;
    }

    public IESEngine(BasicAgreement agree, DerivationFunction kdf, Mac mac, BufferedBlockCipher cipher) {
        this.a = agree;
        this.b = kdf;
        this.c = mac;
        this.e = new byte[mac.e()];
        this.d = cipher;
    }

    public void i(boolean forEncryption, CipherParameters privParam, CipherParameters pubParam, CipherParameters params) {
        this.f = forEncryption;
        this.g = privParam;
        this.h = pubParam;
        this.j = new byte[0];
        c(params);
    }

    public void h(AsymmetricKeyParameter publicKey, CipherParameters params, EphemeralKeyPairGenerator ephemeralKeyPairGenerator) {
        this.f = true;
        this.h = publicKey;
        this.k = ephemeralKeyPairGenerator;
        c(params);
    }

    public void g(AsymmetricKeyParameter privateKey, CipherParameters params, KeyParser publicKeyParser) {
        this.f = false;
        this.g = privateKey;
        this.l = publicKeyParser;
        c(params);
    }

    private void c(CipherParameters params) {
        if (params instanceof ParametersWithIV) {
            this.m = ((ParametersWithIV) params).a();
            this.i = (IESParameters) ((ParametersWithIV) params).b();
            return;
        }
        this.m = null;
        this.i = (IESParameters) params;
    }

    public BufferedBlockCipher d() {
        return this.d;
    }

    public Mac f() {
        return this.c;
    }

    private byte[] b(byte[] in, int inOff, int inLen) {
        int i2;
        byte[] K2;
        byte[] C;
        if (this.d == null) {
            byte[] K1 = new byte[inLen];
            K2 = new byte[(this.i.c() / 8)];
            byte[] K = new byte[(K1.length + K2.length)];
            this.b.a(K, 0, K.length);
            if (this.j.length != 0) {
                System.arraycopy(K, 0, K2, 0, K2.length);
                System.arraycopy(K, K2.length, K1, 0, K1.length);
            } else {
                System.arraycopy(K, 0, K1, 0, K1.length);
                System.arraycopy(K, inLen, K2, 0, K2.length);
            }
            C = new byte[inLen];
            for (int i3 = 0; i3 != inLen; i3++) {
                C[i3] = (byte) (in[inOff + i3] ^ K1[i3]);
            }
            i2 = inLen;
        } else {
            byte[] K12 = new byte[(((IESWithCipherParameters) this.i).d() / 8)];
            K2 = new byte[(this.i.c() / 8)];
            byte[] K3 = new byte[(K12.length + K2.length)];
            this.b.a(K3, 0, K3.length);
            System.arraycopy(K3, 0, K12, 0, K12.length);
            System.arraycopy(K3, K12.length, K2, 0, K2.length);
            if (this.m != null) {
                this.d.f(true, new ParametersWithIV(new KeyParameter(K12), this.m));
            } else {
                this.d.f(true, new KeyParameter(K12));
            }
            C = new byte[this.d.c(inLen)];
            int len = this.d.g(in, inOff, inLen, C, 0);
            i2 = len + this.d.a(C, len);
        }
        byte[] P2 = this.i.b();
        byte[] L2 = null;
        if (this.j.length != 0) {
            L2 = e(P2);
        }
        byte[] T = new byte[this.c.e()];
        this.c.a(new KeyParameter(K2));
        this.c.update(C, 0, C.length);
        if (P2 != null) {
            this.c.update(P2, 0, P2.length);
        }
        if (this.j.length != 0) {
            this.c.update(L2, 0, L2.length);
        }
        this.c.c(T, 0);
        byte[] bArr = this.j;
        byte[] Output = new byte[(bArr.length + i2 + T.length)];
        System.arraycopy(bArr, 0, Output, 0, bArr.length);
        System.arraycopy(C, 0, Output, this.j.length, i2);
        System.arraycopy(T, 0, Output, this.j.length + i2, T.length);
        return Output;
    }

    private byte[] a(byte[] in_enc, int inOff, int inLen) {
        byte[] M;
        byte[] K2;
        byte[] K1;
        int i2;
        CipherParameters cp;
        byte[] bArr = in_enc;
        int i3 = inLen;
        int len = 0;
        if (i3 >= this.j.length + this.c.e()) {
            if (this.d == null) {
                K1 = new byte[((i3 - this.j.length) - this.c.e())];
                K2 = new byte[(this.i.c() / 8)];
                byte[] K = new byte[(K1.length + K2.length)];
                this.b.a(K, 0, K.length);
                if (this.j.length != 0) {
                    System.arraycopy(K, 0, K2, 0, K2.length);
                    System.arraycopy(K, K2.length, K1, 0, K1.length);
                } else {
                    System.arraycopy(K, 0, K1, 0, K1.length);
                    System.arraycopy(K, K1.length, K2, 0, K2.length);
                }
                M = new byte[K1.length];
                for (int i4 = 0; i4 != K1.length; i4++) {
                    M[i4] = (byte) (bArr[(inOff + this.j.length) + i4] ^ K1[i4]);
                }
            } else {
                byte[] K12 = new byte[(((IESWithCipherParameters) this.i).d() / 8)];
                byte[] K22 = new byte[(this.i.c() / 8)];
                byte[] K3 = new byte[(K12.length + K22.length)];
                this.b.a(K3, 0, K3.length);
                System.arraycopy(K3, 0, K12, 0, K12.length);
                System.arraycopy(K3, K12.length, K22, 0, K22.length);
                CipherParameters cp2 = new KeyParameter(K12);
                byte[] bArr2 = this.m;
                if (bArr2 != null) {
                    cp = new ParametersWithIV(cp2, bArr2);
                } else {
                    cp = cp2;
                }
                this.d.f(false, cp);
                byte[] M2 = new byte[this.d.c((i3 - this.j.length) - this.c.e())];
                BufferedBlockCipher bufferedBlockCipher = this.d;
                byte[] bArr3 = this.j;
                int length = (i3 - bArr3.length) - this.c.e();
                len = bufferedBlockCipher.g(in_enc, inOff + bArr3.length, length, M2, 0);
                K1 = K12;
                K2 = K22;
                byte[] bArr4 = K3;
                M = M2;
            }
            byte[] P2 = this.i.b();
            byte[] L2 = null;
            if (this.j.length != 0) {
                L2 = e(P2);
            }
            int end = inOff + i3;
            byte[] T1 = Arrays.B(bArr, end - this.c.e(), end);
            byte[] T2 = new byte[T1.length];
            this.c.a(new KeyParameter(K2));
            Mac mac = this.c;
            byte[] bArr5 = this.j;
            byte[] bArr6 = K1;
            mac.update(bArr, inOff + bArr5.length, (i3 - bArr5.length) - T2.length);
            if (P2 != null) {
                i2 = 0;
                this.c.update(P2, 0, P2.length);
            } else {
                i2 = 0;
            }
            if (this.j.length != 0) {
                this.c.update(L2, i2, L2.length);
            }
            this.c.c(T2, i2);
            if (Arrays.u(T1, T2)) {
                BufferedBlockCipher bufferedBlockCipher2 = this.d;
                if (bufferedBlockCipher2 == null) {
                    return M;
                }
                return Arrays.B(M, 0, len + bufferedBlockCipher2.a(M, len));
            }
            throw new InvalidCipherTextException("invalid MAC");
        }
        throw new InvalidCipherTextException("Length of input must be greater than the MAC and V combined");
    }

    public byte[] j(byte[] in, int inOff, int inLen) {
        byte[] bArr;
        if (this.f) {
            EphemeralKeyPairGenerator ephemeralKeyPairGenerator = this.k;
            if (ephemeralKeyPairGenerator != null) {
                EphemeralKeyPair ephKeyPair = ephemeralKeyPairGenerator.a();
                this.g = ephKeyPair.b().a();
                this.j = ephKeyPair.a();
            }
        } else if (this.l != null) {
            ByteArrayInputStream bIn = new ByteArrayInputStream(in, inOff, inLen);
            try {
                this.h = this.l.a(bIn);
                this.j = Arrays.B(in, inOff, inOff + (inLen - bIn.available()));
            } catch (IOException e2) {
                throw new InvalidCipherTextException("unable to recover ephemeral public key: " + e2.getMessage(), e2);
            } catch (IllegalArgumentException e3) {
                throw new InvalidCipherTextException("unable to recover ephemeral public key: " + e3.getMessage(), e3);
            }
        }
        this.a.a(this.g);
        byte[] Z = BigIntegers.a(this.a.b(), this.a.c(this.h));
        byte[] bArr2 = this.j;
        if (bArr2.length != 0) {
            byte[] VZ = Arrays.r(bArr2, Z);
            Arrays.F(Z, (byte) 0);
            Z = VZ;
        }
        try {
            this.b.b(new KDFParameters(Z, this.i.a()));
            if (this.f) {
                bArr = b(in, inOff, inLen);
            } else {
                bArr = a(in, inOff, inLen);
            }
            return bArr;
        } finally {
            Arrays.F(Z, (byte) 0);
        }
    }

    /* access modifiers changed from: protected */
    public byte[] e(byte[] p2) {
        byte[] L2 = new byte[8];
        if (p2 != null) {
            Pack.p(((long) p2.length) * 8, L2, 0);
        }
        return L2;
    }
}
