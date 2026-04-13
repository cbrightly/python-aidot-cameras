package com.leedarson.newui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class NetworkRssiTips extends LinearLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    private LinearLayout c;
    private ImageView d;
    private TextView f;
    private boolean q;

    static /* synthetic */ void a(NetworkRssiTips x0, String x1) {
        Class[] clsArr = {NetworkRssiTips.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 5212, clsArr, Void.TYPE).isSupported) {
            x0.e(x1);
        }
    }

    public NetworkRssiTips(Context context) {
        this(context, (AttributeSet) null);
    }

    public NetworkRssiTips(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public NetworkRssiTips(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.q = false;
        b(context);
    }

    private void b(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5206, new Class[]{Context.class}, Void.TYPE).isSupported) {
            LayoutInflater.from(context).inflate(R$layout.include_live_wifi_strength, this, true);
            this.c = (LinearLayout) findViewById(R$id.layout_wifi_Strength_tips);
            this.d = (ImageView) findViewById(R$id.img_wifi_strength);
            this.f = (TextView) findViewById(R$id.tv_wifi_strength);
        }
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5207, new Class[0], Void.TYPE).isSupported) {
            this.c.setBackgroundResource(R$drawable.bg_title);
        }
    }

    public void setVisibility(int visibility) {
        Object[] objArr = {new Integer(visibility)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5208, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (this.q) {
                super.setVisibility(visibility);
            }
        }
    }

    public void setNetworkRssiTipState(String networkRssi) {
        if (!PatchProxy.proxy(new Object[]{networkRssi}, this, changeQuickRedirect, false, 5209, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                timber.log.a.g("WifiStrengthTips").a("setWifiStrengthTipShow:" + networkRssi, new Object[0]);
                int rssi = Integer.parseInt(networkRssi);
                if (rssi > 0) {
                    rssi -= 100;
                }
                timber.log.a.g("WifiStrengthTips").a("rssi:" + rssi, new Object[0]);
                if (rssi <= -72) {
                    this.q = true;
                    this.d.setOnClickListener(new a());
                    this.f.setOnClickListener(new b());
                    this.d.setImageDrawable(getResources().getDrawable(R$drawable.img_wifi_poor));
                    this.d.setBackground(getResources().getDrawable(R$drawable.shape_circle_f5515b));
                    this.f.setTextColor(Color.parseColor("#F5515B"));
                    this.f.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_wifi_strength_poor));
                } else if (rssi <= -60) {
                    this.q = true;
                    this.d.setOnClickListener(new c());
                    this.f.setOnClickListener(new d());
                    this.d.setImageDrawable(getResources().getDrawable(R$drawable.img_wifi_weak));
                    this.d.setBackground(getResources().getDrawable(R$drawable.shape_circle_f2b634));
                    this.f.setTextColor(Color.parseColor("#F2B634"));
                    this.f.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_wifi_strength_weak));
                } else {
                    this.q = false;
                    setVisibility(8);
                }
            } catch (Exception e2) {
                this.q = false;
                setVisibility(8);
            }
        }
    }

    public class a implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5213, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            NetworkRssiTips.a(NetworkRssiTips.this, PubUtils.getString(BaseApplication.b(), R$string.lds_wifi_strength_tips_title_poor));
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class b implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5214, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            NetworkRssiTips.a(NetworkRssiTips.this, PubUtils.getString(BaseApplication.b(), R$string.lds_wifi_strength_tips_title_poor));
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class c implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5215, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            NetworkRssiTips.a(NetworkRssiTips.this, PubUtils.getString(BaseApplication.b(), R$string.lds_wifi_strength_tips_title_weak));
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class d implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5216, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            NetworkRssiTips.a(NetworkRssiTips.this, PubUtils.getString(BaseApplication.b(), R$string.lds_wifi_strength_tips_title_weak));
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void e(String tipsTitle) {
        if (!PatchProxy.proxy(new Object[]{tipsTitle}, this, changeQuickRedirect, false, 5210, new Class[]{String.class}, Void.TYPE).isSupported) {
            Dialog dialog = new Dialog(getContext(), R$style.Theme_dialog);
            dialog.setContentView(R$layout.del_dialog_layout);
            dialog.setCanceledOnTouchOutside(false);
            LDSTextView txt_tips_title = (LDSTextView) dialog.findViewById(R$id.tip_title_tv);
            LDSTextView txt_tips_content = (LDSTextView) dialog.findViewById(R$id.tip_content_tv);
            LDSTextView txt_tips_right = (LDSTextView) dialog.findViewById(R$id.right_btn_tv);
            txt_tips_title.setVisibility(0);
            txt_tips_title.setTextSize(16.0f);
            txt_tips_title.setText(tipsTitle);
            txt_tips_content.setTextSize(13.0f);
            txt_tips_content.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_wifi_strength_tips_content));
            ((LDSTextView) dialog.findViewById(R$id.left_btn_tv)).setVisibility(8);
            txt_tips_right.setText(PubUtils.getString(BaseApplication.b(), R$string.ok));
            txt_tips_right.setOnClickListener(new e(dialog));
            dialog.show();
        }
    }

    public class e implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Dialog c;

        e(Dialog dialog) {
            this.c = dialog;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5217, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            this.c.dismiss();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5211, new Class[0], Void.TYPE).isSupported) {
            if (this.q) {
                setVisibility(0);
            } else {
                setVisibility(8);
            }
        }
    }
}
