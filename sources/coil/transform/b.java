package coil.transform;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import coil.bitmap.c;
import coil.size.Size;
import kotlin.coroutines.d;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CircleCropTransformation.kt */
public final class b implements d {
    @NotNull
    private static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    @Deprecated
    private static final PorterDuffXfermode b = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    @NotNull
    public String key() {
        String name = b.class.getName();
        k.d(name, "CircleCropTransformation::class.java.name");
        return name;
    }

    @Nullable
    public Object a(@NotNull c pool, @NotNull Bitmap input, @NotNull Size size, @NotNull d<? super Bitmap> $completion) {
        Paint paint = new Paint(3);
        int minSize = Math.min(input.getWidth(), input.getHeight());
        float radius = ((float) minSize) / 2.0f;
        c cVar = pool;
        Bitmap output = pool.c(minSize, minSize, coil.util.c.c(input));
        Canvas $this$transform_u24lambda_u2d0 = new Canvas(output);
        $this$transform_u24lambda_u2d0.drawCircle(radius, radius, radius, paint);
        paint.setXfermode(b);
        Bitmap bitmap = input;
        $this$transform_u24lambda_u2d0.drawBitmap(input, radius - (((float) input.getWidth()) / 2.0f), radius - (((float) input.getHeight()) / 2.0f), paint);
        return output;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof b;
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    @NotNull
    public String toString() {
        return "CircleCropTransformation()";
    }

    /* compiled from: CircleCropTransformation.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
