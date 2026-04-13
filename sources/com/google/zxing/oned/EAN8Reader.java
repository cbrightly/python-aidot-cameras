package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitArray;

public final class EAN8Reader extends UPCEANReader {
    private final int[] decodeMiddleCounters = new int[4];

    /* access modifiers changed from: protected */
    public int decodeMiddle(BitArray row, int[] startRange, StringBuilder result) {
        int[] counters = this.decodeMiddleCounters;
        counters[0] = 0;
        counters[1] = 0;
        counters[2] = 0;
        counters[3] = 0;
        int end = row.getSize();
        int rowOffset = startRange[1];
        for (int x = 0; x < 4 && rowOffset < end; x++) {
            result.append((char) (UPCEANReader.decodeDigit(row, counters, rowOffset, UPCEANReader.L_PATTERNS) + 48));
            for (int counter : counters) {
                rowOffset += counter;
            }
        }
        int rowOffset2 = UPCEANReader.findGuardPattern(row, rowOffset, true, UPCEANReader.MIDDLE_PATTERN)[1];
        for (int x2 = 0; x2 < 4 && rowOffset2 < end; x2++) {
            result.append((char) (UPCEANReader.decodeDigit(row, counters, rowOffset2, UPCEANReader.L_PATTERNS) + 48));
            for (int counter2 : counters) {
                rowOffset2 += counter2;
            }
        }
        return rowOffset2;
    }

    /* access modifiers changed from: package-private */
    public BarcodeFormat getBarcodeFormat() {
        return BarcodeFormat.EAN_8;
    }
}
