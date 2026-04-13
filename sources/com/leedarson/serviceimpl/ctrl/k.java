package com.leedarson.serviceimpl.ctrl;

import android.content.Context;
import chip.devicecontroller.ChipClusters;
import chip.devicecontroller.ChipDeviceController;
import chip.devicecontroller.ChipStructs;
import chip.devicecontroller.co1;
import chip.devicecontroller.gs1;
import chip.devicecontroller.sp1;
import com.leedarson.serviceimpl.bean.ControlDeviceBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kotlin.text.x;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.s1;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MTCmdCtrl.kt */
public final class k {
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    private Context a;
    @NotNull
    private ChipDeviceController b;
    private final int c = 1;
    /* access modifiers changed from: private */
    public final int d;
    private final int e = 1;
    private final int f = 1;
    private final int g = 1;
    /* access modifiers changed from: private */
    @NotNull
    public final com.leedarson.serviceimpl.manager.c h;

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTCmdCtrl", f = "MTCmdCtrl.kt", l = {164}, m = "CCTCommand")
    /* compiled from: MTCmdCtrl.kt */
    public static final class a extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        int I$0;
        int I$1;
        long J$0;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(k kVar, kotlin.coroutines.d<? super a> dVar) {
            super(dVar);
            this.this$0 = kVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7604, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return k.e(this.this$0, 0, 0, (ChipClusters.DefaultClusterCallback) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTCmdCtrl", f = "MTCmdCtrl.kt", l = {235}, m = "HSLCommand")
    /* compiled from: MTCmdCtrl.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        float F$0;
        float F$1;
        long J$0;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(k kVar, kotlin.coroutines.d<? super c> dVar) {
            super(dVar);
            this.this$0 = kVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7607, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return k.f(this.this$0, 0, (ControlDeviceBean) null, (ChipClusters.DefaultClusterCallback) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTCmdCtrl", f = "MTCmdCtrl.kt", l = {203}, m = "RGBCommand")
    /* compiled from: MTCmdCtrl.kt */
    public static final class e extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        float F$0;
        float F$1;
        long J$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(k kVar, kotlin.coroutines.d<? super e> dVar) {
            super(dVar);
            this.this$0 = kVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7610, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return k.g(this.this$0, 0, (ControlDeviceBean) null, (ChipClusters.DefaultClusterCallback) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTCmdCtrl", f = "MTCmdCtrl.kt", l = {73}, m = "off")
    /* compiled from: MTCmdCtrl.kt */
    public static final class j extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        long J$0;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        j(k kVar, kotlin.coroutines.d<? super j> dVar) {
            super(dVar);
            this.this$0 = kVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7629, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.p(0, (ChipClusters.DefaultClusterCallback) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTCmdCtrl", f = "MTCmdCtrl.kt", l = {49}, m = "on")
    /* compiled from: MTCmdCtrl.kt */
    public static final class l extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        long J$0;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        l(k kVar, kotlin.coroutines.d<? super l> dVar) {
            super(dVar);
            this.this$0 = kVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7632, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.q(0, (ChipClusters.DefaultClusterCallback) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTCmdCtrl", f = "MTCmdCtrl.kt", l = {131}, m = "sendLevelCommandClick")
    /* compiled from: MTCmdCtrl.kt */
    public static final class o extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        int I$0;
        long J$0;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        o(k kVar, kotlin.coroutines.d<? super o> dVar) {
            super(dVar);
            this.this$0 = kVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7650, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return k.j(this.this$0, 0, 0, (ChipClusters.DefaultClusterCallback) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTCmdCtrl", f = "MTCmdCtrl.kt", l = {284}, m = "sendReadVendorIDAttribute")
    /* compiled from: MTCmdCtrl.kt */
    public static final class q extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        long J$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        q(k kVar, kotlin.coroutines.d<? super q> dVar) {
            super(dVar);
            this.this$0 = kVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7653, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.t(0, (ChipClusters.BasicCluster.VendorIDAttributeCallback) null, this);
        }
    }

    public k(@NotNull Context context, @NotNull ChipDeviceController devController) {
        kotlin.jvm.internal.k.e(context, "context");
        kotlin.jvm.internal.k.e(devController, "devController");
        this.a = context;
        this.b = devController;
        this.h = new com.leedarson.serviceimpl.manager.c(context);
    }

    public static final /* synthetic */ Object e(k $this, long deviceId, int cct, ChipClusters.DefaultClusterCallback callback, kotlin.coroutines.d $completion) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this, new Long(deviceId), new Integer(cct), callback, $completion}, (Object) null, changeQuickRedirect, true, 7600, new Class[]{k.class, Long.TYPE, Integer.TYPE, ChipClusters.DefaultClusterCallback.class, kotlin.coroutines.d.class}, Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        return $this.a(deviceId, cct, callback, $completion);
    }

    public static final /* synthetic */ Object f(k $this, long deviceId, ControlDeviceBean controlDeviceBean, ChipClusters.DefaultClusterCallback callback, kotlin.coroutines.d $completion) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this, new Long(deviceId), controlDeviceBean, callback, $completion}, (Object) null, changeQuickRedirect, true, 7602, new Class[]{k.class, Long.TYPE, ControlDeviceBean.class, ChipClusters.DefaultClusterCallback.class, kotlin.coroutines.d.class}, Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        return $this.b(deviceId, controlDeviceBean, callback, $completion);
    }

    public static final /* synthetic */ Object g(k $this, long deviceId, ControlDeviceBean controlDeviceBean, ChipClusters.DefaultClusterCallback callback, kotlin.coroutines.d $completion) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this, new Long(deviceId), controlDeviceBean, callback, $completion}, (Object) null, changeQuickRedirect, true, 7601, new Class[]{k.class, Long.TYPE, ControlDeviceBean.class, ChipClusters.DefaultClusterCallback.class, kotlin.coroutines.d.class}, Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        return $this.d(deviceId, controlDeviceBean, callback, $completion);
    }

    public static final /* synthetic */ Object j(k $this, long deviceId, int progress, ChipClusters.DefaultClusterCallback callback, kotlin.coroutines.d $completion) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this, new Long(deviceId), new Integer(progress), callback, $completion}, (Object) null, changeQuickRedirect, true, 7599, new Class[]{k.class, Long.TYPE, Integer.TYPE, ChipClusters.DefaultClusterCallback.class, kotlin.coroutines.d.class}, Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        return $this.s(deviceId, progress, callback, $completion);
    }

    public final boolean u(long deviceId, @Nullable ChipClusters.BaseChipCluster cluster, @NotNull ChipClusters.DefaultClusterCallback callback) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Long(deviceId), cluster, callback}, this, changeQuickRedirect, false, 7581, new Class[]{Long.TYPE, ChipClusters.BaseChipCluster.class, ChipClusters.DefaultClusterCallback.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        kotlin.jvm.internal.k.e(callback, "callback");
        if (cluster != null) {
            return true;
        }
        com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("on fail:could not find cluster by deviceId:", Long.valueOf(deviceId)), (String) null, 2, (Object) null);
        callback.onError(new IllegalArgumentException(kotlin.jvm.internal.k.l("could not find cluster by deviceId:", Long.valueOf(deviceId))));
        return false;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: chip.devicecontroller.ChipClusters$DefaultClusterCallback} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: com.leedarson.serviceimpl.ctrl.k} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0063  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00ef  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object q(long r18, @org.jetbrains.annotations.NotNull chip.devicecontroller.ChipClusters.DefaultClusterCallback r20, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r21) {
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
            java.lang.Class<chip.devicecontroller.ChipClusters$DefaultClusterCallback> r1 = chip.devicecontroller.ChipClusters.DefaultClusterCallback.class
            r7[r11] = r1
            java.lang.Class<kotlin.coroutines.d> r1 = kotlin.coroutines.d.class
            r7[r12] = r1
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            r1 = 0
            r6 = 7582(0x1d9e, float:1.0625E-41)
            r3 = r17
            r4 = r5
            r5 = r1
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0039
            java.lang.Object r0 = r1.result
            return r0
        L_0x0039:
            boolean r1 = r0 instanceof com.leedarson.serviceimpl.ctrl.k.l
            if (r1 == 0) goto L_0x004f
            r1 = r0
            com.leedarson.serviceimpl.ctrl.k$l r1 = (com.leedarson.serviceimpl.ctrl.k.l) r1
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
            com.leedarson.serviceimpl.ctrl.k$l r1 = new com.leedarson.serviceimpl.ctrl.k$l
            r2 = r17
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0057:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            r5 = 0
            switch(r4) {
                case 0: goto L_0x0088;
                case 1: goto L_0x006b;
                default: goto L_0x0063;
            }
        L_0x0063:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x006b:
            r3 = r17
            r4 = r5
            r5 = r18
            r7 = r20
            long r5 = r0.J$0
            java.lang.Object r8 = r0.L$2
            r4 = r8
            java.lang.String r4 = (java.lang.String) r4
            java.lang.Object r8 = r0.L$1
            r7 = r8
            chip.devicecontroller.ChipClusters$DefaultClusterCallback r7 = (chip.devicecontroller.ChipClusters.DefaultClusterCallback) r7
            java.lang.Object r8 = r0.L$0
            r3 = r8
            com.leedarson.serviceimpl.ctrl.k r3 = (com.leedarson.serviceimpl.ctrl.k) r3
            kotlin.p.b(r1)
            r9 = r1
            goto L_0x00cf
        L_0x0088:
            kotlin.p.b(r1)
            r4 = r17
            r6 = r18
            r8 = r20
            com.leedarson.serviceimpl.k r9 = com.leedarson.serviceimpl.k.a
            java.lang.Long r10 = kotlin.coroutines.jvm.internal.b.d(r6)
            java.lang.String r13 = "on ,addr:"
            java.lang.String r10 = kotlin.jvm.internal.k.l(r13, r10)
            com.leedarson.serviceimpl.k.b(r9, r10, r5, r12, r5)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r9 = "Matter 设备("
            r5.append(r9)
            r5.append(r6)
            java.lang.String r9 = ")控制 on "
            r5.append(r9)
            java.lang.String r5 = r5.toString()
            com.leedarson.serviceimpl.manager.c r9 = r4.h
            int r10 = r4.c
            r0.L$0 = r4
            r0.L$1 = r8
            r0.L$2 = r5
            r0.J$0 = r6
            r0.label = r11
            java.lang.Object r9 = r9.j(r6, r10, r0)
            if (r9 != r3) goto L_0x00cb
            return r3
        L_0x00cb:
            r3 = r4
            r4 = r5
            r5 = r6
            r7 = r8
        L_0x00cf:
            r8 = r9
            chip.devicecontroller.ChipClusters$OnOffCluster r8 = (chip.devicecontroller.ChipClusters.OnOffCluster) r8
            boolean r9 = r3.u(r5, r8, r7)
            if (r9 != 0) goto L_0x00ef
            com.leedarson.serviceimpl.k r10 = com.leedarson.serviceimpl.k.a
            java.lang.String r9 = ",result:error :Unable to get connected device"
            java.lang.String r11 = kotlin.jvm.internal.k.l(r4, r9)
            r14 = 0
            r15 = 8
            r16 = 0
            java.lang.String r12 = "info"
            java.lang.String r13 = "controlDevice"
            com.leedarson.serviceimpl.k.k(r10, r11, r12, r13, r14, r15, r16)
            kotlin.x r9 = kotlin.x.a
            return r9
        L_0x00ef:
            if (r8 != 0) goto L_0x00f2
            goto L_0x00fa
        L_0x00f2:
            com.leedarson.serviceimpl.ctrl.k$m r9 = new com.leedarson.serviceimpl.ctrl.k$m
            r9.<init>(r4, r7)
            r8.on(r9)
        L_0x00fa:
            kotlin.x r9 = kotlin.x.a
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.k.q(long, chip.devicecontroller.ChipClusters$DefaultClusterCallback, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: MTCmdCtrl.kt */
    public static final class m implements ChipClusters.DefaultClusterCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ ChipClusters.DefaultClusterCallback b;

        m(String $elkMsg, ChipClusters.DefaultClusterCallback $callback) {
            this.a = $elkMsg;
            this.b = $callback;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7633, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k.k(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l(this.a, ",result:success"), "info", "controlDevice", (String) null, 8, (Object) null);
                ChipClusters.DefaultClusterCallback defaultClusterCallback = this.b;
                if (defaultClusterCallback != null) {
                    defaultClusterCallback.onSuccess();
                }
            }
        }

        public void onError(@Nullable Exception p0) {
            if (!PatchProxy.proxy(new Object[]{p0}, this, changeQuickRedirect, false, 7634, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                String str = null;
                com.leedarson.serviceimpl.k.e(kVar, kotlin.jvm.internal.k.l("on onError:", p0 == null ? null : p0.getMessage()), (String) null, 2, (Object) null);
                StringBuilder sb = new StringBuilder();
                sb.append(this.a);
                sb.append(",result:error :");
                if (p0 != null) {
                    str = p0.getMessage();
                }
                sb.append(str);
                com.leedarson.serviceimpl.k.k(kVar, sb.toString(), "info", "controlDevice", (String) null, 8, (Object) null);
                ChipClusters.DefaultClusterCallback defaultClusterCallback = this.b;
                if (defaultClusterCallback != null) {
                    defaultClusterCallback.onError(p0);
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: chip.devicecontroller.ChipClusters$DefaultClusterCallback} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: com.leedarson.serviceimpl.ctrl.k} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0063  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00ef  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object p(long r18, @org.jetbrains.annotations.NotNull chip.devicecontroller.ChipClusters.DefaultClusterCallback r20, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r21) {
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
            java.lang.Class<chip.devicecontroller.ChipClusters$DefaultClusterCallback> r1 = chip.devicecontroller.ChipClusters.DefaultClusterCallback.class
            r7[r11] = r1
            java.lang.Class<kotlin.coroutines.d> r1 = kotlin.coroutines.d.class
            r7[r12] = r1
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            r1 = 0
            r6 = 7583(0x1d9f, float:1.0626E-41)
            r3 = r17
            r4 = r5
            r5 = r1
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0039
            java.lang.Object r0 = r1.result
            return r0
        L_0x0039:
            boolean r1 = r0 instanceof com.leedarson.serviceimpl.ctrl.k.j
            if (r1 == 0) goto L_0x004f
            r1 = r0
            com.leedarson.serviceimpl.ctrl.k$j r1 = (com.leedarson.serviceimpl.ctrl.k.j) r1
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
            com.leedarson.serviceimpl.ctrl.k$j r1 = new com.leedarson.serviceimpl.ctrl.k$j
            r2 = r17
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0057:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            r5 = 0
            switch(r4) {
                case 0: goto L_0x0088;
                case 1: goto L_0x006b;
                default: goto L_0x0063;
            }
        L_0x0063:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x006b:
            r3 = r17
            r4 = r5
            r5 = r18
            r7 = r20
            long r5 = r0.J$0
            java.lang.Object r8 = r0.L$2
            r4 = r8
            java.lang.String r4 = (java.lang.String) r4
            java.lang.Object r8 = r0.L$1
            r7 = r8
            chip.devicecontroller.ChipClusters$DefaultClusterCallback r7 = (chip.devicecontroller.ChipClusters.DefaultClusterCallback) r7
            java.lang.Object r8 = r0.L$0
            r3 = r8
            com.leedarson.serviceimpl.ctrl.k r3 = (com.leedarson.serviceimpl.ctrl.k) r3
            kotlin.p.b(r1)
            r9 = r1
            goto L_0x00cf
        L_0x0088:
            kotlin.p.b(r1)
            r4 = r17
            r6 = r18
            r8 = r20
            com.leedarson.serviceimpl.k r9 = com.leedarson.serviceimpl.k.a
            java.lang.Long r10 = kotlin.coroutines.jvm.internal.b.d(r6)
            java.lang.String r13 = "off, addr:"
            java.lang.String r10 = kotlin.jvm.internal.k.l(r13, r10)
            com.leedarson.serviceimpl.k.b(r9, r10, r5, r12, r5)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r9 = "Matter 设备("
            r5.append(r9)
            r5.append(r6)
            java.lang.String r9 = ")控制 off "
            r5.append(r9)
            java.lang.String r5 = r5.toString()
            com.leedarson.serviceimpl.manager.c r9 = r4.h
            int r10 = r4.c
            r0.L$0 = r4
            r0.L$1 = r8
            r0.L$2 = r5
            r0.J$0 = r6
            r0.label = r11
            java.lang.Object r9 = r9.j(r6, r10, r0)
            if (r9 != r3) goto L_0x00cb
            return r3
        L_0x00cb:
            r3 = r4
            r4 = r5
            r5 = r6
            r7 = r8
        L_0x00cf:
            r8 = r9
            chip.devicecontroller.ChipClusters$OnOffCluster r8 = (chip.devicecontroller.ChipClusters.OnOffCluster) r8
            boolean r9 = r3.u(r5, r8, r7)
            if (r9 != 0) goto L_0x00ef
            com.leedarson.serviceimpl.k r10 = com.leedarson.serviceimpl.k.a
            java.lang.String r9 = ",result:error :Unable to get connected device"
            java.lang.String r11 = kotlin.jvm.internal.k.l(r4, r9)
            r14 = 0
            r15 = 8
            r16 = 0
            java.lang.String r12 = "info"
            java.lang.String r13 = "controlDevice"
            com.leedarson.serviceimpl.k.k(r10, r11, r12, r13, r14, r15, r16)
            kotlin.x r9 = kotlin.x.a
            return r9
        L_0x00ef:
            if (r8 != 0) goto L_0x00f2
            goto L_0x00fa
        L_0x00f2:
            com.leedarson.serviceimpl.ctrl.k$k r9 = new com.leedarson.serviceimpl.ctrl.k$k
            r9.<init>(r4, r7)
            r8.off(r9)
        L_0x00fa:
            kotlin.x r9 = kotlin.x.a
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.k.p(long, chip.devicecontroller.ChipClusters$DefaultClusterCallback, kotlin.coroutines.d):java.lang.Object");
    }

    /* renamed from: com.leedarson.serviceimpl.ctrl.k$k  reason: collision with other inner class name */
    /* compiled from: MTCmdCtrl.kt */
    public static final class C0134k implements ChipClusters.DefaultClusterCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ ChipClusters.DefaultClusterCallback b;

        C0134k(String $elkMsg, ChipClusters.DefaultClusterCallback $callback) {
            this.a = $elkMsg;
            this.b = $callback;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7630, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k.k(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l(this.a, ",result:success"), "info", "controlDevice", (String) null, 8, (Object) null);
                ChipClusters.DefaultClusterCallback defaultClusterCallback = this.b;
                if (defaultClusterCallback != null) {
                    defaultClusterCallback.onSuccess();
                }
            }
        }

        public void onError(@Nullable Exception p0) {
            if (!PatchProxy.proxy(new Object[]{p0}, this, changeQuickRedirect, false, 7631, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                String str = null;
                com.leedarson.serviceimpl.k.e(kVar, kotlin.jvm.internal.k.l("off onError:", p0 == null ? null : p0.getMessage()), (String) null, 2, (Object) null);
                StringBuilder sb = new StringBuilder();
                sb.append(this.a);
                sb.append(",result:error :");
                if (p0 != null) {
                    str = p0.getMessage();
                }
                sb.append(str);
                com.leedarson.serviceimpl.k.k(kVar, sb.toString(), "info", "controlDevice", (String) null, 8, (Object) null);
                ChipClusters.DefaultClusterCallback defaultClusterCallback = this.b;
                if (defaultClusterCallback != null) {
                    defaultClusterCallback.onError(p0);
                }
            }
        }
    }

    public final int l(int dimming) {
        Object[] objArr = {new Integer(dimming)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7584, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int level = (int) ((((float) dimming) / 100.0f) * 254.0f);
        int times = 0;
        int returnDimming = 0;
        while (returnDimming != dimming && times < 5) {
            returnDimming = m(level);
            if (returnDimming < dimming) {
                level++;
            } else if (returnDimming > dimming) {
                level--;
            }
            times++;
        }
        return level;
    }

    public final int m(int level) {
        return (int) ((((float) level) / 254.0f) * ((float) 100));
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: chip.devicecontroller.ChipClusters$DefaultClusterCallback} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: com.leedarson.serviceimpl.ctrl.k} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x006f  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0114  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x012c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.Object s(long r24, int r26, chip.devicecontroller.ChipClusters.DefaultClusterCallback r27, kotlin.coroutines.d<? super kotlin.x> r28) {
        /*
            r23 = this;
            r0 = r28
            r1 = 4
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.Long r3 = new java.lang.Long
            r9 = r24
            r3.<init>(r9)
            r11 = 0
            r2[r11] = r3
            java.lang.Integer r3 = new java.lang.Integer
            r12 = r26
            r3.<init>(r12)
            r13 = 1
            r2[r13] = r3
            r14 = 2
            r2[r14] = r27
            r3 = 3
            r2[r3] = r0
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class r1 = java.lang.Long.TYPE
            r7[r11] = r1
            java.lang.Class r1 = java.lang.Integer.TYPE
            r7[r13] = r1
            java.lang.Class<chip.devicecontroller.ChipClusters$DefaultClusterCallback> r1 = chip.devicecontroller.ChipClusters.DefaultClusterCallback.class
            r7[r14] = r1
            java.lang.Class<kotlin.coroutines.d> r1 = kotlin.coroutines.d.class
            r7[r3] = r1
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            r5 = 0
            r6 = 7585(0x1da1, float:1.0629E-41)
            r3 = r23
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0045
            java.lang.Object r0 = r1.result
            return r0
        L_0x0045:
            boolean r1 = r0 instanceof com.leedarson.serviceimpl.ctrl.k.o
            if (r1 == 0) goto L_0x005b
            r1 = r0
            com.leedarson.serviceimpl.ctrl.k$o r1 = (com.leedarson.serviceimpl.ctrl.k.o) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x005b
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r23
            goto L_0x0063
        L_0x005b:
            com.leedarson.serviceimpl.ctrl.k$o r1 = new com.leedarson.serviceimpl.ctrl.k$o
            r2 = r23
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0063:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            r5 = 0
            switch(r4) {
                case 0: goto L_0x0098;
                case 1: goto L_0x0077;
                default: goto L_0x006f;
            }
        L_0x006f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0077:
            r3 = r23
            r4 = r27
            r6 = r11
            r7 = r24
            int r6 = r0.I$0
            long r7 = r0.J$0
            java.lang.Object r9 = r0.L$2
            r5 = r9
            java.lang.String r5 = (java.lang.String) r5
            java.lang.Object r9 = r0.L$1
            r4 = r9
            chip.devicecontroller.ChipClusters$DefaultClusterCallback r4 = (chip.devicecontroller.ChipClusters.DefaultClusterCallback) r4
            java.lang.Object r9 = r0.L$0
            r3 = r9
            com.leedarson.serviceimpl.ctrl.k r3 = (com.leedarson.serviceimpl.ctrl.k) r3
            kotlin.p.b(r1)
            r12 = r5
            r5 = r1
            goto L_0x010b
        L_0x0098:
            kotlin.p.b(r1)
            r4 = r23
            r6 = r27
            r7 = r24
            r9 = r26
            int r10 = r9 + -1
            int r10 = r4.l(r10)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r15 = "Matter 设备("
            r12.append(r15)
            r12.append(r7)
            java.lang.String r15 = ")控制 dim, 原参数:"
            r12.append(r15)
            r12.append(r9)
            java.lang.String r15 = ",转换后 :"
            r12.append(r15)
            r12.append(r10)
            java.lang.String r12 = r12.toString()
            com.leedarson.serviceimpl.k r15 = com.leedarson.serviceimpl.k.a
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r13 = "Lightness:"
            r11.append(r13)
            r11.append(r9)
            java.lang.String r13 = ",matterDim"
            r11.append(r13)
            r11.append(r10)
            java.lang.String r13 = " ,addr:"
            r11.append(r13)
            r11.append(r7)
            java.lang.String r11 = r11.toString()
            com.leedarson.serviceimpl.k.b(r15, r11, r5, r14, r5)
            com.leedarson.serviceimpl.manager.c r5 = r4.h
            int r11 = r4.e
            r0.L$0 = r4
            r0.L$1 = r6
            r0.L$2 = r12
            r0.J$0 = r7
            r0.I$0 = r10
            r13 = 1
            r0.label = r13
            java.lang.Object r5 = r5.i(r7, r11, r0)
            if (r5 != r3) goto L_0x0108
            return r3
        L_0x0108:
            r3 = r4
            r4 = r6
            r6 = r10
        L_0x010b:
            chip.devicecontroller.ChipClusters$LevelControlCluster r5 = (chip.devicecontroller.ChipClusters.LevelControlCluster) r5
            boolean r9 = r3.u(r7, r5, r4)
            if (r9 != 0) goto L_0x012c
            com.leedarson.serviceimpl.k r16 = com.leedarson.serviceimpl.k.a
            java.lang.String r9 = ",result:error :Unable to get connected device"
            java.lang.String r17 = kotlin.jvm.internal.k.l(r12, r9)
            r20 = 0
            r21 = 8
            r22 = 0
            java.lang.String r18 = "info"
            java.lang.String r19 = "controlDevice"
            com.leedarson.serviceimpl.k.k(r16, r17, r18, r19, r20, r21, r22)
            kotlin.x r9 = kotlin.x.a
            return r9
        L_0x012c:
            if (r5 != 0) goto L_0x012f
            goto L_0x014c
        L_0x012f:
            com.leedarson.serviceimpl.ctrl.k$p r9 = new com.leedarson.serviceimpl.ctrl.k$p
            r9.<init>(r12, r4)
            java.lang.Integer r18 = kotlin.coroutines.jvm.internal.b.c(r6)
            r10 = 0
            java.lang.Integer r19 = kotlin.coroutines.jvm.internal.b.c(r10)
            java.lang.Integer r20 = kotlin.coroutines.jvm.internal.b.c(r10)
            java.lang.Integer r21 = kotlin.coroutines.jvm.internal.b.c(r10)
            r16 = r5
            r17 = r9
            r16.moveToLevel(r17, r18, r19, r20, r21)
        L_0x014c:
            kotlin.x r9 = kotlin.x.a
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.k.s(long, int, chip.devicecontroller.ChipClusters$DefaultClusterCallback, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: MTCmdCtrl.kt */
    public static final class p implements ChipClusters.DefaultClusterCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ ChipClusters.DefaultClusterCallback b;

        p(String $elkMsg, ChipClusters.DefaultClusterCallback $callback) {
            this.a = $elkMsg;
            this.b = $callback;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7651, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k.k(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l(this.a, ",result:success"), "info", "controlDevice", (String) null, 8, (Object) null);
                ChipClusters.DefaultClusterCallback defaultClusterCallback = this.b;
                if (defaultClusterCallback != null) {
                    defaultClusterCallback.onSuccess();
                }
            }
        }

        public void onError(@NotNull Exception ex) {
            if (!PatchProxy.proxy(new Object[]{ex}, this, changeQuickRedirect, false, 7652, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                kotlin.jvm.internal.k.e(ex, "ex");
                com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                com.leedarson.serviceimpl.k.e(kVar, kotlin.jvm.internal.k.l("MoveToLevel command failure ", ex), (String) null, 2, (Object) null);
                com.leedarson.serviceimpl.k.k(kVar, this.a + ",result:failure " + ex, "info", "controlDevice", (String) null, 8, (Object) null);
                ChipClusters.DefaultClusterCallback defaultClusterCallback = this.b;
                if (defaultClusterCallback != null) {
                    defaultClusterCallback.onError(ex);
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: chip.devicecontroller.ChipClusters$DefaultClusterCallback} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: com.leedarson.serviceimpl.ctrl.k} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x006f  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0113  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x012b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.Object a(long r21, int r23, chip.devicecontroller.ChipClusters.DefaultClusterCallback r24, kotlin.coroutines.d<? super kotlin.x> r25) {
        /*
            r20 = this;
            r0 = r25
            r1 = 4
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.Long r3 = new java.lang.Long
            r9 = r21
            r3.<init>(r9)
            r11 = 0
            r2[r11] = r3
            java.lang.Integer r3 = new java.lang.Integer
            r12 = r23
            r3.<init>(r12)
            r13 = 1
            r2[r13] = r3
            r14 = 2
            r2[r14] = r24
            r3 = 3
            r2[r3] = r0
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class r1 = java.lang.Long.TYPE
            r7[r11] = r1
            java.lang.Class r1 = java.lang.Integer.TYPE
            r7[r13] = r1
            java.lang.Class<chip.devicecontroller.ChipClusters$DefaultClusterCallback> r1 = chip.devicecontroller.ChipClusters.DefaultClusterCallback.class
            r7[r14] = r1
            java.lang.Class<kotlin.coroutines.d> r1 = kotlin.coroutines.d.class
            r7[r3] = r1
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            r5 = 0
            r6 = 7586(0x1da2, float:1.063E-41)
            r3 = r20
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0045
            java.lang.Object r0 = r1.result
            return r0
        L_0x0045:
            boolean r1 = r0 instanceof com.leedarson.serviceimpl.ctrl.k.a
            if (r1 == 0) goto L_0x005b
            r1 = r0
            com.leedarson.serviceimpl.ctrl.k$a r1 = (com.leedarson.serviceimpl.ctrl.k.a) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x005b
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r20
            goto L_0x0063
        L_0x005b:
            com.leedarson.serviceimpl.ctrl.k$a r1 = new com.leedarson.serviceimpl.ctrl.k$a
            r2 = r20
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0063:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            r5 = 0
            switch(r4) {
                case 0: goto L_0x009c;
                case 1: goto L_0x0077;
                default: goto L_0x006f;
            }
        L_0x006f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0077:
            r3 = r20
            r4 = r24
            r6 = r11
            r7 = r11
            r8 = r21
            int r6 = r0.I$1
            int r7 = r0.I$0
            long r8 = r0.J$0
            java.lang.Object r10 = r0.L$2
            r5 = r10
            java.lang.String r5 = (java.lang.String) r5
            java.lang.Object r10 = r0.L$1
            r4 = r10
            chip.devicecontroller.ChipClusters$DefaultClusterCallback r4 = (chip.devicecontroller.ChipClusters.DefaultClusterCallback) r4
            java.lang.Object r10 = r0.L$0
            r3 = r10
            com.leedarson.serviceimpl.ctrl.k r3 = (com.leedarson.serviceimpl.ctrl.k) r3
            kotlin.p.b(r1)
            r9 = r8
            r8 = r7
            r7 = r1
            goto L_0x010a
        L_0x009c:
            kotlin.p.b(r1)
            r4 = r20
            r6 = r24
            r8 = r21
            r7 = r23
            com.leedarson.serviceimpl.k r10 = com.leedarson.serviceimpl.k.a
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r15 = "CCT Command:"
            r12.append(r15)
            r12.append(r7)
            java.lang.String r15 = " ,addr:"
            r12.append(r15)
            r12.append(r8)
            java.lang.String r12 = r12.toString()
            com.leedarson.serviceimpl.k.b(r10, r12, r5, r14, r5)
            r5 = 0
            r10 = 1000000(0xf4240, float:1.401298E-39)
            int r10 = r10 / r7
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r14 = "Matter 设备("
            r12.append(r14)
            r12.append(r8)
            java.lang.String r14 = ")控制 CCT, 原参数:"
            r12.append(r14)
            r12.append(r7)
            java.lang.String r14 = ",转换后 :"
            r12.append(r14)
            r12.append(r10)
            java.lang.String r12 = r12.toString()
            com.leedarson.serviceimpl.manager.c r14 = r4.h
            int r15 = r4.f
            r0.L$0 = r4
            r0.L$1 = r6
            r0.L$2 = r12
            r0.J$0 = r8
            r0.I$0 = r5
            r0.I$1 = r10
            r0.label = r13
            java.lang.Object r7 = r14.c(r8, r15, r0)
            if (r7 != r3) goto L_0x0104
            return r3
        L_0x0104:
            r3 = r4
            r4 = r6
            r6 = r10
            r9 = r8
            r8 = r5
            r5 = r12
        L_0x010a:
            chip.devicecontroller.ChipClusters$ColorControlCluster r7 = (chip.devicecontroller.ChipClusters.ColorControlCluster) r7
            boolean r12 = r3.u(r9, r7, r4)
            if (r12 != 0) goto L_0x012b
            com.leedarson.serviceimpl.k r13 = com.leedarson.serviceimpl.k.a
            java.lang.String r11 = ",result:error :Unable to get connected device"
            java.lang.String r14 = kotlin.jvm.internal.k.l(r5, r11)
            r17 = 0
            r18 = 8
            r19 = 0
            java.lang.String r15 = "info"
            java.lang.String r16 = "controlDevice"
            com.leedarson.serviceimpl.k.k(r13, r14, r15, r16, r17, r18, r19)
            kotlin.x r11 = kotlin.x.a
            return r11
        L_0x012b:
            if (r7 != 0) goto L_0x012e
            goto L_0x0147
        L_0x012e:
            com.leedarson.serviceimpl.ctrl.k$b r13 = new com.leedarson.serviceimpl.ctrl.k$b
            r13.<init>(r5, r4)
            java.lang.Integer r14 = kotlin.coroutines.jvm.internal.b.c(r6)
            java.lang.Integer r15 = kotlin.coroutines.jvm.internal.b.c(r8)
            java.lang.Integer r16 = kotlin.coroutines.jvm.internal.b.c(r11)
            java.lang.Integer r17 = kotlin.coroutines.jvm.internal.b.c(r11)
            r12 = r7
            r12.moveToColorTemperature(r13, r14, r15, r16, r17)
        L_0x0147:
            kotlin.x r11 = kotlin.x.a
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.k.a(long, int, chip.devicecontroller.ChipClusters$DefaultClusterCallback, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: MTCmdCtrl.kt */
    public static final class b implements ChipClusters.DefaultClusterCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ ChipClusters.DefaultClusterCallback b;

        b(String $elkMsg, ChipClusters.DefaultClusterCallback $callback) {
            this.a = $elkMsg;
            this.b = $callback;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7605, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k.k(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l(this.a, ",result:success"), "info", "controlDevice", (String) null, 8, (Object) null);
                ChipClusters.DefaultClusterCallback defaultClusterCallback = this.b;
                if (defaultClusterCallback != null) {
                    defaultClusterCallback.onSuccess();
                }
            }
        }

        public void onError(@NotNull Exception ex) {
            if (!PatchProxy.proxy(new Object[]{ex}, this, changeQuickRedirect, false, 7606, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                kotlin.jvm.internal.k.e(ex, "ex");
                com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                com.leedarson.serviceimpl.k.e(kVar, kotlin.jvm.internal.k.l("MoveToLevel command failure ", ex), (String) null, 2, (Object) null);
                com.leedarson.serviceimpl.k.k(kVar, this.a + ",result:failure " + ex, "info", "controlDevice", (String) null, 8, (Object) null);
                ChipClusters.DefaultClusterCallback defaultClusterCallback = this.b;
                if (defaultClusterCallback != null) {
                    defaultClusterCallback.onError(ex);
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: chip.devicecontroller.ChipClusters$DefaultClusterCallback} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: com.leedarson.serviceimpl.ctrl.k} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0071  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0090  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0138  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x013b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.Object d(long r18, com.leedarson.serviceimpl.bean.ControlDeviceBean r20, chip.devicecontroller.ChipClusters.DefaultClusterCallback r21, kotlin.coroutines.d<? super kotlin.x> r22) {
        /*
            r17 = this;
            r0 = r22
            r1 = 4
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.Long r3 = new java.lang.Long
            r9 = r18
            r3.<init>(r9)
            r11 = 0
            r2[r11] = r3
            r12 = 1
            r2[r12] = r20
            r13 = 2
            r2[r13] = r21
            r3 = 3
            r2[r3] = r0
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class r1 = java.lang.Long.TYPE
            r7[r11] = r1
            java.lang.Class<com.leedarson.serviceimpl.bean.ControlDeviceBean> r1 = com.leedarson.serviceimpl.bean.ControlDeviceBean.class
            r7[r12] = r1
            java.lang.Class<chip.devicecontroller.ChipClusters$DefaultClusterCallback> r1 = chip.devicecontroller.ChipClusters.DefaultClusterCallback.class
            r7[r13] = r1
            java.lang.Class<kotlin.coroutines.d> r1 = kotlin.coroutines.d.class
            r7[r3] = r1
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            r5 = 0
            r6 = 7587(0x1da3, float:1.0632E-41)
            r3 = r17
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x003e
            java.lang.Object r0 = r1.result
            return r0
        L_0x003e:
            boolean r1 = r0 instanceof com.leedarson.serviceimpl.ctrl.k.e
            if (r1 == 0) goto L_0x0054
            r1 = r0
            com.leedarson.serviceimpl.ctrl.k$e r1 = (com.leedarson.serviceimpl.ctrl.k.e) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0054
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r17
            goto L_0x005c
        L_0x0054:
            com.leedarson.serviceimpl.ctrl.k$e r1 = new com.leedarson.serviceimpl.ctrl.k$e
            r2 = r17
            r1.<init>(r2, r0)
            r0 = r1
        L_0x005c:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            switch(r4) {
                case 0: goto L_0x0090;
                case 1: goto L_0x0071;
                default: goto L_0x0067;
            }
        L_0x0067:
            r16 = r1
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0071:
            r3 = r17
            r4 = r21
            r5 = 0
            r6 = r5
            r7 = r18
            float r5 = r0.F$1
            float r6 = r0.F$0
            long r7 = r0.J$0
            java.lang.Object r9 = r0.L$1
            r4 = r9
            chip.devicecontroller.ChipClusters$DefaultClusterCallback r4 = (chip.devicecontroller.ChipClusters.DefaultClusterCallback) r4
            java.lang.Object r9 = r0.L$0
            r3 = r9
            com.leedarson.serviceimpl.ctrl.k r3 = (com.leedarson.serviceimpl.ctrl.k) r3
            kotlin.p.b(r1)
            r16 = r1
            goto L_0x012f
        L_0x0090:
            kotlin.p.b(r1)
            r4 = r17
            r5 = r21
            r7 = r18
            r6 = r20
            int[] r9 = r6.parseRGB()
            com.leedarson.serviceimpl.k r10 = com.leedarson.serviceimpl.k.a
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r15 = "RGB Command RGBW:"
            r14.append(r15)
            long r12 = r6.getRGBW()
            r14.append(r12)
            java.lang.String r12 = ",r:"
            r14.append(r12)
            r12 = r9[r11]
            r14.append(r12)
            java.lang.String r12 = ",g:"
            r14.append(r12)
            r12 = 1
            r13 = r9[r12]
            r14.append(r13)
            java.lang.String r12 = ",b:"
            r14.append(r12)
            r12 = 2
            r13 = r9[r12]
            r14.append(r13)
            java.lang.String r13 = ",addr:"
            r14.append(r13)
            r14.append(r7)
            java.lang.String r13 = r14.toString()
            r14 = 0
            com.leedarson.serviceimpl.k.b(r10, r13, r14, r12, r14)
            r13 = r9[r11]
            r15 = 1
            r14 = r9[r15]
            r15 = r9[r12]
            float[] r12 = r6.getXY2(r13, r14, r15)
            r13 = r12[r11]
            r14 = 1
            r15 = r12[r14]
            r14 = r15
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r11 = "RGB Command colorX:"
            r15.append(r11)
            r15.append(r13)
            java.lang.String r11 = ",colorY:"
            r15.append(r11)
            r15.append(r14)
            java.lang.String r11 = r15.toString()
            r16 = r1
            r1 = 2
            r15 = 0
            com.leedarson.serviceimpl.k.b(r10, r11, r15, r1, r15)
            com.leedarson.serviceimpl.manager.c r1 = r4.h
            int r10 = r4.g
            r0.L$0 = r4
            r0.L$1 = r5
            r0.J$0 = r7
            r0.F$0 = r13
            r0.F$1 = r14
            r11 = 1
            r0.label = r11
            java.lang.Object r1 = r1.c(r7, r10, r0)
            if (r1 != r3) goto L_0x012b
            return r3
        L_0x012b:
            r3 = r4
            r4 = r5
            r6 = r13
            r5 = r14
        L_0x012f:
            chip.devicecontroller.ChipClusters$ColorControlCluster r1 = (chip.devicecontroller.ChipClusters.ColorControlCluster) r1
            boolean r9 = r3.u(r7, r1, r4)
            if (r9 != 0) goto L_0x013b
            kotlin.x r9 = kotlin.x.a
            return r9
        L_0x013b:
            if (r1 != 0) goto L_0x013e
            goto L_0x015e
        L_0x013e:
            com.leedarson.serviceimpl.ctrl.k$f r10 = new com.leedarson.serviceimpl.ctrl.k$f
            r10.<init>(r4)
            int r9 = (int) r6
            java.lang.Integer r11 = kotlin.coroutines.jvm.internal.b.c(r9)
            int r9 = (int) r5
            java.lang.Integer r12 = kotlin.coroutines.jvm.internal.b.c(r9)
            r9 = 0
            java.lang.Integer r13 = kotlin.coroutines.jvm.internal.b.c(r9)
            java.lang.Integer r14 = kotlin.coroutines.jvm.internal.b.c(r9)
            java.lang.Integer r15 = kotlin.coroutines.jvm.internal.b.c(r9)
            r9 = r1
            r9.moveToColor(r10, r11, r12, r13, r14, r15)
        L_0x015e:
            kotlin.x r9 = kotlin.x.a
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.k.d(long, com.leedarson.serviceimpl.bean.ControlDeviceBean, chip.devicecontroller.ChipClusters$DefaultClusterCallback, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: MTCmdCtrl.kt */
    public static final class f implements ChipClusters.DefaultClusterCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ChipClusters.DefaultClusterCallback a;

        f(ChipClusters.DefaultClusterCallback $callback) {
            this.a = $callback;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7611, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k.h(com.leedarson.serviceimpl.k.a, "MoveToLevel command success", (String) null, 2, (Object) null);
                ChipClusters.DefaultClusterCallback defaultClusterCallback = this.a;
                if (defaultClusterCallback != null) {
                    defaultClusterCallback.onSuccess();
                }
            }
        }

        public void onError(@NotNull Exception ex) {
            if (!PatchProxy.proxy(new Object[]{ex}, this, changeQuickRedirect, false, 7612, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                kotlin.jvm.internal.k.e(ex, "ex");
                com.leedarson.serviceimpl.k.h(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("MoveToLevel command failure ", ex), (String) null, 2, (Object) null);
                ChipClusters.DefaultClusterCallback defaultClusterCallback = this.a;
                if (defaultClusterCallback != null) {
                    defaultClusterCallback.onError(ex);
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x01ba  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x01d0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.Object b(long r24, com.leedarson.serviceimpl.bean.ControlDeviceBean r26, chip.devicecontroller.ChipClusters.DefaultClusterCallback r27, kotlin.coroutines.d<? super kotlin.x> r28) {
        /*
            r23 = this;
            r0 = r28
            r1 = 4
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.Long r3 = new java.lang.Long
            r9 = r24
            r3.<init>(r9)
            r11 = 0
            r2[r11] = r3
            r12 = 1
            r2[r12] = r26
            r13 = 2
            r2[r13] = r27
            r3 = 3
            r2[r3] = r0
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class r1 = java.lang.Long.TYPE
            r7[r11] = r1
            java.lang.Class<com.leedarson.serviceimpl.bean.ControlDeviceBean> r1 = com.leedarson.serviceimpl.bean.ControlDeviceBean.class
            r7[r12] = r1
            java.lang.Class<chip.devicecontroller.ChipClusters$DefaultClusterCallback> r1 = chip.devicecontroller.ChipClusters.DefaultClusterCallback.class
            r7[r13] = r1
            java.lang.Class<kotlin.coroutines.d> r1 = kotlin.coroutines.d.class
            r7[r3] = r1
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            r5 = 0
            r6 = 7588(0x1da4, float:1.0633E-41)
            r3 = r23
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x003e
            java.lang.Object r0 = r1.result
            return r0
        L_0x003e:
            boolean r1 = r0 instanceof com.leedarson.serviceimpl.ctrl.k.c
            if (r1 == 0) goto L_0x0054
            r1 = r0
            com.leedarson.serviceimpl.ctrl.k$c r1 = (com.leedarson.serviceimpl.ctrl.k.c) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0054
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r23
            goto L_0x005c
        L_0x0054:
            com.leedarson.serviceimpl.ctrl.k$c r1 = new com.leedarson.serviceimpl.ctrl.k$c
            r2 = r23
            r1.<init>(r2, r0)
            r0 = r1
        L_0x005c:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            r5 = 0
            switch(r4) {
                case 0: goto L_0x008e;
                case 1: goto L_0x0072;
                default: goto L_0x0068;
            }
        L_0x0068:
            r19 = r1
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0072:
            r3 = r5
            r4 = 0
            float r4 = r0.F$1
            float r5 = r0.F$0
            long r6 = r0.J$0
            java.lang.Object r8 = r0.L$2
            r3 = r8
            java.lang.String r3 = (java.lang.String) r3
            java.lang.Object r8 = r0.L$1
            chip.devicecontroller.ChipClusters$DefaultClusterCallback r8 = (chip.devicecontroller.ChipClusters.DefaultClusterCallback) r8
            java.lang.Object r9 = r0.L$0
            com.leedarson.serviceimpl.ctrl.k r9 = (com.leedarson.serviceimpl.ctrl.k) r9
            kotlin.p.b(r1)
            r19 = r1
            goto L_0x01b1
        L_0x008e:
            kotlin.p.b(r1)
            r4 = r23
            r8 = r27
            r6 = r24
            r9 = r26
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            float[] r14 = r9.getHSL()
            r14 = r14[r11]
            r10.append(r14)
            r14 = 44
            r10.append(r14)
            float[] r15 = r9.getHSL()
            r15 = r15[r12]
            r10.append(r15)
            r10.append(r14)
            float[] r14 = r9.getHSL()
            r14 = r14[r13]
            r10.append(r14)
            java.lang.String r10 = r10.toString()
            java.lang.String r10 = r4.c(r10)
            java.lang.String r20 = ","
            java.lang.String[] r15 = new java.lang.String[]{r20}
            r16 = 0
            r17 = 0
            r18 = 6
            r19 = 0
            r14 = r10
            java.util.List r14 = kotlin.text.x.F0(r14, r15, r16, r17, r18, r19)
            java.lang.Object r14 = r14.get(r11)
            java.lang.String r14 = (java.lang.String) r14
            float r14 = java.lang.Float.parseFloat(r14)
            r15 = 100
            float r15 = (float) r15
            float r14 = r14 * r15
            r5 = 360(0x168, float:5.04E-43)
            float r5 = (float) r5
            float r14 = r14 / r5
            r5 = 254(0xfe, float:3.56E-43)
            float r5 = (float) r5
            float r14 = r14 * r5
            java.lang.String[] r16 = new java.lang.String[]{r20}
            r18 = 0
            r19 = 6
            r20 = 0
            r21 = r14
            r14 = r10
            r22 = r15
            r15 = r16
            r16 = r17
            r17 = r18
            r18 = r19
            r19 = r20
            java.util.List r14 = kotlin.text.x.F0(r14, r15, r16, r17, r18, r19)
            java.lang.Object r14 = r14.get(r12)
            java.lang.String r14 = (java.lang.String) r14
            float r14 = java.lang.Float.parseFloat(r14)
            float r14 = r14 * r22
            float r14 = r14 / r22
            float r5 = r5 * r14
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r15 = "Matter 设备("
            r14.append(r15)
            r14.append(r6)
            java.lang.String r15 = ")控制 HSL, 原参数 h:"
            r14.append(r15)
            float[] r15 = r9.getHSL()
            r15 = r15[r11]
            r14.append(r15)
            java.lang.String r15 = ",s:"
            r14.append(r15)
            float[] r16 = r9.getHSL()
            r11 = r16[r12]
            r14.append(r11)
            java.lang.String r11 = ",l:"
            r14.append(r11)
            float[] r11 = r9.getHSL()
            r11 = r11[r13]
            r14.append(r11)
            java.lang.String r11 = ",转换后 h:"
            r14.append(r11)
            r11 = r21
            int r12 = (int) r11
            r14.append(r12)
            r14.append(r15)
            int r12 = (int) r5
            r14.append(r12)
            java.lang.String r12 = r14.toString()
            com.leedarson.serviceimpl.k r14 = com.leedarson.serviceimpl.k.a
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r19 = r1
            java.lang.String r1 = "HSL Command ,h:"
            r13.append(r1)
            r13.append(r11)
            r13.append(r15)
            r13.append(r5)
            java.lang.String r1 = ",addr:"
            r13.append(r1)
            r13.append(r6)
            java.lang.String r1 = r13.toString()
            r13 = 0
            r15 = 2
            com.leedarson.serviceimpl.k.b(r14, r1, r13, r15, r13)
            com.leedarson.serviceimpl.manager.c r1 = r4.h
            int r13 = r4.g
            r0.L$0 = r4
            r0.L$1 = r8
            r0.L$2 = r12
            r0.J$0 = r6
            r0.F$0 = r11
            r0.F$1 = r5
            r14 = 1
            r0.label = r14
            java.lang.Object r1 = r1.c(r6, r13, r0)
            if (r1 != r3) goto L_0x01ad
            return r3
        L_0x01ad:
            r9 = r4
            r4 = r5
            r5 = r11
            r3 = r12
        L_0x01b1:
            chip.devicecontroller.ChipClusters$ColorControlCluster r1 = (chip.devicecontroller.ChipClusters.ColorControlCluster) r1
            boolean r6 = r9.u(r6, r1, r8)
            if (r6 != 0) goto L_0x01d0
            com.leedarson.serviceimpl.k r9 = com.leedarson.serviceimpl.k.a
            java.lang.String r5 = ",result:error :Unable to get connected device"
            java.lang.String r10 = kotlin.jvm.internal.k.l(r3, r5)
            r13 = 0
            r14 = 8
            r15 = 0
            java.lang.String r11 = "info"
            java.lang.String r12 = "controlDevice"
            com.leedarson.serviceimpl.k.k(r9, r10, r11, r12, r13, r14, r15)
            kotlin.x r5 = kotlin.x.a
            return r5
        L_0x01d0:
            if (r1 != 0) goto L_0x01d3
            goto L_0x01f3
        L_0x01d3:
            com.leedarson.serviceimpl.ctrl.k$d r11 = new com.leedarson.serviceimpl.ctrl.k$d
            r11.<init>(r3, r8)
            int r5 = (int) r5
            java.lang.Integer r12 = kotlin.coroutines.jvm.internal.b.c(r5)
            int r5 = (int) r4
            java.lang.Integer r13 = kotlin.coroutines.jvm.internal.b.c(r5)
            r5 = 0
            java.lang.Integer r14 = kotlin.coroutines.jvm.internal.b.c(r5)
            java.lang.Integer r15 = kotlin.coroutines.jvm.internal.b.c(r5)
            java.lang.Integer r16 = kotlin.coroutines.jvm.internal.b.c(r5)
            r10 = r1
            r10.moveToHueAndSaturation(r11, r12, r13, r14, r15, r16)
        L_0x01f3:
            kotlin.x r5 = kotlin.x.a
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.k.b(long, com.leedarson.serviceimpl.bean.ControlDeviceBean, chip.devicecontroller.ChipClusters$DefaultClusterCallback, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: MTCmdCtrl.kt */
    public static final class d implements ChipClusters.DefaultClusterCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ ChipClusters.DefaultClusterCallback b;

        d(String $elkMsg, ChipClusters.DefaultClusterCallback $callback) {
            this.a = $elkMsg;
            this.b = $callback;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7608, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k.k(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l(this.a, ",result:success"), "info", "controlDevice", (String) null, 8, (Object) null);
                ChipClusters.DefaultClusterCallback defaultClusterCallback = this.b;
                if (defaultClusterCallback != null) {
                    defaultClusterCallback.onSuccess();
                }
            }
        }

        public void onError(@NotNull Exception ex) {
            if (!PatchProxy.proxy(new Object[]{ex}, this, changeQuickRedirect, false, 7609, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                kotlin.jvm.internal.k.e(ex, "ex");
                com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                com.leedarson.serviceimpl.k.e(kVar, kotlin.jvm.internal.k.l("MoveToLevel command failure ", ex), (String) null, 2, (Object) null);
                com.leedarson.serviceimpl.k.k(kVar, this.a + ",result:failure " + ex, "info", "controlDevice", (String) null, 8, (Object) null);
                ChipClusters.DefaultClusterCallback defaultClusterCallback = this.b;
                if (defaultClusterCallback != null) {
                    defaultClusterCallback.onError(ex);
                }
            }
        }
    }

    @NotNull
    public final String c(@NotNull String hslStr) {
        float v;
        boolean z = true;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{hslStr}, this, changeQuickRedirect, false, 7589, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        kotlin.jvm.internal.k.e(hslStr, "hslStr");
        float f2 = (float) 100;
        float h2 = Float.parseFloat((String) x.F0(hslStr, new String[]{","}, false, 0, 6, (Object) null).get(0)) / f2;
        float s = Float.parseFloat((String) x.F0(hslStr, new String[]{","}, false, 0, 6, (Object) null).get(1)) / f2;
        float l2 = Float.parseFloat((String) x.F0(hslStr, new String[]{","}, false, 0, 6, (Object) null).get(2)) / f2;
        float f3 = 0.0f;
        if (s == 0.0f) {
            v = l2;
        } else if (((double) l2) > 0.5d) {
            float f4 = (float) 1;
            float v2 = l2 + ((f4 - l2) * s);
            if (v2 != 0.0f) {
                z = false;
            }
            if (!z) {
                f3 = ((((float) 2) * s) * (f4 - l2)) / v2;
            }
            s = f3;
            v = v2;
        } else {
            float f5 = (float) 1;
            float v3 = l2 * (s + f5);
            if (v3 != 0.0f) {
                z = false;
            }
            if (!z) {
                f3 = (((float) 2) * s) / (f5 + s);
            }
            s = f3;
            v = v3;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(h2);
        sb.append(StringUtil.COMMA);
        sb.append(s);
        sb.append(StringUtil.COMMA);
        sb.append(v);
        String hsvStr = sb.toString();
        timber.log.a.i("HSLToHSV: " + hslStr + "   ===> " + hsvStr, new Object[0]);
        return hsvStr;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v2, resolved type: chip.devicecontroller.ChipClusters$BasicCluster$VendorIDAttributeCallback} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00bd  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object t(long r11, @org.jetbrains.annotations.NotNull chip.devicecontroller.ChipClusters.BasicCluster.VendorIDAttributeCallback r13, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r14) {
        /*
            r10 = this;
            r0 = 3
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Long r2 = new java.lang.Long
            r2.<init>(r11)
            r3 = 0
            r1[r3] = r2
            r8 = 1
            r1[r8] = r13
            r9 = 2
            r1[r9] = r14
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Long.TYPE
            r6[r3] = r0
            java.lang.Class<chip.devicecontroller.ChipClusters$BasicCluster$VendorIDAttributeCallback> r0 = chip.devicecontroller.ChipClusters.BasicCluster.VendorIDAttributeCallback.class
            r6[r8] = r0
            java.lang.Class<kotlin.coroutines.d> r0 = kotlin.coroutines.d.class
            r6[r9] = r0
            java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
            r0 = 0
            r5 = 7590(0x1da6, float:1.0636E-41)
            r2 = r10
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0034
            java.lang.Object r11 = r0.result
            return r11
        L_0x0034:
            boolean r0 = r14 instanceof com.leedarson.serviceimpl.ctrl.k.q
            if (r0 == 0) goto L_0x0047
            r0 = r14
            com.leedarson.serviceimpl.ctrl.k$q r0 = (com.leedarson.serviceimpl.ctrl.k.q) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0047
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x004c
        L_0x0047:
            com.leedarson.serviceimpl.ctrl.k$q r0 = new com.leedarson.serviceimpl.ctrl.k$q
            r0.<init>(r10, r14)
        L_0x004c:
            r14 = r0
            java.lang.Object r0 = r14.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r14.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x006d;
                case 1: goto L_0x0061;
                default: goto L_0x0059;
            }
        L_0x0059:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L_0x0061:
            long r11 = r14.J$0
            java.lang.Object r1 = r14.L$0
            r13 = r1
            chip.devicecontroller.ChipClusters$BasicCluster$VendorIDAttributeCallback r13 = (chip.devicecontroller.ChipClusters.BasicCluster.VendorIDAttributeCallback) r13
            kotlin.p.b(r0)
            r2 = r0
            goto L_0x0091
        L_0x006d:
            kotlin.p.b(r0)
            r2 = r10
            com.leedarson.serviceimpl.k r4 = com.leedarson.serviceimpl.k.a
            java.lang.Long r5 = kotlin.coroutines.jvm.internal.b.d(r11)
            java.lang.String r6 = "sendReadVendorIDAttribute addr:"
            java.lang.String r5 = kotlin.jvm.internal.k.l(r6, r5)
            com.leedarson.serviceimpl.k.m(r4, r5, r3, r9, r3)
            com.leedarson.serviceimpl.manager.c r4 = r2.h
            int r5 = r2.d
            r14.L$0 = r13
            r14.J$0 = r11
            r14.label = r8
            java.lang.Object r2 = r4.b(r11, r5, r14)
            if (r2 != r1) goto L_0x0091
            return r1
        L_0x0091:
            r1 = r2
            chip.devicecontroller.ChipClusters$BasicCluster r1 = (chip.devicecontroller.ChipClusters.BasicCluster) r1
            if (r1 != 0) goto L_0x00bd
            com.leedarson.serviceimpl.k r2 = com.leedarson.serviceimpl.k.a
            java.lang.Long r4 = kotlin.coroutines.jvm.internal.b.d(r11)
            java.lang.String r5 = "on fail:could not find cluster by deviceId:"
            java.lang.String r4 = kotlin.jvm.internal.k.l(r5, r4)
            com.leedarson.serviceimpl.k.e(r2, r4, r3, r9, r3)
            if (r13 != 0) goto L_0x00a8
            goto L_0x00ba
        L_0x00a8:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.Long r3 = kotlin.coroutines.jvm.internal.b.d(r11)
            java.lang.String r4 = "could not find cluster by deviceId:"
            java.lang.String r3 = kotlin.jvm.internal.k.l(r4, r3)
            r2.<init>(r3)
            r13.onError(r2)
        L_0x00ba:
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x00bd:
            com.leedarson.serviceimpl.ctrl.k$r r2 = new com.leedarson.serviceimpl.ctrl.k$r
            r2.<init>(r13)
            r1.readVendorIDAttribute(r2)
            kotlin.x r2 = kotlin.x.a
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.k.t(long, chip.devicecontroller.ChipClusters$BasicCluster$VendorIDAttributeCallback, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: MTCmdCtrl.kt */
    public static final class r implements ChipClusters.BasicCluster.VendorIDAttributeCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ChipClusters.BasicCluster.VendorIDAttributeCallback a;

        public /* synthetic */ void onSubscriptionEstablished() {
            co1.a(this);
        }

        r(ChipClusters.BasicCluster.VendorIDAttributeCallback $callback) {
            this.a = $callback;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Integer value) {
            if (!PatchProxy.proxy(new Object[]{value}, this, changeQuickRedirect, false, 7656, new Class[]{Integer.class}, Void.TYPE).isSupported) {
                a(value.intValue());
            }
        }

        public void a(int value) {
            Object[] objArr = {new Integer(value)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7654, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k.h(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("[Read Success] VendorID: ", Integer.valueOf(value)), (String) null, 2, (Object) null);
                this.a.onSuccess(Integer.valueOf(value));
            }
        }

        public void onError(@NotNull Exception ex) {
            if (!PatchProxy.proxy(new Object[]{ex}, this, changeQuickRedirect, false, 7655, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                kotlin.jvm.internal.k.e(ex, "ex");
                com.leedarson.serviceimpl.k.h(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("Read VendorID failure ", ex), (String) null, 2, (Object) null);
                this.a.onError(ex);
            }
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTCmdCtrl$controlDevice$job$1", f = "MTCmdCtrl.kt", l = {345, 347, 353, 361, 369, 377}, m = "invokeSuspend")
    /* compiled from: MTCmdCtrl.kt */
    public static final class h extends kotlin.coroutines.jvm.internal.l implements kotlin.jvm.functions.p<o0, kotlin.coroutines.d<? super kotlin.x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ControlDeviceBean $controlDeviceBean;
        final /* synthetic */ com.leedarson.serviceimpl.listener.b $ctrlCallback;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(ControlDeviceBean controlDeviceBean, com.leedarson.serviceimpl.listener.b bVar, k kVar, kotlin.coroutines.d<? super h> dVar) {
            super(2, dVar);
            this.$controlDeviceBean = controlDeviceBean;
            this.$ctrlCallback = bVar;
            this.this$0 = kVar;
        }

        @NotNull
        public final kotlin.coroutines.d<kotlin.x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 7618, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
            if (proxy.isSupported) {
                return (kotlin.coroutines.d) proxy.result;
            }
            return new h(this.$controlDeviceBean, this.$ctrlCallback, this.this$0, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 7620, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super kotlin.x>) (kotlin.coroutines.d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super kotlin.x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 7619, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((h) create(o0Var, dVar)).invokeSuspend(kotlin.x.a);
        }

        /* compiled from: MTCmdCtrl.kt */
        public static final class a implements ChipClusters.DefaultClusterCallback {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ com.leedarson.serviceimpl.listener.b a;
            final /* synthetic */ ControlDeviceBean b;

            a(com.leedarson.serviceimpl.listener.b $ctrlCallback, ControlDeviceBean $controlDeviceBean) {
                this.a = $ctrlCallback;
                this.b = $controlDeviceBean;
            }

            /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
                r0 = r8;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onSuccess() {
                /*
                    r8 = this;
                    r0 = 0
                    java.lang.Object[] r1 = new java.lang.Object[r0]
                    com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
                    java.lang.Class[] r6 = new java.lang.Class[r0]
                    java.lang.Class r7 = java.lang.Void.TYPE
                    r4 = 0
                    r5 = 7621(0x1dc5, float:1.0679E-41)
                    r2 = r8
                    com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
                    boolean r0 = r0.isSupported
                    if (r0 == 0) goto L_0x0016
                    return
                L_0x0016:
                    r0 = r8
                    com.leedarson.serviceimpl.listener.b r1 = r0.a
                    if (r1 != 0) goto L_0x001c
                    goto L_0x0027
                L_0x001c:
                    com.leedarson.serviceimpl.bean.ControlDeviceBean r2 = r0.b
                    long r2 = r2.getMatterAddr()
                    java.lang.String r4 = ""
                    r1.onSuccess(r2, r4)
                L_0x0027:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.k.h.a.onSuccess():void");
            }

            public void onError(@Nullable Exception error) {
                com.leedarson.serviceimpl.listener.b bVar;
                if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 7622, new Class[]{Exception.class}, Void.TYPE).isSupported && (bVar = this.a) != null) {
                    bVar.onFail(-1, error);
                }
            }
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v12, resolved type: com.leedarson.serviceimpl.bean.ControlDeviceBean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v6, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v10, resolved type: com.leedarson.serviceimpl.ctrl.k$h$a} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v10, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v15, resolved type: com.leedarson.serviceimpl.bean.ControlDeviceBean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v7, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v13, resolved type: com.leedarson.serviceimpl.ctrl.k$h$a} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v11, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v16, resolved type: com.leedarson.serviceimpl.bean.ControlDeviceBean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v25, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v15, resolved type: com.leedarson.serviceimpl.ctrl.k$h$a} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v14, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v19, resolved type: com.leedarson.serviceimpl.bean.ControlDeviceBean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v26, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v18, resolved type: com.leedarson.serviceimpl.ctrl.k$h$a} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v17, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v22, resolved type: com.leedarson.serviceimpl.bean.ControlDeviceBean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v27, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v21, resolved type: com.leedarson.serviceimpl.ctrl.k$h$a} */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0145, code lost:
            if (r6.hasDimming() == false) goto L_0x0175;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0147, code lost:
            r13 = r9.getMatterAddr();
            r15 = r6.getDimming() + 1;
            r3.L$0 = r5;
            r3.L$1 = r11;
            r3.L$2 = r10;
            r3.L$3 = r9;
            r3.L$4 = r6;
            r3.label = 3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0169, code lost:
            if (com.leedarson.serviceimpl.ctrl.k.j(r10, r13, r15, r5, r3) != r1) goto L_0x016c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x016b, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x016c, code lost:
            r0 = r3;
            r3 = r5;
            r5 = r6;
            r6 = r7;
            r7 = r8;
            r8 = r9;
            r9 = r10;
            r10 = r11;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0175, code lost:
            r0 = r3;
            r3 = r5;
            r5 = r6;
            r6 = r7;
            r7 = r8;
            r8 = r9;
            r9 = r10;
            r10 = r11;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x0181, code lost:
            if (r5.hasCCT() == false) goto L_0x01a5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x0183, code lost:
            r12 = r8.getMatterAddr();
            r14 = r8.getCCT();
            r0.L$0 = r3;
            r0.L$1 = r10;
            r0.L$2 = r9;
            r0.L$3 = r8;
            r0.L$4 = r5;
            r0.label = 4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x01a2, code lost:
            if (com.leedarson.serviceimpl.ctrl.k.e(r9, r12, r14, r3, r0) != r1) goto L_0x01a5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x01a4, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x01a9, code lost:
            if (r5.hasRGBW() == false) goto L_0x01ce;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x01ab, code lost:
            r12 = r8.getMatterAddr();
            r0.L$0 = r3;
            r0.L$1 = r10;
            r0.L$2 = r9;
            r0.L$3 = r8;
            r0.L$4 = r5;
            r0.label = 5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x01c8, code lost:
            if (com.leedarson.serviceimpl.ctrl.k.g(r9, r12, r8, r3, r0) != r1) goto L_0x01cb;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x01ca, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x01cb, code lost:
            r14 = r8;
            r11 = r9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x01ce, code lost:
            r14 = r8;
            r11 = r9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x01d4, code lost:
            if (r5.hasHSL() == false) goto L_0x01f7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:0x01d6, code lost:
            r12 = r14.getMatterAddr();
            r0.L$0 = r10;
            r0.L$1 = null;
            r0.L$2 = null;
            r0.L$3 = null;
            r0.L$4 = null;
            r0.label = 6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x01f1, code lost:
            if (com.leedarson.serviceimpl.ctrl.k.f(r11, r12, r14, r3, r0) != r1) goto L_0x01f4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x01f3, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x01f4, code lost:
            r1 = r6;
            r3 = r7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x01f6, code lost:
            r6 = r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x01fa, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Multi-variable type inference failed */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r19) {
            /*
                r18 = this;
                r0 = 1
                java.lang.Object[] r1 = new java.lang.Object[r0]
                r2 = 0
                r1[r2] = r19
                com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
                java.lang.Class[] r6 = new java.lang.Class[r0]
                java.lang.Class<java.lang.Object> r4 = java.lang.Object.class
                r6[r2] = r4
                java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
                r4 = 0
                r5 = 7617(0x1dc1, float:1.0674E-41)
                r2 = r18
                com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
                boolean r2 = r1.isSupported
                if (r2 == 0) goto L_0x0020
                java.lang.Object r0 = r1.result
                return r0
            L_0x0020:
                java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
                r2 = r18
                int r3 = r2.label
                r4 = 0
                switch(r3) {
                    case 0: goto L_0x00eb;
                    case 1: goto L_0x00ca;
                    case 2: goto L_0x00a8;
                    case 3: goto L_0x0086;
                    case 4: goto L_0x0064;
                    case 5: goto L_0x0042;
                    case 6: goto L_0x0034;
                    default: goto L_0x002c;
                }
            L_0x002c:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0034:
                r0 = r18
                r1 = r19
                r3 = 0
                java.lang.Object r4 = r0.L$0
                com.leedarson.serviceimpl.bean.ControlDeviceBean r4 = (com.leedarson.serviceimpl.bean.ControlDeviceBean) r4
                kotlin.p.b(r1)
                goto L_0x01f6
            L_0x0042:
                r0 = r18
                r3 = r4
                r5 = r4
                r6 = r19
                r7 = 0
                java.lang.Object r8 = r0.L$4
                r5 = r8
                com.leedarson.serviceimpl.bean.ControlDeviceBean r5 = (com.leedarson.serviceimpl.bean.ControlDeviceBean) r5
                java.lang.Object r8 = r0.L$3
                com.leedarson.serviceimpl.bean.ControlDeviceBean r8 = (com.leedarson.serviceimpl.bean.ControlDeviceBean) r8
                java.lang.Object r9 = r0.L$2
                com.leedarson.serviceimpl.ctrl.k r9 = (com.leedarson.serviceimpl.ctrl.k) r9
                java.lang.Object r10 = r0.L$1
                com.leedarson.serviceimpl.bean.ControlDeviceBean r10 = (com.leedarson.serviceimpl.bean.ControlDeviceBean) r10
                java.lang.Object r11 = r0.L$0
                r3 = r11
                com.leedarson.serviceimpl.ctrl.k$h$a r3 = (com.leedarson.serviceimpl.ctrl.k.h.a) r3
                kotlin.p.b(r6)
                goto L_0x01cb
            L_0x0064:
                r0 = r18
                r3 = r4
                r5 = r4
                r6 = r19
                r7 = 0
                java.lang.Object r8 = r0.L$4
                r5 = r8
                com.leedarson.serviceimpl.bean.ControlDeviceBean r5 = (com.leedarson.serviceimpl.bean.ControlDeviceBean) r5
                java.lang.Object r8 = r0.L$3
                com.leedarson.serviceimpl.bean.ControlDeviceBean r8 = (com.leedarson.serviceimpl.bean.ControlDeviceBean) r8
                java.lang.Object r9 = r0.L$2
                com.leedarson.serviceimpl.ctrl.k r9 = (com.leedarson.serviceimpl.ctrl.k) r9
                java.lang.Object r10 = r0.L$1
                com.leedarson.serviceimpl.bean.ControlDeviceBean r10 = (com.leedarson.serviceimpl.bean.ControlDeviceBean) r10
                java.lang.Object r11 = r0.L$0
                r3 = r11
                com.leedarson.serviceimpl.ctrl.k$h$a r3 = (com.leedarson.serviceimpl.ctrl.k.h.a) r3
                kotlin.p.b(r6)
                goto L_0x01a5
            L_0x0086:
                r0 = r18
                r3 = r4
                r5 = r4
                r6 = r19
                r7 = 0
                java.lang.Object r8 = r0.L$4
                r5 = r8
                com.leedarson.serviceimpl.bean.ControlDeviceBean r5 = (com.leedarson.serviceimpl.bean.ControlDeviceBean) r5
                java.lang.Object r8 = r0.L$3
                com.leedarson.serviceimpl.bean.ControlDeviceBean r8 = (com.leedarson.serviceimpl.bean.ControlDeviceBean) r8
                java.lang.Object r9 = r0.L$2
                com.leedarson.serviceimpl.ctrl.k r9 = (com.leedarson.serviceimpl.ctrl.k) r9
                java.lang.Object r10 = r0.L$1
                com.leedarson.serviceimpl.bean.ControlDeviceBean r10 = (com.leedarson.serviceimpl.bean.ControlDeviceBean) r10
                java.lang.Object r11 = r0.L$0
                r3 = r11
                com.leedarson.serviceimpl.ctrl.k$h$a r3 = (com.leedarson.serviceimpl.ctrl.k.h.a) r3
                kotlin.p.b(r6)
                goto L_0x0174
            L_0x00a8:
                r3 = r18
                r5 = r4
                r6 = r4
                r7 = r19
                r8 = 0
                java.lang.Object r9 = r3.L$4
                r6 = r9
                com.leedarson.serviceimpl.bean.ControlDeviceBean r6 = (com.leedarson.serviceimpl.bean.ControlDeviceBean) r6
                java.lang.Object r9 = r3.L$3
                com.leedarson.serviceimpl.bean.ControlDeviceBean r9 = (com.leedarson.serviceimpl.bean.ControlDeviceBean) r9
                java.lang.Object r10 = r3.L$2
                com.leedarson.serviceimpl.ctrl.k r10 = (com.leedarson.serviceimpl.ctrl.k) r10
                java.lang.Object r11 = r3.L$1
                com.leedarson.serviceimpl.bean.ControlDeviceBean r11 = (com.leedarson.serviceimpl.bean.ControlDeviceBean) r11
                java.lang.Object r12 = r3.L$0
                r5 = r12
                com.leedarson.serviceimpl.ctrl.k$h$a r5 = (com.leedarson.serviceimpl.ctrl.k.h.a) r5
                kotlin.p.b(r7)
                goto L_0x013f
            L_0x00ca:
                r3 = r18
                r5 = r4
                r6 = r4
                r7 = r19
                r8 = 0
                java.lang.Object r9 = r3.L$4
                r6 = r9
                com.leedarson.serviceimpl.bean.ControlDeviceBean r6 = (com.leedarson.serviceimpl.bean.ControlDeviceBean) r6
                java.lang.Object r9 = r3.L$3
                com.leedarson.serviceimpl.bean.ControlDeviceBean r9 = (com.leedarson.serviceimpl.bean.ControlDeviceBean) r9
                java.lang.Object r10 = r3.L$2
                com.leedarson.serviceimpl.ctrl.k r10 = (com.leedarson.serviceimpl.ctrl.k) r10
                java.lang.Object r11 = r3.L$1
                com.leedarson.serviceimpl.bean.ControlDeviceBean r11 = (com.leedarson.serviceimpl.bean.ControlDeviceBean) r11
                java.lang.Object r12 = r3.L$0
                r5 = r12
                com.leedarson.serviceimpl.ctrl.k$h$a r5 = (com.leedarson.serviceimpl.ctrl.k.h.a) r5
                kotlin.p.b(r7)
                goto L_0x0125
            L_0x00eb:
                kotlin.p.b(r19)
                r3 = r18
                r7 = r19
                com.leedarson.serviceimpl.ctrl.k$h$a r5 = new com.leedarson.serviceimpl.ctrl.k$h$a
                com.leedarson.serviceimpl.listener.b r6 = r3.$ctrlCallback
                com.leedarson.serviceimpl.bean.ControlDeviceBean r8 = r3.$controlDeviceBean
                r5.<init>(r6, r8)
                com.leedarson.serviceimpl.bean.ControlDeviceBean r9 = r3.$controlDeviceBean
                com.leedarson.serviceimpl.ctrl.k r10 = r3.this$0
                r6 = r9
                r8 = 0
                boolean r11 = r6.hasOnOff()
                if (r11 == 0) goto L_0x0140
                int r11 = r6.getOnOff()
                if (r11 != 0) goto L_0x0126
                long r11 = r9.getMatterAddr()
                r3.L$0 = r5
                r3.L$1 = r9
                r3.L$2 = r10
                r3.L$3 = r9
                r3.L$4 = r6
                r3.label = r0
                java.lang.Object r11 = r10.p(r11, r5, r3)
                if (r11 != r1) goto L_0x0124
                return r1
            L_0x0124:
                r11 = r9
            L_0x0125:
                goto L_0x0141
            L_0x0126:
                long r11 = r9.getMatterAddr()
                r3.L$0 = r5
                r3.L$1 = r9
                r3.L$2 = r10
                r3.L$3 = r9
                r3.L$4 = r6
                r13 = 2
                r3.label = r13
                java.lang.Object r11 = r10.q(r11, r5, r3)
                if (r11 != r1) goto L_0x013e
                return r1
            L_0x013e:
                r11 = r9
            L_0x013f:
                goto L_0x0141
            L_0x0140:
                r11 = r9
            L_0x0141:
                boolean r12 = r6.hasDimming()
                if (r12 == 0) goto L_0x0175
                long r13 = r9.getMatterAddr()
                int r12 = r6.getDimming()
                int r15 = r12 + 1
                r3.L$0 = r5
                r3.L$1 = r11
                r3.L$2 = r10
                r3.L$3 = r9
                r3.L$4 = r6
                r0 = 3
                r3.label = r0
                r12 = r10
                r16 = r5
                r17 = r3
                java.lang.Object r0 = com.leedarson.serviceimpl.ctrl.k.j(r12, r13, r15, r16, r17)
                if (r0 != r1) goto L_0x016c
                return r1
            L_0x016c:
                r0 = r3
                r3 = r5
                r5 = r6
                r6 = r7
                r7 = r8
                r8 = r9
                r9 = r10
                r10 = r11
            L_0x0174:
                goto L_0x017d
            L_0x0175:
                r0 = r3
                r3 = r5
                r5 = r6
                r6 = r7
                r7 = r8
                r8 = r9
                r9 = r10
                r10 = r11
            L_0x017d:
                boolean r11 = r5.hasCCT()
                if (r11 == 0) goto L_0x01a5
                long r12 = r8.getMatterAddr()
                int r14 = r8.getCCT()
                r0.L$0 = r3
                r0.L$1 = r10
                r0.L$2 = r9
                r0.L$3 = r8
                r0.L$4 = r5
                r11 = 4
                r0.label = r11
                r11 = r9
                r15 = r3
                r16 = r0
                java.lang.Object r11 = com.leedarson.serviceimpl.ctrl.k.e(r11, r12, r14, r15, r16)
                if (r11 != r1) goto L_0x01a5
                return r1
            L_0x01a5:
                boolean r11 = r5.hasRGBW()
                if (r11 == 0) goto L_0x01ce
                long r12 = r8.getMatterAddr()
                r0.L$0 = r3
                r0.L$1 = r10
                r0.L$2 = r9
                r0.L$3 = r8
                r0.L$4 = r5
                r11 = 5
                r0.label = r11
                r11 = r9
                r14 = r8
                r15 = r3
                r16 = r0
                java.lang.Object r11 = com.leedarson.serviceimpl.ctrl.k.g(r11, r12, r14, r15, r16)
                if (r11 != r1) goto L_0x01cb
                return r1
            L_0x01cb:
                r14 = r8
                r11 = r9
                goto L_0x01d0
            L_0x01ce:
                r14 = r8
                r11 = r9
            L_0x01d0:
                boolean r8 = r5.hasHSL()
                if (r8 == 0) goto L_0x01f7
                long r12 = r14.getMatterAddr()
                r0.L$0 = r10
                r0.L$1 = r4
                r0.L$2 = r4
                r0.L$3 = r4
                r0.L$4 = r4
                r4 = 6
                r0.label = r4
                r15 = r3
                r16 = r0
                java.lang.Object r3 = com.leedarson.serviceimpl.ctrl.k.f(r11, r12, r14, r15, r16)
                if (r3 != r1) goto L_0x01f4
                return r1
            L_0x01f4:
                r1 = r6
                r3 = r7
            L_0x01f6:
                r6 = r1
            L_0x01f7:
                kotlin.x r1 = kotlin.x.a
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.k.h.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public final void n(@NotNull ControlDeviceBean controlDeviceBean, @NotNull com.leedarson.serviceimpl.listener.b ctrlCallback) {
        Class[] clsArr = {ControlDeviceBean.class, com.leedarson.serviceimpl.listener.b.class};
        if (!PatchProxy.proxy(new Object[]{controlDeviceBean, ctrlCallback}, this, changeQuickRedirect, false, 7593, clsArr, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(controlDeviceBean, "controlDeviceBean");
            kotlin.jvm.internal.k.e(ctrlCallback, "ctrlCallback");
            kotlinx.coroutines.j.d(s1.c, (kotlin.coroutines.g) null, (q0) null, new h(controlDeviceBean, ctrlCallback, this, (kotlin.coroutines.d<? super h>) null), 3, (Object) null).start();
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTCmdCtrl$checkMatterOnlineTask$job$1", f = "MTCmdCtrl.kt", l = {413}, m = "invokeSuspend")
    /* compiled from: MTCmdCtrl.kt */
    public static final class g extends kotlin.coroutines.jvm.internal.l implements kotlin.jvm.functions.p<o0, kotlin.coroutines.d<? super kotlin.x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.serviceimpl.listener.b $callback;
        final /* synthetic */ long $deviceId;
        int label;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(long j, k kVar, com.leedarson.serviceimpl.listener.b bVar, kotlin.coroutines.d<? super g> dVar) {
            super(2, dVar);
            this.$deviceId = j;
            this.this$0 = kVar;
            this.$callback = bVar;
        }

        @NotNull
        public final kotlin.coroutines.d<kotlin.x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 7614, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
            if (proxy.isSupported) {
                return (kotlin.coroutines.d) proxy.result;
            }
            return new g(this.$deviceId, this.this$0, this.$callback, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 7616, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super kotlin.x>) (kotlin.coroutines.d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super kotlin.x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 7615, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((g) create(o0Var, dVar)).invokeSuspend(kotlin.x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            g gVar;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 7613, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    gVar = this;
                    com.leedarson.serviceimpl.k.m(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("MTCmdCtrl.checkMatterOnlineTask addr:", kotlin.coroutines.jvm.internal.b.d(gVar.$deviceId)), (String) null, 2, (Object) null);
                    com.leedarson.serviceimpl.manager.c i = gVar.this$0.h;
                    long j = gVar.$deviceId;
                    int h = gVar.this$0.d;
                    gVar.label = 1;
                    Object b = i.b(j, h, gVar);
                    if (b != d) {
                        Object obj = b;
                        Object obj2 = $result;
                        $result = obj;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    gVar = this;
                    Object obj3 = $result;
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            if (((ChipClusters.BasicCluster) $result) == null) {
                com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("MTCmdCtrl.checkMatterOnlineTask on fail:could not find cluster by deviceId:", kotlin.coroutines.jvm.internal.b.d(gVar.$deviceId)), (String) null, 2, (Object) null);
                gVar.$callback.onFail(-1, new IllegalArgumentException(kotlin.jvm.internal.k.l("MTCmdCtrl.checkMatterOnlineTask could not find cluster by deviceId:", kotlin.coroutines.jvm.internal.b.d(gVar.$deviceId))));
                return kotlin.x.a;
            }
            com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
            com.leedarson.serviceimpl.k.b(kVar, "MTCmdCtrl.checkMatterOnlineTask 读取节点属性值:onSuccess  deviceId=" + gVar.$deviceId + ' ', (String) null, 2, (Object) null);
            gVar.$callback.onSuccess(gVar.$deviceId, kotlin.coroutines.jvm.internal.b.c(0));
            return kotlin.x.a;
        }
    }

    @NotNull
    public final z1 k(long j2, @NotNull com.leedarson.serviceimpl.listener.b bVar) {
        Object[] objArr = {new Long(j2), bVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 7595, new Class[]{Long.TYPE, com.leedarson.serviceimpl.listener.b.class}, z1.class);
        if (proxy.isSupported) {
            return (z1) proxy.result;
        }
        long deviceId = j2;
        com.leedarson.serviceimpl.listener.b callback = bVar;
        kotlin.jvm.internal.k.e(callback, "callback");
        z1 job = kotlinx.coroutines.j.d(s1.c, (kotlin.coroutines.g) null, (q0) null, new g(deviceId, this, callback, (kotlin.coroutines.d<? super g>) null), 3, (Object) null);
        job.start();
        return job;
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTCmdCtrl$removeDevice$job$1", f = "MTCmdCtrl.kt", l = {455}, m = "invokeSuspend")
    /* compiled from: MTCmdCtrl.kt */
    public static final class n extends kotlin.coroutines.jvm.internal.l implements kotlin.jvm.functions.p<o0, kotlin.coroutines.d<? super kotlin.x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.serviceimpl.listener.b $callback;
        final /* synthetic */ long $nodeId;
        int label;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        n(k kVar, long j, com.leedarson.serviceimpl.listener.b bVar, kotlin.coroutines.d<? super n> dVar) {
            super(2, dVar);
            this.this$0 = kVar;
            this.$nodeId = j;
            this.$callback = bVar;
        }

        @NotNull
        public final kotlin.coroutines.d<kotlin.x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 7643, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
            if (proxy.isSupported) {
                return (kotlin.coroutines.d) proxy.result;
            }
            return new n(this.this$0, this.$nodeId, this.$callback, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 7645, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super kotlin.x>) (kotlin.coroutines.d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super kotlin.x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 7644, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((n) create(o0Var, dVar)).invokeSuspend(kotlin.x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            n nVar;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 7642, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    nVar = this;
                    com.leedarson.serviceimpl.manager.c i = nVar.this$0.h;
                    long j = nVar.$nodeId;
                    nVar.label = 1;
                    Object l = com.leedarson.serviceimpl.manager.c.l(i, j, 0, nVar, 2, (Object) null);
                    if (l != d) {
                        Object obj = l;
                        Object obj2 = $result;
                        $result = obj;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    nVar = this;
                    Object obj3 = $result;
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ChipClusters.OperationalCredentialsCluster cluster = (ChipClusters.OperationalCredentialsCluster) $result;
            if (cluster == null) {
                com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("on fail:could not find cluster by deviceId:", kotlin.coroutines.jvm.internal.b.d(nVar.$nodeId)), (String) null, 2, (Object) null);
                com.leedarson.serviceimpl.listener.b bVar = nVar.$callback;
                if (bVar != null) {
                    bVar.onFail(-1, new IllegalArgumentException(kotlin.jvm.internal.k.l("could not find cluster by deviceId:", kotlin.coroutines.jvm.internal.b.d(nVar.$nodeId))));
                }
                return kotlin.x.a;
            }
            ChipClusters.OperationalCredentialsCluster $this$invokeSuspend_u24lambda_u2d0 = cluster;
            $this$invokeSuspend_u24lambda_u2d0.readCurrentFabricIndexAttribute(new a($this$invokeSuspend_u24lambda_u2d0, nVar.$callback, nVar.$nodeId));
            return kotlin.x.a;
        }

        /* compiled from: MTCmdCtrl.kt */
        public static final class a implements ChipClusters.IntegerAttributeCallback {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ ChipClusters.OperationalCredentialsCluster a;
            final /* synthetic */ com.leedarson.serviceimpl.listener.b b;
            final /* synthetic */ long c;

            public /* synthetic */ void onSubscriptionEstablished() {
                gs1.a(this);
            }

            a(ChipClusters.OperationalCredentialsCluster $receiver, com.leedarson.serviceimpl.listener.b $callback, long $nodeId) {
                this.a = $receiver;
                this.b = $callback;
                this.c = $nodeId;
            }

            /* renamed from: com.leedarson.serviceimpl.ctrl.k$n$a$a  reason: collision with other inner class name */
            /* compiled from: MTCmdCtrl.kt */
            public static final class C0135a implements ChipClusters.OperationalCredentialsCluster.NOCResponseCallback {
                public static ChangeQuickRedirect changeQuickRedirect;
                final /* synthetic */ com.leedarson.serviceimpl.listener.b a;
                final /* synthetic */ long b;

                C0135a(com.leedarson.serviceimpl.listener.b $callback, long $nodeId) {
                    this.a = $callback;
                    this.b = $nodeId;
                }

                public void onSuccess(@Nullable Integer statusCode, @Nullable Optional<Integer> optional, @Nullable Optional<String> debugText) {
                    Class[] clsArr = {Integer.class, Optional.class, Optional.class};
                    if (!PatchProxy.proxy(new Object[]{statusCode, optional, debugText}, this, changeQuickRedirect, false, 7648, clsArr, Void.TYPE).isSupported) {
                        com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                        com.leedarson.serviceimpl.k.b(kVar, "removeFabric onSuccess statusCode:" + statusCode + ",debugText:" + debugText, (String) null, 2, (Object) null);
                        if (statusCode != null && statusCode.intValue() == 0) {
                            this.a.onSuccess(this.b, "");
                            return;
                        }
                        com.leedarson.serviceimpl.listener.b bVar = this.a;
                        kotlin.jvm.internal.k.c(statusCode);
                        bVar.onFail(statusCode.intValue(), new Exception(String.valueOf(debugText)));
                    }
                }

                public void onError(@Nullable Exception error) {
                    if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 7649, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                        com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("removeFabric onError:", error), (String) null, 2, (Object) null);
                        this.a.onFail(-1, error);
                    }
                }
            }

            public void onSuccess(int value) {
                Object[] objArr = {new Integer(value)};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7646, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                    com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("readCurrentFabricIndexAttribute onSuccess:", Integer.valueOf(value)), (String) null, 2, (Object) null);
                    this.a.removeFabric(new C0135a(this.b, this.c), Integer.valueOf(value));
                }
            }

            public void onError(@Nullable Exception error) {
                if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 7647, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                    com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("readCurrentFabricIndexAttribute onError:", error), (String) null, 2, (Object) null);
                    this.b.onFail(-1, error);
                }
            }
        }
    }

    public final void r(long j2, @NotNull com.leedarson.serviceimpl.listener.b bVar) {
        Object[] objArr = {new Long(j2), bVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7597, new Class[]{Long.TYPE, com.leedarson.serviceimpl.listener.b.class}, Void.TYPE).isSupported) {
            long nodeId = j2;
            com.leedarson.serviceimpl.listener.b callback = bVar;
            kotlin.jvm.internal.k.e(callback, "callback");
            com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("removeDevice nodeId:", Long.valueOf(nodeId)), (String) null, 2, (Object) null);
            kotlinx.coroutines.j.d(s1.c, (kotlin.coroutines.g) null, (q0) null, new n(this, nodeId, callback, (kotlin.coroutines.d<? super n>) null), 3, (Object) null).start();
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTCmdCtrl$getDevType$1", f = "MTCmdCtrl.kt", l = {505}, m = "invokeSuspend")
    /* compiled from: MTCmdCtrl.kt */
    public static final class i extends kotlin.coroutines.jvm.internal.l implements kotlin.jvm.functions.p<o0, kotlin.coroutines.d<? super kotlin.x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.serviceimpl.listener.b $callback;
        final /* synthetic */ long $nodeId;
        int label;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        i(k kVar, long j, com.leedarson.serviceimpl.listener.b bVar, kotlin.coroutines.d<? super i> dVar) {
            super(2, dVar);
            this.this$0 = kVar;
            this.$nodeId = j;
            this.$callback = bVar;
        }

        @NotNull
        public final kotlin.coroutines.d<kotlin.x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 7624, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
            if (proxy.isSupported) {
                return (kotlin.coroutines.d) proxy.result;
            }
            return new i(this.this$0, this.$nodeId, this.$callback, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 7626, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super kotlin.x>) (kotlin.coroutines.d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super kotlin.x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 7625, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((i) create(o0Var, dVar)).invokeSuspend(kotlin.x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            i iVar;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 7623, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    iVar = this;
                    com.leedarson.serviceimpl.manager.c i = iVar.this$0.h;
                    long j = iVar.$nodeId;
                    iVar.label = 1;
                    Object e = com.leedarson.serviceimpl.manager.c.e(i, j, 0, iVar, 2, (Object) null);
                    if (e != d) {
                        Object obj = e;
                        Object obj2 = $result;
                        $result = obj;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    iVar = this;
                    Object obj3 = $result;
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ChipClusters.DescriptorCluster cluster = (ChipClusters.DescriptorCluster) $result;
            if (cluster == null) {
                com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("on fail:could not find cluster by deviceId:", kotlin.coroutines.jvm.internal.b.d(iVar.$nodeId)), (String) null, 2, (Object) null);
                com.leedarson.serviceimpl.listener.b bVar = iVar.$callback;
                if (bVar != null) {
                    bVar.onFail(-1, new IllegalArgumentException(kotlin.jvm.internal.k.l("could not find cluster by deviceId:", kotlin.coroutines.jvm.internal.b.d(iVar.$nodeId))));
                }
                return kotlin.x.a;
            }
            cluster.readDeviceListAttribute(new a(iVar.$callback, iVar.$nodeId));
            return kotlin.x.a;
        }

        /* compiled from: MTCmdCtrl.kt */
        public static final class a implements ChipClusters.DescriptorCluster.DeviceListAttributeCallback {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ com.leedarson.serviceimpl.listener.b a;
            final /* synthetic */ long b;

            public /* synthetic */ void onSubscriptionEstablished() {
                sp1.a(this);
            }

            a(com.leedarson.serviceimpl.listener.b $callback, long $nodeId) {
                this.a = $callback;
                this.b = $nodeId;
            }

            public void onSuccess(@Nullable List<ChipStructs.DescriptorClusterDeviceType> list) {
                if (!PatchProxy.proxy(new Object[]{list}, this, changeQuickRedirect, false, 7627, new Class[]{List.class}, Void.TYPE).isSupported) {
                    Iterable<ChipStructs.DescriptorClusterDeviceType> valueList = list;
                    com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, "readAttributeListAttribute onSuccess", (String) null, 2, (Object) null);
                    List typeList = new ArrayList();
                    if (valueList != null) {
                        com.leedarson.serviceimpl.listener.b bVar = this.a;
                        long j = this.b;
                        for (ChipStructs.DescriptorClusterDeviceType it : valueList) {
                            com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, it.toString(), (String) null, 2, (Object) null);
                            Long l = it.type;
                            kotlin.jvm.internal.k.d(l, "it.type");
                            typeList.add(l);
                            if (bVar != null) {
                                bVar.onSuccess(j, typeList);
                            }
                        }
                    }
                }
            }

            public void onError(@Nullable Exception ex) {
                if (!PatchProxy.proxy(new Object[]{ex}, this, changeQuickRedirect, false, 7628, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                    com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("readAttributeListAttribute onError:", ex), (String) null, 2, (Object) null);
                    com.leedarson.serviceimpl.listener.b bVar = this.a;
                    if (bVar != null) {
                        bVar.onFail(-1, ex);
                    }
                }
            }
        }
    }

    public final void o(long nodeId, @Nullable com.leedarson.serviceimpl.listener.b callback) {
        Object[] objArr = {new Long(nodeId), callback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7598, new Class[]{Long.TYPE, com.leedarson.serviceimpl.listener.b.class}, Void.TYPE).isSupported) {
            kotlinx.coroutines.j.d(s1.c, (kotlin.coroutines.g) null, (q0) null, new i(this, nodeId, callback, (kotlin.coroutines.d<? super i>) null), 3, (Object) null).start();
        }
    }
}
