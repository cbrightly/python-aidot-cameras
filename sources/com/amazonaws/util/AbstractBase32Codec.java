package com.amazonaws.util;

public abstract class AbstractBase32Codec implements Codec {
    private static final int BITS_3 = 3;
    private static final int BITS_4 = 4;
    private static final int BITS_5 = 5;
    private static final int BIT_MULTIPLIER = 8;
    private static final int MASK_2BITS = 3;
    private static final int MASK_3BITS = 7;
    private static final int MASK_4BITS = 15;
    private static final int MASK_5BITS = 31;
    private static final byte PAD = 61;
    private final byte[] alpahbets;

    /* access modifiers changed from: protected */
    public abstract int pos(byte b);

    protected AbstractBase32Codec(byte[] alphabets) {
        this.alpahbets = alphabets;
    }

    public final byte[] encode(byte[] src) {
        int num5bytes = src.length / 5;
        int remainder = src.length % 5;
        if (remainder == 0) {
            byte[] dest = new byte[(num5bytes * 8)];
            int s = 0;
            int d = 0;
            while (s < src.length) {
                encode5bytes(src, s, dest, d);
                s += 5;
                d += 8;
            }
            return dest;
        }
        byte[] dest2 = new byte[((num5bytes + 1) * 8)];
        int s2 = 0;
        int d2 = 0;
        while (s2 < src.length - remainder) {
            encode5bytes(src, s2, dest2, d2);
            s2 += 5;
            d2 += 8;
        }
        switch (remainder) {
            case 1:
                encode1byte(src, s2, dest2, d2);
                break;
            case 2:
                encode2bytes(src, s2, dest2, d2);
                break;
            case 3:
                encode3bytes(src, s2, dest2, d2);
                break;
            case 4:
                encode4bytes(src, s2, dest2, d2);
                break;
        }
        return dest2;
    }

    private final void encode5bytes(byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        byte[] bArr = this.alpahbets;
        int s2 = s + 1;
        byte s3 = src[s];
        byte p = s3;
        dest[d] = bArr[(s3 >>> 3) & 31];
        int d3 = d2 + 1;
        int s4 = s2 + 1;
        byte s5 = src[s2];
        byte p2 = s5;
        dest[d2] = bArr[((p & 7) << 2) | ((s5 >>> 6) & 3)];
        int d4 = d3 + 1;
        dest[d3] = bArr[(p2 >>> 1) & 31];
        int d5 = d4 + 1;
        int s6 = s4 + 1;
        byte s7 = src[s4];
        byte p3 = s7;
        dest[d4] = bArr[((p2 & 1) << 4) | ((s7 >>> 4) & 15)];
        int d6 = d5 + 1;
        int s8 = s6 + 1;
        byte s9 = src[s6];
        byte p4 = s9;
        dest[d5] = bArr[((p3 & 15) << 1) | ((s9 >>> 7) & 1)];
        int d7 = d6 + 1;
        dest[d6] = bArr[(p4 >>> 2) & 31];
        byte p5 = src[s8];
        dest[d7] = bArr[((p4 & 3) << 3) | ((p5 >>> 5) & 7)];
        dest[d7 + 1] = bArr[p5 & 31];
    }

    private final void encode4bytes(byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        byte[] bArr = this.alpahbets;
        int s2 = s + 1;
        byte s3 = src[s];
        byte p = s3;
        dest[d] = bArr[(s3 >>> 3) & 31];
        int d3 = d2 + 1;
        int s4 = s2 + 1;
        byte s5 = src[s2];
        byte p2 = s5;
        dest[d2] = bArr[((p & 7) << 2) | ((s5 >>> 6) & 3)];
        int d4 = d3 + 1;
        dest[d3] = bArr[(p2 >>> 1) & 31];
        int d5 = d4 + 1;
        int s6 = s4 + 1;
        byte s7 = src[s4];
        byte p3 = s7;
        dest[d4] = bArr[((p2 & 1) << 4) | ((s7 >>> 4) & 15)];
        int d6 = d5 + 1;
        byte b = src[s6];
        byte p4 = b;
        dest[d5] = bArr[((p3 & 15) << 1) | ((b >>> 7) & 1)];
        int d7 = d6 + 1;
        dest[d6] = bArr[(p4 >>> 2) & 31];
        dest[d7] = bArr[(p4 & 3) << 3];
        dest[d7 + 1] = PAD;
    }

    private final void encode3bytes(byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        byte[] bArr = this.alpahbets;
        int s2 = s + 1;
        byte s3 = src[s];
        byte p = s3;
        dest[d] = bArr[(s3 >>> 3) & 31];
        int d3 = d2 + 1;
        int s4 = s2 + 1;
        byte s5 = src[s2];
        byte p2 = s5;
        dest[d2] = bArr[((p & 7) << 2) | ((s5 >>> 6) & 3)];
        int d4 = d3 + 1;
        dest[d3] = bArr[(p2 >>> 1) & 31];
        int d5 = d4 + 1;
        byte p3 = src[s4];
        dest[d4] = bArr[((p2 & 1) << 4) | ((p3 >>> 4) & 15)];
        int d6 = d5 + 1;
        dest[d5] = bArr[(p3 & 15) << 1];
        int i = 0;
        while (i < 3) {
            dest[d6] = PAD;
            i++;
            d6++;
        }
    }

