package org.spongycastle.math.ec.custom.sec;

import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;

public class SecT571K1Point extends ECPoint.AbstractF2m {
    public SecT571K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y) {
        this(curve, x, y, false);
    }

    public SecT571K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression) {
        super(curve, x, y);
        if ((x == null) == (y != null ? false : true)) {
            this.f = withCompression;
            return;
        }
        throw new IllegalArgumentException("Exactly one of the field elements is null");
    }

    SecT571K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        super(curve, x, y, zs);
        this.f = withCompression;
    }

    /* access modifiers changed from: protected */
    public ECPoint d() {
        return new SecT571K1Point((ECCurve) null, f(), g());
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
            if (r19 == 0) goto L_0x017a
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
            org.spongycastle.math.ec.custom.sec.SecT571FieldElement r12 = (org.spongycastle.math.ec.custom.sec.SecT571FieldElement) r12
            boolean r26 = r12.i()
            if (r26 == 0) goto L_0x013f
            r26 = r10
            org.spongycastle.math.ec.custom.sec.SecT571K1Point r10 = new org.spongycastle.math.ec.custom.sec.SecT571K1Point
            r27 = r7
            org.spongycastle.math.ec.ECFieldElement r7 = r2.o()
            r28 = r1
            boolean r1 = r0.f
            r10.<init>(r2, r12, r7, r1)
            return r10
        L_0x013f:
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
        L_0x017a:
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
            org.spongycastle.math.ec.custom.sec.SecT571K1Point r9 = new org.spongycastle.math.ec.custom.sec.SecT571K1Point
            org.spongycastle.math.ec.ECFieldElement r10 = r2.o()
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
            org.spongycastle.math.ec.custom.sec.SecT571K1Point r19 = new org.spongycastle.math.ec.custom.sec.SecT571K1Point
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
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.math.ec.custom.sec.SecT571K1Point.a(org.spongycastle.math.ec.ECPoint):org.spongycastle.math.ec.ECPoint");
    }

    public ECPoint H() {
        ECFieldElement T;
        if (t()) {
            return this;
        }
        ECCurve curve = i();
        ECFieldElement X1 = this.c;
        if (X1.i()) {
            return curve.u();
        }
        ECFieldElement L1 = this.d;
        ECFieldElement Z1 = this.e[0];
        boolean Z1IsOne = Z1.h();
        ECFieldElement Z1Sq = Z1IsOne ? Z1 : Z1.o();
        if (Z1IsOne) {
            T = L1.o().a(L1);
        } else {
            T = L1.a(Z1).j(L1);
        }
        if (T.i()) {
            return new SecT571K1Point(curve, T, curve.o(), this.f);
        }
        ECFieldElement X3 = T.o();
        ECFieldElement Z3 = Z1IsOne ? T : T.j(Z1Sq);
        ECFieldElement t1 = L1.a(X1).o();
        ECFieldElement t2 = Z1IsOne ? Z1 : Z1Sq.o();
        ECFieldElement eCFieldElement = t2;
        ECFieldElement eCFieldElement2 = t1;
        return new SecT571K1Point(curve, X3, t1.a(T).a(Z1Sq).j(t1).a(t2).a(X3).a(Z3), new ECFieldElement[]{Z3}, this.f);
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
        ECFieldElement X1 = this.c;
        if (X1.i()) {
            return eCPoint;
        }
        ECFieldElement X2 = b.n();
        ECFieldElement Z2 = eCPoint.s(0);
        if (X2.i()) {
            ECFieldElement eCFieldElement = X2;
            ECFieldElement eCFieldElement2 = Z2;
        } else if (!Z2.h()) {
            ECFieldElement eCFieldElement3 = X1;
            ECFieldElement eCFieldElement4 = X2;
            ECFieldElement eCFieldElement5 = Z2;
        } else {
            ECFieldElement L1 = this.d;
            ECFieldElement Z1 = this.e[0];
            ECFieldElement L2 = b.o();
            ECFieldElement X1Sq = X1.o();
            ECFieldElement L1Sq = L1.o();
            ECFieldElement Z1Sq = Z1.o();
            ECFieldElement L1Z1 = L1.j(Z1);
            ECFieldElement T = L1Sq.a(L1Z1);
            ECFieldElement L2plus1 = L2.b();
            ECFieldElement A = L2plus1.j(Z1Sq).a(L1Sq).l(T, X1Sq, Z1Sq);
            ECFieldElement eCFieldElement6 = X1;
            ECFieldElement X12 = X2.j(Z1Sq);
            ECFieldElement eCFieldElement7 = X2;
            ECFieldElement B = X12.a(T).o();
            if (B.i()) {
                if (A.i()) {
                    return b.H();
                }
                return curve.u();
            } else if (A.i()) {
                ECFieldElement eCFieldElement8 = L1Z1;
                ECFieldElement eCFieldElement9 = L1Sq;
                ECFieldElement eCFieldElement10 = Z2;
                return new SecT571K1Point(curve, A, curve.o(), this.f);
            } else {
                ECFieldElement L1Sq2 = L1Sq;
                ECFieldElement eCFieldElement11 = Z2;
                ECFieldElement X3 = A.o().j(X12);
                ECFieldElement Z3 = A.j(B).j(Z1Sq);
                ECFieldElement L3 = A.a(B).o().l(T, L2plus1, Z3);
                ECFieldElement eCFieldElement12 = A;
                ECFieldElement eCFieldElement13 = L2plus1;
                ECFieldElement eCFieldElement14 = T;
                ECFieldElement[] eCFieldElementArr = {Z3};
                ECFieldElement eCFieldElement15 = Z1Sq;
                ECFieldElement eCFieldElement16 = L1Sq2;
                ECFieldElement L1Sq3 = Z3;
                ECFieldElement Z32 = eCFieldElement16;
                return new SecT571K1Point(curve, X3, L3, eCFieldElementArr, this.f);
            }
        }
        return H().a(eCPoint);
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
        return new SecT571K1Point(eCCurve, X, L.a(Z), new ECFieldElement[]{Z}, this.f);
    }
}
