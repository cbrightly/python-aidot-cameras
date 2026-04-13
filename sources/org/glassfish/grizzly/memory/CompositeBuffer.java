package org.glassfish.grizzly.memory;

import org.glassfish.grizzly.Buffer;

public abstract class CompositeBuffer implements Buffer {
    protected DisposeOrder disposeOrder = DisposeOrder.LAST_TO_FIRST;

    public interface BulkOperation {
        boolean processByte(byte b, Setter setter);
    }

    public enum DisposeOrder {
        LAST_TO_FIRST,
        FIRST_TO_LAST
    }

    public interface Setter {
        void set(byte b);
    }

    public abstract void allowInternalBuffersDispose(boolean z);

    public abstract boolean allowInternalBuffersDispose();

    public abstract CompositeBuffer append(Buffer buffer);

    public abstract int bulk(BulkOperation bulkOperation);

    public abstract int bulk(BulkOperation bulkOperation, int i, int i2);

    public abstract CompositeBuffer prepend(Buffer buffer);

    public abstract void removeAll();

    public abstract boolean replace(Buffer buffer, Buffer buffer2);

    public abstract Object[] underlying();

    public static CompositeBuffer newBuffer() {
        return BuffersBuffer.create();
    }

    public static CompositeBuffer newBuffer(MemoryManager memoryManager) {
        return BuffersBuffer.create(memoryManager);
    }

    public static CompositeBuffer newBuffer(MemoryManager memoryManager, Buffer... buffers) {
        return BuffersBuffer.create(memoryManager, buffers);
    }

    public static CompositeBuffer newBuffer(MemoryManager memoryManager, Buffer[] buffers, boolean isReadOnly) {
        return BuffersBuffer.create(memoryManager, buffers, isReadOnly);
    }

    public DisposeOrder disposeOrder() {
        return this.disposeOrder;
    }

    public CompositeBuffer disposeOrder(DisposeOrder disposeOrder2) {
        this.disposeOrder = disposeOrder2;
        return this;
    }
}
