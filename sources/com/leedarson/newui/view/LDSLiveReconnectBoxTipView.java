package com.leedarson.newui.view;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import java.util.Locale;

public class LDSLiveReconnectBoxTipView extends RelativeLayout implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private c a1;
    private LDSTextView c;
    private LDSTextView d;
    private LDSTextView f;
    long p0;
    private LDSTextView q;
    private LDSTextView x;
    private ImageView y;
    Dialog z;

    public enum b {
        UNABLE_CONNECT,
        DEVICE_OFFLINE;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public interface c {
        void b();

        void c();

        void d();
    }

    public LDSLiveReconnectBoxTipView(Context context) {
        this(context, (AttributeSet) null);
    }

    public LDSLiveReconnectBoxTipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LDSLiveReconnectBoxTipView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LDSLiveReconnectBoxTipView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.z = null;
        this.p0 = System.currentTimeMillis();
        a(context);
    }

    private void a(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5035, new Class[]{Context.class}, Void.TYPE).isSupported) {
            LayoutInflater.from(context).inflate(R$layout.lds_live_reconnect_box_tip, this, true);
            this.c = (LDSTextView) findViewById(R$id.tvTipsTitle);
            this.d = (LDSTextView) findViewById(R$id.tvTipsContent);
            this.f = (LDSTextView) findViewById(R$id.tvReconnect);
            LDSTextView lDSTextView = (LDSTextView) findViewById(R$id.tvReportIssue);
            this.q = lDSTextView;
            lDSTextView.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_btn_tip_report_issue));
            this.y = (ImageView) findViewById(R$id.imgMoreHelp);
            this.x = (LDSTextView) findViewById(R$id.tv_error_code);
            this.f.setOnClickListener(this);
            this.q.setOnClickListener(this);
            this.y.setOnClickListener(this);
        }
    }

    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[b.values().length];
            a = iArr;
            try {
                iArr[b.DEVICE_OFFLINE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[b.UNABLE_CONNECT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public void b(b offlineType) {
        if (!PatchProxy.proxy(new Object[]{offlineType}, this, changeQuickRedirect, false, 5036, new Class[]{b.class}, Void.TYPE).isSupported) {
            this.q.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_btn_tip_report_issue));
            switch (a.a[offlineType.ordinal()]) {
                case 1:
                    this.c.setText(PubUtils.getString(getContext(), R$string.lds_live_reconnect_tips_offline_title));
                    LDSTextView lDSTextView = this.d;
                    lDSTextView.setText(PubUtils.getString(getContext(), R$string.lds_live_reconnect_tips_offline_content1) + "\r\n" + PubUtils.getString(getContext(), R$string.lds_live_reconnect_tips_offline_content2));
                    return;
                case 2:
                    this.c.setText(PubUtils.getString(getContext(), R$string.lds_live_reconnect_tips_connect_fail_title));
                    LDSTextView lDSTextView2 = this.d;
                    lDSTextView2.setText(PubUtils.getString(getContext(), R$string.lds_live_reconnect_tips_connect_fail_content1) + "\r\n" + PubUtils.getString(getContext(), R$string.lds_live_reconnect_tips_connect_fail_content2) + "\r\n" + PubUtils.getString(getContext(), R$string.lds_live_reconnect_tips_connect_fail_content3));
                    return;
                default:
                    return;
            }
        }
    }

    public void setErrorCodeText(String code) {
        if (!PatchProxy.proxy(new Object[]{code}, this, changeQuickRedirect, false, 5037, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.x.setText(String.format(Locale.US, PubUtils.getString(BaseApplication.b(), R$string.lds_live_reconnect_tips_connect_fail_error_code), new Object[]{code}));
        }
    }

    @SensorsDataInstrumented
    public void onClick(View v) {
        if (PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, 5038, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(v);
            return;
        }
        int viewId = v.getId();
        c cVar = this.a1;
        if (cVar == null) {
            SensorsDataAutoTrackHelper.trackViewOnClick(v);
            return;
        }
        if (viewId == R$id.tvReconnect) {
            cVar.d();
        } else if (viewId == R$id.tvReportIssue) {
            if (System.currentTimeMillis() - this.p0 < 500) {
                this.p0 = System.currentTimeMillis();
                SensorsDataAutoTrackHelper.trackViewOnClick(v);
                return;
            }
            this.p0 = System.currentTimeMillis();
            Dialog dialog = this.z;
            if (dialog != null && dialog.isShowing()) {
                this.z.dismiss();
            }
            Dialog dialog2 = new Dialog(getContext(), R$style.Theme_dialog);
            this.z = dialog2;
            dialog2.setContentView(R$layout.del_dialog_layout);
            this.z.setCanceledOnTouchOutside(false);
            ((LDSTextView) this.z.findViewById(R$id.tip_content_tv)).setText(PubUtils.getString(BaseApplication.b(), R$string.lds_content_report_issue_tip));
            LDSTextView txt_tips_left = (LDSTextView) this.z.findViewById(R$id.left_btn_tv);
            LDSTextView txt_tips_right = (LDSTextView) this.z.findViewById(R$id.right_btn_tv);
            txt_tips_left.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_report_issue_i_see));
            txt_tips_right.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_btn_tip_report_issue));
            txt_tips_left.setOnClickListener(this);
            txt_tips_right.setOnClickListener(this);
            this.z.show();
        } else if (viewId == R$id.imgMoreHelp) {
            cVar.b();
        } else if (viewId == R$id.left_btn_tv) {
            Dialog dialog3 = this.z;
            if (dialog3 != null) {
                dialog3.dismiss();
            }
        } else if (viewId == R$id.right_btn_tv) {
            Dialog dialog4 = this.z;
            if (dialog4 != null) {
                dialog4.dismiss();
            }
            this.a1.c();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(v);
    }

    public void setLiveReconnectClickListener(c liveReconnectClickListener) {
        this.a1 = liveReconnectClickListener;
    }
}
