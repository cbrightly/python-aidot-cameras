package org.spongycastle.crypto.engines;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.util.Constants;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;

public class DSTU7624Engine implements BlockCipher {
    private long[] a;
    private long[] b;
    private long[][] c;
    private int d;
    private int e;
    private int f;
    private boolean g;
    private byte[] h;
    private byte[] i;
    private byte[][] j = {new byte[]{1, 1, 5, 1, 8, 6, 7, 4}, new byte[]{4, 1, 1, 5, 1, 8, 6, 7}, new byte[]{7, 4, 1, 1, 5, 1, 8, 6}, new byte[]{6, 7, 4, 1, 1, 5, 1, 8}, new byte[]{8, 6, 7, 4, 1, 1, 5, 1}, new byte[]{1, 8, 6, 7, 4, 1, 1, 5}, new byte[]{5, 1, 8, 6, 7, 4, 1, 1}, new byte[]{1, 5, 1, 8, 6, 7, 4, 1}};
    private byte[][] k = {new byte[]{-83, -107, 118, -88, 47, 73, -41, -54}, new byte[]{-54, -83, -107, 118, -88, 47, 73, -41}, new byte[]{-41, -54, -83, -107, 118, -88, 47, 73}, new byte[]{73, -41, -54, -83, -107, 118, -88, 47}, new byte[]{47, 73, -41, -54, -83, -107, 118, -88}, new byte[]{-88, 47, 73, -41, -54, -83, -107, 118}, new byte[]{118, -88, 47, 73, -41, -54, -83, -107}, new byte[]{-107, 118, -88, 47, 73, -41, -54, -83}};
    private byte[][] l = {new byte[]{-88, 67, 95, 6, 107, 117, 108, 89, 113, -33, -121, -107, 23, -16, -40, 9, 109, -13, 29, -53, -55, 77, Constants.COMMA, -81, 121, -32, -105, -3, 111, 75, 69, 57, 62, -35, -93, 79, -76, -74, -102, 14, 31, -65, 21, -31, 73, -46, -109, -58, -110, 114, -98, 97, -47, 99, -6, -18, -12, 25, -43, -83, 88, -92, -69, -95, -36, -14, OTACommand.RESP_ID_END_OTA, 55, 66, -28, 122, 50, -100, -52, -85, 74, -113, 110, 4, 39, 46, -25, -30, 90, -106, 22, 35, 43, -62, 101, 102, 15, -68, -87, 71, 65, 52, 72, -4, -73, 106, -120, -91, 83, -122, -7, 91, -37, 56, 123, -61, 30, 34, 51, 36, 40, 54, -57, -78, Constants.SEMI_COLON, -114, 119, -70, -11, 20, -97, 8, 85, -101, 76, -2, 96, 92, -38, 24, 70, -51, 125, 33, -80, 63, 27, -119, -1, -21, OTACommand.RESP_ID_END_OTA_MD5, 105, 58, -99, -41, -45, 112, 103, 64, -75, -34, 93, 48, -111, -79, 120, 17, 1, -27, 0, 104, -104, -96, -59, 2, -90, 116, 45, 11, -94, 118, -77, -66, -50, -67, -82, -23, -118, 49, 28, -20, -15, -103, -108, -86, -10, 38, 47, -17, -24, -116, 53, 3, -44, Byte.MAX_VALUE, -5, 5, -63, 94, -112, 32, 61, OTACommand.RESP_ID_SEND_OTA, -9, -22, 10, 13, 126, -8, 80, 26, -60, 7, 87, -72, 60, 98, -29, -56, -84, 82, 100, MappingData.PATH, -48, -39, 19, 12, 18, 41, 81, -71, -49, -42, 115, -115, OTACommand.RESP_ID_START_OTA, 84, -64, -19, 78, 68, -89, 42, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, 37, -26, -54, 124, -117, 86, OTACommand.RESP_ID_VERSION}, new byte[]{-50, -69, -21, -110, -22, -53, 19, -63, -23, 58, -42, -78, -46, -112, 23, -8, 66, 21, 86, -76, 101, 28, -120, 67, -59, 92, 54, -70, -11, 87, 103, -115, 49, -10, 100, 88, -98, -12, 34, -86, 117, 15, 2, -79, -33, 109, 115, 77, 124, 38, 46, -9, 8, 93, 68, 62, -97, 20, -56, -82, 84, MappingData.PATH, -40, -68, 26, 107, 105, -13, -67, 51, -85, -6, -47, -101, 104, 78, 22, -107, -111, -18, 76, 99, -114, 91, -52, 60, 25, -95, OTACommand.RESP_ID_START_OTA, 73, 123, -39, 111, 55, 96, -54, -25, 43, 72, -3, -106, 69, -4, 65, 18, 13, 121, -27, -119, -116, -29, 32, 48, -36, -73, 108, 74, -75, 63, -105, -44, 98, 45, 6, -92, -91, OTACommand.RESP_ID_END_OTA, 95, 42, -38, -55, 0, 126, -94, 85, -65, 17, -43, -100, -49, 14, 10, 61, 81, 125, -109, 27, -2, -60, 71, 9, -122, 11, -113, -99, 106, 7, -71, -80, -104, 24, 50, 113, 75, -17, Constants.SEMI_COLON, 112, -96, -28, 64, -1, -61, -87, -26, 120, -7, -117, 70, OTACommand.RESP_ID_VERSION, 30, 56, -31, -72, -88, -32, 12, 35, 118, 29, 37, 36, 5, -15, 110, -108, 40, -102, OTACommand.RESP_ID_END_OTA_MD5, -24, -93, 79, 119, -45, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, -30, 82, -14, OTACommand.RESP_ID_SEND_OTA, 80, 122, 47, 116, 83, -77, 97, -81, 57, 53, -34, -51, 31, -103, -84, -83, 114, Constants.COMMA, -35, -48, -121, -66, 94, -90, -20, 4, -58, 3, 52, -5, -37, 89, -74, -62, 1, -16, 90, -19, -89, 102, 33, Byte.MAX_VALUE, -118, 39, -57, -64, 41, -41}, new byte[]{-109, -39, -102, -75, -104, 34, 69, -4, -70, 106, -33, 2, -97, -36, 81, 89, 74, 23, 43, -62, -108, -12, -69, -93, 98, -28, 113, -44, -51, 112, 22, -31, 73, 60, -64, -40, 92, -101, -83, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, 83, -95, 122, -56, 45, -32, -47, 114, -90, Constants.COMMA, -60, -29, 118, 120, -73, -76, 9, Constants.SEMI_COLON, 14, 65, 76, -34, -78, -112, 37, -91, -41, 3, 17, 0, -61, 46, -110, -17, 78, 18, -99, 125, -53, 53, MappingData.PATH, -43, 79, -98, 77, -87, 85, -58, -48, 123, 24, -105, -45, 54, -26, 72, 86, OTACommand.RESP_ID_START_OTA, -113, 119, -52, -100, -71, -30, -84, -72, 47, 21, -92, 124, -38, 56, 30, 11, 5, -42, 20, 110, 108, 126, 102, -3, -79, -27, 96, -81, 94, 51, -121, -55, -16, 93, 109, 63, -120, -115, -57, -9, 29, -23, -20, -19, OTACommand.RESP_ID_VERSION, 41, 39, -49, -103, -88, 80, 15, 55, 36, 40, 48, -107, -46, 62, 91, 64, OTACommand.RESP_ID_END_OTA, -77, 105, 87, 31, 7, 28, -118, -68, 32, -21, -50, -114, -85, -18, 49, -94, 115, -7, -54, 58, 26, -5, 13, -63, -2, -6, -14, 111, -67, -106, -35, 67, 82, -74, 8, -13, -82, -66, 25, -119, 50, 38, -80, -22, 75, 100, OTACommand.RESP_ID_END_OTA_MD5, OTACommand.RESP_ID_SEND_OTA, 107, -11, 121, -65, 1, 95, 117, 99, 27, 35, 61, 104, 42, 101, -24, -111, -10, -1, 19, 88, -15, 71, 10, Byte.MAX_VALUE, -59, -89, -25, 97, 90, 6, 70, 68, 66, 4, -96, -37, 57, -122, 84, -86, -116, 52, 33, -117, -8, 12, 116, 103}, new byte[]{104, -115, -54, 77, 115, 75, 78, 42, -44, 82, 38, -77, 84, 30, 25, 31, 34, 3, 70, 61, 45, 74, 83, OTACommand.RESP_ID_END_OTA, 19, -118, -73, -43, 37, 121, -11, -67, 88, 47, 13, 2, -19, 81, -98, 17, -14, 62, 85, 94, -47, 22, 60, 102, 112, 93, -13, 69, 64, -52, -24, -108, 86, 8, -50, 26, 58, -46, -31, -33, -75, 56, 110, 14, -27, -12, -7, -122, -23, 79, -42, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, 35, -49, 50, -103, 49, 20, -82, -18, -56, 72, -45, 48, -95, -110, 65, -79, 24, -60, Constants.COMMA, 113, 114, 68, 21, -3, 55, -66, 95, -86, -101, -120, -40, -85, -119, -100, -6, 96, -22, -68, 98, 12, 36, -90, -88, -20, 103, 32, -37, 124, 40, -35, -84, 91, 52, 126, MappingData.PATH, -15, 123, -113, 99, -96, 5, -102, 67, 119, 33, -65, 39, 9, -61, -97, -74, -41, 41, -62, -21, -64, -92, -117, -116, 29, -5, -1, -63, -78, -105, 46, -8, 101, -10, 117, 7, 4, 73, 51, -28, -39, -71, -48, 66, -57, 108, -112, 0, -114, 111, 80, 1, -59, -38, 71, 63, -51, 105, -94, -30, 122, -89, -58, -109, 15, 10, 6, -26, 43, -106, -93, 28, -81, 106, 18, OTACommand.RESP_ID_END_OTA_MD5, 57, -25, -80, OTACommand.RESP_ID_SEND_OTA, -9, -2, -99, -121, 92, OTACommand.RESP_ID_START_OTA, 53, -34, -76, -91, -4, OTACommand.RESP_ID_VERSION, -17, -53, -69, 107, 118, -70, 90, 125, 120, 11, -107, -29, -83, 116, -104, Constants.SEMI_COLON, 54, 100, 109, -36, -16, 89, -87, 76, 23, Byte.MAX_VALUE, -111, -72, -55, 87, 27, -32, 97}};
    private byte[][] m = {new byte[]{-92, -94, -87, -59, 78, -55, 3, -39, 126, 15, -46, -83, -25, -45, 39, 91, -29, -95, -24, -26, 124, 42, 85, 12, -122, 57, -41, -115, -72, 18, 111, 40, -51, -118, 112, 86, 114, -7, -65, 79, 115, -23, -9, 87, 22, -84, 80, -64, -99, -73, 71, 113, 96, -60, 116, 67, 108, 31, -109, 119, -36, -50, 32, -116, -103, 95, 68, 1, -11, 30, -121, 94, 97, Constants.COMMA, 75, 29, OTACommand.RESP_ID_START_OTA, 21, -12, 35, -42, -22, -31, 103, -15, Byte.MAX_VALUE, -2, -38, 60, 7, 83, 106, OTACommand.RESP_ID_END_OTA_MD5, -100, -53, 2, OTACommand.RESP_ID_END_OTA, 51, -35, 53, -30, 89, 90, -104, -91, -110, 100, 4, 6, MappingData.PATH, 77, 28, -105, 8, 49, -18, -85, 5, -81, 121, -96, 24, 70, 109, -4, -119, -44, -57, -1, -16, -49, 66, -111, -8, 104, 10, 101, -114, -74, -3, -61, -17, 120, 76, -52, -98, 48, 46, -68, 11, 84, 26, -90, -69, 38, OTACommand.RESP_ID_VERSION, 72, -108, 50, 125, -89, 63, -82, 34, 61, 102, -86, -10, 0, 93, -67, 74, -32, Constants.SEMI_COLON, -76, 23, -117, -97, 118, -80, 36, -102, 37, 99, -37, -21, 122, 62, 92, -77, -79, 41, -14, -54, 88, 110, -40, -88, 47, 117, -33, 20, -5, 19, 73, -120, -78, -20, -28, 52, 45, -106, -58, 58, -19, -107, 14, -27, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, 107, 64, 33, -101, 9, 25, 43, 82, -34, 69, -93, -6, 81, -62, -75, -47, -112, -71, -13, 55, -63, 13, -70, 65, 17, 56, 123, -66, -48, -43, 105, 54, -56, 98, 27, OTACommand.RESP_ID_SEND_OTA, -113}, new byte[]{OTACommand.RESP_ID_END_OTA, -14, 42, -21, -23, -65, 123, -100, 52, -106, -115, -104, -71, 105, -116, 41, 61, -120, 104, 6, 57, 17, 76, 14, -96, 86, 64, -110, 21, -68, -77, -36, 111, -8, 38, -70, -66, -67, 49, -5, -61, -2, OTACommand.RESP_ID_VERSION, 97, -31, 122, 50, -46, 112, 32, -95, 69, -20, -39, 26, 93, -76, -40, 9, -91, 85, -114, 55, 118, -87, 103, MappingData.PATH, 23, 54, 101, -79, -107, 98, 89, 116, -93, 80, 47, 75, -56, -48, -113, -51, -44, 60, -122, 18, 29, 35, -17, -12, 83, 25, 53, -26, Byte.MAX_VALUE, 94, -42, 121, 81, 34, 20, -9, 30, 74, 66, -101, 65, 115, 45, -63, 92, -90, -94, -32, 46, -45, 40, -69, -55, -82, 106, -47, 90, 48, -112, OTACommand.RESP_ID_END_OTA_MD5, -7, -78, 88, -49, 126, -59, -53, -105, -28, 22, 108, -6, -80, 109, 31, 82, -103, 13, 78, 3, -111, -62, 77, 100, 119, -97, -35, -60, 73, -118, -102, 36, 56, -89, 87, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, -57, 124, 125, -25, -10, -73, -84, 39, 70, -34, -33, Constants.SEMI_COLON, -41, -98, 43, 11, -43, 19, 117, -16, 114, -74, -99, 27, 1, 63, 68, -27, -121, -3, 7, -15, -85, -108, 24, -22, -4, 58, OTACommand.RESP_ID_SEND_OTA, 95, 5, 84, -37, 0, -117, -29, 72, 12, -54, 120, -119, 10, -1, 62, 91, OTACommand.RESP_ID_START_OTA, -18, 113, -30, -38, Constants.COMMA, -72, -75, -52, 110, -88, 107, -83, 96, -58, 8, 4, 2, -24, -11, 79, -92, -13, -64, -50, 67, 37, 28, 33, 51, 15, -81, 71, -19, 102, 99, -109, -86}, new byte[]{69, -44, 11, 67, -15, 114, -19, -92, -62, 56, -26, 113, -3, -74, 58, -107, 80, 68, 75, -30, 116, 107, 30, 17, 90, -58, -76, -40, -91, -118, 112, -93, -88, -6, 5, -39, -105, 64, -55, -112, -104, -113, -36, 18, 49, Constants.COMMA, 71, 106, -103, -82, -56, Byte.MAX_VALUE, -7, 79, 93, -106, 111, -12, -77, 57, 33, -38, -100, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, -98, Constants.SEMI_COLON, -16, -65, -17, 6, -18, -27, 95, 32, MappingData.PATH, -52, 60, 84, 74, 82, -108, 14, -64, 40, -10, 86, 96, -94, -29, 15, -20, -99, 36, OTACommand.RESP_ID_END_OTA, 126, -43, 124, -21, 24, -41, -51, -35, 120, -1, -37, -95, 9, -48, 118, OTACommand.RESP_ID_END_OTA_MD5, 117, -69, 29, 26, 47, -80, -2, -42, 52, 99, 53, -46, 42, 89, 109, 77, 119, -25, -114, 97, -49, -97, -50, 39, -11, OTACommand.RESP_ID_VERSION, -122, -57, -90, -5, -8, -121, -85, 98, 63, -33, 72, 0, 20, -102, -67, 91, 4, -110, 2, 37, 101, 76, 83, 12, -14, 41, -81, 23, 108, 65, 48, -23, -109, 85, -9, -84, 104, 38, -60, 125, -54, 122, 62, -96, 55, 3, -63, 54, 105, 102, 8, 22, -89, -68, -59, -45, 34, -73, 19, 70, 50, -24, 87, -120, 43, OTACommand.RESP_ID_START_OTA, -78, 78, 100, 28, -86, -111, 88, 46, -101, 92, 27, 81, 115, 66, 35, 1, 110, -13, 13, -66, 61, 10, 45, 31, 103, 51, 25, 123, 94, -22, -34, -117, -53, -87, -116, -115, -83, 73, OTACommand.RESP_ID_SEND_OTA, -28, -70, -61, 21, -47, -32, -119, -4, -79, -71, -75, 7, 121, -72, -31}, new byte[]{-78, -74, 35, 17, -89, -120, -59, -90, 57, -113, -60, -24, 115, 34, 67, -61, OTACommand.RESP_ID_SEND_OTA, 39, -51, 24, 81, 98, 45, -9, 92, 14, Constants.SEMI_COLON, -3, -54, -101, 13, 15, 121, -116, MappingData.PATH, 76, 116, 28, 10, -114, 124, -108, 7, -57, 94, 20, -95, 33, 87, 80, 78, -87, OTACommand.RESP_ID_VERSION, -39, -17, 100, 65, -49, 60, -18, 46, 19, 41, -70, 52, 90, -82, -118, 97, 51, 18, -71, 85, -88, 21, 5, -10, 3, 6, 73, -75, 37, 9, 22, 12, 42, 56, -4, 32, -12, -27, Byte.MAX_VALUE, -41, 49, 43, 102, 111, -1, 114, -122, -16, -93, 47, 120, 0, -68, -52, -30, -80, -15, 66, -76, 48, 95, 96, 4, -20, -91, -29, -117, -25, 29, -65, OTACommand.RESP_ID_END_OTA_MD5, 123, -26, OTACommand.RESP_ID_START_OTA, -8, -34, -40, -46, 23, -50, 75, 71, -42, 105, 108, 25, -103, -102, 1, -77, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, -79, -7, 89, -62, 55, -23, -56, -96, -19, 79, -119, 104, 109, -43, 38, -111, -121, 88, -67, -55, -104, -36, 117, -64, 118, -11, 103, 107, 126, -21, 82, -53, -47, 91, -97, 11, -37, 64, -110, 26, -6, -84, -28, -31, 113, 31, 101, -115, -105, -98, -107, -112, 93, -73, -63, -81, 84, -5, 2, -32, 53, -69, 58, 77, -83, Constants.COMMA, 61, 86, 8, 27, 74, -109, 106, -85, -72, 122, -14, 125, -38, 63, -2, 62, -66, -22, -86, 68, -58, -48, 54, 72, 112, -106, 119, 36, 83, -33, -13, OTACommand.RESP_ID_END_OTA, 40, 50, 69, 30, -92, -45, -94, 70, 110, -100, -35, 99, -44, -99}};

