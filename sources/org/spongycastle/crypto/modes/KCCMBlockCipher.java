package org.spongycastle.crypto.modes;

import java.io.ByteArrayOutputStream;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;

public class KCCMBlockCipher implements AEADBlockCipher {
    private BlockCipher a;
    private int b;
    private boolean c;
    private byte[] d;
    private byte[] e;
    private byte[] f;
    private byte[] g;
    private byte[] h;
    private byte[] i;
    private byte[] j;
    private byte[] k;
    private ExposedByteArrayOutputStream l;
    private ExposedByteArrayOutputStream m;
    private int n;

    private void p(int Nb) {
        if (Nb == 4 || Nb == 6 || Nb == 8) {
            this.n = Nb;
            return;
        }
        throw new IllegalArgumentException("Nb = 4 is recommended by DSTU7624 but can be changed to only 6 or 8 in this implementation");
    }

    public KCCMBlockCipher(BlockCipher engine) {
        this(engine, 4);
    }

    public KCCMBlockCipher(BlockCipher engine, int nB) {
        this.l = new ExposedByteArrayOutputStream();
        this.m = new ExposedByteArrayOutputStream();
        this.n = 4;
        this.a = engine;
        this.b = engine.c();
        this.g = new byte[engine.c()];
        this.d = new byte[engine.c()];
        this.e = new byte[engine.c()];
        this.f = new byte[engine.c()];
        this.h = new byte[engine.c()];
        this.i = new byte[engine.c()];
        this.j = new byte[engine.c()];
        this.k = new byte[engine.c()];
        p(nB);
    }

    public void a(boolean forEncryption, CipherParameters params) {
        CipherParameters cipherParameters;
        if (params instanceof AEADParameters) {
            AEADParameters parameters = (AEADParameters) params;
            if (parameters.c() > 512 || parameters.c() < 64 || parameters.c() % 8 != 0) {
                throw new IllegalArgumentException("Invalid mac size specified");
            }
            this.g = parameters.d();
            this.b = parameters.c() / 8;
            this.d = parameters.a();
            cipherParameters = parameters.b();
        } else if (params instanceof ParametersWithIV) {
            this.g = ((ParametersWithIV) params).a();
            this.b = this.a.c();
            this.d = null;
            cipherParameters = ((ParametersWithIV) params).b();
        } else {
            throw new IllegalArgumentException("Invalid parameters specified");
        }
        this.e = new byte[this.b];
        this.c = forEncryption;
        this.a.a(true, cipherParameters);
        this.k[0] = 1;
        byte[] bArr = this.d;
        if (bArr != null) {
            i(bArr, 0, bArr.length);
        }
    }

    public BlockCipher g() {
        return this.a;
    }

    public void i(byte[] in, int inOff, int len) {
        this.l.write(in, inOff, len);
    }

    private void m(byte[] assocText, int assocOff, int assocLen, int dataLen) {
        if (assocLen - assocOff < this.a.c()) {
            throw new IllegalArgumentException("authText buffer too short");
        } else if (assocLen % this.a.c() == 0) {
            byte[] bArr = this.g;
            System.arraycopy(bArr, 0, this.h, 0, (bArr.length - this.n) - 1);
            l(dataLen, this.i, 0);
            System.arraycopy(this.i, 0, this.h, (this.g.length - this.n) - 1, 4);
            byte[] bArr2 = this.h;
            bArr2[bArr2.length - 1] = k(true, this.b);
            this.a.f(this.h, 0, this.f, 0);
            l(assocLen, this.i, 0);
            if (assocLen <= this.a.c() - this.n) {
                for (int byteIndex = 0; byteIndex < assocLen; byteIndex++) {
                    byte[] bArr3 = this.i;
                    int i2 = this.n + byteIndex;
                    bArr3[i2] = (byte) (bArr3[i2] ^ assocText[assocOff + byteIndex]);
                }
                for (int byteIndex2 = 0; byteIndex2 < this.a.c(); byteIndex2++) {
                    byte[] bArr4 = this.f;
                    bArr4[byteIndex2] = (byte) (bArr4[byteIndex2] ^ this.i[byteIndex2]);
                }
                BlockCipher blockCipher = this.a;
                byte[] bArr5 = this.f;
                blockCipher.f(bArr5, 0, bArr5, 0);
                return;
            }
            for (int byteIndex3 = 0; byteIndex3 < this.a.c(); byteIndex3++) {
                byte[] bArr6 = this.f;
                bArr6[byteIndex3] = (byte) (bArr6[byteIndex3] ^ this.i[byteIndex3]);
            }
            BlockCipher blockCipher2 = this.a;
            byte[] bArr7 = this.f;
            blockCipher2.f(bArr7, 0, bArr7, 0);
            int authLen = assocLen;
            while (authLen != 0) {
                for (int byteIndex4 = 0; byteIndex4 < this.a.c(); byteIndex4++) {
                    byte[] bArr8 = this.f;
                    bArr8[byteIndex4] = (byte) (bArr8[byteIndex4] ^ assocText[byteIndex4 + assocOff]);
                }
                BlockCipher blockCipher3 = this.a;
                byte[] bArr9 = this.f;
                blockCipher3.f(bArr9, 0, bArr9, 0);
                assocOff += this.a.c();
                authLen -= this.a.c();
            }
        } else {
            throw new IllegalArgumentException("padding not supported");
        }
    }

