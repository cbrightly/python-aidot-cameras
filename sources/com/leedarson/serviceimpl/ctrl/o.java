package com.leedarson.serviceimpl.ctrl;

import android.content.Context;
import chip.devicecontroller.ChipDeviceController;
import chip.devicecontroller.ReportCallback;
import com.leedarson.serviceimpl.bean.ClusterEnum;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.s1;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MTSyncCtrl.kt */
public final class o {
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    private Context a;
    @NotNull
    private ChipDeviceController b;
    private final int c = 1;
    private final int d = 10;

    @f(c = "com.leedarson.serviceimpl.ctrl.MTSyncCtrl", f = "MTSyncCtrl.kt", l = {67}, m = "subscribeCluster")
    /* compiled from: MTSyncCtrl.kt */
    public static final class b extends d {
        public static ChangeQuickRedirect changeQuickRedirect;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ o this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(o oVar, kotlin.coroutines.d<? super b> dVar) {
            super(dVar);
            this.this$0 = oVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7760, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.g(0, (com.leedarson.serviceimpl.listener.b) null, (ReportCallback) null, (ClusterEnum[]) null, this);
        }
    }

    public o(@NotNull Context context, @NotNull ChipDeviceController devController) {
        k.e(context, "context");
        k.e(devController, "devController");
        this.a = context;
        this.b = devController;
    }

    @NotNull
    public final Context a() {
        return this.a;
    }

    @NotNull
    public final ChipDeviceController b() {
        return this.b;
    }

    public final int d() {
        return this.c;
    }

    public final int c() {
        return this.d;
    }

