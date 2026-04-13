package com.leedarson.serviceimpl.manager;

import android.content.Context;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ClusterManager.kt */
public final class c {
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    private Context a;
    private final int b = -1;

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.manager.ClusterManager", f = "ClusterManager.kt", l = {125}, m = "getAccessControlClusterForDevice")
    /* compiled from: ClusterManager.kt */
    public static final class a extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(c cVar, kotlin.coroutines.d<? super a> dVar) {
            super(dVar);
            this.this$0 = cVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8111, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.a(0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.manager.ClusterManager", f = "ClusterManager.kt", l = {63}, m = "getBasicClusterForDevice")
    /* compiled from: ClusterManager.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(c cVar, kotlin.coroutines.d<? super b> dVar) {
            super(dVar);
            this.this$0 = cVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8112, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.b(0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.manager.ClusterManager", f = "ClusterManager.kt", l = {47}, m = "getColorControlClusterForDevice")
    /* renamed from: com.leedarson.serviceimpl.manager.c$c  reason: collision with other inner class name */
    /* compiled from: ClusterManager.kt */
    public static final class C0141c extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0141c(c cVar, kotlin.coroutines.d<? super C0141c> dVar) {
            super(dVar);
            this.this$0 = cVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8113, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.c(0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.manager.ClusterManager", f = "ClusterManager.kt", l = {158}, m = "getDescriptorClusterForDevice")
    /* compiled from: ClusterManager.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(c cVar, kotlin.coroutines.d<? super d> dVar) {
            super(dVar);
            this.this$0 = cVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8114, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.d(0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.manager.ClusterManager", f = "ClusterManager.kt", l = {92}, m = "getGroupClusterForDevice")
    /* compiled from: ClusterManager.kt */
    public static final class e extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(c cVar, kotlin.coroutines.d<? super e> dVar) {
            super(dVar);
            this.this$0 = cVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8115, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.f(0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.manager.ClusterManager", f = "ClusterManager.kt", l = {109}, m = "getGroupKeyManagementClusterForDevice")
    /* compiled from: ClusterManager.kt */
    public static final class f extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(c cVar, kotlin.coroutines.d<? super f> dVar) {
            super(dVar);
            this.this$0 = cVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8116, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.g(0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.manager.ClusterManager", f = "ClusterManager.kt", l = {142}, m = "getIdentifyClusterForDevice")
    /* compiled from: ClusterManager.kt */
    public static final class g extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(c cVar, kotlin.coroutines.d<? super g> dVar) {
            super(dVar);
            this.this$0 = cVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8117, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.h(0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.manager.ClusterManager", f = "ClusterManager.kt", l = {30}, m = "getLevelControlClusterForDevice")
    /* compiled from: ClusterManager.kt */
    public static final class h extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(c cVar, kotlin.coroutines.d<? super h> dVar) {
            super(dVar);
            this.this$0 = cVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8118, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.i(0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.manager.ClusterManager", f = "ClusterManager.kt", l = {14}, m = "getOnOffClusterForDevice")
    /* compiled from: ClusterManager.kt */
    public static final class i extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        i(c cVar, kotlin.coroutines.d<? super i> dVar) {
            super(dVar);
            this.this$0 = cVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8119, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.j(0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.manager.ClusterManager", f = "ClusterManager.kt", l = {77, 80}, m = "getOperationalCredentialsClusterForDevice")
    /* compiled from: ClusterManager.kt */
    public static final class j extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        int I$0;
        long J$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        j(c cVar, kotlin.coroutines.d<? super j> dVar) {
            super(dVar);
            this.this$0 = cVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8120, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.k(0, 0, this);
        }
    }

    public c(@NotNull Context context) {
        k.e(context, "context");
        this.a = context;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: com.leedarson.serviceimpl.manager.c} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x009d A[RETURN] */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object j(long r10, int r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super chip.devicecontroller.ChipClusters.OnOffCluster> r13) {
        /*
            r9 = this;
            r0 = 3
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Long r2 = new java.lang.Long
            r2.<init>(r10)
            r3 = 0
            r1[r3] = r2
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r12)
            r8 = 1
            r1[r8] = r2
            r2 = 2
            r1[r2] = r13
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Long.TYPE
            r6[r3] = r0
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r8] = r0
            java.lang.Class<kotlin.coroutines.d> r0 = kotlin.coroutines.d.class
            r6[r2] = r0
            java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
            r0 = 0
            r5 = 8094(0x1f9e, float:1.1342E-41)
            r2 = r9
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0039
            java.lang.Object r10 = r0.result
            return r10
        L_0x0039:
            boolean r0 = r13 instanceof com.leedarson.serviceimpl.manager.c.i
            if (r0 == 0) goto L_0x004c
            r0 = r13
            com.leedarson.serviceimpl.manager.c$i r0 = (com.leedarson.serviceimpl.manager.c.i) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x004c
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0051
        L_0x004c:
            com.leedarson.serviceimpl.manager.c$i r0 = new com.leedarson.serviceimpl.manager.c$i
            r0.<init>(r9, r13)
        L_0x0051:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x0074;
                case 1: goto L_0x0065;
                default: goto L_0x005d;
            }
        L_0x005d:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x0065:
            r10 = r9
            r11 = r12
            int r11 = r13.I$0
            java.lang.Object r12 = r13.L$0
            r10 = r12
            com.leedarson.serviceimpl.manager.c r10 = (com.leedarson.serviceimpl.manager.c) r10
            kotlin.p.b(r0)
            r2 = r10
            r10 = r0
            goto L_0x008a
        L_0x0074:
            kotlin.p.b(r0)
            r2 = r9
            com.google.chip.chiptool.ChipClient r3 = com.google.chip.chiptool.ChipClient.INSTANCE
            android.content.Context r4 = r2.a
            r13.L$0 = r2
            r13.I$0 = r12
            r13.label = r8
            java.lang.Object r10 = r3.getConnectedDevicePointer(r4, r10, r13)
            if (r10 != r1) goto L_0x0089
            return r1
        L_0x0089:
            r11 = r12
        L_0x008a:
            java.lang.Number r10 = (java.lang.Number) r10
            long r3 = r10.longValue()
            int r10 = (int) r3
            int r12 = r2.b
            if (r10 == r12) goto L_0x009d
            chip.devicecontroller.ChipClusters$OnOffCluster r10 = new chip.devicecontroller.ChipClusters$OnOffCluster
            r10.<init>(r3, r11)
            return r10
        L_0x009d:
            r10 = 0
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.manager.c.j(long, int, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: com.leedarson.serviceimpl.manager.c} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x009d A[RETURN] */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object i(long r10, int r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super chip.devicecontroller.ChipClusters.LevelControlCluster> r13) {
        /*
            r9 = this;
            r0 = 3
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Long r2 = new java.lang.Long
            r2.<init>(r10)
            r3 = 0
            r1[r3] = r2
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r12)
            r8 = 1
            r1[r8] = r2
            r2 = 2
            r1[r2] = r13
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Long.TYPE
            r6[r3] = r0
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r8] = r0
            java.lang.Class<kotlin.coroutines.d> r0 = kotlin.coroutines.d.class
            r6[r2] = r0
            java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
            r0 = 0
            r5 = 8095(0x1f9f, float:1.1344E-41)
            r2 = r9
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0039
            java.lang.Object r10 = r0.result
            return r10
        L_0x0039:
            boolean r0 = r13 instanceof com.leedarson.serviceimpl.manager.c.h
            if (r0 == 0) goto L_0x004c
            r0 = r13
            com.leedarson.serviceimpl.manager.c$h r0 = (com.leedarson.serviceimpl.manager.c.h) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x004c
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0051
        L_0x004c:
            com.leedarson.serviceimpl.manager.c$h r0 = new com.leedarson.serviceimpl.manager.c$h
            r0.<init>(r9, r13)
        L_0x0051:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x0074;
                case 1: goto L_0x0065;
                default: goto L_0x005d;
            }
        L_0x005d:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x0065:
            r10 = r9
            r11 = r12
            int r11 = r13.I$0
            java.lang.Object r12 = r13.L$0
            r10 = r12
            com.leedarson.serviceimpl.manager.c r10 = (com.leedarson.serviceimpl.manager.c) r10
            kotlin.p.b(r0)
            r2 = r10
            r10 = r0
            goto L_0x008a
        L_0x0074:
            kotlin.p.b(r0)
            r2 = r9
            com.google.chip.chiptool.ChipClient r3 = com.google.chip.chiptool.ChipClient.INSTANCE
            android.content.Context r4 = r2.a
            r13.L$0 = r2
            r13.I$0 = r12
            r13.label = r8
            java.lang.Object r10 = r3.getConnectedDevicePointer(r4, r10, r13)
            if (r10 != r1) goto L_0x0089
            return r1
        L_0x0089:
            r11 = r12
        L_0x008a:
            java.lang.Number r10 = (java.lang.Number) r10
            long r3 = r10.longValue()
            int r10 = (int) r3
            int r12 = r2.b
            if (r10 == r12) goto L_0x009d
            chip.devicecontroller.ChipClusters$LevelControlCluster r10 = new chip.devicecontroller.ChipClusters$LevelControlCluster
            r10.<init>(r3, r11)
            return r10
        L_0x009d:
            r10 = 0
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.manager.c.i(long, int, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: com.leedarson.serviceimpl.manager.c} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x009d A[RETURN] */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object c(long r10, int r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super chip.devicecontroller.ChipClusters.ColorControlCluster> r13) {
        /*
            r9 = this;
            r0 = 3
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Long r2 = new java.lang.Long
            r2.<init>(r10)
            r3 = 0
            r1[r3] = r2
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r12)
            r8 = 1
            r1[r8] = r2
            r2 = 2
            r1[r2] = r13
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Long.TYPE
            r6[r3] = r0
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r8] = r0
            java.lang.Class<kotlin.coroutines.d> r0 = kotlin.coroutines.d.class
            r6[r2] = r0
            java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
            r0 = 0
            r5 = 8096(0x1fa0, float:1.1345E-41)
            r2 = r9
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0039
            java.lang.Object r10 = r0.result
            return r10
        L_0x0039:
            boolean r0 = r13 instanceof com.leedarson.serviceimpl.manager.c.C0141c
            if (r0 == 0) goto L_0x004c
            r0 = r13
            com.leedarson.serviceimpl.manager.c$c r0 = (com.leedarson.serviceimpl.manager.c.C0141c) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x004c
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0051
        L_0x004c:
            com.leedarson.serviceimpl.manager.c$c r0 = new com.leedarson.serviceimpl.manager.c$c
            r0.<init>(r9, r13)
        L_0x0051:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x0074;
                case 1: goto L_0x0065;
                default: goto L_0x005d;
            }
        L_0x005d:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x0065:
            r10 = r9
            r11 = r12
            int r11 = r13.I$0
            java.lang.Object r12 = r13.L$0
            r10 = r12
            com.leedarson.serviceimpl.manager.c r10 = (com.leedarson.serviceimpl.manager.c) r10
            kotlin.p.b(r0)
            r2 = r10
            r10 = r0
            goto L_0x008a
        L_0x0074:
            kotlin.p.b(r0)
            r2 = r9
            com.google.chip.chiptool.ChipClient r3 = com.google.chip.chiptool.ChipClient.INSTANCE
            android.content.Context r4 = r2.a
            r13.L$0 = r2
            r13.I$0 = r12
            r13.label = r8
            java.lang.Object r10 = r3.getConnectedDevicePointer(r4, r10, r13)
            if (r10 != r1) goto L_0x0089
            return r1
        L_0x0089:
            r11 = r12
        L_0x008a:
            java.lang.Number r10 = (java.lang.Number) r10
            long r3 = r10.longValue()
            int r10 = (int) r3
            int r12 = r2.b
            if (r10 == r12) goto L_0x009d
            chip.devicecontroller.ChipClusters$ColorControlCluster r10 = new chip.devicecontroller.ChipClusters$ColorControlCluster
            r10.<init>(r3, r11)
            return r10
        L_0x009d:
            r10 = 0
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.manager.c.c(long, int, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: com.leedarson.serviceimpl.manager.c} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x009d A[RETURN] */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object b(long r10, int r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super chip.devicecontroller.ChipClusters.BasicCluster> r13) {
        /*
            r9 = this;
            r0 = 3
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Long r2 = new java.lang.Long
            r2.<init>(r10)
            r3 = 0
            r1[r3] = r2
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r12)
            r8 = 1
            r1[r8] = r2
            r2 = 2
            r1[r2] = r13
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Long.TYPE
            r6[r3] = r0
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r8] = r0
            java.lang.Class<kotlin.coroutines.d> r0 = kotlin.coroutines.d.class
            r6[r2] = r0
            java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
            r0 = 0
            r5 = 8097(0x1fa1, float:1.1346E-41)
            r2 = r9
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0039
            java.lang.Object r10 = r0.result
            return r10
        L_0x0039:
            boolean r0 = r13 instanceof com.leedarson.serviceimpl.manager.c.b
            if (r0 == 0) goto L_0x004c
            r0 = r13
            com.leedarson.serviceimpl.manager.c$b r0 = (com.leedarson.serviceimpl.manager.c.b) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x004c
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0051
        L_0x004c:
            com.leedarson.serviceimpl.manager.c$b r0 = new com.leedarson.serviceimpl.manager.c$b
            r0.<init>(r9, r13)
        L_0x0051:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x0074;
                case 1: goto L_0x0065;
                default: goto L_0x005d;
            }
        L_0x005d:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x0065:
            r10 = r9
            r11 = r12
            int r11 = r13.I$0
            java.lang.Object r12 = r13.L$0
            r10 = r12
            com.leedarson.serviceimpl.manager.c r10 = (com.leedarson.serviceimpl.manager.c) r10
            kotlin.p.b(r0)
            r2 = r10
            r10 = r0
            goto L_0x008a
        L_0x0074:
            kotlin.p.b(r0)
            r2 = r9
            com.google.chip.chiptool.ChipClient r3 = com.google.chip.chiptool.ChipClient.INSTANCE
            android.content.Context r4 = r2.a
            r13.L$0 = r2
            r13.I$0 = r12
            r13.label = r8
            java.lang.Object r10 = r3.getConnectedDevicePointer(r4, r10, r13)
            if (r10 != r1) goto L_0x0089
            return r1
        L_0x0089:
            r11 = r12
        L_0x008a:
            java.lang.Number r10 = (java.lang.Number) r10
            long r3 = r10.longValue()
            int r10 = (int) r3
            int r12 = r2.b
            if (r10 == r12) goto L_0x009d
            chip.devicecontroller.ChipClusters$BasicCluster r10 = new chip.devicecontroller.ChipClusters$BasicCluster
            r10.<init>(r3, r11)
            return r10
        L_0x009d:
            r10 = 0
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.manager.c.b(long, int, kotlin.coroutines.d):java.lang.Object");
    }

    public static /* synthetic */ Object l(c cVar, long j2, int i2, kotlin.coroutines.d dVar, int i3, Object obj) {
        c cVar2 = cVar;
        long j3 = j2;
        kotlin.coroutines.d dVar2 = dVar;
        int i4 = i3;
        int i5 = 0;
        int i6 = i2;
        Object[] objArr = {cVar2, new Long(j3), new Integer(i6), dVar2, new Integer(i4), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {c.class, Long.TYPE, cls, kotlin.coroutines.d.class, cls, Object.class};
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 8100, clsArr, Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        if ((i4 & 2) == 0) {
            i5 = i6;
        }
        return cVar2.k(j3, i5, dVar2);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: com.leedarson.serviceimpl.manager.c} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x006e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00a0  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00bf A[RETURN] */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object k(long r11, int r13, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super chip.devicecontroller.ChipClusters.OperationalCredentialsCluster> r14) {
        /*
            r10 = this;
            r0 = 3
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Long r2 = new java.lang.Long
            r2.<init>(r11)
            r3 = 0
            r1[r3] = r2
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r13)
            r8 = 1
            r1[r8] = r2
            r9 = 2
            r1[r9] = r14
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Long.TYPE
            r6[r3] = r0
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r8] = r0
            java.lang.Class<kotlin.coroutines.d> r0 = kotlin.coroutines.d.class
            r6[r9] = r0
            java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
            r0 = 0
            r5 = 8099(0x1fa3, float:1.1349E-41)
            r2 = r10
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0039
            java.lang.Object r11 = r0.result
            return r11
        L_0x0039:
            boolean r0 = r14 instanceof com.leedarson.serviceimpl.manager.c.j
            if (r0 == 0) goto L_0x004c
            r0 = r14
            com.leedarson.serviceimpl.manager.c$j r0 = (com.leedarson.serviceimpl.manager.c.j) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x004c
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0051
        L_0x004c:
            com.leedarson.serviceimpl.manager.c$j r0 = new com.leedarson.serviceimpl.manager.c$j
            r0.<init>(r10, r14)
        L_0x0051:
            r14 = r0
            java.lang.Object r0 = r14.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r14.label
            switch(r2) {
                case 0: goto L_0x007d;
                case 1: goto L_0x006e;
                case 2: goto L_0x0065;
                default: goto L_0x005d;
            }
        L_0x005d:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L_0x0065:
            r11 = r13
            int r11 = r14.I$0
            kotlin.p.b(r0)
            r13 = r11
            r11 = r0
            goto L_0x00b2
        L_0x006e:
            r2 = r10
            int r13 = r14.I$0
            long r11 = r14.J$0
            java.lang.Object r3 = r14.L$0
            r2 = r3
            com.leedarson.serviceimpl.manager.c r2 = (com.leedarson.serviceimpl.manager.c) r2
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x0094
        L_0x007d:
            kotlin.p.b(r0)
            r2 = r10
            com.google.chip.chiptool.ChipClient r3 = com.google.chip.chiptool.ChipClient.INSTANCE
            android.content.Context r4 = r2.a
            r14.L$0 = r2
            r14.J$0 = r11
            r14.I$0 = r13
            r14.label = r8
            java.lang.Object r3 = r3.getConnectedDevicePointer(r4, r11, r14)
            if (r3 != r1) goto L_0x0094
            return r1
        L_0x0094:
            java.lang.Number r3 = (java.lang.Number) r3
            long r3 = r3.longValue()
            int r5 = (int) r3
            int r6 = r2.b
            r7 = 0
            if (r5 == r6) goto L_0x00bf
            com.google.chip.chiptool.ChipClient r5 = com.google.chip.chiptool.ChipClient.INSTANCE
            android.content.Context r6 = r2.a
            r14.L$0 = r7
            r14.I$0 = r13
            r14.label = r9
            java.lang.Object r11 = r5.getConnectedDevicePointer(r6, r11, r14)
            if (r11 != r1) goto L_0x00b2
            return r1
        L_0x00b2:
            java.lang.Number r11 = (java.lang.Number) r11
            long r11 = r11.longValue()
            chip.devicecontroller.ChipClusters$OperationalCredentialsCluster r1 = new chip.devicecontroller.ChipClusters$OperationalCredentialsCluster
            r1.<init>(r11, r13)
            return r1
        L_0x00bf:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.manager.c.k(long, int, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: com.leedarson.serviceimpl.manager.c} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x009d A[RETURN] */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object f(long r10, int r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super chip.devicecontroller.ChipClusters.GroupsCluster> r13) {
        /*
            r9 = this;
            r0 = 3
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Long r2 = new java.lang.Long
            r2.<init>(r10)
            r3 = 0
            r1[r3] = r2
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r12)
            r8 = 1
            r1[r8] = r2
            r2 = 2
            r1[r2] = r13
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Long.TYPE
            r6[r3] = r0
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r8] = r0
            java.lang.Class<kotlin.coroutines.d> r0 = kotlin.coroutines.d.class
            r6[r2] = r0
            java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
            r0 = 0
            r5 = 8101(0x1fa5, float:1.1352E-41)
            r2 = r9
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0039
            java.lang.Object r10 = r0.result
            return r10
        L_0x0039:
            boolean r0 = r13 instanceof com.leedarson.serviceimpl.manager.c.e
            if (r0 == 0) goto L_0x004c
            r0 = r13
            com.leedarson.serviceimpl.manager.c$e r0 = (com.leedarson.serviceimpl.manager.c.e) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x004c
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0051
        L_0x004c:
            com.leedarson.serviceimpl.manager.c$e r0 = new com.leedarson.serviceimpl.manager.c$e
            r0.<init>(r9, r13)
        L_0x0051:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x0074;
                case 1: goto L_0x0065;
                default: goto L_0x005d;
            }
        L_0x005d:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x0065:
            r10 = r9
            r11 = r12
            int r11 = r13.I$0
            java.lang.Object r12 = r13.L$0
            r10 = r12
            com.leedarson.serviceimpl.manager.c r10 = (com.leedarson.serviceimpl.manager.c) r10
            kotlin.p.b(r0)
            r2 = r10
            r10 = r0
            goto L_0x008a
        L_0x0074:
            kotlin.p.b(r0)
            r2 = r9
            com.google.chip.chiptool.ChipClient r3 = com.google.chip.chiptool.ChipClient.INSTANCE
            android.content.Context r4 = r2.a
            r13.L$0 = r2
            r13.I$0 = r12
            r13.label = r8
            java.lang.Object r10 = r3.getConnectedDevicePointer(r4, r10, r13)
            if (r10 != r1) goto L_0x0089
            return r1
        L_0x0089:
            r11 = r12
        L_0x008a:
            java.lang.Number r10 = (java.lang.Number) r10
            long r3 = r10.longValue()
            int r10 = (int) r3
            int r12 = r2.b
            if (r10 == r12) goto L_0x009d
            chip.devicecontroller.ChipClusters$GroupsCluster r10 = new chip.devicecontroller.ChipClusters$GroupsCluster
            r10.<init>(r3, r11)
            return r10
        L_0x009d:
            r10 = 0
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.manager.c.f(long, int, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: com.leedarson.serviceimpl.manager.c} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x009d A[RETURN] */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object g(long r10, int r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super chip.devicecontroller.ChipClusters.GroupKeyManagementCluster> r13) {
        /*
            r9 = this;
            r0 = 3
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Long r2 = new java.lang.Long
            r2.<init>(r10)
            r3 = 0
            r1[r3] = r2
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r12)
            r8 = 1
            r1[r8] = r2
            r2 = 2
            r1[r2] = r13
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Long.TYPE
            r6[r3] = r0
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r8] = r0
            java.lang.Class<kotlin.coroutines.d> r0 = kotlin.coroutines.d.class
            r6[r2] = r0
            java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
            r0 = 0
            r5 = 8103(0x1fa7, float:1.1355E-41)
            r2 = r9
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0039
            java.lang.Object r10 = r0.result
            return r10
        L_0x0039:
            boolean r0 = r13 instanceof com.leedarson.serviceimpl.manager.c.f
            if (r0 == 0) goto L_0x004c
            r0 = r13
            com.leedarson.serviceimpl.manager.c$f r0 = (com.leedarson.serviceimpl.manager.c.f) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x004c
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0051
        L_0x004c:
            com.leedarson.serviceimpl.manager.c$f r0 = new com.leedarson.serviceimpl.manager.c$f
            r0.<init>(r9, r13)
        L_0x0051:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x0074;
                case 1: goto L_0x0065;
                default: goto L_0x005d;
            }
        L_0x005d:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x0065:
            r10 = r9
            r11 = r12
            int r11 = r13.I$0
            java.lang.Object r12 = r13.L$0
            r10 = r12
            com.leedarson.serviceimpl.manager.c r10 = (com.leedarson.serviceimpl.manager.c) r10
            kotlin.p.b(r0)
            r2 = r10
            r10 = r0
            goto L_0x008a
        L_0x0074:
            kotlin.p.b(r0)
            r2 = r9
            com.google.chip.chiptool.ChipClient r3 = com.google.chip.chiptool.ChipClient.INSTANCE
            android.content.Context r4 = r2.a
            r13.L$0 = r2
            r13.I$0 = r12
            r13.label = r8
            java.lang.Object r10 = r3.getConnectedDevicePointer(r4, r10, r13)
            if (r10 != r1) goto L_0x0089
            return r1
        L_0x0089:
            r11 = r12
        L_0x008a:
            java.lang.Number r10 = (java.lang.Number) r10
            long r3 = r10.longValue()
            int r10 = (int) r3
            int r12 = r2.b
            if (r10 == r12) goto L_0x009d
            chip.devicecontroller.ChipClusters$GroupKeyManagementCluster r10 = new chip.devicecontroller.ChipClusters$GroupKeyManagementCluster
            r10.<init>(r3, r11)
            return r10
        L_0x009d:
            r10 = 0
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.manager.c.g(long, int, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: com.leedarson.serviceimpl.manager.c} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x009d A[RETURN] */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object a(long r10, int r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super chip.devicecontroller.ChipClusters.AccessControlCluster> r13) {
        /*
            r9 = this;
            r0 = 3
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Long r2 = new java.lang.Long
            r2.<init>(r10)
            r3 = 0
            r1[r3] = r2
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r12)
            r8 = 1
            r1[r8] = r2
            r2 = 2
            r1[r2] = r13
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Long.TYPE
            r6[r3] = r0
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r8] = r0
            java.lang.Class<kotlin.coroutines.d> r0 = kotlin.coroutines.d.class
            r6[r2] = r0
            java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
            r0 = 0
            r5 = 8105(0x1fa9, float:1.1358E-41)
            r2 = r9
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0039
            java.lang.Object r10 = r0.result
            return r10
        L_0x0039:
            boolean r0 = r13 instanceof com.leedarson.serviceimpl.manager.c.a
            if (r0 == 0) goto L_0x004c
            r0 = r13
            com.leedarson.serviceimpl.manager.c$a r0 = (com.leedarson.serviceimpl.manager.c.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x004c
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0051
        L_0x004c:
            com.leedarson.serviceimpl.manager.c$a r0 = new com.leedarson.serviceimpl.manager.c$a
            r0.<init>(r9, r13)
        L_0x0051:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x0074;
                case 1: goto L_0x0065;
                default: goto L_0x005d;
            }
        L_0x005d:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x0065:
            r10 = r9
            r11 = r12
            int r11 = r13.I$0
            java.lang.Object r12 = r13.L$0
            r10 = r12
            com.leedarson.serviceimpl.manager.c r10 = (com.leedarson.serviceimpl.manager.c) r10
            kotlin.p.b(r0)
            r2 = r10
            r10 = r0
            goto L_0x008a
        L_0x0074:
            kotlin.p.b(r0)
            r2 = r9
            com.google.chip.chiptool.ChipClient r3 = com.google.chip.chiptool.ChipClient.INSTANCE
            android.content.Context r4 = r2.a
            r13.L$0 = r2
            r13.I$0 = r12
            r13.label = r8
            java.lang.Object r10 = r3.getConnectedDevicePointer(r4, r10, r13)
            if (r10 != r1) goto L_0x0089
            return r1
        L_0x0089:
            r11 = r12
        L_0x008a:
            java.lang.Number r10 = (java.lang.Number) r10
            long r3 = r10.longValue()
            int r10 = (int) r3
            int r12 = r2.b
            if (r10 == r12) goto L_0x009d
            chip.devicecontroller.ChipClusters$AccessControlCluster r10 = new chip.devicecontroller.ChipClusters$AccessControlCluster
            r10.<init>(r3, r11)
            return r10
        L_0x009d:
            r10 = 0
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.manager.c.a(long, int, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: com.leedarson.serviceimpl.manager.c} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x009d A[RETURN] */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object h(long r10, int r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super chip.devicecontroller.ChipClusters.IdentifyCluster> r13) {
        /*
            r9 = this;
            r0 = 3
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Long r2 = new java.lang.Long
            r2.<init>(r10)
            r3 = 0
            r1[r3] = r2
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r12)
            r8 = 1
            r1[r8] = r2
            r2 = 2
            r1[r2] = r13
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Long.TYPE
            r6[r3] = r0
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r8] = r0
            java.lang.Class<kotlin.coroutines.d> r0 = kotlin.coroutines.d.class
            r6[r2] = r0
            java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
            r0 = 0
            r5 = 8107(0x1fab, float:1.136E-41)
            r2 = r9
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0039
            java.lang.Object r10 = r0.result
            return r10
        L_0x0039:
            boolean r0 = r13 instanceof com.leedarson.serviceimpl.manager.c.g
            if (r0 == 0) goto L_0x004c
            r0 = r13
            com.leedarson.serviceimpl.manager.c$g r0 = (com.leedarson.serviceimpl.manager.c.g) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x004c
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0051
        L_0x004c:
            com.leedarson.serviceimpl.manager.c$g r0 = new com.leedarson.serviceimpl.manager.c$g
            r0.<init>(r9, r13)
        L_0x0051:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x0074;
                case 1: goto L_0x0065;
                default: goto L_0x005d;
            }
        L_0x005d:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x0065:
            r10 = r9
            r11 = r12
            int r11 = r13.I$0
            java.lang.Object r12 = r13.L$0
            r10 = r12
            com.leedarson.serviceimpl.manager.c r10 = (com.leedarson.serviceimpl.manager.c) r10
            kotlin.p.b(r0)
            r2 = r10
            r10 = r0
            goto L_0x008a
        L_0x0074:
            kotlin.p.b(r0)
            r2 = r9
            com.google.chip.chiptool.ChipClient r3 = com.google.chip.chiptool.ChipClient.INSTANCE
            android.content.Context r4 = r2.a
            r13.L$0 = r2
            r13.I$0 = r12
            r13.label = r8
            java.lang.Object r10 = r3.getConnectedDevicePointer(r4, r10, r13)
            if (r10 != r1) goto L_0x0089
            return r1
        L_0x0089:
            r11 = r12
        L_0x008a:
            java.lang.Number r10 = (java.lang.Number) r10
            long r3 = r10.longValue()
            int r10 = (int) r3
            int r12 = r2.b
            if (r10 == r12) goto L_0x009d
            chip.devicecontroller.ChipClusters$IdentifyCluster r10 = new chip.devicecontroller.ChipClusters$IdentifyCluster
            r10.<init>(r3, r11)
            return r10
        L_0x009d:
            r10 = 0
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.manager.c.h(long, int, kotlin.coroutines.d):java.lang.Object");
    }

    public static /* synthetic */ Object e(c cVar, long j2, int i2, kotlin.coroutines.d dVar, int i3, Object obj) {
        c cVar2 = cVar;
        long j3 = j2;
        kotlin.coroutines.d dVar2 = dVar;
        int i4 = i3;
        int i5 = 1;
        int i6 = i2;
        Object[] objArr = {cVar2, new Long(j3), new Integer(i6), dVar2, new Integer(i4), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 8110, new Class[]{c.class, Long.TYPE, cls, kotlin.coroutines.d.class, cls, Object.class}, Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        if ((i4 & 2) == 0) {
            i5 = i6;
        }
        return cVar2.d(j3, i5, dVar2);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: com.leedarson.serviceimpl.manager.c} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x009d A[RETURN] */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object d(long r10, int r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super chip.devicecontroller.ChipClusters.DescriptorCluster> r13) {
        /*
            r9 = this;
            r0 = 3
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Long r2 = new java.lang.Long
            r2.<init>(r10)
            r3 = 0
            r1[r3] = r2
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r12)
            r8 = 1
            r1[r8] = r2
            r2 = 2
            r1[r2] = r13
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Long.TYPE
            r6[r3] = r0
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r8] = r0
            java.lang.Class<kotlin.coroutines.d> r0 = kotlin.coroutines.d.class
            r6[r2] = r0
            java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
            r0 = 0
            r5 = 8109(0x1fad, float:1.1363E-41)
            r2 = r9
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0039
            java.lang.Object r10 = r0.result
            return r10
        L_0x0039:
            boolean r0 = r13 instanceof com.leedarson.serviceimpl.manager.c.d
            if (r0 == 0) goto L_0x004c
            r0 = r13
            com.leedarson.serviceimpl.manager.c$d r0 = (com.leedarson.serviceimpl.manager.c.d) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x004c
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0051
        L_0x004c:
            com.leedarson.serviceimpl.manager.c$d r0 = new com.leedarson.serviceimpl.manager.c$d
            r0.<init>(r9, r13)
        L_0x0051:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x0074;
                case 1: goto L_0x0065;
                default: goto L_0x005d;
            }
        L_0x005d:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x0065:
            r10 = r9
            r11 = r12
            int r11 = r13.I$0
            java.lang.Object r12 = r13.L$0
            r10 = r12
            com.leedarson.serviceimpl.manager.c r10 = (com.leedarson.serviceimpl.manager.c) r10
            kotlin.p.b(r0)
            r2 = r10
            r10 = r0
            goto L_0x008a
        L_0x0074:
            kotlin.p.b(r0)
            r2 = r9
            com.google.chip.chiptool.ChipClient r3 = com.google.chip.chiptool.ChipClient.INSTANCE
            android.content.Context r4 = r2.a
            r13.L$0 = r2
            r13.I$0 = r12
            r13.label = r8
            java.lang.Object r10 = r3.getConnectedDevicePointer(r4, r10, r13)
            if (r10 != r1) goto L_0x0089
            return r1
        L_0x0089:
            r11 = r12
        L_0x008a:
            java.lang.Number r10 = (java.lang.Number) r10
            long r3 = r10.longValue()
            int r10 = (int) r3
            int r12 = r2.b
            if (r10 == r12) goto L_0x009d
            chip.devicecontroller.ChipClusters$DescriptorCluster r10 = new chip.devicecontroller.ChipClusters$DescriptorCluster
            r10.<init>(r3, r11)
            return r10
        L_0x009d:
            r10 = 0
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.manager.c.d(long, int, kotlin.coroutines.d):java.lang.Object");
    }
}
