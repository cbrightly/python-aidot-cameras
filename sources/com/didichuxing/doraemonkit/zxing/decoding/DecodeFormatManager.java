package com.didichuxing.doraemonkit.zxing.decoding;

import android.content.Intent;
import android.net.Uri;
import com.didichuxing.doraemonkit.zxing.decoding.Intents;
import com.google.zxing.BarcodeFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

public final class DecodeFormatManager {
    private static final Pattern COMMA_PATTERN = Pattern.compile(",");
    static final Vector<BarcodeFormat> DATA_MATRIX_FORMATS;
    static final Vector<BarcodeFormat> ONE_D_FORMATS;
    static final Vector<BarcodeFormat> PRODUCT_FORMATS;
    static final Vector<BarcodeFormat> QR_CODE_FORMATS;

    static {
        Vector<BarcodeFormat> vector = new Vector<>(5);
        PRODUCT_FORMATS = vector;
        vector.add(BarcodeFormat.UPC_A);
        vector.add(BarcodeFormat.UPC_E);
        vector.add(BarcodeFormat.EAN_13);
        vector.add(BarcodeFormat.EAN_8);
        Vector<BarcodeFormat> vector2 = new Vector<>(vector.size() + 4);
        ONE_D_FORMATS = vector2;
        vector2.addAll(vector);
        vector2.add(BarcodeFormat.CODE_39);
        vector2.add(BarcodeFormat.CODE_93);
        vector2.add(BarcodeFormat.CODE_128);
        vector2.add(BarcodeFormat.ITF);
        Vector<BarcodeFormat> vector3 = new Vector<>(1);
        QR_CODE_FORMATS = vector3;
        vector3.add(BarcodeFormat.QR_CODE);
        Vector<BarcodeFormat> vector4 = new Vector<>(1);
        DATA_MATRIX_FORMATS = vector4;
        vector4.add(BarcodeFormat.DATA_MATRIX);
    }

    private DecodeFormatManager() {
    }

    static Vector<BarcodeFormat> parseDecodeFormats(Intent intent) {
        List<String> scanFormats = null;
        String scanFormatsString = intent.getStringExtra(Intents.Scan.SCAN_FORMATS);
        if (scanFormatsString != null) {
            scanFormats = Arrays.asList(COMMA_PATTERN.split(scanFormatsString));
        }
        return parseDecodeFormats(scanFormats, intent.getStringExtra(Intents.Scan.MODE));
    }

    static Vector<BarcodeFormat> parseDecodeFormats(Uri inputUri) {
        List<String> formats = inputUri.getQueryParameters(Intents.Scan.SCAN_FORMATS);
        if (!(formats == null || formats.size() != 1 || formats.get(0) == null)) {
            formats = Arrays.asList(COMMA_PATTERN.split(formats.get(0)));
        }
        return parseDecodeFormats(formats, inputUri.getQueryParameter(Intents.Scan.MODE));
    }

    private static Vector<BarcodeFormat> parseDecodeFormats(Iterable<String> scanFormats, String decodeMode) {
        if (scanFormats != null) {
            Vector<BarcodeFormat> formats = new Vector<>();
            try {
                for (String format : scanFormats) {
                    formats.add(BarcodeFormat.valueOf(format));
                }
                return formats;
            } catch (IllegalArgumentException e) {
            }
        }
        if (decodeMode == null) {
            return null;
        }
        if (Intents.Scan.PRODUCT_MODE.equals(decodeMode)) {
            return PRODUCT_FORMATS;
        }
        if (Intents.Scan.QR_CODE_MODE.equals(decodeMode)) {
            return QR_CODE_FORMATS;
        }
        if (Intents.Scan.DATA_MATRIX_MODE.equals(decodeMode)) {
            return DATA_MATRIX_FORMATS;
        }
        if (Intents.Scan.ONE_D_MODE.equals(decodeMode)) {
            return ONE_D_FORMATS;
        }
        return null;
    }
}