    @f(c = "com.leedarson.serviceimpl.ctrl.MTSyncCtrl$subscribe$1", f = "MTSyncCtrl.kt", l = {33}, m = "invokeSuspend")
    /* compiled from: MTSyncCtrl.kt */
    public static final class a extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.serviceimpl.listener.b $ctrlCallback;
        final /* synthetic */ long $nodeId;
        final /* synthetic */ ReportCallback $reportCallback;
        int label;
        final /* synthetic */ o this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(o oVar, long j, com.leedarson.serviceimpl.listener.b bVar, ReportCallback reportCallback, kotlin.coroutines.d<? super a> dVar) {
            super(2, dVar);
            this.this$0 = oVar;
            this.$nodeId = j;
            this.$ctrlCallback = bVar;
            this.$reportCallback = reportCallback;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 7757, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
            if (proxy.isSupported) {
                return (kotlin.coroutines.d) proxy.result;
            }
            return new a(this.this$0, this.$nodeId, this.$ctrlCallback, this.$reportCallback, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 7759, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 7758, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((a) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 7756, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    o oVar = this.this$0;
                    long j = this.$nodeId;
                    com.leedarson.serviceimpl.listener.b bVar = this.$ctrlCallback;
                    ReportCallback reportCallback = this.$reportCallback;
                    ClusterEnum[] clusterEnumArr = {ClusterEnum.OnOff, ClusterEnum.HSL_H, ClusterEnum.HSL_S, ClusterEnum.CCT, ClusterEnum.DIM};
                    this.label = 1;
                    if (oVar.g(j, bVar, reportCallback, clusterEnumArr, this) != d) {
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    public final void f(long nodeId, @Nullable com.leedarson.serviceimpl.listener.b ctrlCallback, @NotNull ReportCallback reportCallback) {
        Object[] objArr = {new Long(nodeId), ctrlCallback, reportCallback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7752, new Class[]{Long.TYPE, com.leedarson.serviceimpl.listener.b.class, ReportCallback.class}, Void.TYPE).isSupported) {
            k.e(reportCallback, "reportCallback");
            z1 unused = j.d(s1.c, (g) null, (q0) null, new a(this, nodeId, ctrlCallback, reportCallback, (kotlin.coroutines.d<? super a>) null), 3, (Object) null);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0114, code lost:
        r14.subscribeToPath(r15, r16, ((java.lang.Number) r3).longValue(), (java.util.List<chip.devicecontroller.model.ChipAttributePath>) r19, r7.d(), r7.c());
     */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0078  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x009e  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object g(long r27, @org.jetbrains.annotations.Nullable com.leedarson.serviceimpl.listener.b r29, @org.jetbrains.annotations.NotNull chip.devicecontroller.ReportCallback r30, @org.jetbrains.annotations.NotNull com.leedarson.serviceimpl.bean.ClusterEnum[] r31, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r32) {
        /*
            r26 = this;
            r0 = r32
            r1 = 5
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.Long r3 = new java.lang.Long
            r9 = r27
            r3.<init>(r9)
            r11 = 0
            r2[r11] = r3
            r12 = 1
            r2[r12] = r29
            r3 = 2
            r2[r3] = r30
            r4 = 3
            r2[r4] = r31
            r5 = 4
            r2[r5] = r0
            com.meituan.robust.ChangeQuickRedirect r6 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class r1 = java.lang.Long.TYPE
            r7[r11] = r1
            java.lang.Class<com.leedarson.serviceimpl.listener.b> r1 = com.leedarson.serviceimpl.listener.b.class
            r7[r12] = r1
            java.lang.Class<chip.devicecontroller.ReportCallback> r1 = chip.devicecontroller.ReportCallback.class
            r7[r3] = r1
            java.lang.Class<com.leedarson.serviceimpl.bean.ClusterEnum[]> r1 = com.leedarson.serviceimpl.bean.ClusterEnum[].class
            r7[r4] = r1
            java.lang.Class<kotlin.coroutines.d> r1 = kotlin.coroutines.d.class
            r7[r5] = r1
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            r5 = 0
            r1 = 7753(0x1e49, float:1.0864E-41)
            r3 = r26
            r4 = r6
            r6 = r1
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0047
            java.lang.Object r0 = r1.result
            return r0
        L_0x0047:
            boolean r1 = r0 instanceof com.leedarson.serviceimpl.ctrl.o.b
            if (r1 == 0) goto L_0x005d
            r1 = r0
            com.leedarson.serviceimpl.ctrl.o$b r1 = (com.leedarson.serviceimpl.ctrl.o.b) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x005d
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r26
            goto L_0x0065
        L_0x005d:
            com.leedarson.serviceimpl.ctrl.o$b r1 = new com.leedarson.serviceimpl.ctrl.o$b
            r2 = r26
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0065:
            java.lang.Object r3 = r1.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r1.label
            switch(r4) {
                case 0: goto L_0x009e;
                case 1: goto L_0x0078;
                default: goto L_0x0070;
            }
        L_0x0070:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0078:
            java.lang.Object r0 = r1.L$4
            chip.devicecontroller.ReportCallback r0 = (chip.devicecontroller.ReportCallback) r0
            java.lang.Object r4 = r1.L$3
            chip.devicecontroller.SubscriptionEstablishedCallback r4 = (chip.devicecontroller.SubscriptionEstablishedCallback) r4
            java.lang.Object r5 = r1.L$2
            chip.devicecontroller.ChipDeviceController r5 = (chip.devicecontroller.ChipDeviceController) r5
            java.lang.Object r6 = r1.L$1
            java.util.List r6 = (java.util.List) r6
            java.lang.Object r7 = r1.L$0
            com.leedarson.serviceimpl.ctrl.o r7 = (com.leedarson.serviceimpl.ctrl.o) r7
            kotlin.p.b(r3)     // Catch:{ Exception -> 0x0099 }
            r16 = r0
            r32 = r3
            r15 = r4
            r14 = r5
            r19 = r6
            goto L_0x0114
        L_0x0099:
            r0 = move-exception
            r32 = r3
            goto L_0x0128
        L_0x009e:
            kotlin.p.b(r3)
            r7 = r26
            r4 = r30
            r5 = r27
            r8 = r29
            r9 = r31
            com.leedarson.serviceimpl.ctrl.c r10 = new com.leedarson.serviceimpl.ctrl.c
            r10.<init>(r8, r5)
            java.util.ArrayList r13 = new java.util.ArrayList
            r13.<init>()
            r14 = r9
            r15 = 0
            int r11 = r14.length
            r12 = 0
        L_0x00ba:
            if (r12 >= r11) goto L_0x00ea
            r16 = r14[r12]
            r18 = r16
            r19 = 0
            long r20 = r18.getEndpointId()
            long r22 = r18.getClusterId()
            long r24 = r18.getAttrId()
            r27 = r20
            r29 = r22
            r31 = r24
            chip.devicecontroller.model.ChipAttributePath r2 = chip.devicecontroller.model.ChipAttributePath.newInstance((long) r27, (long) r29, (long) r31)
            r32 = r3
            java.lang.String r3 = "attributePath"
            kotlin.jvm.internal.k.d(r2, r3)
            r13.add(r2)
            int r12 = r12 + 1
            r2 = r26
            r3 = r32
            goto L_0x00ba
        L_0x00ea:
            r32 = r3
            chip.devicecontroller.ChipDeviceController r2 = r7.b()     // Catch:{ Exception -> 0x0127 }
            com.google.chip.chiptool.ChipClient r3 = com.google.chip.chiptool.ChipClient.INSTANCE     // Catch:{ Exception -> 0x0127 }
            android.content.Context r11 = r7.a()     // Catch:{ Exception -> 0x0127 }
            r1.L$0 = r7     // Catch:{ Exception -> 0x0127 }
            r1.L$1 = r13     // Catch:{ Exception -> 0x0127 }
            r1.L$2 = r2     // Catch:{ Exception -> 0x0127 }
            r1.L$3 = r10     // Catch:{ Exception -> 0x0127 }
            r1.L$4 = r4     // Catch:{ Exception -> 0x0127 }
            r12 = 1
            r1.label = r12     // Catch:{ Exception -> 0x0127 }
            java.lang.Object r3 = r3.getConnectedDevicePointer(r11, r5, r1)     // Catch:{ Exception -> 0x0127 }
            if (r3 != r0) goto L_0x010e
            return r0
        L_0x010e:
            r14 = r2
            r16 = r4
            r15 = r10
            r19 = r13
        L_0x0114:
            java.lang.Number r3 = (java.lang.Number) r3     // Catch:{ Exception -> 0x0127 }
            long r17 = r3.longValue()     // Catch:{ Exception -> 0x0127 }
            int r20 = r7.d()     // Catch:{ Exception -> 0x0127 }
            int r21 = r7.c()     // Catch:{ Exception -> 0x0127 }
            r14.subscribeToPath((chip.devicecontroller.SubscriptionEstablishedCallback) r15, (chip.devicecontroller.ReportCallback) r16, (long) r17, (java.util.List<chip.devicecontroller.model.ChipAttributePath>) r19, (int) r20, (int) r21)     // Catch:{ Exception -> 0x0127 }
            goto L_0x0128
        L_0x0127:
            r0 = move-exception
        L_0x0128:
            kotlin.x r0 = kotlin.x.a
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.o.g(long, com.leedarson.serviceimpl.listener.b, chip.devicecontroller.ReportCallback, com.leedarson.serviceimpl.bean.ClusterEnum[], kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    public static final void h(com.leedarson.serviceimpl.listener.b $ctrlCallback, long $nodeId) {
        if (!PatchProxy.proxy(new Object[]{$ctrlCallback, new Long($nodeId)}, (Object) null, changeQuickRedirect, true, 7755, new Class[]{com.leedarson.serviceimpl.listener.b.class, Long.TYPE}, Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, "subscribe to device success", (String) null, 2, (Object) null);
            if ($ctrlCallback != null) {
                $ctrlCallback.onSuccess($nodeId, "");
            }
        }
    }
}
