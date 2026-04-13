package com.hjq.bar;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/* compiled from: ViewBuilder */
public final class c {
    private final LinearLayout a;
    private final TextView b;
    private final TextView c;
    private final TextView d;
    private final View e;

    c(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        this.a = linearLayout;
        linearLayout.setId(R$id.bar_id_main_layout);
        linearLayout.setOrientation(0);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        TextView textView = new TextView(context);
        this.b = textView;
        textView.setId(R$id.bar_id_left_view);
        textView.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
        textView.setPadding(a(context, 12.0f), 0, a(context, 12.0f), 0);
        textView.setCompoundDrawablePadding(a(context, 2.0f));
        textView.setGravity(16);
        textView.setSingleLine();
        textView.setEllipsize(TextUtils.TruncateAt.END);
        TextView textView2 = new TextView(context);
        this.c = textView2;
        textView2.setId(R$id.bar_id_title_view);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(0, -1);
        titleParams.weight = 1.0f;
        titleParams.leftMargin = a(context, 10.0f);
        titleParams.rightMargin = a(context, 10.0f);
        textView2.setLayoutParams(titleParams);
        textView2.setGravity(17);
        textView2.setSingleLine();
        textView2.setEllipsize(TextUtils.TruncateAt.END);
        TextView textView3 = new TextView(context);
        this.d = textView3;
        textView3.setId(R$id.bar_id_right_view);
        textView3.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
        textView3.setPadding(a(context, 12.0f), 0, a(context, 12.0f), 0);
        textView3.setCompoundDrawablePadding(a(context, 2.0f));
        textView3.setGravity(16);
        textView3.setSingleLine();
        textView3.setEllipsize(TextUtils.TruncateAt.END);
        View view = new View(context);
        this.e = view;
        view.setId(R$id.bar_id_line_view);
        FrameLayout.LayoutParams lineParams = new FrameLayout.LayoutParams(-1, 1);
        lineParams.gravity = 80;
        view.setLayoutParams(lineParams);
    }

    /* access modifiers changed from: package-private */
    public LinearLayout f() {
        return this.a;
    }

    /* access modifiers changed from: package-private */
    public View e() {
        return this.e;
    }

    /* access modifiers changed from: package-private */
    public TextView d() {
        return this.b;
    }

    /* access modifiers changed from: package-private */
    public TextView h() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public TextView g() {
        return this.d;
    }

    static int b(Context context) {
        if (Build.VERSION.SDK_INT >= 11) {
            TypedArray ta = context.obtainStyledAttributes(new int[]{16843499});
            int actionBarSize = (int) ta.getDimension(0, 0.0f);
            ta.recycle();
            if (actionBarSize > 0) {
                return actionBarSize;
            }
        }
        return a(context, 100.0f);
    }

    static CharSequence c(Activity activity) {
        CharSequence label = activity.getTitle();
        if (label == null || label.toString().equals("")) {
            return null;
        }
        try {
            PackageManager packageManager = activity.getPackageManager();
            if (!label.toString().equals(packageManager.getPackageInfo(activity.getPackageName(), 0).applicationInfo.loadLabel(packageManager).toString())) {
                return label;
            }
            return null;
        } catch (PackageManager.NameNotFoundException e2) {
            return null;
        }
    }

    static int a(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    static boolean i(TextView view) {
        Drawable[] drawables = view.getCompoundDrawables();
        if (drawables != null) {
            for (Drawable drawable : drawables) {
                if (drawable != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
