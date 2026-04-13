package androidx.core.util;

import android.util.SparseLongArray;
import kotlin.collections.g0;

/* compiled from: SparseLongArray.kt */
public final class SparseLongArrayKt$keyIterator$1 extends g0 {
    final /* synthetic */ SparseLongArray $this_keyIterator;
    private int index;

    SparseLongArrayKt$keyIterator$1(SparseLongArray $receiver) {
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
        SparseLongArray sparseLongArray = this.$this_keyIterator;
        int i = this.index;
        this.index = i + 1;
        return sparseLongArray.keyAt(i);
    }
}
