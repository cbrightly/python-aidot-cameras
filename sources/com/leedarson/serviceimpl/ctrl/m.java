package com.leedarson.serviceimpl.ctrl;

import android.content.Context;
import chip.devicecontroller.ChipClusters;
import chip.devicecontroller.gs1;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import kotlin.collections.q;
import kotlin.jvm.functions.p;
import kotlin.o;
import kotlin.x;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.s1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MTGroupCtrl.kt */
public final class m {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    @NotNull
    public final com.leedarson.serviceimpl.manager.c a;
    private final int b = -1;
    private final int c = 1;
    private final int d;

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTGroupCtrl", f = "MTGroupCtrl.kt", l = {348, 354}, m = "addGroup")
    /* compiled from: MTGroupCtrl.kt */
    public static final class a extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        int I$0;
        long J$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ m this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(m mVar, kotlin.coroutines.d<? super a> dVar) {
            super(dVar);
            this.this$0 = mVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7693, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return m.a(this.this$0, 0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTGroupCtrl", f = "MTGroupCtrl.kt", l = {258, 268}, m = "getFabricIndex")
    /* compiled from: MTGroupCtrl.kt */
    public static final class e extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        long J$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ m this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(m mVar, kotlin.coroutines.d<? super e> dVar) {
            super(dVar);
            this.this$0 = mVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7706, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.j(0, (com.leedarson.serviceimpl.listener.b) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTGroupCtrl", f = "MTGroupCtrl.kt", l = {127, 135}, m = "writeACLAttribute")
    /* compiled from: MTGroupCtrl.kt */
    public static final class j extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        int I$0;
        int I$1;
        long J$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ m this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        j(m mVar, kotlin.coroutines.d<? super j> dVar) {
            super(dVar);
            this.this$0 = mVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7727, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.n(0, 0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTGroupCtrl", f = "MTGroupCtrl.kt", l = {314, 319}, m = "writeGroupKeyMapAttribute")
    /* compiled from: MTGroupCtrl.kt */
    public static final class l extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        int I$0;
        int I$1;
        int I$2;
        long J$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ m this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        l(m mVar, kotlin.coroutines.d<? super l> dVar) {
            super(dVar);
            this.this$0 = mVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7730, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return m.c(this.this$0, 0, 0, 0, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTGroupCtrl", f = "MTGroupCtrl.kt", l = {85, 94}, m = "writeKID")
    /* compiled from: MTGroupCtrl.kt */
    public static final class n extends kotlin.coroutines.jvm.internal.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        int I$0;
        long J$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ m this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        n(m mVar, kotlin.coroutines.d<? super n> dVar) {
            super(dVar);
            this.this$0 = mVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7733, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.q(0, 0, this);
        }
    }

    public m(@NotNull Context context) {
        kotlin.jvm.internal.k.e(context, "context");
        this.a = new com.leedarson.serviceimpl.manager.c(context);
    }

    public static final /* synthetic */ Object a(m $this, long nodeId, int groupId, kotlin.coroutines.d $completion) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this, new Long(nodeId), new Integer(groupId), $completion}, (Object) null, changeQuickRedirect, true, 7692, new Class[]{m.class, Long.TYPE, Integer.TYPE, kotlin.coroutines.d.class}, Object.class);
        return proxy.isSupported ? proxy.result : $this.d(nodeId, groupId, $completion);
    }

    public static final /* synthetic */ Object c(m $this, long nodeId, int groupId, int kid, int fabricIndex, kotlin.coroutines.d $completion) {
        Object[] objArr = {$this, new Long(nodeId), new Integer(groupId), new Integer(kid), new Integer(fabricIndex), $completion};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7691, new Class[]{m.class, Long.TYPE, cls, cls, cls, kotlin.coroutines.d.class}, Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        return $this.o(nodeId, groupId, kid, fabricIndex, $completion);
    }

    public final int g() {
        return this.b;
    }

    public final int h() {
        return this.c;
    }

    public final int i() {
        return this.d;
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTGroupCtrl$addGroup$job$1", f = "MTGroupCtrl.kt", l = {31, 39, 48, 58, 67}, m = "invokeSuspend")
    /* compiled from: MTGroupCtrl.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.serviceimpl.listener.b $callback;
        final /* synthetic */ int $groupId;
        final /* synthetic */ long $nodeId;
        int I$0;
        int label;
        final /* synthetic */ m this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(m mVar, long j, com.leedarson.serviceimpl.listener.b bVar, int i, kotlin.coroutines.d<? super c> dVar) {
            super(2, dVar);
            this.this$0 = mVar;
            this.$nodeId = j;
            this.$callback = bVar;
            this.$groupId = i;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 7697, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
            if (proxy.isSupported) {
                return (kotlin.coroutines.d) proxy.result;
            }
            return new c(this.this$0, this.$nodeId, this.$callback, this.$groupId, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 7699, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 7698, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((c) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0085, code lost:
            r3 = ((java.lang.Number) r3).intValue();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x008b, code lost:
            if (r3 != -1) goto L_0x009f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x008d, code lost:
            r1 = r0.$callback;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x008f, code lost:
            if (r1 != null) goto L_0x0092;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0092, code lost:
            r1.onFail(-1, new java.lang.IllegalStateException("find fabricIndex failed !"));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x009e, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x009f, code lost:
            r9 = r0.this$0;
            r10 = r0.$nodeId;
            r12 = r0.$groupId;
            r0.I$0 = r3;
            r0.label = 2;
            r6 = r9.n(r10, r12, r3, r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x00b0, code lost:
            if (r6 != r1) goto L_0x00b3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x00b2, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x00b3, code lost:
            r16 = r6;
            r6 = r3;
            r3 = r16;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x00c4, code lost:
            if (((java.lang.Number) r3).intValue() != r0.this$0.g()) goto L_0x00de;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x00c6, code lost:
            r1 = r0.$callback;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00c8, code lost:
            if (r1 != null) goto L_0x00cb;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x00cb, code lost:
            r1.onFail(r0.this$0.g(), new java.lang.IllegalStateException("write ACL Attribute failed !"));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x00dd, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x00de, code lost:
            r7 = r0.this$0;
            r9 = r0.$nodeId;
            r11 = r0.$groupId;
            r0.I$0 = r6;
            r0.label = 3;
            r3 = r7.q(r9, r11, r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x00ed, code lost:
            if (r3 != r1) goto L_0x00f0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x00ef, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x00f0, code lost:
            r14 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00f1, code lost:
            r3 = ((java.lang.Number) r3).intValue();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x00fd, code lost:
            if (r3 != r0.this$0.g()) goto L_0x0117;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x00ff, code lost:
            r1 = r0.$callback;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x0101, code lost:
            if (r1 != null) goto L_0x0104;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x0104, code lost:
            r1.onFail(r0.this$0.g(), new java.lang.IllegalStateException("write KID failed !"));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x0116, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x0117, code lost:
            r9 = r0.this$0;
            r10 = r0.$nodeId;
            r12 = r0.$groupId;
            r0.label = 4;
            r3 = com.leedarson.serviceimpl.ctrl.m.c(r9, r10, r12, r3, r14, r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x0126, code lost:
            if (r3 != r1) goto L_0x0129;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:0x0128, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x0129, code lost:
            r16 = r5;
            r5 = r3;
            r3 = r16;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x013a, code lost:
            if (((java.lang.Number) r5).intValue() != r0.this$0.g()) goto L_0x014e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x013c, code lost:
            r1 = r0.$callback;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x013e, code lost:
            if (r1 != null) goto L_0x0141;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x0141, code lost:
            r1.onFail(-1, new java.lang.IllegalStateException("write GroupKeyMapAttribute failed !"));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:56:0x014d, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:57:0x014e, code lost:
            r6 = r0.this$0;
            r9 = r0.$nodeId;
            r7 = r0.$groupId;
            r0.label = 5;
            r5 = com.leedarson.serviceimpl.ctrl.m.a(r6, r9, r7, r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:0x015b, code lost:
            if (r5 != r1) goto L_0x015e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:59:0x015d, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:60:0x015e, code lost:
            r1 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:62:0x016b, code lost:
            if (((java.lang.Number) r1).intValue() != r0.this$0.g()) goto L_0x0188;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:63:0x016d, code lost:
            r5 = r0.$callback;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:64:0x016f, code lost:
            if (r5 != null) goto L_0x0172;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:65:0x0172, code lost:
            r5.onFail(-1, new java.lang.IllegalStateException(kotlin.jvm.internal.k.l("could not find GroupCluster by deviceId:", kotlin.coroutines.jvm.internal.b.d(r0.$nodeId))));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:66:0x0188, code lost:
            r4 = r0.$callback;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:67:0x018a, code lost:
            if (r4 != null) goto L_0x018d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:68:0x018d, code lost:
            r4.onSuccess(r0.$nodeId, kotlin.coroutines.jvm.internal.b.c(0));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:70:0x0198, code lost:
            return kotlin.x.a;
         */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r18) {
            /*
                r17 = this;
                r0 = 1
                java.lang.Object[] r1 = new java.lang.Object[r0]
                r8 = 0
                r1[r8] = r18
                com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
                java.lang.Class[] r6 = new java.lang.Class[r0]
                java.lang.Class<java.lang.Object> r2 = java.lang.Object.class
                r6[r8] = r2
                java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
                r4 = 0
                r5 = 7696(0x1e10, float:1.0784E-41)
                r2 = r17
                com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
                boolean r2 = r1.isSupported
                if (r2 == 0) goto L_0x0020
                java.lang.Object r0 = r1.result
                return r0
            L_0x0020:
                java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
                r2 = r17
                int r3 = r2.label
                r4 = -1
                switch(r3) {
                    case 0: goto L_0x006a;
                    case 1: goto L_0x0061;
                    case 2: goto L_0x0055;
                    case 3: goto L_0x0048;
                    case 4: goto L_0x003e;
                    case 5: goto L_0x0034;
                    default: goto L_0x002c;
                }
            L_0x002c:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0034:
                r0 = r17
                r1 = r18
                kotlin.p.b(r1)
                r3 = r1
                goto L_0x015f
            L_0x003e:
                r0 = r17
                r3 = r18
                kotlin.p.b(r3)
                r5 = r3
                goto L_0x012e
            L_0x0048:
                r0 = r17
                r3 = r18
                int r5 = r0.I$0
                kotlin.p.b(r3)
                r14 = r5
                r5 = r3
                goto L_0x00f1
            L_0x0055:
                r0 = r17
                r3 = r18
                int r5 = r0.I$0
                kotlin.p.b(r3)
                r6 = r5
                r5 = r3
                goto L_0x00b8
            L_0x0061:
                r0 = r17
                r3 = r18
                kotlin.p.b(r3)
                r5 = r3
                goto L_0x0085
            L_0x006a:
                kotlin.p.b(r18)
                r3 = r17
                r5 = r18
                com.leedarson.serviceimpl.ctrl.m r6 = r3.this$0
                long r9 = r3.$nodeId
                com.leedarson.serviceimpl.listener.b r7 = r3.$callback
                r3.label = r0
                java.lang.Object r0 = r6.j(r9, r7, r3)
                if (r0 != r1) goto L_0x0080
                return r1
            L_0x0080:
                r16 = r3
                r3 = r0
                r0 = r16
            L_0x0085:
                java.lang.Number r3 = (java.lang.Number) r3
                int r3 = r3.intValue()
                if (r3 != r4) goto L_0x009f
                com.leedarson.serviceimpl.listener.b r1 = r0.$callback
                if (r1 != 0) goto L_0x0092
                goto L_0x009c
            L_0x0092:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "find fabricIndex failed !"
                r6.<init>(r7)
                r1.onFail(r4, r6)
            L_0x009c:
                kotlin.x r1 = kotlin.x.a
                return r1
            L_0x009f:
                com.leedarson.serviceimpl.ctrl.m r9 = r0.this$0
                long r10 = r0.$nodeId
                int r12 = r0.$groupId
                r0.I$0 = r3
                r6 = 2
                r0.label = r6
                r13 = r3
                r14 = r0
                java.lang.Object r6 = r9.n(r10, r12, r13, r14)
                if (r6 != r1) goto L_0x00b3
                return r1
            L_0x00b3:
                r16 = r6
                r6 = r3
                r3 = r16
            L_0x00b8:
                java.lang.Number r3 = (java.lang.Number) r3
                int r3 = r3.intValue()
                com.leedarson.serviceimpl.ctrl.m r7 = r0.this$0
                int r7 = r7.g()
                if (r3 != r7) goto L_0x00de
                com.leedarson.serviceimpl.listener.b r1 = r0.$callback
                if (r1 != 0) goto L_0x00cb
                goto L_0x00db
            L_0x00cb:
                com.leedarson.serviceimpl.ctrl.m r4 = r0.this$0
                int r4 = r4.g()
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "write ACL Attribute failed !"
                r6.<init>(r7)
                r1.onFail(r4, r6)
            L_0x00db:
                kotlin.x r1 = kotlin.x.a
                return r1
            L_0x00de:
                com.leedarson.serviceimpl.ctrl.m r7 = r0.this$0
                long r9 = r0.$nodeId
                int r11 = r0.$groupId
                r0.I$0 = r6
                r12 = 3
                r0.label = r12
                java.lang.Object r3 = r7.q(r9, r11, r0)
                if (r3 != r1) goto L_0x00f0
                return r1
            L_0x00f0:
                r14 = r6
            L_0x00f1:
                java.lang.Number r3 = (java.lang.Number) r3
                int r3 = r3.intValue()
                com.leedarson.serviceimpl.ctrl.m r6 = r0.this$0
                int r6 = r6.g()
                if (r3 != r6) goto L_0x0117
                com.leedarson.serviceimpl.listener.b r1 = r0.$callback
                if (r1 != 0) goto L_0x0104
                goto L_0x0114
            L_0x0104:
                com.leedarson.serviceimpl.ctrl.m r4 = r0.this$0
                int r4 = r4.g()
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "write KID failed !"
                r6.<init>(r7)
                r1.onFail(r4, r6)
            L_0x0114:
                kotlin.x r1 = kotlin.x.a
                return r1
            L_0x0117:
                com.leedarson.serviceimpl.ctrl.m r9 = r0.this$0
                long r10 = r0.$nodeId
                int r12 = r0.$groupId
                r6 = 4
                r0.label = r6
                r13 = r3
                r15 = r0
                java.lang.Object r3 = com.leedarson.serviceimpl.ctrl.m.c(r9, r10, r12, r13, r14, r15)
                if (r3 != r1) goto L_0x0129
                return r1
            L_0x0129:
                r16 = r5
                r5 = r3
                r3 = r16
            L_0x012e:
                java.lang.Number r5 = (java.lang.Number) r5
                int r5 = r5.intValue()
                com.leedarson.serviceimpl.ctrl.m r6 = r0.this$0
                int r6 = r6.g()
                if (r5 != r6) goto L_0x014e
                com.leedarson.serviceimpl.listener.b r1 = r0.$callback
                if (r1 != 0) goto L_0x0141
                goto L_0x014b
            L_0x0141:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "write GroupKeyMapAttribute failed !"
                r6.<init>(r7)
                r1.onFail(r4, r6)
            L_0x014b:
                kotlin.x r1 = kotlin.x.a
                return r1
            L_0x014e:
                com.leedarson.serviceimpl.ctrl.m r6 = r0.this$0
                long r9 = r0.$nodeId
                int r7 = r0.$groupId
                r11 = 5
                r0.label = r11
                java.lang.Object r5 = com.leedarson.serviceimpl.ctrl.m.a(r6, r9, r7, r0)
                if (r5 != r1) goto L_0x015e
                return r1
            L_0x015e:
                r1 = r5
            L_0x015f:
                java.lang.Number r1 = (java.lang.Number) r1
                int r1 = r1.intValue()
                com.leedarson.serviceimpl.ctrl.m r5 = r0.this$0
                int r5 = r5.g()
                if (r1 != r5) goto L_0x0188
                com.leedarson.serviceimpl.listener.b r5 = r0.$callback
                if (r5 != 0) goto L_0x0172
                goto L_0x0196
            L_0x0172:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                long r7 = r0.$nodeId
                java.lang.Long r7 = kotlin.coroutines.jvm.internal.b.d(r7)
                java.lang.String r8 = "could not find GroupCluster by deviceId:"
                java.lang.String r7 = kotlin.jvm.internal.k.l(r8, r7)
                r6.<init>(r7)
                r5.onFail(r4, r6)
                goto L_0x0196
            L_0x0188:
                com.leedarson.serviceimpl.listener.b r4 = r0.$callback
                if (r4 != 0) goto L_0x018d
                goto L_0x0196
            L_0x018d:
                long r5 = r0.$nodeId
                java.lang.Integer r7 = kotlin.coroutines.jvm.internal.b.c(r8)
                r4.onSuccess(r5, r7)
            L_0x0196:
                kotlin.x r4 = kotlin.x.a
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.m.c.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public final void e(long j2, int i2, @Nullable com.leedarson.serviceimpl.listener.b callback) {
        Object[] objArr = {new Long(j2), new Integer(i2), callback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7680, new Class[]{Long.TYPE, Integer.TYPE, com.leedarson.serviceimpl.listener.b.class}, Void.TYPE).isSupported) {
            long nodeId = j2;
            int groupId = i2;
            com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
            com.leedarson.serviceimpl.k.b(kVar, "addGroupMember nodeId:" + nodeId + ",groupId:" + groupId, (String) null, 2, (Object) null);
            kotlinx.coroutines.j.d(s1.c, (kotlin.coroutines.g) null, (q0) null, new c(this, nodeId, callback, groupId, (kotlin.coroutines.d<? super c>) null), 3, (Object) null).start();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0071  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00af  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00c4  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object q(long r28, int r30, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Integer> r31) {
        /*
            r27 = this;
            r0 = r31
            r1 = 3
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.Long r3 = new java.lang.Long
            r9 = r28
            r3.<init>(r9)
            r11 = 0
            r2[r11] = r3
            java.lang.Integer r3 = new java.lang.Integer
            r12 = r30
            r3.<init>(r12)
            r13 = 1
            r2[r13] = r3
            r14 = 2
            r2[r14] = r0
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class r1 = java.lang.Long.TYPE
            r7[r11] = r1
            java.lang.Class r1 = java.lang.Integer.TYPE
            r7[r13] = r1
            java.lang.Class<kotlin.coroutines.d> r1 = kotlin.coroutines.d.class
            r7[r14] = r1
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            r5 = 0
            r6 = 7681(0x1e01, float:1.0763E-41)
            r3 = r27
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x003e
            java.lang.Object r0 = r1.result
            return r0
        L_0x003e:
            boolean r1 = r0 instanceof com.leedarson.serviceimpl.ctrl.m.n
            if (r1 == 0) goto L_0x0054
            r1 = r0
            com.leedarson.serviceimpl.ctrl.m$n r1 = (com.leedarson.serviceimpl.ctrl.m.n) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0054
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r27
            goto L_0x005c
        L_0x0054:
            com.leedarson.serviceimpl.ctrl.m$n r1 = new com.leedarson.serviceimpl.ctrl.m$n
            r2 = r27
            r1.<init>(r2, r0)
            r0 = r1
        L_0x005c:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            switch(r4) {
                case 0: goto L_0x008b;
                case 1: goto L_0x007c;
                case 2: goto L_0x0071;
                default: goto L_0x0067;
            }
        L_0x0067:
            r18 = r1
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0071:
            java.lang.Object r3 = r0.L$0
            chip.devicecontroller.ChipClusters$GroupKeyManagementCluster r3 = (chip.devicecontroller.ChipClusters.GroupKeyManagementCluster) r3
            kotlin.p.b(r1)
            r18 = r1
            goto L_0x0152
        L_0x007c:
            r4 = r28
            r6 = r30
            int r6 = r0.I$0
            long r4 = r0.J$0
            kotlin.p.b(r1)
            r12 = r4
            r15 = r6
            r4 = r1
            goto L_0x00a9
        L_0x008b:
            kotlin.p.b(r1)
            r4 = r27
            r5 = r28
            r7 = r30
            com.leedarson.serviceimpl.manager.c r8 = r4.a
            int r9 = r4.i()
            r0.J$0 = r5
            r0.I$0 = r7
            r0.label = r13
            java.lang.Object r4 = r8.g(r5, r9, r0)
            if (r4 != r3) goto L_0x00a7
            return r3
        L_0x00a7:
            r12 = r5
            r15 = r7
        L_0x00a9:
            chip.devicecontroller.ChipClusters$GroupKeyManagementCluster r4 = (chip.devicecontroller.ChipClusters.GroupKeyManagementCluster) r4
            r5 = 0
            if (r4 != 0) goto L_0x00c4
            com.leedarson.serviceimpl.k r3 = com.leedarson.serviceimpl.k.a
            java.lang.Long r6 = kotlin.coroutines.jvm.internal.b.d(r12)
            java.lang.String r7 = "on fail:could not find GroupKeyManagementCluster by nodeId:"
            java.lang.String r6 = kotlin.jvm.internal.k.l(r7, r6)
            com.leedarson.serviceimpl.k.e(r3, r6, r5, r14, r5)
            r3 = -1
            java.lang.Integer r3 = kotlin.coroutines.jvm.internal.b.c(r3)
            return r3
        L_0x00c4:
            com.leedarson.serviceimpl.k r6 = com.leedarson.serviceimpl.k.a
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "add group("
            r7.append(r8)
            r7.append(r15)
            r8 = 44
            r7.append(r8)
            r7.append(r12)
            java.lang.String r8 = ") step 1 => keySetWrite"
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            com.leedarson.serviceimpl.k.b(r6, r7, r5, r14, r5)
            r0.L$0 = r4
            r0.J$0 = r12
            r0.I$0 = r15
            r0.label = r14
            kotlin.coroutines.i r14 = new kotlin.coroutines.i
            kotlin.coroutines.d r5 = kotlin.coroutines.intrinsics.b.c(r0)
            r14.<init>(r5)
            r9 = r14
            r16 = 0
            r17 = 42
            chip.devicecontroller.ChipStructs$GroupKeyManagementClusterGroupKeySetStruct r5 = new chip.devicecontroller.ChipStructs$GroupKeyManagementClusterGroupKeySetStruct
            java.lang.Integer r19 = kotlin.coroutines.jvm.internal.b.c(r17)
            java.lang.Integer r20 = kotlin.coroutines.jvm.internal.b.c(r11)
            java.lang.String r6 = "d0d1d2d3d4d5d6d7d8d9dadbdcdddedf"
            byte[] r21 = com.leedarson.base.utils.e.g(r6)
            r7 = 2220000(0x21dfe0, double:1.0968257E-317)
            java.lang.Long r22 = kotlin.coroutines.jvm.internal.b.d(r7)
            byte[] r23 = com.leedarson.base.utils.e.g(r6)
            r7 = 2220001(0x21dfe1, double:1.096826E-317)
            java.lang.Long r24 = kotlin.coroutines.jvm.internal.b.d(r7)
            byte[] r25 = com.leedarson.base.utils.e.g(r6)
            r6 = 2220002(0x21dfe2, double:1.0968267E-317)
            java.lang.Long r26 = kotlin.coroutines.jvm.internal.b.d(r6)
            r18 = r5
            r18.<init>(r19, r20, r21, r22, r23, r24, r25, r26)
            r11 = r5
            com.leedarson.serviceimpl.ctrl.m$o r10 = new com.leedarson.serviceimpl.ctrl.m$o
            r5 = r10
            r6 = r15
            r7 = r12
            r18 = r1
            r1 = r10
            r10 = r17
            r5.<init>(r6, r7, r9, r10)
            r4.keySetWrite(r1, r11)
            java.lang.Object r1 = r14.a()
            java.lang.Object r5 = kotlin.coroutines.intrinsics.c.d()
            if (r1 != r5) goto L_0x014f
            kotlin.coroutines.jvm.internal.h.c(r0)
        L_0x014f:
            if (r1 != r3) goto L_0x0152
            return r3
        L_0x0152:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.m.q(long, int, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: MTGroupCtrl.kt */
    public static final class o implements ChipClusters.DefaultClusterCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;
        final /* synthetic */ long b;
        final /* synthetic */ kotlin.coroutines.d<Integer> c;
        final /* synthetic */ int d;

        o(int $groupId, long $nodeId, kotlin.coroutines.d<? super Integer> $it, int $KID) {
            this.a = $groupId;
            this.b = $nodeId;
            this.c = $it;
            this.d = $KID;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7734, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                com.leedarson.serviceimpl.k.b(kVar, "add group(" + this.a + StringUtil.COMMA + this.b + ") step 1 <= keySetWrite onSuccess", (String) null, 2, (Object) null);
                kotlin.coroutines.d<Integer> dVar = this.c;
                Integer valueOf = Integer.valueOf(this.d);
                o.a aVar = kotlin.o.Companion;
                dVar.resumeWith(kotlin.o.m17constructorimpl(valueOf));
            }
        }

        public void onError(@Nullable Exception error) {
            if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 7735, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                com.leedarson.serviceimpl.k.e(kVar, "add group(" + this.a + StringUtil.COMMA + this.b + ") step 1 <= keySetWrite onError :" + error, (String) null, 2, (Object) null);
                kotlin.coroutines.d<Integer> dVar = this.c;
                o.a aVar = kotlin.o.Companion;
                dVar.resumeWith(kotlin.o.m17constructorimpl(-1));
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: com.leedarson.serviceimpl.ctrl.m} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x00a4  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00dc  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object n(long r26, int r28, int r29, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Integer> r30) {
        /*
            r25 = this;
            r0 = r30
            r1 = 4
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.Long r3 = new java.lang.Long
            r9 = r26
            r3.<init>(r9)
            r11 = 0
            r2[r11] = r3
            java.lang.Integer r3 = new java.lang.Integer
            r12 = r28
            r3.<init>(r12)
            r13 = 1
            r2[r13] = r3
            java.lang.Integer r3 = new java.lang.Integer
            r14 = r29
            r3.<init>(r14)
            r15 = 2
            r2[r15] = r3
            r16 = 3
            r2[r16] = r0
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class r3 = java.lang.Long.TYPE
            r7[r11] = r3
            java.lang.Class r3 = java.lang.Integer.TYPE
            r7[r13] = r3
            r7[r15] = r3
            java.lang.Class<kotlin.coroutines.d> r3 = kotlin.coroutines.d.class
            r7[r16] = r3
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            r5 = 0
            r6 = 7682(0x1e02, float:1.0765E-41)
            r3 = r25
            com.meituan.robust.PatchProxyResult r2 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r3 = r2.isSupported
            if (r3 == 0) goto L_0x004b
            java.lang.Object r0 = r2.result
            return r0
        L_0x004b:
            boolean r2 = r0 instanceof com.leedarson.serviceimpl.ctrl.m.j
            if (r2 == 0) goto L_0x0061
            r2 = r0
            com.leedarson.serviceimpl.ctrl.m$j r2 = (com.leedarson.serviceimpl.ctrl.m.j) r2
            int r3 = r2.label
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = r3 & r4
            if (r5 == 0) goto L_0x0061
            int r3 = r3 - r4
            r2.label = r3
            r0 = r2
            r3 = r25
            goto L_0x0069
        L_0x0061:
            com.leedarson.serviceimpl.ctrl.m$j r2 = new com.leedarson.serviceimpl.ctrl.m$j
            r3 = r25
            r2.<init>(r3, r0)
            r0 = r2
        L_0x0069:
            java.lang.Object r2 = r0.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.c.d()
            int r5 = r0.label
            switch(r5) {
                case 0: goto L_0x00a4;
                case 1: goto L_0x008c;
                case 2: goto L_0x007c;
                default: goto L_0x0074;
            }
        L_0x0074:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x007c:
            java.lang.Object r1 = r0.L$1
            chip.devicecontroller.ChipClusters$AccessControlCluster r1 = (chip.devicecontroller.ChipClusters.AccessControlCluster) r1
            java.lang.Object r1 = r0.L$0
            com.leedarson.serviceimpl.ctrl.m r1 = (com.leedarson.serviceimpl.ctrl.m) r1
            kotlin.p.b(r2)
            r1 = r2
            r16 = r1
            goto L_0x0193
        L_0x008c:
            r5 = r25
            r6 = r29
            r7 = r26
            r9 = r28
            int r6 = r0.I$1
            int r9 = r0.I$0
            long r7 = r0.J$0
            java.lang.Object r10 = r0.L$0
            r5 = r10
            com.leedarson.serviceimpl.ctrl.m r5 = (com.leedarson.serviceimpl.ctrl.m) r5
            kotlin.p.b(r2)
            r10 = r2
            goto L_0x00c2
        L_0x00a4:
            kotlin.p.b(r2)
            r5 = r25
            r6 = r29
            r7 = r26
            r9 = r28
            com.leedarson.serviceimpl.manager.c r10 = r5.a
            r0.L$0 = r5
            r0.J$0 = r7
            r0.I$0 = r9
            r0.I$1 = r6
            r0.label = r13
            java.lang.Object r10 = r10.a(r7, r11, r0)
            if (r10 != r4) goto L_0x00c2
            return r4
        L_0x00c2:
            chip.devicecontroller.ChipClusters$AccessControlCluster r10 = (chip.devicecontroller.ChipClusters.AccessControlCluster) r10
            r12 = 0
            if (r10 != 0) goto L_0x00dc
            com.leedarson.serviceimpl.k r1 = com.leedarson.serviceimpl.k.a
            java.lang.Long r4 = kotlin.coroutines.jvm.internal.b.d(r7)
            java.lang.String r11 = "on fail:could not find AccessControlCluster by nodeId:"
            java.lang.String r4 = kotlin.jvm.internal.k.l(r11, r4)
            com.leedarson.serviceimpl.k.e(r1, r4, r12, r15, r12)
            r1 = -1
            java.lang.Integer r1 = kotlin.coroutines.jvm.internal.b.c(r1)
            return r1
        L_0x00dc:
            com.leedarson.serviceimpl.k r14 = com.leedarson.serviceimpl.k.a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r11 = "add group(groupId :"
            r1.append(r11)
            r1.append(r9)
            java.lang.String r11 = ",nodeId:"
            r1.append(r11)
            r1.append(r7)
            java.lang.String r11 = ",fabricIndex:"
            r1.append(r11)
            r1.append(r6)
            java.lang.String r11 = ") step 0 => writeACLAttribute"
            r1.append(r11)
            java.lang.String r1 = r1.toString()
            com.leedarson.serviceimpl.k.b(r14, r1, r12, r15, r12)
            r0.L$0 = r5
            r0.L$1 = r10
            r0.I$0 = r9
            r0.I$1 = r6
            r0.label = r15
            kotlin.coroutines.i r1 = new kotlin.coroutines.i
            kotlin.coroutines.d r11 = kotlin.coroutines.intrinsics.b.c(r0)
            r1.<init>(r11)
            r11 = r1
            r12 = 0
            chip.devicecontroller.ChipStructs$AccessControlClusterAccessControlEntry r14 = new chip.devicecontroller.ChipStructs$AccessControlClusterAccessControlEntry
            r19 = 5
            java.lang.Integer r20 = kotlin.coroutines.jvm.internal.b.c(r19)
            java.lang.Integer r21 = kotlin.coroutines.jvm.internal.b.c(r15)
            java.lang.Object[] r15 = new java.lang.Object[r13]
            r19 = 112233(0x1b669, float:1.57272E-40)
            java.lang.Integer r19 = kotlin.coroutines.jvm.internal.b.c(r19)
            r18 = 0
            r15[r18] = r19
            java.util.ArrayList r22 = kotlin.collections.q.c(r15)
            r23 = 0
            java.lang.Integer r24 = kotlin.coroutines.jvm.internal.b.c(r6)
            r19 = r14
            r19.<init>(r20, r21, r22, r23, r24)
            chip.devicecontroller.ChipStructs$AccessControlClusterAccessControlEntry r15 = new chip.devicecontroller.ChipStructs$AccessControlClusterAccessControlEntry
            r17 = 4
            java.lang.Integer r20 = kotlin.coroutines.jvm.internal.b.c(r17)
            java.lang.Integer r21 = kotlin.coroutines.jvm.internal.b.c(r16)
            r16 = r2
            java.lang.Object[] r2 = new java.lang.Object[r13]
            java.lang.Integer r17 = kotlin.coroutines.jvm.internal.b.c(r9)
            r18 = 0
            r2[r18] = r17
            java.util.ArrayList r22 = kotlin.collections.q.c(r2)
            java.lang.Integer r24 = kotlin.coroutines.jvm.internal.b.c(r6)
            r19 = r15
            r19.<init>(r20, r21, r22, r23, r24)
            r2 = r15
            com.leedarson.serviceimpl.ctrl.m$k r15 = new com.leedarson.serviceimpl.ctrl.m$k
            r15.<init>(r11, r5)
            r13 = 2
            chip.devicecontroller.ChipStructs$AccessControlClusterAccessControlEntry[] r13 = new chip.devicecontroller.ChipStructs.AccessControlClusterAccessControlEntry[r13]
            r18 = 0
            r13[r18] = r14
            r17 = 1
            r13[r17] = r2
            java.util.ArrayList r13 = kotlin.collections.q.c(r13)
            r10.writeAclAttribute(r15, r13)
            java.lang.Object r1 = r1.a()
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            if (r1 != r2) goto L_0x0190
            kotlin.coroutines.jvm.internal.h.c(r0)
        L_0x0190:
            if (r1 != r4) goto L_0x0193
            return r4
        L_0x0193:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.m.n(long, int, int, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: MTGroupCtrl.kt */
    public static final class k implements ChipClusters.DefaultClusterCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ kotlin.coroutines.d<Integer> a;
        final /* synthetic */ m b;

        k(kotlin.coroutines.d<? super Integer> $it, m $receiver) {
            this.a = $it;
            this.b = $receiver;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7728, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, "writeAclAttribute onSuccess", (String) null, 2, (Object) null);
                kotlin.coroutines.d<Integer> dVar = this.a;
                o.a aVar = kotlin.o.Companion;
                dVar.resumeWith(kotlin.o.m17constructorimpl(0));
            }
        }

        public void onError(@Nullable Exception error) {
            if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 7729, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("writeAclAttribute onError ", error), (String) null, 2, (Object) null);
                kotlin.coroutines.d<Integer> dVar = this.a;
                Integer valueOf = Integer.valueOf(this.b.g());
                o.a aVar = kotlin.o.Companion;
                dVar.resumeWith(kotlin.o.m17constructorimpl(valueOf));
            }
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTGroupCtrl$getGroupMemberShip$job$1", f = "MTGroupCtrl.kt", l = {171}, m = "invokeSuspend")
    /* compiled from: MTGroupCtrl.kt */
    public static final class g extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int $groupId;
        final /* synthetic */ long $nodeId;
        int label;
        final /* synthetic */ m this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(m mVar, long j, int i, kotlin.coroutines.d<? super g> dVar) {
            super(2, dVar);
            this.this$0 = mVar;
            this.$nodeId = j;
            this.$groupId = i;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 7710, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
            if (proxy.isSupported) {
                return (kotlin.coroutines.d) proxy.result;
            }
            return new g(this.this$0, this.$nodeId, this.$groupId, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 7712, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 7711, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((g) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            g gVar;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 7709, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    gVar = this;
                    com.leedarson.serviceimpl.manager.c b = gVar.this$0.a;
                    long j = gVar.$nodeId;
                    int h = gVar.this$0.h();
                    gVar.label = 1;
                    Object f = b.f(j, h, gVar);
                    if (f != d) {
                        Object obj = $result;
                        $result = f;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    gVar = this;
                    Object obj2 = $result;
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ChipClusters.GroupsCluster cluster = (ChipClusters.GroupsCluster) $result;
            if (cluster == null) {
                com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("on fail:could not find cluster by deviceId:", kotlin.coroutines.jvm.internal.b.d(gVar.$nodeId)), (String) null, 2, (Object) null);
                return x.a;
            }
            cluster.getGroupMembership(new a(), q.c(kotlin.coroutines.jvm.internal.b.c(gVar.$groupId)));
            return x.a;
        }

        /* compiled from: MTGroupCtrl.kt */
        public static final class a implements ChipClusters.GroupsCluster.GetGroupMembershipResponseCallback {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void onSuccess(@Nullable Integer capacity, @Nullable ArrayList<Integer> groupList) {
                Class[] clsArr = {Integer.class, ArrayList.class};
                if (!PatchProxy.proxy(new Object[]{capacity, groupList}, this, changeQuickRedirect, false, 7713, clsArr, Void.TYPE).isSupported) {
                    com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("getGroupMembership onSuccess capacity:", capacity), (String) null, 2, (Object) null);
                    if (groupList != null) {
                        for (Number intValue : groupList) {
                            com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, String.valueOf(intValue.intValue()), (String) null, 2, (Object) null);
                        }
                    }
                }
            }

            public void onError(@Nullable Exception error) {
                if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 7714, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                    com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("getGroupMembership onError:", error), (String) null, 2, (Object) null);
                }
            }
        }
    }

    public final void k(long nodeId, int groupId) {
        Object[] objArr = {new Long(nodeId), new Integer(groupId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7683, new Class[]{Long.TYPE, Integer.TYPE}, Void.TYPE).isSupported) {
            kotlinx.coroutines.j.d(s1.c, (kotlin.coroutines.g) null, (q0) null, new g(this, nodeId, groupId, (kotlin.coroutines.d<? super g>) null), 3, (Object) null).start();
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTGroupCtrl$removeGroup$job$1", f = "MTGroupCtrl.kt", l = {199}, m = "invokeSuspend")
    /* compiled from: MTGroupCtrl.kt */
    public static final class i extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.serviceimpl.listener.b $callback;
        final /* synthetic */ int $groupId;
        final /* synthetic */ long $nodeId;
        int label;
        final /* synthetic */ m this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        i(m mVar, long j, com.leedarson.serviceimpl.listener.b bVar, int i, kotlin.coroutines.d<? super i> dVar) {
            super(2, dVar);
            this.this$0 = mVar;
            this.$nodeId = j;
            this.$callback = bVar;
            this.$groupId = i;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 7722, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
            if (proxy.isSupported) {
                return (kotlin.coroutines.d) proxy.result;
            }
            return new i(this.this$0, this.$nodeId, this.$callback, this.$groupId, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 7724, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 7723, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((i) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            i iVar;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 7721, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    iVar = this;
                    com.leedarson.serviceimpl.manager.c b = iVar.this$0.a;
                    long j = iVar.$nodeId;
                    int h = iVar.this$0.h();
                    iVar.label = 1;
                    Object f = b.f(j, h, iVar);
                    if (f != d) {
                        Object obj = f;
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
            ChipClusters.GroupsCluster cluster = (ChipClusters.GroupsCluster) $result;
            if (cluster == null) {
                com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("on fail:could not find cluster by deviceId:", kotlin.coroutines.jvm.internal.b.d(iVar.$nodeId)), (String) null, 2, (Object) null);
                com.leedarson.serviceimpl.listener.b bVar = iVar.$callback;
                if (bVar != null) {
                    bVar.onFail(-1, new IllegalArgumentException(kotlin.jvm.internal.k.l("could not find cluster by deviceId:", kotlin.coroutines.jvm.internal.b.d(iVar.$nodeId))));
                }
                return x.a;
            }
            cluster.removeGroup(new a(iVar.$callback, iVar.$nodeId, iVar.$groupId), kotlin.coroutines.jvm.internal.b.c(iVar.$groupId));
            return x.a;
        }

        /* compiled from: MTGroupCtrl.kt */
        public static final class a implements ChipClusters.GroupsCluster.RemoveGroupResponseCallback {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ com.leedarson.serviceimpl.listener.b a;
            final /* synthetic */ long b;
            final /* synthetic */ int c;

            a(com.leedarson.serviceimpl.listener.b $callback, long $nodeId, int $groupId) {
                this.a = $callback;
                this.b = $nodeId;
                this.c = $groupId;
            }

            public void onSuccess(@Nullable Integer status, @Nullable Integer groupId) {
                Class<Integer> cls = Integer.class;
                Class[] clsArr = {cls, cls};
                if (!PatchProxy.proxy(new Object[]{status, groupId}, this, changeQuickRedirect, false, 7725, clsArr, Void.TYPE).isSupported) {
                    com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                    com.leedarson.serviceimpl.k.b(kVar, "removeGroup onSuccess status:" + status + ",groupId:" + groupId, (String) null, 2, (Object) null);
                    com.leedarson.serviceimpl.listener.b bVar = this.a;
                    if (bVar != null) {
                        bVar.onSuccess(this.b, "");
                    }
                }
            }

            public void onError(@Nullable Exception error) {
                if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 7726, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                    com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                    com.leedarson.serviceimpl.k.e(kVar, "removeGroup onError:" + error + ",groupId:" + this.c, (String) null, 2, (Object) null);
                }
            }
        }
    }

    public final void m(long nodeId, int groupId, @Nullable com.leedarson.serviceimpl.listener.b callback) {
        Object[] objArr = {new Long(nodeId), new Integer(groupId), callback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7684, new Class[]{Long.TYPE, Integer.TYPE, com.leedarson.serviceimpl.listener.b.class}, Void.TYPE).isSupported) {
            s1 s1Var = s1.c;
            kotlinx.coroutines.j.d(s1Var, (kotlin.coroutines.g) null, (q0) null, new i(this, nodeId, callback, groupId, (kotlin.coroutines.d<? super i>) null), 3, (Object) null).start();
        }
    }

    public final void f(long j2, int i2, @Nullable com.leedarson.serviceimpl.listener.b callback) {
        Object[] objArr = {new Long(j2), new Integer(i2), callback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7686, new Class[]{Long.TYPE, Integer.TYPE, com.leedarson.serviceimpl.listener.b.class}, Void.TYPE).isSupported) {
            long nodeId = j2;
            int groupId = i2;
            String groupName = String.valueOf(groupId);
            com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
            com.leedarson.serviceimpl.k.b(kVar, "addGroupIfIdentifying nodeId:" + nodeId + ",groupId:" + groupId + ",groupName:" + groupName, (String) null, 2, (Object) null);
            kotlinx.coroutines.j.d(s1.c, (kotlin.coroutines.g) null, (q0) null, new d(this, nodeId, callback, groupId, groupName, (kotlin.coroutines.d<? super d>) null), 3, (Object) null).start();
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTGroupCtrl$addGroupIfIdentifying$job$1", f = "MTGroupCtrl.kt", l = {232}, m = "invokeSuspend")
    /* compiled from: MTGroupCtrl.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.serviceimpl.listener.b $callback;
        final /* synthetic */ int $groupId;
        final /* synthetic */ String $groupName;
        final /* synthetic */ long $nodeId;
        int label;
        final /* synthetic */ m this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(m mVar, long j, com.leedarson.serviceimpl.listener.b bVar, int i, String str, kotlin.coroutines.d<? super d> dVar) {
            super(2, dVar);
            this.this$0 = mVar;
            this.$nodeId = j;
            this.$callback = bVar;
            this.$groupId = i;
            this.$groupName = str;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 7701, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
            if (proxy.isSupported) {
                return (kotlin.coroutines.d) proxy.result;
            }
            return new d(this.this$0, this.$nodeId, this.$callback, this.$groupId, this.$groupName, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 7703, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 7702, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((d) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            d dVar;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 7700, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    dVar = this;
                    com.leedarson.serviceimpl.manager.c b = dVar.this$0.a;
                    long j = dVar.$nodeId;
                    int h = dVar.this$0.h();
                    dVar.label = 1;
                    Object f = b.f(j, h, dVar);
                    if (f != d) {
                        Object obj = f;
                        Object obj2 = $result;
                        $result = obj;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    dVar = this;
                    Object obj3 = $result;
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ChipClusters.GroupsCluster cluster = (ChipClusters.GroupsCluster) $result;
            if (cluster == null) {
                com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("on fail:could not find cluster by deviceId:", kotlin.coroutines.jvm.internal.b.d(dVar.$nodeId)), (String) null, 2, (Object) null);
                com.leedarson.serviceimpl.listener.b bVar = dVar.$callback;
                if (bVar != null) {
                    bVar.onFail(-1, new IllegalArgumentException(kotlin.jvm.internal.k.l("could not find cluster by deviceId:", kotlin.coroutines.jvm.internal.b.d(dVar.$nodeId))));
                }
                return x.a;
            }
            cluster.addGroupIfIdentifying(new a(dVar.$groupId), kotlin.coroutines.jvm.internal.b.c(dVar.$groupId), dVar.$groupName);
            return x.a;
        }

        /* compiled from: MTGroupCtrl.kt */
        public static final class a implements ChipClusters.DefaultClusterCallback {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ int a;

            a(int $groupId) {
                this.a = $groupId;
            }

            public void onSuccess() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7704, new Class[0], Void.TYPE).isSupported) {
                    com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, "addGroupIfIdentifying onSuccess", (String) null, 2, (Object) null);
                }
            }

            public void onError(@Nullable Exception error) {
                if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 7705, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                    com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                    com.leedarson.serviceimpl.k.e(kVar, "removeGroup onError:" + error + ",groupId:" + this.a, (String) null, 2, (Object) null);
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: com.leedarson.serviceimpl.listener.b} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0078  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00af  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00db  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object j(long r18, @org.jetbrains.annotations.Nullable com.leedarson.serviceimpl.listener.b r20, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Integer> r21) {
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
            r6 = 7687(0x1e07, float:1.0772E-41)
            r3 = r17
            r4 = r5
            r5 = r1
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0039
            java.lang.Object r0 = r1.result
            return r0
        L_0x0039:
            boolean r1 = r0 instanceof com.leedarson.serviceimpl.ctrl.m.e
            if (r1 == 0) goto L_0x004f
            r1 = r0
            com.leedarson.serviceimpl.ctrl.m$e r1 = (com.leedarson.serviceimpl.ctrl.m.e) r1
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
            com.leedarson.serviceimpl.ctrl.m$e r1 = new com.leedarson.serviceimpl.ctrl.m$e
            r2 = r17
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0057:
            java.lang.Object r1 = r0.result
            java.lang.Object r13 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0089;
                case 1: goto L_0x0078;
                case 2: goto L_0x006a;
                default: goto L_0x0062;
            }
        L_0x0062:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x006a:
            java.lang.Object r3 = r0.L$1
            chip.devicecontroller.ChipClusters$OperationalCredentialsCluster r3 = (chip.devicecontroller.ChipClusters.OperationalCredentialsCluster) r3
            java.lang.Object r3 = r0.L$0
            com.leedarson.serviceimpl.listener.b r3 = (com.leedarson.serviceimpl.listener.b) r3
            kotlin.p.b(r1)
            r4 = r1
            goto L_0x0109
        L_0x0078:
            r3 = r18
            r5 = r20
            long r3 = r0.J$0
            java.lang.Object r6 = r0.L$0
            r5 = r6
            com.leedarson.serviceimpl.listener.b r5 = (com.leedarson.serviceimpl.listener.b) r5
            kotlin.p.b(r1)
            r10 = r3
            r3 = r1
            goto L_0x00ab
        L_0x0089:
            kotlin.p.b(r1)
            r14 = r17
            r9 = r18
            r15 = r20
            com.leedarson.serviceimpl.manager.c r3 = r14.a
            r6 = 0
            r8 = 2
            r16 = 0
            r0.L$0 = r15
            r0.J$0 = r9
            r0.label = r11
            r4 = r9
            r7 = r0
            r10 = r9
            r9 = r16
            java.lang.Object r3 = com.leedarson.serviceimpl.manager.c.l(r3, r4, r6, r7, r8, r9)
            if (r3 != r13) goto L_0x00aa
            return r13
        L_0x00aa:
            r5 = r15
        L_0x00ab:
            chip.devicecontroller.ChipClusters$OperationalCredentialsCluster r3 = (chip.devicecontroller.ChipClusters.OperationalCredentialsCluster) r3
            if (r3 != 0) goto L_0x00db
            com.leedarson.serviceimpl.k r4 = com.leedarson.serviceimpl.k.a
            java.lang.Long r6 = kotlin.coroutines.jvm.internal.b.d(r10)
            java.lang.String r7 = "on fail:could not find cluster by deviceId:"
            java.lang.String r6 = kotlin.jvm.internal.k.l(r7, r6)
            r7 = 0
            com.leedarson.serviceimpl.k.e(r4, r6, r7, r12, r7)
            r4 = -1
            if (r5 != 0) goto L_0x00c3
            goto L_0x00d6
        L_0x00c3:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.Long r7 = kotlin.coroutines.jvm.internal.b.d(r10)
            java.lang.String r8 = "could not find cluster by deviceId:"
            java.lang.String r7 = kotlin.jvm.internal.k.l(r8, r7)
            r6.<init>(r7)
            r5.onFail(r4, r6)
        L_0x00d6:
            java.lang.Integer r4 = kotlin.coroutines.jvm.internal.b.c(r4)
            return r4
        L_0x00db:
            r0.L$0 = r5
            r0.L$1 = r3
            r0.label = r12
            kotlin.coroutines.i r4 = new kotlin.coroutines.i
            kotlin.coroutines.d r6 = kotlin.coroutines.intrinsics.b.c(r0)
            r4.<init>(r6)
            r6 = r4
            r7 = 0
            r8 = r3
            r9 = 0
            com.leedarson.serviceimpl.ctrl.m$f r12 = new com.leedarson.serviceimpl.ctrl.m$f
            r12.<init>(r6, r5)
            r8.readCurrentFabricIndexAttribute(r12)
            java.lang.Object r4 = r4.a()
            java.lang.Object r6 = kotlin.coroutines.intrinsics.c.d()
            if (r4 != r6) goto L_0x0106
            kotlin.coroutines.jvm.internal.h.c(r0)
        L_0x0106:
            if (r4 != r13) goto L_0x0109
            return r13
        L_0x0109:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.m.j(long, com.leedarson.serviceimpl.listener.b, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: MTGroupCtrl.kt */
    public static final class f implements ChipClusters.IntegerAttributeCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ kotlin.coroutines.d<Integer> a;
        final /* synthetic */ com.leedarson.serviceimpl.listener.b b;

        public /* synthetic */ void onSubscriptionEstablished() {
            gs1.a(this);
        }

        f(kotlin.coroutines.d<? super Integer> $it, com.leedarson.serviceimpl.listener.b $callback) {
            this.a = $it;
            this.b = $callback;
        }

        public void onSuccess(int value) {
            Object[] objArr = {new Integer(value)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7707, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("readCurrentFabricIndexAttribute onSuccess:", Integer.valueOf(value)), (String) null, 2, (Object) null);
                kotlin.coroutines.d<Integer> dVar = this.a;
                Integer valueOf = Integer.valueOf(value);
                o.a aVar = kotlin.o.Companion;
                dVar.resumeWith(kotlin.o.m17constructorimpl(valueOf));
            }
        }

        public void onError(@Nullable Exception error) {
            if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 7708, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("readCurrentFabricIndexAttribute onError:", error), (String) null, 2, (Object) null);
                com.leedarson.serviceimpl.listener.b bVar = this.b;
                if (bVar != null) {
                    bVar.onFail(-1, error);
                }
                kotlin.coroutines.d<Integer> dVar = this.a;
                o.a aVar = kotlin.o.Companion;
                dVar.resumeWith(kotlin.o.m17constructorimpl(-1));
            }
        }
    }

    public final void p(long nodeId, int groupId, @Nullable com.leedarson.serviceimpl.listener.b callback) {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: com.leedarson.serviceimpl.ctrl.m} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0081  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00e2  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00fa  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.Object o(long r18, int r20, int r21, int r22, kotlin.coroutines.d<? super java.lang.Integer> r23) {
        /*
            r17 = this;
            r0 = r23
            r1 = 5
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.Long r3 = new java.lang.Long
            r9 = r18
            r3.<init>(r9)
            r4 = 0
            r2[r4] = r3
            java.lang.Integer r3 = new java.lang.Integer
            r11 = r20
            r3.<init>(r11)
            r12 = 1
            r2[r12] = r3
            java.lang.Integer r3 = new java.lang.Integer
            r13 = r21
            r3.<init>(r13)
            r14 = 2
            r2[r14] = r3
            java.lang.Integer r3 = new java.lang.Integer
            r15 = r22
            r3.<init>(r15)
            r5 = 3
            r2[r5] = r3
            r3 = 4
            r2[r3] = r0
            com.meituan.robust.ChangeQuickRedirect r6 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class r1 = java.lang.Long.TYPE
            r7[r4] = r1
            java.lang.Class r1 = java.lang.Integer.TYPE
            r7[r12] = r1
            r7[r14] = r1
            r7[r5] = r1
            java.lang.Class<kotlin.coroutines.d> r1 = kotlin.coroutines.d.class
            r7[r3] = r1
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            r5 = 0
            r1 = 7688(0x1e08, float:1.0773E-41)
            r3 = r17
            r4 = r6
            r6 = r1
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0058
            java.lang.Object r0 = r1.result
            return r0
        L_0x0058:
            boolean r1 = r0 instanceof com.leedarson.serviceimpl.ctrl.m.l
            if (r1 == 0) goto L_0x006e
            r1 = r0
            com.leedarson.serviceimpl.ctrl.m$l r1 = (com.leedarson.serviceimpl.ctrl.m.l) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x006e
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r17
            goto L_0x0076
        L_0x006e:
            com.leedarson.serviceimpl.ctrl.m$l r1 = new com.leedarson.serviceimpl.ctrl.m$l
            r2 = r17
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0076:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            switch(r4) {
                case 0: goto L_0x00b6;
                case 1: goto L_0x009a;
                case 2: goto L_0x008b;
                default: goto L_0x0081;
            }
        L_0x0081:
            r16 = r1
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x008b:
            java.lang.Object r3 = r0.L$1
            chip.devicecontroller.ChipClusters$GroupKeyManagementCluster r3 = (chip.devicecontroller.ChipClusters.GroupKeyManagementCluster) r3
            java.lang.Object r3 = r0.L$0
            com.leedarson.serviceimpl.ctrl.m r3 = (com.leedarson.serviceimpl.ctrl.m) r3
            kotlin.p.b(r1)
            r16 = r1
            goto L_0x0177
        L_0x009a:
            r4 = r17
            r5 = r21
            r6 = r18
            r8 = r20
            r9 = r22
            int r9 = r0.I$2
            int r5 = r0.I$1
            int r8 = r0.I$0
            long r6 = r0.J$0
            java.lang.Object r10 = r0.L$0
            r4 = r10
            com.leedarson.serviceimpl.ctrl.m r4 = (com.leedarson.serviceimpl.ctrl.m) r4
            kotlin.p.b(r1)
            r10 = r1
            goto L_0x00dc
        L_0x00b6:
            kotlin.p.b(r1)
            r4 = r17
            r5 = r21
            r6 = r18
            r8 = r20
            r9 = r22
            com.leedarson.serviceimpl.manager.c r10 = r4.a
            int r11 = r4.i()
            r0.L$0 = r4
            r0.J$0 = r6
            r0.I$0 = r8
            r0.I$1 = r5
            r0.I$2 = r9
            r0.label = r12
            java.lang.Object r10 = r10.g(r6, r11, r0)
            if (r10 != r3) goto L_0x00dc
            return r3
        L_0x00dc:
            chip.devicecontroller.ChipClusters$GroupKeyManagementCluster r10 = (chip.devicecontroller.ChipClusters.GroupKeyManagementCluster) r10
            r11 = 0
            if (r10 != 0) goto L_0x00fa
            com.leedarson.serviceimpl.k r3 = com.leedarson.serviceimpl.k.a
            java.lang.Long r12 = kotlin.coroutines.jvm.internal.b.d(r6)
            java.lang.String r13 = "on fail:could not find cluster by deviceId:"
            java.lang.String r12 = kotlin.jvm.internal.k.l(r13, r12)
            com.leedarson.serviceimpl.k.e(r3, r12, r11, r14, r11)
            int r3 = r4.g()
            java.lang.Integer r3 = kotlin.coroutines.jvm.internal.b.c(r3)
            return r3
        L_0x00fa:
            r0.L$0 = r4
            r0.L$1 = r10
            r0.J$0 = r6
            r0.I$0 = r8
            r0.I$1 = r5
            r0.I$2 = r9
            r0.label = r14
            kotlin.coroutines.i r12 = new kotlin.coroutines.i
            kotlin.coroutines.d r13 = kotlin.coroutines.intrinsics.b.c(r0)
            r12.<init>(r13)
            r22 = r12
            r13 = 0
            com.leedarson.serviceimpl.k r15 = com.leedarson.serviceimpl.k.a
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r14 = "add group("
            r11.append(r14)
            r11.append(r8)
            r14 = 44
            r11.append(r14)
            r11.append(r6)
            java.lang.String r14 = ") step 2 => writeGroupKeyMapAttribute "
            r11.append(r14)
            java.lang.String r11 = r11.toString()
            r16 = r1
            r1 = 2
            r14 = 0
            com.leedarson.serviceimpl.k.b(r15, r11, r14, r1, r14)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            chip.devicecontroller.ChipStructs$GroupKeyManagementClusterGroupKeyMapStruct r11 = new chip.devicecontroller.ChipStructs$GroupKeyManagementClusterGroupKeyMapStruct
            java.lang.Integer r14 = kotlin.coroutines.jvm.internal.b.c(r8)
            java.lang.Integer r15 = kotlin.coroutines.jvm.internal.b.c(r5)
            java.lang.Integer r2 = kotlin.coroutines.jvm.internal.b.c(r9)
            r11.<init>(r14, r15, r2)
            r1.add(r11)
            com.leedarson.serviceimpl.ctrl.m$m r2 = new com.leedarson.serviceimpl.ctrl.m$m
            r18 = r2
            r19 = r8
            r20 = r6
            r23 = r4
            r18.<init>(r19, r20, r22, r23)
            r10.writeGroupKeyMapAttribute(r2, r1)
            java.lang.Object r1 = r12.a()
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            if (r1 != r2) goto L_0x0174
            kotlin.coroutines.jvm.internal.h.c(r0)
        L_0x0174:
            if (r1 != r3) goto L_0x0177
            return r3
        L_0x0177:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.m.o(long, int, int, int, kotlin.coroutines.d):java.lang.Object");
    }

    /* renamed from: com.leedarson.serviceimpl.ctrl.m$m  reason: collision with other inner class name */
    /* compiled from: MTGroupCtrl.kt */
    public static final class C0136m implements ChipClusters.DefaultClusterCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;
        final /* synthetic */ long b;
        final /* synthetic */ kotlin.coroutines.d<Integer> c;
        final /* synthetic */ m d;

        C0136m(int $groupId, long $nodeId, kotlin.coroutines.d<? super Integer> $it, m $receiver) {
            this.a = $groupId;
            this.b = $nodeId;
            this.c = $it;
            this.d = $receiver;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7731, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                com.leedarson.serviceimpl.k.b(kVar, "add group(" + this.a + StringUtil.COMMA + this.b + ") step 2 <== writeGroupKeyMapAttribute onSuccess", (String) null, 2, (Object) null);
                kotlin.coroutines.d<Integer> dVar = this.c;
                o.a aVar = kotlin.o.Companion;
                dVar.resumeWith(kotlin.o.m17constructorimpl(0));
            }
        }

        public void onError(@Nullable Exception error) {
            if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 7732, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                com.leedarson.serviceimpl.k.e(kVar, "add group(" + this.a + StringUtil.COMMA + this.b + ") step 2 <== writeGroupKeyMapAttribute error : " + error, (String) null, 2, (Object) null);
                kotlin.coroutines.d<Integer> dVar = this.c;
                Integer valueOf = Integer.valueOf(this.d.g());
                o.a aVar = kotlin.o.Companion;
                dVar.resumeWith(kotlin.o.m17constructorimpl(valueOf));
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: com.leedarson.serviceimpl.ctrl.m} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0071  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x007f  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0093  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00ce  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.Object d(long r21, int r23, kotlin.coroutines.d<? super java.lang.Integer> r24) {
        /*
            r20 = this;
            r0 = r24
            r1 = 3
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.Long r3 = new java.lang.Long
            r9 = r21
            r3.<init>(r9)
            r4 = 0
            r2[r4] = r3
            java.lang.Integer r3 = new java.lang.Integer
            r11 = r23
            r3.<init>(r11)
            r12 = 1
            r2[r12] = r3
            r13 = 2
            r2[r13] = r0
            com.meituan.robust.ChangeQuickRedirect r5 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class r1 = java.lang.Long.TYPE
            r7[r4] = r1
            java.lang.Class r1 = java.lang.Integer.TYPE
            r7[r12] = r1
            java.lang.Class<kotlin.coroutines.d> r1 = kotlin.coroutines.d.class
            r7[r13] = r1
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            r1 = 0
            r6 = 7689(0x1e09, float:1.0775E-41)
            r3 = r20
            r4 = r5
            r5 = r1
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0040
            java.lang.Object r0 = r1.result
            return r0
        L_0x0040:
            boolean r1 = r0 instanceof com.leedarson.serviceimpl.ctrl.m.a
            if (r1 == 0) goto L_0x0056
            r1 = r0
            com.leedarson.serviceimpl.ctrl.m$a r1 = (com.leedarson.serviceimpl.ctrl.m.a) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0056
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r20
            goto L_0x005e
        L_0x0056:
            com.leedarson.serviceimpl.ctrl.m$a r1 = new com.leedarson.serviceimpl.ctrl.m$a
            r2 = r20
            r1.<init>(r2, r0)
            r0 = r1
        L_0x005e:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            switch(r4) {
                case 0: goto L_0x0093;
                case 1: goto L_0x007f;
                case 2: goto L_0x0071;
                default: goto L_0x0069;
            }
        L_0x0069:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0071:
            java.lang.Object r3 = r0.L$1
            chip.devicecontroller.ChipClusters$GroupsCluster r3 = (chip.devicecontroller.ChipClusters.GroupsCluster) r3
            java.lang.Object r3 = r0.L$0
            com.leedarson.serviceimpl.ctrl.m r3 = (com.leedarson.serviceimpl.ctrl.m) r3
            kotlin.p.b(r1)
            r9 = r1
            goto L_0x0134
        L_0x007f:
            r4 = r20
            r5 = r21
            r7 = r23
            int r7 = r0.I$0
            long r5 = r0.J$0
            java.lang.Object r8 = r0.L$0
            r4 = r8
            com.leedarson.serviceimpl.ctrl.m r4 = (com.leedarson.serviceimpl.ctrl.m) r4
            kotlin.p.b(r1)
            r8 = r1
            goto L_0x00b1
        L_0x0093:
            kotlin.p.b(r1)
            r4 = r20
            r5 = r21
            r7 = r23
            com.leedarson.serviceimpl.manager.c r8 = r4.a
            int r9 = r4.h()
            r0.L$0 = r4
            r0.J$0 = r5
            r0.I$0 = r7
            r0.label = r12
            java.lang.Object r8 = r8.f(r5, r9, r0)
            if (r8 != r3) goto L_0x00b1
            return r3
        L_0x00b1:
            chip.devicecontroller.ChipClusters$GroupsCluster r8 = (chip.devicecontroller.ChipClusters.GroupsCluster) r8
            r9 = 0
            if (r8 != 0) goto L_0x00ce
            com.leedarson.serviceimpl.k r3 = com.leedarson.serviceimpl.k.a
            java.lang.Long r10 = kotlin.coroutines.jvm.internal.b.d(r5)
            java.lang.String r11 = "on fail:could not find cluster by deviceId:"
            java.lang.String r10 = kotlin.jvm.internal.k.l(r11, r10)
            com.leedarson.serviceimpl.k.e(r3, r10, r9, r13, r9)
            int r3 = r4.g()
            java.lang.Integer r3 = kotlin.coroutines.jvm.internal.b.c(r3)
            return r3
        L_0x00ce:
            com.leedarson.serviceimpl.k r10 = com.leedarson.serviceimpl.k.a
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "add group("
            r11.append(r12)
            r11.append(r7)
            r12 = 44
            r11.append(r12)
            r11.append(r5)
            java.lang.String r12 = ") step 3 => addGroup "
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            com.leedarson.serviceimpl.k.b(r10, r11, r9, r13, r9)
            r0.L$0 = r4
            r0.L$1 = r8
            r0.J$0 = r5
            r0.I$0 = r7
            r0.label = r13
            kotlin.coroutines.i r9 = new kotlin.coroutines.i
            kotlin.coroutines.d r10 = kotlin.coroutines.intrinsics.b.c(r0)
            r9.<init>(r10)
            r18 = r9
            r10 = 0
            com.leedarson.serviceimpl.ctrl.m$b r11 = new com.leedarson.serviceimpl.ctrl.m$b
            r14 = r11
            r15 = r5
            r17 = r7
            r19 = r4
            r14.<init>(r15, r17, r18, r19)
            java.lang.Integer r12 = kotlin.coroutines.jvm.internal.b.c(r7)
            java.lang.Integer r13 = kotlin.coroutines.jvm.internal.b.c(r7)
            java.lang.String r14 = "name"
            java.lang.String r13 = kotlin.jvm.internal.k.l(r14, r13)
            r8.addGroup(r11, r12, r13)
            java.lang.Object r9 = r9.a()
            java.lang.Object r10 = kotlin.coroutines.intrinsics.c.d()
            if (r9 != r10) goto L_0x0131
            kotlin.coroutines.jvm.internal.h.c(r0)
        L_0x0131:
            if (r9 != r3) goto L_0x0134
            return r3
        L_0x0134:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ctrl.m.d(long, int, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: MTGroupCtrl.kt */
    public static final class b implements ChipClusters.GroupsCluster.AddGroupResponseCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long a;
        final /* synthetic */ int b;
        final /* synthetic */ kotlin.coroutines.d<Integer> c;
        final /* synthetic */ m d;

        b(long $nodeId, int $groupId, kotlin.coroutines.d<? super Integer> $it, m $receiver) {
            this.a = $nodeId;
            this.b = $groupId;
            this.c = $it;
            this.d = $receiver;
        }

        public void onSuccess(@Nullable Integer status, @Nullable Integer groupId) {
            Class<Integer> cls = Integer.class;
            Class[] clsArr = {cls, cls};
            if (!PatchProxy.proxy(new Object[]{status, groupId}, this, changeQuickRedirect, false, 7694, clsArr, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                com.leedarson.serviceimpl.k.b(kVar, "add group(" + groupId + StringUtil.COMMA + this.a + ") step 3 <== addGroup onSuccess status:" + status, (String) null, 2, (Object) null);
            }
        }

        public void onError(@Nullable Exception error) {
            if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 7695, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.k kVar = com.leedarson.serviceimpl.k.a;
                com.leedarson.serviceimpl.k.e(kVar, "add group(" + this.b + StringUtil.COMMA + this.a + ") step 3 <== addGroup onError:" + error, (String) null, 2, (Object) null);
                kotlin.coroutines.d<Integer> dVar = this.c;
                Integer valueOf = Integer.valueOf(this.d.g());
                o.a aVar = kotlin.o.Companion;
                dVar.resumeWith(kotlin.o.m17constructorimpl(valueOf));
            }
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "com.leedarson.serviceimpl.ctrl.MTGroupCtrl$identify$1", f = "MTGroupCtrl.kt", l = {371}, m = "invokeSuspend")
    /* compiled from: MTGroupCtrl.kt */
    public static final class h extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long $nodeId;
        int label;
        final /* synthetic */ m this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(m mVar, long j, kotlin.coroutines.d<? super h> dVar) {
            super(2, dVar);
            this.this$0 = mVar;
            this.$nodeId = j;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 7716, new Class[]{Object.class, kotlin.coroutines.d.class}, kotlin.coroutines.d.class);
            if (proxy.isSupported) {
                return (kotlin.coroutines.d) proxy.result;
            }
            return new h(this.this$0, this.$nodeId, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 7718, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (kotlin.coroutines.d<? super x>) (kotlin.coroutines.d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 7717, new Class[]{o0.class, kotlin.coroutines.d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((h) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            h hVar;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 7715, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    hVar = this;
                    com.leedarson.serviceimpl.manager.c b = hVar.this$0.a;
                    long j = hVar.$nodeId;
                    hVar.label = 1;
                    Object h = b.h(j, 0, hVar);
                    if (h != d) {
                        Object obj = h;
                        Object obj2 = $result;
                        $result = obj;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    hVar = this;
                    Object obj3 = $result;
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ChipClusters.IdentifyCluster cluster = (ChipClusters.IdentifyCluster) $result;
            if (cluster == null) {
                com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("on fail:could not find cluster by deviceId:", kotlin.coroutines.jvm.internal.b.d(hVar.$nodeId)), (String) null, 2, (Object) null);
                return x.a;
            }
            cluster.identify(new a(), kotlin.coroutines.jvm.internal.b.c(10000000));
            return x.a;
        }

        /* compiled from: MTGroupCtrl.kt */
        public static final class a implements ChipClusters.DefaultClusterCallback {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void onSuccess() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7719, new Class[0], Void.TYPE).isSupported) {
                    com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, "identify onSuccess", (String) null, 2, (Object) null);
                }
            }

            public void onError(@Nullable Exception error) {
                if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 7720, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                    com.leedarson.serviceimpl.k.e(com.leedarson.serviceimpl.k.a, kotlin.jvm.internal.k.l("identify error:", error), (String) null, 2, (Object) null);
                }
            }
        }
    }

    public final void l(long nodeId, int i2) {
        Object[] objArr = {new Long(nodeId), new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7690, new Class[]{Long.TYPE, Integer.TYPE}, Void.TYPE).isSupported) {
            kotlinx.coroutines.j.d(s1.c, (kotlin.coroutines.g) null, (q0) null, new h(this, nodeId, (kotlin.coroutines.d<? super h>) null), 3, (Object) null).start();
        }
    }
}
