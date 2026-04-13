package smarthome.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;

public class LdsKillSelfService extends Service {
    private static long c = 50;
    private Handler d = new Handler();
    /* access modifiers changed from: private */
    public String f;

    public int onStartCommand(Intent intent, int flags, int startId) {
        PushAutoTrackHelper.onServiceStartCommand(this, intent, flags, startId);
        c = intent.getLongExtra("Delayed", 50);
        this.f = intent.getStringExtra("PackageName");
        this.d.postDelayed(new a(), c);
        return super.onStartCommand(intent, flags, startId);
    }

    public class a implements Runnable {
        a() {
        }

        public void run() {
            LdsKillSelfService.this.startActivity(LdsKillSelfService.this.getPackageManager().getLaunchIntentForPackage(LdsKillSelfService.this.f));
            LdsKillSelfService.this.stopSelf();
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
