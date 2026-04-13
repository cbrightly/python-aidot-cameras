package androidx.recyclerview.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.collection.LongSparseArray;
import androidx.collection.SimpleArrayMap;
import androidx.core.util.Pools;
import androidx.recyclerview.widget.RecyclerView;

public class ViewInfoStore {
    private static final boolean DEBUG = false;
    @VisibleForTesting
    final SimpleArrayMap<RecyclerView.ViewHolder, InfoRecord> mLayoutHolderMap = new SimpleArrayMap<>();
    @VisibleForTesting
    final LongSparseArray<RecyclerView.ViewHolder> mOldChangedHolders = new LongSparseArray<>();

    public interface ProcessCallback {
        void processAppeared(RecyclerView.ViewHolder viewHolder, @Nullable RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

        void processDisappeared(RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, @Nullable RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

        void processPersistent(RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

        void unused(RecyclerView.ViewHolder viewHolder);
    }

    ViewInfoStore() {
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.mLayoutHolderMap.clear();
        this.mOldChangedHolders.clear();
    }

    /* access modifiers changed from: package-private */
    public void addToPreLayout(RecyclerView.ViewHolder holder, RecyclerView.ItemAnimator.ItemHolderInfo info) {
        InfoRecord record = this.mLayoutHolderMap.get(holder);
        if (record == null) {
            record = InfoRecord.obtain();
            this.mLayoutHolderMap.put(holder, record);
        }
        record.preInfo = info;
        record.flags |= 4;
    }

    /* access modifiers changed from: package-private */
    public boolean isDisappearing(RecyclerView.ViewHolder holder) {
        InfoRecord record = this.mLayoutHolderMap.get(holder);
        return (record == null || (record.flags & 1) == 0) ? false : true;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public RecyclerView.ItemAnimator.ItemHolderInfo popFromPreLayout(RecyclerView.ViewHolder vh) {
        return popFromLayoutStep(vh, 4);
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public RecyclerView.ItemAnimator.ItemHolderInfo popFromPostLayout(RecyclerView.ViewHolder vh) {
        return popFromLayoutStep(vh, 8);
    }

    private RecyclerView.ItemAnimator.ItemHolderInfo popFromLayoutStep(RecyclerView.ViewHolder vh, int flag) {
        InfoRecord record;
        RecyclerView.ItemAnimator.ItemHolderInfo info;
        int index = this.mLayoutHolderMap.indexOfKey(vh);
        if (index >= 0 && (record = this.mLayoutHolderMap.valueAt(index)) != null) {
            int i = record.flags;
            if ((i & flag) != 0) {
                int i2 = (~flag) & i;
                record.flags = i2;
                if (flag == 4) {
                    info = record.preInfo;
                } else if (flag == 8) {
                    info = record.postInfo;
                } else {
                    throw new IllegalArgumentException("Must provide flag PRE or POST");
                }
                if ((i2 & 12) == 0) {
                    this.mLayoutHolderMap.removeAt(index);
                    InfoRecord.recycle(record);
                }
                return info;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void addToOldChangeHolders(long key, RecyclerView.ViewHolder holder) {
        this.mOldChangedHolders.put(key, holder);
    }

    /* access modifiers changed from: package-private */
    public void addToAppearedInPreLayoutHolders(RecyclerView.ViewHolder holder, RecyclerView.ItemAnimator.ItemHolderInfo info) {
        InfoRecord record = this.mLayoutHolderMap.get(holder);
        if (record == null) {
            record = InfoRecord.obtain();
            this.mLayoutHolderMap.put(holder, record);
        }
        record.flags |= 2;
        record.preInfo = info;
    }

    /* access modifiers changed from: package-private */
    public boolean isInPreLayout(RecyclerView.ViewHolder viewHolder) {
        InfoRecord record = this.mLayoutHolderMap.get(viewHolder);
        return (record == null || (record.flags & 4) == 0) ? false : true;
    }

    /* access modifiers changed from: package-private */
    public RecyclerView.ViewHolder getFromOldChangeHolders(long key) {
        return this.mOldChangedHolders.get(key);
    }

    /* access modifiers changed from: package-private */
    public void addToPostLayout(RecyclerView.ViewHolder holder, RecyclerView.ItemAnimator.ItemHolderInfo info) {
        InfoRecord record = this.mLayoutHolderMap.get(holder);
        if (record == null) {
            record = InfoRecord.obtain();
            this.mLayoutHolderMap.put(holder, record);
        }
        record.postInfo = info;
        record.flags |= 8;
    }

    /* access modifiers changed from: package-private */
    public void addToDisappearedInLayout(RecyclerView.ViewHolder holder) {
        InfoRecord record = this.mLayoutHolderMap.get(holder);
        if (record == null) {
            record = InfoRecord.obtain();
            this.mLayoutHolderMap.put(holder, record);
        }
        record.flags |= 1;
    }

    /* access modifiers changed from: package-private */
    public void removeFromDisappearedInLayout(RecyclerView.ViewHolder holder) {
        InfoRecord record = this.mLayoutHolderMap.get(holder);
        if (record != null) {
            record.flags &= -2;
        }
    }

    /* access modifiers changed from: package-private */
    public void process(ProcessCallback callback) {
        for (int index = this.mLayoutHolderMap.size() - 1; index >= 0; index--) {
            RecyclerView.ViewHolder viewHolder = this.mLayoutHolderMap.keyAt(index);
            InfoRecord record = this.mLayoutHolderMap.removeAt(index);
            int i = record.flags;
            if ((i & 3) == 3) {
                callback.unused(viewHolder);
            } else if ((i & 1) != 0) {
                RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo = record.preInfo;
                if (itemHolderInfo == null) {
                    callback.unused(viewHolder);
                } else {
                    callback.processDisappeared(viewHolder, itemHolderInfo, record.postInfo);
                }
            } else if ((i & 14) == 14) {
                callback.processAppeared(viewHolder, record.preInfo, record.postInfo);
            } else if ((i & 12) == 12) {
                callback.processPersistent(viewHolder, record.preInfo, record.postInfo);
            } else if ((i & 4) != 0) {
                callback.processDisappeared(viewHolder, record.preInfo, (RecyclerView.ItemAnimator.ItemHolderInfo) null);
            } else if ((i & 8) != 0) {
                callback.processAppeared(viewHolder, record.preInfo, record.postInfo);
            }
            InfoRecord.recycle(record);
        }
    }

    /* access modifiers changed from: package-private */
    public void removeViewHolder(RecyclerView.ViewHolder holder) {
        int i = this.mOldChangedHolders.size() - 1;
        while (true) {
            if (i < 0) {
                break;
            } else if (holder == this.mOldChangedHolders.valueAt(i)) {
                this.mOldChangedHolders.removeAt(i);
                break;
            } else {
                i--;
            }
        }
        InfoRecord info = this.mLayoutHolderMap.remove(holder);
        if (info != null) {
            InfoRecord.recycle(info);
        }
    }

    /* access modifiers changed from: package-private */
    public void onDetach() {
        InfoRecord.drainCache();
    }

    public void onViewDetached(RecyclerView.ViewHolder viewHolder) {
        removeFromDisappearedInLayout(viewHolder);
    }

    public static class InfoRecord {
        static final int FLAG_APPEAR = 2;
        static final int FLAG_APPEAR_AND_DISAPPEAR = 3;
        static final int FLAG_APPEAR_PRE_AND_POST = 14;
        static final int FLAG_DISAPPEARED = 1;
        static final int FLAG_POST = 8;
        static final int FLAG_PRE = 4;
        static final int FLAG_PRE_AND_POST = 12;
        static Pools.Pool<InfoRecord> sPool = new Pools.SimplePool(20);
        int flags;
        @Nullable
        RecyclerView.ItemAnimator.ItemHolderInfo postInfo;
        @Nullable
        RecyclerView.ItemAnimator.ItemHolderInfo preInfo;

        private InfoRecord() {
        }

        static InfoRecord obtain() {
            InfoRecord record = sPool.acquire();
            return record == null ? new InfoRecord() : record;
        }

        static void recycle(InfoRecord record) {
            record.flags = 0;
            record.preInfo = null;
            record.postInfo = null;
            sPool.release(record);
        }

        static void drainCache() {
            do {
            } while (sPool.acquire() != null);
        }
    }
}
