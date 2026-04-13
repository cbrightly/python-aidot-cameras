package org.spongycastle.pqc.crypto.gmss.util;

import org.glassfish.grizzly.http.server.util.MappingData;

public class GMSSUtil {
    public byte[] c(int value) {
        return new byte[]{(byte) (value & 255), (byte) ((value >> 8) & 255), (byte) ((value >> 16) & 255), (byte) ((value >> 24) & 255)};
    }

    public int a(byte[] bytes, int offset) {
        int offset2 = offset + 1;
        int offset3 = offset2 + 1;
        return (bytes[offset] & 255) | ((bytes[offset2] & 255) << 8) | ((bytes[offset3] & 255) << MappingData.PATH) | ((bytes[offset3 + 1] & 255) << 24);
    }

    public byte[] b(byte[][] arraycp) {
        byte[] dest = new byte[(arraycp.length * arraycp[0].length)];
        int indx = 0;
        for (int i = 0; i < arraycp.length; i++) {
            System.arraycopy(arraycp[i], 0, dest, indx, arraycp[i].length);
            indx += arraycp[i].length;
        }
        return dest;
    }
}
