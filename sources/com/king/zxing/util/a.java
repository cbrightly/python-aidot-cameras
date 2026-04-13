package com.king.zxing.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.king.zxing.j;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;

/* compiled from: CodeUtils */
public final class a {
    static int a = 0;
    static int b = 0;

    public static String c(String bitmapPath) {
        Map<DecodeHintType, Object> hints = new HashMap<>();
        Vector<BarcodeFormat> decodeFormats = new Vector<>();
        decodeFormats.addAll(j.d);
        decodeFormats.addAll(j.e);
        decodeFormats.addAll(j.f);
        decodeFormats.addAll(j.g);
        decodeFormats.addAll(j.h);
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
        return d(bitmapPath, hints);
    }

    public static String d(String bitmapPath, Map<DecodeHintType, Object> hints) {
        Result result = f(bitmapPath, hints);
        if (result != null) {
            return result.getText();
        }
        return null;
    }

    public static Result f(String bitmapPath, Map<DecodeHintType, Object> hints) {
        return e(bitmapPath, IjkMediaCodecInfo.RANK_SECURE, 530, hints);
    }

    public static Result e(String bitmapPath, int reqWidth, int reqHeight, Map<DecodeHintType, Object> hints) {
        boolean isReDecode;
        Result result = null;
        try {
            MultiFormatReader reader = new MultiFormatReader();
            reader.setHints(hints);
            RGBLuminanceSource source = b(a(bitmapPath, reqWidth, reqHeight));
            if (source != null) {
                try {
                    result = reader.decodeWithState(new BinaryBitmap(new HybridBinarizer(source)));
                    isReDecode = false;
                } catch (Exception e) {
                    isReDecode = true;
                }
                if (isReDecode) {
                    try {
                        result = reader.decodeWithState(new BinaryBitmap(new HybridBinarizer(source.invert())));
                        isReDecode = false;
                    } catch (Exception e2) {
                        isReDecode = true;
                    }
                }
                if (isReDecode) {
                    try {
                        result = reader.decodeWithState(new BinaryBitmap(new GlobalHistogramBinarizer(source)));
                        isReDecode = false;
                    } catch (Exception e3) {
                        isReDecode = true;
                    }
                }
                if (isReDecode) {
                    if (source.isRotateSupported()) {
                        try {
                            result = reader.decodeWithState(new BinaryBitmap(new HybridBinarizer(source.rotateCounterClockwise())));
                        } catch (Exception e4) {
                        }
                    }
                }
                reader.reset();
            }
        } catch (Exception e5) {
            b.i(e5.getMessage());
        }
        return result;
    }

    private static Bitmap a(String path, int reqWidth, int reqHeight) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, newOpts);
        float width = (float) newOpts.outWidth;
        float height = (float) newOpts.outHeight;
        int wSize = 1;
        if (width > ((float) reqWidth)) {
            wSize = (int) (width / ((float) reqWidth));
        }
        int hSize = 1;
        if (height > ((float) reqHeight)) {
            hSize = (int) (height / ((float) reqHeight));
        }
        int size = Math.max(wSize, hSize);
        if (size <= 0) {
            size = 1;
        }
        newOpts.inSampleSize = size;
        newOpts.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, newOpts);
    }

    private static RGBLuminanceSource b(@NonNull Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[(width * height)];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return new RGBLuminanceSource(width, height, pixels);
    }
}
