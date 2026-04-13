package coil.memory;

import android.os.Build;
import coil.size.Size;
import coil.util.m;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HardwareBitmapService.kt */
public abstract class h {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);

    public /* synthetic */ h(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract boolean a(@NotNull Size size, @Nullable m mVar);

    private h() {
    }

    /* compiled from: HardwareBitmapService.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        @NotNull
        public final h a() {
            int i = Build.VERSION.SDK_INT;
            if (i < 26 || g.b) {
                return new i(false);
            }
            if (i == 26 || i == 27) {
                return l.b;
            }
            return new i(true);
        }
    }
}
