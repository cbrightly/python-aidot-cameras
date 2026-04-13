package com.king.zxing;

import com.didichuxing.doraemonkit.zxing.decoding.Intents;
import com.google.zxing.BarcodeFormat;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/* compiled from: DecodeFormatManager */
public final class j {
    private static final Pattern a = Pattern.compile(",");
    public static final Set<BarcodeFormat> b;
    public static final Set<BarcodeFormat> c;
    public static final Set<BarcodeFormat> d;
    public static final Set<BarcodeFormat> e;
    public static final Set<BarcodeFormat> f;
    public static final Set<BarcodeFormat> g;
    public static final Set<BarcodeFormat> h;
    private static final Map<String, Set<BarcodeFormat>> i;

    static {
        EnumSet of = EnumSet.of(BarcodeFormat.QR_CODE, BarcodeFormat.MAXICODE);
        e = of;
        EnumSet of2 = EnumSet.of(BarcodeFormat.DATA_MATRIX);
        f = of2;
        EnumSet of3 = EnumSet.of(BarcodeFormat.AZTEC);
        g = of3;
        EnumSet of4 = EnumSet.of(BarcodeFormat.PDF_417);
        h = of4;
        EnumSet of5 = EnumSet.of(BarcodeFormat.UPC_A, new BarcodeFormat[]{BarcodeFormat.UPC_E, BarcodeFormat.EAN_13, BarcodeFormat.EAN_8, BarcodeFormat.RSS_14, BarcodeFormat.RSS_EXPANDED});
        b = of5;
        EnumSet of6 = EnumSet.of(BarcodeFormat.CODE_39, BarcodeFormat.CODE_93, BarcodeFormat.CODE_128, BarcodeFormat.ITF, BarcodeFormat.CODABAR);
        c = of6;
        EnumSet copyOf = EnumSet.copyOf(of5);
        d = copyOf;
        copyOf.addAll(of6);
        HashMap hashMap = new HashMap();
        i = hashMap;
        hashMap.put(Intents.Scan.ONE_D_MODE, copyOf);
        hashMap.put(Intents.Scan.PRODUCT_MODE, of5);
        hashMap.put(Intents.Scan.QR_CODE_MODE, of);
        hashMap.put(Intents.Scan.DATA_MATRIX_MODE, of2);
        hashMap.put("AZTEC_MODE", of3);
        hashMap.put("PDF417_MODE", of4);
    }
}
