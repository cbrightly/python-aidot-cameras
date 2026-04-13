package com.google.zxing.qrcode.encoder;

import com.alibaba.fastjson.asm.Opcodes;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class Encoder {
    private static final int[] ALPHANUMERIC_TABLE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1};
    static final String DEFAULT_BYTE_MODE_ENCODING = "ISO-8859-1";

    private Encoder() {
    }

    private static int calculateMaskPenalty(ByteMatrix matrix) {
        return MaskUtil.applyMaskPenaltyRule1(matrix) + MaskUtil.applyMaskPenaltyRule2(matrix) + MaskUtil.applyMaskPenaltyRule3(matrix) + MaskUtil.applyMaskPenaltyRule4(matrix);
    }

    public static QRCode encode(String content, ErrorCorrectionLevel ecLevel) {
        return encode(content, ecLevel, (Map<EncodeHintType, ?>) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x008c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.zxing.qrcode.encoder.QRCode encode(java.lang.String r16, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel r17, java.util.Map<com.google.zxing.EncodeHintType, ?> r18) {
        /*
            r0 = r16
            r1 = r17
            r2 = r18
            java.lang.String r3 = "ISO-8859-1"
            if (r2 == 0) goto L_0x0014
            com.google.zxing.EncodeHintType r4 = com.google.zxing.EncodeHintType.CHARACTER_SET
            boolean r4 = r2.containsKey(r4)
            if (r4 == 0) goto L_0x0014
            r4 = 1
            goto L_0x0015
        L_0x0014:
            r4 = 0
        L_0x0015:
            if (r4 == 0) goto L_0x0021
            com.google.zxing.EncodeHintType r5 = com.google.zxing.EncodeHintType.CHARACTER_SET
            java.lang.Object r5 = r2.get(r5)
            java.lang.String r3 = r5.toString()
        L_0x0021:
            com.google.zxing.qrcode.decoder.Mode r5 = chooseMode(r0, r3)
            com.google.zxing.common.BitArray r6 = new com.google.zxing.common.BitArray
            r6.<init>()
            com.google.zxing.qrcode.decoder.Mode r7 = com.google.zxing.qrcode.decoder.Mode.BYTE
            if (r5 != r7) goto L_0x0041
            if (r4 != 0) goto L_0x0038
            java.lang.String r8 = "ISO-8859-1"
            boolean r8 = r8.equals(r3)
            if (r8 != 0) goto L_0x0041
        L_0x0038:
            com.google.zxing.common.CharacterSetECI r8 = com.google.zxing.common.CharacterSetECI.getCharacterSetECIByName(r3)
            if (r8 == 0) goto L_0x0041
            appendECI(r8, r6)
        L_0x0041:
            appendModeInfo(r5, r6)
            com.google.zxing.common.BitArray r8 = new com.google.zxing.common.BitArray
            r8.<init>()
            appendBytes(r0, r5, r8, r3)
            if (r2 == 0) goto L_0x0079
            com.google.zxing.EncodeHintType r9 = com.google.zxing.EncodeHintType.QR_VERSION
            boolean r10 = r2.containsKey(r9)
            if (r10 == 0) goto L_0x0079
            java.lang.Object r9 = r2.get(r9)
            java.lang.String r9 = r9.toString()
            int r9 = java.lang.Integer.parseInt(r9)
            com.google.zxing.qrcode.decoder.Version r10 = com.google.zxing.qrcode.decoder.Version.getVersionForNumber(r9)
            int r11 = calculateBitsNeeded(r5, r6, r8, r10)
            boolean r12 = willFit(r11, r10, r1)
            if (r12 == 0) goto L_0x0071
            goto L_0x007d
        L_0x0071:
            com.google.zxing.WriterException r7 = new com.google.zxing.WriterException
            java.lang.String r12 = "Data too big for requested version"
            r7.<init>((java.lang.String) r12)
            throw r7
        L_0x0079:
            com.google.zxing.qrcode.decoder.Version r10 = recommendVersion(r1, r5, r6, r8)
        L_0x007d:
            com.google.zxing.common.BitArray r9 = new com.google.zxing.common.BitArray
            r9.<init>()
            r9.appendBitArray(r6)
            if (r5 != r7) goto L_0x008c
            int r7 = r8.getSizeInBytes()
            goto L_0x0090
        L_0x008c:
            int r7 = r16.length()
        L_0x0090:
            appendLengthInfo(r7, r10, r5, r9)
            r9.appendBitArray(r8)
            com.google.zxing.qrcode.decoder.Version$ECBlocks r11 = r10.getECBlocksForLevel(r1)
            int r12 = r10.getTotalCodewords()
            int r13 = r11.getTotalECCodewords()
            int r12 = r12 - r13
            terminateBits(r12, r9)
            int r13 = r10.getTotalCodewords()
            int r14 = r11.getNumBlocks()
            com.google.zxing.common.BitArray r13 = interleaveWithECBytes(r9, r13, r12, r14)
            com.google.zxing.qrcode.encoder.QRCode r14 = new com.google.zxing.qrcode.encoder.QRCode
            r14.<init>()
            r14.setECLevel(r1)
            r14.setMode(r5)
            r14.setVersion(r10)
            int r15 = r10.getDimensionForVersion()
            com.google.zxing.qrcode.encoder.ByteMatrix r0 = new com.google.zxing.qrcode.encoder.ByteMatrix
            r0.<init>(r15, r15)
            int r2 = chooseMaskPattern(r13, r1, r10, r0)
            r14.setMaskPattern(r2)
            com.google.zxing.qrcode.encoder.MatrixUtil.buildMatrix(r13, r1, r10, r2, r0)
            r14.setMatrix(r0)
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.encoder.Encoder.encode(java.lang.String, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel, java.util.Map):com.google.zxing.qrcode.encoder.QRCode");
    }

    private static Version recommendVersion(ErrorCorrectionLevel ecLevel, Mode mode, BitArray headerBits, BitArray dataBits) {
        return chooseVersion(calculateBitsNeeded(mode, headerBits, dataBits, chooseVersion(calculateBitsNeeded(mode, headerBits, dataBits, Version.getVersionForNumber(1)), ecLevel)), ecLevel);
    }

    private static int calculateBitsNeeded(Mode mode, BitArray headerBits, BitArray dataBits, Version version) {
        return headerBits.getSize() + mode.getCharacterCountBits(version) + dataBits.getSize();
    }

    static int getAlphanumericCode(int code) {
        int[] iArr = ALPHANUMERIC_TABLE;
        if (code < iArr.length) {
            return iArr[code];
        }
        return -1;
    }

    public static Mode chooseMode(String content) {
        return chooseMode(content, (String) null);
    }

    private static Mode chooseMode(String content, String encoding) {
        if ("Shift_JIS".equals(encoding) && isOnlyDoubleByteKanji(content)) {
            return Mode.KANJI;
        }
        boolean hasNumeric = false;
        boolean hasAlphanumeric = false;
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c >= '0' && c <= '9') {
                hasNumeric = true;
            } else if (getAlphanumericCode(c) == -1) {
                return Mode.BYTE;
            } else {
                hasAlphanumeric = true;
            }
        }
        if (hasAlphanumeric) {
            return Mode.ALPHANUMERIC;
        }
        if (hasNumeric) {
            return Mode.NUMERIC;
        }
        return Mode.BYTE;
    }

    private static boolean isOnlyDoubleByteKanji(String content) {
        try {
            byte[] bytes = content.getBytes("Shift_JIS");
            int length = bytes.length;
            if (length % 2 != 0) {
                return false;
            }
            for (int i = 0; i < length; i += 2) {
                int byte1 = bytes[i] & 255;
                if ((byte1 < 129 || byte1 > 159) && (byte1 < 224 || byte1 > 235)) {
                    return false;
                }
            }
            return true;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    private static int chooseMaskPattern(BitArray bits, ErrorCorrectionLevel ecLevel, Version version, ByteMatrix matrix) {
        int minPenalty = Integer.MAX_VALUE;
        int bestMaskPattern = -1;
        for (int maskPattern = 0; maskPattern < 8; maskPattern++) {
            MatrixUtil.buildMatrix(bits, ecLevel, version, maskPattern, matrix);
            int penalty = calculateMaskPenalty(matrix);
            if (penalty < minPenalty) {
                minPenalty = penalty;
                bestMaskPattern = maskPattern;
            }
        }
        return bestMaskPattern;
    }

    private static Version chooseVersion(int numInputBits, ErrorCorrectionLevel ecLevel) {
        for (int versionNum = 1; versionNum <= 40; versionNum++) {
            Version version = Version.getVersionForNumber(versionNum);
            if (willFit(numInputBits, version, ecLevel)) {
                return version;
            }
        }
        throw new WriterException("Data too big");
    }

    private static boolean willFit(int numInputBits, Version version, ErrorCorrectionLevel ecLevel) {
        return version.getTotalCodewords() - version.getECBlocksForLevel(ecLevel).getTotalECCodewords() >= (numInputBits + 7) / 8;
    }

    static void terminateBits(int numDataBytes, BitArray bits) {
        int capacity = numDataBytes * 8;
        if (bits.getSize() <= capacity) {
            for (int i = 0; i < 4 && bits.getSize() < capacity; i++) {
                bits.appendBit(false);
            }
            int numBitsInLastByte = bits.getSize() & 7;
            if (numBitsInLastByte > 0) {
                for (int i2 = numBitsInLastByte; i2 < 8; i2++) {
                    bits.appendBit(false);
                }
            }
            int numPaddingBytes = numDataBytes - bits.getSizeInBytes();
            for (int i3 = 0; i3 < numPaddingBytes; i3++) {
                bits.appendBits((i3 & 1) == 0 ? 236 : 17, 8);
            }
            if (bits.getSize() != capacity) {
                throw new WriterException("Bits size does not equal capacity");
            }
            return;
        }
        throw new WriterException("data bits cannot fit in the QR Code" + bits.getSize() + " > " + capacity);
    }

    static void getNumDataBytesAndNumECBytesForBlockID(int numTotalBytes, int numDataBytes, int numRSBlocks, int blockID, int[] numDataBytesInBlock, int[] numECBytesInBlock) {
        if (blockID < numRSBlocks) {
            int numRsBlocksInGroup2 = numTotalBytes % numRSBlocks;
            int numRsBlocksInGroup1 = numRSBlocks - numRsBlocksInGroup2;
            int numTotalBytesInGroup1 = numTotalBytes / numRSBlocks;
            int numDataBytesInGroup1 = numDataBytes / numRSBlocks;
            int numDataBytesInGroup2 = numDataBytesInGroup1 + 1;
            int numEcBytesInGroup1 = numTotalBytesInGroup1 - numDataBytesInGroup1;
            int numEcBytesInGroup2 = (numTotalBytesInGroup1 + 1) - numDataBytesInGroup2;
            if (numEcBytesInGroup1 != numEcBytesInGroup2) {
                throw new WriterException("EC bytes mismatch");
            } else if (numRSBlocks != numRsBlocksInGroup1 + numRsBlocksInGroup2) {
                throw new WriterException("RS blocks mismatch");
            } else if (numTotalBytes != ((numDataBytesInGroup1 + numEcBytesInGroup1) * numRsBlocksInGroup1) + ((numDataBytesInGroup2 + numEcBytesInGroup2) * numRsBlocksInGroup2)) {
                throw new WriterException("Total bytes mismatch");
            } else if (blockID < numRsBlocksInGroup1) {
                numDataBytesInBlock[0] = numDataBytesInGroup1;
                numECBytesInBlock[0] = numEcBytesInGroup1;
            } else {
                numDataBytesInBlock[0] = numDataBytesInGroup2;
                numECBytesInBlock[0] = numEcBytesInGroup2;
            }
        } else {
            throw new WriterException("Block ID too large");
        }
    }

    static BitArray interleaveWithECBytes(BitArray bits, int numTotalBytes, int numDataBytes, int numRSBlocks) {
        int i = numTotalBytes;
        int i2 = numDataBytes;
        int i3 = numRSBlocks;
        if (bits.getSizeInBytes() == i2) {
            Collection<BlockPair> blocks = new ArrayList<>(i3);
            int dataBytesOffset = 0;
            int maxNumDataBytes = 0;
            int maxNumEcBytes = 0;
            for (int i4 = 0; i4 < i3; i4++) {
                int[] numDataBytesInBlock = new int[1];
                int[] numEcBytesInBlock = new int[1];
                getNumDataBytesAndNumECBytesForBlockID(numTotalBytes, numDataBytes, numRSBlocks, i4, numDataBytesInBlock, numEcBytesInBlock);
                int size = numDataBytesInBlock[0];
                byte[] dataBytes = new byte[size];
                bits.toBytes(dataBytesOffset * 8, dataBytes, 0, size);
                byte[] ecBytes = generateECBytes(dataBytes, numEcBytesInBlock[0]);
                blocks.add(new BlockPair(dataBytes, ecBytes));
                maxNumDataBytes = Math.max(maxNumDataBytes, size);
                maxNumEcBytes = Math.max(maxNumEcBytes, ecBytes.length);
                dataBytesOffset += numDataBytesInBlock[0];
            }
            BitArray bitArray = bits;
            if (i2 == dataBytesOffset) {
                BitArray result = new BitArray();
                for (int i5 = 0; i5 < maxNumDataBytes; i5++) {
                    for (BlockPair block : blocks) {
                        byte[] dataBytes2 = block.getDataBytes();
                        if (i5 < dataBytes2.length) {
                            result.appendBits(dataBytes2[i5], 8);
                        }
                    }
                }
                for (int i6 = 0; i6 < maxNumEcBytes; i6++) {
                    for (BlockPair block2 : blocks) {
                        byte[] ecBytes2 = block2.getErrorCorrectionBytes();
                        if (i6 < ecBytes2.length) {
                            result.appendBits(ecBytes2[i6], 8);
                        }
                    }
                }
                if (i == result.getSizeInBytes()) {
                    return result;
                }
                throw new WriterException("Interleaving error: " + i + " and " + result.getSizeInBytes() + " differ.");
            }
            throw new WriterException("Data bytes does not match offset");
        }
        BitArray bitArray2 = bits;
        throw new WriterException("Number of bits and data bytes does not match");
    }

    static byte[] generateECBytes(byte[] dataBytes, int numEcBytesInBlock) {
        int numDataBytes = dataBytes.length;
        int[] toEncode = new int[(numDataBytes + numEcBytesInBlock)];
        for (int i = 0; i < numDataBytes; i++) {
            toEncode[i] = dataBytes[i] & 255;
        }
        new ReedSolomonEncoder(GenericGF.QR_CODE_FIELD_256).encode(toEncode, numEcBytesInBlock);
        byte[] ecBytes = new byte[numEcBytesInBlock];
        for (int i2 = 0; i2 < numEcBytesInBlock; i2++) {
            ecBytes[i2] = (byte) toEncode[numDataBytes + i2];
        }
        return ecBytes;
    }

    static void appendModeInfo(Mode mode, BitArray bits) {
        bits.appendBits(mode.getBits(), 4);
    }

    static void appendLengthInfo(int numLetters, Version version, Mode mode, BitArray bits) {
        int numBits = mode.getCharacterCountBits(version);
        if (numLetters < (1 << numBits)) {
            bits.appendBits(numLetters, numBits);
            return;
        }
        throw new WriterException(numLetters + " is bigger than " + ((1 << numBits) - 1));
    }

    /* renamed from: com.google.zxing.qrcode.encoder.Encoder$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$zxing$qrcode$decoder$Mode;

        static {
            int[] iArr = new int[Mode.values().length];
            $SwitchMap$com$google$zxing$qrcode$decoder$Mode = iArr;
            try {
                iArr[Mode.NUMERIC.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.ALPHANUMERIC.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.BYTE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.KANJI.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static void appendBytes(String content, Mode mode, BitArray bits, String encoding) {
        switch (AnonymousClass1.$SwitchMap$com$google$zxing$qrcode$decoder$Mode[mode.ordinal()]) {
            case 1:
                appendNumericBytes(content, bits);
                return;
            case 2:
                appendAlphanumericBytes(content, bits);
                return;
            case 3:
                append8BitBytes(content, bits, encoding);
                return;
            case 4:
                appendKanjiBytes(content, bits);
                return;
            default:
                throw new WriterException("Invalid mode: " + mode);
        }
    }

    static void appendNumericBytes(CharSequence content, BitArray bits) {
        int length = content.length();
        int i = 0;
        while (i < length) {
            int num1 = content.charAt(i) - '0';
            if (i + 2 < length) {
                bits.appendBits((num1 * 100) + ((content.charAt(i + 1) - '0') * 10) + (content.charAt(i + 2) - '0'), 10);
                i += 3;
            } else if (i + 1 < length) {
                bits.appendBits((num1 * 10) + (content.charAt(i + 1) - '0'), 7);
                i += 2;
            } else {
                bits.appendBits(num1, 4);
                i++;
            }
        }
    }

    static void appendAlphanumericBytes(CharSequence content, BitArray bits) {
        int length = content.length();
        int i = 0;
        while (i < length) {
            int code1 = getAlphanumericCode(content.charAt(i));
            if (code1 == -1) {
                throw new WriterException();
            } else if (i + 1 < length) {
                int code2 = getAlphanumericCode(content.charAt(i + 1));
                if (code2 != -1) {
                    bits.appendBits((code1 * 45) + code2, 11);
                    i += 2;
                } else {
                    throw new WriterException();
                }
            } else {
                bits.appendBits(code1, 6);
                i++;
            }
        }
    }

    static void append8BitBytes(String content, BitArray bits, String encoding) {
        try {
            for (byte b : content.getBytes(encoding)) {
                bits.appendBits(b, 8);
            }
        } catch (UnsupportedEncodingException uee) {
            throw new WriterException((Throwable) uee);
        }
    }

    static void appendKanjiBytes(String content, BitArray bits) {
        try {
            byte[] bytes = content.getBytes("Shift_JIS");
            int length = bytes.length;
            int i = 0;
            while (i < length) {
                int code = ((bytes[i] & 255) << 8) | (bytes[i + 1] & 255);
                int subtracted = -1;
                if (code >= 33088 && code <= 40956) {
                    subtracted = code - 33088;
                } else if (code >= 57408 && code <= 60351) {
                    subtracted = code - 49472;
                }
                if (subtracted != -1) {
                    bits.appendBits(((subtracted >> 8) * Opcodes.CHECKCAST) + (subtracted & 255), 13);
                    i += 2;
                } else {
                    throw new WriterException("Invalid byte sequence");
                }
            }
        } catch (UnsupportedEncodingException uee) {
            throw new WriterException((Throwable) uee);
        }
    }

    private static void appendECI(CharacterSetECI eci, BitArray bits) {
        bits.appendBits(Mode.ECI.getBits(), 4);
        bits.appendBits(eci.getValue(), 8);
    }
}
