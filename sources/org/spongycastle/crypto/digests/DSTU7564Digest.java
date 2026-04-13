package org.spongycastle.crypto.digests;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.util.Constants;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Memoable;
import org.spongycastle.util.Pack;

public class DSTU7564Digest implements ExtendedDigest, Memoable {
    private static final byte[][] a = {new byte[]{1, 1, 5, 1, 8, 6, 7, 4}, new byte[]{4, 1, 1, 5, 1, 8, 6, 7}, new byte[]{7, 4, 1, 1, 5, 1, 8, 6}, new byte[]{6, 7, 4, 1, 1, 5, 1, 8}, new byte[]{8, 6, 7, 4, 1, 1, 5, 1}, new byte[]{1, 8, 6, 7, 4, 1, 1, 5}, new byte[]{5, 1, 8, 6, 7, 4, 1, 1}, new byte[]{1, 5, 1, 8, 6, 7, 4, 1}};
    private static final byte[][] b = {new byte[]{-88, 67, 95, 6, 107, 117, 108, 89, 113, -33, -121, -107, 23, -16, -40, 9, 109, -13, 29, -53, -55, 77, Constants.COMMA, -81, 121, -32, -105, -3, 111, 75, 69, 57, 62, -35, -93, 79, -76, -74, -102, 14, 31, -65, 21, -31, 73, -46, -109, -58, -110, 114, -98, 97, -47, 99, -6, -18, -12, 25, -43, -83, 88, -92, -69, -95, -36, -14, OTACommand.RESP_ID_END_OTA, 55, 66, -28, 122, 50, -100, -52, -85, 74, -113, 110, 4, 39, 46, -25, -30, 90, -106, 22, 35, 43, -62, 101, 102, 15, -68, -87, 71, 65, 52, 72, -4, -73, 106, -120, -91, 83, -122, -7, 91, -37, 56, 123, -61, 30, 34, 51, 36, 40, 54, -57, -78, Constants.SEMI_COLON, -114, 119, -70, -11, 20, -97, 8, 85, -101, 76, -2, 96, 92, -38, 24, 70, -51, 125, 33, -80, 63, 27, -119, -1, -21, OTACommand.RESP_ID_END_OTA_MD5, 105, 58, -99, -41, -45, 112, 103, 64, -75, -34, 93, 48, -111, -79, 120, 17, 1, -27, 0, 104, -104, -96, -59, 2, -90, 116, 45, 11, -94, 118, -77, -66, -50, -67, -82, -23, -118, 49, 28, -20, -15, -103, -108, -86, -10, 38, 47, -17, -24, -116, 53, 3, -44, Byte.MAX_VALUE, -5, 5, -63, 94, -112, 32, 61, OTACommand.RESP_ID_SEND_OTA, -9, -22, 10, 13, 126, -8, 80, 26, -60, 7, 87, -72, 60, 98, -29, -56, -84, 82, 100, MappingData.PATH, -48, -39, 19, 12, 18, 41, 81, -71, -49, -42, 115, -115, OTACommand.RESP_ID_START_OTA, 84, -64, -19, 78, 68, -89, 42, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, 37, -26, -54, 124, -117, 86, OTACommand.RESP_ID_VERSION}, new byte[]{-50, -69, -21, -110, -22, -53, 19, -63, -23, 58, -42, -78, -46, -112, 23, -8, 66, 21, 86, -76, 101, 28, -120, 67, -59, 92, 54, -70, -11, 87, 103, -115, 49, -10, 100, 88, -98, -12, 34, -86, 117, 15, 2, -79, -33, 109, 115, 77, 124, 38, 46, -9, 8, 93, 68, 62, -97, 20, -56, -82, 84, MappingData.PATH, -40, -68, 26, 107, 105, -13, -67, 51, -85, -6, -47, -101, 104, 78, 22, -107, -111, -18, 76, 99, -114, 91, -52, 60, 25, -95, OTACommand.RESP_ID_START_OTA, 73, 123, -39, 111, 55, 96, -54, -25, 43, 72, -3, -106, 69, -4, 65, 18, 13, 121, -27, -119, -116, -29, 32, 48, -36, -73, 108, 74, -75, 63, -105, -44, 98, 45, 6, -92, -91, OTACommand.RESP_ID_END_OTA, 95, 42, -38, -55, 0, 126, -94, 85, -65, 17, -43, -100, -49, 14, 10, 61, 81, 125, -109, 27, -2, -60, 71, 9, -122, 11, -113, -99, 106, 7, -71, -80, -104, 24, 50, 113, 75, -17, Constants.SEMI_COLON, 112, -96, -28, 64, -1, -61, -87, -26, 120, -7, -117, 70, OTACommand.RESP_ID_VERSION, 30, 56, -31, -72, -88, -32, 12, 35, 118, 29, 37, 36, 5, -15, 110, -108, 40, -102, OTACommand.RESP_ID_END_OTA_MD5, -24, -93, 79, 119, -45, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, -30, 82, -14, OTACommand.RESP_ID_SEND_OTA, 80, 122, 47, 116, 83, -77, 97, -81, 57, 53, -34, -51, 31, -103, -84, -83, 114, Constants.COMMA, -35, -48, -121, -66, 94, -90, -20, 4, -58, 3, 52, -5, -37, 89, -74, -62, 1, -16, 90, -19, -89, 102, 33, Byte.MAX_VALUE, -118, 39, -57, -64, 41, -41}, new byte[]{-109, -39, -102, -75, -104, 34, 69, -4, -70, 106, -33, 2, -97, -36, 81, 89, 74, 23, 43, -62, -108, -12, -69, -93, 98, -28, 113, -44, -51, 112, 22, -31, 73, 60, -64, -40, 92, -101, -83, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, 83, -95, 122, -56, 45, -32, -47, 114, -90, Constants.COMMA, -60, -29, 118, 120, -73, -76, 9, Constants.SEMI_COLON, 14, 65, 76, -34, -78, -112, 37, -91, -41, 3, 17, 0, -61, 46, -110, -17, 78, 18, -99, 125, -53, 53, MappingData.PATH, -43, 79, -98, 77, -87, 85, -58, -48, 123, 24, -105, -45, 54, -26, 72, 86, OTACommand.RESP_ID_START_OTA, -113, 119, -52, -100, -71, -30, -84, -72, 47, 21, -92, 124, -38, 56, 30, 11, 5, -42, 20, 110, 108, 126, 102, -3, -79, -27, 96, -81, 94, 51, -121, -55, -16, 93, 109, 63, -120, -115, -57, -9, 29, -23, -20, -19, OTACommand.RESP_ID_VERSION, 41, 39, -49, -103, -88, 80, 15, 55, 36, 40, 48, -107, -46, 62, 91, 64, OTACommand.RESP_ID_END_OTA, -77, 105, 87, 31, 7, 28, -118, -68, 32, -21, -50, -114, -85, -18, 49, -94, 115, -7, -54, 58, 26, -5, 13, -63, -2, -6, -14, 111, -67, -106, -35, 67, 82, -74, 8, -13, -82, -66, 25, -119, 50, 38, -80, -22, 75, 100, OTACommand.RESP_ID_END_OTA_MD5, OTACommand.RESP_ID_SEND_OTA, 107, -11, 121, -65, 1, 95, 117, 99, 27, 35, 61, 104, 42, 101, -24, -111, -10, -1, 19, 88, -15, 71, 10, Byte.MAX_VALUE, -59, -89, -25, 97, 90, 6, 70, 68, 66, 4, -96, -37, 57, -122, 84, -86, -116, 52, 33, -117, -8, 12, 116, 103}, new byte[]{104, -115, -54, 77, 115, 75, 78, 42, -44, 82, 38, -77, 84, 30, 25, 31, 34, 3, 70, 61, 45, 74, 83, OTACommand.RESP_ID_END_OTA, 19, -118, -73, -43, 37, 121, -11, -67, 88, 47, 13, 2, -19, 81, -98, 17, -14, 62, 85, 94, -47, 22, 60, 102, 112, 93, -13, 69, 64, -52, -24, -108, 86, 8, -50, 26, 58, -46, -31, -33, -75, 56, 110, 14, -27, -12, -7, -122, -23, 79, -42, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, 35, -49, 50, -103, 49, 20, -82, -18, -56, 72, -45, 48, -95, -110, 65, -79, 24, -60, Constants.COMMA, 113, 114, 68, 21, -3, 55, -66, 95, -86, -101, -120, -40, -85, -119, -100, -6, 96, -22, -68, 98, 12, 36, -90, -88, -20, 103, 32, -37, 124, 40, -35, -84, 91, 52, 126, MappingData.PATH, -15, 123, -113, 99, -96, 5, -102, 67, 119, 33, -65, 39, 9, -61, -97, -74, -41, 41, -62, -21, -64, -92, -117, -116, 29, -5, -1, -63, -78, -105, 46, -8, 101, -10, 117, 7, 4, 73, 51, -28, -39, -71, -48, 66, -57, 108, -112, 0, -114, 111, 80, 1, -59, -38, 71, 63, -51, 105, -94, -30, 122, -89, -58, -109, 15, 10, 6, -26, 43, -106, -93, 28, -81, 106, 18, OTACommand.RESP_ID_END_OTA_MD5, 57, -25, -80, OTACommand.RESP_ID_SEND_OTA, -9, -2, -99, -121, 92, OTACommand.RESP_ID_START_OTA, 53, -34, -76, -91, -4, OTACommand.RESP_ID_VERSION, -17, -53, -69, 107, 118, -70, 90, 125, 120, 11, -107, -29, -83, 116, -104, Constants.SEMI_COLON, 54, 100, 109, -36, -16, 89, -87, 76, 23, Byte.MAX_VALUE, -111, -72, -55, 87, 27, -32, 97}};
    private int c;
    private int d;
    private int e;
    private int f;
    private byte[] g;
    private byte[][] h;
    private byte[][] i;
    private byte[][] j;
    private byte[] k;
    private byte[] l;
    private long[] m;
    private long n;
    private int o;
    private byte[] p;

