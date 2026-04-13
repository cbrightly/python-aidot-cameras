package com.leedarson.newui.signal_test;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.combeans.MqttMessageConfigBean;
import com.leedarson.serviceinterface.LDSBaseMqttService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlinx.coroutines.flow.e;
import kotlinx.coroutines.flow.q;
import kotlinx.coroutines.flow.x;
import kotlinx.coroutines.flow.z;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z0;
import kotlinx.coroutines.z1;
import meshsdk.model.json.RoutineRule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: SignalTestViewModel.kt */
public final class SignalTestViewModel extends ViewModel {
    public static ChangeQuickRedirect changeQuickRedirect;
    @Nullable
    private IpcDeviceBean a;
    /* access modifiers changed from: private */
    public final LDSBaseMqttService b = ((LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class));
    /* access modifiers changed from: private */
    @NotNull
    public final q<d> c;
    @NotNull
    private final x<d> d;
    /* access modifiers changed from: private */
    public int e;

    public SignalTestViewModel() {
        q<d> a2 = z.a(d.None);
        this.c = a2;
        this.d = e.b(a2);
    }

    public static final /* synthetic */ void a(SignalTestViewModel $this, d wifiEnum) {
        Class[] clsArr = {SignalTestViewModel.class, d.class};
        if (!PatchProxy.proxy(new Object[]{$this, wifiEnum}, (Object) null, changeQuickRedirect, true, 4709, clsArr, Void.TYPE).isSupported) {
            $this.g(wifiEnum);
        }
    }

