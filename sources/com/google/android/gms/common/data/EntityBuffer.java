package com.google.android.gms.common.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.util.ArrayList;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public abstract class EntityBuffer<T> extends AbstractDataBuffer<T> {
    private boolean zaa = false;
    private ArrayList zab;

    @KeepForSdk
    protected EntityBuffer(@NonNull DataHolder dataHolder) {
        super(dataHolder);
    }

    private final void zab() {
        synchronized (this) {
            if (!this.zaa) {
                int count = ((DataHolder) Preconditions.checkNotNull(this.mDataHolder)).getCount();
                ArrayList arrayList = new ArrayList();
                this.zab = arrayList;
                if (count > 0) {
                    arrayList.add(0);
                    String primaryDataMarkerColumn = getPrimaryDataMarkerColumn();
                    String string = this.mDataHolder.getString(primaryDataMarkerColumn, 0, this.mDataHolder.getWindowIndex(0));
                    int i = 1;
                    while (i < count) {
                        int windowIndex = this.mDataHolder.getWindowIndex(i);
                        String string2 = this.mDataHolder.getString(primaryDataMarkerColumn, i, windowIndex);
                        if (string2 != null) {
                            if (!string2.equals(string)) {
                                this.zab.add(Integer.valueOf(i));
                                string = string2;
                            }
                            i++;
                        } else {
                            throw new NullPointerException("Missing value for markerColumn: " + primaryDataMarkerColumn + ", at row: " + i + ", for window: " + windowIndex);
                        }
                    }
                }
                this.zaa = true;
            }
        }
    }

    @ResultIgnorabilityUnspecified
    @NonNull
    @KeepForSdk
    public final T get(int position) {
        int i;
        zab();
        int zaa2 = zaa(position);
        int i2 = 0;
        if (position >= 0 && position != this.zab.size()) {
            if (position == this.zab.size() - 1) {
                i = ((DataHolder) Preconditions.checkNotNull(this.mDataHolder)).getCount() - ((Integer) this.zab.get(position)).intValue();
            } else {
                i = ((Integer) this.zab.get(position + 1)).intValue() - ((Integer) this.zab.get(position)).intValue();
            }
            if (i == 1) {
                int zaa3 = zaa(position);
                int windowIndex = ((DataHolder) Preconditions.checkNotNull(this.mDataHolder)).getWindowIndex(zaa3);
                String childDataMarkerColumn = getChildDataMarkerColumn();
                if (childDataMarkerColumn == null || this.mDataHolder.getString(childDataMarkerColumn, zaa3, windowIndex) != null) {
                    i2 = 1;
                }
            } else {
                i2 = i;
            }
        }
        return getEntry(zaa2, i2);
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    @Nullable
    public String getChildDataMarkerColumn() {
        return null;
    }

    @KeepForSdk
    public int getCount() {
        zab();
        return this.zab.size();
    }

    /* access modifiers changed from: protected */
    @NonNull
    @KeepForSdk
    public abstract T getEntry(int i, int i2);

    /* access modifiers changed from: protected */
    @NonNull
    @KeepForSdk
    public abstract String getPrimaryDataMarkerColumn();

    /* access modifiers changed from: package-private */
    public final int zaa(int i) {
        if (i >= 0 && i < this.zab.size()) {
            return ((Integer) this.zab.get(i)).intValue();
        }
        throw new IllegalArgumentException("Position " + i + " is out of bounds for this buffer");
    }
}
