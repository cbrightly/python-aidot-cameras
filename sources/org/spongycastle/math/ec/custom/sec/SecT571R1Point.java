package org.spongycastle.math.ec.custom.sec;

import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat576;

public class SecT571R1Point extends ECPoint.AbstractF2m {
    public SecT571R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y) {
        this(curve, x, y, false);
    }

    public SecT571R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression) {
        super(curve, x, y);
        if ((x == null) == (y != null ? false : true)) {
            this.f = withCompression;
            return;
        }
        throw new IllegalArgumentException("Exactly one of the field elements is null");
    }

    SecT571R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        super(curve, x, y, zs);
        this.f = withCompression;
    }

    /* access modifiers changed from: protected */
    public ECPoint d() {
        return new SecT571R1Point((ECCurve) null, f(), g());
    }

    public ECFieldElement r() {
        ECFieldElement X = this.c;
        ECFieldElement L = this.d;
        if (t() || X.i()) {
            return L;
        }
        ECFieldElement Y = L.a(X).j(X);
        ECFieldElement Z = this.e[0];
        if (!Z.h()) {
            return Y.d(Z);
        }
        return Y;
    }

    /* access modifiers changed from: protected */
    public boolean h() {
        ECFieldElement X = n();
        if (!X.i() && o().s() != X.s()) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.spongycastle.math.ec.ECPoint a(org.spongycastle.math.ec.ECPoint r33) {
        /*
            r32 = this;
            r0 = r32
            r1 = r33
            boolean r2 = r32.t()
            if (r2 == 0) goto L_0x000b
            return r1
        L_0x000b:
            boolean r2 = r33.t()
            if (r2 == 0) goto L_0x0012
            return r0
        L_0x0012:
            org.spongycastle.math.ec.ECCurve r2 = r32.i()
            org.spongycastle.math.ec.ECFieldElement r3 = r0.c
            org.spongycastle.math.ec.custom.sec.SecT571FieldElement r3 = (org.spongycastle.math.ec.custom.sec.SecT571FieldElement) r3
            org.spongycastle.math.ec.ECFieldElement r4 = r33.n()
            r9 = r4
            org.spongycastle.math.ec.custom.sec.SecT571FieldElement r9 = (org.spongycastle.math.ec.custom.sec.SecT571FieldElement) r9
            boolean r4 = r3.i()
            if (r4 == 0) goto L_0x0037
            boolean r4 = r9.i()
            if (r4 == 0) goto L_0x0032
            org.spongycastle.math.ec.ECPoint r4 = r2.u()
            return r4
        L_0x0032:
            org.spongycastle.math.ec.ECPoint r4 = r1.a(r0)
            return r4
        L_0x0037:
            org.spongycastle.math.ec.ECFieldElement r4 = r0.d
            r10 = r4
            org.spongycastle.math.ec.custom.sec.SecT571FieldElement r10 = (org.spongycastle.math.ec.custom.sec.SecT571FieldElement) r10
            org.spongycastle.math.ec.ECFieldElement[] r4 = r0.e
            r5 = 0
            r4 = r4[r5]
            r11 = r4
            org.spongycastle.math.ec.custom.sec.SecT571FieldElement r11 = (org.spongycastle.math.ec.custom.sec.SecT571FieldElement) r11
            org.spongycastle.math.ec.ECFieldElement r4 = r33.o()
            r12 = r4
            org.spongycastle.math.ec.custom.sec.SecT571FieldElement r12 = (org.spongycastle.math.ec.custom.sec.SecT571FieldElement) r12
            org.spongycastle.math.ec.ECFieldElement r4 = r1.s(r5)
            r13 = r4
            org.spongycastle.math.ec.custom.sec.SecT571FieldElement r13 = (org.spongycastle.math.ec.custom.sec.SecT571FieldElement) r13
            long[] r14 = org.spongycastle.math.raw.Nat576.a()
            long[] r15 = org.spongycastle.math.raw.Nat576.a()
            long[] r8 = org.spongycastle.math.raw.Nat576.a()
            long[] r7 = org.spongycastle.math.raw.Nat576.a()
            boolean r4 = r11.h()
            if (r4 == 0) goto L_0x006a
            r4 = 0
            goto L_0x0070
        L_0x006a:
            long[] r4 = r11.g
            long[] r4 = org.spongycastle.math.ec.custom.sec.SecT571Field.p(r4)
        L_0x0070:
            if (r4 != 0) goto L_0x007c
            long[] r6 = r9.g
            long[] r5 = r12.g
            r31 = r6
            r6 = r5
            r5 = r31
            goto L_0x008c
        L_0x007c:
            long[] r5 = r9.g
            r6 = r15
            org.spongycastle.math.ec.custom.sec.SecT571Field.n(r5, r4, r15)
            long[] r5 = r12.g
            r18 = r7
            org.spongycastle.math.ec.custom.sec.SecT571Field.n(r5, r4, r7)
            r5 = r6
            r6 = r18
        L_0x008c:
            boolean r18 = r13.h()
            if (r18 == 0) goto L_0x0095
            r16 = 0
            goto L_0x009d
        L_0x0095:
            long[] r1 = r13.g
            long[] r1 = org.spongycastle.math.ec.custom.sec.SecT571Field.p(r1)
            r16 = r1
        L_0x009d:
            r1 = r16
            if (r1 != 0) goto L_0x00b0
            r16 = r13
            long[] r13 = r3.g
            r18 = r13
            long[] r13 = r10.g
            r31 = r18
            r18 = r3
            r3 = r31
            goto L_0x00c8
        L_0x00b0:
            r16 = r13
            long[] r13 = r3.g
            r18 = r14
            org.spongycastle.math.ec.custom.sec.SecT571Field.n(r13, r1, r14)
            long[] r13 = r10.g
            r19 = r8
            org.spongycastle.math.ec.custom.sec.SecT571Field.n(r13, r1, r8)
            r13 = r19
            r31 = r18
            r18 = r3
            r3 = r31
        L_0x00c8:
            r19 = r8
            r20 = r4
            r4 = r19
            org.spongycastle.math.ec.custom.sec.SecT571Field.b(r13, r6, r4)
            r19 = r7
            r21 = r13
            r13 = r19
            org.spongycastle.math.ec.custom.sec.SecT571Field.b(r3, r5, r13)
            boolean r19 = org.spongycastle.math.raw.Nat576.f(r13)
            if (r19 == 0) goto L_0x00f0
            boolean r17 = org.spongycastle.math.raw.Nat576.f(r4)
            if (r17 == 0) goto L_0x00eb
            org.spongycastle.math.ec.ECPoint r17 = r32.H()
            return r17
        L_0x00eb:
            org.spongycastle.math.ec.ECPoint r17 = r2.u()
            return r17
        L_0x00f0:
            boolean r19 = r9.i()
            if (r19 == 0) goto L_0x017c
            org.spongycastle.math.ec.ECPoint r19 = r32.y()
            org.spongycastle.math.ec.ECFieldElement r22 = r19.q()
            r23 = r6
            r6 = r22
            org.spongycastle.math.ec.custom.sec.SecT571FieldElement r6 = (org.spongycastle.math.ec.custom.sec.SecT571FieldElement) r6
            r22 = r9
            org.spongycastle.math.ec.ECFieldElement r9 = r19.r()
            r18 = r12
            r25 = r11
            r24 = r12
            org.spongycastle.math.ec.ECFieldElement r11 = r9.a(r12)
            org.spongycastle.math.ec.ECFieldElement r11 = r11.d(r6)
            org.spongycastle.math.ec.ECFieldElement r12 = r11.o()
            org.spongycastle.math.ec.ECFieldElement r12 = r12.a(r11)
            org.spongycastle.math.ec.ECFieldElement r12 = r12.a(r6)
            org.spongycastle.math.ec.ECFieldElement r12 = r12.b()
            org.spongycastle.math.ec.custom.sec.SecT571FieldElement r12 = (org.spongycastle.math.ec.custom.sec.SecT571FieldElement) r12
            boolean r26 = r12.i()
            if (r26 == 0) goto L_0x0141
            r26 = r10
            org.spongycastle.math.ec.custom.sec.SecT571R1Point r10 = new org.spongycastle.math.ec.custom.sec.SecT571R1Point
            r27 = r7
            org.spongycastle.math.ec.custom.sec.SecT571FieldElement r7 = org.spongycastle.math.ec.custom.sec.SecT571R1Curve.k
            r28 = r1
            boolean r1 = r0.f
            r10.<init>(r2, r12, r7, r1)
            return r10
        L_0x0141:
            r28 = r1
            r27 = r7
            r26 = r10
            org.spongycastle.math.ec.ECFieldElement r1 = r6.a(r12)
            org.spongycastle.math.ec.ECFieldElement r1 = r11.j(r1)
            org.spongycastle.math.ec.ECFieldElement r1 = r1.a(r12)
            org.spongycastle.math.ec.ECFieldElement r1 = r1.a(r9)
            org.spongycastle.math.ec.ECFieldElement r7 = r1.d(r12)
            org.spongycastle.math.ec.ECFieldElement r7 = r7.a(r12)
            org.spongycastle.math.ec.custom.sec.SecT571FieldElement r7 = (org.spongycastle.math.ec.custom.sec.SecT571FieldElement) r7
            java.math.BigInteger r10 = org.spongycastle.math.ec.ECConstants.b
            org.spongycastle.math.ec.ECFieldElement r10 = r2.m(r10)
            r1 = r10
            org.spongycastle.math.ec.custom.sec.SecT571FieldElement r1 = (org.spongycastle.math.ec.custom.sec.SecT571FieldElement) r1
            r9 = r1
            r18 = r6
            r10 = r7
            r1 = r20
            r11 = r25
            r29 = r27
            r25 = r4
            r27 = r26
            r26 = r3
            goto L_0x020b
        L_0x017c:
            r28 = r1
            r23 = r6
            r27 = r7
            r22 = r9
            r26 = r10
            r25 = r11
            r24 = r12
            org.spongycastle.math.ec.custom.sec.SecT571Field.t(r13, r13)
            long[] r1 = org.spongycastle.math.ec.custom.sec.SecT571Field.p(r4)
            r6 = r14
            r7 = r15
            org.spongycastle.math.ec.custom.sec.SecT571Field.n(r3, r1, r6)
            org.spongycastle.math.ec.custom.sec.SecT571Field.n(r5, r1, r7)
            org.spongycastle.math.ec.custom.sec.SecT571FieldElement r9 = new org.spongycastle.math.ec.custom.sec.SecT571FieldElement
            r9.<init>((long[]) r14)
            r12 = r9
            long[] r9 = r12.g
            org.spongycastle.math.ec.custom.sec.SecT571Field.l(r6, r7, r9)
            boolean r9 = r12.i()
            if (r9 == 0) goto L_0x01b4
            org.spongycastle.math.ec.custom.sec.SecT571R1Point r9 = new org.spongycastle.math.ec.custom.sec.SecT571R1Point
            org.spongycastle.math.ec.custom.sec.SecT571FieldElement r10 = org.spongycastle.math.ec.custom.sec.SecT571R1Curve.k
            boolean r11 = r0.f
            r9.<init>(r2, r12, r10, r11)
            return r9
        L_0x01b4:
            org.spongycastle.math.ec.custom.sec.SecT571FieldElement r9 = new org.spongycastle.math.ec.custom.sec.SecT571FieldElement
            r9.<init>((long[]) r8)
            long[] r10 = r9.g
            org.spongycastle.math.ec.custom.sec.SecT571Field.n(r13, r1, r10)
            if (r28 == 0) goto L_0x01c8
            long[] r10 = r9.g
            r11 = r28
            org.spongycastle.math.ec.custom.sec.SecT571Field.n(r10, r11, r10)
            goto L_0x01ca
        L_0x01c8:
            r11 = r28
        L_0x01ca:
            long[] r10 = org.spongycastle.math.raw.Nat576.b()
            r19 = r1
            r1 = r27
            org.spongycastle.math.ec.custom.sec.SecT571Field.b(r7, r13, r1)
            org.spongycastle.math.ec.custom.sec.SecT571Field.u(r1, r10)
            r28 = r11
            r11 = r26
            r26 = r3
            long[] r3 = r11.g
            r27 = r11
            r11 = r25
            r25 = r4
            long[] r4 = r11.g
            org.spongycastle.math.ec.custom.sec.SecT571Field.b(r3, r4, r1)
            long[] r3 = r9.g
            org.spongycastle.math.ec.custom.sec.SecT571Field.m(r1, r3, r10)
            org.spongycastle.math.ec.custom.sec.SecT571FieldElement r3 = new org.spongycastle.math.ec.custom.sec.SecT571FieldElement
            r3.<init>((long[]) r1)
            long[] r4 = r3.g
            org.spongycastle.math.ec.custom.sec.SecT571Field.q(r10, r4)
            if (r20 == 0) goto L_0x0206
            long[] r4 = r9.g
            r29 = r1
            r1 = r20
            org.spongycastle.math.ec.custom.sec.SecT571Field.n(r4, r1, r4)
            goto L_0x020a
        L_0x0206:
            r29 = r1
            r1 = r20
        L_0x020a:
            r10 = r3
        L_0x020b:
            org.spongycastle.math.ec.custom.sec.SecT571R1Point r19 = new org.spongycastle.math.ec.custom.sec.SecT571R1Point
            r3 = 1
            org.spongycastle.math.ec.ECFieldElement[] r7 = new org.spongycastle.math.ec.ECFieldElement[r3]
            r3 = 0
            r7[r3] = r9
            boolean r6 = r0.f
            r17 = r26
            r3 = r19
            r20 = r25
            r4 = r2
            r25 = r5
            r5 = r12
            r26 = r6
            r6 = r10
            r30 = r8
            r8 = r26
            r3.<init>(r4, r5, r6, r7, r8)
            return r19
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.math.ec.custom.sec.SecT571R1Point.a(org.spongycastle.math.ec.ECPoint):org.spongycastle.math.ec.ECPoint");
    }

    public ECPoint H() {
        long[] Z1Sq;
        long[] L1Z1;
        long[] X1Z1;
        if (t()) {
            return this;
        }
        ECCurve curve = i();
        SecT571FieldElement X1 = (SecT571FieldElement) this.c;
        if (X1.i()) {
            return curve.u();
        }
        SecT571FieldElement L1 = (SecT571FieldElement) this.d;
        SecT571FieldElement Z1 = (SecT571FieldElement) this.e[0];
        long[] t1 = Nat576.a();
        long[] t2 = Nat576.a();
        long[] Z1Precomp = Z1.h() ? null : SecT571Field.p(Z1.g);
        if (Z1Precomp == null) {
            L1Z1 = L1.g;
            Z1Sq = Z1.g;
        } else {
            SecT571Field.n(L1.g, Z1Precomp, t1);
            SecT571Field.t(Z1.g, t2);
            L1Z1 = t1;
            Z1Sq = t2;
        }
        long[] T = Nat576.a();
        SecT571Field.t(L1.g, T);
        SecT571Field.d(L1Z1, Z1Sq, T);
        if (Nat576.f(T)) {
            return new SecT571R1Point(curve, new SecT571FieldElement(T), SecT571R1Curve.k, this.f);
        }
        long[] tt = Nat576.b();
        SecT571Field.m(T, L1Z1, tt);
        SecT571FieldElement X3 = new SecT571FieldElement(t1);
        SecT571Field.t(T, X3.g);
        SecT571FieldElement Z3 = new SecT571FieldElement(T);
        if (Z1Precomp != null) {
            long[] jArr = Z3.g;
            SecT571Field.l(jArr, Z1Sq, jArr);
        }
        if (Z1Precomp == null) {
            X1Z1 = X1.g;
        } else {
            SecT571Field.n(X1.g, Z1Precomp, t2);
            X1Z1 = t2;
        }
        SecT571Field.u(X1Z1, tt);
        SecT571Field.q(tt, t2);
        SecT571FieldElement X32 = X3;
        SecT571Field.d(X3.g, Z3.g, t2);
        long[] jArr2 = X1Z1;
        SecT571FieldElement secT571FieldElement = Z3;
        long[] jArr3 = tt;
        long[] jArr4 = T;
        return new SecT571R1Point(curve, X32, new SecT571FieldElement(t2), new ECFieldElement[]{Z3}, this.f);
    }

    public ECPoint I(ECPoint b) {
        ECPoint eCPoint = b;
        if (t()) {
            return eCPoint;
        }
        if (b.t()) {
            return H();
        }
        ECCurve curve = i();
        SecT571FieldElement X1 = (SecT571FieldElement) this.c;
        if (X1.i()) {
            return eCPoint;
        }
        SecT571FieldElement X2 = (SecT571FieldElement) b.n();
        SecT571FieldElement Z2 = (SecT571FieldElement) eCPoint.s(0);
        if (X2.i()) {
            SecT571FieldElement secT571FieldElement = X2;
            SecT571FieldElement secT571FieldElement2 = Z2;
        } else if (!Z2.h()) {
            SecT571FieldElement secT571FieldElement3 = X1;
            SecT571FieldElement secT571FieldElement4 = X2;
            SecT571FieldElement secT571FieldElement5 = Z2;
        } else {
            SecT571FieldElement L1 = (SecT571FieldElement) this.d;
            SecT571FieldElement Z1 = (SecT571FieldElement) this.e[0];
            SecT571FieldElement L2 = (SecT571FieldElement) b.o();
            long[] t1 = Nat576.a();
            long[] t2 = Nat576.a();
            long[] t3 = Nat576.a();
            long[] t4 = Nat576.a();
            long[] X1Sq = t1;
            SecT571Field.t(X1.g, X1Sq);
            long[] L1Sq = t2;
            SecT571Field.t(L1.g, L1Sq);
            long[] Z1Sq = t3;
            SecT571Field.t(Z1.g, Z1Sq);
            long[] L1Z1 = t4;
            SecT571FieldElement secT571FieldElement6 = X1;
            SecT571FieldElement secT571FieldElement7 = Z2;
            SecT571Field.l(L1.g, Z1.g, L1Z1);
            long[] T = L1Z1;
            SecT571Field.d(Z1Sq, L1Sq, T);
            long[] Z1SqPrecomp = SecT571Field.p(Z1Sq);
            long[] jArr = L1Z1;
            SecT571FieldElement secT571FieldElement8 = L1;
            long[] A = t3;
            SecT571Field.n(L2.g, Z1SqPrecomp, A);
            SecT571Field.b(A, L1Sq, A);
            long[] tt = Nat576.b();
            SecT571Field.m(A, T, tt);
            SecT571Field.o(X1Sq, Z1SqPrecomp, tt);
            SecT571Field.q(tt, A);
            long[] jArr2 = Z1Sq;
            long[] Z1Sq2 = X2.g;
            SecT571FieldElement secT571FieldElement9 = X2;
            long[] X2Z1Sq = t1;
            SecT571Field.n(Z1Sq2, Z1SqPrecomp, X2Z1Sq);
            long[] B = t2;
            SecT571Field.b(X2Z1Sq, T, B);
            SecT571Field.t(B, B);
            if (Nat576.f(B)) {
                if (Nat576.f(A)) {
                    return b.H();
                }
                return curve.u();
            } else if (Nat576.f(A)) {
                long[] jArr3 = L1Sq;
                long[] jArr4 = X1Sq;
                SecT571FieldElement secT571FieldElement10 = Z1;
                return new SecT571R1Point(curve, new SecT571FieldElement(A), SecT571R1Curve.k, this.f);
            } else {
                long[] jArr5 = X1Sq;
                SecT571FieldElement secT571FieldElement11 = Z1;
                SecT571FieldElement X3 = new SecT571FieldElement();
                SecT571Field.t(A, X3.g);
                long[] jArr6 = X3.g;
                SecT571Field.l(jArr6, X2Z1Sq, jArr6);
                SecT571FieldElement Z3 = new SecT571FieldElement(t1);
                SecT571Field.l(A, B, Z3.g);
                long[] jArr7 = Z3.g;
                SecT571Field.n(jArr7, Z1SqPrecomp, jArr7);
                SecT571FieldElement L3 = new SecT571FieldElement(t2);
                SecT571Field.b(A, B, L3.g);
                long[] jArr8 = L3.g;
                SecT571Field.t(jArr8, jArr8);
                Nat.Q(18, tt);
                SecT571Field.m(L3.g, T, tt);
                SecT571Field.f(L2.g, t4);
                SecT571Field.m(t4, Z3.g, tt);
                SecT571Field.q(tt, L3.g);
                long[] jArr9 = t2;
                long[] jArr10 = tt;
                long[] jArr11 = B;
                ECFieldElement[] eCFieldElementArr = {Z3};
                SecT571FieldElement secT571FieldElement12 = L3;
                long[] jArr12 = t4;
                return new SecT571R1Point(curve, X3, L3, eCFieldElementArr, this.f);
            }
        }
        return H().a(b);
    }

    public ECPoint x() {
        if (t()) {
            return this;
        }
        ECFieldElement X = this.c;
        if (X.i()) {
            return this;
        }
        ECFieldElement L = this.d;
        ECFieldElement Z = this.e[0];
        ECCurve eCCurve = this.b;
        return new SecT571R1Point(eCCurve, X, L.a(Z), new ECFieldElement[]{Z}, this.f);
    }
}
