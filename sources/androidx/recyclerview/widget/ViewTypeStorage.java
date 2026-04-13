package androidx.recyclerview.widget;

import android.util.SparseArray;
import android.util.SparseIntArray;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

public interface ViewTypeStorage {

    public interface ViewTypeLookup {
        void dispose();

        int globalToLocal(int i);

        int localToGlobal(int i);
    }

    @NonNull
    ViewTypeLookup createViewTypeWrapper(@NonNull NestedAdapterWrapper nestedAdapterWrapper);

    @NonNull
    NestedAdapterWrapper getWrapperForGlobalType(int i);

    public static class SharedIdRangeViewTypeStorage implements ViewTypeStorage {
        SparseArray<List<NestedAdapterWrapper>> mGlobalTypeToWrapper = new SparseArray<>();

        @NonNull
        public NestedAdapterWrapper getWrapperForGlobalType(int globalViewType) {
            List<NestedAdapterWrapper> nestedAdapterWrappers = this.mGlobalTypeToWrapper.get(globalViewType);
            if (nestedAdapterWrappers != null && !nestedAdapterWrappers.isEmpty()) {
                return nestedAdapterWrappers.get(0);
            }
            throw new IllegalArgumentException("Cannot find the wrapper for global view type " + globalViewType);
        }

        @NonNull
        public ViewTypeLookup createViewTypeWrapper(@NonNull NestedAdapterWrapper wrapper) {
            return new WrapperViewTypeLookup(wrapper);
        }

        /* access modifiers changed from: package-private */
        public void removeWrapper(@NonNull NestedAdapterWrapper wrapper) {
            for (int i = this.mGlobalTypeToWrapper.size() - 1; i >= 0; i--) {
                List<NestedAdapterWrapper> wrappers = this.mGlobalTypeToWrapper.valueAt(i);
                if (wrappers.remove(wrapper) && wrappers.isEmpty()) {
                    this.mGlobalTypeToWrapper.removeAt(i);
                }
            }
        }

        public class WrapperViewTypeLookup implements ViewTypeLookup {
            final NestedAdapterWrapper mWrapper;

            WrapperViewTypeLookup(NestedAdapterWrapper wrapper) {
                this.mWrapper = wrapper;
            }

            public int localToGlobal(int localType) {
                List<NestedAdapterWrapper> wrappers = SharedIdRangeViewTypeStorage.this.mGlobalTypeToWrapper.get(localType);
                if (wrappers == null) {
                    wrappers = new ArrayList<>();
                    SharedIdRangeViewTypeStorage.this.mGlobalTypeToWrapper.put(localType, wrappers);
                }
                if (!wrappers.contains(this.mWrapper)) {
                    wrappers.add(this.mWrapper);
                }
                return localType;
            }

            public int globalToLocal(int globalType) {
                return globalType;
            }

            public void dispose() {
                SharedIdRangeViewTypeStorage.this.removeWrapper(this.mWrapper);
            }
        }
    }

    public static class IsolatedViewTypeStorage implements ViewTypeStorage {
        SparseArray<NestedAdapterWrapper> mGlobalTypeToWrapper = new SparseArray<>();
        int mNextViewType = 0;

        /* access modifiers changed from: package-private */
        public int obtainViewType(NestedAdapterWrapper wrapper) {
            int nextId = this.mNextViewType;
            this.mNextViewType = nextId + 1;
            this.mGlobalTypeToWrapper.put(nextId, wrapper);
            return nextId;
        }

        @NonNull
        public NestedAdapterWrapper getWrapperForGlobalType(int globalViewType) {
            NestedAdapterWrapper wrapper = this.mGlobalTypeToWrapper.get(globalViewType);
            if (wrapper != null) {
                return wrapper;
            }
            throw new IllegalArgumentException("Cannot find the wrapper for global view type " + globalViewType);
        }

        @NonNull
        public ViewTypeLookup createViewTypeWrapper(@NonNull NestedAdapterWrapper wrapper) {
            return new WrapperViewTypeLookup(wrapper);
        }

        /* access modifiers changed from: package-private */
        public void removeWrapper(@NonNull NestedAdapterWrapper wrapper) {
            for (int i = this.mGlobalTypeToWrapper.size() - 1; i >= 0; i--) {
                if (this.mGlobalTypeToWrapper.valueAt(i) == wrapper) {
                    this.mGlobalTypeToWrapper.removeAt(i);
                }
            }
        }

        public class WrapperViewTypeLookup implements ViewTypeLookup {
            private SparseIntArray mGlobalToLocalMapping = new SparseIntArray(1);
            private SparseIntArray mLocalToGlobalMapping = new SparseIntArray(1);
            final NestedAdapterWrapper mWrapper;

            WrapperViewTypeLookup(NestedAdapterWrapper wrapper) {
                this.mWrapper = wrapper;
            }

            public int localToGlobal(int localType) {
                int index = this.mLocalToGlobalMapping.indexOfKey(localType);
                if (index > -1) {
                    return this.mLocalToGlobalMapping.valueAt(index);
                }
                int globalType = IsolatedViewTypeStorage.this.obtainViewType(this.mWrapper);
                this.mLocalToGlobalMapping.put(localType, globalType);
                this.mGlobalToLocalMapping.put(globalType, localType);
                return globalType;
            }

            public int globalToLocal(int globalType) {
                int index = this.mGlobalToLocalMapping.indexOfKey(globalType);
                if (index >= 0) {
                    return this.mGlobalToLocalMapping.valueAt(index);
                }
                throw new IllegalStateException("requested global type " + globalType + " does not belong to the adapter:" + this.mWrapper.adapter);
            }

            public void dispose() {
                IsolatedViewTypeStorage.this.removeWrapper(this.mWrapper);
            }
        }
    }
}
