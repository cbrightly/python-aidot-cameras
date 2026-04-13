package com.google.android.gms.common.util;

import android.os.ParcelFileDescriptor;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.annotation.Nullable;

@ShowFirstParty
@KeepForSdk
@Deprecated
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class IOUtils {
    private IOUtils() {
    }

    @KeepForSdk
    public static void closeQuietly(@Nullable ParcelFileDescriptor p) {
        if (p != null) {
            try {
                p.close();
            } catch (IOException e) {
            }
        }
    }

    @ResultIgnorabilityUnspecified
    @KeepForSdk
    @Deprecated
    public static long copyStream(@NonNull InputStream inputStream, @NonNull OutputStream outputStream) {
        return copyStream(inputStream, outputStream, false, 1024);
    }

    @KeepForSdk
    public static boolean isGzipByteBuffer(@NonNull byte[] inputBytes) {
        if (inputBytes.length > 1) {
            if ((((inputBytes[1] & 255) << 8) | (inputBytes[0] & 255)) == 35615) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    @KeepForSdk
    @Deprecated
    public static byte[] readInputStreamFully(@NonNull InputStream is) {
        return readInputStreamFully(is, true);
    }

    @NonNull
    @KeepForSdk
    @Deprecated
    public static byte[] toByteArray(@NonNull InputStream in) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Preconditions.checkNotNull(in);
        Preconditions.checkNotNull(byteArrayOutputStream);
        byte[] bArr = new byte[4096];
        while (true) {
            int read = in.read(bArr);
            if (read == -1) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }

    @KeepForSdk
    public static void closeQuietly(@Nullable Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }

    @ResultIgnorabilityUnspecified
    @KeepForSdk
    @Deprecated
    public static long copyStream(@NonNull InputStream inputStream, @NonNull OutputStream outputStream, boolean closeWhenDone, int bufferSizeBytes) {
        byte[] bArr = new byte[bufferSizeBytes];
        long j = 0;
        while (true) {
            try {
                int read = inputStream.read(bArr, 0, bufferSizeBytes);
                if (read == -1) {
                    break;
                }
                j += (long) read;
                outputStream.write(bArr, 0, read);
            } catch (Throwable th) {
                if (closeWhenDone) {
                    closeQuietly((Closeable) inputStream);
                    closeQuietly((Closeable) outputStream);
                }
                throw th;
            }
        }
        if (closeWhenDone) {
            closeQuietly((Closeable) inputStream);
            closeQuietly((Closeable) outputStream);
        }
        return j;
    }

    @NonNull
    @KeepForSdk
    @Deprecated
    public static byte[] readInputStreamFully(@NonNull InputStream is, boolean closeWhenDone) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copyStream(is, byteArrayOutputStream, closeWhenDone, 1024);
        return byteArrayOutputStream.toByteArray();
    }
}
