package com.leedarson.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: TriangleDrawable */
public class b extends Drawable {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int a = -1;
    private int b;

    public b(int arrowDirection, int bgColor) {
        this.b = arrowDirection;
        this.a = bgColor;
    }

    public void draw(@NonNull Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11795, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(this.a);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(a(), paint);
        }
    }

    private Path a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11796, new Class[0], Path.class);
        if (proxy.isSupported) {
            return (Path) proxy.result;
        }
        Rect bound = getBounds();
        Path path = new Path();
        int i = this.b;
        if (i == 12) {
            path.moveTo((float) (bound.right / 2), 0.0f);
            path.lineTo(0.0f, (float) bound.bottom);
            path.lineTo((float) bound.right, (float) bound.bottom);
            path.close();
        } else if (i == 13) {
            path.moveTo((float) (bound.right / 2), (float) bound.bottom);
            path.lineTo(0.0f, 0.0f);
            path.lineTo((float) bound.right, 0.0f);
            path.close();
        } else if (i == 14) {
            path.moveTo(0.0f, (float) (bound.bottom / 2));
            path.lineTo((float) bound.right, 0.0f);
            path.lineTo((float) bound.right, (float) bound.bottom);
            path.close();
        } else {
            path.moveTo((float) bound.right, (float) (bound.bottom / 2));
            path.lineTo(0.0f, 0.0f);
            path.lineTo(0.0f, (float) bound.bottom);
            path.close();
        }
        return path;
    }

    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    public int getOpacity() {
        return -2;
    }
}
