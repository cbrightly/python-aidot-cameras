package com.leedarson.newui.cloud_play_back.repos;

import android.content.Context;
import com.leedarson.newui.repoter.beans.ELKStepRecordBean;
import java.io.File;

/* compiled from: lambda */
public final /* synthetic */ class t implements Runnable {
    public final /* synthetic */ c0 c;
    public final /* synthetic */ File d;
    public final /* synthetic */ Context f;
    public final /* synthetic */ ELKStepRecordBean q;

    public /* synthetic */ t(c0 c0Var, File file, Context context, ELKStepRecordBean eLKStepRecordBean) {
        this.c = c0Var;
        this.d = file;
        this.f = context;
        this.q = eLKStepRecordBean;
    }

    public final void run() {
        this.c.O(this.d, this.f, this.q);
    }
}
