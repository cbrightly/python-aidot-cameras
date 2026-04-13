package androidx.work.impl.utils;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.Logger;
import androidx.work.Operation;
import androidx.work.impl.OperationImpl;
import androidx.work.impl.Scheduler;
import androidx.work.impl.Schedulers;
import androidx.work.impl.WorkContinuationImpl;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.background.systemalarm.RescheduleReceiver;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.workers.ConstraintTrackingWorker;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class EnqueueRunnable implements Runnable {
    private static final String TAG = Logger.tagWithPrefix("EnqueueRunnable");
    private final OperationImpl mOperation = new OperationImpl();
    private final WorkContinuationImpl mWorkContinuation;

    public EnqueueRunnable(@NonNull WorkContinuationImpl workContinuation) {
        this.mWorkContinuation = workContinuation;
    }

    public void run() {
        try {
            if (!this.mWorkContinuation.hasCycles()) {
                if (addToDatabase()) {
                    PackageManagerHelper.setComponentEnabled(this.mWorkContinuation.getWorkManagerImpl().getApplicationContext(), RescheduleReceiver.class, true);
                    scheduleWorkInBackground();
                }
                this.mOperation.setState(Operation.SUCCESS);
                return;
            }
            throw new IllegalStateException(String.format("WorkContinuation has cycles (%s)", new Object[]{this.mWorkContinuation}));
        } catch (Throwable exception) {
            this.mOperation.setState(new Operation.State.FAILURE(exception));
        }
    }

    @NonNull
    public Operation getOperation() {
        return this.mOperation;
    }

    @VisibleForTesting
    public boolean addToDatabase() {
        WorkDatabase workDatabase = this.mWorkContinuation.getWorkManagerImpl().getWorkDatabase();
        workDatabase.beginTransaction();
        try {
            boolean needsScheduling = processContinuation(this.mWorkContinuation);
            workDatabase.setTransactionSuccessful();
            return needsScheduling;
        } finally {
            workDatabase.endTransaction();
        }
    }

    @VisibleForTesting
    public void scheduleWorkInBackground() {
        WorkManagerImpl workManager = this.mWorkContinuation.getWorkManagerImpl();
        Schedulers.schedule(workManager.getConfiguration(), workManager.getWorkDatabase(), workManager.getSchedulers());
    }

    private static boolean processContinuation(@NonNull WorkContinuationImpl workContinuation) {
        boolean needsScheduling = false;
        List<WorkContinuationImpl> parents = workContinuation.getParents();
        if (parents != null) {
            for (WorkContinuationImpl parent : parents) {
                if (!parent.isEnqueued()) {
                    needsScheduling |= processContinuation(parent);
                } else {
                    Logger.get().warning(TAG, String.format("Already enqueued work ids (%s).", new Object[]{TextUtils.join(", ", parent.getIds())}), new Throwable[0]);
                }
            }
        }
        return needsScheduling | enqueueContinuation(workContinuation);
    }

    private static boolean enqueueContinuation(@NonNull WorkContinuationImpl workContinuation) {
        boolean needsScheduling = enqueueWorkWithPrerequisites(workContinuation.getWorkManagerImpl(), workContinuation.getWork(), (String[]) WorkContinuationImpl.prerequisitesFor(workContinuation).toArray(new String[0]), workContinuation.getName(), workContinuation.getExistingWorkPolicy());
        workContinuation.markEnqueued();
        return needsScheduling;
    }

    /* JADX WARNING: type inference failed for: r3v10, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x01b1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean enqueueWorkWithPrerequisites(androidx.work.impl.WorkManagerImpl r22, @androidx.annotation.NonNull java.util.List<? extends androidx.work.WorkRequest> r23, java.lang.String[] r24, java.lang.String r25, androidx.work.ExistingWorkPolicy r26) {
        /*
            r0 = r22
            r1 = r24
            r2 = r25
            r3 = r26
            r4 = 0
            long r5 = java.lang.System.currentTimeMillis()
            androidx.work.impl.WorkDatabase r7 = r22.getWorkDatabase()
            if (r1 == 0) goto L_0x0018
            int r10 = r1.length
            if (r10 <= 0) goto L_0x0018
            r10 = 1
            goto L_0x0019
        L_0x0018:
            r10 = 0
        L_0x0019:
            r11 = 1
            r12 = 0
            r13 = 0
            if (r10 == 0) goto L_0x0078
            int r14 = r1.length
            r15 = 0
        L_0x0020:
            if (r15 >= r14) goto L_0x0073
            r9 = r1[r15]
            androidx.work.impl.model.WorkSpecDao r8 = r7.workSpecDao()
            androidx.work.impl.model.WorkSpec r8 = r8.getWorkSpec(r9)
            if (r8 != 0) goto L_0x004c
            androidx.work.Logger r14 = androidx.work.Logger.get()
            java.lang.String r15 = TAG
            r18 = r4
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r19 = r12
            r12 = 0
            r4[r12] = r9
            r20 = r9
            java.lang.String r9 = "Prerequisite %s doesn't exist; not enqueuing"
            java.lang.String r4 = java.lang.String.format(r9, r4)
            java.lang.Throwable[] r9 = new java.lang.Throwable[r12]
            r14.error(r15, r4, r9)
            return r12
        L_0x004c:
            r18 = r4
            r20 = r9
            r19 = r12
            androidx.work.WorkInfo$State r4 = r8.state
            androidx.work.WorkInfo$State r9 = androidx.work.WorkInfo.State.SUCCEEDED
            if (r4 != r9) goto L_0x005a
            r9 = 1
            goto L_0x005b
        L_0x005a:
            r9 = 0
        L_0x005b:
            r11 = r11 & r9
            androidx.work.WorkInfo$State r9 = androidx.work.WorkInfo.State.FAILED
            if (r4 != r9) goto L_0x0063
            r9 = 1
            r12 = r9
            goto L_0x006e
        L_0x0063:
            androidx.work.WorkInfo$State r9 = androidx.work.WorkInfo.State.CANCELLED
            if (r4 != r9) goto L_0x006c
            r9 = 1
            r13 = r9
            r12 = r19
            goto L_0x006e
        L_0x006c:
            r12 = r19
        L_0x006e:
            int r15 = r15 + 1
            r4 = r18
            goto L_0x0020
        L_0x0073:
            r18 = r4
            r19 = r12
            goto L_0x007a
        L_0x0078:
            r18 = r4
        L_0x007a:
            boolean r4 = android.text.TextUtils.isEmpty(r25)
            r8 = 1
            r4 = r4 ^ r8
            if (r4 == 0) goto L_0x0086
            if (r10 != 0) goto L_0x0086
            r9 = r8
            goto L_0x0087
        L_0x0086:
            r9 = 0
        L_0x0087:
            if (r9 == 0) goto L_0x01a1
            androidx.work.impl.model.WorkSpecDao r14 = r7.workSpecDao()
            java.util.List r14 = r14.getWorkSpecIdAndStatesForName(r2)
            boolean r15 = r14.isEmpty()
            if (r15 != 0) goto L_0x019c
            androidx.work.ExistingWorkPolicy r15 = androidx.work.ExistingWorkPolicy.APPEND
            if (r3 == r15) goto L_0x0103
            androidx.work.ExistingWorkPolicy r15 = androidx.work.ExistingWorkPolicy.APPEND_OR_REPLACE
            if (r3 != r15) goto L_0x00a3
            r19 = r9
            goto L_0x0105
        L_0x00a3:
            androidx.work.ExistingWorkPolicy r8 = androidx.work.ExistingWorkPolicy.KEEP
            if (r3 != r8) goto L_0x00d1
            java.util.Iterator r8 = r14.iterator()
        L_0x00ab:
            boolean r15 = r8.hasNext()
            if (r15 == 0) goto L_0x00cd
            java.lang.Object r15 = r8.next()
            androidx.work.impl.model.WorkSpec$IdAndState r15 = (androidx.work.impl.model.WorkSpec.IdAndState) r15
            r17 = r8
            androidx.work.WorkInfo$State r8 = r15.state
            r19 = r9
            androidx.work.WorkInfo$State r9 = androidx.work.WorkInfo.State.ENQUEUED
            if (r8 == r9) goto L_0x00cb
            androidx.work.WorkInfo$State r9 = androidx.work.WorkInfo.State.RUNNING
            if (r8 != r9) goto L_0x00c6
            goto L_0x00cb
        L_0x00c6:
            r8 = r17
            r9 = r19
            goto L_0x00ab
        L_0x00cb:
            r9 = 0
            return r9
        L_0x00cd:
            r19 = r9
            r9 = 0
            goto L_0x00d4
        L_0x00d1:
            r19 = r9
            r9 = 0
        L_0x00d4:
            androidx.work.impl.utils.CancelWorkRunnable r8 = androidx.work.impl.utils.CancelWorkRunnable.forName(r2, r0, r9)
            r8.run()
            r8 = 1
            androidx.work.impl.model.WorkSpecDao r15 = r7.workSpecDao()
            java.util.Iterator r16 = r14.iterator()
        L_0x00e4:
            boolean r17 = r16.hasNext()
            if (r17 == 0) goto L_0x00fd
            java.lang.Object r17 = r16.next()
            r9 = r17
            androidx.work.impl.model.WorkSpec$IdAndState r9 = (androidx.work.impl.model.WorkSpec.IdAndState) r9
            r17 = r8
            java.lang.String r8 = r9.id
            r15.delete(r8)
            r8 = r17
            r9 = 0
            goto L_0x00e4
        L_0x00fd:
            r17 = r8
            r18 = r17
            goto L_0x01a7
        L_0x0103:
            r19 = r9
        L_0x0105:
            androidx.work.impl.model.DependencyDao r9 = r7.dependencyDao()
            java.util.ArrayList r15 = new java.util.ArrayList
            r15.<init>()
            java.util.Iterator r16 = r14.iterator()
        L_0x0112:
            boolean r17 = r16.hasNext()
            if (r17 == 0) goto L_0x0150
            java.lang.Object r17 = r16.next()
            r8 = r17
            androidx.work.impl.model.WorkSpec$IdAndState r8 = (androidx.work.impl.model.WorkSpec.IdAndState) r8
            r17 = r10
            java.lang.String r10 = r8.id
            boolean r10 = r9.hasDependents(r10)
            if (r10 != 0) goto L_0x0148
            androidx.work.WorkInfo$State r10 = r8.state
            r21 = r9
            androidx.work.WorkInfo$State r9 = androidx.work.WorkInfo.State.SUCCEEDED
            if (r10 != r9) goto L_0x0134
            r9 = 1
            goto L_0x0135
        L_0x0134:
            r9 = 0
        L_0x0135:
            r9 = r9 & r11
            androidx.work.WorkInfo$State r11 = androidx.work.WorkInfo.State.FAILED
            if (r10 != r11) goto L_0x013c
            r12 = 1
            goto L_0x0141
        L_0x013c:
            androidx.work.WorkInfo$State r11 = androidx.work.WorkInfo.State.CANCELLED
            if (r10 != r11) goto L_0x0141
            r13 = 1
        L_0x0141:
            java.lang.String r10 = r8.id
            r15.add(r10)
            r11 = r9
            goto L_0x014a
        L_0x0148:
            r21 = r9
        L_0x014a:
            r10 = r17
            r9 = r21
            r8 = 1
            goto L_0x0112
        L_0x0150:
            r21 = r9
            r17 = r10
            androidx.work.ExistingWorkPolicy r8 = androidx.work.ExistingWorkPolicy.APPEND_OR_REPLACE
            if (r3 != r8) goto L_0x018d
            if (r13 != 0) goto L_0x015c
            if (r12 == 0) goto L_0x018d
        L_0x015c:
            androidx.work.impl.model.WorkSpecDao r8 = r7.workSpecDao()
            java.util.List r9 = r8.getWorkSpecIdAndStatesForName(r2)
            java.util.Iterator r10 = r9.iterator()
        L_0x0169:
            boolean r16 = r10.hasNext()
            if (r16 == 0) goto L_0x0183
            java.lang.Object r16 = r10.next()
            r3 = r16
            androidx.work.impl.model.WorkSpec$IdAndState r3 = (androidx.work.impl.model.WorkSpec.IdAndState) r3
            r16 = r9
            java.lang.String r9 = r3.id
            r8.delete(r9)
            r3 = r26
            r9 = r16
            goto L_0x0169
        L_0x0183:
            r16 = r9
            java.util.List r15 = java.util.Collections.emptyList()
            r3 = 0
            r9 = 0
            r13 = r3
            r12 = r9
        L_0x018d:
            java.lang.Object[] r3 = r15.toArray(r1)
            r1 = r3
            java.lang.String[] r1 = (java.lang.String[]) r1
            int r3 = r1.length
            if (r3 <= 0) goto L_0x0199
            r8 = 1
            goto L_0x019a
        L_0x0199:
            r8 = 0
        L_0x019a:
            r10 = r8
            goto L_0x01a7
        L_0x019c:
            r19 = r9
            r17 = r10
            goto L_0x01a5
        L_0x01a1:
            r19 = r9
            r17 = r10
        L_0x01a5:
            r10 = r17
        L_0x01a7:
            java.util.Iterator r3 = r23.iterator()
        L_0x01ab:
            boolean r8 = r3.hasNext()
            if (r8 == 0) goto L_0x0279
            java.lang.Object r8 = r3.next()
            androidx.work.WorkRequest r8 = (androidx.work.WorkRequest) r8
            androidx.work.impl.model.WorkSpec r9 = r8.getWorkSpec()
            if (r10 == 0) goto L_0x01d2
            if (r11 != 0) goto L_0x01d2
            if (r12 == 0) goto L_0x01c6
            androidx.work.WorkInfo$State r14 = androidx.work.WorkInfo.State.FAILED
            r9.state = r14
            goto L_0x01df
        L_0x01c6:
            if (r13 == 0) goto L_0x01cd
            androidx.work.WorkInfo$State r14 = androidx.work.WorkInfo.State.CANCELLED
            r9.state = r14
            goto L_0x01df
        L_0x01cd:
            androidx.work.WorkInfo$State r14 = androidx.work.WorkInfo.State.BLOCKED
            r9.state = r14
            goto L_0x01df
        L_0x01d2:
            boolean r14 = r9.isPeriodic()
            if (r14 != 0) goto L_0x01db
            r9.periodStartTime = r5
            goto L_0x01df
        L_0x01db:
            r14 = 0
            r9.periodStartTime = r14
        L_0x01df:
            int r14 = android.os.Build.VERSION.SDK_INT
            r15 = 23
            if (r14 < r15) goto L_0x01ed
            r15 = 25
            if (r14 > r15) goto L_0x01ed
            tryDelegateConstrainedWorkSpec(r9)
            goto L_0x01fc
        L_0x01ed:
            r15 = 22
            if (r14 > r15) goto L_0x01fc
            java.lang.String r14 = "androidx.work.impl.background.gcm.GcmScheduler"
            boolean r14 = usesScheduler(r0, r14)
            if (r14 == 0) goto L_0x01fc
            tryDelegateConstrainedWorkSpec(r9)
        L_0x01fc:
            androidx.work.WorkInfo$State r14 = r9.state
            androidx.work.WorkInfo$State r15 = androidx.work.WorkInfo.State.ENQUEUED
            if (r14 != r15) goto L_0x0205
            r14 = 1
            r18 = r14
        L_0x0205:
            androidx.work.impl.model.WorkSpecDao r14 = r7.workSpecDao()
            r14.insertWorkSpec(r9)
            if (r10 == 0) goto L_0x0236
            int r14 = r1.length
            r15 = 0
        L_0x0210:
            if (r15 >= r14) goto L_0x0231
            r0 = r1[r15]
            r16 = r1
            androidx.work.impl.model.Dependency r1 = new androidx.work.impl.model.Dependency
            r24 = r3
            java.lang.String r3 = r8.getStringId()
            r1.<init>(r3, r0)
            androidx.work.impl.model.DependencyDao r3 = r7.dependencyDao()
            r3.insertDependency(r1)
            int r15 = r15 + 1
            r0 = r22
            r3 = r24
            r1 = r16
            goto L_0x0210
        L_0x0231:
            r16 = r1
            r24 = r3
            goto L_0x023a
        L_0x0236:
            r16 = r1
            r24 = r3
        L_0x023a:
            java.util.Set r0 = r8.getTags()
            java.util.Iterator r0 = r0.iterator()
        L_0x0242:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x025f
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            androidx.work.impl.model.WorkTagDao r3 = r7.workTagDao()
            androidx.work.impl.model.WorkTag r14 = new androidx.work.impl.model.WorkTag
            java.lang.String r15 = r8.getStringId()
            r14.<init>(r1, r15)
            r3.insert(r14)
            goto L_0x0242
        L_0x025f:
            if (r4 == 0) goto L_0x0271
            androidx.work.impl.model.WorkNameDao r0 = r7.workNameDao()
            androidx.work.impl.model.WorkName r1 = new androidx.work.impl.model.WorkName
            java.lang.String r3 = r8.getStringId()
            r1.<init>(r2, r3)
            r0.insert(r1)
        L_0x0271:
            r0 = r22
            r3 = r24
            r1 = r16
            goto L_0x01ab
        L_0x0279:
            return r18
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.work.impl.utils.EnqueueRunnable.enqueueWorkWithPrerequisites(androidx.work.impl.WorkManagerImpl, java.util.List, java.lang.String[], java.lang.String, androidx.work.ExistingWorkPolicy):boolean");
    }

    private static void tryDelegateConstrainedWorkSpec(WorkSpec workSpec) {
        Class<ConstraintTrackingWorker> cls = ConstraintTrackingWorker.class;
        Constraints constraints = workSpec.constraints;
        String workerClassName = workSpec.workerClassName;
        if (workerClassName.equals(cls.getName())) {
            return;
        }
        if (constraints.requiresBatteryNotLow() || constraints.requiresStorageNotLow()) {
            Data.Builder builder = new Data.Builder();
            builder.putAll(workSpec.input).putString(ConstraintTrackingWorker.ARGUMENT_CLASS_NAME, workerClassName);
            workSpec.workerClassName = cls.getName();
            workSpec.input = builder.build();
        }
    }

    private static boolean usesScheduler(@NonNull WorkManagerImpl workManager, @NonNull String className) {
        try {
            Class<?> klass = Class.forName(className);
            for (Scheduler scheduler : workManager.getSchedulers()) {
                if (klass.isAssignableFrom(scheduler.getClass())) {
                    return true;
                }
            }
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
