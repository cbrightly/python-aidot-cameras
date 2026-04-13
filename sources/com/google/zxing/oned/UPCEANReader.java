package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Map;

public abstract class UPCEANReader extends OneDReader {
    static final int[] END_PATTERN = {1, 1, 1, 1, 1, 1};
    static final int[][] L_AND_G_PATTERNS;
    static final int[][] L_PATTERNS;
    private static final float MAX_AVG_VARIANCE = 0.48f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.7f;
    static final int[] MIDDLE_PATTERN = {1, 1, 1, 1, 1};
    static final int[] START_END_PATTERN = {1, 1, 1};
    private final StringBuilder decodeRowStringBuffer = new StringBuilder(20);
    private final EANManufacturerOrgSupport eanManSupport = new EANManufacturerOrgSupport();
    private final UPCEANExtensionSupport extensionReader = new UPCEANExtensionSupport();

    /* access modifiers changed from: protected */
    public abstract int decodeMiddle(BitArray bitArray, int[] iArr, StringBuilder sb);

    /* access modifiers changed from: package-private */
    public abstract BarcodeFormat getBarcodeFormat();

    static {
        int[][] iArr = {new int[]{3, 2, 1, 1}, new int[]{2, 2, 2, 1}, new int[]{2, 1, 2, 2}, new int[]{1, 4, 1, 1}, new int[]{1, 1, 3, 2}, new int[]{1, 2, 3, 1}, new int[]{1, 1, 1, 4}, new int[]{1, 3, 1, 2}, new int[]{1, 2, 1, 3}, new int[]{3, 1, 1, 2}};
        L_PATTERNS = iArr;
        int[][] iArr2 = new int[20][];
        L_AND_G_PATTERNS = iArr2;
        System.arraycopy(iArr, 0, iArr2, 0, 10);
        for (int i = 10; i < 20; i++) {
            int[] widths = L_PATTERNS[i - 10];
            int[] reversedWidths = new int[widths.length];
            for (int j = 0; j < widths.length; j++) {
                reversedWidths[j] = widths[(widths.length - j) - 1];
            }
            L_AND_G_PATTERNS[i] = reversedWidths;
        }
    }

    protected UPCEANReader() {
    }

    static int[] findStartGuardPattern(BitArray row) {
        boolean foundStart = false;
        int[] startRange = null;
        int nextStart = 0;
        int[] counters = new int[START_END_PATTERN.length];
        while (!foundStart) {
            int[] iArr = START_END_PATTERN;
            Arrays.fill(counters, 0, iArr.length, 0);
            startRange = findGuardPattern(row, nextStart, false, iArr, counters);
            int start = startRange[0];
            nextStart = startRange[1];
            int quietStart = start - (nextStart - start);
            if (quietStart >= 0) {
                foundStart = row.isRange(quietStart, start, false);
            }
        }
        return startRange;
    }

    public Result decodeRow(int rowNumber, BitArray row, Map<DecodeHintType, ?> hints) {
        return decodeRow(rowNumber, row, findStartGuardPattern(row), hints);
    }

