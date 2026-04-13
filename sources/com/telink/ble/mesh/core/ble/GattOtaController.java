package com.telink.ble.mesh.core.ble;

import android.os.Handler;
import android.os.HandlerThread;
import com.leedarson.base.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.ble.GattRequest;
import com.telink.ble.mesh.util.MeshLogger;
import com.telink.ble.mesh.util.OtaPacketParser;
import meshsdk.MeshLogNew;
import meshsdk.ctrl.CmdCtrl;

public class GattOtaController {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String a = "GATT-OTA";
    protected Handler b;
    private GattConnection c;
    private GattOtaCallback d;
    private final OtaPacketParser e = new OtaPacketParser();
    private int f = 0;
    private int g = 100;
    private GattRequest.Callback h = new GattRequest.Callback() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void b(GattRequest request, Object obj) {
            Class[] clsArr = {GattRequest.class, Object.class};
            if (!PatchProxy.proxy(new Object[]{request, obj}, this, changeQuickRedirect, false, 12386, clsArr, Void.TYPE).isSupported) {
                GattOtaController.a(GattOtaController.this, request, obj);
            }
        }

        public void a(GattRequest request, String str) {
            Class[] clsArr = {GattRequest.class, String.class};
            if (!PatchProxy.proxy(new Object[]{request, str}, this, changeQuickRedirect, false, 12387, clsArr, Void.TYPE).isSupported) {
                GattOtaController.b(GattOtaController.this, request);
            }
        }

