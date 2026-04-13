package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.util.UniqueName;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.concurrent.ConcurrentMap;

public class ChannelOption<T> extends UniqueName {
    @Deprecated
    public static final ChannelOption<Long> AIO_READ_TIMEOUT = valueOf("AIO_READ_TIMEOUT");
    @Deprecated
    public static final ChannelOption<Long> AIO_WRITE_TIMEOUT = valueOf("AIO_WRITE_TIMEOUT");
    public static final ChannelOption<ByteBufAllocator> ALLOCATOR = valueOf("ALLOCATOR");
    public static final ChannelOption<Boolean> ALLOW_HALF_CLOSURE = valueOf("ALLOW_HALF_CLOSURE");
    @Deprecated
    public static final ChannelOption<Boolean> AUTO_CLOSE = valueOf("AUTO_CLOSE");
    public static final ChannelOption<Boolean> AUTO_READ = valueOf("AUTO_READ");
    public static final ChannelOption<Integer> CONNECT_TIMEOUT_MILLIS = valueOf("CONNECT_TIMEOUT_MILLIS");
    @Deprecated
    public static final ChannelOption<Boolean> DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION = valueOf("DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION");
    public static final ChannelOption<InetAddress> IP_MULTICAST_ADDR = valueOf("IP_MULTICAST_ADDR");
    public static final ChannelOption<NetworkInterface> IP_MULTICAST_IF = valueOf("IP_MULTICAST_IF");
    public static final ChannelOption<Boolean> IP_MULTICAST_LOOP_DISABLED = valueOf("IP_MULTICAST_LOOP_DISABLED");
    public static final ChannelOption<Integer> IP_MULTICAST_TTL = valueOf("IP_MULTICAST_TTL");
    public static final ChannelOption<Integer> IP_TOS = valueOf("IP_TOS");
    public static final ChannelOption<Integer> MAX_MESSAGES_PER_READ = valueOf("MAX_MESSAGES_PER_READ");
    public static final ChannelOption<MessageSizeEstimator> MESSAGE_SIZE_ESTIMATOR = valueOf("MESSAGE_SIZE_ESTIMATOR");
    public static final ChannelOption<RecvByteBufAllocator> RCVBUF_ALLOCATOR = valueOf("RCVBUF_ALLOCATOR");
    public static final ChannelOption<Boolean> SINGLE_EVENTEXECUTOR_PER_GROUP = valueOf("SINGLE_EVENTEXECUTOR_PER_GROUP");
    public static final ChannelOption<Integer> SO_BACKLOG = valueOf("SO_BACKLOG");
    public static final ChannelOption<Boolean> SO_BROADCAST = valueOf("SO_BROADCAST");
    public static final ChannelOption<Boolean> SO_KEEPALIVE = valueOf("SO_KEEPALIVE");
    public static final ChannelOption<Integer> SO_LINGER = valueOf("SO_LINGER");
    public static final ChannelOption<Integer> SO_RCVBUF = valueOf("SO_RCVBUF");
    public static final ChannelOption<Boolean> SO_REUSEADDR = valueOf("SO_REUSEADDR");
    public static final ChannelOption<Integer> SO_SNDBUF = valueOf("SO_SNDBUF");
    public static final ChannelOption<Integer> SO_TIMEOUT = valueOf("SO_TIMEOUT");
    public static final ChannelOption<Boolean> TCP_NODELAY = valueOf("TCP_NODELAY");
    public static final ChannelOption<Integer> WRITE_BUFFER_HIGH_WATER_MARK = valueOf("WRITE_BUFFER_HIGH_WATER_MARK");
    public static final ChannelOption<Integer> WRITE_BUFFER_LOW_WATER_MARK = valueOf("WRITE_BUFFER_LOW_WATER_MARK");
    public static final ChannelOption<Integer> WRITE_SPIN_COUNT = valueOf("WRITE_SPIN_COUNT");
    private static final ConcurrentMap<String, ChannelOption> names = PlatformDependent.newConcurrentHashMap();

    public static <T> ChannelOption<T> valueOf(String name) {
        ObjectUtil.checkNotNull(name, "name");
        ConcurrentMap<String, ChannelOption> concurrentMap = names;
        ChannelOption<T> option = (ChannelOption) concurrentMap.get(name);
        if (option != null) {
            return option;
        }
        ChannelOption<T> option2 = new ChannelOption<>(name);
        ChannelOption<T> old = concurrentMap.putIfAbsent(name, option2);
        if (old != null) {
            return old;
        }
        return option2;
    }

    public static boolean exists(String name) {
        ObjectUtil.checkNotNull(name, "name");
        return names.containsKey(name);
    }

    public static <T> ChannelOption<T> newInstance(String name) {
        ObjectUtil.checkNotNull(name, "name");
        ChannelOption<T> option = new ChannelOption<>(name);
        if (names.putIfAbsent(name, option) == null) {
            return option;
        }
        throw new IllegalArgumentException(String.format("'%s' is already in use", new Object[]{name}));
    }

    @Deprecated
    protected ChannelOption(String name) {
        super(name);
    }

    public void validate(T value) {
        if (value == null) {
            throw new NullPointerException("value");
        }
    }
}
