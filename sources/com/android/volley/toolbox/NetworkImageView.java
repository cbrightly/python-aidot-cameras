package com.android.volley.toolbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.k;

public class NetworkImageView extends ImageView {
    private k.a a1;
    private String c;
    private int d;
    @Nullable
    private Drawable f;
    private k p0;
    @Nullable
    private Bitmap q;
    /* access modifiers changed from: private */
    public int x;
    /* access modifiers changed from: private */
    @Nullable
    public Drawable y;
    /* access modifiers changed from: private */
    @Nullable
    public Bitmap z;

    public NetworkImageView(Context context) {
        this(context, (AttributeSet) null);
    }

    public NetworkImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NetworkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setDefaultImageResId(int defaultImage) {
        this.q = null;
        this.f = null;
        this.d = defaultImage;
    }

    public void setDefaultImageDrawable(@Nullable Drawable defaultImageDrawable) {
        this.d = 0;
        this.q = null;
        this.f = defaultImageDrawable;
    }

    public void setDefaultImageBitmap(Bitmap defaultImage) {
        this.d = 0;
        this.f = null;
        this.q = defaultImage;
    }

    public void setErrorImageResId(int errorImage) {
        this.z = null;
        this.y = null;
        this.x = errorImage;
    }

    public void setErrorImageDrawable(@Nullable Drawable errorImageDrawable) {
        this.x = 0;
        this.z = null;
        this.y = errorImageDrawable;
    }

    public void setErrorImageBitmap(Bitmap errorImage) {
        this.x = 0;
        this.y = null;
        this.z = errorImage;
    }

    /* access modifiers changed from: package-private */
    public void d(boolean isInLayoutPass) {
        int width = getWidth();
        int height = getHeight();
        ImageView.ScaleType scaleType = getScaleType();
        boolean wrapWidth = false;
        boolean wrapHeight = false;
        boolean z2 = true;
        int maxHeight = 0;
        if (getLayoutParams() != null) {
            wrapWidth = getLayoutParams().width == -2;
            wrapHeight = getLayoutParams().height == -2;
        }
        if (!wrapWidth || !wrapHeight) {
            z2 = false;
        }
        boolean isFullyWrapContent = z2;
        if (width != 0 || height != 0 || isFullyWrapContent) {
            if (TextUtils.isEmpty(this.c)) {
                k.a aVar = this.a1;
                if (aVar == null) {
                    e();
                } else {
                    aVar.a();
                    throw null;
                }
            } else {
                k.a aVar2 = this.a1;
                if (aVar2 == null) {
                    int maxWidth = wrapWidth ? 0 : width;
                    if (!wrapHeight) {
                        maxHeight = height;
                    }
                    this.p0.a(this.c, new a(isInLayoutPass), maxWidth, maxHeight, scaleType);
                    throw null;
                }
                aVar2.b();
                throw null;
            }
        }
    }

    public class a implements k.b {
        final /* synthetic */ boolean a;

        a(boolean z) {
            this.a = z;
        }

        public void onErrorResponse(VolleyError error) {
            if (NetworkImageView.this.x != 0) {
                NetworkImageView networkImageView = NetworkImageView.this;
                networkImageView.setImageResource(networkImageView.x);
            } else if (NetworkImageView.this.y != null) {
                NetworkImageView networkImageView2 = NetworkImageView.this;
                networkImageView2.setImageDrawable(networkImageView2.y);
            } else if (NetworkImageView.this.z != null) {
                NetworkImageView networkImageView3 = NetworkImageView.this;
                networkImageView3.setImageBitmap(networkImageView3.z);
            }
        }
    }

    private void e() {
        int i = this.d;
        if (i != 0) {
            setImageResource(i);
            return;
        }
        Drawable drawable = this.f;
        if (drawable != null) {
            setImageDrawable(drawable);
            return;
        }
        Bitmap bitmap = this.q;
        if (bitmap != null) {
            setImageBitmap(bitmap);
        } else {
            setImageBitmap((Bitmap) null);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        d(true);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        k.a aVar = this.a1;
        if (aVar == null) {
            super.onDetachedFromWindow();
        } else {
            aVar.a();
            throw null;
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }
}
