package com.didichuxing.doraemonkit.widget.bravh.diff;

import androidx.recyclerview.widget.DiffUtil;
import org.jetbrains.annotations.Nullable;

/* compiled from: BrvahAsyncDiffer.kt */
public final class BrvahAsyncDiffer$submitList$1$result$1 extends DiffUtil.Callback {
    final /* synthetic */ BrvahAsyncDiffer$submitList$1 this$0;

    BrvahAsyncDiffer$submitList$1$result$1(BrvahAsyncDiffer$submitList$1 $outer) {
        this.this$0 = $outer;
    }

    public int getOldListSize() {
        return this.this$0.$oldList.size();
    }

    public int getNewListSize() {
        return this.this$0.$newList.size();
    }

    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Object oldItem = this.this$0.$oldList.get(oldItemPosition);
        Object newItem = this.this$0.$newList.get(newItemPosition);
        if (oldItem == null || newItem == null) {
            return oldItem == null && newItem == null;
        }
        return this.this$0.this$0.config.getDiffCallback().areItemsTheSame(oldItem, newItem);
    }

    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Object oldItem = this.this$0.$oldList.get(oldItemPosition);
        Object newItem = this.this$0.$newList.get(newItemPosition);
        if (oldItem != null && newItem != null) {
            return this.this$0.this$0.config.getDiffCallback().areContentsTheSame(oldItem, newItem);
        }
        if (oldItem == null && newItem == null) {
            return true;
        }
        throw new AssertionError();
    }

    @Nullable
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Object oldItem = this.this$0.$oldList.get(oldItemPosition);
        Object newItem = this.this$0.$newList.get(newItemPosition);
        if (oldItem != null && newItem != null) {
            return this.this$0.this$0.config.getDiffCallback().getChangePayload(oldItem, newItem);
        }
        throw new AssertionError();
    }
}
