package androidx.activity.result;

import android.content.Intent;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ActivityResult.kt */
public final class ActivityResultKt {
    public static final int component1(@NotNull ActivityResult $this$component1) {
        k.e($this$component1, "<this>");
        return $this$component1.getResultCode();
    }

    @Nullable
    public static final Intent component2(@NotNull ActivityResult $this$component2) {
        k.e($this$component2, "<this>");
        return $this$component2.getData();
    }
}
