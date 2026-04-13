package com.leedarson.newui.view.ldsnakebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.RestrictTo;
import androidx.core.view.ViewCompat;
import com.leedarson.R$dimen;
import com.leedarson.R$id;
import com.leedarson.R$styleable;
import com.leedarson.newui.view.ldsnakebar.BaseTransientBottomBar;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class SnackbarContentLayout extends LinearLayout implements BaseTransientBottomBar.m {
    public static ChangeQuickRedirect changeQuickRedirect;
    private TextView c;
    private Button d;
    private int f;
    private int q;

    public SnackbarContentLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public SnackbarContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R$styleable.SnackbarLayout);
        this.f = a.getDimensionPixelSize(R$styleable.SnackbarLayout_android_maxWidth, -1);
        this.q = a.getDimensionPixelSize(R$styleable.SnackbarLayout_maxActionInlineWidth, -1);
        a.recycle();
    }

    public void onFinishInflate() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5352, new Class[0], Void.TYPE).isSupported) {
            super.onFinishInflate();
            this.c = (TextView) findViewById(R$id.snackbar_text);
            this.d = (Button) findViewById(R$id.snackbar_action);
        }
    }

    public TextView getMessageView() {
        return this.c;
    }

    public Button getActionView() {
        return this.d;
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        Object[] objArr = {new Integer(widthMeasureSpec), new Integer(heightMeasureSpec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5353, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (this.f > 0 && getMeasuredWidth() > (i = this.f)) {
                widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
            int multiLineVPadding = getResources().getDimensionPixelSize(R$dimen.design_snackbar_padding_vertical_2lines);
            int singleLineVPadding = getResources().getDimensionPixelSize(R$dimen.design_snackbar_padding_vertical);
            boolean isMultiLine = this.c.getLayout().getLineCount() > 1;
            boolean remeasure = false;
            if (!isMultiLine || this.q <= 0 || this.d.getMeasuredWidth() <= this.q) {
                int messagePadding = isMultiLine ? multiLineVPadding : singleLineVPadding;
                if (b(0, messagePadding, messagePadding)) {
                    remeasure = true;
                }
            } else if (b(1, multiLineVPadding, multiLineVPadding - singleLineVPadding)) {
                remeasure = true;
            }
            if (remeasure) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }
    }

    private boolean b(int orientation, int messagePadTop, int messagePadBottom) {
        Object[] objArr = {new Integer(orientation), new Integer(messagePadTop), new Integer(messagePadBottom)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls, cls};
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5354, clsArr, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        boolean changed = false;
        if (orientation != getOrientation()) {
            setOrientation(orientation);
            changed = true;
        }
        if (this.c.getPaddingTop() == messagePadTop && this.c.getPaddingBottom() == messagePadBottom) {
            return changed;
        }
        a(this.c, messagePadTop, messagePadBottom);
        return true;
    }

    private static void a(View view, int topPadding, int bottomPadding) {
        Object[] objArr = {view, new Integer(topPadding), new Integer(bottomPadding)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 5355, new Class[]{View.class, cls, cls}, Void.TYPE).isSupported) {
            if (ViewCompat.isPaddingRelative(view)) {
                ViewCompat.setPaddingRelative(view, ViewCompat.getPaddingStart(view), topPadding, ViewCompat.getPaddingEnd(view), bottomPadding);
            } else {
                view.setPadding(view.getPaddingLeft(), topPadding, view.getPaddingRight(), bottomPadding);
            }
        }
    }

    public void animateContentIn(int delay, int duration) {
        Object[] objArr = {new Integer(delay), new Integer(duration)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5356, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            this.c.setAlpha(0.0f);
            this.c.animate().alpha(1.0f).setDuration((long) duration).setStartDelay((long) delay).start();
            if (this.d.getVisibility() == 0) {
                this.d.setAlpha(0.0f);
                this.d.animate().alpha(1.0f).setDuration((long) duration).setStartDelay((long) delay).start();
            }
        }
    }

    public void animateContentOut(int delay, int duration) {
        Object[] objArr = {new Integer(delay), new Integer(duration)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5357, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            this.c.setAlpha(1.0f);
            this.c.animate().alpha(0.0f).setDuration((long) duration).setStartDelay((long) delay).start();
            if (this.d.getVisibility() == 0) {
                this.d.setAlpha(1.0f);
                this.d.animate().alpha(0.0f).setDuration((long) duration).setStartDelay((long) delay).start();
            }
        }
    }
}
