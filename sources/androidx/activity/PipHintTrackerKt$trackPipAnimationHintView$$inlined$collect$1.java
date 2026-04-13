package androidx.activity;

import android.app.Activity;
import android.graphics.Rect;
import kotlin.x;
import kotlinx.coroutines.flow.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Collect.kt */
public final class PipHintTrackerKt$trackPipAnimationHintView$$inlined$collect$1 implements d<Rect> {
    final /* synthetic */ Activity $this_trackPipAnimationHintView$inlined;

    public PipHintTrackerKt$trackPipAnimationHintView$$inlined$collect$1(Activity activity) {
        this.$this_trackPipAnimationHintView$inlined = activity;
    }

    @Nullable
    public Object emit(Object value, @NotNull kotlin.coroutines.d dVar) {
        Api26Impl.INSTANCE.setPipParamsSourceRectHint(this.$this_trackPipAnimationHintView$inlined, (Rect) value);
        return x.a;
    }
}