    public DSTU7624Engine(int blockBitLength) {
        if (blockBitLength == 128 || blockBitLength == 256 || blockBitLength == 512) {
            int i2 = blockBitLength / 64;
            this.d = i2;
            long[] jArr = new long[i2];
            this.a = jArr;
            this.h = new byte[((jArr.length * 64) / 8)];
            this.i = new byte[((jArr.length * 64) / 8)];
            return;
        }
        throw new IllegalArgumentException("unsupported block length: only 128/256/512 are allowed");
    }

    public void a(boolean forEncryption, CipherParameters params) {
        if (params instanceof KeyParameter) {
            this.g = forEncryption;
            byte[] keyBytes = ((KeyParameter) params).a();
            int keyBitLength = keyBytes.length * 8;
            int blockBitLength = this.d * 64;
            if (keyBitLength != 128 && keyBitLength != 256 && keyBitLength != 512) {
                throw new IllegalArgumentException("unsupported key length: only 128/256/512 are allowed");
            } else if (blockBitLength == 128 && keyBitLength == 512) {
                throw new IllegalArgumentException("Unsupported key length");
            } else if (blockBitLength == 256 && keyBitLength == 128) {
                throw new IllegalArgumentException("Unsupported key length");
            } else if (blockBitLength != 512 || keyBitLength == 512) {
                switch (keyBitLength) {
                    case 128:
                        this.f = 10;
                        break;
                    case 256:
                        this.f = 14;
                        break;
                    case 512:
                        this.f = 18;
                        break;
                }
                this.e = keyBitLength / 64;
                this.c = new long[(this.f + 1)][];
                int roundKeyIndex = 0;
                while (true) {
                    long[][] jArr = this.c;
                    if (roundKeyIndex < jArr.length) {
                        jArr[roundKeyIndex] = new long[this.d];
                        roundKeyIndex++;
                    } else {
                        int roundKeyIndex2 = this.e;
                        long[] jArr2 = new long[roundKeyIndex2];
                        this.b = jArr2;
                        if (keyBytes.length == (roundKeyIndex2 * 64) / 8) {
                            Pack.n(keyBytes, 0, jArr2);
                            long[] tempKeys = new long[this.d];
                            n(this.b, tempKeys);
                            m(this.b, tempKeys);
                            o();
                            return;
                        }
                        throw new IllegalArgumentException("Invalid key parameter passed to DSTU7624Engine init");
                    }
                }
            } else {
                throw new IllegalArgumentException("Unsupported key length");
            }
        } else {
            throw new IllegalArgumentException("Invalid parameter passed to DSTU7624Engine init");
        }
    }

