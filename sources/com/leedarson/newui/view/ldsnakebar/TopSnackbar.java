package com.leedarson.newui.view.ldsnakebar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import com.leedarson.R$layout;
import com.leedarson.newui.view.ldsnakebar.BaseTransientBottomBar;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public final class TopSnackbar extends BaseTransientBottomBar<TopSnackbar> {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static class a extends BaseTransientBottomBar.l<TopSnackbar> {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void d(TopSnackbar sb) {
        }

        public void c(TopSnackbar transientBottomBar, int event) {
        }
    }

    private TopSnackbar(ViewGroup parent, View content, BaseTransientBottomBar.m contentViewCallback) {
        super(parent, content, contentViewCallback);
    }

    @NonNull
    public static TopSnackbar s(@NonNull View view, @NonNull CharSequence text, int duration) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, text, new Integer(duration)}, (Object) null, changeQuickRedirect, true, 5394, new Class[]{View.class, CharSequence.class, Integer.TYPE}, TopSnackbar.class);
        if (proxy.isSupported) {
            return (TopSnackbar) proxy.result;
        }
        ViewGroup parent = r(view);
        if (parent != null) {
            SnackbarContentLayout content = (SnackbarContentLayout) LayoutInflater.from(parent.getContext()).inflate(R$layout.design_layout_topsnackbar_include, parent, false);
            TopSnackbar snackbar = new TopSnackbar(parent, content, content);
            snackbar.t(text);
            snackbar.n(duration);
            return snackbar;
        }
        throw new IllegalArgumentException("No suitable parent found from the given view. Please provide a valid view.");
    }

    /* JADX WARNING: type inference failed for: r1v6, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.view.ViewGroup r(android.view.View r8) {
        /*
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r8
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.view.View> r0 = android.view.View.class
            r6[r2] = r0
            java.lang.Class<android.view.ViewGroup> r7 = android.view.ViewGroup.class
            r2 = 0
            r4 = 1
            r5 = 5396(0x1514, float:7.561E-42)
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0021
            java.lang.Object r8 = r0.result
            android.view.ViewGroup r8 = (android.view.ViewGroup) r8
            return r8
        L_0x0021:
            r0 = 0
        L_0x0022:
            boolean r1 = r8 instanceof androidx.coordinatorlayout.widget.CoordinatorLayout
            if (r1 == 0) goto L_0x002a
            r1 = r8
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            return r1
        L_0x002a:
            boolean r1 = r8 instanceof android.widget.FrameLayout
            if (r1 == 0) goto L_0x003e
            int r1 = r8.getId()
            r2 = 16908290(0x1020002, float:2.3877235E-38)
            if (r1 != r2) goto L_0x003b
            r1 = r8
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            return r1
        L_0x003b:
            r0 = r8
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
        L_0x003e:
            if (r8 == 0) goto L_0x004e
            android.view.ViewParent r1 = r8.getParent()
            boolean r2 = r1 instanceof android.view.View
            if (r2 == 0) goto L_0x004c
            r2 = r1
            android.view.View r2 = (android.view.View) r2
            goto L_0x004d
        L_0x004c:
            r2 = 0
        L_0x004d:
            r8 = r2
        L_0x004e:
            if (r8 != 0) goto L_0x0051
            return r0
        L_0x0051:
            goto L_0x0022
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.ldsnakebar.TopSnackbar.r(android.view.View):android.view.ViewGroup");
    }

    @NonNull
    public TopSnackbar t(@NonNull CharSequence message) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 5397, new Class[]{CharSequence.class}, TopSnackbar.class);
        if (proxy.isSupported) {
            return (TopSnackbar) proxy.result;
        }
        ((SnackbarContentLayout) this.e.getChildAt(0)).getMessageView().setText(message);
        return this;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final class SnackbarLayout extends BaseTransientBottomBar.SnackbarBaseLayout {
        public static ChangeQuickRedirect changeQuickRedirect;

        public SnackbarLayout(Context context) {
            super(context);
        }

        public SnackbarLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            Object[] objArr = {new Integer(widthMeasureSpec), new Integer(heightMeasureSpec)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5406, new Class[]{cls, cls}, Void.TYPE).isSupported) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                int childCount = getChildCount();
                int availableWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    if (child.getLayoutParams().width == -1) {
                        child.measure(View.MeasureSpec.makeMeasureSpec(availableWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(), 1073741824));
                    }
                }
            }
        }
    }
}
