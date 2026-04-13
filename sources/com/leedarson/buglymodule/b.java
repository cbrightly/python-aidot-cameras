package com.leedarson.buglymodule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.util.Log;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.maps.android.BuildConfig;
import com.leedarson.log.e;
import com.leedarson.log.elk.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tencent.bugly.crashreport.CrashReport;
import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* compiled from: BuglyCrashHandlerCallback */
public class b extends CrashReport.CrashHandleCallback {
    public static ChangeQuickRedirect changeQuickRedirect;
    String a;
    private Context b;

    public b(Context context) {
        this.b = context;
        this.a = String.format(Locale.US, "%s/buglyLog/", new Object[]{context.getApplicationContext().getFilesDir().getPath()});
    }

    @SuppressLint({"WrongConstant"})
    public Map<String, String> onCrashHandleStart(int crashType, String errorType, String errorMessage, String errorStack) {
        Class<String> cls = String.class;
        Object[] objArr = {new Integer(crashType), errorType, errorMessage, errorStack};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1123, new Class[]{Integer.TYPE, cls, cls, cls}, Map.class);
        if (proxy.isSupported) {
            return (Map) proxy.result;
        }
        HashMap<String, String> map = new HashMap<>();
        Log.e("Bugly", "BuglyCrashHandlerCallback#onCrashHandleStart");
        HashMap<String, Object> errMap = new HashMap<>();
        a(this.b, errMap);
        errMap.put("crashType", String.valueOf(crashType));
        errMap.put("errorType", errorType);
        errMap.put("errorMessage", errorMessage);
        errMap.put("errorStack", errorStack);
        errMap.put("origin", "Bugly");
        b(errMap);
        a.y(this).o("error").t("FrameWork").q(errMap).u("app_crashed_reason", errorMessage).x("AppCrash").b(10).a().b();
        return map;
    }

    public void a(Context ctx, Map<String, Object> infos) {
        if (!PatchProxy.proxy(new Object[]{ctx, infos}, this, changeQuickRedirect, false, 1124, new Class[]{Context.class, Map.class}, Void.TYPE).isSupported) {
            try {
                PackageInfo pi = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 1);
                if (pi != null) {
                    String versionName = pi.versionName;
                    if (versionName == null) {
                        versionName = BuildConfig.TRAVIS;
                    }
                    infos.put("versionName", versionName);
                    infos.put("versionCode", pi.versionCode + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (Field field : Build.class.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    infos.put(field.getName(), field.get((Object) null).toString());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private String b(Map<String, Object> infos) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{infos}, this, changeQuickRedirect, false, 1125, new Class[]{Map.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : infos.entrySet()) {
            String value = entry.getValue().toString();
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(value);
            sb.append("\n");
        }
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        sb.append(Progress.DATE);
        sb.append("=");
        sb.append(time);
        sb.append("\n");
        File dir = new File(this.a);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Log.e("Bugly", "BuglyCrashHandlerCallback#\n" + sb.toString());
        e.d(dir, "crash_" + time + ".log", sb.toString());
        return null;
    }
}
