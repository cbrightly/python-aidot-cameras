package org.spongycastle.pqc.crypto.rainbow;

import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.pqc.crypto.MessageSigner;
import org.spongycastle.pqc.crypto.rainbow.util.ComputeInField;
import org.spongycastle.pqc.crypto.rainbow.util.GF2Field;

public class RainbowSigner implements MessageSigner {
    private SecureRandom a;
    int b;
    private short[] c;
    private ComputeInField d = new ComputeInField();
    RainbowKeyParameters e;

    public void a(boolean forSigning, CipherParameters param) {
        if (!forSigning) {
            this.e = (RainbowPublicKeyParameters) param;
        } else if (param instanceof ParametersWithRandom) {
            ParametersWithRandom rParam = (ParametersWithRandom) param;
            this.a = rParam.b();
            this.e = (RainbowPrivateKeyParameters) rParam.a();
        } else {
            this.a = new SecureRandom();
            this.e = (RainbowPrivateKeyParameters) param;
        }
        this.b = this.e.b();
    }

    private short[] d(Layer[] layer, short[] msg) {
        short[] sArr = new short[msg.length];
        short[] Y_ = this.d.i(((RainbowPrivateKeyParameters) this.e).e(), this.d.b(((RainbowPrivateKeyParameters) this.e).c(), msg));
        for (int i = 0; i < layer[0].f(); i++) {
            this.c[i] = (short) this.a.nextInt();
            short[] sArr2 = this.c;
            sArr2[i] = (short) (sArr2[i] & 255);
        }
        return Y_;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00a5 A[Catch:{ Exception -> 0x00ae }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00ba A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:5:0x0031 A[Catch:{ Exception -> 0x00ae }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] b(byte[] r17) {
        /*
            r16 = this;
            r1 = r16
            org.spongycastle.pqc.crypto.rainbow.RainbowKeyParameters r0 = r1.e
            org.spongycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters r0 = (org.spongycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters) r0
            org.spongycastle.pqc.crypto.rainbow.Layer[] r2 = r0.g()
            int r3 = r2.length
            org.spongycastle.pqc.crypto.rainbow.RainbowKeyParameters r0 = r1.e
            org.spongycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters r0 = (org.spongycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters) r0
            short[][] r0 = r0.f()
            int r0 = r0.length
            short[] r0 = new short[r0]
            r1.c = r0
            int r0 = r3 + -1
            r0 = r2[r0]
            int r0 = r0.g()
            byte[] r4 = new byte[r0]
            short[] r5 = r16.e(r17)
            r0 = 0
            r6 = r0
        L_0x0028:
            r7 = 1
            r8 = 0
            short[] r0 = r1.d(r2, r5)     // Catch:{ Exception -> 0x00ae }
            r9 = 0
        L_0x002f:
            if (r9 >= r3) goto L_0x0083
            r10 = r2[r9]     // Catch:{ Exception -> 0x00ae }
            int r10 = r10.e()     // Catch:{ Exception -> 0x00ae }
            short[] r10 = new short[r10]     // Catch:{ Exception -> 0x00ae }
            r11 = r2[r9]     // Catch:{ Exception -> 0x00ae }
            int r11 = r11.e()     // Catch:{ Exception -> 0x00ae }
            short[] r11 = new short[r11]     // Catch:{ Exception -> 0x00ae }
            r12 = 0
        L_0x0042:
            r13 = r2[r9]     // Catch:{ Exception -> 0x00ae }
            int r13 = r13.e()     // Catch:{ Exception -> 0x00ae }
            if (r12 >= r13) goto L_0x0053
            short r13 = r0[r8]     // Catch:{ Exception -> 0x00ae }
            r10[r12] = r13     // Catch:{ Exception -> 0x00ae }
            int r8 = r8 + 1
            int r12 = r12 + 1
            goto L_0x0042
        L_0x0053:
            org.spongycastle.pqc.crypto.rainbow.util.ComputeInField r12 = r1.d     // Catch:{ Exception -> 0x00ae }
            r13 = r2[r9]     // Catch:{ Exception -> 0x00ae }
            short[] r14 = r1.c     // Catch:{ Exception -> 0x00ae }
            short[][] r13 = r13.h(r14)     // Catch:{ Exception -> 0x00ae }
            short[] r12 = r12.j(r13, r10)     // Catch:{ Exception -> 0x00ae }
            r11 = r12
            if (r11 == 0) goto L_0x007b
            r12 = 0
        L_0x0065:
            int r13 = r11.length     // Catch:{ Exception -> 0x00ae }
            if (r12 >= r13) goto L_0x0078
            short[] r13 = r1.c     // Catch:{ Exception -> 0x00ae }
            r14 = r2[r9]     // Catch:{ Exception -> 0x00ae }
            int r14 = r14.f()     // Catch:{ Exception -> 0x00ae }
            int r14 = r14 + r12
            short r15 = r11[r12]     // Catch:{ Exception -> 0x00ae }
            r13[r14] = r15     // Catch:{ Exception -> 0x00ae }
            int r12 = r12 + 1
            goto L_0x0065
        L_0x0078:
            int r9 = r9 + 1
            goto L_0x002f
        L_0x007b:
            java.lang.Exception r12 = new java.lang.Exception     // Catch:{ Exception -> 0x00ae }
            java.lang.String r13 = "LES is not solveable!"
            r12.<init>(r13)     // Catch:{ Exception -> 0x00ae }
            throw r12     // Catch:{ Exception -> 0x00ae }
        L_0x0083:
            org.spongycastle.pqc.crypto.rainbow.util.ComputeInField r9 = r1.d     // Catch:{ Exception -> 0x00ae }
            org.spongycastle.pqc.crypto.rainbow.RainbowKeyParameters r10 = r1.e     // Catch:{ Exception -> 0x00ae }
            org.spongycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters r10 = (org.spongycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters) r10     // Catch:{ Exception -> 0x00ae }
            short[] r10 = r10.d()     // Catch:{ Exception -> 0x00ae }
            short[] r11 = r1.c     // Catch:{ Exception -> 0x00ae }
            short[] r9 = r9.b(r10, r11)     // Catch:{ Exception -> 0x00ae }
            org.spongycastle.pqc.crypto.rainbow.util.ComputeInField r10 = r1.d     // Catch:{ Exception -> 0x00ae }
            org.spongycastle.pqc.crypto.rainbow.RainbowKeyParameters r11 = r1.e     // Catch:{ Exception -> 0x00ae }
            org.spongycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters r11 = (org.spongycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters) r11     // Catch:{ Exception -> 0x00ae }
            short[][] r11 = r11.f()     // Catch:{ Exception -> 0x00ae }
            short[] r10 = r10.i(r11, r9)     // Catch:{ Exception -> 0x00ae }
            r11 = 0
        L_0x00a2:
            int r12 = r4.length     // Catch:{ Exception -> 0x00ae }
            if (r11 >= r12) goto L_0x00ad
            short r12 = r10[r11]     // Catch:{ Exception -> 0x00ae }
            byte r12 = (byte) r12     // Catch:{ Exception -> 0x00ae }
            r4[r11] = r12     // Catch:{ Exception -> 0x00ae }
            int r11 = r11 + 1
            goto L_0x00a2
        L_0x00ad:
            goto L_0x00b0
        L_0x00ae:
            r0 = move-exception
            r7 = 0
        L_0x00b0:
            r0 = 65536(0x10000, float:9.18355E-41)
            if (r7 != 0) goto L_0x00b8
            int r6 = r6 + 1
            if (r6 < r0) goto L_0x0028
        L_0x00b8:
            if (r6 == r0) goto L_0x00bb
            return r4
        L_0x00bb:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r9 = "unable to generate signature - LES not solvable"
            r0.<init>(r9)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.pqc.crypto.rainbow.RainbowSigner.b(byte[]):byte[]");
    }

    public boolean c(byte[] message, byte[] signature) {
        short[] sigInt = new short[signature.length];
        for (int i = 0; i < signature.length; i++) {
            sigInt[i] = (short) (((short) signature[i]) & 255);
        }
        short[] msgHashVal = e(message);
        short[] verificationResult = f(sigInt);
        boolean verified = true;
        if (msgHashVal.length != verificationResult.length) {
            return false;
        }
        for (int i2 = 0; i2 < msgHashVal.length; i2++) {
            verified = verified && msgHashVal[i2] == verificationResult[i2];
        }
        return verified;
    }

    private short[] f(short[] signature) {
        short[][] coeff_quadratic = ((RainbowPublicKeyParameters) this.e).c();
        short[][] coeff_singular = ((RainbowPublicKeyParameters) this.e).e();
        short[] coeff_scalar = ((RainbowPublicKeyParameters) this.e).d();
        short[] rslt = new short[coeff_quadratic.length];
        int n = coeff_singular[0].length;
        for (int p = 0; p < coeff_quadratic.length; p++) {
            int offset = 0;
            for (int x = 0; x < n; x++) {
                for (int y = x; y < n; y++) {
                    rslt[p] = GF2Field.a(rslt[p], GF2Field.c(coeff_quadratic[p][offset], GF2Field.c(signature[x], signature[y])));
                    offset++;
                }
                rslt[p] = GF2Field.a(rslt[p], GF2Field.c(coeff_singular[p][x], signature[x]));
            }
            rslt[p] = GF2Field.a(rslt[p], coeff_scalar[p]);
        }
        return rslt;
    }

    private short[] e(byte[] message) {
        short[] output = new short[this.b];
        int h = 0;
        int i = 0;
        while (i < message.length) {
            output[i] = (short) message[h];
            output[i] = (short) (output[i] & 255);
            h++;
            i++;
            if (i >= output.length) {
                break;
            }
        }
        return output;
    }
}