    public String b() {
        return "DSTU7624";
    }

    public int c() {
        return (this.d * 64) / 8;
    }

    public int f(byte[] in, int inOff, byte[] out, int outOff) {
        if (this.b == null) {
            throw new IllegalStateException("DSTU7624 engine not initialised");
        } else if (c() + inOff > in.length) {
            throw new DataLengthException("Input buffer too short");
        } else if (c() + outOff <= out.length) {
            if (this.g) {
                Pack.n(in, inOff, this.a);
                for (int wordIndex = 0; wordIndex < this.d; wordIndex++) {
                    long[] jArr = this.a;
                    jArr[wordIndex] = jArr[wordIndex] + this.c[0][wordIndex];
                }
                for (int round = 1; round < this.f; round++) {
                    l();
                    k();
                    g(this.j);
                    for (int wordIndex2 = 0; wordIndex2 < this.d; wordIndex2++) {
                        long[] jArr2 = this.a;
                        jArr2[wordIndex2] = jArr2[wordIndex2] ^ this.c[round][wordIndex2];
                    }
                }
                l();
                k();
                g(this.j);
                for (int wordIndex3 = 0; wordIndex3 < this.d; wordIndex3++) {
                    long[] jArr3 = this.a;
                    jArr3[wordIndex3] = jArr3[wordIndex3] + this.c[this.f][wordIndex3];
                }
                Pack.t(this.a, out, outOff);
            } else {
                int round2 = this.f;
                Pack.n(in, inOff, this.a);
                for (int wordIndex4 = 0; wordIndex4 < this.d; wordIndex4++) {
                    long[] jArr4 = this.a;
                    jArr4[wordIndex4] = jArr4[wordIndex4] - this.c[round2][wordIndex4];
                }
                for (int round3 = this.f - 1; round3 > 0; round3--) {
                    g(this.k);
                    d();
                    e();
                    for (int wordIndex5 = 0; wordIndex5 < this.d; wordIndex5++) {
                        long[] jArr5 = this.a;
                        jArr5[wordIndex5] = jArr5[wordIndex5] ^ this.c[round3][wordIndex5];
                    }
                }
                g(this.k);
                d();
                e();
                for (int wordIndex6 = 0; wordIndex6 < this.d; wordIndex6++) {
                    long[] jArr6 = this.a;
                    jArr6[wordIndex6] = jArr6[wordIndex6] - this.c[0][wordIndex6];
                }
                Pack.t(this.a, out, outOff);
            }
            return c();
        } else {
            throw new OutputLengthException("Output buffer too short");
        }
    }

