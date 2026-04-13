package com.leedarson.base.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import com.leedarson.module_base.R$drawable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import io.reactivex.android.schedulers.a;
import io.reactivex.disposables.b;
import io.reactivex.l;
import java.util.concurrent.TimeUnit;

public class CustomSecurityFloatView extends View {
    public static ChangeQuickRedirect changeQuickRedirect;
    private float[] c = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    Bitmap d;
    int f;
    int q;
    int x;
    Paint y = null;

    public CustomSecurityFloatView(Context context) {
        super(context);
    }

    public CustomSecurityFloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        c(90.0f, 0.0f, 0.0f, 90.0f);
        this.d = BitmapFactory.decodeResource(context.getResources(), R$drawable.slider);
        b X = l.C(100, TimeUnit.MILLISECONDS).J(a.a()).X(new c(this));
        Paint paint = new Paint();
        this.y = paint;
        paint.setColor(-1);
        this.y.setAlpha(239);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public /* synthetic */ void b(Long l) {
        if (!PatchProxy.proxy(new Object[]{l}, this, changeQuickRedirect, false, 653, new Class[]{Long.class}, Void.TYPE).isSupported) {
            invalidate();
        }
    }

    public void onVisibilityChanged(@NonNull View changedView, int visibility) {
        if (!PatchProxy.proxy(new Object[]{changedView, new Integer(visibility)}, this, changeQuickRedirect, false, 650, new Class[]{View.class, Integer.TYPE}, Void.TYPE).isSupported) {
            super.onVisibilityChanged(changedView, visibility);
        }
    }

    public void c(float leftTop, float rightTop, float rightBottom, float leftBottom) {
        float[] fArr = this.c;
        fArr[0] = leftTop;
        fArr[1] = leftTop;
        fArr[2] = rightTop;
        fArr[3] = rightTop;
        fArr[4] = rightBottom;
        fArr[5] = rightBottom;
        fArr[6] = leftBottom;
        fArr[7] = leftBottom;
    }

    public void onFinishInflate() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 651, new Class[0], Void.TYPE).isSupported) {
            super.onFinishInflate();
            this.f = getWidth();
            this.q = getHeight();
            this.x = this.d.getWidth();
        }
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 652, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            canvas.drawCircle((float) (getWidth() / 2), (float) (getWidth() / 2), (float) (getWidth() / 2), this.y);
        }
    }
}
