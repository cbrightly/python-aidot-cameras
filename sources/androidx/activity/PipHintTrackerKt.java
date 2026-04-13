package androidx.activity;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import androidx.annotation.RequiresApi;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.c;
import kotlin.x;
import kotlinx.coroutines.flow.e;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PipHintTracker.kt */
public final class PipHintTrackerKt {
    /* access modifiers changed from: private */
    public static final Rect trackPipAnimationHintView$positionInWindow(View $this$trackPipAnimationHintView_u24positionInWindow) {
        Rect position = new Rect();
        $this$trackPipAnimationHintView_u24positionInWindow.getGlobalVisibleRect(position);
        return position;
    }

    @RequiresApi(26)
    @Nullable
    public static final Object trackPipAnimationHintView(@NotNull Activity $this$trackPipAnimationHintView, @NotNull View view, @NotNull d<? super x> $completion) {
        Object a = e.e(new PipHintTrackerKt$trackPipAnimationHintView$flow$1(view, (d<? super PipHintTrackerKt$trackPipAnimationHintView$flow$1>) null)).a(new PipHintTrackerKt$trackPipAnimationHintView$$inlined$collect$1($this$trackPipAnimationHintView), $completion);
        if (a == c.d()) {
            return a;
        }
        return x.a;
    }
}
