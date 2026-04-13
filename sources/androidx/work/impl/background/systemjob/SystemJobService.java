package androidx.work.impl.background.systemjob;

import android.app.Application;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.PersistableBundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.work.Logger;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.WorkManagerImpl;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(23)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class SystemJobService extends JobService implements ExecutionListener {
    private static final String TAG = Logger.tagWithPrefix("SystemJobService");
    private final Map<String, JobParameters> mJobParameters = new HashMap();
    private WorkManagerImpl mWorkManagerImpl;

    public void onCreate() {
        super.onCreate();
        try {
            WorkManagerImpl instance = WorkManagerImpl.getInstance(getApplicationContext());
            this.mWorkManagerImpl = instance;
            instance.getProcessor().addExecutionListener(this);
        } catch (IllegalStateException e) {
            if (Application.class.equals(getApplication().getClass())) {
                Logger.get().warning(TAG, "Could not find WorkManager instance; this may be because an auto-backup is in progress. Ignoring JobScheduler commands for now. Please make sure that you are initializing WorkManager if you have manually disabled WorkManagerInitializer.", new Throwable[0]);
                return;
            }
            throw new IllegalStateException("WorkManager needs to be initialized via a ContentProvider#onCreate() or an Application#onCreate().");
        }
    }

    public void onDestroy() {
        super.onDestroy();
        WorkManagerImpl workManagerImpl = this.mWorkManagerImpl;
        if (workManagerImpl != null) {
            workManagerImpl.getProcessor().removeExecutionListener(this);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x006c, code lost:
        r2 = null;
        r3 = android.os.Build.VERSION.SDK_INT;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0071, code lost:
        if (r3 < 24) goto L_0x00a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0073, code lost:
        r2 = new androidx.work.WorkerParameters.RuntimeExtras();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x007d, code lost:
        if (r9.getTriggeredContentUris() == null) goto L_0x008a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x007f, code lost:
        r2.triggeredContentUris = java.util.Arrays.asList(r9.getTriggeredContentUris());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x008e, code lost:
        if (r9.getTriggeredContentAuthorities() == null) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0090, code lost:
        r2.triggeredContentAuthorities = java.util.Arrays.asList(r9.getTriggeredContentAuthorities());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x009d, code lost:
        if (r3 < 28) goto L_0x00a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x009f, code lost:
        r2.network = r9.getNetwork();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a5, code lost:
        r8.mWorkManagerImpl.startWork(r0, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00aa, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onStartJob(@androidx.annotation.NonNull android.app.job.JobParameters r9) {
        /*
            r8 = this;
            androidx.work.impl.WorkManagerImpl r0 = r8.mWorkManagerImpl
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x0017
            androidx.work.Logger r0 = androidx.work.Logger.get()
            java.lang.String r3 = TAG
            java.lang.String r4 = "WorkManager is not initialized; requesting retry."
            java.lang.Throwable[] r5 = new java.lang.Throwable[r2]
            r0.debug(r3, r4, r5)
            r8.jobFinished(r9, r1)
            return r2
        L_0x0017:
            java.lang.String r0 = getWorkSpecIdFromJobParameters(r9)
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            if (r3 == 0) goto L_0x002f
            androidx.work.Logger r1 = androidx.work.Logger.get()
            java.lang.String r3 = TAG
            java.lang.String r4 = "WorkSpec id not found!"
            java.lang.Throwable[] r5 = new java.lang.Throwable[r2]
            r1.error(r3, r4, r5)
            return r2
        L_0x002f:
            java.util.Map<java.lang.String, android.app.job.JobParameters> r3 = r8.mJobParameters
            monitor-enter(r3)
            java.util.Map<java.lang.String, android.app.job.JobParameters> r4 = r8.mJobParameters     // Catch:{ all -> 0x00ab }
            boolean r4 = r4.containsKey(r0)     // Catch:{ all -> 0x00ab }
            if (r4 == 0) goto L_0x0051
            androidx.work.Logger r4 = androidx.work.Logger.get()     // Catch:{ all -> 0x00ab }
            java.lang.String r5 = TAG     // Catch:{ all -> 0x00ab }
            java.lang.String r6 = "Job is already being executed by SystemJobService: %s"
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x00ab }
            r1[r2] = r0     // Catch:{ all -> 0x00ab }
            java.lang.String r1 = java.lang.String.format(r6, r1)     // Catch:{ all -> 0x00ab }
            java.lang.Throwable[] r6 = new java.lang.Throwable[r2]     // Catch:{ all -> 0x00ab }
            r4.debug(r5, r1, r6)     // Catch:{ all -> 0x00ab }
            monitor-exit(r3)     // Catch:{ all -> 0x00ab }
            return r2
        L_0x0051:
            androidx.work.Logger r4 = androidx.work.Logger.get()     // Catch:{ all -> 0x00ab }
            java.lang.String r5 = TAG     // Catch:{ all -> 0x00ab }
            java.lang.String r6 = "onStartJob for %s"
            java.lang.Object[] r7 = new java.lang.Object[r1]     // Catch:{ all -> 0x00ab }
            r7[r2] = r0     // Catch:{ all -> 0x00ab }
            java.lang.String r6 = java.lang.String.format(r6, r7)     // Catch:{ all -> 0x00ab }
            java.lang.Throwable[] r2 = new java.lang.Throwable[r2]     // Catch:{ all -> 0x00ab }
            r4.debug(r5, r6, r2)     // Catch:{ all -> 0x00ab }
            java.util.Map<java.lang.String, android.app.job.JobParameters> r2 = r8.mJobParameters     // Catch:{ all -> 0x00ab }
            r2.put(r0, r9)     // Catch:{ all -> 0x00ab }
            monitor-exit(r3)     // Catch:{ all -> 0x00ab }
            r2 = 0
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 24
            if (r3 < r4) goto L_0x00a5
            androidx.work.WorkerParameters$RuntimeExtras r4 = new androidx.work.WorkerParameters$RuntimeExtras
            r4.<init>()
            r2 = r4
            android.net.Uri[] r4 = r9.getTriggeredContentUris()
            if (r4 == 0) goto L_0x008a
            android.net.Uri[] r4 = r9.getTriggeredContentUris()
            java.util.List r4 = java.util.Arrays.asList(r4)
            r2.triggeredContentUris = r4
        L_0x008a:
            java.lang.String[] r4 = r9.getTriggeredContentAuthorities()
            if (r4 == 0) goto L_0x009b
            java.lang.String[] r4 = r9.getTriggeredContentAuthorities()
            java.util.List r4 = java.util.Arrays.asList(r4)
            r2.triggeredContentAuthorities = r4
        L_0x009b:
            r4 = 28
            if (r3 < r4) goto L_0x00a5
            android.net.Network r3 = r9.getNetwork()
            r2.network = r3
        L_0x00a5:
            androidx.work.impl.WorkManagerImpl r3 = r8.mWorkManagerImpl
            r3.startWork(r0, r2)
            return r1
        L_0x00ab:
            r1 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x00ab }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.work.impl.background.systemjob.SystemJobService.onStartJob(android.app.job.JobParameters):boolean");
    }

    public boolean onStopJob(@NonNull JobParameters params) {
        if (this.mWorkManagerImpl == null) {
            Logger.get().debug(TAG, "WorkManager is not initialized; requesting retry.", new Throwable[0]);
            return true;
        }
        String workSpecId = getWorkSpecIdFromJobParameters(params);
        if (TextUtils.isEmpty(workSpecId)) {
            Logger.get().error(TAG, "WorkSpec id not found!", new Throwable[0]);
            return false;
        }
        Logger.get().debug(TAG, String.format("onStopJob for %s", new Object[]{workSpecId}), new Throwable[0]);
        synchronized (this.mJobParameters) {
            this.mJobParameters.remove(workSpecId);
        }
        this.mWorkManagerImpl.stopWork(workSpecId);
        return true ^ this.mWorkManagerImpl.getProcessor().isCancelled(workSpecId);
    }

    public void onExecuted(@NonNull String workSpecId, boolean needsReschedule) {
        JobParameters parameters;
        Logger.get().debug(TAG, String.format("%s executed on JobScheduler", new Object[]{workSpecId}), new Throwable[0]);
        synchronized (this.mJobParameters) {
            parameters = this.mJobParameters.remove(workSpecId);
        }
        if (parameters != null) {
            jobFinished(parameters, needsReschedule);
        }
    }

    @Nullable
    private static String getWorkSpecIdFromJobParameters(@NonNull JobParameters parameters) {
        try {
            PersistableBundle extras = parameters.getExtras();
            if (extras == null || !extras.containsKey("EXTRA_WORK_SPEC_ID")) {
                return null;
            }
            return extras.getString("EXTRA_WORK_SPEC_ID");
        } catch (NullPointerException e) {
            return null;
        }
    }
}
