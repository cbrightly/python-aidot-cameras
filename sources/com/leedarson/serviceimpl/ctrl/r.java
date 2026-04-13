package com.leedarson.serviceimpl.ctrl;

import android.content.Context;
import androidx.core.app.NotificationCompat;
import chip.devicecontroller.ChipClusters;
import chip.devicecontroller.gs1;
import com.leedarson.serviceimpl.MatterServiceImpl;
import com.leedarson.serviceimpl.bean.MtNode;
import com.leedarson.serviceinterface.MatterService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kotlin.coroutines.g;
import kotlin.coroutines.i;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.h;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.o;
import kotlin.x;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.s1;
import kotlinx.coroutines.z1;
import meshsdk.BaseResp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: RemoveFabricCtrl.kt */
public final class r {
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    private final String a;
    @NotNull
    private final Context b;
    private final long c;
    @NotNull
    private final JSONArray d;
    @NotNull
    private final MatterServiceImpl e;
    @NotNull
    private final List<a> f = new ArrayList();
    @NotNull
    private final List<a> g = new ArrayList();

    public r(@NotNull Context context, long matterAddr, @NotNull JSONArray arr, @NotNull String callback, @NotNull MatterServiceImpl service) {
        k.e(context, "context");
        k.e(arr, "arr");
        k.e(callback, "callback");
        k.e(service, NotificationCompat.CATEGORY_SERVICE);
        this.a = callback;
        this.b = context;
        this.c = matterAddr;
        this.d = arr;
        this.e = service;
    }

    public static final /* synthetic */ void a(r $this) {
        if (!PatchProxy.proxy(new Object[]{$this}, (Object) null, changeQuickRedirect, true, 7823, new Class[]{r.class}, Void.TYPE).isSupported) {
            $this.h();
        }
    }

    @NotNull
    public final Context b() {
        return this.b;
    }

    public final long f() {
        return this.c;
    }

    @NotNull
    public final JSONArray e() {
        return this.d;
    }

    @NotNull
    public final List<a> g() {
        return this.f;
    }

    @NotNull
    public final List<a> d() {
        return this.g;
    }