    public DSTU7564Digest(DSTU7564Digest digest) {
        p(digest);
    }

    private void p(DSTU7564Digest digest) {
        this.c = digest.c;
        this.d = digest.d;
        this.e = digest.e;
        this.f = digest.f;
        this.g = Arrays.h(digest.g);
        this.h = Arrays.p(digest.h);
        this.i = Arrays.p(digest.i);
        this.j = Arrays.p(digest.j);
        this.k = Arrays.h(digest.k);
        this.l = Arrays.h(digest.l);
        this.m = Arrays.l(digest.m);
        this.n = digest.n;
        this.o = digest.o;
        this.p = Arrays.h(digest.p);
    }

    public DSTU7564Digest(int hashSizeBits) {
        byte[][] bArr;
        if (hashSizeBits == 256 || hashSizeBits == 384 || hashSizeBits == 512) {
            this.c = hashSizeBits / 8;
            if (hashSizeBits > 256) {
                this.d = 128;
                this.e = 16;
                this.f = 14;
                this.h = new byte[128][];
            } else {
                this.d = 64;
                this.e = 8;
                this.f = 10;
                this.h = new byte[64][];
            }
            int bufferIndex = 0;
            while (true) {
                bArr = this.h;
                if (bufferIndex >= bArr.length) {
                    break;
                }
                bArr[bufferIndex] = new byte[this.e];
                bufferIndex++;
            }
            bArr[0][0] = (byte) bArr.length;
            this.g = null;
            this.i = new byte[128][];
            this.j = new byte[128][];
            for (int bufferIndex2 = 0; bufferIndex2 < this.h.length; bufferIndex2++) {
                this.i[bufferIndex2] = new byte[8];
                this.j[bufferIndex2] = new byte[8];
            }
            this.k = new byte[16];
            this.l = new byte[8];
            this.m = new long[this.e];
            this.p = new byte[this.d];
            return;
        }
        throw new IllegalArgumentException("Hash size is not recommended. Use 256/384/512 instead");
    }

