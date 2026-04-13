package org.spongycastle.crypto.encodings;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.util.Arrays;

public class PKCS1Encoding implements AsymmetricBlockCipher {
    private SecureRandom a;
    private AsymmetricBlockCipher b;
    private boolean c;
    private boolean d;
    private boolean e;
    private int f = -1;
    private byte[] g = null;
    private byte[] h;

    public PKCS1Encoding(AsymmetricBlockCipher cipher) {
        this.b = cipher;
        this.e = j();
    }

    public PKCS1Encoding(AsymmetricBlockCipher cipher, byte[] fallback) {
        this.b = cipher;
        this.e = j();
        this.g = fallback;
        this.f = fallback.length;
    }

    private boolean j() {
        String strict = (String) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                return System.getProperty("org.spongycastle.pkcs1.strict");
            }
        });
        String notStrict = (String) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                return System.getProperty("org.spongycastle.pkcs1.not_strict");
            }
        });
        if (notStrict != null) {
            return true ^ notStrict.equals("true");
        }
        if (strict == null || strict.equals("true")) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: type inference failed for: r1v12, types: [org.spongycastle.crypto.CipherParameters] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(boolean r4, org.spongycastle.crypto.CipherParameters r5) {
        /*
            r3 = this;
            boolean r0 = r5 instanceof org.spongycastle.crypto.params.ParametersWithRandom
            if (r0 == 0) goto L_0x0015
            r0 = r5
            org.spongycastle.crypto.params.ParametersWithRandom r0 = (org.spongycastle.crypto.params.ParametersWithRandom) r0
            java.security.SecureRandom r1 = r0.b()
            r3.a = r1
            org.spongycastle.crypto.CipherParameters r1 = r0.a()
            r0 = r1
            org.spongycastle.crypto.params.AsymmetricKeyParameter r0 = (org.spongycastle.crypto.params.AsymmetricKeyParameter) r0
            goto L_0x0027
        L_0x0015:
            r0 = r5
            org.spongycastle.crypto.params.AsymmetricKeyParameter r0 = (org.spongycastle.crypto.params.AsymmetricKeyParameter) r0
            boolean r1 = r0.a()
            if (r1 != 0) goto L_0x0027
            if (r4 == 0) goto L_0x0027
            java.security.SecureRandom r1 = new java.security.SecureRandom
            r1.<init>()
            r3.a = r1
        L_0x0027:
            org.spongycastle.crypto.AsymmetricBlockCipher r1 = r3.b
            r1.a(r4, r5)
            boolean r1 = r0.a()
            r3.d = r1
            r3.c = r4
            org.spongycastle.crypto.AsymmetricBlockCipher r1 = r3.b
            int r1 = r1.b()
            byte[] r1 = new byte[r1]
            r3.h = r1
            int r1 = r3.f
            if (r1 <= 0) goto L_0x0053
            byte[] r1 = r3.g
            if (r1 != 0) goto L_0x0053
            java.security.SecureRandom r1 = r3.a
            if (r1 == 0) goto L_0x004b
            goto L_0x0053
        L_0x004b:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "encoder requires random"
            r1.<init>(r2)
            throw r1
        L_0x0053:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.crypto.encodings.PKCS1Encoding.a(boolean, org.spongycastle.crypto.CipherParameters):void");
    }

    public int c() {
        int baseBlockSize = this.b.c();
        if (this.c) {
            return baseBlockSize - 10;
        }
        return baseBlockSize;
    }

    public int b() {
        int baseBlockSize = this.b.b();
        if (this.c) {
            return baseBlockSize;
        }
        return baseBlockSize - 10;
    }

    public byte[] d(byte[] in, int inOff, int inLen) {
        if (this.c) {
            return h(in, inOff, inLen);
        }
        return f(in, inOff, inLen);
    }

    private byte[] h(byte[] in, int inOff, int inLen) {
        if (inLen <= c()) {
            byte[] block = new byte[this.b.c()];
            if (this.d) {
                block[0] = 1;
                for (int i = 1; i != (block.length - inLen) - 1; i++) {
                    block[i] = -1;
                }
            } else {
                this.a.nextBytes(block);
                block[0] = 2;
                for (int i2 = 1; i2 != (block.length - inLen) - 1; i2++) {
                    while (block[i2] == 0) {
                        block[i2] = (byte) this.a.nextInt();
                    }
                }
            }
            block[(block.length - inLen) - 1] = 0;
            System.arraycopy(in, inOff, block, block.length - inLen, inLen);
            return this.b.d(block, 0, block.length);
        }
        throw new IllegalArgumentException("input data too large");
    }

    private static int e(byte[] encoded, int pLen) {
        int correct = 0 | (encoded[0] ^ 2);
        int plen = encoded.length - (pLen + 1);
        for (int i = 1; i < plen; i++) {
            byte tmp = encoded[i];
            int tmp2 = tmp | (tmp >> 1);
            int tmp3 = tmp2 | (tmp2 >> 2);
            correct |= ((tmp3 | (tmp3 >> 4)) & 1) - 1;
        }
        int correct2 = correct | encoded[encoded.length - (pLen + 1)];
        int correct3 = correct2 | (correct2 >> 1);
        int correct4 = correct3 | (correct3 >> 2);
        return ~(((correct4 | (correct4 >> 4)) & 1) - 1);
    }

    private byte[] g(byte[] in, int inOff, int inLen) {
        byte[] random;
        if (this.d) {
            byte[] block = this.b.d(in, inOff, inLen);
            if (this.g == null) {
                random = new byte[this.f];
                this.a.nextBytes(random);
            } else {
                random = this.g;
            }
            byte[] data = this.e & (block.length != this.b.b()) ? this.h : block;
            int correct = e(data, this.f);
            byte[] result = new byte[this.f];
            int i = 0;
            while (true) {
                int i2 = this.f;
                if (i < i2) {
                    result[i] = (byte) ((data[(data.length - i2) + i] & (~correct)) | (random[i] & correct));
                    i++;
                } else {
                    Arrays.F(data, (byte) 0);
                    return result;
                }
            }
        } else {
            throw new InvalidCipherTextException("sorry, this method is only for decryption, not for signing");
        }
    }

    private byte[] f(byte[] in, int inOff, int inLen) {
        byte[] data;
        boolean badType;
        if (this.f != -1) {
            return g(in, inOff, inLen);
        }
        byte[] block = this.b.d(in, inOff, inLen);
        boolean z = true;
        boolean incorrectLength = this.e & (block.length != this.b.b());
        if (block.length < b()) {
            data = this.h;
        } else {
            data = block;
        }
        byte type = data[0];
        if (this.d) {
            badType = type != 2;
        } else {
            badType = type != 1;
        }
        int start = i(type, data) + 1;
        if (start >= 10) {
            z = false;
        }
        if (z || badType) {
            Arrays.F(data, (byte) 0);
            throw new InvalidCipherTextException("block incorrect");
        } else if (!incorrectLength) {
            byte[] result = new byte[(data.length - start)];
            System.arraycopy(data, start, result, 0, result.length);
            return result;
        } else {
            Arrays.F(data, (byte) 0);
            throw new InvalidCipherTextException("block incorrect size");
        }
    }

    private int i(byte type, byte[] block) {
        int start = -1;
        boolean padErr = false;
        for (int i = 1; i != block.length; i++) {
            byte pad = block[i];
            boolean z = false;
            if ((pad == 0) && (start < 0)) {
                start = i;
            }
            boolean z2 = (type == 1) & (start < 0);
            if (pad != -1) {
                z = true;
            }
            padErr |= z2 & z;
        }
        if (padErr) {
            return -1;
        }
        return start;
    }
}
