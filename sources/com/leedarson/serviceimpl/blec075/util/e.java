package com.leedarson.serviceimpl.blec075.util;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.w;
import com.leedarson.serviceinterface.LightsRhythmService;
import com.leedarson.serviceinterface.event.IBluetoothEnableStatueChange;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONException;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.a;

/* compiled from: PermissionCompat */
public class e {
    public static ChangeQuickRedirect changeQuickRedirect;
    int a = LightsRhythmService.ERR_REFUSE_ONE_TIME_PERMISSION;
    private final int b = -1;
    private final int c = 0;
    private final int d = 1;
    private final int e = 2;
    /* access modifiers changed from: private */
    public Activity f;
    int g = 0;
    private final String h = "LDS_ALREADY_ASK_KEY_android.permission.BLUETOOTH_SCAN";
    private final String i = "LDS_ALREADY_ASK_KEY_android.permission.BLUETOOTH_CONNECT";

    static /* synthetic */ void a(e x0, com.tbruyelle.rxpermissions2.b x1, String x2) {
        Class[] clsArr = {e.class, com.tbruyelle.rxpermissions2.b.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 6615, clsArr, Void.TYPE).isSupported) {
            x0.e(x1, x2);
        }
    }

    static /* synthetic */ void c(e x0, int x1, String x2, String x3) {
        Class<String> cls = String.class;
        Object[] objArr = {x0, new Integer(x1), x2, x3};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 6616, new Class[]{e.class, Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
            x0.m(x1, x2, x3);
        }
    }

    static /* synthetic */ void d(e x0, int x1, String x2) {
        Object[] objArr = {x0, new Integer(x1), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 6617, new Class[]{e.class, Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            x0.l(x1, x2);
        }
    }

    public e(Activity activity) {
        this.f = activity;
    }

    public io.reactivex.disposables.b f(com.tbruyelle.rxpermissions2.b bVar, String data, String str) {
        int status;
        Class<String> cls = String.class;
        int status2 = 1;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bVar, data, str}, this, changeQuickRedirect, false, 6608, new Class[]{com.tbruyelle.rxpermissions2.b.class, cls, cls}, io.reactivex.disposables.b.class);
        if (proxy.isSupported) {
            return (io.reactivex.disposables.b) proxy.result;
        }
        com.tbruyelle.rxpermissions2.b mPermisions = bVar;
        String callbackKey = str;
        boolean autoRequestPermission = true;
        try {
            autoRequestPermission = new JSONObject(data).optBoolean("autoRequestPermission", true);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        String[] perms = {"android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT"};
        BluetoothAdapter bleAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!autoRequestPermission) {
            if (!w.R()) {
                if (bleAdapter == null || !bleAdapter.isEnabled()) {
                    status2 = -1;
                }
                l(status2, callbackKey);
            } else if (!g(mPermisions, perms)) {
                boolean locationPermissionDenied = SharePreferenceUtils.getPrefBoolean(this.f, "nearby_permission_denied", false);
                timber.log.a.i("  sufun.permision.check.Manifest.permission.BLUETOOTH_SCAN=" + EasyPermissions.h(this.f, perms[0]), new Object[0]);
                timber.log.a.i("  sufun.permision.check.Manifest.permission.BLUETOOTH_CONNECT=" + EasyPermissions.h(this.f, perms[1]), new Object[0]);
                if (locationPermissionDenied) {
                    if (EasyPermissions.h(this.f, perms[0]) || EasyPermissions.h(this.f, perms[1])) {
                        status = 2;
                    } else {
                        status = 0;
                    }
                    l(status, callbackKey);
                } else {
                    mPermisions.o(this.f, perms).Y(new a(this, new AtomicInteger(), callbackKey), b.c);
                }
            } else {
                if (bleAdapter == null || !bleAdapter.isEnabled()) {
                    status2 = -1;
                }
                l(status2, callbackKey);
            }
            return null;
        }
        k(mPermisions, callbackKey);
        return null;
    }

    /* access modifiers changed from: private */
    /* renamed from: h */
    public /* synthetic */ void i(AtomicInteger _countPermisionCallBack, String callbackKey, Boolean aBoolean) {
        if (!PatchProxy.proxy(new Object[]{_countPermisionCallBack, callbackKey, aBoolean}, this, changeQuickRedirect, false, 6614, new Class[]{AtomicInteger.class, String.class, Boolean.class}, Void.TYPE).isSupported) {
            _countPermisionCallBack.set(_countPermisionCallBack.get() + 1);
            if (aBoolean.booleanValue() || (!SharePreferenceUtils.getPrefBoolean(BaseApplication.b(), "LDS_ALREADY_ASK_KEY_android.permission.BLUETOOTH_SCAN", false) && !SharePreferenceUtils.getPrefBoolean(BaseApplication.b(), "LDS_ALREADY_ASK_KEY_android.permission.BLUETOOTH_CONNECT", false))) {
                l(2, callbackKey);
            } else {
                l(0, callbackKey);
            }
        }
    }

    static /* synthetic */ void j(Throwable throwable) {
    }

    public boolean g(com.tbruyelle.rxpermissions2.b rxPermissions, String... arr) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{rxPermissions, arr}, this, changeQuickRedirect, false, 6609, new Class[]{com.tbruyelle.rxpermissions2.b.class, String[].class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        for (String item : arr) {
            if (!rxPermissions.f(item)) {
                return false;
            }
        }
        return true;
    }

    public io.reactivex.disposables.b k(com.tbruyelle.rxpermissions2.b mPermisions, String callbackKey) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{mPermisions, callbackKey}, this, changeQuickRedirect2, false, 6610, new Class[]{com.tbruyelle.rxpermissions2.b.class, String.class}, io.reactivex.disposables.b.class);
        if (proxy.isSupported) {
            return (io.reactivex.disposables.b) proxy.result;
        }
        if (w.R()) {
            String[] perms = {"android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_ADVERTISE"};
            return mPermisions.l(perms).Y(new a(perms, mPermisions, callbackKey), new b());
        }
        e(mPermisions, callbackKey);
        return null;
    }

    /* compiled from: PermissionCompat */
    public class a implements io.reactivex.functions.e<com.tbruyelle.rxpermissions2.a> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String[] c;
        final /* synthetic */ com.tbruyelle.rxpermissions2.b d;
        final /* synthetic */ String f;

        a(String[] strArr, com.tbruyelle.rxpermissions2.b bVar, String str) {
            this.c = strArr;
            this.d = bVar;
            this.f = str;
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 6619, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((com.tbruyelle.rxpermissions2.a) obj);
            }
        }

        public void a(com.tbruyelle.rxpermissions2.a permission) {
            if (!PatchProxy.proxy(new Object[]{permission}, this, changeQuickRedirect, false, 6618, new Class[]{com.tbruyelle.rxpermissions2.a.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("BleC075ServiceImpl");
                g.c("permissionCompat accept:" + permission.a + "," + permission.b, new Object[0]);
                BaseApplication b = BaseApplication.b();
                StringBuilder sb = new StringBuilder();
                sb.append("LDS_ALREADY_ASK_KEY_");
                sb.append(permission.a);
                SharePreferenceUtils.setPrefBoolean(b, sb.toString(), true);
                if (permission.b) {
                    e eVar = e.this;
                    int i = eVar.g + 1;
                    eVar.g = i;
                    if (i == this.c.length) {
                        e.a(eVar, this.d, this.f);
                    }
                } else if (permission.c) {
                    SharePreferenceUtils.setPrefBoolean(e.this.f, "nearby_permission_denied", true);
                    e.c(e.this, 2, this.f, "单次拒绝，下次仍可提示");
                } else {
                    e.c(e.this, 0, this.f, "永久拒绝，不再提示");
                }
            }
        }
    }

    /* compiled from: PermissionCompat */
    public class b implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 6621, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 6620, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                throwable.printStackTrace();
            }
        }
    }

    private void e(com.tbruyelle.rxpermissions2.b bVar, String callbackKey) {
        if (!PatchProxy.proxy(new Object[]{bVar, callbackKey}, this, changeQuickRedirect, false, 6611, new Class[]{com.tbruyelle.rxpermissions2.b.class, String.class}, Void.TYPE).isSupported) {
            BluetoothAdapter bleAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bleAdapter == null || bleAdapter.isEnabled()) {
                l(1, callbackKey);
                return;
            }
            NeedPermissionEvent tempPermision = new NeedPermissionEvent(24, callbackKey);
            tempPermision.mBluetoothOpenHandler = new c(callbackKey);
            org.greenrobot.eventbus.c.c().l(tempPermision);
        }
    }

    /* compiled from: PermissionCompat */
    public class c implements IBluetoothEnableStatueChange {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        c(String str) {
            this.a = str;
        }

        public void onOpenSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6622, new Class[0], Void.TYPE).isSupported) {
                e.d(e.this, 1, this.a);
                com.leedarson.log.sensorsdata.a.b().r("ClickBluetoothPermissionSystemSettings", true);
            }
        }

        public void onOpenFail() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6623, new Class[0], Void.TYPE).isSupported) {
                e.d(e.this, -1, this.a);
                com.leedarson.log.sensorsdata.a.b().r("ClickBluetoothPermissionSystemSettings", false);
            }
        }
    }

    private void m(int status, String callbackKey, String extra) {
        Class<String> cls = String.class;
        Object[] objArr = {new Integer(status), callbackKey, extra};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6612, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
            JSONObject joGetPermission = new JSONObject();
            JSONObject joGetPermissionData = new JSONObject();
            try {
                joGetPermission.put("code", 200);
                joGetPermissionData.put("status", status);
                joGetPermissionData.put("desc", (Object) extra);
                joGetPermission.put("data", (Object) joGetPermissionData);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, joGetPermission.toString()));
        }
    }

    private void l(int status, String callbackKey) {
        Object[] objArr = {new Integer(status), callbackKey};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6613, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            m(status, callbackKey, "");
        }
    }
}
