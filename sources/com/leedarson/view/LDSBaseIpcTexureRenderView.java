package com.leedarson.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.TextureView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.meituan.robust.ChangeQuickRedirect;

public abstract class LDSBaseIpcTexureRenderView extends TextureView {
    public static ChangeQuickRedirect changeQuickRedirect;
    public boolean c = true;

    public interface a {
        void a();
    }

    public interface b {
        boolean a(float f, MotionEvent motionEvent);
    }

    public abstract void a();

    public abstract void b();

    public abstract void setDefaultTouchEvent(a aVar);

    public abstract void setHasScale(boolean z);

    public LDSBaseIpcTexureRenderView(@NonNull Context context) {
        super(context);
    }

    public LDSBaseIpcTexureRenderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LDSBaseIpcTexureRenderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
