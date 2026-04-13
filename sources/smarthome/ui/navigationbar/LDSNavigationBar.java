package smarthome.ui.navigationbar;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.bean.Constants;
import com.leedarson.serviceinterface.JsbridgeService;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.NotifyToMainWebViewTabChangeEvent;
import com.leedarson.serviceinterface.event.TabResendProgressEvent;
import com.leedarson.serviceinterface.event.WebviewReloadEvent;
import com.leedarson.serviceinterface.listener.OnBridgeRespListener;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import meshsdk.cache.CacheHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import smarthome.reporter.r;
import timber.log.a;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class LDSNavigationBar extends LinearLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public String a1;
    /* access modifiers changed from: private */
    public String a2;
    private final String c;
    private LinkedHashMap<String, j> d;
    private LinkedHashMap<String, NavigationItem> f;
    Handler p0;
    /* access modifiers changed from: private */
    public r p1;
    boolean p2;
    io.reactivex.disposables.b p3;
    io.reactivex.disposables.b p4;
    /* access modifiers changed from: private */
    public String q;
    private boolean x;
    private e y;
    /* access modifiers changed from: private */
    public d z;
    private io.reactivex.processors.b<String> z4;

    static /* synthetic */ void c(LDSNavigationBar x0, String x1) {
        Class[] clsArr = {LDSNavigationBar.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 14261, clsArr, Void.TYPE).isSupported) {
            x0.P(x1);
        }
    }

    static /* synthetic */ void f(LDSNavigationBar x0, NavigationItem x1, String x2) {
        Class[] clsArr = {LDSNavigationBar.class, NavigationItem.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 14262, clsArr, Void.TYPE).isSupported) {
            x0.n(x1, x2);
        }
    }

    static /* synthetic */ void g(LDSNavigationBar x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 14263, new Class[]{LDSNavigationBar.class}, Void.TYPE).isSupported) {
            x0.J();
        }
    }

    static /* synthetic */ void h(LDSNavigationBar x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 14264, new Class[]{LDSNavigationBar.class}, Void.TYPE).isSupported) {
            x0.K();
        }
    }

    static /* synthetic */ void i(LDSNavigationBar x0, String x1, boolean x2) {
        Object[] objArr = {x0, x1, new Byte(x2 ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 14265, new Class[]{LDSNavigationBar.class, String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            x0.I(x1, x2);
        }
    }

    public LDSNavigationBar(Context context) {
        this(context, (AttributeSet) null);
    }

    public LDSNavigationBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.c = Constants.SERVICE_TAB_BAR;
        this.x = true;
        this.y = new e();
        this.z = new d();
        this.a1 = "";
        this.a2 = j.Home.name();
        this.p2 = true;
        this.z4 = io.reactivex.processors.b.Y();
        q();
    }

    private void q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14237, new Class[0], Void.TYPE).isSupported) {
            if (!org.greenrobot.eventbus.c.c().j(this)) {
                org.greenrobot.eventbus.c.c().p(this);
            }
            SharePreferenceUtils.setPrefString(getContext(), "resendCurrentActiveKey", "");
            this.p0 = new Handler(Looper.getMainLooper());
            this.d = new LinkedHashMap<>();
            this.f = new LinkedHashMap<>();
            this.d.put("Home", j.Home);
            this.d.put("Automations", j.Automations);
            this.d.put("Shop", j.Shop);
            this.d.put("Community", j.Community);
            this.d.put("Me", j.Me);
            s();
        }
    }

    public void M(String activeKey, JSONArray tabs, JSONArray badges) {
        if (!PatchProxy.proxy(new Object[]{activeKey, tabs, badges}, this, changeQuickRedirect, false, 14238, new Class[]{String.class, JSONArray.class, JSONArray.class}, Void.TYPE).isSupported) {
            if (tabs != null && tabs.length() > 0) {
                try {
                    removeAllViews();
                    this.f.clear();
                    for (int i = 0; i < tabs.length(); i++) {
                        JSONObject tab = (JSONObject) tabs.get(i);
                        String key = tab.optString(CacheEntity.KEY);
                        String title = tab.optString("title");
                        String icon = tab.optString("icon");
                        if (this.d.containsKey(key)) {
                            this.d.get(key).setTitle(title);
                            this.d.get(key).setIcon(icon);
                            this.f.put(key, r(this.d.get(key)));
                        }
                    }
                    requestLayout();
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            if (badges != null && badges.length() > 0) {
                int i2 = 0;
                while (i2 < badges.length()) {
                    try {
                        JSONObject badgeItem = badges.getJSONObject(i2);
                        String key2 = badgeItem.optString(CacheEntity.KEY);
                        int badge = badgeItem.optInt("badge");
                        int type = badgeItem.optInt(IjkMediaMeta.IJKM_KEY_TYPE, 0);
                        if (this.f.containsKey(key2)) {
                            NavigationItem navigationItem = this.f.get(key2);
                            if (type == 0) {
                                navigationItem.setBadge(badge);
                            } else if (type == 1) {
                                navigationItem.setNewIcon(badge);
                            } else if (type == 2) {
                                navigationItem.setSaleIcon(badge);
                            }
                        }
                        i2++;
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            }
            if (!TextUtils.isEmpty(activeKey)) {
                if (Q(activeKey)) {
                    this.q = activeKey;
                    if ("Community".equals(activeKey) || "Shop".equals(activeKey)) {
                        if ("Shop".equals(activeKey)) {
                            I(activeKey, false);
                        }
                        N();
                        smarthome.manager.b.g().r(activeKey);
                    } else {
                        smarthome.manager.b.g().r("Home");
                    }
                    for (String next : this.f.keySet()) {
                        if (next.equals(activeKey)) {
                            this.f.get(next).f(true, !this.x);
                        } else {
                            this.f.get(next).setSelectState(false);
                        }
                    }
                    this.x = false;
                }
            } else if (!TextUtils.isEmpty(this.q)) {
                for (String next2 : this.f.keySet()) {
                    if (next2.equals(this.q)) {
                        this.f.get(next2).f(true, false);
                    } else {
                        this.f.get(next2).setSelectState(false);
                    }
                }
            }
        }
    }

    private boolean Q(String key) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 14239, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.d.containsKey(key)) {
            return true;
        }
        a.b g = timber.log.a.g(LDSNavigationBar.class.getSimpleName());
        g.c("非法 activeKey:" + key, new Object[0]);
        com.leedarson.log.elk.a e2 = com.leedarson.log.elk.a.y(this).e("TabBar.setConfig");
        e2.p("TabBar.setConfig 非法 activeKey:" + key).a().b();
        return false;
    }

    private void s() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14240, new Class[0], Void.TYPE).isSupported) {
            removeAllViews();
            this.f.clear();
            for (Map.Entry<String, TabEnum> entry : this.d.entrySet()) {
                this.f.put(entry.getKey(), r((j) entry.getValue()));
            }
            requestLayout();
        }
    }

    private NavigationItem r(j tabEnum) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{tabEnum}, this, changeQuickRedirect, false, 14241, new Class[]{j.class}, NavigationItem.class);
        if (proxy.isSupported) {
            return (NavigationItem) proxy.result;
        }
        NavigationItem item = new NavigationItem(getContext());
        item.d(tabEnum, "#585F66", "#FC8E35");
        item.setOnClickListener(new c(tabEnum.name()));
        addView(item);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) item.getLayoutParams();
        params.height = -1;
        params.width = 0;
        params.weight = 1.0f;
        params.topMargin = com.leedarson.base.utils.d.b(getContext(), 0.5f);
        return item;
    }

    public void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14242, new Class[0], Void.TYPE).isSupported) {
            p(false);
        }
    }

    public void p(boolean force) {
        Object[] objArr = {new Byte(force ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 14243, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (force || (!"Shop".equals(this.q) && !"Community".equals(this.q))) {
                for (NavigationItem navigationItem : this.f.values()) {
                    navigationItem.a();
                    if (navigationItem.getKey().equals(this.q)) {
                        navigationItem.setLottieProgress(1.0f);
                    }
                }
                setVisibility(8);
            }
        }
    }

    public void N() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14244, new Class[0], Void.TYPE).isSupported) {
            setVisibility(0);
        }
    }

    public class c implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        String c;

        public c(String key) {
            this.c = key;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 14268, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            if (this.c.equals(LDSNavigationBar.this.q)) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            int childCount = LDSNavigationBar.this.getChildCount();
            LDSNavigationBar lDSNavigationBar = LDSNavigationBar.this;
            LDSNavigationBar.c(lDSNavigationBar, "LDSNavigationBar.postClickChange  onClick.key=" + this.c);
            for (int i = 0; i < childCount; i++) {
                NavigationItem child = (NavigationItem) LDSNavigationBar.this.getChildAt(i);
                if (child.getKey().equals(this.c)) {
                    String unused = LDSNavigationBar.this.q = this.c;
                    if (j.Home.name().equals(this.c) || j.Automations.name().equals(this.c) || j.Me.name().equals(this.c)) {
                        if (!LDSNavigationBar.this.a2.equals(this.c)) {
                            LDSNavigationBar lDSNavigationBar2 = LDSNavigationBar.this;
                            if (!lDSNavigationBar2.p2) {
                                LDSNavigationBar.f(lDSNavigationBar2, child, this.c);
                                LDSNavigationBar lDSNavigationBar3 = LDSNavigationBar.this;
                                lDSNavigationBar3.p2 = true;
                                String unused2 = lDSNavigationBar3.a2 = this.c;
                            }
                        }
                        LDSNavigationBar.g(LDSNavigationBar.this);
                        LDSNavigationBar.h(LDSNavigationBar.this);
                        child.setSelectState(true);
                        LDSNavigationBar.i(LDSNavigationBar.this, this.c, false);
                        LDSNavigationBar lDSNavigationBar32 = LDSNavigationBar.this;
                        lDSNavigationBar32.p2 = true;
                        String unused3 = lDSNavigationBar32.a2 = this.c;
                    } else {
                        LDSNavigationBar.g(LDSNavigationBar.this);
                        LDSNavigationBar.h(LDSNavigationBar.this);
                        LDSNavigationBar.this.p2 = false;
                        child.setSelectState(true);
                        LDSNavigationBar.i(LDSNavigationBar.this, this.c, false);
                    }
                } else {
                    child.setSelectState(false);
                }
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void n(NavigationItem item, String key) {
        if (!PatchProxy.proxy(new Object[]{item, key}, this, changeQuickRedirect, false, 14245, new Class[]{NavigationItem.class, String.class}, Void.TYPE).isSupported) {
            I(key, true);
            J();
            this.p3 = io.reactivex.e.w(1).o(new f(this)).P(300, TimeUnit.MILLISECONDS).c(l.c()).I(new g(item), new d(item));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: t */
    public /* synthetic */ org.reactivestreams.a u(Integer integer) {
        return this.z4;
    }

    static /* synthetic */ void v(NavigationItem item, String str) {
        if (!PatchProxy.proxy(new Object[]{item, str}, (Object) null, changeQuickRedirect, true, 14260, new Class[]{NavigationItem.class, String.class}, Void.TYPE).isSupported) {
            item.setSelectState(true);
        }
    }

    static /* synthetic */ void w(NavigationItem item, Throwable th) {
        if (!PatchProxy.proxy(new Object[]{item, th}, (Object) null, changeQuickRedirect, true, 14259, new Class[]{NavigationItem.class, Throwable.class}, Void.TYPE).isSupported) {
            item.setSelectState(true);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void J() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 14246(0x37a6, float:1.9963E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            io.reactivex.disposables.b r1 = r0.p3
            if (r1 == 0) goto L_0x0026
            boolean r1 = r1.isDisposed()
            if (r1 != 0) goto L_0x0026
            io.reactivex.disposables.b r1 = r0.p3
            r1.dispose()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: smarthome.ui.navigationbar.LDSNavigationBar.J():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void K() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 14247(0x37a7, float:1.9964E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            io.reactivex.disposables.b r1 = r0.p4
            if (r1 == 0) goto L_0x0026
            boolean r1 = r1.isDisposed()
            if (r1 != 0) goto L_0x0026
            io.reactivex.disposables.b r1 = r0.p4
            r1.dispose()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: smarthome.ui.navigationbar.LDSNavigationBar.K():void");
    }

    public void H(String tabKey) {
        if (!PatchProxy.proxy(new Object[]{tabKey}, this, changeQuickRedirect, false, 14248, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.z4.onNext(tabKey);
        }
    }

    private void I(String key, boolean needDelaySwitch) {
        Class cls = JsbridgeService.class;
        if (!PatchProxy.proxy(new Object[]{key, new Byte(needDelaySwitch ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 14249, new Class[]{String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            P("LDSNavigationBar.postClickChange key=" + key);
            if (needDelaySwitch) {
                K();
                this.p4 = io.reactivex.e.w(1).o(new a(this)).P(300, TimeUnit.MILLISECONDS).c(l.c()).I(new b(this, key), new c(this, key));
            } else {
                O(key);
            }
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("activeKey", (Object) key);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            if (!"Community".equals(this.q) && !"Shop".equals(this.q)) {
                this.p0.removeCallbacks(this.z);
                this.p0.postDelayed(this.z, CacheHandler.delayTime);
            }
            if (smarthome.manager.b.g().f("Community") != null) {
                ((JsbridgeService) com.alibaba.android.arouter.launcher.a.c().g(cls)).nativeCallJs(smarthome.manager.b.g().f("Community").getWebView(), Constants.SERVICE_TAB_BAR, "onChange", jsonObject.toString(), e.a);
            }
            if (smarthome.manager.b.g().f("Shop") != null) {
                ((JsbridgeService) com.alibaba.android.arouter.launcher.a.c().g(cls)).nativeCallJs(smarthome.manager.b.g().f("Shop").getWebView(), Constants.SERVICE_TAB_BAR, "onChange", jsonObject.toString(), h.a);
                org.greenrobot.eventbus.c.c().l(new NotifyToMainWebViewTabChangeEvent(Constants.SERVICE_TAB_BAR, "onChange", jsonObject.toString(), new a()));
            }
            P("LDSNavigationBar.postClickChange.startInvokeJs key=" + key);
            if (!"Shop".equals(this.q)) {
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_TAB_BAR, "onChange", jsonObject.toString(), new b()));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: x */
    public /* synthetic */ org.reactivestreams.a y(Integer integer) {
        return this.z4;
    }

    /* access modifiers changed from: private */
    /* renamed from: z */
    public /* synthetic */ void A(String key, String str) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{key, str}, this, changeQuickRedirect, false, 14258, clsArr, Void.TYPE).isSupported) {
            P("LDSNavigationBar.postClickChange  onpageLoaded key=" + key);
            O(key);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: B */
    public /* synthetic */ void C(String key, Throwable th) {
        Class[] clsArr = {String.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{key, th}, this, changeQuickRedirect, false, 14257, clsArr, Void.TYPE).isSupported) {
            O(key);
            P("LDSNavigationBar.postClickChange  timeout key=" + key);
        }
    }

    static /* synthetic */ void D(String data, WVJBWebView srouceWebView) {
    }

    public class a implements OnBridgeRespListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onResult(String data, WVJBWebView wVJBWebView) {
            Class[] clsArr = {String.class, WVJBWebView.class};
            if (!PatchProxy.proxy(new Object[]{data, wVJBWebView}, this, changeQuickRedirect, false, 14266, clsArr, Void.TYPE).isSupported) {
                try {
                    LDSNavigationBar lDSNavigationBar = LDSNavigationBar.this;
                    LDSNavigationBar.c(lDSNavigationBar, "LDSNavigationBar.postClickChange.InvokeJsResponse data=" + data);
                    if (new JSONObject(data).optInt("code") == 200) {
                        LDSNavigationBar lDSNavigationBar2 = LDSNavigationBar.this;
                        lDSNavigationBar2.p0.removeCallbacks(lDSNavigationBar2.z);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static /* synthetic */ void E(String data, WVJBWebView srouceWebView) {
    }

    public class b implements OnBridgeRespListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onResult(String data, WVJBWebView wVJBWebView) {
            Class[] clsArr = {String.class, WVJBWebView.class};
            if (!PatchProxy.proxy(new Object[]{data, wVJBWebView}, this, changeQuickRedirect, false, 14267, clsArr, Void.TYPE).isSupported) {
                try {
                    LDSNavigationBar lDSNavigationBar = LDSNavigationBar.this;
                    LDSNavigationBar.c(lDSNavigationBar, "LDSNavigationBar.postClickChange.InvokeJsResponse data=" + data);
                    if (new JSONObject(data).optInt("code") == 200) {
                        LDSNavigationBar lDSNavigationBar2 = LDSNavigationBar.this;
                        lDSNavigationBar2.p0.removeCallbacks(lDSNavigationBar2.z);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void P(String msg) {
    }

    private void O(String key) {
        if (!PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 14250, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (key.equals("Shop")) {
                smarthome.manager.b.g().r("Shop");
                N();
                R();
                com.leedarson.log.sensorsdata.a.b().m("ClickEnterShop", new JSONObject());
                smarthome.manager.b.g().m(this);
                P("LDSNavigationBar.postClickChange.switchTab.KEY_SHOP key=" + key);
            } else if (key.equals("Community")) {
                smarthome.manager.b.g().r("Community");
                N();
                R();
                com.leedarson.log.sensorsdata.a.b().m("ClickEnterCommunity", new JSONObject());
                P("LDSNavigationBar.postClickChange.switchTab.KEY_COMMUNITY key=" + key);
            } else {
                P("LDSNavigationBar.postClickChange.switchTab.main key=" + key);
                this.p0.removeCallbacks(this.y);
                smarthome.manager.b.g().s();
            }
        }
    }

    public void R() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14251, new Class[0], Void.TYPE).isSupported) {
            this.p0.removeCallbacks(this.y);
            this.p0.postDelayed(this.y, 200);
        }
    }

    public String getCurrentActiveKey() {
        return this.q;
    }

    public void setCurrentActiveKey(String currentActiveKey) {
        if (!PatchProxy.proxy(new Object[]{currentActiveKey}, this, changeQuickRedirect, false, 14252, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.q = currentActiveKey;
            a.b g = timber.log.a.g("LDSNavigationBar");
            g.m("this.currentActivityKey 设置为:" + this.q, new Object[0]);
        }
    }

    public String getLastMainWebTabKey() {
        return this.a2;
    }

    public final class e implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14270, new Class[0], Void.TYPE).isSupported) {
                if (LDSNavigationBar.this.getVisibility() != 0) {
                    a.b g = timber.log.a.g("CZB");
                    g.a("当前tabbar已隐藏 key is:" + LDSNavigationBar.this.q, new Object[0]);
                }
                if (LDSNavigationBar.this.q.equals("Shop") || LDSNavigationBar.this.q.equals("Community")) {
                    LDSNavigationBar.this.N();
                }
            }
        }
    }

    public final class d implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14269, new Class[0], Void.TYPE).isSupported) {
                a.b g = timber.log.a.g(LDSNavigationBar.class.getSimpleName());
                g.c("JSBridge onResponse timeout,event:tabbar.onChange,currentActiveKey:" + LDSNavigationBar.this.q, new Object[0]);
                LDSNavigationBar lDSNavigationBar = LDSNavigationBar.this;
                r unused = lDSNavigationBar.p1 = new r(lDSNavigationBar.getContext());
                r k = LDSNavigationBar.this.p1;
                k.j(System.currentTimeMillis() + "", "tabBar", "tabBarChangeTimeout");
                LDSNavigationBar.this.p0.removeCallbacks(this);
                if (com.leedarson.base.utils.c.h().j() != null) {
                    LDSNavigationBar lDSNavigationBar2 = LDSNavigationBar.this;
                    String unused2 = lDSNavigationBar2.a1 = lDSNavigationBar2.q;
                    SharePreferenceUtils.setPrefString(LDSNavigationBar.this.getContext(), "resendCurrentActiveKey", LDSNavigationBar.this.q);
                    org.greenrobot.eventbus.c.c().l(new WebviewReloadEvent(com.leedarson.base.utils.c.h().j()));
                }
            }
        }
    }

    public void G() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14253, new Class[0], Void.TYPE).isSupported) {
            Handler handler = this.p0;
            if (handler != null) {
                handler.removeCallbacks((Runnable) null);
            }
            if (org.greenrobot.eventbus.c.c().j(this)) {
                org.greenrobot.eventbus.c.c().r(this);
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0043, code lost:
        if (r2.equals(com.leedarson.serviceinterface.event.TabResendProgressEvent.STEP_RESTART_HTTP_SERVER) != false) goto L_0x0047;
     */
    @org.greenrobot.eventbus.l
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onTabReloadProgressEvent(com.leedarson.serviceinterface.event.TabResendProgressEvent r10) {
        /*
            r9 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r10
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<com.leedarson.serviceinterface.event.TabResendProgressEvent> r2 = com.leedarson.serviceinterface.event.TabResendProgressEvent.class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 14254(0x37ae, float:1.9974E-41)
            r2 = r9
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001d
            return
        L_0x001d:
            r1 = r9
            java.lang.String r2 = r10.stepName
            r3 = -1
            int r4 = r2.hashCode()
            switch(r4) {
                case -1837690534: goto L_0x003d;
                case 91058122: goto L_0x0033;
                case 1151543240: goto L_0x0029;
                default: goto L_0x0028;
            }
        L_0x0028:
            goto L_0x0046
        L_0x0029:
            java.lang.String r0 = "diagnosisWebview"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0028
            r0 = r8
            goto L_0x0047
        L_0x0033:
            java.lang.String r0 = "resendOnChange"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0028
            r0 = 2
            goto L_0x0047
        L_0x003d:
            java.lang.String r4 = "restartHttpServer"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x0028
            goto L_0x0047
        L_0x0046:
            r0 = r3
        L_0x0047:
            switch(r0) {
                case 0: goto L_0x0072;
                case 1: goto L_0x0061;
                case 2: goto L_0x004b;
                default: goto L_0x004a;
            }
        L_0x004a:
            goto L_0x008b
        L_0x004b:
            smarthome.reporter.r r0 = r1.p1
            com.leedarson.log.tracker.BaseStepBean r2 = new com.leedarson.log.tracker.BaseStepBean
            java.lang.String r3 = r10.stepName
            int r4 = r10.code
            long r5 = r10.duration
            r2.<init>(r3, r4, r5)
            r0.e(r2)
            smarthome.reporter.r r0 = r1.p1
            r0.l()
            goto L_0x008b
        L_0x0061:
            smarthome.reporter.r r0 = r1.p1
            com.leedarson.log.tracker.BaseStepBean r2 = new com.leedarson.log.tracker.BaseStepBean
            java.lang.String r3 = r10.stepName
            int r4 = r10.code
            long r5 = r10.duration
            r2.<init>(r3, r4, r5)
            r0.e(r2)
            goto L_0x008b
        L_0x0072:
            smarthome.reporter.r r0 = r1.p1
            com.leedarson.log.tracker.BaseStepBean r2 = new com.leedarson.log.tracker.BaseStepBean
            java.lang.String r3 = r10.stepName
            int r4 = r10.code
            long r5 = r10.duration
            r2.<init>(r3, r4, r5)
            r0.e(r2)
            int r0 = r10.code
            r2 = 200(0xc8, float:2.8E-43)
            if (r0 != r2) goto L_0x008b
            r1.L()
        L_0x008b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: smarthome.ui.navigationbar.LDSNavigationBar.onTabReloadProgressEvent(com.leedarson.serviceinterface.event.TabResendProgressEvent):void");
    }

    public boolean F() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14255, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : !TextUtils.isEmpty(this.a1);
    }

    public LinkedHashMap<String, NavigationItem> getItemViewMap() {
        return this.f;
    }

    public void L() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14256, new Class[0], Void.TYPE).isSupported) {
            if (!this.q.equals(this.a1)) {
                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    NavigationItem child = (NavigationItem) getChildAt(i);
                    if (child.getKey().equals(this.a1)) {
                        child.performClick();
                    }
                }
            } else {
                I(this.a1, false);
            }
            this.a1 = "";
            SharePreferenceUtils.setPrefString(getContext(), "resendCurrentActiveKey", "");
            org.greenrobot.eventbus.c.c().l(new TabResendProgressEvent(TabResendProgressEvent.STEP_RESEND_ON_CHANGE, 10, 200));
        }
    }
}