    private final void encode2bytes(byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        byte[] bArr = this.alpahbets;
        int s2 = s + 1;
        byte s3 = src[s];
        byte p = s3;
        dest[d] = bArr[(s3 >>> 3) & 31];
        int d3 = d2 + 1;
        byte b = src[s2];
        byte p2 = b;
        dest[d2] = bArr[((p & 7) << 2) | ((b >>> 6) & 3)];
        int d4 = d3 + 1;
        dest[d3] = bArr[(p2 >>> 1) & 31];
        int d5 = d4 + 1;
        dest[d4] = bArr[(p2 & 1) << 4];
        int i = 0;
        while (i < 4) {
            dest[d5] = PAD;
            i++;
            d5++;
        }
    }

    private final void encode1byte(byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        byte[] bArr = this.alpahbets;
        byte p = src[s];
        dest[d] = bArr[(p >>> 3) & 31];
        int d3 = d2 + 1;
        dest[d2] = bArr[(p & 7) << 2];
        int i = 0;
        while (i < 6) {
            dest[d3] = PAD;
            i++;
            d3++;
        }
    }

    private final void decode5bytes(byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        int s2 = s + 1;
        int s3 = s2 + 1;
        int pos = pos(src[s2]);
        int p = pos;
        dest[d] = (byte) ((pos(src[s]) << 3) | ((pos >>> 2) & 7));
        int d3 = d2 + 1;
        int s4 = s3 + 1;
        int pos2 = ((p & 3) << 6) | (pos(src[s3]) << 1);
        int s5 = s4 + 1;
        int pos3 = pos(src[s4]);
        int p2 = pos3;
        dest[d2] = (byte) (pos2 | ((pos3 >>> 4) & 1));
        int d4 = d3 + 1;
        int s6 = s5 + 1;
        int pos4 = pos(src[s5]);
        int p3 = pos4;
        dest[d3] = (byte) (((p2 & 15) << 4) | ((pos4 >>> 1) & 15));
        int s7 = s6 + 1;
        int pos5 = ((p3 & 1) << 7) | (pos(src[s6]) << 2);
        int s8 = s7 + 1;
        int p4 = pos(src[s7]);
        dest[d4] = (byte) (pos5 | ((p4 >>> 3) & 3));
        dest[d4 + 1] = (byte) (((p4 & 7) << 5) | pos(src[s8]));
    }

    private final void decode1to4bytes(int n, byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        int s2 = s + 1;
        int s3 = s2 + 1;
        int pos = pos(src[s2]);
        int p = pos;
        dest[d] = (byte) ((pos(src[s]) << 3) | ((pos >>> 2) & 7));
        if (n == 1) {
            CodecUtils.sanityCheckLastPos(p, 3);
            return;
        }
        int d3 = d2 + 1;
        int s4 = s3 + 1;
        int pos2 = ((p & 3) << 6) | (pos(src[s3]) << 1);
        int s5 = s4 + 1;
        int pos3 = pos(src[s4]);
        int p2 = pos3;
        dest[d2] = (byte) (pos2 | ((pos3 >>> 4) & 1));
        if (n == 2) {
            CodecUtils.sanityCheckLastPos(p2, 15);
            return;
        }
        int d4 = d3 + 1;
        int s6 = s5 + 1;
        int pos4 = pos(src[s5]);
        int p3 = pos4;
        dest[d3] = (byte) ((15 & (pos4 >>> 1)) | ((p2 & 15) << 4));
        if (n == 3) {
            CodecUtils.sanityCheckLastPos(p3, 1);
            return;
        }
        int pos5 = ((p3 & 1) << 7) | (pos(src[s6]) << 2);
        int p4 = pos(src[s6 + 1]);
        dest[d4] = (byte) (pos5 | ((p4 >>> 3) & 3));
        CodecUtils.sanityCheckLastPos(p4, 7);
    }

    public final byte[] decode(byte[] src, int length) {
        int fq;
        if (length % 8 == 0) {
            int pads = 0;
            int last = length - 1;
            while (pads < 6 && last > -1 && src[last] == 61) {
                last--;
                pads++;
            }
            switch (pads) {
                case 0:
                    fq = 5;
                    break;
                case 1:
                    fq = 4;
                    break;
                case 3:
                    fq = 3;
                    break;
                case 4:
                    fq = 2;
                    break;
                case 6:
                    fq = 1;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid number of paddings " + pads);
            }
            byte[] dest = new byte[(((length / 8) * 5) - (5 - fq))];
            int s = 0;
            int d = 0;
            while (d < dest.length - (fq % 5)) {
                decode5bytes(src, s, dest, d);
                s += 8;
                d += 5;
            }
            if (fq < 5) {
                decode1to4bytes(fq, src, s, dest, d);
            }
            return dest;
        }
        throw new IllegalArgumentException("Input is expected to be encoded in multiple of 8 bytes but found: " + length);
    }
}