    public String b() {
        return "DSTU7564";
    }

    public int e() {
        return this.c;
    }

    public int k() {
        return this.d;
    }

    public void d(byte in) {
        byte[] bArr = this.p;
        int i2 = this.o;
        int i3 = i2 + 1;
        this.o = i3;
        bArr[i2] = in;
        if (i3 == this.d) {
            s(bArr, 0);
            this.o = 0;
        }
        this.n++;
    }

    public void update(byte[] in, int inOff, int len) {
        while (this.o != 0 && len > 0) {
            d(in[inOff]);
            len--;
            inOff++;
        }
        if (len > 0) {
            while (len > this.d) {
                s(in, inOff);
                int i2 = this.d;
                inOff += i2;
                this.n += (long) i2;
                len -= i2;
            }
            while (len > 0) {
                d(in[inOff]);
                len--;
                inOff++;
            }
        }
    }

    public int c(byte[] out, int outOff) {
        byte[] r = r(this.p, 0, this.o);
        this.g = r;
        int paddedLen = r.length;
        int paddedOff = 0;
        while (paddedLen != 0) {
            s(this.g, paddedOff);
            int i2 = this.d;
            paddedOff += i2;
            paddedLen -= i2;
        }
        byte[][] temp = new byte[128][];
        int bufferIndex = 0;
        while (true) {
            byte[][] bArr = this.h;
            if (bufferIndex >= bArr.length) {
                break;
            }
            temp[bufferIndex] = new byte[8];
            System.arraycopy(bArr[bufferIndex], 0, temp[bufferIndex], 0, 8);
            bufferIndex++;
        }
        for (int roundIndex = 0; roundIndex < this.f; roundIndex++) {
            for (int columnIndex = 0; columnIndex < this.e; columnIndex++) {
                byte[] bArr2 = temp[columnIndex];
                bArr2[0] = (byte) (bArr2[0] ^ ((byte) ((columnIndex * 16) ^ roundIndex)));
            }
            for (int rowIndex = 0; rowIndex < 8; rowIndex++) {
                for (int columnIndex2 = 0; columnIndex2 < this.e; columnIndex2++) {
                    temp[columnIndex2][rowIndex] = b[rowIndex % 4][temp[columnIndex2][rowIndex] & 255];
                }
            }
            int shift = -1;
            for (int rowIndex2 = 0; rowIndex2 < 8; rowIndex2++) {
                if (rowIndex2 == 7 && this.e == 16) {
                    shift = 11;
                } else {
                    shift++;
                }
                int columnIndex3 = 0;
                while (true) {
                    int i3 = this.e;
                    if (columnIndex3 >= i3) {
                        break;
                    }
                    this.k[(columnIndex3 + shift) % i3] = temp[columnIndex3][rowIndex2];
                    columnIndex3++;
                }
                for (int columnIndex4 = 0; columnIndex4 < this.e; columnIndex4++) {
                    temp[columnIndex4][rowIndex2] = this.k[columnIndex4];
                }
            }
            for (int columnIndex5 = 0; columnIndex5 < this.e; columnIndex5++) {
                Arrays.F(this.l, (byte) 0);
                for (int rowIndex3 = 7; rowIndex3 >= 0; rowIndex3--) {
                    byte multiplicationResult = 0;
                    for (int rowInternalIndex = 7; rowInternalIndex >= 0; rowInternalIndex--) {
                        multiplicationResult = (byte) (q(temp[columnIndex5][rowInternalIndex], a[rowIndex3][rowInternalIndex]) ^ multiplicationResult);
                    }
                    this.l[rowIndex3] = multiplicationResult;
                }
                for (int rowIndex4 = 0; rowIndex4 < 8; rowIndex4++) {
                    temp[columnIndex5][rowIndex4] = this.l[rowIndex4];
                }
            }
        }
        for (int rowIndex5 = 0; rowIndex5 < 8; rowIndex5++) {
            for (int columnIndex6 = 0; columnIndex6 < this.e; columnIndex6++) {
                byte[] bArr3 = this.h[columnIndex6];
                bArr3[rowIndex5] = (byte) (bArr3[rowIndex5] ^ temp[columnIndex6][rowIndex5]);
            }
        }
        byte[] stateBuffer = new byte[(this.e * 8)];
        int stateLineIndex = 0;
        for (int columnIndex7 = 0; columnIndex7 < this.e; columnIndex7++) {
            for (int rowIndex6 = 0; rowIndex6 < 8; rowIndex6++) {
                stateBuffer[stateLineIndex] = this.h[columnIndex7][rowIndex6];
                stateLineIndex++;
            }
        }
        int columnIndex8 = stateBuffer.length;
        int i4 = this.c;
        System.arraycopy(stateBuffer, columnIndex8 - i4, out, outOff, i4);
        reset();
        return this.c;
    }

