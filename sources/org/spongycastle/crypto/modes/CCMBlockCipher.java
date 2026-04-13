package org.spongycastle.crypto.modes;

import java.io.ByteArrayOutputStream;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.macs.CBCBlockCipherMac;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;

public class CCMBlockCipher implements AEADBlockCipher {
    private BlockCipher a;
    private int b;
    private boolean c;
    private byte[] d;
    private byte[] e;
    private int f;
    private CipherParameters g;
    private byte[] h;
    private ExposedByteArrayOutputStream i = new ExposedByteArrayOutputStream();
    private ExposedByteArrayOutputStream j = new ExposedByteArrayOutputStream();

    public CCMBlockCipher(BlockCipher c2) {
        this.a = c2;
        int c3 = c2.c();
        this.b = c3;
        this.h = new byte[c3];
        if (c3 != 16) {
            throw new IllegalArgumentException("cipher required with a block size of 16.");
        }
    }

    public BlockCipher g() {
        return this.a;
    }

    public void a(boolean forEncryption, CipherParameters params) {
        CipherParameters cipherParameters;
        this.c = forEncryption;
        if (params instanceof AEADParameters) {
            AEADParameters param = (AEADParameters) params;
            this.d = param.d();
            this.e = param.a();
            this.f = param.c() / 8;
            cipherParameters = param.b();
        } else if (params instanceof ParametersWithIV) {
            ParametersWithIV param2 = (ParametersWithIV) params;
            this.d = param2.a();
            this.e = null;
            this.f = this.h.length / 2;
            cipherParameters = param2.b();
        } else {
            throw new IllegalArgumentException("invalid parameters passed to CCM: " + params.getClass().getName());
        }
        if (cipherParameters != null) {
            this.g = cipherParameters;
        }
        byte[] bArr = this.d;
        if (bArr == null || bArr.length < 7 || bArr.length > 13) {
            throw new IllegalArgumentException("nonce must have length from 7 to 13 octets");
        }
        o();
    }

    public String j() {
        return this.a.b() + "/CCM";
    }

    public void m(byte in) {
        this.i.write(in);
    }

    public void i(byte[] in, int inOff, int len) {
        this.i.write(in, inOff, len);
    }

    public int d(byte[] in, int inOff, int inLen, byte[] out, int outOff) {
        if (in.length >= inOff + inLen) {
            this.j.write(in, inOff, inLen);
            return 0;
        }
        throw new DataLengthException("Input buffer too short");
    }

    public int c(byte[] out, int outOff) {
        int len = n(this.j.a(), 0, this.j.size(), out, outOff);
        o();
        return len;
    }

    public void o() {
        this.a.reset();
        this.i.reset();
        this.j.reset();
    }

    public byte[] h() {
        byte[] mac = new byte[this.f];
        System.arraycopy(this.h, 0, mac, 0, mac.length);
        return mac;
    }

    public int e(int len) {
        return 0;
    }

    public int f(int len) {
        int totalData = this.j.size() + len;
        if (this.c) {
            return this.f + totalData;
        }
        int i2 = this.f;
        if (totalData < i2) {
            return 0;
        }
        return totalData - i2;
    }

