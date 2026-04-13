package com.scwang.smart.refresh.classics;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import com.scwang.smart.refresh.classics.ClassicsAbstract;
import com.scwang.smart.refresh.footer.classics.R$id;
import com.scwang.smart.refresh.layout.api.a;
import com.scwang.smart.refresh.layout.api.e;
import com.scwang.smart.refresh.layout.api.f;
import com.scwang.smart.refresh.layout.constant.c;
import com.scwang.smart.refresh.layout.simple.SimpleComponent;
import com.scwang.smart.refresh.layout.util.b;

public abstract class ClassicsAbstract<T extends ClassicsAbstract> extends SimpleComponent implements a {
    public static final int q = R$id.srl_classics_title;
    public static final int x = R$id.srl_classics_arrow;
    public static final int y = R$id.srl_classics_progress;
    protected int A4 = 500;
    protected int B4 = 20;
    protected int C4 = 20;
    protected int D4 = 0;
    protected ImageView a1;
    protected com.scwang.smart.drawable.a a2;
    protected ImageView p0;
    protected e p1;
    protected com.scwang.smart.drawable.a p2;
    protected boolean p3;
    protected boolean p4;
    protected TextView z;
    protected int z4;

    public ClassicsAbstract(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.d = c.a;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.D4 == 0) {
            this.B4 = getPaddingTop();
            int paddingBottom = getPaddingBottom();
            this.C4 = paddingBottom;
            if (this.B4 == 0 || paddingBottom == 0) {
                int paddingLeft = getPaddingLeft();
                int paddingRight = getPaddingRight();
                int i = this.B4;
                if (i == 0) {
                    i = b.c(20.0f);
                }
                this.B4 = i;
                int i2 = this.C4;
                if (i2 == 0) {
                    i2 = b.c(20.0f);
                }
                this.C4 = i2;
                setPadding(paddingLeft, this.B4, paddingRight, i2);
            }
            setClipToPadding(false);
        }
        if (View.MeasureSpec.getMode(heightMeasureSpec) == 1073741824) {
            int parentHeight = View.MeasureSpec.getSize(heightMeasureSpec);
            int i3 = this.D4;
            if (parentHeight < i3) {
                int padding = (parentHeight - i3) / 2;
                setPadding(getPaddingLeft(), padding, getPaddingRight(), padding);
            } else {
                setPadding(getPaddingLeft(), 0, getPaddingRight(), 0);
            }
        } else {
            setPadding(getPaddingLeft(), this.B4, getPaddingRight(), this.C4);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.D4 == 0) {
            for (int i4 = 0; i4 < getChildCount(); i4++) {
                int height = getChildAt(i4).getMeasuredHeight();
                if (this.D4 < height) {
                    this.D4 = height;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        View arrowView = this.p0;
        View progressView = this.a1;
        arrowView.animate().cancel();
        progressView.animate().cancel();
        Drawable drawable = this.a1.getDrawable();
        if ((drawable instanceof Animatable) && ((Animatable) drawable).isRunning()) {
            ((Animatable) drawable).stop();
        }
    }

    /* access modifiers changed from: protected */
    public T r() {
        return this;
    }

    public void g(@NonNull e kernel, int height, int maxDragHeight) {
        this.p1 = kernel;
        kernel.e(this, this.z4);
    }

    public void i(@NonNull f refreshLayout, int height, int maxDragHeight) {
        View progressView = this.a1;
        if (progressView.getVisibility() != 0) {
            progressView.setVisibility(0);
            Drawable drawable = this.a1.getDrawable();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            } else {
                progressView.animate().rotation(36000.0f).setDuration(100000);
            }
        }
    }

    public void j(@NonNull f refreshLayout, int height, int maxDragHeight) {
        i(refreshLayout, height, maxDragHeight);
    }

    public int f(@NonNull f refreshLayout, boolean success) {
        View progressView = this.a1;
        Drawable drawable = this.a1.getDrawable();
        if (!(drawable instanceof Animatable)) {
            progressView.animate().rotation(0.0f).setDuration(0);
        } else if (((Animatable) drawable).isRunning()) {
            ((Animatable) drawable).stop();
        }
        progressView.setVisibility(8);
        return this.A4;
    }

    public void setPrimaryColors(@ColorInt int... colors) {
        if (colors.length > 0) {
            if (!(getBackground() instanceof BitmapDrawable) && !this.p4) {
                t(colors[0]);
                this.p4 = false;
            }
            if (!this.p3) {
                if (colors.length > 1) {
                    s(colors[1]);
                }
                this.p3 = false;
            }
        }
    }

    public T t(@ColorInt int primaryColor) {
        this.p4 = true;
        this.z4 = primaryColor;
        e eVar = this.p1;
        if (eVar != null) {
            eVar.e(this, primaryColor);
        }
        return r();
    }

    public T s(@ColorInt int accentColor) {
        this.p3 = true;
        this.z.setTextColor(accentColor);
        com.scwang.smart.drawable.a aVar = this.a2;
        if (aVar != null) {
            aVar.a(accentColor);
            this.p0.invalidateDrawable(this.a2);
        }
        com.scwang.smart.drawable.a aVar2 = this.p2;
        if (aVar2 != null) {
            aVar2.a(accentColor);
            this.a1.invalidateDrawable(this.p2);
        }
        return r();
    }
}