    @f(c = "com.leedarson.serviceimpl.ctrl.RemoveFabricCtrl$removeFabrics$1", f = "RemoveFabricCtrl.kt", l = {40, 53, 81}, m = "invokeSuspend")
    /* compiled from: RemoveFabricCtrl.kt */
    public static final class c extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        int I$0;
        int I$1;
        int I$2;
        Object L$0;
        Object L$1;
        int label;
        final /* synthetic */ r this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(r rVar, kotlin.coroutines.d<? super c> dVar) {
            super(2, dVar);
            this.this$0 = rVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect, false, 7828, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
            return proxy.isSupported ? (kotlin.coroutines.d) proxy.result : new c(this.this$0, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 7830, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 7829, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((c) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0095, code lost:
            r8 = (chip.devicecontroller.ChipClusters.OperationalCredentialsCluster) r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0097, code lost:
            if (r8 != null) goto L_0x00fe;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0099, code lost:
            com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("on fail:could not find cluster by matterAddr:", kotlin.coroutines.jvm.internal.b.d(r4.this$0.f())), (java.lang.String) null, 2, (java.lang.Object) null);
            r2 = r4.this$0.e().length();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x00b8, code lost:
            if (r2 <= 0) goto L_0x00f6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x00ba, code lost:
            r6 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x00bb, code lost:
            r7 = r6;
            r6 = r6 + 1;
            r10 = r4.this$0.d();
            r12 = r4.this$0.e().get(r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x00cf, code lost:
            if (r12 == null) goto L_0x00f0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x00d1, code lost:
            r10.add(new com.leedarson.serviceimpl.ctrl.r.a(((java.lang.Integer) r12).intValue(), kotlin.jvm.internal.k.l("could not find OperationalCredentialsCluster by matterAddr:", kotlin.coroutines.jvm.internal.b.d(r4.this$0.f())), false));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x00ed, code lost:
            if (r6 < r2) goto L_0x00bb;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x00f5, code lost:
            throw new java.lang.NullPointerException("null cannot be cast to non-null type kotlin.Int");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x00f6, code lost:
            com.leedarson.serviceimpl.ctrl.r.a(r4.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00fd, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x00fe, code lost:
            r10 = r4.this$0;
            r4.L$0 = r8;
            r4.label = 2;
            r10 = r10.c(r8, r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0108, code lost:
            if (r10 != r2) goto L_0x010b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x010a, code lost:
            return r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x010b, code lost:
            r16 = r10;
            r10 = r8;
            r8 = r16;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x0110, code lost:
            r8 = ((java.lang.Number) r8).intValue();
            com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("currentFabricIndex:", kotlin.coroutines.jvm.internal.b.c(r8)), (java.lang.String) null, 2, (java.lang.Object) null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x0126, code lost:
            if (r8 != -1) goto L_0x0178;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x0128, code lost:
            r2 = r4.this$0.e().length();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x0132, code lost:
            if (r2 <= 0) goto L_0x0170;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x0134, code lost:
            r6 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x0135, code lost:
            r7 = r6;
            r6 = r6 + 1;
            r10 = r4.this$0.d();
            r12 = r4.this$0.e().get(r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x0149, code lost:
            if (r12 == null) goto L_0x016a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x014b, code lost:
            r10.add(new com.leedarson.serviceimpl.ctrl.r.a(((java.lang.Integer) r12).intValue(), kotlin.jvm.internal.k.l("get currentFabricIndex fail:", kotlin.coroutines.jvm.internal.b.d(r4.this$0.f())), false));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x0167, code lost:
            if (r6 < r2) goto L_0x0135;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x016f, code lost:
            throw new java.lang.NullPointerException("null cannot be cast to non-null type kotlin.Int");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x0170, code lost:
            com.leedarson.serviceimpl.ctrl.r.a(r4.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:0x0177, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x0178, code lost:
            r5 = r4.this$0.e().length();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x0182, code lost:
            if (r5 <= 0) goto L_0x01a7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x0184, code lost:
            r11 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x0185, code lost:
            r12 = r11;
            r11 = r11 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x0199, code lost:
            if (kotlin.jvm.internal.k.a(r4.this$0.e().get(r12), kotlin.coroutines.jvm.internal.b.c(r8)) == false) goto L_0x01a5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x019b, code lost:
            r4.this$0.e().remove(r12);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x01a5, code lost:
            if (r11 < r5) goto L_0x0185;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:56:0x01a7, code lost:
            r4.this$0.e().put(r8);
            r5 = r4.this$0.e().length();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:57:0x01ba, code lost:
            if (r5 <= 0) goto L_0x025d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:0x01bc, code lost:
            r11 = r8;
            r12 = r10;
            r16 = r9;
            r9 = r5;
            r5 = r16;
         */
        /* JADX WARNING: Removed duplicated region for block: B:86:0x0258  */
        /* JADX WARNING: Removed duplicated region for block: B:87:0x025a  */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r18) {
            /*
                r17 = this;
                r1 = 1
                java.lang.Object[] r2 = new java.lang.Object[r1]
                r0 = 0
                r2[r0] = r18
                com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
                java.lang.Class[] r7 = new java.lang.Class[r1]
                java.lang.Class<java.lang.Object> r3 = java.lang.Object.class
                r7[r0] = r3
                java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
                r5 = 0
                r6 = 7827(0x1e93, float:1.0968E-41)
                r3 = r17
                com.meituan.robust.PatchProxyResult r2 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
                boolean r3 = r2.isSupported
                if (r3 == 0) goto L_0x0020
                java.lang.Object r0 = r2.result
                return r0
            L_0x0020:
                java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
                r3 = r17
                int r4 = r3.label
                java.lang.String r5 = "null cannot be cast to non-null type kotlin.Int"
                r6 = 2
                r7 = 0
                switch(r4) {
                    case 0: goto L_0x006b;
                    case 1: goto L_0x0062;
                    case 2: goto L_0x0053;
                    case 3: goto L_0x0037;
                    default: goto L_0x002f;
                }
            L_0x002f:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0037:
                r4 = r17
                r5 = r18
                r8 = 0
                int r9 = r4.I$2
                int r10 = r4.I$1
                int r11 = r4.I$0
                java.lang.Object r0 = r4.L$1
                com.leedarson.serviceimpl.ctrl.r r0 = (com.leedarson.serviceimpl.ctrl.r) r0
                java.lang.Object r12 = r4.L$0
                chip.devicecontroller.ChipClusters$OperationalCredentialsCluster r12 = (chip.devicecontroller.ChipClusters.OperationalCredentialsCluster) r12
                kotlin.p.b(r5)     // Catch:{ Exception -> 0x0050 }
                r1 = r5
                goto L_0x0229
            L_0x0050:
                r0 = move-exception
                goto L_0x0248
            L_0x0053:
                r4 = r17
                r8 = r18
                java.lang.Object r9 = r4.L$0
                chip.devicecontroller.ChipClusters$OperationalCredentialsCluster r9 = (chip.devicecontroller.ChipClusters.OperationalCredentialsCluster) r9
                kotlin.p.b(r8)
                r10 = r9
                r9 = r8
                goto L_0x0110
            L_0x0062:
                r4 = r17
                r8 = r18
                kotlin.p.b(r8)
                r9 = r8
                goto L_0x0095
            L_0x006b:
                kotlin.p.b(r18)
                r4 = r17
                r8 = r18
                com.leedarson.serviceimpl.manager.c r9 = new com.leedarson.serviceimpl.manager.c
                com.leedarson.serviceimpl.ctrl.r r10 = r4.this$0
                android.content.Context r10 = r10.b()
                r9.<init>(r10)
                com.leedarson.serviceimpl.ctrl.r r10 = r4.this$0
                long r10 = r10.f()
                r12 = 0
                r14 = 2
                r15 = 0
                r4.label = r1
                r13 = r4
                java.lang.Object r9 = com.leedarson.serviceimpl.manager.c.l(r9, r10, r12, r13, r14, r15)
                if (r9 != r2) goto L_0x0090
                return r2
            L_0x0090:
                r16 = r9
                r9 = r8
                r8 = r16
            L_0x0095:
                chip.devicecontroller.ChipClusters$OperationalCredentialsCluster r8 = (chip.devicecontroller.ChipClusters.OperationalCredentialsCluster) r8
                if (r8 != 0) goto L_0x00fe
                com.leedarson.serviceimpl.k r2 = com.leedarson.serviceimpl.k.a
                com.leedarson.serviceimpl.ctrl.r r10 = r4.this$0
                long r10 = r10.f()
                java.lang.Long r10 = kotlin.coroutines.jvm.internal.b.d(r10)
                java.lang.String r11 = "on fail:could not find cluster by matterAddr:"
                java.lang.String r10 = kotlin.jvm.internal.k.l(r11, r10)
                com.leedarson.serviceimpl.k.e(r2, r10, r7, r6, r7)
                com.leedarson.serviceimpl.ctrl.r r2 = r4.this$0
                org.json.JSONArray r2 = r2.e()
                int r2 = r2.length()
                if (r2 <= 0) goto L_0x00f6
                r6 = r0
            L_0x00bb:
                r7 = r6
                int r6 = r6 + r1
                com.leedarson.serviceimpl.ctrl.r r10 = r4.this$0
                java.util.List r10 = r10.d()
                com.leedarson.serviceimpl.ctrl.r$a r11 = new com.leedarson.serviceimpl.ctrl.r$a
                com.leedarson.serviceimpl.ctrl.r r12 = r4.this$0
                org.json.JSONArray r12 = r12.e()
                java.lang.Object r12 = r12.get(r7)
                if (r12 == 0) goto L_0x00f0
                java.lang.Integer r12 = (java.lang.Integer) r12
                int r12 = r12.intValue()
                com.leedarson.serviceimpl.ctrl.r r13 = r4.this$0
                long r13 = r13.f()
                java.lang.Long r13 = kotlin.coroutines.jvm.internal.b.d(r13)
                java.lang.String r14 = "could not find OperationalCredentialsCluster by matterAddr:"
                java.lang.String r13 = kotlin.jvm.internal.k.l(r14, r13)
                r11.<init>(r12, r13, r0)
                r10.add(r11)
                if (r6 < r2) goto L_0x00bb
                goto L_0x00f6
            L_0x00f0:
                java.lang.NullPointerException r0 = new java.lang.NullPointerException
                r0.<init>(r5)
                throw r0
            L_0x00f6:
                com.leedarson.serviceimpl.ctrl.r r0 = r4.this$0
                com.leedarson.serviceimpl.ctrl.r.a(r0)
                kotlin.x r0 = kotlin.x.a
                return r0
            L_0x00fe:
                com.leedarson.serviceimpl.ctrl.r r10 = r4.this$0
                r4.L$0 = r8
                r4.label = r6
                java.lang.Object r10 = r10.c(r8, r4)
                if (r10 != r2) goto L_0x010b
                return r2
            L_0x010b:
                r16 = r10
                r10 = r8
                r8 = r16
            L_0x0110:
                java.lang.Number r8 = (java.lang.Number) r8
                int r8 = r8.intValue()
                com.leedarson.serviceimpl.k r11 = com.leedarson.serviceimpl.k.a
                java.lang.Integer r12 = kotlin.coroutines.jvm.internal.b.c(r8)
                java.lang.String r13 = "currentFabricIndex:"
                java.lang.String r12 = kotlin.jvm.internal.k.l(r13, r12)
                com.leedarson.serviceimpl.k.b(r11, r12, r7, r6, r7)
                r11 = -1
                if (r8 != r11) goto L_0x0178
                com.leedarson.serviceimpl.ctrl.r r2 = r4.this$0
                org.json.JSONArray r2 = r2.e()
                int r2 = r2.length()
                if (r2 <= 0) goto L_0x0170
                r6 = r0
            L_0x0135:
                r7 = r6
                int r6 = r6 + r1
                com.leedarson.serviceimpl.ctrl.r r10 = r4.this$0
                java.util.List r10 = r10.d()
                com.leedarson.serviceimpl.ctrl.r$a r11 = new com.leedarson.serviceimpl.ctrl.r$a
                com.leedarson.serviceimpl.ctrl.r r12 = r4.this$0
                org.json.JSONArray r12 = r12.e()
                java.lang.Object r12 = r12.get(r7)
                if (r12 == 0) goto L_0x016a
                java.lang.Integer r12 = (java.lang.Integer) r12
                int r12 = r12.intValue()
                com.leedarson.serviceimpl.ctrl.r r13 = r4.this$0
                long r13 = r13.f()
                java.lang.Long r13 = kotlin.coroutines.jvm.internal.b.d(r13)
                java.lang.String r14 = "get currentFabricIndex fail:"
                java.lang.String r13 = kotlin.jvm.internal.k.l(r14, r13)
                r11.<init>(r12, r13, r0)
                r10.add(r11)
                if (r6 < r2) goto L_0x0135
                goto L_0x0170
            L_0x016a:
                java.lang.NullPointerException r0 = new java.lang.NullPointerException
                r0.<init>(r5)
                throw r0
            L_0x0170:
                com.leedarson.serviceimpl.ctrl.r r0 = r4.this$0
                com.leedarson.serviceimpl.ctrl.r.a(r0)
                kotlin.x r0 = kotlin.x.a
                return r0
            L_0x0178:
                com.leedarson.serviceimpl.ctrl.r r5 = r4.this$0
                org.json.JSONArray r5 = r5.e()
                int r5 = r5.length()
                if (r5 <= 0) goto L_0x01a7
                r11 = r0
            L_0x0185:
                r12 = r11
                int r11 = r11 + r1
                com.leedarson.serviceimpl.ctrl.r r13 = r4.this$0
                org.json.JSONArray r13 = r13.e()
                java.lang.Object r13 = r13.get(r12)
                java.lang.Integer r14 = kotlin.coroutines.jvm.internal.b.c(r8)
                boolean r13 = kotlin.jvm.internal.k.a(r13, r14)
                if (r13 == 0) goto L_0x01a5
                com.leedarson.serviceimpl.ctrl.r r5 = r4.this$0
                org.json.JSONArray r5 = r5.e()
                r5.remove(r12)
                goto L_0x01a7
            L_0x01a5:
                if (r11 < r5) goto L_0x0185
            L_0x01a7:
                com.leedarson.serviceimpl.ctrl.r r5 = r4.this$0
                org.json.JSONArray r5 = r5.e()
                r5.put((int) r8)
                com.leedarson.serviceimpl.ctrl.r r5 = r4.this$0
                org.json.JSONArray r5 = r5.e()
                int r5 = r5.length()
                if (r5 <= 0) goto L_0x025d
                r11 = r8
                r12 = r10
                r16 = r9
                r9 = r5
                r5 = r16
            L_0x01c3:
                r8 = r0
                int r10 = r0 + 1
                com.leedarson.serviceimpl.ctrl.r r0 = r4.this$0
                org.json.JSONArray r0 = r0.e()
                java.lang.Object r0 = r0.get(r8)
                if (r0 != 0) goto L_0x01d5
                r0 = r10
                goto L_0x0256
            L_0x01d5:
                com.leedarson.serviceimpl.ctrl.r r13 = r4.this$0
                r14 = 0
                org.json.JSONArray r15 = r13.e()
                java.lang.Object r15 = r15.get(r8)
                if (r15 != 0) goto L_0x01fc
                org.json.JSONArray r15 = r13.e()
                java.lang.Object r15 = r15.get(r8)
                java.lang.String r1 = "null"
                boolean r1 = kotlin.jvm.internal.k.a(r1, r15)
                if (r1 == 0) goto L_0x01fc
                org.json.JSONArray r1 = r13.e()
                java.lang.Object r1 = r1.get(r8)
                if (r1 == 0) goto L_0x0254
            L_0x01fc:
                org.json.JSONArray r1 = r13.e()     // Catch:{ Exception -> 0x0246 }
                java.lang.Object r1 = r1.get(r8)     // Catch:{ Exception -> 0x0246 }
                java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0246 }
                int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ Exception -> 0x0246 }
                r0 = r1
                r4.L$0 = r12     // Catch:{ Exception -> 0x0246 }
                r4.L$1 = r13     // Catch:{ Exception -> 0x0246 }
                r4.I$0 = r11     // Catch:{ Exception -> 0x0246 }
                r4.I$1 = r10     // Catch:{ Exception -> 0x0246 }
                r4.I$2 = r9     // Catch:{ Exception -> 0x0246 }
                r1 = 3
                r4.label = r1     // Catch:{ Exception -> 0x0246 }
                java.lang.Object r1 = r13.j(r0, r12, r11, r4)     // Catch:{ Exception -> 0x0246 }
                if (r1 != r2) goto L_0x0222
                return r2
            L_0x0222:
                r0 = r13
                r8 = r14
                r16 = r5
                r5 = r1
                r1 = r16
            L_0x0229:
                com.leedarson.serviceimpl.ctrl.r$a r5 = (com.leedarson.serviceimpl.ctrl.r.a) r5     // Catch:{ Exception -> 0x0243 }
                boolean r13 = r5.b()     // Catch:{ Exception -> 0x0243 }
                if (r13 == 0) goto L_0x0239
                java.util.List r0 = r0.g()     // Catch:{ Exception -> 0x0243 }
                r0.add(r5)     // Catch:{ Exception -> 0x0243 }
                goto L_0x0240
            L_0x0239:
                java.util.List r0 = r0.d()     // Catch:{ Exception -> 0x0243 }
                r0.add(r5)     // Catch:{ Exception -> 0x0243 }
            L_0x0240:
                r5 = r1
                r14 = r8
                goto L_0x0254
            L_0x0243:
                r0 = move-exception
                r5 = r1
                goto L_0x0248
            L_0x0246:
                r0 = move-exception
                r8 = r14
            L_0x0248:
                com.leedarson.serviceimpl.k r1 = com.leedarson.serviceimpl.k.a
                java.lang.String r13 = "removeFabrics===》"
                java.lang.String r13 = kotlin.jvm.internal.k.l(r13, r0)
                com.leedarson.serviceimpl.k.e(r1, r13, r7, r6, r7)
                r14 = r8
            L_0x0254:
                r0 = r10
            L_0x0256:
                if (r0 < r9) goto L_0x025a
                r9 = r5
                goto L_0x025d
            L_0x025a:
                r1 = 1
                goto L_0x01c3
            L_0x025d:
                com.leedarson.serviceimpl.ctrl.r r0 = r4.this$0
                com.leedarson.serviceimpl.ctrl.r.a(r0)
                kotlin.x r0 = kotlin.x.a
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.r.c.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public final void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7819, new Class[0], Void.TYPE).isSupported) {
            z1 unused = j.d(s1.c, (g) null, (q0) null, new c(this, (kotlin.coroutines.d<? super c>) null), 3, (Object) null);
        }
    }

    /* compiled from: RemoveFabricCtrl.kt */
    public static final class b implements ChipClusters.IntegerAttributeCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ kotlin.coroutines.d<Integer> a;

        public /* synthetic */ void onSubscriptionEstablished() {
            gs1.a(this);
        }

        b(kotlin.coroutines.d<? super Integer> $it) {
            this.a = $it;
        }

        public void onSuccess(int value) {
            Object[] objArr = {new Integer(value)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7825, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                kotlin.coroutines.d<Integer> dVar = this.a;
                Integer valueOf = Integer.valueOf(value);
                o.a aVar = o.Companion;
                dVar.resumeWith(o.m17constructorimpl(valueOf));
            }
        }

        public void onError(@Nullable Exception error) {
            if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 7826, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, k.l("getCurrentFabricIndex onError:", error), (String) null, 2, (Object) null);
                kotlin.coroutines.d<Integer> dVar = this.a;
                o.a aVar = o.Companion;
                dVar.resumeWith(o.m17constructorimpl(-1));
            }
        }
    }

    @Nullable
    public final Object c(@NotNull ChipClusters.OperationalCredentialsCluster cluster, @NotNull kotlin.coroutines.d<? super Integer> $completion) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{cluster, $completion}, this, changeQuickRedirect2, false, 7820, new Class[]{ChipClusters.OperationalCredentialsCluster.class, kotlin.coroutines.d.class}, Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        i it = new i(kotlin.coroutines.intrinsics.b.c($completion));
        cluster.readCurrentFabricIndexAttribute(new b(it));
        Object a2 = it.a();
        if (a2 == kotlin.coroutines.intrinsics.c.d()) {
            h.c($completion);
        }
        return a2;
    }

    /* compiled from: RemoveFabricCtrl.kt */
    public static final class d implements ChipClusters.OperationalCredentialsCluster.NOCResponseCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ kotlin.coroutines.d<a> a;
        final /* synthetic */ int b;
        final /* synthetic */ int c;
        final /* synthetic */ r d;

        d(kotlin.coroutines.d<? super a> $it, int $fabricIdx, int $currentFabricIndex, r $receiver) {
            this.a = $it;
            this.b = $fabricIdx;
            this.c = $currentFabricIndex;
            this.d = $receiver;
        }

        public void onSuccess(@Nullable Integer statusCode, @Nullable Optional<Integer> optional, @Nullable Optional<String> debugText) {
            if (!PatchProxy.proxy(new Object[]{statusCode, optional, debugText}, this, changeQuickRedirect, false, 7831, new Class[]{Integer.class, Optional.class, Optional.class}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                com.leedarson.serviceimpl.k.b(kVar, "removeFabric onSuccess statusCode:" + statusCode + ",debugText:" + debugText, (String) null, 2, (Object) null);
                if (statusCode != null && statusCode.intValue() == 0) {
                    kotlin.coroutines.d<a> dVar = this.a;
                    a aVar = new a(this.b, "", true);
                    o.a aVar2 = o.Companion;
                    dVar.resumeWith(o.m17constructorimpl(aVar));
                } else {
                    kotlin.coroutines.d<a> dVar2 = this.a;
                    int i = this.b;
                    a aVar3 = new a(i, "statusCode:" + statusCode + ",msg:" + debugText, false);
                    o.a aVar4 = o.Companion;
                    dVar2.resumeWith(o.m17constructorimpl(aVar3));
                }
                if (this.c == this.b) {
                    com.leedarson.serviceimpl.k.h(kVar, "删除了当前fabricIndex，停止该节点心跳检测", (String) null, 2, (Object) null);
                    com.leedarson.serviceimpl.manager.d dVar3 = com.leedarson.serviceimpl.manager.d.a;
                    MtNode b2 = dVar3.b(this.d.f());
                    if (b2 != null) {
                        b2.shutDown();
                    }
                    dVar3.f(this.d.f());
                }
            }
        }

        public void onError(@Nullable Exception error) {
            if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 7832, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, k.l("removeFabric onError:", error), (String) null, 2, (Object) null);
                kotlin.coroutines.d<a> dVar = this.a;
                a aVar = new a(this.b, String.valueOf(error), false);
                o.a aVar2 = o.Companion;
                dVar.resumeWith(o.m17constructorimpl(aVar));
            }
        }
    }

    @Nullable
    public final Object j(int fabricIdx, @NotNull ChipClusters.OperationalCredentialsCluster cluster, int currentFabricIndex, @NotNull kotlin.coroutines.d<? super a> $completion) {
        Object[] objArr = {new Integer(fabricIdx), cluster, new Integer(currentFabricIndex), $completion};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7821, new Class[]{cls, ChipClusters.OperationalCredentialsCluster.class, cls, kotlin.coroutines.d.class}, Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        i it = new i(kotlin.coroutines.intrinsics.b.c($completion));
        cluster.removeFabric(new d(it, fabricIdx, currentFabricIndex, this), kotlin.coroutines.jvm.internal.b.c(fabricIdx));
        Object a2 = it.a();
        if (a2 == kotlin.coroutines.intrinsics.c.d()) {
            h.c($completion);
        }
        return a2;
    }

    private final void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7822, new Class[0], Void.TYPE).isSupported) {
            if (this.g.size() + this.f.size() == this.d.length()) {
                JSONObject respJson = new JSONObject();
                JSONArray successArr = new JSONArray();
                JSONArray failArr = new JSONArray();
                for (a it : this.f) {
                    successArr.put(it.a());
                }
                for (a it2 : this.g) {
                    failArr.put((Object) it2.c());
                }
                respJson.put("removeSuccess", (Object) successArr);
                respJson.put("removeFailed", (Object) failArr);
                if (this.g.size() > 0) {
                    this.e.postJsBridgeCallback(this.a, com.leedarson.base.utils.p.b(BaseResp.ERR_MSG_SEND_FAIL, respJson).toString(), MatterService.ACTION_REMOVE_FABRICS);
                } else {
                    this.e.postJsBridgeCallback(this.a, com.leedarson.base.utils.p.d(respJson).toString(), MatterService.ACTION_REMOVE_FABRICS);
                }
            }
        }
    }

    /* compiled from: RemoveFabricCtrl.kt */
    public static final class a {
        public static ChangeQuickRedirect changeQuickRedirect;
        private final int a;
        @NotNull
        private final String b;
        private final boolean c;

        public a(int fabricIndex, @NotNull String error, boolean success) {
            k.e(error, "error");
            this.a = fabricIndex;
            this.b = error;
            this.c = success;
        }

        public final int a() {
            return this.a;
        }

        public final boolean b() {
            return this.c;
        }

        @NotNull
        public final JSONObject c() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7824, new Class[0], JSONObject.class);
            if (proxy.isSupported) {
                return (JSONObject) proxy.result;
            }
            JSONObject json = new JSONObject();
            json.put("fabricIndex", this.a);
            json.put("error", (Object) this.b);
            return json;
        }
    }
}