    public void reset() {
        byte[][] bArr;
        int bufferIndex = 0;
        while (true) {
            bArr = this.h;
            if (bufferIndex >= bArr.length) {
                break;
            }
            bArr[bufferIndex] = new byte[this.e];
            bufferIndex++;
        }
        bArr[0][0] = (byte) bArr.length;
        this.n = 0;
        this.o = 0;
        Arrays.F(this.p, (byte) 0);
        byte[] bArr2 = this.g;
        if (bArr2 != null) {
            Arrays.F(bArr2, (byte) 0);
        }
    }

    private void s(byte[] input, int inOff) {
        for (int bufferIndex = 0; bufferIndex < this.h.length; bufferIndex++) {
            Arrays.F(this.i[bufferIndex], (byte) 0);
            Arrays.F(this.j[bufferIndex], (byte) 0);
        }
        for (int bufferIndex2 = 0; bufferIndex2 < 8; bufferIndex2++) {
            for (int byteIndex = 0; byteIndex < this.e; byteIndex++) {
                this.i[byteIndex][bufferIndex2] = (byte) (this.h[byteIndex][bufferIndex2] ^ input[((byteIndex * 8) + bufferIndex2) + inOff]);
                this.j[byteIndex][bufferIndex2] = input[(byteIndex * 8) + bufferIndex2 + inOff];
            }
        }
        n();
        o();
        for (int bufferIndex3 = 0; bufferIndex3 < 8; bufferIndex3++) {
            for (int byteIndex2 = 0; byteIndex2 < this.e; byteIndex2++) {
                byte[] bArr = this.h[byteIndex2];
                bArr[bufferIndex3] = (byte) (bArr[bufferIndex3] ^ ((byte) (this.i[byteIndex2][bufferIndex3] ^ this.j[byteIndex2][bufferIndex3])));
            }
        }
    }

