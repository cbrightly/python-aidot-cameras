package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public final class Code39Writer extends OneDimensionalCodeWriter {
    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
        if (format == BarcodeFormat.CODE_39) {
            return super.encode(contents, format, width, height, hints);
        }
        throw new IllegalArgumentException("Can only encode CODE_39, but got " + format);
    }

    public boolean[] encode(String contents) {
        int length = contents.length();
        if (length <= 80) {
            int[] widths = new int[9];
            int codeWidth = length + 25;
            int i = 0;
            while (true) {
                if (i < length) {
                    int indexInString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(contents.charAt(i));
                    if (indexInString >= 0) {
                        toIntArray(Code39Reader.CHARACTER_ENCODINGS[indexInString], widths);
                        for (int width : widths) {
                            codeWidth += width;
                        }
                        i++;
                    } else {
                        throw new IllegalArgumentException("Bad contents: " + contents);
                    }
                } else {
                    boolean[] result = new boolean[codeWidth];
                    toIntArray(Code39Reader.ASTERISK_ENCODING, widths);
                    int pos = OneDimensionalCodeWriter.appendPattern(result, 0, widths, true);
                    int[] narrowWhite = {1};
                    int pos2 = pos + OneDimensionalCodeWriter.appendPattern(result, pos, narrowWhite, false);
                    for (int i2 = 0; i2 < length; i2++) {
                        toIntArray(Code39Reader.CHARACTER_ENCODINGS["0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(contents.charAt(i2))], widths);
                        int pos3 = pos2 + OneDimensionalCodeWriter.appendPattern(result, pos2, widths, true);
                        pos2 = pos3 + OneDimensionalCodeWriter.appendPattern(result, pos3, narrowWhite, false);
                    }
                    toIntArray(Code39Reader.ASTERISK_ENCODING, widths);
                    OneDimensionalCodeWriter.appendPattern(result, pos2, widths, true);
                    return result;
                }
            }
        } else {
            throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got " + length);
        }
    }

    private static void toIntArray(int a, int[] toReturn) {
        for (int i = 0; i < 9; i++) {
            int i2 = 1;
            if (((1 << (8 - i)) & a) != 0) {
                i2 = 2;
            }
            toReturn[i] = i2;
        }
    }
}
