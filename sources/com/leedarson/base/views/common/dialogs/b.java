package com.leedarson.base.views.common.dialogs;

import android.app.Dialog;
import android.content.Context;
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

/* compiled from: LDSLeedarsonActionDialog */
public class b extends Dialog {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String a1;
    private Context c;
    /* access modifiers changed from: private */
    public c d;
    private LDSTextView f;
    private String p0;
    private LDSTextView q;
    private LDSTextView x;
    private LDSTextView y;
    private String z;

    /* compiled from: LDSLeedarsonActionDialog */
    public interface c {
        void a();

        void onCancel();
    }

    public b(@NonNull Context context) {
        this(context, R$style.Theme_dialog);
    }

    public b(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.c = context;
        b();
    }

    public void c(c mActionHandler) {
        this.d = mActionHandler;
    }

    private void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 844, new Class[0], Void.TYPE).isSupported) {
            setContentView(R$layout.lds_leedarson_alert_action_dialog);
            this.f = (LDSTextView) findViewById(R$id.tip_title_tv);
            this.q = (LDSTextView) findViewById(R$id.tip_content_tv);
            this.x = (LDSTextView) findViewById(R$id.btn_confirm);
            this.y = (LDSTextView) findViewById(R$id.btn_cancel);
            this.x.setOnClickListener(new a());
            this.y.setOnClickListener(new C0083b());
            setCanceledOnTouchOutside(false);
            setCancelable(false);
        }
    }

    /* compiled from: LDSLeedarsonActionDialog */
    public class a implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 852, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            if (b.this.d != null) {
                b.this.d.a();
                b.this.dismiss();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    /* renamed from: com.leedarson.base.views.common.dialogs.b$b  reason: collision with other inner class name */
    /* compiled from: LDSLeedarsonActionDialog */
    public class C0083b implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0083b() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 853, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            if (b.this.d != null) {
                b.this.d.onCancel();
                b.this.dismiss();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public void g(String titleText) {
        if (!PatchProxy.proxy(new Object[]{titleText}, this, changeQuickRedirect, false, 845, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.f.setVisibility(0);
            this.f.setText(titleText);
            this.z = titleText;
        }
    }

    public void d(String cancelText) {
        if (!PatchProxy.proxy(new Object[]{cancelText}, this, changeQuickRedirect, false, 846, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (TextUtils.isEmpty(cancelText)) {
                this.y.setVisibility(8);
                return;
            }
            this.y.setVisibility(0);
            this.y.setText(cancelText);
        }
    }

    public void f(String contentText) {
        if (!PatchProxy.proxy(new Object[]{contentText}, this, changeQuickRedirect, false, 847, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.q.setVisibility(0);
            this.q.setText(contentText);
            this.p0 = contentText;
        }
    }

    public void e(String confirmText) {
        if (!PatchProxy.proxy(new Object[]{confirmText}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SPEAKERSTART, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.a1 = confirmText;
            this.x.setText(confirmText);
        }
    }

    public void show() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SPEAKERSTART_WEB_RTC_RESP, new Class[0], Void.TYPE).isSupported) {
            super.show();
        }
    }
}
