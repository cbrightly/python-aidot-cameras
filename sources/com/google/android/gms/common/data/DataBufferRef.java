package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public abstract class DataBufferRef {
    @NonNull
    @KeepForSdk
    protected final DataHolder mDataHolder;
    @KeepForSdk
    protected int mDataRow;
    private int zaa;

    @KeepForSdk
    public DataBufferRef(@NonNull DataHolder holder, int dataRow) {
        this.mDataHolder = (DataHolder) Preconditions.checkNotNull(holder);
        zaa(dataRow);
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    public void copyToBuffer(@NonNull String column, @NonNull CharArrayBuffer dataOut) {
        this.mDataHolder.zac(column, this.mDataRow, this.zaa, dataOut);
    }

    @KeepForSdk
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof DataBufferRef) {
            DataBufferRef dataBufferRef = (DataBufferRef) obj;
            if (!Objects.equal(Integer.valueOf(dataBufferRef.mDataRow), Integer.valueOf(this.mDataRow)) || !Objects.equal(Integer.valueOf(dataBufferRef.zaa), Integer.valueOf(this.zaa)) || dataBufferRef.mDataHolder != this.mDataHolder) {
                return false;
            }
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    public boolean getBoolean(@NonNull String column) {
        return this.mDataHolder.getBoolean(column, this.mDataRow, this.zaa);
    }

    /* access modifiers changed from: protected */
    @NonNull
    @KeepForSdk
    public byte[] getByteArray(@NonNull String column) {
        return this.mDataHolder.getByteArray(column, this.mDataRow, this.zaa);
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    public int getDataRow() {
        return this.mDataRow;
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    public double getDouble(@NonNull String column) {
        return this.mDataHolder.zaa(column, this.mDataRow, this.zaa);
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    public float getFloat(@NonNull String column) {
        return this.mDataHolder.zab(column, this.mDataRow, this.zaa);
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    public int getInteger(@NonNull String column) {
        return this.mDataHolder.getInteger(column, this.mDataRow, this.zaa);
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    public long getLong(@NonNull String column) {
        return this.mDataHolder.getLong(column, this.mDataRow, this.zaa);
    }

    /* access modifiers changed from: protected */
    @NonNull
    @KeepForSdk
    public String getString(@NonNull String column) {
        return this.mDataHolder.getString(column, this.mDataRow, this.zaa);
    }

    @KeepForSdk
    public boolean hasColumn(@NonNull String column) {
        return this.mDataHolder.hasColumn(column);
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    public boolean hasNull(@NonNull String column) {
        return this.mDataHolder.hasNull(column, this.mDataRow, this.zaa);
    }

    @KeepForSdk
    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.mDataRow), Integer.valueOf(this.zaa), this.mDataHolder);
    }

    @KeepForSdk
    public boolean isDataValid() {
        return !this.mDataHolder.isClosed();
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    @Nullable
    public Uri parseUri(@NonNull String column) {
        String string = this.mDataHolder.getString(column, this.mDataRow, this.zaa);
        if (string == null) {
            return null;
        }
        return Uri.parse(string);
    }

    /* access modifiers changed from: protected */
    public final void zaa(int i) {
        boolean z = false;
        if (i >= 0 && i < this.mDataHolder.getCount()) {
            z = true;
        }
        Preconditions.checkState(z);
        this.mDataRow = i;
        this.zaa = this.mDataHolder.getWindowIndex(i);
    }
}
