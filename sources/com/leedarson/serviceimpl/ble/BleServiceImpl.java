package com.leedarson.serviceimpl.ble;

import android.content.Context;
import android.util.Log;
import com.google.gson.internal.LinkedTreeMap;
import com.leedarson.serviceimpl.ble.manager.g;
import com.leedarson.serviceinterface.BleService;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.greenrobot.eventbus.c;

public class BleServiceImpl implements BleService {
    public static String a = "mesh_name";
    public static String b = "mesh_password";
    public static ChangeQuickRedirect changeQuickRedirect;
    Context c;
    private boolean d = true;

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0056, code lost:
        if (r14.equals("sartScan") != false) goto L_0x00b7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(java.lang.String r13, java.lang.String r14, java.lang.String r15) {
        /*
            r12 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 3
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r9 = 0
            r2[r9] = r13
            r10 = 1
            r2[r10] = r14
            r11 = 2
            r2[r11] = r15
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            r7[r9] = r0
            r7[r10] = r0
            r7[r11] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 6201(0x1839, float:8.69E-42)
            r3 = r12
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0027
            return
        L_0x0027:
            r0 = r12
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "handleData: "
            r2.append(r3)
            r2.append(r14)
            java.lang.String r3 = " data:"
            r2.append(r3)
            r2.append(r15)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "BleServiceImpl"
            android.util.Log.e(r3, r2)
            r2 = -1
            int r3 = r14.hashCode()
            switch(r3) {
                case -1884350534: goto L_0x00ab;
                case -1616913239: goto L_0x00a1;
                case 530405532: goto L_0x0097;
                case 686923427: goto L_0x008d;
                case 816904744: goto L_0x0083;
                case 1153192782: goto L_0x0079;
                case 1316781914: goto L_0x006e;
                case 1634507256: goto L_0x0064;
                case 1714778527: goto L_0x005a;
                case 2121659309: goto L_0x0050;
                default: goto L_0x004e;
            }
        L_0x004e:
            goto L_0x00b6
        L_0x0050:
            java.lang.String r3 = "sartScan"
            boolean r3 = r14.equals(r3)
            if (r3 == 0) goto L_0x004e
            goto L_0x00b7
        L_0x005a:
            java.lang.String r1 = "stopScan"
            boolean r1 = r14.equals(r1)
            if (r1 == 0) goto L_0x004e
            r1 = 5
            goto L_0x00b7
        L_0x0064:
            java.lang.String r1 = "getBuleToothStatus"
            boolean r1 = r14.equals(r1)
            if (r1 == 0) goto L_0x004e
            r1 = 7
            goto L_0x00b7
        L_0x006e:
            java.lang.String r1 = "startOta"
            boolean r1 = r14.equals(r1)
            if (r1 == 0) goto L_0x004e
            r1 = 8
            goto L_0x00b7
        L_0x0079:
            java.lang.String r1 = "setNewAddressAndMesh"
            boolean r1 = r14.equals(r1)
            if (r1 == 0) goto L_0x004e
            r1 = 6
            goto L_0x00b7
        L_0x0083:
            java.lang.String r1 = "startConnect"
            boolean r1 = r14.equals(r1)
            if (r1 == 0) goto L_0x004e
            r1 = r9
            goto L_0x00b7
        L_0x008d:
            java.lang.String r1 = "sendCommand"
            boolean r1 = r14.equals(r1)
            if (r1 == 0) goto L_0x004e
            r1 = r10
            goto L_0x00b7
        L_0x0097:
            java.lang.String r1 = "disconnect"
            boolean r1 = r14.equals(r1)
            if (r1 == 0) goto L_0x004e
            r1 = r11
            goto L_0x00b7
        L_0x00a1:
            java.lang.String r1 = "scanAgainForAndroid"
            boolean r1 = r14.equals(r1)
            if (r1 == 0) goto L_0x004e
            r1 = 4
            goto L_0x00b7
        L_0x00ab:
            java.lang.String r1 = "stopOta"
            boolean r1 = r14.equals(r1)
            if (r1 == 0) goto L_0x004e
            r1 = 9
            goto L_0x00b7
        L_0x00b6:
            r1 = r2
        L_0x00b7:
            switch(r1) {
                case 0: goto L_0x00dc;
                case 1: goto L_0x00d8;
                case 2: goto L_0x00d7;
                case 3: goto L_0x00d3;
                case 4: goto L_0x00cf;
                case 5: goto L_0x00cb;
                case 6: goto L_0x00c7;
                case 7: goto L_0x00c3;
                case 8: goto L_0x00bf;
                case 9: goto L_0x00bb;
                default: goto L_0x00ba;
            }
        L_0x00ba:
            goto L_0x00e0
        L_0x00bb:
            r0.n(r15, r13)
            goto L_0x00e0
        L_0x00bf:
            r0.m(r15)
            goto L_0x00e0
        L_0x00c3:
            r0.j(r15)
            goto L_0x00e0
        L_0x00c7:
            r0.o(r15)
            goto L_0x00e0
        L_0x00cb:
            r0.i(r15)
            goto L_0x00e0
        L_0x00cf:
            r0.k(r15)
            goto L_0x00e0
        L_0x00d3:
            r0.h(r15)
            goto L_0x00e0
        L_0x00d7:
            goto L_0x00e0
        L_0x00d8:
            r0.l(r15)
            goto L_0x00e0
        L_0x00dc:
            r0.a(r15)
        L_0x00e0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.ble.BleServiceImpl.handleData(java.lang.String, java.lang.String, java.lang.String):void");
    }

    public void init(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 6202, new Class[]{Context.class}, Void.TYPE).isSupported) {
            Log.e("BleServiceImpl", "init: ");
            this.c = context;
        }
    }

    private void a(String data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 6203, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (data != null) {
                c.c().l(new NeedPermissionEvent(94, data));
            }
        }
    }

    public void l(String data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 6204, new Class[]{String.class}, Void.TYPE).isSupported) {
            Log.e("com.tellink.ble.mesh", "=======sendCommand data：" + data);
            if (data != null) {
                c.c().l(new NeedPermissionEvent(96, data));
            }
        }
    }

    private void h(String data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 6205, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                LinkedTreeMap linkedTreeMap = g.b(data);
                if (linkedTreeMap != null) {
                    c.c().l(new NeedPermissionEvent(101, linkedTreeMap.get("meshName").toString()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void k(String data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 6206, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                LinkedTreeMap linkedTreeMap = g.b(data);
                Log.e("com.tellink.ble.mesh", "==========H5ModuleName.ACTION_BLE_SCAN_AGAIN: // BLE再次搜索 3 ");
                if (linkedTreeMap != null) {
                    String name = linkedTreeMap.get("meshName").toString();
                    if (this.d) {
                        c.c().l(new NeedPermissionEvent(100, name));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void i(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 6207, new Class[]{String.class}, Void.TYPE).isSupported) {
            c.c().l(new NeedPermissionEvent(99, ""));
        }
    }

    public void o(String data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 6208, new Class[]{String.class}, Void.TYPE).isSupported) {
            c.c().l(new NeedPermissionEvent(98, data));
        }
    }

    public void j(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 6209, new Class[]{String.class}, Void.TYPE).isSupported) {
            c c2 = c.c();
            c2.l(new JsCallH5ByNativeEvent("BLEService", "onGetBuleToothStatus", "{\"state\":" + SharePreferenceUtils.getPrefInt(this.c, "BLE_STATUS", 0) + "}"));
        }
    }

    public void m(String data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 6210, new Class[]{String.class}, Void.TYPE).isSupported) {
            c.c().l(new NeedPermissionEvent(97, data));
        }
    }

    public void n(String data, String callbackKey) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{data, callbackKey}, this, changeQuickRedirect, false, 6211, clsArr, Void.TYPE).isSupported) {
            c.c().l(new NeedPermissionEvent(95, data, callbackKey));
        }
    }
}
