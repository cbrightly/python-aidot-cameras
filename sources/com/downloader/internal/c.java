package com.downloader.internal;

import com.downloader.i;
import com.downloader.k;
import com.downloader.l;
import com.downloader.request.a;

/* compiled from: DownloadRunnable */
public class c implements Runnable {
    public final i c;
    public final int d;
    public final a f;

    c(a request) {
        this.f = request;
        this.c = request.v();
        this.d = request.y();
    }

    public void run() {
        this.f.I(l.RUNNING);
        k response = d.d(this.f).k();
        if (response.d()) {
            this.f.k();
        } else if (response.c()) {
            this.f.i();
        } else if (response.a() != null) {
            this.f.h(response.a());
        } else if (!response.b()) {
            this.f.h(new com.downloader.a());
        }
    }
}
