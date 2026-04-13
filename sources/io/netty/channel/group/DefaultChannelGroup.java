package io.netty.channel.group;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ServerChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.ConcurrentSet;
import io.netty.util.internal.StringUtil;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultChannelGroup extends AbstractSet<Channel> implements ChannelGroup {
    private static final AtomicInteger nextId = new AtomicInteger();
    private volatile boolean closed;
    private final EventExecutor executor;
    private final String name;
    private final ConcurrentSet<Channel> nonServerChannels;
    private final ChannelFutureListener remover;
    private final ConcurrentSet<Channel> serverChannels;
    private final boolean stayClosed;

    public DefaultChannelGroup(EventExecutor executor2) {
        this(executor2, false);
    }

    public DefaultChannelGroup(String name2, EventExecutor executor2) {
        this(name2, executor2, false);
    }

    public DefaultChannelGroup(EventExecutor executor2, boolean stayClosed2) {
        this("group-0x" + Integer.toHexString(nextId.incrementAndGet()), executor2, stayClosed2);
    }

    public DefaultChannelGroup(String name2, EventExecutor executor2, boolean stayClosed2) {
        this.serverChannels = new ConcurrentSet<>();
        this.nonServerChannels = new ConcurrentSet<>();
        this.remover = new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) {
                DefaultChannelGroup.this.remove(future.channel());
            }
        };
        if (name2 != null) {
            this.name = name2;
            this.executor = executor2;
            this.stayClosed = stayClosed2;
            return;
        }
        throw new NullPointerException("name");
    }

    public String name() {
        return this.name;
    }

    public boolean isEmpty() {
        return this.nonServerChannels.isEmpty() && this.serverChannels.isEmpty();
    }

    public int size() {
        return this.nonServerChannels.size() + this.serverChannels.size();
    }

    public boolean contains(Object o) {
        if (!(o instanceof Channel)) {
            return false;
        }
        Channel c = (Channel) o;
        if (o instanceof ServerChannel) {
            return this.serverChannels.contains(c);
        }
        return this.nonServerChannels.contains(c);
    }

    public boolean add(Channel channel) {
        boolean added = (channel instanceof ServerChannel ? this.serverChannels : this.nonServerChannels).add(channel);
        if (added) {
            channel.closeFuture().addListener(this.remover);
        }
        if (this.stayClosed && this.closed) {
            channel.close();
        }
        return added;
    }

    public boolean remove(Object o) {
        boolean removed;
        if (!(o instanceof Channel)) {
            return false;
        }
        Channel c = (Channel) o;
        if (c instanceof ServerChannel) {
            removed = this.serverChannels.remove(c);
        } else {
            removed = this.nonServerChannels.remove(c);
        }
        if (!removed) {
            return false;
        }
        c.closeFuture().removeListener(this.remover);
        return true;
    }

    public void clear() {
        this.nonServerChannels.clear();
        this.serverChannels.clear();
    }

    public Iterator<Channel> iterator() {
        return new CombinedIterator(this.serverChannels.iterator(), this.nonServerChannels.iterator());
    }

    public Object[] toArray() {
        Collection<Channel> channels = new ArrayList<>(size());
        channels.addAll(this.serverChannels);
        channels.addAll(this.nonServerChannels);
        return channels.toArray();
    }

    public <T> T[] toArray(T[] a) {
        Collection<Channel> channels = new ArrayList<>(size());
        channels.addAll(this.serverChannels);
        channels.addAll(this.nonServerChannels);
        return channels.toArray(a);
    }

    public ChannelGroupFuture close() {
        return close(ChannelMatchers.all());
    }

    public ChannelGroupFuture disconnect() {
        return disconnect(ChannelMatchers.all());
    }

    public ChannelGroupFuture deregister() {
        return deregister(ChannelMatchers.all());
    }

    public ChannelGroupFuture write(Object message) {
        return write(message, ChannelMatchers.all());
    }

    private static Object safeDuplicate(Object message) {
        if (message instanceof ByteBuf) {
            return ((ByteBuf) message).duplicate().retain();
        }
        if (message instanceof ByteBufHolder) {
            return ((ByteBufHolder) message).duplicate().retain();
        }
        return ReferenceCountUtil.retain(message);
    }

    public ChannelGroupFuture write(Object message, ChannelMatcher matcher) {
        if (message == null) {
            throw new NullPointerException("message");
        } else if (matcher != null) {
            Map<Channel, ChannelFuture> futures = new LinkedHashMap<>(size());
            Iterator<Channel> it = this.nonServerChannels.iterator();
            while (it.hasNext()) {
                Channel c = it.next();
                if (matcher.matches(c)) {
                    futures.put(c, c.write(safeDuplicate(message)));
                }
            }
            ReferenceCountUtil.release(message);
            return new DefaultChannelGroupFuture((ChannelGroup) this, futures, this.executor);
        } else {
            throw new NullPointerException("matcher");
        }
    }

    public ChannelGroup flush() {
        return flush(ChannelMatchers.all());
    }

    public ChannelGroupFuture flushAndWrite(Object message) {
        return writeAndFlush(message);
    }

    public ChannelGroupFuture writeAndFlush(Object message) {
        return writeAndFlush(message, ChannelMatchers.all());
    }

    public ChannelGroupFuture disconnect(ChannelMatcher matcher) {
        if (matcher != null) {
            Map<Channel, ChannelFuture> futures = new LinkedHashMap<>(size());
            Iterator<Channel> it = this.serverChannels.iterator();
            while (it.hasNext()) {
                Channel c = it.next();
                if (matcher.matches(c)) {
                    futures.put(c, c.disconnect());
                }
            }
            Iterator<Channel> it2 = this.nonServerChannels.iterator();
            while (it2.hasNext()) {
                Channel c2 = it2.next();
                if (matcher.matches(c2)) {
                    futures.put(c2, c2.disconnect());
                }
            }
            return new DefaultChannelGroupFuture((ChannelGroup) this, futures, this.executor);
        }
        throw new NullPointerException("matcher");
    }

    public ChannelGroupFuture close(ChannelMatcher matcher) {
        if (matcher != null) {
            Map<Channel, ChannelFuture> futures = new LinkedHashMap<>(size());
            if (this.stayClosed) {
                this.closed = true;
            }
            Iterator<Channel> it = this.serverChannels.iterator();
            while (it.hasNext()) {
                Channel c = it.next();
                if (matcher.matches(c)) {
                    futures.put(c, c.close());
                }
            }
            Iterator<Channel> it2 = this.nonServerChannels.iterator();
            while (it2.hasNext()) {
                Channel c2 = it2.next();
                if (matcher.matches(c2)) {
                    futures.put(c2, c2.close());
                }
            }
            return new DefaultChannelGroupFuture((ChannelGroup) this, futures, this.executor);
        }
        throw new NullPointerException("matcher");
    }

    public ChannelGroupFuture deregister(ChannelMatcher matcher) {
        if (matcher != null) {
            Map<Channel, ChannelFuture> futures = new LinkedHashMap<>(size());
            Iterator<Channel> it = this.serverChannels.iterator();
            while (it.hasNext()) {
                Channel c = it.next();
                if (matcher.matches(c)) {
                    futures.put(c, c.deregister());
                }
            }
            Iterator<Channel> it2 = this.nonServerChannels.iterator();
            while (it2.hasNext()) {
                Channel c2 = it2.next();
                if (matcher.matches(c2)) {
                    futures.put(c2, c2.deregister());
                }
            }
            return new DefaultChannelGroupFuture((ChannelGroup) this, futures, this.executor);
        }
        throw new NullPointerException("matcher");
    }

    public ChannelGroup flush(ChannelMatcher matcher) {
        Iterator<Channel> it = this.nonServerChannels.iterator();
        while (it.hasNext()) {
            Channel c = it.next();
            if (matcher.matches(c)) {
                c.flush();
            }
        }
        return this;
    }

    public ChannelGroupFuture flushAndWrite(Object message, ChannelMatcher matcher) {
        return writeAndFlush(message, matcher);
    }

    public ChannelGroupFuture writeAndFlush(Object message, ChannelMatcher matcher) {
        if (message != null) {
            Map<Channel, ChannelFuture> futures = new LinkedHashMap<>(size());
            Iterator<Channel> it = this.nonServerChannels.iterator();
            while (it.hasNext()) {
                Channel c = it.next();
                if (matcher.matches(c)) {
                    futures.put(c, c.writeAndFlush(safeDuplicate(message)));
                }
            }
            ReferenceCountUtil.release(message);
            return new DefaultChannelGroupFuture((ChannelGroup) this, futures, this.executor);
        }
        throw new NullPointerException("message");
    }

    public ChannelGroupFuture newCloseFuture() {
        return newCloseFuture(ChannelMatchers.all());
    }

    public ChannelGroupFuture newCloseFuture(ChannelMatcher matcher) {
        Map<Channel, ChannelFuture> futures = new LinkedHashMap<>(size());
        Iterator<Channel> it = this.serverChannels.iterator();
        while (it.hasNext()) {
            Channel c = it.next();
            if (matcher.matches(c)) {
                futures.put(c, c.closeFuture());
            }
        }
        Iterator<Channel> it2 = this.nonServerChannels.iterator();
        while (it2.hasNext()) {
            Channel c2 = it2.next();
            if (matcher.matches(c2)) {
                futures.put(c2, c2.closeFuture());
            }
        }
        return new DefaultChannelGroupFuture((ChannelGroup) this, futures, this.executor);
    }

    public int hashCode() {
        return System.identityHashCode(this);
    }

    public boolean equals(Object o) {
        return this == o;
    }

    public int compareTo(ChannelGroup o) {
        int v = name().compareTo(o.name());
        if (v != 0) {
            return v;
        }
        return System.identityHashCode(this) - System.identityHashCode(o);
    }

    public String toString() {
        return StringUtil.simpleClassName((Object) this) + "(name: " + name() + ", size: " + size() + ')';
    }
}
