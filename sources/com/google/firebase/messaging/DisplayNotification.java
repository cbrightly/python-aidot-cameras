package com.google.firebase.messaging;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.messaging.CommonNotificationBuilder;
import com.google.firebase.messaging.Constants;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DisplayNotification {
    private static final int IMAGE_DOWNLOAD_TIMEOUT_SECONDS = 5;
    private final Context context;
    private final ExecutorService networkIoExecutor;
    private final NotificationParams params;

    public DisplayNotification(Context context2, NotificationParams params2, ExecutorService networkIoExecutor2) {
        this.networkIoExecutor = networkIoExecutor2;
        this.context = context2;
        this.params = params2;
    }

    private boolean isAppForeground() {
        if (((KeyguardManager) this.context.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
            return false;
        }
        if (!PlatformVersion.isAtLeastLollipop()) {
            SystemClock.sleep(10);
        }
        int pid = Process.myPid();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = ((ActivityManager) this.context.getSystemService("activity")).getRunningAppProcesses();
        if (appProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo process : appProcesses) {
                if (process.pid == pid) {
                    if (process.importance == 100) {
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean handleNotification() {
        if (this.params.getBoolean(Constants.MessageNotificationKeys.NO_UI)) {
            return true;
        }
        if (isAppForeground()) {
            return false;
        }
        ImageDownload imageDownload = startImageDownloadInBackground();
        CommonNotificationBuilder.DisplayNotificationInfo notificationInfo = CommonNotificationBuilder.createNotificationInfo(this.context, this.params);
        waitForAndApplyImageDownload(notificationInfo.notificationBuilder, imageDownload);
        showNotification(notificationInfo);
        return true;
    }

    @Nullable
    private ImageDownload startImageDownloadInBackground() {
        ImageDownload imageDownload = ImageDownload.create(this.params.getString(Constants.MessageNotificationKeys.IMAGE_URL));
        if (imageDownload != null) {
            imageDownload.start(this.networkIoExecutor);
        }
        return imageDownload;
    }

    private void waitForAndApplyImageDownload(NotificationCompat.Builder n, @Nullable ImageDownload imageDownload) {
        if (imageDownload != null) {
            try {
                Bitmap bitmap = (Bitmap) Tasks.await(imageDownload.getTask(), 5, TimeUnit.SECONDS);
                n.setLargeIcon(bitmap);
                n.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon((Bitmap) null));
            } catch (ExecutionException e) {
                Log.w(Constants.TAG, "Failed to download image: " + e.getCause());
            } catch (InterruptedException e2) {
                Log.w(Constants.TAG, "Interrupted while downloading image, showing notification without it");
                imageDownload.close();
                Thread.currentThread().interrupt();
            } catch (TimeoutException e3) {
                Log.w(Constants.TAG, "Failed to download image in time, showing notification without it");
                imageDownload.close();
            }
        }
    }

    private void showNotification(CommonNotificationBuilder.DisplayNotificationInfo info) {
        if (Log.isLoggable(Constants.TAG, 3)) {
            Log.d(Constants.TAG, "Showing notification");
        }
        NotificationManager notificationManager = (NotificationManager) this.context.getSystemService("notification");
        String str = info.tag;
        int i = info.id;
        Notification build = info.notificationBuilder.build();
        notificationManager.notify(str, i, build);
        PushAutoTrackHelper.onNotify(notificationManager, str, i, build);
    }
}
