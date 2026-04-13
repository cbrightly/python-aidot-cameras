package androidx.core.graphics;

import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import java.util.Iterator;
import kotlin.jvm.internal.markers.a;
import org.jetbrains.annotations.NotNull;

/* compiled from: Region.kt */
public final class RegionKt$iterator$1 implements Iterator<Rect>, a {
    final /* synthetic */ Region $this_iterator;
    private boolean hasMore;
    @NotNull
    private final RegionIterator iterator;
    @NotNull
    private final Rect rect;

    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    RegionKt$iterator$1(Region $receiver) {
        this.$this_iterator = $receiver;
        RegionIterator regionIterator = new RegionIterator($receiver);
        this.iterator = regionIterator;
        Rect rect2 = new Rect();
        this.rect = rect2;
        this.hasMore = regionIterator.next(rect2);
    }

    public boolean hasNext() {
        return this.hasMore;
    }

    @NotNull
    public Rect next() {
        if (this.hasMore) {
            Rect r = new Rect(this.rect);
            this.hasMore = this.iterator.next(this.rect);
            return r;
        }
        throw new IndexOutOfBoundsException();
    }
}