    public void reset() {
        Arrays.G(this.a, 0);
        Arrays.F(this.h, (byte) 0);
        Arrays.F(this.i, (byte) 0);
    }

    private void n(long[] workingKey, long[] tempKeys) {
        int i2 = this.d;
        long[] k0 = new long[i2];
        long[] k1 = new long[i2];
        long[] jArr = new long[i2];
        this.a = jArr;
        long j2 = jArr[0];
        int i3 = this.e;
        jArr[0] = j2 + ((long) (i2 + i3 + 1));
        if (i2 == i3) {
            System.arraycopy(workingKey, 0, k0, 0, k0.length);
            System.arraycopy(workingKey, 0, k1, 0, k1.length);
        } else {
            System.arraycopy(workingKey, 0, k0, 0, i2);
            int i4 = this.d;
            System.arraycopy(workingKey, i4, k1, 0, i4);
        }
        int wordIndex = 0;
        while (true) {
            long[] jArr2 = this.a;
            if (wordIndex >= jArr2.length) {
                break;
            }
            jArr2[wordIndex] = jArr2[wordIndex] + k0[wordIndex];
            wordIndex++;
        }
        l();
        k();
        g(this.j);
        int wordIndex2 = 0;
        while (true) {
            long[] jArr3 = this.a;
            if (wordIndex2 >= jArr3.length) {
                break;
            }
            jArr3[wordIndex2] = jArr3[wordIndex2] ^ k1[wordIndex2];
            wordIndex2++;
        }
        l();
        k();
        g(this.j);
        int wordIndex3 = 0;
        while (true) {
            long[] jArr4 = this.a;
            if (wordIndex3 < jArr4.length) {
                jArr4[wordIndex3] = jArr4[wordIndex3] + k0[wordIndex3];
                wordIndex3++;
            } else {
                l();
                k();
                g(this.j);
                System.arraycopy(this.a, 0, tempKeys, 0, this.d);
                return;
            }
        }
    }

