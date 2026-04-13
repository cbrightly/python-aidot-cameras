package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import android.app.job.JobParameters;

/* compiled from: lambda */
public final /* synthetic */ class c implements Runnable {
    public final /* synthetic */ JobInfoSchedulerService c;
    public final /* synthetic */ JobParameters d;

    public /* synthetic */ c(JobInfoSchedulerService jobInfoSchedulerService, JobParameters jobParameters) {
        this.c = jobInfoSchedulerService;
        this.d = jobParameters;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
