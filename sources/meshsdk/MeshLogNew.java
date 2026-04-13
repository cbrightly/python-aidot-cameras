package meshsdk;

import timber.log.a;

public class MeshLogNew {
    private static final int DEBUG = 1;
    private static final int ERROR = 4;
    private static final int INFO = 2;
    public static String MESHJSON_TAG = "MeshJsonLog";
    private static final int VERBOSE = 0;
    private static final int WARN = 3;
    public static String defaultTag = "SIGMeshNew";
    public static boolean isPrint = true;
    private static boolean openPrint = false;

    public static void d(String msg) {
        a.b g = a.g(defaultTag);
        g.a("" + msg, new Object[0]);
    }

    public static void i(String msg) {
        MeshLog.i(msg);
    }

    public static void w(String msg) {
        MeshLog.w(msg);
    }

    public static void e(String msg) {
        MeshLog.e(msg);
    }

    public static void v(String msg) {
        MeshLog.v(msg);
    }

    public static void meshMsg(String msg) {
        MeshLog.v("mesh消息:" + msg);
    }

    public static void ota(String msg) {
        MeshLog.v("MeshOTA:" + msg);
    }

    public static void otaWarn(String msg) {
        MeshLog.w("MeshOTA:" + msg);
    }

    public static void debugInfo(String msg) {
        d(msg);
    }

    public static void meshJsonLog(String msg) {
        MeshLog.i(MESHJSON_TAG + ":" + msg);
    }
}
