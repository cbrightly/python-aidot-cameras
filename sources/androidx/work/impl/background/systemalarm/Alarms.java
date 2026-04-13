package androidx.work.impl.background.systemalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.core.app.NotificationCompat;
import androidx.work.Logger;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.SystemIdInfo;
import androidx.work.impl.model.SystemIdInfoDao;
import androidx.work.impl.utils.IdGenerator;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class Alarms {
    private static final String TAG = Logger.tagWithPrefix("Alarms");

    public static void setAlarm(@NonNull Context context, @NonNull WorkManagerImpl workManager, @NonNull String workSpecId, long triggerAtMillis) {
        WorkDatabase workDatabase = workManager.getWorkDatabase();
        SystemIdInfoDao systemIdInfoDao = workDatabase.systemIdInfoDao();
        SystemIdInfo systemIdInfo = systemIdInfoDao.getSystemIdInfo(workSpecId);
        if (systemIdInfo != null) {
            cancelExactAlarm(context, workSpecId, systemIdInfo.systemId);
            setExactAlarm(context, workSpecId, systemIdInfo.systemId, triggerAtMillis);
            return;
        }
        int alarmId = new IdGenerator(workDatabase).nextAlarmManagerId();
        systemIdInfoDao.insertSystemIdInfo(new SystemIdInfo(workSpecId, alarmId));
        setExactAlarm(context, workSpecId, alarmId, triggerAtMillis);
    }

    public static void cancelAlarm(@NonNull Context context, @NonNull WorkManagerImpl workManager, @NonNull String workSpecId) {
        SystemIdInfoDao systemIdInfoDao = workManager.getWorkDatabase().systemIdInfoDao();
        SystemIdInfo systemIdInfo = systemIdInfoDao.getSystemIdInfo(workSpecId);
        if (systemIdInfo != null) {
            cancelExactAlarm(context, workSpecId, systemIdInfo.systemId);
            Logger.get().debug(TAG, String.format("Removing SystemIdInfo for workSpecId (%s)", new Object[]{workSpecId}), new Throwable[0]);
            systemIdInfoDao.removeSystemIdInfo(workSpecId);
        }
    }

    private static void cancelExactAlarm(@NonNull Context context, @NonNull String workSpecId, int alarmId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        Intent delayMet = CommandHandler.createDelayMetIntent(context, workSpecId);
        int flags = 536870912;
        if (Build.VERSION.SDK_INT >= 23) {
            flags = 536870912 | 67108864;
        }
        PendingIntent pendingIntent = PendingIntent.getService(context, alarmId, delayMet, flags);
        if (pendingIntent != null && alarmManager != null) {
            Logger.get().debug(TAG, String.format("Cancelling existing alarm with (workSpecId, systemId) (%s, %s)", new Object[]{workSpecId, Integer.valueOf(alarmId)}), new Throwable[0]);
            alarmManager.cancel(pendingIntent);
        }
    }

    private static void setExactAlarm(@NonNull Context context, @NonNull String workSpecId, int alarmId, long triggerAtMillis) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        int flags = 134217728;
        int i = Build.VERSION.SDK_INT;
        if (i >= 23) {
            flags = 134217728 | 67108864;
        }
        PendingIntent pendingIntent = PendingIntent.getService(context, alarmId, CommandHandler.createDelayMetIntent(context, workSpecId), flags);
        if (alarmManager == null) {
            return;
        }
        if (i >= 19) {
            alarmManager.setExact(0, triggerAtMillis, pendingIntent);
        } else {
            alarmManager.set(0, triggerAtMillis, pendingIntent);
        }
    }

    private Alarms() {
    }
}
