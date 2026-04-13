package com.telink.ble.mesh.foundation;

import android.content.Context;
import androidx.annotation.NonNull;
import com.leedarson.serviceimpl.elkstrays.b;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.MeshMessage;
import com.telink.ble.mesh.foundation.MeshController;
import com.telink.ble.mesh.foundation.parameter.AutoConnectParameters;
import com.telink.ble.mesh.foundation.parameter.BindingParameters;
import com.telink.ble.mesh.foundation.parameter.GattOtaParameters;
import com.telink.ble.mesh.foundation.parameter.ProvisioningParameters;
import com.telink.ble.mesh.foundation.parameter.ScanParameters;
import com.telink.ble.mesh.util.MeshLogger;
import java.security.Security;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import meshsdk.MeshLogNew;
import meshsdk.ctrl.BindCtrl;
import meshsdk.model.json.AddDevicesBean;
import org.spongycastle.jce.provider.BouncyCastleProvider;

public class MeshService implements MeshController.EventCallback {
    private static MeshService a = new MeshService();
    public static ChangeQuickRedirect changeQuickRedirect;
    private MeshController b;
    private MeshController c;
    private boolean d = true;
    private EventHandler e;
    private Context f;
    public MeshConfiguration g = null;
    private HashMap<String, MeshController> h = new HashMap<>();

