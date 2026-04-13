package io.netty.buffer;

import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakTracker;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;

public abstract class AbstractByteBufAllocator implements ByteBufAllocator {
    static final int CALCULATE_THRESHOLD = 4194304;
    static final int DEFAULT_INITIAL_CAPACITY = 256;
    static final int DEFAULT_MAX_CAPACITY = Integer.MAX_VALUE;
    static final int DEFAULT_MAX_COMPONENTS = 16;
    private final boolean directByDefault;
    private final ByteBuf emptyBuf;

    /* access modifiers changed from: protected */
    public abstract ByteBuf newDirectBuffer(int i, int i2);

    /* access modifiers changed from: protected */
    public abstract ByteBuf newHeapBuffer(int i, int i2);

    static {
        ResourceLeakDetector.addExclusions(AbstractByteBufAllocator.class, "toLeakAwareBuffer");
    }

    /* renamed from: io.netty.buffer.AbstractByteBufAllocator$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$util$ResourceLeakDetector$Level;

        static {
            int[] iArr = new int[ResourceLeakDetector.Level.values().length];
            $SwitchMap$io$netty$util$ResourceLeakDetector$Level = iArr;
            try {
                iArr[ResourceLeakDetector.Level.SIMPLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$io$netty$util$ResourceLeakDetector$Level[ResourceLeakDetector.Level.ADVANCED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$io$netty$util$ResourceLeakDetector$Level[ResourceLeakDetector.Level.PARANOID.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    protected static ByteBuf toLeakAwareBuffer(ByteBuf buf) {
        switch (AnonymousClass1.$SwitchMap$io$netty$util$ResourceLeakDetector$Level[ResourceLeakDetector.getLevel().ordinal()]) {
            case 1:
                ResourceLeakTracker<ByteBuf> leak = AbstractByteBuf.leakDetector.track(buf);
                return leak != null ? new SimpleLeakAwareByteBuf(buf, leak) : buf;
            case 2:
            case 3:
                ResourceLeakTracker<ByteBuf> leak2 = AbstractByteBuf.leakDetector.track(buf);
                if (leak2 != null) {
                    return new AdvancedLeakAwareByteBuf(buf, leak2);
                }
                return buf;
            default:
                return buf;
        }
    }

    protected static CompositeByteBuf toLeakAwareBuffer(CompositeByteBuf buf) {
        switch (AnonymousClass1.$SwitchMap$io$netty$util$ResourceLeakDetector$Level[ResourceLeakDetector.getLevel().ordinal()]) {
            case 1:
                ResourceLeakTracker<ByteBuf> leak = AbstractByteBuf.leakDetector.track(buf);
                return leak != null ? new SimpleLeakAwareCompositeByteBuf(buf, leak) : buf;
            case 2:
            case 3:
                ResourceLeakTracker<ByteBuf> leak2 = AbstractByteBuf.leakDetector.track(buf);
                if (leak2 != null) {
                    return new AdvancedLeakAwareCompositeByteBuf(buf, leak2);
                }
                return buf;
            default:
                return buf;
        }
    }

    protected AbstractByteBufAllocator() {
        this(false);
    }

    protected AbstractByteBufAllocator(boolean preferDirect) {
        this.directByDefault = preferDirect && PlatformDependent.hasUnsafe();
        this.emptyBuf = new EmptyByteBuf(this);
    }

    public ByteBuf buffer() {
        if (this.directByDefault) {
            return directBuffer();
        }
        return heapBuffer();
    }

    public ByteBuf buffer(int initialCapacity) {
        if (this.directByDefault) {
            return directBuffer(initialCapacity);
        }
        return heapBuffer(initialCapacity);
    }

    public ByteBuf buffer(int initialCapacity, int maxCapacity) {
        if (this.directByDefault) {
            return directBuffer(initialCapacity, maxCapacity);
        }
        return heapBuffer(initialCapacity, maxCapacity);
    }

    public ByteBuf ioBuffer() {
        if (PlatformDependent.hasUnsafe()) {
            return directBuffer(256);
        }
        return heapBuffer(256);
    }

    public ByteBuf ioBuffer(int initialCapacity) {
        if (PlatformDependent.hasUnsafe()) {
            return directBuffer(initialCapacity);
        }
        return heapBuffer(initialCapacity);
    }

    public ByteBuf ioBuffer(int initialCapacity, int maxCapacity) {
        if (PlatformDependent.hasUnsafe()) {
            return directBuffer(initialCapacity, maxCapacity);
        }
        return heapBuffer(initialCapacity, maxCapacity);
    }

    public ByteBuf heapBuffer() {
        return heapBuffer(256, Integer.MAX_VALUE);
    }

    public ByteBuf heapBuffer(int initialCapacity) {
        return heapBuffer(initialCapacity, Integer.MAX_VALUE);
    }

    public ByteBuf heapBuffer(int initialCapacity, int maxCapacity) {
        if (initialCapacity == 0 && maxCapacity == 0) {
            return this.emptyBuf;
        }
        validate(initialCapacity, maxCapacity);
        return newHeapBuffer(initialCapacity, maxCapacity);
    }

    public ByteBuf directBuffer() {
        return directBuffer(256, Integer.MAX_VALUE);
    }

    public ByteBuf directBuffer(int initialCapacity) {
        return directBuffer(initialCapacity, Integer.MAX_VALUE);
    }

    public ByteBuf directBuffer(int initialCapacity, int maxCapacity) {
        if (initialCapacity == 0 && maxCapacity == 0) {
            return this.emptyBuf;
        }
        validate(initialCapacity, maxCapacity);
        return newDirectBuffer(initialCapacity, maxCapacity);
    }

    public CompositeByteBuf compositeBuffer() {
        if (this.directByDefault) {
            return compositeDirectBuffer();
        }
        return compositeHeapBuffer();
    }

    public CompositeByteBuf compositeBuffer(int maxNumComponents) {
        if (this.directByDefault) {
            return compositeDirectBuffer(maxNumComponents);
        }
        return compositeHeapBuffer(maxNumComponents);
    }

    public CompositeByteBuf compositeHeapBuffer() {
        return compositeHeapBuffer(16);
    }

    public CompositeByteBuf compositeHeapBuffer(int maxNumComponents) {
        return toLeakAwareBuffer(new CompositeByteBuf(this, false, maxNumComponents));
    }

    public CompositeByteBuf compositeDirectBuffer() {
        return compositeDirectBuffer(16);
    }

    public CompositeByteBuf compositeDirectBuffer(int maxNumComponents) {
        return toLeakAwareBuffer(new CompositeByteBuf(this, true, maxNumComponents));
    }

    private static void validate(int initialCapacity, int maxCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("initialCapacity: " + initialCapacity + " (expected: 0+)");
        } else if (initialCapacity > maxCapacity) {
            throw new IllegalArgumentException(String.format("initialCapacity: %d (expected: not greater than maxCapacity(%d)", new Object[]{Integer.valueOf(initialCapacity), Integer.valueOf(maxCapacity)}));
        }
    }

    public String toString() {
        return StringUtil.simpleClassName((Object) this) + "(directByDefault: " + this.directByDefault + ')';
    }
}
