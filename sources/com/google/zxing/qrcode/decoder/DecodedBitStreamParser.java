package com.google.zxing.qrcode.decoder;

import com.alibaba.fastjson.asm.Opcodes;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.StringUtils;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Map;

public final class DecodedBitStreamParser {
    private static final char[] ALPHANUMERIC_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ $%*+-./:".toCharArray();
    private static final int GB2312_SUBSET = 1;

    private DecodedBitStreamParser() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00bb, code lost:
        r10 = r2;
        r11 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00bf, code lost:
        if (r6 != com.google.zxing.qrcode.decoder.Mode.TERMINATOR) goto L_0x00e6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00c1, code lost:
        r4 = r7.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00cd, code lost:
        if (r14.isEmpty() == false) goto L_0x00d1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00cf, code lost:
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00d1, code lost:
        r5 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00d2, code lost:
        if (r18 != null) goto L_0x00d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00d4, code lost:
        r6 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00d6, code lost:
        r6 = r18.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00db, code lost:
        r12 = r7;
        r13 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00e5, code lost:
        return new com.google.zxing.common.DecoderResult(r16, r4, r5, r6, r10, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00ee, code lost:
        r12 = r7;
        r13 = r8;
        r2 = r10;
        r3 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00fd, code lost:
        throw com.google.zxing.FormatException.getFormatInstance();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.google.zxing.common.DecoderResult decode(byte[] r16, com.google.zxing.qrcode.decoder.Version r17, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel r18, java.util.Map<com.google.zxing.DecodeHintType, ?> r19) {
        /*
            r1 = r17
            com.google.zxing.common.BitSource r0 = new com.google.zxing.common.BitSource
            r9 = r16
            r0.<init>(r9)
            r8 = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r2 = 50
            r0.<init>(r2)
            r7 = r0
            java.util.ArrayList r14 = new java.util.ArrayList
            r0 = 1
            r14.<init>(r0)
            r2 = -1
            r3 = -1
            r4 = 0
            r5 = 0
        L_0x001c:
            int r6 = r8.available()     // Catch:{ IllegalArgumentException -> 0x00f6 }
            r10 = 4
            if (r6 >= r10) goto L_0x002b
            com.google.zxing.qrcode.decoder.Mode r6 = com.google.zxing.qrcode.decoder.Mode.TERMINATOR     // Catch:{ IllegalArgumentException -> 0x0026 }
            goto L_0x0033
        L_0x0026:
            r0 = move-exception
            r12 = r7
            r13 = r8
            goto L_0x00f9
        L_0x002b:
            int r6 = r8.readBits(r10)     // Catch:{ IllegalArgumentException -> 0x00f6 }
            com.google.zxing.qrcode.decoder.Mode r6 = com.google.zxing.qrcode.decoder.Mode.forBits(r6)     // Catch:{ IllegalArgumentException -> 0x00f6 }
        L_0x0033:
            int[] r11 = com.google.zxing.qrcode.decoder.DecodedBitStreamParser.AnonymousClass1.$SwitchMap$com$google$zxing$qrcode$decoder$Mode     // Catch:{ IllegalArgumentException -> 0x00f6 }
            int r12 = r6.ordinal()     // Catch:{ IllegalArgumentException -> 0x00f6 }
            r12 = r11[r12]     // Catch:{ IllegalArgumentException -> 0x00f6 }
            switch(r12) {
                case 5: goto L_0x008a;
                case 6: goto L_0x0086;
                case 7: goto L_0x0086;
                case 8: goto L_0x006a;
                case 9: goto L_0x0056;
                case 10: goto L_0x0043;
                default: goto L_0x003e;
            }     // Catch:{ IllegalArgumentException -> 0x00f6 }
        L_0x003e:
            int r10 = r6.getCharacterCountBits(r1)     // Catch:{ IllegalArgumentException -> 0x00f6 }
            goto L_0x008b
        L_0x0043:
            int r10 = r8.readBits(r10)     // Catch:{ IllegalArgumentException -> 0x0026 }
            int r11 = r6.getCharacterCountBits(r1)     // Catch:{ IllegalArgumentException -> 0x0026 }
            int r11 = r8.readBits(r11)     // Catch:{ IllegalArgumentException -> 0x0026 }
            if (r10 != r0) goto L_0x00bb
            decodeHanziSegment(r8, r7, r11)     // Catch:{ IllegalArgumentException -> 0x0026 }
            goto L_0x00bb
        L_0x0056:
            int r10 = parseECIValue(r8)     // Catch:{ IllegalArgumentException -> 0x0026 }
            com.google.zxing.common.CharacterSetECI r11 = com.google.zxing.common.CharacterSetECI.getCharacterSetECIByValue(r10)     // Catch:{ IllegalArgumentException -> 0x0026 }
            r4 = r11
            if (r4 == 0) goto L_0x0065
            r10 = r2
            r11 = r3
            goto L_0x00bd
        L_0x0065:
            com.google.zxing.FormatException r0 = com.google.zxing.FormatException.getFormatInstance()     // Catch:{ IllegalArgumentException -> 0x0026 }
            throw r0     // Catch:{ IllegalArgumentException -> 0x0026 }
        L_0x006a:
            int r10 = r8.available()     // Catch:{ IllegalArgumentException -> 0x0026 }
            r11 = 16
            if (r10 < r11) goto L_0x0081
            r10 = 8
            int r11 = r8.readBits(r10)     // Catch:{ IllegalArgumentException -> 0x0026 }
            r2 = r11
            int r10 = r8.readBits(r10)     // Catch:{ IllegalArgumentException -> 0x0026 }
            r3 = r10
            r10 = r2
            r11 = r3
            goto L_0x00bd
        L_0x0081:
            com.google.zxing.FormatException r0 = com.google.zxing.FormatException.getFormatInstance()     // Catch:{ IllegalArgumentException -> 0x0026 }
            throw r0     // Catch:{ IllegalArgumentException -> 0x0026 }
        L_0x0086:
            r5 = 1
            r10 = r2
            r11 = r3
            goto L_0x00bd
        L_0x008a:
            goto L_0x00bb
        L_0x008b:
            int r10 = r8.readBits(r10)     // Catch:{ IllegalArgumentException -> 0x00f6 }
            r15 = r10
            int r10 = r6.ordinal()     // Catch:{ IllegalArgumentException -> 0x00f6 }
            r10 = r11[r10]     // Catch:{ IllegalArgumentException -> 0x00f6 }
            switch(r10) {
                case 1: goto L_0x00b6;
                case 2: goto L_0x00b1;
                case 3: goto L_0x00a6;
                case 4: goto L_0x00a2;
                default: goto L_0x0099;
            }
        L_0x0099:
            r12 = r7
            r13 = r8
            r0 = r15
            com.google.zxing.FormatException r7 = com.google.zxing.FormatException.getFormatInstance()     // Catch:{ IllegalArgumentException -> 0x00f4 }
            goto L_0x00f3
        L_0x00a2:
            decodeKanjiSegment(r8, r7, r15)     // Catch:{ IllegalArgumentException -> 0x0026 }
            goto L_0x00bb
        L_0x00a6:
            r10 = r8
            r11 = r7
            r12 = r15
            r13 = r4
            r0 = r15
            r15 = r19
            decodeByteSegment(r10, r11, r12, r13, r14, r15)     // Catch:{ IllegalArgumentException -> 0x0026 }
            goto L_0x00bb
        L_0x00b1:
            r0 = r15
            decodeAlphanumericSegment(r8, r7, r0, r5)     // Catch:{ IllegalArgumentException -> 0x0026 }
            goto L_0x00bb
        L_0x00b6:
            r0 = r15
            decodeNumericSegment(r8, r7, r0)     // Catch:{ IllegalArgumentException -> 0x00f6 }
        L_0x00bb:
            r10 = r2
            r11 = r3
        L_0x00bd:
            com.google.zxing.qrcode.decoder.Mode r0 = com.google.zxing.qrcode.decoder.Mode.TERMINATOR     // Catch:{ IllegalArgumentException -> 0x00ed }
            if (r6 != r0) goto L_0x00e6
            com.google.zxing.common.DecoderResult r0 = new com.google.zxing.common.DecoderResult
            java.lang.String r4 = r7.toString()
            boolean r2 = r14.isEmpty()
            r3 = 0
            if (r2 == 0) goto L_0x00d1
            r5 = r3
            goto L_0x00d2
        L_0x00d1:
            r5 = r14
        L_0x00d2:
            if (r18 != 0) goto L_0x00d6
            r6 = r3
            goto L_0x00db
        L_0x00d6:
            java.lang.String r2 = r18.toString()
            r6 = r2
        L_0x00db:
            r2 = r0
            r3 = r16
            r12 = r7
            r7 = r10
            r13 = r8
            r8 = r11
            r2.<init>(r3, r4, r5, r6, r7, r8)
            return r0
        L_0x00e6:
            r12 = r7
            r13 = r8
            r2 = r10
            r3 = r11
            r0 = 1
            goto L_0x001c
        L_0x00ed:
            r0 = move-exception
            r12 = r7
            r13 = r8
            r2 = r10
            r3 = r11
            goto L_0x00f9
        L_0x00f3:
            throw r7     // Catch:{ IllegalArgumentException -> 0x00f4 }
        L_0x00f4:
            r0 = move-exception
            goto L_0x00f9
        L_0x00f6:
            r0 = move-exception
            r12 = r7
            r13 = r8
        L_0x00f9:
            com.google.zxing.FormatException r4 = com.google.zxing.FormatException.getFormatInstance()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.decoder.DecodedBitStreamParser.decode(byte[], com.google.zxing.qrcode.decoder.Version, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel, java.util.Map):com.google.zxing.common.DecoderResult");
    }

    /* renamed from: com.google.zxing.qrcode.decoder.DecodedBitStreamParser$1  reason: invalid class name */
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
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.TERMINATOR.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.FNC1_FIRST_POSITION.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.FNC1_SECOND_POSITION.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.STRUCTURED_APPEND.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.ECI.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.HANZI.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    private static void decodeHanziSegment(BitSource bits, StringBuilder result, int count) {
        int i;
        if (count * 13 <= bits.available()) {
            byte[] buffer = new byte[(count * 2)];
            int offset = 0;
            while (count > 0) {
                int twoBytes = bits.readBits(13);
                int assembledTwoBytes = ((twoBytes / 96) << 8) | (twoBytes % 96);
                if (assembledTwoBytes < 959) {
                    i = 41377;
                } else {
                    i = 42657;
                }
                int assembledTwoBytes2 = assembledTwoBytes + i;
                buffer[offset] = (byte) ((assembledTwoBytes2 >> 8) & 255);
                buffer[offset + 1] = (byte) (assembledTwoBytes2 & 255);
                offset += 2;
                count--;
            }
            try {
                result.append(new String(buffer, StringUtils.GB2312));
            } catch (UnsupportedEncodingException e) {
                throw FormatException.getFormatInstance();
            }
        } else {
            throw FormatException.getFormatInstance();
        }
    }

    private static void decodeKanjiSegment(BitSource bits, StringBuilder result, int count) {
        int i;
        if (count * 13 <= bits.available()) {
            byte[] buffer = new byte[(count * 2)];
            int offset = 0;
            while (count > 0) {
                int twoBytes = bits.readBits(13);
                int assembledTwoBytes = ((twoBytes / Opcodes.CHECKCAST) << 8) | (twoBytes % Opcodes.CHECKCAST);
                if (assembledTwoBytes < 7936) {
                    i = 33088;
                } else {
                    i = 49472;
                }
                int assembledTwoBytes2 = assembledTwoBytes + i;
                buffer[offset] = (byte) (assembledTwoBytes2 >> 8);
                buffer[offset + 1] = (byte) assembledTwoBytes2;
                offset += 2;
                count--;
            }
            try {
                result.append(new String(buffer, StringUtils.SHIFT_JIS));
            } catch (UnsupportedEncodingException e) {
                throw FormatException.getFormatInstance();
            }
        } else {
            throw FormatException.getFormatInstance();
        }
    }

    private static void decodeByteSegment(BitSource bits, StringBuilder result, int count, CharacterSetECI currentCharacterSetECI, Collection<byte[]> byteSegments, Map<DecodeHintType, ?> hints) {
        String encoding;
        if (count * 8 <= bits.available()) {
            byte[] readBytes = new byte[count];
            for (int i = 0; i < count; i++) {
                readBytes[i] = (byte) bits.readBits(8);
            }
            if (currentCharacterSetECI == null) {
                encoding = StringUtils.guessEncoding(readBytes, hints);
            } else {
                encoding = currentCharacterSetECI.name();
            }
            try {
                result.append(new String(readBytes, encoding));
                byteSegments.add(readBytes);
            } catch (UnsupportedEncodingException e) {
                throw FormatException.getFormatInstance();
            }
        } else {
            throw FormatException.getFormatInstance();
        }
    }

    private static char toAlphaNumericChar(int value) {
        char[] cArr = ALPHANUMERIC_CHARS;
        if (value < cArr.length) {
            return cArr[value];
        }
        throw FormatException.getFormatInstance();
    }

    private static void decodeAlphanumericSegment(BitSource bits, StringBuilder result, int count, boolean fc1InEffect) {
        int start = result.length();
        while (count > 1) {
            if (bits.available() >= 11) {
                int nextTwoCharsBits = bits.readBits(11);
                result.append(toAlphaNumericChar(nextTwoCharsBits / 45));
                result.append(toAlphaNumericChar(nextTwoCharsBits % 45));
                count -= 2;
            } else {
                throw FormatException.getFormatInstance();
            }
        }
        if (count == 1) {
            if (bits.available() >= 6) {
                result.append(toAlphaNumericChar(bits.readBits(6)));
            } else {
                throw FormatException.getFormatInstance();
            }
        }
        if (fc1InEffect) {
            for (int i = start; i < result.length(); i++) {
                if (result.charAt(i) == '%') {
                    if (i >= result.length() - 1 || result.charAt(i + 1) != '%') {
                        result.setCharAt(i, 29);
                    } else {
                        result.deleteCharAt(i + 1);
                    }
                }
            }
        }
    }

    private static void decodeNumericSegment(BitSource bits, StringBuilder result, int count) {
        while (count >= 3) {
            if (bits.available() >= 10) {
                int threeDigitsBits = bits.readBits(10);
                if (threeDigitsBits < 1000) {
                    result.append(toAlphaNumericChar(threeDigitsBits / 100));
                    result.append(toAlphaNumericChar((threeDigitsBits / 10) % 10));
                    result.append(toAlphaNumericChar(threeDigitsBits % 10));
                    count -= 3;
                } else {
                    throw FormatException.getFormatInstance();
                }
            } else {
                throw FormatException.getFormatInstance();
            }
        }
        if (count == 2) {
            if (bits.available() >= 7) {
                int twoDigitsBits = bits.readBits(7);
                if (twoDigitsBits < 100) {
                    result.append(toAlphaNumericChar(twoDigitsBits / 10));
                    result.append(toAlphaNumericChar(twoDigitsBits % 10));
                    return;
                }
                throw FormatException.getFormatInstance();
            }
            throw FormatException.getFormatInstance();
        } else if (count != 1) {
        } else {
            if (bits.available() >= 4) {
                int digitBits = bits.readBits(4);
                if (digitBits < 10) {
                    result.append(toAlphaNumericChar(digitBits));
                    return;
                }
                throw FormatException.getFormatInstance();
            }
            throw FormatException.getFormatInstance();
        }
    }

    private static int parseECIValue(BitSource bits) {
        int firstByte = bits.readBits(8);
        if ((firstByte & 128) == 0) {
            return firstByte & NeedPermissionEvent.PER_IPC_SPEAK_PERM;
        }
        if ((firstByte & Opcodes.CHECKCAST) == 128) {
            return ((firstByte & 63) << 8) | bits.readBits(8);
        } else if ((firstByte & 224) == 192) {
            return ((firstByte & 31) << 16) | bits.readBits(16);
        } else {
            throw FormatException.getFormatInstance();
        }
    }
}
