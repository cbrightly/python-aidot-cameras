package com.leedarson.serviceimpl.bodyfat;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanRecord;
import android.os.Handler;
import android.os.Looper;
import androidx.core.view.MotionEventCompat;
import com.leedarson.serviceimpl.base.d;
import com.leedarson.serviceimpl.blec075.h;
import com.leedarson.serviceimpl.bodyfat.model.BFDeviceNotifyDataBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.glassfish.grizzly.http.server.util.MappingData;
import timber.log.a;

/* compiled from: BroadcastAnalyst */
public class e implements d {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String a = "BodyFat";
    private String b;
    /* access modifiers changed from: private */
    public boolean c = false;
    private Handler d;
    private b e = new b();
    private c f = c.INSTABLE;
    private BFDeviceNotifyDataBean g;
    private BFDeviceNotifyDataBean h;
    private a i;
    private boolean j = false;

    /* compiled from: BroadcastAnalyst */
    public interface a {
        void a(BFDeviceNotifyDataBean bFDeviceNotifyDataBean, String str);
    }

    /* compiled from: BroadcastAnalyst */
    public enum c {
        STABLE,
        INSTABLE;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public void g(String mac) {
        if (!PatchProxy.proxy(new Object[]{mac}, this, changeQuickRedirect, false, 6918, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.b = mac;
            com.leedarson.serviceimpl.base.c.k().x(this);
            if (this.d == null) {
                this.d = new Handler(Looper.getMainLooper());
            }
            this.c = false;
            this.j = true;
        }
    }

    public void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6919, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.base.c.k().B(this);
            Handler handler = this.d;
            if (handler != null) {
                handler.removeCallbacks(this.e);
            }
            this.c = false;
            this.j = false;
        }
    }

    public void onLeScan(BluetoothDevice bluetoothDevice, int i2, byte[] scanRecord, ScanRecord scanRecord2) {
        if (!PatchProxy.proxy(new Object[]{bluetoothDevice, new Integer(i2), scanRecord, scanRecord2}, this, changeQuickRedirect, false, 6920, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class, ScanRecord.class}, Void.TYPE).isSupported) {
            h.a unit = h.k(scanRecord).get((byte) -1);
            if (unit != null) {
                byte[] adData = unit.c;
                if (adData.length >= 14 && adData[0] == -84 && adData[1] == -96) {
                    a.b g2 = timber.log.a.g("BodyFat");
                    g2.m("广播称自定义数据:" + unit.toString(), new Object[0]);
                    byte[] macBytes = new byte[6];
                    System.arraycopy(adData, 2, macBytes, 0, macBytes.length);
                    if (com.leedarson.base.utils.e.a(macBytes).toUpperCase().equals(this.b.toUpperCase())) {
                        b(adData);
                        return;
                    }
                    a.b g3 = timber.log.a.g("BodyFat");
                    g3.n("收到非本设备的体脂称数据,本 mac:" + this.b, new Object[0]);
                }
            }
        }
    }

    public void b(byte[] customData) {
        int i2 = 1;
        if (!PatchProxy.proxy(new Object[]{customData}, this, changeQuickRedirect, false, 6921, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            byte[] measureData = new byte[6];
            System.arraycopy(customData, 8, measureData, 0, measureData.length);
            for (int i3 = 0; i3 < measureData.length; i3++) {
                measureData[i3] = (byte) (measureData[i3] ^ -96);
            }
            timber.log.a.g("BodyFat").a("广播称解密后测量数据为:" + com.leedarson.base.utils.e.a(measureData) + ",原始广播包:" + com.leedarson.base.utils.e.a(customData), new Object[0]);
            byte cmd = measureData[4];
            if (cmd == -83) {
                if (c(measureData) != 0) {
                    if ((measureData[0] & -128) != 0) {
                        i2 = 2;
                    }
                    BFDeviceNotifyDataBean notifyDataBean = new BFDeviceNotifyDataBean(i2);
                    notifyDataBean.weight1000 = c(measureData);
                    timber.log.a.g("BodyFat").h("解析后数据:" + notifyDataBean.toString(), new Object[0]);
                    if (notifyDataBean.type == 2) {
                        this.g = notifyDataBean;
                        return;
                    }
                    this.f = c.INSTABLE;
                    this.h = notifyDataBean;
                    this.g = null;
                    a aVar = this.i;
                    if (aVar != null) {
                        aVar.a(notifyDataBean, this.b);
                    }
                }
            } else if (cmd == -90 || cmd == -85) {
                int imp = 0;
                if (cmd == -90) {
                    imp = (int) c.a(new byte[]{measureData[0], measureData[1]}, 2);
                } else if (cmd == -85) {
                    imp = (int) c.a(new byte[]{measureData[2], measureData[3]}, 2);
                }
                timber.log.a.g("BodyFat").a("收到阻抗数据:" + imp, new Object[0]);
                BFDeviceNotifyDataBean bFDeviceNotifyDataBean = this.g;
                if (bFDeviceNotifyDataBean != null) {
                    bFDeviceNotifyDataBean.impedance = imp;
                    a aVar2 = this.i;
                    if (aVar2 != null) {
                        aVar2.a(bFDeviceNotifyDataBean, this.b);
                        this.g = null;
                        this.h = null;
                    }
                } else if (this.h != null) {
                    timber.log.a.g("BodyFat").c("未收到稳定数据,使用最后一次不稳定数据上报", new Object[0]);
                    BFDeviceNotifyDataBean bFDeviceNotifyDataBean2 = this.h;
                    bFDeviceNotifyDataBean2.type = 2;
                    bFDeviceNotifyDataBean2.impedance = imp;
                    a aVar3 = this.i;
                    if (aVar3 != null) {
                        aVar3.a(bFDeviceNotifyDataBean2, this.b);
                        this.h = null;
                    }
                }
            } else {
                timber.log.a.g("BodyFat").c("收到其他数据:" + cmd, new Object[0]);
            }
        }
    }

    public int c(byte[] measureData) {
        return ((measureData[1] << MappingData.PATH) & 196608) | ((measureData[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (measureData[3] & 255);
    }

    public void onScanFail(int errorCode) {
    }

    public String onFromBz() {
        return "bodyfat scan";
    }

    public void f(a dataReceiveListener) {
        this.i = dataReceiveListener;
    }

    /* compiled from: BroadcastAnalyst */
    public final class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6923, new Class[0], Void.TYPE).isSupported) {
                boolean unused = e.this.c = false;
            }
        }
    }

    public boolean d() {
        return this.j;
    }

    public void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6922, new Class[0], Void.TYPE).isSupported) {
            timber.log.a.g("BodyFat").h("蓝牙重启，广播称重新扫描", new Object[0]);
            h();
            g(this.b);
        }
    }
}
