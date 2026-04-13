package smarthome.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.exifinterface.media.ExifInterface;
import com.bumptech.glide.load.engine.i;
import com.bumptech.glide.request.f;
import com.leedarson.base.R$id;
import com.leedarson.base.R$layout;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.base.utils.o;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;
import smarthome.utils.l;
import timber.log.a;

@Deprecated
public class AdvertPageActivity extends BaseActivity implements View.OnClickListener, o {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String A4;
    /* access modifiers changed from: private */
    public int B4 = 3;
    private final String C4 = "1";
    private final String D4 = ExifInterface.GPS_MEASUREMENT_2D;
    private final String E4 = ExifInterface.GPS_MEASUREMENT_3D;
    private final String F4 = "4";
    private final String G4 = "5";
    smarthome.repos.a H4 = null;
    /* access modifiers changed from: private */
    public LDSTextView a1;
    /* access modifiers changed from: private */
    public c a2;
    private LinearLayout p0;
    private ImageView p1;
    /* access modifiers changed from: private */
    public Timer p2;
    private String p3;
    private String p4;
    private String z4;

    static /* synthetic */ int Y(AdvertPageActivity x0) {
        int i = x0.B4 - 1;
        x0.B4 = i;
        return i;
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 13891, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            setTitle("Ad");
            requestWindowFeature(1);
            super.onCreate(savedInstanceState);
            getWindow().setFlags(1024, 1024);
            initView();
        }
    }

    public int O() {
        return R$layout.advert_page;
    }

    public void init() {
    }

    public void R() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13892, new Class[0], Void.TYPE).isSupported) {
            this.a2 = new c(this);
            Timer timer = this.p2;
            if (timer != null) {
                timer.cancel();
            }
            Timer timer2 = new Timer();
            this.p2 = timer2;
            timer2.schedule(new a(), 1000, 1000);
        }
    }

    public class a extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13897, new Class[0], Void.TYPE).isSupported) {
                if (AdvertPageActivity.this.B4 <= 0) {
                    AdvertPageActivity.this.p2.cancel();
                    AdvertPageActivity.this.a2.sendEmptyMessage(241);
                    return;
                }
                AdvertPageActivity.this.a2.sendEmptyMessage(242);
            }
        }
    }

    private void initView() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13893, new Class[0], Void.TYPE).isSupported) {
            this.p0 = (LinearLayout) findViewById(R$id.skip_jump);
            this.a1 = (LDSTextView) findViewById(R$id.show_sec);
            this.p1 = (ImageView) findViewById(R$id.advert_image);
            this.p0.setOnClickListener(this);
            this.p1.setOnClickListener(this);
            f options = (f) ((f) new f().m0(false)).f(i.a);
            this.z4 = getIntent().getStringExtra("imageUrl");
            this.p3 = getIntent().getStringExtra("linkUrl");
            this.A4 = getIntent().getStringExtra("advertId");
            this.p4 = getIntent().getStringExtra("linkType");
            this.B4 = getIntent().getIntExtra("displayDuration", 3);
            LDSTextView lDSTextView = this.a1;
            lDSTextView.setText(this.B4 + ExifInterface.LATITUDE_SOUTH);
            a.b g = timber.log.a.g("Advert");
            g.h("AdvertPageActivity:imageUrl= " + this.z4 + " advId=" + this.A4 + " secShow= " + getIntent().getIntExtra("displayDuration", 5), new Object[0]);
            if (!TextUtils.isEmpty(this.z4)) {
                com.bumptech.glide.b.u(this).q(this.z4).a(options).H0(this.p1);
                SharePreferenceUtils.setPrefInt(this, this.A4, SharePreferenceUtils.getPrefInt(this, this.A4, 1) + 1);
                SharePreferenceUtils.setPrefString(this, "advertTime", l.b());
            }
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13894, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            Timer timer = this.p2;
            if (timer != null) {
                timer.cancel();
            }
            c cVar = this.a2;
            if (cVar != null) {
                cVar.removeCallbacksAndMessages((Object) null);
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006a, code lost:
        if (r2.equals(androidx.exifinterface.media.ExifInterface.GPS_MEASUREMENT_2D) != false) goto L_0x0078;
     */
    @com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onClick(android.view.View r12) {
        /*
            r11 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r12
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.view.View> r2 = android.view.View.class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 13895(0x3647, float:1.9471E-41)
            r2 = r11
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0020
            com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper.trackViewOnClick(r12)
            return
        L_0x0020:
            r1 = r11
            r9 = r12
            int r10 = r9.getId()
            int r2 = com.leedarson.base.R$id.skip_jump
            if (r10 != r2) goto L_0x002f
            r1.finish()
            goto L_0x011a
        L_0x002f:
            int r2 = com.leedarson.base.R$id.advert_image
            if (r10 != r2) goto L_0x011a
            java.lang.String r2 = r1.p4
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x0101
            java.lang.String r2 = r1.p4
            r3 = -1
            int r4 = r2.hashCode()
            java.lang.String r5 = "5"
            java.lang.String r6 = "4"
            switch(r4) {
                case 49: goto L_0x006d;
                case 50: goto L_0x0064;
                case 51: goto L_0x005a;
                case 52: goto L_0x0052;
                case 53: goto L_0x004a;
                default: goto L_0x0049;
            }
        L_0x0049:
            goto L_0x0077
        L_0x004a:
            boolean r0 = r2.equals(r5)
            if (r0 == 0) goto L_0x0049
            r0 = 4
            goto L_0x0078
        L_0x0052:
            boolean r0 = r2.equals(r6)
            if (r0 == 0) goto L_0x0049
            r0 = 3
            goto L_0x0078
        L_0x005a:
            java.lang.String r0 = "3"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0049
            r0 = 2
            goto L_0x0078
        L_0x0064:
            java.lang.String r4 = "2"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x0049
            goto L_0x0078
        L_0x006d:
            java.lang.String r0 = "1"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0049
            r0 = r8
            goto L_0x0078
        L_0x0077:
            r0 = r3
        L_0x0078:
            switch(r0) {
                case 0: goto L_0x00fe;
                case 1: goto L_0x00ea;
                case 2: goto L_0x00c9;
                case 3: goto L_0x007d;
                case 4: goto L_0x007d;
                default: goto L_0x007b;
            }
        L_0x007b:
            goto L_0x0101
        L_0x007d:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.SystemService> r2 = com.leedarson.serviceinterface.SystemService.class
            java.lang.Object r0 = r0.g(r2)
            com.leedarson.serviceinterface.SystemService r0 = (com.leedarson.serviceinterface.SystemService) r0
            org.json.JSONObject r2 = new org.json.JSONObject
            r2.<init>()
            r8 = r2
            java.lang.String r2 = "url"
            java.lang.String r3 = r1.p3     // Catch:{ JSONException -> 0x00c4 }
            r8.put((java.lang.String) r2, (java.lang.Object) r3)     // Catch:{ JSONException -> 0x00c4 }
            java.lang.String r2 = r1.p4     // Catch:{ JSONException -> 0x00c4 }
            boolean r2 = r6.equals(r2)     // Catch:{ JSONException -> 0x00c4 }
            if (r2 == 0) goto L_0x00ad
            r3 = 0
            java.lang.String r4 = ""
            java.lang.String r6 = "openBrowser"
            java.lang.String r7 = r8.toString()     // Catch:{ JSONException -> 0x00c4 }
            r2 = r0
            r5 = r1
            r2.handleData(r3, r4, r5, r6, r7)     // Catch:{ JSONException -> 0x00c4 }
            goto L_0x00c3
        L_0x00ad:
            java.lang.String r2 = r1.p4     // Catch:{ JSONException -> 0x00c4 }
            boolean r2 = r5.equals(r2)     // Catch:{ JSONException -> 0x00c4 }
            if (r2 == 0) goto L_0x00c3
            r3 = 0
            java.lang.String r4 = ""
            java.lang.String r6 = "openAppStore"
            java.lang.String r7 = r8.toString()     // Catch:{ JSONException -> 0x00c4 }
            r2 = r0
            r5 = r1
            r2.handleData(r3, r4, r5, r6, r7)     // Catch:{ JSONException -> 0x00c4 }
        L_0x00c3:
            goto L_0x0101
        L_0x00c4:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0101
        L_0x00c9:
            java.lang.String r0 = r1.p3
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x00d5
            com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper.trackViewOnClick(r12)
            return
        L_0x00d5:
            android.content.Intent r0 = new android.content.Intent
            java.lang.Class<com.leedarson.serviceimpl.system.external.ExternalActivity> r2 = com.leedarson.serviceimpl.system.external.ExternalActivity.class
            r0.<init>(r1, r2)
            java.lang.String r2 = r1.p3
            java.lang.String r3 = "advertUrl"
            r0.putExtra(r3, r2)
            r1.startActivity(r0)
            r1.finish()
            goto L_0x0101
        L_0x00ea:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.Event r2 = new com.leedarson.serviceinterface.event.Event
            java.lang.String r3 = r1.p3
            java.lang.String r4 = "AdvertEvent"
            r2.<init>((java.lang.String) r4, (java.lang.String) r3)
            r0.l(r2)
            r1.finish()
            goto L_0x0101
        L_0x00fe:
            r1.finish()
        L_0x0101:
            smarthome.repos.a r0 = r1.H4
            if (r0 != 0) goto L_0x010c
            smarthome.repos.a r0 = new smarthome.repos.a
            r0.<init>()
            r1.H4 = r0
        L_0x010c:
            smarthome.repos.a r0 = r1.H4
            java.lang.String r2 = r1.A4
            smarthome.ui.AdvertPageActivity$b r3 = new smarthome.ui.AdvertPageActivity$b
            r3.<init>()
            java.lang.String r4 = "splash_screens_click"
            r0.c(r4, r2, r3)
        L_0x011a:
            com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper.trackViewOnClick(r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: smarthome.ui.AdvertPageActivity.onClick(android.view.View):void");
    }

    public class b extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 13900, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 13898, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("Advert");
                g.h("userEventLog onError: " + e.getMsg(), new Object[0]);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 13899, new Class[]{String.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("Advert");
                g.h("userEventLog onSuccess: " + response, new Object[0]);
            }
        }
    }

    public void onBackPressed() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13896, new Class[0], Void.TYPE).isSupported) {
            super.onBackPressed();
            finish();
        }
    }

    public void o0(WVJBWebView webView) {
    }

    public void p0(WVJBWebView webView) {
    }

    public boolean d0() {
        return false;
    }

    public static class c extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;
        WeakReference<AdvertPageActivity> a;

        c(AdvertPageActivity activity) {
            this.a = new WeakReference<>(activity);
        }

        public void handleMessage(Message msg) {
            AdvertPageActivity mActivity;
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 13901, new Class[]{Message.class}, Void.TYPE).isSupported && (mActivity = (AdvertPageActivity) this.a.get()) != null) {
                switch (msg.what) {
                    case 241:
                        mActivity.finish();
                        return;
                    case 242:
                        if (mActivity.B4 != 0) {
                            LDSTextView b0 = mActivity.a1;
                            b0.setText(AdvertPageActivity.Y(mActivity) + ExifInterface.LATITUDE_SOUTH);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }
}
