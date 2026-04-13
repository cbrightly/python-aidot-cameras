package com.leedarson.newui.cloud_play_back.repos;

import android.content.Context;
import com.leedarson.newui.cloud_play_back.repos.c0;
import java.io.File;

/* compiled from: lambda */
public final /* synthetic */ class l implements Runnable {
    public final /* synthetic */ File c;
    public final /* synthetic */ Context d;

    public /* synthetic */ l(File file, Context context) {
        this.c = file;
        this.d = context;
    }

    public final void run() {
        c0.b.a(this.c, this.d);
    }
}
