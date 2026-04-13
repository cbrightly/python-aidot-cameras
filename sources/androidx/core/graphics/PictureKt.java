package androidx.core.graphics;

import android.graphics.Canvas;
import android.graphics.Picture;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.j;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: Picture.kt */
public final class PictureKt {
    @NotNull
    public static final Picture record(@NotNull Picture $this$record, int width, int height, @NotNull l<? super Canvas, x> block) {
        k.e($this$record, "<this>");
        k.e(block, "block");
        Canvas c = $this$record.beginRecording(width, height);
        k.d(c, "beginRecording(width, height)");
        try {
            block.invoke(c);
            return $this$record;
        } finally {
            j.b(1);
            $this$record.endRecording();
            j.a(1);
        }
    }
}