    public int d(byte[] in, int inOff, int inLen, byte[] out, int outOff) {
        if (in.length >= inOff + inLen) {
            this.m.write(in, inOff, inLen);
            return 0;
        }
        throw new DataLengthException("input buffer too short");
    }

    public int n(byte[] in, int inOff, int len, byte[] out, int outOff) {
        int i2;
        if (in.length - inOff < len) {
            throw new DataLengthException("input buffer too short");
        } else if (out.length - outOff >= len) {
            if (this.l.size() > 0) {
                if (this.c) {
                    m(this.l.a(), 0, this.l.size(), this.m.size());
                } else {
                    m(this.l.a(), 0, this.l.size(), this.m.size() - this.b);
                }
            }
            if (this.c) {
                if (len % this.a.c() == 0) {
                    b(in, inOff, len);
                    this.a.f(this.g, 0, this.j, 0);
                    int totalLength = len;
                    while (totalLength > 0) {
                        j(in, inOff, len, out, outOff);
                        totalLength -= this.a.c();
                        inOff += this.a.c();
                        outOff += this.a.c();
                    }
                    int byteIndex = 0;
                    while (true) {
                        byte[] bArr = this.k;
                        if (byteIndex >= bArr.length) {
                            break;
                        }
                        byte[] bArr2 = this.j;
                        bArr2[byteIndex] = (byte) (bArr2[byteIndex] + bArr[byteIndex]);
                        byteIndex++;
                    }
                    this.a.f(this.j, 0, this.i, 0);
                    int byteIndex2 = 0;
                    while (true) {
                        int i3 = this.b;
                        if (byteIndex2 < i3) {
                            out[outOff + byteIndex2] = (byte) (this.i[byteIndex2] ^ this.f[byteIndex2]);
                            byteIndex2++;
                        } else {
                            System.arraycopy(this.f, 0, this.e, 0, i3);
                            o();
                            return this.b + len;
                        }
                    }
                } else {
                    throw new DataLengthException("partial blocks not supported");
                }
            } else if ((len - this.b) % this.a.c() == 0) {
                this.a.f(this.g, 0, this.j, 0);
                int blocks = len / this.a.c();
                for (int blockNum = 0; blockNum < blocks; blockNum++) {
                    j(in, inOff, len, out, outOff);
                    inOff += this.a.c();
                    outOff += this.a.c();
                }
                if (len > inOff) {
                    int byteIndex3 = 0;
                    while (true) {
                        byte[] bArr3 = this.k;
                        if (byteIndex3 >= bArr3.length) {
                            break;
                        }
                        byte[] bArr4 = this.j;
                        bArr4[byteIndex3] = (byte) (bArr4[byteIndex3] + bArr3[byteIndex3]);
                        byteIndex3++;
                    }
                    this.a.f(this.j, 0, this.i, 0);
                    int byteIndex4 = 0;
                    while (true) {
                        i2 = this.b;
                        if (byteIndex4 >= i2) {
                            break;
                        }
                        out[outOff + byteIndex4] = (byte) (this.i[byteIndex4] ^ in[inOff + byteIndex4]);
                        byteIndex4++;
                    }
                    outOff += i2;
                }
                int byteIndex5 = 0;
                while (true) {
                    byte[] bArr5 = this.k;
                    if (byteIndex5 >= bArr5.length) {
                        break;
                    }
                    byte[] bArr6 = this.j;
                    bArr6[byteIndex5] = (byte) (bArr6[byteIndex5] + bArr5[byteIndex5]);
                    byteIndex5++;
                }
                this.a.f(this.j, 0, this.i, 0);
                int i4 = this.b;
                System.arraycopy(out, outOff - i4, this.i, 0, i4);
                b(out, 0, outOff - this.b);
                System.arraycopy(this.f, 0, this.e, 0, this.b);
                int i5 = this.b;
                byte[] calculatedMac = new byte[i5];
                System.arraycopy(this.i, 0, calculatedMac, 0, i5);
                if (Arrays.u(this.e, calculatedMac)) {
                    o();
                    return len - this.b;
                }
                throw new InvalidCipherTextException("mac check failed");
            } else {
                throw new DataLengthException("partial blocks not supported");
            }
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    private void j(byte[] input, int inOff, int len, byte[] output, int outOff) {
        int byteIndex = 0;
        while (true) {
            byte[] bArr = this.k;
            if (byteIndex >= bArr.length) {
                break;
            }
            byte[] bArr2 = this.j;
            bArr2[byteIndex] = (byte) (bArr2[byteIndex] + bArr[byteIndex]);
            byteIndex++;
        }
        this.a.f(this.j, 0, this.i, 0);
        for (int byteIndex2 = 0; byteIndex2 < this.a.c(); byteIndex2++) {
            output[outOff + byteIndex2] = (byte) (this.i[byteIndex2] ^ input[inOff + byteIndex2]);
        }
    }

    private void b(byte[] authText, int authOff, int len) {
        int totalLen = len;
        while (totalLen > 0) {
            for (int byteIndex = 0; byteIndex < this.a.c(); byteIndex++) {
                byte[] bArr = this.f;
                bArr[byteIndex] = (byte) (bArr[byteIndex] ^ authText[authOff + byteIndex]);
            }
            BlockCipher blockCipher = this.a;
            byte[] bArr2 = this.f;
            blockCipher.f(bArr2, 0, bArr2, 0);
            totalLen -= this.a.c();
            authOff += this.a.c();
        }
    }

    public int c(byte[] out, int outOff) {
        int len = n(this.m.a(), 0, this.m.size(), out, outOff);
        o();
        return len;
    }

    public byte[] h() {
        return Arrays.h(this.e);
    }

    public int e(int len) {
        return len;
    }

    public int f(int len) {
        return this.b + len;
    }

    public void o() {
        Arrays.F(this.h, (byte) 0);
        Arrays.F(this.i, (byte) 0);
        Arrays.F(this.k, (byte) 0);
        Arrays.F(this.f, (byte) 0);
        this.k[0] = 1;
        this.m.reset();
        this.l.reset();
        byte[] bArr = this.d;
        if (bArr != null) {
            i(bArr, 0, bArr.length);
        }
    }

    private void l(int num, byte[] outBytes, int outOff) {
        outBytes[outOff + 3] = (byte) (num >> 24);
        outBytes[outOff + 2] = (byte) (num >> 16);
        outBytes[outOff + 1] = (byte) (num >> 8);
        outBytes[outOff] = (byte) num;
    }

    private byte k(boolean authTextPresents, int macSize) {
        StringBuffer flagByte = new StringBuffer();
        if (authTextPresents) {
            flagByte.append("1");
        } else {
            flagByte.append("0");
        }
        switch (macSize) {
            case 8:
                flagByte.append("010");
                break;
            case 16:
                flagByte.append("011");
                break;
            case 32:
                flagByte.append("100");
                break;
            case 48:
                flagByte.append("101");
                break;
            case 64:
                flagByte.append("110");
                break;
        }
        String binaryNb = Integer.toBinaryString(this.n - 1);
        while (binaryNb.length() < 4) {
            binaryNb = new StringBuffer(binaryNb).insert(0, "0").toString();
        }
        flagByte.append(binaryNb);
        return (byte) Integer.parseInt(flagByte.toString(), 2);
    }

    public class ExposedByteArrayOutputStream extends ByteArrayOutputStream {
        public ExposedByteArrayOutputStream() {
        }

        public byte[] a() {
            return this.buf;
        }
    }
}
