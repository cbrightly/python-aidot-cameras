package com.leedarson.serviceimpl.tcp.manager;

import com.meituan.robust.ChangeQuickRedirect;

/* compiled from: Utils */
public class c {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static long a(byte[] res) {
        long num = 0;
        for (int ix = 0; ix < 8; ix++) {
            num = (num << 8) | ((long) (res[ix] & 255));
        }
        return num;
    }
}
