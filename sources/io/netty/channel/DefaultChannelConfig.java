package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.nio.AbstractNioByteChannel;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class DefaultChannelConfig implements ChannelConfig {
    private static final AtomicIntegerFieldUpdater<DefaultChannelConfig> AUTOREAD_UPDATER = AtomicIntegerFieldUpdater.newUpdater(DefaultChannelConfig.class, "autoRead");
    private static final int DEFAULT_CONNECT_TIMEOUT = 30000;
    private static final MessageSizeEstimator DEFAULT_MSG_SIZE_ESTIMATOR = DefaultMessageSizeEstimator.DEFAULT;
    private static final RecvByteBufAllocator DEFAULT_RCVBUF_ALLOCATOR = AdaptiveRecvByteBufAllocator.DEFAULT;
    private volatile ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
    private volatile boolean autoClose = true;
    private volatile int autoRead = 1;
    protected final Channel channel;
    private volatile int connectTimeoutMillis = 30000;
    private volatile int maxMessagesPerRead;
    private volatile MessageSizeEstimator msgSizeEstimator = DEFAULT_MSG_SIZE_ESTIMATOR;
    private volatile boolean pinEventExecutor = true;
    private volatile RecvByteBufAllocator rcvBufAllocator = DEFAULT_RCVBUF_ALLOCATOR;
    private volatile int writeBufferHighWaterMark = 65536;
    private volatile int writeBufferLowWaterMark = 32768;
    private volatile int writeSpinCount = 16;

    public DefaultChannelConfig(Channel channel2) {
        if (channel2 != null) {
            this.channel = channel2;
            if ((channel2 instanceof ServerChannel) || (channel2 instanceof AbstractNioByteChannel)) {
                this.maxMessagesPerRead = 16;
            } else {
                this.maxMessagesPerRead = 1;
            }
        } else {
            throw new NullPointerException("channel");
        }
    }

    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions((Map<ChannelOption<?>, Object>) null, ChannelOption.CONNECT_TIMEOUT_MILLIS, ChannelOption.MAX_MESSAGES_PER_READ, ChannelOption.WRITE_SPIN_COUNT, ChannelOption.ALLOCATOR, ChannelOption.AUTO_READ, ChannelOption.AUTO_CLOSE, ChannelOption.RCVBUF_ALLOCATOR, ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, ChannelOption.MESSAGE_SIZE_ESTIMATOR, ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP);
    }

    /* access modifiers changed from: protected */
    public Map<ChannelOption<?>, Object> getOptions(Map<ChannelOption<?>, Object> result, ChannelOption<?>... options) {
        if (result == null) {
            result = new IdentityHashMap<>();
        }
        for (ChannelOption<?> o : options) {
            result.put(o, getOption(o));
        }
        return result;
    }

    public boolean setOptions(Map<ChannelOption<?>, ?> options) {
        if (options != null) {
            boolean setAllOptions = true;
            for (Map.Entry<ChannelOption<?>, ?> e : options.entrySet()) {
                if (!setOption(e.getKey(), e.getValue())) {
                    setAllOptions = false;
                }
            }
            return setAllOptions;
        }
        throw new NullPointerException("options");
    }

    public <T> T getOption(ChannelOption<T> option) {
        if (option == null) {
            throw new NullPointerException("option");
        } else if (option == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
            return Integer.valueOf(getConnectTimeoutMillis());
        } else {
            if (option == ChannelOption.MAX_MESSAGES_PER_READ) {
                return Integer.valueOf(getMaxMessagesPerRead());
            }
            if (option == ChannelOption.WRITE_SPIN_COUNT) {
                return Integer.valueOf(getWriteSpinCount());
            }
            if (option == ChannelOption.ALLOCATOR) {
                return getAllocator();
            }
            if (option == ChannelOption.RCVBUF_ALLOCATOR) {
                return getRecvByteBufAllocator();
            }
            if (option == ChannelOption.AUTO_READ) {
                return Boolean.valueOf(isAutoRead());
            }
            if (option == ChannelOption.AUTO_CLOSE) {
                return Boolean.valueOf(isAutoClose());
            }
            if (option == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
                return Integer.valueOf(getWriteBufferHighWaterMark());
            }
            if (option == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
                return Integer.valueOf(getWriteBufferLowWaterMark());
            }
            if (option == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
                return getMessageSizeEstimator();
            }
            if (option == ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP) {
                return Boolean.valueOf(getPinEventExecutorPerGroup());
            }
            return null;
        }
    }

    public <T> boolean setOption(ChannelOption<T> option, T value) {
        validate(option, value);
        if (option == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
            setConnectTimeoutMillis(((Integer) value).intValue());
            return true;
        } else if (option == ChannelOption.MAX_MESSAGES_PER_READ) {
            setMaxMessagesPerRead(((Integer) value).intValue());
            return true;
        } else if (option == ChannelOption.WRITE_SPIN_COUNT) {
            setWriteSpinCount(((Integer) value).intValue());
            return true;
        } else if (option == ChannelOption.ALLOCATOR) {
            setAllocator((ByteBufAllocator) value);
            return true;
        } else if (option == ChannelOption.RCVBUF_ALLOCATOR) {
            setRecvByteBufAllocator((RecvByteBufAllocator) value);
            return true;
        } else if (option == ChannelOption.AUTO_READ) {
            setAutoRead(((Boolean) value).booleanValue());
            return true;
        } else if (option == ChannelOption.AUTO_CLOSE) {
            setAutoClose(((Boolean) value).booleanValue());
            return true;
        } else if (option == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
            setWriteBufferHighWaterMark(((Integer) value).intValue());
            return true;
        } else if (option == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
            setWriteBufferLowWaterMark(((Integer) value).intValue());
            return true;
        } else if (option == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
            setMessageSizeEstimator((MessageSizeEstimator) value);
            return true;
        } else if (option != ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP) {
            return false;
        } else {
            setPinEventExecutorPerGroup(((Boolean) value).booleanValue());
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public <T> void validate(ChannelOption<T> option, T value) {
        if (option != null) {
            option.validate(value);
            return;
        }
        throw new NullPointerException("option");
    }

    public int getConnectTimeoutMillis() {
        return this.connectTimeoutMillis;
    }

    public ChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis2) {
        if (connectTimeoutMillis2 >= 0) {
            this.connectTimeoutMillis = connectTimeoutMillis2;
            return this;
        }
        throw new IllegalArgumentException(String.format("connectTimeoutMillis: %d (expected: >= 0)", new Object[]{Integer.valueOf(connectTimeoutMillis2)}));
    }

    public int getMaxMessagesPerRead() {
        return this.maxMessagesPerRead;
    }

    public ChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead2) {
        if (maxMessagesPerRead2 > 0) {
            this.maxMessagesPerRead = maxMessagesPerRead2;
            return this;
        }
        throw new IllegalArgumentException("maxMessagesPerRead: " + maxMessagesPerRead2 + " (expected: > 0)");
    }

    public int getWriteSpinCount() {
        return this.writeSpinCount;
    }

    public ChannelConfig setWriteSpinCount(int writeSpinCount2) {
        if (writeSpinCount2 > 0) {
            this.writeSpinCount = writeSpinCount2;
            return this;
        }
        throw new IllegalArgumentException("writeSpinCount must be a positive integer.");
    }

    public ByteBufAllocator getAllocator() {
        return this.allocator;
    }

    public ChannelConfig setAllocator(ByteBufAllocator allocator2) {
        if (allocator2 != null) {
            this.allocator = allocator2;
            return this;
        }
        throw new NullPointerException("allocator");
    }

    public RecvByteBufAllocator getRecvByteBufAllocator() {
        return this.rcvBufAllocator;
    }

    public ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator2) {
        if (allocator2 != null) {
            this.rcvBufAllocator = allocator2;
            return this;
        }
        throw new NullPointerException("allocator");
    }

    public boolean isAutoRead() {
        return this.autoRead == 1;
    }

    public ChannelConfig setAutoRead(boolean autoRead2) {
        boolean z = true;
        if (AUTOREAD_UPDATER.getAndSet(this, autoRead2) != 1) {
            z = false;
        }
        boolean oldAutoRead = z;
        if (autoRead2 && !oldAutoRead) {
            this.channel.read();
        } else if (!autoRead2 && oldAutoRead) {
            autoReadCleared();
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public void autoReadCleared() {
    }

    public boolean isAutoClose() {
        return this.autoClose;
    }

    public ChannelConfig setAutoClose(boolean autoClose2) {
        this.autoClose = autoClose2;
        return this;
    }

    public int getWriteBufferHighWaterMark() {
        return this.writeBufferHighWaterMark;
    }

    public ChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark2) {
        if (writeBufferHighWaterMark2 < getWriteBufferLowWaterMark()) {
            throw new IllegalArgumentException("writeBufferHighWaterMark cannot be less than writeBufferLowWaterMark (" + getWriteBufferLowWaterMark() + "): " + writeBufferHighWaterMark2);
        } else if (writeBufferHighWaterMark2 >= 0) {
            this.writeBufferHighWaterMark = writeBufferHighWaterMark2;
            return this;
        } else {
            throw new IllegalArgumentException("writeBufferHighWaterMark must be >= 0");
        }
    }

    public int getWriteBufferLowWaterMark() {
        return this.writeBufferLowWaterMark;
    }

    public ChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark2) {
        if (writeBufferLowWaterMark2 > getWriteBufferHighWaterMark()) {
            throw new IllegalArgumentException("writeBufferLowWaterMark cannot be greater than writeBufferHighWaterMark (" + getWriteBufferHighWaterMark() + "): " + writeBufferLowWaterMark2);
        } else if (writeBufferLowWaterMark2 >= 0) {
            this.writeBufferLowWaterMark = writeBufferLowWaterMark2;
            return this;
        } else {
            throw new IllegalArgumentException("writeBufferLowWaterMark must be >= 0");
        }
    }

    public MessageSizeEstimator getMessageSizeEstimator() {
        return this.msgSizeEstimator;
    }

    public ChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
        if (estimator != null) {
            this.msgSizeEstimator = estimator;
            return this;
        }
        throw new NullPointerException("estimator");
    }

    private ChannelConfig setPinEventExecutorPerGroup(boolean pinEventExecutor2) {
        this.pinEventExecutor = pinEventExecutor2;
        return this;
    }

    private boolean getPinEventExecutorPerGroup() {
        return this.pinEventExecutor;
    }
}
