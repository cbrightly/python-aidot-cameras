package androidx.work.impl.background.systemjob;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Context;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.PersistableBundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.os.BuildCompat;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ContentUriTriggers;
import androidx.work.Logger;
import androidx.work.NetworkType;
import androidx.work.impl.model.WorkSpec;

@RequiresApi(api = 23)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class SystemJobInfoConverter {
    static final String EXTRA_IS_PERIODIC = "EXTRA_IS_PERIODIC";
    static final String EXTRA_WORK_SPEC_ID = "EXTRA_WORK_SPEC_ID";
    private static final String TAG = Logger.tagWithPrefix("SystemJobInfoConverter");
    private final ComponentName mWorkServiceComponent;

    @VisibleForTesting(otherwise = 3)
    SystemJobInfoConverter(@NonNull Context context) {
        this.mWorkServiceComponent = new ComponentName(context.getApplicationContext(), SystemJobService.class);
    }

    /* access modifiers changed from: package-private */
    public JobInfo convert(WorkSpec workSpec, int jobId) {
        WorkSpec workSpec2 = workSpec;
        Constraints constraints = workSpec2.constraints;
        PersistableBundle extras = new PersistableBundle();
        extras.putString(EXTRA_WORK_SPEC_ID, workSpec2.id);
        extras.putBoolean(EXTRA_IS_PERIODIC, workSpec.isPeriodic());
        JobInfo.Builder builder = new JobInfo.Builder(jobId, this.mWorkServiceComponent).setRequiresCharging(constraints.requiresCharging()).setRequiresDeviceIdle(constraints.requiresDeviceIdle()).setExtras(extras);
        setRequiredNetwork(builder, constraints.getRequiredNetworkType());
        if (!constraints.requiresDeviceIdle()) {
            builder.setBackoffCriteria(workSpec2.backoffDelayDuration, workSpec2.backoffPolicy == BackoffPolicy.LINEAR ? 0 : 1);
        }
        long offset = Math.max(workSpec.calculateNextRunTime() - System.currentTimeMillis(), 0);
        int i = Build.VERSION.SDK_INT;
        if (i <= 28) {
            builder.setMinimumLatency(offset);
        } else if (offset > 0) {
            builder.setMinimumLatency(offset);
        } else if (!workSpec2.expedited) {
            builder.setImportantWhileForeground(true);
        }
        if (i >= 24 && constraints.hasContentUriTriggers()) {
            for (ContentUriTriggers.Trigger trigger : constraints.getContentUriTriggers().getTriggers()) {
                builder.addTriggerContentUri(convertContentUriTrigger(trigger));
            }
            builder.setTriggerContentUpdateDelay(constraints.getTriggerContentUpdateDelay());
            builder.setTriggerContentMaxDelay(constraints.getTriggerMaxContentDelay());
        }
        boolean isDelayed = false;
        builder.setPersisted(false);
        if (Build.VERSION.SDK_INT >= 26) {
            builder.setRequiresBatteryNotLow(constraints.requiresBatteryNotLow());
            builder.setRequiresStorageNotLow(constraints.requiresStorageNotLow());
        }
        boolean isRetry = workSpec2.runAttemptCount > 0;
        if (offset > 0) {
            isDelayed = true;
        }
        if (BuildCompat.isAtLeastS() && workSpec2.expedited && !isRetry && !isDelayed) {
            builder.setExpedited(true);
        }
        return builder.build();
    }

    @RequiresApi(24)
    private static JobInfo.TriggerContentUri convertContentUriTrigger(ContentUriTriggers.Trigger trigger) {
        return new JobInfo.TriggerContentUri(trigger.getUri(), trigger.shouldTriggerForDescendants() ? 1 : 0);
    }

    static void setRequiredNetwork(@NonNull JobInfo.Builder builder, @NonNull NetworkType networkType) {
        if (Build.VERSION.SDK_INT < 30 || networkType != NetworkType.TEMPORARILY_UNMETERED) {
            builder.setRequiredNetworkType(convertNetworkType(networkType));
        } else {
            builder.setRequiredNetwork(new NetworkRequest.Builder().addCapability(25).build());
        }
    }

    /* renamed from: androidx.work.impl.background.systemjob.SystemJobInfoConverter$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$work$NetworkType;

        static {
            int[] iArr = new int[NetworkType.values().length];
            $SwitchMap$androidx$work$NetworkType = iArr;
            try {
                iArr[NetworkType.NOT_REQUIRED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$work$NetworkType[NetworkType.CONNECTED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$work$NetworkType[NetworkType.UNMETERED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$androidx$work$NetworkType[NetworkType.NOT_ROAMING.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$androidx$work$NetworkType[NetworkType.METERED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    static int convertNetworkType(NetworkType networkType) {
        switch (AnonymousClass1.$SwitchMap$androidx$work$NetworkType[networkType.ordinal()]) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                if (Build.VERSION.SDK_INT >= 24) {
                    return 3;
                }
                break;
            case 5:
                if (Build.VERSION.SDK_INT >= 26) {
                    return 4;
                }
                break;
        }
        Logger.get().debug(TAG, String.format("API version too low. Cannot convert network type value %s", new Object[]{networkType}), new Throwable[0]);
        return 1;
    }
}
