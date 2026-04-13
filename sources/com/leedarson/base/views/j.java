package com.leedarson.base.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import androidx.core.view.GravityCompat;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.module_base.R$id;
import com.leedarson.module_base.R$layout;
import com.leedarson.module_base.R$style;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: UpgradeProgressDialogView */
public class j extends Dialog {
    public static ChangeQuickRedirect changeQuickRedirect;
    private LDSTextView c;
    private ProgressBar d;
    private String f = "";
    private View q;

    public j(Context context) {
        super(context, R$style.myDialogTheme2);
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 823, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            setContentView(R$layout.layout_upgrade_dialog);
            setCanceledOnTouchOutside(false);
            setCancelable(false);
            b();
        }
    }

    private void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 824, new Class[0], Void.TYPE).isSupported) {
            String surfaceMain = SharePreferenceUtils.getPrefString(getContext(), "surfaceMain", "");
            String themeColor = SharePreferenceUtils.getPrefString(getContext(), "themeColor", "");
            String contextTextColor = SharePreferenceUtils.getPrefString(getContext(), "contextTextColor", "");
            boolean showBottomDialog = SharePreferenceUtils.getPrefBoolean(getContext(), "showBottomDialog", false);
            this.d = (ProgressBar) findViewById(R$id.pb_upgrade);
            this.c = (LDSTextView) findViewById(R$id.tv_progress);
            LDSTextView tvTitle = (LDSTextView) findViewById(R$id.tv_title);
            this.q = findViewById(R$id.rl_dialog_content);
            DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
            if (!TextUtils.isEmpty(themeColor)) {
                this.c.setTextColor(Color.parseColor(themeColor));
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setCornerRadius(8.0f);
                gradientDrawable.setColor(Color.parseColor(themeColor));
                ((LayerDrawable) this.d.getProgressDrawable()).setDrawableByLayerId(16908301, new ClipDrawable(gradientDrawable, GravityCompat.START, 1));
            }
            if (!TextUtils.isEmpty(surfaceMain)) {
                this.q.setBackground(a(surfaceMain, 30, true, true));
            }
            if (!TextUtils.isEmpty(contextTextColor)) {
                tvTitle.setTextColor(Color.parseColor(contextTextColor));
            }
            if (showBottomDialog) {
                getWindow().setGravity(80);
                int width4 = dm.widthPixels;
                WindowManager.LayoutParams lp1 = getWindow().getAttributes();
                lp1.width = width4;
                getWindow().setAttributes(lp1);
                this.q.setBackground(a(surfaceMain, 30, true, false));
            }
        }
    }

    private ShapeDrawable a(String str, int i, boolean z, boolean bottomRound) {
        Object[] objArr = {str, new Integer(i), new Byte(z ? (byte) 1 : 0), new Byte(bottomRound ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        Class[] clsArr = {String.class, Integer.TYPE, cls, cls};
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 825, clsArr, ShapeDrawable.class);
        if (proxy.isSupported) {
            return (ShapeDrawable) proxy.result;
        }
        int radius = i;
        String color = str;
        boolean isFill = z;
        float[] outerRadii = {(float) radius, (float) radius, (float) radius, (float) radius, (float) radius, (float) radius, (float) radius, (float) radius};
        if (!bottomRound) {
            outerRadii[4] = 0.0f;
            outerRadii[5] = 0.0f;
            outerRadii[6] = 0.0f;
            outerRadii[7] = 0.0f;
        }
        ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(outerRadii, (RectF) null, (float[]) null));
        drawable.getPaint().setColor(Color.parseColor(color));
        drawable.getPaint().setAntiAlias(true);
        if (isFill) {
            drawable.getPaint().setStyle(Paint.Style.FILL);
        } else {
            drawable.getPaint().setStyle(Paint.Style.STROKE);
        }
        return drawable;
    }

    public void d(String progressClolor) {
        this.f = progressClolor;
    }

    public void c(int i) {
        Object[] objArr = {new Integer(i)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 826, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            LDSTextView lDSTextView = this.c;
            lDSTextView.setText(i + "%");
            this.d.setProgress(i);
            if (i == 100) {
                dismiss();
            }
        }
    }
}
