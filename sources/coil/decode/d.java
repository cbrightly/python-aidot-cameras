package coil.decode;

import androidx.annotation.Px;
import coil.size.OriginalSize;
import coil.size.PixelSize;
import coil.size.Size;
import coil.size.e;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.k;
import kotlin.math.b;
import kotlin.ranges.n;
import okio.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: DecodeUtils.kt */
public final class d {
    @NotNull
    public static final d a = new d();
    @NotNull
    private static final f b;
    @NotNull
    private static final f c;
    @NotNull
    private static final f d;
    @NotNull
    private static final f e;
    @NotNull
    private static final f f;
    @NotNull
    private static final f g;
    @NotNull
    private static final f h;
    @NotNull
    private static final f i;
    @NotNull
    private static final f j;

    /* compiled from: DecodeUtils.kt */
    public final /* synthetic */ class a {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[e.values().length];
            iArr[e.FILL.ordinal()] = 1;
            iArr[e.FIT.ordinal()] = 2;
            a = iArr;
        }
    }

    private d() {
    }

    static {
        f.a aVar = f.Companion;
        b = aVar.d("GIF87a");
        c = aVar.d("GIF89a");
        d = aVar.d("RIFF");
        e = aVar.d("WEBP");
        f = aVar.d("VP8X");
        g = aVar.d("ftyp");
        h = aVar.d("msf1");
        i = aVar.d("hevc");
        j = aVar.d("hevx");
    }

    public static final boolean h(@NotNull okio.e source) {
        k.e(source, "source");
        return source.V(0, c) || source.V(0, b);
    }

    public static final boolean j(@NotNull okio.e source) {
        k.e(source, "source");
        return source.V(0, d) && source.V(8, e);
    }

    public static final boolean g(@NotNull okio.e source) {
        k.e(source, "source");
        return j(source) && source.V(12, f) && source.request(17) && ((byte) (source.getBuffer().n(16) & 2)) > 0;
    }

    public static final boolean i(@NotNull okio.e source) {
        k.e(source, "source");
        return source.V(4, g);
    }

    public static final boolean f(@NotNull okio.e source) {
        k.e(source, "source");
        return i(source) && (source.V(8, h) || source.V(8, i) || source.V(8, j));
    }

    public static final int a(@Px int srcWidth, @Px int srcHeight, @Px int dstWidth, @Px int dstHeight, @NotNull e scale) {
        k.e(scale, "scale");
        int widthInSampleSize = n.b(Integer.highestOneBit(srcWidth / dstWidth), 1);
        int heightInSampleSize = n.b(Integer.highestOneBit(srcHeight / dstHeight), 1);
        switch (a.a[scale.ordinal()]) {
            case 1:
                return Math.min(widthInSampleSize, heightInSampleSize);
            case 2:
                return Math.max(widthInSampleSize, heightInSampleSize);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public static final double d(@Px int srcWidth, @Px int srcHeight, @Px int dstWidth, @Px int dstHeight, @NotNull e scale) {
        k.e(scale, "scale");
        double widthPercent = ((double) dstWidth) / ((double) srcWidth);
        double heightPercent = ((double) dstHeight) / ((double) srcHeight);
        switch (a.a[scale.ordinal()]) {
            case 1:
                return Math.max(widthPercent, heightPercent);
            case 2:
                return Math.min(widthPercent, heightPercent);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public static final float e(@Px float srcWidth, @Px float srcHeight, @Px float dstWidth, @Px float dstHeight, @NotNull e scale) {
        k.e(scale, "scale");
        float widthPercent = dstWidth / srcWidth;
        float heightPercent = dstHeight / srcHeight;
        switch (a.a[scale.ordinal()]) {
            case 1:
                return Math.max(widthPercent, heightPercent);
            case 2:
                return Math.min(widthPercent, heightPercent);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public static final double c(@Px double srcWidth, @Px double srcHeight, @Px double dstWidth, @Px double dstHeight, @NotNull e scale) {
        k.e(scale, "scale");
        double widthPercent = dstWidth / srcWidth;
        double heightPercent = dstHeight / srcHeight;
        switch (a.a[scale.ordinal()]) {
            case 1:
                return Math.max(widthPercent, heightPercent);
            case 2:
                return Math.min(widthPercent, heightPercent);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public static final PixelSize b(int srcWidth, int srcHeight, @NotNull Size dstSize, @NotNull e scale) {
        k.e(dstSize, "dstSize");
        k.e(scale, "scale");
        if (dstSize instanceof OriginalSize) {
            return new PixelSize(srcWidth, srcHeight);
        }
        if (dstSize instanceof PixelSize) {
            double multiplier = d(srcWidth, srcHeight, ((PixelSize) dstSize).d(), ((PixelSize) dstSize).c(), scale);
            return new PixelSize(b.a(((double) srcWidth) * multiplier), b.a(((double) srcHeight) * multiplier));
        }
        throw new NoWhenBranchMatchedException();
    }
}
