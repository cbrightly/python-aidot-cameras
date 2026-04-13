package com.leedarson.newui.analyse;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.q;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.serviceimpl.ipcservice.IpcServiceImpl;
import com.leedarson.serviceinterface.HttpReportService;
import com.leedarson.serviceinterface.LDSBaseMqttService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.smartcamera.kvswebrtc.f0;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.d0;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.channels.h;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.flow.e;
import kotlinx.coroutines.flow.p;
import kotlinx.coroutines.flow.t;
import kotlinx.coroutines.flow.v;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.p0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z0;
import kotlinx.coroutines.z1;
import meshsdk.model.json.RoutineRule;
import meshsdk.util.MeshConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LiveFailAnalyse.kt */
public final class b implements o0 {
    @NotNull
    public static final a c = new a((DefaultConstructorMarker) null);
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    private final String d;
    private final /* synthetic */ o0 f = p0.b();
    /* access modifiers changed from: private */
    @NotNull
    public final p<a> q;
    @NotNull
    private final t<a> x;

    @NotNull
    public g getCoroutineContext() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3506, new Class[0], g.class);
        return proxy.isSupported ? (g) proxy.result : this.f.getCoroutineContext();
    }

    public b(@NotNull String device) {
        k.e(device, RoutineRule.THEN_TYPE_DEVICE);
        this.d = device;
        p<a> b = v.b(0, 0, (h) null, 7, (Object) null);
        this.q = b;
        this.x = e.a(b);
    }

    public static final /* synthetic */ a a(b $this) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this}, (Object) null, changeQuickRedirect, true, 3527, new Class[]{b.class}, a.class);
        return proxy.isSupported ? (a) proxy.result : $this.m();
    }

    public static final /* synthetic */ boolean b(b $this) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this}, (Object) null, changeQuickRedirect, true, 3519, new Class[]{b.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : $this.q();
    }

    public static final /* synthetic */ void c(b $this, String msg) {
        Class[] clsArr = {b.class, String.class};
        if (!PatchProxy.proxy(new Object[]{$this, msg}, (Object) null, changeQuickRedirect, true, 3522, clsArr, Void.TYPE).isSupported) {
            $this.r(msg);
        }
    }

    public static final /* synthetic */ a d(b $this) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this}, (Object) null, changeQuickRedirect, true, 3526, new Class[]{b.class}, a.class);
        return proxy.isSupported ? (a) proxy.result : $this.s();
    }

    public static final /* synthetic */ boolean f(b $this) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this}, (Object) null, changeQuickRedirect, true, 3521, new Class[]{b.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : $this.x();
    }

    public static final /* synthetic */ q.a g(b $this) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this}, (Object) null, changeQuickRedirect, true, 3524, new Class[]{b.class}, q.a.class);
        return proxy.isSupported ? (q.a) proxy.result : $this.y();
    }

    public static final /* synthetic */ a h(b $this, q.a google, q.a aidot) {
        Class<q.a> cls = q.a.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this, google, aidot}, (Object) null, changeQuickRedirect, true, 3525, new Class[]{b.class, cls, cls}, a.class);
        return proxy.isSupported ? (a) proxy.result : $this.z(google, aidot);
    }

    public static final /* synthetic */ q.a i(b $this) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this}, (Object) null, changeQuickRedirect, true, 3523, new Class[]{b.class}, q.a.class);
        return proxy.isSupported ? (q.a) proxy.result : $this.A();
    }

    public static final /* synthetic */ boolean k(b $this) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this}, (Object) null, changeQuickRedirect, true, 3520, new Class[]{b.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : $this.B();
    }

    @NotNull
    public final t<a> t() {
        return this.x;
    }

    @f(c = "com.leedarson.newui.analyse.LiveFailAnalyse$start$1", f = "LiveFailAnalyse.kt", l = {52, 52, 52, 54, 55, 59, 60, 64, 65}, m = "invokeSuspend")
    /* renamed from: com.leedarson.newui.analyse.b$b  reason: collision with other inner class name */
    /* compiled from: LiveFailAnalyse.kt */
    public static final class C0100b extends l implements kotlin.jvm.functions.p<o0, kotlin.coroutines.d<? super x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;
        final /* synthetic */ b this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0100b(b bVar, kotlin.coroutines.d<? super C0100b> dVar) {
            super(2, dVar);
            this.this$0 = bVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 3529, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
            if (proxy.isSupported) {
                return (kotlin.coroutines.d) proxy.result;
            }
            C0100b bVar = new C0100b(this.this$0, dVar);
            bVar.L$0 = obj;
            return bVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 3531, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 3530, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((C0100b) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v11, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v17, resolved type: kotlinx.coroutines.w0} */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0113, code lost:
            r14 = (com.leedarson.base.utils.q.a) r14;
            r2.L$0 = r6;
            r2.L$1 = r4;
            r2.L$2 = r14;
            r2.label = 2;
            r5 = r5.r(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0122, code lost:
            if (r5 != r1) goto L_0x0125;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0124, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0125, code lost:
            r12 = r4;
            r4 = r14;
            r14 = r5;
            r5 = r12;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0129, code lost:
            r14 = com.leedarson.newui.analyse.b.h(r5, r4, (com.leedarson.base.utils.q.a) r14);
            r2.L$0 = null;
            r2.L$1 = null;
            r2.L$2 = null;
            r2.label = 3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x013c, code lost:
            if (r6.emit(r14, r2) != r1) goto L_0x013f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x013e, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x013f, code lost:
            r14 = r0;
            r0 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x0150, code lost:
            r2 = com.leedarson.newui.analyse.b.e(r0.this$0);
            r3 = com.leedarson.newui.analyse.a.DeviceP2PNoReason;
            r0.label = 5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x015f, code lost:
            if (r2.emit(r3, r0) != r1) goto L_0x01bb;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x0161, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x0177, code lost:
            r2 = com.leedarson.newui.analyse.b.e(r0.this$0);
            r3 = com.leedarson.newui.analyse.b.d(r0.this$0);
            r0.label = 7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x018a, code lost:
            if (r2.emit(r3, r0) != r1) goto L_0x01bb;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:0x018c, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x01a3, code lost:
            r2 = com.leedarson.newui.analyse.b.e(r0.this$0);
            r3 = com.leedarson.newui.analyse.b.a(r0.this$0);
            r0.label = 9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x01b7, code lost:
            if (r2.emit(r3, r0) != r1) goto L_0x01bb;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x01b9, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:57:0x01bd, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Multi-variable type inference failed */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r14) {
            /*
                r13 = this;
                r0 = 1
                java.lang.Object[] r1 = new java.lang.Object[r0]
                r2 = 0
                r1[r2] = r14
                com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
                java.lang.Class[] r6 = new java.lang.Class[r0]
                java.lang.Class<java.lang.Object> r4 = java.lang.Object.class
                r6[r2] = r4
                java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
                r4 = 0
                r5 = 3528(0xdc8, float:4.944E-42)
                r2 = r13
                com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
                boolean r2 = r1.isSupported
                if (r2 == 0) goto L_0x001f
                java.lang.Object r14 = r1.result
                return r14
            L_0x001f:
                java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
                int r2 = r13.label
                r3 = 0
                switch(r2) {
                    case 0: goto L_0x008a;
                    case 1: goto L_0x0072;
                    case 2: goto L_0x005b;
                    case 3: goto L_0x0055;
                    case 4: goto L_0x004f;
                    case 5: goto L_0x0049;
                    case 6: goto L_0x0043;
                    case 7: goto L_0x003d;
                    case 8: goto L_0x0037;
                    case 9: goto L_0x0031;
                    default: goto L_0x0029;
                }
            L_0x0029:
                java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r14.<init>(r0)
                throw r14
            L_0x0031:
                r0 = r13
                kotlin.p.b(r14)
                goto L_0x01ba
            L_0x0037:
                r0 = r13
                kotlin.p.b(r14)
                goto L_0x01a3
            L_0x003d:
                r0 = r13
                kotlin.p.b(r14)
                goto L_0x018d
            L_0x0043:
                r0 = r13
                kotlin.p.b(r14)
                goto L_0x0177
            L_0x0049:
                r0 = r13
                kotlin.p.b(r14)
                goto L_0x0162
            L_0x004f:
                r0 = r13
                kotlin.p.b(r14)
                goto L_0x0150
            L_0x0055:
                r0 = r13
                kotlin.p.b(r14)
                goto L_0x0141
            L_0x005b:
                r0 = r13
                java.lang.Object r2 = r0.L$2
                com.leedarson.base.utils.q$a r2 = (com.leedarson.base.utils.q.a) r2
                java.lang.Object r4 = r0.L$1
                com.leedarson.newui.analyse.b r4 = (com.leedarson.newui.analyse.b) r4
                java.lang.Object r5 = r0.L$0
                kotlinx.coroutines.flow.p r5 = (kotlinx.coroutines.flow.p) r5
                kotlin.p.b(r14)
                r6 = r5
                r5 = r4
                r4 = r2
                r2 = r0
                r0 = r14
                goto L_0x0129
            L_0x0072:
                r0 = r13
                r2 = r3
                java.lang.Object r4 = r0.L$2
                com.leedarson.newui.analyse.b r4 = (com.leedarson.newui.analyse.b) r4
                java.lang.Object r5 = r0.L$1
                kotlinx.coroutines.flow.p r5 = (kotlinx.coroutines.flow.p) r5
                java.lang.Object r6 = r0.L$0
                r2 = r6
                kotlinx.coroutines.w0 r2 = (kotlinx.coroutines.w0) r2
                kotlin.p.b(r14)
                r6 = r5
                r5 = r2
                r2 = r0
                r0 = r14
                goto L_0x0113
            L_0x008a:
                kotlin.p.b(r14)
                r2 = r13
                java.lang.Object r4 = r2.L$0
                kotlinx.coroutines.o0 r4 = (kotlinx.coroutines.o0) r4
                com.leedarson.newui.analyse.b r5 = r2.this$0
                boolean r5 = com.leedarson.newui.analyse.b.b(r5)
                if (r5 == 0) goto L_0x018e
                r6 = 0
                r7 = 0
                com.leedarson.newui.analyse.b$b$a r8 = new com.leedarson.newui.analyse.b$b$a
                com.leedarson.newui.analyse.b r5 = r2.this$0
                r8.<init>(r5, r3)
                r9 = 3
                r10 = 0
                r5 = r4
                kotlinx.coroutines.w0 unused = kotlinx.coroutines.j.b(r5, r6, r7, r8, r9, r10)
                com.leedarson.newui.analyse.b r5 = r2.this$0
                boolean r5 = com.leedarson.newui.analyse.b.k(r5)
                if (r5 == 0) goto L_0x0163
                com.leedarson.newui.analyse.b r5 = r2.this$0
                boolean r5 = com.leedarson.newui.analyse.b.f(r5)
                if (r5 == 0) goto L_0x0143
                com.leedarson.newui.analyse.b r5 = r2.this$0
                java.lang.String r6 = "[结果3]p2pOrLivePlaySuccess: fail"
                com.leedarson.newui.analyse.b.c(r5, r6)
                r6 = 0
                r7 = 0
                com.leedarson.newui.analyse.b$b$b r8 = new com.leedarson.newui.analyse.b$b$b
                com.leedarson.newui.analyse.b r5 = r2.this$0
                r8.<init>(r5, r3)
                r9 = 3
                r10 = 0
                r5 = r4
                kotlinx.coroutines.w0 unused = kotlinx.coroutines.j.b(r5, r6, r7, r8, r9, r10)
                com.leedarson.newui.analyse.b r5 = r2.this$0
                java.lang.Thread r6 = java.lang.Thread.currentThread()
                java.lang.String r7 = "currentThread: "
                java.lang.String r6 = kotlin.jvm.internal.k.l(r7, r6)
                com.leedarson.newui.analyse.b.c(r5, r6)
                r6 = 0
                r7 = 0
                com.leedarson.newui.analyse.b$b$d r8 = new com.leedarson.newui.analyse.b$b$d
                com.leedarson.newui.analyse.b r5 = r2.this$0
                r8.<init>(r5, r3)
                r5 = r4
                kotlinx.coroutines.w0 r11 = kotlinx.coroutines.j.b(r5, r6, r7, r8, r9, r10)
                com.leedarson.newui.analyse.b$b$c r8 = new com.leedarson.newui.analyse.b$b$c
                com.leedarson.newui.analyse.b r5 = r2.this$0
                r8.<init>(r5, r3)
                r5 = r4
                kotlinx.coroutines.w0 r5 = kotlinx.coroutines.j.b(r5, r6, r7, r8, r9, r10)
                com.leedarson.newui.analyse.b r6 = r2.this$0
                kotlinx.coroutines.flow.p r6 = r6.q
                com.leedarson.newui.analyse.b r7 = r2.this$0
                r2.L$0 = r5
                r2.L$1 = r6
                r2.L$2 = r7
                r2.label = r0
                java.lang.Object r0 = r11.r(r2)
                if (r0 != r1) goto L_0x010f
                return r1
            L_0x010f:
                r4 = r7
                r12 = r0
                r0 = r14
                r14 = r12
            L_0x0113:
                com.leedarson.base.utils.q$a r14 = (com.leedarson.base.utils.q.a) r14
                r2.L$0 = r6
                r2.L$1 = r4
                r2.L$2 = r14
                r7 = 2
                r2.label = r7
                java.lang.Object r5 = r5.r(r2)
                if (r5 != r1) goto L_0x0125
                return r1
            L_0x0125:
                r12 = r4
                r4 = r14
                r14 = r5
                r5 = r12
            L_0x0129:
                com.leedarson.base.utils.q$a r14 = (com.leedarson.base.utils.q.a) r14
                com.leedarson.newui.analyse.a r14 = com.leedarson.newui.analyse.b.h(r5, r4, r14)
                r2.L$0 = r3
                r2.L$1 = r3
                r2.L$2 = r3
                r3 = 3
                r2.label = r3
                java.lang.Object r14 = r6.emit(r14, r2)
                if (r14 != r1) goto L_0x013f
                return r1
            L_0x013f:
                r14 = r0
                r0 = r2
            L_0x0141:
                goto L_0x01bb
            L_0x0143:
                r3 = 500(0x1f4, double:2.47E-321)
                r0 = 4
                r2.label = r0
                java.lang.Object r0 = kotlinx.coroutines.z0.a(r3, r2)
                if (r0 != r1) goto L_0x014f
                return r1
            L_0x014f:
                r0 = r2
            L_0x0150:
                com.leedarson.newui.analyse.b r2 = r0.this$0
                kotlinx.coroutines.flow.p r2 = r2.q
                com.leedarson.newui.analyse.a r3 = com.leedarson.newui.analyse.a.DeviceP2PNoReason
                r4 = 5
                r0.label = r4
                java.lang.Object r2 = r2.emit(r3, r0)
                if (r2 != r1) goto L_0x0162
                return r1
            L_0x0162:
                goto L_0x01bb
            L_0x0163:
                com.leedarson.newui.analyse.b r0 = r2.this$0
                java.lang.String r3 = "[结果2]receiveDeviceSuccess: fail"
                com.leedarson.newui.analyse.b.c(r0, r3)
                r3 = 1500(0x5dc, double:7.41E-321)
                r0 = 6
                r2.label = r0
                java.lang.Object r0 = kotlinx.coroutines.z0.a(r3, r2)
                if (r0 != r1) goto L_0x0176
                return r1
            L_0x0176:
                r0 = r2
            L_0x0177:
                com.leedarson.newui.analyse.b r2 = r0.this$0
                kotlinx.coroutines.flow.p r2 = r2.q
                com.leedarson.newui.analyse.b r3 = r0.this$0
                com.leedarson.newui.analyse.a r3 = com.leedarson.newui.analyse.b.d(r3)
                r4 = 7
                r0.label = r4
                java.lang.Object r2 = r2.emit(r3, r0)
                if (r2 != r1) goto L_0x018d
                return r1
            L_0x018d:
                goto L_0x01bb
            L_0x018e:
                com.leedarson.newui.analyse.b r0 = r2.this$0
                java.lang.String r3 = "[结果1]connectServerSuccess: fail"
                com.leedarson.newui.analyse.b.c(r0, r3)
                r3 = 1000(0x3e8, double:4.94E-321)
                r0 = 8
                r2.label = r0
                java.lang.Object r0 = kotlinx.coroutines.z0.a(r3, r2)
                if (r0 != r1) goto L_0x01a2
                return r1
            L_0x01a2:
                r0 = r2
            L_0x01a3:
                com.leedarson.newui.analyse.b r2 = r0.this$0
                kotlinx.coroutines.flow.p r2 = r2.q
                com.leedarson.newui.analyse.b r3 = r0.this$0
                com.leedarson.newui.analyse.a r3 = com.leedarson.newui.analyse.b.a(r3)
                r4 = 9
                r0.label = r4
                java.lang.Object r2 = r2.emit(r3, r0)
                if (r2 != r1) goto L_0x01ba
                return r1
            L_0x01ba:
            L_0x01bb:
                kotlin.x r1 = kotlin.x.a
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.analyse.b.C0100b.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        @f(c = "com.leedarson.newui.analyse.LiveFailAnalyse$start$1$1", f = "LiveFailAnalyse.kt", l = {35, 36}, m = "invokeSuspend")
        /* renamed from: com.leedarson.newui.analyse.b$b$a */
        /* compiled from: LiveFailAnalyse.kt */
        public static final class a extends l implements kotlin.jvm.functions.p<o0, kotlin.coroutines.d<? super x>, Object> {
            public static ChangeQuickRedirect changeQuickRedirect;
            int label;
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(b bVar, kotlin.coroutines.d<? super a> dVar) {
                super(2, dVar);
                this.this$0 = bVar;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect, false, 3533, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
                return proxy.isSupported ? (kotlin.coroutines.d) proxy.result : new a(this.this$0, dVar);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                Class<Object> cls = Object.class;
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 3535, new Class[]{cls, cls}, Object.class);
                return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
            }

            @Nullable
            public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 3534, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
                return proxy.isSupported ? proxy.result : ((a) create(o0Var, dVar)).invokeSuspend(x.a);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                a aVar;
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 3532, new Class[]{Object.class}, Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                Object d = kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        kotlin.p.b($result);
                        this.label = 1;
                        if (z0.a(500, this) != d) {
                            aVar = this;
                            break;
                        } else {
                            return d;
                        }
                    case 1:
                        aVar = this;
                        kotlin.p.b($result);
                        break;
                    case 2:
                        kotlin.p.b($result);
                        break;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                p e = aVar.this$0.q;
                a aVar2 = a.AppNetworkNoProblem;
                aVar.label = 2;
                if (e.emit(aVar2, aVar) == d) {
                    return d;
                }
                return x.a;
            }
        }

        @f(c = "com.leedarson.newui.analyse.LiveFailAnalyse$start$1$2", f = "LiveFailAnalyse.kt", l = {42, 43}, m = "invokeSuspend")
        /* renamed from: com.leedarson.newui.analyse.b$b$b  reason: collision with other inner class name */
        /* compiled from: LiveFailAnalyse.kt */
        public static final class C0101b extends l implements kotlin.jvm.functions.p<o0, kotlin.coroutines.d<? super x>, Object> {
            public static ChangeQuickRedirect changeQuickRedirect;
            int label;
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0101b(b bVar, kotlin.coroutines.d<? super C0101b> dVar) {
                super(2, dVar);
                this.this$0 = bVar;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect, false, 3537, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
                return proxy.isSupported ? (kotlin.coroutines.d) proxy.result : new C0101b(this.this$0, dVar);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                Class<Object> cls = Object.class;
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 3539, new Class[]{cls, cls}, Object.class);
                return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
            }

            @Nullable
            public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 3538, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
                return proxy.isSupported ? proxy.result : ((C0101b) create(o0Var, dVar)).invokeSuspend(x.a);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                C0101b bVar;
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 3536, new Class[]{Object.class}, Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                Object d = kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        kotlin.p.b($result);
                        this.label = 1;
                        if (z0.a(500, this) != d) {
                            bVar = this;
                            break;
                        } else {
                            return d;
                        }
                    case 1:
                        bVar = this;
                        kotlin.p.b($result);
                        break;
                    case 2:
                        kotlin.p.b($result);
                        break;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                p e = bVar.this$0.q;
                a aVar = a.DeviceNetworkNoProblem;
                bVar.label = 2;
                if (e.emit(aVar, bVar) == d) {
                    return d;
                }
                return x.a;
            }
        }

        @f(c = "com.leedarson.newui.analyse.LiveFailAnalyse$start$1$pingGoogle$1", f = "LiveFailAnalyse.kt", l = {}, m = "invokeSuspend")
        /* renamed from: com.leedarson.newui.analyse.b$b$d */
        /* compiled from: LiveFailAnalyse.kt */
        public static final class d extends l implements kotlin.jvm.functions.p<o0, kotlin.coroutines.d<? super q.a>, Object> {
            public static ChangeQuickRedirect changeQuickRedirect;
            int label;
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            d(b bVar, kotlin.coroutines.d<? super d> dVar) {
                super(2, dVar);
                this.this$0 = bVar;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect, false, 3545, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
                return proxy.isSupported ? (kotlin.coroutines.d) proxy.result : new d(this.this$0, dVar);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                Class<Object> cls = Object.class;
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 3547, new Class[]{cls, cls}, Object.class);
                return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super q.a>) (kotlin.coroutines.d) obj2);
            }

            @Nullable
            public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super q.a> dVar) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 3546, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
                return proxy.isSupported ? proxy.result : ((d) create(o0Var, dVar)).invokeSuspend(x.a);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 3544, new Class[]{Object.class}, Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        kotlin.p.b(obj);
                        return b.i(this.this$0);
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }

        @f(c = "com.leedarson.newui.analyse.LiveFailAnalyse$start$1$pingAidot$1", f = "LiveFailAnalyse.kt", l = {}, m = "invokeSuspend")
        /* renamed from: com.leedarson.newui.analyse.b$b$c */
        /* compiled from: LiveFailAnalyse.kt */
        public static final class c extends l implements kotlin.jvm.functions.p<o0, kotlin.coroutines.d<? super q.a>, Object> {
            public static ChangeQuickRedirect changeQuickRedirect;
            int label;
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            c(b bVar, kotlin.coroutines.d<? super c> dVar) {
                super(2, dVar);
                this.this$0 = bVar;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect, false, 3541, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
                return proxy.isSupported ? (kotlin.coroutines.d) proxy.result : new c(this.this$0, dVar);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                Class<Object> cls = Object.class;
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 3543, new Class[]{cls, cls}, Object.class);
                return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super q.a>) (kotlin.coroutines.d) obj2);
            }

            @Nullable
            public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super q.a> dVar) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 3542, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
                return proxy.isSupported ? proxy.result : ((c) create(o0Var, dVar)).invokeSuspend(x.a);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 3540, new Class[]{Object.class}, Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        kotlin.p.b(obj);
                        return b.g(this.this$0);
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }
    }

    public final void C() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3507, new Class[0], Void.TYPE).isSupported) {
            z1 unused = j.d(this, d1.b(), (q0) null, new C0100b(this, (d<? super C0100b>) null), 2, (Object) null);
        }
    }

    private final boolean q() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3508, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        LDSBaseMqttService lDSBaseMqttService = (LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class);
        Integer mqttService = lDSBaseMqttService == null ? null : Integer.valueOf(lDSBaseMqttService.getMqttConnectStatue());
        com.leedarson.smartcamera.reporters.b $this$connectServerSuccess_u24lambda_u2d0 = com.leedarson.smartcamera.reporters.a.a.a().c(this.d);
        if ($this$connectServerSuccess_u24lambda_u2d0 == null) {
            r("[判断1]mqttService: " + mqttService + " reporter中无对应的设备");
            if (mqttService != null && mqttService.intValue() == 1) {
                return true;
            }
            return false;
        }
        r("[判断1]mqttService: " + mqttService + " getIceConfigSuccess：" + $this$connectServerSuccess_u24lambda_u2d0.b() + " sendOfferSuccess:" + $this$connectServerSuccess_u24lambda_u2d0.e() + " getCandidateSuccess:" + $this$connectServerSuccess_u24lambda_u2d0.a());
        if (mqttService != null && mqttService.intValue() == 1 && $this$connectServerSuccess_u24lambda_u2d0.b() && $this$connectServerSuccess_u24lambda_u2d0.e() && $this$connectServerSuccess_u24lambda_u2d0.a()) {
            return true;
        }
        return false;
    }

    private final boolean B() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3509, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        com.leedarson.smartcamera.reporters.b $this$receiveDeviceSuccess_u24lambda_u2d1 = com.leedarson.smartcamera.reporters.a.a.a().c(this.d);
        if ($this$receiveDeviceSuccess_u24lambda_u2d1 == null) {
            return false;
        }
        r("[判断2]receiveOfferSuccess:" + $this$receiveDeviceSuccess_u24lambda_u2d1.c() + " receiveCandidateSuccess:" + $this$receiveDeviceSuccess_u24lambda_u2d1.d());
        if (!$this$receiveDeviceSuccess_u24lambda_u2d1.c() || !$this$receiveDeviceSuccess_u24lambda_u2d1.d()) {
            return false;
        }
        return true;
    }

    private final boolean x() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3510, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        f0 kvsWebRTCChannel = com.leedarson.manager.a.i().j(this.d);
        r("[判断3]isPeerConnectionConnect:" + kvsWebRTCChannel.t1() + " isDataChannelConnected:" + kvsWebRTCChannel.r1());
        if (!kvsWebRTCChannel.t1() || !kvsWebRTCChannel.r1()) {
            return false;
        }
        return true;
    }

    private final boolean n() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3511, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        Object systemService = BaseApplication.b().getSystemService("connectivity");
        if (systemService != null) {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) systemService).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return false;
            }
            return activeNetworkInfo.isConnected();
        }
        throw new NullPointerException("null cannot be cast to non-null type android.net.ConnectivityManager");
    }

    private final a m() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3512, new Class[0], a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        a aVar = a.AppNetworkUnconnected;
        if (!n()) {
            return a.AppNetworkUnconnected;
        }
        String $this$appNetwork_u24lambda_u2d3 = ((HttpReportService) com.alibaba.android.arouter.launcher.a.c().g(HttpReportService.class)).getNetWorkInfoDetail();
        if ($this$appNetwork_u24lambda_u2d3 == null) {
            return aVar;
        }
        r(k.l("appNetwork ", $this$appNetwork_u24lambda_u2d3));
        if (kotlin.text.x.S($this$appNetwork_u24lambda_u2d3, "enableNetworkFlag=true", false, 2, (Object) null)) {
            return a.AppNetworkNoReason;
        }
        if (kotlin.text.x.S($this$appNetwork_u24lambda_u2d3, "未检测", false, 2, (Object) null)) {
            return a.AppNetworkNoReason;
        }
        return a.AppNetworkUnavailable;
    }

    private final a s() {
        int rssi;
        boolean z = false;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3513, new Class[0], a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        a aVar = a.DeviceNetworkNoReason;
        IpcDeviceBean $this$deviceStatus_u24lambda_u2d5 = IpcServiceImpl.o(this.d);
        if ($this$deviceStatus_u24lambda_u2d5 == null) {
            return aVar;
        }
        r("deviceStatus: " + $this$deviceStatus_u24lambda_u2d5.online + " rssi " + $this$deviceStatus_u24lambda_u2d5.props.networkRssi);
        boolean networkRssiNormal = false;
        try {
            String str = $this$deviceStatus_u24lambda_u2d5.props.networkRssi;
            k.d(str, "props.networkRssi");
            int $this$deviceStatus_u24lambda_u2d5_u24lambda_u2d4 = Integer.parseInt(str);
            if ($this$deviceStatus_u24lambda_u2d5_u24lambda_u2d4 >= 0) {
                rssi = $this$deviceStatus_u24lambda_u2d5_u24lambda_u2d4 - 100;
            } else {
                rssi = $this$deviceStatus_u24lambda_u2d5_u24lambda_u2d4;
            }
            if (rssi > -70) {
                z = true;
            }
            networkRssiNormal = z;
        } catch (Exception e) {
            r(k.l("networkRssi转换失败", $this$deviceStatus_u24lambda_u2d5.props.networkRssi));
        }
        if (!$this$deviceStatus_u24lambda_u2d5.online.booleanValue()) {
            aVar = a.DeviceOffline;
        }
        if (!networkRssiNormal) {
            aVar = a.DeviceRssiLow;
        }
        Boolean bool = $this$deviceStatus_u24lambda_u2d5.online;
        k.d(bool, MeshConstants.AC_STATE_LOGIN_SUCCESS);
        if (!bool.booleanValue() || !networkRssiNormal) {
            return aVar;
        }
        return a.DeviceNetworkNoReason;
    }

    private final a z(q.a google, q.a aidot) {
        Class<q.a> cls = q.a.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{google, aidot}, this, changeQuickRedirect2, false, 3514, new Class[]{cls, cls}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        r("pingAnalyse : google : " + google + " + aidot : " + aidot);
        if (u(google) && u(aidot)) {
            return a.PingError;
        }
        if (u(google)) {
            if (v(aidot)) {
                return a.PingError;
            }
            return a.PingNoProblem;
        } else if (u(aidot)) {
            if (v(google)) {
                return a.PingError;
            }
            return a.PingNoProblem;
        } else if (w(aidot, google)) {
            return a.PingError;
        } else {
            return a.PingNoProblem;
        }
    }

    private final boolean u(q.a result) {
        float f2 = result.b;
        if (f2 == 100.0f) {
            return true;
        }
        return ((f2 > -1.0f ? 1 : (f2 == -1.0f ? 0 : -1)) == 0) || result.a == -1;
    }

    private final boolean v(q.a result) {
        return result.b >= 20.0f || result.a > 200;
    }

    private final boolean w(q.a result1, q.a result2) {
        return (result1.b >= 20.0f && result2.b >= 20.0f) || (result1.a > 200 && result2.a > 200);
    }

    private final q.a y() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3515, new Class[0], q.a.class);
        if (proxy.isSupported) {
            return (q.a) proxy.result;
        }
        String prefString = SharePreferenceUtils.getPrefString(BaseApplication.b(), "regionCode", "us");
        k.d(prefString, "getPrefString(BaseApplication.getApp(), \"regionCode\", \"us\")");
        String regionCode = prefString.toUpperCase(Locale.ROOT);
        k.d(regionCode, "this as java.lang.String).toUpperCase(Locale.ROOT)");
        d0 d0Var = d0.a;
        String aidotUrl = String.format("http://test-ping-%s.arnoo.com", Arrays.copyOf(new Object[]{regionCode}, 1));
        k.d(aidotUrl, "format(format, *args)");
        r(aidotUrl);
        r(k.l("currentThread: ", Thread.currentThread()));
        q.a f2 = q.f(aidotUrl, 2, 10, 100);
        k.d(f2, "getRTTAndLostRate(aidotUrl,LdsPingUtil.TYPE_AVG,10,100)");
        return f2;
    }

    private final q.a A() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3516, new Class[0], q.a.class);
        if (proxy.isSupported) {
            return (q.a) proxy.result;
        }
        r(k.l("currentThread: ", Thread.currentThread()));
        q.a f2 = q.f("https://www.google.com", 2, 10, 100);
        k.d(f2, "getRTTAndLostRate(googleUrl,LdsPingUtil.TYPE_AVG,10,100)");
        return f2;
    }

    public final void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3517, new Class[0], Void.TYPE).isSupported) {
            r("cancelCoroutineScope");
            p0.d(this, (CancellationException) null, 1, (Object) null);
        }
    }

    private final void r(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 3518, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("LiveFailAnalyse").a(msg, new Object[0]);
        }
    }

    /* compiled from: LiveFailAnalyse.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
