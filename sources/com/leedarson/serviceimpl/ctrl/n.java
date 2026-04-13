package com.leedarson.serviceimpl.ctrl;

import android.content.Context;
import chip.devicecontroller.ChipDeviceController;
import chip.devicecontroller.OpenCommissioningCallback;
import com.google.chip.chiptool.ChipClient;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.s1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MTMultiAdminCtrl.kt */
public final class n {
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    private Context a;
    @NotNull
    private ChipDeviceController b;

    public n(@NotNull Context context, @NotNull ChipDeviceController devController) {
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

    @f(c = "com.leedarson.serviceimpl.ctrl.MTMultiAdminCtrl$openPairingWindowCallback$1", f = "MTMultiAdminCtrl.kt", l = {19}, m = "invokeSuspend")
    /* compiled from: MTMultiAdminCtrl.kt */
    public static final class a extends l implements p<o0, d<? super x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long $addr;
        final /* synthetic */ OpenCommissioningCallback $callback;
        final /* synthetic */ int $duration;
        int label;
        final /* synthetic */ n this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(n nVar, long j, OpenCommissioningCallback openCommissioningCallback, int i, d<? super a> dVar) {
            super(2, dVar);
            this.this$0 = nVar;
            this.$addr = j;
            this.$callback = openCommissioningCallback;
            this.$duration = i;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 7741, new Class[]{Object.class, d.class}, d.class);
            if (proxy.isSupported) {
                return (d) proxy.result;
            }
            return new a(this.this$0, this.$addr, this.$callback, this.$duration, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 7743, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (d<? super x>) (d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 7742, new Class[]{o0.class, d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((a) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            a aVar;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 7740, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    aVar = this;
                    ChipClient chipClient = ChipClient.INSTANCE;
                    Context a = aVar.this$0.a();
                    long j = aVar.$addr;
                    aVar.label = 1;
                    Object connectedDevicePointer = chipClient.getConnectedDevicePointer(a, j, aVar);
                    if (connectedDevicePointer != d) {
                        Object obj = connectedDevicePointer;
                        Object obj2 = $result;
                        $result = obj;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    aVar = this;
                    Object obj3 = $result;
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            long devicePtr = ((Number) $result).longValue();
            if (((int) devicePtr) == -1) {
                OpenCommissioningCallback openCommissioningCallback = aVar.$callback;
                if (openCommissioningCallback != null) {
                    openCommissioningCallback.onError(-1, aVar.$addr);
                }
                return x.a;
            }
            aVar.this$0.b().openPairingWindowCallback(devicePtr, aVar.$duration, aVar.$callback);
            return x.a;
        }
    }

    public final void c(long addr, int duration, @NotNull OpenCommissioningCallback openCommissioningCallback) {
        Object[] objArr = {new Long(addr), new Integer(duration), openCommissioningCallback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7738, new Class[]{Long.TYPE, Integer.TYPE, OpenCommissioningCallback.class}, Void.TYPE).isSupported) {
            OpenCommissioningCallback callback = openCommissioningCallback;
            k.e(callback, "callback");
            s1 s1Var = s1.c;
            j.d(s1Var, (g) null, (q0) null, new a(this, addr, callback, duration, (d<? super a>) null), 3, (Object) null).start();
        }
    }

    @f(c = "com.leedarson.serviceimpl.ctrl.MTMultiAdminCtrl$openPairingWindowWithPINCallback$1", f = "MTMultiAdminCtrl.kt", l = {38}, m = "invokeSuspend")
    /* compiled from: MTMultiAdminCtrl.kt */
    public static final class b extends l implements p<o0, d<? super x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long $addr;
        final /* synthetic */ OpenCommissioningCallback $callback;
        final /* synthetic */ int $discriminator;
        final /* synthetic */ int $duration;
        final /* synthetic */ long $iteration;
        final /* synthetic */ long $setupPinCode;
        int label;
        final /* synthetic */ n this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(n nVar, long j, OpenCommissioningCallback openCommissioningCallback, int i, long j2, int i2, long j3, d<? super b> dVar) {
            super(2, dVar);
            this.this$0 = nVar;
            this.$addr = j;
            this.$callback = openCommissioningCallback;
            this.$duration = i;
            this.$iteration = j2;
            this.$discriminator = i2;
            this.$setupPinCode = j3;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 7745, new Class[]{Object.class, d.class}, d.class);
            if (proxy.isSupported) {
                return (d) proxy.result;
            }
            return new b(this.this$0, this.$addr, this.$callback, this.$duration, this.$iteration, this.$discriminator, this.$setupPinCode, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 7747, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (d<? super x>) (d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 7746, new Class[]{o0.class, d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((b) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object $result;
            b bVar;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7744, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    Object obj2 = obj;
                    ChipClient chipClient = ChipClient.INSTANCE;
                    Context a = this.this$0.a();
                    long j = this.$addr;
                    this.label = 1;
                    Object connectedDevicePointer = chipClient.getConnectedDevicePointer(a, j, this);
                    if (connectedDevicePointer != d) {
                        $result = connectedDevicePointer;
                        bVar = this;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    bVar = this;
                    $result = obj;
                    kotlin.p.b($result);
                    Object obj3 = $result;
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            long devicePtr = ((Number) $result).longValue();
            if (((int) devicePtr) == -1) {
                OpenCommissioningCallback openCommissioningCallback = bVar.$callback;
                if (openCommissioningCallback != null) {
                    openCommissioningCallback.onError(-1, bVar.$addr);
                }
                return x.a;
            }
            long j2 = devicePtr;
            bVar.this$0.b().openPairingWindowWithPINCallback(devicePtr, bVar.$duration, bVar.$iteration, bVar.$discriminator, bVar.$setupPinCode, bVar.$callback);
            return x.a;
        }
    }

    public final void d(long j, int i, long iteration, int discriminator, long setupPinCode, @NotNull OpenCommissioningCallback openCommissioningCallback) {
        Object[] objArr = {new Long(j), new Integer(i), new Long(iteration), new Integer(discriminator), new Long(setupPinCode), openCommissioningCallback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Long.TYPE;
        Class cls2 = Integer.TYPE;
        Class[] clsArr = {cls, cls2, cls, cls2, cls, OpenCommissioningCallback.class};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7739, clsArr, Void.TYPE).isSupported) {
            OpenCommissioningCallback callback = openCommissioningCallback;
            long addr = j;
            int duration = i;
            k.e(callback, "callback");
            s1 s1Var = s1.c;
            OpenCommissioningCallback openCommissioningCallback2 = callback;
            j.d(s1Var, (g) null, (q0) null, new b(this, addr, openCommissioningCallback2, duration, iteration, discriminator, setupPinCode, (d<? super b>) null), 3, (Object) null).start();
        }
    }
}
