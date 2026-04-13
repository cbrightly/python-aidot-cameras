package org.glassfish.grizzly.http.io;

import java.util.Arrays;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.HeapBuffer;
import org.glassfish.grizzly.memory.MemoryManager;

public final class TemporaryHeapBuffer extends HeapBuffer {
    boolean hasClonedArray;
    boolean isDisposed;

    TemporaryHeapBuffer() {
    }

    /* access modifiers changed from: package-private */
    public void reset(byte[] heap, int offset, int len) {
        this.heap = heap;
        this.offset = offset;
        this.cap = len;
        this.lim = len;
        this.pos = 0;
        this.byteBuffer = null;
        this.isDisposed = false;
        this.hasClonedArray = false;
    }

    /* access modifiers changed from: package-private */
    public Buffer cloneContent(MemoryManager memoryManager) {
        Buffer buffer;
        int length = remaining();
        if (!this.hasClonedArray) {
            buffer = memoryManager.allocate(length);
            buffer.put(this.heap, this.offset + this.pos, length);
            buffer.flip();
        } else {
            buffer = Buffers.wrap(memoryManager, this.heap, this.offset + this.pos, length);
        }
        buffer.allowBufferDispose(true);
        dispose();
        return buffer;
    }

    /* access modifiers changed from: protected */
    public void onShareHeap() {
        if (!this.hasClonedArray) {
            byte[] bArr = this.heap;
            int i = this.offset;
            this.heap = Arrays.copyOfRange(bArr, i, this.cap + i);
            this.offset = 0;
            this.hasClonedArray = true;
        }
        super.onShareHeap();
    }

    public void dispose() {
        this.isDisposed = true;
        super.dispose();
    }

    /* access modifiers changed from: package-private */
    public boolean isDisposed() {
        return this.isDisposed;
    }

    public void recycle() {
        reset((byte[]) null, 0, 0);
    }
}
