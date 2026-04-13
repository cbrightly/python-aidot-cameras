package smarthome.service;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Process;
import com.leedarson.base.application.BaseApplication;

/* compiled from: RestartAPPTool */
public class a {
    public static void a(Context context, long Delayed) {
        if (!BaseApplication.d) {
            Intent intent1 = new Intent(context, LdsKillSelfService.class);
            intent1.putExtra("PackageName", context.getPackageName());
            intent1.putExtra("Delayed", Delayed);
            if (Build.VERSION.SDK_INT >= 26) {
                context.startForegroundService(intent1);
            } else {
                context.startService(intent1);
            }
        }
        Process.killProcess(Process.myPid());
    }
}
