package com.leedarson.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.leedarson.base.views.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class FlingView extends View {
    public static ChangeQuickRedirect changeQuickRedirect;
    private e c;
    private View d;

    public FlingView(@NonNull Context context) {
        super(context);
        a();
    }

    public FlingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        a();
    }

    public FlingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a();
    }

    public void setAttachView(View attachView) {
        this.d = attachView;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11650, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }

    public boolean onTouchEvent(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11651, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        View view = this.d;
        if (view != null && (view instanceof View.OnTouchListener)) {
            ((View.OnTouchListener) view).onTouch(view, event);
        }
        e eVar = this.c;
        if (eVar != null) {
            eVar.j(this, event);
        }
        return true;
    }

    private void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11652, new Class[0], Void.TYPE).isSupported) {
            this.c = new e(this);
        }
    }

    public e getFlingViewHelper() {
        return this.c;
    }
}
