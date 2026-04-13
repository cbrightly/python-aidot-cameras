package com.google.firebase.messaging;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import androidx.annotation.GuardedBy;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import net.sqlcipher.database.SQLiteDatabase;

@KeepForSdk
public class FcmBroadcastProcessor {
    private static final String EXTRA_BINARY_DATA = "rawData";
    private static final String EXTRA_BINARY_DATA_BASE_64 = "gcm.rawData64";
    @GuardedBy("lock")
    private static WithinAppServiceConnection fcmServiceConn;
    private static final Object lock = new Object();
    private final Context context;
    private final Executor executor;

    public FcmBroadcastProcessor(Context context2) {
        this.context = context2;
        this.executor = x.c;
    }

    public FcmBroadcastProcessor(Context context2, ExecutorService executor2) {
        this.context = context2;
        this.executor = executor2;
    }

    @KeepForSdk
    public Task<Integer> process(Intent intent) {
        String binaryData64 = intent.getStringExtra(EXTRA_BINARY_DATA_BASE_64);
        if (binaryData64 != null) {
            intent.putExtra("rawData", Base64.decode(binaryData64, 0));
            intent.removeExtra(EXTRA_BINARY_DATA_BASE_64);
        }
        return startMessagingService(this.context, intent);
    }

    @SuppressLint({"InlinedApi"})
    public Task<Integer> startMessagingService(Context context2, Intent intent) {
        boolean isHighPriority = true;
        boolean subjectToBackgroundCheck = PlatformVersion.isAtLeastO() && context2.getApplicationInfo().targetSdkVersion >= 26;
        if ((intent.getFlags() & SQLiteDatabase.CREATE_IF_NECESSARY) == 0) {
            isHighPriority = false;
        }
        if (!subjectToBackgroundCheck || isHighPriority) {
            return Tasks.call(this.executor, new d(context2, intent)).continueWithTask(this.executor, new f(context2, intent));
        }
        return bindToMessagingService(context2, intent);
    }

    static /* synthetic */ Task lambda$startMessagingService$2(Context context2, Intent intent, Task r) {
        if (!PlatformVersion.isAtLeastO() || ((Integer) r.getResult()).intValue() != 402) {
            return r;
        }
        return bindToMessagingService(context2, intent).continueWith(x.c, e.a);
    }

    static /* synthetic */ Integer lambda$startMessagingService$1(Task t) {
        return 403;
    }

    private static Task<Integer> bindToMessagingService(Context context2, Intent intent) {
        if (Log.isLoggable(Constants.TAG, 3)) {
            Log.d(Constants.TAG, "Binding to service");
        }
        if (ServiceStarter.getInstance().hasWakeLockPermission(context2)) {
            WakeLockHolder.sendWakefulServiceIntent(context2, getServiceConnection(context2, "com.google.firebase.MESSAGING_EVENT"), intent);
        } else {
            getServiceConnection(context2, "com.google.firebase.MESSAGING_EVENT").sendIntent(intent);
        }
        return Tasks.forResult(-1);
    }

    private static WithinAppServiceConnection getServiceConnection(Context context2, String action) {
        WithinAppServiceConnection withinAppServiceConnection;
        synchronized (lock) {
            if (fcmServiceConn == null) {
                fcmServiceConn = new WithinAppServiceConnection(context2, action);
            }
            withinAppServiceConnection = fcmServiceConn;
        }
        return withinAppServiceConnection;
    }

    @VisibleForTesting
    public static void reset() {
        synchronized (lock) {
            fcmServiceConn = null;
        }
    }
}
