package com.telink.bluetooth.event;

import com.meituan.robust.ChangeQuickRedirect;
import com.telink.util.c;

/* compiled from: DataEvent */
public class a<A> extends c<String> {
    public static ChangeQuickRedirect changeQuickRedirect;
    protected A d;

    public a(Object sender, String type, A args) {
        super(sender, type);
        this.d = args;
    }
}
