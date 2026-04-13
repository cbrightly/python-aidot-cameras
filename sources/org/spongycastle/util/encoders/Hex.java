package org.spongycastle.util.encoders;

import java.io.ByteArrayOutputStream;
import org.spongycastle.util.Strings;

public class Hex {
    private static final Encoder a = new HexEncoder();

    public static String d(byte[] data) {
        return e(data, 0, data.length);
    }

    public static String e(byte[] data, int off, int length) {
        return Strings.b(c(data, off, length));
    }

    public static byte[] b(byte[] data) {
        return c(data, 0, data.length);
    }

    public static byte[] c(byte[] data, int off, int length) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        try {
            a.a(data, off, length, bOut);
            return bOut.toByteArray();
        } catch (Exception e) {
            throw new EncoderException("exception encoding Hex string: " + e.getMessage(), e);
        }
    }

    public static byte[] a(String data) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        try {
            a.b(data, bOut);
            return bOut.toByteArray();
        } catch (Exception e) {
            throw new DecoderException("exception decoding Hex string: " + e.getMessage(), e);
        }
    }
}
