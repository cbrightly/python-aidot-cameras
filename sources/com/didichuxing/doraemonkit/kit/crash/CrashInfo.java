package com.didichuxing.doraemonkit.kit.crash;

import java.io.Serializable;

public class CrashInfo implements Serializable {
    public final long time;
    public final Throwable tr;

    public CrashInfo(Throwable tr2, long l) {
        this.tr = tr2;
        this.time = l;
    }
}
