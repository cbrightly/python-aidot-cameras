package androidx.camera.core.internal.compat;

import android.media.ImageWriter;
import android.os.Build;
import android.util.Log;
import android.view.Surface;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(26)
public final class ImageWriterCompatApi26Impl {
    private static final String TAG = "ImageWriterCompatApi26";
    private static Method sNewInstanceMethod;

    static {
        Class<ImageWriter> cls = ImageWriter.class;
        try {
            Class cls2 = Integer.TYPE;
            sNewInstanceMethod = cls.getMethod("newInstance", new Class[]{Surface.class, cls2, cls2});
        } catch (NoSuchMethodException e) {
            Log.i(TAG, "Unable to initialize via reflection.", e);
        }
    }

    @NonNull
    static ImageWriter newInstance(@NonNull Surface surface, @IntRange(from = 1) int maxImages, int format) {
        Throwable t = null;
        if (Build.VERSION.SDK_INT >= 26) {
            try {
                return (ImageWriter) Preconditions.checkNotNull(sNewInstanceMethod.invoke((Object) null, new Object[]{surface, Integer.valueOf(maxImages), Integer.valueOf(format)}));
            } catch (IllegalAccessException | InvocationTargetException e) {
                t = e;
            }
        }
        throw new RuntimeException("Unable to invoke newInstance(Surface, int, int) via reflection.", t);
    }

    private ImageWriterCompatApi26Impl() {
    }
}