    public int n(byte[] in, int inOff, int inLen, byte[] output, int outOff) {
        int outputLen;
        int i2;
        int i3;
        byte[] bArr = in;
        int i4 = inOff;
        int i5 = inLen;
        byte[] bArr2 = output;
        int i6 = outOff;
        if (this.g != null) {
            byte[] bArr3 = this.d;
            int n = bArr3.length;
            int q = 15 - n;
            if (q >= 4 || i5 < (1 << (q * 8))) {
                byte[] iv = new byte[this.b];
                iv[0] = (byte) ((q - 1) & 7);
                System.arraycopy(bArr3, 0, iv, 1, bArr3.length);
                BlockCipher ctrCipher = new SICBlockCipher(this.a);
                ctrCipher.a(this.c, new ParametersWithIV(this.g, iv));
                int inIndex = inOff;
                int outIndex = outOff;
                if (this.c) {
                    outputLen = this.f + i5;
                    if (bArr2.length >= outputLen + i6) {
                        b(bArr, i4, i5, this.h);
                        byte[] encMac = new byte[this.b];
                        ctrCipher.f(this.h, 0, encMac, 0);
                        while (true) {
                            i3 = this.b;
                            if (inIndex >= (i4 + i5) - i3) {
                                break;
                            }
                            ctrCipher.f(bArr, inIndex, bArr2, outIndex);
                            int i7 = this.b;
                            outIndex += i7;
                            inIndex += i7;
                        }
                        byte[] block = new byte[i3];
                        int i8 = n;
                        System.arraycopy(bArr, inIndex, block, 0, (i5 + i4) - inIndex);
                        ctrCipher.f(block, 0, block, 0);
                        System.arraycopy(block, 0, bArr2, outIndex, (i5 + i4) - inIndex);
                        int i9 = q;
                        System.arraycopy(encMac, 0, bArr2, i6 + i5, this.f);
                    } else {
                        throw new OutputLengthException("Output buffer too short.");
                    }
                } else {
                    int i10 = q;
                    int n2 = this.f;
                    if (i5 >= n2) {
                        outputLen = i5 - n2;
                        if (bArr2.length >= outputLen + i6) {
                            byte b2 = 0;
                            System.arraycopy(bArr, i4 + outputLen, this.h, 0, n2);
                            byte[] bArr4 = this.h;
                            ctrCipher.f(bArr4, 0, bArr4, 0);
                            int i11 = this.f;
                            while (true) {
                                byte[] bArr5 = this.h;
                                if (i11 == bArr5.length) {
                                    break;
                                }
                                bArr5[i11] = b2;
                                i11++;
                                b2 = 0;
                            }
                            while (true) {
                                i2 = this.b;
                                if (inIndex >= (i4 + outputLen) - i2) {
                                    break;
                                }
                                ctrCipher.f(bArr, inIndex, bArr2, outIndex);
                                int i12 = this.b;
                                outIndex += i12;
                                inIndex += i12;
                            }
                            byte[] block2 = new byte[i2];
                            System.arraycopy(bArr, inIndex, block2, 0, outputLen - (inIndex - i4));
                            ctrCipher.f(block2, 0, block2, 0);
                            System.arraycopy(block2, 0, bArr2, outIndex, outputLen - (inIndex - i4));
                            byte[] calculatedMacBlock = new byte[this.b];
                            b(bArr2, i6, outputLen, calculatedMacBlock);
                            if (!Arrays.u(this.h, calculatedMacBlock)) {
                                throw new InvalidCipherTextException("mac check in CCM failed");
                            }
                        } else {
                            throw new OutputLengthException("Output buffer too short.");
                        }
                    } else {
                        throw new InvalidCipherTextException("data too short");
                    }
                }
                return outputLen;
            }
            throw new IllegalStateException("CCM packet too large for choice of q.");
        }
        throw new IllegalStateException("CCM cipher unitialized.");
    }

    private int b(byte[] data, int dataOff, int dataLen, byte[] macBlock) {
        int extra;
        Mac cMac = new CBCBlockCipherMac(this.a, this.f * 8);
        cMac.a(this.g);
        byte[] b0 = new byte[16];
        if (l()) {
            b0[0] = (byte) (b0[0] | 64);
        }
        b0[0] = (byte) (b0[0] | ((((cMac.e() - 2) / 2) & 7) << 3));
        byte b2 = b0[0];
        byte[] bArr = this.d;
        b0[0] = (byte) (b2 | (((15 - bArr.length) - 1) & 7));
        System.arraycopy(bArr, 0, b0, 1, bArr.length);
        int q = dataLen;
        int count = 1;
        while (q > 0) {
            b0[b0.length - count] = (byte) (q & 255);
            q >>>= 8;
            count++;
        }
        cMac.update(b0, 0, b0.length);
        if (l()) {
            int textLength = k();
            if (textLength < 65280) {
                cMac.d((byte) (textLength >> 8));
                cMac.d((byte) textLength);
                extra = 2;
            } else {
                cMac.d((byte) -1);
                cMac.d((byte) -2);
                cMac.d((byte) (textLength >> 24));
                cMac.d((byte) (textLength >> 16));
                cMac.d((byte) (textLength >> 8));
                cMac.d((byte) textLength);
                extra = 6;
            }
            byte[] bArr2 = this.e;
            if (bArr2 != null) {
                cMac.update(bArr2, 0, bArr2.length);
            }
            if (this.i.size() > 0) {
                cMac.update(this.i.a(), 0, this.i.size());
            }
            int extra2 = (extra + textLength) % 16;
            if (extra2 != 0) {
                for (int i2 = extra2; i2 != 16; i2++) {
                    cMac.d((byte) 0);
                }
            }
        }
        cMac.update(data, dataOff, dataLen);
        return cMac.c(macBlock, 0);
    }

    private int k() {
        int size = this.i.size();
        byte[] bArr = this.e;
        return size + (bArr == null ? 0 : bArr.length);
    }

    private boolean l() {
        return k() > 0;
    }

    public class ExposedByteArrayOutputStream extends ByteArrayOutputStream {
        public ExposedByteArrayOutputStream() {
        }

        public byte[] a() {
            return this.buf;
        }
    }
}
