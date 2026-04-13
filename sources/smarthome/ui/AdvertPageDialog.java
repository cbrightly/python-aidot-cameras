package smarthome.ui;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.DialogFragment;
import com.bumptech.glide.load.engine.i;
import com.bumptech.glide.request.f;
import com.leedarson.base.R$id;
import com.leedarson.base.R$layout;
import com.leedarson.base.R$style;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.base.utils.o;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.autotrack.aop.FragmentTrackHelper;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;
import smarthome.bean.AdvertBean;
import smarthome.utils.l;
import timber.log.a;

public class AdvertPageDialog extends DialogFragment implements View.OnClickListener, o {
    public static ChangeQuickRedirect changeQuickRedirect;
    smarthome.repos.a A4 = null;
    private String a1;
    private final String a2 = "1";
    private LinearLayout c;
    /* access modifiers changed from: private */
    public LDSTextView d;
    private ImageView f;
    private String p0;
    /* access modifiers changed from: private */
    public int p1 = 3;
    private final String p2 = ExifInterface.GPS_MEASUREMENT_2D;
    private final String p3 = ExifInterface.GPS_MEASUREMENT_3D;
    private final String p4 = "4";
    /* access modifiers changed from: private */
    public c q;
    /* access modifiers changed from: private */
    public Timer x;
    private String y;
    private String z;
    private final String z4 = "5";

    @SensorsDataInstrumented
    public void onHiddenChanged(boolean z2) {
        super.onHiddenChanged(z2);
        FragmentTrackHelper.trackOnHiddenChanged(this, z2);
    }

    @SensorsDataInstrumented
    public void onPause() {
        super.onPause();
        FragmentTrackHelper.trackFragmentPause(this);
    }

    @SensorsDataInstrumented
    public void onResume() {
        super.onResume();
        FragmentTrackHelper.trackFragmentResume(this);
    }