    private void m(long[] workingKey, long[] tempKey) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = this.e;
        long[] initialData = new long[i6];
        int i7 = this.d;
        long[] tempRoundKey = new long[i7];
        long[] tmv = new long[i7];
        int round = 0;
        System.arraycopy(workingKey, 0, initialData, 0, i6);
        for (int wordIndex = 0; wordIndex < this.d; wordIndex++) {
            tmv[wordIndex] = 281479271743489L;
        }
        while (true) {
            System.arraycopy(tempKey, 0, this.a, 0, this.d);
            int wordIndex2 = 0;
            while (true) {
                i2 = this.d;
                if (wordIndex2 >= i2) {
                    break;
                }
                long[] jArr = this.a;
                jArr[wordIndex2] = jArr[wordIndex2] + tmv[wordIndex2];
                wordIndex2++;
            }
            System.arraycopy(this.a, 0, tempRoundKey, 0, i2);
            System.arraycopy(initialData, 0, this.a, 0, this.d);
            for (int wordIndex3 = 0; wordIndex3 < this.d; wordIndex3++) {
                long[] jArr2 = this.a;
                jArr2[wordIndex3] = jArr2[wordIndex3] + tempRoundKey[wordIndex3];
            }
            l();
            k();
            g(this.j);
            for (int wordIndex4 = 0; wordIndex4 < this.d; wordIndex4++) {
                long[] jArr3 = this.a;
                jArr3[wordIndex4] = jArr3[wordIndex4] ^ tempRoundKey[wordIndex4];
            }
            l();
            k();
            g(this.j);
            int wordIndex5 = 0;
            while (true) {
                i3 = this.d;
                if (wordIndex5 >= i3) {
                    break;
                }
                long[] jArr4 = this.a;
                jArr4[wordIndex5] = jArr4[wordIndex5] + tempRoundKey[wordIndex5];
                wordIndex5++;
            }
            System.arraycopy(this.a, 0, this.c[round], 0, i3);
            if (this.f != round) {
                if (this.d != this.e) {
                    round += 2;
                    j(tmv);
                    System.arraycopy(tempKey, 0, this.a, 0, this.d);
                    int wordIndex6 = 0;
                    while (true) {
                        i4 = this.d;
                        if (wordIndex6 >= i4) {
                            break;
                        }
                        long[] jArr5 = this.a;
                        jArr5[wordIndex6] = jArr5[wordIndex6] + tmv[wordIndex6];
                        wordIndex6++;
                    }
                    System.arraycopy(this.a, 0, tempRoundKey, 0, i4);
                    int i8 = this.d;
                    System.arraycopy(initialData, i8, this.a, 0, i8);
                    for (int wordIndex7 = 0; wordIndex7 < this.d; wordIndex7++) {
                        long[] jArr6 = this.a;
                        jArr6[wordIndex7] = jArr6[wordIndex7] + tempRoundKey[wordIndex7];
                    }
                    l();
                    k();
                    g(this.j);
                    for (int wordIndex8 = 0; wordIndex8 < this.d; wordIndex8++) {
                        long[] jArr7 = this.a;
                        jArr7[wordIndex8] = jArr7[wordIndex8] ^ tempRoundKey[wordIndex8];
                    }
                    l();
                    k();
                    g(this.j);
                    int wordIndex9 = 0;
                    while (true) {
                        i5 = this.d;
                        if (wordIndex9 >= i5) {
                            break;
                        }
                        long[] jArr8 = this.a;
                        jArr8[wordIndex9] = jArr8[wordIndex9] + tempRoundKey[wordIndex9];
                        wordIndex9++;
                    }
                    System.arraycopy(this.a, 0, this.c[round], 0, i5);
                    if (this.f == round) {
                        return;
                    }
                }
                round += 2;
                j(tmv);
                long temp = initialData[0];
                System.arraycopy(initialData, 1, initialData, 0, initialData.length - 1);
                initialData[initialData.length - 1] = temp;
            } else {
                return;
            }
        }
    }

    private void o() {
        for (int roundIndex = 1; roundIndex < this.f; roundIndex += 2) {
            long[][] jArr = this.c;
            System.arraycopy(jArr[roundIndex - 1], 0, jArr[roundIndex], 0, this.d);
            i(this.c[roundIndex]);
        }
    }

    private void l() {
        for (int i2 = 0; i2 < this.d; i2++) {
            long[] jArr = this.a;
            byte[][] bArr = this.l;
            jArr[i2] = (((long) bArr[0][(int) (jArr[i2] & 255)]) & 255) | ((((long) bArr[1][(int) ((jArr[i2] & 65280) >>> 8)]) << 8) & 65280) | ((((long) bArr[2][(int) ((jArr[i2] & 16711680) >>> 16)]) << 16) & 16711680) | ((((long) bArr[3][(int) ((jArr[i2] & 4278190080L) >>> 24)]) << 24) & 4278190080L) | ((((long) bArr[0][(int) ((jArr[i2] & 1095216660480L) >>> 32)]) << 32) & 1095216660480L) | ((((long) bArr[1][(int) ((jArr[i2] & 280375465082880L) >>> 40)]) << 40) & 280375465082880L) | ((((long) bArr[2][(int) ((jArr[i2] & 71776119061217280L) >>> 48)]) << 48) & 71776119061217280L) | ((((long) bArr[3][(int) ((jArr[i2] & -72057594037927936L) >>> 56)]) << 56) & -72057594037927936L);
        }
    }

    private void e() {
        for (int i2 = 0; i2 < this.d; i2++) {
            long[] jArr = this.a;
            byte[][] bArr = this.m;
            jArr[i2] = (((long) bArr[0][(int) (jArr[i2] & 255)]) & 255) | ((((long) bArr[1][(int) ((jArr[i2] & 65280) >>> 8)]) << 8) & 65280) | ((((long) bArr[2][(int) ((jArr[i2] & 16711680) >>> 16)]) << 16) & 16711680) | ((((long) bArr[3][(int) ((jArr[i2] & 4278190080L) >>> 24)]) << 24) & 4278190080L) | ((((long) bArr[0][(int) ((jArr[i2] & 1095216660480L) >>> 32)]) << 32) & 1095216660480L) | ((((long) bArr[1][(int) ((jArr[i2] & 280375465082880L) >>> 40)]) << 40) & 280375465082880L) | ((((long) bArr[2][(int) ((jArr[i2] & 71776119061217280L) >>> 48)]) << 48) & 71776119061217280L) | ((((long) bArr[3][(int) ((jArr[i2] & -72057594037927936L) >>> 56)]) << 56) & -72057594037927936L);
        }
    }

    private void k() {
        int shift = -1;
        Pack.t(this.a, this.h, 0);
        for (int row = 0; row < 8; row++) {
            if (row % (8 / this.d) == 0) {
                shift++;
            }
            int col = 0;
            while (true) {
                int i2 = this.d;
                if (col >= i2) {
                    break;
                }
                this.i[((((col + shift) % i2) * 64) / 8) + row] = this.h[((col * 64) / 8) + row];
                col++;
            }
        }
        Pack.n(this.i, 0, this.a);
    }

    private void d() {
        int shift = -1;
        Pack.t(this.a, this.h, 0);
        for (int row = 0; row < 8; row++) {
            if (row % (8 / this.d) == 0) {
                shift++;
            }
            int col = 0;
            while (true) {
                int i2 = this.d;
                if (col >= i2) {
                    break;
                }
                this.i[((col * 64) / 8) + row] = this.h[((((col + shift) % i2) * 64) / 8) + row];
                col++;
            }
        }
        Pack.n(this.i, 0, this.a);
    }

    private void g(byte[][] matrix) {
        Pack.t(this.a, this.h, 0);
        for (int col = 0; col < this.d; col++) {
            long result = 0;
            long shift = -72057594037927936L;
            for (int row = 7; row >= 0; row--) {
                byte product = 0;
                for (int b2 = 7; b2 >= 0; b2--) {
                    product = (byte) (h(this.h[((col * 64) / 8) + b2], matrix[row][b2]) ^ product);
                }
                result |= (((long) product) << ((row * 64) / 8)) & shift;
                shift >>>= 8;
            }
            this.a[col] = result;
        }
    }

    private byte h(byte x, byte y) {
        int u = x & 255;
        int v = y & 255;
        int r = (-(v & 1)) & u;
        for (int i2 = 1; i2 < 8; i2++) {
            u <<= 1;
            v >>>= 1;
            r ^= (-(v & 1)) & u;
        }
        int hi = 65280 & r;
        int r2 = r ^ (((((hi >>> 4) ^ hi) ^ (hi >>> 5)) ^ (hi >>> 6)) ^ (hi >>> 8));
        int hi2 = r2 & 3840;
        return (byte) (r2 ^ (((((hi2 >>> 4) ^ hi2) ^ (hi2 >>> 5)) ^ (hi2 >>> 6)) ^ (hi2 >>> 8)));
    }

    private void j(long[] value) {
        for (int i2 = 0; i2 < value.length; i2++) {
            value[i2] = value[i2] << 1;
        }
        for (int i3 = 0; i3 < value.length / 2; i3++) {
            long temp = value[i3];
            value[i3] = value[(value.length - i3) - 1];
            value[(value.length - i3) - 1] = temp;
        }
    }

    private void i(long[] value) {
        int rotateBytesLength = (value.length * 2) + 3;
        int bytesLength = value.length * 8;
        byte[] bytes = new byte[((value.length * 64) / 8)];
        Pack.t(value, bytes, 0);
        byte[] buffer = new byte[rotateBytesLength];
        System.arraycopy(bytes, 0, buffer, 0, rotateBytesLength);
        System.arraycopy(bytes, rotateBytesLength, bytes, 0, bytesLength - rotateBytesLength);
        System.arraycopy(buffer, 0, bytes, bytesLength - rotateBytesLength, rotateBytesLength);
        Pack.n(bytes, 0, value);
    }
}
