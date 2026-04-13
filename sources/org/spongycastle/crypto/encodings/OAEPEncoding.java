package org.spongycastle.crypto.encodings;

import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.Arrays;

public class OAEPEncoding implements AsymmetricBlockCipher {
    private byte[] a;
    private Digest b;
    private AsymmetricBlockCipher c;
    private SecureRandom d;
    private boolean e;

    public OAEPEncoding(AsymmetricBlockCipher cipher, Digest hash, byte[] encodingParams) {
        this(cipher, hash, hash, encodingParams);
    }

    public OAEPEncoding(AsymmetricBlockCipher cipher, Digest hash, Digest mgf1Hash, byte[] encodingParams) {
        this.c = cipher;
        this.b = mgf1Hash;
        this.a = new byte[hash.e()];
        hash.reset();
        if (encodingParams != null) {
            hash.update(encodingParams, 0, encodingParams.length);
        }
        hash.c(this.a, 0);
    }

    public void a(boolean forEncryption, CipherParameters param) {
        if (param instanceof ParametersWithRandom) {
            this.d = ((ParametersWithRandom) param).b();
        } else {
            this.d = new SecureRandom();
        }
        this.c.a(forEncryption, param);
        this.e = forEncryption;
    }

    public int c() {
        int baseBlockSize = this.c.c();
        if (this.e) {
            return (baseBlockSize - 1) - (this.a.length * 2);
        }
        return baseBlockSize;
    }

    public int b() {
        int baseBlockSize = this.c.b();
        if (this.e) {
            return baseBlockSize;
        }
        return (baseBlockSize - 1) - (this.a.length * 2);
    }

    public byte[] d(byte[] in, int inOff, int inLen) {
        if (this.e) {
            return g(in, inOff, inLen);
        }
        return f(in, inOff, inLen);
    }

    public byte[] g(byte[] in, int inOff, int inLen) {
        if (inLen <= c()) {
            byte[] block = new byte[(c() + 1 + (this.a.length * 2))];
            System.arraycopy(in, inOff, block, block.length - inLen, inLen);
            block[(block.length - inLen) - 1] = 1;
            byte[] bArr = this.a;
            System.arraycopy(bArr, 0, block, bArr.length, bArr.length);
            byte[] seed = new byte[this.a.length];
            this.d.nextBytes(seed);
            byte[] mask = h(seed, 0, seed.length, block.length - this.a.length);
            for (int i = this.a.length; i != block.length; i++) {
                block[i] = (byte) (block[i] ^ mask[i - this.a.length]);
            }
            System.arraycopy(seed, 0, block, 0, this.a.length);
            byte[] bArr2 = this.a;
            byte[] mask2 = h(block, bArr2.length, block.length - bArr2.length, bArr2.length);
            for (int i2 = 0; i2 != this.a.length; i2++) {
                block[i2] = (byte) (block[i2] ^ mask2[i2]);
            }
            return this.c.d(block, 0, block.length);
        }
        throw new DataLengthException("input data too long");
    }

    public byte[] f(byte[] in, int inOff, int inLen) {
        byte[] bArr;
        byte[] bArr2;
        byte[] data = this.c.d(in, inOff, inLen);
        byte[] block = new byte[this.c.b()];
        System.arraycopy(data, 0, block, block.length - data.length, data.length);
        int length = block.length;
        byte[] bArr3 = this.a;
        boolean shortData = length < (bArr3.length * 2) + 1;
        byte[] mask = h(block, bArr3.length, block.length - bArr3.length, bArr3.length);
        int i = 0;
        while (true) {
            bArr = this.a;
            if (i == bArr.length) {
                break;
            }
            block[i] = (byte) (block[i] ^ mask[i]);
            i++;
        }
        byte[] mask2 = h(block, 0, bArr.length, block.length - bArr.length);
        for (int i2 = this.a.length; i2 != block.length; i2++) {
            block[i2] = (byte) (block[i2] ^ mask2[i2 - this.a.length]);
        }
        boolean defHashWrong = false;
        int i3 = 0;
        while (true) {
            bArr2 = this.a;
            if (i3 == bArr2.length) {
                break;
            }
            if (bArr2[i3] != block[bArr2.length + i3]) {
                defHashWrong = true;
            }
            i3++;
        }
        int start = block.length;
        for (int index = bArr2.length * 2; index != block.length; index++) {
            if ((block[index] != 0) && (start == block.length)) {
                start = index;
            }
        }
        boolean z = start > block.length - 1;
        boolean z2 = block[start] != 1;
        int start2 = start + 1;
        if (!(defHashWrong | shortData) && !(z | z2)) {
            byte[] output = new byte[(block.length - start2)];
            System.arraycopy(block, start2, output, 0, output.length);
            return output;
        }
        Arrays.F(block, (byte) 0);
        throw new InvalidCipherTextException("data wrong");
    }

    private void e(int i, byte[] sp) {
        sp[0] = (byte) (i >>> 24);
        sp[1] = (byte) (i >>> 16);
        sp[2] = (byte) (i >>> 8);
        sp[3] = (byte) (i >>> 0);
    }

    private byte[] h(byte[] Z, int zOff, int zLen, int length) {
        byte[] mask = new byte[length];
        byte[] hashBuf = new byte[this.b.e()];
        byte[] C = new byte[4];
        int counter = 0;
        this.b.reset();
        while (counter < length / hashBuf.length) {
            e(counter, C);
            this.b.update(Z, zOff, zLen);
            this.b.update(C, 0, C.length);
            this.b.c(hashBuf, 0);
            System.arraycopy(hashBuf, 0, mask, hashBuf.length * counter, hashBuf.length);
            counter++;
        }
        if (hashBuf.length * counter < length) {
            e(counter, C);
            this.b.update(Z, zOff, zLen);
            this.b.update(C, 0, C.length);
            this.b.c(hashBuf, 0);
            System.arraycopy(hashBuf, 0, mask, hashBuf.length * counter, mask.length - (hashBuf.length * counter));
        }
        return mask;
    }
}
