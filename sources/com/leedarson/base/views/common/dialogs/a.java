package com.leedarson.base.views.common.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.module_base.R$id;
import com.leedarson.module_base.R$layout;
import com.leedarson.module_base.R$style;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.tutk.IOTC.AVIOCTRLDEFs;

/* compiled from: LDSActionDialog */
public class a extends Dialog {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String a1;
    private Context c;
    /* access modifiers changed from: private */
    public c d;
    private LDSTextView f;
    private String p0;
    private String p1;
    private LDSTextView q;
    private LDSTextView x;
    private LDSTextView y;
    private View z;

    /* compiled from: LDSActionDialog */
    public interface c {
        void a();

        void onCancel();
    }

    public a(@NonNull Context context) {
        this(context, R$style.Theme_dialog);
    }

    public a(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.c = context;
        b();
    }

    public void c(c mActionHandler) {
        this.d = mActionHandler;
    }

    private void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SETWIFI_REQ, new Class[0], Void.TYPE).isSupported) {
            setContentView(R$layout.lds_alert_action_dialog);
            this.z = findViewById(R$id.actionBtnDivider);
            this.f = (LDSTextView) findViewById(R$id.tip_title_tv);
            this.q = (LDSTextView) findViewById(R$id.tip_content_tv);
            this.x = (LDSTextView) findViewById(R$id.btn_confirm);
            this.y = (LDSTextView) findViewById(R$id.btn_cancel);
            this.x.setOnClickListener(new C0082a());
            this.y.setOnClickListener(new b());
            setCanceledOnTouchOutside(false);
            setCancelable(false);
        }
    }

    /* renamed from: com.leedarson.base.views.common.dialogs.a$a  reason: collision with other inner class name */
    /* compiled from: LDSActionDialog */
    public class C0082a implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0082a() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 842, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            if (a.this.d != null) {
                a.this.d.a();
                a.this.dismiss();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    /* compiled from: LDSActionDialog */
    public class b implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 843, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            if (a.this.d != null) {
                a.this.d.onCancel();
                a.this.dismiss();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public void i(String titleText) {
        if (!PatchProxy.proxy(new Object[]{titleText}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SETWIFI_RESP, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.f.setVisibility(0);
            this.f.setText(titleText);
            this.p0 = titleText;
        }
    }

    public void d(String cancelText) {
        if (!PatchProxy.proxy(new Object[]{cancelText}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETWIFI_REQ, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (TextUtils.isEmpty(cancelText)) {
                this.y.setVisibility(8);
                this.z.setVisibility(8);
                return;
            }
            this.z.setVisibility(0);
            this.y.setVisibility(0);
            this.y.setText(cancelText);
        }
    }

    public void h(String contentText) {
        if (!PatchProxy.proxy(new Object[]{contentText}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETWIFI_RESP, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.q.setVisibility(0);
            this.q.setText(contentText);
            this.a1 = contentText;
        }
    }

    public void f(String confirmText) {
        if (!PatchProxy.proxy(new Object[]{confirmText}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SETWIFI_REQ_2, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.p1 = confirmText;
            this.x.setText(confirmText);
        }
    }

    public void e(String hexColor) {
        if (!PatchProxy.proxy(new Object[]{hexColor}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GETWIFI_RESP_2, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.y.setTextColor(Color.parseColor(hexColor));
        }
    }

    public void g(String hexColor) {
        if (!PatchProxy.proxy(new Object[]{hexColor}, this, changeQuickRedirect, false, 840, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.x.setTextColor(Color.parseColor(hexColor));
        }
    }

    public void show() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 841, new Class[0], Void.TYPE).isSupported) {
            super.show();
        }
    }
}
