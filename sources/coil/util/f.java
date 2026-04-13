package coil.util;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import coil.decode.b;
import coil.memory.s;
import coil.request.j;
import coil.request.l;
import coil.size.e;
import coil.target.c;
import java.io.Closeable;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.y;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import kotlin.text.x;
import okhttp3.b0;
import okhttp3.e;
import okhttp3.u;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Extensions.kt */
public final class f {
    private static final u a = new u.a().f();

    /* compiled from: Extensions.kt */
    public final /* synthetic */ class a {
        public static final /* synthetic */ int[] a;
        public static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[b.values().length];
            iArr[b.MEMORY_CACHE.ordinal()] = 1;
            iArr[b.MEMORY.ordinal()] = 2;
            iArr[b.DISK.ordinal()] = 3;
            iArr[b.NETWORK.ordinal()] = 4;
            a = iArr;
            int[] iArr2 = new int[ImageView.ScaleType.values().length];
            iArr2[ImageView.ScaleType.FIT_START.ordinal()] = 1;
            iArr2[ImageView.ScaleType.FIT_CENTER.ordinal()] = 2;
            iArr2[ImageView.ScaleType.FIT_END.ordinal()] = 3;
            iArr2[ImageView.ScaleType.CENTER_INSIDE.ordinal()] = 4;
            b = iArr2;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v0, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: coil.memory.t} */
    /* JADX WARNING: Multi-variable type inference failed */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final coil.memory.t g(@org.jetbrains.annotations.NotNull android.view.View r6) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.k.e(r6, r0)
            int r0 = coil.base.R$id.coil_request_manager
            java.lang.Object r1 = r6.getTag(r0)
            boolean r2 = r1 instanceof coil.memory.t
            r3 = 0
            if (r2 == 0) goto L_0x0013
            coil.memory.t r1 = (coil.memory.t) r1
            goto L_0x0014
        L_0x0013:
            r1 = r3
        L_0x0014:
            if (r1 != 0) goto L_0x003f
            monitor-enter(r6)
            r2 = 0
            java.lang.Object r4 = r6.getTag(r0)     // Catch:{ all -> 0x003c }
            boolean r5 = r4 instanceof coil.memory.t     // Catch:{ all -> 0x003c }
            if (r5 == 0) goto L_0x0023
            r3 = r4
            coil.memory.t r3 = (coil.memory.t) r3     // Catch:{ all -> 0x003c }
        L_0x0023:
            if (r3 != 0) goto L_0x0036
            coil.memory.t r3 = new coil.memory.t     // Catch:{ all -> 0x003c }
            r3.<init>()     // Catch:{ all -> 0x003c }
            r4 = r3
            r5 = 0
            r6.addOnAttachStateChangeListener(r4)     // Catch:{ all -> 0x003c }
            r6.setTag(r0, r4)     // Catch:{ all -> 0x003c }
            goto L_0x0039
        L_0x0036:
            r0 = 0
        L_0x0039:
            monitor-exit(r6)
            r1 = r3
            goto L_0x003f
        L_0x003c:
            r0 = move-exception
            monitor-exit(r6)
            throw r0
        L_0x003f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.util.f.g(android.view.View):coil.memory.t");
    }

    @NotNull
    public static final String b(@NotNull b $this$emoji) {
        k.e($this$emoji, "<this>");
        switch (a.a[$this$emoji.ordinal()]) {
            case 1:
            case 2:
                return "🧠";
            case 3:
                return "💾";
            case 4:
                return "☁️ ";
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public static final int i(@NotNull Drawable $this$width) {
        Bitmap bitmap;
        k.e($this$width, "<this>");
        Integer num = null;
        BitmapDrawable bitmapDrawable = $this$width instanceof BitmapDrawable ? (BitmapDrawable) $this$width : null;
        if (!(bitmapDrawable == null || (bitmap = bitmapDrawable.getBitmap()) == null)) {
            num = Integer.valueOf(bitmap.getWidth());
        }
        return num == null ? $this$width.getIntrinsicWidth() : num.intValue();
    }

    public static final int d(@NotNull Drawable $this$height) {
        Bitmap bitmap;
        k.e($this$height, "<this>");
        Integer num = null;
        BitmapDrawable bitmapDrawable = $this$height instanceof BitmapDrawable ? (BitmapDrawable) $this$height : null;
        if (!(bitmapDrawable == null || (bitmap = bitmapDrawable.getBitmap()) == null)) {
            num = Integer.valueOf(bitmap.getHeight());
        }
        return num == null ? $this$height.getIntrinsicHeight() : num.intValue();
    }

    public static final boolean k(@NotNull Drawable $this$isVector) {
        k.e($this$isVector, "<this>");
        return ($this$isVector instanceof VectorDrawableCompat) || (Build.VERSION.SDK_INT >= 21 && ($this$isVector instanceof VectorDrawable));
    }

    public static final void a(@NotNull Closeable $this$closeQuietly) {
        k.e($this$closeQuietly, "<this>");
        try {
            $this$closeQuietly.close();
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception e) {
        }
    }

    @NotNull
    public static final e h(@NotNull ImageView $this$scale) {
        k.e($this$scale, "<this>");
        ImageView.ScaleType scaleType = $this$scale.getScaleType();
        switch (scaleType == null ? -1 : a.b[scaleType.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
                return e.FIT;
            default:
                return e.FILL;
        }
    }

    @NotNull
    public static final e.a m(@NotNull kotlin.jvm.functions.a<? extends e.a> initializer) {
        k.e(initializer, "initializer");
        return new a(i.b(initializer));
    }

    /* access modifiers changed from: private */
    public static final okhttp3.e n(g $lazy, b0 it) {
        k.e($lazy, "$lazy");
        return ((e.a) $lazy.getValue()).a(it);
    }

    @Nullable
    public static final String e(@NotNull MimeTypeMap $this$getMimeTypeFromUrl, @Nullable String url) {
        k.e($this$getMimeTypeFromUrl, "<this>");
        if (url == null || w.A(url)) {
            return null;
        }
        return $this$getMimeTypeFromUrl.getMimeTypeFromExtension(x.S0(x.U0(x.c1(x.c1(url, '#', (String) null, 2, (Object) null), '?', (String) null, 2, (Object) null), '/', (String) null, 2, (Object) null), '.', ""));
    }

    @Nullable
    public static final String c(@NotNull Uri $this$firstPathSegment) {
        k.e($this$firstPathSegment, "<this>");
        List<String> pathSegments = $this$firstPathSegment.getPathSegments();
        k.d(pathSegments, "pathSegments");
        return (String) y.U(pathSegments);
    }

    public static final int f(@NotNull Configuration $this$nightMode) {
        k.e($this$nightMode, "<this>");
        return $this$nightMode.uiMode & 48;
    }

    public static final u p(@Nullable u $this$orEmpty) {
        return $this$orEmpty == null ? a : $this$orEmpty;
    }

    @NotNull
    public static final l o(@Nullable l $this$orEmpty) {
        return $this$orEmpty == null ? l.d : $this$orEmpty;
    }

    public static final boolean j() {
        return k.a(Looper.myLooper(), Looper.getMainLooper());
    }

    public static final void q(@NotNull s $this$metadata, @Nullable j.a value) {
        k.e($this$metadata, "<this>");
        coil.target.b d = $this$metadata.d();
        View view = null;
        c cVar = d instanceof c ? (c) d : null;
        if (cVar != null) {
            view = cVar.getView();
        }
        if (view != null) {
            g(view).g(value);
        }
    }
}
