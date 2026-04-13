package com.scwang.smart.refresh.layout.simple;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.a;
import com.scwang.smart.refresh.layout.api.d;
import com.scwang.smart.refresh.layout.api.e;
import com.scwang.smart.refresh.layout.api.f;
import com.scwang.smart.refresh.layout.constant.b;
import com.scwang.smart.refresh.layout.constant.c;
import com.scwang.smart.refresh.layout.listener.h;

public abstract class SimpleComponent extends RelativeLayout implements a {
    protected View c;
    protected c d;
    protected a f;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    protected SimpleComponent(@NonNull View wrapped) {
        this(wrapped, wrapped instanceof a ? (a) wrapped : null);
    }

    protected SimpleComponent(@NonNull View wrappedView, @Nullable a wrappedInternal) {
        super(wrappedView.getContext(), (AttributeSet) null, 0);
        this.c = wrappedView;
        this.f = wrappedInternal;
        if ((this instanceof com.scwang.smart.refresh.layout.api.c) && (wrappedInternal instanceof d) && wrappedInternal.getSpinnerStyle() == c.e) {
            wrappedInternal.getView().setScaleY(-1.0f);
        } else if (this instanceof d) {
            a aVar = this.f;
            if ((aVar instanceof com.scwang.smart.refresh.layout.api.c) && aVar.getSpinnerStyle() == c.e) {
                wrappedInternal.getView().setScaleY(-1.0f);
            }
        }
    }

    protected SimpleComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        if (getView() == ((a) obj).getView()) {
            return true;
        }
        return false;
    }

    @NonNull
    public View getView() {
        View view = this.c;
        return view == null ? this : view;
    }

    public int f(@NonNull f refreshLayout, boolean success) {
        a aVar = this.f;
        if (aVar == null || aVar == this) {
            return 0;
        }
        return aVar.f(refreshLayout, success);
    }

    public void setPrimaryColors(@ColorInt int... colors) {
        a aVar = this.f;
        if (aVar != null && aVar != this) {
            aVar.setPrimaryColors(colors);
        }
    }

    @NonNull
    public c getSpinnerStyle() {
        int i;
        c cVar = this.d;
        if (cVar != null) {
            return cVar;
        }
        a aVar = this.f;
        if (aVar != null && aVar != this) {
            return aVar.getSpinnerStyle();
        }
        View view = this.c;
        if (view != null) {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (params instanceof SmartRefreshLayout.LayoutParams) {
                c cVar2 = ((SmartRefreshLayout.LayoutParams) params).spinnerStyle;
                this.d = cVar2;
                if (cVar2 != null) {
                    return cVar2;
                }
            }
            if (params != null && ((i = params.height) == 0 || i == -1)) {
                for (c style : c.f) {
                    if (style.i) {
                        this.d = style;
                        return style;
                    }
                }
            }
        }
        c cVar3 = c.a;
        this.d = cVar3;
        return cVar3;
    }

    public void g(@NonNull e kernel, int height, int maxDragHeight) {
        a aVar = this.f;
        if (aVar == null || aVar == this) {
            View view = this.c;
            if (view != null) {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                if (params instanceof SmartRefreshLayout.LayoutParams) {
                    kernel.e(this, ((SmartRefreshLayout.LayoutParams) params).backgroundColor);
                    return;
                }
                return;
            }
            return;
        }
        aVar.g(kernel, height, maxDragHeight);
    }

    public boolean m() {
        a aVar = this.f;
        return (aVar == null || aVar == this || !aVar.m()) ? false : true;
    }

    public void k(float percentX, int offsetX, int offsetMax) {
        a aVar = this.f;
        if (aVar != null && aVar != this) {
            aVar.k(percentX, offsetX, offsetMax);
        }
    }

    public void q(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        a aVar = this.f;
        if (aVar != null && aVar != this) {
            aVar.q(isDragging, percent, offset, height, maxDragHeight);
        }
    }

    public void j(@NonNull f refreshLayout, int height, int maxDragHeight) {
        a aVar = this.f;
        if (aVar != null && aVar != this) {
            aVar.j(refreshLayout, height, maxDragHeight);
        }
    }

    public void i(@NonNull f refreshLayout, int height, int maxDragHeight) {
        a aVar = this.f;
        if (aVar != null && aVar != this) {
            aVar.i(refreshLayout, height, maxDragHeight);
        }
    }

    public void h(@NonNull f refreshLayout, @NonNull b oldState, @NonNull b newState) {
        a aVar = this.f;
        if (aVar != null && aVar != this) {
            if ((this instanceof com.scwang.smart.refresh.layout.api.c) && (aVar instanceof d)) {
                if (oldState.isFooter) {
                    oldState = oldState.toHeader();
                }
                if (newState.isFooter) {
                    newState = newState.toHeader();
                }
            } else if ((this instanceof d) && (aVar instanceof com.scwang.smart.refresh.layout.api.c)) {
                if (oldState.isHeader) {
                    oldState = oldState.toFooter();
                }
                if (newState.isHeader) {
                    newState = newState.toFooter();
                }
            }
            h listener = this.f;
            if (listener != null) {
                listener.h(refreshLayout, oldState, newState);
            }
        }
    }

    @SuppressLint({"RestrictedApi"})
    public boolean b(boolean noMoreData) {
        a aVar = this.f;
        return (aVar instanceof com.scwang.smart.refresh.layout.api.c) && ((com.scwang.smart.refresh.layout.api.c) aVar).b(noMoreData);
    }
}
