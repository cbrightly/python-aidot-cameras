package org.glassfish.tyrus.core;

import com.alibaba.fastjson.asm.Opcodes;
import com.leedarson.serviceimpl.business.bean.OTACommand;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

public class StrictUtf8 extends Charset {
    public StrictUtf8() {
        super("StrictUtf8", new String[0]);
    }

    public CharsetDecoder newDecoder() {
        return new Decoder(this);
    }

    public CharsetEncoder newEncoder() {
        return new Encoder(this);
    }

    public boolean contains(Charset cs) {
        return "StrictUtf8".equals(cs.name());
    }

    /* access modifiers changed from: private */
    public static boolean isSurrogate(char ch) {
        return ch >= 55296 && ch < 57344;
    }

    /* access modifiers changed from: private */
    public static char highSurrogate(int codePoint) {
        return (char) ((codePoint >>> 10) + 55232);
    }

    /* access modifiers changed from: private */
    public static char lowSurrogate(int codePoint) {
        return (char) ((codePoint & 1023) + 56320);
    }

    /* access modifiers changed from: private */
    public static void updatePositions(ByteBuffer src, int sp, CharBuffer dst, int dp) {
        src.position(sp - src.arrayOffset());
        dst.position(dp - dst.arrayOffset());
    }

    /* access modifiers changed from: private */
    public static void updatePositions(CharBuffer src, int sp, ByteBuffer dst, int dp) {
        src.position(sp - src.arrayOffset());
        dst.position(dp - dst.arrayOffset());
    }

    public static class Decoder extends CharsetDecoder {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class<StrictUtf8> cls = StrictUtf8.class;
        }

        private Decoder(Charset cs) {
            super(cs, 1.0f, 1.0f);
        }

        private static boolean isNotContinuation(int b) {
            return (b & Opcodes.CHECKCAST) != 128;
        }

        private static boolean isMalformed3(int b1, int b2, int b3) {
            return ((b1 != -32 || (b2 & 224) != 128) && (b2 & Opcodes.CHECKCAST) == 128 && (b3 & Opcodes.CHECKCAST) == 128) ? false : true;
        }

        private static boolean isMalformed3_2(int b1, int b2) {
            return (b1 == -32 && (b2 & 224) == 128) || (b2 & Opcodes.CHECKCAST) != 128;
        }

        private static boolean isMalformed4(int b2, int b3, int b4) {
            return ((b2 & Opcodes.CHECKCAST) == 128 && (b3 & Opcodes.CHECKCAST) == 128 && (b4 & Opcodes.CHECKCAST) == 128) ? false : true;
        }

        private static boolean isMalformed4_2(int b1, int b2) {
            return (b1 == 240 && b2 == 144) || (b2 & Opcodes.CHECKCAST) != 128;
        }

        private static boolean isMalformed4_3(int b3) {
            return (b3 & Opcodes.CHECKCAST) != 128;
        }

        private static CoderResult malformedN(ByteBuffer src, int nb) {
            int i = 2;
            switch (nb) {
                case 1:
                case 2:
                    return CoderResult.malformedForLength(1);
                case 3:
                    int b1 = src.get();
                    int b2 = src.get();
                    if ((b1 == -32 && (b2 & 224) == 128) || isNotContinuation(b2)) {
                        i = 1;
                    }
                    return CoderResult.malformedForLength(i);
                case 4:
                    int b12 = src.get() & 255;
                    int b22 = src.get() & 255;
                    if (b12 > 244 || ((b12 == 240 && (b22 < 144 || b22 > 191)) || ((b12 == 244 && (b22 & 240) != 128) || isNotContinuation(b22)))) {
                        return CoderResult.malformedForLength(1);
                    }
                    if (isNotContinuation(src.get())) {
                        return CoderResult.malformedForLength(2);
                    }
                    return CoderResult.malformedForLength(3);
                default:
                    throw new AssertionError();
            }
        }

