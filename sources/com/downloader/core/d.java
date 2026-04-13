package com.downloader.core;

import com.downloader.i;
import com.downloader.internal.c;
import java.util.concurrent.FutureTask;

/* compiled from: DownloadFutureTask */
public class d extends FutureTask<c> implements Comparable<d> {
    private final c c;

    d(c downloadRunnable) {
        super(downloadRunnable, (Object) null);
        this.c = downloadRunnable;
    }

    /* renamed from: a */
    public int compareTo(d other) {
        int i;
        int i2;
        c cVar = this.c;
        i p1 = cVar.c;
        c cVar2 = other.c;
        i p2 = cVar2.c;
        if (p1 == p2) {
            i2 = cVar.d;
            i = cVar2.d;
        } else {
            i2 = p2.ordinal();
            i = p1.ordinal();
        }
        return i2 - i;
    }
}