    static {
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    public static MeshService k() {
        return a;
    }

    public boolean p() {
        return this.d;
    }

    public void u(boolean msgQueue) {
        this.d = msgQueue;
    }

    public void o(@NonNull Context context, EventHandler eventHandler) {
        Class[] clsArr = {Context.class, EventHandler.class};
        if (!PatchProxy.proxy(new Object[]{context, eventHandler}, this, changeQuickRedirect, false, 13266, clsArr, Void.TYPE).isSupported) {
            MeshLogger.d("MeshService#init");
            if (this.b == null) {
                this.b = new MeshController("主-MeshController");
            }
            this.b.V1(this);
            this.b.Z1(context);
            this.f = context;
            this.e = eventHandler;
        }
    }

    public int h() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13267, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.b.hashCode();
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13268, new Class[0], Void.TYPE).isSupported) {
            MeshLogger.d("MeshService#clear");
            MeshController meshController = this.b;
            if (meshController != null) {
                meshController.h2("MeshService clear");
                this.b = null;
            }
            MeshController meshController2 = this.c;
            if (meshController2 != null) {
                meshController2.h2("MeshService clear scanController stop");
                this.c = null;
            }
        }
    }

    public void w(MeshConfiguration configuration) {
        if (!PatchProxy.proxy(new Object[]{configuration}, this, changeQuickRedirect, false, 13269, new Class[]{MeshConfiguration.class}, Void.TYPE).isSupported) {
            this.g = configuration;
            this.b.Y1(configuration);
        }
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13270, new Class[0], Void.TYPE).isSupported) {
            this.b.g0();
        }
    }

    public int i() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13272, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.b.r0();
    }

    public int j(String macAddress) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{macAddress}, this, changeQuickRedirect, false, 13273, new Class[]{String.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (this.h.containsKey(macAddress)) {
            return this.h.get(macAddress).r0();
        }
        return this.b.r0();
    }

    public void q(int meshAddress) {
        Object[] objArr = {new Integer(meshAddress)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13274, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.b.N1(meshAddress);
        }
    }

    public void r(String macAddress, int meshAddress) {
        if (!PatchProxy.proxy(new Object[]{macAddress, new Integer(meshAddress)}, this, changeQuickRedirect, false, 13275, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if (this.h.containsKey(macAddress)) {
                this.h.get(macAddress).N1(meshAddress);
            }
        }
    }

    public MeshController.Mode f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13276, new Class[0], MeshController.Mode.class);
        if (proxy.isSupported) {
            return (MeshController.Mode) proxy.result;
        }
        MeshController meshController = this.b;
        if (meshController != null) {
            return meshController.s0();
        }
        return MeshController.Mode.MODE_IDLE;
    }

    public MeshController l() {
        return this.b;
    }

    public void D(ScanParameters scanParameters) {
        if (!PatchProxy.proxy(new Object[]{scanParameters}, this, changeQuickRedirect, false, 13277, new Class[]{ScanParameters.class}, Void.TYPE).isSupported) {
            if (this.c == null) {
                MeshController meshController = new MeshController("scan-MeshController");
                this.c = meshController;
                meshController.V1(this);
                this.c.Z1(this.f);
            }
            this.c.g2(scanParameters, false);
        }
    }

    public void F(String fromBz) {
        if (!PatchProxy.proxy(new Object[]{fromBz}, this, changeQuickRedirect, false, 13278, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.c.i2(fromBz);
        }
    }

    public boolean C(ProvisioningParameters provisioningParameters) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{provisioningParameters}, this, changeQuickRedirect, false, 13279, new Class[]{ProvisioningParameters.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : this.b.d2(provisioningParameters);
    }

    public boolean B(String macAddress, ProvisioningParameters provisioningParameters) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{macAddress, provisioningParameters}, this, changeQuickRedirect, false, 13280, new Class[]{String.class, ProvisioningParameters.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.h.containsKey(macAddress)) {
            return this.h.get(macAddress).d2(provisioningParameters);
        }
        return false;
    }

    public void A(List<AddDevicesBean> meshDevicesList) {
        if (!PatchProxy.proxy(new Object[]{meshDevicesList}, this, changeQuickRedirect, false, 13281, new Class[]{List.class}, Void.TYPE).isSupported) {
            this.b.i2("MeshService startMultiProvisioning 主Controll");
            for (int i = 0; i < meshDevicesList.size(); i++) {
                AddDevicesBean tempMeshDevicesAddBean = meshDevicesList.get(i);
                String mAddressKey = tempMeshDevicesAddBean.networkingDevice.bluetoothDevice.getAddress();
                if (this.h.containsKey(mAddressKey)) {
                    MeshLogNew.v("开始配网前, 释放设备:" + mAddressKey + " 旧的控制器资源");
                    this.h.get(mAddressKey).h2("MeshService startMultiProvisioning");
                    this.h.remove(mAddressKey);
                }
                MeshController meshController = new MeshController("配网-MeshController");
                meshController.U1(tempMeshDevicesAddBean.getTrackReport());
                meshController.J = mAddressKey;
                meshController.Z1(this.f);
                meshController.Y1(this.g);
                meshController.V1(this);
                this.h.put(mAddressKey, meshController);
                BindCtrl bindCtrl = tempMeshDevicesAddBean.mBindCtrl;
                bindCtrl.mMeshControllerHashCode = meshController.hashCode() + "";
                meshController.d2(meshDevicesList.get(i).mProvisionParams);
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void e(java.lang.String r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r9
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r6[r2] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 13282(0x33e2, float:1.8612E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x001d
            return
        L_0x001d:
            r0 = r8
            java.util.HashMap<java.lang.String, com.telink.ble.mesh.foundation.MeshController> r1 = r0.h
            java.util.Set r1 = r1.entrySet()
            java.util.Iterator r1 = r1.iterator()
            java.lang.String r2 = ""
        L_0x002a:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x0053
            java.lang.Object r3 = r1.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            java.lang.Object r4 = r3.getKey()
            java.lang.String r4 = (java.lang.String) r4
            java.lang.String r5 = ":"
            java.lang.String r6 = ""
            java.lang.String r4 = r4.replace(r5, r6)
            boolean r4 = r4.equals(r9)
            if (r4 == 0) goto L_0x0052
            java.lang.Object r4 = r3.getKey()
            r2 = r4
            java.lang.String r2 = (java.lang.String) r2
            goto L_0x0053
        L_0x0052:
            goto L_0x002a
        L_0x0053:
            java.util.HashMap<java.lang.String, com.telink.ble.mesh.foundation.MeshController> r3 = r0.h
            boolean r3 = r3.containsKey(r2)
            if (r3 == 0) goto L_0x0081
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "addStep: 释放连接:"
            r3.append(r4)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            com.leedarson.serviceimpl.reporters.b.d(r3)
            java.util.HashMap<java.lang.String, com.telink.ble.mesh.foundation.MeshController> r3 = r0.h
            java.lang.Object r3 = r3.get(r2)
            com.telink.ble.mesh.foundation.MeshController r3 = (com.telink.ble.mesh.foundation.MeshController) r3
            java.lang.String r4 = "MeshService disconnectBleConnectByMacAddress"
            r3.h2(r4)
            java.util.HashMap<java.lang.String, com.telink.ble.mesh.foundation.MeshController> r3 = r0.h
            r3.remove(r2)
        L_0x0081:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.foundation.MeshService.e(java.lang.String):void");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void v(java.lang.String r12) {
        /*
            r11 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r12
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r4 = java.lang.String.class
            r6[r2] = r4
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 13283(0x33e3, float:1.8613E-41)
            r2 = r11
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001d
            return
        L_0x001d:
            r1 = r11
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "批量配网流程结束，设置主控设备资源:"
            r2.append(r3)
            r2.append(r12)
            java.lang.String r3 = " 控制室:"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            meshsdk.MeshLogNew.v(r2)
            java.util.HashMap<java.lang.String, com.telink.ble.mesh.foundation.MeshController> r2 = r1.h
            java.util.Set r2 = r2.entrySet()
            java.util.Iterator r2 = r2.iterator()
            r3 = 0
            java.lang.String r4 = ""
        L_0x0045:
            boolean r5 = r2.hasNext()
            if (r5 == 0) goto L_0x0098
            java.lang.Object r5 = r2.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            java.lang.Object r6 = r5.getValue()
            com.telink.ble.mesh.foundation.MeshController r6 = (com.telink.ble.mesh.foundation.MeshController) r6
            boolean r6 = r6.K
            if (r6 != 0) goto L_0x0097
            java.lang.Object r6 = r5.getValue()
            com.telink.ble.mesh.foundation.MeshController r6 = (com.telink.ble.mesh.foundation.MeshController) r6
            int r7 = r6.w0()
            if (r7 <= r3) goto L_0x0097
            r3 = r7
            java.lang.Object r8 = r5.getKey()
            java.lang.String r8 = (java.lang.String) r8
            java.lang.String r9 = ":"
            java.lang.String r10 = ""
            java.lang.String r8 = r8.replace(r9, r10)
            boolean r8 = r8.equals(r12)
            if (r8 == 0) goto L_0x0097
            java.lang.Object r8 = r5.getKey()
            r4 = r8
            java.lang.String r4 = (java.lang.String) r4
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "ambitionMacKey:"
            r8.append(r9)
            r8.append(r4)
            java.lang.String r8 = r8.toString()
            meshsdk.MeshLogNew.v(r8)
        L_0x0097:
            goto L_0x0045
        L_0x0098:
            java.util.HashMap<java.lang.String, com.telink.ble.mesh.foundation.MeshController> r5 = r1.h
            boolean r5 = r5.containsKey(r4)
            if (r5 == 0) goto L_0x00f8
            java.util.HashMap<java.lang.String, com.telink.ble.mesh.foundation.MeshController> r5 = r1.h
            java.lang.Object r5 = r5.get(r4)
            com.telink.ble.mesh.foundation.MeshController r5 = (com.telink.ble.mesh.foundation.MeshController) r5
            r5.K = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r6 = "新的主控设备:"
            r0.append(r6)
            r0.append(r12)
            java.lang.String r6 = ",controller:"
            r0.append(r6)
            r0.append(r5)
            java.lang.String r0 = r0.toString()
            meshsdk.MeshLogNew.v(r0)
            java.lang.String r0 = "释放旧主控设备资源"
            meshsdk.MeshLogNew.v(r0)
            com.telink.ble.mesh.foundation.MeshController r0 = r1.b
            java.lang.String r6 = "MeshService setUpMasterControllerByMac"
            r0.h2(r6)
            r1.b = r5
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r6 = "当前主节点的action状态："
            r0.append(r6)
            com.telink.ble.mesh.foundation.MeshController r6 = r1.b
            com.telink.ble.mesh.foundation.MeshController$Mode r6 = r6.s0()
            r0.append(r6)
            java.lang.String r0 = r0.toString()
            meshsdk.MeshLogNew.v(r0)
            if (r3 <= 0) goto L_0x00f8
            com.telink.ble.mesh.foundation.MeshController r0 = r1.b
            r0.X1(r3)
        L_0x00f8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.foundation.MeshService.v(java.lang.String):void");
    }

    public void E() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13284, new Class[0], Void.TYPE).isSupported) {
            try {
                MeshLogNew.v("批量配网任务完成");
                for (Map.Entry<String, MeshController> item : this.h.entrySet()) {
                    if (!item.getValue().K) {
                        MeshController value = item.getValue();
                        MeshLogNew.v("释放 非主控 设备:" + item.getKey() + " 的资源,controller:" + value);
                        value.h2("MeshService stopMeshControllerAfterTaskFinished");
                    }
                }
                this.h.clear();
            } catch (Exception ex) {
                MeshLogger.c("SUFUN. Exception While stopMeshControllerAfterTaskFinished:" + ex.toString());
            }
        }
    }

    public void x(BindingParameters bindingParameters) {
        if (!PatchProxy.proxy(new Object[]{bindingParameters}, this, changeQuickRedirect, false, 13285, new Class[]{BindingParameters.class}, Void.TYPE).isSupported) {
            this.b.a2(bindingParameters);
        }
    }

    public void y(String macAddress, BindingParameters bindingParameters) {
        Class[] clsArr = {String.class, BindingParameters.class};
        if (!PatchProxy.proxy(new Object[]{macAddress, bindingParameters}, this, changeQuickRedirect, false, 13286, clsArr, Void.TYPE).isSupported) {
            if (this.h.containsKey(macAddress)) {
                this.h.get(macAddress).a2(bindingParameters);
            }
        }
    }

    public void a(AutoConnectParameters parameters) {
        if (!PatchProxy.proxy(new Object[]{parameters}, this, changeQuickRedirect, false, 13287, new Class[]{AutoConnectParameters.class}, Void.TYPE).isSupported) {
            b.a("真正进入调用组网接口autoConnect");
            this.b.e0(parameters);
        }
    }

    public void z(GattOtaParameters otaParameters) {
        if (!PatchProxy.proxy(new Object[]{otaParameters}, this, changeQuickRedirect, false, 13288, new Class[]{GattOtaParameters.class}, Void.TYPE).isSupported) {
            this.b.c2(otaParameters);
        }
    }

    public void n(boolean disconnect, String reason) {
        Object[] objArr = {new Byte(disconnect ? (byte) 1 : 0), reason};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13293, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            this.b.B0(disconnect, reason, true);
        }
    }

    public boolean t(MeshMessage meshMessage) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{meshMessage}, this, changeQuickRedirect, false, 13297, new Class[]{MeshMessage.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (meshMessage == null) {
            return false;
        }
        return this.b.T1(meshMessage);
    }

    public void b() {
        MeshController meshController;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13298, new Class[0], Void.TYPE).isSupported && (meshController = this.b) != null) {
            meshController.f0();
        }
    }

    public MeshMessage g() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13299, new Class[0], MeshMessage.class);
        if (proxy.isSupported) {
            return (MeshMessage) proxy.result;
        }
        MeshController meshController = this.b;
        if (meshController != null) {
            return meshController.q0();
        }
        return null;
    }

    public boolean m() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13300, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : this.b.v0();
    }

    public void s(boolean enable) {
        Object[] objArr = {new Byte(enable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13301, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.b.Q1(enable);
        }
    }

    public void onEventPrepared(Event<String> event) {
        EventHandler eventHandler;
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13305, new Class[]{Event.class}, Void.TYPE).isSupported && (eventHandler = this.e) != null) {
            eventHandler.onEventHandle(event);
        }
    }
}
