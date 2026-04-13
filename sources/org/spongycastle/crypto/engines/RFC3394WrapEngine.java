package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.Arrays;

public class RFC3394WrapEngine implements Wrapper {
    private BlockCipher a;
    private boolean b;
    private KeyParameter c;
    private boolean d;
    private byte[] e;

    public RFC3394WrapEngine(BlockCipher engine) {
        this(engine, false);
    }

    public RFC3394WrapEngine(BlockCipher engine, boolean useReverseDirection) {
        this.e = new byte[]{-90, -90, -90, -90, -90, -90, -90, -90};
        this.a = engine;
        this.b = !useReverseDirection;
    }

    public void a(boolean forWrapping, CipherParameters param) {
        this.d = forWrapping;
        if (param instanceof ParametersWithRandom) {
            param = ((ParametersWithRandom) param).a();
        }
        if (param instanceof KeyParameter) {
            this.c = (KeyParameter) param;
        } else if (param instanceof ParametersWithIV) {
            this.e = ((ParametersWithIV) param).a();
            this.c = (KeyParameter) ((ParametersWithIV) param).b();
            if (this.e.length != 8) {
                throw new IllegalArgumentException("IV not equal to 8");
            }
        }
    }

    public String b() {
        return this.a.b();
    }

    public byte[] wrap(byte[] in, int inOff, int inLen) {
        if (this.d) {
            int n = inLen / 8;
            if (n * 8 == inLen) {
                byte[] bArr = this.e;
                byte[] block = new byte[(bArr.length + inLen)];
                byte[] buf = new byte[(bArr.length + 8)];
                System.arraycopy(bArr, 0, block, 0, bArr.length);
                System.arraycopy(in, inOff, block, this.e.length, inLen);
                this.a.a(this.b, this.c);
                for (int j = 0; j != 6; j++) {
                    for (int i = 1; i <= n; i++) {
                        System.arraycopy(block, 0, buf, 0, this.e.length);
                        System.arraycopy(block, i * 8, buf, this.e.length, 8);
                        this.a.f(buf, 0, buf, 0);
                        int t = (n * j) + i;
                        int k = 1;
                        while (t != 0) {
                            int length = this.e.length - k;
                            buf[length] = (byte) (buf[length] ^ ((byte) t));
                            t >>>= 8;
                            k++;
                        }
                        System.arraycopy(buf, 0, block, 0, 8);
                        System.arraycopy(buf, 8, block, i * 8, 8);
                    }
                }
                return block;
            }
            throw new DataLengthException("wrap data must be a multiple of 8 bytes");
        }
        throw new IllegalStateException("not set for wrapping");
    }

    public byte[] c(byte[] in, int inOff, int inLen) {
        byte[] bArr = in;
        int i = inOff;
        int i2 = inLen;
        if (!this.d) {
            int n = i2 / 8;
            if (n * 8 == i2) {
                byte[] bArr2 = this.e;
                byte[] block = new byte[(i2 - bArr2.length)];
                byte[] a2 = new byte[bArr2.length];
                int i3 = 8;
                byte[] buf = new byte[(bArr2.length + 8)];
                System.arraycopy(bArr, i, a2, 0, bArr2.length);
                byte[] bArr3 = this.e;
                System.arraycopy(bArr, bArr3.length + i, block, 0, i2 - bArr3.length);
                int i4 = 1;
                this.a.a(!this.b, this.c);
                int n2 = n - 1;
                int j = 5;
                while (j >= 0) {
                    int i5 = n2;
                    while (i5 >= i4) {
                        System.arraycopy(a2, 0, buf, 0, this.e.length);
                        System.arraycopy(block, (i5 - 1) * i3, buf, this.e.length, i3);
                        int t = (n2 * j) + i5;
                        int k = 1;
                        while (t != 0) {
                            int length = this.e.length - k;
                            buf[length] = (byte) (buf[length] ^ ((byte) t));
                            t >>>= 8;
                            k++;
                        }
                        this.a.f(buf, 0, buf, 0);
                        i3 = 8;
                        System.arraycopy(buf, 0, a2, 0, 8);
                        System.arraycopy(buf, 8, block, (i5 - 1) * 8, 8);
                        i5--;
                        i4 = 1;
                    }
                    j--;
                    i4 = 1;
                }
                if (Arrays.u(a2, this.e)) {
                    return block;
                }
                throw new InvalidCipherTextException("checksum failed");
            }
            throw new InvalidCipherTextException("unwrap data must be a multiple of 8 bytes");
        }
        throw new IllegalStateException("not set for unwrapping");
    }
}
