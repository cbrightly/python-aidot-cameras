package com.leedarson.newui.widgets;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
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

/* compiled from: LDSSwitchResolutionTipDialog */
public class r {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;
    private LDSTextView b;
    private LDSTextView c;
    private LDSTextView d;
    private LDSTextView e;
    Dialog f;

    public r(Context context) {
        this.a = context;
        b();
    }

    private void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5649, new Class[0], Void.TYPE).isSupported) {
            Dialog dialog = new Dialog(this.a, R$style.Theme_dialog);
            this.f = dialog;
            dialog.setContentView(R$layout.del_dialog_layout);
            this.f.setCanceledOnTouchOutside(false);
            this.b = (LDSTextView) this.f.findViewById(R$id.tip_title_tv);
            this.c = (LDSTextView) this.f.findViewById(R$id.tip_content_tv);
            this.d = (LDSTextView) this.f.findViewById(R$id.left_btn_tv);
            this.e = (LDSTextView) this.f.findViewById(R$id.right_btn_tv);
            this.d.setOnClickListener(new n(this));
            this.e.setOnClickListener(o.c);
            this.b.setVisibility(8);
            this.c.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_ipc_live_tip_long_loading_tip));
            this.d.setText(PubUtils.getString(this.a, R$string.cancel));
            this.e.setText(PubUtils.getString(this.a, R$string.ok));
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: c */
    public /* synthetic */ void d(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5653, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        a();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    @SensorsDataInstrumented
    static /* synthetic */ void e(View view) {
        View view2 = view;
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 5650(0x1612, float:7.917E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            android.app.Dialog r1 = r0.f
            if (r1 == 0) goto L_0x0026
            boolean r1 = r1.isShowing()
            if (r1 == 0) goto L_0x0026
            android.app.Dialog r1 = r0.f
            r1.dismiss()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.widgets.r.a():void");
    }

    public void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5651, new Class[0], Void.TYPE).isSupported) {
            this.f.show();
        }
    }

    public void setOnOkClickHandler(View.OnClickListener onOkClickHandler) {
        if (!PatchProxy.proxy(new Object[]{onOkClickHandler}, this, changeQuickRedirect, false, 5652, new Class[]{View.OnClickListener.class}, Void.TYPE).isSupported) {
            this.e.setOnClickListener(onOkClickHandler);
        }
    }
}
