package com.tutk.IOTC;

import android.util.Log;
import androidx.core.view.MotionEventCompat;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;

public class ByteUtil {
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i = begin; i < begin + count; i++) {
            bs[i - begin] = src[i];
        }
        return bs;
    }

    public static byte[] objectToByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(byteArrayOutputStream2);
            objectOutputStream2.writeObject(obj);
            objectOutputStream2.flush();
            bytes = byteArrayOutputStream2.toByteArray();
            try {
                objectOutputStream2.close();
            } catch (IOException e) {
                Log.e("====close Stream failed", e.toString());
            }
            try {
                byteArrayOutputStream2.close();
            } catch (IOException e2) {
                Log.e("====close Stream failed", e2.toString());
            }
        } catch (IOException e3) {
            Log.e("====objectToByte failed", e3.toString());
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e4) {
                    Log.e("====close Stream failed", e4.toString());
                }
            }
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
        } catch (Throwable th) {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e5) {
                    Log.e("====close Stream failed", e5.toString());
                }
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e6) {
                    Log.e("====close Stream failed", e6.toString());
                }
            }
            throw th;
        }
        return bytes;
    }

    public static Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
            return obj;
        } catch (IOException ex) {
            ex.printStackTrace();
            return obj;
        } catch (ClassNotFoundException ex2) {
            ex2.printStackTrace();
            return obj;
        }
    }

    public static short byteToShort(byte[] b) {
        return (short) (((short) (b[0] & 255)) | ((short) (((short) (b[1] & 255)) << 8)));
    }

    public static short getShort(byte[] b, int index) {
        return (short) ((b[index + 1] << 8) | (b[index + 0] & 255));
    }

    public static int byte2int(byte[] res) {
        return (res[0] & 255) | ((res[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((res[2] << 24) >>> 8) | (res[3] << 24);
    }

    public static int byte2intBig(byte[] res) {
        return (res[3] & 255) | ((res[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((res[1] << 24) >>> 8) | (res[0] << 24);
    }

    public static long byte2long(byte[] res) {
        long num = 0;
        for (int ix = 7; ix >= 0; ix--) {
            num = (num << 8) | ((long) (res[ix] & 255));
        }
        return num;
    }

    public static long byte2longBig(byte[] res) {
        long num = 0;
        for (int ix = 0; ix < 8; ix++) {
            num = (num << 8) | ((long) (res[ix] & 255));
        }
        return num;
    }

    public static byte[] intToBytes(int value) {
        byte[] src = new byte[4];
        src[3] = (byte) ((value >> 24) & 255);
        src[2] = (byte) ((value >> 16) & 255);
        src[1] = (byte) ((value >> 8) & 255);
        src[0] = (byte) (value & 255);
        return src;
    }

    public static byte[] intToBytes2(int value) {
        return new byte[]{(byte) ((value >> 24) & 255), (byte) ((value >> 16) & 255), (byte) ((value >> 8) & 255), (byte) (value & 255)};
    }

    public static String getHexBinString(byte[] bs) {
        StringBuffer log = new StringBuffer();
        for (int i = 0; i < bs.length; i++) {
            log.append(String.format(Locale.US, "%02X", new Object[]{Byte.valueOf(bs[i])}) + " ");
        }
        return log.toString();
    }

    public static String byteToString(byte[] actualData) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < actualData.length; i++) {
            sb.append(actualData[i] + ",");
        }
        return sb.toString();
    }

    public static String byte2String(byte[] byteArray) {
        char[] result = new char[(byteArray.length * 2)];
        for (int i = 0; i < byteArray.length; i++) {
            char temp = (char) (((byteArray[i] & 240) >> 4) & 15);
            result[i * 2] = (char) (temp > 9 ? (temp + 'A') - 10 : temp + '0');
            char temp2 = (char) (byteArray[i] & 15);
            result[(i * 2) + 1] = (char) (temp2 > 9 ? (temp2 + 'A') - 10 : temp2 + '0');
        }
        return new String(result);
    }

    public static boolean byteAllZero(byte[] actualData) {
        for (byte b : actualData) {
            if (b != 0) {
                return false;
            }
        }
        return true;
    }

    public static int[] byteToIntArray(byte[] actualData) {
        int[] result = new int[actualData.length];
        for (int i = 0; i < actualData.length; i++) {
            result[i] = actualData[i];
        }
        return result;
    }

    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
        byte[] byte_3 = new byte[(byte_1.length + byte_2.length)];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }

    public static String[] stringsMerger(String[] strings_1, String[] strings_2) {
        String[] strings_3 = new String[(strings_1.length + strings_2.length)];
        System.arraycopy(strings_1, 0, strings_3, 0, strings_1.length);
        System.arraycopy(strings_2, 0, strings_3, strings_1.length, strings_2.length);
        return strings_3;
    }
}
