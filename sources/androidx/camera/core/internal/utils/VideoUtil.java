package androidx.camera.core.internal.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Logger;
import androidx.core.util.Preconditions;

public final class VideoUtil {
    private static final String TAG = "VideoUtil";

    private VideoUtil() {
    }

    @Nullable
    public static String getAbsolutePathFromUri(@NonNull ContentResolver resolver, @NonNull Uri contentUri) {
        Cursor cursor = null;
        try {
            Cursor cursor2 = (Cursor) Preconditions.checkNotNull(resolver.query(contentUri, new String[]{"_data"}, (String) null, (String[]) null, (String) null));
            int columnIndex = cursor2.getColumnIndexOrThrow("_data");
            cursor2.moveToFirst();
            String string = cursor2.getString(columnIndex);
            cursor2.close();
            return string;
        } catch (RuntimeException e) {
            Logger.e(TAG, String.format("Failed in getting absolute path for Uri %s with Exception %s", new Object[]{contentUri.toString(), e.toString()}));
            if (cursor != null) {
                cursor.close();
            }
            return "";
        } catch (Throwable e2) {
            if (cursor != null) {
                cursor.close();
            }
            throw e2;
        }
    }
}
