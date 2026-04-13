package org.eclipse.paho.client.mqttv3.internal.security;

import org.glassfish.grizzly.http.server.util.MappingData;

/* compiled from: SimpleBase64Encoder */
public class b {
    private static final char[] a = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    public static String b(byte[] bytes) {
        int len = bytes.length;
        StringBuffer encoded = new StringBuffer(((len + 2) / 3) * 4);
        int i = 0;
        int j = len;
        while (j >= 3) {
            encoded.append(d((long) (((bytes[i] & 255) << MappingData.PATH) | ((bytes[i + 1] & 255) << 8) | (bytes[i + 2] & 255)), 4));
            i += 3;
            j -= 3;
        }
        if (j == 2) {
            encoded.append(d((long) (((bytes[i] & 255) << 8) | (bytes[i + 1] & 255)), 3));
        }
        if (j == 1) {
            encoded.append(d((long) (bytes[i] & 255), 2));
        }
        return encoded.toString();
    }

    public static byte[] a(String string) {
        byte[] encoded = string.getBytes();
        int len = encoded.length;
        byte[] decoded = new byte[((len * 3) / 4)];
        int i = 0;
        int j = len;
        int k = 0;
        while (j >= 4) {
            long d = c(encoded, i, 4);
            j -= 4;
            i += 4;
            for (int l = 2; l >= 0; l--) {
                decoded[k + l] = (byte) ((int) (d & 255));
                d >>= 8;
            }
            k += 3;
        }
        if (j == 3) {
            long d2 = c(encoded, i, 3);
            for (int l2 = 1; l2 >= 0; l2--) {
                decoded[k + l2] = (byte) ((int) (d2 & 255));
                d2 >>= 8;
            }
        }
        if (j == 2) {
            decoded[k] = (byte) ((int) (c(encoded, i, 2) & 255));
        }
        return decoded;
    }

    private static final String d(long input, int size) {
        StringBuffer result = new StringBuffer(size);
        while (size > 0) {
            size--;
            result.append(a[(int) (63 & input)]);
            input >>= 6;
        }
        return result.toString();
    }

    private static final long c(byte[] encoded, int idx, int size) {
        long res = 0;
        int f = 0;
        while (size > 0) {
            size--;
            long r = 0;
            int idx2 = idx + 1;
            byte d = encoded[idx];
            if (d == 47) {
                r = 1;
            }
            if (d >= 48 && d <= 57) {
                r = (long) ((d + 2) - 48);
            }
            if (d >= 65 && d <= 90) {
                r = (long) ((d + 12) - 65);
            }
            if (d >= 97 && d <= 122) {
                r = (long) ((d + 38) - 97);
            }
            res += r << f;
            f += 6;
            idx = idx2;
        }
        return res;
    }
}
