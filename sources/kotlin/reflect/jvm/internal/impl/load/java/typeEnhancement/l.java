package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.r;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.load.java.a;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.i;
import kotlin.reflect.jvm.internal.impl.load.java.t;
import kotlin.reflect.jvm.internal.impl.load.java.x;
import kotlin.reflect.jvm.internal.impl.resolve.constants.g;
import kotlin.reflect.jvm.internal.impl.resolve.constants.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.impl.types.g1;
import kotlin.reflect.jvm.internal.impl.types.v;
import kotlin.reflect.jvm.internal.impl.types.y;
import kotlin.reflect.jvm.internal.impl.utils.h;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: signatureEnhancement.kt */
public final class l {
    private final kotlin.reflect.jvm.internal.impl.load.java.a a;
    private final kotlin.reflect.jvm.internal.impl.utils.e b;

    public l(@NotNull kotlin.reflect.jvm.internal.impl.load.java.a annotationTypeQualifierResolver, @NotNull kotlin.reflect.jvm.internal.impl.utils.e jsr305State) {
        k.f(annotationTypeQualifierResolver, "annotationTypeQualifierResolver");
        k.f(jsr305State, "jsr305State");
        this.a = annotationTypeQualifierResolver;
        this.b = jsr305State;
    }

    private final h e(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.c $this$extractNullabilityTypeFromArgument) {
        g<?> c2 = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.c($this$extractNullabilityTypeFromArgument);
        if (!(c2 instanceof j)) {
            c2 = null;
        }
        j enumValue = (j) c2;
        if (enumValue == null) {
            return new h(g.NOT_NULL, false, 2, (DefaultConstructorMarker) null);
        }
        String b2 = enumValue.c().b();
        switch (b2.hashCode()) {
            case 73135176:
                if (!b2.equals("MAYBE")) {
                    return null;
                }
                break;
            case 74175084:
                if (!b2.equals("NEVER")) {
                    return null;
                }
                break;
            case 433141802:
                if (b2.equals(LDNetUtil.NETWORKTYPE_INVALID)) {
                    return new h(g.FORCE_FLEXIBILITY, false, 2, (DefaultConstructorMarker) null);
                }
                return null;
            case 1933739535:
                if (b2.equals("ALWAYS")) {
                    return new h(g.NOT_NULL, false, 2, (DefaultConstructorMarker) null);
                }
                return null;
            default:
                return null;
        }
        return new h(g.NULLABLE, false, 2, (DefaultConstructorMarker) null);
    }

    @Nullable
    public final h c(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.c annotationDescriptor) {
        h d2;
        k.f(annotationDescriptor, "annotationDescriptor");
        h it = d(annotationDescriptor);
        if (it != null) {
            return it;
        }
        kotlin.reflect.jvm.internal.impl.descriptors.annotations.c typeQualifierAnnotation = this.a.i(annotationDescriptor);
        if (typeQualifierAnnotation == null) {
            return null;
        }
        h jsr305State = this.a.f(annotationDescriptor);
        if (!jsr305State.isIgnore() && (d2 = d(typeQualifierAnnotation)) != null) {
            return h.b(d2, (g) null, jsr305State.isWarning(), 1, (Object) null);
        }
        return null;
    }

    private final h d(kotlin.reflect.jvm.internal.impl.descriptors.annotations.c annotationDescriptor) {
        h migrationStatus;
        kotlin.reflect.jvm.internal.impl.name.b annotationFqName = annotationDescriptor.e();
        if (annotationFqName == null) {
            return null;
        }
        if (t.i().contains(annotationFqName)) {
            migrationStatus = new h(g.NULLABLE, false, 2, (DefaultConstructorMarker) null);
        } else if (t.h().contains(annotationFqName)) {
            migrationStatus = new h(g.NOT_NULL, false, 2, (DefaultConstructorMarker) null);
        } else if (k.a(annotationFqName, t.f())) {
            migrationStatus = e(annotationDescriptor);
        } else if (k.a(annotationFqName, t.d()) && this.b.b()) {
            migrationStatus = new h(g.NULLABLE, false, 2, (DefaultConstructorMarker) null);
        } else if (k.a(annotationFqName, t.c()) && this.b.b()) {
            migrationStatus = new h(g.NOT_NULL, false, 2, (DefaultConstructorMarker) null);
        } else if (k.a(annotationFqName, t.a())) {
            migrationStatus = new h(g.NOT_NULL, true);
        } else if (k.a(annotationFqName, t.b())) {
            migrationStatus = new h(g.NULLABLE, true);
        } else {
            migrationStatus = null;
        }
        if (migrationStatus == null) {
            return null;
        }
        if (migrationStatus.d() || !(annotationDescriptor instanceof i) || !((i) annotationDescriptor).h()) {
            return migrationStatus;
        }
        return h.b(migrationStatus, (g) null, true, 1, (Object) null);
    }

