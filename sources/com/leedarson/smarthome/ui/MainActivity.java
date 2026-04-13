package com.leedarson.smarthome.ui;

import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.iotsolution.preaidot.R;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.c;
import com.leedarson.serviceinterface.BleMeshService;
import com.leedarson.serviceinterface.event.JpushMessageEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.smarthome.robust.h;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Locale;
import me.leolin.shortcutbadger.b;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.l;
import org.json.JSONObject;
import smarthome.ui.CoreActivity;
import smarthome.utils.k;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class MainActivity extends CoreActivity {
    public static ChangeQuickRedirect changeQuickRedirect;
    private long c6 = 0;
    long d6 = System.currentTimeMillis();
    long e6 = System.currentTimeMillis();

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 10721, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            r2();
            registerReceiver(BaseApplication.b().B4, new IntentFilter("android.intent.action.LOCALE_CHANGED"));
            super.onCreate(savedInstanceState);
            this.c6 = System.currentTimeMillis();
            timber.log.a.i("BLE.TRV MainActivity.onCreate", new Object[0]);
        }
    }

    public void s2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10722, new Class[0], Void.TYPE).isSupported) {
            new h().o();
        }
    }

    private void r2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10723, new Class[0], Void.TYPE).isSupported) {
            String languages = k.a("languages.json", this);
            timber.log.a.i("lang---->   languages" + languages, new Object[0]);
            SharePreferenceUtils.setPrefString(this, "repositoryName", "M071-AiDot");
            SharePreferenceUtils.setPrefBoolean(this, "HAVE_REMOTE", false);
            SharePreferenceUtils.setPrefString(this, "LANGUAGES", languages);
            SharePreferenceUtils.setPrefString(this, "DEFAULT_LANGUAGE", "en-US");
            SharePreferenceUtils.setPrefString(this, "REMOTE_URL", "");
            SharePreferenceUtils.setPrefString(this, "WEB_VERSION", "3.47.0");
            SharePreferenceUtils.setPrefString(this, "APP_ID", "1392315867093508098");
            SharePreferenceUtils.setPrefBoolean(this, "haveWebserver", true);
            SharePreferenceUtils.setPrefString(this, "TENANT_ID", "11");
            SharePreferenceUtils.setPrefString(this, "ONEKEY_LOGIN_KEY", "");
            SharePreferenceUtils.setPrefString(this, "WXAPPID", "");
            SharePreferenceUtils.setPrefString(this, "WXSECRET", "");
            SharePreferenceUtils.setPrefString(this, "QQSECRET", "");
            SharePreferenceUtils.setPrefString(this, "QQAPPKEY", "");
            SharePreferenceUtils.setPrefString(this, "PACK_SERVER_ENV", "pre");
            SharePreferenceUtils.setPrefBoolean(this, "is_first_ipc", true);
            p2();
        }
    }

    private void p2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10724, new Class[0], Void.TYPE).isSupported) {
            if (TextUtils.isEmpty(SharePreferenceUtils.getPrefString(BaseApplication.b(), IjkMediaMeta.IJKM_KEY_LANGUAGE, ""))) {
                Locale locale = PubUtils.getCurrentSystemLocal();
                String systemLocale = locale.getLanguage() + "-" + locale.getCountry();
                SharePreferenceUtils.setPrefString(BaseApplication.b(), "KEY_CURRENT_SYSTEM_LANGUAGE", systemLocale);
                timber.log.a.g("mainActivity").m("lang---->(初次安装)systemLocal=" + systemLocale, new Object[0]);
                String matchLanguageLocal = Q1(systemLocale);
                SharePreferenceUtils.setPrefString(BaseApplication.b(), IjkMediaMeta.IJKM_KEY_LANGUAGE, matchLanguageLocal);
                timber.log.a.g("mainActivity").m("lang---->(初次安装)matchLanguageLocal=" + matchLanguageLocal, new Object[0]);
                return;
            }
            String _currentSpSystemLanguage = SharePreferenceUtils.getPrefString(BaseApplication.b(), "KEY_CURRENT_SYSTEM_LANGUAGE", "");
            Locale locale2 = PubUtils.getCurrentSystemLocal();
            String _systemLocaleStr = locale2.getLanguage() + "-" + locale2.getCountry();
            Locale locale3 = Locale.US;
            if (_currentSpSystemLanguage.toUpperCase(locale3).equals(_systemLocaleStr.toUpperCase(locale3))) {
                timber.log.a.g("mainActivity").m("lang---->(非初次安装不需要更新)_currentSpSystemLanguage=" + _currentSpSystemLanguage + ",_systemLocaleStr=" + _systemLocaleStr, new Object[0]);
                return;
            }
            SharePreferenceUtils.setPrefString(BaseApplication.b(), "KEY_CURRENT_SYSTEM_LANGUAGE", _systemLocaleStr);
            String matchLanguageLocal2 = Q1(_systemLocaleStr);
            SharePreferenceUtils.setPrefString(BaseApplication.b(), IjkMediaMeta.IJKM_KEY_LANGUAGE, matchLanguageLocal2);
            timber.log.a.g("mainActivity").m("lang---->(非初次安装)_currentSpSystemLanguage=" + _currentSpSystemLanguage + ",_systemLocaleStr=" + _systemLocaleStr + ",matchLanguageLocal=" + matchLanguageLocal2, new Object[0]);
        }
    }

    public int O() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10725, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : super.O();
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10726, new Class[0], Void.TYPE).isSupported) {
            super.init();
        }
    }

    public void R() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10727, new Class[0], Void.TYPE).isSupported) {
            super.R();
        }
    }

    public void initView() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10728, new Class[0], Void.TYPE).isSupported) {
            super.initView();
            this.p2.setSplashImg(R.layout.splash_layout);
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onJpushMessage(JpushMessageEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 10729, new Class[]{JpushMessageEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                String body = event.getMessageBody();
                String title = event.getTitle();
                String tag = "";
                try {
                    JSONObject jsonObject = new JSONObject(event.getTag());
                    if (jsonObject.has("unReadNum")) {
                        int unRead = Integer.parseInt(jsonObject.getString("unReadNum"));
                        b.a(getApplicationContext(), unRead);
                        SharePreferenceUtils.setPrefInt(this, "shortcut_badge_count", unRead);
                    }
                    if (jsonObject.has(Progress.TAG) != 0) {
                        tag = jsonObject.getString(Progress.TAG);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                com.leedarson.smarthome.fcm.a.c(this, title, body == null ? "" : body, tag);
            }
        }
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10730, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
            this.c6 = System.currentTimeMillis();
            com.leedarson.base.logger.a.c("main_act", "sufun==> Main.onPause---》");
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10731, new Class[0], Void.TYPE).isSupported) {
            this.d6 = System.currentTimeMillis();
            super.onResume();
            q2();
            com.leedarson.base.logger.a.c("main_act", "sufun==> MainActivity Main.onResume---》");
            if (SharePreferenceUtils.getPrefBoolean(this, "hasAgree", false)) {
                s2();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void q2() {
        BleMeshService navigation;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10732, new Class[0], Void.TYPE).isSupported && Long.valueOf(System.currentTimeMillis() - this.c6).longValue() > 180000 && (navigation = (BleMeshService) com.alibaba.android.arouter.launcher.a.c().g(BleMeshService.class)) != null) {
            navigation.checkNeedToAutoConnectProcess();
        }
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean z = false;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 10733, new Class[]{KeyEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (event.getKeyCode() == 4) {
            boolean diffTimeCanbeUseFlag = System.currentTimeMillis() - this.e6 < 500;
            this.e6 = System.currentTimeMillis();
            if (System.currentTimeMillis() - this.d6 < 500) {
                z = true;
            }
            boolean isTranslateToPage = z;
            if (diffTimeCanbeUseFlag || isTranslateToPage) {
                com.leedarson.base.logger.a.c("main_act", "sufun==>MainActivity 被拦截成功了---》");
                return true;
            }
            com.leedarson.base.logger.a.c("main_act", "sufun==>MainActivity 拦截失败---》  isTranslateToPage=" + isTranslateToPage + "   diffTimeCanbeUseFlag=" + diffTimeCanbeUseFlag);
        }
        return super.dispatchKeyEvent(event);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x003e, code lost:
        if (r2.equals("ToMainNavigatorEvent") != false) goto L_0x0042;
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
            java.lang.Class<com.leedarson.serviceinterface.event.Event> r2 = com.leedarson.serviceinterface.event.Event.class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 10734(0x29ee, float:1.5042E-41)
            r2 = r9
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001d
            return
        L_0x001d:
            r1 = r9
            if (r10 == 0) goto L_0x0089
            java.lang.String r2 = r10.getKey()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x002b
            goto L_0x0089
        L_0x002b:
            java.lang.String r2 = r10.getKey()
            r3 = -1
            int r4 = r2.hashCode()
            switch(r4) {
                case 236947095: goto L_0x0038;
                default: goto L_0x0037;
            }
        L_0x0037:
            goto L_0x0041
        L_0x0038:
            java.lang.String r4 = "ToMainNavigatorEvent"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x0037
            goto L_0x0042
        L_0x0041:
            r8 = r3
        L_0x0042:
            switch(r8) {
                case 0: goto L_0x0046;
                default: goto L_0x0045;
            }
        L_0x0045:
            goto L_0x0088
        L_0x0046:
            com.leedarson.base.utils.c r2 = com.leedarson.base.utils.c.h()
            android.app.Activity r2 = r2.c()
            if (r2 == 0) goto L_0x0088
            com.leedarson.base.utils.c r3 = com.leedarson.base.utils.c.h()
            java.lang.Class r4 = r2.getClass()
            r3.r(r4)
            android.content.Intent r3 = new android.content.Intent
            java.lang.Class<com.leedarson.smarthome.ui.MainActivity> r4 = com.leedarson.smarthome.ui.MainActivity.class
            r3.<init>(r2, r4)
            r2.startActivity(r3)
            r4 = 2130772013(0x7f01002d, float:1.7147132E38)
            r5 = 2130772015(0x7f01002f, float:1.7147136E38)
            r2.overridePendingTransition(r4, r5)
            com.leedarson.base.utils.c r4 = com.leedarson.base.utils.c.h()
            r4.t(r0)
            android.os.Handler r0 = new android.os.Handler
            android.os.Looper r4 = android.os.Looper.getMainLooper()
            r0.<init>(r4)
            com.leedarson.smarthome.ui.MainActivity$a r4 = new com.leedarson.smarthome.ui.MainActivity$a
            r4.<init>()
            r5 = 300(0x12c, double:1.48E-321)
            r0.postDelayed(r4, r5)
        L_0x0088:
            return
        L_0x0089:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smarthome.ui.MainActivity.onEvent(com.leedarson.serviceinterface.event.Event):void");
    }

    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10738, new Class[0], Void.TYPE).isSupported) {
                c.h().g();
            }
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10735, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            unregisterReceiver(BaseApplication.b().B4);
        }
    }

    public void R1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10736, new Class[0], Void.TYPE).isSupported) {
            super.R1();
            com.leedarson.smarthome.util.b.e();
        }
    }

    public void T1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10737, new Class[0], Void.TYPE).isSupported) {
            super.T1();
            com.leedarson.smarthome.util.b.e();
        }
    }
}
