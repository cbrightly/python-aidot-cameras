package com.leedarson.newui.view.radar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class PRIView extends AppCompatTextView {
    public static ChangeQuickRedirect changeQuickRedirect;

    public PRIView(Context context) {
        super(context);
        a();
    }

    public PRIView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        a();
    }

    public PRIView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a();
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5407, new Class[0], Void.TYPE).isSupported) {
            setLayerType(1, (Paint) null);
        }
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 5408, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
        }
    }
}
