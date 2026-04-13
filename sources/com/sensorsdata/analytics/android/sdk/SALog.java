package com.sensorsdata.analytics.android.sdk;

import android.util.Log;

public class SALog {
    private static final int CHUNK_SIZE = 4000;
    private static boolean debug;
    private static boolean disableSDK;
    private static boolean enableLog;

    public static void d(String tag, String msg) {
        if (debug && !disableSDK) {
            info(tag, msg, (Throwable) null);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (debug && !disableSDK) {
            info(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (enableLog && !disableSDK) {
            info(tag, msg, (Throwable) null);
        }
    }

    public static void i(String tag, Throwable tr) {
        if (enableLog && !disableSDK) {
            info(tag, "", tr);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (enableLog && !disableSDK) {
            info(tag, msg, tr);
        }
    }

    public static void info(String tag, String msg, Throwable tr) {
        if (msg != null) {
            try {
                byte[] bytes = msg.getBytes();
                int length = bytes.length;
                if (length <= 4000) {
                    Log.i(tag, msg, tr);
                } else {
                    int index = 0;
                    while (index < length - 4000) {
                        int lastIndexOfLF = lastIndexOfLF(bytes, index);
                        int chunkLength = lastIndexOfLF - index;
                        Log.i(tag, new String(bytes, index, chunkLength), (Throwable) null);
                        if (chunkLength < 4000) {
                            index = lastIndexOfLF + 1;
                        } else {
                            index = lastIndexOfLF;
                        }
                    }
                    if (length > index) {
                        Log.i(tag, new String(bytes, index, length - index), tr);
                    }
                }
            } catch (Exception e) {
                printStackTrace(e);
            }
        } else {
            Log.i(tag, (String) null, tr);
        }
    }

    private static int lastIndexOfLF(byte[] bytes, int fromIndex) {
        int index = Math.min(fromIndex + 4000, bytes.length - 1);
        for (int i = index; i > index - 4000; i--) {
            if (bytes[i] == 10) {
                return i;
            }
        }
        return index;
    }

    public static void printStackTrace(Exception e) {
        if (enableLog && !disableSDK && e != null) {
            Log.e("SA.Exception", "", e);
        }
    }

    static void setDebug(boolean isDebug) {
        debug = isDebug;
    }

    public static void setEnableLog(boolean isEnableLog) {
        enableLog = isEnableLog;
    }

    public static void setDisableSDK(boolean configDisableSDK) {
        disableSDK = configDisableSDK;
    }

    public static boolean isLogEnabled() {
        return enableLog;
    }
}
