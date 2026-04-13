package org.spongycastle.crypto.engines;

import java.util.ArrayList;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.Arrays;

public class DSTU7624WrapEngine implements Wrapper {
    private boolean a;
    private DSTU7624Engine b;
    private byte[] c;
    private byte[] d = new byte[4];
    private byte[] e = new byte[this.b.c()];
    private byte[] f = new byte[this.b.c()];
    private ArrayList<byte[]> g = new ArrayList<>();

    public DSTU7624WrapEngine(int blockBitLength) {
        DSTU7624Engine dSTU7624Engine = new DSTU7624Engine(blockBitLength);
        this.b = dSTU7624Engine;
        this.c = new byte[(dSTU7624Engine.c() / 2)];
    }

    public void a(boolean forWrapping, CipherParameters param) {
        if (param instanceof ParametersWithRandom) {
            param = ((ParametersWithRandom) param).a();
        }
        this.a = forWrapping;
        if (param instanceof KeyParameter) {
            this.b.a(forWrapping, param);
            return;
        }
        throw new IllegalArgumentException("invalid parameters passed to DSTU7624WrapEngine");
    }

    public String b() {
        return "DSTU7624WrapEngine";
    }

    public byte[] wrap(byte[] in, int inOff, int inLen) {
        if (!this.a) {
            throw new IllegalStateException("not set for wrapping");
        } else if (inLen % this.b.c() != 0) {
            throw new DataLengthException("wrap data must be a multiple of " + this.b.c() + " bytes");
        } else if (inOff + inLen <= in.length) {
            int n = ((inLen / this.b.c()) + 1) * 2;
            int V = (n - 1) * 6;
            byte[] wrappedBuffer = new byte[(this.b.c() + inLen)];
            System.arraycopy(in, inOff, wrappedBuffer, 0, inLen);
            System.arraycopy(wrappedBuffer, 0, this.c, 0, this.b.c() / 2);
            this.g.clear();
            int bHalfBlocksLen = wrappedBuffer.length - (this.b.c() / 2);
            int bufOff = this.b.c() / 2;
            while (bHalfBlocksLen != 0) {
                byte[] temp = new byte[(this.b.c() / 2)];
                System.arraycopy(wrappedBuffer, bufOff, temp, 0, this.b.c() / 2);
                this.g.add(temp);
                bHalfBlocksLen -= this.b.c() / 2;
                bufOff += this.b.c() / 2;
            }
            for (int j = 0; j < V; j++) {
                System.arraycopy(this.c, 0, wrappedBuffer, 0, this.b.c() / 2);
                System.arraycopy(this.g.get(0), 0, wrappedBuffer, this.b.c() / 2, this.b.c() / 2);
                this.b.f(wrappedBuffer, 0, wrappedBuffer, 0);
                d(j + 1, this.d, 0);
                for (int byteNum = 0; byteNum < 4; byteNum++) {
                    int c2 = (this.b.c() / 2) + byteNum;
                    wrappedBuffer[c2] = (byte) (wrappedBuffer[c2] ^ this.d[byteNum]);
                }
                System.arraycopy(wrappedBuffer, this.b.c() / 2, this.c, 0, this.b.c() / 2);
                for (int i = 2; i < n; i++) {
                    System.arraycopy(this.g.get(i - 1), 0, this.g.get(i - 2), 0, this.b.c() / 2);
                }
                System.arraycopy(wrappedBuffer, 0, this.g.get(n - 2), 0, this.b.c() / 2);
            }
            System.arraycopy(this.c, 0, wrappedBuffer, 0, this.b.c() / 2);
            int bufOff2 = this.b.c() / 2;
            for (int i2 = 0; i2 < n - 1; i2++) {
                System.arraycopy(this.g.get(i2), 0, wrappedBuffer, bufOff2, this.b.c() / 2);
                bufOff2 += this.b.c() / 2;
            }
            return wrappedBuffer;
        } else {
            throw new DataLengthException("input buffer too short");
        }
    }

    public byte[] c(byte[] in, int inOff, int inLen) {
        if (this.a) {
            throw new IllegalStateException("not set for unwrapping");
        } else if (inLen % this.b.c() == 0) {
            int n = (inLen * 2) / this.b.c();
            int V = (n - 1) * 6;
            byte[] buffer = new byte[inLen];
            System.arraycopy(in, inOff, buffer, 0, inLen);
            byte[] B = new byte[(this.b.c() / 2)];
            System.arraycopy(buffer, 0, B, 0, this.b.c() / 2);
            this.g.clear();
            int bHalfBlocksLen = buffer.length - (this.b.c() / 2);
            int bufOff = this.b.c() / 2;
            while (bHalfBlocksLen != 0) {
                byte[] temp = new byte[(this.b.c() / 2)];
                System.arraycopy(buffer, bufOff, temp, 0, this.b.c() / 2);
                this.g.add(temp);
                bHalfBlocksLen -= this.b.c() / 2;
                bufOff += this.b.c() / 2;
            }
            for (int j = 0; j < V; j++) {
                System.arraycopy(this.g.get(n - 2), 0, buffer, 0, this.b.c() / 2);
                System.arraycopy(B, 0, buffer, this.b.c() / 2, this.b.c() / 2);
                d(V - j, this.d, 0);
                for (int byteNum = 0; byteNum < 4; byteNum++) {
                    int c2 = (this.b.c() / 2) + byteNum;
                    buffer[c2] = (byte) (buffer[c2] ^ this.d[byteNum]);
                }
                this.b.f(buffer, 0, buffer, 0);
                System.arraycopy(buffer, 0, B, 0, this.b.c() / 2);
                for (int i = 2; i < n; i++) {
                    System.arraycopy(this.g.get((n - i) - 1), 0, this.g.get(n - i), 0, this.b.c() / 2);
                }
                System.arraycopy(buffer, this.b.c() / 2, this.g.get(0), 0, this.b.c() / 2);
            }
            System.arraycopy(B, 0, buffer, 0, this.b.c() / 2);
            int bufOff2 = this.b.c() / 2;
            for (int i2 = 0; i2 < n - 1; i2++) {
                System.arraycopy(this.g.get(i2), 0, buffer, bufOff2, this.b.c() / 2);
                bufOff2 += this.b.c() / 2;
            }
            System.arraycopy(buffer, buffer.length - this.b.c(), this.e, 0, this.b.c());
            byte[] wrappedBuffer = new byte[(buffer.length - this.b.c())];
            if (Arrays.b(this.e, this.f)) {
                System.arraycopy(buffer, 0, wrappedBuffer, 0, buffer.length - this.b.c());
                return wrappedBuffer;
            }
            throw new InvalidCipherTextException("checksum failed");
        } else {
            throw new DataLengthException("unwrap data must be a multiple of " + this.b.c() + " bytes");
        }
    }

    private void d(int number, byte[] outBytes, int outOff) {
        outBytes[outOff + 3] = (byte) (number >> 24);
        outBytes[outOff + 2] = (byte) (number >> 16);
        outBytes[outOff + 1] = (byte) (number >> 8);
        outBytes[outOff] = (byte) number;
    }
}
