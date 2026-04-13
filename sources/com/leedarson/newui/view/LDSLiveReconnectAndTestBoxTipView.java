package com.leedarson.newui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
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
import com.leedarson.newui.repos.beans.AbnormalVersionBean;
import com.leedarson.newui.widgets.p;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.Locale;

public class LDSLiveReconnectAndTestBoxTipView extends RelativeLayout implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private AbnormalVersionBean a1;
    long a2;
    private LDSTextView c;
    private LDSTextView d;
    private LDSTextView f;
    /* access modifiers changed from: private */
    public String p0;
    /* access modifiers changed from: private */
    public p p1;
    Dialog p2;
    long p3;
    /* access modifiers changed from: private */
    public c p4;
    private LDSTextView q;
    private LDSTextView x;
    private LDSTextView y;
    private ImageView z;

    public enum b {
        UNABLE_CONNECT,
        DEVICE_OFFLINE;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public interface c {
        void b();

        void c();

        void d();

        void e();

        void g();

        void h();

        void i();
    }

    public LDSLiveReconnectAndTestBoxTipView(Context context) {
        this(context, (AttributeSet) null);
    }

    public LDSLiveReconnectAndTestBoxTipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LDSLiveReconnectAndTestBoxTipView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LDSLiveReconnectAndTestBoxTipView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.a2 = 0;
        this.p2 = null;
        this.p3 = System.currentTimeMillis();
        f(context);
    }

    public void setDeviceid(String deviceid) {
        this.p0 = deviceid;
    }

    public void setAbnormalVersionBean(AbnormalVersionBean abnormalVersionBean) {
        this.a1 = abnormalVersionBean;
    }

    private void f(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5024, new Class[]{Context.class}, Void.TYPE).isSupported) {
            LayoutInflater.from(context).inflate(R$layout.lds_live_reconnect_and_test_box_tip, this, true);
            this.c = (LDSTextView) findViewById(R$id.tvTipsTitle);
            this.d = (LDSTextView) findViewById(R$id.tvTipsContent);
            this.f = (LDSTextView) findViewById(R$id.tvReconnect);
            LDSTextView lDSTextView = (LDSTextView) findViewById(R$id.tvReportIssue);
            this.q = lDSTextView;
            lDSTextView.getPaint().setFlags(8);
            this.q.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_btn_tip_report_issue));
            this.z = (ImageView) findViewById(R$id.imgMoreHelp);
            this.x = (LDSTextView) findViewById(R$id.tv_error_code);
            this.y = (LDSTextView) findViewById(R$id.tvTest);
            this.f.setOnClickListener(this);
            this.q.setOnClickListener(this);
            this.z.setOnClickListener(this);
            this.y.setOnClickListener(this);
        }
    }

    public void i(int code, b bVar) {
        boolean z2;
        if (!PatchProxy.proxy(new Object[]{new Integer(code), bVar}, this, changeQuickRedirect, false, 5025, new Class[]{Integer.TYPE, b.class}, Void.TYPE).isSupported) {
            if (System.currentTimeMillis() - this.a2 >= 500) {
                if (code == -50002) {
                    this.a2 = System.currentTimeMillis();
                    this.d.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_live_reconnect_tips_maximum_connection_content));
                    this.y.setVisibility(8);
                    this.q.setVisibility(8);
                } else {
                    this.y.setVisibility(0);
                    this.q.setVisibility(0);
                    AbnormalVersionBean abnormalVersionBean = this.a1;
                    if (abnormalVersionBean == null || (!(z2 = abnormalVersionBean.appAbnormalVersion) && !abnormalVersionBean.deviceAbnormalVersion)) {
                        this.q.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_btn_tip_report_issue));
                        this.y.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_btn_tip_connection_test));
                        this.d.setText(PubUtils.getString(getContext(), R$string.lds_live_reconnect_and_test_tips_offline_content));
                    } else {
                        if (z2) {
                            this.y.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_btn_tip_upgrade_app));
                            this.d.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_live_reconnect_tips_update_app_content));
                        } else if (abnormalVersionBean.deviceAbnormalVersion) {
                            this.y.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_btn_tip_upgrade_version));
                            this.d.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_live_reconnect_tips_update_camera_firmware_content));
                        }
                        this.q.setVisibility(8);
                    }
                }
                e();
                this.y.requestLayout();
            }
        }
    }

    private void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5026, new Class[0], Void.TYPE).isSupported) {
            String reconnectStr = PubUtils.getString(BaseApplication.b(), R$string.video_reconnect);
            StringBuilder builder = new StringBuilder();
            try {
                int space = this.y.getText().length() - reconnectStr.length();
                if (space > 2) {
                    char[] halfChar = new char[(space / 2)];
                    for (int i = 0; i < halfChar.length; i++) {
                        halfChar[i] = ' ';
                    }
                    builder.append(halfChar);
                    builder.append(reconnectStr);
                    builder.append(halfChar);
                } else {
                    builder.append(reconnectStr);
                }
                this.f.setText(builder);
            } catch (Exception e) {
                e.printStackTrace();
                this.f.setText(reconnectStr);
            }
            this.f.requestLayout();
        }
    }

    public void setErrorCodeText(String code) {
        if (!PatchProxy.proxy(new Object[]{code}, this, changeQuickRedirect, false, 5027, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.x.setText(String.format(Locale.US, PubUtils.getString(BaseApplication.b(), R$string.lds_live_reconnect_tips_connect_fail_error_code), new Object[]{code}));
        }
    }

    @SensorsDataInstrumented
    public void onClick(View v) {
        if (PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, 5028, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(v);
            return;
        }
        int viewId = v.getId();
        c cVar = this.p4;
        if (cVar == null) {
            SensorsDataAutoTrackHelper.trackViewOnClick(v);
            return;
        }
        if (viewId == R$id.tvReconnect) {
            cVar.d();
        } else if (viewId == R$id.tvReportIssue) {
            if (System.currentTimeMillis() - this.p3 < 500) {
                this.p3 = System.currentTimeMillis();
                SensorsDataAutoTrackHelper.trackViewOnClick(v);
                return;
            }
            this.p3 = System.currentTimeMillis();
            Dialog dialog = this.p2;
            if (dialog != null && dialog.isShowing()) {
                this.p2.dismiss();
            }
            Dialog dialog2 = new Dialog(getContext(), R$style.Theme_dialog);
            this.p2 = dialog2;
            dialog2.setContentView(R$layout.del_dialog_layout);
            this.p2.setCanceledOnTouchOutside(false);
            LDSTextView txt_tips_title = (LDSTextView) this.p2.findViewById(R$id.tip_title_tv);
            LDSTextView txt_tips_content = (LDSTextView) this.p2.findViewById(R$id.tip_content_tv);
            txt_tips_title.setVisibility(0);
            txt_tips_title.setTextSize(13.0f);
            txt_tips_title.setLDSTypeface(0);
            txt_tips_title.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_report_issue_tip_test_title));
            String content = PubUtils.getString(BaseApplication.b(), R$string.lds_report_issue_tip_test_content);
            SpannableStringBuilder style = new SpannableStringBuilder(content);
            style.setSpan(new ForegroundColorSpan(Color.parseColor("#FC8E35")), 0, content.length() - 1, 17);
            style.setSpan(new UnderlineSpan(), 0, content.length() - 1, 17);
            txt_tips_content.setTextSize(13.0f);
            txt_tips_content.setText(style);
            txt_tips_content.setOnClickListener(this);
            LDSTextView txt_tips_left = (LDSTextView) this.p2.findViewById(R$id.left_btn_tv);
            LDSTextView txt_tips_right = (LDSTextView) this.p2.findViewById(R$id.right_btn_tv);
            txt_tips_left.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_report_issue_i_see));
            txt_tips_right.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_btn_tip_report_issue));
            txt_tips_left.setOnClickListener(this);
            txt_tips_right.setOnClickListener(this);
            this.p2.show();
        } else if (viewId == R$id.imgMoreHelp) {
            cVar.b();
        } else if (viewId == R$id.left_btn_tv) {
            Dialog dialog3 = this.p2;
            if (dialog3 != null) {
                dialog3.dismiss();
            }
        } else if (viewId == R$id.right_btn_tv) {
            Dialog dialog4 = this.p2;
            if (dialog4 != null) {
                dialog4.dismiss();
            }
            this.p4.c();
        } else if (viewId == R$id.tip_content_tv) {
            Dialog dialog5 = this.p2;
            if (dialog5 != null) {
                dialog5.dismiss();
            }
            this.p4.i();
            this.p4.h();
        } else if (viewId == R$id.tvTest) {
            if (System.currentTimeMillis() - this.p3 < 500) {
                this.p3 = System.currentTimeMillis();
                SensorsDataAutoTrackHelper.trackViewOnClick(v);
                return;
            }
            this.p3 = System.currentTimeMillis();
            this.p4.i();
            if (this.y.getText().equals(PubUtils.getString(BaseApplication.b(), R$string.lds_btn_tip_upgrade_app))) {
                this.a1 = null;
                this.p4.e();
            } else if (this.y.getText().equals(PubUtils.getString(BaseApplication.b(), R$string.lds_btn_tip_upgrade_version))) {
                this.a1 = null;
                this.p4.g();
            } else {
                post(new a());
            }
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(v);
    }

    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5031, new Class[0], Void.TYPE).isSupported) {
                p unused = LDSLiveReconnectAndTestBoxTipView.this.p1 = new p(LDSLiveReconnectAndTestBoxTipView.this.getContext(), LDSLiveReconnectAndTestBoxTipView.this.p0);
                LDSLiveReconnectAndTestBoxTipView.this.p1.setOnDismissListener(new C0119a());
                LDSLiveReconnectAndTestBoxTipView.this.p1.show();
                LDSLiveReconnectAndTestBoxTipView.this.p1.x();
            }
        }

        /* renamed from: com.leedarson.newui.view.LDSLiveReconnectAndTestBoxTipView$a$a  reason: collision with other inner class name */
        public class C0119a implements DialogInterface.OnDismissListener {
            public static ChangeQuickRedirect changeQuickRedirect;

            C0119a() {
            }

            public void onDismiss(DialogInterface dialogInterface) {
                if (!PatchProxy.proxy(new Object[]{dialogInterface}, this, changeQuickRedirect, false, 5032, new Class[]{DialogInterface.class}, Void.TYPE).isSupported) {
                    LDSLiveReconnectAndTestBoxTipView.this.p4.d();
                }
            }
        }
    }

    public boolean g() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5029, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        p pVar = this.p1;
        return pVar != null && pVar.isShowing();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void h() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 5030(0x13a6, float:7.049E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.newui.widgets.p r1 = r0.p1
            if (r1 == 0) goto L_0x0026
            boolean r1 = r1.isShowing()
            if (r1 == 0) goto L_0x0026
            com.leedarson.newui.widgets.p r1 = r0.p1
            r1.dismiss()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.LDSLiveReconnectAndTestBoxTipView.h():void");
    }

    public void setLiveReconnectClickListener(c liveReconnectClickListener) {
        this.p4 = liveReconnectClickListener;
    }
}
