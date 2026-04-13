package com.google.android.gms.common.sqlite;

import android.database.AbstractWindowedCursor;
import android.database.CrossProcessCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class CursorWrapper extends android.database.CursorWrapper implements CrossProcessCursor {
    private AbstractWindowedCursor zza;

    @KeepForSdk
    public CursorWrapper(@NonNull Cursor cursor) {
        super(cursor);
        for (int i = 0; i < 10 && (cursor instanceof android.database.CursorWrapper); i++) {
            cursor = ((android.database.CursorWrapper) cursor).getWrappedCursor();
        }
        if (cursor instanceof AbstractWindowedCursor) {
            this.zza = (AbstractWindowedCursor) cursor;
            return;
        }
        throw new IllegalArgumentException("Unknown type: ".concat(String.valueOf(cursor.getClass().getName())));
    }

    @KeepForSdk
    public void fillWindow(int index, @NonNull CursorWindow window) {
        this.zza.fillWindow(index, window);
    }

    @ResultIgnorabilityUnspecified
    @KeepForSdk
    @Nullable
    public CursorWindow getWindow() {
        return this.zza.getWindow();
    }

    @NonNull
    public final /* synthetic */ Cursor getWrappedCursor() {
        return this.zza;
    }

    @ResultIgnorabilityUnspecified
    public final boolean onMove(int i, int i2) {
        return this.zza.onMove(i, i2);
    }

    @KeepForSdk
    public void setWindow(@Nullable CursorWindow window) {
        this.zza.setWindow(window);
    }
}
