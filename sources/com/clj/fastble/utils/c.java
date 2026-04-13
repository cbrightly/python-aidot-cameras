package com.clj.fastble.utils;

/* compiled from: HexUtil */
public class c {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String a(byte[] data, boolean addSpace) {
        if (data == null || data.length < 1) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b2 : data) {
            String hex = Integer.toHexString(b2 & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
            if (addSpace) {
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }
}
