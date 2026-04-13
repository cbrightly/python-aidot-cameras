package io.netty.channel;

import io.netty.channel.ChannelHandler;
import io.netty.util.internal.InternalThreadLocalMap;
import java.util.Map;

public abstract class ChannelHandlerAdapter implements ChannelHandler {
    boolean added;

    /* access modifiers changed from: protected */
    public void ensureNotSharable() {
        if (isSharable()) {
            throw new IllegalStateException("ChannelHandler " + getClass().getName() + " is not allowed to be shared");
        }
    }

    public boolean isSharable() {
        Class<?> clazz = getClass();
        Map<Class<?>, Boolean> cache = InternalThreadLocalMap.get().handlerSharableCache();
        Boolean sharable = cache.get(clazz);
        if (sharable == null) {
            sharable = Boolean.valueOf(clazz.isAnnotationPresent(ChannelHandler.Sharable.class));
            cache.put(clazz, sharable);
        }
        return sharable.booleanValue();
    }

    public void handlerAdded(ChannelHandlerContext ctx) {
    }

    public void handlerRemoved(ChannelHandlerContext ctx) {
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.fireExceptionCaught(cause);
    }
}
