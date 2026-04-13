package com.leedarson.newui.cloud_play_back.repos;

import android.content.Context;
import com.leedarson.newui.repoter.beans.ELKStepRecordBean;

/* compiled from: lambda */
public final /* synthetic */ class r implements Runnable {
    public final /* synthetic */ c0 c;
    public final /* synthetic */ String[] d;
    public final /* synthetic */ ELKStepRecordBean f;
    public final /* synthetic */ String q;
    public final /* synthetic */ Context x;

    public /* synthetic */ r(c0 c0Var, String[] strArr, ELKStepRecordBean eLKStepRecordBean, String str, Context context) {
        this.c = c0Var;
        this.d = strArr;
        this.f = eLKStepRecordBean;
        this.q = str;
        this.x = context;
    }

    public final void run() {
        this.c.A(this.d, this.f, this.q, this.x);
    }
}
