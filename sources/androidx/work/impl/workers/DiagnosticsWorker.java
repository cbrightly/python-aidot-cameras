package androidx.work.impl.workers;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.work.ListenableWorker;
import androidx.work.Logger;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.SystemIdInfo;
import androidx.work.impl.model.SystemIdInfoDao;
import androidx.work.impl.model.WorkNameDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.model.WorkTagDao;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class DiagnosticsWorker extends Worker {
    private static final String TAG = Logger.tagWithPrefix("DiagnosticsWrkr");

    public DiagnosticsWorker(@NonNull Context context, @NonNull WorkerParameters parameters) {
        super(context, parameters);
    }

    @NonNull
    public ListenableWorker.Result doWork() {
        WorkDatabase database = WorkManagerImpl.getInstance(getApplicationContext()).getWorkDatabase();
        WorkSpecDao workSpecDao = database.workSpecDao();
        WorkNameDao workNameDao = database.workNameDao();
        WorkTagDao workTagDao = database.workTagDao();
        SystemIdInfoDao systemIdInfoDao = database.systemIdInfoDao();
        List<WorkSpec> completed = workSpecDao.getRecentlyCompletedWork(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1));
        List<WorkSpec> running = workSpecDao.getRunningWork();
        List<WorkSpec> enqueued = workSpecDao.getAllEligibleWorkSpecsForScheduling(200);
        if (completed != null && !completed.isEmpty()) {
            Logger logger = Logger.get();
            String str = TAG;
            logger.info(str, "Recently completed work:\n\n", new Throwable[0]);
            Logger.get().info(str, workSpecRows(workNameDao, workTagDao, systemIdInfoDao, completed), new Throwable[0]);
        }
        if (running != null && !running.isEmpty()) {
            Logger logger2 = Logger.get();
            String str2 = TAG;
            logger2.info(str2, "Running work:\n\n", new Throwable[0]);
            Logger.get().info(str2, workSpecRows(workNameDao, workTagDao, systemIdInfoDao, running), new Throwable[0]);
        }
        if (enqueued != null && !enqueued.isEmpty()) {
            Logger logger3 = Logger.get();
            String str3 = TAG;
            logger3.info(str3, "Enqueued work:\n\n", new Throwable[0]);
            Logger.get().info(str3, workSpecRows(workNameDao, workTagDao, systemIdInfoDao, enqueued), new Throwable[0]);
        }
        return ListenableWorker.Result.success();
    }

    @NonNull
    private static String workSpecRows(@NonNull WorkNameDao workNameDao, @NonNull WorkTagDao workTagDao, @NonNull SystemIdInfoDao systemIdInfoDao, @NonNull List<WorkSpec> workSpecs) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\n Id \t Class Name\t %s\t State\t Unique Name\t Tags\t", new Object[]{Build.VERSION.SDK_INT >= 23 ? "Job Id" : "Alarm Id"}));
        for (WorkSpec workSpec : workSpecs) {
            Integer systemId = null;
            SystemIdInfo info = systemIdInfoDao.getSystemIdInfo(workSpec.id);
            if (info != null) {
                systemId = Integer.valueOf(info.systemId);
            }
            sb.append(workSpecRow(workSpec, TextUtils.join(",", workNameDao.getNamesForWorkSpecId(workSpec.id)), systemId, TextUtils.join(",", workTagDao.getTagsForWorkSpecId(workSpec.id))));
        }
        return sb.toString();
    }

    @NonNull
    private static String workSpecRow(@NonNull WorkSpec workSpec, @Nullable String name, @Nullable Integer systemId, @NonNull String tags) {
        return String.format("\n%s\t %s\t %s\t %s\t %s\t %s\t", new Object[]{workSpec.id, workSpec.workerClassName, systemId, workSpec.state.name(), name, tags});
    }
}