        private static CoderResult malformed(ByteBuffer src, int sp, CharBuffer dst, int dp, int nb) {
            src.position(sp - src.arrayOffset());
            CoderResult cr = malformedN(src, nb);
            StrictUtf8.updatePositions(src, sp, dst, dp);
            return cr;
        }

        private static CoderResult malformed(ByteBuffer src, int mark, int nb) {
            src.position(mark);
            CoderResult cr = malformedN(src, nb);
            src.position(mark);
            return cr;
        }

        private static CoderResult malformedForLength(ByteBuffer src, int sp, CharBuffer dst, int dp, int malformedNB) {
            StrictUtf8.updatePositions(src, sp, dst, dp);
            return CoderResult.malformedForLength(malformedNB);
        }

        private static CoderResult malformedForLength(ByteBuffer src, int mark, int malformedNB) {
            src.position(mark);
            return CoderResult.malformedForLength(malformedNB);
        }

        private static CoderResult xflow(ByteBuffer src, int sp, int sl, CharBuffer dst, int dp, int nb) {
            StrictUtf8.updatePositions(src, sp, dst, dp);
            return (nb == 0 || sl - sp < nb) ? CoderResult.UNDERFLOW : CoderResult.OVERFLOW;
        }

        private static CoderResult xflow(Buffer src, int mark, int nb) {
            src.position(mark);
            return (nb == 0 || src.remaining() < nb) ? CoderResult.UNDERFLOW : CoderResult.OVERFLOW;
        }

        private CoderResult decodeArrayLoop(ByteBuffer src, CharBuffer dst) {
            ByteBuffer byteBuffer = src;
            CharBuffer charBuffer = dst;
            byte[] sa = src.array();
            int sp = src.arrayOffset() + src.position();
            int sl = src.arrayOffset() + src.limit();
            char[] da = dst.array();
            int dp = dst.arrayOffset() + dst.position();
            int dl = dst.arrayOffset() + dst.limit();
            int dlASCII = dp + Math.min(sl - sp, dl - dp);
            while (dp < dlASCII && sa[sp] >= 0) {
                da[dp] = (char) sa[sp];
                dp++;
                sp++;
            }
            int sp2 = sp;
            int dp2 = dp;
            while (sp2 < sl) {
                byte b1 = sa[sp2];
                if (b1 >= 0) {
                    if (dp2 >= dl) {
                        return xflow(src, sp2, sl, dst, dp2, 1);
                    }
                    da[dp2] = (char) b1;
                    sp2++;
                    dp2++;
                } else if ((b1 >> 5) != -2 || (b1 & 30) == 0) {
                    if ((b1 >> 4) == -2) {
                        int srcRemaining = sl - sp2;
                        if (srcRemaining >= 3 && dp2 < dl) {
                            byte b2 = sa[sp2 + 1];
                            byte b3 = sa[sp2 + 2];
                            if (isMalformed3(b1, b2, b3)) {
                                return malformed(byteBuffer, sp2, charBuffer, dp2, 3);
                            }
                            char c = (char) (((b1 << 12) ^ (b2 << 6)) ^ (-123008 ^ b3));
                            if (StrictUtf8.isSurrogate(c)) {
                                return malformedForLength(byteBuffer, sp2, charBuffer, dp2, 3);
                            }
                            da[dp2] = c;
                            sp2 += 3;
                            dp2++;
                        } else if (srcRemaining > 1 && isMalformed3_2(b1, sa[sp2 + 1])) {
                            return malformedForLength(byteBuffer, sp2, charBuffer, dp2, 1);
                        } else {
                            int i = srcRemaining;
                            return xflow(src, sp2, sl, dst, dp2, 3);
                        }
                    } else if ((b1 >> 3) != -2) {
                        return malformed(byteBuffer, sp2, charBuffer, dp2, 1);
                    } else {
                        int srcRemaining2 = sl - sp2;
                        if (srcRemaining2 >= 4 && dl - dp2 >= 2) {
                            byte b22 = sa[sp2 + 1];
                            byte b32 = sa[sp2 + 2];
                            byte b4 = sa[sp2 + 3];
                            int uc = (((b1 << 18) ^ (b22 << 12)) ^ (b32 << 6)) ^ (b4 ^ 3678080);
                            if (isMalformed4(b22, b32, b4) || !Character.isSupplementaryCodePoint(uc)) {
                                return malformed(byteBuffer, sp2, charBuffer, dp2, 4);
                            }
                            int dp3 = dp2 + 1;
                            da[dp2] = StrictUtf8.highSurrogate(uc);
                            dp2 = dp3 + 1;
                            da[dp3] = StrictUtf8.lowSurrogate(uc);
                            sp2 += 4;
                        } else if (srcRemaining2 > 1 && isMalformed4_2(b1, sa[sp2 + 1])) {
                            return malformedForLength(byteBuffer, sp2, charBuffer, dp2, 1);
                        } else {
                            if (srcRemaining2 > 2 && isMalformed4_3(sa[sp2 + 2])) {
                                return malformedForLength(byteBuffer, sp2, charBuffer, dp2, 2);
                            }
                            int i2 = srcRemaining2;
                            return xflow(src, sp2, sl, dst, dp2, 4);
                        }
                    }
                } else if (sl - sp2 < 2 || dp2 >= dl) {
                    return xflow(src, sp2, sl, dst, dp2, 2);
                } else {
                    byte b23 = sa[sp2 + 1];
                    if (isNotContinuation(b23)) {
                        return malformedForLength(byteBuffer, sp2, charBuffer, dp2, 1);
                    }
                    da[dp2] = (char) (((b1 << 6) ^ b23) ^ OTACommand.RESP_ID_VERSION);
                    sp2 += 2;
                    dp2++;
                }
            }
            return xflow(src, sp2, sl, dst, dp2, 0);
        }

