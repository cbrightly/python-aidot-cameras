package com.leedarson.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$style;
import com.leedarson.serviceimpl.system.notch.b;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

/* compiled from: LDSBottomDialog */
public class c extends Dialog {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context c;
    private View d;

    public c(@NonNull Context context) {
        super(context, R$style.BottomDialog);
        this.c = context;
    }

    public c(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.c = context;
    }

    private void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11833, new Class[0], Void.TYPE).isSupported) {
            Window dialogWindow = getWindow();
            dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams dialogWidowLayoutParams = dialogWindow.getAttributes();
            dialogWidowLayoutParams.width = -1;
            dialogWidowLayoutParams.horizontalMargin = 0.0f;
            dialogWindow.setAttributes(dialogWidowLayoutParams);
        }
    }

    public void e(int layoutId) {
        if (!PatchProxy.proxy(new Object[]{new Integer(layoutId)}, this, changeQuickRedirect, false, 11834, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.d = LayoutInflater.from(this.c).inflate(layoutId, (ViewGroup) null);
            View contentWrap = LayoutInflater.from(this.c).inflate(R$layout.dailog_outer_container, (ViewGroup) null);
            RelativeLayout rl_dialog = (RelativeLayout) contentWrap.findViewById(R$id.rl_dialog);
            rl_dialog.setPadding(0, (b.b(this.c) / 2) + 88, 0, 0);
            a(contentWrap, rl_dialog);
        }
    }

    public void f(int layoutId) {
        if (!PatchProxy.proxy(new Object[]{new Integer(layoutId)}, this, changeQuickRedirect, false, 11835, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.d = LayoutInflater.from(this.c).inflate(layoutId, (ViewGroup) null);
            View contentWrap = LayoutInflater.from(this.c).inflate(R$layout.dailog_outer_container, (ViewGroup) null);
            RelativeLayout rl_dialog = (RelativeLayout) contentWrap.findViewById(R$id.rl_dialog);
            rl_dialog.setPadding(0, b.b(this.c), 0, 0);
            a(contentWrap, rl_dialog);
        }
    }

    /* compiled from: LDSBottomDialog */
    public class a implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            View view2 = view;
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public void a(View contentWrap, RelativeLayout rl_dialog) {
        Class[] clsArr = {View.class, RelativeLayout.class};
        if (!PatchProxy.proxy(new Object[]{contentWrap, rl_dialog}, this, changeQuickRedirect, false, 11836, clsArr, Void.TYPE).isSupported) {
            this.d.setOnClickListener(new a());
            rl_dialog.setOnClickListener(new a(this));
            ((RelativeLayout) contentWrap.findViewById(R$id.lnDialogOutContainer)).addView(this.d, new RelativeLayout.LayoutParams(-1, -2));
            setContentView(contentWrap);
            b();
            contentWrap.setLayoutParams(contentWrap.getLayoutParams());
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: c */
    public /* synthetic */ void d(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11838, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void show() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11837, new Class[0], Void.TYPE).isSupported) {
            super.show();
            this.d.invalidate();
        }
    }
}
