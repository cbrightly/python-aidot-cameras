package com.king.zxing;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Display;
import android.view.WindowManager;
import com.didichuxing.doraemonkit.zxing.decoding.DecodeThread;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.king.zxing.camera.d;
import com.king.zxing.util.b;
import com.leedarson.serviceimpl.camera.R$id;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/* compiled from: DecodeHandler */
public final class k extends Handler {
    private final d a;
    private final h b;
    private final MultiFormatReader c;
    private boolean d = true;
    private final CaptureActivity e;
    private final Map<DecodeHintType, Object> f;

    k(CaptureActivity context, d cameraManager, h handler, Map<DecodeHintType, Object> hints) {
        MultiFormatReader multiFormatReader = new MultiFormatReader();
        this.c = multiFormatReader;
        multiFormatReader.setActivity(context);
        multiFormatReader.setHints(hints);
        this.f = hints;
        this.e = context;
        this.a = cameraManager;
        this.b = handler;
    }

    public void handleMessage(Message message) {
        if (message != null) {
            try {
                if (this.d) {
                    int i = message.what;
                    if (i == R$id.decode) {
                        b.h("收到解码数据");
                        c((byte[]) message.obj, message.arg1, message.arg2, d(), this.b.c());
                    } else if (i == R$id.quit) {
                        b.h("DecodeHandler quit");
                        this.d = false;
                        Looper.myLooper().quit();
                    } else {
                        b.h("DecodeHandler no message 處理");
                    }
                    return;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                b.h("DecodeHandler exception:" + e2.getMessage());
                return;
            }
        }
        b.h("DecodeHandler message null");
    }

    private boolean d() {
        try {
            Display display = ((WindowManager) this.e.getSystemService("window")).getDefaultDisplay();
            Point screenResolution = new Point();
            display.getSize(screenResolution);
            if (screenResolution.x < screenResolution.y) {
                return true;
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private void c(byte[] data, int width, int height, boolean isScreenPortrait, boolean isSupportVerticalCode) {
        boolean isReDecode;
        b.h("decode data length: " + data.length + ",width:" + width + ",height:" + height + ",isScreenPortrait:" + isScreenPortrait);
        long start = System.currentTimeMillis();
        Result rawResult = null;
        PlanarYUVLuminanceSource source = a(data, width, height, isScreenPortrait);
        if (source != null) {
            try {
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                b.h("hints:" + this.f + ",mulforreaders:" + this.c);
                rawResult = this.c.decode(bitmap, this.f);
                isReDecode = false;
                b.h(" HybridBinarize 解碼成功");
            } catch (Exception e2) {
                isReDecode = true;
                b.h("QRCodeReader exception HybridBinarize 解碼失敗:" + e2.getMessage());
            }
            if (isReDecode) {
                try {
                    rawResult = this.c.decodeWithState(new BinaryBitmap(new GlobalHistogramBinarizer(source)));
                    b.h("GlobalHistogramBinarizer 解碼成功");
                } catch (Exception e3) {
                    b.h("exception GlobalHistogramBinarizer 解碼失敗:" + e3.getMessage());
                }
            }
            this.c.reset();
        }
        if (rawResult != null) {
            long end = System.currentTimeMillis();
            b.a("Found barcode in " + (end - start) + " ms");
            StringBuilder sb = new StringBuilder();
            sb.append("-lite 解码成功：");
            sb.append(rawResult.getText());
            b.h(sb.toString());
            BarcodeFormat barcodeFormat = rawResult.getBarcodeFormat();
            h hVar = this.b;
            if (hVar != null && hVar.b() && barcodeFormat == BarcodeFormat.QR_CODE) {
                ResultPoint[] resultPoints = rawResult.getResultPoints();
                if (resultPoints.length >= 3) {
                    float distance1 = ResultPoint.distance(resultPoints[0], resultPoints[1]);
                    float distance2 = ResultPoint.distance(resultPoints[1], resultPoints[2]);
                    float distance3 = ResultPoint.distance(resultPoints[0], resultPoints[2]);
                    int max = (int) Math.max(Math.max(distance1, distance2), distance3);
                    float f2 = distance1;
                    Message message = Message.obtain();
                    float f3 = distance2;
                    message.what = R$id.decode_succeeded;
                    message.obj = rawResult;
                    if (this.b.a()) {
                        Bundle bundle = new Bundle();
                        float f4 = distance3;
                        bundle.putBoolean(FirebaseAnalytics.Param.SUCCESS, true);
                        b(source, bundle);
                        message.setData(bundle);
                    }
                    this.b.sendMessageDelayed(message, 300);
                    return;
                }
            }
            h hVar2 = this.b;
            if (hVar2 != null) {
                Message message2 = Message.obtain(hVar2, R$id.decode_succeeded, rawResult);
                if (this.b.a()) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putBoolean(FirebaseAnalytics.Param.SUCCESS, true);
                    b(source, bundle2);
                    message2.setData(bundle2);
                }
                message2.sendToTarget();
                return;
            }
            return;
        }
        b.h(" --lite 解码失敗");
        h hVar3 = this.b;
        if (hVar3 != null) {
            Message message3 = Message.obtain(hVar3, R$id.decode_failed);
            if (this.b.a()) {
                Bundle bundle3 = new Bundle();
                bundle3.putBoolean(FirebaseAnalytics.Param.SUCCESS, false);
                b(source, bundle3);
                message3.setData(bundle3);
            }
            message3.sendToTarget();
        }
    }

    private PlanarYUVLuminanceSource a(byte[] data, int width, int height, boolean isRotate) {
        b.h("传入的数据:width=" + width + ",height:" + height);
        if (!isRotate) {
            return this.a.a(data, width, height);
        }
        byte[] rotatedData = new byte[data.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rotatedData[(((x * height) + height) - y) - 1] = data[(y * width) + x];
            }
        }
        int tmp = width;
        int width2 = height;
        int height2 = tmp;
        b.h("转换后的数据:width=" + width2 + ",height:" + height2);
        return this.a.a(rotatedData, width2, height2);
    }

    private static void b(PlanarYUVLuminanceSource source, Bundle bundle) {
        int[] pixels = source.renderThumbnail();
        int width = source.getThumbnailWidth();
        Bitmap bitmap = Bitmap.createBitmap(pixels, 0, width, width, source.getThumbnailHeight(), Bitmap.Config.ARGB_8888);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        bundle.putByteArray(DecodeThread.BARCODE_BITMAP, out.toByteArray());
        bundle.putFloat("barcode_scaled_factor", ((float) width) / ((float) source.getWidth()));
    }
}
