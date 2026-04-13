package com.bumptech.glide.integration.webp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import androidx.annotation.Keep;
import com.alibaba.fastjson.asm.Opcodes;
import com.bumptech.glide.integration.webp.b;
import java.io.BufferedInputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Keep
public class WebpBitmapFactory {
    private static final int IN_TEMP_BUFFER_SIZE = 8192;
    private static final int MAX_WEBP_HEADER_SIZE = 21;

    private static native Bitmap nativeDecodeByteArray(byte[] bArr, int i, int i2, BitmapFactory.Options options, float f, byte[] bArr2);

    private static native Bitmap nativeDecodeStream(InputStream inputStream, BitmapFactory.Options options, float f, byte[] bArr);

    static {
        System.loadLibrary("glide-webp");
    }

    private static void setDensityFromOptions(Bitmap outputBitmap, BitmapFactory.Options opts) {
        if (outputBitmap != null && opts != null) {
            int density = opts.inDensity;
            if (density != 0) {
                outputBitmap.setDensity(density);
                int targetDensity = opts.inTargetDensity;
                if (targetDensity != 0 && density != targetDensity && density != opts.inScreenDensity && opts.inScaled) {
                    outputBitmap.setDensity(targetDensity);
                }
            } else if (opts.inBitmap != null) {
                outputBitmap.setDensity(Opcodes.IF_ICMPNE);
            }
        }
    }

    private static void setWebpBitmapOptions(Bitmap bitmap, BitmapFactory.Options opts) {
        setDensityFromOptions(bitmap, opts);
        if (opts != null) {
            opts.outMimeType = "image/webp";
        }
    }

    @Keep
    private static boolean setOutDimensions(BitmapFactory.Options options, int imageWidth, int imageHeight) {
        if (options == null) {
            return false;
        }
        options.outWidth = imageWidth;
        options.outHeight = imageHeight;
        return options.inJustDecodeBounds;
    }

    @Keep
    private static Bitmap createBitmap(int width, int height, BitmapFactory.Options options) {
        Bitmap bitmap;
        if (options != null && (bitmap = options.inBitmap) != null && bitmap.isMutable()) {
            return options.inBitmap;
        }
        Bitmap bitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap2.setHasAlpha(true);
        bitmap2.eraseColor(0);
        return bitmap2;
    }

    private static void setDefaultPadding(Rect padding) {
        if (padding != null) {
            padding.top = -1;
            padding.left = -1;
            padding.bottom = -1;
            padding.right = -1;
        }
    }

    public static boolean webpSupportRequired(byte[] headers, int offset, int length) {
        b.e imageType;
        try {
            imageType = b.d(headers, offset, length);
        } catch (IOException e) {
            imageType = b.e.NONE_WEBP;
        }
        return Build.VERSION.SDK_INT <= 17 && b.g(imageType);
    }

    private static byte[] getImageHeader(InputStream is) {
        if (!is.markSupported()) {
            is = new BufferedInputStream(is, 21);
        }
        is.mark(21);
        byte[] header = new byte[21];
        try {
            is.read(header, 0, 21);
            is.reset();
            return header;
        } catch (IOException e) {
            return null;
        }
    }

    private static InputStream wrapToMarkSupportedStream(InputStream inputStream) {
        if (!inputStream.markSupported()) {
            return new BufferedInputStream(inputStream, 8192);
        }
        return inputStream;
    }

    public static Bitmap decodeByteArray(byte[] data, int offset, int length) {
        return decodeByteArray(data, offset, length, (BitmapFactory.Options) null);
    }

    public static Bitmap decodeByteArray(byte[] data, int offset, int length, BitmapFactory.Options opts) {
        if ((offset | length) < 0 || data.length < offset + length) {
            throw new ArrayIndexOutOfBoundsException();
        } else if (!webpSupportRequired(data, offset, length)) {
            return BitmapFactory.decodeByteArray(data, offset, length, opts);
        } else {
            Bitmap bm = nativeDecodeByteArray(data, offset, length, opts, getScaleFromOptions(opts), getInTempStorageFromOptions(opts));
            setWebpBitmapOptions(bm, opts);
            return bm;
        }
    }

    public static Bitmap decodeFile(String pathName) {
        return decodeFile(pathName, (BitmapFactory.Options) null);
    }

