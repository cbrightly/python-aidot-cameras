package androidx.core.app;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.INotificationSideChannel;

public abstract class NotificationCompatSideChannelService extends Service {
    public abstract void cancel(String str, int i, String str2);

    public abstract void cancelAll(String str);

    public abstract void notify(String str, int i, String str2, Notification notification);

    public IBinder onBind(Intent intent) {
        if (!intent.getAction().equals(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL) || Build.VERSION.SDK_INT > 19) {
            return null;
        }
        return new NotificationSideChannelStub();
    }

    public class NotificationSideChannelStub extends INotificationSideChannel.Stub {
        NotificationSideChannelStub() {
        }

        public void notify(String packageName, int id, String tag, Notification notification) {
            NotificationCompatSideChannelService.this.checkPermission(Binder.getCallingUid(), packageName);
            long idToken = Binder.clearCallingIdentity();
            try {
                NotificationCompatSideChannelService.this.notify(packageName, id, tag, notification);
            } finally {
                Binder.restoreCallingIdentity(idToken);
            }
        }

        public void cancel(String packageName, int id, String tag) {
            NotificationCompatSideChannelService.this.checkPermission(Binder.getCallingUid(), packageName);
            long idToken = Binder.clearCallingIdentity();
            try {
                NotificationCompatSideChannelService.this.cancel(packageName, id, tag);
            } finally {
                Binder.restoreCallingIdentity(idToken);
            }
        }

        public void cancelAll(String packageName) {
            NotificationCompatSideChannelService.this.checkPermission(Binder.getCallingUid(), packageName);
            long idToken = Binder.clearCallingIdentity();
            try {
                NotificationCompatSideChannelService.this.cancelAll(packageName);
            } finally {
                Binder.restoreCallingIdentity(idToken);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void checkPermission(int callingUid, String packageName) {
        String[] packagesForUid = getPackageManager().getPackagesForUid(callingUid);
        int length = packagesForUid.length;
        int i = 0;
        while (i < length) {
            if (!packagesForUid[i].equals(packageName)) {
                i++;
            } else {
                return;
            }
        }
        throw new SecurityException("NotificationSideChannelService: Uid " + callingUid + " is not authorized for package " + packageName);
    }
}
