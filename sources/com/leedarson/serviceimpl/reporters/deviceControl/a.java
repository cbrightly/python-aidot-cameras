package com.leedarson.serviceimpl.reporters.deviceControl;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.kit.toolpanel.KitWrapItem;
import com.leedarson.serviceinterface.LightsRhythmService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.functions.e;
import io.reactivex.l;
import java.util.concurrent.TimeUnit;
import meshsdk.BaseResp;
import meshsdk.ctrl.ControlDevIntercepter;
import meshsdk.util.LDSModel;
import meshsdk.util.MeshConstants;

/* compiled from: DeviceControlReporterTask */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    public b a;
    public long b;
    public long c;
    public int d;
    public long e;
    public String f;
    public io.reactivex.disposables.b g;
    public ControlDevIntercepter.CtrlDevWrap h;

    /* compiled from: DeviceControlReporterTask */
    public enum b {
        CODE_SUCCESS(200, "设备响应成功"),
        CODE_SUCCESS_NOT_MATCH(201, "设备响应成功，但值不匹配"),
        CODE_SEND_SUCCESS(KitWrapItem.TYPE_MODE, "发送成功，但不知道设备是否响应"),
        CODE_SEND_FAIL_NOT_CONNECTED(BaseResp.ERR_MSG_SEND_FAIL, "发送失败，设备未连接"),
        CODE_SEND_FAIL_BUSY(401, "发送失败，指令排队超时"),
        CODE_SEND_FAIL(402, "ble回调发送失败");
        
        public static ChangeQuickRedirect changeQuickRedirect;
        int code;
        String desc;

        private b(int code2, String desc2) {
            this.code = code2;
            this.desc = desc2;
        }
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8568, new Class[0], Void.TYPE).isSupported) {
            io.reactivex.disposables.b bVar = this.g;
            if (bVar != null && !bVar.isDisposed()) {
                this.g.dispose();
            }
            this.g = l.F(1).l(10, TimeUnit.SECONDS).b0(io.reactivex.schedulers.a.a()).J(io.reactivex.schedulers.a.a()).X(new C0154a());
        }
    }

    /* renamed from: com.leedarson.serviceimpl.reporters.deviceControl.a$a  reason: collision with other inner class name */
    /* compiled from: DeviceControlReporterTask */
    public class C0154a implements e<Integer> {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0154a() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8572, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Integer) obj);
            }
        }

        public void a(Integer num) {
            if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 8571, new Class[]{Integer.class}, Void.TYPE).isSupported) {
                b.b().f(a.this);
            }
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8569, new Class[0], Void.TYPE).isSupported) {
            long duration = 0;
            long j = this.c;
            if (j > 0) {
                duration = j - this.b;
            }
            io.reactivex.disposables.b bVar = this.g;
            if (bVar != null && !bVar.isDisposed()) {
                this.g.dispose();
            }
            String message = "";
            if (this.a != null) {
                message = this.h.node.macAddress + " " + LDSModel.LdsModelName.modelName(this.h.modelId) + "=" + this.h.value + " " + this.a.desc + " 耗时" + duration + "ms";
            }
            com.leedarson.log.elk.a p = com.leedarson.log.elk.a.y(a.class).t("BleMesh").e(MeshConstants.EVENT_DEVICE_CONTROL).o("info").b(8).p(message);
            b bVar2 = this.a;
            p.u("code", Integer.valueOf(bVar2 != null ? bVar2.code : -1)).u("mac", this.h.node.macAddress).u(TypedValues.TransitionType.S_DURATION, Long.valueOf(duration)).u("taskId", Long.valueOf(this.e)).u("inRhym", Boolean.valueOf(a())).a().b();
            b.d("上报task:" + this.e + ",message:" + message);
        }
    }

    public boolean a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8570, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        LightsRhythmService rhythmService = (LightsRhythmService) com.alibaba.android.arouter.launcher.a.c().g(LightsRhythmService.class);
        if (rhythmService != null) {
            return rhythmService.isMeshRhythm();
        }
        return false;
    }
}
