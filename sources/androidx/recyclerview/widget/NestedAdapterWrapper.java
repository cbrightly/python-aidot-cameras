package androidx.recyclerview.widget;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Preconditions;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StableIdStorage;
import androidx.recyclerview.widget.ViewTypeStorage;

public class NestedAdapterWrapper {
    public final RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
    private RecyclerView.AdapterDataObserver mAdapterObserver = new RecyclerView.AdapterDataObserver() {
        public void onChanged() {
            NestedAdapterWrapper nestedAdapterWrapper = NestedAdapterWrapper.this;
            nestedAdapterWrapper.mCachedItemCount = nestedAdapterWrapper.adapter.getItemCount();
            NestedAdapterWrapper nestedAdapterWrapper2 = NestedAdapterWrapper.this;
            nestedAdapterWrapper2.mCallback.onChanged(nestedAdapterWrapper2);
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            NestedAdapterWrapper nestedAdapterWrapper = NestedAdapterWrapper.this;
            nestedAdapterWrapper.mCallback.onItemRangeChanged(nestedAdapterWrapper, positionStart, itemCount, (Object) null);
        }

        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            NestedAdapterWrapper nestedAdapterWrapper = NestedAdapterWrapper.this;
            nestedAdapterWrapper.mCallback.onItemRangeChanged(nestedAdapterWrapper, positionStart, itemCount, payload);
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            NestedAdapterWrapper nestedAdapterWrapper = NestedAdapterWrapper.this;
            nestedAdapterWrapper.mCachedItemCount += itemCount;
            nestedAdapterWrapper.mCallback.onItemRangeInserted(nestedAdapterWrapper, positionStart, itemCount);
            NestedAdapterWrapper nestedAdapterWrapper2 = NestedAdapterWrapper.this;
            if (nestedAdapterWrapper2.mCachedItemCount > 0 && nestedAdapterWrapper2.adapter.getStateRestorationPolicy() == RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY) {
                NestedAdapterWrapper nestedAdapterWrapper3 = NestedAdapterWrapper.this;
                nestedAdapterWrapper3.mCallback.onStateRestorationPolicyChanged(nestedAdapterWrapper3);
            }
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            NestedAdapterWrapper nestedAdapterWrapper = NestedAdapterWrapper.this;
            nestedAdapterWrapper.mCachedItemCount -= itemCount;
            nestedAdapterWrapper.mCallback.onItemRangeRemoved(nestedAdapterWrapper, positionStart, itemCount);
            NestedAdapterWrapper nestedAdapterWrapper2 = NestedAdapterWrapper.this;
            if (nestedAdapterWrapper2.mCachedItemCount < 1 && nestedAdapterWrapper2.adapter.getStateRestorationPolicy() == RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY) {
                NestedAdapterWrapper nestedAdapterWrapper3 = NestedAdapterWrapper.this;
                nestedAdapterWrapper3.mCallback.onStateRestorationPolicyChanged(nestedAdapterWrapper3);
            }
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            boolean z = true;
            if (itemCount != 1) {
                z = false;
            }
            Preconditions.checkArgument(z, "moving more than 1 item is not supported in RecyclerView");
            NestedAdapterWrapper nestedAdapterWrapper = NestedAdapterWrapper.this;
            nestedAdapterWrapper.mCallback.onItemRangeMoved(nestedAdapterWrapper, fromPosition, toPosition);
        }

        public void onStateRestorationPolicyChanged() {
            NestedAdapterWrapper nestedAdapterWrapper = NestedAdapterWrapper.this;
            nestedAdapterWrapper.mCallback.onStateRestorationPolicyChanged(nestedAdapterWrapper);
        }
    };
    int mCachedItemCount;
    final Callback mCallback;
    @NonNull
    private final StableIdStorage.StableIdLookup mStableIdLookup;
    @NonNull
    private final ViewTypeStorage.ViewTypeLookup mViewTypeLookup;

    public interface Callback {
        void onChanged(@NonNull NestedAdapterWrapper nestedAdapterWrapper);

        void onItemRangeChanged(@NonNull NestedAdapterWrapper nestedAdapterWrapper, int i, int i2);

        void onItemRangeChanged(@NonNull NestedAdapterWrapper nestedAdapterWrapper, int i, int i2, @Nullable Object obj);

        void onItemRangeInserted(@NonNull NestedAdapterWrapper nestedAdapterWrapper, int i, int i2);

        void onItemRangeMoved(@NonNull NestedAdapterWrapper nestedAdapterWrapper, int i, int i2);

        void onItemRangeRemoved(@NonNull NestedAdapterWrapper nestedAdapterWrapper, int i, int i2);

        void onStateRestorationPolicyChanged(NestedAdapterWrapper nestedAdapterWrapper);
    }

    NestedAdapterWrapper(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter2, Callback callback, ViewTypeStorage viewTypeStorage, StableIdStorage.StableIdLookup stableIdLookup) {
        this.adapter = adapter2;
        this.mCallback = callback;
        this.mViewTypeLookup = viewTypeStorage.createViewTypeWrapper(this);
        this.mStableIdLookup = stableIdLookup;
        this.mCachedItemCount = adapter2.getItemCount();
        adapter2.registerAdapterDataObserver(this.mAdapterObserver);
    }

    /* access modifiers changed from: package-private */
    public void dispose() {
        this.adapter.unregisterAdapterDataObserver(this.mAdapterObserver);
        this.mViewTypeLookup.dispose();
    }

    /* access modifiers changed from: package-private */
    public int getCachedItemCount() {
        return this.mCachedItemCount;
    }

    /* access modifiers changed from: package-private */
    public int getItemViewType(int localPosition) {
        return this.mViewTypeLookup.localToGlobal(this.adapter.getItemViewType(localPosition));
    }

    /* access modifiers changed from: package-private */
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int globalViewType) {
        return this.adapter.onCreateViewHolder(parent, this.mViewTypeLookup.globalToLocal(globalViewType));
    }

    /* access modifiers changed from: package-private */
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int localPosition) {
        this.adapter.bindViewHolder(viewHolder, localPosition);
    }

    public long getItemId(int localPosition) {
        return this.mStableIdLookup.localToGlobal(this.adapter.getItemId(localPosition));
    }
}
