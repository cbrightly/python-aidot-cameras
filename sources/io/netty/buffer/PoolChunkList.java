package io.netty.buffer;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class PoolChunkList<T> implements PoolChunkListMetric {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Iterator<PoolChunkMetric> EMPTY_METRICS = Collections.emptyList().iterator();
    private final PoolArena<T> arena;
    private PoolChunk<T> head;
    private final int maxCapacity;
    private final int maxUsage;
    private final int minUsage;
    private final PoolChunkList<T> nextList;
    private PoolChunkList<T> prevList;

    static {
        Class<PoolChunkList> cls = PoolChunkList.class;
    }

    PoolChunkList(PoolArena<T> arena2, PoolChunkList<T> nextList2, int minUsage2, int maxUsage2, int chunkSize) {
        if (minUsage2 <= maxUsage2) {
            this.arena = arena2;
            this.nextList = nextList2;
            this.minUsage = minUsage2;
            this.maxUsage = maxUsage2;
            this.maxCapacity = calculateMaxCapacity(minUsage2, chunkSize);
            return;
        }
        throw new AssertionError();
    }

    private static int calculateMaxCapacity(int minUsage2, int chunkSize) {
        int minUsage3 = minUsage0(minUsage2);
        if (minUsage3 == 100) {
            return 0;
        }
        return (int) ((((long) chunkSize) * (100 - ((long) minUsage3))) / 100);
    }

    /* access modifiers changed from: package-private */
    public void prevList(PoolChunkList<T> prevList2) {
        if (this.prevList == null) {
            this.prevList = prevList2;
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public boolean allocate(PooledByteBuf<T> buf, int reqCapacity, int normCapacity) {
        if (this.head == null || normCapacity > this.maxCapacity) {
            return false;
        }
        PoolChunk<T> cur = this.head;
        do {
            long handle = cur.allocate(normCapacity);
            if (handle < 0) {
                cur = cur.next;
            } else {
                cur.initBuf(buf, handle, reqCapacity);
                if (cur.usage() < this.maxUsage) {
                    return true;
                }
                remove(cur);
                this.nextList.add(cur);
                return true;
            }
        } while (cur != null);
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean free(PoolChunk<T> chunk, long handle) {
        chunk.free(handle);
        if (chunk.usage() >= this.minUsage) {
            return true;
        }
        remove(chunk);
        return move0(chunk);
    }

    private boolean move(PoolChunk<T> chunk) {
        if (chunk.usage() >= this.maxUsage) {
            throw new AssertionError();
        } else if (chunk.usage() < this.minUsage) {
            return move0(chunk);
        } else {
            add0(chunk);
            return true;
        }
    }

    private boolean move0(PoolChunk<T> chunk) {
        PoolChunkList<T> poolChunkList = this.prevList;
        if (poolChunkList != null) {
            return poolChunkList.move(chunk);
        }
        if (chunk.usage() == 0) {
            return false;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public void add(PoolChunk<T> chunk) {
        if (chunk.usage() >= this.maxUsage) {
            this.nextList.add(chunk);
        } else {
            add0(chunk);
        }
    }

    /* access modifiers changed from: package-private */
    public void add0(PoolChunk<T> chunk) {
        chunk.parent = this;
        PoolChunk<T> poolChunk = this.head;
        if (poolChunk == null) {
            this.head = chunk;
            chunk.prev = null;
            chunk.next = null;
            return;
        }
        chunk.prev = null;
        chunk.next = poolChunk;
        poolChunk.prev = chunk;
        this.head = chunk;
    }

    private void remove(PoolChunk<T> cur) {
        if (cur == this.head) {
            PoolChunk<T> poolChunk = cur.next;
            this.head = poolChunk;
            if (poolChunk != null) {
                poolChunk.prev = null;
                return;
            }
            return;
        }
        PoolChunk<T> next = cur.next;
        PoolChunk<T> poolChunk2 = cur.prev;
        poolChunk2.next = next;
        if (next != null) {
            next.prev = poolChunk2;
        }
    }

    public int minUsage() {
        return minUsage0(this.minUsage);
    }

    public int maxUsage() {
        return Math.min(this.maxUsage, 100);
    }

    private static int minUsage0(int value) {
        return Math.max(1, value);
    }

    public Iterator<PoolChunkMetric> iterator() {
        synchronized (this.arena) {
            if (this.head == null) {
                Iterator<PoolChunkMetric> it = EMPTY_METRICS;
                return it;
            }
            List<PoolChunkMetric> metrics = new ArrayList<>();
            PoolChunk<T> cur = this.head;
            do {
                metrics.add(cur);
                cur = cur.next;
            } while (cur != null);
            Iterator<PoolChunkMetric> it2 = metrics.iterator();
            return it2;
        }
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        synchronized (this.arena) {
            PoolChunk<T> cur = this.head;
            if (cur == null) {
                return "none";
            }
            while (true) {
                buf.append(cur);
                cur = cur.next;
                if (cur == null) {
                    return buf.toString();
                }
                buf.append(StringUtil.NEWLINE);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void destroy(PoolArena<T> arena2) {
        for (PoolChunk<T> chunk = this.head; chunk != null; chunk = chunk.next) {
            arena2.destroyChunk(chunk);
        }
        this.head = null;
    }
}
