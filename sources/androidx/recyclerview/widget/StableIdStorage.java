package androidx.recyclerview.widget;

import androidx.annotation.NonNull;
import androidx.collection.LongSparseArray;

public interface StableIdStorage {

    public interface StableIdLookup {
        long localToGlobal(long j);
    }

    @NonNull
    StableIdLookup createStableIdLookup();

    public static class NoStableIdStorage implements StableIdStorage {
        private final StableIdLookup mNoIdLookup = new StableIdLookup() {
            public long localToGlobal(long localId) {
                return -1;
            }
        };

        @NonNull
        public StableIdLookup createStableIdLookup() {
            return this.mNoIdLookup;
        }
    }

    public static class SharedPoolStableIdStorage implements StableIdStorage {
        private final StableIdLookup mSameIdLookup = new StableIdLookup() {
            public long localToGlobal(long localId) {
                return localId;
            }
        };

        @NonNull
        public StableIdLookup createStableIdLookup() {
            return this.mSameIdLookup;
        }
    }

    public static class IsolatedStableIdStorage implements StableIdStorage {
        long mNextStableId = 0;

        /* access modifiers changed from: package-private */
        public long obtainId() {
            long j = this.mNextStableId;
            this.mNextStableId = 1 + j;
            return j;
        }

        @NonNull
        public StableIdLookup createStableIdLookup() {
            return new WrapperStableIdLookup();
        }

        public class WrapperStableIdLookup implements StableIdLookup {
            private final LongSparseArray<Long> mLocalToGlobalLookup = new LongSparseArray<>();

            WrapperStableIdLookup() {
            }

            public long localToGlobal(long localId) {
                Long globalId = this.mLocalToGlobalLookup.get(localId);
                if (globalId == null) {
                    globalId = Long.valueOf(IsolatedStableIdStorage.this.obtainId());
                    this.mLocalToGlobalLookup.put(localId, globalId);
                }
                return globalId.longValue();
            }
        }
    }
}
