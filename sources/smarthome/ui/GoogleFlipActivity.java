package smarthome.ui;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import com.leedarson.base.R$layout;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.base.ui.BaseFragmentActivity;
import com.leedarson.bean.Constants;
import com.leedarson.module_base.R$id;
import com.leedarson.serviceinterface.JsbridgeService;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import org.json.JSONObject;

public class GoogleFlipActivity extends BaseFragmentActivity {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean A4 = false;
    private final String a2 = "GoogleFlipActivity";
    private final String p2 = "com.google.flip_auth_result";
    /* access modifiers changed from: private */
    public CoreActivity p3;
    private AuthResultReceiver p4;
    /* access modifiers changed from: private */
    public Handler z4;

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 14129, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            ComponentName callingActivity = getCallingActivity();
            f0("onCreate taskId:" + getTaskId() + ",callingActivity:" + callingActivity);
            super.onCreate(savedInstanceState);
            StatusBarUtil.setStatusBarFullTransparent(this);
        }
    }

    public int S() {
        return R$layout.activity_google_flip;
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14130, new Class[0], Void.TYPE).isSupported) {
            Handler handler = new Handler();
            this.z4 = handler;
            if (!this.A4) {
                handler.postDelayed(new a(), 50);
            }
            this.z4.postDelayed(new b(), 1000);
        }
    }

    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14142, new Class[0], Void.TYPE).isSupported) {
                if (GoogleFlipActivity.this.p3 != null) {
                    GoogleFlipActivity.this.p3.o0(com.leedarson.base.utils.c.h().j());
                }
                GoogleFlipActivity.this.p0(com.leedarson.base.utils.c.h().j());
            }
        }
    }

    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14143, new Class[0], Void.TYPE).isSupported) {
                StatusBarUtil.setLightMode(GoogleFlipActivity.this);
            }
        }
    }

    public void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14131, new Class[0], Void.TYPE).isSupported) {
            this.p3 = (CoreActivity) com.leedarson.base.utils.c.h().k();
            j0();
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra("CLIENT_ID")) {
                f0("initBundleData extras:" + intent.getExtras().toString());
                CoreActivity coreActivity = this.p3;
                if (coreActivity != null) {
                    coreActivity.V1(intent, com.leedarson.base.utils.c.h().j());
                    return;
                }
                f0("zqr--从google home 进入 googleFlipActivity");
                this.A4 = true;
                Bundle bundle = new Bundle();
                bundle.putBoolean("fromGoogleHome", true);
                bundle.putParcelable("externalIntent", intent);
                com.alibaba.android.arouter.launcher.a.c().a("/app/main/").L(bundle).C();
            }
        }
    }

    private void f0(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 14132, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("GoogleFlipActivity").a(msg, new Object[0]);
        }
    }

    public void onNewIntent(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 14133, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            super.onNewIntent(intent);
            f0("onNewIntent:" + intent.toString());
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14134, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            Handler handler = this.z4;
            if (handler != null) {
                handler.removeCallbacksAndMessages((Object) null);
            }
            if (!this.A4) {
                o0(com.leedarson.base.utils.c.h().j());
                CoreActivity coreActivity = this.p3;
                if (coreActivity != null) {
                    coreActivity.p0(com.leedarson.base.utils.c.h().j());
                }
            }
            k0();
            f0("onDestroy:" + getTaskId());
        }
    }

    public void g0(String data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 14135, new Class[]{String.class}, Void.TYPE).isSupported) {
            Intent returnIntent = new Intent();
            int authStatus = -1;
            String authCode = "";
            try {
                JSONObject jsonObject = new JSONObject(data);
                if (jsonObject.has("authStatus")) {
                    authStatus = jsonObject.optInt("authStatus");
                }
                if (jsonObject.has("authCode")) {
                    authCode = jsonObject.optString("authCode");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            f0("zqr--authStatus:" + authStatus + ",authCode:" + authCode);
            if (authStatus == 0) {
                returnIntent.putExtra("AUTHORIZATION_CODE", authCode);
                setResult(-1, returnIntent);
            } else if (authStatus == -1 || authStatus == -3) {
                returnIntent.putExtra("ERROR_TYPE", 2);
                returnIntent.putExtra("ERROR_CODE", 13);
                setResult(-2, returnIntent);
            } else if (authStatus == -2) {
                returnIntent.putExtra("ERROR_TYPE", 2);
                returnIntent.putExtra("ERROR_CODE", 0);
                setResult(-2, returnIntent);
            }
            f0("zqr--收到认证回调数据，关闭googleFlipActivity");
            finish();
        }
    }

    public class AuthResultReceiver extends BroadcastReceiver {
        public static ChangeQuickRedirect changeQuickRedirect;

        AuthResultReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
            if (!PatchProxy.proxy(new Object[]{context, intent}, this, changeQuickRedirect, false, 14145, new Class[]{Context.class, Intent.class}, Void.TYPE).isSupported) {
                if ("com.google.flip_auth_result".equals(intent.getAction())) {
                    GoogleFlipActivity.this.z4.postDelayed(new a(intent.getStringExtra("data")), (long) intent.getIntExtra("delay", 0));
                }
            }
        }

        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ String c;

            a(String str) {
                this.c = str;
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14146, new Class[0], Void.TYPE).isSupported) {
                    GoogleFlipActivity.this.g0(this.c);
                }
            }
        }
    }

    private void j0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14136, new Class[0], Void.TYPE).isSupported) {
            if (this.p4 == null) {
                AuthResultReceiver authResultReceiver = new AuthResultReceiver();
                this.p4 = authResultReceiver;
                registerReceiver(authResultReceiver, new IntentFilter("com.google.flip_auth_result"));
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void k0() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 14137(0x3739, float:1.981E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            smarthome.ui.GoogleFlipActivity$AuthResultReceiver r1 = r0.p4
            if (r1 == 0) goto L_0x001e
            r0.unregisterReceiver(r1)
        L_0x001e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: smarthome.ui.GoogleFlipActivity.k0():void");
    }

    public void onBackPressed() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14138, new Class[0], Void.TYPE).isSupported) {
            e0(com.leedarson.base.utils.c.h().j(), Constants.SERVICE_SYSTEM, "onBack", "");
            Intent intent = new Intent();
            intent.putExtra("AUTHORIZATION_CODE", "");
            setResult(0, intent);
            finish();
        }
    }

    public void p0(WVJBWebView webView) {
        if (!PatchProxy.proxy(new Object[]{webView}, this, changeQuickRedirect, false, 14139, new Class[]{WVJBWebView.class}, Void.TYPE).isSupported) {
            super.p0(webView);
            findViewById(R$id.rl_web_container).setVisibility(0);
        }
    }

    public class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ WVJBWebView c;
        final /* synthetic */ String d;
        final /* synthetic */ String f;
        final /* synthetic */ String q;

        c(WVJBWebView wVJBWebView, String str, String str2, String str3) {
            this.c = wVJBWebView;
            this.d = str;
            this.f = str2;
            this.q = str3;
        }

        public void run() {
            WVJBWebView wVJBWebView;
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14144, new Class[0], Void.TYPE).isSupported) {
                JsbridgeService jsbridgeService = (JsbridgeService) com.alibaba.android.arouter.launcher.a.c().g(JsbridgeService.class);
                if (jsbridgeService != null && (wVJBWebView = this.c) != null) {
                    jsbridgeService.nativeCallJs(wVJBWebView, this.d, this.f, this.q);
                }
            }
        }
    }

    public void e0(WVJBWebView bridgeWebView, String service, String action, String data) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{bridgeWebView, service, action, data}, this, changeQuickRedirect, false, 14141, new Class[]{WVJBWebView.class, cls, cls, cls}, Void.TYPE).isSupported) {
            runOnUiThread(new c(bridgeWebView, service, action, data));
        }
    }
}
