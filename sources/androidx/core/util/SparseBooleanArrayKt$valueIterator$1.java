package androidx.core.util;

import android.util.SparseBooleanArray;
import kotlin.collections.n;

/* compiled from: SparseBooleanArray.kt */
public final class SparseBooleanArrayKt$valueIterator$1 extends n {
    final /* synthetic */ SparseBooleanArray $this_valueIterator;
    private int index;

    SparseBooleanArrayKt$valueIterator$1(SparseBooleanArray $receiver) {
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

    public boolean nextBoolean() {
        SparseBooleanArray sparseBooleanArray = this.$this_valueIterator;
        int i = this.index;
        this.index = i + 1;
        return sparseBooleanArray.valueAt(i);
    }
}
