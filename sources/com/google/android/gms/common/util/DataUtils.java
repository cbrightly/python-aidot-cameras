package com.google.android.gms.common.util;

import android.database.CharArrayBuffer;
import android.graphics.Bitmap;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.io.ByteArrayOutputStream;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class DataUtils {
    @KeepForSdk
    public static void copyStringToBuffer(@Nullable String toCopy, @NonNull CharArrayBuffer dataOut) {
        if (TextUtils.isEmpty(toCopy)) {
            dataOut.sizeCopied = 0;
            return;
        }
        char[] cArr = dataOut.data;
        if (cArr == null || cArr.length < toCopy.length()) {
            dataOut.data = toCopy.toCharArray();
        } else {
            toCopy.getChars(0, toCopy.length(), dataOut.data, 0);
        }
        dataOut.sizeCopied = toCopy.length();
    }

    @NonNull
    @KeepForSdk
    public static byte[] loadImageBytes(@NonNull Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
