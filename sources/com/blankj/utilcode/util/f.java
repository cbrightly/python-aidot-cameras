package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: ConvertUtils */
public final class f {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String c(byte[] bytes) {
        return d(bytes, true);
    }

    public static String d(byte[] bytes, boolean isUpperCase) {
        if (bytes == null) {
            return "";
        }
        char[] hexDigits = isUpperCase ? a : b;
        int len = bytes.length;
        if (len <= 0) {
            return "";
        }
        char[] ret = new char[(len << 1)];
        int j = 0;
        for (int i = 0; i < len; i++) {
            int j2 = j + 1;
            ret[j] = hexDigits[(bytes[i] >> 4) & 15];
            j = j2 + 1;
            ret[j2] = hexDigits[bytes[i] & 15];
        }
        return new String(ret);
    }

    public static double b(long byteSize, int unit) {
        if (byteSize < 0) {
            return -1.0d;
        }
        return ((double) byteSize) / ((double) unit);
    }

    @SuppressLint({"DefaultLocale"})
    public static String a(long byteSize, int precision) {
        if (precision < 0) {
            throw new IllegalArgumentException("precision shouldn't be less than zero!");
        } else if (byteSize < 0) {
            throw new IllegalArgumentException("byteSize shouldn't be less than zero!");
        } else if (byteSize < 1024) {
            return String.format("%." + precision + "fB", new Object[]{Double.valueOf((double) byteSize)});
        } else if (byteSize < 1048576) {
            return String.format("%." + precision + "fKB", new Object[]{Double.valueOf(((double) byteSize) / 1024.0d)});
        } else if (byteSize < IjkMediaMeta.AV_CH_STEREO_RIGHT) {
            return String.format("%." + precision + "fMB", new Object[]{Double.valueOf(((double) byteSize) / 1048576.0d)});
        } else {
            return String.format("%." + precision + "fGB", new Object[]{Double.valueOf(((double) byteSize) / 1.073741824E9d)});
        }
    }

    public static ByteArrayOutputStream g(InputStream is) {
        if (is == null) {
            return null;
        }
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b2 = new byte[8192];
            while (true) {
                int read = is.read(b2, 0, 8192);
                int len = read;
                if (read != -1) {
                    os.write(b2, 0, len);
                } else {
                    try {
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            is.close();
            return os;
        } catch (IOException e2) {
            e2.printStackTrace();
            try {
                is.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            return null;
        } catch (Throwable th) {
            try {
                is.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            throw th;
        }
    }

    public static String h(InputStream is, String charsetName) {
        if (is == null) {
            return "";
        }
        try {
            ByteArrayOutputStream baos = g(is);
            if (baos == null) {
                return "";
            }
            return baos.toString(f(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static InputStream j(String string, String charsetName) {
        if (string == null) {
            return null;
        }
        try {
            return new ByteArrayInputStream(string.getBytes(f(charsetName)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int e(float dpValue) {
        return h0.f(dpValue);
    }

    public static int i(float pxValue) {
        return h0.H(pxValue);
    }

    private static String f(String charsetName) {
        String cn = charsetName;
        if (h0.E(charsetName) || !Charset.isSupported(charsetName)) {
            return "UTF-8";
        }
        return cn;
    }
}
