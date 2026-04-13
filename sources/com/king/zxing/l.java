package com.king.zxing;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPointCallback;
import com.king.zxing.camera.d;
import com.king.zxing.util.b;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/* compiled from: DecodeThread */
public final class l extends Thread {
    private final CaptureActivity c;
    private final d d;
    private final Map<DecodeHintType, Object> f;
    private Handler q;
    private h x;
    private final CountDownLatch y = new CountDownLatch(1);

    l(CaptureActivity context, d cameraManager, h captureHandler, Collection<BarcodeFormat> decodeFormats, Map<DecodeHintType, Object> baseHints, String characterSet, ResultPointCallback resultPointCallback) {
        this.c = context;
        this.d = cameraManager;
        this.x = captureHandler;
        EnumMap enumMap = new EnumMap(DecodeHintType.class);
        this.f = enumMap;
        if (baseHints != null) {
            enumMap.putAll(baseHints);
        }
        if (decodeFormats == null || decodeFormats.isEmpty()) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            decodeFormats = EnumSet.noneOf(BarcodeFormat.class);
            if (prefs.getBoolean("preferences_decode_1D_product", false)) {
                decodeFormats.addAll(j.b);
            }
            if (prefs.getBoolean("preferences_decode_1D_industrial", true)) {
                decodeFormats.addAll(j.c);
            }
            if (prefs.getBoolean("preferences_decode_QR", true)) {
                decodeFormats.addAll(j.e);
            }
            if (prefs.getBoolean("preferences_decode_Data_Matrix", true)) {
                decodeFormats.addAll(j.f);
            }
            if (prefs.getBoolean("preferences_decode_Aztec", false)) {
                decodeFormats.addAll(j.g);
            }
            if (prefs.getBoolean("preferences_decode_PDF417", false)) {
                decodeFormats.addAll(j.h);
            }
        }
        enumMap.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
        b.h("DecodeThread decodeFormats:" + decodeFormats);
        enumMap.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        DecodeHintType decodeHintType = DecodeHintType.CHARACTER_SET;
        enumMap.put(decodeHintType, "utf-8");
        if (characterSet != null) {
            enumMap.put(decodeHintType, characterSet);
        }
        enumMap.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, resultPointCallback);
        b.f("Hints: " + enumMap);
    }

    /* access modifiers changed from: package-private */
    public Handler a() {
        try {
            this.y.await();
        } catch (InterruptedException e) {
        }
        return this.q;
    }

    public void run() {
        Looper.prepare();
        this.q = new k(this.c, this.d, this.x, this.f);
        this.y.countDown();
        Looper.loop();
    }
}
