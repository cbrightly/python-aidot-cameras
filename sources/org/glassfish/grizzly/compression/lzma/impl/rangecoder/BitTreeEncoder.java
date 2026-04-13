package org.glassfish.grizzly.compression.lzma.impl.rangecoder;

public class BitTreeEncoder {
    final short[] Models;
    final int NumBitLevels;

    public BitTreeEncoder(int numBitLevels) {
        this.NumBitLevels = numBitLevels;
        this.Models = new short[(1 << numBitLevels)];
    }

    public void init() {
        RangeDecoder.initBitModels(this.Models);
    }

    public void encode(RangeEncoder rangeEncoder, int symbol) {
        int m = 1;
        int bitIndex = this.NumBitLevels;
        while (bitIndex != 0) {
            bitIndex--;
            int bit = (symbol >>> bitIndex) & 1;
            rangeEncoder.encode(this.Models, m, bit);
            m = (m << 1) | bit;
        }
    }

    public void reverseEncode(RangeEncoder rangeEncoder, int symbol) {
        int m = 1;
        for (int i = 0; i < this.NumBitLevels; i++) {
            int bit = symbol & 1;
            rangeEncoder.encode(this.Models, m, bit);
            m = (m << 1) | bit;
            symbol >>= 1;
        }
    }

    public int getPrice(int symbol) {
        int price = 0;
        int m = 1;
        int bitIndex = this.NumBitLevels;
        while (bitIndex != 0) {
            bitIndex--;
            int bit = (symbol >>> bitIndex) & 1;
            price += RangeEncoder.getPrice(this.Models[m], bit);
            m = (m << 1) + bit;
        }
        return price;
    }

    public int reverseGetPrice(int symbol) {
        int price = 0;
        int m = 1;
        for (int i = this.NumBitLevels; i != 0; i--) {
            int bit = symbol & 1;
            symbol >>>= 1;
            price += RangeEncoder.getPrice(this.Models[m], bit);
            m = (m << 1) | bit;
        }
        return price;
    }

    public static int reverseGetPrice(short[] Models2, int startIndex, int NumBitLevels2, int symbol) {
        int price = 0;
        int m = 1;
        for (int i = NumBitLevels2; i != 0; i--) {
            int bit = symbol & 1;
            symbol >>>= 1;
            price += RangeEncoder.getPrice(Models2[startIndex + m], bit);
            m = (m << 1) | bit;
        }
        return price;
    }

    public static void reverseEncode(short[] Models2, int startIndex, RangeEncoder rangeEncoder, int NumBitLevels2, int symbol) {
        int m = 1;
        for (int i = 0; i < NumBitLevels2; i++) {
            int bit = symbol & 1;
            rangeEncoder.encode(Models2, startIndex + m, bit);
            m = (m << 1) | bit;
            symbol >>= 1;
        }
    }
}
