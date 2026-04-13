package meshsdk;

import android.os.Process;
import com.leedarson.bean.Constants;
import meshsdk.util.ProcedureCollector;
import timber.log.a;

public class MeshLog {
    private static final int DEBUG = 1;
    private static final int ERROR = 4;
    private static final int INFO = 2;
    private static final int VERBOSE = 0;
    private static final int WARN = 3;
    public static String defaultTag = Constants.SERVICE_SIG_MESH;
    public static boolean isPrint = true;
    private static boolean openPrint = false;

    public static void d(String msg) {
        a.b g = a.g(defaultTag);
        g.a("" + msg + ",thread:" + Thread.currentThread().getName(), new Object[0]);
    }

    public static void i(String msg) {
        a.b g = a.g(defaultTag);
        g.h("" + msg, new Object[0]);
    }

    public static void logMusicRhythm(String msg) {
        a.b g = a.g(defaultTag);
        g.h("musicRhythm->" + msg + ",thread:" + Thread.currentThread().getName(), new Object[0]);
    }

    public static void logMusicRhythmWarn(String msg) {
        a.b g = a.g(defaultTag);
        g.n("musicRhythm->" + msg, new Object[0]);
    }

    public static void logAddGroup(String msg) {
        a.b g = a.g(defaultTag);
        g.h("addGroup/deleteGroup->" + msg, new Object[0]);
    }

    public static void w(String msg) {
        a.b g = a.g(defaultTag);
        g.n("" + msg, new Object[0]);
    }

    public static void e(String msg) {
        a.b g = a.g(defaultTag);
        g.c("" + msg, new Object[0]);
    }

    public static void v(String msg) {
        a.b g = a.g(defaultTag);
        g.m("" + msg, new Object[0]);
    }

    public static void log(String msg, int level, String tag) {
        switch (level) {
            case 0:
                a.g(tag).m(msg, new Object[0]);
                break;
            case 1:
                a.g(tag).a(msg, new Object[0]);
                break;
            case 2:
                a.g(tag).h(msg, new Object[0]);
                break;
            case 3:
                a.g(tag).n(msg, new Object[0]);
                break;
            case 4:
                a.g(tag).c(msg, new Object[0]);
                break;
        }
        ProcedureCollector.addLog(msg, tag, (long) Process.myTid());
    }

    public static void debugInfo(String msg) {
        d(msg);
    }
}
