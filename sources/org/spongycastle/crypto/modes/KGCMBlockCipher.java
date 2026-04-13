package org.spongycastle.crypto.modes;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;

public class KGCMBlockCipher implements AEADBlockCipher {
    private static final BigInteger a = BigInteger.valueOf(0);
    private static final BigInteger b = BigInteger.valueOf(1);
    private static final BigInteger c = new BigInteger("340282366920938463463374607431768211456", 10);
    private static final BigInteger d = new BigInteger("340282366920938463463374607431768211455", 10);
    private static final BigInteger e = new BigInteger("135", 10);
    private static final BigInteger f = new BigInteger("115792089237316195423570985008687907853269984665640564039457584007913129639936", 10);
    private static final BigInteger g = new BigInteger("115792089237316195423570985008687907853269984665640564039457584007913129639935", 10);
    private static final BigInteger h = new BigInteger("1061", 10);
    private static final BigInteger i = new BigInteger("13407807929942597099574024998205846127479365820592393377723561443721764030073546976801874298166903427690031858186486050853753882811946569946433649006084096", 10);
    private static final BigInteger j = new BigInteger("13407807929942597099574024998205846127479365820592393377723561443721764030073546976801874298166903427690031858186486050853753882811946569946433649006084095", 10);
    private static final BigInteger k = new BigInteger("293", 10);
    private BlockCipher l;
    private BufferedBlockCipher m;
    private int n;
    private boolean o;
    private byte[] p;
    private byte[] q;
    private byte[] r;
    private byte[] s;
    private byte[] t;
    private byte[] u;
    private int v;
    private int w;
    private ExposedByteArrayOutputStream x = new ExposedByteArrayOutputStream();
    private ExposedByteArrayOutputStream y = new ExposedByteArrayOutputStream();

    public KGCMBlockCipher(BlockCipher dstu7624Engine) {
        this.l = dstu7624Engine;
        this.m = new BufferedBlockCipher(new KCTRBlockCipher(dstu7624Engine));
        this.n = 0;
        this.p = new byte[this.l.c()];
        this.r = new byte[this.l.c()];
        this.s = new byte[this.l.c()];
        this.t = new byte[this.l.c()];
        this.u = new byte[this.l.c()];
        this.w = 0;
        this.v = 0;
        this.q = null;
    }

    public void a(boolean forEncryption, CipherParameters params) {
        KeyParameter engineParam;
        this.o = forEncryption;
        if (params instanceof AEADParameters) {
            AEADParameters param = (AEADParameters) params;
            byte[] iv = param.d();
            byte[] bArr = this.r;
            Arrays.F(bArr, (byte) 0);
            System.arraycopy(iv, 0, this.r, bArr.length - iv.length, iv.length);
            this.p = param.a();
            int macSizeBits = param.c();
            if (macSizeBits < 64 || macSizeBits > this.l.c() * 8 || macSizeBits % 8 != 0) {
                throw new IllegalArgumentException("Invalid value for MAC size: " + macSizeBits);
            }
            this.n = macSizeBits / 8;
            engineParam = param.b();
            byte[] bArr2 = this.p;
            if (bArr2 != null) {
                i(bArr2, 0, bArr2.length);
            }
        } else if (params instanceof ParametersWithIV) {
            ParametersWithIV param2 = (ParametersWithIV) params;
            byte[] iv2 = param2.a();
            byte[] bArr3 = this.r;
            Arrays.F(bArr3, (byte) 0);
            System.arraycopy(iv2, 0, this.r, bArr3.length - iv2.length, iv2.length);
            this.p = null;
            this.n = this.l.c();
            engineParam = (KeyParameter) param2.b();
        } else {
            throw new IllegalArgumentException("Invalid parameter passed");
        }
        this.q = new byte[this.l.c()];
        this.m.f(true, new ParametersWithIV(engineParam, this.r));
        this.l.a(true, engineParam);
    }

    public BlockCipher g() {
        return this.l;
    }

    public void m(byte in) {
        this.x.write(in);
    }

    public void i(byte[] in, int inOff, int len) {
        this.x.write(in, inOff, len);
    }

    private void l(byte[] authText, int authOff, int len) {
        this.v = len * 8;
        BlockCipher blockCipher = this.l;
        byte[] bArr = this.s;
        blockCipher.f(bArr, 0, bArr, 0);
        int totalLength = len;
        int inOff_ = authOff;
        while (totalLength > 0) {
            for (int byteIndex = 0; byteIndex < this.l.c(); byteIndex++) {
                byte[] bArr2 = this.t;
                bArr2[byteIndex] = (byte) (bArr2[byteIndex] ^ authText[inOff_ + byteIndex]);
            }
            k(this.l.c() * 8, this.t, this.s, this.u);
            byte[] T = Arrays.T(this.u);
            this.u = T;
            System.arraycopy(T, 0, this.t, 0, this.l.c());
            totalLength -= this.l.c();
            inOff_ += this.l.c();
        }
    }

    public int d(byte[] in, int inOff, int inLen, byte[] out, int outOff) {
        if (in.length >= inOff + inLen) {
            this.y.write(in, inOff, inLen);
            return 0;
        }
        throw new DataLengthException("input buffer too short");
    }

