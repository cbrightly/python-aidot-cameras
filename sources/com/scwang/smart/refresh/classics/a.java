package com.scwang.smart.refresh.classics;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import androidx.annotation.NonNull;

/* compiled from: ArrowDrawable */
public class a extends com.scwang.smart.drawable.a {
    private int d = 0;
    private int f = 0;
    private Path q = new Path();

    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        int width = bounds.width();
        int height = bounds.height();
        if (!(this.d == width && this.f == height)) {
            int lineWidth = (width * 30) / 225;
            this.q.reset();
            float vector1 = ((float) lineWidth) * 0.70710677f;
            float vector2 = ((float) lineWidth) / 0.70710677f;
            this.q.moveTo(((float) width) / 2.0f, (float) height);
            this.q.lineTo(0.0f, ((float) height) / 2.0f);
            this.q.lineTo(vector1, (((float) height) / 2.0f) - vector1);
            this.q.lineTo((((float) width) / 2.0f) - (((float) lineWidth) / 2.0f), (((float) height) - vector2) - (((float) lineWidth) / 2.0f));
            this.q.lineTo((((float) width) / 2.0f) - (((float) lineWidth) / 2.0f), 0.0f);
            this.q.lineTo((((float) width) / 2.0f) + (((float) lineWidth) / 2.0f), 0.0f);
            this.q.lineTo((((float) width) / 2.0f) + (((float) lineWidth) / 2.0f), (((float) height) - vector2) - (((float) lineWidth) / 2.0f));
            this.q.lineTo(((float) width) - vector1, (((float) height) / 2.0f) - vector1);
            this.q.lineTo((float) width, ((float) height) / 2.0f);
            this.q.close();
            this.d = width;
            this.f = height;
        }
        canvas.drawPath(this.q, this.c);
    }
}
