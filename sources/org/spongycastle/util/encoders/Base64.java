package org.spongycastle.util.encoders;

import java.io.ByteArrayOutputStream;

public class Base64 {
    private static final Encoder a = new Base64Encoder();

    public static byte[] b(byte[] data) {
        return c(data, 0, data.length);
    }

    public static byte[] c(byte[] data, int off, int length) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream(((length + 2) / 3) * 4);
        try {
            a.a(data, off, length, bOut);
            return bOut.toByteArray();
        } catch (Exception e) {
            throw new EncoderException("exception encoding base64 string: " + e.getMessage(), e);
        }
    }

    public static byte[] a(String data) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream((data.length() / 4) * 3);
        try {
            a.b(data, bOut);
            return bOut.toByteArray();
        } catch (Exception e) {
            throw new DecoderException("unable to decode base64 string: " + e.getMessage(), e);
        }
    }
}