    private void o() {
        for (int roundIndex = 0; roundIndex < this.f; roundIndex++) {
            for (int columnIndex = 0; columnIndex < this.e; columnIndex++) {
                this.m[columnIndex] = Pack.m(this.j[columnIndex], 0);
                long[] jArr = this.m;
                jArr[columnIndex] = jArr[columnIndex] + (67818912035696883L ^ (((((long) ((this.e - columnIndex) - 1)) * 16) ^ ((long) roundIndex)) << 56));
                Pack.r(jArr[columnIndex], this.j[columnIndex], 0);
            }
            for (int rowIndex = 0; rowIndex < 8; rowIndex++) {
                for (int columnIndex2 = 0; columnIndex2 < this.e; columnIndex2++) {
                    byte[][] bArr = this.j;
                    bArr[columnIndex2][rowIndex] = b[rowIndex % 4][bArr[columnIndex2][rowIndex] & 255];
                }
            }
            int shift = -1;
            for (int rowIndex2 = 0; rowIndex2 < 8; rowIndex2++) {
                if (rowIndex2 == 7 && this.e == 16) {
                    shift = 11;
                } else {
                    shift++;
                }
                int columnIndex3 = 0;
                while (true) {
                    int i2 = this.e;
                    if (columnIndex3 >= i2) {
                        break;
                    }
                    this.k[(columnIndex3 + shift) % i2] = this.j[columnIndex3][rowIndex2];
                    columnIndex3++;
                }
                for (int columnIndex4 = 0; columnIndex4 < this.e; columnIndex4++) {
                    this.j[columnIndex4][rowIndex2] = this.k[columnIndex4];
                }
            }
            for (int columnIndex5 = 0; columnIndex5 < this.e; columnIndex5++) {
                Arrays.F(this.l, (byte) 0);
                for (int rowIndex3 = 7; rowIndex3 >= 0; rowIndex3--) {
                    byte multiplicationResult = 0;
                    for (int rowInternalIndex = 7; rowInternalIndex >= 0; rowInternalIndex--) {
                        multiplicationResult = (byte) (q(this.j[columnIndex5][rowInternalIndex], a[rowIndex3][rowInternalIndex]) ^ multiplicationResult);
                    }
                    this.l[rowIndex3] = multiplicationResult;
                }
                for (int rowIndex4 = 0; rowIndex4 < 8; rowIndex4++) {
                    this.j[columnIndex5][rowIndex4] = this.l[rowIndex4];
                }
            }
        }
    }