    public Result decodeRow(int rowNumber, BitArray row, int[] startGuardRange, Map<DecodeHintType, ?> hints) {
        ResultPointCallback resultPointCallback;
        int extensionLength;
        String countryID;
        int i = rowNumber;
        BitArray bitArray = row;
        int[] iArr = startGuardRange;
        Map<DecodeHintType, ?> map = hints;
        if (map == null) {
            resultPointCallback = null;
        } else {
            resultPointCallback = (ResultPointCallback) map.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
        }
        ResultPointCallback resultPointCallback2 = resultPointCallback;
        if (resultPointCallback2 != null) {
            resultPointCallback2.foundPossibleResultPoint(new ResultPoint(((float) (iArr[0] + iArr[1])) / 2.0f, (float) i));
        }
        StringBuilder result = this.decodeRowStringBuffer;
        result.setLength(0);
        int endStart = decodeMiddle(bitArray, iArr, result);
        if (resultPointCallback2 != null) {
            resultPointCallback2.foundPossibleResultPoint(new ResultPoint((float) endStart, (float) i));
        }
        int[] endRange = decodeEnd(bitArray, endStart);
        if (resultPointCallback2 != null) {
            resultPointCallback2.foundPossibleResultPoint(new ResultPoint(((float) (endRange[0] + endRange[1])) / 2.0f, (float) i));
        }
        int end = endRange[1];
        int quietEnd = (end - endRange[0]) + end;
        if (quietEnd >= row.getSize() || !bitArray.isRange(end, quietEnd, false)) {
            ResultPointCallback resultPointCallback3 = resultPointCallback2;
            StringBuilder sb = result;
            throw NotFoundException.getNotFoundInstance();
        }
        String resultString = result.toString();
        if (resultString.length() < 8) {
            throw FormatException.getFormatInstance();
        } else if (checkChecksum(resultString)) {
            float left = ((float) (iArr[1] + iArr[0])) / 2.0f;
            BarcodeFormat format = getBarcodeFormat();
            ResultPointCallback resultPointCallback4 = resultPointCallback2;
            StringBuilder sb2 = result;
            Result decodeResult = new Result(resultString, (byte[]) null, new ResultPoint[]{new ResultPoint(left, (float) i), new ResultPoint(((float) (endRange[1] + endRange[0])) / 2.0f, (float) i)}, format);
            try {
                Result extensionResult = this.extensionReader.decodeRow(i, bitArray, endRange[1]);
                decodeResult.putMetadata(ResultMetadataType.UPC_EAN_EXTENSION, extensionResult.getText());
                decodeResult.putAllMetadata(extensionResult.getResultMetadata());
                decodeResult.addResultPoints(extensionResult.getResultPoints());
                extensionLength = extensionResult.getText().length();
            } catch (ReaderException e) {
                extensionLength = 0;
            }
            int[] allowedExtensions = map == null ? null : (int[]) map.get(DecodeHintType.ALLOWED_EAN_EXTENSIONS);
            if (allowedExtensions != null) {
                boolean valid = false;
                int length = allowedExtensions.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    int i3 = length;
                    if (extensionLength == allowedExtensions[i2]) {
                        valid = true;
                        break;
                    }
                    i2++;
                    length = i3;
                }
                if (!valid) {
                    throw NotFoundException.getNotFoundInstance();
                }
            }
            if ((format == BarcodeFormat.EAN_13 || format == BarcodeFormat.UPC_A) && (countryID = this.eanManSupport.lookupCountryIdentifier(resultString)) != null) {
                decodeResult.putMetadata(ResultMetadataType.POSSIBLE_COUNTRY, countryID);
            }
            return decodeResult;
        } else {
            throw ChecksumException.getChecksumInstance();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean checkChecksum(String s) {
        return checkStandardUPCEANChecksum(s);
    }

    static boolean checkStandardUPCEANChecksum(CharSequence s) {
        int length = s.length();
        if (length != 0 && getStandardUPCEANChecksum(s.subSequence(0, length - 1)) == Character.digit(s.charAt(length - 1), 10)) {
            return true;
        }
        return false;
    }

    static int getStandardUPCEANChecksum(CharSequence s) {
        int length = s.length();
        int sum = 0;
        for (int i = length - 1; i >= 0; i -= 2) {
            int digit = s.charAt(i) - '0';
            if (digit < 0 || digit > 9) {
                throw FormatException.getFormatInstance();
            }
            sum += digit;
        }
        int sum2 = sum * 3;
        for (int i2 = length - 2; i2 >= 0; i2 -= 2) {
            int digit2 = s.charAt(i2) - '0';
            if (digit2 < 0 || digit2 > 9) {
                throw FormatException.getFormatInstance();
            }
            sum2 += digit2;
        }
        return (1000 - sum2) % 10;
    }

    /* access modifiers changed from: package-private */
    public int[] decodeEnd(BitArray row, int endStart) {
        return findGuardPattern(row, endStart, false, START_END_PATTERN);
    }

    static int[] findGuardPattern(BitArray row, int rowOffset, boolean whiteFirst, int[] pattern) {
        return findGuardPattern(row, rowOffset, whiteFirst, pattern, new int[pattern.length]);
    }

    private static int[] findGuardPattern(BitArray row, int rowOffset, boolean whiteFirst, int[] pattern, int[] counters) {
        int width = row.getSize();
        int rowOffset2 = whiteFirst ? row.getNextUnset(rowOffset) : row.getNextSet(rowOffset);
        int counterPosition = 0;
        int patternStart = rowOffset2;
        int patternLength = pattern.length;
        boolean isWhite = whiteFirst;
        for (int x = rowOffset2; x < width; x++) {
            boolean z = true;
            if (row.get(x) != isWhite) {
                counters[counterPosition] = counters[counterPosition] + 1;
            } else {
                if (counterPosition != patternLength - 1) {
                    counterPosition++;
                } else if (OneDReader.patternMatchVariance(counters, pattern, MAX_INDIVIDUAL_VARIANCE) < MAX_AVG_VARIANCE) {
                    return new int[]{patternStart, x};
                } else {
                    patternStart += counters[0] + counters[1];
                    System.arraycopy(counters, 2, counters, 0, counterPosition - 1);
                    counters[counterPosition - 1] = 0;
                    counters[counterPosition] = 0;
                    counterPosition--;
                }
                counters[counterPosition] = 1;
                if (isWhite) {
                    z = false;
                }
                isWhite = z;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    static int decodeDigit(BitArray row, int[] counters, int rowOffset, int[][] patterns) {
        OneDReader.recordPattern(row, rowOffset, counters);
        float bestVariance = MAX_AVG_VARIANCE;
        int bestMatch = -1;
        int max = patterns.length;
        for (int i = 0; i < max; i++) {
            float variance = OneDReader.patternMatchVariance(counters, patterns[i], MAX_INDIVIDUAL_VARIANCE);
            if (variance < bestVariance) {
                bestVariance = variance;
                bestMatch = i;
            }
        }
        if (bestMatch >= 0) {
            return bestMatch;
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
