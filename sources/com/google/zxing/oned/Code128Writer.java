package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public final class Code128Writer extends OneDimensionalCodeWriter {
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_B = 100;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final char ESCAPE_FNC_1 = 'ñ';
    private static final char ESCAPE_FNC_2 = 'ò';
    private static final char ESCAPE_FNC_3 = 'ó';
    private static final char ESCAPE_FNC_4 = 'ô';

    public enum CType {
        UNCODABLE,
        ONE_DIGIT,
        TWO_DIGITS,
        FNC_1
    }

    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
        if (format == BarcodeFormat.CODE_128) {
            return super.encode(contents, format, width, height, hints);
        }
        throw new IllegalArgumentException("Can only encode CODE_128, but got " + format);
    }

    public boolean[] encode(String contents) {
        int patternIndex;
        int patternIndex2;
        int length = contents.length();
        if (length < 1 || length > 80) {
            throw new IllegalArgumentException("Contents length should be between 1 and 80 characters, but got " + length);
        }
        for (int i = 0; i < length; i++) {
            char c = contents.charAt(i);
            if (c < ' ' || c > '~') {
                switch (c) {
                    case 241:
                    case 242:
                    case 243:
                    case IjkMediaMeta.FF_PROFILE_H264_HIGH_444_PREDICTIVE /*244*/:
                        break;
                    default:
                        throw new IllegalArgumentException("Bad character in input: " + c);
                }
            }
        }
        Collection<int[]> patterns = new ArrayList<>();
        int checkSum = 0;
        int checkWeight = 1;
        int codeSet = 0;
        int position = 0;
        while (position < length) {
            int newCodeSet = chooseCode(contents, position, codeSet);
            if (newCodeSet == codeSet) {
                switch (contents.charAt(position)) {
                    case 241:
                        patternIndex = 102;
                        break;
                    case 242:
                        patternIndex = 97;
                        break;
                    case 243:
                        patternIndex = 96;
                        break;
                    case IjkMediaMeta.FF_PROFILE_H264_HIGH_444_PREDICTIVE /*244*/:
                        patternIndex = 100;
                        break;
                    default:
                        if (codeSet != 100) {
                            patternIndex = Integer.parseInt(contents.substring(position, position + 2));
                            position++;
                            break;
                        } else {
                            patternIndex = contents.charAt(position) - ' ';
                            break;
                        }
                }
                position++;
            } else {
                if (codeSet != 0) {
                    patternIndex2 = newCodeSet;
                } else if (newCodeSet == 100) {
                    patternIndex2 = 104;
                } else {
                    patternIndex2 = 105;
                }
                codeSet = newCodeSet;
            }
            patterns.add(Code128Reader.CODE_PATTERNS[patternIndex]);
            checkSum += patternIndex * checkWeight;
            if (position != 0) {
                checkWeight++;
            }
        }
        int[][] iArr = Code128Reader.CODE_PATTERNS;
        patterns.add(iArr[checkSum % 103]);
        patterns.add(iArr[106]);
        int codeWidth = 0;
        for (int[] pattern : patterns) {
            for (int width : r8.next()) {
                codeWidth += width;
            }
        }
        boolean[] result = new boolean[codeWidth];
        int pos = 0;
        for (int[] pattern2 : patterns) {
            pos += OneDimensionalCodeWriter.appendPattern(result, pos, pattern2, true);
        }
        return result;
    }

    private static CType findCType(CharSequence value, int start) {
        int last = value.length();
        if (start >= last) {
            return CType.UNCODABLE;
        }
        char c = value.charAt(start);
        if (c == 241) {
            return CType.FNC_1;
        }
        if (c < '0' || c > '9') {
            return CType.UNCODABLE;
        }
        if (start + 1 >= last) {
            return CType.ONE_DIGIT;
        }
        char c2 = value.charAt(start + 1);
        if (c2 < '0' || c2 > '9') {
            return CType.ONE_DIGIT;
        }
        return CType.TWO_DIGITS;
    }

    private static int chooseCode(CharSequence value, int start, int oldCode) {
        CType cType;
        CType lookahead;
        CType lookahead2;
        CType lookahead3 = findCType(value, start);
        CType cType2 = CType.UNCODABLE;
        if (lookahead3 == cType2 || lookahead3 == (cType = CType.ONE_DIGIT)) {
            return 100;
        }
        if (oldCode == 99) {
            return 99;
        }
        if (oldCode == 100) {
            CType cType3 = CType.FNC_1;
            if (lookahead3 == cType3 || (lookahead = findCType(value, start + 2)) == cType2 || lookahead == cType) {
                return 100;
            }
            if (lookahead != cType3) {
                int index = start + 4;
                while (true) {
                    CType findCType = findCType(value, index);
                    lookahead2 = findCType;
                    if (findCType != CType.TWO_DIGITS) {
                        break;
                    }
                    index += 2;
                }
                if (lookahead2 == CType.ONE_DIGIT) {
                    return 100;
                }
                return 99;
            } else if (findCType(value, start + 3) == CType.TWO_DIGITS) {
                return 99;
            } else {
                return 100;
            }
        } else {
            if (lookahead3 == CType.FNC_1) {
                lookahead3 = findCType(value, start + 1);
            }
            if (lookahead3 == CType.TWO_DIGITS) {
                return 99;
            }
            return 100;
        }
    }
}
