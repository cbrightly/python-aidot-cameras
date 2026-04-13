package androidx.work.impl.utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkDatabaseMigrations;
import androidx.work.impl.model.Preference;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class IdGenerator {
    public static final int INITIAL_ID = 0;
    public static final String NEXT_ALARM_MANAGER_ID_KEY = "next_alarm_manager_id";
    public static final String NEXT_JOB_SCHEDULER_ID_KEY = "next_job_scheduler_id";
    public static final String PREFERENCE_FILE_KEY = "androidx.work.util.id";
    private final WorkDatabase mWorkDatabase;

    public IdGenerator(@NonNull WorkDatabase workDatabase) {
        this.mWorkDatabase = workDatabase;
    }

    public int nextJobSchedulerIdWithRange(int minInclusive, int maxInclusive) {
        int id;
        synchronized (IdGenerator.class) {
            id = nextId(NEXT_JOB_SCHEDULER_ID_KEY);
            if (id < minInclusive || id > maxInclusive) {
                id = minInclusive;
                update(NEXT_JOB_SCHEDULER_ID_KEY, id + 1);
            }
        }
        return id;
    }

    public int nextAlarmManagerId() {
        int nextId;
        synchronized (IdGenerator.class) {
            nextId = nextId(NEXT_ALARM_MANAGER_ID_KEY);
        }
        return nextId;
    }

    private int nextId(String key) {
        this.mWorkDatabase.beginTransaction();
        try {
            Long value = this.mWorkDatabase.preferenceDao().getLongValue(key);
            int nextId = 0;
            int id = value != null ? value.intValue() : 0;
            if (id != Integer.MAX_VALUE) {
                nextId = id + 1;
            }
            update(key, nextId);
            this.mWorkDatabase.setTransactionSuccessful();
            return id;
        } finally {
            this.mWorkDatabase.endTransaction();
        }
    }

    private void update(String key, int value) {
        this.mWorkDatabase.preferenceDao().insertPreference(new Preference(key, (long) value));
    }

    public static void migrateLegacyIdGenerator(@NonNull Context context, @NonNull SupportSQLiteDatabase sqLiteDatabase) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_KEY, 0);
        if (sharedPreferences.contains(NEXT_JOB_SCHEDULER_ID_KEY) || sharedPreferences.contains(NEXT_JOB_SCHEDULER_ID_KEY)) {
            int nextJobId = sharedPreferences.getInt(NEXT_JOB_SCHEDULER_ID_KEY, 0);
            int nextAlarmId = sharedPreferences.getInt(NEXT_ALARM_MANAGER_ID_KEY, 0);
            sqLiteDatabase.beginTransaction();
            try {
                sqLiteDatabase.execSQL(WorkDatabaseMigrations.INSERT_PREFERENCE, new Object[]{NEXT_JOB_SCHEDULER_ID_KEY, Integer.valueOf(nextJobId)});
                sqLiteDatabase.execSQL(WorkDatabaseMigrations.INSERT_PREFERENCE, new Object[]{NEXT_ALARM_MANAGER_ID_KEY, Integer.valueOf(nextAlarmId)});
                sharedPreferences.edit().clear().apply();
                sqLiteDatabase.setTransactionSuccessful();
            } finally {
                sqLiteDatabase.endTransaction();
            }
        }
    }
}