    @SensorsDataInstrumented
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        FragmentTrackHelper.onFragmentViewCreated(this, view, bundle);
    }

    @SensorsDataInstrumented
    public void setUserVisibleHint(boolean z2) {
        super.setUserVisibleHint(z2);
        FragmentTrackHelper.trackFragmentSetUserVisibleHint(this, z2);
    }

    static /* synthetic */ int m1(AdvertPageDialog x0) {
        int i = x0.p1 - 1;
        x0.p1 = i;
        return i;
    }

    public AdvertPageDialog(String imageUrl, AdvertBean bean) {
        this.p0 = imageUrl;
        this.y = bean.getLinkUrl();
        this.a1 = bean.getId();
        this.z = bean.getLinkType();
        this.p1 = bean.getDisplayDuration();
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{inflater, container, bundle}, this, changeQuickRedirect2, false, 13902, new Class[]{LayoutInflater.class, ViewGroup.class, Bundle.class}, View.class);
        if (proxy.isSupported) {
            return (View) proxy.result;
        }
        View view = inflater.inflate(R$layout.advert_page, container, false);
        r1(view);
        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 13903, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            setStyle(2, R$style.FullScreen_dialog);
            super.onCreate(savedInstanceState);
            this.q = new c(this);
            Timer timer = this.x;
            if (timer != null) {
                timer.cancel();
            }
            Timer timer2 = new Timer();
            this.x = timer2;
            timer2.schedule(new a(), 1000, 1000);
        }
    }

    public class a extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13909, new Class[0], Void.TYPE).isSupported) {
                if (AdvertPageDialog.this.p1 <= 0) {
                    AdvertPageDialog.this.x.cancel();
                    AdvertPageDialog.this.q.sendEmptyMessage(241);
                    return;
                }
                AdvertPageDialog.this.q.sendEmptyMessage(242);
            }
        }
    }

    public void onStart() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13904, new Class[0], Void.TYPE).isSupported) {
            super.onStart();
            getDialog().getWindow().addFlags(1024);
            getDialog().getWindow().getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = -1;
            params.height = -1;
            getDialog().getWindow().setAttributes(params);
            q1();
        }
    }

    private void q1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13905, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 19) {
                getDialog().getWindow().getDecorView().setSystemUiVisibility(4102);
            }
        }
    }

    private void r1(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 13906, new Class[]{View.class}, Void.TYPE).isSupported) {
            this.c = (LinearLayout) view.findViewById(R$id.skip_jump);
            this.d = (LDSTextView) view.findViewById(R$id.show_sec);
            this.f = (ImageView) view.findViewById(R$id.advert_image);
            this.c.setOnClickListener(this);
            this.f.setOnClickListener(this);
            f options = (f) ((f) new f().m0(false)).f(i.a);
            LDSTextView lDSTextView = this.d;
            lDSTextView.setText(this.p1 + ExifInterface.LATITUDE_SOUTH);
            a.b g = timber.log.a.g("Advert");
            g.h("AdvertPageActivity:imageUrl= " + this.p0 + " advId=" + this.a1 + " secShow= " + this.p1, new Object[0]);
            if (!TextUtils.isEmpty(this.p0)) {
                com.bumptech.glide.b.t(getContext()).q(this.p0).a(options).H0(this.f);
                SharePreferenceUtils.setPrefInt(getActivity(), this.a1, SharePreferenceUtils.getPrefInt(getActivity(), this.a1, 1) + 1);
                SharePreferenceUtils.setPrefString(getActivity(), "advertTime", l.b());
            }
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13907, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            Timer timer = this.x;
            if (timer != null) {
                timer.cancel();
            }
            c cVar = this.q;
            if (cVar != null) {
                cVar.removeCallbacksAndMessages((Object) null);
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006d, code lost:
        if (r4.equals(androidx.exifinterface.media.ExifInterface.GPS_MEASUREMENT_2D) != false) goto L_0x007b;
     */
    @com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onClick(android.view.View r17) {
        /*
            r16 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r17
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.view.View> r2 = android.view.View.class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 13908(0x3654, float:1.9489E-41)
            r2 = r16
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0021
            com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper.trackViewOnClick(r17)
            return
        L_0x0021:
            r1 = r16
            r2 = r17
            int r3 = r2.getId()
            int r4 = com.leedarson.base.R$id.skip_jump
            if (r3 != r4) goto L_0x0032
            r1.dismiss()
            goto L_0x012c
        L_0x0032:
            int r4 = com.leedarson.base.R$id.advert_image
            if (r3 != r4) goto L_0x012c
            java.lang.String r4 = r1.z
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x0113
            java.lang.String r4 = r1.z
            r5 = -1
            int r6 = r4.hashCode()
            java.lang.String r7 = "5"
            java.lang.String r9 = "4"
            switch(r6) {
                case 49: goto L_0x0070;
                case 50: goto L_0x0067;
                case 51: goto L_0x005d;
                case 52: goto L_0x0055;
                case 53: goto L_0x004d;
                default: goto L_0x004c;
            }
        L_0x004c:
            goto L_0x007a
        L_0x004d:
            boolean r0 = r4.equals(r7)
            if (r0 == 0) goto L_0x004c
            r0 = 4
            goto L_0x007b
        L_0x0055:
            boolean r0 = r4.equals(r9)
            if (r0 == 0) goto L_0x004c
            r0 = 3
            goto L_0x007b
        L_0x005d:
            java.lang.String r0 = "3"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x004c
            r0 = 2
            goto L_0x007b
        L_0x0067:
            java.lang.String r6 = "2"
            boolean r4 = r4.equals(r6)
            if (r4 == 0) goto L_0x004c
            goto L_0x007b
        L_0x0070:
            java.lang.String r0 = "1"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x004c
            r0 = r8
            goto L_0x007b
        L_0x007a:
            r0 = r5
        L_0x007b:
            switch(r0) {
                case 0: goto L_0x0110;
                case 1: goto L_0x00fc;
                case 2: goto L_0x00d3;
                case 3: goto L_0x0080;
                case 4: goto L_0x0080;
                default: goto L_0x007e;
            }
        L_0x007e:
            goto L_0x0113
        L_0x0080:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.SystemService> r4 = com.leedarson.serviceinterface.SystemService.class
            java.lang.Object r0 = r0.g(r4)
            r4 = r0
            com.leedarson.serviceinterface.SystemService r4 = (com.leedarson.serviceinterface.SystemService) r4
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r5 = r0
            java.lang.String r0 = "url"
            java.lang.String r6 = r1.y     // Catch:{ JSONException -> 0x00ce }
            r5.put((java.lang.String) r0, (java.lang.Object) r6)     // Catch:{ JSONException -> 0x00ce }
            java.lang.String r0 = r1.z     // Catch:{ JSONException -> 0x00ce }
            boolean r0 = r9.equals(r0)     // Catch:{ JSONException -> 0x00ce }
            if (r0 == 0) goto L_0x00b4
            r11 = 0
            java.lang.String r12 = ""
            androidx.fragment.app.FragmentActivity r13 = r1.getActivity()     // Catch:{ JSONException -> 0x00ce }
            java.lang.String r14 = "openBrowser"
            java.lang.String r15 = r5.toString()     // Catch:{ JSONException -> 0x00ce }
            r10 = r4
            r10.handleData(r11, r12, r13, r14, r15)     // Catch:{ JSONException -> 0x00ce }
            goto L_0x00cd
        L_0x00b4:
            java.lang.String r0 = r1.z     // Catch:{ JSONException -> 0x00ce }
            boolean r0 = r7.equals(r0)     // Catch:{ JSONException -> 0x00ce }
            if (r0 == 0) goto L_0x00cd
            r11 = 0
            java.lang.String r12 = ""
            androidx.fragment.app.FragmentActivity r13 = r1.getActivity()     // Catch:{ JSONException -> 0x00ce }
            java.lang.String r14 = "openAppStore"
            java.lang.String r15 = r5.toString()     // Catch:{ JSONException -> 0x00ce }
            r10 = r4
            r10.handleData(r11, r12, r13, r14, r15)     // Catch:{ JSONException -> 0x00ce }
        L_0x00cd:
            goto L_0x0113
        L_0x00ce:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0113
        L_0x00d3:
            java.lang.String r0 = r1.y
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x00df
            com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper.trackViewOnClick(r17)
            return
        L_0x00df:
            android.content.Intent r0 = new android.content.Intent
            androidx.fragment.app.FragmentActivity r4 = r1.getActivity()
            java.lang.Class<com.leedarson.serviceimpl.system.external.ExternalActivity> r5 = com.leedarson.serviceimpl.system.external.ExternalActivity.class
            r0.<init>(r4, r5)
            java.lang.String r4 = r1.y
            java.lang.String r5 = "advertUrl"
            r0.putExtra(r5, r4)
            androidx.fragment.app.FragmentActivity r4 = r1.getActivity()
            r4.startActivity(r0)
            r1.dismiss()
            goto L_0x0113
        L_0x00fc:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.Event r4 = new com.leedarson.serviceinterface.event.Event
            java.lang.String r5 = r1.y
            java.lang.String r6 = "AdvertEvent"
            r4.<init>((java.lang.String) r6, (java.lang.String) r5)
            r0.l(r4)
            r1.dismiss()
            goto L_0x0113
        L_0x0110:
            r1.dismiss()
        L_0x0113:
            smarthome.repos.a r0 = r1.A4
            if (r0 != 0) goto L_0x011e
            smarthome.repos.a r0 = new smarthome.repos.a
            r0.<init>()
            r1.A4 = r0
        L_0x011e:
            smarthome.repos.a r0 = r1.A4
            java.lang.String r4 = r1.a1
            smarthome.ui.AdvertPageDialog$b r5 = new smarthome.ui.AdvertPageDialog$b
            r5.<init>()
            java.lang.String r6 = "splash_screens_click"
            r0.c(r6, r4, r5)
        L_0x012c:
            com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper.trackViewOnClick(r17)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: smarthome.ui.AdvertPageDialog.onClick(android.view.View):void");
    }

    public class b extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 13912, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 13910, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("Advert");
                g.h("userEventLog onError: " + e.getMsg(), new Object[0]);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 13911, new Class[]{String.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("Advert");
                g.h("userEventLog onSuccess: " + response, new Object[0]);
            }
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
        WeakReference<AdvertPageDialog> a;

        c(AdvertPageDialog dialogFragment) {
            this.a = new WeakReference<>(dialogFragment);
        }

        public void handleMessage(Message msg) {
            AdvertPageDialog dialog;
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 13913, new Class[]{Message.class}, Void.TYPE).isSupported && (dialog = (AdvertPageDialog) this.a.get()) != null) {
                switch (msg.what) {
                    case 241:
                        try {
                            dialog.dismiss();
                            return;
                        } catch (Exception e) {
                            return;
                        }
                    case 242:
                        if (dialog.p1 != 0) {
                            LDSTextView p1 = dialog.d;
                            p1.setText(AdvertPageDialog.m1(dialog) + ExifInterface.LATITUDE_SOUTH);
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
