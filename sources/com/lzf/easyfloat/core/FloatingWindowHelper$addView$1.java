package com.lzf.easyfloat.core;

import android.view.MotionEvent;
import androidx.core.app.NotificationCompat;
import com.lzf.easyfloat.interfaces.OnFloatTouchListener;
import com.lzf.easyfloat.widget.ParentFrameLayout;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: FloatingWindowHelper.kt */
public final class FloatingWindowHelper$addView$1 implements OnFloatTouchListener {
    final /* synthetic */ FloatingWindowHelper this$0;

    FloatingWindowHelper$addView$1(FloatingWindowHelper $receiver) {
        this.this$0 = $receiver;
    }

    public void onTouch(@NotNull MotionEvent event) {
        k.e(event, NotificationCompat.CATEGORY_EVENT);
        TouchUtils access$getTouchUtils$p = this.this$0.touchUtils;
        if (access$getTouchUtils$p == null) {
            k.t("touchUtils");
            access$getTouchUtils$p = null;
        }
        ParentFrameLayout frameLayout = this.this$0.getFrameLayout();
        k.c(frameLayout);
        access$getTouchUtils$p.updateFloat(frameLayout, event, this.this$0.getWindowManager(), this.this$0.getParams());
    }
}
