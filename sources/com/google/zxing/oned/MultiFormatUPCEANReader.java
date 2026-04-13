package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class MultiFormatUPCEANReader extends OneDReader {
    private final UPCEANReader[] readers;

    public MultiFormatUPCEANReader(Map<DecodeHintType, ?> hints) {
        Collection<BarcodeFormat> possibleFormats;
        if (hints == null) {
            possibleFormats = null;
        } else {
            possibleFormats = (Collection) hints.get(DecodeHintType.POSSIBLE_FORMATS);
        }
        Collection<UPCEANReader> readers2 = new ArrayList<>();
        if (possibleFormats != null) {
            if (possibleFormats.contains(BarcodeFormat.EAN_13)) {
                readers2.add(new EAN13Reader());
            } else if (possibleFormats.contains(BarcodeFormat.UPC_A)) {
                readers2.add(new UPCAReader());
            }
            if (possibleFormats.contains(BarcodeFormat.EAN_8)) {
                readers2.add(new EAN8Reader());
            }
            if (possibleFormats.contains(BarcodeFormat.UPC_E)) {
                readers2.add(new UPCEReader());
            }
        }
        if (readers2.isEmpty()) {
            readers2.add(new EAN13Reader());
            readers2.add(new EAN8Reader());
            readers2.add(new UPCEReader());
        }
        this.readers = (UPCEANReader[]) readers2.toArray(new UPCEANReader[readers2.size()]);
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x003a A[SYNTHETIC, Splitter:B:17:0x003a] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0044 A[SYNTHETIC, Splitter:B:20:0x0044] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0052 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0078 A[ADDED_TO_REGION, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.zxing.Result decodeRow(int r18, com.google.zxing.common.BitArray r19, java.util.Map<com.google.zxing.DecodeHintType, ?> r20) {
        /*
            r17 = this;
            r1 = r20
            int[] r2 = com.google.zxing.oned.UPCEANReader.findStartGuardPattern(r19)
            r3 = r17
            com.google.zxing.oned.UPCEANReader[] r4 = r3.readers
            int r5 = r4.length
            r6 = 0
            r7 = r6
        L_0x000d:
            if (r7 >= r5) goto L_0x0086
            r8 = r4[r7]
            r9 = r18
            r10 = r19
            com.google.zxing.Result r0 = r8.decodeRow(r9, r10, r2, r1)     // Catch:{ ReaderException -> 0x007b }
            com.google.zxing.BarcodeFormat r11 = r0.getBarcodeFormat()     // Catch:{ ReaderException -> 0x007b }
            com.google.zxing.BarcodeFormat r12 = com.google.zxing.BarcodeFormat.EAN_13     // Catch:{ ReaderException -> 0x007b }
            r13 = 1
            if (r11 != r12) goto L_0x0035
            java.lang.String r11 = r0.getText()     // Catch:{ ReaderException -> 0x0031 }
            char r11 = r11.charAt(r6)     // Catch:{ ReaderException -> 0x0031 }
            r12 = 48
            if (r11 != r12) goto L_0x0035
            r11 = r13
            goto L_0x0036
        L_0x0031:
            r0 = move-exception
            r16 = r2
            goto L_0x007e
        L_0x0035:
            r11 = r6
        L_0x0036:
            if (r1 != 0) goto L_0x003a
            r12 = 0
            goto L_0x0042
        L_0x003a:
            com.google.zxing.DecodeHintType r12 = com.google.zxing.DecodeHintType.POSSIBLE_FORMATS     // Catch:{ ReaderException -> 0x007b }
            java.lang.Object r12 = r1.get(r12)     // Catch:{ ReaderException -> 0x007b }
            java.util.Collection r12 = (java.util.Collection) r12     // Catch:{ ReaderException -> 0x007b }
        L_0x0042:
            if (r12 == 0) goto L_0x004f
            com.google.zxing.BarcodeFormat r14 = com.google.zxing.BarcodeFormat.UPC_A     // Catch:{ ReaderException -> 0x0031 }
            boolean r14 = r12.contains(r14)     // Catch:{ ReaderException -> 0x0031 }
            if (r14 == 0) goto L_0x004d
            goto L_0x004f
        L_0x004d:
            r14 = r6
            goto L_0x0050
        L_0x004f:
            r14 = r13
        L_0x0050:
            if (r11 == 0) goto L_0x0078
            if (r14 == 0) goto L_0x0078
            com.google.zxing.Result r15 = new com.google.zxing.Result     // Catch:{ ReaderException -> 0x007b }
            java.lang.String r6 = r0.getText()     // Catch:{ ReaderException -> 0x007b }
            java.lang.String r6 = r6.substring(r13)     // Catch:{ ReaderException -> 0x007b }
            byte[] r13 = r0.getRawBytes()     // Catch:{ ReaderException -> 0x007b }
            com.google.zxing.ResultPoint[] r1 = r0.getResultPoints()     // Catch:{ ReaderException -> 0x007b }
            r16 = r2
            com.google.zxing.BarcodeFormat r2 = com.google.zxing.BarcodeFormat.UPC_A     // Catch:{ ReaderException -> 0x0076 }
            r15.<init>(r6, r13, r1, r2)     // Catch:{ ReaderException -> 0x0076 }
            r1 = r15
            java.util.Map r2 = r0.getResultMetadata()     // Catch:{ ReaderException -> 0x0076 }
            r1.putAllMetadata(r2)     // Catch:{ ReaderException -> 0x0076 }
            return r1
        L_0x0076:
            r0 = move-exception
            goto L_0x007e
        L_0x0078:
            r16 = r2
            return r0
        L_0x007b:
            r0 = move-exception
            r16 = r2
        L_0x007e:
            int r7 = r7 + 1
            r1 = r20
            r2 = r16
            r6 = 0
            goto L_0x000d
        L_0x0086:
            com.google.zxing.NotFoundException r0 = com.google.zxing.NotFoundException.getNotFoundInstance()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.MultiFormatUPCEANReader.decodeRow(int, com.google.zxing.common.BitArray, java.util.Map):com.google.zxing.Result");
    }

    public void reset() {
        for (Reader reader : this.readers) {
            reader.reset();
        }
    }
}
