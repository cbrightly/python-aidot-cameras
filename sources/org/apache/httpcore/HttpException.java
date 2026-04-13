package org.apache.httpcore;

public class HttpException extends Exception {
    private static final long serialVersionUID = -5437299376222011036L;

    static String clean(String message) {
        char[] chars = message.toCharArray();
        int i = 0;
        while (i < chars.length && chars[i] >= ' ') {
            i++;
        }
        if (i == chars.length) {
            return message;
        }
        StringBuilder builder = new StringBuilder(chars.length * 2);
        for (int i2 = 0; i2 < chars.length; i2++) {
            char ch = chars[i2];
            if (ch < ' ') {
                builder.append("[0x");
                String hexString = Integer.toHexString(i2);
                if (hexString.length() == 1) {
                    builder.append("0");
                }
                builder.append(hexString);
                builder.append("]");
            } else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    public HttpException() {
    }

    public HttpException(String message) {
        super(clean(message));
    }

    public HttpException(String message, Throwable cause) {
        super(clean(message));
        initCause(cause);
    }
}
