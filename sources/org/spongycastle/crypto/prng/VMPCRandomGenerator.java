package org.spongycastle.crypto.prng;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.util.Constants;

public class VMPCRandomGenerator implements RandomGenerator {
    private byte a = 0;
    private byte[] b = {-69, Constants.COMMA, 98, Byte.MAX_VALUE, -75, -86, -44, 13, OTACommand.RESP_ID_START_OTA, -2, -78, OTACommand.RESP_ID_SEND_OTA, -53, -96, -95, 8, 24, 113, 86, -24, 73, 2, MappingData.PATH, -60, -34, 53, -91, -20, OTACommand.RESP_ID_VERSION, 18, -72, 105, -38, 47, 117, -52, -94, 9, 54, 3, 97, 45, -3, -32, -35, 5, 67, -112, -83, -56, -31, -81, 87, -101, 76, -40, 81, -82, 80, OTACommand.RESP_CMD_ID_SEND_OTA_POINT, 60, 10, -28, -13, -100, 38, 35, 83, -55, OTACommand.RESP_ID_END_OTA, -105, 70, -79, -103, 100, 49, 119, -43, 29, -42, 120, -67, 94, -80, -118, 34, 56, -8, 104, 43, 42, -59, -45, -9, -68, 111, -33, 4, -27, -107, 62, 37, -122, -90, 11, -113, -15, 36, 14, -41, 64, -77, -49, 126, 6, 21, -102, 77, 28, -93, -37, 50, -110, 88, 17, 39, -12, 89, -48, 78, 106, 23, 91, -84, -1, 7, -64, 101, 121, -4, -57, -51, 118, 66, 93, -25, 58, 52, 122, 48, 40, 15, 115, 1, -7, -47, -46, 25, -23, -111, -71, 90, -19, 65, 109, -76, -61, -98, -65, 99, -6, 31, 51, 96, 71, -119, -16, -106, 26, 95, -109, 61, 55, 75, -39, -88, -63, 27, -10, 57, -117, -73, 12, 32, -50, -120, 110, -74, 116, -114, -115, 22, 41, -14, -121, -11, -21, 112, -29, -5, 85, -97, -58, 68, 74, 69, 125, -30, 107, 92, 108, 102, -87, -116, -18, OTACommand.RESP_ID_END_OTA_MD5, 19, -89, 30, -99, -36, 103, 72, -70, 46, -26, -92, -85, 124, -108, 0, 33, -17, -22, -66, -54, 114, 79, 82, -104, 63, -62, 20, 123, Constants.SEMI_COLON, 84};
    private byte c = -66;

    public void a(byte[] bytes) {
        b(bytes, 0, bytes.length);
    }

    public void b(byte[] bytes, int start, int len) {
        synchronized (this.b) {
            int end = start + len;
            for (int i = start; i != end; i++) {
                byte[] bArr = this.b;
                byte b2 = this.c;
                byte b3 = this.a;
                byte b4 = bArr[(b2 + bArr[b3 & 255]) & 255];
                this.c = b4;
                bytes[i] = bArr[(bArr[bArr[b4 & 255] & 255] + 1) & 255];
                byte temp = bArr[b3 & 255];
                bArr[b3 & 255] = bArr[b4 & 255];
                bArr[b4 & 255] = temp;
                this.a = (byte) ((b3 + 1) & 255);
            }
        }
    }
}
