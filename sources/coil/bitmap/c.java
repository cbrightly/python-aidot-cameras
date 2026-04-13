package coil.bitmap;

import android.graphics.Bitmap;
import androidx.annotation.Px;
import org.jetbrains.annotations.NotNull;

/* compiled from: BitmapPool.kt */
public interface c {
    @NotNull
    public static final a a = a.a;

    void a(int i);

    void b(@NotNull Bitmap bitmap);

    @NotNull
    Bitmap c(@Px int i, @Px int i2, @NotNull Bitmap.Config config);

    @NotNull
    Bitmap e(@Px int i, @Px int i2, @NotNull Bitmap.Config config);

    /* compiled from: BitmapPool.kt */
    public static final class a {
        static final /* synthetic */ a a = new a();

        private a() {
        }
    }
}
