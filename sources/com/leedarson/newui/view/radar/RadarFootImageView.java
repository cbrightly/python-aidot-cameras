package com.leedarson.newui.view.radar;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import com.devs.vectorchildfinder.VectorDrawableCompat;
import com.leedarson.smartcamera.kvswebrtc.beans.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class RadarFootImageView extends AppCompatImageView implements b {
    public static int c = 0;
    public static ChangeQuickRedirect changeQuickRedirect;
    public static int d = 0;
    private a f;
    private VectorDrawableCompat.b q;

    public RadarFootImageView(Context context) {
        super(context);
    }

    public RadarFootImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RadarFootImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setData(a data) {
        this.f = data;
    }

    public a getData() {
        return this.f;
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 5428, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
        }
    }

    public void setFullPath(VectorDrawableCompat.b fullPath) {
        this.q = fullPath;
    }

    public VectorDrawableCompat.b getFullPath() {
        return this.q;
    }
}
