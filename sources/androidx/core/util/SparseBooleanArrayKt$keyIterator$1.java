package androidx.core.util;

import android.util.SparseBooleanArray;
import kotlin.collections.g0;

/* compiled from: SparseBooleanArray.kt */
public final class SparseBooleanArrayKt$keyIterator$1 extends g0 {
    final /* synthetic */ SparseBooleanArray $this_keyIterator;
    private int index;

    SparseBooleanArrayKt$keyIterator$1(SparseBooleanArray $receiver) {
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
        SparseBooleanArray sparseBooleanArray = this.$this_keyIterator;
        int i = this.index;
        this.index = i + 1;
        return sparseBooleanArray.keyAt(i);
    }
}