    public static final /* synthetic */ d c(SignalTestViewModel $this, int wifiStrength) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$this, new Integer(wifiStrength)}, (Object) null, changeQuickRedirect, true, 4710, new Class[]{SignalTestViewModel.class, Integer.TYPE}, d.class);
        return proxy.isSupported ? (d) proxy.result : $this.j(wifiStrength);
    }

    public static final /* synthetic */ void f(SignalTestViewModel $this) {
        if (!PatchProxy.proxy(new Object[]{$this}, (Object) null, changeQuickRedirect, true, 4708, new Class[]{SignalTestViewModel.class}, Void.TYPE).isSupported) {
            $this.l();
        }
    }

    @Nullable
    public final IpcDeviceBean h() {
        return this.a;
    }

    public final void m(@Nullable IpcDeviceBean ipcDeviceBean) {
        this.a = ipcDeviceBean;
    }

    @NotNull
    public final x<d> k() {
        return this.d;
    }

    @f(c = "com.leedarson.newui.signal_test.SignalTestViewModel$getWifiBaseInfo$1", f = "SignalTestViewModel.kt", l = {30}, m = "invokeSuspend")
    /* compiled from: SignalTestViewModel.kt */
    public static final class b extends l implements p<o0, d<? super kotlin.x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        int label;
        final /* synthetic */ SignalTestViewModel this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(SignalTestViewModel signalTestViewModel, d<? super b> dVar) {
            super(2, dVar);
            this.this$0 = signalTestViewModel;
        }

        @NotNull
        public final d<kotlin.x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect, false, 4716, new Class[]{Object.class, d.class}, d.class);
            return proxy.isSupported ? (d) proxy.result : new b(this.this$0, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 4718, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (d<? super kotlin.x>) (d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super kotlin.x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 4717, new Class[]{o0.class, d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((b) create(o0Var, dVar)).invokeSuspend(kotlin.x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object $result;
            b bVar;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4715, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    Object $result2 = obj;
                    this.label = 1;
                    if (z0.a(((long) (this.this$0.e * 2)) * 1000, this) != d) {
                        bVar = this;
                        $result = $result2;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    bVar = this;
                    $result = obj;
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            String userId = BaseApplication.b().d();
            LDSBaseMqttService d2 = bVar.this$0.b;
            String str = "iot/v1/s/" + userId + "/device/devActionReq";
            MqttMessageConfigBean mqttMessageConfigBean = new MqttMessageConfigBean();
            kotlin.x xVar = kotlin.x.a;
            JSONObject jSONObject = new JSONObject();
            SignalTestViewModel signalTestViewModel = bVar.this$0;
            JSONObject $this$invokeSuspend_u24lambda_u2d2 = jSONObject;
            $this$invokeSuspend_u24lambda_u2d2.put(FirebaseAnalytics.Param.METHOD, (Object) "devActionReq");
            $this$invokeSuspend_u24lambda_u2d2.put(NotificationCompat.CATEGORY_SERVICE, (Object) RoutineRule.THEN_TYPE_DEVICE);
            JSONObject jSONObject2 = new JSONObject();
            JSONObject $this$invokeSuspend_u24lambda_u2d2_u24lambda_u2d1 = jSONObject2;
            IpcDeviceBean h = signalTestViewModel.h();
            Object obj2 = $result;
            $this$invokeSuspend_u24lambda_u2d2_u24lambda_u2d1.put("devId", (Object) h == null ? null : h.id);
            $this$invokeSuspend_u24lambda_u2d2_u24lambda_u2d1.put("action", (Object) "WifiBaseInfo");
            $this$invokeSuspend_u24lambda_u2d2_u24lambda_u2d1.put("userId", (Object) userId);
            IpcDeviceBean h2 = signalTestViewModel.h();
            $this$invokeSuspend_u24lambda_u2d2_u24lambda_u2d1.put("password", (Object) h2 == null ? null : h2.password);
            $this$invokeSuspend_u24lambda_u2d2.put("payload", (Object) jSONObject2);
            d2.publish(str, mqttMessageConfigBean, jSONObject, new a(bVar.this$0));
            return xVar;
        }

        /* compiled from: SignalTestViewModel.kt */
        public static final class a implements LDSBaseMqttService.MqttActionHandler {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ SignalTestViewModel a;

            a(SignalTestViewModel $receiver) {
                this.a = $receiver;
            }

            public void onActionSuccess(@Nullable String str, @Nullable JSONObject callbackData) {
                JSONObject jSONObject;
                JSONArray jSONArray;
                if (!PatchProxy.proxy(new Object[]{str, callbackData}, this, changeQuickRedirect, false, 4719, new Class[]{String.class, JSONObject.class}, Void.TYPE).isSupported) {
                    if (!(callbackData == null || (jSONObject = callbackData.getJSONObject("payload")) == null || (jSONArray = jSONObject.getJSONArray("out")) == null)) {
                        int $this$onActionSuccess_u24lambda_u2d0 = jSONArray.optInt(1, 0);
                        SignalTestViewModel signalTestViewModel = this.a;
                        SignalTestViewModel.a(signalTestViewModel, SignalTestViewModel.c(signalTestViewModel, $this$onActionSuccess_u24lambda_u2d0));
                    }
                    SignalTestViewModel.f(this.a);
                }
            }

            public void onActionFail(int i, @Nullable String str, @Nullable String str2) {
                Class<String> cls = String.class;
                Object[] objArr = {new Integer(i), str, str2};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4720, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                    SignalTestViewModel.f(this.a);
                }
            }
        }
    }

    public final void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4705, new Class[0], Void.TYPE).isSupported) {
            z1 unused = j.d(ViewModelKt.getViewModelScope(this), (g) null, (q0) null, new b(this, (d<? super b>) null), 3, (Object) null);
        }
    }

    private final void l() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4706, new Class[0], Void.TYPE).isSupported) {
            if (this.e == 0) {
                this.e = 1;
            }
            i();
        }
    }

    @f(c = "com.leedarson.newui.signal_test.SignalTestViewModel$emitWifiEnum$1", f = "SignalTestViewModel.kt", l = {73}, m = "invokeSuspend")
    /* compiled from: SignalTestViewModel.kt */
    public static final class a extends l implements p<o0, d<? super kotlin.x>, Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ d $wifiEnum;
        int label;
        final /* synthetic */ SignalTestViewModel this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(SignalTestViewModel signalTestViewModel, d dVar, d<? super a> dVar2) {
            super(2, dVar2);
            this.this$0 = signalTestViewModel;
            this.$wifiEnum = dVar;
        }

        @NotNull
        public final d<kotlin.x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, dVar}, this, changeQuickRedirect2, false, 4712, new Class[]{Object.class, d.class}, d.class);
            if (proxy.isSupported) {
                return (d) proxy.result;
            }
            return new a(this.this$0, this.$wifiEnum, dVar);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 4714, new Class[]{cls, cls}, Object.class);
            return proxy.isSupported ? proxy.result : invoke((o0) obj, (d<? super kotlin.x>) (d) obj2);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super kotlin.x> dVar) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{o0Var, dVar}, this, changeQuickRedirect, false, 4713, new Class[]{o0.class, d.class}, Object.class);
            return proxy.isSupported ? proxy.result : ((a) create(o0Var, dVar)).invokeSuspend(kotlin.x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{$result}, this, changeQuickRedirect, false, 4711, new Class[]{Object.class}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Object d = c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    q e = this.this$0.c;
                    d dVar = this.$wifiEnum;
                    this.label = 1;
                    if (e.emit(dVar, this) != d) {
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
            return kotlin.x.a;
        }
    }

    private final void g(d wifiEnum) {
        if (!PatchProxy.proxy(new Object[]{wifiEnum}, this, changeQuickRedirect, false, 4707, new Class[]{d.class}, Void.TYPE).isSupported) {
            z1 unused = j.d(ViewModelKt.getViewModelScope(this), (g) null, (q0) null, new a(this, wifiEnum, (d<? super a>) null), 3, (Object) null);
        }
    }

    private final d j(int wifiStrength) {
        if (wifiStrength <= 28) {
            return d.VeryPoor;
        }
        boolean z = true;
        if (29 <= wifiStrength && wifiStrength <= 40) {
            return d.Poor;
        }
        if (41 > wifiStrength || wifiStrength > 54) {
            z = false;
        }
        if (z) {
            return d.Medium;
        }
        if (wifiStrength > 54) {
            return d.Great;
        }
        return d.Medium;
    }
}
