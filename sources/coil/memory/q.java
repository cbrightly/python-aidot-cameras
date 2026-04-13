package coil.memory;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import androidx.annotation.WorkerThread;
import androidx.core.view.ViewCompat;
import coil.request.NullRequestDataException;
import coil.request.c;
import coil.request.g;
import coil.request.i;
import coil.size.Size;
import coil.target.b;
import coil.util.h;
import coil.util.m;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import kotlin.collections.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RequestService.kt */
public final class q {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    public static final Bitmap.Config[] b = (Build.VERSION.SDK_INT >= 26 ? new Bitmap.Config[]{Bitmap.Config.ARGB_8888, Bitmap.Config.RGBA_F16} : new Bitmap.Config[]{Bitmap.Config.ARGB_8888});
    @Nullable
    private final m c;
    @NotNull
    private final h d = h.a.a();

    public q(@Nullable m logger) {
        this.c = logger;
    }

    @NotNull
    public final g a(@NotNull i request, @NotNull Throwable throwable) {
        k.e(request, Progress.REQUEST);
        k.e(throwable, "throwable");
        return new g(throwable instanceof NullRequestDataException ? request.t() : request.s(), request, throwable);
    }

    @NotNull
    @WorkerThread
    public final coil.decode.m e(@NotNull i request, @NotNull Size size, boolean isOnline) {
        k.e(request, Progress.REQUEST);
        k.e(size, "size");
        Bitmap.Config config = d(request) && c(request, size) ? request.j() : Bitmap.Config.ARGB_8888;
        return new coil.decode.m(request.l(), config, request.k(), request.G(), h.b(request), request.i() && request.J().isEmpty() && config != Bitmap.Config.ALPHA_8, request.F(), request.v(), request.B(), request.z(), request.q(), isOnline ? request.A() : c.DISABLED);
    }

    public final boolean b(@NotNull i request, @NotNull Bitmap.Config requestedConfig) {
        k.e(request, Progress.REQUEST);
        k.e(requestedConfig, "requestedConfig");
        if (!coil.util.c.d(requestedConfig)) {
            return true;
        }
        if (!request.h()) {
            return false;
        }
        b target = request.I();
        if (target instanceof coil.target.c) {
            View $this$isConfigValidForHardware_u24lambda_u2d0 = ((coil.target.c) target).getView();
            if (((!ViewCompat.isAttachedToWindow($this$isConfigValidForHardware_u24lambda_u2d0) || $this$isConfigValidForHardware_u24lambda_u2d0.isHardwareAccelerated()) ? null : 1) != null) {
                return false;
            }
        }
        return true;
    }

    @WorkerThread
    private final boolean c(i request, Size size) {
        return b(request, request.j()) && this.d.a(size, this.c);
    }

    private final boolean d(i request) {
        return request.J().isEmpty() || l.r(b, request.j());
    }

    /* compiled from: RequestService.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