    @NotNull
    public final <D extends kotlin.reflect.jvm.internal.impl.descriptors.b> Collection<D> b(@NotNull kotlin.reflect.jvm.internal.impl.load.java.lazy.h c2, @NotNull Collection<? extends D> platformSignatures) {
        k.f(c2, "c");
        k.f(platformSignatures, "platformSignatures");
        Iterable<kotlin.reflect.jvm.internal.impl.descriptors.b> $this$mapTo$iv$iv = platformSignatures;
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (kotlin.reflect.jvm.internal.impl.descriptors.b it : $this$mapTo$iv$iv) {
            destination$iv$iv.add(a(it, c2));
        }
        return destination$iv$iv;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:109:0x0265, code lost:
        if (r13.c() != true) goto L_0x0269;
     */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x0259  */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x0260  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x0268  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x026f  */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x02a2  */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x02b0  */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x02b9  */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x02be  */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x02d9 A[LOOP:3: B:134:0x02d3->B:136:0x02d9, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x030d A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x030e  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0155  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x01f1  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x01f3  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x01ff  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x0203  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0216  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x021b  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0239  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x023b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final <D extends kotlin.reflect.jvm.internal.impl.descriptors.b> D a(@org.jetbrains.annotations.NotNull D r28, kotlin.reflect.jvm.internal.impl.load.java.lazy.h r29) {
        /*
            r27 = this;
            r7 = r27
            r8 = r28
            boolean r0 = r8 instanceof kotlin.reflect.jvm.internal.impl.load.java.descriptors.b
            if (r0 != 0) goto L_0x0009
            return r8
        L_0x0009:
            r0 = r8
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.b r0 = (kotlin.reflect.jvm.internal.impl.load.java.descriptors.b) r0
            kotlin.reflect.jvm.internal.impl.descriptors.b$a r0 = r0.h()
            kotlin.reflect.jvm.internal.impl.descriptors.b$a r1 = kotlin.reflect.jvm.internal.impl.descriptors.b.a.FAKE_OVERRIDE
            r9 = 1
            if (r0 != r1) goto L_0x002d
            r0 = r8
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.b r0 = (kotlin.reflect.jvm.internal.impl.load.java.descriptors.b) r0
            kotlin.reflect.jvm.internal.impl.descriptors.b r0 = r0.a()
            java.lang.String r1 = "original"
            kotlin.jvm.internal.k.b(r0, r1)
            java.util.Collection r0 = r0.d()
            int r0 = r0.size()
            if (r0 != r9) goto L_0x002d
            return r8
        L_0x002d:
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r0 = r28.getAnnotations()
            r10 = r29
            kotlin.reflect.jvm.internal.impl.load.java.lazy.h r11 = kotlin.reflect.jvm.internal.impl.load.java.lazy.a.h(r10, r0)
            boolean r0 = r8 instanceof kotlin.reflect.jvm.internal.impl.load.java.descriptors.g
            if (r0 == 0) goto L_0x005e
            r0 = r8
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.g r0 = (kotlin.reflect.jvm.internal.impl.load.java.descriptors.g) r0
            kotlin.reflect.jvm.internal.impl.descriptors.impl.c0 r0 = r0.getGetter()
            if (r0 == 0) goto L_0x005e
            boolean r0 = r0.D()
            if (r0 != 0) goto L_0x005e
            r0 = r8
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.g r0 = (kotlin.reflect.jvm.internal.impl.load.java.descriptors.g) r0
            kotlin.reflect.jvm.internal.impl.descriptors.impl.c0 r0 = r0.getGetter()
            if (r0 != 0) goto L_0x0057
            kotlin.jvm.internal.k.n()
        L_0x0057:
            java.lang.String r1 = "getter!!"
            kotlin.jvm.internal.k.b(r0, r1)
            r2 = r0
            goto L_0x005f
        L_0x005e:
            r2 = r8
        L_0x005f:
            r0 = r8
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.b r0 = (kotlin.reflect.jvm.internal.impl.load.java.descriptors.b) r0
            kotlin.reflect.jvm.internal.impl.descriptors.l0 r0 = r0.L()
            r12 = 0
            if (r0 == 0) goto L_0x0094
            r0 = r2
            r1 = 0
            boolean r3 = r0 instanceof kotlin.reflect.jvm.internal.impl.descriptors.u
            if (r3 != 0) goto L_0x0079
            r3 = r12
            goto L_0x007a
        L_0x0079:
            r3 = r0
        L_0x007a:
            kotlin.reflect.jvm.internal.impl.descriptors.u r3 = (kotlin.reflect.jvm.internal.impl.descriptors.u) r3
            if (r3 == 0) goto L_0x0087
            kotlin.reflect.jvm.internal.impl.descriptors.a$a<kotlin.reflect.jvm.internal.impl.descriptors.w0> r0 = kotlin.reflect.jvm.internal.impl.load.java.descriptors.f.O4
            java.lang.Object r0 = r3.q0(r0)
            kotlin.reflect.jvm.internal.impl.descriptors.w0 r0 = (kotlin.reflect.jvm.internal.impl.descriptors.w0) r0
            goto L_0x0088
        L_0x0087:
            r0 = r12
        L_0x0088:
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$d r1 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l.d.INSTANCE
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$b r0 = r7.h(r8, r0, r11, r1)
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$a r0 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l.b.d(r0, r12, r9, r12)
            goto L_0x0095
        L_0x0094:
            r0 = r12
        L_0x0095:
            r13 = r0
            boolean r0 = r8 instanceof kotlin.reflect.jvm.internal.impl.load.java.descriptors.f
            if (r0 != 0) goto L_0x00a0
            r0 = r12
            goto L_0x00a1
        L_0x00a0:
            r0 = r8
        L_0x00a1:
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.f r0 = (kotlin.reflect.jvm.internal.impl.load.java.descriptors.f) r0
            r14 = 0
            if (r0 == 0) goto L_0x00d2
            r1 = 0
            kotlin.reflect.jvm.internal.impl.load.kotlin.v r3 = kotlin.reflect.jvm.internal.impl.load.kotlin.v.a
            kotlin.reflect.jvm.internal.impl.descriptors.m r4 = r0.b()
            if (r4 == 0) goto L_0x00ca
            kotlin.reflect.jvm.internal.impl.descriptors.e r4 = (kotlin.reflect.jvm.internal.impl.descriptors.e) r4
            r5 = 3
            java.lang.String r5 = kotlin.reflect.jvm.internal.impl.load.kotlin.t.c(r0, r14, r14, r5, r12)
            java.lang.String r0 = r3.l(r4, r5)
            if (r0 == 0) goto L_0x00d2
            r1 = 0
            java.util.Map r3 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.i.d()
            java.lang.Object r3 = r3.get(r0)
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.j r3 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.j) r3
            goto L_0x00d3
        L_0x00ca:
            kotlin.TypeCastException r3 = new kotlin.TypeCastException
            java.lang.String r4 = "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor"
            r3.<init>(r4)
            throw r3
        L_0x00d2:
            r3 = r12
        L_0x00d3:
            r15 = r3
            if (r15 == 0) goto L_0x0134
            r0 = r15
            r1 = 0
            java.util.List r3 = r0.a()
            int r3 = r3.size()
            r4 = r8
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.b r4 = (kotlin.reflect.jvm.internal.impl.load.java.descriptors.b) r4
            java.util.List r4 = r4.g()
            int r4 = r4.size()
            if (r3 != r4) goto L_0x00ef
            r3 = r9
            goto L_0x00f0
        L_0x00ef:
            r3 = r14
        L_0x00f0:
            if (r3 == 0) goto L_0x00f4
            goto L_0x0134
        L_0x00f4:
            r3 = 0
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Predefined enhancement info for "
            r4.append(r5)
            r4.append(r8)
            java.lang.String r5 = " has "
            r4.append(r5)
            java.util.List r5 = r0.a()
            int r5 = r5.size()
            r4.append(r5)
            java.lang.String r5 = ", but "
            r4.append(r5)
            r5 = r8
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.b r5 = (kotlin.reflect.jvm.internal.impl.load.java.descriptors.b) r5
            java.util.List r5 = r5.g()
            int r5 = r5.size()
            r4.append(r5)
            java.lang.String r5 = " expected"
            r4.append(r5)
            java.lang.String r3 = r4.toString()
            java.lang.AssertionError r4 = new java.lang.AssertionError
            r4.<init>(r3)
            throw r4
        L_0x0134:
            java.util.List r0 = r2.g()
            java.lang.String r1 = "annotationOwnerForMember.valueParameters"
            kotlin.jvm.internal.k.b(r0, r1)
            r1 = 0
            java.util.ArrayList r3 = new java.util.ArrayList
            r6 = 10
            int r4 = kotlin.collections.r.r(r0, r6)
            r3.<init>(r4)
            r4 = r0
            r5 = 0
            java.util.Iterator r16 = r4.iterator()
        L_0x014f:
            boolean r17 = r16.hasNext()
            if (r17 == 0) goto L_0x01dd
            java.lang.Object r17 = r16.next()
            r6 = r17
            kotlin.reflect.jvm.internal.impl.descriptors.w0 r6 = (kotlin.reflect.jvm.internal.impl.descriptors.w0) r6
            r19 = 0
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$f r12 = new kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$f
            r12.<init>(r6)
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$b r12 = r7.h(r8, r6, r11, r12)
            if (r15 == 0) goto L_0x017d
            java.util.List r14 = r15.a()
            if (r14 == 0) goto L_0x017d
            int r9 = r6.getIndex()
            java.lang.Object r9 = kotlin.collections.y.V(r14, r9)
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.r r9 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.r) r9
            goto L_0x017e
        L_0x017d:
            r9 = 0
        L_0x017e:
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$a r9 = r12.c(r9)
            boolean r12 = r9.c()
            java.lang.String r14 = "p"
            if (r12 == 0) goto L_0x0193
            kotlin.reflect.jvm.internal.impl.types.b0 r12 = r9.b()
            r23 = r0
            goto L_0x01a2
        L_0x0193:
            kotlin.jvm.internal.k.b(r6, r14)
            kotlin.reflect.jvm.internal.impl.types.b0 r12 = r6.getType()
            r23 = r0
            java.lang.String r0 = "p.type"
            kotlin.jvm.internal.k.b(r12, r0)
        L_0x01a2:
            r0 = r12
            kotlin.jvm.internal.k.b(r6, r14)
            boolean r12 = r7.f(r6, r0)
            boolean r14 = r9.c()
            if (r14 != 0) goto L_0x01b9
            boolean r14 = r6.v0()
            if (r12 == r14) goto L_0x01b7
            goto L_0x01b9
        L_0x01b7:
            r14 = 0
            goto L_0x01ba
        L_0x01b9:
            r14 = 1
        L_0x01ba:
            r24 = r0
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$c r0 = new kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$c
            r25 = r1
            kotlin.reflect.jvm.internal.impl.types.b0 r1 = r9.b()
            r26 = r4
            boolean r4 = r9.a()
            r0.<init>(r1, r12, r14, r4)
            r3.add(r0)
            r0 = r23
            r1 = r25
            r4 = r26
            r6 = 10
            r9 = 1
            r12 = 0
            r14 = 0
            goto L_0x014f
        L_0x01dd:
            r23 = r0
            r25 = r1
            r26 = r4
            r9 = r3
            r3 = 1
            r0 = r28
            r1 = 0
            boolean r4 = r0 instanceof kotlin.reflect.jvm.internal.impl.descriptors.i0
            if (r4 != 0) goto L_0x01f3
            r4 = 0
            goto L_0x01f4
        L_0x01f3:
            r4 = r0
        L_0x01f4:
            kotlin.reflect.jvm.internal.impl.descriptors.i0 r4 = (kotlin.reflect.jvm.internal.impl.descriptors.i0) r4
            if (r4 == 0) goto L_0x0203
            boolean r0 = kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.c.a(r4)
            r1 = 1
            if (r0 != r1) goto L_0x0203
            kotlin.reflect.jvm.internal.impl.load.java.a$a r0 = kotlin.reflect.jvm.internal.impl.load.java.a.C0356a.FIELD
            r5 = r0
            goto L_0x0206
        L_0x0203:
            kotlin.reflect.jvm.internal.impl.load.java.a$a r0 = kotlin.reflect.jvm.internal.impl.load.java.a.C0356a.METHOD_RETURN_TYPE
            r5 = r0
        L_0x0206:
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$e r6 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l.e.INSTANCE
            r0 = r27
            r1 = r28
            r4 = r11
            r12 = 10
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$b r0 = r0.g(r1, r2, r3, r4, r5, r6)
            if (r15 == 0) goto L_0x021b
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.r r1 = r15.b()
            goto L_0x021c
        L_0x021b:
            r1 = 0
        L_0x021c:
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$a r0 = r0.c(r1)
            if (r13 == 0) goto L_0x022b
            boolean r1 = r13.a()
            r3 = 1
            if (r1 == r3) goto L_0x025b
        L_0x022b:
            boolean r1 = r0.a()
            if (r1 != 0) goto L_0x025b
            r1 = r9
            r3 = 0
            boolean r4 = r1.isEmpty()
            if (r4 == 0) goto L_0x023b
            r1 = 0
            goto L_0x0256
        L_0x023b:
            java.util.Iterator r4 = r1.iterator()
        L_0x023f:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x0255
            java.lang.Object r5 = r4.next()
            r6 = r5
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$c r6 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l.c) r6
            r14 = 0
            boolean r6 = r6.a()
            if (r6 == 0) goto L_0x023f
            r1 = 1
            goto L_0x0256
        L_0x0255:
            r1 = 0
        L_0x0256:
            if (r1 == 0) goto L_0x0259
            goto L_0x025b
        L_0x0259:
            r1 = 0
            goto L_0x025c
        L_0x025b:
            r1 = 1
        L_0x025c:
            if (r13 == 0) goto L_0x0268
            boolean r3 = r13.c()
            r4 = 1
            if (r3 == r4) goto L_0x029f
            goto L_0x0269
        L_0x0268:
            r4 = 1
        L_0x0269:
            boolean r3 = r0.c()
            if (r3 != 0) goto L_0x029f
            r3 = r9
            r5 = 0
            boolean r6 = r3.isEmpty()
            if (r6 == 0) goto L_0x027a
            r21 = 0
            goto L_0x0299
        L_0x027a:
            java.util.Iterator r6 = r3.iterator()
        L_0x027e:
            boolean r14 = r6.hasNext()
            if (r14 == 0) goto L_0x0297
            java.lang.Object r14 = r6.next()
            r16 = r14
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$c r16 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l.c) r16
            r17 = 0
            boolean r16 = r16.c()
            if (r16 == 0) goto L_0x027e
            r21 = r4
            goto L_0x0299
        L_0x0297:
            r21 = 0
        L_0x0299:
            if (r21 != 0) goto L_0x029f
            if (r1 == 0) goto L_0x029e
            goto L_0x029f
        L_0x029e:
            return r8
        L_0x029f:
            if (r1 == 0) goto L_0x02b0
            kotlin.reflect.jvm.internal.impl.descriptors.a$a r3 = kotlin.reflect.jvm.internal.impl.resolve.deprecation.a.a()
            kotlin.reflect.jvm.internal.impl.load.java.g r4 = new kotlin.reflect.jvm.internal.impl.load.java.g
            r4.<init>(r8)
            kotlin.n r3 = kotlin.t.a(r3, r4)
            goto L_0x02b1
        L_0x02b0:
            r3 = 0
        L_0x02b1:
            r4 = r8
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.b r4 = (kotlin.reflect.jvm.internal.impl.load.java.descriptors.b) r4
            if (r13 == 0) goto L_0x02be
            kotlin.reflect.jvm.internal.impl.types.b0 r5 = r13.b()
            goto L_0x02bf
        L_0x02be:
            r5 = 0
        L_0x02bf:
            r6 = r9
            r14 = 0
            r16 = r1
            java.util.ArrayList r1 = new java.util.ArrayList
            int r12 = kotlin.collections.r.r(r6, r12)
            r1.<init>(r12)
            r12 = r6
            r17 = 0
            java.util.Iterator r18 = r12.iterator()
        L_0x02d3:
            boolean r19 = r18.hasNext()
            if (r19 == 0) goto L_0x02fe
            java.lang.Object r19 = r18.next()
            r20 = r19
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$c r20 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l.c) r20
            r21 = 0
            r22 = r2
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.l r2 = new kotlin.reflect.jvm.internal.impl.load.java.descriptors.l
            r23 = r6
            kotlin.reflect.jvm.internal.impl.types.b0 r6 = r20.b()
            boolean r7 = r20.d()
            r2.<init>(r6, r7)
            r1.add(r2)
            r7 = r27
            r2 = r22
            r6 = r23
            goto L_0x02d3
        L_0x02fe:
            r22 = r2
            r23 = r6
            kotlin.reflect.jvm.internal.impl.types.b0 r2 = r0.b()
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.b r1 = r4.U(r5, r1, r2, r3)
            if (r1 == 0) goto L_0x030e
            return r1
        L_0x030e:
            kotlin.TypeCastException r1 = new kotlin.TypeCastException
            java.lang.String r2 = "null cannot be cast to non-null type D"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l.a(kotlin.reflect.jvm.internal.impl.descriptors.b, kotlin.reflect.jvm.internal.impl.load.java.lazy.h):kotlin.reflect.jvm.internal.impl.descriptors.b");
    }

    /* compiled from: signatureEnhancement.kt */
    public static final class d extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.descriptors.b, b0> {
        public static final d INSTANCE = new d();

        d() {
            super(1);
        }

        @NotNull
        public final b0 invoke(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b it) {
            k.f(it, "it");
            l0 L = it.L();
            if (L == null) {
                k.n();
            }
            k.b(L, "it.extensionReceiverParameter!!");
            b0 type = L.getType();
            k.b(type, "it.extensionReceiverParameter!!.type");
            return type;
        }
    }

    /* compiled from: signatureEnhancement.kt */
    public static final class f extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.descriptors.b, b0> {
        final /* synthetic */ w0 $p;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(w0 w0Var) {
            super(1);
            this.$p = w0Var;
        }

        @NotNull
        public final b0 invoke(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b it) {
            k.f(it, "it");
            w0 w0Var = it.g().get(this.$p.getIndex());
            k.b(w0Var, "it.valueParameters[p.index]");
            b0 type = w0Var.getType();
            k.b(type, "it.valueParameters[p.index].type");
            return type;
        }
    }

    /* compiled from: signatureEnhancement.kt */
    public static final class e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.descriptors.b, b0> {
        public static final e INSTANCE = new e();

        e() {
            super(1);
        }

        @NotNull
        public final b0 invoke(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b it) {
            k.f(it, "it");
            b0 returnType = it.getReturnType();
            if (returnType == null) {
                k.n();
            }
            return returnType;
        }
    }

    private final boolean f(@NotNull w0 $this$hasDefaultValueInAnnotation, b0 type) {
        boolean z;
        kotlin.reflect.jvm.internal.impl.load.java.descriptors.a defaultValue = kotlin.reflect.jvm.internal.impl.load.java.descriptors.k.b($this$hasDefaultValueInAnnotation);
        if (defaultValue instanceof kotlin.reflect.jvm.internal.impl.load.java.descriptors.j) {
            z = x.a(type, ((kotlin.reflect.jvm.internal.impl.load.java.descriptors.j) defaultValue).a()) != null;
        } else if (k.a(defaultValue, kotlin.reflect.jvm.internal.impl.load.java.descriptors.h.a)) {
            z = c1.b(type);
        } else if (defaultValue == null) {
            z = $this$hasDefaultValueInAnnotation.v0();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        if (!z || !$this$hasDefaultValueInAnnotation.d().isEmpty()) {
            return false;
        }
        return true;
    }

    /* compiled from: signatureEnhancement.kt */
    public final class b {
        private final kotlin.reflect.jvm.internal.impl.descriptors.annotations.a a;
        private final b0 b;
        private final Collection<b0> c;
        private final boolean d;
        private final kotlin.reflect.jvm.internal.impl.load.java.lazy.h e;
        private final a.C0356a f;
        final /* synthetic */ l g;

        /* compiled from: signatureEnhancement.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Integer, d> {
            final /* synthetic */ d[] $computedResult;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(d[] dVarArr) {
                super(1);
                this.$computedResult = dVarArr;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return invoke(((Number) obj).intValue());
            }

            @NotNull
            public final d invoke(int index) {
                d[] dVarArr = this.$computedResult;
                if (index >= 0 && index <= kotlin.collections.l.y(dVarArr)) {
                    return dVarArr[index];
                }
                int i = index;
                return d.b.a();
            }
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$b$b  reason: collision with other inner class name */
        /* compiled from: signatureEnhancement.kt */
        public static final class C0368b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Integer, d> {
            final /* synthetic */ r $predefined$inlined;
            final /* synthetic */ kotlin.jvm.functions.l $qualifiers$inlined;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0368b(r rVar, kotlin.jvm.functions.l lVar) {
                super(1);
                this.$predefined$inlined = rVar;
                this.$qualifiers$inlined = lVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return invoke(((Number) obj).intValue());
            }

            @NotNull
            public final d invoke(int index) {
                d dVar = this.$predefined$inlined.a().get(Integer.valueOf(index));
                return dVar != null ? dVar : (d) this.$qualifiers$inlined.invoke(Integer.valueOf(index));
            }
        }

        /* compiled from: signatureEnhancement.kt */
        public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<g1, Boolean> {
            public static final c INSTANCE = new c();

            c() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Boolean.valueOf(invoke((g1) obj));
            }

            public final boolean invoke(g1 it) {
                kotlin.reflect.jvm.internal.impl.descriptors.h classifier = it.I0().c();
                if (classifier == null) {
                    return false;
                }
                k.b(classifier, "it.constructor.declarati… ?: return@contains false");
                kotlin.reflect.jvm.internal.impl.name.f name = classifier.getName();
                kotlin.reflect.jvm.internal.impl.builtins.jvm.c cVar = kotlin.reflect.jvm.internal.impl.builtins.jvm.c.m;
                if (!k.a(name, cVar.l().g()) || !k.a(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.f(classifier), cVar.l())) {
                    return false;
                }
                return true;
            }
        }

        /* compiled from: signatureEnhancement.kt */
        public static final class f extends kotlin.jvm.internal.l implements p<b0, kotlin.reflect.jvm.internal.impl.load.java.lazy.h, kotlin.x> {
            final /* synthetic */ ArrayList $list;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            f(ArrayList arrayList) {
                super(2);
                this.$list = arrayList;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                invoke((b0) obj, (kotlin.reflect.jvm.internal.impl.load.java.lazy.h) obj2);
                return kotlin.x.a;
            }

            public final void invoke(@NotNull b0 type, @NotNull kotlin.reflect.jvm.internal.impl.load.java.lazy.h ownerContext) {
                k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
                k.f(ownerContext, "ownerContext");
                kotlin.reflect.jvm.internal.impl.load.java.lazy.h c = kotlin.reflect.jvm.internal.impl.load.java.lazy.a.h(ownerContext, type.getAnnotations());
                ArrayList arrayList = this.$list;
                kotlin.reflect.jvm.internal.impl.load.java.lazy.d b = c.b();
                arrayList.add(new p(type, b != null ? b.a(a.C0356a.TYPE_USE) : null));
                for (kotlin.reflect.jvm.internal.impl.types.w0 arg : type.H0()) {
                    if (arg.b()) {
                        ArrayList arrayList2 = this.$list;
                        b0 type2 = arg.getType();
                        k.b(type2, "arg.type");
                        arrayList2.add(new p(type2, (d) null));
                    } else {
                        b0 type3 = arg.getType();
                        k.b(type3, "arg.type");
                        invoke(type3, c);
                    }
                }
            }
        }

        public b(@Nullable l $outer, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.a typeContainer, @NotNull b0 fromOverride, Collection<? extends b0> fromOverridden, @NotNull boolean isCovariant, @NotNull kotlin.reflect.jvm.internal.impl.load.java.lazy.h containerContext, a.C0356a containerApplicabilityType) {
            k.f(fromOverride, "fromOverride");
            k.f(fromOverridden, "fromOverridden");
            k.f(containerContext, "containerContext");
            k.f(containerApplicabilityType, "containerApplicabilityType");
            this.g = $outer;
            this.a = typeContainer;
            this.b = fromOverride;
            this.c = fromOverridden;
            this.d = isCovariant;
            this.e = containerContext;
            this.f = containerApplicabilityType;
        }

        private final boolean h() {
            Object $this$safeAs$iv = this.a;
            b0 b0Var = null;
            w0 w0Var = (w0) (!($this$safeAs$iv instanceof w0) ? null : $this$safeAs$iv);
            if (w0Var != null) {
                b0Var = w0Var.r0();
            }
            return b0Var != null;
        }

        public static /* synthetic */ a d(b bVar, r rVar, int i, Object obj) {
            if ((i & 1) != 0) {
                rVar = null;
            }
            return bVar.c(rVar);
        }

        @NotNull
        public final a c(@Nullable r predefined) {
            kotlin.jvm.functions.l lVar;
            kotlin.jvm.functions.l qualifiers = a();
            if (predefined != null) {
                r rVar = predefined;
                lVar = new C0368b(predefined, qualifiers);
            } else {
                lVar = null;
            }
            kotlin.jvm.functions.l qualifiersWithPredefined = lVar;
            boolean containsFunctionN = c1.c(this.b, c.INSTANCE);
            b0 enhanced = t.b(this.b, qualifiersWithPredefined != null ? qualifiersWithPredefined : qualifiers);
            if (enhanced != null) {
                return new a(enhanced, true, containsFunctionN);
            }
            return new a(this.b, false, containsFunctionN);
        }

        private final d f(@NotNull b0 $this$extractQualifiers) {
            n nVar;
            g gVar;
            e eVar;
            if (y.b($this$extractQualifiers)) {
                v it = y.a($this$extractQualifiers);
                nVar = new n(it.Q0(), it.R0());
            } else {
                nVar = new n($this$extractQualifiers, $this$extractQualifiers);
            }
            b0 lower = (b0) nVar.component1();
            b0 upper = (b0) nVar.component2();
            kotlin.reflect.jvm.internal.impl.builtins.jvm.c mapping = kotlin.reflect.jvm.internal.impl.builtins.jvm.c.m;
            if (lower.J0()) {
                gVar = g.NULLABLE;
            } else if (!upper.J0()) {
                gVar = g.NOT_NULL;
            } else {
                gVar = null;
            }
            if (mapping.t(lower)) {
                eVar = e.READ_ONLY;
            } else if (mapping.q(upper)) {
                eVar = e.MUTABLE;
            } else {
                eVar = null;
            }
            return new d(gVar, eVar, $this$extractQualifiers.L0() instanceof f, false, 8, (DefaultConstructorMarker) null);
        }

        private final d g(@NotNull b0 $this$extractQualifiersFromAnnotations, boolean isHeadTypeConstructor, d defaultQualifiersForType) {
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g composedAnnotation;
            d defaultTypeQualifier;
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.a aVar;
            if (!isHeadTypeConstructor || (aVar = this.a) == null) {
                composedAnnotation = $this$extractQualifiersFromAnnotations.getAnnotations();
            } else {
                composedAnnotation = kotlin.reflect.jvm.internal.impl.descriptors.annotations.i.a(aVar.getAnnotations(), $this$extractQualifiersFromAnnotations.getAnnotations());
            }
            d $fun$ifPresent$1 = new d(composedAnnotation);
            e $fun$uniqueNotNull$2 = e.INSTANCE;
            g gVar = null;
            if (isHeadTypeConstructor) {
                kotlin.reflect.jvm.internal.impl.load.java.lazy.d b2 = this.e.b();
                defaultTypeQualifier = b2 != null ? b2.a(this.f) : null;
            } else {
                defaultTypeQualifier = defaultQualifiersForType;
            }
            h nullabilityInfo = e(composedAnnotation);
            if (nullabilityInfo == null) {
                if (defaultTypeQualifier == null || defaultTypeQualifier.c() == null) {
                    nullabilityInfo = null;
                } else {
                    nullabilityInfo = new h(defaultTypeQualifier.c(), defaultTypeQualifier.e());
                }
            }
            g c2 = nullabilityInfo != null ? nullabilityInfo.c() : null;
            e eVar = (e) $fun$uniqueNotNull$2.invoke($fun$ifPresent$1.invoke(t.j(), e.READ_ONLY), $fun$ifPresent$1.invoke(t.g(), e.MUTABLE));
            if (nullabilityInfo != null) {
                gVar = nullabilityInfo.c();
            }
            boolean z = false;
            boolean z2 = gVar == g.NOT_NULL && kotlin.reflect.jvm.internal.impl.types.typeUtil.a.j($this$extractQualifiersFromAnnotations);
            if (nullabilityInfo != null && nullabilityInfo.d()) {
                z = true;
            }
            return new d(c2, eVar, z2, z);
        }

        /* compiled from: signatureEnhancement.kt */
        public static final class d extends kotlin.jvm.internal.l implements p<List<? extends kotlin.reflect.jvm.internal.impl.name.b>, T, T> {
            final /* synthetic */ kotlin.reflect.jvm.internal.impl.descriptors.annotations.g $composedAnnotation;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            d(kotlin.reflect.jvm.internal.impl.descriptors.annotations.g gVar) {
                super(2);
                this.$composedAnnotation = gVar;
            }

            @Nullable
            public final <T> T invoke(@NotNull List<kotlin.reflect.jvm.internal.impl.name.b> $this$ifPresent, @NotNull T qualifier) {
                kotlin.reflect.jvm.internal.impl.name.b it;
                k.f($this$ifPresent, "$this$ifPresent");
                k.f(qualifier, "qualifier");
                List<kotlin.reflect.jvm.internal.impl.name.b> list = $this$ifPresent;
                boolean z = true;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator<T> it2 = list.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            z = false;
                            break;
                        }
                        if (this.$composedAnnotation.c((kotlin.reflect.jvm.internal.impl.name.b) it2.next()) != null) {
                            it = 1;
                            continue;
                        } else {
                            it = null;
                            continue;
                        }
                        if (it != null) {
                            break;
                        }
                    }
                } else {
                    z = false;
                }
                if (z) {
                    return qualifier;
                }
                return null;
            }
        }

        /* compiled from: signatureEnhancement.kt */
        public static final class e extends kotlin.jvm.internal.l implements p<T, T, T> {
            public static final e INSTANCE = new e();

            e() {
                super(2);
            }

            @Nullable
            public final <T> T invoke(@Nullable T x, @Nullable T y) {
                if (x == null || y == null || k.a(x, y)) {
                    return x != null ? x : y;
                }
                return null;
            }
        }

        private final h e(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.g $this$extractNullability) {
            l lVar = this.g;
            Iterator it = $this$extractNullability.iterator();
            while (it.hasNext()) {
                h c2 = lVar.c((kotlin.reflect.jvm.internal.impl.descriptors.annotations.c) it.next());
                if (c2 != null) {
                    return c2;
                }
            }
            return null;
        }

        /* JADX WARNING: Removed duplicated region for block: B:22:0x0071  */
        /* JADX WARNING: Removed duplicated region for block: B:23:0x0073  */
        /* JADX WARNING: Removed duplicated region for block: B:26:0x007c  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private final kotlin.jvm.functions.l<java.lang.Integer, kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d> a() {
            /*
                r27 = this;
                r0 = r27
                java.util.Collection<kotlin.reflect.jvm.internal.impl.types.b0> r1 = r0.c
                r2 = 0
                java.util.ArrayList r3 = new java.util.ArrayList
                r4 = 10
                int r4 = kotlin.collections.r.r(r1, r4)
                r3.<init>(r4)
                r4 = r1
                r5 = 0
                java.util.Iterator r6 = r4.iterator()
            L_0x0016:
                boolean r7 = r6.hasNext()
                if (r7 == 0) goto L_0x002c
                java.lang.Object r7 = r6.next()
                r8 = r7
                kotlin.reflect.jvm.internal.impl.types.b0 r8 = (kotlin.reflect.jvm.internal.impl.types.b0) r8
                r9 = 0
                java.util.List r8 = r0.i(r8)
                r3.add(r8)
                goto L_0x0016
            L_0x002c:
                r1 = r3
                kotlin.reflect.jvm.internal.impl.types.b0 r2 = r0.b
                java.util.List r2 = r0.i(r2)
                boolean r3 = r0.d
                r5 = 1
                if (r3 == 0) goto L_0x006d
                java.util.Collection<kotlin.reflect.jvm.internal.impl.types.b0> r3 = r0.c
                r6 = 0
                boolean r7 = r3 instanceof java.util.Collection
                if (r7 == 0) goto L_0x0048
                boolean r7 = r3.isEmpty()
                if (r7 == 0) goto L_0x0048
                r3 = 0
                goto L_0x0069
            L_0x0048:
                java.util.Iterator r7 = r3.iterator()
            L_0x004c:
                boolean r8 = r7.hasNext()
                if (r8 == 0) goto L_0x0068
                java.lang.Object r8 = r7.next()
                r9 = r8
                kotlin.reflect.jvm.internal.impl.types.b0 r9 = (kotlin.reflect.jvm.internal.impl.types.b0) r9
                r10 = 0
                kotlin.reflect.jvm.internal.impl.types.checker.g r11 = kotlin.reflect.jvm.internal.impl.types.checker.g.a
                kotlin.reflect.jvm.internal.impl.types.b0 r12 = r0.b
                boolean r11 = r11.b(r9, r12)
                r9 = r11 ^ 1
                if (r9 == 0) goto L_0x004c
                r3 = r5
                goto L_0x0069
            L_0x0068:
                r3 = 0
            L_0x0069:
                if (r3 == 0) goto L_0x006d
                r3 = r5
                goto L_0x006e
            L_0x006d:
                r3 = 0
            L_0x006e:
                if (r3 == 0) goto L_0x0073
                r6 = r5
                goto L_0x0077
            L_0x0073:
                int r6 = r2.size()
            L_0x0077:
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d[] r7 = new kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d[r6]
                r8 = 0
            L_0x007a:
                if (r8 >= r6) goto L_0x00fc
                r9 = r8
                r10 = 0
                if (r9 != 0) goto L_0x0082
                r11 = r5
                goto L_0x0083
            L_0x0082:
                r11 = 0
            L_0x0083:
                if (r11 != 0) goto L_0x008a
                if (r3 != 0) goto L_0x0088
                goto L_0x008a
            L_0x0088:
                r12 = 0
                goto L_0x008b
            L_0x008a:
                r12 = r5
            L_0x008b:
                if (r12 == 0) goto L_0x00f3
                java.lang.Object r12 = r2.get(r9)
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.p r12 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.p) r12
                kotlin.reflect.jvm.internal.impl.types.b0 r13 = r12.a()
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d r12 = r12.b()
                r14 = r1
                r15 = 0
                java.util.ArrayList r16 = new java.util.ArrayList
                r16.<init>()
                r17 = r16
                r16 = r14
                r18 = 0
                r19 = r16
                r20 = 0
                java.util.Iterator r21 = r19.iterator()
            L_0x00b0:
                boolean r22 = r21.hasNext()
                if (r22 == 0) goto L_0x00e5
                java.lang.Object r22 = r21.next()
                r23 = r22
                r24 = 0
                r4 = r23
                java.util.List r4 = (java.util.List) r4
                r25 = 0
                java.lang.Object r26 = kotlin.collections.y.V(r4, r9)
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.p r26 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.p) r26
                if (r26 == 0) goto L_0x00d1
                kotlin.reflect.jvm.internal.impl.types.b0 r26 = r26.c()
                goto L_0x00d3
            L_0x00d1:
                r26 = 0
            L_0x00d3:
                if (r26 == 0) goto L_0x00df
                r4 = r26
                r25 = 0
                r5 = r17
                r5.add(r4)
                goto L_0x00e1
            L_0x00df:
                r5 = r17
            L_0x00e1:
                r17 = r5
                r5 = 1
                goto L_0x00b0
            L_0x00e5:
                r5 = r17
                r4 = r5
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d r4 = r0.b(r13, r4, r12, r11)
                r7[r8] = r4
                int r8 = r8 + 1
                r5 = 1
                goto L_0x007a
            L_0x00f3:
                r4 = 0
                java.lang.AssertionError r4 = new java.lang.AssertionError
                java.lang.String r5 = "Only head type constructors should be computed"
                r4.<init>(r5)
                throw r4
            L_0x00fc:
                r4 = r7
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$b$a r5 = new kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$b$a
                r5.<init>(r4)
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l.b.a():kotlin.jvm.functions.l");
        }

        private final List<p> i(@NotNull b0 $this$toIndexed) {
            ArrayList list = new ArrayList(1);
            new f(list).invoke($this$toIndexed, this.e);
            return list;
        }

        /* JADX WARNING: Removed duplicated region for block: B:46:0x012b  */
        /* JADX WARNING: Removed duplicated region for block: B:52:0x014c  */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x014e  */
        /* JADX WARNING: Removed duplicated region for block: B:56:0x0156  */
        /* JADX WARNING: Removed duplicated region for block: B:70:0x0187 A[ADDED_TO_REGION] */
        /* JADX WARNING: Removed duplicated region for block: B:75:0x01a1  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private final kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d b(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.types.b0 r23, java.util.Collection<? extends kotlin.reflect.jvm.internal.impl.types.b0> r24, kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d r25, boolean r26) {
            /*
                r22 = this;
                r0 = r22
                r1 = r26
                r2 = r24
                r3 = 0
                java.util.ArrayList r4 = new java.util.ArrayList
                r5 = 10
                int r5 = kotlin.collections.r.r(r2, r5)
                r4.<init>(r5)
                r5 = r2
                r6 = 0
                java.util.Iterator r7 = r5.iterator()
            L_0x0018:
                boolean r8 = r7.hasNext()
                if (r8 == 0) goto L_0x002e
                java.lang.Object r8 = r7.next()
                r9 = r8
                kotlin.reflect.jvm.internal.impl.types.b0 r9 = (kotlin.reflect.jvm.internal.impl.types.b0) r9
                r10 = 0
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d r9 = r0.f(r9)
                r4.add(r9)
                goto L_0x0018
            L_0x002e:
                r2 = r4
                r3 = r2
                r4 = 0
                java.util.ArrayList r5 = new java.util.ArrayList
                r5.<init>()
                r6 = r3
                r7 = 0
                r8 = r6
                r9 = 0
                java.util.Iterator r10 = r8.iterator()
            L_0x003f:
                boolean r11 = r10.hasNext()
                if (r11 == 0) goto L_0x005c
                java.lang.Object r11 = r10.next()
                r12 = r11
                r13 = 0
                r14 = r12
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d r14 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d) r14
                r15 = 0
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.e r14 = r14.b()
                if (r14 == 0) goto L_0x005a
                r15 = 0
                r5.add(r14)
                goto L_0x005b
            L_0x005a:
            L_0x005b:
                goto L_0x003f
            L_0x005c:
                java.util.Set r3 = kotlin.collections.y.H0(r5)
                r4 = r2
                r5 = 0
                java.util.ArrayList r6 = new java.util.ArrayList
                r6.<init>()
                r7 = r4
                r8 = 0
                r9 = r7
                r10 = 0
                java.util.Iterator r11 = r9.iterator()
            L_0x0071:
                boolean r12 = r11.hasNext()
                if (r12 == 0) goto L_0x0090
                java.lang.Object r12 = r11.next()
                r13 = r12
                r14 = 0
                r15 = r13
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d r15 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d) r15
                r16 = 0
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.g r15 = r15.c()
                if (r15 == 0) goto L_0x008e
                r16 = 0
                r6.add(r15)
                goto L_0x008f
            L_0x008e:
            L_0x008f:
                goto L_0x0071
            L_0x0090:
                java.util.Set r4 = kotlin.collections.y.H0(r6)
                r5 = r24
                r6 = 0
                java.util.ArrayList r7 = new java.util.ArrayList
                r7.<init>()
                r8 = r5
                r9 = 0
                r10 = r8
                r11 = 0
                java.util.Iterator r12 = r10.iterator()
            L_0x00aa:
                boolean r13 = r12.hasNext()
                if (r13 == 0) goto L_0x00d6
                java.lang.Object r13 = r12.next()
                r14 = r13
                r15 = 0
                r16 = r14
                kotlin.reflect.jvm.internal.impl.types.b0 r16 = (kotlin.reflect.jvm.internal.impl.types.b0) r16
                r17 = 0
                r18 = r5
                kotlin.reflect.jvm.internal.impl.types.b0 r5 = kotlin.reflect.jvm.internal.impl.types.e1.c(r16)
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d r5 = r0.f(r5)
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.g r5 = r5.c()
                if (r5 == 0) goto L_0x00d2
                r16 = 0
                r7.add(r5)
                goto L_0x00d3
            L_0x00d2:
            L_0x00d3:
                r5 = r18
                goto L_0x00aa
            L_0x00d6:
                r18 = r5
                java.util.Set r5 = kotlin.collections.y.H0(r7)
                r6 = r23
                r7 = r25
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d r8 = r0.g(r6, r1, r7)
                r9 = r8
                r10 = 0
                boolean r11 = r9.e()
                r9 = 1
                r10 = r11 ^ 1
                if (r10 == 0) goto L_0x00f3
                r10 = r8
                goto L_0x00f4
            L_0x00f3:
                r10 = 0
            L_0x00f4:
                if (r10 == 0) goto L_0x00fb
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.g r10 = r10.c()
                goto L_0x00fc
            L_0x00fb:
                r10 = 0
            L_0x00fc:
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.g r12 = r8.c()
                boolean r13 = r0.d
                if (r13 == 0) goto L_0x0108
                if (r1 == 0) goto L_0x0108
                r13 = r9
                goto L_0x0109
            L_0x0108:
                r13 = 0
            L_0x0109:
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.g r15 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.n.c(r4, r10, r13)
                if (r15 == 0) goto L_0x012d
                r16 = r15
                r17 = 0
                boolean r18 = r22.h()
                if (r18 == 0) goto L_0x0126
                if (r1 == 0) goto L_0x0126
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.g r11 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.g.NULLABLE
                r14 = r16
                if (r14 != r11) goto L_0x0128
                r11 = r9
                goto L_0x0129
            L_0x0126:
                r14 = r16
            L_0x0128:
                r11 = 0
            L_0x0129:
                if (r11 != 0) goto L_0x012d
                r11 = r15
                goto L_0x012e
            L_0x012d:
                r11 = 0
            L_0x012e:
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.e r14 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.e.MUTABLE
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.e r15 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.e.READ_ONLY
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.e r9 = r8.b()
                java.lang.Object r9 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.n.b(r3, r14, r15, r9, r13)
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.e r9 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.e) r9
                if (r12 != r10) goto L_0x014e
                boolean r14 = kotlin.jvm.internal.k.a(r5, r4)
                r15 = 1
                r14 = r14 ^ r15
                if (r14 == 0) goto L_0x014c
                goto L_0x014e
            L_0x014c:
                r15 = 0
                goto L_0x014f
            L_0x014e:
                r15 = 1
            L_0x014f:
                r14 = r15
                boolean r15 = r8.d()
                if (r15 != 0) goto L_0x0183
                r15 = r2
                r17 = 0
                boolean r18 = r15.isEmpty()
                if (r18 == 0) goto L_0x0161
                r15 = 0
                goto L_0x017e
            L_0x0161:
                java.util.Iterator r18 = r15.iterator()
            L_0x0165:
                boolean r19 = r18.hasNext()
                if (r19 == 0) goto L_0x017d
                java.lang.Object r19 = r18.next()
                r20 = r19
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d r20 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d) r20
                r21 = 0
                boolean r20 = r20.d()
                if (r20 == 0) goto L_0x0165
                r15 = 1
                goto L_0x017e
            L_0x017d:
                r15 = 0
            L_0x017e:
                if (r15 == 0) goto L_0x0181
                goto L_0x0183
            L_0x0181:
                r15 = 0
                goto L_0x0184
            L_0x0183:
                r15 = 1
            L_0x0184:
                if (r11 != 0) goto L_0x019b
                if (r14 == 0) goto L_0x019b
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.g r17 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.n.c(r5, r12, r13)
                r18 = r17
                r0 = r18
                r1 = 1
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d r1 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.n.a(r0, r9, r1, r15)
                return r1
            L_0x019b:
                r1 = 1
                if (r11 != 0) goto L_0x01a1
                goto L_0x01a2
            L_0x01a1:
                r1 = 0
            L_0x01a2:
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d r0 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.n.a(r11, r9, r1, r15)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l.b.b(kotlin.reflect.jvm.internal.impl.types.b0, java.util.Collection, kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d, boolean):kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.d");
        }
    }

    /* compiled from: signatureEnhancement.kt */
    public static class a {
        @NotNull
        private final b0 a;
        private final boolean b;
        private final boolean c;

        public a(@NotNull b0 type, boolean wereChanges, boolean containsFunctionN) {
            k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
            this.a = type;
            this.b = wereChanges;
            this.c = containsFunctionN;
        }

        @NotNull
        public final b0 b() {
            return this.a;
        }

        public final boolean c() {
            return this.b;
        }

        public final boolean a() {
            return this.c;
        }
    }

    /* compiled from: signatureEnhancement.kt */
    public static final class c extends a {
        private final boolean d;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public c(@NotNull b0 type, boolean hasDefaultValue, boolean wereChanges, boolean containsFunctionN) {
            super(type, wereChanges, containsFunctionN);
            k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
            this.d = hasDefaultValue;
        }

        public final boolean d() {
            return this.d;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = kotlin.reflect.jvm.internal.impl.load.java.lazy.a.h(r11, r10.getAnnotations());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l.b h(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.b r9, kotlin.reflect.jvm.internal.impl.descriptors.w0 r10, kotlin.reflect.jvm.internal.impl.load.java.lazy.h r11, kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.descriptors.b, ? extends kotlin.reflect.jvm.internal.impl.types.b0> r12) {
        /*
            r8 = this;
            if (r10 == 0) goto L_0x0012
            r0 = r10
            r1 = 0
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r2 = r0.getAnnotations()
            kotlin.reflect.jvm.internal.impl.load.java.lazy.h r0 = kotlin.reflect.jvm.internal.impl.load.java.lazy.a.h(r11, r2)
            if (r0 == 0) goto L_0x0012
            r5 = r0
            goto L_0x0013
        L_0x0012:
            r5 = r11
        L_0x0013:
            kotlin.reflect.jvm.internal.impl.load.java.a$a r6 = kotlin.reflect.jvm.internal.impl.load.java.a.C0356a.VALUE_PARAMETER
            r4 = 0
            r1 = r8
            r2 = r9
            r3 = r10
            r7 = r12
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$b r0 = r1.g(r2, r3, r4, r5, r6, r7)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l.h(kotlin.reflect.jvm.internal.impl.descriptors.b, kotlin.reflect.jvm.internal.impl.descriptors.w0, kotlin.reflect.jvm.internal.impl.load.java.lazy.h, kotlin.jvm.functions.l):kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.l$b");
    }

    private final b g(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b $this$parts, kotlin.reflect.jvm.internal.impl.descriptors.annotations.a typeContainer, boolean isCovariant, kotlin.reflect.jvm.internal.impl.load.java.lazy.h containerContext, a.C0356a containerApplicabilityType, kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.descriptors.b, ? extends b0> collector) {
        kotlin.reflect.jvm.internal.impl.descriptors.b bVar = $this$parts;
        kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.descriptors.b, ? extends b0> lVar = collector;
        b0 b0Var = (b0) lVar.invoke($this$parts);
        Iterable<kotlin.reflect.jvm.internal.impl.descriptors.b> $this$mapTo$iv$iv = $this$parts.d();
        k.b($this$mapTo$iv$iv, "this.overriddenDescriptors");
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (kotlin.reflect.jvm.internal.impl.descriptors.b it : $this$mapTo$iv$iv) {
            k.b(it, "it");
            destination$iv$iv.add((b0) lVar.invoke(it));
        }
        return new b(this, typeContainer, b0Var, destination$iv$iv, isCovariant, kotlin.reflect.jvm.internal.impl.load.java.lazy.a.h(containerContext, ((b0) lVar.invoke($this$parts)).getAnnotations()), containerApplicabilityType);
    }
}
