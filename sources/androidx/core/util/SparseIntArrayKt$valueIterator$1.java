package androidx.core.util;

import android.util.SparseIntArray;
import kotlin.collections.g0;

/* compiled from: SparseIntArray.kt */
public final class SparseIntArrayKt$valueIterator$1 extends g0 {
    final /* synthetic */ SparseIntArray $this_valueIterator;
    private int index;

    SparseIntArrayKt$valueIterator$1(SparseIntArray $receiver) {
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

    public int nextInt() {
        SparseIntArray sparseIntArray = this.$this_valueIterator;
        int i = this.index;
        this.index = i + 1;
        return sparseIntArray.valueAt(i);
    }
}
