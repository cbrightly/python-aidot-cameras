package coil.util;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import coil.fetch.g;
import coil.request.i;
import coil.size.b;
import coil.target.c;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.k;
import kotlin.n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Requests.kt */
public final class h {

    /* compiled from: Requests.kt */
    public final /* synthetic */ class a {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[b.values().length];
            iArr[b.EXACT.ordinal()] = 1;
            iArr[b.INEXACT.ordinal()] = 2;
            iArr[b.AUTOMATIC.ordinal()] = 3;
            a = iArr;
        }
    }

    @Nullable
    public static final Drawable c(@NotNull i $this$getDrawableCompat, @Nullable Drawable drawable, @Nullable @DrawableRes Integer resId, @Nullable Drawable drawable2) {
        k.e($this$getDrawableCompat, "<this>");
        if (drawable != null) {
            return drawable;
        }
        if (resId == null) {
            return drawable2;
        }
        if (resId.intValue() == 0) {
            return null;
        }
        return e.a($this$getDrawableCompat.l(), resId.intValue());
    }

    @Nullable
    public static final <T> g<T> a(@NotNull i $this$fetcher, @NotNull T data) {
        k.e($this$fetcher, "<this>");
        k.e(data, "data");
        n<g<?>, Class<?>> u = $this$fetcher.u();
        if (u == null) {
            return null;
        }
        g fetcher = u.component1();
        if (u.component2().isAssignableFrom(data.getClass())) {
            return fetcher;
        }
        throw new IllegalStateException((fetcher.getClass().getName() + " cannot handle data with type " + data.getClass().getName() + '.').toString());
    }

    public static final boolean b(@NotNull i $this$allowInexactSize) {
        k.e($this$allowInexactSize, "<this>");
        switch (a.a[$this$allowInexactSize.E().ordinal()]) {
            case 1:
                return false;
            case 2:
                return true;
            case 3:
                i $this$_get_allowInexactSize__u24lambda_u2d1 = $this$allowInexactSize;
                if (!($this$_get_allowInexactSize__u24lambda_u2d1.I() instanceof c) || !(((c) $this$_get_allowInexactSize__u24lambda_u2d1.I()).getView() instanceof ImageView) || !($this$_get_allowInexactSize__u24lambda_u2d1.H() instanceof coil.size.g) || ((coil.size.g) $this$_get_allowInexactSize__u24lambda_u2d1.H()).getView() != ((c) $this$_get_allowInexactSize__u24lambda_u2d1.I()).getView()) {
                    return $this$_get_allowInexactSize__u24lambda_u2d1.p().k() == null && ($this$_get_allowInexactSize__u24lambda_u2d1.H() instanceof coil.size.a);
                }
                return true;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
}
