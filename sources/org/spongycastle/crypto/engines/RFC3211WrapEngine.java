package org.spongycastle.crypto.engines;

import java.security.SecureRandom;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;

public class RFC3211WrapEngine implements Wrapper {
    private CBCBlockCipher a;
    private ParametersWithIV b;
    private boolean c;
    private SecureRandom d;

    public RFC3211WrapEngine(BlockCipher engine) {
        this.a = new CBCBlockCipher(engine);
    }

    public void a(boolean forWrapping, CipherParameters param) {
        this.c = forWrapping;
        if (param instanceof ParametersWithRandom) {
            ParametersWithRandom p = (ParametersWithRandom) param;
            this.d = p.b();
            this.b = (ParametersWithIV) p.a();
            return;
        }
        if (forWrapping) {
            this.d = new SecureRandom();
        }
        this.b = (ParametersWithIV) param;
    }

    public String b() {
        return this.a.g().b() + "/RFC3211Wrap";
    }

    public byte[] wrap(byte[] in, int inOff, int inLen) {
        byte[] cekBlock;
        if (this.c) {
            this.a.a(true, this.b);
            int blockSize = this.a.c();
            if (inLen + 4 < blockSize * 2) {
                cekBlock = new byte[(blockSize * 2)];
            } else {
                cekBlock = new byte[((inLen + 4) % blockSize == 0 ? inLen + 4 : (((inLen + 4) / blockSize) + 1) * blockSize)];
            }
            cekBlock[0] = (byte) inLen;
            cekBlock[1] = (byte) (~in[inOff]);
            cekBlock[2] = (byte) (~in[inOff + 1]);
            cekBlock[3] = (byte) (~in[inOff + 2]);
            System.arraycopy(in, inOff, cekBlock, 4, inLen);
            byte[] pad = new byte[(cekBlock.length - (inLen + 4))];
            this.d.nextBytes(pad);
            System.arraycopy(pad, 0, cekBlock, inLen + 4, pad.length);
            for (int i = 0; i < cekBlock.length; i += blockSize) {
                this.a.f(cekBlock, i, cekBlock, i);
            }
            for (int i2 = 0; i2 < cekBlock.length; i2 += blockSize) {
                this.a.f(cekBlock, i2, cekBlock, i2);
            }
            return cekBlock;
        }
        throw new IllegalStateException("not set for wrapping");
    }

    public byte[] c(byte[] in, int inOff, int inLen) {
        if (!this.c) {
            int blockSize = this.a.c();
            if (inLen >= blockSize * 2) {
                byte[] cekBlock = new byte[inLen];
                byte[] iv = new byte[blockSize];
                System.arraycopy(in, inOff, cekBlock, 0, inLen);
                System.arraycopy(in, inOff, iv, 0, iv.length);
                this.a.a(false, new ParametersWithIV(this.b.b(), iv));
                for (int i = blockSize; i < cekBlock.length; i += blockSize) {
                    this.a.f(cekBlock, i, cekBlock, i);
                }
                System.arraycopy(cekBlock, cekBlock.length - iv.length, iv, 0, iv.length);
                this.a.a(false, new ParametersWithIV(this.b.b(), iv));
                this.a.f(cekBlock, 0, cekBlock, 0);
                this.a.a(false, this.b);
                for (int i2 = 0; i2 < cekBlock.length; i2 += blockSize) {
                    this.a.f(cekBlock, i2, cekBlock, i2);
                }
                if ((cekBlock[0] & 255) <= cekBlock.length - 4) {
                    byte[] key = new byte[(cekBlock[0] & 255)];
                    System.arraycopy(cekBlock, 4, key, 0, cekBlock[0]);
                    int nonEqual = 0;
                    for (int i3 = 0; i3 != 3; i3++) {
                        nonEqual |= key[i3] ^ ((byte) (~cekBlock[i3 + 1]));
                    }
                    if (nonEqual == 0) {
                        return key;
                    }
                    throw new InvalidCipherTextException("wrapped key fails checksum");
                }
                throw new InvalidCipherTextException("wrapped key corrupted");
            }
            throw new InvalidCipherTextException("input too short");
        }
        throw new IllegalStateException("not set for unwrapping");
    }
}
