package com.telink.bluetooth.light;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.bluetooth.event.b;
import com.telink.bluetooth.light.a;
import com.telink.bluetooth.light.e;

public abstract class LightService extends Service implements e.b, a.b {
    public static ChangeQuickRedirect changeQuickRedirect;
    protected e c;
    protected IBinder d;

    public IBinder onBind(Intent intent) {
        return this.d;
    }

    public void onCreate() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13781, new Class[0], Void.TYPE).isSupported) {
            super.onCreate();
            a.a().d(this);
            a.a().b();
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13782, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            a.a().d((a.b) null);
            a.a().c();
            e eVar = this.c;
            if (eVar != null) {
                eVar.W();
            }
        }
    }

    public void g(boolean disconnect) {
        e eVar;
        Object[] objArr = {new Byte(disconnect ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13786, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported && (eVar = this.c) != null) {
            eVar.G(disconnect);
        }
    }

    public void b(f controller, int mode, int i, int newStatus) {
        Object[] objArr = {controller, new Integer(mode), new Integer(i), new Integer(newStatus)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {f.class, cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13810, clsArr, Void.TYPE).isSupported) {
            g light = controller.N();
            Intent intent = new Intent();
            Log.e("weiweiwei", "onStatusChanged newStatus = " + newStatus);
            if (newStatus == 41) {
                intent.setAction("com.telink.bluetooth.light.ACTION_LE_SCAN_TIMEOUT");
            } else if (newStatus == 40) {
                intent.setAction("com.telink.bluetooth.light.ACTION_SCAN_COMPLETED");
            } else if (newStatus == 30) {
                intent.setAction("com.telink.bluetooth.light.ACTION_OFFLINE");
            } else if (newStatus == 13) {
                intent.setAction("com.telink.bluetooth.light.ACTION_UPDATE_MESH_COMPLETED");
            } else if (newStatus == 52) {
                OtaDeviceInfo deviceInfo = new OtaDeviceInfo();
                String N = light.N();
                deviceInfo.a1 = N;
                if (TextUtils.isEmpty(N)) {
                    deviceInfo.a1 = light.S();
                }
                deviceInfo.c = light.u();
                deviceInfo.a2 = controller.O();
                deviceInfo.z = newStatus;
                intent.setAction("com.telink.bluetooth.light.ACTION_STATUS_CHANGED");
                intent.putExtra("com.telink.bluetooth.light.EXTRA_MODE", mode);
                intent.putExtra("com.telink.bluetooth.light.EXTRA_DEVICE", deviceInfo);
            } else if (newStatus == 62) {
                StringBuilder sb = new StringBuilder();
                sb.append("STATUS_GET_FIRMWAREID_COMPLETED");
                k kVar = k.CHARACTERISTICFW_FIRMWARE;
                sb.append(light.M(kVar.getValue()));
                Log.e("weiweiwei", sb.toString());
                DeviceInfo deviceInfo2 = new DeviceInfo();
                deviceInfo2.q = light.O();
                deviceInfo2.c = light.u();
                deviceInfo2.z = 62;
                deviceInfo2.p1 = light.M(kVar.getValue());
                Log.e("weicb", "onStatusChanged meshAddress = " + deviceInfo2.q + "  status = " + deviceInfo2.z + "  deviceInfo.firmwareID = " + deviceInfo2.p1);
                intent.setAction("com.telink.bluetooth.light.ACTION_STATUS_CHANGED");
                intent.putExtra("com.telink.bluetooth.light.EXTRA_MODE", mode);
                intent.putExtra("com.telink.bluetooth.light.EXTRA_DEVICE", deviceInfo2);
                com.telink.a.b().a(b.d(this, "com.telink.bluetooth.light.EVENT_STATUS_CHANGED", deviceInfo2));
            } else {
                Log.e("weiweiwei ", " light: " + light.toString());
                DeviceInfo deviceInfo3 = new DeviceInfo();
                deviceInfo3.c = light.u();
                deviceInfo3.d = light.t();
                deviceInfo3.f = light.Q();
                deviceInfo3.q = light.O();
                deviceInfo3.x = light.R();
                deviceInfo3.y = light.U();
                deviceInfo3.z = newStatus;
                String N2 = light.N();
                deviceInfo3.a1 = N2;
                if (TextUtils.isEmpty(N2)) {
                    deviceInfo3.a1 = light.S();
                }
                Log.e("tel1", " deviceInfo.firmwareRevision : " + deviceInfo3.a1 + deviceInfo3.f);
                intent.setAction("com.telink.bluetooth.light.ACTION_STATUS_CHANGED");
                intent.putExtra("com.telink.bluetooth.light.EXTRA_MODE", mode);
                intent.putExtra("com.telink.bluetooth.light.EXTRA_DEVICE", deviceInfo3);
            }
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }

    public void e(g gVar, int mode, int opcode, int src, byte[] params, byte[] data) {
        Class<byte[]> cls = byte[].class;
        Object[] objArr = {gVar, new Integer(mode), new Integer(opcode), new Integer(src), params, data};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        Class[] clsArr = {g.class, cls2, cls2, cls2, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13811, clsArr, Void.TYPE).isSupported) {
            g light = gVar;
            Intent intent = new Intent();
            intent.setAction("com.telink.bluetooth.light.ACTION_NOTIFICATION");
            intent.putExtra("com.telink.bluetooth.light.EXTRA_MODE", mode);
            NotificationInfo notifyInfo = new NotificationInfo();
            notifyInfo.d = src;
            notifyInfo.c = opcode;
            notifyInfo.f = params;
            notifyInfo.q = data;
            if (light != null) {
                DeviceInfo deviceInfo = new DeviceInfo();
                deviceInfo.c = light.u();
                deviceInfo.d = light.t();
                deviceInfo.f = light.Q();
                deviceInfo.q = light.O();
                deviceInfo.x = light.R();
                deviceInfo.y = light.U();
                notifyInfo.x = deviceInfo;
            }
            intent.putExtra("com.telink.bluetooth.light.EXTRA_NOTIFY", notifyInfo);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }

    public void c(g light, int mode, com.telink.bluetooth.a command, boolean success) {
    }

    public void a(int errorCode) {
        Object[] objArr = {new Integer(errorCode)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13812, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            Intent intent = new Intent();
            intent.setAction("com.telink.bluetooth.light.ACTION_ERROR");
            intent.putExtra("com.telink.bluetooth.light.EXTRA_ERROR_CODE", errorCode);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }

    public void f(int stateCode, int errorCode, int deviceId) {
        Object[] objArr = {new Integer(stateCode), new Integer(errorCode), new Integer(deviceId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13813, clsArr, Void.TYPE).isSupported) {
            Intent intent = new Intent();
            intent.setAction("com.telink.bluetooth.light.ACTION_ERROR_REPORT");
            ErrorReportInfo errorReportInfo = new ErrorReportInfo();
            errorReportInfo.c = stateCode;
            errorReportInfo.d = errorCode;
            errorReportInfo.f = deviceId;
            intent.putExtra("com.telink.bluetooth.light.EXTRA_ERROR_REPORT_INFO", errorReportInfo);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }

    public boolean d(byte b, int i, byte[] bArr, Object obj, int i2) {
        Object[] objArr = {new Byte(b), new Integer(i), bArr, obj, new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13814, new Class[]{Byte.TYPE, cls, byte[].class, Object.class, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        int address = i;
        Object tag = obj;
        byte opcode = b;
        byte[] params = bArr;
        int delay = i2;
        e eVar = this.c;
        if (eVar == null) {
            return false;
        }
        return eVar.N(opcode, address, params, tag, delay);
    }
}
