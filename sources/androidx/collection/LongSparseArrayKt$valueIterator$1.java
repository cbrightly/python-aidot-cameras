package androidx.collection;

import java.util.Iterator;
import kotlin.jvm.internal.markers.a;

/* compiled from: LongSparseArray.kt */
public final class LongSparseArrayKt$valueIterator$1 implements Iterator<T>, a {
    final /* synthetic */ LongSparseArray $this_valueIterator;
    private int index;

    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    LongSparseArrayKt$valueIterator$1(LongSparseArray<T> $receiver) {
        this.$this_valueIterator = $receiver;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int i) {
        this.index = i;
    }

    public boolean hasNext() {
        return this.index < this.$this_valueIterator.size();
    }

    public T next() {
        LongSparseArray longSparseArray = this.$this_valueIterator;
        int i = this.index;
        this.index = i + 1;
        return longSparseArray.valueAt(i);
    }
}
