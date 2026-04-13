package androidx.core.util;

import android.util.SparseLongArray;
import kotlin.collections.h0;

/* compiled from: SparseLongArray.kt */
public final class SparseLongArrayKt$valueIterator$1 extends h0 {
    final /* synthetic */ SparseLongArray $this_valueIterator;
    private int index;

    SparseLongArrayKt$valueIterator$1(SparseLongArray $receiver) {
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

    public long nextLong() {
        SparseLongArray sparseLongArray = this.$this_valueIterator;
        int i = this.index;
        this.index = i + 1;
        return sparseLongArray.valueAt(i);
    }
}
