package com.didichuxing.doraemonkit.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageUtil {
    private ImageUtil() {
    }

    public static Bitmap decodeSampledBitmapFromFilePath(String imagePath, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        if (height <= reqHeight && width <= reqWidth) {
            return 1;
        }
        int heightRatio = Math.round(((float) height) / ((float) reqHeight));
        int widthRatio = Math.round(((float) width) / ((float) reqWidth));
        return heightRatio < widthRatio ? heightRatio : widthRatio;
    }

    public static int getPixel(Bitmap bitmap, int x, int y) {
        if (bitmap != null && x >= 0 && x <= bitmap.getWidth() && y >= 0 && y <= bitmap.getHeight()) {
            return bitmap.getPixel(x, y);
        }
        return -1;
    }
}
