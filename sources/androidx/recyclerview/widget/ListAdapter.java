package androidx.recyclerview.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.util.List;

public abstract class ListAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    final AsyncListDiffer<T> mDiffer;
    private final AsyncListDiffer.ListListener<T> mListener;

    protected ListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        AnonymousClass1 r0 = new AsyncListDiffer.ListListener<T>() {
            public void onCurrentListChanged(@NonNull List<T> previousList, @NonNull List<T> currentList) {
                ListAdapter.this.onCurrentListChanged(previousList, currentList);
            }
        };
        this.mListener = r0;
        AsyncListDiffer<T> asyncListDiffer = new AsyncListDiffer<>((ListUpdateCallback) new AdapterListUpdateCallback(this), new AsyncDifferConfig.Builder(diffCallback).build());
        this.mDiffer = asyncListDiffer;
        asyncListDiffer.addListListener(r0);
    }

    protected ListAdapter(@NonNull AsyncDifferConfig<T> config) {
        AnonymousClass1 r0 = new AsyncListDiffer.ListListener<T>() {
            public void onCurrentListChanged(@NonNull List<T> previousList, @NonNull List<T> currentList) {
                ListAdapter.this.onCurrentListChanged(previousList, currentList);
            }
        };
        this.mListener = r0;
        AsyncListDiffer<T> asyncListDiffer = new AsyncListDiffer<>((ListUpdateCallback) new AdapterListUpdateCallback(this), config);
        this.mDiffer = asyncListDiffer;
        asyncListDiffer.addListListener(r0);
    }

    public void submitList(@Nullable List<T> list) {
        this.mDiffer.submitList(list);
    }

    public void submitList(@Nullable List<T> list, @Nullable Runnable commitCallback) {
        this.mDiffer.submitList(list, commitCallback);
    }

    /* access modifiers changed from: protected */
    public T getItem(int position) {
        return this.mDiffer.getCurrentList().get(position);
    }

    public int getItemCount() {
        return this.mDiffer.getCurrentList().size();
    }

    @NonNull
    public List<T> getCurrentList() {
        return this.mDiffer.getCurrentList();
    }

    public void onCurrentListChanged(@NonNull List<T> list, @NonNull List<T> list2) {
    }
}
