package androidx.activity;

import androidx.lifecycle.LifecycleOwner;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: OnBackPressedDispatcher.kt */
public final class OnBackPressedDispatcherKt {
    public static /* synthetic */ OnBackPressedCallback addCallback$default(OnBackPressedDispatcher onBackPressedDispatcher, LifecycleOwner lifecycleOwner, boolean z, l lVar, int i, Object obj) {
        if ((i & 1) != 0) {
            lifecycleOwner = null;
        }
        if ((i & 2) != 0) {
            z = true;
        }
        return addCallback(onBackPressedDispatcher, lifecycleOwner, z, lVar);
    }

    @NotNull
    public static final OnBackPressedCallback addCallback(@NotNull OnBackPressedDispatcher $this$addCallback, @Nullable LifecycleOwner owner, boolean enabled, @NotNull l<? super OnBackPressedCallback, x> onBackPressed) {
        k.e($this$addCallback, "<this>");
        k.e(onBackPressed, "onBackPressed");
        OnBackPressedDispatcherKt$addCallback$callback$1 callback = new OnBackPressedDispatcherKt$addCallback$callback$1(onBackPressed, enabled);
        if (owner != null) {
            $this$addCallback.addCallback(owner, callback);
        } else {
            $this$addCallback.addCallback(callback);
        }
        return callback;
    }
}
