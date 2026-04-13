package com.leedarson.newui;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.leedarson.R$anim;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.base.ui.BaseFragmentActivity;
import com.leedarson.base.utils.c;
import com.leedarson.bean.Constants;
import com.leedarson.event.FullScreenEvent;
import com.leedarson.serviceinterface.event.AddLiveEvent;
import com.leedarson.serviceinterface.event.AfterLocationPermissionGrantedEvent;
import com.leedarson.serviceinterface.event.ClearFragmentEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.OnPermissionsDeniedEvent;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.lang.reflect.Method;
import java.util.List;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.l;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class MainWebViewShowActivity extends BaseFragmentActivity {
    public static ChangeQuickRedirect changeQuickRedirect;
    private View A4;
    private RelativeLayout a2;
    private RelativeLayout p2;
    private Handler p3 = new Handler();
    /* access modifiers changed from: private */
    public WVJBWebView p4;
    /* access modifiers changed from: private */
    public LinearLayout z4;

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 2321, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
        }
    }

    public int S() {
        return R$layout.activity_main_web_view_show;
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2322, new Class[0], Void.TYPE).isSupported) {
            this.a2 = (RelativeLayout) findViewById(R$id.rl_web_container);
            this.p2 = (RelativeLayout) findViewById(R$id.rl_base_content);
            this.p4 = c.h().j();
            this.A4 = findViewById(R$id.video_container);
            LinearLayout linearLayout = (LinearLayout) findViewById(R$id.web_container);
            this.z4 = linearLayout;
            linearLayout.setVisibility(8);
            this.p3.postDelayed(new a(), 200);
        }
    }

    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2338, new Class[0], Void.TYPE).isSupported) {
                MainWebViewShowActivity.this.z4.setVisibility(0);
            }
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2323, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            Log.d("MainWebViewShow", "onResume: ");
            if (this.p4 == null) {
                this.p4 = c.h().j();
            }
            c.h().q();
            p0(this.p4);
        }
    }

    public void T() {
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Object[] objArr = {new Integer(keyCode), event};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 2324, new Class[]{Integer.TYPE, KeyEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_SYSTEM, "onBack", ""));
        return true;
    }

    public void finish() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2325, new Class[0], Void.TYPE).isSupported) {
            super.finish();
            overridePendingTransition(R$anim.ipc_slide_in_left, R$anim.ipc_slide_out_right);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0058, code lost:
        if (r1.equals("CloseWebViewShowEvent") != false) goto L_0x005c;
     */
    @org.greenrobot.eventbus.l(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onEvent(com.leedarson.serviceinterface.event.Event r10) {
        /*
            r9 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r10
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<com.leedarson.serviceinterface.event.Event> r0 = com.leedarson.serviceinterface.event.Event.class
            r6[r8] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2326(0x916, float:3.26E-42)
            r2 = r9
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x001d
            return
        L_0x001d:
            r0 = r9
            if (r10 == 0) goto L_0x0065
            java.lang.String r1 = r10.getKey()
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 == 0) goto L_0x002b
            goto L_0x0065
        L_0x002b:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "onEvent: "
            r1.append(r2)
            java.lang.String r2 = r10.getKey()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "MainWebViewShow"
            android.util.Log.d(r2, r1)
            java.lang.String r1 = r10.getKey()
            r2 = -1
            int r3 = r1.hashCode()
            switch(r3) {
                case 2107112316: goto L_0x0052;
                default: goto L_0x0051;
            }
        L_0x0051:
            goto L_0x005b
        L_0x0052:
            java.lang.String r3 = "CloseWebViewShowEvent"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0051
            goto L_0x005c
        L_0x005b:
            r8 = r2
        L_0x005c:
            switch(r8) {
                case 0: goto L_0x0060;
                default: goto L_0x005f;
            }
        L_0x005f:
            goto L_0x0064
        L_0x0060:
            r0.finish()
        L_0x0064:
            return
        L_0x0065:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.MainWebViewShowActivity.onEvent(com.leedarson.serviceinterface.event.Event):void");
    }

    private void e0(String routePath, String message, int containerViewId) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{routePath, message, new Integer(containerViewId)}, this, changeQuickRedirect, false, 2327, new Class[]{cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            Fragment fragment = (Fragment) com.alibaba.android.arouter.launcher.a.c().a(routePath).C();
            if (fragment != null) {
                Bundle bundle = new Bundle();
                bundle.putString("message", message);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                String deviceId = "";
                try {
                    deviceId = new JSONObject(message).getString("deviceId");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                transaction.add(containerViewId, fragment, deviceId);
                transaction.commitAllowingStateLoss();
                this.A4.setVisibility(0);
                getWindow().addFlags(128);
            }
        }
    }

    private void f0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2328, new Class[0], Void.TYPE).isSupported) {
            try {
                Method method = getSupportFragmentManager().getClass().getMethod("noteStateNotSaved", new Class[0]);
                method.setAccessible(true);
                method.invoke(getSupportFragmentManager(), new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void j0(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 2330, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                f0();
                FragmentManager manager = getSupportFragmentManager();
                Fragment fragment1 = manager.findFragmentByTag(deviceId);
                if (fragment1 != null) {
                    manager.beginTransaction().remove(fragment1).commit();
                    WVJBWebView wVJBWebView = this.p4;
                    if (wVJBWebView != null) {
                        wVJBWebView.z();
                    }
                }
                getWindow().clearFlags(128);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void g0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2331, new Class[0], Void.TYPE).isSupported) {
            try {
                WVJBWebView wVJBWebView = this.p4;
                if (wVJBWebView != null) {
                    wVJBWebView.z();
                }
                f0();
                FragmentManager manager = getSupportFragmentManager();
                for (Fragment fragment : manager.getFragments()) {
                    manager.beginTransaction().remove(fragment).commit();
                }
                getWindow().clearFlags(128);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onAddLiveEvent(AddLiveEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 2332, new Class[]{AddLiveEvent.class}, Void.TYPE).isSupported) {
            this.p2.setVisibility(0);
            WVJBWebView wVJBWebView = this.p4;
            if (wVJBWebView != null) {
                wVJBWebView.setBackgroundColor(0);
            }
            int skinType = 0;
            try {
                JSONObject jsonObject = new JSONObject(event.getData());
                int playerType = jsonObject.getInt(IjkMediaMeta.IJKM_KEY_TYPE);
                if (jsonObject.has("skinType")) {
                    skinType = jsonObject.getInt("skinType");
                }
                if (playerType != 0) {
                    return;
                }
                if (skinType == 2 || skinType == 4) {
                    e0("/ipc/easy_live/", event.getData(), R$id.video_container);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    @SuppressLint({"SourceLockedOrientationActivity"})
    public void onClearFragmentEvent(ClearFragmentEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 2333, new Class[]{ClearFragmentEvent.class}, Void.TYPE).isSupported) {
            try {
                this.A4.setVisibility(8);
                if (event != null) {
                    if (getResources().getConfiguration().orientation == 2) {
                        setRequestedOrientation(1);
                        getWindow().clearFlags(1024);
                    }
                    if (!TextUtils.isEmpty(event.getDeviceId())) {
                        j0(event.getDeviceId());
                    } else {
                        g0();
                    }
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onFullScreen(FullScreenEvent fullScreenEvent) {
        if (!PatchProxy.proxy(new Object[]{fullScreenEvent}, this, changeQuickRedirect, false, 2334, new Class[]{FullScreenEvent.class}, Void.TYPE).isSupported) {
            try {
                WVJBWebView wVJBWebView = this.p4;
                if (wVJBWebView != null) {
                    wVJBWebView.setVisibility(4);
                }
                this.p3.postDelayed(new b(), 150);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2339, new Class[0], Void.TYPE).isSupported) {
                if (MainWebViewShowActivity.this.p4 != null) {
                    MainWebViewShowActivity.this.p4.setVisibility(0);
                }
            }
        }
    }

    public void p0(WVJBWebView webView) {
        if (!PatchProxy.proxy(new Object[]{webView}, this, changeQuickRedirect, false, 2335, new Class[]{WVJBWebView.class}, Void.TYPE).isSupported) {
            if (webView == null) {
                if (c.h().j() == null) {
                    Log.e("attachWebview", "  MainWebViewSHowActivity.attachWebView  入传为空（webview==null）");
                    com.alibaba.android.arouter.launcher.a.c().a("/app/main/").D(this);
                    finish();
                    return;
                }
                Log.e("attachWebview", "MainWebViewSHowActivity.attachWebView  入传为空（webview==null） 正在尝试使用 getAppManager().getInstanceWebView()=" + c.h().j());
                webView = c.h().j();
            }
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            if (webView.getParent() == null || this.z4.hashCode() != webView.getParent().hashCode()) {
                o0(webView);
                this.z4.addView(webView, layoutParams);
            }
        }
    }

    @pub.devrel.easypermissions.a(137)
    public void resultForRequestLocation() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2336, new Class[0], Void.TYPE).isSupported) {
            org.greenrobot.eventbus.c.c().l(new AfterLocationPermissionGrantedEvent());
        }
    }

    public void Q(int requestCode, List<String> perms) {
        Object[] objArr = {new Integer(requestCode), perms};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2337, new Class[]{Integer.TYPE, List.class}, Void.TYPE).isSupported) {
            super.Q(requestCode, perms);
            org.greenrobot.eventbus.c.c().l(new OnPermissionsDeniedEvent(requestCode, perms));
        }
    }
}
