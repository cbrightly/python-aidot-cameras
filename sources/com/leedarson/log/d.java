package com.leedarson.log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Process;
import android.util.Log;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.maps.android.BuildConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import meshsdk.cache.CacheHandler;

/* compiled from: CrashHandler */
public class d implements Thread.UncaughtExceptionHandler {
    private static d c;
    public static ChangeQuickRedirect changeQuickRedirect;
    public static String d;
    private a a1;
    private Thread.UncaughtExceptionHandler f;
    private boolean p0;
    private Context q;
    private Map<String, String> x = new HashMap();
    private DateFormat y = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    private String z = "crashLog.log";

    /* compiled from: CrashHandler */
    public interface a {
        void a(Thread thread, Throwable th);
    }

    public static d b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 1132, new Class[0], d.class);
        if (proxy.isSupported) {
            return (d) proxy.result;
        }
        if (c == null) {
            synchronized (d.class) {
                if (c == null) {
                    c = new d();
                }
            }
        }
        return c;
    }

    public void e(Context context, boolean debug) {
        if (!PatchProxy.proxy(new Object[]{context, new Byte(debug ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 1133, new Class[]{Context.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            this.q = context;
            this.p0 = debug;
            d = String.format(Locale.US, "%s/crashLog/", new Object[]{context.getApplicationContext().getFilesDir().getPath()});
            this.f = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
            File dir = new File(d);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
        if (!PatchProxy.proxy(new Object[]{thread, ex}, this, changeQuickRedirect, false, 1134, new Class[]{Thread.class, Throwable.class}, Void.TYPE).isSupported) {
            if (!d(ex) && (uncaughtExceptionHandler = this.f) != null) {
                uncaughtExceptionHandler.uncaughtException(thread, ex);
            } else if (this.p0) {
                try {
                    Thread.sleep(CacheHandler.delayTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Process.killProcess(Process.myPid());
                System.exit(1);
            } else {
                a aVar = this.a1;
                if (aVar != null) {
                    aVar.a(thread, ex);
                }
            }
        }
    }

    @SuppressLint({"WrongConstant"})
    private boolean d(Throwable ex) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ex}, this, changeQuickRedirect, false, 1135, new Class[]{Throwable.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (ex == null) {
            return false;
        }
        com.leedarson.log.elk.a.y(this).o("error").t("FrameWork").p(c(ex)).u("app_crashed_reason", c(ex)).x("AppCrash").b(10).a().b();
        a(this.q.getApplicationContext());
        String f2 = f(ex);
        return true;
    }

    public static String c(Throwable throwable) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{throwable}, (Object) null, changeQuickRedirect, true, 1136, new Class[]{Throwable.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }

    public void a(Context ctx) {
        if (!PatchProxy.proxy(new Object[]{ctx}, this, changeQuickRedirect, false, 1137, new Class[]{Context.class}, Void.TYPE).isSupported) {
            try {
                PackageInfo pi = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 1);
                if (pi != null) {
                    String versionName = pi.versionName;
                    if (versionName == null) {
                        versionName = BuildConfig.TRAVIS;
                    }
                    this.x.put("versionName", versionName);
                    this.x.put("versionCode", pi.versionCode + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (Field field : Build.class.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    this.x.put(field.getName(), field.get((Object) null).toString());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private String f(Throwable th) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{th}, this, changeQuickRedirect, false, 1138, new Class[]{Throwable.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Throwable ex = th;
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : this.x.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        sb.append(Progress.DATE);
        sb.append("=");
        sb.append(simpleDateFormat.format(date));
        sb.append("\n");
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        for (Throwable cause = ex.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        printWriter.close();
        sb.append(writer.toString());
        FileOutputStream fos = null;
        try {
            String path = d;
            File dir = new File(path);
            if (!dir.exists()) {
                Log.d("CrashHandler", "mkdirs ");
                dir.mkdirs();
            }
            this.z = "crash_" + System.currentTimeMillis() + ".log";
            File logFile = new File(path, this.z);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            FileOutputStream fos2 = new FileOutputStream(logFile, true);
            fos2.write(sb.toString().getBytes());
            fos2.flush();
            String str = this.z;
            try {
                fos2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        } catch (Exception e2) {
            e2.printStackTrace();
            if (fos == null) {
                return null;
            }
            try {
                fos.close();
                return null;
            } catch (IOException e3) {
                e3.printStackTrace();
                return null;
            }
        } catch (Throwable th2) {
            Throwable th3 = th2;
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th3;
        }
    }

    public void g(a handlerExceptionListener) {
        this.a1 = handlerExceptionListener;
    }
}
