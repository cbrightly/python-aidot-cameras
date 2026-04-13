package com.leedarson.serviceimpl.blec075;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.leedarson.base.utils.h;
import com.leedarson.base.utils.w;
import com.leedarson.bean.Constants;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.math.BigInteger;
import java.util.UUID;
import meshsdk.BaseResp;
import org.greenrobot.eventbus.c;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: LdsBleAdvertise */
public class e {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public BleC075ServiceImpl a;
    private BluetoothAdapter b;
    private BluetoothLeAdvertiser c;
    private b d;
    private AdvertiseSettings e;
    private AdvertiseData f;
    private int g = 30000;
    private Context h;
    /* access modifiers changed from: private */
    public String i = "BleC075ServiceImpl";
    private int j;
    /* access modifiers changed from: private */
    public String k;
    /* access modifiers changed from: private */
    public String l;
    int m = 0;
    private Handler n = new Handler();
    long o = System.currentTimeMillis();

    public e(BleC075ServiceImpl bleC075Service, Context context, String callbackKey) {
        this.a = bleC075Service;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.b = defaultAdapter;
        this.c = defaultAdapter.getBluetoothLeAdvertiser();
        this.h = context;
        this.k = callbackKey;
    }

    public void j() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6402, new Class[0], Void.TYPE).isSupported) {
            this.o = System.currentTimeMillis();
        }
    }

    public String h(int i2, int tid, String str, String cmdData) {
        Class<String> cls = String.class;
        Object[] objArr = {new Integer(i2), new Integer(tid), str, cmdData};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6403, new Class[]{cls2, cls2, cls, cls}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        int cmd = i2;
        String mac = str;
        this.j = tid;
        this.l = mac;
        this.m = cmd;
        this.e = new AdvertiseSettings.Builder().setAdvertiseMode(2).setTxPowerLevel(3).setTimeout(this.g).setConnectable(true).build();
        byte[] data = g(1, cmd, mac, cmdData);
        String serviceData = w.b(data);
        String serData = h.m(serviceData);
        UUID serviceUuid = new UUID(new BigInteger(serData.substring(0, 16), 16).longValue(), new BigInteger(serData.substring(16), 16).longValue());
        a.b g2 = timber.log.a.g(this.i);
        g2.c("rest - serviceUuid = " + serviceUuid.toString(), new Object[0]);
        this.f = new AdvertiseData.Builder().setIncludeDeviceName(false).setIncludeTxPowerLevel(true).addManufacturerData(65520, data).build();
        this.d = new b();
        if (this.c != null && this.b.isEnabled()) {
            a("开始触发广播....");
            this.c.startAdvertising(this.e, this.f, this.d);
        }
        return serviceData;
    }

    private void a(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 6404, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g(this.i);
            g2.a("一键复位： oneKeyReset:  -> " + str, new Object[0]);
        }
    }

    private byte[] g(int version, int i2, String mac, String cmdData) {
        Class<String> cls = String.class;
        Object[] objArr = {new Integer(version), new Integer(i2), mac, cmdData};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        Class[] clsArr = {cls2, cls2, cls, cls};
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6405, clsArr, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        int cmd = i2;
        byte[] params = new byte[16];
        params[0] = 17;
        params[1] = 21;
        params[2] = (byte) version;
        params[3] = (byte) this.j;
        byte[] macBytes = h.n(mac);
        System.arraycopy(macBytes, 0, params, 4, macBytes.length);
        params[10] = (byte) cmd;
        byte[] cmdDataBytes = f(cmd, cmdData);
        System.arraycopy(cmdDataBytes, 0, params, 11, cmdDataBytes.length);
        byte[] crcDataBytes = new byte[14];
        System.arraycopy(params, 0, crcDataBytes, 0, 14);
        byte[] crcBytes = h.c(h.b(crcDataBytes));
        System.arraycopy(crcBytes, 0, params, 14, crcBytes.length);
        return params;
    }

    public void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6406, new Class[0], Void.TYPE).isSupported) {
            timber.log.a.g(this.i).c("rest - 停止广播", new Object[0]);
            this.c.stopAdvertising(this.d);
        }
    }

    /* compiled from: LdsBleAdvertise */
    public class b extends AdvertiseCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        private b() {
        }

        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            if (!PatchProxy.proxy(new Object[]{settingsInEffect}, this, changeQuickRedirect, false, 6408, new Class[]{AdvertiseSettings.class}, Void.TYPE).isSupported) {
                super.onStartSuccess(settingsInEffect);
                a.b g = timber.log.a.g(e.this.i);
                g.c("rest - 开启服务成功 onStartSuccess isConnectable = " + settingsInEffect.isConnectable() + ", timeout = " + settingsInEffect.getTimeout(), new Object[0]);
                try {
                    e.this.a.scan(false);
                    JSONObject jsonObject5 = new JSONObject();
                    jsonObject5.put("code", 200);
                    c.c().l(new JsBridgeCallbackEvent(e.this.k, jsonObject5.toString()));
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("mac", (Object) e.this.l);
                    jsonObject3.put("step", 10);
                    c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_BLUETOOTH_NEW, "onOneKeyResetStatusChange", jsonObject3.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void onStartFailure(int errorCode) {
            if (!PatchProxy.proxy(new Object[]{new Integer(errorCode)}, this, changeQuickRedirect, false, 6409, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                super.onStartFailure(errorCode);
                a.b g = timber.log.a.g(e.this.i);
                g.c("rest - 开启服务失败 onStartFailure errorCode = " + errorCode, new Object[0]);
                com.leedarson.log.elk.a e = com.leedarson.log.elk.a.y(com.leedarson.serviceimpl.blec075.onekeyreset.h.class).t("LdsBle").e("OneKeyReset");
                e.p("开启广播服务失败 onStartFailure errorCode = " + errorCode).a().b();
                try {
                    e.this.i();
                    JSONObject jsonObject5 = new JSONObject();
                    jsonObject5.put("code", (int) BaseResp.ERR_MSG_SEND_FAIL);
                    jsonObject5.put(NotificationCompat.CATEGORY_MESSAGE, (Object) "蓝牙广播服务开启失败 errorCode = " + errorCode);
                    c.c().l(new JsBridgeCallbackEvent(e.this.k, jsonObject5.toString()));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public byte[] f(int cmd, String cmdData) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(cmd), cmdData}, this, changeQuickRedirect, false, 6407, new Class[]{Integer.TYPE, String.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (cmd == 1) {
            return new byte[]{0, 0, 0};
        }
        if (cmd == 2) {
            return new byte[]{0, 0, 0};
        }
        if (cmd == 3) {
            return TextUtils.isEmpty(cmdData) ? new byte[]{0, 0, 0} : h.n(cmdData);
        }
        if (cmd == 4) {
            return new byte[]{0, 0, 0};
        }
        return new byte[0];
    }
}