    public int c(byte[] out, int outOff) {
        int resultLen;
        int len = this.y.size();
        if (this.x.size() > 0) {
            l(this.x.a(), 0, this.x.size());
        }
        if (!this.o) {
            this.w = (len - this.n) * 8;
            b(this.y.a(), 0, len - this.n);
            int resultLen2 = this.m.g(this.y.a(), 0, len - this.n, out, outOff);
            resultLen = resultLen2 + this.m.a(out, outOff + resultLen2);
        } else if (out.length - outOff >= this.n + len) {
            this.w = len * 8;
            int resultLen3 = this.m.g(this.y.a(), 0, len, out, outOff);
            resultLen = resultLen3 + this.m.a(out, outOff + resultLen3);
            b(out, outOff, len);
        } else {
            throw new OutputLengthException("Output buffer too short");
        }
        int outOff2 = outOff + resultLen;
        byte[] bArr = this.q;
        if (bArr == null) {
            throw new IllegalStateException("mac is not calculated");
        } else if (this.o) {
            System.arraycopy(bArr, 0, out, outOff2, this.n);
            n();
            return this.n + resultLen;
        } else {
            byte[] mac = new byte[this.n];
            System.arraycopy(this.y.a(), resultLen, mac, 0, this.n);
            int i2 = this.n;
            byte[] calculatedMac = new byte[i2];
            System.arraycopy(this.q, 0, calculatedMac, 0, i2);
            if (Arrays.u(mac, calculatedMac)) {
                n();
                return resultLen;
            }
            throw new InvalidCipherTextException("mac verification failed");
        }
    }

    public byte[] h() {
        int i2 = this.n;
        byte[] mac = new byte[i2];
        System.arraycopy(this.q, 0, mac, 0, i2);
        return mac;
    }

    public int e(int len) {
        return len;
    }

    public int f(int len) {
        if (this.o) {
            return len;
        }
        return this.n + len;
    }

    public void n() {
        this.s = new byte[this.l.c()];
        this.t = new byte[this.l.c()];
        this.u = new byte[this.l.c()];
        this.w = 0;
        this.v = 0;
        this.l.reset();
        this.y.reset();
        this.x.reset();
        byte[] bArr = this.p;
        if (bArr != null) {
            i(bArr, 0, bArr.length);
        }
    }

    private void b(byte[] input, int inOff, int len) {
        this.q = new byte[this.l.c()];
        int totalLength = len;
        int inOff_ = inOff;
        while (totalLength > 0) {
            for (int byteIndex = 0; byteIndex < this.l.c(); byteIndex++) {
                byte[] bArr = this.t;
                bArr[byteIndex] = (byte) (bArr[byteIndex] ^ input[byteIndex + inOff_]);
            }
            k(this.l.c() * 8, this.t, this.s, this.u);
            byte[] T = Arrays.T(this.u);
            this.u = T;
            System.arraycopy(T, 0, this.t, 0, this.l.c());
            totalLength -= this.l.c();
            inOff_ += this.l.c();
        }
        Arrays.F(this.u, (byte) 0);
        j(this.v, this.u, 0);
        j(this.w, this.u, this.l.c() / 2);
        for (int byteIndex2 = 0; byteIndex2 < this.l.c(); byteIndex2++) {
            byte[] bArr2 = this.t;
            bArr2[byteIndex2] = (byte) (bArr2[byteIndex2] ^ this.u[byteIndex2]);
        }
        this.l.f(this.t, 0, this.q, 0);
    }

    private void j(int num, byte[] outBytes, int outOff) {
        outBytes[outOff + 3] = (byte) (num >> 24);
        outBytes[outOff + 2] = (byte) (num >> 16);
        outBytes[outOff + 1] = (byte) (num >> 8);
        outBytes[outOff] = (byte) num;
    }

    private void k(int blockBitLength, byte[] x2, byte[] y2, byte[] x_mult_y) {
        BigInteger polyred;
        BigInteger mask2;
        BigInteger mask1;
        byte[] bArr = x_mult_y;
        byte[] fieldOperationBuffer1 = new byte[this.l.c()];
        byte[] fieldOperationBuffer2 = new byte[this.l.c()];
        System.arraycopy(x2, 0, fieldOperationBuffer1, 0, this.l.c());
        System.arraycopy(y2, 0, fieldOperationBuffer2, 0, this.l.c());
        byte[] fieldOperationBuffer12 = Arrays.T(fieldOperationBuffer1);
        byte[] fieldOperationBuffer22 = Arrays.T(fieldOperationBuffer2);
        switch (blockBitLength) {
            case 128:
                mask1 = c;
                mask2 = d;
                polyred = e;
                break;
            case 256:
                mask1 = f;
                mask2 = g;
                polyred = h;
                break;
            case 512:
                mask1 = i;
                mask2 = j;
                polyred = k;
                break;
            default:
                mask1 = c;
                mask2 = d;
                polyred = e;
                break;
        }
        BigInteger p2 = a;
        BigInteger p1 = new BigInteger(1, fieldOperationBuffer12);
        BigInteger p22 = new BigInteger(1, fieldOperationBuffer22);
        while (true) {
            BigInteger bigInteger = a;
            if (!p22.equals(bigInteger)) {
                BigInteger bigInteger2 = b;
                if (p22.and(bigInteger2).equals(bigInteger2)) {
                    p2 = p2.xor(p1);
                }
                BigInteger p12 = p1.shiftLeft(1);
                if (!p12.and(mask1).equals(bigInteger)) {
                    p12 = p12.xor(polyred);
                }
                p1 = p12;
                p22 = p22.shiftRight(1);
            } else {
                byte[] got = BigIntegers.b(p2.and(mask2));
                Arrays.F(bArr, (byte) 0);
                System.arraycopy(got, 0, bArr, 0, got.length);
                return;
            }
        }
    }

    public class ExposedByteArrayOutputStream extends ByteArrayOutputStream {
        public ExposedByteArrayOutputStream() {
        }

        public byte[] a() {
            return this.buf;
        }
    }
}
