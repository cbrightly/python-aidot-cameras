package coil.bitmap;

import android.graphics.Bitmap;
import android.os.Build;
import androidx.annotation.Px;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BitmapPoolStrategy.kt */
public interface d {
    @NotNull
    public static final a a = a.a;

    @NotNull
    String a(@Px int i, @Px int i2, @NotNull Bitmap.Config config);

    void b(@NotNull Bitmap bitmap);

    @Nullable
    Bitmap c(@Px int i, @Px int i2, @NotNull Bitmap.Config config);

    @NotNull
    String d(@NotNull Bitmap bitmap);

    @Nullable
    Bitmap removeLast();

    /* compiled from: BitmapPoolStrategy.kt */
    public static final class a {
        static final /* synthetic */ a a = new a();

        private a() {
        }

        @NotNull
        public final d a() {
            if (Build.VERSION.SDK_INT >= 19) {
                return new j();
            }
            return new b();
        }
    }
}