        private CoderResult decodeBufferLoop(ByteBuffer src, CharBuffer dst) {
            int mark = src.position();
            int limit = src.limit();
            while (mark < limit) {
                int b1 = src.get();
                if (b1 >= 0) {
                    if (dst.remaining() < 1) {
                        return xflow(src, mark, 1);
                    }
                    dst.put((char) b1);
                    mark++;
                } else if ((b1 >> 5) != -2 || (b1 & 30) == 0) {
                    if ((b1 >> 4) == -2) {
                        int srcRemaining = limit - mark;
                        if (srcRemaining >= 3 && dst.remaining() >= 1) {
                            int b2 = src.get();
                            int b3 = src.get();
                            if (isMalformed3(b1, b2, b3)) {
                                return malformed(src, mark, 3);
                            }
                            char c = (char) (((b1 << 12) ^ (b2 << 6)) ^ (-123008 ^ b3));
                            if (StrictUtf8.isSurrogate(c)) {
                                return malformedForLength(src, mark, 3);
                            }
                            dst.put(c);
                            mark += 3;
                        } else if (srcRemaining <= 1 || !isMalformed3_2(b1, src.get())) {
                            return xflow(src, mark, 3);
                        } else {
                            return malformedForLength(src, mark, 1);
                        }
                    } else if ((b1 >> 3) != -2) {
                        return malformed(src, mark, 1);
                    } else {
                        int srcRemaining2 = limit - mark;
                        if (srcRemaining2 >= 4 && dst.remaining() >= 2) {
                            int b22 = src.get();
                            int b32 = src.get();
                            int b4 = src.get();
                            int uc = (((b1 << 18) ^ (b22 << 12)) ^ (b32 << 6)) ^ (3678080 ^ b4);
                            if (isMalformed4(b22, b32, b4) || !Character.isSupplementaryCodePoint(uc)) {
                                return malformed(src, mark, 4);
                            }
                            dst.put(StrictUtf8.highSurrogate(uc));
                            dst.put(StrictUtf8.lowSurrogate(uc));
                            mark += 4;
                        } else if (srcRemaining2 > 1 && isMalformed4_2(b1, src.get())) {
                            return malformedForLength(src, mark, 1);
                        } else {
                            if (srcRemaining2 <= 2 || !isMalformed4_3(src.get())) {
                                return xflow(src, mark, 4);
                            }
                            return malformedForLength(src, mark, 2);
                        }
                    }
                } else if (limit - mark < 2 || dst.remaining() < 1) {
                    return xflow(src, mark, 2);
                } else {
                    int b23 = src.get();
                    if (isNotContinuation(b23)) {
                        return malformedForLength(src, mark, 1);
                    }
                    dst.put((char) (((b1 << 6) ^ b23) ^ 3968));
                    mark += 2;
                }
            }
            return xflow(src, mark, 0);
        }

