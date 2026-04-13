package com.leedarson.newui.cloud_play_back.view;

import java.io.File;

/* compiled from: lambda */
public final /* synthetic */ class i0 implements Runnable {
    public final /* synthetic */ File c;

    public /* synthetic */ i0(File file) {
        this.c = file;
    }

    public final void run() {
        LDSBasePlayerView.i(this.c);
    }
}
