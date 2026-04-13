package androidx.collection;

import kotlin.collections.g0;

/* compiled from: SparseArray.kt */
public final class SparseArrayKt$keyIterator$1 extends g0 {
    final /* synthetic */ SparseArrayCompat $this_keyIterator;
    private int index;

    SparseArrayKt$keyIterator$1(SparseArrayCompat<T> $receiver) {
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

    public int nextInt() {
        SparseArrayCompat sparseArrayCompat = this.$this_keyIterator;
        int i = this.index;
        this.index = i + 1;
        return sparseArrayCompat.keyAt(i);
    }
}
