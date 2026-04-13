package androidx.work.impl.model;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.collection.ArrayMap;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import androidx.work.Constraints;
import androidx.work.ContentUriTriggers;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.WorkInfo;
import androidx.work.impl.model.WorkSpec;
import com.leedarson.bean.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import org.slf4j.e;

public final class WorkSpecDao_Impl implements WorkSpecDao {
    /* access modifiers changed from: private */
    public final RoomDatabase __db;
    private final EntityInsertionAdapter<WorkSpec> __insertionAdapterOfWorkSpec;
    private final SharedSQLiteStatement __preparedStmtOfDelete;
    private final SharedSQLiteStatement __preparedStmtOfIncrementWorkSpecRunAttemptCount;
    private final SharedSQLiteStatement __preparedStmtOfMarkWorkSpecScheduled;
    private final SharedSQLiteStatement __preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast;
    private final SharedSQLiteStatement __preparedStmtOfResetScheduledState;
    private final SharedSQLiteStatement __preparedStmtOfResetWorkSpecRunAttemptCount;
    private final SharedSQLiteStatement __preparedStmtOfSetOutput;
    private final SharedSQLiteStatement __preparedStmtOfSetPeriodStartTime;

    public WorkSpecDao_Impl(RoomDatabase __db2) {
        this.__db = __db2;
        this.__insertionAdapterOfWorkSpec = new EntityInsertionAdapter<WorkSpec>(__db2) {
            public String createQuery() {
                return "INSERT OR IGNORE INTO `WorkSpec` (`id`,`state`,`worker_class_name`,`input_merger_class_name`,`input`,`output`,`initial_delay`,`interval_duration`,`flex_duration`,`run_attempt_count`,`backoff_policy`,`backoff_delay_duration`,`period_start_time`,`minimum_retention_duration`,`schedule_requested_at`,`run_in_foreground`,`out_of_quota_policy`,`required_network_type`,`requires_charging`,`requires_device_idle`,`requires_battery_not_low`,`requires_storage_not_low`,`trigger_content_update_delay`,`trigger_max_content_delay`,`content_uri_triggers`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            }

            public void bind(SupportSQLiteStatement stmt, WorkSpec value) {
                SupportSQLiteStatement supportSQLiteStatement = stmt;
                WorkSpec workSpec = value;
                String str = workSpec.id;
                if (str == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindString(1, str);
                }
                supportSQLiteStatement.bindLong(2, (long) WorkTypeConverters.stateToInt(workSpec.state));
                String str2 = workSpec.workerClassName;
                if (str2 == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, str2);
                }
                String str3 = workSpec.inputMergerClassName;
                if (str3 == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, str3);
                }
                byte[] _tmp_1 = Data.toByteArrayInternal(workSpec.input);
                if (_tmp_1 == null) {
                    supportSQLiteStatement.bindNull(5);
                } else {
                    supportSQLiteStatement.bindBlob(5, _tmp_1);
                }
                byte[] _tmp_2 = Data.toByteArrayInternal(workSpec.output);
                if (_tmp_2 == null) {
                    supportSQLiteStatement.bindNull(6);
                } else {
                    supportSQLiteStatement.bindBlob(6, _tmp_2);
                }
                supportSQLiteStatement.bindLong(7, workSpec.initialDelay);
                supportSQLiteStatement.bindLong(8, workSpec.intervalDuration);
                supportSQLiteStatement.bindLong(9, workSpec.flexDuration);
                supportSQLiteStatement.bindLong(10, (long) workSpec.runAttemptCount);
                supportSQLiteStatement.bindLong(11, (long) WorkTypeConverters.backoffPolicyToInt(workSpec.backoffPolicy));
                supportSQLiteStatement.bindLong(12, workSpec.backoffDelayDuration);
                supportSQLiteStatement.bindLong(13, workSpec.periodStartTime);
                supportSQLiteStatement.bindLong(14, workSpec.minimumRetentionDuration);
                supportSQLiteStatement.bindLong(15, workSpec.scheduleRequestedAt);
                supportSQLiteStatement.bindLong(16, (long) workSpec.expedited);
                supportSQLiteStatement.bindLong(17, (long) WorkTypeConverters.outOfQuotaPolicyToInt(workSpec.outOfQuotaPolicy));
                Constraints _tmpConstraints = workSpec.constraints;
                if (_tmpConstraints != null) {
                    supportSQLiteStatement.bindLong(18, (long) WorkTypeConverters.networkTypeToInt(_tmpConstraints.getRequiredNetworkType()));
                    supportSQLiteStatement.bindLong(19, (long) _tmpConstraints.requiresCharging());
                    supportSQLiteStatement.bindLong(20, (long) _tmpConstraints.requiresDeviceIdle());
                    supportSQLiteStatement.bindLong(21, (long) _tmpConstraints.requiresBatteryNotLow());
                    supportSQLiteStatement.bindLong(22, (long) _tmpConstraints.requiresStorageNotLow());
                    supportSQLiteStatement.bindLong(23, _tmpConstraints.getTriggerContentUpdateDelay());
                    supportSQLiteStatement.bindLong(24, _tmpConstraints.getTriggerMaxContentDelay());
                    byte[] _tmp_11 = WorkTypeConverters.contentUriTriggersToByteArray(_tmpConstraints.getContentUriTriggers());
                    if (_tmp_11 == null) {
                        supportSQLiteStatement.bindNull(25);
                    } else {
                        supportSQLiteStatement.bindBlob(25, _tmp_11);
                    }
                } else {
                    supportSQLiteStatement.bindNull(18);
                    supportSQLiteStatement.bindNull(19);
                    supportSQLiteStatement.bindNull(20);
                    supportSQLiteStatement.bindNull(21);
                    supportSQLiteStatement.bindNull(22);
                    supportSQLiteStatement.bindNull(23);
                    supportSQLiteStatement.bindNull(24);
                    supportSQLiteStatement.bindNull(25);
                }
            }
        };
        this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db2) {
            public String createQuery() {
                return "DELETE FROM workspec WHERE id=?";
            }
        };
        this.__preparedStmtOfSetOutput = new SharedSQLiteStatement(__db2) {
            public String createQuery() {
                return "UPDATE workspec SET output=? WHERE id=?";
            }
        };
        this.__preparedStmtOfSetPeriodStartTime = new SharedSQLiteStatement(__db2) {
            public String createQuery() {
                return "UPDATE workspec SET period_start_time=? WHERE id=?";
            }
        };
        this.__preparedStmtOfIncrementWorkSpecRunAttemptCount = new SharedSQLiteStatement(__db2) {
            public String createQuery() {
                return "UPDATE workspec SET run_attempt_count=run_attempt_count+1 WHERE id=?";
            }
        };
        this.__preparedStmtOfResetWorkSpecRunAttemptCount = new SharedSQLiteStatement(__db2) {
            public String createQuery() {
                return "UPDATE workspec SET run_attempt_count=0 WHERE id=?";
            }
        };
        this.__preparedStmtOfMarkWorkSpecScheduled = new SharedSQLiteStatement(__db2) {
            public String createQuery() {
                return "UPDATE workspec SET schedule_requested_at=? WHERE id=?";
            }
        };
        this.__preparedStmtOfResetScheduledState = new SharedSQLiteStatement(__db2) {
            public String createQuery() {
                return "UPDATE workspec SET schedule_requested_at=-1 WHERE state NOT IN (2, 3, 5)";
            }
        };
        this.__preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast = new SharedSQLiteStatement(__db2) {
            public String createQuery() {
                return "DELETE FROM workspec WHERE state IN (2, 3, 5) AND (SELECT COUNT(*)=0 FROM dependency WHERE     prerequisite_id=id AND     work_spec_id NOT IN         (SELECT id FROM workspec WHERE state IN (2, 3, 5)))";
            }
        };
    }

    public void insertWorkSpec(WorkSpec workSpec) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfWorkSpec.insert(workSpec);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void delete(String id) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfDelete.acquire();
        if (id == null) {
            _stmt.bindNull(1);
        } else {
            _stmt.bindString(1, id);
        }
        this.__db.beginTransaction();
        try {
            _stmt.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDelete.release(_stmt);
        }
    }

    public void setOutput(String id, Data output) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfSetOutput.acquire();
        byte[] _tmp = Data.toByteArrayInternal(output);
        if (_tmp == null) {
            _stmt.bindNull(1);
        } else {
            _stmt.bindBlob(1, _tmp);
        }
        if (id == null) {
            _stmt.bindNull(2);
        } else {
            _stmt.bindString(2, id);
        }
        this.__db.beginTransaction();
        try {
            _stmt.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfSetOutput.release(_stmt);
        }
    }

    public void setPeriodStartTime(String id, long periodStartTime) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfSetPeriodStartTime.acquire();
        _stmt.bindLong(1, periodStartTime);
        if (id == null) {
            _stmt.bindNull(2);
        } else {
            _stmt.bindString(2, id);
        }
        this.__db.beginTransaction();
        try {
            _stmt.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfSetPeriodStartTime.release(_stmt);
        }
    }

    public int incrementWorkSpecRunAttemptCount(String id) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfIncrementWorkSpecRunAttemptCount.acquire();
        if (id == null) {
            _stmt.bindNull(1);
        } else {
            _stmt.bindString(1, id);
        }
        this.__db.beginTransaction();
        try {
            int _result = _stmt.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
            return _result;
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfIncrementWorkSpecRunAttemptCount.release(_stmt);
        }
    }

    public int resetWorkSpecRunAttemptCount(String id) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfResetWorkSpecRunAttemptCount.acquire();
        if (id == null) {
            _stmt.bindNull(1);
        } else {
            _stmt.bindString(1, id);
        }
        this.__db.beginTransaction();
        try {
            int _result = _stmt.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
            return _result;
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfResetWorkSpecRunAttemptCount.release(_stmt);
        }
    }

    public int markWorkSpecScheduled(String id, long startTime) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfMarkWorkSpecScheduled.acquire();
        _stmt.bindLong(1, startTime);
        if (id == null) {
            _stmt.bindNull(2);
        } else {
            _stmt.bindString(2, id);
        }
        this.__db.beginTransaction();
        try {
            int _result = _stmt.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
            return _result;
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfMarkWorkSpecScheduled.release(_stmt);
        }
    }

    public int resetScheduledState() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfResetScheduledState.acquire();
        this.__db.beginTransaction();
        try {
            int _result = _stmt.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
            return _result;
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfResetScheduledState.release(_stmt);
        }
    }

    public void pruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement _stmt = this.__preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast.acquire();
        this.__db.beginTransaction();
        try {
            _stmt.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast.release(_stmt);
        }
    }

    public WorkSpec getWorkSpec(String id) {
        RoomSQLiteQuery _statement;
        int _cursorIndexOfInput;
        int _cursorIndexOfOutput;
        WorkSpec _result;
        boolean z;
        String str = id;
        RoomSQLiteQuery _statement2 = RoomSQLiteQuery.acquire("SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE id=?", 1);
        if (str == null) {
            _statement2.bindNull(1);
        } else {
            _statement2.bindString(1, str);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement2, false, (CancellationSignal) null);
        try {
            int _cursorIndexOfMRequiredNetworkType = CursorUtil.getColumnIndexOrThrow(_cursor, "required_network_type");
            int _cursorIndexOfMRequiresCharging = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_charging");
            int _cursorIndexOfMRequiresDeviceIdle = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_device_idle");
            int _cursorIndexOfMRequiresBatteryNotLow = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_battery_not_low");
            int _cursorIndexOfMRequiresStorageNotLow = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_storage_not_low");
            int _cursorIndexOfMTriggerContentUpdateDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "trigger_content_update_delay");
            int _cursorIndexOfMTriggerMaxContentDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "trigger_max_content_delay");
            int _cursorIndexOfMContentUriTriggers = CursorUtil.getColumnIndexOrThrow(_cursor, "content_uri_triggers");
            int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, Constants.ACTION_STATE);
            int _cursorIndexOfWorkerClassName = CursorUtil.getColumnIndexOrThrow(_cursor, "worker_class_name");
            int _cursorIndexOfInputMergerClassName = CursorUtil.getColumnIndexOrThrow(_cursor, "input_merger_class_name");
            Object obj = "SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE id=?";
            try {
                _cursorIndexOfInput = CursorUtil.getColumnIndexOrThrow(_cursor, "input");
            } catch (Throwable th) {
                th = th;
                _statement = _statement2;
                _cursor.close();
                _statement.release();
                throw th;
            }
            try {
                _cursorIndexOfOutput = CursorUtil.getColumnIndexOrThrow(_cursor, "output");
                _statement = _statement2;
            } catch (Throwable th2) {
                th = th2;
                _statement = _statement2;
                _cursor.close();
                _statement.release();
                throw th;
            }
            try {
                int _cursorIndexOfInitialDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "initial_delay");
                int _cursorIndexOfIntervalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "interval_duration");
                int _cursorIndexOfFlexDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "flex_duration");
                int _cursorIndexOfRunAttemptCount = CursorUtil.getColumnIndexOrThrow(_cursor, "run_attempt_count");
                int _cursorIndexOfBackoffPolicy = CursorUtil.getColumnIndexOrThrow(_cursor, "backoff_policy");
                int _cursorIndexOfBackoffDelayDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "backoff_delay_duration");
                int _cursorIndexOfPeriodStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "period_start_time");
                int _cursorIndexOfMinimumRetentionDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "minimum_retention_duration");
                int _cursorIndexOfScheduleRequestedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "schedule_requested_at");
                int _cursorIndexOfExpedited = CursorUtil.getColumnIndexOrThrow(_cursor, "run_in_foreground");
                int _cursorIndexOfOutOfQuotaPolicy = CursorUtil.getColumnIndexOrThrow(_cursor, "out_of_quota_policy");
                if (_cursor.moveToFirst()) {
                    String _tmpId = _cursor.getString(_cursorIndexOfId);
                    String _tmpWorkerClassName = _cursor.getString(_cursorIndexOfWorkerClassName);
                    Constraints _tmpConstraints = new Constraints();
                    NetworkType _tmpMRequiredNetworkType = WorkTypeConverters.intToNetworkType(_cursor.getInt(_cursorIndexOfMRequiredNetworkType));
                    int i = _cursorIndexOfMRequiredNetworkType;
                    Constraints _tmpConstraints2 = _tmpConstraints;
                    int i2 = _cursorIndexOfWorkerClassName;
                    NetworkType _tmpMRequiredNetworkType2 = _tmpMRequiredNetworkType;
                    _tmpConstraints2.setRequiredNetworkType(_tmpMRequiredNetworkType2);
                    NetworkType networkType = _tmpMRequiredNetworkType2;
                    boolean _tmpMRequiresCharging = _cursor.getInt(_cursorIndexOfMRequiresCharging) != 0;
                    _tmpConstraints2.setRequiresCharging(_tmpMRequiresCharging);
                    boolean z2 = _tmpMRequiresCharging;
                    boolean _tmpMRequiresCharging2 = _cursor.getInt(_cursorIndexOfMRequiresDeviceIdle) != 0;
                    _tmpConstraints2.setRequiresDeviceIdle(_tmpMRequiresCharging2);
                    boolean z3 = _tmpMRequiresCharging2;
                    boolean _tmpMRequiresBatteryNotLow = _cursor.getInt(_cursorIndexOfMRequiresBatteryNotLow) != 0;
                    _tmpConstraints2.setRequiresBatteryNotLow(_tmpMRequiresBatteryNotLow);
                    boolean z4 = _tmpMRequiresBatteryNotLow;
                    boolean _tmpMRequiresBatteryNotLow2 = _cursor.getInt(_cursorIndexOfMRequiresStorageNotLow) != 0;
                    _tmpConstraints2.setRequiresStorageNotLow(_tmpMRequiresBatteryNotLow2);
                    int i3 = _cursorIndexOfMRequiresCharging;
                    int i4 = _cursorIndexOfMRequiresDeviceIdle;
                    long _tmpMTriggerContentUpdateDelay = _cursor.getLong(_cursorIndexOfMTriggerContentUpdateDelay);
                    _tmpConstraints2.setTriggerContentUpdateDelay(_tmpMTriggerContentUpdateDelay);
                    long j = _tmpMTriggerContentUpdateDelay;
                    long _tmpMTriggerMaxContentDelay = _cursor.getLong(_cursorIndexOfMTriggerMaxContentDelay);
                    _tmpConstraints2.setTriggerMaxContentDelay(_tmpMTriggerMaxContentDelay);
                    boolean z5 = _tmpMRequiresBatteryNotLow2;
                    ContentUriTriggers _tmpMContentUriTriggers = WorkTypeConverters.byteArrayToContentUriTriggers(_cursor.getBlob(_cursorIndexOfMContentUriTriggers));
                    _tmpConstraints2.setContentUriTriggers(_tmpMContentUriTriggers);
                    ContentUriTriggers contentUriTriggers = _tmpMContentUriTriggers;
                    int i5 = _cursorIndexOfId;
                    String _tmpId2 = _tmpId;
                    long j2 = _tmpMTriggerMaxContentDelay;
                    String _tmpWorkerClassName2 = _tmpWorkerClassName;
                    long j3 = j2;
                    _result = new WorkSpec(_tmpId2, _tmpWorkerClassName2);
                    String str2 = _tmpId2;
                    _result.state = WorkTypeConverters.intToState(_cursor.getInt(_cursorIndexOfState));
                    _result.inputMergerClassName = _cursor.getString(_cursorIndexOfInputMergerClassName);
                    byte[] _tmp_7 = _cursor.getBlob(_cursorIndexOfInput);
                    int i6 = _cursorIndexOfInputMergerClassName;
                    _result.input = Data.fromByteArray(_tmp_7);
                    byte[] _tmp_8 = _cursor.getBlob(_cursorIndexOfOutput);
                    int i7 = _cursorIndexOfInput;
                    _result.output = Data.fromByteArray(_tmp_8);
                    String str3 = _tmpWorkerClassName2;
                    int _cursorIndexOfInitialDelay2 = _cursorIndexOfInitialDelay;
                    int _cursorIndexOfInitialDelay3 = _cursorIndexOfState;
                    _result.initialDelay = _cursor.getLong(_cursorIndexOfInitialDelay2);
                    byte[] _tmp_82 = _tmp_8;
                    int _cursorIndexOfIntervalDuration2 = _cursorIndexOfIntervalDuration;
                    int _cursorIndexOfIntervalDuration3 = _cursorIndexOfInitialDelay2;
                    _result.intervalDuration = _cursor.getLong(_cursorIndexOfIntervalDuration2);
                    int _cursorIndexOfFlexDuration2 = _cursorIndexOfFlexDuration;
                    byte[] bArr = _tmp_7;
                    _result.flexDuration = _cursor.getLong(_cursorIndexOfFlexDuration2);
                    int _cursorIndexOfRunAttemptCount2 = _cursorIndexOfRunAttemptCount;
                    _result.runAttemptCount = _cursor.getInt(_cursorIndexOfRunAttemptCount2);
                    int _cursorIndexOfBackoffPolicy2 = _cursorIndexOfBackoffPolicy;
                    int i8 = _cursorIndexOfFlexDuration2;
                    _result.backoffPolicy = WorkTypeConverters.intToBackoffPolicy(_cursor.getInt(_cursorIndexOfBackoffPolicy2));
                    int i9 = _cursorIndexOfBackoffPolicy2;
                    int _cursorIndexOfBackoffDelayDuration2 = _cursorIndexOfBackoffDelayDuration;
                    int _cursorIndexOfBackoffDelayDuration3 = _cursorIndexOfRunAttemptCount2;
                    _result.backoffDelayDuration = _cursor.getLong(_cursorIndexOfBackoffDelayDuration2);
                    int _cursorIndexOfIntervalDuration4 = _cursorIndexOfIntervalDuration2;
                    int _cursorIndexOfPeriodStartTime2 = _cursorIndexOfPeriodStartTime;
                    byte[] bArr2 = _tmp_82;
                    _result.periodStartTime = _cursor.getLong(_cursorIndexOfPeriodStartTime2);
                    int i10 = _cursorIndexOfBackoffDelayDuration2;
                    int _cursorIndexOfMinimumRetentionDuration2 = _cursorIndexOfMinimumRetentionDuration;
                    int _cursorIndexOfMinimumRetentionDuration3 = _cursorIndexOfPeriodStartTime2;
                    _result.minimumRetentionDuration = _cursor.getLong(_cursorIndexOfMinimumRetentionDuration2);
                    int _cursorIndexOfScheduleRequestedAt2 = _cursorIndexOfScheduleRequestedAt;
                    int _cursorIndexOfScheduleRequestedAt3 = _cursorIndexOfIntervalDuration4;
                    _result.scheduleRequestedAt = _cursor.getLong(_cursorIndexOfScheduleRequestedAt2);
                    int _cursorIndexOfExpedited2 = _cursorIndexOfExpedited;
                    if (_cursor.getInt(_cursorIndexOfExpedited2) != 0) {
                        int i11 = _cursorIndexOfScheduleRequestedAt2;
                        z = true;
                    } else {
                        int i12 = _cursorIndexOfScheduleRequestedAt2;
                        z = false;
                    }
                    _result.expedited = z;
                    int i13 = _cursorIndexOfExpedited2;
                    _result.outOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(_cursor.getInt(_cursorIndexOfOutOfQuotaPolicy));
                    _result.constraints = _tmpConstraints2;
                } else {
                    int i14 = _cursorIndexOfWorkerClassName;
                    int i15 = _cursorIndexOfInputMergerClassName;
                    int i16 = _cursorIndexOfInput;
                    int i17 = _cursorIndexOfId;
                    int i18 = _cursorIndexOfMRequiresCharging;
                    int i19 = _cursorIndexOfMRequiresDeviceIdle;
                    int i20 = _cursorIndexOfBackoffPolicy;
                    int _cursorIndexOfMRequiresCharging2 = _cursorIndexOfBackoffDelayDuration;
                    int i21 = _cursorIndexOfScheduleRequestedAt;
                    int _cursorIndexOfScheduleRequestedAt4 = _cursorIndexOfIntervalDuration;
                    int _cursorIndexOfBackoffPolicy3 = _cursorIndexOfFlexDuration;
                    int _cursorIndexOfBackoffDelayDuration4 = _cursorIndexOfRunAttemptCount;
                    int _cursorIndexOfIntervalDuration5 = _cursorIndexOfInitialDelay;
                    int _cursorIndexOfInitialDelay4 = _cursorIndexOfState;
                    int _cursorIndexOfState2 = _cursorIndexOfMinimumRetentionDuration;
                    int _cursorIndexOfMinimumRetentionDuration4 = _cursorIndexOfPeriodStartTime;
                    _result = null;
                }
                _cursor.close();
                _statement.release();
                return _result;
            } catch (Throwable th3) {
                th = th3;
                _cursor.close();
                _statement.release();
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            Object obj2 = "SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE id=?";
            _statement = _statement2;
            _cursor.close();
            _statement.release();
            throw th;
        }
    }

    public WorkSpec[] getWorkSpecs(List<String> ids) {
        RoomSQLiteQuery _statement;
        int _cursorIndexOfState;
        int _cursorIndexOfExpedited;
        int _cursorIndexOfInputMergerClassName;
        int _cursorIndexOfBackoffDelayDuration;
        int _cursorIndexOfOutput;
        int _cursorIndexOfScheduleRequestedAt;
        boolean z;
        StringBuilder _stringBuilder = StringUtil.newStringBuilder();
        _stringBuilder.append("SELECT ");
        _stringBuilder.append(e.ANY_MARKER);
        _stringBuilder.append(" FROM workspec WHERE id IN (");
        int _inputSize = ids.size();
        StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
        _stringBuilder.append(")");
        String _sql = _stringBuilder.toString();
        int _argCount = _inputSize + 0;
        RoomSQLiteQuery _statement2 = RoomSQLiteQuery.acquire(_sql, _argCount);
        int _argIndex = 1;
        for (String _item : ids) {
            if (_item == null) {
                _statement2.bindNull(_argIndex);
            } else {
                _statement2.bindString(_argIndex, _item);
            }
            _argIndex++;
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement2, false, (CancellationSignal) null);
        try {
            int _cursorIndexOfMRequiredNetworkType = CursorUtil.getColumnIndexOrThrow(_cursor, "required_network_type");
            int _cursorIndexOfMRequiresCharging = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_charging");
            int _cursorIndexOfMRequiresDeviceIdle = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_device_idle");
            int _cursorIndexOfMRequiresBatteryNotLow = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_battery_not_low");
            int _cursorIndexOfMRequiresStorageNotLow = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_storage_not_low");
            int _cursorIndexOfMTriggerContentUpdateDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "trigger_content_update_delay");
            int _cursorIndexOfMTriggerMaxContentDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "trigger_max_content_delay");
            int _cursorIndexOfMContentUriTriggers = CursorUtil.getColumnIndexOrThrow(_cursor, "content_uri_triggers");
            int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            StringBuilder sb = _stringBuilder;
            try {
                _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, Constants.ACTION_STATE);
                int i = _inputSize;
            } catch (Throwable th) {
                th = th;
                int i2 = _inputSize;
                String str = _sql;
                int i3 = _argCount;
                _statement = _statement2;
                int i4 = _argIndex;
                _cursor.close();
                _statement.release();
                throw th;
            }
            try {
                _cursorIndexOfExpedited = CursorUtil.getColumnIndexOrThrow(_cursor, "worker_class_name");
                String str2 = _sql;
                try {
                    _cursorIndexOfInputMergerClassName = CursorUtil.getColumnIndexOrThrow(_cursor, "input_merger_class_name");
                    int i5 = _argCount;
                } catch (Throwable th2) {
                    th = th2;
                    int i6 = _argCount;
                    _statement = _statement2;
                    int i7 = _argIndex;
                    _cursor.close();
                    _statement.release();
                    throw th;
                }
                try {
                    _cursorIndexOfBackoffDelayDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "input");
                    int i8 = _argIndex;
                    try {
                        _cursorIndexOfOutput = CursorUtil.getColumnIndexOrThrow(_cursor, "output");
                        _statement = _statement2;
                    } catch (Throwable th3) {
                        th = th3;
                        _statement = _statement2;
                        _cursor.close();
                        _statement.release();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    _statement = _statement2;
                    int i9 = _argIndex;
                    _cursor.close();
                    _statement.release();
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
                String str3 = _sql;
                int i10 = _argCount;
                _statement = _statement2;
                int i11 = _argIndex;
                _cursor.close();
                _statement.release();
                throw th;
            }
            try {
                int _cursorIndexOfInputMergerClassName2 = CursorUtil.getColumnIndexOrThrow(_cursor, "initial_delay");
                int _cursorIndexOfInput = CursorUtil.getColumnIndexOrThrow(_cursor, "interval_duration");
                int _cursorIndexOfIntervalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "flex_duration");
                int _cursorIndexOfRunAttemptCount = CursorUtil.getColumnIndexOrThrow(_cursor, "run_attempt_count");
                int _cursorIndexOfRunAttemptCount2 = CursorUtil.getColumnIndexOrThrow(_cursor, "backoff_policy");
                int _cursorIndexOfFlexDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "backoff_delay_duration");
                int _cursorIndexOfInitialDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "period_start_time");
                int _cursorIndexOfPeriodStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "minimum_retention_duration");
                int _cursorIndexOfMinimumRetentionDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "schedule_requested_at");
                int _cursorIndexOfScheduleRequestedAt2 = CursorUtil.getColumnIndexOrThrow(_cursor, "run_in_foreground");
                int _cursorIndexOfOutOfQuotaPolicy = CursorUtil.getColumnIndexOrThrow(_cursor, "out_of_quota_policy");
                WorkSpec[] _result = new WorkSpec[_cursor.getCount()];
                int _index = 0;
                while (_cursor.moveToNext()) {
                    String _tmpId = _cursor.getString(_cursorIndexOfId);
                    String _tmpWorkerClassName = _cursor.getString(_cursorIndexOfExpedited);
                    Constraints _tmpConstraints = new Constraints();
                    NetworkType _tmpMRequiredNetworkType = WorkTypeConverters.intToNetworkType(_cursor.getInt(_cursorIndexOfMRequiredNetworkType));
                    int _cursorIndexOfMRequiredNetworkType2 = _cursorIndexOfMRequiredNetworkType;
                    Constraints _tmpConstraints2 = _tmpConstraints;
                    int _cursorIndexOfId2 = _cursorIndexOfId;
                    NetworkType _tmpMRequiredNetworkType2 = _tmpMRequiredNetworkType;
                    _tmpConstraints2.setRequiredNetworkType(_tmpMRequiredNetworkType2);
                    NetworkType networkType = _tmpMRequiredNetworkType2;
                    boolean _tmpMRequiresCharging = _cursor.getInt(_cursorIndexOfMRequiresCharging) != 0;
                    _tmpConstraints2.setRequiresCharging(_tmpMRequiresCharging);
                    boolean z2 = _tmpMRequiresCharging;
                    boolean _tmpMRequiresCharging2 = _cursor.getInt(_cursorIndexOfMRequiresDeviceIdle) != 0;
                    _tmpConstraints2.setRequiresDeviceIdle(_tmpMRequiresCharging2);
                    boolean z3 = _tmpMRequiresCharging2;
                    boolean _tmpMRequiresBatteryNotLow = _cursor.getInt(_cursorIndexOfMRequiresBatteryNotLow) != 0;
                    _tmpConstraints2.setRequiresBatteryNotLow(_tmpMRequiresBatteryNotLow);
                    boolean z4 = _tmpMRequiresBatteryNotLow;
                    boolean _tmpMRequiresBatteryNotLow2 = _cursor.getInt(_cursorIndexOfMRequiresStorageNotLow) != 0;
                    _tmpConstraints2.setRequiresStorageNotLow(_tmpMRequiresBatteryNotLow2);
                    int _cursorIndexOfMRequiresCharging2 = _cursorIndexOfMRequiresCharging;
                    int _cursorIndexOfMRequiresDeviceIdle2 = _cursorIndexOfMRequiresDeviceIdle;
                    long _tmpMTriggerContentUpdateDelay = _cursor.getLong(_cursorIndexOfMTriggerContentUpdateDelay);
                    _tmpConstraints2.setTriggerContentUpdateDelay(_tmpMTriggerContentUpdateDelay);
                    long j = _tmpMTriggerContentUpdateDelay;
                    _tmpConstraints2.setTriggerMaxContentDelay(_cursor.getLong(_cursorIndexOfMTriggerMaxContentDelay));
                    boolean z5 = _tmpMRequiresBatteryNotLow2;
                    ContentUriTriggers _tmpMContentUriTriggers = WorkTypeConverters.byteArrayToContentUriTriggers(_cursor.getBlob(_cursorIndexOfMContentUriTriggers));
                    _tmpConstraints2.setContentUriTriggers(_tmpMContentUriTriggers);
                    ContentUriTriggers contentUriTriggers = _tmpMContentUriTriggers;
                    int _cursorIndexOfWorkerClassName = _cursorIndexOfExpedited;
                    String _tmpId2 = _tmpId;
                    int _cursorIndexOfMContentUriTriggers2 = _cursorIndexOfMContentUriTriggers;
                    WorkSpec _item_1 = new WorkSpec(_tmpId2, _tmpWorkerClassName);
                    int _cursorIndexOfState2 = _cursorIndexOfState;
                    _item_1.state = WorkTypeConverters.intToState(_cursor.getInt(_cursorIndexOfState));
                    _item_1.inputMergerClassName = _cursor.getString(_cursorIndexOfInputMergerClassName);
                    byte[] _tmp_7 = _cursor.getBlob(_cursorIndexOfBackoffDelayDuration);
                    String str4 = _tmpId2;
                    _item_1.input = Data.fromByteArray(_tmp_7);
                    byte[] _tmp_8 = _cursor.getBlob(_cursorIndexOfOutput);
                    byte[] bArr = _tmp_7;
                    _item_1.output = Data.fromByteArray(_tmp_8);
                    byte[] bArr2 = _tmp_8;
                    int _cursorIndexOfInitialDelay2 = _cursorIndexOfInputMergerClassName2;
                    int _cursorIndexOfInputMergerClassName3 = _cursorIndexOfInputMergerClassName;
                    _item_1.initialDelay = _cursor.getLong(_cursorIndexOfInitialDelay2);
                    int _cursorIndexOfIntervalDuration2 = _cursorIndexOfInput;
                    int _cursorIndexOfInput2 = _cursorIndexOfBackoffDelayDuration;
                    _item_1.intervalDuration = _cursor.getLong(_cursorIndexOfIntervalDuration2);
                    int _cursorIndexOfInitialDelay3 = _cursorIndexOfInitialDelay2;
                    int _cursorIndexOfFlexDuration2 = _cursorIndexOfIntervalDuration;
                    int _cursorIndexOfIntervalDuration3 = _cursorIndexOfIntervalDuration2;
                    _item_1.flexDuration = _cursor.getLong(_cursorIndexOfFlexDuration2);
                    int _cursorIndexOfRunAttemptCount3 = _cursorIndexOfRunAttemptCount;
                    _item_1.runAttemptCount = _cursor.getInt(_cursorIndexOfRunAttemptCount3);
                    int _cursorIndexOfBackoffPolicy = _cursorIndexOfRunAttemptCount2;
                    int _cursorIndexOfRunAttemptCount4 = _cursorIndexOfRunAttemptCount3;
                    _item_1.backoffPolicy = WorkTypeConverters.intToBackoffPolicy(_cursor.getInt(_cursorIndexOfBackoffPolicy));
                    int _cursorIndexOfBackoffPolicy2 = _cursorIndexOfBackoffPolicy;
                    int _cursorIndexOfBackoffDelayDuration2 = _cursorIndexOfFlexDuration;
                    int _cursorIndexOfFlexDuration3 = _cursorIndexOfFlexDuration2;
                    _item_1.backoffDelayDuration = _cursor.getLong(_cursorIndexOfBackoffDelayDuration2);
                    int _cursorIndexOfPeriodStartTime2 = _cursorIndexOfInitialDelay;
                    int _cursorIndexOfInitialDelay4 = _cursorIndexOfInitialDelay3;
                    _item_1.periodStartTime = _cursor.getLong(_cursorIndexOfPeriodStartTime2);
                    int _cursorIndexOfBackoffDelayDuration3 = _cursorIndexOfBackoffDelayDuration2;
                    int _cursorIndexOfMinimumRetentionDuration2 = _cursorIndexOfPeriodStartTime;
                    int _cursorIndexOfPeriodStartTime3 = _cursorIndexOfPeriodStartTime2;
                    _item_1.minimumRetentionDuration = _cursor.getLong(_cursorIndexOfMinimumRetentionDuration2);
                    int _cursorIndexOfScheduleRequestedAt3 = _cursorIndexOfMinimumRetentionDuration;
                    int _cursorIndexOfScheduleRequestedAt4 = _cursorIndexOfMinimumRetentionDuration2;
                    _item_1.scheduleRequestedAt = _cursor.getLong(_cursorIndexOfScheduleRequestedAt3);
                    int _cursorIndexOfExpedited2 = _cursorIndexOfScheduleRequestedAt2;
                    if (_cursor.getInt(_cursorIndexOfExpedited2) != 0) {
                        _cursorIndexOfScheduleRequestedAt = _cursorIndexOfScheduleRequestedAt3;
                        z = true;
                    } else {
                        _cursorIndexOfScheduleRequestedAt = _cursorIndexOfScheduleRequestedAt3;
                        z = false;
                    }
                    _item_1.expedited = z;
                    int _cursorIndexOfOutOfQuotaPolicy2 = _cursorIndexOfOutOfQuotaPolicy;
                    int _cursorIndexOfOutOfQuotaPolicy3 = _cursorIndexOfOutOfQuotaPolicy2;
                    _item_1.outOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(_cursor.getInt(_cursorIndexOfOutOfQuotaPolicy2));
                    _item_1.constraints = _tmpConstraints2;
                    _result[_index] = _item_1;
                    _index++;
                    _cursorIndexOfInputMergerClassName = _cursorIndexOfInputMergerClassName3;
                    _cursorIndexOfRunAttemptCount = _cursorIndexOfRunAttemptCount4;
                    _cursorIndexOfInputMergerClassName2 = _cursorIndexOfInitialDelay4;
                    _cursorIndexOfInitialDelay = _cursorIndexOfPeriodStartTime3;
                    _cursorIndexOfPeriodStartTime = _cursorIndexOfScheduleRequestedAt4;
                    _cursorIndexOfMinimumRetentionDuration = _cursorIndexOfScheduleRequestedAt;
                    _cursorIndexOfMContentUriTriggers = _cursorIndexOfMContentUriTriggers2;
                    _cursorIndexOfId = _cursorIndexOfId2;
                    _cursorIndexOfMRequiredNetworkType = _cursorIndexOfMRequiredNetworkType2;
                    _cursorIndexOfOutOfQuotaPolicy = _cursorIndexOfOutOfQuotaPolicy3;
                    _cursorIndexOfMRequiresCharging = _cursorIndexOfMRequiresCharging2;
                    _cursorIndexOfMRequiresDeviceIdle = _cursorIndexOfMRequiresDeviceIdle2;
                    _cursorIndexOfState = _cursorIndexOfState2;
                    _cursorIndexOfRunAttemptCount2 = _cursorIndexOfBackoffPolicy2;
                    _cursorIndexOfScheduleRequestedAt2 = _cursorIndexOfExpedited2;
                    _cursorIndexOfExpedited = _cursorIndexOfWorkerClassName;
                    int i12 = _cursorIndexOfFlexDuration3;
                    _cursorIndexOfFlexDuration = _cursorIndexOfBackoffDelayDuration3;
                    _cursorIndexOfBackoffDelayDuration = _cursorIndexOfInput2;
                    _cursorIndexOfInput = _cursorIndexOfIntervalDuration3;
                    _cursorIndexOfIntervalDuration = i12;
                }
                _cursor.close();
                _statement.release();
                return _result;
            } catch (Throwable th6) {
                th = th6;
                _cursor.close();
                _statement.release();
                throw th;
            }
        } catch (Throwable th7) {
            th = th7;
            StringBuilder sb2 = _stringBuilder;
            int i13 = _inputSize;
            String str5 = _sql;
            int i14 = _argCount;
            _statement = _statement2;
            int i15 = _argIndex;
            _cursor.close();
            _statement.release();
            throw th;
        }
    }

    public List<WorkSpec.IdAndState> getWorkSpecIdAndStatesForName(String name) {
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT id, state FROM workspec WHERE id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
        if (name == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, name);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement, false, (CancellationSignal) null);
        try {
            int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, Constants.ACTION_STATE);
            List<WorkSpec.IdAndState> _result = new ArrayList<>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                WorkSpec.IdAndState _item = new WorkSpec.IdAndState();
                _item.id = _cursor.getString(_cursorIndexOfId);
                _item.state = WorkTypeConverters.intToState(_cursor.getInt(_cursorIndexOfState));
                _result.add(_item);
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    public List<String> getAllWorkSpecIds() {
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT id FROM workspec", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement, false, (CancellationSignal) null);
        try {
            List<String> _result = new ArrayList<>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                _result.add(_cursor.getString(0));
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    public LiveData<List<String>> getAllWorkSpecIdsLiveData() {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT id FROM workspec", 0);
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"workspec"}, true, new Callable<List<String>>() {
            public List<String> call() {
                Cursor _cursor;
                WorkSpecDao_Impl.this.__db.beginTransaction();
                try {
                    _cursor = DBUtil.query(WorkSpecDao_Impl.this.__db, _statement, false, (CancellationSignal) null);
                    List<String> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        _result.add(_cursor.getString(0));
                    }
                    WorkSpecDao_Impl.this.__db.setTransactionSuccessful();
                    _cursor.close();
                    WorkSpecDao_Impl.this.__db.endTransaction();
                    return _result;
                } catch (Throwable th) {
                    WorkSpecDao_Impl.this.__db.endTransaction();
                    throw th;
                }
            }

            /* access modifiers changed from: protected */
            public void finalize() {
                _statement.release();
            }
        });
    }

    public WorkInfo.State getState(String id) {
        WorkInfo.State _result;
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT state FROM workspec WHERE id=?", 1);
        if (id == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, id);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement, false, (CancellationSignal) null);
        try {
            if (_cursor.moveToFirst()) {
                _result = WorkTypeConverters.intToState(_cursor.getInt(0));
            } else {
                _result = null;
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    public WorkSpec.WorkInfoPojo getWorkStatusPojoForId(String id) {
        Cursor _cursor;
        WorkSpec.WorkInfoPojo _result;
        String str = id;
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count FROM workspec WHERE id=?", 1);
        if (str == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, str);
        }
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            _cursor = DBUtil.query(this.__db, _statement, true, (CancellationSignal) null);
            int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, Constants.ACTION_STATE);
            int _cursorIndexOfOutput = CursorUtil.getColumnIndexOrThrow(_cursor, "output");
            int _cursorIndexOfRunAttemptCount = CursorUtil.getColumnIndexOrThrow(_cursor, "run_attempt_count");
            ArrayMap<String, ArrayList<String>> _collectionTags = new ArrayMap<>();
            ArrayMap<String, ArrayList<Data>> _collectionProgress = new ArrayMap<>();
            while (_cursor.moveToNext()) {
                if (!_cursor.isNull(_cursorIndexOfId)) {
                    String _tmpKey = _cursor.getString(_cursorIndexOfId);
                    if (_collectionTags.get(_tmpKey) == null) {
                        _collectionTags.put(_tmpKey, new ArrayList());
                    }
                }
                if (!_cursor.isNull(_cursorIndexOfId)) {
                    String _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
                    if (_collectionProgress.get(_tmpKey_1) == null) {
                        _collectionProgress.put(_tmpKey_1, new ArrayList());
                    }
                }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipWorkTagAsjavaLangString(_collectionTags);
            __fetchRelationshipWorkProgressAsandroidxWorkData(_collectionProgress);
            if (_cursor.moveToFirst()) {
                ArrayList<String> _tmpTagsCollection_1 = null;
                if (!_cursor.isNull(_cursorIndexOfId)) {
                    _tmpTagsCollection_1 = _collectionTags.get(_cursor.getString(_cursorIndexOfId));
                }
                if (_tmpTagsCollection_1 == null) {
                    _tmpTagsCollection_1 = new ArrayList<>();
                }
                ArrayList<Data> _tmpProgressCollection_1 = null;
                if (!_cursor.isNull(_cursorIndexOfId)) {
                    _tmpProgressCollection_1 = _collectionProgress.get(_cursor.getString(_cursorIndexOfId));
                }
                if (_tmpProgressCollection_1 == null) {
                    _tmpProgressCollection_1 = new ArrayList<>();
                }
                _result = new WorkSpec.WorkInfoPojo();
                _result.id = _cursor.getString(_cursorIndexOfId);
                int i = _cursorIndexOfId;
                _result.state = WorkTypeConverters.intToState(_cursor.getInt(_cursorIndexOfState));
                _result.output = Data.fromByteArray(_cursor.getBlob(_cursorIndexOfOutput));
                _result.runAttemptCount = _cursor.getInt(_cursorIndexOfRunAttemptCount);
                _result.tags = _tmpTagsCollection_1;
                _result.progress = _tmpProgressCollection_1;
            } else {
                _result = null;
            }
            this.__db.setTransactionSuccessful();
            _cursor.close();
            _statement.release();
            this.__db.endTransaction();
            return _result;
        } catch (Throwable th) {
            this.__db.endTransaction();
            throw th;
        }
    }

    public List<WorkSpec.WorkInfoPojo> getWorkStatusPojoForIds(List<String> ids) {
        StringBuilder _stringBuilder;
        WorkSpec.WorkInfoPojo _item_1;
        int _inputSize;
        StringBuilder _stringBuilder2 = StringUtil.newStringBuilder();
        _stringBuilder2.append("SELECT id, state, output, run_attempt_count FROM workspec WHERE id IN (");
        int _inputSize2 = ids.size();
        StringUtil.appendPlaceholders(_stringBuilder2, _inputSize2);
        _stringBuilder2.append(")");
        String _sql = _stringBuilder2.toString();
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _inputSize2 + 0);
        int _argIndex = 1;
        for (String _item : ids) {
            if (_item == null) {
                _statement.bindNull(_argIndex);
            } else {
                _statement.bindString(_argIndex, _item);
            }
            _argIndex++;
        }
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            Cursor _cursor = DBUtil.query(this.__db, _statement, true, (CancellationSignal) null);
            try {
                int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
                int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, Constants.ACTION_STATE);
                int _cursorIndexOfOutput = CursorUtil.getColumnIndexOrThrow(_cursor, "output");
                int _cursorIndexOfRunAttemptCount = CursorUtil.getColumnIndexOrThrow(_cursor, "run_attempt_count");
                ArrayMap<String, ArrayList<String>> _collectionTags = new ArrayMap<>();
                ArrayMap<String, ArrayList<Data>> _collectionProgress = new ArrayMap<>();
                while (_cursor.moveToNext()) {
                    try {
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            String _tmpKey = _cursor.getString(_cursorIndexOfId);
                            if (_collectionTags.get(_tmpKey) == null) {
                                _collectionTags.put(_tmpKey, new ArrayList());
                            }
                        }
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            String _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
                            if (_collectionProgress.get(_tmpKey_1) == null) {
                                _collectionProgress.put(_tmpKey_1, new ArrayList());
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        StringBuilder sb = _stringBuilder2;
                        int i = _inputSize2;
                        String str = _sql;
                        _cursor.close();
                        _statement.release();
                        throw th;
                    }
                }
                _cursor.moveToPosition(-1);
                __fetchRelationshipWorkTagAsjavaLangString(_collectionTags);
                __fetchRelationshipWorkProgressAsandroidxWorkData(_collectionProgress);
                List<WorkSpec.WorkInfoPojo> _result = new ArrayList<>(_cursor.getCount());
                while (_cursor.moveToNext()) {
                    ArrayList<String> _tmpTagsCollection_1 = null;
                    if (!_cursor.isNull(_cursorIndexOfId)) {
                        try {
                            _stringBuilder = _stringBuilder2;
                            _tmpTagsCollection_1 = _collectionTags.get(_cursor.getString(_cursorIndexOfId));
                        } catch (Throwable th2) {
                            th = th2;
                            int i2 = _inputSize2;
                            String str2 = _sql;
                            _cursor.close();
                            _statement.release();
                            throw th;
                        }
                    } else {
                        _stringBuilder = _stringBuilder2;
                    }
                    if (_tmpTagsCollection_1 == null) {
                        _tmpTagsCollection_1 = new ArrayList<>();
                    }
                    ArrayList<Data> _tmpProgressCollection_1 = null;
                    try {
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            _tmpProgressCollection_1 = _collectionProgress.get(_cursor.getString(_cursorIndexOfId));
                        }
                        if (_tmpProgressCollection_1 == null) {
                            _tmpProgressCollection_1 = new ArrayList<>();
                        }
                        _item_1 = new WorkSpec.WorkInfoPojo();
                        _inputSize = _inputSize2;
                    } catch (Throwable th3) {
                        th = th3;
                        int i3 = _inputSize2;
                        String str3 = _sql;
                        _cursor.close();
                        _statement.release();
                        throw th;
                    }
                    try {
                        String string = _cursor.getString(_cursorIndexOfId);
                        int _cursorIndexOfId2 = _cursorIndexOfId;
                        WorkSpec.WorkInfoPojo _item_12 = _item_1;
                        _item_12.id = string;
                        int _tmp = _cursor.getInt(_cursorIndexOfState);
                        String _sql2 = _sql;
                        _item_12.state = WorkTypeConverters.intToState(_tmp);
                        int i4 = _tmp;
                        _item_12.output = Data.fromByteArray(_cursor.getBlob(_cursorIndexOfOutput));
                        _item_12.runAttemptCount = _cursor.getInt(_cursorIndexOfRunAttemptCount);
                        _item_12.tags = _tmpTagsCollection_1;
                        _item_12.progress = _tmpProgressCollection_1;
                        _result.add(_item_12);
                        _stringBuilder2 = _stringBuilder;
                        _inputSize2 = _inputSize;
                        _sql = _sql2;
                        _cursorIndexOfId = _cursorIndexOfId2;
                    } catch (Throwable th4) {
                        th = th4;
                        _cursor.close();
                        _statement.release();
                        throw th;
                    }
                }
                int i5 = _cursorIndexOfId;
                StringBuilder sb2 = _stringBuilder2;
                int i6 = _inputSize2;
                String str4 = _sql;
                this.__db.setTransactionSuccessful();
                try {
                    _cursor.close();
                    _statement.release();
                    this.__db.endTransaction();
                    return _result;
                } catch (Throwable th5) {
                    th = th5;
                    this.__db.endTransaction();
                    throw th;
                }
            } catch (Throwable th6) {
                th = th6;
                StringBuilder sb3 = _stringBuilder2;
                int i7 = _inputSize2;
                String str5 = _sql;
                _cursor.close();
                _statement.release();
                throw th;
            }
        } catch (Throwable th7) {
            th = th7;
            StringBuilder sb4 = _stringBuilder2;
            int i8 = _inputSize2;
            String str6 = _sql;
            this.__db.endTransaction();
            throw th;
        }
    }

    public LiveData<List<WorkSpec.WorkInfoPojo>> getWorkStatusPojoLiveDataForIds(List<String> ids) {
        StringBuilder _stringBuilder = StringUtil.newStringBuilder();
        _stringBuilder.append("SELECT id, state, output, run_attempt_count FROM workspec WHERE id IN (");
        int _inputSize = ids.size();
        StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
        _stringBuilder.append(")");
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_stringBuilder.toString(), _inputSize + 0);
        int _argIndex = 1;
        for (String _item : ids) {
            if (_item == null) {
                _statement.bindNull(_argIndex);
            } else {
                _statement.bindString(_argIndex, _item);
            }
            _argIndex++;
        }
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"WorkTag", "WorkProgress", "workspec"}, true, new Callable<List<WorkSpec.WorkInfoPojo>>() {
            public List<WorkSpec.WorkInfoPojo> call() {
                Cursor _cursor;
                WorkSpecDao_Impl.this.__db.beginTransaction();
                try {
                    _cursor = DBUtil.query(WorkSpecDao_Impl.this.__db, _statement, true, (CancellationSignal) null);
                    int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
                    int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, Constants.ACTION_STATE);
                    int _cursorIndexOfOutput = CursorUtil.getColumnIndexOrThrow(_cursor, "output");
                    int _cursorIndexOfRunAttemptCount = CursorUtil.getColumnIndexOrThrow(_cursor, "run_attempt_count");
                    ArrayMap<String, ArrayList<String>> _collectionTags = new ArrayMap<>();
                    ArrayMap<String, ArrayList<Data>> _collectionProgress = new ArrayMap<>();
                    while (_cursor.moveToNext()) {
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            String _tmpKey = _cursor.getString(_cursorIndexOfId);
                            if (_collectionTags.get(_tmpKey) == null) {
                                _collectionTags.put(_tmpKey, new ArrayList());
                            }
                        }
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            String _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
                            if (_collectionProgress.get(_tmpKey_1) == null) {
                                _collectionProgress.put(_tmpKey_1, new ArrayList());
                            }
                        }
                    }
                    _cursor.moveToPosition(-1);
                    WorkSpecDao_Impl.this.__fetchRelationshipWorkTagAsjavaLangString(_collectionTags);
                    WorkSpecDao_Impl.this.__fetchRelationshipWorkProgressAsandroidxWorkData(_collectionProgress);
                    List<WorkSpec.WorkInfoPojo> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        ArrayList<String> _tmpTagsCollection_1 = null;
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            _tmpTagsCollection_1 = _collectionTags.get(_cursor.getString(_cursorIndexOfId));
                        }
                        if (_tmpTagsCollection_1 == null) {
                            _tmpTagsCollection_1 = new ArrayList<>();
                        }
                        ArrayList<Data> _tmpProgressCollection_1 = null;
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            _tmpProgressCollection_1 = _collectionProgress.get(_cursor.getString(_cursorIndexOfId));
                        }
                        if (_tmpProgressCollection_1 == null) {
                            _tmpProgressCollection_1 = new ArrayList<>();
                        }
                        WorkSpec.WorkInfoPojo _item_1 = new WorkSpec.WorkInfoPojo();
                        _item_1.id = _cursor.getString(_cursorIndexOfId);
                        _item_1.state = WorkTypeConverters.intToState(_cursor.getInt(_cursorIndexOfState));
                        _item_1.output = Data.fromByteArray(_cursor.getBlob(_cursorIndexOfOutput));
                        _item_1.runAttemptCount = _cursor.getInt(_cursorIndexOfRunAttemptCount);
                        _item_1.tags = _tmpTagsCollection_1;
                        _item_1.progress = _tmpProgressCollection_1;
                        _result.add(_item_1);
                    }
                    WorkSpecDao_Impl.this.__db.setTransactionSuccessful();
                    _cursor.close();
                    WorkSpecDao_Impl.this.__db.endTransaction();
                    return _result;
                } catch (Throwable th) {
                    WorkSpecDao_Impl.this.__db.endTransaction();
                    throw th;
                }
            }

            /* access modifiers changed from: protected */
            public void finalize() {
                _statement.release();
            }
        });
    }

    public List<WorkSpec.WorkInfoPojo> getWorkStatusPojoForTag(String tag) {
        Cursor _cursor;
        String str = tag;
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count FROM workspec WHERE id IN (SELECT work_spec_id FROM worktag WHERE tag=?)", 1);
        if (str == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, str);
        }
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            _cursor = DBUtil.query(this.__db, _statement, true, (CancellationSignal) null);
            int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, Constants.ACTION_STATE);
            int _cursorIndexOfOutput = CursorUtil.getColumnIndexOrThrow(_cursor, "output");
            int _cursorIndexOfRunAttemptCount = CursorUtil.getColumnIndexOrThrow(_cursor, "run_attempt_count");
            ArrayMap<String, ArrayList<String>> _collectionTags = new ArrayMap<>();
            ArrayMap<String, ArrayList<Data>> _collectionProgress = new ArrayMap<>();
            while (_cursor.moveToNext()) {
                if (!_cursor.isNull(_cursorIndexOfId)) {
                    String _tmpKey = _cursor.getString(_cursorIndexOfId);
                    if (_collectionTags.get(_tmpKey) == null) {
                        _collectionTags.put(_tmpKey, new ArrayList());
                    }
                }
                if (!_cursor.isNull(_cursorIndexOfId)) {
                    String _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
                    if (_collectionProgress.get(_tmpKey_1) == null) {
                        _collectionProgress.put(_tmpKey_1, new ArrayList());
                    }
                }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipWorkTagAsjavaLangString(_collectionTags);
            __fetchRelationshipWorkProgressAsandroidxWorkData(_collectionProgress);
            List<WorkSpec.WorkInfoPojo> _result = new ArrayList<>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                ArrayList<String> _tmpTagsCollection_1 = null;
                if (!_cursor.isNull(_cursorIndexOfId)) {
                    _tmpTagsCollection_1 = _collectionTags.get(_cursor.getString(_cursorIndexOfId));
                }
                if (_tmpTagsCollection_1 == null) {
                    _tmpTagsCollection_1 = new ArrayList<>();
                }
                ArrayList<Data> _tmpProgressCollection_1 = null;
                if (!_cursor.isNull(_cursorIndexOfId)) {
                    _tmpProgressCollection_1 = _collectionProgress.get(_cursor.getString(_cursorIndexOfId));
                }
                if (_tmpProgressCollection_1 == null) {
                    _tmpProgressCollection_1 = new ArrayList<>();
                }
                WorkSpec.WorkInfoPojo _item = new WorkSpec.WorkInfoPojo();
                _item.id = _cursor.getString(_cursorIndexOfId);
                int _tmp = _cursor.getInt(_cursorIndexOfState);
                _item.state = WorkTypeConverters.intToState(_tmp);
                int i = _tmp;
                _item.output = Data.fromByteArray(_cursor.getBlob(_cursorIndexOfOutput));
                _item.runAttemptCount = _cursor.getInt(_cursorIndexOfRunAttemptCount);
                _item.tags = _tmpTagsCollection_1;
                _item.progress = _tmpProgressCollection_1;
                _result.add(_item);
                String str2 = tag;
                _cursorIndexOfId = _cursorIndexOfId;
            }
            this.__db.setTransactionSuccessful();
            _cursor.close();
            _statement.release();
            this.__db.endTransaction();
            return _result;
        } catch (Throwable th) {
            this.__db.endTransaction();
            throw th;
        }
    }

    public LiveData<List<WorkSpec.WorkInfoPojo>> getWorkStatusPojoLiveDataForTag(String tag) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count FROM workspec WHERE id IN (SELECT work_spec_id FROM worktag WHERE tag=?)", 1);
        if (tag == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, tag);
        }
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"WorkTag", "WorkProgress", "workspec", "worktag"}, true, new Callable<List<WorkSpec.WorkInfoPojo>>() {
            public List<WorkSpec.WorkInfoPojo> call() {
                Cursor _cursor;
                WorkSpecDao_Impl.this.__db.beginTransaction();
                try {
                    _cursor = DBUtil.query(WorkSpecDao_Impl.this.__db, _statement, true, (CancellationSignal) null);
                    int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
                    int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, Constants.ACTION_STATE);
                    int _cursorIndexOfOutput = CursorUtil.getColumnIndexOrThrow(_cursor, "output");
                    int _cursorIndexOfRunAttemptCount = CursorUtil.getColumnIndexOrThrow(_cursor, "run_attempt_count");
                    ArrayMap<String, ArrayList<String>> _collectionTags = new ArrayMap<>();
                    ArrayMap<String, ArrayList<Data>> _collectionProgress = new ArrayMap<>();
                    while (_cursor.moveToNext()) {
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            String _tmpKey = _cursor.getString(_cursorIndexOfId);
                            if (_collectionTags.get(_tmpKey) == null) {
                                _collectionTags.put(_tmpKey, new ArrayList());
                            }
                        }
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            String _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
                            if (_collectionProgress.get(_tmpKey_1) == null) {
                                _collectionProgress.put(_tmpKey_1, new ArrayList());
                            }
                        }
                    }
                    _cursor.moveToPosition(-1);
                    WorkSpecDao_Impl.this.__fetchRelationshipWorkTagAsjavaLangString(_collectionTags);
                    WorkSpecDao_Impl.this.__fetchRelationshipWorkProgressAsandroidxWorkData(_collectionProgress);
                    List<WorkSpec.WorkInfoPojo> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        ArrayList<String> _tmpTagsCollection_1 = null;
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            _tmpTagsCollection_1 = _collectionTags.get(_cursor.getString(_cursorIndexOfId));
                        }
                        if (_tmpTagsCollection_1 == null) {
                            _tmpTagsCollection_1 = new ArrayList<>();
                        }
                        ArrayList<Data> _tmpProgressCollection_1 = null;
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            _tmpProgressCollection_1 = _collectionProgress.get(_cursor.getString(_cursorIndexOfId));
                        }
                        if (_tmpProgressCollection_1 == null) {
                            _tmpProgressCollection_1 = new ArrayList<>();
                        }
                        WorkSpec.WorkInfoPojo _item = new WorkSpec.WorkInfoPojo();
                        _item.id = _cursor.getString(_cursorIndexOfId);
                        _item.state = WorkTypeConverters.intToState(_cursor.getInt(_cursorIndexOfState));
                        _item.output = Data.fromByteArray(_cursor.getBlob(_cursorIndexOfOutput));
                        _item.runAttemptCount = _cursor.getInt(_cursorIndexOfRunAttemptCount);
                        _item.tags = _tmpTagsCollection_1;
                        _item.progress = _tmpProgressCollection_1;
                        _result.add(_item);
                    }
                    WorkSpecDao_Impl.this.__db.setTransactionSuccessful();
                    _cursor.close();
                    WorkSpecDao_Impl.this.__db.endTransaction();
                    return _result;
                } catch (Throwable th) {
                    WorkSpecDao_Impl.this.__db.endTransaction();
                    throw th;
                }
            }

            /* access modifiers changed from: protected */
            public void finalize() {
                _statement.release();
            }
        });
    }

    public List<WorkSpec.WorkInfoPojo> getWorkStatusPojoForName(String name) {
        Cursor _cursor;
        String str = name;
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count FROM workspec WHERE id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
        if (str == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, str);
        }
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            _cursor = DBUtil.query(this.__db, _statement, true, (CancellationSignal) null);
            int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, Constants.ACTION_STATE);
            int _cursorIndexOfOutput = CursorUtil.getColumnIndexOrThrow(_cursor, "output");
            int _cursorIndexOfRunAttemptCount = CursorUtil.getColumnIndexOrThrow(_cursor, "run_attempt_count");
            ArrayMap<String, ArrayList<String>> _collectionTags = new ArrayMap<>();
            ArrayMap<String, ArrayList<Data>> _collectionProgress = new ArrayMap<>();
            while (_cursor.moveToNext()) {
                if (!_cursor.isNull(_cursorIndexOfId)) {
                    String _tmpKey = _cursor.getString(_cursorIndexOfId);
                    if (_collectionTags.get(_tmpKey) == null) {
                        _collectionTags.put(_tmpKey, new ArrayList());
                    }
                }
                if (!_cursor.isNull(_cursorIndexOfId)) {
                    String _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
                    if (_collectionProgress.get(_tmpKey_1) == null) {
                        _collectionProgress.put(_tmpKey_1, new ArrayList());
                    }
                }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipWorkTagAsjavaLangString(_collectionTags);
            __fetchRelationshipWorkProgressAsandroidxWorkData(_collectionProgress);
            List<WorkSpec.WorkInfoPojo> _result = new ArrayList<>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                ArrayList<String> _tmpTagsCollection_1 = null;
                if (!_cursor.isNull(_cursorIndexOfId)) {
                    _tmpTagsCollection_1 = _collectionTags.get(_cursor.getString(_cursorIndexOfId));
                }
                if (_tmpTagsCollection_1 == null) {
                    _tmpTagsCollection_1 = new ArrayList<>();
                }
                ArrayList<Data> _tmpProgressCollection_1 = null;
                if (!_cursor.isNull(_cursorIndexOfId)) {
                    _tmpProgressCollection_1 = _collectionProgress.get(_cursor.getString(_cursorIndexOfId));
                }
                if (_tmpProgressCollection_1 == null) {
                    _tmpProgressCollection_1 = new ArrayList<>();
                }
                WorkSpec.WorkInfoPojo _item = new WorkSpec.WorkInfoPojo();
                _item.id = _cursor.getString(_cursorIndexOfId);
                int _tmp = _cursor.getInt(_cursorIndexOfState);
                _item.state = WorkTypeConverters.intToState(_tmp);
                int i = _tmp;
                _item.output = Data.fromByteArray(_cursor.getBlob(_cursorIndexOfOutput));
                _item.runAttemptCount = _cursor.getInt(_cursorIndexOfRunAttemptCount);
                _item.tags = _tmpTagsCollection_1;
                _item.progress = _tmpProgressCollection_1;
                _result.add(_item);
                String str2 = name;
                _cursorIndexOfId = _cursorIndexOfId;
            }
            this.__db.setTransactionSuccessful();
            _cursor.close();
            _statement.release();
            this.__db.endTransaction();
            return _result;
        } catch (Throwable th) {
            this.__db.endTransaction();
            throw th;
        }
    }

    public LiveData<List<WorkSpec.WorkInfoPojo>> getWorkStatusPojoLiveDataForName(String name) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count FROM workspec WHERE id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
        if (name == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, name);
        }
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"WorkTag", "WorkProgress", "workspec", "workname"}, true, new Callable<List<WorkSpec.WorkInfoPojo>>() {
            public List<WorkSpec.WorkInfoPojo> call() {
                Cursor _cursor;
                WorkSpecDao_Impl.this.__db.beginTransaction();
                try {
                    _cursor = DBUtil.query(WorkSpecDao_Impl.this.__db, _statement, true, (CancellationSignal) null);
                    int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
                    int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, Constants.ACTION_STATE);
                    int _cursorIndexOfOutput = CursorUtil.getColumnIndexOrThrow(_cursor, "output");
                    int _cursorIndexOfRunAttemptCount = CursorUtil.getColumnIndexOrThrow(_cursor, "run_attempt_count");
                    ArrayMap<String, ArrayList<String>> _collectionTags = new ArrayMap<>();
                    ArrayMap<String, ArrayList<Data>> _collectionProgress = new ArrayMap<>();
                    while (_cursor.moveToNext()) {
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            String _tmpKey = _cursor.getString(_cursorIndexOfId);
                            if (_collectionTags.get(_tmpKey) == null) {
                                _collectionTags.put(_tmpKey, new ArrayList());
                            }
                        }
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            String _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
                            if (_collectionProgress.get(_tmpKey_1) == null) {
                                _collectionProgress.put(_tmpKey_1, new ArrayList());
                            }
                        }
                    }
                    _cursor.moveToPosition(-1);
                    WorkSpecDao_Impl.this.__fetchRelationshipWorkTagAsjavaLangString(_collectionTags);
                    WorkSpecDao_Impl.this.__fetchRelationshipWorkProgressAsandroidxWorkData(_collectionProgress);
                    List<WorkSpec.WorkInfoPojo> _result = new ArrayList<>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        ArrayList<String> _tmpTagsCollection_1 = null;
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            _tmpTagsCollection_1 = _collectionTags.get(_cursor.getString(_cursorIndexOfId));
                        }
                        if (_tmpTagsCollection_1 == null) {
                            _tmpTagsCollection_1 = new ArrayList<>();
                        }
                        ArrayList<Data> _tmpProgressCollection_1 = null;
                        if (!_cursor.isNull(_cursorIndexOfId)) {
                            _tmpProgressCollection_1 = _collectionProgress.get(_cursor.getString(_cursorIndexOfId));
                        }
                        if (_tmpProgressCollection_1 == null) {
                            _tmpProgressCollection_1 = new ArrayList<>();
                        }
                        WorkSpec.WorkInfoPojo _item = new WorkSpec.WorkInfoPojo();
                        _item.id = _cursor.getString(_cursorIndexOfId);
                        _item.state = WorkTypeConverters.intToState(_cursor.getInt(_cursorIndexOfState));
                        _item.output = Data.fromByteArray(_cursor.getBlob(_cursorIndexOfOutput));
                        _item.runAttemptCount = _cursor.getInt(_cursorIndexOfRunAttemptCount);
                        _item.tags = _tmpTagsCollection_1;
                        _item.progress = _tmpProgressCollection_1;
                        _result.add(_item);
                    }
                    WorkSpecDao_Impl.this.__db.setTransactionSuccessful();
                    _cursor.close();
                    WorkSpecDao_Impl.this.__db.endTransaction();
                    return _result;
                } catch (Throwable th) {
                    WorkSpecDao_Impl.this.__db.endTransaction();
                    throw th;
                }
            }

            /* access modifiers changed from: protected */
            public void finalize() {
                _statement.release();
            }
        });
    }

    public List<Data> getInputsFromPrerequisites(String id) {
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT output FROM workspec WHERE id IN (SELECT prerequisite_id FROM dependency WHERE work_spec_id=?)", 1);
        if (id == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, id);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement, false, (CancellationSignal) null);
        try {
            List<Data> _result = new ArrayList<>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                _result.add(Data.fromByteArray(_cursor.getBlob(0)));
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    public List<String> getUnfinishedWorkWithTag(String tag) {
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT id FROM workspec WHERE state NOT IN (2, 3, 5) AND id IN (SELECT work_spec_id FROM worktag WHERE tag=?)", 1);
        if (tag == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, tag);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement, false, (CancellationSignal) null);
        try {
            List<String> _result = new ArrayList<>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                _result.add(_cursor.getString(0));
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    public List<String> getUnfinishedWorkWithName(String name) {
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT id FROM workspec WHERE state NOT IN (2, 3, 5) AND id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
        if (name == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, name);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement, false, (CancellationSignal) null);
        try {
            List<String> _result = new ArrayList<>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                _result.add(_cursor.getString(0));
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    public List<String> getAllUnfinishedWork() {
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT id FROM workspec WHERE state NOT IN (2, 3, 5)", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement, false, (CancellationSignal) null);
        try {
            List<String> _result = new ArrayList<>(_cursor.getCount());
            while (_cursor.moveToNext()) {
                _result.add(_cursor.getString(0));
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    public boolean hasUnfinishedWork() {
        boolean _result = false;
        RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT COUNT(*) > 0 FROM workspec WHERE state NOT IN (2, 3, 5) LIMIT 1", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement, false, (CancellationSignal) null);
        try {
            if (!_cursor.moveToFirst()) {
                _result = false;
            } else if (_cursor.getInt(0) != 0) {
                _result = true;
            }
            return _result;
        } finally {
            _cursor.close();
            _statement.release();
        }
    }

    public LiveData<Long> getScheduleRequestedAtLiveData(String id) {
        final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire("SELECT schedule_requested_at FROM workspec WHERE id=?", 1);
        if (id == null) {
            _statement.bindNull(1);
        } else {
            _statement.bindString(1, id);
        }
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"workspec"}, false, new Callable<Long>() {
            public Long call() {
                Long _result;
                Cursor _cursor = DBUtil.query(WorkSpecDao_Impl.this.__db, _statement, false, (CancellationSignal) null);
                try {
                    if (!_cursor.moveToFirst()) {
                        _result = null;
                    } else if (_cursor.isNull(0)) {
                        _result = null;
                    } else {
                        _result = Long.valueOf(_cursor.getLong(0));
                    }
                    return _result;
                } finally {
                    _cursor.close();
                }
            }

            /* access modifiers changed from: protected */
            public void finalize() {
                _statement.release();
            }
        });
    }

    public List<WorkSpec> getEligibleWorkForScheduling(int schedulerLimit) {
        RoomSQLiteQuery _statement;
        int _cursorIndexOfInputMergerClassName;
        int _cursorIndexOfScheduleRequestedAt;
        int _cursorIndexOfOutput;
        boolean z;
        RoomSQLiteQuery _statement2 = RoomSQLiteQuery.acquire("SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE state=0 AND schedule_requested_at=-1 ORDER BY period_start_time LIMIT (SELECT MAX(?-COUNT(*), 0) FROM workspec WHERE schedule_requested_at<>-1 AND state NOT IN (2, 3, 5))", 1);
        _statement2.bindLong(1, (long) schedulerLimit);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement2, false, (CancellationSignal) null);
        try {
            int _cursorIndexOfMRequiredNetworkType = CursorUtil.getColumnIndexOrThrow(_cursor, "required_network_type");
            int _cursorIndexOfMRequiresCharging = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_charging");
            int _cursorIndexOfMRequiresDeviceIdle = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_device_idle");
            int _cursorIndexOfMRequiresBatteryNotLow = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_battery_not_low");
            int _cursorIndexOfMRequiresStorageNotLow = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_storage_not_low");
            int _cursorIndexOfMTriggerContentUpdateDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "trigger_content_update_delay");
            int _cursorIndexOfMTriggerMaxContentDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "trigger_max_content_delay");
            int _cursorIndexOfMContentUriTriggers = CursorUtil.getColumnIndexOrThrow(_cursor, "content_uri_triggers");
            int _tmp_10 = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, Constants.ACTION_STATE);
            int _cursorIndexOfWorkerClassName = CursorUtil.getColumnIndexOrThrow(_cursor, "worker_class_name");
            Object obj = "SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE state=0 AND schedule_requested_at=-1 ORDER BY period_start_time LIMIT (SELECT MAX(?-COUNT(*), 0) FROM workspec WHERE schedule_requested_at<>-1 AND state NOT IN (2, 3, 5))";
            try {
                _cursorIndexOfInputMergerClassName = CursorUtil.getColumnIndexOrThrow(_cursor, "input_merger_class_name");
            } catch (Throwable th) {
                th = th;
                _statement = _statement2;
                _cursor.close();
                _statement.release();
                throw th;
            }
            try {
                _cursorIndexOfScheduleRequestedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "input");
                _cursorIndexOfOutput = CursorUtil.getColumnIndexOrThrow(_cursor, "output");
                _statement = _statement2;
            } catch (Throwable th2) {
                th = th2;
                _statement = _statement2;
                _cursor.close();
                _statement.release();
                throw th;
            }
            try {
                int _cursorIndexOfInput = CursorUtil.getColumnIndexOrThrow(_cursor, "initial_delay");
                int _cursorIndexOfInitialDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "interval_duration");
                int _cursorIndexOfFlexDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "flex_duration");
                int _tmp_9 = CursorUtil.getColumnIndexOrThrow(_cursor, "run_attempt_count");
                int _cursorIndexOfFlexDuration2 = CursorUtil.getColumnIndexOrThrow(_cursor, "backoff_policy");
                int _cursorIndexOfRunAttemptCount = CursorUtil.getColumnIndexOrThrow(_cursor, "backoff_delay_duration");
                int _cursorIndexOfBackoffDelayDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "period_start_time");
                int _cursorIndexOfIntervalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "minimum_retention_duration");
                int _cursorIndexOfPeriodStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "schedule_requested_at");
                int _cursorIndexOfExpedited = CursorUtil.getColumnIndexOrThrow(_cursor, "run_in_foreground");
                int _tmp_11 = CursorUtil.getColumnIndexOrThrow(_cursor, "out_of_quota_policy");
                int _cursorIndexOfOutput2 = _cursorIndexOfOutput;
                List<WorkSpec> _result = new ArrayList<>(_cursor.getCount());
                while (_cursor.moveToNext()) {
                    String _tmpId = _cursor.getString(_tmp_10);
                    String _tmpWorkerClassName = _cursor.getString(_cursorIndexOfWorkerClassName);
                    Constraints _tmpConstraints = new Constraints();
                    NetworkType _tmpMRequiredNetworkType = WorkTypeConverters.intToNetworkType(_cursor.getInt(_cursorIndexOfMRequiredNetworkType));
                    int _cursorIndexOfMRequiredNetworkType2 = _cursorIndexOfMRequiredNetworkType;
                    Constraints _tmpConstraints2 = _tmpConstraints;
                    int _cursorIndexOfWorkerClassName2 = _cursorIndexOfWorkerClassName;
                    NetworkType _tmpMRequiredNetworkType2 = _tmpMRequiredNetworkType;
                    _tmpConstraints2.setRequiredNetworkType(_tmpMRequiredNetworkType2);
                    NetworkType networkType = _tmpMRequiredNetworkType2;
                    boolean _tmpMRequiresCharging = _cursor.getInt(_cursorIndexOfMRequiresCharging) != 0;
                    _tmpConstraints2.setRequiresCharging(_tmpMRequiresCharging);
                    boolean z2 = _tmpMRequiresCharging;
                    boolean _tmpMRequiresCharging2 = _cursor.getInt(_cursorIndexOfMRequiresDeviceIdle) != 0;
                    _tmpConstraints2.setRequiresDeviceIdle(_tmpMRequiresCharging2);
                    boolean z3 = _tmpMRequiresCharging2;
                    boolean _tmpMRequiresBatteryNotLow = _cursor.getInt(_cursorIndexOfMRequiresBatteryNotLow) != 0;
                    _tmpConstraints2.setRequiresBatteryNotLow(_tmpMRequiresBatteryNotLow);
                    boolean z4 = _tmpMRequiresBatteryNotLow;
                    boolean _tmpMRequiresBatteryNotLow2 = _cursor.getInt(_cursorIndexOfMRequiresStorageNotLow) != 0;
                    _tmpConstraints2.setRequiresStorageNotLow(_tmpMRequiresBatteryNotLow2);
                    int _cursorIndexOfMRequiresCharging2 = _cursorIndexOfMRequiresCharging;
                    int _cursorIndexOfMRequiresDeviceIdle2 = _cursorIndexOfMRequiresDeviceIdle;
                    long _tmpMTriggerContentUpdateDelay = _cursor.getLong(_cursorIndexOfMTriggerContentUpdateDelay);
                    _tmpConstraints2.setTriggerContentUpdateDelay(_tmpMTriggerContentUpdateDelay);
                    long j = _tmpMTriggerContentUpdateDelay;
                    _tmpConstraints2.setTriggerMaxContentDelay(_cursor.getLong(_cursorIndexOfMTriggerMaxContentDelay));
                    boolean z5 = _tmpMRequiresBatteryNotLow2;
                    ContentUriTriggers _tmpMContentUriTriggers = WorkTypeConverters.byteArrayToContentUriTriggers(_cursor.getBlob(_cursorIndexOfMContentUriTriggers));
                    _tmpConstraints2.setContentUriTriggers(_tmpMContentUriTriggers);
                    ContentUriTriggers contentUriTriggers = _tmpMContentUriTriggers;
                    int _cursorIndexOfId = _tmp_10;
                    String _tmpWorkerClassName2 = _tmpWorkerClassName;
                    WorkSpec _item = new WorkSpec(_tmpId, _tmpWorkerClassName2);
                    String str = _tmpWorkerClassName2;
                    _item.state = WorkTypeConverters.intToState(_cursor.getInt(_cursorIndexOfState));
                    _item.inputMergerClassName = _cursor.getString(_cursorIndexOfInputMergerClassName);
                    byte[] _tmp_7 = _cursor.getBlob(_cursorIndexOfScheduleRequestedAt);
                    int _cursorIndexOfInputMergerClassName2 = _cursorIndexOfInputMergerClassName;
                    _item.input = Data.fromByteArray(_tmp_7);
                    int _cursorIndexOfOutput3 = _cursorIndexOfOutput2;
                    int _cursorIndexOfOutput4 = _cursorIndexOfOutput3;
                    _item.output = Data.fromByteArray(_cursor.getBlob(_cursorIndexOfOutput3));
                    String str2 = _tmpId;
                    int _cursorIndexOfInitialDelay2 = _cursorIndexOfInput;
                    int _cursorIndexOfInput2 = _cursorIndexOfScheduleRequestedAt;
                    _item.initialDelay = _cursor.getLong(_cursorIndexOfInitialDelay2);
                    byte[] _tmp_72 = _tmp_7;
                    int _cursorIndexOfIntervalDuration2 = _cursorIndexOfInitialDelay;
                    int _cursorIndexOfInitialDelay3 = _cursorIndexOfInitialDelay2;
                    _item.intervalDuration = _cursor.getLong(_cursorIndexOfIntervalDuration2);
                    int _cursorIndexOfIntervalDuration3 = _cursorIndexOfIntervalDuration2;
                    int _cursorIndexOfFlexDuration3 = _cursorIndexOfFlexDuration;
                    byte[] bArr = _tmp_72;
                    _item.flexDuration = _cursor.getLong(_cursorIndexOfFlexDuration3);
                    int _cursorIndexOfRunAttemptCount2 = _tmp_9;
                    _item.runAttemptCount = _cursor.getInt(_cursorIndexOfRunAttemptCount2);
                    int _cursorIndexOfBackoffPolicy = _cursorIndexOfFlexDuration2;
                    int _cursorIndexOfFlexDuration4 = _cursorIndexOfFlexDuration3;
                    _item.backoffPolicy = WorkTypeConverters.intToBackoffPolicy(_cursor.getInt(_cursorIndexOfBackoffPolicy));
                    int _cursorIndexOfBackoffPolicy2 = _cursorIndexOfBackoffPolicy;
                    int _cursorIndexOfBackoffDelayDuration2 = _cursorIndexOfRunAttemptCount;
                    int _cursorIndexOfRunAttemptCount3 = _cursorIndexOfRunAttemptCount2;
                    _item.backoffDelayDuration = _cursor.getLong(_cursorIndexOfBackoffDelayDuration2);
                    int _cursorIndexOfIntervalDuration4 = _cursorIndexOfIntervalDuration3;
                    int _cursorIndexOfPeriodStartTime2 = _cursorIndexOfBackoffDelayDuration;
                    int _cursorIndexOfPeriodStartTime3 = _cursorIndexOfBackoffDelayDuration2;
                    _item.periodStartTime = _cursor.getLong(_cursorIndexOfPeriodStartTime2);
                    int _cursorIndexOfPeriodStartTime4 = _cursorIndexOfPeriodStartTime2;
                    int _cursorIndexOfMinimumRetentionDuration = _cursorIndexOfIntervalDuration;
                    int _cursorIndexOfIntervalDuration5 = _cursorIndexOfIntervalDuration4;
                    _item.minimumRetentionDuration = _cursor.getLong(_cursorIndexOfMinimumRetentionDuration);
                    int _cursorIndexOfMinimumRetentionDuration2 = _cursorIndexOfMinimumRetentionDuration;
                    int _cursorIndexOfScheduleRequestedAt2 = _cursorIndexOfPeriodStartTime;
                    int _cursorIndexOfScheduleRequestedAt3 = _cursorIndexOfPeriodStartTime4;
                    _item.scheduleRequestedAt = _cursor.getLong(_cursorIndexOfScheduleRequestedAt2);
                    int _cursorIndexOfExpedited2 = _cursorIndexOfExpedited;
                    if (_cursor.getInt(_cursorIndexOfExpedited2) != 0) {
                        _cursorIndexOfExpedited = _cursorIndexOfExpedited2;
                        z = true;
                    } else {
                        _cursorIndexOfExpedited = _cursorIndexOfExpedited2;
                        z = false;
                    }
                    _item.expedited = z;
                    int _cursorIndexOfOutOfQuotaPolicy = _tmp_11;
                    int _cursorIndexOfOutOfQuotaPolicy2 = _cursorIndexOfOutOfQuotaPolicy;
                    _item.outOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(_cursor.getInt(_cursorIndexOfOutOfQuotaPolicy));
                    _item.constraints = _tmpConstraints2;
                    _result.add(_item);
                    _cursorIndexOfFlexDuration = _cursorIndexOfFlexDuration4;
                    _tmp_9 = _cursorIndexOfRunAttemptCount3;
                    _cursorIndexOfRunAttemptCount = _cursorIndexOfPeriodStartTime3;
                    _cursorIndexOfBackoffDelayDuration = _cursorIndexOfScheduleRequestedAt3;
                    _cursorIndexOfWorkerClassName = _cursorIndexOfWorkerClassName2;
                    _cursorIndexOfMRequiredNetworkType = _cursorIndexOfMRequiredNetworkType2;
                    _cursorIndexOfMRequiresCharging = _cursorIndexOfMRequiresCharging2;
                    _cursorIndexOfMRequiresDeviceIdle = _cursorIndexOfMRequiresDeviceIdle2;
                    _tmp_10 = _cursorIndexOfId;
                    _cursorIndexOfInputMergerClassName = _cursorIndexOfInputMergerClassName2;
                    _cursorIndexOfOutput2 = _cursorIndexOfOutput4;
                    _cursorIndexOfFlexDuration2 = _cursorIndexOfBackoffPolicy2;
                    _tmp_11 = _cursorIndexOfOutOfQuotaPolicy2;
                    _cursorIndexOfPeriodStartTime = _cursorIndexOfScheduleRequestedAt2;
                    _cursorIndexOfScheduleRequestedAt = _cursorIndexOfInput2;
                    _cursorIndexOfInput = _cursorIndexOfInitialDelay3;
                    _cursorIndexOfInitialDelay = _cursorIndexOfIntervalDuration5;
                    _cursorIndexOfIntervalDuration = _cursorIndexOfMinimumRetentionDuration2;
                }
                _cursor.close();
                _statement.release();
                return _result;
            } catch (Throwable th3) {
                th = th3;
                _cursor.close();
                _statement.release();
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            Object obj2 = "SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE state=0 AND schedule_requested_at=-1 ORDER BY period_start_time LIMIT (SELECT MAX(?-COUNT(*), 0) FROM workspec WHERE schedule_requested_at<>-1 AND state NOT IN (2, 3, 5))";
            _statement = _statement2;
            _cursor.close();
            _statement.release();
            throw th;
        }
    }

    public List<WorkSpec> getAllEligibleWorkSpecsForScheduling(int maxLimit) {
        RoomSQLiteQuery _statement;
        int _cursorIndexOfInputMergerClassName;
        int _cursorIndexOfScheduleRequestedAt;
        int _cursorIndexOfOutput;
        boolean z;
        RoomSQLiteQuery _statement2 = RoomSQLiteQuery.acquire("SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE state=0 ORDER BY period_start_time LIMIT ?", 1);
        _statement2.bindLong(1, (long) maxLimit);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement2, false, (CancellationSignal) null);
        try {
            int _cursorIndexOfMRequiredNetworkType = CursorUtil.getColumnIndexOrThrow(_cursor, "required_network_type");
            int _cursorIndexOfMRequiresCharging = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_charging");
            int _cursorIndexOfMRequiresDeviceIdle = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_device_idle");
            int _cursorIndexOfMRequiresBatteryNotLow = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_battery_not_low");
            int _cursorIndexOfMRequiresStorageNotLow = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_storage_not_low");
            int _cursorIndexOfMTriggerContentUpdateDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "trigger_content_update_delay");
            int _cursorIndexOfMTriggerMaxContentDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "trigger_max_content_delay");
            int _cursorIndexOfMContentUriTriggers = CursorUtil.getColumnIndexOrThrow(_cursor, "content_uri_triggers");
            int _tmp_10 = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, Constants.ACTION_STATE);
            int _cursorIndexOfWorkerClassName = CursorUtil.getColumnIndexOrThrow(_cursor, "worker_class_name");
            Object obj = "SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE state=0 ORDER BY period_start_time LIMIT ?";
            try {
                _cursorIndexOfInputMergerClassName = CursorUtil.getColumnIndexOrThrow(_cursor, "input_merger_class_name");
            } catch (Throwable th) {
                th = th;
                _statement = _statement2;
                _cursor.close();
                _statement.release();
                throw th;
            }
            try {
                _cursorIndexOfScheduleRequestedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "input");
                _cursorIndexOfOutput = CursorUtil.getColumnIndexOrThrow(_cursor, "output");
                _statement = _statement2;
            } catch (Throwable th2) {
                th = th2;
                _statement = _statement2;
                _cursor.close();
                _statement.release();
                throw th;
            }
            try {
                int _cursorIndexOfInput = CursorUtil.getColumnIndexOrThrow(_cursor, "initial_delay");
                int _cursorIndexOfInitialDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "interval_duration");
                int _cursorIndexOfFlexDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "flex_duration");
                int _tmp_9 = CursorUtil.getColumnIndexOrThrow(_cursor, "run_attempt_count");
                int _cursorIndexOfFlexDuration2 = CursorUtil.getColumnIndexOrThrow(_cursor, "backoff_policy");
                int _cursorIndexOfRunAttemptCount = CursorUtil.getColumnIndexOrThrow(_cursor, "backoff_delay_duration");
                int _cursorIndexOfBackoffDelayDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "period_start_time");
                int _cursorIndexOfIntervalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "minimum_retention_duration");
                int _cursorIndexOfPeriodStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "schedule_requested_at");
                int _cursorIndexOfExpedited = CursorUtil.getColumnIndexOrThrow(_cursor, "run_in_foreground");
                int _tmp_11 = CursorUtil.getColumnIndexOrThrow(_cursor, "out_of_quota_policy");
                int _cursorIndexOfOutput2 = _cursorIndexOfOutput;
                List<WorkSpec> _result = new ArrayList<>(_cursor.getCount());
                while (_cursor.moveToNext()) {
                    String _tmpId = _cursor.getString(_tmp_10);
                    String _tmpWorkerClassName = _cursor.getString(_cursorIndexOfWorkerClassName);
                    Constraints _tmpConstraints = new Constraints();
                    NetworkType _tmpMRequiredNetworkType = WorkTypeConverters.intToNetworkType(_cursor.getInt(_cursorIndexOfMRequiredNetworkType));
                    int _cursorIndexOfMRequiredNetworkType2 = _cursorIndexOfMRequiredNetworkType;
                    Constraints _tmpConstraints2 = _tmpConstraints;
                    int _cursorIndexOfWorkerClassName2 = _cursorIndexOfWorkerClassName;
                    NetworkType _tmpMRequiredNetworkType2 = _tmpMRequiredNetworkType;
                    _tmpConstraints2.setRequiredNetworkType(_tmpMRequiredNetworkType2);
                    NetworkType networkType = _tmpMRequiredNetworkType2;
                    boolean _tmpMRequiresCharging = _cursor.getInt(_cursorIndexOfMRequiresCharging) != 0;
                    _tmpConstraints2.setRequiresCharging(_tmpMRequiresCharging);
                    boolean z2 = _tmpMRequiresCharging;
                    boolean _tmpMRequiresCharging2 = _cursor.getInt(_cursorIndexOfMRequiresDeviceIdle) != 0;
                    _tmpConstraints2.setRequiresDeviceIdle(_tmpMRequiresCharging2);
                    boolean z3 = _tmpMRequiresCharging2;
                    boolean _tmpMRequiresBatteryNotLow = _cursor.getInt(_cursorIndexOfMRequiresBatteryNotLow) != 0;
                    _tmpConstraints2.setRequiresBatteryNotLow(_tmpMRequiresBatteryNotLow);
                    boolean z4 = _tmpMRequiresBatteryNotLow;
                    boolean _tmpMRequiresBatteryNotLow2 = _cursor.getInt(_cursorIndexOfMRequiresStorageNotLow) != 0;
                    _tmpConstraints2.setRequiresStorageNotLow(_tmpMRequiresBatteryNotLow2);
                    int _cursorIndexOfMRequiresCharging2 = _cursorIndexOfMRequiresCharging;
                    int _cursorIndexOfMRequiresDeviceIdle2 = _cursorIndexOfMRequiresDeviceIdle;
                    long _tmpMTriggerContentUpdateDelay = _cursor.getLong(_cursorIndexOfMTriggerContentUpdateDelay);
                    _tmpConstraints2.setTriggerContentUpdateDelay(_tmpMTriggerContentUpdateDelay);
                    long j = _tmpMTriggerContentUpdateDelay;
                    _tmpConstraints2.setTriggerMaxContentDelay(_cursor.getLong(_cursorIndexOfMTriggerMaxContentDelay));
                    boolean z5 = _tmpMRequiresBatteryNotLow2;
                    ContentUriTriggers _tmpMContentUriTriggers = WorkTypeConverters.byteArrayToContentUriTriggers(_cursor.getBlob(_cursorIndexOfMContentUriTriggers));
                    _tmpConstraints2.setContentUriTriggers(_tmpMContentUriTriggers);
                    ContentUriTriggers contentUriTriggers = _tmpMContentUriTriggers;
                    int _cursorIndexOfId = _tmp_10;
                    String _tmpWorkerClassName2 = _tmpWorkerClassName;
                    WorkSpec _item = new WorkSpec(_tmpId, _tmpWorkerClassName2);
                    String str = _tmpWorkerClassName2;
                    _item.state = WorkTypeConverters.intToState(_cursor.getInt(_cursorIndexOfState));
                    _item.inputMergerClassName = _cursor.getString(_cursorIndexOfInputMergerClassName);
                    byte[] _tmp_7 = _cursor.getBlob(_cursorIndexOfScheduleRequestedAt);
                    int _cursorIndexOfInputMergerClassName2 = _cursorIndexOfInputMergerClassName;
                    _item.input = Data.fromByteArray(_tmp_7);
                    int _cursorIndexOfOutput3 = _cursorIndexOfOutput2;
                    int _cursorIndexOfOutput4 = _cursorIndexOfOutput3;
                    _item.output = Data.fromByteArray(_cursor.getBlob(_cursorIndexOfOutput3));
                    String str2 = _tmpId;
                    int _cursorIndexOfInitialDelay2 = _cursorIndexOfInput;
                    int _cursorIndexOfInput2 = _cursorIndexOfScheduleRequestedAt;
                    _item.initialDelay = _cursor.getLong(_cursorIndexOfInitialDelay2);
                    byte[] _tmp_72 = _tmp_7;
                    int _cursorIndexOfIntervalDuration2 = _cursorIndexOfInitialDelay;
                    int _cursorIndexOfInitialDelay3 = _cursorIndexOfInitialDelay2;
                    _item.intervalDuration = _cursor.getLong(_cursorIndexOfIntervalDuration2);
                    int _cursorIndexOfIntervalDuration3 = _cursorIndexOfIntervalDuration2;
                    int _cursorIndexOfFlexDuration3 = _cursorIndexOfFlexDuration;
                    byte[] bArr = _tmp_72;
                    _item.flexDuration = _cursor.getLong(_cursorIndexOfFlexDuration3);
                    int _cursorIndexOfRunAttemptCount2 = _tmp_9;
                    _item.runAttemptCount = _cursor.getInt(_cursorIndexOfRunAttemptCount2);
                    int _cursorIndexOfBackoffPolicy = _cursorIndexOfFlexDuration2;
                    int _cursorIndexOfFlexDuration4 = _cursorIndexOfFlexDuration3;
                    _item.backoffPolicy = WorkTypeConverters.intToBackoffPolicy(_cursor.getInt(_cursorIndexOfBackoffPolicy));
                    int _cursorIndexOfBackoffPolicy2 = _cursorIndexOfBackoffPolicy;
                    int _cursorIndexOfBackoffDelayDuration2 = _cursorIndexOfRunAttemptCount;
                    int _cursorIndexOfRunAttemptCount3 = _cursorIndexOfRunAttemptCount2;
                    _item.backoffDelayDuration = _cursor.getLong(_cursorIndexOfBackoffDelayDuration2);
                    int _cursorIndexOfIntervalDuration4 = _cursorIndexOfIntervalDuration3;
                    int _cursorIndexOfPeriodStartTime2 = _cursorIndexOfBackoffDelayDuration;
                    int _cursorIndexOfPeriodStartTime3 = _cursorIndexOfBackoffDelayDuration2;
                    _item.periodStartTime = _cursor.getLong(_cursorIndexOfPeriodStartTime2);
                    int _cursorIndexOfPeriodStartTime4 = _cursorIndexOfPeriodStartTime2;
                    int _cursorIndexOfMinimumRetentionDuration = _cursorIndexOfIntervalDuration;
                    int _cursorIndexOfIntervalDuration5 = _cursorIndexOfIntervalDuration4;
                    _item.minimumRetentionDuration = _cursor.getLong(_cursorIndexOfMinimumRetentionDuration);
                    int _cursorIndexOfMinimumRetentionDuration2 = _cursorIndexOfMinimumRetentionDuration;
                    int _cursorIndexOfScheduleRequestedAt2 = _cursorIndexOfPeriodStartTime;
                    int _cursorIndexOfScheduleRequestedAt3 = _cursorIndexOfPeriodStartTime4;
                    _item.scheduleRequestedAt = _cursor.getLong(_cursorIndexOfScheduleRequestedAt2);
                    int _cursorIndexOfExpedited2 = _cursorIndexOfExpedited;
                    if (_cursor.getInt(_cursorIndexOfExpedited2) != 0) {
                        _cursorIndexOfExpedited = _cursorIndexOfExpedited2;
                        z = true;
                    } else {
                        _cursorIndexOfExpedited = _cursorIndexOfExpedited2;
                        z = false;
                    }
                    _item.expedited = z;
                    int _cursorIndexOfOutOfQuotaPolicy = _tmp_11;
                    int _cursorIndexOfOutOfQuotaPolicy2 = _cursorIndexOfOutOfQuotaPolicy;
                    _item.outOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(_cursor.getInt(_cursorIndexOfOutOfQuotaPolicy));
                    _item.constraints = _tmpConstraints2;
                    _result.add(_item);
                    _cursorIndexOfFlexDuration = _cursorIndexOfFlexDuration4;
                    _tmp_9 = _cursorIndexOfRunAttemptCount3;
                    _cursorIndexOfRunAttemptCount = _cursorIndexOfPeriodStartTime3;
                    _cursorIndexOfBackoffDelayDuration = _cursorIndexOfScheduleRequestedAt3;
                    _cursorIndexOfWorkerClassName = _cursorIndexOfWorkerClassName2;
                    _cursorIndexOfMRequiredNetworkType = _cursorIndexOfMRequiredNetworkType2;
                    _cursorIndexOfMRequiresCharging = _cursorIndexOfMRequiresCharging2;
                    _cursorIndexOfMRequiresDeviceIdle = _cursorIndexOfMRequiresDeviceIdle2;
                    _tmp_10 = _cursorIndexOfId;
                    _cursorIndexOfInputMergerClassName = _cursorIndexOfInputMergerClassName2;
                    _cursorIndexOfOutput2 = _cursorIndexOfOutput4;
                    _cursorIndexOfFlexDuration2 = _cursorIndexOfBackoffPolicy2;
                    _tmp_11 = _cursorIndexOfOutOfQuotaPolicy2;
                    _cursorIndexOfPeriodStartTime = _cursorIndexOfScheduleRequestedAt2;
                    _cursorIndexOfScheduleRequestedAt = _cursorIndexOfInput2;
                    _cursorIndexOfInput = _cursorIndexOfInitialDelay3;
                    _cursorIndexOfInitialDelay = _cursorIndexOfIntervalDuration5;
                    _cursorIndexOfIntervalDuration = _cursorIndexOfMinimumRetentionDuration2;
                }
                _cursor.close();
                _statement.release();
                return _result;
            } catch (Throwable th3) {
                th = th3;
                _cursor.close();
                _statement.release();
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            Object obj2 = "SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE state=0 ORDER BY period_start_time LIMIT ?";
            _statement = _statement2;
            _cursor.close();
            _statement.release();
            throw th;
        }
    }

    public List<WorkSpec> getScheduledWork() {
        RoomSQLiteQuery _statement;
        int _cursorIndexOfOutput;
        int _cursorIndexOfBackoffDelayDuration;
        boolean z;
        RoomSQLiteQuery _statement2 = RoomSQLiteQuery.acquire("SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE state=0 AND schedule_requested_at<>-1", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement2, false, (CancellationSignal) null);
        try {
            int _cursorIndexOfMRequiredNetworkType = CursorUtil.getColumnIndexOrThrow(_cursor, "required_network_type");
            int _cursorIndexOfMRequiresCharging = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_charging");
            int _cursorIndexOfMRequiresDeviceIdle = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_device_idle");
            int _cursorIndexOfExpedited = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_battery_not_low");
            int _tmp_10 = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_storage_not_low");
            int _cursorIndexOfMTriggerContentUpdateDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "trigger_content_update_delay");
            int _cursorIndexOfMTriggerMaxContentDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "trigger_max_content_delay");
            int _cursorIndexOfMContentUriTriggers = CursorUtil.getColumnIndexOrThrow(_cursor, "content_uri_triggers");
            int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, Constants.ACTION_STATE);
            int _cursorIndexOfWorkerClassName = CursorUtil.getColumnIndexOrThrow(_cursor, "worker_class_name");
            int _cursorIndexOfScheduleRequestedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "input_merger_class_name");
            int _cursorIndexOfInput = CursorUtil.getColumnIndexOrThrow(_cursor, "input");
            Object obj = "SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE state=0 AND schedule_requested_at<>-1";
            try {
                _cursorIndexOfOutput = CursorUtil.getColumnIndexOrThrow(_cursor, "output");
                _statement = _statement2;
            } catch (Throwable th) {
                th = th;
                _statement = _statement2;
                _cursor.close();
                _statement.release();
                throw th;
            }
            try {
                int _cursorIndexOfInputMergerClassName = CursorUtil.getColumnIndexOrThrow(_cursor, "initial_delay");
                int _cursorIndexOfMRequiresStorageNotLow = CursorUtil.getColumnIndexOrThrow(_cursor, "interval_duration");
                int _cursorIndexOfMRequiresBatteryNotLow = CursorUtil.getColumnIndexOrThrow(_cursor, "flex_duration");
                int _tmp_9 = CursorUtil.getColumnIndexOrThrow(_cursor, "run_attempt_count");
                int _cursorIndexOfInitialDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "backoff_policy");
                int _cursorIndexOfRunAttemptCount = CursorUtil.getColumnIndexOrThrow(_cursor, "backoff_delay_duration");
                int _cursorIndexOfIntervalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "period_start_time");
                int _cursorIndexOfFlexDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "minimum_retention_duration");
                int _cursorIndexOfPeriodStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "schedule_requested_at");
                int _cursorIndexOfBackoffDelayDuration2 = CursorUtil.getColumnIndexOrThrow(_cursor, "run_in_foreground");
                int _tmp_11 = CursorUtil.getColumnIndexOrThrow(_cursor, "out_of_quota_policy");
                int _cursorIndexOfOutput2 = _cursorIndexOfOutput;
                List<WorkSpec> _result = new ArrayList<>(_cursor.getCount());
                while (_cursor.moveToNext()) {
                    String _tmpId = _cursor.getString(_cursorIndexOfId);
                    String _tmpWorkerClassName = _cursor.getString(_cursorIndexOfWorkerClassName);
                    Constraints _tmpConstraints = new Constraints();
                    NetworkType _tmpMRequiredNetworkType = WorkTypeConverters.intToNetworkType(_cursor.getInt(_cursorIndexOfMRequiredNetworkType));
                    int _cursorIndexOfMRequiredNetworkType2 = _cursorIndexOfMRequiredNetworkType;
                    Constraints _tmpConstraints2 = _tmpConstraints;
                    int _cursorIndexOfId2 = _cursorIndexOfId;
                    _tmpConstraints2.setRequiredNetworkType(_tmpMRequiredNetworkType);
                    int _cursorIndexOfMRequiresCharging2 = _cursorIndexOfMRequiresCharging;
                    boolean _tmpMRequiresCharging = _cursor.getInt(_cursorIndexOfMRequiresCharging) != 0;
                    _tmpConstraints2.setRequiresCharging(_tmpMRequiresCharging);
                    boolean z2 = _tmpMRequiresCharging;
                    boolean _tmpMRequiresCharging2 = _cursor.getInt(_cursorIndexOfMRequiresDeviceIdle) != 0;
                    _tmpConstraints2.setRequiresDeviceIdle(_tmpMRequiresCharging2);
                    boolean z3 = _tmpMRequiresCharging2;
                    boolean _tmpMRequiresBatteryNotLow = _cursor.getInt(_cursorIndexOfExpedited) != 0;
                    _tmpConstraints2.setRequiresBatteryNotLow(_tmpMRequiresBatteryNotLow);
                    boolean z4 = _tmpMRequiresBatteryNotLow;
                    boolean _tmpMRequiresBatteryNotLow2 = _cursor.getInt(_tmp_10) != 0;
                    _tmpConstraints2.setRequiresStorageNotLow(_tmpMRequiresBatteryNotLow2);
                    boolean z5 = _tmpMRequiresBatteryNotLow2;
                    int _cursorIndexOfMRequiresDeviceIdle2 = _cursorIndexOfMRequiresDeviceIdle;
                    long _tmpMTriggerContentUpdateDelay = _cursor.getLong(_cursorIndexOfMTriggerContentUpdateDelay);
                    _tmpConstraints2.setTriggerContentUpdateDelay(_tmpMTriggerContentUpdateDelay);
                    long j = _tmpMTriggerContentUpdateDelay;
                    long _tmpMTriggerMaxContentDelay = _cursor.getLong(_cursorIndexOfMTriggerMaxContentDelay);
                    _tmpConstraints2.setTriggerMaxContentDelay(_tmpMTriggerMaxContentDelay);
                    long j2 = _tmpMTriggerMaxContentDelay;
                    ContentUriTriggers _tmpMContentUriTriggers = WorkTypeConverters.byteArrayToContentUriTriggers(_cursor.getBlob(_cursorIndexOfMContentUriTriggers));
                    _tmpConstraints2.setContentUriTriggers(_tmpMContentUriTriggers);
                    ContentUriTriggers contentUriTriggers = _tmpMContentUriTriggers;
                    WorkSpec _item = new WorkSpec(_tmpId, _tmpWorkerClassName);
                    String str = _tmpId;
                    _item.state = WorkTypeConverters.intToState(_cursor.getInt(_cursorIndexOfState));
                    _item.inputMergerClassName = _cursor.getString(_cursorIndexOfScheduleRequestedAt);
                    byte[] _tmp_7 = _cursor.getBlob(_cursorIndexOfInput);
                    int _cursorIndexOfInput2 = _cursorIndexOfInput;
                    _item.input = Data.fromByteArray(_tmp_7);
                    int _cursorIndexOfOutput3 = _cursorIndexOfOutput2;
                    int _cursorIndexOfOutput4 = _cursorIndexOfOutput3;
                    _item.output = Data.fromByteArray(_cursor.getBlob(_cursorIndexOfOutput3));
                    byte[] bArr = _tmp_7;
                    int _cursorIndexOfInitialDelay2 = _cursorIndexOfInputMergerClassName;
                    int _cursorIndexOfInputMergerClassName2 = _cursorIndexOfScheduleRequestedAt;
                    _item.initialDelay = _cursor.getLong(_cursorIndexOfInitialDelay2);
                    int _cursorIndexOfMRequiresBatteryNotLow2 = _cursorIndexOfExpedited;
                    int _cursorIndexOfIntervalDuration2 = _cursorIndexOfMRequiresStorageNotLow;
                    int _cursorIndexOfMRequiresStorageNotLow2 = _tmp_10;
                    _item.intervalDuration = _cursor.getLong(_cursorIndexOfIntervalDuration2);
                    int _cursorIndexOfIntervalDuration3 = _cursorIndexOfIntervalDuration2;
                    int _cursorIndexOfFlexDuration2 = _cursorIndexOfMRequiresBatteryNotLow;
                    int _cursorIndexOfMRequiresBatteryNotLow3 = _cursorIndexOfMRequiresBatteryNotLow2;
                    _item.flexDuration = _cursor.getLong(_cursorIndexOfFlexDuration2);
                    int _cursorIndexOfRunAttemptCount2 = _tmp_9;
                    _item.runAttemptCount = _cursor.getInt(_cursorIndexOfRunAttemptCount2);
                    int _cursorIndexOfBackoffPolicy = _cursorIndexOfInitialDelay;
                    int _cursorIndexOfInitialDelay3 = _cursorIndexOfInitialDelay2;
                    _item.backoffPolicy = WorkTypeConverters.intToBackoffPolicy(_cursor.getInt(_cursorIndexOfBackoffPolicy));
                    int _cursorIndexOfBackoffPolicy2 = _cursorIndexOfBackoffPolicy;
                    int _cursorIndexOfBackoffDelayDuration3 = _cursorIndexOfRunAttemptCount;
                    int _cursorIndexOfRunAttemptCount3 = _cursorIndexOfRunAttemptCount2;
                    _item.backoffDelayDuration = _cursor.getLong(_cursorIndexOfBackoffDelayDuration3);
                    int _cursorIndexOfFlexDuration3 = _cursorIndexOfFlexDuration2;
                    int _cursorIndexOfPeriodStartTime2 = _cursorIndexOfIntervalDuration;
                    int _cursorIndexOfIntervalDuration4 = _cursorIndexOfIntervalDuration3;
                    _item.periodStartTime = _cursor.getLong(_cursorIndexOfPeriodStartTime2);
                    int _cursorIndexOfPeriodStartTime3 = _cursorIndexOfPeriodStartTime2;
                    int _cursorIndexOfMinimumRetentionDuration = _cursorIndexOfFlexDuration;
                    int _cursorIndexOfFlexDuration4 = _cursorIndexOfFlexDuration3;
                    _item.minimumRetentionDuration = _cursor.getLong(_cursorIndexOfMinimumRetentionDuration);
                    int _cursorIndexOfMinimumRetentionDuration2 = _cursorIndexOfMinimumRetentionDuration;
                    int _cursorIndexOfScheduleRequestedAt2 = _cursorIndexOfPeriodStartTime;
                    int _cursorIndexOfScheduleRequestedAt3 = _cursorIndexOfPeriodStartTime3;
                    _item.scheduleRequestedAt = _cursor.getLong(_cursorIndexOfScheduleRequestedAt2);
                    int _cursorIndexOfExpedited2 = _cursorIndexOfBackoffDelayDuration2;
                    if (_cursor.getInt(_cursorIndexOfExpedited2) != 0) {
                        _cursorIndexOfBackoffDelayDuration = _cursorIndexOfBackoffDelayDuration3;
                        z = true;
                    } else {
                        _cursorIndexOfBackoffDelayDuration = _cursorIndexOfBackoffDelayDuration3;
                        z = false;
                    }
                    _item.expedited = z;
                    int _cursorIndexOfOutOfQuotaPolicy = _tmp_11;
                    int _cursorIndexOfOutOfQuotaPolicy2 = _cursorIndexOfOutOfQuotaPolicy;
                    _item.outOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(_cursor.getInt(_cursorIndexOfOutOfQuotaPolicy));
                    _item.constraints = _tmpConstraints2;
                    _result.add(_item);
                    _tmp_10 = _cursorIndexOfMRequiresStorageNotLow2;
                    _tmp_9 = _cursorIndexOfRunAttemptCount3;
                    _cursorIndexOfMRequiresStorageNotLow = _cursorIndexOfIntervalDuration4;
                    _cursorIndexOfIntervalDuration = _cursorIndexOfScheduleRequestedAt3;
                    _cursorIndexOfRunAttemptCount = _cursorIndexOfBackoffDelayDuration;
                    _cursorIndexOfId = _cursorIndexOfId2;
                    _cursorIndexOfMRequiredNetworkType = _cursorIndexOfMRequiredNetworkType2;
                    _tmp_11 = _cursorIndexOfOutOfQuotaPolicy2;
                    _cursorIndexOfMRequiresCharging = _cursorIndexOfMRequiresCharging2;
                    _cursorIndexOfMRequiresDeviceIdle = _cursorIndexOfMRequiresDeviceIdle2;
                    _cursorIndexOfInput = _cursorIndexOfInput2;
                    _cursorIndexOfOutput2 = _cursorIndexOfOutput4;
                    _cursorIndexOfPeriodStartTime = _cursorIndexOfScheduleRequestedAt2;
                    _cursorIndexOfBackoffDelayDuration2 = _cursorIndexOfExpedited2;
                    _cursorIndexOfScheduleRequestedAt = _cursorIndexOfInputMergerClassName2;
                    _cursorIndexOfExpedited = _cursorIndexOfMRequiresBatteryNotLow3;
                    _cursorIndexOfInputMergerClassName = _cursorIndexOfInitialDelay3;
                    _cursorIndexOfMRequiresBatteryNotLow = _cursorIndexOfFlexDuration4;
                    _cursorIndexOfInitialDelay = _cursorIndexOfBackoffPolicy2;
                    _cursorIndexOfFlexDuration = _cursorIndexOfMinimumRetentionDuration2;
                }
                _cursor.close();
                _statement.release();
                return _result;
            } catch (Throwable th2) {
                th = th2;
                _cursor.close();
                _statement.release();
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            Object obj2 = "SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE state=0 AND schedule_requested_at<>-1";
            _statement = _statement2;
            _cursor.close();
            _statement.release();
            throw th;
        }
    }

    public List<WorkSpec> getRunningWork() {
        RoomSQLiteQuery _statement;
        int _cursorIndexOfOutput;
        int _cursorIndexOfBackoffDelayDuration;
        boolean z;
        RoomSQLiteQuery _statement2 = RoomSQLiteQuery.acquire("SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE state=1", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement2, false, (CancellationSignal) null);
        try {
            int _cursorIndexOfMRequiredNetworkType = CursorUtil.getColumnIndexOrThrow(_cursor, "required_network_type");
            int _cursorIndexOfMRequiresCharging = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_charging");
            int _cursorIndexOfMRequiresDeviceIdle = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_device_idle");
            int _cursorIndexOfExpedited = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_battery_not_low");
            int _tmp_10 = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_storage_not_low");
            int _cursorIndexOfMTriggerContentUpdateDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "trigger_content_update_delay");
            int _cursorIndexOfMTriggerMaxContentDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "trigger_max_content_delay");
            int _cursorIndexOfMContentUriTriggers = CursorUtil.getColumnIndexOrThrow(_cursor, "content_uri_triggers");
            int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, Constants.ACTION_STATE);
            int _cursorIndexOfWorkerClassName = CursorUtil.getColumnIndexOrThrow(_cursor, "worker_class_name");
            int _cursorIndexOfScheduleRequestedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "input_merger_class_name");
            int _cursorIndexOfInput = CursorUtil.getColumnIndexOrThrow(_cursor, "input");
            Object obj = "SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE state=1";
            try {
                _cursorIndexOfOutput = CursorUtil.getColumnIndexOrThrow(_cursor, "output");
                _statement = _statement2;
            } catch (Throwable th) {
                th = th;
                _statement = _statement2;
                _cursor.close();
                _statement.release();
                throw th;
            }
            try {
                int _cursorIndexOfInputMergerClassName = CursorUtil.getColumnIndexOrThrow(_cursor, "initial_delay");
                int _cursorIndexOfMRequiresStorageNotLow = CursorUtil.getColumnIndexOrThrow(_cursor, "interval_duration");
                int _cursorIndexOfMRequiresBatteryNotLow = CursorUtil.getColumnIndexOrThrow(_cursor, "flex_duration");
                int _tmp_9 = CursorUtil.getColumnIndexOrThrow(_cursor, "run_attempt_count");
                int _cursorIndexOfInitialDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "backoff_policy");
                int _cursorIndexOfRunAttemptCount = CursorUtil.getColumnIndexOrThrow(_cursor, "backoff_delay_duration");
                int _cursorIndexOfIntervalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "period_start_time");
                int _cursorIndexOfFlexDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "minimum_retention_duration");
                int _cursorIndexOfPeriodStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "schedule_requested_at");
                int _cursorIndexOfBackoffDelayDuration2 = CursorUtil.getColumnIndexOrThrow(_cursor, "run_in_foreground");
                int _tmp_11 = CursorUtil.getColumnIndexOrThrow(_cursor, "out_of_quota_policy");
                int _cursorIndexOfOutput2 = _cursorIndexOfOutput;
                List<WorkSpec> _result = new ArrayList<>(_cursor.getCount());
                while (_cursor.moveToNext()) {
                    String _tmpId = _cursor.getString(_cursorIndexOfId);
                    String _tmpWorkerClassName = _cursor.getString(_cursorIndexOfWorkerClassName);
                    Constraints _tmpConstraints = new Constraints();
                    NetworkType _tmpMRequiredNetworkType = WorkTypeConverters.intToNetworkType(_cursor.getInt(_cursorIndexOfMRequiredNetworkType));
                    int _cursorIndexOfMRequiredNetworkType2 = _cursorIndexOfMRequiredNetworkType;
                    Constraints _tmpConstraints2 = _tmpConstraints;
                    int _cursorIndexOfId2 = _cursorIndexOfId;
                    _tmpConstraints2.setRequiredNetworkType(_tmpMRequiredNetworkType);
                    int _cursorIndexOfMRequiresCharging2 = _cursorIndexOfMRequiresCharging;
                    boolean _tmpMRequiresCharging = _cursor.getInt(_cursorIndexOfMRequiresCharging) != 0;
                    _tmpConstraints2.setRequiresCharging(_tmpMRequiresCharging);
                    boolean z2 = _tmpMRequiresCharging;
                    boolean _tmpMRequiresCharging2 = _cursor.getInt(_cursorIndexOfMRequiresDeviceIdle) != 0;
                    _tmpConstraints2.setRequiresDeviceIdle(_tmpMRequiresCharging2);
                    boolean z3 = _tmpMRequiresCharging2;
                    boolean _tmpMRequiresBatteryNotLow = _cursor.getInt(_cursorIndexOfExpedited) != 0;
                    _tmpConstraints2.setRequiresBatteryNotLow(_tmpMRequiresBatteryNotLow);
                    boolean z4 = _tmpMRequiresBatteryNotLow;
                    boolean _tmpMRequiresBatteryNotLow2 = _cursor.getInt(_tmp_10) != 0;
                    _tmpConstraints2.setRequiresStorageNotLow(_tmpMRequiresBatteryNotLow2);
                    boolean z5 = _tmpMRequiresBatteryNotLow2;
                    int _cursorIndexOfMRequiresDeviceIdle2 = _cursorIndexOfMRequiresDeviceIdle;
                    long _tmpMTriggerContentUpdateDelay = _cursor.getLong(_cursorIndexOfMTriggerContentUpdateDelay);
                    _tmpConstraints2.setTriggerContentUpdateDelay(_tmpMTriggerContentUpdateDelay);
                    long j = _tmpMTriggerContentUpdateDelay;
                    long _tmpMTriggerMaxContentDelay = _cursor.getLong(_cursorIndexOfMTriggerMaxContentDelay);
                    _tmpConstraints2.setTriggerMaxContentDelay(_tmpMTriggerMaxContentDelay);
                    long j2 = _tmpMTriggerMaxContentDelay;
                    ContentUriTriggers _tmpMContentUriTriggers = WorkTypeConverters.byteArrayToContentUriTriggers(_cursor.getBlob(_cursorIndexOfMContentUriTriggers));
                    _tmpConstraints2.setContentUriTriggers(_tmpMContentUriTriggers);
                    ContentUriTriggers contentUriTriggers = _tmpMContentUriTriggers;
                    WorkSpec _item = new WorkSpec(_tmpId, _tmpWorkerClassName);
                    String str = _tmpId;
                    _item.state = WorkTypeConverters.intToState(_cursor.getInt(_cursorIndexOfState));
                    _item.inputMergerClassName = _cursor.getString(_cursorIndexOfScheduleRequestedAt);
                    byte[] _tmp_7 = _cursor.getBlob(_cursorIndexOfInput);
                    int _cursorIndexOfInput2 = _cursorIndexOfInput;
                    _item.input = Data.fromByteArray(_tmp_7);
                    int _cursorIndexOfOutput3 = _cursorIndexOfOutput2;
                    int _cursorIndexOfOutput4 = _cursorIndexOfOutput3;
                    _item.output = Data.fromByteArray(_cursor.getBlob(_cursorIndexOfOutput3));
                    byte[] bArr = _tmp_7;
                    int _cursorIndexOfInitialDelay2 = _cursorIndexOfInputMergerClassName;
                    int _cursorIndexOfInputMergerClassName2 = _cursorIndexOfScheduleRequestedAt;
                    _item.initialDelay = _cursor.getLong(_cursorIndexOfInitialDelay2);
                    int _cursorIndexOfMRequiresBatteryNotLow2 = _cursorIndexOfExpedited;
                    int _cursorIndexOfIntervalDuration2 = _cursorIndexOfMRequiresStorageNotLow;
                    int _cursorIndexOfMRequiresStorageNotLow2 = _tmp_10;
                    _item.intervalDuration = _cursor.getLong(_cursorIndexOfIntervalDuration2);
                    int _cursorIndexOfIntervalDuration3 = _cursorIndexOfIntervalDuration2;
                    int _cursorIndexOfFlexDuration2 = _cursorIndexOfMRequiresBatteryNotLow;
                    int _cursorIndexOfMRequiresBatteryNotLow3 = _cursorIndexOfMRequiresBatteryNotLow2;
                    _item.flexDuration = _cursor.getLong(_cursorIndexOfFlexDuration2);
                    int _cursorIndexOfRunAttemptCount2 = _tmp_9;
                    _item.runAttemptCount = _cursor.getInt(_cursorIndexOfRunAttemptCount2);
                    int _cursorIndexOfBackoffPolicy = _cursorIndexOfInitialDelay;
                    int _cursorIndexOfInitialDelay3 = _cursorIndexOfInitialDelay2;
                    _item.backoffPolicy = WorkTypeConverters.intToBackoffPolicy(_cursor.getInt(_cursorIndexOfBackoffPolicy));
                    int _cursorIndexOfBackoffPolicy2 = _cursorIndexOfBackoffPolicy;
                    int _cursorIndexOfBackoffDelayDuration3 = _cursorIndexOfRunAttemptCount;
                    int _cursorIndexOfRunAttemptCount3 = _cursorIndexOfRunAttemptCount2;
                    _item.backoffDelayDuration = _cursor.getLong(_cursorIndexOfBackoffDelayDuration3);
                    int _cursorIndexOfFlexDuration3 = _cursorIndexOfFlexDuration2;
                    int _cursorIndexOfPeriodStartTime2 = _cursorIndexOfIntervalDuration;
                    int _cursorIndexOfIntervalDuration4 = _cursorIndexOfIntervalDuration3;
                    _item.periodStartTime = _cursor.getLong(_cursorIndexOfPeriodStartTime2);
                    int _cursorIndexOfPeriodStartTime3 = _cursorIndexOfPeriodStartTime2;
                    int _cursorIndexOfMinimumRetentionDuration = _cursorIndexOfFlexDuration;
                    int _cursorIndexOfFlexDuration4 = _cursorIndexOfFlexDuration3;
                    _item.minimumRetentionDuration = _cursor.getLong(_cursorIndexOfMinimumRetentionDuration);
                    int _cursorIndexOfMinimumRetentionDuration2 = _cursorIndexOfMinimumRetentionDuration;
                    int _cursorIndexOfScheduleRequestedAt2 = _cursorIndexOfPeriodStartTime;
                    int _cursorIndexOfScheduleRequestedAt3 = _cursorIndexOfPeriodStartTime3;
                    _item.scheduleRequestedAt = _cursor.getLong(_cursorIndexOfScheduleRequestedAt2);
                    int _cursorIndexOfExpedited2 = _cursorIndexOfBackoffDelayDuration2;
                    if (_cursor.getInt(_cursorIndexOfExpedited2) != 0) {
                        _cursorIndexOfBackoffDelayDuration = _cursorIndexOfBackoffDelayDuration3;
                        z = true;
                    } else {
                        _cursorIndexOfBackoffDelayDuration = _cursorIndexOfBackoffDelayDuration3;
                        z = false;
                    }
                    _item.expedited = z;
                    int _cursorIndexOfOutOfQuotaPolicy = _tmp_11;
                    int _cursorIndexOfOutOfQuotaPolicy2 = _cursorIndexOfOutOfQuotaPolicy;
                    _item.outOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(_cursor.getInt(_cursorIndexOfOutOfQuotaPolicy));
                    _item.constraints = _tmpConstraints2;
                    _result.add(_item);
                    _tmp_10 = _cursorIndexOfMRequiresStorageNotLow2;
                    _tmp_9 = _cursorIndexOfRunAttemptCount3;
                    _cursorIndexOfMRequiresStorageNotLow = _cursorIndexOfIntervalDuration4;
                    _cursorIndexOfIntervalDuration = _cursorIndexOfScheduleRequestedAt3;
                    _cursorIndexOfRunAttemptCount = _cursorIndexOfBackoffDelayDuration;
                    _cursorIndexOfId = _cursorIndexOfId2;
                    _cursorIndexOfMRequiredNetworkType = _cursorIndexOfMRequiredNetworkType2;
                    _tmp_11 = _cursorIndexOfOutOfQuotaPolicy2;
                    _cursorIndexOfMRequiresCharging = _cursorIndexOfMRequiresCharging2;
                    _cursorIndexOfMRequiresDeviceIdle = _cursorIndexOfMRequiresDeviceIdle2;
                    _cursorIndexOfInput = _cursorIndexOfInput2;
                    _cursorIndexOfOutput2 = _cursorIndexOfOutput4;
                    _cursorIndexOfPeriodStartTime = _cursorIndexOfScheduleRequestedAt2;
                    _cursorIndexOfBackoffDelayDuration2 = _cursorIndexOfExpedited2;
                    _cursorIndexOfScheduleRequestedAt = _cursorIndexOfInputMergerClassName2;
                    _cursorIndexOfExpedited = _cursorIndexOfMRequiresBatteryNotLow3;
                    _cursorIndexOfInputMergerClassName = _cursorIndexOfInitialDelay3;
                    _cursorIndexOfMRequiresBatteryNotLow = _cursorIndexOfFlexDuration4;
                    _cursorIndexOfInitialDelay = _cursorIndexOfBackoffPolicy2;
                    _cursorIndexOfFlexDuration = _cursorIndexOfMinimumRetentionDuration2;
                }
                _cursor.close();
                _statement.release();
                return _result;
            } catch (Throwable th2) {
                th = th2;
                _cursor.close();
                _statement.release();
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            Object obj2 = "SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE state=1";
            _statement = _statement2;
            _cursor.close();
            _statement.release();
            throw th;
        }
    }

    public List<WorkSpec> getRecentlyCompletedWork(long startingAt) {
        RoomSQLiteQuery _statement;
        int _cursorIndexOfWorkerClassName;
        int _cursorIndexOfExpedited;
        int _tmp_10;
        int _cursorIndexOfOutput;
        int _cursorIndexOfBackoffDelayDuration;
        boolean z;
        RoomSQLiteQuery _statement2 = RoomSQLiteQuery.acquire("SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE period_start_time >= ? AND state IN (2, 3, 5) ORDER BY period_start_time DESC", 1);
        _statement2.bindLong(1, startingAt);
        this.__db.assertNotSuspendingTransaction();
        Cursor _cursor = DBUtil.query(this.__db, _statement2, false, (CancellationSignal) null);
        try {
            int _cursorIndexOfMRequiredNetworkType = CursorUtil.getColumnIndexOrThrow(_cursor, "required_network_type");
            int _cursorIndexOfMRequiresCharging = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_charging");
            int _cursorIndexOfMRequiresDeviceIdle = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_device_idle");
            int _cursorIndexOfMRequiresBatteryNotLow = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_battery_not_low");
            int _cursorIndexOfMRequiresStorageNotLow = CursorUtil.getColumnIndexOrThrow(_cursor, "requires_storage_not_low");
            int _cursorIndexOfMTriggerContentUpdateDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "trigger_content_update_delay");
            int _cursorIndexOfMTriggerMaxContentDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "trigger_max_content_delay");
            int _cursorIndexOfOutput2 = CursorUtil.getColumnIndexOrThrow(_cursor, "content_uri_triggers");
            int _cursorIndexOfPeriodStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, Constants.ACTION_STATE);
            Object obj = "SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE period_start_time >= ? AND state IN (2, 3, 5) ORDER BY period_start_time DESC";
            try {
                _cursorIndexOfWorkerClassName = CursorUtil.getColumnIndexOrThrow(_cursor, "worker_class_name");
            } catch (Throwable th) {
                th = th;
                _statement = _statement2;
                _cursor.close();
                _statement.release();
                throw th;
            }
            try {
                _cursorIndexOfExpedited = CursorUtil.getColumnIndexOrThrow(_cursor, "input_merger_class_name");
                _tmp_10 = CursorUtil.getColumnIndexOrThrow(_cursor, "input");
                _cursorIndexOfOutput = CursorUtil.getColumnIndexOrThrow(_cursor, "output");
                _statement = _statement2;
            } catch (Throwable th2) {
                th = th2;
                _statement = _statement2;
                _cursor.close();
                _statement.release();
                throw th;
            }
            try {
                int _cursorIndexOfInputMergerClassName = CursorUtil.getColumnIndexOrThrow(_cursor, "initial_delay");
                int _cursorIndexOfIntervalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "interval_duration");
                int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "flex_duration");
                int _tmp_9 = CursorUtil.getColumnIndexOrThrow(_cursor, "run_attempt_count");
                int _cursorIndexOfInitialDelay = CursorUtil.getColumnIndexOrThrow(_cursor, "backoff_policy");
                int _cursorIndexOfIntervalDuration2 = CursorUtil.getColumnIndexOrThrow(_cursor, "backoff_delay_duration");
                int _cursorIndexOfRunAttemptCount = CursorUtil.getColumnIndexOrThrow(_cursor, "period_start_time");
                int _cursorIndexOfBackoffPolicy = CursorUtil.getColumnIndexOrThrow(_cursor, "minimum_retention_duration");
                int _cursorIndexOfMinimumRetentionDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "schedule_requested_at");
                int _cursorIndexOfBackoffDelayDuration2 = CursorUtil.getColumnIndexOrThrow(_cursor, "run_in_foreground");
                int _tmp_11 = CursorUtil.getColumnIndexOrThrow(_cursor, "out_of_quota_policy");
                int _cursorIndexOfOutput3 = _cursorIndexOfOutput;
                List<WorkSpec> _result = new ArrayList<>(_cursor.getCount());
                while (_cursor.moveToNext()) {
                    String _tmpId = _cursor.getString(_cursorIndexOfPeriodStartTime);
                    String _tmpWorkerClassName = _cursor.getString(_cursorIndexOfWorkerClassName);
                    Constraints _tmpConstraints = new Constraints();
                    NetworkType _tmpMRequiredNetworkType = WorkTypeConverters.intToNetworkType(_cursor.getInt(_cursorIndexOfMRequiredNetworkType));
                    int _cursorIndexOfMRequiredNetworkType2 = _cursorIndexOfMRequiredNetworkType;
                    Constraints _tmpConstraints2 = _tmpConstraints;
                    int _cursorIndexOfWorkerClassName2 = _cursorIndexOfWorkerClassName;
                    NetworkType _tmpMRequiredNetworkType2 = _tmpMRequiredNetworkType;
                    _tmpConstraints2.setRequiredNetworkType(_tmpMRequiredNetworkType2);
                    NetworkType networkType = _tmpMRequiredNetworkType2;
                    boolean _tmpMRequiresCharging = _cursor.getInt(_cursorIndexOfMRequiresCharging) != 0;
                    _tmpConstraints2.setRequiresCharging(_tmpMRequiresCharging);
                    boolean z2 = _tmpMRequiresCharging;
                    boolean _tmpMRequiresCharging2 = _cursor.getInt(_cursorIndexOfMRequiresDeviceIdle) != 0;
                    _tmpConstraints2.setRequiresDeviceIdle(_tmpMRequiresCharging2);
                    boolean z3 = _tmpMRequiresCharging2;
                    boolean _tmpMRequiresBatteryNotLow = _cursor.getInt(_cursorIndexOfMRequiresBatteryNotLow) != 0;
                    _tmpConstraints2.setRequiresBatteryNotLow(_tmpMRequiresBatteryNotLow);
                    boolean z4 = _tmpMRequiresBatteryNotLow;
                    boolean _tmpMRequiresBatteryNotLow2 = _cursor.getInt(_cursorIndexOfMRequiresStorageNotLow) != 0;
                    _tmpConstraints2.setRequiresStorageNotLow(_tmpMRequiresBatteryNotLow2);
                    int _cursorIndexOfMRequiresCharging2 = _cursorIndexOfMRequiresCharging;
                    int _cursorIndexOfMRequiresDeviceIdle2 = _cursorIndexOfMRequiresDeviceIdle;
                    long _tmpMTriggerContentUpdateDelay = _cursor.getLong(_cursorIndexOfMTriggerContentUpdateDelay);
                    _tmpConstraints2.setTriggerContentUpdateDelay(_tmpMTriggerContentUpdateDelay);
                    long j = _tmpMTriggerContentUpdateDelay;
                    _tmpConstraints2.setTriggerMaxContentDelay(_cursor.getLong(_cursorIndexOfMTriggerMaxContentDelay));
                    boolean z5 = _tmpMRequiresBatteryNotLow2;
                    ContentUriTriggers _tmpMContentUriTriggers = WorkTypeConverters.byteArrayToContentUriTriggers(_cursor.getBlob(_cursorIndexOfOutput2));
                    _tmpConstraints2.setContentUriTriggers(_tmpMContentUriTriggers);
                    ContentUriTriggers contentUriTriggers = _tmpMContentUriTriggers;
                    int _cursorIndexOfMContentUriTriggers = _cursorIndexOfOutput2;
                    String _tmpWorkerClassName2 = _tmpWorkerClassName;
                    WorkSpec _item = new WorkSpec(_tmpId, _tmpWorkerClassName2);
                    int _cursorIndexOfState2 = _cursorIndexOfState;
                    _item.state = WorkTypeConverters.intToState(_cursor.getInt(_cursorIndexOfState));
                    _item.inputMergerClassName = _cursor.getString(_cursorIndexOfExpedited);
                    byte[] _tmp_7 = _cursor.getBlob(_tmp_10);
                    String str = _tmpWorkerClassName2;
                    _item.input = Data.fromByteArray(_tmp_7);
                    int _cursorIndexOfOutput4 = _cursorIndexOfOutput3;
                    byte[] bArr = _tmp_7;
                    _item.output = Data.fromByteArray(_cursor.getBlob(_cursorIndexOfOutput4));
                    int _cursorIndexOfInput = _tmp_10;
                    int _cursorIndexOfInitialDelay2 = _cursorIndexOfInputMergerClassName;
                    int _cursorIndexOfInputMergerClassName2 = _cursorIndexOfExpedited;
                    _item.initialDelay = _cursor.getLong(_cursorIndexOfInitialDelay2);
                    int _cursorIndexOfIntervalDuration3 = _cursorIndexOfIntervalDuration;
                    String str2 = _tmpId;
                    _item.intervalDuration = _cursor.getLong(_cursorIndexOfIntervalDuration3);
                    int _cursorIndexOfFlexDuration = _cursorIndexOfId;
                    int _cursorIndexOfId2 = _cursorIndexOfPeriodStartTime;
                    _item.flexDuration = _cursor.getLong(_cursorIndexOfFlexDuration);
                    int _cursorIndexOfRunAttemptCount2 = _tmp_9;
                    _item.runAttemptCount = _cursor.getInt(_cursorIndexOfRunAttemptCount2);
                    int _cursorIndexOfBackoffPolicy2 = _cursorIndexOfInitialDelay;
                    int _cursorIndexOfInitialDelay3 = _cursorIndexOfInitialDelay2;
                    _item.backoffPolicy = WorkTypeConverters.intToBackoffPolicy(_cursor.getInt(_cursorIndexOfBackoffPolicy2));
                    int _cursorIndexOfFlexDuration2 = _cursorIndexOfFlexDuration;
                    int _cursorIndexOfBackoffDelayDuration3 = _cursorIndexOfIntervalDuration2;
                    int _cursorIndexOfIntervalDuration4 = _cursorIndexOfIntervalDuration3;
                    _item.backoffDelayDuration = _cursor.getLong(_cursorIndexOfBackoffDelayDuration3);
                    int _cursorIndexOfPeriodStartTime2 = _cursorIndexOfRunAttemptCount;
                    int _cursorIndexOfPeriodStartTime3 = _cursorIndexOfRunAttemptCount2;
                    _item.periodStartTime = _cursor.getLong(_cursorIndexOfPeriodStartTime2);
                    int _cursorIndexOfMinimumRetentionDuration2 = _cursorIndexOfBackoffPolicy;
                    int _cursorIndexOfBackoffPolicy3 = _cursorIndexOfBackoffPolicy2;
                    _item.minimumRetentionDuration = _cursor.getLong(_cursorIndexOfMinimumRetentionDuration2);
                    int _cursorIndexOfPeriodStartTime4 = _cursorIndexOfPeriodStartTime2;
                    int _cursorIndexOfScheduleRequestedAt = _cursorIndexOfMinimumRetentionDuration;
                    int _cursorIndexOfScheduleRequestedAt2 = _cursorIndexOfMinimumRetentionDuration2;
                    _item.scheduleRequestedAt = _cursor.getLong(_cursorIndexOfScheduleRequestedAt);
                    int _cursorIndexOfExpedited2 = _cursorIndexOfBackoffDelayDuration2;
                    if (_cursor.getInt(_cursorIndexOfExpedited2) != 0) {
                        _cursorIndexOfBackoffDelayDuration = _cursorIndexOfBackoffDelayDuration3;
                        z = true;
                    } else {
                        _cursorIndexOfBackoffDelayDuration = _cursorIndexOfBackoffDelayDuration3;
                        z = false;
                    }
                    _item.expedited = z;
                    int _cursorIndexOfOutOfQuotaPolicy = _tmp_11;
                    int _cursorIndexOfOutOfQuotaPolicy2 = _cursorIndexOfOutOfQuotaPolicy;
                    _item.outOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(_cursor.getInt(_cursorIndexOfOutOfQuotaPolicy));
                    _item.constraints = _tmpConstraints2;
                    _result.add(_item);
                    _cursorIndexOfOutput3 = _cursorIndexOfOutput4;
                    _cursorIndexOfIntervalDuration = _cursorIndexOfIntervalDuration4;
                    _tmp_9 = _cursorIndexOfPeriodStartTime3;
                    _cursorIndexOfIntervalDuration2 = _cursorIndexOfBackoffDelayDuration;
                    _cursorIndexOfWorkerClassName = _cursorIndexOfWorkerClassName2;
                    _cursorIndexOfMRequiredNetworkType = _cursorIndexOfMRequiredNetworkType2;
                    _cursorIndexOfMRequiresCharging = _cursorIndexOfMRequiresCharging2;
                    _cursorIndexOfMRequiresDeviceIdle = _cursorIndexOfMRequiresDeviceIdle2;
                    _cursorIndexOfOutput2 = _cursorIndexOfMContentUriTriggers;
                    _cursorIndexOfState = _cursorIndexOfState2;
                    _tmp_10 = _cursorIndexOfInput;
                    _tmp_11 = _cursorIndexOfOutOfQuotaPolicy2;
                    _cursorIndexOfBackoffDelayDuration2 = _cursorIndexOfExpedited2;
                    _cursorIndexOfRunAttemptCount = _cursorIndexOfPeriodStartTime4;
                    _cursorIndexOfExpedited = _cursorIndexOfInputMergerClassName2;
                    _cursorIndexOfPeriodStartTime = _cursorIndexOfId2;
                    _cursorIndexOfInputMergerClassName = _cursorIndexOfInitialDelay3;
                    _cursorIndexOfInitialDelay = _cursorIndexOfBackoffPolicy3;
                    _cursorIndexOfBackoffPolicy = _cursorIndexOfScheduleRequestedAt2;
                    _cursorIndexOfId = _cursorIndexOfFlexDuration2;
                    _cursorIndexOfMinimumRetentionDuration = _cursorIndexOfScheduleRequestedAt;
                }
                _cursor.close();
                _statement.release();
                return _result;
            } catch (Throwable th3) {
                th = th3;
                _cursor.close();
                _statement.release();
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            Object obj2 = "SELECT `required_network_type`, `requires_charging`, `requires_device_idle`, `requires_battery_not_low`, `requires_storage_not_low`, `trigger_content_update_delay`, `trigger_max_content_delay`, `content_uri_triggers`, `WorkSpec`.`id` AS `id`, `WorkSpec`.`state` AS `state`, `WorkSpec`.`worker_class_name` AS `worker_class_name`, `WorkSpec`.`input_merger_class_name` AS `input_merger_class_name`, `WorkSpec`.`input` AS `input`, `WorkSpec`.`output` AS `output`, `WorkSpec`.`initial_delay` AS `initial_delay`, `WorkSpec`.`interval_duration` AS `interval_duration`, `WorkSpec`.`flex_duration` AS `flex_duration`, `WorkSpec`.`run_attempt_count` AS `run_attempt_count`, `WorkSpec`.`backoff_policy` AS `backoff_policy`, `WorkSpec`.`backoff_delay_duration` AS `backoff_delay_duration`, `WorkSpec`.`period_start_time` AS `period_start_time`, `WorkSpec`.`minimum_retention_duration` AS `minimum_retention_duration`, `WorkSpec`.`schedule_requested_at` AS `schedule_requested_at`, `WorkSpec`.`run_in_foreground` AS `run_in_foreground`, `WorkSpec`.`out_of_quota_policy` AS `out_of_quota_policy` FROM workspec WHERE period_start_time >= ? AND state IN (2, 3, 5) ORDER BY period_start_time DESC";
            _statement = _statement2;
            _cursor.close();
            _statement.release();
            throw th;
        }
    }

    public int setState(WorkInfo.State state, String... ids) {
        this.__db.assertNotSuspendingTransaction();
        StringBuilder _stringBuilder = StringUtil.newStringBuilder();
        _stringBuilder.append("UPDATE workspec SET state=");
        _stringBuilder.append("?");
        _stringBuilder.append(" WHERE id IN (");
        StringUtil.appendPlaceholders(_stringBuilder, ids.length);
        _stringBuilder.append(")");
        SupportSQLiteStatement _stmt = this.__db.compileStatement(_stringBuilder.toString());
        _stmt.bindLong(1, (long) WorkTypeConverters.stateToInt(state));
        int _argIndex = 2;
        for (String _item : ids) {
            if (_item == null) {
                _stmt.bindNull(_argIndex);
            } else {
                _stmt.bindString(_argIndex, _item);
            }
            _argIndex++;
        }
        this.__db.beginTransaction();
        try {
            int _result = _stmt.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
            return _result;
        } finally {
            this.__db.endTransaction();
        }
    }

    /* access modifiers changed from: private */
    public void __fetchRelationshipWorkTagAsjavaLangString(ArrayMap<String, ArrayList<String>> _map) {
        ArrayList<String> _tmpRelation;
        Set<String> __mapKeySet = _map.keySet();
        if (!__mapKeySet.isEmpty()) {
            if (_map.size() > 999) {
                ArrayMap<String, ArrayList<String>> _tmpInnerMap = new ArrayMap<>(999);
                int _tmpIndex = 0;
                int _mapIndex = 0;
                int _limit = _map.size();
                while (_mapIndex < _limit) {
                    _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
                    _mapIndex++;
                    _tmpIndex++;
                    if (_tmpIndex == 999) {
                        __fetchRelationshipWorkTagAsjavaLangString(_tmpInnerMap);
                        _tmpInnerMap = new ArrayMap<>(999);
                        _tmpIndex = 0;
                    }
                }
                if (_tmpIndex > 0) {
                    __fetchRelationshipWorkTagAsjavaLangString(_tmpInnerMap);
                    return;
                }
                return;
            }
            StringBuilder _stringBuilder = StringUtil.newStringBuilder();
            _stringBuilder.append("SELECT `tag`,`work_spec_id` FROM `WorkTag` WHERE `work_spec_id` IN (");
            int _inputSize = __mapKeySet.size();
            StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
            _stringBuilder.append(")");
            RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_stringBuilder.toString(), _inputSize + 0);
            int _argIndex = 1;
            for (String _item : __mapKeySet) {
                if (_item == null) {
                    _stmt.bindNull(_argIndex);
                } else {
                    _stmt.bindString(_argIndex, _item);
                }
                _argIndex++;
            }
            Cursor _cursor = DBUtil.query(this.__db, _stmt, false, (CancellationSignal) null);
            try {
                int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "work_spec_id");
                if (_itemKeyIndex != -1) {
                    while (_cursor.moveToNext()) {
                        if (!_cursor.isNull(_itemKeyIndex) && (_tmpRelation = _map.get(_cursor.getString(_itemKeyIndex))) != null) {
                            _tmpRelation.add(_cursor.getString(0));
                        }
                    }
                    _cursor.close();
                }
            } finally {
                _cursor.close();
            }
        }
    }

    /* access modifiers changed from: private */
    public void __fetchRelationshipWorkProgressAsandroidxWorkData(ArrayMap<String, ArrayList<Data>> _map) {
        ArrayList<Data> _tmpRelation;
        Set<String> __mapKeySet = _map.keySet();
        if (!__mapKeySet.isEmpty()) {
            if (_map.size() > 999) {
                ArrayMap<String, ArrayList<Data>> _tmpInnerMap = new ArrayMap<>(999);
                int _tmpIndex = 0;
                int _mapIndex = 0;
                int _limit = _map.size();
                while (_mapIndex < _limit) {
                    _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
                    _mapIndex++;
                    _tmpIndex++;
                    if (_tmpIndex == 999) {
                        __fetchRelationshipWorkProgressAsandroidxWorkData(_tmpInnerMap);
                        _tmpInnerMap = new ArrayMap<>(999);
                        _tmpIndex = 0;
                    }
                }
                if (_tmpIndex > 0) {
                    __fetchRelationshipWorkProgressAsandroidxWorkData(_tmpInnerMap);
                    return;
                }
                return;
            }
            StringBuilder _stringBuilder = StringUtil.newStringBuilder();
            _stringBuilder.append("SELECT `progress`,`work_spec_id` FROM `WorkProgress` WHERE `work_spec_id` IN (");
            int _inputSize = __mapKeySet.size();
            StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
            _stringBuilder.append(")");
            RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_stringBuilder.toString(), _inputSize + 0);
            int _argIndex = 1;
            for (String _item : __mapKeySet) {
                if (_item == null) {
                    _stmt.bindNull(_argIndex);
                } else {
                    _stmt.bindString(_argIndex, _item);
                }
                _argIndex++;
            }
            Cursor _cursor = DBUtil.query(this.__db, _stmt, false, (CancellationSignal) null);
            try {
                int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "work_spec_id");
                if (_itemKeyIndex != -1) {
                    while (_cursor.moveToNext()) {
                        if (!_cursor.isNull(_itemKeyIndex) && (_tmpRelation = _map.get(_cursor.getString(_itemKeyIndex))) != null) {
                            _tmpRelation.add(Data.fromByteArray(_cursor.getBlob(0)));
                        }
                    }
                    _cursor.close();
                }
            } finally {
                _cursor.close();
            }
        }
    }
}
