package androidx.collection;

import kotlin.collections.h0;

/* compiled from: LongSparseArray.kt */
public final class LongSparseArrayKt$keyIterator$1 extends h0 {
    final /* synthetic */ LongSparseArray $this_keyIterator;
    private int index;

    LongSparseArrayKt$keyIterator$1(LongSparseArray<T> $receiver) {
        this.$this_keyIterator = $receiver;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int i) {
        this.index = i;
    }

    public boolean hasNext() {
        return this.index < this.$this_keyIterator.size();
    }

    public long nextLong() {
        LongSparseArray longSparseArray = this.$this_keyIterator;
        int i = this.index;
        this.index = i + 1;
        return longSparseArray.keyAt(i);
    }
}
