package androidx.room.util;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class FileUtil {
    @SuppressLint({"LambdaLast"})
    public static void copy(@NonNull ReadableByteChannel input, @NonNull FileChannel output) {
        try {
            if (Build.VERSION.SDK_INT <= 23) {
                InputStream inputStream = Channels.newInputStream(input);
                OutputStream outputStream = Channels.newOutputStream(output);
                byte[] buffer = new byte[4096];
                while (true) {
                    int read = inputStream.read(buffer);
                    int length = read;
                    if (read <= 0) {
                        break;
                    }
                    outputStream.write(buffer, 0, length);
                }
            } else {
                output.transferFrom(input, 0, DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
            }
            output.force(false);
        } finally {
            input.close();
            output.close();
        }
    }

    private FileUtil() {
    }
}
