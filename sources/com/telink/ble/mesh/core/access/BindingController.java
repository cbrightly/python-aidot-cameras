package com.telink.ble.mesh.core.access;

import android.os.Handler;
import android.os.HandlerThread;
import androidx.work.WorkRequest;
import com.leedarson.serviceimpl.reporters.AddDeviceStepBean;
import com.leedarson.serviceimpl.reporters.b;
import com.leedarson.serviceimpl.reporters.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.MeshMessage;
import com.telink.ble.mesh.core.message.MeshSigModel;
import com.telink.ble.mesh.core.message.NotificationMessage;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.config.AppKeyAddMessage;
import com.telink.ble.mesh.core.message.config.AppKeyStatusMessage;
import com.telink.ble.mesh.core.message.config.CompositionDataGetMessage;
import com.telink.ble.mesh.core.message.config.CompositionDataStatusMessage;
import com.telink.ble.mesh.core.message.config.ModelAppBindMessage;
import com.telink.ble.mesh.core.message.config.ModelAppStatusMessage;
import com.telink.ble.mesh.entity.BindingDevice;
import com.telink.ble.mesh.entity.CompositionData;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;

public class BindingController {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String a = "Binding";
    private int b = 0;
    private int c;
    private byte[] d;
    private BindingDevice e;
    private int f = 0;
    private List<BindingModel> g = new ArrayList();
    private AccessBridge h;
    private Handler i;
    public String j = "";
    private b k;
    private int l;
    private Runnable m = new Runnable() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12154, new Class[0], Void.TYPE).isSupported) {
                e eVar = e.CODE_BIND_TIMEOUT;
                BindingController.a(BindingController.this, new AddDeviceStepBean(eVar.getDesc(), eVar.getCode()));
                BindingController.b(BindingController.this, "SUFUN.binding timeout");
            }
        }
    };

    static /* synthetic */ void a(BindingController x0, AddDeviceStepBean x1) {
        Class[] clsArr = {BindingController.class, AddDeviceStepBean.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 12152, clsArr, Void.TYPE).isSupported) {
            x0.d(x1);
        }
    }

    static /* synthetic */ void b(BindingController x0, String x1) {
        Class[] clsArr = {BindingController.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 12153, clsArr, Void.TYPE).isSupported) {
            x0.n(x1);
        }
    }

    public BindingController(HandlerThread handlerThread) {
        this.i = new Handler(handlerThread.getLooper());
    }

    public void v(AccessBridge accessBridge) {
        this.h = accessBridge;
    }

    public void x(b addDevicesReporter) {
        this.k = addDevicesReporter;
    }

    public b h() {
        return this.k;
    }

    public BindingDevice j() {
        return this.e;
    }

    public void e(int netKeyIndex, byte[] appKey, BindingDevice device) {
        if (!PatchProxy.proxy(new Object[]{new Integer(netKeyIndex), appKey, device}, this, changeQuickRedirect, false, 12132, new Class[]{Integer.TYPE, byte[].class, BindingDevice.class}, Void.TYPE).isSupported) {
            this.c = netKeyIndex;
            this.e = device;
            this.d = appKey;
            this.g.clear();
            this.f = 0;
            w();
            this.i.postDelayed(this.m, l() ? WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS : 60000);
            m("开始bind-(getCompositionData以及addAppkey过程，开启30s超时定时器) defaultBound? " + device.h());
            if (this.e.h()) {
                m("bind大步骤--addAppKey小步骤2--- add app key");
                c();
            } else if (this.e.c() == null) {
                m("bind大步骤--getCompositionData小步骤2--- getCompositionData");
                y();
            } else {
                s(this.e.c());
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void w() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 12133(0x2f65, float:1.7002E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            android.os.Handler r1 = r0.i
            if (r1 == 0) goto L_0x0020
            java.lang.Runnable r2 = r0.m
            r1.removeCallbacks(r2)
        L_0x0020:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.core.access.BindingController.w():void");
    }

    private void y() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12134, new Class[0], Void.TYPE).isSupported) {
            d(new AddDeviceStepBean(AddDeviceStepBean.STEP_BIND_GET_COMPOSITION_DATA));
            k();
        }
    }

    private void d(AddDeviceStepBean stepBean) {
        if (!PatchProxy.proxy(new Object[]{stepBean}, this, changeQuickRedirect, false, 12135, new Class[]{AddDeviceStepBean.class}, Void.TYPE).isSupported) {
            if (h() != null) {
                h().a(stepBean);
            }
        }
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12136, new Class[0], Void.TYPE).isSupported) {
            Handler handler = this.i;
            if (handler != null) {
                handler.removeCallbacksAndMessages((Object) null);
            }
            this.f = 0;
            this.b = 0;
            this.l = 0;
            this.g.clear();
        }
    }

    private boolean l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12137, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        BindingDevice bindingDevice = this.e;
        return bindingDevice != null && bindingDevice.b() == BindingBearer.GattOnly;
    }

    private void k() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12138, new Class[0], Void.TYPE).isSupported) {
            z(1);
            t(new CompositionDataGetMessage(this.e.d()));
        }
    }

    private void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12139, new Class[0], Void.TYPE).isSupported) {
            m("bind大步骤--addAppKey小步骤3--- addAppKey");
            m("SUFUN.add app key");
            z(2);
            AppKeyAddMessage command = new AppKeyAddMessage(this.e.d());
            command.K(this.c);
            command.J(this.e.a());
            command.I(this.d);
            t(command);
        }
    }

    private void t(MeshMessage meshMessage) {
        if (!PatchProxy.proxy(new Object[]{meshMessage}, this, changeQuickRedirect, false, 12140, new Class[]{MeshMessage.class}, Void.TYPE).isSupported) {
            if (this.h != null) {
                if (!l()) {
                    meshMessage.E(8);
                }
                if (!this.h.j(meshMessage, 1)) {
                    AddDeviceStepBean stepBean = null;
                    if (meshMessage instanceof CompositionDataGetMessage) {
                        StringBuilder sb = new StringBuilder();
                        e eVar = e.CODE_BIND_GET_COMPOSITION_DATA_FAIL;
                        sb.append(eVar.getDesc());
                        sb.append("(send CompositionDataGetMessage error)");
                        stepBean = new AddDeviceStepBean(sb.toString(), eVar.getCode());
                    } else if (meshMessage instanceof AppKeyAddMessage) {
                        StringBuilder sb2 = new StringBuilder();
                        e eVar2 = e.CODE_BIND_ADD_APPKEY_FAIL;
                        sb2.append(eVar2.getDesc());
                        sb2.append("(send AppKeyAddMessage error)");
                        stepBean = new AddDeviceStepBean(sb2.toString(), eVar2.getCode());
                    } else if (meshMessage instanceof ModelAppBindMessage) {
                        StringBuilder sb3 = new StringBuilder();
                        e eVar3 = e.CODE_BIND_MODELS_FAIL;
                        sb3.append(eVar3.getDesc());
                        sb3.append("(send ModelAppBindMessage error)");
                        stepBean = new AddDeviceStepBean(sb3.toString(), eVar3.getCode());
                    }
                    if (stepBean != null) {
                        d(stepBean);
                    }
                    n("SUFUN.binding message sent error");
                }
            }
        }
    }

    private void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12141, new Class[0], Void.TYPE).isSupported) {
            int size = this.g.size();
            int i2 = this.f;
            if (size > i2) {
                BindingModel bindingModel = this.g.get(i2);
                int modelId = bindingModel.a;
                ModelAppBindMessage command = new ModelAppBindMessage(this.e.d());
                int eleAdr = this.e.d() + bindingModel.b;
                command.J(eleAdr);
                command.I(this.e.a());
                command.L(bindingModel.c);
                command.K(modelId);
                m("SUFUN.bind next model: " + Integer.toHexString(modelId) + " at: " + Integer.toHexString(eleAdr));
                t(command);
                return;
            }
            p();
        }
    }

    private void z(int step) {
        Object[] objArr = {new Integer(step)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12142, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            m("SUFUN.upate step: " + step);
            this.b = step;
        }
    }

    private void s(CompositionData compositionData) {
        if (!PatchProxy.proxy(new Object[]{compositionData}, this, changeQuickRedirect, false, 12143, new Class[]{CompositionData.class}, Void.TYPE).isSupported) {
            List<BindingModel> modelsInCps = i(compositionData);
            if (modelsInCps == null || modelsInCps.size() == 0) {
                StringBuilder sb = new StringBuilder();
                e eVar = e.CODE_BIND_GET_COMPOSITION_DATA_FAIL;
                sb.append(eVar.getDesc());
                sb.append("(");
                sb.append("empty models in composition data");
                sb.append(")");
                d(new AddDeviceStepBean(sb.toString(), eVar.getCode()));
                n("empty models in composition data");
                return;
            }
            this.g.clear();
            this.f = 0;
            if (this.e.e() == null) {
                this.g.addAll(modelsInCps);
            } else {
                for (BindingModel bindingModel : modelsInCps) {
                    int[] e2 = this.e.e();
                    int length = e2.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        }
                        if (bindingModel.a == e2[i2]) {
                            this.g.add(bindingModel);
                            break;
                        }
                        i2++;
                    }
                }
            }
            if (this.g.size() == 0) {
                StringBuilder sb2 = new StringBuilder();
                e eVar2 = e.CODE_BIND_GET_COMPOSITION_DATA_FAIL;
                sb2.append(eVar2.getDesc());
                sb2.append("(no target models found)");
                d(new AddDeviceStepBean(sb2.toString(), eVar2.getCode()));
                n("getCompositionData fail");
                return;
            }
            m("SUFUN.models prepared: " + this.g.size());
            this.e.j(compositionData);
            AddDeviceStepBean stepBean = new AddDeviceStepBean(AddDeviceStepBean.STEP_BIND_GET_COMPOSITION_DATA_SUCCESS);
            this.l = 0;
            d(stepBean);
            m("bind大步骤--getCompositionData小步骤2--- success");
            d(new AddDeviceStepBean(AddDeviceStepBean.STEP_BIND_ADD_APP_KEY));
            c();
        }
    }

    private List<BindingModel> i(CompositionData compositionData) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{compositionData}, this, changeQuickRedirect, false, 12144, new Class[]{CompositionData.class}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        if (compositionData.elements == null) {
            return null;
        }
        List<BindingModel> models = new ArrayList<>();
        HashSet<Integer> ignoresHashSet = new HashSet<>();
        ignoresHashSet.add(2);
        ignoresHashSet.add(3);
        ignoresHashSet.add(4098);
        ignoresHashSet.add(4100);
        ignoresHashSet.add(4102);
        ignoresHashSet.add(4103);
        ignoresHashSet.add(4614);
        ignoresHashSet.add(4615);
        ignoresHashSet.add(4110);
        ignoresHashSet.add(4111);
        ignoresHashSet.add(4874);
        ignoresHashSet.add(4875);
        int offset = 0;
        for (CompositionData.Element ele : compositionData.elements) {
            List<Integer> list = ele.sigModels;
            if (list != null) {
                for (Integer intValue : list) {
                    int modelId = intValue.intValue();
                    if (!MeshSigModel.isConfigurationModel(modelId) && !ignoresHashSet.contains(Integer.valueOf(modelId))) {
                        models.add(new BindingModel(modelId, offset, true));
                    }
                }
            }
            List<Integer> list2 = ele.vendorModels;
            if (list2 != null) {
                for (Integer intValue2 : list2) {
                    int modelId2 = intValue2.intValue();
                    if (!ignoresHashSet.contains(Integer.valueOf(modelId2))) {
                        models.add(new BindingModel(modelId2, offset, false));
                    }
                }
            }
            offset++;
        }
        return models;
    }

    public void u(NotificationMessage message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 12145, new Class[]{NotificationMessage.class}, Void.TYPE).isSupported) {
            Opcode opcode = Opcode.valueOf(message.a());
            if (opcode != null) {
                switch (AnonymousClass2.a[opcode.ordinal()]) {
                    case 1:
                        if (this.b != 1) {
                            m("SUFUN.step not getting cps");
                            return;
                        } else {
                            s(((CompositionDataStatusMessage) message.d()).c());
                            return;
                        }
                    case 2:
                        if (this.b != 2) {
                            m("SUFUN.step not app key adding");
                            return;
                        }
                        AppKeyStatusMessage appKeyStatusMessage = (AppKeyStatusMessage) message.d();
                        if (appKeyStatusMessage.c() == 0) {
                            int protocolVer = this.e.g();
                            m("SUFUN.app key add success,protocolVersion=" + protocolVer);
                            if (this.e.h() || protocolVer >= SIGMesh.NEW_PROTOCOL) {
                                if (this.e.h()) {
                                    m("SUFUN.default bound complete");
                                }
                                if (protocolVer >= SIGMesh.NEW_PROTOCOL) {
                                    m("使用新版协议配网,skip key bind step,onBindSuccess");
                                }
                                d(new AddDeviceStepBean(AddDeviceStepBean.STEP_BIND_ADD_APP_KEY_SUCCESS));
                                m("bind大步骤--addAppKey小步骤3--- 成功");
                                m("bind大步骤--也就--- 成功");
                                p();
                                return;
                            }
                            z(3);
                            f();
                            return;
                        }
                        StringBuilder sb = new StringBuilder();
                        e eVar = e.CODE_BIND_ADD_APPKEY_FAIL;
                        sb.append(eVar.getDesc());
                        sb.append("(onMessageNotification status error,status:");
                        sb.append(appKeyStatusMessage.c());
                        sb.append(")");
                        d(new AddDeviceStepBean(sb.toString(), eVar.getCode()));
                        n("SUFUN.app key status error");
                        return;
                    case 3:
                        if (this.b != 3) {
                            m("SUFUN.step not app key binding");
                            return;
                        }
                        ModelAppStatusMessage appStatus = (ModelAppStatusMessage) message.d();
                        int size = this.g.size();
                        int i2 = this.f;
                        if (size > i2) {
                            int modelId = this.g.get(i2).a;
                            boolean sig = this.g.get(this.f).c;
                            if (modelId != appStatus.c()) {
                                m("SUFUN.model id error");
                                f();
                                return;
                            } else if (!sig || appStatus.d() == 0) {
                                this.f++;
                                f();
                                return;
                            } else {
                                StringBuilder sb2 = new StringBuilder();
                                e eVar2 = e.CODE_BIND_MODELS_FAIL;
                                sb2.append(eVar2.getDesc());
                                sb2.append("(onMessageNotification status error,status:");
                                sb2.append(appStatus.d());
                                sb2.append(",sig:");
                                sb2.append(sig);
                                sb2.append(")");
                                d(new AddDeviceStepBean(sb2.toString(), eVar2.getCode()));
                                n("SUFUN.mode app status error");
                                return;
                            }
                        } else {
                            return;
                        }
                    default:
                        return;
                }
            }
        }
    }

    /* renamed from: com.telink.ble.mesh.core.access.BindingController$2  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[Opcode.values().length];
            a = iArr;
            try {
                iArr[Opcode.COMPOSITION_DATA_STATUS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[Opcode.APPKEY_STATUS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[Opcode.MODE_APP_STATUS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public void q(boolean success, int i2, int i3, int i4) {
        Object[] objArr = {new Byte(success ? (byte) 1 : 0), new Integer(i2), new Integer(i3), new Integer(i4)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12146, new Class[]{Boolean.TYPE, cls, cls, cls}, Void.TYPE).isSupported) {
            int opcode = i2;
            int rspCount = i4;
            int rspMax = i3;
            if (!success) {
                String strOpcode = String.format(Locale.US, "opcode: %06X ", new Object[]{Integer.valueOf(opcode)});
                Opcode messageOpCode = Opcode.valueOf(opcode);
                Opcode opcode2 = Opcode.COMPOSITION_DATA_GET;
                if (opcode == opcode2.value || opcode == Opcode.APPKEY_ADD.value) {
                    int i5 = this.l;
                    if (i5 < 2) {
                        this.l = i5 + 1;
                        AddDeviceStepBean stepBean = new AddDeviceStepBean("bind complete fail(" + messageOpCode + "," + strOpcode + ",rspMax:" + rspMax + ",rspCount:" + rspCount + ")" + (messageOpCode + "失败，重试第:" + this.l + "次"), e.CODE_BIND_COMPLETE_FAIL.getCode());
                        if (opcode == opcode2.value) {
                            stepBean.setStepGetCompositionDataRetry(true);
                            d(stepBean);
                            y();
                        } else if (opcode == Opcode.APPKEY_ADD.value) {
                            stepBean.setStepAddAppKeyRetry(true);
                            d(stepBean);
                            c();
                        }
                    } else {
                        d(new AddDeviceStepBean("bind complete fail(" + messageOpCode + "," + strOpcode + ",rspMax:" + rspMax + ",rspCount:" + rspCount + ")" + (messageOpCode + "失败,已重试2次"), e.CODE_BIND_COMPLETE_FAIL.getCode()));
                        n("binding command send fail:");
                    }
                } else {
                    d(new AddDeviceStepBean("bind complete fail(" + messageOpCode + "," + strOpcode + ",rspMax:" + rspMax + ",rspCount:" + rspCount + ")", e.CODE_BIND_COMPLETE_FAIL.getCode()));
                    n("binding command send fail");
                }
            }
        }
    }

    private void n(String desc) {
        if (!PatchProxy.proxy(new Object[]{desc}, this, changeQuickRedirect, false, 12147, new Class[]{String.class}, Void.TYPE).isSupported) {
            m("SUFUN.binding fail: " + desc);
            r();
            o(0, desc);
        }
    }

    private void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12148, new Class[0], Void.TYPE).isSupported) {
            r();
            o(1, "binding success");
        }
    }

    private void r() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12149, new Class[0], Void.TYPE).isSupported) {
            g();
        }
    }

    private void o(int state, String desc) {
        AccessBridge accessBridge;
        Object[] objArr = {new Integer(state), desc};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12150, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported && (accessBridge = this.h) != null) {
            accessBridge.g(state, desc, 1, (Object) null);
        }
    }

    public class BindingModel {
        int a;
        int b;
        boolean c;

        public BindingModel(int modelId, int offset, boolean sig) {
            this.a = modelId;
            this.b = offset;
            this.c = sig;
        }
    }

    private void m(String logInfo) {
        if (!PatchProxy.proxy(new Object[]{logInfo}, this, changeQuickRedirect, false, 12151, new Class[]{String.class}, Void.TYPE).isSupported) {
            StringBuilder sb = new StringBuilder();
            sb.append(j().d());
            sb.append(",");
            sb.append(logInfo);
            sb.append(", BindingController.macAddress=");
            sb.append(this.j);
            sb.append(", meshController:");
            AccessBridge accessBridge = this.h;
            sb.append(accessBridge != null ? accessBridge.a() : "");
            MeshLog.i(sb.toString());
        }
    }
}