        /* access modifiers changed from: protected */
        public CoderResult decodeLoop(ByteBuffer src, CharBuffer dst) {
            if (!src.hasArray() || !dst.hasArray()) {
                return decodeBufferLoop(src, dst);
            }
            return decodeArrayLoop(src, dst);
        }

        private static ByteBuffer getByteBuffer(ByteBuffer bb, byte[] ba, int sp) {
            if (bb == null) {
                bb = ByteBuffer.wrap(ba);
            }
            bb.position(sp);
            return bb;
        }

        public int decode(byte[] sa, int sp, int len, char[] da) {
            int dp;
            int dp2;
            int dp3;
            int dp4;
            byte[] bArr = sa;
            int i = len;
            char[] cArr = da;
            int sl = sp + i;
            int dlASCII = Math.min(i, cArr.length);
            ByteBuffer bb = null;
            int dp5 = 0;
            int dp6 = sp;
            while (dp < dlASCII && bArr[dp2] >= 0) {
                cArr[dp] = (char) bArr[dp2];
                dp5 = dp + 1;
                dp6 = dp2 + 1;
            }
            while (dp2 < sl) {
                int sp2 = dp2 + 1;
                byte sp3 = bArr[dp2];
                if (sp3 >= 0) {
                    dp3 = dp + 1;
                    cArr[dp] = (char) sp3;
                } else if ((sp3 >> 5) != -2 || (sp3 & 30) == 0) {
                    if ((sp3 >> 4) == -2) {
                        if (sp2 + 1 < sl) {
                            int sp4 = sp2 + 1;
                            byte sp5 = bArr[sp2];
                            int sp6 = sp4 + 1;
                            byte b3 = bArr[sp4];
                            if (!isMalformed3(sp3, sp5, b3)) {
                                char c = (char) (((sp3 << 12) ^ (sp5 << 6)) ^ (-123008 ^ b3));
                                if (!StrictUtf8.isSurrogate(c)) {
                                    dp4 = dp + 1;
                                    cArr[dp] = c;
                                } else if (malformedInputAction() != CodingErrorAction.REPLACE) {
                                    return -1;
                                } else {
                                    dp4 = dp + 1;
                                    cArr[dp] = replacement().charAt(0);
                                }
                                dp = dp4;
                                dp2 = sp6;
                            } else if (malformedInputAction() != CodingErrorAction.REPLACE) {
                                return -1;
                            } else {
                                cArr[dp] = replacement().charAt(0);
                                int sp7 = sp6 - 3;
                                bb = getByteBuffer(bb, bArr, sp7);
                                dp2 = malformedN(bb, 3).length() + sp7;
                                dp++;
                            }
                        } else if (malformedInputAction() != CodingErrorAction.REPLACE) {
                            return -1;
                        } else {
                            if (sp2 >= sl || !isMalformed3_2(sp3, bArr[sp2])) {
                                int dp7 = dp + 1;
                                cArr[dp] = replacement().charAt(0);
                                return dp7;
                            }
                            cArr[dp] = replacement().charAt(0);
                            dp2 = sp2;
                            dp++;
                        }
                    } else if ((sp3 >> 3) == -2) {
                        if (sp2 + 2 < sl) {
                            int sp8 = sp2 + 1;
                            byte sp9 = bArr[sp2];
                            int sp10 = sp8 + 1;
                            byte b32 = bArr[sp8];
                            int sp11 = sp10 + 1;
                            byte b4 = bArr[sp10];
                            int uc = (((sp3 << 18) ^ (sp9 << 12)) ^ (b32 << 6)) ^ (3678080 ^ b4);
                            if (!isMalformed4(sp9, b32, b4) && Character.isSupplementaryCodePoint(uc)) {
                                int dp8 = dp + 1;
                                cArr[dp] = StrictUtf8.highSurrogate(uc);
                                dp = dp8 + 1;
                                cArr[dp8] = StrictUtf8.lowSurrogate(uc);
                                dp2 = sp11;
                            } else if (malformedInputAction() != CodingErrorAction.REPLACE) {
                                return -1;
                            } else {
                                cArr[dp] = replacement().charAt(0);
                                int sp12 = sp11 - 4;
                                bb = getByteBuffer(bb, bArr, sp12);
                                dp2 = malformedN(bb, 4).length() + sp12;
                                dp++;
                            }
                        } else if (malformedInputAction() != CodingErrorAction.REPLACE) {
                            return -1;
                        } else {
                            if (sp2 >= sl || !isMalformed4_2(sp3, bArr[sp2])) {
                                int sp13 = sp2 + 1;
                                if (sp13 >= sl || !isMalformed4_3(bArr[sp13])) {
                                    int dp9 = dp + 1;
                                    cArr[dp] = replacement().charAt(0);
                                    return dp9;
                                }
                                cArr[dp] = replacement().charAt(0);
                                dp2 = sp13;
                                dp++;
                            } else {
                                cArr[dp] = replacement().charAt(0);
                                dp2 = sp2;
                                dp++;
                            }
                        }
                    } else if (malformedInputAction() != CodingErrorAction.REPLACE) {
                        return -1;
                    } else {
                        dp3 = dp + 1;
                        cArr[dp] = replacement().charAt(0);
                    }
                } else if (sp2 < sl) {
                    int sp14 = sp2 + 1;
                    byte sp15 = bArr[sp2];
                    if (!isNotContinuation(sp15)) {
                        cArr[dp] = (char) (((sp3 << 6) ^ sp15) ^ OTACommand.RESP_ID_VERSION);
                        dp2 = sp14;
                        dp++;
                    } else if (malformedInputAction() != CodingErrorAction.REPLACE) {
                        return -1;
                    } else {
                        cArr[dp] = replacement().charAt(0);
                        dp2 = sp14 - 1;
                        dp++;
                    }
                } else if (malformedInputAction() != CodingErrorAction.REPLACE) {
                    return -1;
                } else {
                    int dp10 = dp + 1;
                    cArr[dp] = replacement().charAt(0);
                    return dp10;
                }
                dp = dp3;
                dp2 = sp2;
            }
            return dp;
        }
    }

    public static final class Encoder extends CharsetEncoder {
        private Parser sgp;

        private Encoder(Charset cs) {
            super(cs, 1.1f, 3.0f);
        }

        public boolean canEncode(char c) {
            return !StrictUtf8.isSurrogate(c);
        }

        public boolean isLegalReplacement(byte[] repl) {
            return (repl.length == 1 && repl[0] >= 0) || super.isLegalReplacement(repl);
        }

        private static CoderResult overflow(CharBuffer src, int sp, ByteBuffer dst, int dp) {
            StrictUtf8.updatePositions(src, sp, dst, dp);
            return CoderResult.OVERFLOW;
        }

        private static CoderResult overflow(CharBuffer src, int mark) {
            src.position(mark);
            return CoderResult.OVERFLOW;
        }

        private CoderResult encodeArrayLoop(CharBuffer src, ByteBuffer dst) {
            int dp;
            char[] sa = src.array();
            int sp = src.arrayOffset() + src.position();
            int sl = src.arrayOffset() + src.limit();
            byte[] da = dst.array();
            int dp2 = dst.arrayOffset() + dst.position();
            int dl = dst.arrayOffset() + dst.limit();
            int dlASCII = Math.min(sl - sp, dl - dp2) + dp2;
            while (dp < dlASCII && sa[sp] < 128) {
                da[dp] = (byte) sa[sp];
                dp2 = dp + 1;
                sp++;
            }
            while (sp < sl) {
                char c = sa[sp];
                if (c < 128) {
                    if (dp >= dl) {
                        return overflow(src, sp, dst, dp);
                    }
                    da[dp] = (byte) c;
                    dp++;
                } else if (c < 2048) {
                    if (dl - dp < 2) {
                        return overflow(src, sp, dst, dp);
                    }
                    int dp3 = dp + 1;
                    da[dp] = (byte) ((c >> 6) | Opcodes.CHECKCAST);
                    dp = dp3 + 1;
                    da[dp3] = (byte) ((c & '?') | 128);
                } else if (StrictUtf8.isSurrogate(c)) {
                    if (this.sgp == null) {
                        this.sgp = new Parser();
                    }
                    int uc = this.sgp.parse(c, sa, sp, sl);
                    if (uc < 0) {
                        StrictUtf8.updatePositions(src, sp, dst, dp);
                        return this.sgp.error();
                    } else if (dl - dp < 4) {
                        return overflow(src, sp, dst, dp);
                    } else {
                        int dp4 = dp + 1;
                        da[dp] = (byte) ((uc >> 18) | 240);
                        int dp5 = dp4 + 1;
                        da[dp4] = (byte) (((uc >> 12) & 63) | 128);
                        int dp6 = dp5 + 1;
                        da[dp5] = (byte) (((uc >> 6) & 63) | 128);
                        dp = dp6 + 1;
                        da[dp6] = (byte) ((uc & 63) | 128);
                        sp++;
                    }
                } else if (dl - dp < 3) {
                    return overflow(src, sp, dst, dp);
                } else {
                    int dp7 = dp + 1;
                    da[dp] = (byte) ((c >> 12) | 224);
                    int dp8 = dp7 + 1;
                    da[dp7] = (byte) (((c >> 6) & 63) | 128);
                    da[dp8] = (byte) ((c & '?') | 128);
                    dp = dp8 + 1;
                }
                sp++;
            }
            StrictUtf8.updatePositions(src, sp, dst, dp);
            return CoderResult.UNDERFLOW;
        }

        private CoderResult encodeBufferLoop(CharBuffer src, ByteBuffer dst) {
            int mark = src.position();
            while (src.hasRemaining()) {
                char c = src.get();
                if (c < 128) {
                    if (!dst.hasRemaining()) {
                        return overflow(src, mark);
                    }
                    dst.put((byte) c);
                } else if (c < 2048) {
                    if (dst.remaining() < 2) {
                        return overflow(src, mark);
                    }
                    dst.put((byte) ((c >> 6) | Opcodes.CHECKCAST));
                    dst.put((byte) (128 | (c & '?')));
                } else if (StrictUtf8.isSurrogate(c)) {
                    if (this.sgp == null) {
                        this.sgp = new Parser();
                    }
                    int uc = this.sgp.parse(c, src);
                    if (uc < 0) {
                        src.position(mark);
                        return this.sgp.error();
                    } else if (dst.remaining() < 4) {
                        return overflow(src, mark);
                    } else {
                        dst.put((byte) ((uc >> 18) | 240));
                        dst.put((byte) (((uc >> 12) & 63) | 128));
                        dst.put((byte) (((uc >> 6) & 63) | 128));
                        dst.put((byte) (128 | (uc & 63)));
                        mark++;
                    }
                } else if (dst.remaining() < 3) {
                    return overflow(src, mark);
                } else {
                    dst.put((byte) ((c >> 12) | 224));
                    dst.put((byte) (((c >> 6) & 63) | 128));
                    dst.put((byte) (128 | (c & '?')));
                }
                mark++;
            }
            src.position(mark);
            return CoderResult.UNDERFLOW;
        }

        /* access modifiers changed from: protected */
        public final CoderResult encodeLoop(CharBuffer src, ByteBuffer dst) {
            if (!src.hasArray() || !dst.hasArray()) {
                return encodeBufferLoop(src, dst);
            }
            return encodeArrayLoop(src, dst);
        }

        public int encode(char[] sa, int sp, int len, byte[] da) {
            int dp;
            int dp2;
            int sl = sp + len;
            int dp3 = 0;
            int dlASCII = Math.min(len, da.length) + 0;
            while (dp < dlASCII && sa[sp] < 128) {
                da[dp] = (byte) sa[sp];
                dp3 = dp + 1;
                sp = sp + 1;
            }
            while (sp < sl) {
                int sp2 = sp + 1;
                char c = sa[sp];
                if (c < 128) {
                    da[dp] = (byte) c;
                    sp = sp2;
                    dp++;
                } else if (c < 2048) {
                    int dp4 = dp + 1;
                    da[dp] = (byte) ((c >> 6) | Opcodes.CHECKCAST);
                    dp = dp4 + 1;
                    da[dp4] = (byte) ((c & '?') | 128);
                    sp = sp2;
                } else if (StrictUtf8.isSurrogate(c)) {
                    if (this.sgp == null) {
                        this.sgp = new Parser();
                    }
                    int uc = this.sgp.parse(c, sa, sp2 - 1, sl);
                    if (uc >= 0) {
                        int dp5 = dp + 1;
                        da[dp] = (byte) ((uc >> 18) | 240);
                        int dp6 = dp5 + 1;
                        da[dp5] = (byte) (((uc >> 12) & 63) | 128);
                        int dp7 = dp6 + 1;
                        da[dp6] = (byte) (((uc >> 6) & 63) | 128);
                        da[dp7] = (byte) ((uc & 63) | 128);
                        sp2++;
                        dp2 = dp7 + 1;
                    } else if (malformedInputAction() != CodingErrorAction.REPLACE) {
                        return -1;
                    } else {
                        dp2 = dp + 1;
                        da[dp] = replacement()[0];
                    }
                    sp = sp2;
                    dp = dp2;
                } else {
                    int dp8 = dp + 1;
                    da[dp] = (byte) ((c >> 12) | 224);
                    int dp9 = dp8 + 1;
                    da[dp8] = (byte) (((c >> 6) & 63) | 128);
                    da[dp9] = (byte) ((c & '?') | 128);
                    sp = sp2;
                    dp = dp9 + 1;
                }
            }
            return dp;
        }
    }

    public static class Parser {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private CoderResult error = CoderResult.UNDERFLOW;

        static {
            Class<StrictUtf8> cls = StrictUtf8.class;
        }

        public CoderResult error() {
            CoderResult coderResult = this.error;
            if (coderResult != null) {
                return coderResult;
            }
            throw new AssertionError();
        }

        public int parse(char c, CharBuffer in) {
            if (Character.isHighSurrogate(c)) {
                if (!in.hasRemaining()) {
                    this.error = CoderResult.UNDERFLOW;
                    return -1;
                }
                char d = in.get();
                if (Character.isLowSurrogate(d)) {
                    int character = Character.toCodePoint(c, d);
                    this.error = null;
                    return character;
                }
                this.error = CoderResult.malformedForLength(1);
                return -1;
            } else if (Character.isLowSurrogate(c) != 0) {
                this.error = CoderResult.malformedForLength(1);
                return -1;
            } else {
                int character2 = c;
                this.error = null;
                return character2;
            }
        }

        public int parse(char c, char[] ia, int ip, int il) {
            if (ia[ip] != c) {
                throw new AssertionError();
            } else if (Character.isHighSurrogate(c)) {
                if (il - ip < 2) {
                    this.error = CoderResult.UNDERFLOW;
                    return -1;
                }
                char d = ia[ip + 1];
                if (Character.isLowSurrogate(d)) {
                    int character = Character.toCodePoint(c, d);
                    this.error = null;
                    return character;
                }
                this.error = CoderResult.malformedForLength(1);
                return -1;
            } else if (Character.isLowSurrogate(c) != 0) {
                this.error = CoderResult.malformedForLength(1);
                return -1;
            } else {
                int character2 = c;
                this.error = null;
                return character2;
            }
        }
    }
}