    public static Bitmap decodeFile(String pathName, BitmapFactory.Options opts) {
        Bitmap bm = null;
        InputStream stream = null;
        try {
            InputStream stream2 = new FileInputStream(pathName);
            bm = decodeStream(stream2, (Rect) null, opts);
            try {
                stream2.close();
            } catch (IOException e) {
            }
        } catch (Exception e2) {
            Log.e("WebpBitmapFactory", "Unable to decode stream: " + e2);
            if (stream != null) {
                stream.close();
            }
        } catch (Throwable th) {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e3) {
                }
            }
            throw th;
        }
        return bm;
    }

    public static Bitmap decodeResource(Resources res, int id) {
        return decodeResource(res, id, (BitmapFactory.Options) null);
    }

    public static Bitmap decodeResource(Resources res, int id, BitmapFactory.Options opts) {
        Bitmap bm = null;
        InputStream is = null;
        try {
            TypedValue value = new TypedValue();
            InputStream is2 = res.openRawResource(id, value);
            bm = decodeResourceStream(res, value, is2, (Rect) null, opts);
            if (is2 != null) {
                try {
                    is2.close();
                } catch (IOException e) {
                }
            }
        } catch (Exception e2) {
            if (is != null) {
                is.close();
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e3) {
                }
            }
            throw th;
        }
        if (bm != null || opts == null || opts.inBitmap == null) {
            return bm;
        }
        throw new IllegalArgumentException("Problem decoding into existing bitmap");
    }

    public static Bitmap decodeResourceStream(Resources res, TypedValue value, InputStream is, Rect pad, BitmapFactory.Options opts) {
        if (opts == null) {
            opts = new BitmapFactory.Options();
        }
        if (opts.inDensity == 0 && value != null) {
            int density = value.density;
            if (density == 0) {
                opts.inDensity = Opcodes.IF_ICMPNE;
            } else if (density != 65535) {
                opts.inDensity = density;
            }
        }
        if (opts.inTargetDensity == 0 && res != null) {
            opts.inTargetDensity = res.getDisplayMetrics().densityDpi;
        }
        return decodeStream(is, pad, opts);
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fd) {
        return decodeFileDescriptor(fd, (Rect) null, (BitmapFactory.Options) null);
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fd, Rect outPadding, BitmapFactory.Options opts) {
        Bitmap bm;
        InputStream is = wrapToMarkSupportedStream(new FileInputStream(fd));
        try {
            if (webpSupportRequired(getImageHeader(is), 0, 21)) {
                bm = nativeDecodeStream(is, opts, getScaleFromOptions(opts), getInTempStorageFromOptions(opts));
                setWebpBitmapOptions(bm, opts);
                setDefaultPadding(outPadding);
            } else {
                bm = BitmapFactory.decodeFileDescriptor(fd, outPadding, opts);
            }
            try {
                is.close();
            } catch (Throwable th) {
            }
            return bm;
        } catch (Throwable th2) {
        }
        throw th;
    }

    public static Bitmap decodeStream(InputStream is) {
        return decodeStream(is, (Rect) null, (BitmapFactory.Options) null);
    }

    public static Bitmap decodeStream(InputStream is, Rect outPadding, BitmapFactory.Options opts) {
        if (is == null) {
            return null;
        }
        InputStream is2 = wrapToMarkSupportedStream(is);
        if (!webpSupportRequired(getImageHeader(is2), 0, 21)) {
            return BitmapFactory.decodeStream(is2, outPadding, opts);
        }
        Bitmap bm = nativeDecodeStream(is2, opts, getScaleFromOptions(opts), getInTempStorageFromOptions(opts));
        setWebpBitmapOptions(bm, opts);
        setDefaultPadding(outPadding);
        return bm;
    }

    private static byte[] getInTempStorageFromOptions(BitmapFactory.Options options) {
        byte[] bArr;
        if (options == null || (bArr = options.inTempStorage) == null) {
            return new byte[8192];
        }
        return bArr;
    }

    private static float getScaleFromOptions(BitmapFactory.Options options) {
        float scale = 1.0f;
        if (options == null) {
            return 1.0f;
        }
        int sampleSize = options.inSampleSize;
        if (sampleSize > 1) {
            scale = 1.0f / ((float) sampleSize);
        }
        if (!options.inScaled) {
            return scale;
        }
        int density = options.inDensity;
        int targetDensity = options.inTargetDensity;
        int screenDensity = options.inScreenDensity;
        if (density == 0 || targetDensity == 0 || density == screenDensity) {
            return scale;
        }
        return ((float) targetDensity) / ((float) density);
    }
}
