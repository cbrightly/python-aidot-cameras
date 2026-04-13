package com.leedarson.tcp;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.Future;

/* compiled from: FutureListener */
public abstract class c implements ChannelFutureListener {
    public static ChangeQuickRedirect changeQuickRedirect;

    public abstract void a();

    public abstract void c();

    public /* bridge */ /* synthetic */ void operationComplete(Future future) {
        if (!PatchProxy.proxy(new Object[]{future}, this, changeQuickRedirect, false, 10757, new Class[]{Future.class}, Void.TYPE).isSupported) {
            b((ChannelFuture) future);
        }
    }

    public void b(ChannelFuture future) {
        if (!PatchProxy.proxy(new Object[]{future}, this, changeQuickRedirect, false, 10756, new Class[]{ChannelFuture.class}, Void.TYPE).isSupported) {
            if (future.isSuccess()) {
                c();
            } else {
                a();
            }
        }
    }
}
