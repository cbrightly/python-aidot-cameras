package com.leedarson.serviceimpl.ctrl;

import android.content.Context;
import chip.devicecontroller.ChipClusters;
import chip.devicecontroller.ChipStructs;
import chip.devicecontroller.lu1;
import com.leedarson.serviceimpl.bean.DCLVendorInfoBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.List;
import kotlin.coroutines.g;
import kotlin.coroutines.i;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.o;
import kotlin.x;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.s1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MTFabricCtrl.kt */
public final class l {
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    private final com.leedarson.serviceimpl.manager.c a;
    private final int b = -1;

    @f(c = "com.leedarson.serviceimpl.ctrl.MTFabricCtrl", f = "MTFabricCtrl.kt", l = {64, 71}, m = "readFabricsAttr")
    /* compiled from: MTFabricCtrl.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        long J$0;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ l this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(l lVar, kotlin.coroutines.d<? super d> dVar) {
            super(dVar);
            this.this$0 = lVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7677, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return l.b(this.this$0, 0, (com.leedarson.serviceimpl.listener.b) null, this);
        }
    }

    public l(@NotNull Context context) {
        k.e(context, "context");
        this.a = new com.leedarson.serviceimpl.manager.c(context);
    }

    public static final /* synthetic */ Object a(l $this, kotlin.coroutines.d $completion) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this, $completion}, (Object) null, changeQuickRedirect, true, 7668, new Class[]{l.class, kotlin.coroutines.d.class}, Object.class);
        return proxy.isSupported ? proxy.result : $this.e($completion);
    }

    public static final /* synthetic */ Object b(l $this, long nodeId, com.leedarson.serviceimpl.listener.b callback, kotlin.coroutines.d $completion) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this, new Long(nodeId), callback, $completion}, (Object) null, changeQuickRedirect, true, 7667, new Class[]{l.class, Long.TYPE, com.leedarson.serviceimpl.listener.b.class, kotlin.coroutines.d.class}, Object.class);
        return proxy.isSupported ? proxy.result : $this.g(nodeId, callback, $completion);
    }

    public final int d() {
        return this.b;
    }

    @f(c = "com.leedarson.serviceimpl.ctrl.MTFabricCtrl$readFabrics$job$1", f = "MTFabricCtrl.kt", l = {31, 34}, m = "invokeSuspend")
    /* compiled from: MTFabricCtrl.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.serviceimpl.listener.b $callback;
        final /* synthetic */ long $nodeId;
        Object L$0;
        int label;
        final /* synthetic */ l this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(l lVar, long j, com.leedarson.serviceimpl.listener.b bVar, kotlin.coroutines.d<? super c> dVar) {
            super(2, dVar);
            this.this$0 = lVar;
            this.$nodeId = j;
            this.$callback = bVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 7674, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
            if (proxy.isSupported) {
                return (kotlin.coroutines.d) proxy.result;
            }
            return new c(this.this$0, this.$nodeId, this.$callback, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 7676, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 7675, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((c) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: java.util.List} */
        /* JADX WARNING: Multi-variable type inference failed */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r24) {
            /*
                r23 = this;
                r0 = 1
                java.lang.Object[] r1 = new java.lang.Object[r0]
                r8 = 0
                r1[r8] = r24
                com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
                java.lang.Class[] r6 = new java.lang.Class[r0]
                java.lang.Class<java.lang.Object> r2 = java.lang.Object.class
                r6[r8] = r2
                java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
                r4 = 0
                r5 = 7673(0x1df9, float:1.0752E-41)
                r2 = r23
                com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
                boolean r2 = r1.isSupported
                if (r2 == 0) goto L_0x0020
                java.lang.Object r0 = r1.result
                return r0
            L_0x0020:
                java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
                r2 = r23
                int r3 = r2.label
                r4 = 0
                switch(r3) {
                    case 0: goto L_0x004c;
                    case 1: goto L_0x0043;
                    case 2: goto L_0x0034;
                    default: goto L_0x002c;
                }
            L_0x002c:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0034:
                r1 = r23
                r3 = r4
                r5 = r24
                java.lang.Object r6 = r1.L$0
                r3 = r6
                java.util.List r3 = (java.util.List) r3
                kotlin.p.b(r5)
                r6 = r5
                goto L_0x007c
            L_0x0043:
                r3 = r23
                r5 = r24
                kotlin.p.b(r5)
                r6 = r5
                goto L_0x0067
            L_0x004c:
                kotlin.p.b(r24)
                r3 = r23
                r5 = r24
                com.leedarson.serviceimpl.ctrl.l r6 = r3.this$0
                long r9 = r3.$nodeId
                com.leedarson.serviceimpl.listener.b r7 = r3.$callback
                r3.label = r0
                java.lang.Object r6 = com.leedarson.serviceimpl.ctrl.l.b(r6, r9, r7, r3)
                if (r6 != r1) goto L_0x0062
                return r1
            L_0x0062:
                r22 = r6
                r6 = r5
                r5 = r22
            L_0x0067:
                java.util.List r5 = (java.util.List) r5
                if (r5 == 0) goto L_0x0117
                com.leedarson.serviceimpl.ctrl.l r7 = r3.this$0
                r3.L$0 = r5
                r9 = 2
                r3.label = r9
                java.lang.Object r7 = com.leedarson.serviceimpl.ctrl.l.a(r7, r3)
                if (r7 != r1) goto L_0x0079
                return r1
            L_0x0079:
                r1 = r3
                r3 = r5
                r5 = r7
            L_0x007c:
                java.util.List r5 = (java.util.List) r5
                if (r5 == 0) goto L_0x0088
                boolean r7 = r5.isEmpty()
                if (r7 == 0) goto L_0x0087
                goto L_0x0088
            L_0x0087:
                r0 = r8
            L_0x0088:
                if (r0 == 0) goto L_0x00a2
                com.leedarson.serviceimpl.listener.b r0 = r1.$callback
                if (r0 != 0) goto L_0x008f
                goto L_0x009f
            L_0x008f:
                com.leedarson.serviceimpl.ctrl.l r4 = r1.this$0
                int r4 = r4.d()
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r8 = "DCL Vendorinfo query fail"
                r7.<init>(r8)
                r0.onFail(r4, r7)
            L_0x009f:
                kotlin.x r0 = kotlin.x.a
                return r0
            L_0x00a2:
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
                r7 = r3
                com.leedarson.serviceimpl.ctrl.l r8 = r1.this$0
                r9 = 0
                java.util.Iterator r10 = r7.iterator()
            L_0x00af:
                boolean r11 = r10.hasNext()
                if (r11 == 0) goto L_0x0108
                java.lang.Object r11 = r10.next()
                r12 = r11
                chip.devicecontroller.ChipStructs$OperationalCredentialsClusterFabricDescriptor r12 = (chip.devicecontroller.ChipStructs.OperationalCredentialsClusterFabricDescriptor) r12
                r13 = 0
                java.lang.Integer r14 = r12.vendorId
                java.lang.String r15 = "it.vendorId"
                kotlin.jvm.internal.k.d(r14, r15)
                int r14 = r14.intValue()
                com.leedarson.serviceimpl.bean.DCLVendorInfoBean r14 = r8.c(r5, r14)
                com.leedarson.serviceimpl.bean.FabricBean r4 = new com.leedarson.serviceimpl.bean.FabricBean
                java.lang.Long r2 = r12.fabricId
                r24 = r3
                java.lang.String r3 = "it.fabricId"
                kotlin.jvm.internal.k.d(r2, r3)
                long r17 = r2.longValue()
                java.lang.Integer r2 = r12.fabricIndex
                java.lang.String r3 = "it.fabricIndex"
                kotlin.jvm.internal.k.d(r2, r3)
                int r19 = r2.intValue()
                java.lang.Integer r2 = r12.vendorId
                kotlin.jvm.internal.k.d(r2, r15)
                int r20 = r2.intValue()
                if (r14 != 0) goto L_0x00f4
                r21 = 0
                goto L_0x00fa
            L_0x00f4:
                java.lang.String r2 = r14.getVendorName()
                r21 = r2
            L_0x00fa:
                r16 = r4
                r16.<init>(r17, r19, r20, r21)
                r0.add(r4)
                r2 = r23
                r3 = r24
                r4 = 0
                goto L_0x00af
            L_0x0108:
                r24 = r3
                com.leedarson.serviceimpl.listener.b r2 = r1.$callback
                if (r2 != 0) goto L_0x010f
                goto L_0x0114
            L_0x010f:
                long r3 = r1.$nodeId
                r2.onSuccess(r3, r0)
            L_0x0114:
                r5 = r24
                r3 = r1
            L_0x0117:
                kotlin.x r0 = kotlin.x.a
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.l.c.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public final void f(long j, @Nullable com.leedarson.serviceimpl.listener.b callback) {
        Object[] objArr = {new Long(j), callback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7663, new Class[]{Long.TYPE, com.leedarson.serviceimpl.listener.b.class}, Void.TYPE).isSupported) {
            long nodeId = j;
            com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, k.l("readFabrics nodeId:", Long.valueOf(nodeId)), (String) null, 2, (Object) null);
            j.d(s1.c, (g) null, (q0) null, new c(this, nodeId, callback, (kotlin.coroutines.d<? super c>) null), 3, (Object) null).start();
        }
    }

    @Nullable
    public final DCLVendorInfoBean c(@Nullable List<DCLVendorInfoBean> dclVendorList, int vid) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dclVendorList, new Integer(vid)}, this, changeQuickRedirect, false, 7664, new Class[]{List.class, Integer.TYPE}, DCLVendorInfoBean.class);
        if (proxy.isSupported) {
            return (DCLVendorInfoBean) proxy.result;
        }
        if (dclVendorList == null) {
            return null;
        }
        for (DCLVendorInfoBean it : dclVendorList) {
            Integer vendorID = it.getVendorID();
            if (vendorID != null && vendorID.intValue() == vid) {
                return it;
            }
        }
        return null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: com.leedarson.serviceimpl.listener.b} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: com.leedarson.serviceimpl.ctrl.l} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00df  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.Object g(long r18, com.leedarson.serviceimpl.listener.b r20, kotlin.coroutines.d<? super java.util.List<chip.devicecontroller.ChipStructs.OperationalCredentialsClusterFabricDescriptor>> r21) {
        /*
            r17 = this;
            r0 = r21
            r1 = 3
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.Long r3 = new java.lang.Long
            r9 = r18
            r3.<init>(r9)
            r4 = 0
            r2[r4] = r3
            r11 = 1
            r2[r11] = r20
            r12 = 2
            r2[r12] = r0
            com.meituan.robust.ChangeQuickRedirect r5 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class r1 = java.lang.Long.TYPE
            r7[r4] = r1
            java.lang.Class<com.leedarson.serviceimpl.listener.b> r1 = com.leedarson.serviceimpl.listener.b.class
            r7[r11] = r1
            java.lang.Class<kotlin.coroutines.d> r1 = kotlin.coroutines.d.class
            r7[r12] = r1
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            r1 = 0
            r6 = 7665(0x1df1, float:1.0741E-41)
            r3 = r17
            r4 = r5
            r5 = r1
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0039
            java.lang.Object r0 = r1.result
            return r0
        L_0x0039:
            boolean r1 = r0 instanceof com.leedarson.serviceimpl.ctrl.l.d
            if (r1 == 0) goto L_0x004f
            r1 = r0
            com.leedarson.serviceimpl.ctrl.l$d r1 = (com.leedarson.serviceimpl.ctrl.l.d) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x004f
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r17
            goto L_0x0057
        L_0x004f:
            com.leedarson.serviceimpl.ctrl.l$d r1 = new com.leedarson.serviceimpl.ctrl.l$d
            r2 = r17
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0057:
            java.lang.Object r1 = r0.result
            java.lang.Object r13 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0094;
                case 1: goto L_0x007c;
                case 2: goto L_0x006a;
                default: goto L_0x0062;
            }
        L_0x0062:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x006a:
            java.lang.Object r3 = r0.L$2
            chip.devicecontroller.ChipClusters$OperationalCredentialsCluster r3 = (chip.devicecontroller.ChipClusters.OperationalCredentialsCluster) r3
            java.lang.Object r3 = r0.L$1
            com.leedarson.serviceimpl.listener.b r3 = (com.leedarson.serviceimpl.listener.b) r3
            java.lang.Object r3 = r0.L$0
            com.leedarson.serviceimpl.ctrl.l r3 = (com.leedarson.serviceimpl.ctrl.l) r3
            kotlin.p.b(r1)
            r7 = r1
            goto L_0x010b
        L_0x007c:
            r3 = r17
            r4 = r18
            r6 = r20
            long r4 = r0.J$0
            java.lang.Object r7 = r0.L$1
            r6 = r7
            com.leedarson.serviceimpl.listener.b r6 = (com.leedarson.serviceimpl.listener.b) r6
            java.lang.Object r7 = r0.L$0
            r3 = r7
            com.leedarson.serviceimpl.ctrl.l r3 = (com.leedarson.serviceimpl.ctrl.l) r3
            kotlin.p.b(r1)
            r14 = r3
            r3 = r1
            goto L_0x00b9
        L_0x0094:
            kotlin.p.b(r1)
            r14 = r17
            r9 = r18
            r15 = r20
            com.leedarson.serviceimpl.manager.c r3 = r14.a
            r6 = 0
            r8 = 2
            r16 = 0
            r0.L$0 = r14
            r0.L$1 = r15
            r0.J$0 = r9
            r0.label = r11
            r4 = r9
            r7 = r0
            r10 = r9
            r9 = r16
            java.lang.Object r3 = com.leedarson.serviceimpl.manager.c.l(r3, r4, r6, r7, r8, r9)
            if (r3 != r13) goto L_0x00b7
            return r13
        L_0x00b7:
            r4 = r10
            r6 = r15
        L_0x00b9:
            chip.devicecontroller.ChipClusters$OperationalCredentialsCluster r3 = (chip.devicecontroller.ChipClusters.OperationalCredentialsCluster) r3
            if (r3 != 0) goto L_0x00df
            com.leedarson.serviceimpl.k r7 = com.leedarson.serviceimpl.k.a
            java.lang.Long r8 = kotlin.coroutines.jvm.internal.b.d(r4)
            java.lang.String r9 = "on fail:could not find getOperationalCredentialsClusterForDevice by nodeId:"
            java.lang.String r8 = kotlin.jvm.internal.k.l(r9, r8)
            r9 = 0
            com.leedarson.serviceimpl.k.e(r7, r8, r9, r12, r9)
            if (r6 != 0) goto L_0x00d0
            goto L_0x00de
        L_0x00d0:
            int r7 = r14.d()
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r10 = "could not find getOperationalCredentialsClusterForDevice"
            r8.<init>(r10)
            r6.onFail(r7, r8)
        L_0x00de:
            return r9
        L_0x00df:
            r0.L$0 = r14
            r0.L$1 = r6
            r0.L$2 = r3
            r0.label = r12
            kotlin.coroutines.i r7 = new kotlin.coroutines.i
            kotlin.coroutines.d r8 = kotlin.coroutines.intrinsics.b.c(r0)
            r7.<init>(r8)
            r8 = r7
            r9 = 0
            com.leedarson.serviceimpl.ctrl.l$e r10 = new com.leedarson.serviceimpl.ctrl.l$e
            r10.<init>(r8, r6, r14)
            r3.readFabricsAttribute(r10)
            java.lang.Object r7 = r7.a()
            java.lang.Object r8 = kotlin.coroutines.intrinsics.c.d()
            if (r7 != r8) goto L_0x0108
            kotlin.coroutines.jvm.internal.h.c(r0)
        L_0x0108:
            if (r7 != r13) goto L_0x010b
            return r13
        L_0x010b:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.l.g(long, com.leedarson.serviceimpl.listener.b, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: MTFabricCtrl.kt */
    public static final class e implements ChipClusters.OperationalCredentialsCluster.FabricsAttributeCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ kotlin.coroutines.d<List<ChipStructs.OperationalCredentialsClusterFabricDescriptor>> a;
        final /* synthetic */ com.leedarson.serviceimpl.listener.b b;
        final /* synthetic */ l c;

        public /* synthetic */ void onSubscriptionEstablished() {
            lu1.a(this);
        }

        e(kotlin.coroutines.d<? super List<ChipStructs.OperationalCredentialsClusterFabricDescriptor>> $it, com.leedarson.serviceimpl.listener.b $callback, l $receiver) {
            this.a = $it;
            this.b = $callback;
            this.c = $receiver;
        }

        public void onSuccess(@Nullable List<ChipStructs.OperationalCredentialsClusterFabricDescriptor> valueList) {
            if (!PatchProxy.proxy(new Object[]{valueList}, this, changeQuickRedirect, false, 7678, new Class[]{List.class}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, "readFabricsAttr onSuccess", (String) null, 2, (Object) null);
                if (valueList != null) {
                    for (ChipStructs.OperationalCredentialsClusterFabricDescriptor it : valueList) {
                        com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, it.toString(), (String) null, 2, (Object) null);
                    }
                }
                kotlin.coroutines.d<List<ChipStructs.OperationalCredentialsClusterFabricDescriptor>> dVar = this.a;
                o.a aVar = o.Companion;
                dVar.resumeWith(o.m17constructorimpl(valueList));
            }
        }

        public void onError(@Nullable Exception ex) {
            if (!PatchProxy.proxy(new Object[]{ex}, this, changeQuickRedirect, false, 7679, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, k.l("readFabricsAttr onError ", ex), (String) null, 2, (Object) null);
                com.leedarson.serviceimpl.listener.b bVar = this.b;
                if (bVar != null) {
                    bVar.onFail(this.c.d(), ex);
                }
                kotlin.coroutines.d<List<ChipStructs.OperationalCredentialsClusterFabricDescriptor>> dVar = this.a;
                o.a aVar = o.Companion;
                dVar.resumeWith(o.m17constructorimpl((Object) null));
            }
        }
    }

    private final Object e(kotlin.coroutines.d<? super List<DCLVendorInfoBean>> $completion) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$completion}, this, changeQuickRedirect, false, 7666, new Class[]{kotlin.coroutines.d.class}, Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        i it = new i(kotlin.coroutines.intrinsics.b.c($completion));
        kotlin.coroutines.d continuation = it;
        com.leedarson.serviceimpl.i.a.B("", new a(continuation), new b(continuation));
        Object a2 = it.a();
        if (a2 == kotlin.coroutines.intrinsics.c.d()) {
            h.c($completion);
        }
        return a2;
    }

    /* compiled from: MTFabricCtrl.kt */
    public static final class a<T> implements io.reactivex.functions.e {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ kotlin.coroutines.d<List<DCLVendorInfoBean>> c;

        a(kotlin.coroutines.d<? super List<DCLVendorInfoBean>> dVar) {
            this.c = dVar;
        }

        public /* bridge */ /* synthetic */ void accept(Object p0) {
            if (!PatchProxy.proxy(new Object[]{p0}, this, changeQuickRedirect, false, 7670, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((List) p0);
            }
        }

        public final void a(@Nullable List<DCLVendorInfoBean> it) {
            if (!PatchProxy.proxy(new Object[]{it}, this, changeQuickRedirect, false, 7669, new Class[]{List.class}, Void.TYPE).isSupported) {
                kotlin.coroutines.d<List<DCLVendorInfoBean>> dVar = this.c;
                o.a aVar = o.Companion;
                dVar.resumeWith(o.m17constructorimpl(it));
            }
        }
    }

    /* compiled from: MTFabricCtrl.kt */
    public static final class b<T> implements io.reactivex.functions.e {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ kotlin.coroutines.d<List<DCLVendorInfoBean>> c;

        b(kotlin.coroutines.d<? super List<DCLVendorInfoBean>> dVar) {
            this.c = dVar;
        }

        public /* bridge */ /* synthetic */ void accept(Object p0) {
            if (!PatchProxy.proxy(new Object[]{p0}, this, changeQuickRedirect, false, 7672, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) p0);
            }
        }

        public final void a(Throwable th) {
            if (!PatchProxy.proxy(new Object[]{th}, this, changeQuickRedirect, false, 7671, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, "queryDCL error:", (String) null, 2, (Object) null);
                kotlin.coroutines.d<List<DCLVendorInfoBean>> dVar = this.c;
                ArrayList arrayList = new ArrayList();
                o.a aVar = o.Companion;
                dVar.resumeWith(o.m17constructorimpl(arrayList));
            }
        }
    }
}
