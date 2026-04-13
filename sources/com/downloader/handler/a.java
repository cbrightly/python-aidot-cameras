package com.downloader.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.downloader.e;
import com.downloader.j;

/* compiled from: ProgressHandler */
public class a extends Handler {
    private final e a;

    public a(e listener) {
        super(Looper.getMainLooper());
        this.a = listener;
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                e eVar = this.a;
                if (eVar != null) {
                    eVar.a((j) msg.obj);
                    return;
                }
                return;
            default:
                super.handleMessage(msg);
                return;
        }
    }
}
