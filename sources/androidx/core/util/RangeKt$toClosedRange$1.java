package androidx.core.util;

import android.util.Range;
import kotlin.ranges.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: Range.kt */
public final class RangeKt$toClosedRange$1 implements f<T> {
    final /* synthetic */ Range<T> $this_toClosedRange;

    RangeKt$toClosedRange$1(Range<T> $receiver) {
        this.$this_toClosedRange = $receiver;
    }

    public boolean contains(@NotNull T value) {
        return f.a.a(this, value);
    }

    public boolean isEmpty() {
        return f.a.b(this);
    }

    public T getEndInclusive() {
        return this.$this_toClosedRange.getUpper();
    }

    public T getStart() {
        return this.$this_toClosedRange.getLower();
    }
}
