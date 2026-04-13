package com.didichuxing.doraemonkit.zxing.decoding;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.zxing.activity.CaptureActivity;
import com.didichuxing.doraemonkit.zxing.camera.CameraManager;
import com.didichuxing.doraemonkit.zxing.camera.PlanarYUVLuminanceSource;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import java.util.Hashtable;

public final class DecodeHandler extends Handler {
    private static final String TAG = DecodeHandler.class.getSimpleName();
    private final CaptureActivity activity;
    private final MultiFormatReader multiFormatReader;

    DecodeHandler(CaptureActivity activity2, Hashtable<DecodeHintType, Object> hints) {
        MultiFormatReader multiFormatReader2 = new MultiFormatReader();
        this.multiFormatReader = multiFormatReader2;
        multiFormatReader2.setHints(hints);
        this.activity = activity2;
    }

    public void handleMessage(Message message) {
        int i = message.what;
        if (i == R.id.decode) {
            decode((byte[]) message.obj, message.arg1, message.arg2);
        } else if (i == R.id.quit) {
            Looper.myLooper().quit();
        }
    }

    private void decode(byte[] data, int width, int height) {
        byte[] bArr = data;
        int i = width;
        int i2 = height;
        long start = System.currentTimeMillis();
        Result rawResult = null;
        byte[] rotatedData = new byte[bArr.length];
        for (int y = 0; y < i2; y++) {
            for (int x = 0; x < i; x++) {
                rotatedData[(((x * i2) + i2) - y) - 1] = bArr[(y * i) + x];
            }
        }
        int height2 = width;
        PlanarYUVLuminanceSource source = CameraManager.get().buildLuminanceSource(rotatedData, height, height2);
        try {
            rawResult = this.multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(source)));
        } catch (ReaderException e) {
        } catch (Throwable th) {
            this.multiFormatReader.reset();
            throw th;
        }
        this.multiFormatReader.reset();
        if (rawResult != null) {
            long end = System.currentTimeMillis();
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Found barcode (");
            int i3 = height2;
            sb.append(end - start);
            sb.append(" ms):\n");
            sb.append(rawResult.toString());
            Log.d(str, sb.toString());
            Message message = Message.obtain(this.activity.getHandler(), R.id.decode_succeeded, rawResult);
            Bundle bundle = new Bundle();
            bundle.putParcelable(DecodeThread.BARCODE_BITMAP, source.renderCroppedGreyscaleBitmap());
            message.setData(bundle);
            message.sendToTarget();
            return;
        }
        Message.obtain(this.activity.getHandler(), R.id.decode_failed).sendToTarget();
    }
}
