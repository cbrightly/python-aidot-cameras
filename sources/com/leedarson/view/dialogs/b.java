package com.leedarson.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$style;
import com.leedarson.base.views.common.LDSTextView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

/* compiled from: LDSAlertDialog */
public class b extends Dialog {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context c;
    /* access modifiers changed from: private */
    public C0196b d;
    private LDSTextView f;
    private LDSTextView q;
    private LDSTextView x;
    private String y;
    private String z;

    /* renamed from: com.leedarson.view.dialogs.b$b  reason: collision with other inner class name */
    /* compiled from: LDSAlertDialog */
    public interface C0196b {
        void a();
    }

    public b(@NonNull Context context) {
        this(context, R$style.Theme_dialog);
    }

    public b(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.c = context;
        b();
    }

    public void c(C0196b mActionHandler) {
        this.d = mActionHandler;
    }

    private void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11827, new Class[0], Void.TYPE).isSupported) {
            setContentView(R$layout.lds_alert_dialog);
            this.f = (LDSTextView) findViewById(R$id.tip_title_tv);
            this.q = (LDSTextView) findViewById(R$id.tip_content_tv);
            LDSTextView lDSTextView = (LDSTextView) findViewById(R$id.btn_confirm);
            this.x = lDSTextView;
            lDSTextView.setOnClickListener(new a());
            setCanceledOnTouchOutside(false);
            setCancelable(false);
        }
    }

    /* compiled from: LDSAlertDialog */
    public class a implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11832, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            if (b.this.d != null) {
                b.this.d.a();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public void e(String contentText) {
        if (!PatchProxy.proxy(new Object[]{contentText}, this, changeQuickRedirect, false, 11829, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.q.setVisibility(0);
            this.q.setText(contentText);
            this.y = contentText;
        }
    }

    public void d(String confirmText) {
        if (!PatchProxy.proxy(new Object[]{confirmText}, this, changeQuickRedirect, false, 11830, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.z = confirmText;
            this.x.setText(confirmText);
        }
    }

    public void show() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11831, new Class[0], Void.TYPE).isSupported) {
            super.show();
        }
    }
}