        public boolean c(GattRequest request) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{request}, this, changeQuickRedirect, false, 12388, new Class[]{GattRequest.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            GattOtaController.c(GattOtaController.this, request);
            return false;
        }
    };

    public interface GattOtaCallback {
        void a(int i);
    }

    static /* synthetic */ void a(GattOtaController x0, GattRequest x1, Object x2) {
        Class[] clsArr = {GattOtaController.class, GattRequest.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 12383, clsArr, Void.TYPE).isSupported) {
            x0.j(x1, x2);
        }
    }

    static /* synthetic */ void b(GattOtaController x0, GattRequest x1) {
        Class[] clsArr = {GattOtaController.class, GattRequest.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 12384, clsArr, Void.TYPE).isSupported) {
            x0.k(x1);
        }
    }

    static /* synthetic */ void c(GattOtaController x0, GattRequest x1) {
        Class[] clsArr = {GattOtaController.class, GattRequest.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 12385, clsArr, Void.TYPE).isSupported) {
            x0.l(x1);
        }
    }

    public GattOtaController(GattConnection gattConnection, HandlerThread handlerThread) {
        this.b = new Handler(handlerThread.getLooper());
        this.c = gattConnection;
    }

    public void w(GattOtaCallback callback) {
        this.d = callback;
    }

    public void d(byte[] firmware) {
        if (!PatchProxy.proxy(new Object[]{firmware}, this, changeQuickRedirect, false, 12361, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            e(firmware, 100);
        }
    }

    public void e(byte[] firmware, int readInterval) {
        if (!PatchProxy.proxy(new Object[]{firmware, new Integer(readInterval)}, this, changeQuickRedirect, false, 12362, new Class[]{byte[].class, Integer.TYPE}, Void.TYPE).isSupported) {
            h("GattOtaControll begin");
            f();
            this.e.m(firmware);
            this.g = readInterval;
            s();
        }
    }

    private void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12363, new Class[0], Void.TYPE).isSupported) {
            this.f = 0;
            this.e.a();
        }
    }

    private void v(GattRequest request) {
        GattConnection gattConnection;
        if (!PatchProxy.proxy(new Object[]{request}, this, changeQuickRedirect, false, 12364, new Class[]{GattRequest.class}, Void.TYPE).isSupported && (gattConnection = this.c) != null) {
            gattConnection.p0(request);
        }
    }

    public void p(byte[] data, int tag) {
        if (!PatchProxy.proxy(new Object[]{data, new Integer(tag)}, this, changeQuickRedirect, false, 12366, new Class[]{byte[].class, Integer.TYPE}, Void.TYPE).isSupported) {
            GattRequest cmd = GattRequest.b();
            cmd.b = UUIDInfo.b;
            cmd.a = UUIDInfo.a;
            cmd.e = (byte[]) data.clone();
            cmd.f = Integer.valueOf(tag);
            cmd.d = GattRequest.RequestType.WRITE_NO_RESPONSE;
            cmd.h = this.h;
            v(cmd);
        }
    }

    public int g() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12367, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.e.i();
    }

    private void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12368, new Class[0], Void.TYPE).isSupported) {
            if (this.e.k()) {
                n();
            }
        }
    }

    private void o() {
        GattOtaCallback gattOtaCallback;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12369, new Class[0], Void.TYPE).isSupported && (gattOtaCallback = this.d) != null) {
            gattOtaCallback.a(1);
        }
    }

    private void m() {
        GattOtaCallback gattOtaCallback;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12370, new Class[0], Void.TYPE).isSupported && (gattOtaCallback = this.d) != null) {
            gattOtaCallback.a(0);
        }
    }

    private void n() {
        GattOtaCallback gattOtaCallback;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12371, new Class[0], Void.TYPE).isSupported && (gattOtaCallback = this.d) != null) {
            gattOtaCallback.a(2);
        }
    }

    private void s() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12372, new Class[0], Void.TYPE).isSupported) {
            byte[] bytes = {0, -1};
            MeshLogNew.ota("发送 写入ota-prepare指令:" + e.b(bytes, ":"));
            p(bytes, 6);
        }
    }

    private void u() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12373, new Class[0], Void.TYPE).isSupported) {
            byte[] bytes = {1, -1};
            MeshLogNew.ota("发送 写入ota-start指令:" + e.b(bytes, ":"));
            p(bytes, 7);
        }
    }

    private void t() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12374, new Class[0], Void.TYPE).isSupported) {
            int index = this.e.e();
            byte[] data = {2, -1, (byte) (index & 255), (byte) ((index >> 8) & 255), (byte) ((~index) & 255), (byte) (((~index) >> 8) & 255)};
            MeshLogNew.ota("发送 写入ota-end指令:" + e.b(data, ":"));
            p(data, 8);
        }
    }

    private void r() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12375, new Class[0], Void.TYPE).isSupported) {
            if (this.e.j()) {
                p(this.e.f(), this.e.l() ? 3 : 1);
            }
        }
    }

    public void q(int index) {
        Object[] objArr = {new Integer(index)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12376, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.e.n(index);
            r();
        }
    }

    private boolean y() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12377, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        int i = this.g;
        if (i <= 0) {
            return false;
        }
        int sectionSize = i * 16;
        int sendTotal = this.e.g() * 16;
        if (sendTotal <= 0 || sendTotal % sectionSize != 0) {
            return false;
        }
        GattRequest cmd = GattRequest.b();
        cmd.a = UUIDInfo.a;
        cmd.b = UUIDInfo.b;
        cmd.d = GattRequest.RequestType.READ;
        cmd.f = 2;
        cmd.h = this.h;
        this.f++;
        v(cmd);
        return true;
    }

    private void j(GattRequest command, Object data) {
        if (!PatchProxy.proxy(new Object[]{command, data}, this, changeQuickRedirect, false, 12378, new Class[]{GattRequest.class, Object.class}, Void.TYPE).isSupported) {
            if (command.f.equals(6)) {
                MeshLogNew.ota("发送ota-prepare指令--成功");
                u();
            } else if (command.f.equals(7)) {
                MeshLogNew.ota("发送ota-start指令--成功");
                r();
            } else if (command.f.equals(8)) {
                MeshLogNew.ota("发送ota-end指令--成功");
                x();
                f();
                o();
            } else if (command.f.equals(3)) {
                t();
            } else if (command.f.equals(1)) {
                if (!y()) {
                    r();
                }
                x();
            } else if (!command.f.equals(2)) {
            } else {
                if (data != null) {
                    byte[] datas = (byte[]) data;
                    if (datas.length == 7) {
                        byte status = datas[2];
                        byte[] countBytes = new byte[4];
                        System.arraycopy(datas, 3, countBytes, 0, countBytes.length);
                        int count = CmdCtrl.getBigHex(countBytes, 4);
                        int packetCount = count / 16;
                        if (status != 0) {
                            MeshLogNew.otaWarn("ota-升级的固件包-丢包检测-:status = 1丢包,准备补发第:" + packetCount + "包,已接收(字节）:" + count + ",已发送(字节):" + (this.e.g() * 16) + ",接收包数:" + packetCount + ",发包数:" + this.e.g());
                            q(packetCount + -1);
                        } else if (packetCount == this.e.g()) {
                            r();
                        } else {
                            MeshLogNew.otaWarn("ota-升级的固件包-丢包检测-status=0，但是还是丢包,准备补发第:" + packetCount + "包,已接收(字节）:" + count + ",已发送(字节):" + (this.e.g() * 16) + ",接收包数:" + packetCount + ",发包数:" + this.e.g());
                            q(packetCount + -1);
                        }
                    } else {
                        MeshLogNew.otaWarn("ota-升级的固件包-丢包检测-数据包有误,继续发下一包》，return data:" + e.b(datas, ":"));
                        r();
                    }
                } else {
                    MeshLogNew.otaWarn("ota-升级的固件包-丢包检测-旧版本不支持，继续发包");
                    r();
                }
            }
        }
    }

    private void k(GattRequest request) {
        if (!PatchProxy.proxy(new Object[]{request}, this, changeQuickRedirect, false, 12379, new Class[]{GattRequest.class}, Void.TYPE).isSupported) {
            if (request.f.equals(8)) {
                x();
                f();
                MeshLogNew.otaWarn("发送ota-end指令--失败，之前的数据都发成功了，还是回调ota成功");
                o();
            } else if (request.f.equals(2)) {
                MeshLogNew.otaWarn("发送ota-检测指令--失败，继续发第" + this.e.g() + "包数据");
                r();
            } else {
                MeshLogNew.otaWarn("发送ota-tag:" + request.f + " 指令--失败, 回调ota失败");
                f();
                m();
            }
        }
    }

    private void l(GattRequest request) {
        if (!PatchProxy.proxy(new Object[]{request}, this, changeQuickRedirect, false, 12380, new Class[]{GattRequest.class}, Void.TYPE).isSupported) {
            if (request.f.equals(8)) {
                x();
                f();
                o();
            } else if (request.f.equals(2)) {
                MeshLogNew.otaWarn("发送ota-检测指令--超时，继续发一包数据");
                r();
            } else {
                f();
                m();
            }
        }
    }

    private void h(String logMessage) {
        if (!PatchProxy.proxy(new Object[]{logMessage}, this, changeQuickRedirect, false, 12381, new Class[]{String.class}, Void.TYPE).isSupported) {
            i(logMessage, 1);
        }
    }

    private void i(String logMessage, int level) {
        if (!PatchProxy.proxy(new Object[]{logMessage, new Integer(level)}, this, changeQuickRedirect, false, 12382, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            MeshLogger.g(logMessage, "GATT-OTA", level);
            MeshLogNew.ota(logMessage);
        }
    }
}