    private void n() {
        for (int roundIndex = 0; roundIndex < this.f; roundIndex++) {
            for (int columnIndex = 0; columnIndex < this.e; columnIndex++) {
                byte[] bArr = this.i[columnIndex];
                bArr[0] = (byte) (bArr[0] ^ ((byte) ((columnIndex * 16) ^ roundIndex)));
            }
            for (int rowIndex = 0; rowIndex < 8; rowIndex++) {
                for (int columnIndex2 = 0; columnIndex2 < this.e; columnIndex2++) {
                    byte[][] bArr2 = this.i;
                    bArr2[columnIndex2][rowIndex] = b[rowIndex % 4][bArr2[columnIndex2][rowIndex] & 255];
                }
            }
            int shift = -1;
            for (int rowIndex2 = 0; rowIndex2 < 8; rowIndex2++) {
                if (rowIndex2 == 7 && this.e == 16) {
                    shift = 11;
                } else {
                    shift++;
                }
                int columnIndex3 = 0;
                while (true) {
                    int i2 = this.e;
                    if (columnIndex3 >= i2) {
                        break;
                    }
                    this.k[(columnIndex3 + shift) % i2] = this.i[columnIndex3][rowIndex2];
                    columnIndex3++;
                }
                for (int columnIndex4 = 0; columnIndex4 < this.e; columnIndex4++) {
                    this.i[columnIndex4][rowIndex2] = this.k[columnIndex4];
                }
            }
            for (int columnIndex5 = 0; columnIndex5 < this.e; columnIndex5++) {
                Arrays.F(this.l, (byte) 0);
                for (int rowIndex3 = 7; rowIndex3 >= 0; rowIndex3--) {
                    byte multiplicationResult = 0;
                    for (int rowInternalIndex = 7; rowInternalIndex >= 0; rowInternalIndex--) {
                        multiplicationResult = (byte) (q(this.i[columnIndex5][rowInternalIndex], a[rowIndex3][rowInternalIndex]) ^ multiplicationResult);
                    }
                    this.l[rowIndex3] = multiplicationResult;
                }
                for (int rowIndex4 = 0; rowIndex4 < 8; rowIndex4++) {
                    this.i[columnIndex5][rowIndex4] = this.l[rowIndex4];
                }
            }
        }
    }

    private byte q(byte x, byte y) {
        byte result = 0;
        for (int bitIndex = 0; bitIndex < 8; bitIndex++) {
            if ((y & 1) == 1) {
                result = (byte) (result ^ x);
            }
            byte highestBit = (byte) (x & OTACommand.RESP_ID_VERSION);
            x = (byte) (x << 1);
            if (highestBit == Byte.MIN_VALUE) {
                x = (byte) (x ^ 285);
            }
            y = (byte) (y >> 1);
        }
        return result;
    }

    private byte[] r(byte[] in, int inOff, int len) {
        byte[] padded;
        int i2 = this.d;
        if (i2 - len < 13) {
            padded = new byte[(i2 * 2)];
        } else {
            padded = new byte[i2];
        }
        System.arraycopy(in, inOff, padded, 0, len);
        padded[len] = OTACommand.RESP_ID_VERSION;
        Pack.r(this.n * 8, padded, padded.length - 12);
        return padded;
    }

    public Memoable copy() {
        return new DSTU7564Digest(this);
    }

    public void m(Memoable other) {
        p((DSTU7564Digest) other);
    }
}
