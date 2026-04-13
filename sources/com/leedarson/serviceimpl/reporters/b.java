package com.leedarson.serviceimpl.reporters;

import android.os.Handler;
import android.os.Looper;
import com.leedarson.log.mgr.u;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import meshsdk.SIGMesh;
import meshsdk.model.json.AddDevicesBean;
import meshsdk.util.MeshConstants;
import timber.log.a;

/* compiled from: AddDevicesReporter */
public class b {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String a;
    private String b;
    private AddDevicesBean c;
    private Handler d = new Handler(Looper.getMainLooper());
    private Runnable e = new a();

    /* compiled from: AddDevicesReporter */
    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8525, new Class[0], Void.TYPE).isSupported) {
                b.this.g();
            }
        }
    }

    public void c(AddDevicesBean param) {
        if (!PatchProxy.proxy(new Object[]{param}, this, changeQuickRedirect, false, 8519, new Class[]{AddDevicesBean.class}, Void.TYPE).isSupported) {
            this.c = param;
            this.a = param.getMac();
            this.b = MeshConstants.TRACE_ID_ADD_DEVICES + this.a + param.getReportTraceId();
            a tracker = new a(SIGMesh.getInstance().getContext(), param);
            tracker.j(this.b, "BleMesh", MeshConstants.TRACE_ID_ADD_DEVICES);
            u.d().b(tracker);
            this.d.postDelayed(this.e, 120000);
        }
    }

    public void a(AddDeviceStepBean stepBean) {
        if (!PatchProxy.proxy(new Object[]{stepBean}, this, changeQuickRedirect, false, 8520, new Class[]{AddDeviceStepBean.class}, Void.TYPE).isSupported) {
            stepBean.step += "(mac:" + this.a + ")";
            timber.log.a.g("AddDevicesReporter").m("addStep:" + stepBean.step + ",thread:" + Thread.currentThread().getName(), new Object[0]);
            u.d().a(this.b, stepBean);
        }
    }

    public void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8521, new Class[0], Void.TYPE).isSupported) {
            this.d.removeCallbacks(this.e);
            timber.log.a.g("AddDevicesReporter").m("上报埋点", new Object[0]);
            u.d().e(this.b);
        }
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8522, new Class[0], Void.TYPE).isSupported) {
            a(new AddDeviceStepBean(AddDeviceStepBean.STEP_ADD_MESH_TIMEOUT, e.CODE_ADD_DEVICE_TIMEOUT.getCode()));
            u.d().e(this.b);
        }
    }

    public static void d(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, (Object) null, changeQuickRedirect, true, 8523, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g("AddDevicesReporter");
            g.m(log + ",thread:" + Thread.currentThread().getName(), new Object[0]);
        }
    }

    public static void e(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, (Object) null, changeQuickRedirect, true, 8524, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g("AddDevicesReporter");
            g.n(log + ",thread:" + Thread.currentThread().getName(), new Object[0]);
        }
    }

    public AddDevicesBean b() {
        return this.c;
    }
}
