package com.bumptech.glide.integration.webp.decoder;

import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: Utils */
public class h {
    static int a(int srcWidth, int srcHeight, int targetWidth, int targetHeight) {
        int exactSampleSize = Math.min(srcHeight / targetHeight, srcWidth / targetWidth);
        int sampleSize = Math.max(1, exactSampleSize == 0 ? 0 : Integer.highestOneBit(exactSampleSize));
        if (Log.isLoggable("Utils", 2) && sampleSize > 1) {
            Log.v("Utils", "Downsampling WEBP, sampleSize: " + sampleSize + ", target dimens: [" + targetWidth + "x" + targetHeight + "], actual dimens: [" + srcWidth + "x" + srcHeight + "]");
        }
        return sampleSize;
    }

    static byte[] b(InputStream is) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(16384);
        try {
            byte[] data = new byte[16384];
            while (true) {
                int read = is.read(data);
                int nRead = read;
                if (read != -1) {
                    buffer.write(data, 0, nRead);
                } else {
                    buffer.flush();
                    return buffer.toByteArray();
                }
            }
        } catch (IOException e) {
            if (!Log.isLoggable("Utils", 5)) {
                return null;
            }
            Log.w("Utils", "Error reading data from stream", e);
            return null;
        }
    }
}
